using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyProgress
    {
        public int SurveyId { get; set; }

        public ICollection<SurveyProgressItem> Items { get; set; }
    }
}