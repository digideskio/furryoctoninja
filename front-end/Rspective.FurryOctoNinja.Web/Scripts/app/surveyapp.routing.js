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
                    survey: ["api", "$location", function (api, $location) {
                        return api.survey.get()
                            .then(function (data) {
                                if (data.CompletedByUser) {
                                    return $location.path("/survey/results");
                                }
                                return data;
                            });
                    }]
                }
            })
            .when('/survey/results', {
                controller: 'SurveyResultsController',
                controllerAs: 'results',
                templateUrl: '../../Templates/results.html',
                resolve: {
                    results: ["api", function (api) {
                        return api.survey.results();
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
                controller: 'UserController',
                controllerAs: 'user',
                templateUrl: '../../Templates/users.html',
                resolve: {
                    users: ["api", function (api) {
                        return api.user.all();
                    }]
                }
            });
    }
})();