using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyResults
    {
        public ICollection<SurveyResultsQuestion> Questions { get; set; }
    }
}