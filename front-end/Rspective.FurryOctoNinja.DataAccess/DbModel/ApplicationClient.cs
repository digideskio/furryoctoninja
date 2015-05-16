using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class ApplicationClient
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(50)]
        [Required]
        public string Name { get; set; }

        [MaxLength(200)]
        [Required]
        public string PublicKey { get; set; }

        [MaxLength(200)]
        [Required]
        public string SecretKey { get; set; }

        [DefaultValue(true)]
        public bool IsActive { get; set; }
    }
}
