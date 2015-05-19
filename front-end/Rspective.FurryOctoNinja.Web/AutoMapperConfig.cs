using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.Web.Models;

namespace Rspective.FurryOctoNinja.Web
{
    public class AutoMapperConfig
    {
        public static void CreateMaps()
        {
            Mapper.CreateMap<AuthDTO, AuthenticationDetails>();
            Mapper.CreateMap<SurveyProgressDTO, SurveyProgress>();
            Mapper.CreateMap<SurveyProgressItemDTO, SurveyProgressItem>();
        }
    }
}