using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class SurveyRepository : Repository<Survey>, ISurveyRepository
    {
        public SurveyRepository(IUnitOfWork ouw)
            : base(ouw)
        {
        }

        public Survey GetSurvey()
        {
            return this.DbSet
                .Include("Questions")
                .Include("Questions.Answers")
                .FirstOrDefault();
        }
    }
}
