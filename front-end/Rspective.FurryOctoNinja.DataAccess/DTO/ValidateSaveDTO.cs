using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class ValidateSaveDTO
    {
        public bool AlreadyCompleted { get; set; }

        public bool ContentChanged { get; set; }
    }
}
