(function () {
    angular.module("surveyapp")
        .controller("EditSurveyController", edit);

    edit.$inject = ["api", "survey"];

    function edit(api, survey) {
        var self = this;

        api.auth.refresh();

        self.survey = survey;
    }
})();