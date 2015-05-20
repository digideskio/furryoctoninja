using Rspective.FurryOctoNinja.DataAccess.DTO;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.Services
{
    public interface IUserService
    {
        ICollection<UserDTO> GetAll();

        UserDTO Get(int userId);

        ValidateUserDTO Save(UserSaveDTO user);

        ValidateUserDTO Update(UserUpdateDTO user);

        void Delete(int userId);
    }
}
