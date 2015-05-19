using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    public interface ISurveyService
    {
        SurveyDTO GetSurvey();

        void SaveSurvey(int userId, SurveySaveDTO surveySave);

        bool HasCompleted(int userId);

        SurveyResultsDTO GetResults(int userId);

        SurveyProgressDTO GetProgress();

        void Reset();
    }
}
