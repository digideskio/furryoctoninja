using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class AuthenticationDetails
    {
        public string Token { get; set; }

        public ICollection<string> Roles { get; set; }
    }
}