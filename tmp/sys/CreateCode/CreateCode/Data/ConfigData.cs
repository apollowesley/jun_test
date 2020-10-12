using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CreateCode
{
    public class ConfigData
    {
        #region 数据库配置

        /// <summary>
        /// 数据库地址
        /// </summary>
        public static string sqlAddress;
        /// <summary>
        /// 数据库名
        /// </summary>
        public static string sqlName;
        /// <summary>
        /// 数据库登录名
        /// </summary>
        public static string userName;
        /// <summary>
        /// 数据库登录密码
        /// </summary>
        public static string passWord;
        #endregion

        #region 文件配置
        /// <summary>
        /// 文件名配置 json数据
        /// </summary>
        private static string FileNameConfig;

        /// <summary>
        /// 是否覆盖
        /// </summary>
        public static bool isCover;

        /// <summary>
        /// 打开的文件
        /// </summary>
        private static string openFile;

        /// <summary>
        /// 关闭时选中的文件
        /// </summary>
        public static string selectFile;
        #endregion


        #region 方法
        /// <summary>
        /// 获取文件配置
        /// </summary>
        /// <returns></returns>
        public static Dictionary<string, string> getFileNameConfig()
        {
            try
            {
                return JsonHelper.JsonToObject<Dictionary<string, string>>(FileNameConfig);
            }
            catch
            {
                return new Dictionary<string, string>();
            }
        }

        /// <summary>
        /// 设置文件配置
        /// </summary>
        /// <returns></returns>
        public static void setFileNameConfig(Dictionary<string, string> dic)
        {
            FileNameConfig = JsonHelper.ObjectToJson(dic);
        }

        /// <summary>
        /// 获取打开的文件
        /// </summary>
        /// <returns></returns>
        public static List<string> getOpenFile()
        {
            try
            {
                return JsonHelper.JsonToObject<List<string>>(openFile);
            }
            catch
            {
                return new List<string>();
            }
        }

        /// <summary>
        /// 设置打开的文件
        /// </summary>
        public static void setOpenFile(List<string> sList)
        {
            openFile = JsonHelper.ObjectToJson(sList);
        }
        #endregion
    }
}
