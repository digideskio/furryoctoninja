using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    interface IApplicationUserRepository : IRepository<ApplicationUser>
    {
        ApplicationUser Authenticate(string login, string password);

        ICollection<ApplicationUser> GetAll();

        ICollection<ApplicationUser> GetAllUsers();

        ApplicationUser Get(int userId);
    }
}
