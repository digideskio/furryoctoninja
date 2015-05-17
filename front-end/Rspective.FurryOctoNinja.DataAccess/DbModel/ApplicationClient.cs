using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

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

        [Required]
        public long TokenExpirationTime { get; set; }

        public bool IsActive { get; set; }
    }
}
