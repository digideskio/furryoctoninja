using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    interface ISurveyRepository : IRepository<Survey>
    {
        Survey GetSurvey();
        void RecreteSurvey(SurveyDTO dto);
        void MakeCorrections(SurveyDTO surveyDTO);
    }
}
