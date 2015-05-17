using System;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class AuthDTO
    {
        public string Token { get; set; }

        public DateTime Expiration { get; set; }

        public ICollection<string> Roles { get; set; }
    }
}
