using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Common
{
    public static class StringUtil
    {
        public static string subStringBacoPre(string str)
        {
            if (string.IsNullOrEmpty(str)) return "";
            string baco_pre = MobileConfiguration.GetSetting("BacoPre");

            if (!string.IsNullOrEmpty(baco_pre))
            {
                return str.Trim().Replace(MobileConfiguration.GetSetting("BacoPre"), "");
            }
            return "";
        }
    }
}
