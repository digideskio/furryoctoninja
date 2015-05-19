using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class ApplicationTokenRepository : Repository<ApplicationToken>, IApplicationTokenRepository
    {
        public ApplicationTokenRepository (IUnitOfWork ouw)
            : base(ouw)
        {
        }
        
        public ApplicationToken Validate(string clientId, string token)
        {
            return this.DbSet
                .Include("Client")
                .Include("User")
                .Include("User.Roles")
                .FirstOrDefault(applicationToken => 
                    applicationToken.Client.PublicKey == clientId 
                    && applicationToken.Token == token 
                    && applicationToken.Expiration > DateTime.UtcNow);
        }

        public void Invalidate(int clientId, int userId, DateTime? expiration)
        {
            this.Delete(applicationToken =>
                applicationToken.Client.Id == clientId
                && applicationToken.User.Id == userId 
                && (!expiration.HasValue || applicationToken.Expiration < expiration));
        }
    }
}
