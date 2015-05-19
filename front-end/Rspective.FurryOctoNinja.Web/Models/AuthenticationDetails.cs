using System;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class AuthenticationDetails
    {
        public string Token { get; set; }

        public DateTime Expiration { get; set; }

        public ICollection<string> Roles { get; set; }
    }
}