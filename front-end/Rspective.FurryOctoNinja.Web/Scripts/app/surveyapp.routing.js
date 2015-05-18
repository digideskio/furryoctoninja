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
            })
            .when('/admin/edit', {
                controller: 'EditSurveyController',
                controllerAs: 'edit',
                templateUrl: '../../Templates/editor.html',
                resolve: {
                    survey: ["api", function (api) {
                        return api.survey.get();
                    }]
                }
            })
            .otherwise({
                redirectTo: '/survey'
            })
        ;
        >>>>>>> 04b1a0318810649f69188256fbc0b7f19e309b60
    }
})();