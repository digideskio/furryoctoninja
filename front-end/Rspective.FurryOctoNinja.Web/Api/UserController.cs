using Rspective.FurryOctoNinja.Web.Models;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class UserController : ApiController
    {
        [HttpPost, ActionName("login")]
        public HttpResponseMessage Login()
        {
            return Request.CreateResponse(
                HttpStatusCode.OK, 
                new AuthenticationDetails() {
                    Token = "GQDstcKsx0NHjPOuXOYg5MbeJ1XT0uFiwDVvVBrk",
                    Roles = new string[] { "Administrator", "User" }
                });
        }

        [HttpPost, ActionName("post")]
        public HttpResponseMessage Post(dynamic data)
        {
            return Request.CreateResponse(HttpStatusCode.OK, "Save()");
        }
    }
}