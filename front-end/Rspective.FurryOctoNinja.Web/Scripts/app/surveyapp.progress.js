(function () {
    angular.module("surveyapp")
        .controller("SurveyProgressController", progress);

    progress.$inject = ["dataRefresher", "api", "progress"];

    function progress(dataRefresher, api, progress) {
        var self = this;

        self.doNotShow = null;
        self.progress = progress;

        dataRefresher.addTemporary(function () {
            api.survey.progress()
                .then(function (progress) {
                    self.users = progress;
                });
        }, 45);
    }
})();