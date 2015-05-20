using Rspective.FurryOctoNinja.DataAccess.Services;
using System.Web;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Auth
{
    class TokenAuthorizeAttribute : AuthorizeAttribute
    {
        private IAuthService authService;
        private string[] roles;
        private const string ClientKey = "AuthorizationClient";
        private const string TokenKey = "AuthorizationToken";

        public TokenAuthorizeAttribute()
        {
            this.authService = GlobalConfiguration.Configuration.DependencyResolver.GetService(typeof(IAuthService)) as IAuthService;
        }

        public TokenAuthorizeAttribute(string[] roles)
            : this()
        {
            this.roles = roles;
        }

        public TokenAuthorizeAttribute(string role)
            : this(new string[] { role })
        { 
        }

        public override void OnAuthorization(System.Web.Http.Controllers.HttpActionContext actionContext)
        {
            var authorization = actionContext.Request.Headers.Authorization;

            if (authorization != null && authorization.Scheme.ToLowerInvariant() == "token")
            {
                var parameters = authorization.Parameter.Split(new char[] { ':' });

                if (parameters.Length == 2)
                {
                    var clientId = parameters[0];
                    var token = parameters[1];
                    var auth = this.authService.Authenticate(clientId, token);

                    actionContext.Request.Properties.Add(ClientKey, clientId);
                    actionContext.Request.Properties.Add(TokenKey, token);

                    if (auth != null) 
                    {
                        HttpContext.Current.User = new Auth.AuthenticatedUser()
                        {
                            Id = auth.User.Id,
                            ClientId = clientId,
                            Token = token,
                            Name = auth.User.Name,
                            Login = auth.User.Login,
                            Roles = auth.User.Roles
                        };
                    }
                }
            } 

            base.OnAuthorization(actionContext);
        }

        protected override bool IsAuthorized(System.Web.Http.Controllers.HttpActionContext actionContext)
        {
            var user = HttpContext.Current.User as AuthenticatedUser;
            return user != null && user.IsAuthorized(this.roles);
        }
    }
}