using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System;
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
    }
}
