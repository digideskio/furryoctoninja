using System;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyDTO
    {
        public int Id { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public DateTime CreatedDate { get; set; }

        public IEnumerable<QuestionDTO> Questions { get; set; }

        public bool IsFinished { get; set; }
    }
}
