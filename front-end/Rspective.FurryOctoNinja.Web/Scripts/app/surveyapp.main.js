(function () {
    angular.module("surveyapp")
        .controller("MainController", main);

    main.$inject = ["api", "survey"];

    function main(api, survey) {
        api.auth.refresh();
        this.survey = survey;

        this.saveSurvey = {};
        this.saveSurvey.Id = survey.Id;
        this.saveSurvey.Answers = [];
    }
})();