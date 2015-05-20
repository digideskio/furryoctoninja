using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Helpers;
using Rspective.FurryOctoNinja.DataAccess.Repositories;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    class UserService : IUserService
    {
        private IUnitOfWork ouw;
        private IApplicationUserRepository userRepository;

        public UserService(IUnitOfWork ouw, IApplicationUserRepository userRepository)
        {
            this.ouw = ouw;
            this.userRepository = userRepository;
        }

        public ICollection<UserDTO> GetAll()
        {
            return Mapper.Map<ICollection<UserDTO>>(this.userRepository.GetAll());
        }

        public UserDTO Get(int userId)
        {
            return Mapper.Map<UserDTO>(this.userRepository.Get(userId));
        }

        public ValidateUserDTO Save(UserSaveDTO user)
        {
            var result = new ValidateUserDTO() { };

            if (this.userRepository.Exists(user.Login, null))
            {
                var errors = new List<string>() { "The given login '" + user.Login + "' is already in use." };
                result.OverallErrors = errors;
                return result;
            }

            var applicationUser = Mapper.Map<ApplicationUser>(user);
            applicationUser.Password = PasswordEncryptor.Encrypt(user.Password);
            applicationUser.Roles = new ApplicationUserRole[] { new ApplicationUserRole() { Name = "User" } };

            result.ValidatedUser = Mapper.Map<UserDTO>(this.userRepository.Create(applicationUser));
            return result;
        }

        public ValidateUserDTO Update(UserUpdateDTO user)
        {
            var result = new ValidateUserDTO() { };

            if (this.userRepository.Exists(user.Login, user.Id))
            {
                var errors = new List<string>() { "The given login '" + user.Login + "' is already in use." };
                result.OverallErrors = errors;
                return result;
            }

            var applicationUser = this.userRepository.Get(user.Id);
            applicationUser.Name = user.Name;
            applicationUser.Login = user.Login;
            applicationUser.Password = PasswordEncryptor.Encrypt(user.Password);
            this.userRepository.Update(applicationUser);
            result.ValidatedUser = this.Get(user.Id);
            return result;
        }

        public void Delete(int userId)
        {
            this.userRepository.Delete(this.userRepository.Get(userId));
        }
    }
}
