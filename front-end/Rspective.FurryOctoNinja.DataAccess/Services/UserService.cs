using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
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
        
        public int Save(UserSaveDTO user)
        {
            var applicationUser = Mapper.Map<ApplicationUser>(user);

            applicationUser.Roles = new ApplicationUserRole[] {
                new ApplicationUserRole() { Name = "User" }
            };

            return this.userRepository.Create(applicationUser).Id;
        }

        public UserDTO Update(UserUpdateDTO user)
        {
            var applicationUser = Mapper.Map<ApplicationUser>(user);

            applicationUser.Roles = new ApplicationUserRole[] {
                new ApplicationUserRole() { Name = "User" }
            };

            var userId = this.userRepository.Update(applicationUser);

            return this.Get(userId);
        }

        public void Delete(int userId)
        {
            this.userRepository.Delete(this.userRepository.Get(userId));
        }
    }
}
