using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class Question
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(200)]
        public string Text { get; set; }

        [Key, ForeignKey("Survey")]
        public int SurveyId { get; set; }

        public virtual Survey Survey { get; set; }

        public virtual ICollection<Answer> Answers { get; set; }
    }
}
