using Rspective.FurryOctoNinja.DataAccess.DbModel;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    interface IApplicationUserRepository : IRepository<ApplicationUser>
    {
        ApplicationUser Authenticate(string login, string password);
    }
}
