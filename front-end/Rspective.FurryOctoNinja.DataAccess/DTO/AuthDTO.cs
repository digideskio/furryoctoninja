using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class AuthDTO
    {
        public string Token { get; set; }

        public DateTime Expiration { get; set; }

        public ICollection<string> Roles { get; set; }
    }
}
