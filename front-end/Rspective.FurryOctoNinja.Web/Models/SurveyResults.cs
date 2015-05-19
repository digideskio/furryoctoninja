using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyResults
    {
        public string Title { get; set; }

        public string Description { get; set; }

        public ICollection<SurveyResultsQuestion> Questions { get; set; }
    }
}