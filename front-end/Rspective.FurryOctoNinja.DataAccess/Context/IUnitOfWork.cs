using System.Data.Entity;

namespace Rspective.FurryOctoNinja.DataAccess.Context
{
    interface IUnitOfWork
    {
        void SaveChanges();
        DbContext Context { get; }
    }
}
