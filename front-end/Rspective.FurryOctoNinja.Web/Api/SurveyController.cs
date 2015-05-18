using Rspective.FurryOctoNinja.DataAccess.Services;
using Rspective.FurryOctoNinja.Web.Models;
using System;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using log4net;
using log4net.Config;
using Rspective.FurryOctoNinja.Web.Auth;
using Rspective.FurryOctoNinja.Web.Providers;
using System.Threading.Tasks;

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
        [TokenAuthorize(roles: new string[] { "User", "Admin" })]
        public HttpResponseMessage Get()
        {
            // Fill IsUserChoice
            return Request.CreateResponse(HttpStatusCode.OK, surveyService.GetSurvey());
        }

        [HttpGet, ActionName("users")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Users()
        {
            return Request.CreateResponse(HttpStatusCode.OK, SurveyProgressMock.Mock);
        }

        [HttpGet, ActionName("results")]
        [TokenAuthorize(roles: new string[] { "User", "Admin" })]
        public HttpResponseMessage Results()
        {
            return Request.CreateResponse(HttpStatusCode.OK, SurveyResultsMock.Mock);
        }

        [HttpPost, ActionName("post")]
        [TokenAuthorize(role: "User")]
        public HttpResponseMessage Save(SurveySave survey)
        {
            if (survey == null || survey.Answers == null || survey.Answers.Count == 0)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }
           
            // HttpStatusCode.ResetContent // Survey has been modified, cannot be saved
            // Return results with IsUserChoice field

            return Request.CreateResponse(HttpStatusCode.OK, "Save()");
        }

        [HttpPost, ActionName("notify")]
        [TokenAuthorize(role: "Admin")]
        public async Task<HttpResponseMessage> Notify()
        {
            await OneSignalProvider.NotifyMobileDevices();

            return Request.CreateResponse(HttpStatusCode.OK);
        }
    }
}
