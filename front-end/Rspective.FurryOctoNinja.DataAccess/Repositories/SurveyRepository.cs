using AutoMapper;
using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using Rspective.FurryOctoNinja.DataAccess.DTO;
using System;
using System.Collections.Generic;
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
            var survey = this.DbSet
                .Include("Questions")
                .Include("Questions.Answers")
                .FirstOrDefault();

            if (survey.Questions == null)
            {
                survey.Questions = new List<Question>();
            }

            foreach (Question q in survey.Questions)
            {
                if (q.Answers == null)
                {
                    q.Answers = new List<Answer>();
                }
            }

            return survey;
        }

        public void RecreteSurvey(SurveyDTO dto)
        {
            var survey = this.GetSurvey();
            if (survey == null)
            {
                throw new InvalidOperationException();
            }

            Context.Set<Answer>().RemoveRange(survey.Questions.SelectMany(q => q.Answers));
            Context.Set<Question>().RemoveRange(survey.Questions);
            Context.Set<Survey>().Remove(survey);
            Context.SaveChanges();

            var @new = Mapper.Map<Survey>(dto);
            @new.CreatedDate = DateTime.UtcNow;

            this.Create(@new);
        }

        public void MakeCorrections(SurveyDTO surveyDTO)
        {
            var survey = this.GetSurvey();
            if (survey == null)
            {
                throw new InvalidOperationException();
            }

            survey.Title = surveyDTO.Title;
            survey.Description = surveyDTO.Description;

            foreach (QuestionDTO question in surveyDTO.Questions)
            {
                var originalQuestion = survey.Questions.FirstOrDefault(q => q.Id == question.Id);
                if (originalQuestion == null)
                    continue;

                originalQuestion.Text = question.Text;
                foreach (AnswerDTO answer in question.Answers)
                {
                    var originalAnswer = originalQuestion.Answers.FirstOrDefault(a => a.Id == answer.Id);
                    if (originalAnswer == null)
                        continue;

                    originalAnswer.Text = answer.Text;
                }
            }

            this.Context.SaveChanges();
        }
    }
}
