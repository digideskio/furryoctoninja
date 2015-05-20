using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class ValidateSurveyDTO
    {
        public IList<string> OverallErrors { get; set; }

        public IList<string> QuestionsErrors { get; set; }

        public IList<string> AnswersErrors { get; set; }

        public bool IsValid { get; set; }

        public SurveyDTO ValidatedSurvey { get; set; }
    }
}
