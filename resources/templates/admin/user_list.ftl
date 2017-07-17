${container("title", "Users")}

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1 class="page-title">Users</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">
            <#list users>
                <div class="list-header">
                    <h3>Registered Users</h3>
                </div>

                <ol id="user-list" class="item-list">
                    <#items as user>
                        <li>
                            <a href="${adminlink("users/edit", user)}" class="anchor">
                                <h4 class="title">${user.name}</h4>
                            </a>

                            <a class="delete-item delete-button" href="${adminlink("users/delete", user)}">
                                <i class="fa fa-times"></i>
                            </a>
                        </li>
                    </#items>
                </ol>

                <div class="list-footer">
                    Showing ${users?size} of ${users?size} items.
                </div>
            <#else>
                <p id="empty-list">No users have registered yet.</p>
            </#list>
        </div>
    </div>
</div>