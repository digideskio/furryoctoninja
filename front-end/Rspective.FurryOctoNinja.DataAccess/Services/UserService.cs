using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
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
    }
}
