﻿using Rspective.FurryOctoNinja.DataAccess.Context;
using Rspective.FurryOctoNinja.DataAccess.DbModel;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Rspective.FurryOctoNinja.DataAccess.Repositories
{
    class ApplicationUserRepository : Repository<ApplicationUser>, IApplicationUserRepository
    {
        public ApplicationUserRepository(IUnitOfWork ouw)
            : base(ouw)
        {
        }

        public ApplicationUser Authenticate(string login, string password)
        {
            return this.DbSet
                .Include("Roles")
                .FirstOrDefault(user => user.Login == login && user.Password == password);
        }

        public ICollection<ApplicationUser> GetAll()
        {
            return this.DbSet
                .Include("Roles")
                .ToArray();
        }

        public ICollection<ApplicationUser> GetAllUsers()
        {
            return this.DbSet
               .Include("Roles")
               .Where(user => user.Roles.FirstOrDefault(role => role.Name == "Admin") == null)
               .ToArray();
        }

        public ApplicationUser Get(int userId)
        {
            return this.DbSet
                .Include("Roles")
                .FirstOrDefault(user => user.Id == userId);
        }

        public bool Exists(string login, int? exceptId)
        {
            return this.DbSet.Any(user => user.Login == login && (!exceptId.HasValue || exceptId.Value != user.Id));
        }
    }
}
