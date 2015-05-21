using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Helpers;
using Rspective.FurryOctoNinja.DataAccess.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    class SurveyService : ISurveyService
    {
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

        public SurveyDTO GetSurvey(int? userId)
        {
            var survey = surveyRepository.GetSurvey();

            if (survey == null)
            {
                survey = this.surveyRepository.Create(new Survey()
                {
                    Title = "Title",
                    Description = "Description",
                    CreatedDate = DateTime.UtcNow
                });
            }

            var result = Mapper.Map<SurveyDTO>(survey);

            if (userId.HasValue)
            {
                result.CompletedByUser = this.userAnswerRepository.HasCompleted(survey.Id, userId.Value);
            }

            return result;
        }

        public bool HasCompleted(int userId)
        {
            var survey = this.GetSurvey(null);
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
            var survey = this.GetSurvey(null);
            var answers = this.userAnswerRepository.GetForSurvey(survey.Id);
            var userAnswers = this.userAnswerRepository.GetForSingleUser(survey.Id, userId);

            var result = Mapper.Map<SurveyResultsDTO>(survey);
            result.CompletedByUser = this.userAnswerRepository.HasCompleted(survey.Id, userId);

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
            var survey = this.GetSurvey(null);
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
            var survey = this.GetSurvey(null);
            this.userAnswerRepository.Reset(survey.Id);
        }

        public ValidateSurveyDTO Validate(SurveyDTO survey)
        {
            if (survey == null)
            {
                throw new ArgumentNullException("survey");
            }

            return new SurveyValidator(survey, this.surveyRepository.GetSurvey(), this.userAnswerRepository.HasAnyoneCompleted(survey.Id)).Validate();
        }

        public void Save(SurveyDTO survey)
        {
            if (survey == null)
            {
                throw new ArgumentNullException("survey");
            }

            ValidateSurveyDTO result = this.Validate(survey);
            if (result.IsValid)
            {
                if (this.userAnswerRepository.HasAnyoneCompleted(survey.Id))
                {
                    this.surveyRepository.MakeCorrections(result.ValidatedSurvey);
                }
                else
                {
                    this.surveyRepository.RecreteSurvey(survey);
                }
            }
        }

        public bool ValidateSave(int userId, SurveySaveDTO surveySave)
        {
            var survey = this.GetSurvey(null);

            // TODO: Validate dates!

            if (this.userAnswerRepository.HasCompleted(surveySave.Id, userId))
            {
                return false;
            }

            return true;
        }
    }
}
