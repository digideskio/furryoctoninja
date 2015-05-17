﻿using Rspective.FurryOctoNinja.DataAccess.DTO;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    public interface IAuthService
    {
        AuthDTO Login(string clientId, string login, string password);

        AuthDTO Refresh(string clientId, string token);

        AuthUserDTO Authorize(string clientId, string token, string[] roles);
    }
}
