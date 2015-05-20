using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyResults
    {
        public int SurveyId { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public ICollection<SurveyResultsQuestion> Questions { get; set; }

        public bool CompletedByUser { get; set; }
    }
}