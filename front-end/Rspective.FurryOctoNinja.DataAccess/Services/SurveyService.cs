using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.Repositories;
using System.Linq;

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

        public Survey GetSurvey()
        {
            return surveyRepository.All().ToList().ElementAt(0);
        }
    }
}
