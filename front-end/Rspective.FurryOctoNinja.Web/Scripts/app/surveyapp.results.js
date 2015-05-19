(function () {
    angular.module("surveyapp")
        .controller("SurveyResultsController", results);

    results.$inject = ["api", "results"];

    function results(api, results) {
        var self = this;

        api.auth.refresh();

        self.json = JSON.stringify(results, null, 4);
        self.results = results;
    }
})();