using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyProgressItem
    {
        public int UserId { get; set; }

        public string UserName { get; set; }

        public bool Completed { get; set; }
    }
}
