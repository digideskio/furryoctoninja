using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class ValidateLoginDTO
    {
        public ICollection<string> OverallErrors { get; set; }

        public AuthDTO Auth { get; set; }
    }
}
