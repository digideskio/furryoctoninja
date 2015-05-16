using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class AuthLogin
    {
        public string Login { get; set; }

        public string Password { get; set; }

        public string ClientId { get; set; }
    }
}