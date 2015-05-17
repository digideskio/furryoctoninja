using log4net;
using log4net.Config;
using Rspective.FurryOctoNinja.DataAccess;
using Rspective.FurryOctoNinja.Web.Helpers;
using System;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Routing;

namespace Rspective.FurryOctoNinja.Web
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801
    public class MvcApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();

            WebApiConfig.Register(GlobalConfiguration.Configuration);
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);

            DataAccess.AutoMapperConfig.CreateMaps();
            Web.AutoMapperConfig.CreateMaps();

            var container = UnityConfig.RegisterTypes();
            GlobalConfiguration.Configuration.DependencyResolver = new UnityResolver(container);

            XmlConfigurator.Configure();
        }

        void Application_Error(object sender, EventArgs e)
        {
            // Code that runs when an unhandled error occurs

            // Get the exception object.
            Exception exc = Server.GetLastError();

            // Handle HTTP errors
            if (exc.GetType() == typeof(HttpException))
            {
                // The Complete Error Handling Example generates
                // some errors using URLs with "NoCatch" in them;
                // ignore these here to simulate what would happen
                // if a global.asax handler were not implemented.
                if (exc.Message.Contains("NoCatch") || exc.Message.Contains("maxUrlLength"))
                    return;
            }

            LogManager.GetLogger("LeAppender").Error("[ERROR] Unexpected", exc);

            // Clear the error from the server
            Server.ClearError();
        }
    }
}