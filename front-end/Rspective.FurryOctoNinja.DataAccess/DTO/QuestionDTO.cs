using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class QuestionDTO
    {
        public int Id { get; set; }

        public string Text { get; set; }

        public IEnumerable<AnswerDTO> Answers { get; set; }
    }
}
