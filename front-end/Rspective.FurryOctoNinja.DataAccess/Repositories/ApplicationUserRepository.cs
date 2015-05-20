using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class ApplicationUserRepository : Repository<ApplicationUser>, IApplicationUserRepository
    {
        public ApplicationUserRepository(IUnitOfWork ouw)
            : base(ouw)
        {
        }

        public ApplicationUser Authenticate(string login, string password)
        {
            return this.DbSet
                .Include("Roles")
                .FirstOrDefault(user => user.Login == login && user.Password == password);
        }

        public ICollection<ApplicationUser> GetAll()
        {
            return this.DbSet
                .Include("Roles")
                .ToArray();
        }

        public ApplicationUser Get(int userId)
        {
            return this.DbSet
                .Include("Roles")
                .FirstOrDefault(user => user.Id == userId);
        }
    }
}
