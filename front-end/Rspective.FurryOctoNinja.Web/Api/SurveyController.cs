using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Services;
using Rspective.FurryOctoNinja.Web.Auth;
using Rspective.FurryOctoNinja.Web.Models;
using Rspective.FurryOctoNinja.Web.Providers;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
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
        [TokenAuthorize(roles: new string[] { "User", "Admin" })]
        public HttpResponseMessage Get()
        {
            var survey = this.surveyService.GetSurvey();
            // TODO: Merge User and his answers
            return Request.CreateResponse(HttpStatusCode.OK, survey);
        }

        [HttpGet, ActionName("users")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Progress()
        {
            var progress = this.surveyService.GetProgress();
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<SurveyProgress>(progress));
        }

        [HttpGet, ActionName("results")]
        [TokenAuthorize(roles: new string[] { "User", "Admin" })]
        public HttpResponseMessage Results()
        {
            var results = this.surveyService.GetResults((HttpContext.Current.User as Auth.AuthenticatedUser).Id);;
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<SurveyResults>(results));
        }

        [HttpPost, ActionName("post")]
        [TokenAuthorize(role: "User")]
        public HttpResponseMessage Save(SurveySave survey)
        {
            if (survey == null || survey.Answers == null || survey.Answers.Count == 0)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }
           
            // TODO: HttpStatusCode.ResetContent // Survey has been modified, cannot be saved
            // Return results with IsUserChoice field

            var userId = (HttpContext.Current.User as Auth.AuthenticatedUser).Id;
            this.surveyService.SaveSurvey(userId, Mapper.Map<SurveySaveDTO>(survey));

            return Request.CreateResponse(HttpStatusCode.OK, "Save()");
        }

        [HttpPost, ActionName("notify")]
        [TokenAuthorize(role: "Admin")]
        public async Task<HttpResponseMessage> Notify()
        {
            await OneSignalProvider.NotifyMobileDevices();

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [HttpPost, ActionName("reset")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Reset() {
            this.surveyService.Reset();
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [HttpPost, ActionName("validate")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Validate(SurveyDTO survey)
        {
            if (surveyService == null)
            {
                Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            return Request.CreateResponse(HttpStatusCode.OK, this.surveyService.Validate(survey));
        }
    }
}
