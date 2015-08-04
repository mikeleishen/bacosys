using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Linq;
using System.IO;
using System.Reflection;
using System.Collections.Specialized;

namespace Common
{
    public class MobileConfiguration
    {
        public static NameValueCollection Settings;
        public static DateTime LastReadFileTime;
        public static string GetSetting(string key)
        {
            Initialize();
            return Settings[key];
        }

        public static void Initialize()
        {
            try
            {
                string appPath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetName().CodeBase);
                string configFile = Path.Combine(appPath, "config.xml");
                if (!File.Exists(configFile))
                {
                    throw new FileNotFoundException("配置文件 config.xml 丢失.");
                }
                if (Settings == null || Settings.Count < 1 || LastReadFileTime == null || LastReadFileTime != File.GetLastWriteTime(configFile))
                {
                    LastReadFileTime = File.GetLastWriteTime(configFile);
                    XmlDocument xmlDocument = new XmlDocument();
                    xmlDocument.Load(configFile);
                    XmlNodeList nodeList = xmlDocument.GetElementsByTagName("appSettings");
                    Settings = new NameValueCollection();
                    foreach (XmlNode node in nodeList)
                    {
                        foreach (XmlNode key in node.ChildNodes)
                        {
                            Settings.Add(key.Attributes["key"].Value, key.Attributes["value"].Value);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("读取配置文件出错：" + ex.Message);
            }
        }
    }
}
