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
        public HttpResponseMessage Remove(int id)
        {
            var currentUser = HttpContext.Current.User as AuthenticatedUser;
            if (currentUser.Id == id)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            this.userService.Delete(id);
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [HttpPut, ActionName("put")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Update(UserUpdate user)
        {
            var updatedUser = this.userService.Update(Mapper.Map<UserUpdateDTO>(user));
            if (updatedUser.OverallErrors != null && updatedUser.OverallErrors.Count > 0)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest, updatedUser);
            }

            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<Models.User>(updatedUser.ValidatedUser));
        }

        [HttpPost, ActionName("post")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Create(UserSave user)
        {
            var createdUser = this.userService.Save(Mapper.Map<UserSaveDTO>(user));
            if (createdUser.OverallErrors != null && createdUser.OverallErrors.Count > 0)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest, createdUser);
            }

            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<Models.User>(createdUser.ValidatedUser));
        }

        [HttpGet, ActionName("get")]
        [TokenAuthorize(role: "Admin")]
        public HttpResponseMessage Get(int id)
        {
            return Request.CreateResponse(HttpStatusCode.OK, Mapper.Map<Models.User>(this.userService.Get(id)));
        }
    }
}