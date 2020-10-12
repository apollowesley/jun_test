using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic;

namespace CreateCode
{
    public partial class MainFrom : Form
    {
        public MainFrom()
        {
            InitializeComponent();
        }
        /// <summary>
        /// 查找窗体
        /// </summary>
        FindAndReplace dar = null;

        /// <summary>
        /// 测试按钮
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void reflect_Click(object sender, EventArgs e)
        {
            //Type testType = typeof(ConfigData);
            //object obj = Activator.CreateInstance(testType);
            //Assembly assembly = testType.Assembly;
            //FieldInfo[] fi = testType.GetFields();
            //MessageBox.Show(fi[0].Name);
            //fi[0].SetValue(obj, "sss");
            //MessageBox.Show(ConfigData.sqlAddress);
            //TabPage tp = (TabPage)DynamicControlHelper.getControl(myTabControl.SelectedTab.Name);
            //tp.Text = "aaa";
            //findNext("666");
            MessageBox.Show("有啥好测试得哟");
        }

        #region 初始化

        /// <summary>
        /// 加载
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MainFrom_Load(object sender, EventArgs e)
        {
            List<string> slist = ConfigData.getOpenFile();
            foreach (string item in slist)
            {
                if (File.Exists(item))//判断文件是否存在
                {
                    createTPadnRTB(item);
                }
            }
            if (!string.IsNullOrEmpty(ConfigData.selectFile))
            {
                DynamicControlHelper dch = DynamicControlHelper.DCHList.Where(p => p.filePath == ConfigData.selectFile).FirstOrDefault();
                if (dch != null)
                {
                    myTabControl.SelectedTab = (TabPage)dch.control.Parent;
                }
            }

        }

        #endregion

        #region 配置功能


        /// <summary>
        /// 配置数据库连接
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void linkSQL_Click(object sender, EventArgs e)
        {
            LinkSQLFrom lsq = new LinkSQLFrom();
            lsq.ShowDialog();
        }

        /// <summary>
        /// 配置要生成的表
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void settingDatatable_Click(object sender, EventArgs e)
        {
            SettingTable st = new SettingTable();
            st.ShowDialog();
        }

        /// <summary>
        /// 配置要生成的文件名
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void settingFileName_Click(object sender, EventArgs e)
        {
            SettingFileName sf = new SettingFileName();
            sf.ShowDialog();
        }


        #endregion

        #region 生成


