(function () {
    angular.module("surveyapp")
        .controller("EditSurveyController", edit);

    edit.$inject = ["api", "survey"];

    function edit(api, survey) {
        var self = this;
        
        api.auth.refresh();

        self.displayedQuestion = 0;
        self.updateDisplayedQuestion = function () {
            self.displayedQuestion = Math.min(self.displayedQuestion, (self.survey.Question || []).length);
        };

        self.addQuestion = function () {
            self.survey.Questions.push({ Text: '', Answers: [] });
            self.displayedQuestion = self.survey.Questions.length - 1;
        };

        self.notifyDisabled = false;
        self.notifyMobileDevices = function () {
            self.notifyDisabled = true;
            api.survey.notify()
                .done(function () {
                    self.notifyDisabled = false;
                })
        };

        self.survey = survey;
    }
})();