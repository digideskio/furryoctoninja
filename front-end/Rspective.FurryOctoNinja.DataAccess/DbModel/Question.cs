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

        public virtual ICollection<Answer> Answers { get; set; }
    }
}
