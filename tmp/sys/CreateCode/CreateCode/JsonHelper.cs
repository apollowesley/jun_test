using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.Script.Serialization;

namespace CreateCode
{
    public class JsonHelper
    {
        /// <summary>
        /// 对象转换成json字符串
        /// </summary>
        /// <param name="obj"></param>
        /// <returns></returns>
        public static string ObjectToJson(object obj)
        {
            JavaScriptSerializer jsonSerialize = new JavaScriptSerializer();
            return jsonSerialize.Serialize(obj);
        }
        
        /// <summary>
        /// json字符串转换成对象
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="jsonStr"></param>
        /// <returns></returns>
        public static T JsonToObject<T>(string jsonStr)
        {
            JavaScriptSerializer jsonSerialize = new JavaScriptSerializer();
            return jsonSerialize.Deserialize<T>(jsonStr);
        }
    }
}
