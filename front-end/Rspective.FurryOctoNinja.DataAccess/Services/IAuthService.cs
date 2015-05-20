using Rspective.FurryOctoNinja.DataAccess.DTO;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    public interface IAuthService
    {
        ValidateLoginDTO Login(string clientId, string login, string password);

        AuthDTO Refresh(string clientId, string token);

        AuthUserDTO Authenticate(string clientId, string token);
    }
}
