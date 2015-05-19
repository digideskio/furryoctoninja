(function () {
    angular.module("surveyapp")
        .controller("SurveyProgressController", progress);

    progress.$inject = ["dataRefresher", "api", "progress"];

    function progress(dataRefresher, api, progress) {
        var self = this;

        self.json = JSON.stringify(progress, null, 4);
        self.progress = progress;

        dataRefresher.addTemporary(function () {
            api.survey.progress()
                .then(function (progress) {
                    self.json = JSON.stringify(progress, null, 4);
                    self.users = progress;
                });
        }, 45);
    }
})();