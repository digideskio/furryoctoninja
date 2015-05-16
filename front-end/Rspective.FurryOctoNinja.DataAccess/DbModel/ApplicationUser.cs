using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class ApplicationUser
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(200)]
        [Required]
        public string Login { get; set; }
    }
}
