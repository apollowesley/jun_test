﻿//------------------------------------------------------------------------------
// <auto-generated>
//     此代码由工具生成。
//     运行时版本:4.0.30319.36315
//
//     对此文件的更改可能会导致不正确的行为，并且如果
//     重新生成代码，这些更改将会丢失。
// </auto-generated>
//------------------------------------------------------------------------------

namespace CodeBuilder.Properties {
    
    
    [global::System.Runtime.CompilerServices.CompilerGeneratedAttribute()]
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.VisualStudio.Editors.SettingsDesigner.SettingsSingleFileGenerator", "12.0.0.0")]
    internal sealed partial class Settings : global::System.Configuration.ApplicationSettingsBase {
        
        private static Settings defaultInstance = ((Settings)(global::System.Configuration.ApplicationSettingsBase.Synchronized(new Settings())));
        
        public static Settings Default {
            get {
                return defaultInstance;
            }
        }
        
        [global::System.Configuration.UserScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("Server=.\\SQLEXPRESS;Database=ECMS;Trusted_Connection=True;")]
        public string strConn {
            get {
                return ((string)(this["strConn"]));
            }
            set {
                this["strConn"] = value;
            }
        }
        
        [global::System.Configuration.UserScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("MyProject")]
        public string strNamespace {
            get {
                return ((string)(this["strNamespace"]));
            }
            set {
                this["strNamespace"] = value;
            }
        }
        
        [global::System.Configuration.UserScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("双击此处选择文件导出路径...")]
        public string strPath {
            get {
                return ((string)(this["strPath"]));
            }
            set {
                this["strPath"] = value;
            }
        }
        
        [global::System.Configuration.ApplicationScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("using System;\r\nusing System.Collections.Generic;\r\nusing System.Data.SqlClient;\r\nu" +
            "sing System.Text;\r\nnamespace Helper\r\n{\r\n    public static class GenericSQLGenera" +
            "tor\r\n    {\r\n        public static string GetWhereStr<T>(T entity, string tableNa" +
            "me, out List<SqlParameter> list, params string[] fields) where T : new()\r\n      " +
            "  {\r\n            StringBuilder sbu = new StringBuilder();\r\n            list = ne" +
            "w List<SqlParameter>();\r\n\r\n            sbu.Append(\"\");\r\n            sbu.Append(\"" +
            "select * from [\" + tableName + \"] where (1=1)\");\r\n            if (fields != null" +
            ")\r\n            {\r\n                //遍历每一个要生成SQL的字段，取出内容\r\n                foreach" +
            " (string field in fields)\r\n                {\r\n                    object value =" +
            " entity.GetType().GetProperty(field).GetValue(entity, null);\r\n                  " +
            "  if (value is int || value is double || value is decimal || value is double || " +
            "value is long || value is float)\r\n                    {\r\n\r\n                     " +
            "   sbu.AppendFormat(\" and ([{0}]=@{0})\", field);\r\n                        list.A" +
            "dd(new SqlParameter(\"@\" + field + \"\", value));\r\n\r\n                    }\r\n       " +
            "             else if (value is DateTime)\r\n                    {\r\n               " +
            "         sbu.AppendFormat(\" and ([{0}]=@{0})\", field);\r\n                        " +
            "list.Add(new SqlParameter(\"@\" + field + \"\", Convert.ToDateTime(value)));\r\n\r\n    " +
            "                }\r\n                    else if (value is Guid)\r\n                " +
            "    {\r\n                        sbu.AppendFormat(\" and ([{0}]=@{0})\", field);\r\n  " +
            "                      list.Add(new SqlParameter(\"@\" + field + \"\", new Guid(value" +
            ".ToString())));\r\n\r\n                    }\r\n                    else if (value is " +
            "Boolean)\r\n                    {\r\n                        sbu.AppendFormat(\" and " +
            "([{0}]=@{0})\", field);\r\n                        list.Add(new SqlParameter(\"@\" + " +
            "field + \"\", Convert.ToBoolean(value)));\r\n\r\n                    }\r\n              " +
            "      else if(value is String||value is Char)\r\n                    {\r\n          " +
            "              sbu.AppendFormat(\" and ([{0}]=@{0})\", field);\r\n                   " +
            "     list.Add(new SqlParameter(\"@\" + field + \"\", Convert.ToString(value)));\r\n\r\n " +
            "                   }else{\r\n                        sbu.AppendFormat(\" and ([{0}]" +
            "=@{0})\", field);\r\n                        list.Add(new SqlParameter(\"@\" + field " +
            "+ \"\",Helper.SqlHelper.ToDBValue( value)));\r\n                    }\r\n             " +
            "   }\r\n            }\r\n            return (sbu.ToString());\r\n        }\r\n    }\r\n}")]
        public string GenericSQLGeneratorHelper {
            get {
                return ((string)(this["GenericSQLGeneratorHelper"]));
            }
        }
        
