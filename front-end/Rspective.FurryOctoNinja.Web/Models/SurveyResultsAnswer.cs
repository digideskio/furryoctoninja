﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyResultsAnswer
    {
        public int AnswerId { get; set; }

        public string AnswerText { get; set; }

        public int Count { get; set; }
    }
}