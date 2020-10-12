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
    public partial class FindAndReplace : Form
    {
        public static MainFrom form;
        public FindAndReplace(MainFrom f)
        {
            form = f;
            this.TopMost = true;
            InitializeComponent();
        }
        //public FindAndReplace()
        //{

        //}

        /// <summary>
        /// 查找内容改变
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void findContext_TextChanged(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(findContext.Text.Trim()))
            {
                findNext.Enabled = false;
                replaceAll.Enabled = false;
                replaceOne.Enabled = false;
            }
            else
            {
                findNext.Enabled = true;
                replaceAll.Enabled = true;
                replaceOne.Enabled = true;
            }
        }

        private void findNext_Click(object sender, EventArgs e)
        {
            Action<string> act = new Action<string>(findTextNext);
            act.Invoke(findContext.Text.Trim());
        }
        private void replaceOne_Click(object sender, EventArgs e)
        {
            Action<string, string> act = new Action<string, string>(replaceOneText);
            act.Invoke(findContext.Text.Trim(), replaceContext.Text);
        }

        private void replaceAll_Click(object sender, EventArgs e)
        {
            Action<string, string> act = new Action<string, string>(replaceALLText);
            act.Invoke(findContext.Text.Trim(), replaceContext.Text);            
        }
        #region 委托
        void findTextNext(string findtext)
        {
            if (!form.findNext(findtext))
            {
                this.TopMost = false;
                MessageBox.Show("未查找到");
                this.TopMost = true;
            }
        }

        void replaceOneText(string findtext, string repalceText)
        {
            if (!form.replaceOne(findtext, repalceText))
            {
                this.TopMost = false;
                MessageBox.Show("没有可替换");
                this.TopMost = true;
            }
                
        }
        void replaceALLText(string findtext, string repalceText)
        {
            form.replaceAll(findtext, repalceText);
        }

        #endregion

        




    }
}