        [global::System.Configuration.ApplicationScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("using System;\r\nusing System.Collections.Generic;\r\nusing System.Linq;\r\nusing Syste" +
            "m.Text;\r\nusing System.Configuration;\r\nusing System.Data.SqlClient;\r\nusing System" +
            ".Data;\r\n\r\nnamespace Helper\r\n{\r\n    public static class SqlHelper\r\n    {\r\n       " +
            " public static readonly string connstr = ConfigurationManager.ConnectionStrings[" +
            "\"connstr\"].ConnectionString;\r\n        /// <summary>\r\n        /// 打开数据库\r\n        " +
            "/// </summary>\r\n        /// <returns></returns>\r\n        public static SqlConnec" +
            "tion OpenConnection()\r\n        {\r\n            using (SqlConnection conn = new Sq" +
            "lConnection(connstr))\r\n            {\r\n                conn.Open();\r\n            " +
            "    return conn;\r\n            }\r\n        }\r\n        /// <summary>\r\n        /// 执" +
            "行返回受影响的行数\r\n        /// </summary>\r\n        /// <param name=\"cmdText\"></param>\r\n " +
            "       /// <param name=\"parameters\"></param>\r\n        /// <returns></returns>\r\n " +
            "       public static int ExecuteNonQuery(string cmdText, params SqlParameter[] p" +
            "arameters)\r\n        {\r\n            using (SqlConnection conn = new SqlConnection" +
            "(connstr))\r\n            {\r\n                conn.Open();\r\n                return " +
            "ExecuteNonQuery(conn, cmdText, parameters);\r\n            }\r\n        }\r\n        /" +
            "// <summary>\r\n        /// 执行返回第一行第一列\r\n        /// </summary>\r\n        /// <param" +
            " name=\"cmdText\"></param>\r\n        /// <param name=\"parameters\"></param>\r\n       " +
            " /// <returns></returns>\r\n        public static object ExecuteScalar(string cmdT" +
            "ext, params SqlParameter[] parameters)\r\n        {\r\n            using (SqlConnect" +
            "ion conn = new SqlConnection(connstr))\r\n            {\r\n                conn.Open" +
            "();\r\n                return ExecuteScalar(conn, cmdText, parameters);\r\n         " +
            "   }\r\n        }\r\n        /// <summary>\r\n        /// 执行返回DataSet\r\n        /// </s" +
            "ummary>\r\n        /// <param name=\"cmdText\"></param>\r\n        /// <param name=\"pa" +
            "rameters\"></param>\r\n        /// <returns></returns>\r\n        public static DataS" +
            "et ExecuteDataSet(string cmdText, params SqlParameter[] parameters)\r\n        {\r\n" +
            "            using (SqlConnection con = new SqlConnection(connstr))\r\n            " +
            "{\r\n                con.Open();\r\n                return ExecuteDataSet(con, cmdTe" +
            "xt, parameters);\r\n            }\r\n        }\r\n        /// <summary>\r\n        /// 执" +
            "行返回DataTable\r\n        /// </summary>\r\n        /// <param name=\"cmdText\"></param>" +
            "\r\n        /// <param name=\"parameters\"></param>\r\n        /// <returns></returns>" +
            "\r\n        public static DataTable ExecuteDataTable(string cmdText, params SqlPar" +
            "ameter[] parameters)\r\n        {\r\n            using (SqlConnection con = new SqlC" +
            "onnection(connstr))\r\n            {\r\n                con.Open();\r\n               " +
            " return ExecuteDataTable(con, cmdText, parameters);\r\n            }\r\n        }\r\n " +
            "       /// <summary>\r\n        /// 执行返回受影响的行数\r\n        /// </summary>\r\n        //" +
            "/ <param name=\"conn\"></param>\r\n        /// <param name=\"cmdText\"></param>\r\n     " +
            "   /// <param name=\"parameters\"></param>\r\n        /// <returns></returns>\r\n     " +
            "   public static int ExecuteNonQuery(SqlConnection conn, string cmdText,\r\n      " +
            "     params SqlParameter[] parameters)\r\n        {\r\n            using (SqlCommand" +
            " cmd = conn.CreateCommand())\r\n            {\r\n                cmd.CommandText = c" +
            "mdText;\r\n                cmd.Parameters.AddRange(parameters);\r\n                r" +
            "eturn cmd.ExecuteNonQuery();\r\n            }\r\n        }\r\n        /// <summary>\r\n " +
            "       /// 执行返回第一行第一列\r\n        /// </summary>\r\n        /// <param name=\"conn\"></" +
            "param>\r\n        /// <param name=\"cmdText\"></param>\r\n        /// <param name=\"par" +
            "ameters\"></param>\r\n        /// <returns></returns>\r\n        public static object" +
            " ExecuteScalar(SqlConnection conn, string cmdText,\r\n            params SqlParame" +
            "ter[] parameters)\r\n        {\r\n            using (SqlCommand cmd = conn.CreateCom" +
            "mand())\r\n            {\r\n                cmd.CommandText = cmdText;\r\n            " +
            "    cmd.Parameters.AddRange(parameters);\r\n                return cmd.ExecuteScal" +
            "ar();\r\n            }\r\n        }\r\n        /// <summary>\r\n        /// 执行返回DataTabl" +
            "e\r\n        /// </summary>\r\n        /// <param name=\"con\"></param>\r\n        /// <" +
            "param name=\"cmdText\"></param>\r\n        /// <param name=\"parameters\"></param>\r\n  " +
            "      /// <returns></returns>\r\n        public static DataTable ExecuteDataTable(" +
            "SqlConnection con, string cmdText, params SqlParameter[] parameters)\r\n        {\r" +
            "\n            return ExecuteDataSet(con, cmdText, parameters).Tables[0];\r\n       " +
            " }\r\n        /// <summary>\r\n        /// 执行返回DataSet\r\n        /// </summary>\r\n    " +
            "    /// <param name=\"con\"></param>\r\n        /// <param name=\"cmdText\"></param>\r\n" +
            "        /// <param name=\"parameters\"></param>\r\n        /// <returns></returns>\r\n" +
            "        public static DataSet ExecuteDataSet(SqlConnection con, string cmdText, " +
            "params SqlParameter[] parameters)\r\n        {\r\n            using (SqlCommand cmd " +
            "= con.CreateCommand())\r\n            {\r\n                cmd.CommandText = cmdText" +
            ";\r\n                cmd.Parameters.AddRange(parameters);\r\n                using (" +
            "SqlDataAdapter adapter = new SqlDataAdapter(cmd))\r\n                {\r\n          " +
            "          DataSet ds = new DataSet();\r\n                    adapter.FillSchema(ds" +
            ", SchemaType.Source);\r\n                    adapter.Fill(ds);\r\n                  " +
            "  return ds;\r\n                }\r\n            }\r\n        }\r\n        /// <summary>" +
            "\r\n        /// null 转换为DBNull\r\n        /// </summary>\r\n        /// <param name=\"v" +
            "alue\"></param>\r\n        /// <returns></returns>\r\n        public static object To" +
            "DBValue(this object value)\r\n        {\r\n            return value == null ? DBNull" +
            ".Value : value;\r\n        }\r\n        /// <summary>\r\n        /// DBNull转换为null\r\n  " +
            "      /// </summary>\r\n        /// <param name=\"dbValue\"></param>\r\n        /// <r" +
            "eturns></returns>\r\n        public static object FromDBValue(this object dbValue)" +
            "\r\n        {\r\n            return dbValue == DBNull.Value ? null : dbValue;\r\n     " +
            "   }\r\n    }\r\n}")]
        public string SqlHelper {
            get {
                return ((string)(this["SqlHelper"]));
            }
        }
        
