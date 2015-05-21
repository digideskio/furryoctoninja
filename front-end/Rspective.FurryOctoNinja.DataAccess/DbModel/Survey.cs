using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class Survey
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(200)]
        public string Title { get; set; }

        [MaxLength(1000)]
        public string Description { get; set; }

        [DataType(DataType.DateTime)]
        public DateTime CreatedDate { get; set; }

        public virtual ICollection<Question> Questions { get; set; }
    }
}
