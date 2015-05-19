(function () {
    angular.module("surveyapp")
        .controller("SurveyResultsController", results);

    results.$inject = ["dataRefresher", "api", "results"];

    function results(dataRefresher, api, results) {
        var self = this;

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