        [global::System.Configuration.ApplicationScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("using System;\r\nusing System.Collections.Generic;\r\nusing System.Linq;\r\nusing Syste" +
            "m.Text;\r\nusing System.Configuration;\r\nusing System.Data;\r\nusing MySql.Data.MySql" +
            "Client;\r\n\r\nnamespace Helper\r\n{\r\n    public static class MySqlHelper\r\n    {\r\n    " +
            "    public static readonly string connstr = ConfigurationManager.ConnectionStrin" +
            "gs[\"connstr\"].ConnectionString;\r\n        /// <summary>\r\n        /// 打开数据库\r\n     " +
            "   /// </summary>\r\n        /// <returns></returns>\r\n        public static MySqlC" +
            "onnection OpenConnection()\r\n        {\r\n            using (MySqlConnection conn =" +
            " new MySqlConnection(connstr))\r\n            {\r\n                conn.Open();\r\n   " +
            "             return conn;\r\n            }\r\n        }\r\n        /// <summary>\r\n    " +
            "    /// 执行返回受影响的行数\r\n        /// </summary>\r\n        /// <param name=\"cmdText\"></" +
            "param>\r\n        /// <param name=\"parameters\"></param>\r\n        /// <returns></re" +
            "turns>\r\n        public static int ExecuteNonQuery(string cmdText, params MySqlPa" +
            "rameter[] parameters)\r\n        {\r\n            using (MySqlConnection conn = new " +
            "MySqlConnection(connstr))\r\n            {\r\n                conn.Open();\r\n        " +
            "        return ExecuteNonQuery(conn, cmdText, parameters);\r\n            }\r\n     " +
            "   }\r\n        /// <summary>\r\n        /// 执行返回第一行第一列\r\n        /// </summary>\r\n   " +
            "     /// <param name=\"cmdText\"></param>\r\n        /// <param name=\"parameters\"></" +
            "param>\r\n        /// <returns></returns>\r\n        public static object ExecuteSca" +
            "lar(string cmdText, params MySqlParameter[] parameters)\r\n        {\r\n            " +
            "using (MySqlConnection conn = new MySqlConnection(connstr))\r\n            {\r\n    " +
            "            conn.Open();\r\n                return ExecuteScalar(conn, cmdText, pa" +
            "rameters);\r\n            }\r\n        }\r\n        /// <summary>\r\n        /// 执行返回Dat" +
            "aSet\r\n        /// </summary>\r\n        /// <param name=\"cmdText\"></param>\r\n      " +
            "  /// <param name=\"parameters\"></param>\r\n        /// <returns></returns>\r\n      " +
            "  public static DataSet ExecuteDataSet(string cmdText, params MySqlParameter[] p" +
            "arameters)\r\n        {\r\n            using (MySqlConnection con = new MySqlConnect" +
            "ion(connstr))\r\n            {\r\n                con.Open();\r\n                retur" +
            "n ExecuteDataSet(con, cmdText, parameters);\r\n            }\r\n        }\r\n        /" +
            "// <summary>\r\n        /// 执行返回DataTable\r\n        /// </summary>\r\n        /// <pa" +
            "ram name=\"cmdText\"></param>\r\n        /// <param name=\"parameters\"></param>\r\n    " +
            "    /// <returns></returns>\r\n        public static DataTable ExecuteDataTable(st" +
            "ring cmdText, params MySqlParameter[] parameters)\r\n        {\r\n            using " +
            "(MySqlConnection con = new MySqlConnection(connstr))\r\n            {\r\n           " +
            "     con.Open();\r\n                return ExecuteDataTable(con, cmdText, paramete" +
            "rs);\r\n            }\r\n        }\r\n        /// <summary>\r\n        /// 执行返回受影响的行数\r\n " +
            "       /// </summary>\r\n        /// <param name=\"conn\"></param>\r\n        /// <par" +
            "am name=\"cmdText\"></param>\r\n        /// <param name=\"parameters\"></param>\r\n     " +
            "   /// <returns></returns>\r\n        public static int ExecuteNonQuery(MySqlConne" +
            "ction conn, string cmdText,\r\n           params MySqlParameter[] parameters)\r\n   " +
            "     {\r\n            using (MySqlCommand cmd = conn.CreateCommand())\r\n           " +
            " {\r\n                cmd.CommandText = cmdText;\r\n                cmd.Parameters.A" +
            "ddRange(parameters);\r\n                return cmd.ExecuteNonQuery();\r\n           " +
            " }\r\n        }\r\n        /// <summary>\r\n        /// 执行返回第一行第一列\r\n        /// </summ" +
            "ary>\r\n        /// <param name=\"conn\"></param>\r\n        /// <param name=\"cmdText\"" +
            "></param>\r\n        /// <param name=\"parameters\"></param>\r\n        /// <returns><" +
            "/returns>\r\n        public static object ExecuteScalar(MySqlConnection conn, stri" +
            "ng cmdText,\r\n            params MySqlParameter[] parameters)\r\n        {\r\n       " +
            "     using (MySqlCommand cmd = conn.CreateCommand())\r\n            {\r\n           " +
            "     cmd.CommandText = cmdText;\r\n                cmd.Parameters.AddRange(paramet" +
            "ers);\r\n                return cmd.ExecuteScalar();\r\n            }\r\n        }\r\n  " +
            "      /// <summary>\r\n        /// 执行返回DataTable\r\n        /// </summary>\r\n        " +
            "/// <param name=\"con\"></param>\r\n        /// <param name=\"cmdText\"></param>\r\n    " +
            "    /// <param name=\"parameters\"></param>\r\n        /// <returns></returns>\r\n    " +
            "    public static DataTable ExecuteDataTable(MySqlConnection con, string cmdText" +
            ", params MySqlParameter[] parameters)\r\n        {\r\n            return ExecuteData" +
            "Set(con, cmdText, parameters).Tables[0];\r\n        }\r\n        /// <summary>\r\n    " +
            "    /// 执行返回DataSet\r\n        /// </summary>\r\n        /// <param name=\"con\"></par" +
            "am>\r\n        /// <param name=\"cmdText\"></param>\r\n        /// <param name=\"parame" +
            "ters\"></param>\r\n        /// <returns></returns>\r\n        public static DataSet E" +
            "xecuteDataSet(MySqlConnection con, string cmdText, params MySqlParameter[] param" +
            "eters)\r\n        {\r\n            using (MySqlCommand cmd = con.CreateCommand())\r\n " +
            "           {\r\n                cmd.CommandText = cmdText;\r\n                cmd.Pa" +
            "rameters.AddRange(parameters);\r\n                using (MySqlDataAdapter adapter " +
            "= new MySqlDataAdapter(cmd))\r\n                {\r\n                    DataSet ds " +
            "= new DataSet();\r\n                    adapter.FillSchema(ds, SchemaType.Source);" +
            "\r\n                    adapter.Fill(ds);\r\n                    return ds;\r\n       " +
            "         }\r\n            }\r\n        }\r\n        /// <summary>\r\n        /// null 转换" +
            "为DBNull\r\n        /// </summary>\r\n        /// <param name=\"value\"></param>\r\n     " +
            "   /// <returns></returns>\r\n        public static object ToDBValue(this object v" +
            "alue)\r\n        {\r\n            return value == null ? DBNull.Value : value;\r\n    " +
            "    }\r\n        /// <summary>\r\n        /// DBNull转换为null\r\n        /// </summary>\r" +
            "\n        /// <param name=\"dbValue\"></param>\r\n        /// <returns></returns>\r\n  " +
            "      public static object FromDBValue(this object dbValue)\r\n        {\r\n        " +
            "    return dbValue == DBNull.Value ? null : dbValue;\r\n        }\r\n    }\r\n}")]
        public string MySqlHelper {
            get {
                return ((string)(this["MySqlHelper"]));
            }
        }
        
