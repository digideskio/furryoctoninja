using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyResultsQuestion
    {
        public int QuestionId { get; set; }

        public string QuestionText { get; set; }

        public ICollection<SurveyResultsAnswer> Answers { get; set; }
    }
}