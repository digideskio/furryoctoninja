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

            var loginValidation = this.authService.Login(login.ClientId, login.Login, login.Password);
            
            if (loginValidation.OverallErrors != null && loginValidation.OverallErrors.Count > 0)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest, loginValidation);
            }

            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<AuthenticationDetails>(loginValidation.Auth));
        }
        
        [HttpPost, ActionName("refresh")]
        [TokenAuthorize]
        public HttpResponseMessage Refresh()
        {
            var user = HttpContext.Current.User as Auth.AuthenticatedUser;
            var auth = this.authService.Refresh(user.ClientId, user.Token);
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<AuthenticationDetails>(auth));
        }
    }
}