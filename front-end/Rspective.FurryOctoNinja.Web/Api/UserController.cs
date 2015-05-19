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
    public class UserController : ApiController
    {
        private IUserService userService;

        public UserController(IUserService userService)
        {
            this.userService = userService;
        }

        [HttpGet, ActionName("current")]
        [TokenAuthorize]
        public HttpResponseMessage Current()
        {
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<User>(HttpContext.Current.User as AuthenticatedUser));
        }

        [HttpGet, ActionName("all")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage All()
        {
            return Request.CreateResponse(HttpStatusCode.OK, this.userService.GetAll());
        }
    }
}