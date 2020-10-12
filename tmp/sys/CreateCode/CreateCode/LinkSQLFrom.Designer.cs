﻿namespace CreateCode
{
    partial class LinkSQLFrom
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
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.SqlAddress = new System.Windows.Forms.TextBox();
            this.SqlName = new System.Windows.Forms.TextBox();
            this.btnConfirm = new System.Windows.Forms.Button();
            this.UserName = new System.Windows.Forms.TextBox();
            this.PassWord = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(25, 19);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "数据库地址";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(37, 46);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 12);
            this.label2.TabIndex = 1;
            this.label2.Text = "数据库名";
            // 
            // SqlAddress
            // 
            this.SqlAddress.Location = new System.Drawing.Point(96, 16);
            this.SqlAddress.Name = "SqlAddress";
            this.SqlAddress.Size = new System.Drawing.Size(330, 21);
            this.SqlAddress.TabIndex = 1;
            // 
            // SqlName
            // 
            this.SqlName.Location = new System.Drawing.Point(96, 43);
            this.SqlName.Name = "SqlName";
            this.SqlName.Size = new System.Drawing.Size(330, 21);
            this.SqlName.TabIndex = 2;
            // 
            // btnConfirm
            // 
            this.btnConfirm.Location = new System.Drawing.Point(351, 123);
            this.btnConfirm.Name = "btnConfirm";
            this.btnConfirm.Size = new System.Drawing.Size(75, 30);
            this.btnConfirm.TabIndex = 6;
            this.btnConfirm.Text = "确定";
            this.btnConfirm.UseVisualStyleBackColor = true;
            this.btnConfirm.Click += new System.EventHandler(this.btnConfirm_Click);
            // 
            // UserName
            // 
            this.UserName.Location = new System.Drawing.Point(96, 69);
            this.UserName.Name = "UserName";
            this.UserName.Size = new System.Drawing.Size(330, 21);
            this.UserName.TabIndex = 3;
            // 
            // PassWord
            // 
            this.PassWord.Location = new System.Drawing.Point(96, 96);
            this.PassWord.Name = "PassWord";
            this.PassWord.Size = new System.Drawing.Size(330, 21);
            this.PassWord.TabIndex = 4;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(49, 72);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(41, 12);
            this.label3.TabIndex = 7;
            this.label3.Text = "登录名";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(61, 99);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(29, 12);
            this.label4.TabIndex = 8;
            this.label4.Text = "密码";
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(260, 123);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 30);
            this.button1.TabIndex = 5;
            this.button1.Text = "测试链接";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // LinkSQLFrom
            // 
            this.AcceptButton = this.btnConfirm;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(438, 165);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.PassWord);
            this.Controls.Add(this.UserName);
            this.Controls.Add(this.btnConfirm);
            this.Controls.Add(this.SqlName);
            this.Controls.Add(this.SqlAddress);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "LinkSQLFrom";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "配置数据库";
            this.Load += new System.EventHandler(this.LinkSQLFrom_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox SqlAddress;
        private System.Windows.Forms.TextBox SqlName;
        private System.Windows.Forms.Button btnConfirm;
        private System.Windows.Forms.TextBox UserName;
        private System.Windows.Forms.TextBox PassWord;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button button1;
    }
}