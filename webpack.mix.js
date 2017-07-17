const { mix } = require('laravel-mix');

/*
 |--------------------------------------------------------------------------
 | Mix Asset Management
 |--------------------------------------------------------------------------
 |
 | Mix provides a clean, fluent API for defining some Webpack build steps
 | for your Laravel application. By default, we are compiling the Sass
 | file for the application as well as bundling up all the JS files.
 |
 */

mix.js('resources/assets/js/app.js', 'resources/static/js');
mix.less('resources/assets/less/app.less', 'resources/static/css');
mix.sass('resources/assets/scss/libs.scss', 'resources/static/css');

mix.disableNotifications();