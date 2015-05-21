using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Helpers
{
    class SurveyValidator
    {
        private readonly SurveyDTO survey;
        private readonly Survey original;
        private readonly bool hasAnyoneCompleted;

        private const int MinQuestionsCount = 5;
        private const int MaxQuestionsCount = 20;

        private const int MinAnswersCount = 2;
        private const int MaxAnswersCount = 8;

        public SurveyValidator(SurveyDTO survey, Survey original, bool hasAnyoneCompleted)
        {
            if (survey == null)
            {
                throw new ArgumentNullException("survey");
            }

            if (original == null)
            {
                throw new ArgumentNullException("original");
            }

            this.survey = survey;
            this.original = original;
            this.hasAnyoneCompleted = hasAnyoneCompleted;
        }

        public ValidateSurveyDTO Validate()
        {
            if (this.hasAnyoneCompleted)
            {
                return this.ValidateIfSurveyHasChanged();
            }

            var result = new ValidateSurveyDTO() { };

            // exclude empty questions with all empty answers from validation
            var questions = (this.survey.Questions ?? new List<QuestionDTO>());
            questions = questions.Except(questions.Where(q => string.IsNullOrEmpty(q.Text) && q.Answers.All(a => string.IsNullOrEmpty(a.Text))));

            if (questions.Count() < MinQuestionsCount || questions.Count() > MaxQuestionsCount)
            {
                result.OverallErrors.Add(string.Format("Survey cannot have less than {0} and more than {1} questions.", MinQuestionsCount, MaxQuestionsCount));
            }

            foreach (QuestionDTO question in questions)
            {
                var questionError = string.Empty;
                var answersError = string.Empty;

                if (string.IsNullOrEmpty(question.Text))
                {
                    questionError = "Question cannot be empty.";
                }

                question.Answers = question.Answers.Where(a => !string.IsNullOrEmpty(a.Text));
                if (question.Answers.Count() < MinAnswersCount || question.Answers.Count() > MaxAnswersCount)
                {
                    answersError = string.Format("Question cannot have less than {0} and more than {1} answers.", MinAnswersCount, MaxAnswersCount);
                }

                result.QuestionsErrors.Add(questionError);
                result.AnswersErrors.Add(answersError);
            }

            result.ValidatedSurvey = this.survey;
            result.ValidatedSurvey.Questions = questions;

            return result;
        }

        private ValidateSurveyDTO ValidateIfSurveyHasChanged()
        {
            var result = new ValidateSurveyDTO() { };

            // check for addition of question/answer
            if (this.survey.Questions.Any(q => q.Id == default(int)) ||
                this.survey.Questions.SelectMany(q => q.Answers).Any(a => a.Id == default(int)))
            {
                result.OverallErrors.Add("You cannot add new questions or answers if anyone has completed the survey.");
            }

            // check for removal of question/answer
            if (this.survey.Questions.Count(q => q.Id != default(int)) != this.original.Questions.Count() ||
                this.survey.Questions.SelectMany(q => q.Answers).Count(a => a.Id != default(int)) != this.original.Questions.SelectMany(q => q.Answers).Select(a => a.Id).Count())
            {
                result.OverallErrors.Add("You cannot remove existing questions or answers if anyone has completed the survey.");
            }

            // restore survey

            result.ValidatedSurvey = result.IsValid ? this.survey : Mapper.Map<SurveyDTO>(this.original);
            return result;
        }
    }
}
