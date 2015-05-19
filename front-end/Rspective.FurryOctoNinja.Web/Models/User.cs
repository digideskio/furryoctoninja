using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class User
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public ICollection<string> Roles { get; set; }
    }
}