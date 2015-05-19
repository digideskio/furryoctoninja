using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.DataAccess.Services;
using Rspective.FurryOctoNinja.Web.Auth;
using Rspective.FurryOctoNinja.Web.Models;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class UserController : ApiController
    {
        private IUserService userService;

        public UserController(IUserService userService)
        {
            this.userService = userService;
        }

        [HttpGet, ActionName("current")]
        [TokenAuthorize]
        public HttpResponseMessage Current()
        {
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<User>(HttpContext.Current.User as AuthenticatedUser));
        }

        [HttpGet, ActionName("all")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage All()
        {
            return Request.CreateResponse(HttpStatusCode.OK, this.userService.GetAll());
        }

        [HttpDelete, ActionName("delete")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Delete(int userId)
        {
            this.userService.Delete(userId);
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [HttpPut, ActionName("put")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Update(UserUpdate user)
        {
            var updatedUser = this.userService.Update(Mapper.Map<UserUpdateDTO>(user));
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<Models.User>(updatedUser));
        }

        [HttpPost, ActionName("post")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Create(UserSave user)
        {
            var createdUser = this.userService.Save(Mapper.Map<UserSaveDTO>(user));
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<Models.User>(createdUser));
        }

        [HttpGet, ActionName("get")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Get(int userId)
        {
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<Models.User>(this.userService.Get(userId)));
        }
    }
}