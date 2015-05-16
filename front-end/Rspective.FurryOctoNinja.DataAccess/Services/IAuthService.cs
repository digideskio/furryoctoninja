using Rspective.FurryOctoNinja.DataAccess.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    public interface IAuthService
    {
        AuthDTO Login(string login, string password, string clientId);

        AuthDTO Refresh(string token, string clientId);

        bool IsAuthorized(string token, string clientId, string requestedRole);
    }
}
