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

        public void Invalidate(ApplicationClient client, ApplicationUser user, DateTime? expiration)
        {
            this.Delete(applicationToken => 
                applicationToken.Client.Id == client.Id 
                && applicationToken.User.Id == user.Id 
                && (!expiration.HasValue || applicationToken.Expiration < expiration));
        }
    }
}
