using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyResultsQuestion
    {
        public int Id { get; set; }

        public string Text { get; set; }

        public ICollection<SurveyResultsAnswer> Answers { get; set; }
    }
}