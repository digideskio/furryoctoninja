using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    interface IApplicationTokenRepository : IRepository<ApplicationToken>
    {
        ApplicationToken Validate(string clientId, string token);

        void Invalidate(ApplicationClient client, ApplicationUser user, DateTime? expiration);
    }
}
