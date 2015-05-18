(function () {
    angular.module("surveyapp")
        .controller("MainController", main);

    main.$inject = ["api", "survey"];

    function main(api, survey) {
        var self = this;

        api.auth.refresh();

        self.survey = survey;

        self.saveSurvey = {};
        self.saveSurvey.Id = survey.Id;
        self.saveSurvey.Answers = [];
    }
})();