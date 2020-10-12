using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace CreateCode
{
    public class DynamicControlHelper
    {
        /// <summary>
        /// 动态控件list
        /// </summary>
        public static List<DynamicControlHelper> DCHList = new List<DynamicControlHelper>();

        /// <summary>
        /// 控件id
        /// </summary>
        public string id { get; set; }

        /// <summary>
        /// 控件
        /// </summary>
        public Control control { get; set; }

        /// <summary>
        /// 文件路径
        /// </summary>
        public string filePath { get; set; }

        /// <summary>
        /// 是否已经保存
        /// </summary>
        public bool isSave { get; set; }

        /// <summary>
        /// 判断控件id是否唯一
        /// </summary>
        /// <param name="Id"></param>
        /// <returns>true 唯一</returns>
        public static bool isSoleId(string Id)
        {
            if (DCHList.Where(p => p.id == Id).FirstOrDefault() == null)
                return true;
            else
                return false;
        }

        /// <summary>
        /// 创建唯一Id
        /// </summary>
        /// <param name="text">控件id前字</param>
        /// <returns></returns>
        public static string createId(string text)
        {
            Random r = new Random();
            string result = text + r.Next(1, 10000);
            while (!isSoleId(result))
            {
                result = text + r.Next(1, 10000);
            }
            return result;
        }

        /// <summary>
        /// 获取控件
        /// </summary>
        /// <param name="Id">控件id</param>
        /// <returns></returns>
        public static Control getControl(string Id)
        {
            DynamicControlHelper dch=DCHList.Where(p=>p.id==Id).FirstOrDefault();
            if (dch == null)
            {
                throw (new System.Exception("控件有错误"));
            }
            else
            {
                return dch.control;
            }
        }

        /// <summary>
        /// 判断文件路径是否存在
        /// </summary>
        /// <param name="path"></param>
        /// <returns>true 存在</returns>
        public static bool isHavePath(string path)
        {
            DynamicControlHelper dch = DCHList.Where(p => p.filePath == path).FirstOrDefault();
            if (dch == null)
                return false;
            else
                return true;
        }

        /// <summary>
        /// 判断文件路径是否存在
        /// </summary>
        /// <param name="control"></param>
        /// <returns>true 存在</returns>
        public static bool isHavePath(Control control)
        {
            DynamicControlHelper dch = DCHList.Where(p => p.control == control).FirstOrDefault();
            if (dch == null||string.IsNullOrEmpty(dch.filePath))
                return false;
            else
                return true;
        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="dch"></param>
        public static void update(DynamicControlHelper dch)
        {
            for (int i = 0; i < DCHList.Count; i++)
            {
                if (DCHList[i].id == dch.id)
                {
                    DCHList[i] = dch;
                    break;
                }
            }
            
        }

        /// <summary>
        /// 移除指定控件
        /// </summary>
        /// <param name="control"></param>
        public static void reomve(Control control)
        {
            DynamicControlHelper dch = DCHList.Where(p => p.control == control).FirstOrDefault();
            if (dch != null)
                DCHList.Remove(dch);
        }
    }
}
