using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    class SurveyService : ISurveyService
    {
        private const int MinQuestionsCount = 5;
        private const int MaxQuestionsCount = 20;

        private const int MinAnswersCount = 2;
        private const int MaxAnswersCount = 8;

        private IUnitOfWork ouw;
        private ISurveyRepository surveyRepository;
        private IUserAnswerRepository userAnswerRepository;
        private IApplicationUserRepository userRepository;

        public SurveyService(IUnitOfWork ouw, ISurveyRepository surveyRepository, IUserAnswerRepository userAnswerRepository, IApplicationUserRepository userRepository)
        {
            this.ouw = ouw;
            this.surveyRepository = surveyRepository;
            this.userAnswerRepository = userAnswerRepository;
            this.userRepository = userRepository;
        }

        public SurveyDTO GetSurvey()
        {
            var survey = surveyRepository.GetSurvey();

            // TODO: Create Survet if it's empty
            if (survey != null)
            {
                return Mapper.Map<SurveyDTO>(survey);
            }

            throw new InvalidOperationException();
        }

        public bool HasCompleted(int userId)
        {
            var survey = this.GetSurvey();
            return this.userAnswerRepository.HasCompleted(survey.Id, userId);
        }

        public void SaveSurvey(int userId, SurveySaveDTO survey)
        {
            if (this.HasCompleted(userId))
            {
                // TODO: Handle already saved
                return;
            }

            foreach (var answer in survey.Answers)
            {
                var userAnswer = Mapper.Map<UserAnswer>(answer);
                userAnswer.UserId = userId;
                userAnswer.SurveyId = survey.Id;
                this.userAnswerRepository.Create(userAnswer);
            }
        }

        public SurveyResultsDTO GetResults(int userId)
        {
            var survey = this.GetSurvey();
            var answers = this.userAnswerRepository.GetForSurvey(survey.Id);
            var userAnswers = this.userAnswerRepository.GetForSingleUser(survey.Id, userId);

            var result = Mapper.Map<SurveyResultsDTO>(survey);

            foreach (var question in result.Questions)
            {
                foreach (var answer in question.Answers)
                {
                    answer.Count = answers.Count(surveyAnswer => surveyAnswer.AnswerId == answer.Id && surveyAnswer.QuestionId == question.Id);
                    answer.IsUserChoice = userAnswers.Any(userAnswer => userAnswer.AnswerId == answer.Id && userAnswer.QuestionId == question.Id);
                }
            }

            return result;
        }

        public SurveyProgressDTO GetProgress()
        {
            var survey = this.GetSurvey();
            var answers = this.userAnswerRepository.GetForSurvey(survey.Id);
            var users = this.userRepository.GetAllUsers();
            var items = new List<SurveyProgressItemDTO>();

            foreach (var user in users)
            {
                items.Add(new SurveyProgressItemDTO()
                {
                    UserId = user.Id,
                    UserName = user.Name,
                    Completed = answers.Any(answer => answer.SurveyId == survey.Id && answer.UserId == user.Id)
                });
            }

            var result = Mapper.Map<SurveyProgressDTO>(survey);
            result.Items = items;
            return result;
        }

        public void Reset()
        {
            var survey = this.GetSurvey();
            this.userAnswerRepository.Reset(survey.Id);
        }

        public ValidateSurveyDTO Validate(SurveyDTO survey)
        {
            if (survey == null)
            {
                throw new ArgumentNullException("survey");
            }

            var result = new ValidateSurveyDTO()
            {
                OverallErrors = new List<string>(),
                QuestionsErrors = new List<string>(),
                AnswersErrors = new List<string>(),
            };

            // exclude empty questions with all empty answers from validation
            var questions = (survey.Questions ?? new List<QuestionDTO>());
            questions = questions.Except(questions.Where(q => string.IsNullOrEmpty(q.Text) && q.Answers.All(a => string.IsNullOrEmpty(a.Text))));

            if (questions.Count() < MinQuestionsCount || questions.Count() > MaxQuestionsCount)
            {
                result.OverallErrors.Add(string.Format("Survey can have number of questions between {0} and {1}.", MinQuestionsCount, MaxQuestionsCount));
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
                    answersError = string.Format("Question can have number of answers between {0} and {1}.", MinAnswersCount, MaxAnswersCount);
                }

                result.QuestionsErrors.Add(questionError);
                result.AnswersErrors.Add(answersError);
            }

            survey.Questions = questions;
            result.ValidatedSurvey = survey;

            result.IsValid =
                !result.OverallErrors.Any() &&
                !result.QuestionsErrors.Any(x => !string.IsNullOrEmpty(x)) &&
                !result.AnswersErrors.Any(x => !string.IsNullOrEmpty(x));

            return result;
        }
    }
}
