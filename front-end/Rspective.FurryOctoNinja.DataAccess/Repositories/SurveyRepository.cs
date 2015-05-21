using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using System;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class SurveyRepository : Repository<Survey>, ISurveyRepository
    {
        public SurveyRepository(IUnitOfWork ouw)
            : base(ouw)
        {
        }

        public Survey GetSurvey()
        {
            return this.DbSet
                .Include("Questions")
                .Include("Questions.Answers")
                .FirstOrDefault();
        }

        public void UpdateSurvey(SurveyDTO dto)
        {
            var survey = this.GetSurvey();
            if (survey == null)
            {
                throw new InvalidOperationException();
            }

            this.Delete(survey);
            this.Create(Mapper.Map<Survey>(dto));
        }
    }
}
