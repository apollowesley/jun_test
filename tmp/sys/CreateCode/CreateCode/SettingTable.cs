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
    public partial class SettingTable : Form
    {
        public SettingTable()
        {
            InitializeComponent();
        }

        private void SettingTable_Load(object sender, EventArgs e)
        {
            createCheckBox();
        }

        private void createCheckBox()
        {
            DataTable dt = NeedTable.getDataBaseTable();
            DataTable checkedDt = NeedTable.dt;
            foreach (DataRow dr in dt.Rows)
            {
                CheckBox cb = new CheckBox();
                cb.Name = dr[0].ToString();
                cb.Text = dr[0].ToString();
                cb.Checked = isHave(dr[0].ToString(), checkedDt);
                flowLayoutPanel1.Controls.Add(cb);
            }
        }

        private bool isHave(string name, DataTable checkedDt)
        {

            foreach (DataRow dr in checkedDt.Rows)
            {
                if (dr[0].ToString() == name)
                {
                    return true;
                }
            }
            return false;
        }

        private void checkedAll_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            foreach (Control c in flowLayoutPanel1.Controls)
            {
                CheckBox cb = c as CheckBox;
                cb.Checked = true;
            }
        }

        private void checkedNotAll_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            foreach (Control c in flowLayoutPanel1.Controls)
            {
                CheckBox cb = c as CheckBox;
                cb.Checked = false;
            }
        }

        private void BtOk_Click(object sender, EventArgs e)
        {
            DataTable dt = new DataTable();
            dt.Columns.Add();
            foreach (Control c in flowLayoutPanel1.Controls)
            {
                CheckBox cb = c as CheckBox;
                if (cb.Checked)
                {
                    DataRow dr = dt.NewRow();
                    dr[0] = cb.Name;
                    dt.Rows.Add(dr);
                }
            }
            NeedTable.dt = dt;
            this.Close();
        }

        private void BtClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }


    }
}
