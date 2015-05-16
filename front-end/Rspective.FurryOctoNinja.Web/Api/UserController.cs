using Rspective.FurryOctoNinja.Web.Models;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class UserController : ApiController
    {
        [HttpPost, ActionName("login")]
        public HttpResponseMessage Create()
        {
            return Request.CreateResponse(
                HttpStatusCode.OK, 
                new AuthenticationDetails() {
                    Token = "bd14ec06f15041839d77e5282f75f239",
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