using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;

namespace CreateCode
{
    public class NeedTable
    {
        public static DataTable dt = getDataBaseTable();

        
        /// <summary>
        /// 获取数据库中所有表的名字
        /// </summary>
        /// <returns></returns>
        public static DataTable getDataBaseTable()
        {
            string connectionString = string.Format(@"Data Source={0};Initial Catalog={1};User ID={2};Password={3}", ConfigData.sqlAddress, ConfigData.sqlName, ConfigData.userName, ConfigData.passWord);
            string sql = "select name from sysobjects where xtype = 'u' and status>=0 order by name";
            DataTable dt = new DataTable();
            SqlConnection conn = new SqlConnection(connectionString);
            try
            {
                conn.Open();
                SqlCommand comm = new SqlCommand(sql, conn);
                SqlDataAdapter adapter = new SqlDataAdapter(comm);
                if (adapter != null)
                {
                    adapter.Fill(dt);
                }
                return dt;
            }
            catch (Exception ex)
            {
                throw ex;                                
            }
            finally
            {
                if (conn.State != ConnectionState.Closed)
                {
                    conn.Close();
                }
                conn.Dispose();
            }
        }
    }
}