        /// <summary>
        /// 生成
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Create_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(ConfigData.sqlAddress) || string.IsNullOrEmpty(ConfigData.sqlName) || string.IsNullOrEmpty(ConfigData.userName) || string.IsNullOrEmpty(ConfigData.passWord))
            {//判断数据库连接
                MessageBox.Show("没有配置数据库连接,请先配置数据库");
                LinkSQLFrom lf = new LinkSQLFrom();
                lf.Show();
                return;
            }
            DataTable dt = NeedTable.dt;//获取要生成的数据库表名
            CreateFile(dt);
        }

        /// <summary>
        /// 创建文件
        /// </summary>
        /// <param name="dt"></param>
        private void CreateFile(DataTable dt)
        {
            if (myTabControl.TabPages.Count == 0 || dt.Rows.Count == 0)
            {
                MessageBox.Show("没有要生成的文件");
                return;
            }
            //打开的tabpage都会创建文件
            foreach (TabPage tp in myTabControl.TabPages)
            {
                string path = Directory.GetCurrentDirectory() + @"\TempFile\" + tp.Text.Split('.')[0];
                if (!Directory.Exists(path))//判断文件夹是否存在
                {
                    Directory.CreateDirectory(path);//创建文件夹
                }

                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    RichTextBox rtb = getRTB(tp);
                    string className = formatName(dt.Rows[i][0].ToString());
                    string filePath = path + @"\" + formatFileName(tp.Text.Split('.')[0], className);
                    if (File.Exists(filePath) && !ConfigData.isCover)//文件存在且不覆盖文件
                    {
                        continue;
                    }
                    string writeStr = rtb.Text.Replace("@table", className);
                    FileStream fs = new FileStream(filePath, FileMode.OpenOrCreate, FileAccess.ReadWrite); //可以指定盘符，也可以指定任意文件名，还可以为word等文件
                    StreamWriter sw = new StreamWriter(fs); // 创建写入流
                    sw.WriteLine(writeStr); // 写入
                    sw.Close(); //关闭文件
                    fs.Close();
                }
            }
            MessageBox.Show("生成成功");
            //打开文件夹
            System.Diagnostics.Process.Start("Explorer.exe", Directory.GetCurrentDirectory() + @"\TempFile");
        }

        /// <summary>
        /// 格式化类名
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        private string formatName(string name)
        {
            string result = "";
            try
            {
                string[] array = name.Split('_');
                if (array.Length > 1)//判断文件是否是以下划线命名规则
                {
                    for (int i = 1; i < array.Length; i++)
                    {
                        //首字母大小
                        if (!string.IsNullOrEmpty(array[i]))
                        {
                            result += array[i].Substring(0, 1).ToUpper() + array[i].Substring(1);
                        }
                    }
                }
                else
                {
                    result = name.Substring(0, 1).ToUpper() + array[0].Substring(1);
                }
            }
            catch
            {
                result = name;
            }
            return result;
        }

        /// <summary>
        /// 格式化文件名
        /// </summary>
        /// <param name="name">文件夹名</param>
        /// <param name="className">类名</param>
        /// <returns></returns>
        private string formatFileName(string name, string className)
        {
            if (ConfigData.getFileNameConfig().ContainsKey(name))//判断是否有配置文件
            {
                return ConfigData.getFileNameConfig()[name].Replace("@table", className);
            }

            switch (name.ToUpper())//默认
            {
                case "IDAL":
                    return "I" + className + "DAL.cs";
                case "DAL":
                    return className + "DAL.cs";
                case "IBLL":
                    return "I" + className + "BLL.cs";
                case "BLL":
                    return className + "BLL.cs";
                default:
                    return className+".txt";
            }
        }
        #endregion

        #region 关闭程序与判断是否保存


        /// <summary>
        /// 关闭程序
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MainFrom_FormClosing(object sender, FormClosingEventArgs e)
        {
            List<string> slist = new List<string>();
            foreach (TabPage tp in myTabControl.TabPages)//保存页面打开的路径
            {
                RichTextBox rtb = tp.Controls[0] as RichTextBox;
                slist.Add(DynamicControlHelper.DCHList.Where(p => p.control == rtb).FirstOrDefault().filePath);
            }
            ConfigData.setOpenFile(slist);
            if (slist.Count > 0)
                ConfigData.selectFile = DynamicControlHelper.DCHList.Where(p => p.control == getRTB()).FirstOrDefault().filePath;
            SettingData.WriteIniData();
            foreach (TabPage tp in myTabControl.TabPages)
            {
                if (!isSvae(tp))//判断是否已经保存
                {
                    DialogResult dr = MessageBox.Show("你有未保存页面，是否返回保存", "是否保存", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1);
                    if (dr == DialogResult.Yes || dr == DialogResult.Cancel)
                    {
                        e.Cancel = true;//取消关闭事件
                    }
                    return;
                }
            }
        }

        /// <summary>
        /// 判断页面是否保存
        /// </summary>
        /// <param name="tp"></param>
        /// <returns>true 保存</returns>
        private bool isSvae(TabPage tp)
        {
            RichTextBox rtb = tp.Controls[0] as RichTextBox;
            DynamicControlHelper dch = DynamicControlHelper.DCHList.Where(p => p.control == rtb).FirstOrDefault();
            if (dch != null && dch.isSave)
            {
                return true;
            }
            return false;
        }
        #endregion

        #region 文件菜单功能
        /// <summary>
        /// 创建新模板
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void createTemplate_Click(object sender, EventArgs e)
        {
            createTPadnRTB();
        }

        /// <summary>
        /// 打开模板
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void openTemplate_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog2 = new OpenFileDialog();
            openFileDialog2.InitialDirectory = System.IO.Directory.GetCurrentDirectory() + @"\TempFile"; ;
            openFileDialog2.Filter = "rtf文件(*.rtf)|*.rtf";
            if (openFileDialog2.ShowDialog() == DialogResult.OK)
            {
                createTPadnRTB(openFileDialog2.FileName);
            }
        }
        /// <summary>
        /// 保存模板
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void preserveTemplate_Click(object sender, EventArgs e)
        {
            Control tp = (Control)getRTB();
            if (DynamicControlHelper.isHavePath(tp))
            {
                string fileName = DynamicControlHelper.DCHList.Where(p => p.control == tp).FirstOrDefault().filePath;
                savePreserve(2, fileName);
                return;
            }
            savePreserve(2);
        }

        /// <summary>
        /// 另存为模板
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveTemplate_Click(object sender, EventArgs e)
        {
            savePreserve(1);
        }

        /// <summary>
        /// 添加控件TabPage 和 RichTextBox
        /// </summary>
        /// <param name="fileName"> 文件路径</param>
        private void createTPadnRTB(params string[] fileName)
        {
            //判断文件是否已经打开
            if (fileName.Length > 0 && DynamicControlHelper.isHavePath(fileName[0]))
            {
                myTabControl.SelectedTab = (TabPage)DynamicControlHelper.DCHList.Where(p => p.filePath == fileName[0]).FirstOrDefault().control.Parent;
                return;
            }
            Random r = new Random();
            TabPage tp = new TabPage();
            tp.Name = DynamicControlHelper.createId("tabPage");
            if (fileName.Length == 0)
                tp.Text = "new Template *";
            else
                tp.Text = Path.GetFileName(fileName[0]);
            //添加右击菜单
            tp.ContextMenuStrip = contextMenuStrip1;
            myTabControl.Controls.Add(tp);
            DynamicControlHelper tpControl = new DynamicControlHelper { control = tp, id = tp.Name };
            DynamicControlHelper.DCHList.Add(tpControl);
            RichTextBox rtb = new RichTextBox();
            rtb.Name = DynamicControlHelper.createId("richTextBox");
            if (fileName.Length > 0)
                rtb.LoadFile(fileName[0]);
            rtb.Dock = DockStyle.Fill;
            //添加事件
            rtb.KeyDown += rtb_KeyDown;
            rtb.TextChanged += rtb_TextChanged;
            tp.Controls.Add(rtb);
            DynamicControlHelper rtbControl;
            if (fileName.Length == 0)
                rtbControl = new DynamicControlHelper { control = rtb, id = rtb.Name };
            else
                rtbControl = new DynamicControlHelper { control = rtb, id = rtb.Name, filePath = fileName[0], isSave = true };
            DynamicControlHelper.DCHList.Add(rtbControl);
            myTabControl.SelectedTab = tp;
        }

        /// <summary>
        ///  保存或另存为
        /// </summary>
        /// <param name="state">1表示另存为 2表示保存</param>
        /// <param name="fileName">存在表示非第一次保存</param>
        private void savePreserve(int state, params string[] fileName)
        {

            RichTextBox rtb = getRTB();
            if (fileName.Length == 0)//没有文件路径表示第一次保存或另存为
            {
                string path = Directory.GetCurrentDirectory() + @"\TempFile";
                if (!Directory.Exists(path))
                {
                    Directory.CreateDirectory(path);
                }

                Stream myStream;
                SaveFileDialog saveFileDialog1 = new SaveFileDialog();
                saveFileDialog1.Filter = "rtf文件(*.rtf)|*.rtf";
                saveFileDialog1.InitialDirectory = path;
                saveFileDialog1.FilterIndex = 2;
                saveFileDialog1.RestoreDirectory = true;
                if (saveFileDialog1.ShowDialog() == DialogResult.OK)
                {
                    if ((myStream = saveFileDialog1.OpenFile()) != null)
                    {
                        using (StreamWriter sw = new StreamWriter(myStream))
                        {
                            sw.Write(rtb.Rtf);
                        }
                        myStream.Close();
                        //MessageBox.Show("保存成功");
                        if (state == 2)//表示第一次保存
                        {
                            //修改参数
                            DynamicControlHelper dch = DynamicControlHelper.DCHList.Where(p => p.control == rtb).FirstOrDefault();
                            if (dch != null)
                            {
                                dch.isSave = true;
                                dch.filePath = saveFileDialog1.FileName;
                                DynamicControlHelper.update(dch);
                                rtb.Parent.Text = Path.GetFileName(saveFileDialog1.FileName);
                            }
                        }
                    }
                }
            }
            else
            {

                FileStream fs = new FileStream(fileName[0], FileMode.OpenOrCreate, FileAccess.ReadWrite);
                StreamWriter sw = new StreamWriter(fs); // 创建写入流
                sw.Write(rtb.Rtf); // 写入
                sw.Close(); //关闭文件
                fs.Close();
                //MessageBox.Show("保存成功");
                //修改参数
                DynamicControlHelper dch = DynamicControlHelper.DCHList.Where(p => p.control == rtb).FirstOrDefault();
                if (dch != null)
                {
                    dch.isSave = true;
                    DynamicControlHelper.update(dch);
                    rtb.Parent.Text = rtb.Parent.Text.Replace(" *", "");
                }
            }

        }
        /// <summary>
        /// 获取RichTextBox控件
        /// </summary>
        /// <returns></returns>
        private RichTextBox getRTB()
        {
            if (myTabControl.SelectedTab == null)
            {
                throw (new Exception("没有tab控件"));
            }
            TabPage tp = myTabControl.SelectedTab;
            RichTextBox rtb = new RichTextBox();
            foreach (Control c in tp.Controls)
            {
                if (!DynamicControlHelper.isSoleId(c.Name))
                {
                    rtb = c as RichTextBox;
                    break;
                }
            }

            return rtb;
        }

        /// <summary>
        /// 获取RichTextBox控件
        /// </summary>
        /// <returns></returns>
        private RichTextBox getRTB(TabPage tp)
        {
            RichTextBox rtb = new RichTextBox();
            foreach (Control c in tp.Controls)
            {
                if (!DynamicControlHelper.isSoleId(c.Name))
                {
                    rtb = c as RichTextBox;
                    break;
                }
            }

            return rtb;
        }
        #endregion

        #region 事件
        /// <summary>
        /// RichTextBox控件按键事件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void rtb_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.Modifiers == Keys.Control)
            {
                switch (e.KeyCode)
                {
                    case Keys.S://cril+s
                        preserveTemplate.PerformClick();//保存事件
                        break;
                    case Keys.F://cril+f 查找或替换
                        if (dar == null || dar.IsDisposed)//判断窗体是否关闭
                        {
                            dar = new FindAndReplace(this);
                            dar.Show();
                        }
                        else
                            dar.Activate();
                        break;
                }

            }


        }

        /// <summary>
        /// RichTextBox文字改变事件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void rtb_TextChanged(object sender, EventArgs e)
        {
            Control con = (Control)sender;
            DynamicControlHelper dch = DynamicControlHelper.DCHList.Where(p => p.control == con).FirstOrDefault();
            if (dch != null)
            {
                dch.isSave = false;
                DynamicControlHelper.update(dch);
                con.Parent.Text = con.Parent.Text.Replace(" *", "") + " *";
            }
        }


        /// <summary>
        ///  myTabControl右击选项卡
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void myTabControl_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)//右击选项选中tab
            {
                for (int i = 0; i < myTabControl.TabPages.Count; i++)
                {
                    if (myTabControl.GetTabRect(i).Contains(new Point(e.X, e.Y)))
                    {
                        myTabControl.SelectedTab = myTabControl.TabPages[i];
                        break;
                    }
                }
            }
        }
        #endregion

        #region 委托
        private int lastIndex = 0;

        /// <summary>
        /// 查找
        /// </summary>
        /// <param name="findText"></param>
        public bool findNext(string findText)
        {
            RichTextBox rtb = getRTB();
            lastIndex = rtb.Find(findText, lastIndex < 0 ? 0 : lastIndex, rtb.Text.Length, RichTextBoxFinds.None);
            this.Activate();
            if (lastIndex == -1)//表示查找到底
            {
                lastIndex = rtb.Find(findText, 0, rtb.Text.Length, RichTextBoxFinds.None);
                if (lastIndex == -1)//没有查到
                {
                    return false;
                }
            }
            lastIndex++;//也便于查找下一个
            return true;
        }

        /// <summary>
        /// 替换
        /// </summary>
        /// <param name="findText"></param>
        /// <param name="replaceText"></param>
        /// <returns></returns>
        public bool replaceOne(string findText, string replaceText)
        {
            lastIndex--;//先减在查询是否有值，在减表示位置
            if (!findNext(findText))
            {
                return false;
            }
            lastIndex--;
            if (lastIndex >= 0)
            {
                RichTextBox rtb = getRTB();
                this.Activate();
                rtb.Select(lastIndex, findText.Length);
                rtb.SelectedText = replaceText;
                findNext(findText);//有+ 替换就要 -
                lastIndex--;
                return true;
            }
            return false;
        }

        /// <summary>
        /// 替换全部
        /// </summary>
        /// <param name="findText"></param>
        /// <param name="replaceText"></param>
        /// <returns></returns>
        public bool replaceAll(string findText, string replaceText)
        {
            RichTextBox rtb = getRTB();
            //rtb.Text = rtb.Text.Replace(findText, replaceText);
            //不区分大小写替换
            rtb.Text = Strings.Replace(rtb.Text, findText, replaceText, 1, -1, CompareMethod.Text);
            return true;
        }
        #endregion

        #region 右击关闭菜单

        /// <summary>
        /// 关闭当前
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void closeIndex_Click(object sender, EventArgs e)
        {
            TabPage tp = myTabControl.SelectedTab;
            if (!isSvae(tp))
            {
                DialogResult dr = MessageBox.Show("有未保存页面，是否返回保存", "是否保存", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1);
                if (dr == DialogResult.Yes || dr == DialogResult.Cancel)
                {
                    return;
                }
            }
            //先从数值中删除TabPage下面的RichTextBox，在删除TabPage
            DynamicControlHelper.reomve(tp.Controls[0]);
            DynamicControlHelper.reomve(tp);
            myTabControl.TabPages.Remove(tp);
        }

        /// <summary>
        /// 关闭所有
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void closeAll_Click(object sender, EventArgs e)
        {
            foreach (TabPage tp in myTabControl.TabPages)
            {
                if (!isSvae(tp))
                {
                    DialogResult dr = MessageBox.Show("你有未保存页面，是否返回保存", "是否保存", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1);
                    if (dr == DialogResult.Yes || dr == DialogResult.Cancel)
                    {
                        return;
                    }
                    break;
                }
            }
            foreach (TabPage tp in myTabControl.TabPages)
            {
                DynamicControlHelper.reomve(tp.Controls[0]);
                DynamicControlHelper.reomve(tp);
                myTabControl.TabPages.Remove(tp);
            }
        }

        /// <summary>
        /// 关闭其他
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void closeOther_Click(object sender, EventArgs e)
        {
            TabPage indexTp = myTabControl.SelectedTab;
            foreach (TabPage tp in myTabControl.TabPages)
            {
                if (tp != indexTp && !isSvae(tp))
                {
                    DialogResult dr = MessageBox.Show("你有未保存页面，是否返回保存", "是否保存", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1);
                    if (dr == DialogResult.Yes || dr == DialogResult.Cancel)
                    {
                        return;
                    }
                    break;
                }
            }
            foreach (TabPage tp in myTabControl.TabPages)
            {
                if (tp != indexTp)
                {
                    DynamicControlHelper.reomve(tp.Controls[0]);
                    DynamicControlHelper.reomve(tp);
                    myTabControl.TabPages.Remove(tp);
                }
            }
        }
        #endregion

        #region 帮助
        /// <summary>
        /// 使用说明
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void useExplain_Click(object sender, EventArgs e)
        {
            MessageBox.Show(
                "生成是对当前所有打开文件都有效的\r\n" +
                "1.使用@table替换类名，可以在文件和配置中使用\r\n" +
                "2.文件名末尾有 * 表示未保存\r\n" +
                "3.快捷键\r\n" +
                "   Ctrl+S:保存\r\n" +
                "   Ctrl+F:查询或替换\r\n" +
                "配置生成文件名\r\n"+
                "   文件名 为当前打开文件的名字(不含扩展名)\r\n"+
                "   生成的文件名 使用@table为类名,自定义文件名,需要包含扩展名\r\n",
                "使用说明", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
        }
        #endregion
        
    }
}
