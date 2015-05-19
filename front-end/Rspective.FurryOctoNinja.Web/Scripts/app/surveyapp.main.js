(function () {
    angular.module("surveyapp")
        .controller("MainController", main);

    main.$inject = ["api", "survey"];

    function main(api, survey) {
        var self = this;

        self.survey = survey;

        self.saveSurvey = {};
        self.saveSurvey.Id = survey.Id;
        self.saveSurvey.Answers = [];

        self.submit = function () {
            api.survey.post(self.saveSurvey);
        }
    }
})();