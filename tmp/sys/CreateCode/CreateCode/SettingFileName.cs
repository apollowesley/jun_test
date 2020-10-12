using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace CreateCode
{
    public partial class SettingFileName : Form
    {
        public SettingFileName()
        {
            InitializeComponent();
        }

        private void SettingFileName_Load(object sender, EventArgs e)
        {
            BindData();
        }

        private void BindData()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("文件名");
            dt.Columns.Add("生成的文件名");
            foreach (var item in ConfigData.getFileNameConfig())
            {
                DataRow dr = dt.NewRow();
                dr[0] = item.Key;
                dr[1] = item.Value;
                dt.Rows.Add(dr);
            }
            dataGridView1.DataSource = dt;
        }

        private void btOK_Click(object sender, EventArgs e)
        {
            DataTable dt = new DataTable();
            // 列强制转换
            for (int count = 0; count < dataGridView1.Columns.Count; count++)
            {
                DataColumn dc = new DataColumn(dataGridView1.Columns[count].Name.ToString());
                dt.Columns.Add(dc);
            }

            // 循环行
            for (int count = 0; count < dataGridView1.Rows.Count-1; count++)
            {
                DataRow dr = dt.NewRow();
                for (int countsub = 0; countsub < dataGridView1.Columns.Count; countsub++)
                {
                    dr[countsub] = Convert.ToString(dataGridView1.Rows[count].Cells[countsub].Value);
                }
                dt.Rows.Add(dr);
            }
            Dictionary<string, string> dic = new Dictionary<string, string>();
            foreach (DataRow dr in dt.Rows)
            {
                dic.Add(dr[0].ToString(), dr[1].ToString());
            }
            ConfigData.setFileNameConfig(dic) ;
            
            SettingData.WriteIniData();
            
            this.Close();
        }

        private void btColse_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
