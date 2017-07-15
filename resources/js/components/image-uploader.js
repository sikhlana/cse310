App.ImageUploader = function(container) { this.init(container); };
App.ImageUploader.prototype =
{
    init: function(container)
    {
        this.container = container;
        this.container.data('app', this);

        this.container.get(0).addEventListener('dragover', $.context(this, 'dragover'), false);
        this.container.get(0).addEventListener('dragleave', $.context(this, 'dragleave'), false);
        this.container.get(0).addEventListener('drop', $.context(this, 'drop'), false);

        var sentinel = container.find('.sentinel').removeClass('sentinel');
        this.sentinel = $("<p>").append(sentinel.clone()).html();
        sentinel.remove();

        this.container.find('.image').each(function()
        {
            new App.ImageUploader.Image(container, $(this));
        });

        this.editor = $('#image-edit-form').eq(0);
    },

    dragover: function(e)
    {
        e.stopPropagation();
        e.preventDefault();

        this.container.addClass('dragging');
    },

    dragleave: function(e)
    {
        e.stopPropagation();
        e.preventDefault();

        this.container.removeClass('dragging');
    },

    drop: function(e)
    {
        this.dragleave(e);

        var files = e.target.files || e.dataTransfer.files;
        if (files.length > 0)
        {
            for (var i = 0; i < files.length; i++)
            {
                this.processDroppedFile(files[i]);
            }
        }
        else
        {
            var url = e.dataTransfer.getData('Text');
            this.processDroppedLink(url);
        }
    },

    processDroppedFile: function(file)
    {
        if (!file.type.startsWith('image/'))
        {
            App.alert('error', 'Error', 'The provided file is not a valid image.');
            return;
        }

        var image = new App.ImageUploader.Image(this.container, $(this.sentinel), true);
        image.upload(file);
    },

    processDroppedLink: function(url)
    {
        App.ajax(this.container.data('import-url'), 'POST', {
            url: url,
            temp_hash: this.container.data('temp-hash')
        }, $.context(this, 'processServerResponse', [
            new App.ImageUploader.Image(this.container, $(this.sentinel), true)
        ]));
    },

    processServerResponse: function(image, data)
    {
        image.setImage(data.viewLink);
        image.setEditUrl(data.editLink);
        image.setDeleteUrl(data.deleteLink);

        if (!data.imported)
        {
            image.postUpload();
        }
    }
};

App.ImageUploader.Image = function(container, image, append) { this.init(container, image, append) };
App.ImageUploader.Image.prototype =
{
    init: function(container, image, append)
    {
        this.container = container;
        this.image = image;

        this.container.removeClass('empty');

        if (append)
        {
            this.container.append(this.image);
        }

        this.progress = image.find('.upload-progress');
        image.find('.edit-image').on('click', $.context(this, 'edit'));
        image.find('.delete-image').on('click', $.context(this, 'delete'));
    },

    setImage: function(data)
    {
        this.image.find('.preview').attr('src', data);
    },

    upload: function(file)
    {
        this.image.addClass('uploading');

        var data = new FormData;
        data.append('temp_hash', this.container.data('temp-hash'));
        data.append('file', file);

        App.ajax(
            this.container.data('upload-url'), 'POST', data,
            $.context(this.container.data('app'), 'processServerResponse', [this]),
            { onUploadProgress: $.context(this, 'uploadProgress') }
        );
    },

    uploadProgress: function(e)
    {
        this.progress.css('width', Math.round((e.loaded * 100) / e.total ));
    },

    postUpload: function()
    {
        this.image.removeClass('uploading');
    },

    setEditUrl: function(link)
    {
        this.image.data('edit-url', link);
    },

    edit: function(e)
    {
        e.preventDefault();
        var self = this,
            editor = this.container.data('app').editor;

        App.ajax(this.image.data('edit-url'), 'get', {}, function(data)
        {
            editor.find('form.modal-content').attr('action', data.saveLink);
            editor.find('.modal-body').html(data.html);
            editor.find('.image-cropper').cropper({
                viewMode: 2, aspectRatio: 3 / 4, crop: $.context(self, 'crop', [editor])
            });

            editor.modal('show');
        });
    },

    crop: function(editor, e)
    {
        editor.find('[name=x]').val(e.x);
        editor.find('[name=y]').val(e.y);
        editor.find('[name=height]').val(e.height);
        editor.find('[name=width]').val(e.width);
    },

    setDeleteUrl: function(link)
    {
        this.image.data('delete-url', link);
    },

    delete: function()
    {
        swal(
        {
            title: "Are you sure?",
            text: "You will not be able to recover this image!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, $.context(this, 'confirmedDelete'));
    },

    confirmedDelete: function()
    {
        App.ajax(this.image.data('delete-url'), 'delete', {}, $.context(this, 'postDelete'));
    },

    postDelete: function()
    {
        this.image.remove();
        App.alert('success', 'Deleted!', 'The image has been successfully delete.', 2000);

        if (this.container.find('.image').length < 1)
        {
            this.container.addClass('empty');
        }
    }
};

App.register('.image-uploader', 'App.ImageUploader');