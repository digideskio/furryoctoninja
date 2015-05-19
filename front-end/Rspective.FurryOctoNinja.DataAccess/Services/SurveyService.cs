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

        public void SaveUserAnswers(ICollection<SurveyAnswerDTO> answers)
        {
            foreach (var answer in answers) {
                this.userAnswerRepository.Create(Mapper.Map<UserAnswer>(answer));
            }
        }

        public ICollection<SurveyAnswerDTO> GetAnswers(int? userId)
        {
            var survey = this.GetSurvey();
            var answers = userId.HasValue ? 
                this.userAnswerRepository.GetForSingleUser(survey.Id, userId.Value)
                : this.userAnswerRepository.GetForSurvey(survey.Id);

            return Mapper.Map<ICollection<SurveyAnswerDTO>>(answers);
        }

        public ICollection<SurveyResultsDTO> GetResults()
        {
            var survey = this.GetSurvey();
            var answers = this.userAnswerRepository.GetForSurvey(survey.Id);
            var users = this.userRepository.All();
            var result = new List<SurveyResultsDTO>();

            foreach (var user in users)
            {
                result.Add(new SurveyResultsDTO()
                {
                    SurveyId = survey.Id,
                    UserId = user.Id,
                    UserName = user.Name,
                    Answered = answers.Any(answer => answer.SurveyId == survey.Id && answer.UserId == user.Id)
                });
            }

            return result;
        }
    }
}
