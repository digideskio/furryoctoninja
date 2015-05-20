using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class ValidateUserDTO
    {
        public IList<string> OverallErrors { get; set; }

        public UserDTO ValidatedUser { get; set; }
    }
}
