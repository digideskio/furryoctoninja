using Rspective.FurryOctoNinja.Web.Auth;
using System.Web;
using System.Web.Http;

namespace Rspective.FurryOctoNinja.Web.Api
{
    public class BaseController : ApiController
    {
        public AuthenticatedUser AuthenticatedUser
        {
            get
            {
                return HttpContext.Current.User as Auth.AuthenticatedUser;
            }
        }

    }
}
