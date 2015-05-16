using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System.Data.Entity;

namespace Rspective.FurryOctoNinja.DataAccess
{
    class SurveyAppDbSeed : CreateDatabaseIfNotExists<SurveyAppContext>
    {
        protected override void Seed(SurveyAppContext context)
        {
            base.Seed(context);
        }
    }
}
