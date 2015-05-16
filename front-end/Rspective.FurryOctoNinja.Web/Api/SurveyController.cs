using Rspective.FurryOctoNinja.DataAccess.Services;
using Rspective.FurryOctoNinja.Web.Models;
using System;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using log4net;
using log4net.Config;

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
            LogManager.GetLogger("LeAppender").Info("[INFO] GET");

            try {
                return Request.CreateResponse(HttpStatusCode.OK, surveyService.GetSurvey());
            } catch(Exception ex) {

                LogManager.GetLogger("LeAppender").Error("[ERROR] GET", ex);
            }

            return Request.CreateResponse(HttpStatusCode.InternalServerError);
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
