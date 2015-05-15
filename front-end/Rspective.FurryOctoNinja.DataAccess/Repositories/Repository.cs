using Rspective.FurryOctoNinja.DataAccess.Context;
using System;
using System.Data.Entity;
using System.Linq;
using System.Linq.Expressions;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class Repository<TObject> : IRepository<TObject> where TObject : class
    {
        protected DbContext Context;

        public Repository(IUnitOfWork uow)
        {
            Context = uow.Context;
        }

        protected DbSet<TObject> DbSet
        {
            get
            {
                return Context.Set<TObject>();
            }
        }

        public void Dispose()
        {
            if (Context != null)
            {
                Context.Dispose();
            }
        }

        public virtual IQueryable<TObject> All()
        {
            return DbSet.AsQueryable();
        }

        public virtual IQueryable<TObject> Filter(Expression<Func<TObject, bool>> predicate)
        {
            return DbSet.Where(predicate).AsQueryable<TObject>();
        }

        public bool Contains(Expression<Func<TObject, bool>> predicate)
        {
            return DbSet.Count(predicate) > 0;
        }

        public virtual TObject Find(params object[] keys)
        {
            return DbSet.Find(keys);
        }

        public virtual TObject Find(Expression<Func<TObject, bool>> predicate)
        {
            return DbSet.FirstOrDefault(predicate);
        }

        public virtual TObject Create(TObject TObject)
        {
            var newEntry = DbSet.Add(TObject);
            Context.SaveChanges();
            return newEntry;
        }

        public virtual int Delete(TObject TObject)
        {
            DbSet.Remove(TObject);
            return Context.SaveChanges();
        }

        public virtual int Update(TObject TObject)
        {
            var entry = Context.Entry(TObject);
            DbSet.Attach(TObject);
            entry.State = EntityState.Modified;
            return Context.SaveChanges();
        }

        public virtual int Delete(Expression<Func<TObject, bool>> predicate)
        {
            var objects = Filter(predicate);
            foreach (var obj in objects)
            {
                DbSet.Remove(obj);
            }

            return Context.SaveChanges();
        }
    }
}
