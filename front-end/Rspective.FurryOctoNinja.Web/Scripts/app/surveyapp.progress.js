(function () {
    angular.module("surveyapp")
        .controller("SurveyProgressController", progress);

    progress.$inject = ["api", "progress"];

    function progress(api, progress) {
        var self = this;

        api.auth.refresh();

        self.json = JSON.stringify(progress, null, 4);
        self.progress = progress;
    }
})();