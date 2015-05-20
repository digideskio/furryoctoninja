(function () {
    angular.module("surveyapp")
        .controller("EditSurveyController", edit);

    edit.$inject = ["api", "survey"];

    function edit(api, survey) {
        var self = this;

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
                .then(function () {
                    self.notifyDisabled = false;
                })
        };

        self.mode = "editor";
        self.submit = function () {
            api.survey
                .validate(self.survey)
                .then(function (result) {
                    self.mode = "validation";
                    self.survey = result.ValidatedSurvey;

                    self.errors = {};
                    self.errors.overral = result.OverallErrors;
                    self.errors.questions = result.QuestionsErrors;
                    self.errors.answers = result.AnswersErrors;

                    return result.IsValid;
                })
                .then(function (isValid) {
                    if (isValid) {
                        api.survey
                            .update(self.survey)
                            .then(function (survey) {
                                self.survey = survey;
                                self.mode = "editor";
                            });
                    }
                });
        };

        self.survey = survey;
    }
})();