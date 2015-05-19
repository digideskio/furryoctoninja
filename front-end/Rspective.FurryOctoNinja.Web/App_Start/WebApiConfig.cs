using System.Web.Http;
using System.Web.Routing;

namespace Rspective.FurryOctoNinja.Web
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            config.Routes.MapHttpRoute(
                name: "ControllerActionGetApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional, action = "Get"  },
                constraints: new { id = @"\d?", httpMethod = new HttpMethodConstraint(new string[] { "GET" }) }
            );

            config.Routes.MapHttpRoute(
                name: "ControllerActionPostApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional, action = "Post" },
                constraints: new { id = @"\d?", httpMethod = new HttpMethodConstraint(new string[] { "POST" }) }
            );

            config.Routes.MapHttpRoute(
                name: "ControllerActionApi",
                routeTemplate: "api/{controller}/{action}/{id}",
                defaults: new { id = RouteParameter.Optional },
                constraints: new { id = @"\d?" }
            );

        }
    }
}
