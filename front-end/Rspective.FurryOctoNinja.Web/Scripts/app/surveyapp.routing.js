(function () {
    angular.module("surveyapp")
        .config(configureRouting);

    configureRouting.$inject = ["$routeProvider"];

    function configureRouting($routeProvider) {
        $routeProvider
            .when('/survey', {
                controller: 'MainController',
                controllerAs: 'main',
                templateUrl: '../../Templates/survey.html',
                resolve: {
                    survey: ["api", function (api) {
                        return api.survey.get();
                    }]
                }
            });
    }
})();