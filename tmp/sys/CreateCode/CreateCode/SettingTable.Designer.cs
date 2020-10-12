namespace CreateCode
{
    partial class SettingTable
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
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.checkedNotAll = new System.Windows.Forms.LinkLabel();
            this.checkedAll = new System.Windows.Forms.LinkLabel();
            this.BtClose = new System.Windows.Forms.Button();
            this.BtOk = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.flowLayoutPanel1);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.checkedNotAll);
            this.splitContainer1.Panel2.Controls.Add(this.checkedAll);
            this.splitContainer1.Panel2.Controls.Add(this.BtClose);
            this.splitContainer1.Panel2.Controls.Add(this.BtOk);
            this.splitContainer1.Size = new System.Drawing.Size(484, 261);
            this.splitContainer1.SplitterDistance = 215;
            this.splitContainer1.TabIndex = 0;
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.AutoScroll = true;
            this.flowLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(484, 215);
            this.flowLayoutPanel1.TabIndex = 0;
            // 
            // checkedNotAll
            // 
            this.checkedNotAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.checkedNotAll.AutoSize = true;
            this.checkedNotAll.LinkBehavior = System.Windows.Forms.LinkBehavior.HoverUnderline;
            this.checkedNotAll.Location = new System.Drawing.Point(263, 21);
            this.checkedNotAll.Name = "checkedNotAll";
            this.checkedNotAll.Size = new System.Drawing.Size(41, 12);
            this.checkedNotAll.TabIndex = 3;
            this.checkedNotAll.TabStop = true;
            this.checkedNotAll.Text = "全不选";
            this.checkedNotAll.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.checkedNotAll_LinkClicked);
            // 
            // checkedAll
            // 
            this.checkedAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.checkedAll.AutoSize = true;
            this.checkedAll.LinkBehavior = System.Windows.Forms.LinkBehavior.HoverUnderline;
            this.checkedAll.Location = new System.Drawing.Point(228, 21);
            this.checkedAll.Name = "checkedAll";
            this.checkedAll.Size = new System.Drawing.Size(29, 12);
            this.checkedAll.TabIndex = 2;
            this.checkedAll.TabStop = true;
            this.checkedAll.Text = "全选";
            this.checkedAll.LinkClicked += new System.Windows.Forms.LinkLabelLinkClickedEventHandler(this.checkedAll_LinkClicked);
            // 
            // BtClose
            // 
            this.BtClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.BtClose.Location = new System.Drawing.Point(406, 4);
            this.BtClose.Name = "BtClose";
            this.BtClose.Size = new System.Drawing.Size(75, 35);
            this.BtClose.TabIndex = 1;
            this.BtClose.Text = "取消";
            this.BtClose.UseVisualStyleBackColor = true;
            this.BtClose.Click += new System.EventHandler(this.BtClose_Click);
            // 
            // BtOk
            // 
            this.BtOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.BtOk.Location = new System.Drawing.Point(323, 4);
            this.BtOk.Name = "BtOk";
            this.BtOk.Size = new System.Drawing.Size(77, 35);
            this.BtOk.TabIndex = 0;
            this.BtOk.Text = "确定";
            this.BtOk.UseVisualStyleBackColor = true;
            this.BtOk.Click += new System.EventHandler(this.BtOk_Click);
            // 
            // SettingTable
            // 
            this.AcceptButton = this.BtOk;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(484, 261);
            this.Controls.Add(this.splitContainer1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.Name = "SettingTable";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "配置需要生成文件的表名";
            this.Load += new System.EventHandler(this.SettingTable_Load);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.Panel2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private System.Windows.Forms.Button BtClose;
        private System.Windows.Forms.Button BtOk;
        private System.Windows.Forms.LinkLabel checkedNotAll;
        private System.Windows.Forms.LinkLabel checkedAll;

    }
}