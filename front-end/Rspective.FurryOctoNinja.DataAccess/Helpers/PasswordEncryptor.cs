using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Rspective.FurryOctoNinja.DataAccess.Helpers
{
    class PasswordEncryptor
    {
        public static string Encrypt(string password)
        {
            using(var md5 = MD5.Create())
            {
                byte[] data = md5.ComputeHash(Encoding.UTF8.GetBytes(password ?? string.Empty));
                var builder = new StringBuilder();

                foreach (var character in data) 
                {
                    builder.Append(character.ToString("x2"));
                }

                return builder.ToString();
            }
        }
    }
}
