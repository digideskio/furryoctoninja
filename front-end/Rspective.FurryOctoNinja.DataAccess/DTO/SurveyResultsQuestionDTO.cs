using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyResultsQuestionDTO
    {
        public int Id { get; set; }

        public string Text { get; set; }

        public ICollection<SurveyResultsAnswerDTO> Answers { get; set; }
    }
}
