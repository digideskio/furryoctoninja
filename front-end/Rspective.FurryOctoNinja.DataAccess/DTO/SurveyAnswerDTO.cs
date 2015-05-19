using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.DTO
{
    public class SurveyAnswerDTO
    {
        public int UserId { get; set; }

        public int SurveyId { get; set; }

        public int QuestionId { get; set; }

        public int AnswerId { get; set;  }
    }
}
