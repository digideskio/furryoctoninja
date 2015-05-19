using System;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveySave
    {
        public int Id { get; set; }

        public DateTime Modified { get; set; }

        public ICollection<SurveySaveAnswer> Answers { get; set; }
    }
}