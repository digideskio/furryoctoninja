(function () {
    angular.module("surveyapp", ["ngRoute", "angular-loading-bar", "LocalStorageModule"])
        .config(configure)
        .run(run);

    configure.$inject = ["localStorageServiceProvider"];

    function configure(localStorageServiceProvider) {
        localStorageServiceProvider.setPrefix('myApsurveyApp');
    }

    run.$inject = ["$rootScope"];

    function run($rootScope) {

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