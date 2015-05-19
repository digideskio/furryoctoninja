using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyResultsDTO
    {
        public int SurveyId { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public ICollection<SurveyResultsQuestionDTO> Questions { get; set; }
    }
}
