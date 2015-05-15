using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.Context
{
    interface IDbContextFactory
    {
        DbContext GetContext();
    }
}
