using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Rspective.FurryOctoNinja.Web.Models
{
    //-- Mock. TODO: remove
    public class SurveyProgressMock
    {
        public static SurveyProgress Mock
        {
            get
            {
                return new SurveyProgress()
                {
                    Items = new SurveyProgressItem[] { 
						new SurveyProgressItem() { UserId = 1, UserName = "Tomasz Pindel", Completed = true },
						new SurveyProgressItem() { UserId = 2, UserName = "Michał Sędzielewski", Completed = false },
						new SurveyProgressItem() { UserId = 3, UserName = "Paweł Rychlik", Completed = true },
						new SurveyProgressItem() { UserId = 4, UserName = "Błażej Andraszyk", Completed = false },
						new SurveyProgressItem() { UserId = 5, UserName = "Kornel Labun", Completed = true },
						new SurveyProgressItem() { UserId = 6, UserName = "Marcin Polak", Completed = false },
						new SurveyProgressItem() { UserId = 7, UserName = "Adrian Wilczek", Completed = true },
						new SurveyProgressItem() { UserId = 8, UserName = "Wojtek Gawroński", Completed = false },
						new SurveyProgressItem() { UserId = 9, UserName = "Łukasz Wiktor", Completed = true },
						new SurveyProgressItem() { UserId = 10, UserName = "Tomek Sikora", Completed = false },
						new SurveyProgressItem() { UserId = 11, UserName = "Marcin Róg", Completed = true }
					}
                };
            }
        }
    }
}