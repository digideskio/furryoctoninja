using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    interface IApplicationTokenRepository : IRepository<ApplicationToken>
    {
        ApplicationToken Validate(string clientId, string token);

        void Invalidate(int clientId, int userId, DateTime? expiration);
    }
}
