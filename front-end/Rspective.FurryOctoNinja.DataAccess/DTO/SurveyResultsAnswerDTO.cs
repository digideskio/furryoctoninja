
namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyResultsAnswerDTO
    {
        public int Id { get; set; }

        public string Text { get; set; }

        public int Count { get; set; }

        public bool? IsUserChoice { get; set; }
    }
}
