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
            .when('/admin/survey', {
                controller: 'EditSurveyController',
                controllerAs: 'edit',
                templateUrl: '../../Templates/editor.html',
                resolve: {
                    survey: ["api", function (api) {
                        return api.survey.get();
                    }]
                }
            })
            .when('/admin/results', {
                controller: 'SurveyResultsController',
                controllerAs: 'results',
                templateUrl: '../../Templates/results.html',
                resolve: {
                    results: ["api", function (api) {
                        return api.survey.results();
                    }]
                }
            })
            .when('/admin/progress', {
                controller: 'SurveyProgressController',
                controllerAs: 'progress',
                templateUrl: '../../Templates/progress.html',
                resolve: {
                    progress: ["api", function (api) {
                        return api.survey.progress();
                    }]
                }
            })
            .when('/admin/users', {
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