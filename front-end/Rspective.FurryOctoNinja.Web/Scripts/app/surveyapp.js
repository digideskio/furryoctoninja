(function () {
    angular.module("surveyapp", ["ngRoute", "angular-loading-bar", "LocalStorageModule", "surveyapp.directives"])
        .config(configure)
        .run(run);

    configure.$inject = ["localStorageServiceProvider"];

    function configure(localStorageServiceProvider) {
        localStorageServiceProvider.setPrefix('myApsurveyApp');
    }

    run.$inject = ["$rootScope", "$location", "authStorage"];

    function run($rootScope, $location, authStorage) {
        $rootScope.loadView = function (path) {
            $location.path(path);
        };
        $rootScope.isView = function (path) {
            return $location.path() === path;
        };
        $rootScope.isAuthenticated = function () {
            return authStorage.isAuthenticated();
        };
        $rootScope.isAdmin = function () {
            return authStorage.isAdmin();
        };
        $rootScope.logout = function () {
            authStorage.save(null);
            window.location = "/";
        };
        $rootScope.validateForm = function (form) {
            form._validated = true;

            for (var fieldName in form) {
                if (fieldName[0] != '$') {
                    var formField = form[fieldName];
                    formField.$pristine && formField.$setViewValue(formField.$modelValue);
                }
            }

            return form.$valid;
        };
    }
})();