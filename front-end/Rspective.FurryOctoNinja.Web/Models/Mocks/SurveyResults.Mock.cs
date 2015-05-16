using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    //-- Mock. TODO: remove
    public class SurveyResultsMock
    {
        public static SurveyResults Mock
        {
            get
            {
                return new SurveyResults()
                {
                    Questions = new SurveyResultsQuestion[] { 
                        new SurveyResultsQuestion() {
                            Id = 1,
                            Text = "Czy lubisz koty?",
                            Answers = new SurveyResultsAnswer[] {
                                new SurveyResultsAnswer() { Id = 1, Text = "Tak", Count = 5 },
                                new SurveyResultsAnswer() { Id = 2, Text = "Nie", Count = 10 },
                                new SurveyResultsAnswer() { Id = 3, Text = "Nie wiem", Count = 3 }
                            }
                        },
                        new SurveyResultsQuestion() {
                            Id = 2,
                            Text = "Na kogo zagłosujesz w II turze wyborów?",
                            Answers = new SurveyResultsAnswer[] {
                                new SurveyResultsAnswer() { Id = 1, Text = "Bronek", Count = 2 },
                                new SurveyResultsAnswer() { Id = 2, Text = "Duda", Count = 2 },
                                new SurveyResultsAnswer() { Id = 3, Text = "Sarna z krzesłem na głowie", Count = 14 }
                            }
                        },
                        new SurveyResultsQuestion() {
                            Id = 3,
                            Text = "Czy kibucujesz Barcelonie?",
                            Answers = new SurveyResultsAnswer[] {
                                new SurveyResultsAnswer() { Id = 1, Text = "Tak", Count = 1 },
                                new SurveyResultsAnswer() { Id = 2, Text = "Yes", Count = 17 },
                                new SurveyResultsAnswer() { Id = 3, Text = "Oui", Count = 0 }
                            }
                        }
                    }
                };
            }
        }
    }
}