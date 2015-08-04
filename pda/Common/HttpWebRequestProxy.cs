using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.IO;
using Newtonsoft.Json;

namespace Common
{
    public static class HttpWebRequestProxy
    {
        public static string token = "";
        public static string username = "";
        public static string userroleid = "";

        public static string PostRest(string restPath, string restMethod, string restContentType, string data)
        {
            try
            {
                string ResponseResult = "";

                string Request_Url = MobileConfiguration.GetSetting("ServiceAddress");
                Request_Url = Path.Combine(Request_Url, restPath);
                Uri address = new Uri(Request_Url);

                // Create the web request  
                HttpWebRequest request = WebRequest.Create(address) as HttpWebRequest;

                // Set type to POST  
                request.Method = restMethod;
                request.ContentType = restContentType;

                // Create a byte array of the data we want to send  
                byte[] byteData = UTF8Encoding.UTF8.GetBytes(data);

                // Set the content length in the request headers  
                request.ContentLength = byteData.Length;

                // Write data  
                using (Stream postStream = request.GetRequestStream())
                {
                    postStream.Write(byteData, 0, byteData.Length); 
                }

                // Get response
                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    // Get the response stream  
                    StreamReader output = new StreamReader(response.GetResponseStream());

                    ResponseResult = output.ReadToEnd();
                }
                return ResponseResult;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public static U PostRest_JsonOmnibearing<T,U>(string restPath,T req,U res) where U:new()
        {
            string ResponseStr = HttpWebRequestProxy.PostRest(restPath,
                "POST", "application/json", JsonConvert.SerializeObject(req));
            if (res == null) { res =new U(); }
            res = JsonConvert.DeserializeObject<U>(ResponseStr);
            return res;
        }
    }
}
