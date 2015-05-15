using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class SurveyRepository : Repository<Survey>, ISurveyRepository
    {
        public SurveyRepository(IUnitOfWork ouw)
            : base(ouw)
        {

        }
    }
}
