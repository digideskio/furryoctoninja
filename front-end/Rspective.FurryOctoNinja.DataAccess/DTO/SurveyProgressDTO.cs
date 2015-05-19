
using System.Collections.Generic;
namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyProgressDTO
    {
        public int SurveyId { get; set; }

        public ICollection<SurveyProgressItemDTO> Items { get; set; }
    }
}