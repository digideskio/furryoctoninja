using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class Answer
    {
        [Key]
        public int Id { get; set; }

        [Key, ForeignKey("Question")]
        public int QuestionId { get; set; }

        public virtual Question Question { get; set; }

        [MaxLength(200)]
        public string Text { get; set; }
    }
}
