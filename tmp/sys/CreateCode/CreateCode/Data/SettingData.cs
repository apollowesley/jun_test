using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text;

namespace CreateCode
{
    public class SettingData
    {
        /// <summary>
        /// 配置文件地址
        /// </summary>
        public static string configIni = Directory.GetCurrentDirectory() + @"\config.ini";
        #region API函数声明

        /// <summary>
        /// 写入INI文件
        /// </summary>
        /// <param name="section">节点名称[如[TypeName]]</param>
        /// <param name="key">键</param>
        /// <param name="val">值</param>
        /// <param name="filePath">文件路径</param>
        /// <returns></returns>
        [DllImport("kernel32")]//返回0表示失败，非0为成功
        private static extern long WritePrivateProfileString(string section, string key,
            string val, string filePath);

        /// <summary>
        /// 读取INI文件
        /// </summary>
        /// <param name="section">节点名称</param>
        /// <param name="key">键</param>
        /// <param name="def">值</param>
        /// <param name="retVal">stringbulider对象</param>
        /// <param name="size">字节大小</param>
        /// <param name="filePath">文件路径</param>
        /// <returns></returns>
        [DllImport("kernel32")]//返回取得字符串缓冲区的长度
        private static extern long GetPrivateProfileString(string section, string key,
            string def, StringBuilder retVal, int size, string filePath);


        #endregion

        #region 读Ini文件

        /// <summary>
        /// 读配置文件
        /// </summary>
        /// <returns></returns>
        public static void ReadIniData()
        {
            if (File.Exists(configIni))
            {

                Type cdType = typeof(ConfigData);
                //object obj = Activator.CreateInstance(cdType);
                BindingFlags flag = BindingFlags.NonPublic |BindingFlags.Public| BindingFlags.Instance | BindingFlags.Static;
                FieldInfo[] fis = cdType.GetFields(flag);
                foreach (FieldInfo fi in fis)
                {
                    try
                    {
                        StringBuilder temp = new StringBuilder(1024);
                        GetPrivateProfileString("congif", fi.Name, "", temp, 1024, configIni);
                        //ConstData.chatFormActive = Convert.ToBoolean(temp.ToString());
                        if (!fi.FieldType.IsGenericType)
                        {
                            //非泛型
                            fi.SetValue(null, string.IsNullOrEmpty(temp.ToString()) ? null : Convert.ChangeType(temp.ToString(), fi.FieldType));
                        }
                        else
                        {
                            //泛型Nullable<>
                            Type genericTypeDefinition = fi.FieldType.GetGenericTypeDefinition();
                            if (genericTypeDefinition == typeof(Nullable<>))
                            {
                                fi.SetValue(null, string.IsNullOrEmpty(temp.ToString()) ? null : Convert.ChangeType(temp.ToString(), Nullable.GetUnderlyingType(fi.FieldType)));
                            }
                        }

                        //fi.SetValue(null, Convert.ChangeType(temp, fi.FieldType));
                    }
                    catch (Exception ex)
                    {
                        continue;
                    }
                }
            }
        }

        #endregion

        #region 写Ini文件

        /// <summary>
        /// 写配置文件
        /// </summary>
        public static bool WriteIniData()
        {
            bool result = false;
            if (!File.Exists(configIni))
            {
                File.Create(configIni);
            }
            Type cdType = typeof(ConfigData);
            BindingFlags flag = BindingFlags.NonPublic | BindingFlags.Public | BindingFlags.Instance | BindingFlags.Static;
            FieldInfo[] fis = cdType.GetFields(flag);
            try
            {
                foreach (FieldInfo fi in fis)
                {

                    object c = fi.GetValue(null);
                    long OpStation = WritePrivateProfileString("congif", fi.Name, fi.GetValue(null).ToString(), configIni);
                }
                result = true;
            }
            catch (Exception ex)
            {
                result = false;
            }           
            return result;
        }
        #endregion
    }
}
