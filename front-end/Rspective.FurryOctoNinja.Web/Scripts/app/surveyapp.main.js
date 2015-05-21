(function () {
    angular.module("surveyapp")
        .controller("MainController", main);

    main.$inject = ["api", "survey", "$location"];

    function main(api, survey, $location) {
        var self = this;

        self.survey = survey;

        self.saveSurvey = {};
        self.saveSurvey.Modified = survey.CreatedDate;
        self.saveSurvey.Id = survey.Id;
        self.saveSurvey.Answers = [];

        self.validate = function () {
            self.errors = [];
            self.isValid = true;
            self.saveSurvey.Answers.forEach(function (answer, index) {
                if (!answer.AnswerId) {
                    self.errors.push(index + 1);
                }
                self.isValid = self.isValid && !!answer.AnswerId;
            });
        };

        self.submit = function () {
            self.validate();

            if (self.isValid) {
                api.survey.post(self.saveSurvey).then(function () {
                    $location.path("/survey/results");
                });
            }
        }
    }
})();