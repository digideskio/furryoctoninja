using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    public interface ISurveyService
    {
        SurveyDTO GetSurvey();

        void SaveUserAnswers(ICollection<SurveyAnswerDTO> answers);

        SurveyResultsDTO GetResults(int userId);

        SurveyProgressDTO GetProgress();
    }
}
