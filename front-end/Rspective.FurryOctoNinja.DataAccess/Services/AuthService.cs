using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Helpers;
using Rspective.FurryOctoNinja.DataAccess.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;

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

        public AuthDTO Login(string clientId, string login, string password)
        {
            var user = this.userRepositiory.Authenticate(login, PasswordEncryptor.Encrypt(password));
            var client = this.clientRepository.GetByPublicKey(clientId);

            if (user == null || client == null) 
            { 
                return null; 
            }

            var expiration = DateTime.UtcNow.AddMinutes(client.TokenExpirationTime);
            var token = GenerateToken(client, user, expiration);

            this.tokenRepository.Invalidate(client.Id, user.Id, expiration);
            this.tokenRepository.Create(new ApplicationToken() {
                Token = token,
                Expiration = expiration,
                ClientId = client.Id,
                UserId = user.Id
            });

            return new AuthDTO() {
                Expiration = expiration,
                Token = token,
                Roles = user.Roles.Select(role => role.Name).ToArray()
            };
        }

        public AuthDTO Refresh(string clientId, string token)
        {
            var applicationToken = this.tokenRepository.Validate(clientId, token);

            if (applicationToken == null)
            {
                return null;
            }

            applicationToken.Expiration = DateTime.UtcNow.AddMinutes(applicationToken.Client.TokenExpirationTime);
            applicationToken.Token = this.GenerateToken(applicationToken.Client, applicationToken.User, applicationToken.Expiration.GetValueOrDefault());

            this.tokenRepository.Update(applicationToken);

            return new AuthDTO()
            {
                Token = applicationToken.Token,
                Expiration = applicationToken.Expiration.GetValueOrDefault(),
                Roles = applicationToken.User.Roles.Select(role => role.Name).ToArray()
            };
        }

        public AuthUserDTO Authenticate(string clientId, string token)
        {
            var applicationToken = this.tokenRepository.Validate(clientId, token);

            if (applicationToken == null) 
            {
                return null;
            }
            
            try
            {
                // TODO: Make use of passed token data
                string jsonPayload = JWT.JsonWebToken.Decode(token, applicationToken.Client.SecretKey);
           
                return new AuthUserDTO() {
                     User = Mapper.Map<UserDTO>(applicationToken.User)
                };;
            }
            catch (JWT.SignatureVerificationException)
            {
                return null;
            }
        }

        private string GenerateToken(ApplicationClient client, ApplicationUser user, DateTime expiration)
        {
            var payload = new Dictionary<string, object>()
            {
                { "Expiration", expiration },
                { "Client", client.Name },
                { "UserId", user.Id },
                { "Roles", user.Roles.Select(role => role.Name).ToArray() }
            };

            return JWT.JsonWebToken.Encode(payload, client.SecretKey, JWT.JwtHashAlgorithm.HS256);
        }
    }
}
