(function () {
    angular.module("surveyapp")
        .controller("SurveyProgressController", progress);

    progress.$inject = ["dataRefresher", "api", "progress"];

    function progress(dataRefresher, api, progress) {
        var self = this;

        self.resetDisabled = false;
        self.reset = function () {
            self.resetDisabled = true;
            api.survey.reset()
                .then(function () {
                    return api.survey.progress()
                })
                .then(function (progress) {
                    self.progress = progress;
                })
                .then(function () {
                    self.resetDisabled = false;
                });
        };

        self.doNotShow = null;
        self.progress = progress;

        dataRefresher.addTemporary(function () {
            api.survey.progress()
                .then(function (progress) {
                    self.progress = progress;
                });
        }, 45);
    }
})();