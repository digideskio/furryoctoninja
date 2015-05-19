using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyProgressDTO
    {
        public int Id { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public ICollection<SurveyProgressItemDTO> Items { get; set; }
    }
}