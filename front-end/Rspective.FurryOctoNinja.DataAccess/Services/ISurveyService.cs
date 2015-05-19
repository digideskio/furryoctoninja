using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    public interface ISurveyService
    {
        SurveyDTO GetSurvey();

        void SaveUserAnswers(ICollection<SurveyAnswerDTO> answers);

        ICollection<SurveyAnswerDTO> GetAnswers(int? userId);

        ICollection<SurveyResultsDTO> GetResults();
    }
}
