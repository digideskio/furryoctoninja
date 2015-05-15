using System.Data.Entity;

namespace Rspective.FurryOctoNinja.DataAccess.Context
{
    class DbContextFactory : IDbContextFactory
    {
        private readonly DbContext context;

        public DbContextFactory()
        {
            context = new SurveyAppContext();
        }

        public DbContext GetContext()
        {
            return context;
        }
    }
}
