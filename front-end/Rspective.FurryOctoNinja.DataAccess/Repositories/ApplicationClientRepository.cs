using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class ApplicationClientRepository : Repository<ApplicationClient>, IApplicationClientRepository
    {
        public ApplicationClientRepository(IUnitOfWork ouw)
            : base(ouw)
        {
        }
        
        public ApplicationClient GetByPublicKey(string publicKey)
        {
            return this.DbSet.FirstOrDefault(client => client.PublicKey == publicKey);
        }
    }
}
