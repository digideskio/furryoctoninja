using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System.Data.Entity;

namespace Rspective.FurryOctoNinja.DataAccess
{
    class SurveyAppContext : DbContext
    {
        public SurveyAppContext()
            : base("name=surveyapp")
        {
        }

        public DbSet<Survey> Surveys { get; set; }
        public DbSet<Question> Questions { get; set; }
        public DbSet<Answer> Answers { get; set; }

        public DbSet<ApplicationClient> ApplicationClients { get; set; }
        public DbSet<ApplicationUser> ApplicationUsers { get; set; }
        public DbSet<ApplicationToken> ApplicationTokens { get; set; }
    }
}
