using System.Collections.Generic;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class ValidateSurveyDTO
    {
        public ValidateSurveyDTO()
        {
            this.OverallErrors = new List<string>();
            this.QuestionsErrors = new List<string>();
            this.AnswersErrors = new List<string>();
        }

        public IList<string> OverallErrors { get; set; }

        public IList<string> QuestionsErrors { get; set; }

        public IList<string> AnswersErrors { get; set; }

        public bool IsValid
        {
            get
            {
                return
                    !(this.OverallErrors ?? new List<string>()).Any(x => !string.IsNullOrEmpty(x)) &&
                    !(this.QuestionsErrors ?? new List<string>()).Any(x => !string.IsNullOrEmpty(x)) &&
                    !(this.AnswersErrors ?? new List<string>()).Any(x => !string.IsNullOrEmpty(x));
            }
        }

        public SurveyDTO ValidatedSurvey { get; set; }
    }
}
