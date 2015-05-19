using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class UserAnswer
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public int UserId { get; set; }

        public virtual ApplicationUser User { get; set; }

        [Required]
        public int SurveyId { get; set; }

        public virtual Survey Survey { get; set; }

        [Required]
        public int QuestionId { get; set; }

        public virtual Question Question { get; set; }

        [Required]
        public int AnswerId { get; set; }

        public virtual Answer Answer { get; set; }
    }
}
