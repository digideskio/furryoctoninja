using System;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveySaveDTO
    {
        public int Id { get; set; }

        public DateTime Modified { get; set; }

        public ICollection<SurveySaveAnswerDTO> Answers { get; set; }
    }
}
