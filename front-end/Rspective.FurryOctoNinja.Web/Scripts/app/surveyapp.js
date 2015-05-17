(function () {
    angular.module("surveyapp", [])
        .config(configure)
        .run(run);

    configure.$inject = [];

    function configure() {
    }

    run.$inject = [ "$rootScope" ];

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