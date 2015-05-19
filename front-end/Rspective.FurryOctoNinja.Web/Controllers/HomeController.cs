using System.Web.Mvc;

namespace Rspective.FurryOctoNinja.Web.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Login() 
        {
            return View();
        }
    }
}
