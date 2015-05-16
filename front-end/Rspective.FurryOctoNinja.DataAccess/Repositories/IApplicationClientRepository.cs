using Rspective.FurryOctoNinja.DataAccess.DbModel;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    interface IApplicationClientRepository : IRepository<ApplicationClient>
    {
        ApplicationClient GetByPublicKey(string publicKey);
    }
}