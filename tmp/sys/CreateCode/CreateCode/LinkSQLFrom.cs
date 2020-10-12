using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace CreateCode
{
    public partial class LinkSQLFrom : Form
    {
        public LinkSQLFrom()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 配置信息
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnConfirm_Click(object sender, EventArgs e)
        {
            if(string.IsNullOrEmpty(SqlAddress.Text.Trim())||
                string.IsNullOrEmpty(SqlName.Text.Trim())||
                string.IsNullOrEmpty(UserName.Text.Trim())||
                string.IsNullOrEmpty(PassWord.Text.Trim()))
            {
                MessageBox.Show("不能为空!");
                return;
            }
            ConfigData.sqlAddress = SqlAddress.Text.Trim();
            ConfigData.sqlName = SqlName.Text.Trim();
            ConfigData.userName = UserName.Text.Trim();
            ConfigData.passWord = PassWord.Text.Trim();
            SettingData.WriteIniData();
            this.Close();
        }

        /// <summary>
        /// 测试连接
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button1_Click(object sender, EventArgs e)
        {
            string connectionString = string.Format(@"Data Source={0};Initial Catalog={1};User ID={2};Password={3}", SqlAddress.Text.Trim(), SqlName.Text.Trim(), UserName.Text.Trim(),PassWord.Text.Trim());
            SqlConnection conn = new SqlConnection(connectionString);
            try {
                conn.Open();
                MessageBox.Show("成功");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
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

        private void LinkSQLFrom_Load(object sender, EventArgs e)
        {
            //配置
            if (!string.IsNullOrEmpty(ConfigData.sqlAddress))
            {
                SqlAddress.Text = ConfigData.sqlAddress;
            }
            if (!string.IsNullOrEmpty(ConfigData.sqlName))
            {
                SqlName.Text = ConfigData.sqlName;
            }
            if (!string.IsNullOrEmpty(ConfigData.userName))
            {
                UserName.Text = ConfigData.userName;
            }
            if (!string.IsNullOrEmpty(ConfigData.passWord))
            {
                PassWord.Text = ConfigData.passWord;
            }
        }
    }
}
