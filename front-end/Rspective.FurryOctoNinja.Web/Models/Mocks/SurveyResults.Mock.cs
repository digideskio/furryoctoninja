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
                            QuestionId = 1,
                            QuestionText = "Czy lubisz koty?",
                            Answers = new SurveyResultsAnswer[] {
                                new SurveyResultsAnswer() { AnswerId = 1, AnswerText = "Tak", Count = 5 },
                                new SurveyResultsAnswer() { AnswerId = 1, AnswerText = "Nie", Count = 10 },
                                new SurveyResultsAnswer() { AnswerId = 1, AnswerText = "Nie wiem", Count = 3 }
                            }
                        },
                        new SurveyResultsQuestion() {
                            QuestionId = 2,
                            QuestionText = "Na kogo zagłosujesz w II turze wyborów?",
                            Answers = new SurveyResultsAnswer[] {
                                new SurveyResultsAnswer() { AnswerId = 1, AnswerText = "Bronek", Count = 2 },
                                new SurveyResultsAnswer() { AnswerId = 2, AnswerText = "Duda", Count = 2 },
                                new SurveyResultsAnswer() { AnswerId = 3, AnswerText = "Sarna z krzesłem na głowie", Count = 14 }
                            }
                        },
                        new SurveyResultsQuestion() {
                            QuestionId = 1,
                            QuestionText = "Czy kibucujesz Barcelonie?",
                            Answers = new SurveyResultsAnswer[] {
                                new SurveyResultsAnswer() { AnswerId = 1, AnswerText = "Tak", Count = 1 },
                                new SurveyResultsAnswer() { AnswerId = 2, AnswerText = "Yes", Count = 17 },
                                new SurveyResultsAnswer() { AnswerId = 3, AnswerText = "Oui", Count = 0 }
                            }
                        }
                    }
                };
            }
        }
    }
}