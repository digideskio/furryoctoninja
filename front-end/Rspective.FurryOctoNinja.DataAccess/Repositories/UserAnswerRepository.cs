using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System.Collections.Generic;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class UserAnswerRepository: Repository<UserAnswer>, IUserAnswerRepository
    {
        public UserAnswerRepository(IUnitOfWork ouw)
            : base(ouw)
        {
        }

        public ICollection<UserAnswer> GetForSingleUser(int surveyId, int userId)
        {
            return this.DbSet
                .Where(answer => answer.SurveyId == surveyId && answer.UserId == userId)
                .ToArray();
        }

        public ICollection<UserAnswer> GetForSurvey(int surveyId)
        {
            return this.DbSet
                .Where(answer => answer.SurveyId == surveyId)
                .ToArray();
        }

        public void Reset(int surveyId)
        {
            this.Delete(answer => answer.SurveyId == surveyId);
        }
    }
}
