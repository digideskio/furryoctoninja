using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;

namespace Rspective.FurryOctoNinja.DataAccess
{
    public class AutoMapperConfig
    {
        public static void CreateMaps()
        {
            Mapper.CreateMap<Survey, SurveyDTO>();
            Mapper.CreateMap<Question, QuestionDTO>();
            Mapper.CreateMap<Answer, AnswerDTO>();
            Mapper.CreateMap<ApplicationUser, UserDTO>();
            Mapper.CreateMap<UserAnswer, SurveyAnswerDTO>();
            Mapper.CreateMap<SurveyAnswerDTO, UserAnswer>();
            Mapper.CreateMap<SurveyDTO, SurveyProgressDTO>();
            Mapper.CreateMap<SurveyDTO, SurveyResultsDTO>();
            Mapper.CreateMap<QuestionDTO, SurveyResultsQuestionDTO>();
            Mapper.CreateMap<AnswerDTO, SurveyResultsAnswerDTO>();
        }
    }
}
