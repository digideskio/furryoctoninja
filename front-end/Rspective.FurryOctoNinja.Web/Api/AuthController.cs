using Rspective.FurryOctoNinja.DataAccess.Services;
using Rspective.FurryOctoNinja.Web.Models;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class AuthController : ApiController
    {
        private IAuthService authService;

        public AuthController(IAuthService authService)
        {
            this.authService = authService;
        }

        [HttpPost, ActionName("login")]
        public HttpResponseMessage Login(AuthLogin login)
        {
            if (login == null) 
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            var auth = this.authService.Login(login.Login, login.Password, login.ClientId);

            if (auth == null)
            {
                return Request.CreateResponse(HttpStatusCode.Unauthorized);
            }

            return Request.CreateResponse(
                HttpStatusCode.OK, 
                new AuthenticationDetails() {
                    Token = auth.Token,
                    Expiration = auth.Expiration,
                    Roles = new string[] { "Admin", "User" }
                });
        }
        /*
        [HttpPost, ActionName("refresh")]
        public HttpResponseMessage Refresh()
        {
            return Request.CreateResponse(
                HttpStatusCode.OK,
                new AuthenticationDetails()
                {
                    Token = auth.Token,
                    Expiration = auth.Expiration,
                    Roles = new string[] { "Admin", "User" }
                });
        }*/
    }
}