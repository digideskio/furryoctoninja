using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Repositories;
using System;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    class SurveyService : ISurveyService
    {
        private IUnitOfWork ouw;
        private ISurveyRepository surveyRepository;

        public SurveyService(IUnitOfWork ouw, ISurveyRepository surveyRepository)
        {
            this.ouw = ouw;
            this.surveyRepository = surveyRepository;
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
    }
}
