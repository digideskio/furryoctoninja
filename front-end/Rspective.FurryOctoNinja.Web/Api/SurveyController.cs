using Rspective.FurryOctoNinja.DataAccess.Services;
using Rspective.FurryOctoNinja.Web.Models;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class SurveyController : System.Web.Http.ApiController
    {
        ISurveyService surveyService;

        public SurveyController(ISurveyService surveyService)
        {
            this.surveyService = surveyService;
        }

        [HttpGet, ActionName("get")]
        public HttpResponseMessage Get()
        {
            return Request.CreateResponse(HttpStatusCode.OK, surveyService.GetSurvey());
        }

        [HttpGet, ActionName("users")]
        public HttpResponseMessage Users()
        {
            return Request.CreateResponse(HttpStatusCode.OK, SurveyProgressMock.Mock);
        }

        [HttpGet, ActionName("results")]
        public HttpResponseMessage Results()
        {
            return Request.CreateResponse(HttpStatusCode.OK, SurveyResultsMock.Mock);
        }

        [HttpPost, ActionName("post")]
        public HttpResponseMessage Save()
        {
            return Request.CreateResponse(HttpStatusCode.OK, "Save()");
        }
    }
}
