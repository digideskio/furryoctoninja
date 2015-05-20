using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using Rspective.FurryOctoNinja.Web.Auth;
using Rspective.FurryOctoNinja.Web.Models;

namespace Rspective.FurryOctoNinja.Web
{
    public class AutoMapperConfig
    {
        public static void CreateMaps()
        {
            Mapper.CreateMap<AuthDTO, AuthenticationDetails>();
            Mapper.CreateMap<SurveyDTO, Survey>();
            Mapper.CreateMap<QuestionDTO, Question>();
            Mapper.CreateMap<AnswerDTO, Answer>();
            Mapper.CreateMap<SurveyProgressDTO, SurveyProgress>();
            Mapper.CreateMap<SurveyProgressItemDTO, SurveyProgressItem>();
            Mapper.CreateMap<SurveyResultsDTO, SurveyResults>();
            Mapper.CreateMap<SurveyResultsAnswerDTO, SurveyResultsAnswer>();
            Mapper.CreateMap<SurveyResultsQuestionDTO, SurveyResultsQuestion>();
            Mapper.CreateMap<AuthenticatedUser, User>();
            Mapper.CreateMap<SurveySave, SurveySaveDTO>();
            Mapper.CreateMap<SurveySaveAnswer, SurveySaveAnswerDTO>();
            Mapper.CreateMap<UserSave, UserSaveDTO>();
            Mapper.CreateMap<UserUpdate, UserUpdateDTO>();
            Mapper.CreateMap<UserDTO, User>();
        }
    }
}