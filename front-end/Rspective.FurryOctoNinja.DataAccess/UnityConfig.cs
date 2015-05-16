using Microsoft.Practices.Unity;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.Repositories;
using Rspective.FurryOctoNinja.DataAccess.Services;

namespace Rspective.FurryOctoNinja.DataAccess
{
    public class UnityConfig
    {
        public static UnityContainer RegisterTypes()
        {
            var container = new UnityContainer();

            container.RegisterType<IDbContextFactory, DbContextFactory>();
            container.RegisterType<IUnitOfWork, UnitOfWork>();

            container.RegisterType<ISurveyRepository, SurveyRepository>();
            container.RegisterType<IApplicationClientRepository, ApplicationClientRepository>();
            container.RegisterType<IApplicationUserRepository, ApplicationUserRepository>();
            container.RegisterType<IApplicationTokenRepository, ApplicationTokenRepository>();

            container.RegisterType<ISurveyService, SurveyService>(new PerThreadLifetimeManager());
            container.RegisterType<IAuthService, AuthService>(new PerThreadLifetimeManager());

            return container;
            //DependencyResolver.SetResolver(new UnityDependencyResolver(container));
        }
    }
}
