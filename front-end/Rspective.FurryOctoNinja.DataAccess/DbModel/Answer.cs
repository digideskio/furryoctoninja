using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class Answer
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(200)]
        public string Text { get; set; }
    }
}
