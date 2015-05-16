using System;
using System.ComponentModel.DataAnnotations;

namespace Rspective.FurryOctoNinja.DataAccess.DbModel
{
    class ApplicationToken
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public ApplicationClient Client { get; set; }

        [Required]
        public ApplicationUser User { get; set; }

        [Required]
        public string Token { get; set; }
        
        [Required]
        public DateTime? Expiration { get; set; }
    }
}
