using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System.Data.Entity;

namespace Rspective.FurryOctoNinja.DataAccess
{
    class SurveyAppDbSeed : DropCreateDatabaseIfModelChanges<SurveyAppContext>
    {
        protected override void Seed(SurveyAppContext context)
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

            base.Seed(context);
        }
    }
}
