using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Rspective.FurryOctoNinja.Web.Models
{
    public class Survey
    {
        public int Id { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        [JsonConverter(typeof(Rspective.FurryOctoNinja.Web.Helpers.UnixDateTimeConverter))]
        public DateTime CreatedDate { get; set; }

        public IEnumerable<Question> Questions { get; set; }
    }
}