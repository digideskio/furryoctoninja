using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    interface IUserAnswerRepository : IRepository<UserAnswer>
    {
        ICollection<UserAnswer> GetForSingleUser(int surveyId, int userId);

        ICollection<UserAnswer> GetForSurvey(int surveyId);

        void Reset(int surveyId);
    }
}
