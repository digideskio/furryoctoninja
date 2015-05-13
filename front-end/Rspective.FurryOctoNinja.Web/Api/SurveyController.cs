using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Net;
using System.Net.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class SurveyController : System.Web.Http.ApiController
    {
        [HttpGet]
        public HttpResponseMessage Get()
        {
            return Request.CreateResponse(HttpStatusCode.OK, "Get()");
        }

        [HttpGet]
        [ActionName("users")]
        public HttpResponseMessage Users()
        {
            return Request.CreateResponse(HttpStatusCode.OK, "Users()");
        }

        [HttpGet]
        [ActionName("results")]
        public HttpResponseMessage Results()
        {
            return Request.CreateResponse(HttpStatusCode.OK, "Results()");
        }

        [HttpPost]
        public HttpResponseMessage Post()
        {
            return Request.CreateResponse(HttpStatusCode.OK, "Save()");
        }
    }
}
