﻿using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyResultsDTO
    {
        public int SurveyId { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public ICollection<SurveyResultsQuestionDTO> Questions { get; set; }

        public bool CompletedByUser { get; set; }
    }
}
