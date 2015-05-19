using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveySaveDTO
    {
        public int Id { get; set; }

        public DateTime Modified { get; set; }

        public ICollection<SurveySaveAnswerDTO> Answers { get; set; }
    }
}
