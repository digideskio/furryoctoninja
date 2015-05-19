using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyProgress
    {
        public string Title { get; set; }

        public string Description { get; set; }

        public ICollection<SurveyProgressItem> Items { get; set; }
    }
}