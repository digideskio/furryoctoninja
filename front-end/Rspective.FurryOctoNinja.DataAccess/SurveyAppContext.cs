using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System.Data.Entity;

namespace Rspective.FurryOctoNinja.DataAccess
{
    class SurveyAppContext : DbContext
    {
        public SurveyAppContext()
            : base("name=surveyapp")
        {
            Database.SetInitializer<SurveyAppContext>(new SurveyAppDbSeed());
        }

        public DbSet<Survey> Surveys { get; set; }
        public DbSet<Question> Questions { get; set; }
        public DbSet<Answer> Answers { get; set; }
    }
}
