using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class UserAnswer
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public int UserId { get; set; }

        public virtual ApplicationUser User { get; set; }

        [ForeignKey("Survey")]
        public int SurveyId { get; set; }

        public virtual Survey Survey { get; set; }

        [ForeignKey("Question")]
        public int QuestionId { get; set; }

        public virtual Question Question { get; set; }

        [ForeignKey("Answer")]
        public int AnswerId { get; set; }

        public virtual Answer Answer { get; set; }
    }
}
