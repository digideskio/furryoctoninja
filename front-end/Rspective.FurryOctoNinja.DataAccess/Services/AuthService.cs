using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
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
        private IApplicationTokenRepository tokenRepository;

        public AuthService(IUnitOfWork ouw, 
            IApplicationClientRepository clientRepository, 
            IApplicationUserRepository userRepositiory, 
            IApplicationTokenRepository tokenRepository)
        {
            this.ouw = ouw;
            this.clientRepository = clientRepository;
            this.userRepositiory = userRepositiory;
            this.tokenRepository = tokenRepository;
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
            var token = GenerateToken(client, user, expiration);

            this.tokenRepository.Invalidate(client, user, expiration);
            this.tokenRepository.Create(new ApplicationToken() {
                Token = token,
                Expiration = expiration,
                ClientId = client.Id,
                UserId = user.Id
            });

            this.ouw.SaveChanges();

            return new AuthDTO() {
                Expiration = expiration,
                Token = token
            };
        }

        public AuthDTO Refresh(string token, string clientId)
        {
            var applicationToken = this.tokenRepository.Validate(token, clientId);

            if (applicationToken == null)
            {
                return null;
            }

            applicationToken.Expiration = DateTime.UtcNow;
            applicationToken.Token = this.GenerateToken(applicationToken.Client, applicationToken.User, applicationToken.Expiration.GetValueOrDefault());

            this.tokenRepository.Update(applicationToken);
            this.ouw.SaveChanges();

            return new AuthDTO()
            {
                Token = applicationToken.Token,
                Expiration = applicationToken.Expiration.GetValueOrDefault()
            };
        }

        public bool IsAuthorized(string token, string clientId, string requestedRole)
        {
            var applicationToken = this.tokenRepository.Validate(token, clientId);

            if (applicationToken == null) 
            {
                return false;
            }
            
            try
            {
                // TODO: Make use of passed token data
                string jsonPayload = JWT.JsonWebToken.Decode(token, applicationToken.Client.SecretKey);
           
                // TODO: Check user role
                return !string.IsNullOrWhiteSpace(requestedRole) ? true : true;
            }
            catch (JWT.SignatureVerificationException)
            {
                return false;
            }
        }

        private string GenerateToken(ApplicationClient client, ApplicationUser user, DateTime expiration)
        {
            var payload = new Dictionary<string, object>()
            {
                { "Expiration", expiration },
                { "Client", client.Name },
                { "UserId", user.Id }
            };

            return JWT.JsonWebToken.Encode(payload, client.SecretKey, JWT.JwtHashAlgorithm.HS256);
        }
    }
}
