(function () {
    angular.module("surveyapp")
        .controller("SurveyResultsController", results);

    results.$inject = ["dataRefresher", "api", "results"];

    function results(dataRefresher, api, results) {
        var self = this;

        self.resetDisabled = false;
        self.reset = function () {
            self.resetDisabled = true;
            api.survey.reset()
                .then(function () {
                    return api.survey.results();   
                })
                .then(function (results) {
                    self.results = results;
                })
                .then(function() {
                    self.resetDisabled = false;
                })
                .catch(function() {
                    self.resetDisabled = false;
                });
        };

        self.results = results;

        dataRefresher.addTemporary(function () {
            api.survey.results()
               .then(function (results) {
                   self.results = results;
               });
        }, 45);
    }
})();