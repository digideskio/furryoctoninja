using AutoMapper;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class ApplicationUser
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(200)]
        [Required]
        public string Login { get; set; }

        [MaxLength(200)]
        [Required]
        [IgnoreMap]
        public string Password { get; set; }

        [Required]
        public string Name { get; set; }

        [Required]
        public virtual ICollection<ApplicationUserRole> Roles { get; set; }
    }
}
