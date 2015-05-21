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
        public DbSet<UserAnswer> UserAnswers { get; set; }

        public DbSet<ApplicationClient> ApplicationClients { get; set; }
        public DbSet<ApplicationUser> ApplicationUsers { get; set; }
        public DbSet<ApplicationUserRole> ApplicationUserRoles { get; set; }
        public DbSet<ApplicationToken> ApplicationTokens { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            /*modelBuilder.Entity<Survey>()
                .HasMany(s => s.Questions)
                .WithRequired(q => q.Survey)
                .WillCascadeOnDelete(true);

            modelBuilder.Entity<Question>()
                .HasMany(s => s.Answers)
                .WithRequired(q => q.Question)
                .WillCascadeOnDelete(true);

            modelBuilder.Entity<UserAnswer>()
                .HasRequired(ua => ua.Answer)
                .WithRequiredDependent()
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<UserAnswer>()
                .HasRequired(ua => ua.Question)
                .WithRequiredDependent()
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<UserAnswer>()
                .HasRequired(ua => ua.Survey)
                .WithRequiredDependent()
                .WillCascadeOnDelete(false);*/

            base.OnModelCreating(modelBuilder);
        }
    }
}
