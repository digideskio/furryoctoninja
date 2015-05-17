using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class AuthUserDTO
    {
        public bool IsAuthorized { get; set; }

        public UserDTO User { get; set; }
    }
}
