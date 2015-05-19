﻿using Rspective.FurryOctoNinja.DataAccess.Services;
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
using System.Web;
using AutoMapper;

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
            // TODO: REMOVE MOCK
            return Request.CreateResponse(HttpStatusCode.OK, SurveyProgressMock.Mock);
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<SurveyProgress>(progress));
        }

        [HttpGet, ActionName("results")]
        [TokenAuthorize(roles: new string[] { "User", "Admin" })]
        public HttpResponseMessage Results()
        {
            var results = this.surveyService.GetResults((HttpContext.Current.User as Auth.User).Id);
            // TODO: REMOVE MOCK
            return Request.CreateResponse(HttpStatusCode.OK, SurveyResultsMock.Mock);
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
