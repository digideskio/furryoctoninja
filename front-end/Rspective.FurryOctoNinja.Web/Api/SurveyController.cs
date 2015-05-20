using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Services;
using Rspective.FurryOctoNinja.Web.Auth;
using Rspective.FurryOctoNinja.Web.Models;
using Rspective.FurryOctoNinja.Web.Providers;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class SurveyController : BaseController
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
            var userId = base.AuthenticatedUser.Id;
            var survey = this.surveyService.GetSurvey(userId);
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<Survey>(survey));
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
            var results = this.surveyService.GetResults(base.AuthenticatedUser.Id); ;
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<SurveyResults>(results));
        }

        [HttpPost, ActionName("post")]
        [TokenAuthorize(role: "User")]
        public async Task<HttpResponseMessage> Save(SurveySave survey)
        {
            if (survey == null || survey.Answers == null || survey.Answers.Count == 0)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            var userId = base.AuthenticatedUser.Id;
            var surveyDTO = Mapper.Map<SurveySaveDTO>(survey);

            if (!this.surveyService.ValidateSave(userId, surveyDTO))
            {
                return Request.CreateResponse(HttpStatusCode.ResetContent);
            }

            this.surveyService.SaveSurvey(userId, surveyDTO);

            await OneSignalProvider.NotifyMobileDevices(
                "SURVEY-RESULTS-UPDATED",
                "Survey results updated.",
                "The survey's results has been posted recently, please reaload your content.");

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [HttpPost, ActionName("notify")]
        [TokenAuthorize(role: "Admin")]
        public async Task<HttpResponseMessage> Notify()
        {
            await OneSignalProvider.NotifyMobileDevices(
                "SURVEY-CHANGED",
                "Survey updated.",
                "The survey has been updated recently, please reaload your content.");

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [HttpPost, ActionName("reset")]
        [TokenAuthorize(role: "Admin")]
        public async Task<HttpResponseMessage> Reset()
        {
            this.surveyService.Reset();

            await OneSignalProvider.NotifyMobileDevices(
                "SURVEY-RESTART",
                "Survey reseted.",
                "The survey has been reseted recently, please reaload your content.");

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [HttpPost, ActionName("validate")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Validate(SurveyDTO survey)
        {
            if (surveyService == null)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            return Request.CreateResponse(HttpStatusCode.OK, this.surveyService.Validate(survey));
        }

        [HttpPost, ActionName("update")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage UpdateSurvey(SurveyDTO survey)
        {
            if (survey == null)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            this.surveyService.Save(survey);
            return Request.CreateResponse(HttpStatusCode.OK, this.surveyService.GetSurvey(base.AuthenticatedUser.Id));
        }
    }
}
