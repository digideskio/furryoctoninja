using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class Question
    {
        public int Id { get; set; }

        public string Text { get; set; }

        public IEnumerable<Answer> Answers { get; set; }
    }
}