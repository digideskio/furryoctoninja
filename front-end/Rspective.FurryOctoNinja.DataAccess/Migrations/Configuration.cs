namespace Rspective.FurryOctoNinja.DataAccess.Migrations
{
    using Rspective.FurryOctoNinja.DataAccess.DbModel;
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<Rspective.FurryOctoNinja.DataAccess.SurveyAppContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
            ContextKey = "Rspective.FurryOctoNinja.DataAccess.SurveyAppContext";
        }

        protected override void Seed(Rspective.FurryOctoNinja.DataAccess.SurveyAppContext context)
        {
            this.SeedClients(context);
            this.SeedUsers(context);
            this.SeedSurveys(context);
        }

        private void SeedClients(SurveyAppContext context)
        {
            var mock = new ApplicationClient[] {
                new ApplicationClient() 
                {
                    Name = "angularjs",
                    PublicKey = "El246n9cf1minYI0YGcBVQ8971fK8Gfp",
                    SecretKey = "517DW0p493372N4s2753ply07p1tqe78"
                },
                new ApplicationClient()
                {
                    Name = "androidmobile",
                    PublicKey = "p35iw0R6RO1730BSK432qswrZldwY0jR",
                    SecretKey = "PX5ie6aj22t7xi3J3u4xXR6r90RXPo88"
                }
            };

            foreach (var entry in mock)
            {
                context.ApplicationClients.Add(entry);
            }
        }

        private void SeedUsers(SurveyAppContext context)
        {
            var mock = new ApplicationUser[] {
                new ApplicationUser()
                {
                    Login = "bandro"
                },
                new ApplicationUser()
                {
                    Login = "pavel"
                },
                new ApplicationUser()
                {
                    Login = "polok"
                },
                new ApplicationUser()
                {
                    Login = "roger"
                },
                new ApplicationUser()
                {
                    Login = "admin"
                }
            };

            foreach (var entry in mock)
            {
                context.ApplicationUsers.Add(entry);
            }
        }

        private void SeedSurveys(SurveyAppContext context)
        {
            var mock = new Survey()
            {
                Title = "Test",
                Description = "Some test survey",
                Questions = new Question[] { 
                        new Question() {
                            Text = "Czy lubisz koty?",
                            Answers = new Answer[] {
                                new Answer() { Text = "Tak" },
                                new Answer() { Text = "Nie" },
                                new Answer() { Text = "Nie wiem" }
                            }
                        },
                        new Question() {
                            Text = "Na kogo zagłosujesz w II turze wyborów?",
                            Answers = new Answer[] {
                                new Answer() { Text = "Bronek" },
                                new Answer() { Text = "Duda" },
                                new Answer() { Text = "Sarna z krzesłem na głowie" }
                            }
                        },
                        new Question() {
                            Text = "Czy kibucujesz Barcelonie?",
                            Answers = new Answer[] {
                                new Answer() { Text = "Tak" },
                                new Answer() { Text = "Yes" },
                                new Answer() { Text = "Oui" },
                                new Answer() { Text = "Ja" }
                            }
                        }
                    }
            };

            context.Surveys.Add(mock);
        }
    }
}
