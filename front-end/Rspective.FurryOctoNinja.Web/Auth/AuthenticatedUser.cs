﻿using System.Collections.Generic;
using System.Linq;
using System.Security.Principal;

namespace Rspective.FurryOctoNinja.Web.Auth
{
    public class AuthenticatedUser : IPrincipal, IIdentity
    {
        public string AuthenticationType
        {
            get { return "Token"; }
        }

        public bool IsAuthenticated
        {
            get { return true; }
        }

        public int Id { get; set; }

        public string Name { get; set; }

        public string Login { get; set; }

        public string ClientId { get; set; }

        public string Token { get; set; }

        public ICollection<string> Roles { get; set; }

        public IIdentity Identity
        {
            get { return this; }
        }

        public bool IsInRole(string role)
        {
            return this.Roles != null && this.Roles.Contains(role);
        }

        internal bool IsAuthorized(string[] roles)
        {
            if (roles == null || roles.Length == 0)
            {
                return true;
            }

            return roles.Any(role => IsInRole(role));
        }
    }
}