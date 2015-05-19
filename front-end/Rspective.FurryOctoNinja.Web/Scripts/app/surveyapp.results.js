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
                    return api.survey.results()
                        .then(function (results) {
                            self.json = JSON.stringify(results, null, 4);
                            self.results = results;
                        });
                })
                .then(function() {
                    self.resetDisabled = false;
                });
        };

        self.json = JSON.stringify(results, null, 4);
        self.results = results;

        dataRefresher.addTemporary(function () {
            api.survey.results()
               .then(function (results) {
                   self.json = JSON.stringify(results, null, 4);
                   self.results = results;
               });
        }, 45);
    }
})();