using System;
using System.Data.Entity;

namespace Rspective.FurryOctoNinja.DataAccess.Context
{
    class UnitOfWork : IUnitOfWork, IDisposable
    {
        private readonly DbContext context;
        private bool disposed = false;

        public UnitOfWork(IDbContextFactory contextFactory)
        {
            context = contextFactory.GetContext();
        }

        public void SaveChanges()
        {
            if (context != null)
            {
                context.SaveChanges();
            }
        }

        public DbContext Context
        {
            get { return context; }
        }

        protected virtual void Dispose(bool disposing)
        {
            if (!disposed)
            {
                if (disposing)
                {
                    context.Dispose();
                }
            }

            disposed = true;
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }
    }
}
