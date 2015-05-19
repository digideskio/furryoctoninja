
namespace Rspective.FurryOctoNinja.Web.Models
{
    public class SurveyResultsAnswer
    {
        public int Id { get; set; }

        public string Text { get; set; }

        public int Count { get; set; }

        public bool? IsUserChoice { get; set; }
    }
}