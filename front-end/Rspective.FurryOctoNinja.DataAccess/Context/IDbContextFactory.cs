using System.Data.Entity;

namespace Rspective.FurryOctoNinja.DataAccess.Context
{
    interface IDbContextFactory
    {
        DbContext GetContext();
    }
}
