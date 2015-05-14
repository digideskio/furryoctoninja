using System;

namespace Rspective.FurryOctoNinja.Web.Models
{
    //-- Mock. TODO: remove
    public static class SurveyMock
    { 
        public static Survey Mock
        {
            get
            {
                return new Survey()
                {
                    Id = 1,
                    Title = "Test",
                    Description = "Some test survey",
                    CreatedDate = DateTime.Now,
                    Questions = new Question[] { 
                        new Question() {
                            Id = 1,
                            Text = "Czy lubisz koty?",
                            Answers = new Answer[] {
                                new Answer() { Id = 1, Text = "Tak" },
                                new Answer() { Id = 2, Text = "Nie" },
                                new Answer() { Id = 3, Text = "Nie wiem" }
                            }
                        },
                        new Question() {
                            Id = 2,
                            Text = "Na kogo zagłosujesz w II turze wyborów?",
                            Answers = new Answer[] {
                                new Answer() { Id = 4, Text = "Bronek" },
                                new Answer() { Id = 5, Text = "Duda" },
                                new Answer() { Id = 6, Text = "Sarna z krzesłem na głowie" }
                            }
                        },
                        new Question() {
                            Id = 3,
                            Text = "Czy kibucujesz Barcelonie?",
                            Answers = new Answer[] {
                                new Answer() { Id = 7, Text = "Tak" },
                                new Answer() { Id = 8, Text = "Yes" },
                                new Answer() { Id = 9, Text = "Oui" }
                            }
                        }
                    }
                };
            }
        }
    }
}