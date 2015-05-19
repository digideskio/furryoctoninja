using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyProgressItemDTO
    {
        public int UserId { get; set; }

        public string UserName { get; set; }

        public bool Completed { get; set; }
    }
}
