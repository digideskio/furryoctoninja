using Rspective.FurryOctoNinja.Web.Models;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class SurveyController : System.Web.Http.ApiController
    {
        [HttpGet, ActionName("get")]
        public HttpResponseMessage Get()
        {
            return Request.CreateResponse(HttpStatusCode.OK, SurveyMock.Mock);
        }

        [HttpGet, ActionName("users")]
        public HttpResponseMessage Users()
        {
            return Request.CreateResponse(HttpStatusCode.OK, SurveyProgressMock.Mock);
        }

        [HttpGet, ActionName("results")]
        public HttpResponseMessage Results()
        {
            return Request.CreateResponse(HttpStatusCode.OK, "Results()");
        }

        [HttpPost, ActionName("post")]
        public HttpResponseMessage Save()
        {
            return Request.CreateResponse(HttpStatusCode.OK, "Save()");
        }
    }
}
