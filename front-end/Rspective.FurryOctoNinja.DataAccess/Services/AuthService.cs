using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    class AuthService : IAuthService
    {
        private IUnitOfWork ouw;
        private IApplicationClientRepository clientRepository;
        private IApplicationUserRepository userRepositiory;

        public AuthService(IUnitOfWork ouw, IApplicationClientRepository clientRepository, IApplicationUserRepository userRepositiory)
        {
            this.ouw = ouw;
            this.clientRepository = clientRepository;
            this.userRepositiory = userRepositiory;
        }

        public AuthDTO Login(string login, string password, string clientId)
        {
            var user = this.userRepositiory.Authenticate(login, password);
            var client = this.clientRepository.GetByPublicKey(clientId);

            if (user == null || client == null) 
            { 
                return null; 
            }

            var expiration = DateTime.UtcNow.AddMinutes(30);
            var payload = new Dictionary<string, object>()
            {
                { "Expiration", expiration },
                { "Client", client.Name },
                { "UserId", user.Id }
            };

            string token = JWT.JsonWebToken.Encode(payload, client.SecretKey, JWT.JwtHashAlgorithm.HS256);

            return new AuthDTO() {
                Expiration = expiration,
                Token = token
            };
        }

        public AuthDTO Refresh(string token, string clientId)
        {
            return null;
        }

        public bool IsAuthorized(string token, string requestedRole)
        {
            return true;
        }
    }
}
