using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Services;
using Rspective.FurryOctoNinja.Web.Auth;
using Rspective.FurryOctoNinja.Web.Models;
using System.Net;
using System.Net.Http;
using System.Web;
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

            var auth = this.authService.Login(login.ClientId, login.Login, login.Password);

            if (auth == null)
            {
                return Request.CreateResponse(HttpStatusCode.Unauthorized);
            }

            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<AuthenticationDetails>(auth));
        }
        
        [HttpPost, ActionName("refresh")]
        [TokenAuthorize]
        public HttpResponseMessage Refresh()
        {
            var user = HttpContext.Current.User as Auth.User;
            var auth = this.authService.Refresh(user.ClientId, user.Token);
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<AuthenticationDetails>(auth));
        }
    }
}