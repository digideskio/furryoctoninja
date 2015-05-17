using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class UserDTO
    {
        public string Name { get; set; }

        public ICollection<string> Roles { get; set; }
    }
}
