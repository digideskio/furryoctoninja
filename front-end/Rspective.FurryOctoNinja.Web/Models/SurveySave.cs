using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveySave
    {
        public int Id { get; set; }

        public DateTime Modified { get; set; }

        public ICollection<SurveySaveAnswer> Answers { get; set; }
    }
}