        [global::System.Configuration.ApplicationScopedSettingAttribute()]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [global::System.Configuration.DefaultSettingValueAttribute("using MySql.Data.MySqlClient;\r\nusing System;\r\nusing System.Collections.Generic;\r\n" +
            "using System.Text;\r\nnamespace Helper\r\n{\r\n    public static class GenericSQLGener" +
            "ator\r\n    {\r\n        public static string GetWhereStr<T>(T entity, string tableN" +
            "ame, out List<MySqlParameter> list, params string[] fields) where T : new()\r\n   " +
            "     {\r\n            StringBuilder sbu = new StringBuilder();\r\n            list =" +
            " new List<MySqlParameter>();\r\n\r\n            sbu.Append(\"\");\r\n            sbu.App" +
            "end(\"select * from [\" + tableName + \"] where (1=1)\");\r\n            if (fields !=" +
            " null)\r\n            {\r\n                //遍历每一个要生成MySql的字段，取出内容\r\n                " +
            "foreach (string field in fields)\r\n                {\r\n                    object " +
            "value = entity.GetType().GetProperty(field).GetValue(entity, null);\r\n           " +
            "         if (value is int || value is double || value is decimal || value is dou" +
            "ble || value is long || value is float)\r\n                    {\r\n\r\n              " +
            "          sbu.AppendFormat(\" and ([{0}]=@{0})\", field);\r\n                       " +
            " list.Add(new MySqlParameter(\"@\" + field + \"\", value));\r\n\r\n                    }" +
            "\r\n                    else if (value is DateTime)\r\n                    {\r\n      " +
            "                  sbu.AppendFormat(\" and ([{0}]=@{0})\", field);\r\n               " +
            "         list.Add(new MySqlParameter(\"@\" + field + \"\", Convert.ToDateTime(value)" +
            "));\r\n\r\n                    }\r\n                    else if (value is Guid)\r\n     " +
            "               {\r\n                        sbu.AppendFormat(\" and ([{0}]=@{0})\", " +
            "field);\r\n                        list.Add(new MySqlParameter(\"@\" + field + \"\", n" +
            "ew Guid(value.ToString())));\r\n\r\n                    }\r\n                    else " +
            "if (value is Boolean)\r\n                    {\r\n                        sbu.Append" +
            "Format(\" and ([{0}]=@{0})\", field);\r\n                        list.Add(new MySqlP" +
            "arameter(\"@\" + field + \"\", Convert.ToBoolean(value)));\r\n\r\n                    }\r" +
            "\n                    else if (value is String || value is Char)\r\n               " +
            "     {\r\n                        sbu.AppendFormat(\" and ([{0}]=@{0})\", field);\r\n " +
            "                       list.Add(new MySqlParameter(\"@\" + field + \"\", Convert.ToS" +
            "tring(value)));\r\n\r\n                    }\r\n                    else\r\n            " +
            "        {\r\n                        sbu.AppendFormat(\" and ([{0}]=@{0})\", field);" +
            "\r\n                        list.Add(new MySqlParameter(\"@\" + field + \"\", Helper.M" +
            "ySqlHelper.ToDBValue(value)));\r\n                    }\r\n                }\r\n      " +
            "      }\r\n            return (sbu.ToString());\r\n        }\r\n    }\r\n}")]
        public string GenericMySQLGeneratorHelper {
            get {
                return ((string)(this["GenericMySQLGeneratorHelper"]));
            }
        }
    }
}