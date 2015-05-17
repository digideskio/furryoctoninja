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
        AuthDTO Login(string clientId, string login, string password);

        AuthDTO Refresh(string clientId, string token);

        AuthUserDTO Authorize(string clientId, string token, string[] roles);
    }
}
