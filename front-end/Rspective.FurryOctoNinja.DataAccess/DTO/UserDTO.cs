﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class UserDTO
    {
        public string Name { get; set; }

        public ICollection<string> Roles { get; set; }
    }
}
