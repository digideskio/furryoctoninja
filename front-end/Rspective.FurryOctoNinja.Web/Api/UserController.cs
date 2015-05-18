using Rspective.FurryOctoNinja.Web.Auth;
using Rspective.FurryOctoNinja.Web.Models;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class UserController : ApiController
    {
        [HttpGet, ActionName("current")]
        [TokenAuthorize]
        public HttpResponseMessage Current(dynamic data)
        {
            return Request.CreateResponse(HttpStatusCode.OK, HttpContext.Current.User as Models.User);
        }
    }
}