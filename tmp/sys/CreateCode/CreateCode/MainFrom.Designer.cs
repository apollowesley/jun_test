namespace CreateCode
{
    partial class MainFrom
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainFrom));
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.文件ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.createTemplate = new System.Windows.Forms.ToolStripMenuItem();
            this.openTemplate = new System.Windows.Forms.ToolStripMenuItem();
            this.preserveTemplate = new System.Windows.Forms.ToolStripMenuItem();
            this.saveTemplate = new System.Windows.Forms.ToolStripMenuItem();
            this.生成ToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.Create = new System.Windows.Forms.ToolStripMenuItem();
            this.设置ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.linkSQL = new System.Windows.Forms.ToolStripMenuItem();
            this.settingDatatable = new System.Windows.Forms.ToolStripMenuItem();
            this.settingFileName = new System.Windows.Forms.ToolStripMenuItem();
            this.测试按钮ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.reflect = new System.Windows.Forms.ToolStripMenuItem();
            this.帮助ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.useExplain = new System.Windows.Forms.ToolStripMenuItem();
            this.myTabControl = new System.Windows.Forms.TabControl();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.closeIndex = new System.Windows.Forms.ToolStripMenuItem();
            this.closeAll = new System.Windows.Forms.ToolStripMenuItem();
            this.closeOther = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.contextMenuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.文件ToolStripMenuItem,
            this.生成ToolStripMenuItem1,
            this.设置ToolStripMenuItem,
            this.测试按钮ToolStripMenuItem,
            this.帮助ToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(854, 25);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // 文件ToolStripMenuItem
            // 
            this.文件ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.createTemplate,
            this.openTemplate,
            this.preserveTemplate,
            this.saveTemplate});
            this.文件ToolStripMenuItem.Name = "文件ToolStripMenuItem";
            this.文件ToolStripMenuItem.Size = new System.Drawing.Size(44, 21);
            this.文件ToolStripMenuItem.Text = "文件";
            // 
            // createTemplate
            // 
            this.createTemplate.Name = "createTemplate";
            this.createTemplate.Size = new System.Drawing.Size(112, 22);
            this.createTemplate.Text = "新建";
            this.createTemplate.Click += new System.EventHandler(this.createTemplate_Click);
            // 
            // openTemplate
            // 
            this.openTemplate.Name = "openTemplate";
            this.openTemplate.Size = new System.Drawing.Size(112, 22);
            this.openTemplate.Text = "打开";
            this.openTemplate.Click += new System.EventHandler(this.openTemplate_Click);
            // 
            // preserveTemplate
            // 
            this.preserveTemplate.Name = "preserveTemplate";
            this.preserveTemplate.Size = new System.Drawing.Size(112, 22);
            this.preserveTemplate.Text = "保存";
            this.preserveTemplate.Click += new System.EventHandler(this.preserveTemplate_Click);
            // 
            // saveTemplate
            // 
            this.saveTemplate.Name = "saveTemplate";
            this.saveTemplate.Size = new System.Drawing.Size(112, 22);
            this.saveTemplate.Text = "另存为";
            this.saveTemplate.Click += new System.EventHandler(this.saveTemplate_Click);
            // 
            // 生成ToolStripMenuItem1
            // 
            this.生成ToolStripMenuItem1.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.Create});
            this.生成ToolStripMenuItem1.Name = "生成ToolStripMenuItem1";
            this.生成ToolStripMenuItem1.Size = new System.Drawing.Size(44, 21);
            this.生成ToolStripMenuItem1.Text = "生成";
            // 
            // Create
            // 
            this.Create.Name = "Create";
            this.Create.Size = new System.Drawing.Size(100, 22);
            this.Create.Text = "生成";
            this.Create.Click += new System.EventHandler(this.Create_Click);
            // 
            // 设置ToolStripMenuItem
            // 
            this.设置ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.linkSQL,
            this.settingDatatable,
            this.settingFileName});
            this.设置ToolStripMenuItem.Name = "设置ToolStripMenuItem";
            this.设置ToolStripMenuItem.Size = new System.Drawing.Size(44, 21);
            this.设置ToolStripMenuItem.Text = "设置";
            // 
            // linkSQL
            // 
            this.linkSQL.Name = "linkSQL";
            this.linkSQL.Size = new System.Drawing.Size(160, 22);
            this.linkSQL.Text = "配置数据库";
            this.linkSQL.Click += new System.EventHandler(this.linkSQL_Click);
            // 
            // settingDatatable
            // 
            this.settingDatatable.Name = "settingDatatable";
            this.settingDatatable.Size = new System.Drawing.Size(160, 22);
            this.settingDatatable.Text = "配置要生成的表";
            this.settingDatatable.Click += new System.EventHandler(this.settingDatatable_Click);
            // 
            // settingFileName
            // 
            this.settingFileName.Name = "settingFileName";
            this.settingFileName.Size = new System.Drawing.Size(160, 22);
            this.settingFileName.Text = "配置生成文件名";
            this.settingFileName.Click += new System.EventHandler(this.settingFileName_Click);
            // 
            // 测试按钮ToolStripMenuItem
            // 
            this.测试按钮ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.reflect});
            this.测试按钮ToolStripMenuItem.Name = "测试按钮ToolStripMenuItem";
            this.测试按钮ToolStripMenuItem.Size = new System.Drawing.Size(68, 21);
            this.测试按钮ToolStripMenuItem.Text = "测试按钮";
            // 
            // reflect
            // 
            this.reflect.Name = "reflect";
            this.reflect.Size = new System.Drawing.Size(124, 22);
            this.reflect.Text = "反射类型";
            this.reflect.Click += new System.EventHandler(this.reflect_Click);
            // 
            // 帮助ToolStripMenuItem
            // 
            this.帮助ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.useExplain});
            this.帮助ToolStripMenuItem.Name = "帮助ToolStripMenuItem";
            this.帮助ToolStripMenuItem.Size = new System.Drawing.Size(44, 21);
            this.帮助ToolStripMenuItem.Text = "帮助";
            // 
            // useExplain
            // 
            this.useExplain.Name = "useExplain";
            this.useExplain.Size = new System.Drawing.Size(124, 22);
            this.useExplain.Text = "使用说明";
            this.useExplain.Click += new System.EventHandler(this.useExplain_Click);
            // 
            // myTabControl
            // 
            this.myTabControl.ContextMenuStrip = this.contextMenuStrip1;
            this.myTabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.myTabControl.Location = new System.Drawing.Point(0, 25);
            this.myTabControl.Name = "myTabControl";
            this.myTabControl.SelectedIndex = 0;
            this.myTabControl.Size = new System.Drawing.Size(854, 412);
            this.myTabControl.TabIndex = 1;
            this.myTabControl.MouseDown += new System.Windows.Forms.MouseEventHandler(this.myTabControl_MouseDown);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.closeIndex,
            this.closeAll,
            this.closeOther});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(125, 70);
            // 
            // closeIndex
            // 
            this.closeIndex.Name = "closeIndex";
            this.closeIndex.Size = new System.Drawing.Size(124, 22);
            this.closeIndex.Text = "关闭当前";
            this.closeIndex.Click += new System.EventHandler(this.closeIndex_Click);
            // 
            // closeAll
            // 
            this.closeAll.Name = "closeAll";
            this.closeAll.Size = new System.Drawing.Size(124, 22);
            this.closeAll.Text = "关闭所有";
            this.closeAll.Click += new System.EventHandler(this.closeAll_Click);
            // 
            // closeOther
            // 
            this.closeOther.Name = "closeOther";
            this.closeOther.Size = new System.Drawing.Size(124, 22);
            this.closeOther.Text = "关闭其他";
            this.closeOther.Click += new System.EventHandler(this.closeOther_Click);
            // 
            // MainFrom
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(854, 437);
            this.Controls.Add(this.myTabControl);
            this.Controls.Add(this.menuStrip1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "MainFrom";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "代码生成工具——By:谁有我低调 lhj0502@vip.qq.com";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainFrom_FormClosing);
            this.Load += new System.EventHandler(this.MainFrom_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.contextMenuStrip1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem 文件ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openTemplate;
        private System.Windows.Forms.ToolStripMenuItem saveTemplate;
        private System.Windows.Forms.ToolStripMenuItem 生成ToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem Create;
        private System.Windows.Forms.ToolStripMenuItem 设置ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem linkSQL;
        private System.Windows.Forms.ToolStripMenuItem settingDatatable;
        private System.Windows.Forms.ToolStripMenuItem 测试按钮ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem reflect;
        private System.Windows.Forms.ToolStripMenuItem createTemplate;
        private System.Windows.Forms.TabControl myTabControl;
        private System.Windows.Forms.ToolStripMenuItem preserveTemplate;
        private System.Windows.Forms.ToolStripMenuItem settingFileName;
        private System.Windows.Forms.ToolStripMenuItem 帮助ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem useExplain;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem closeIndex;
        private System.Windows.Forms.ToolStripMenuItem closeAll;
        private System.Windows.Forms.ToolStripMenuItem closeOther;
    }
}