namespace CreateCode
{
    partial class FindAndReplace
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
            this.findContext = new System.Windows.Forms.TextBox();
            this.replaceContext = new System.Windows.Forms.TextBox();
            this.findNext = new System.Windows.Forms.Button();
            this.replaceOne = new System.Windows.Forms.Button();
            this.replaceAll = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(8, 15);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(53, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "查找内容";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(8, 42);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 12);
            this.label2.TabIndex = 1;
            this.label2.Text = "替换内容";
            // 
            // findContext
            // 
            this.findContext.Location = new System.Drawing.Point(67, 12);
            this.findContext.Name = "findContext";
            this.findContext.Size = new System.Drawing.Size(292, 21);
            this.findContext.TabIndex = 0;
            this.findContext.TextChanged += new System.EventHandler(this.findContext_TextChanged);
            // 
            // replaceContext
            // 
            this.replaceContext.Location = new System.Drawing.Point(67, 39);
            this.replaceContext.Name = "replaceContext";
            this.replaceContext.Size = new System.Drawing.Size(292, 21);
            this.replaceContext.TabIndex = 1;
            // 
            // findNext
            // 
            this.findNext.Enabled = false;
            this.findNext.Location = new System.Drawing.Point(258, 66);
            this.findNext.Name = "findNext";
            this.findNext.Size = new System.Drawing.Size(101, 33);
            this.findNext.TabIndex = 2;
            this.findNext.Text = "查找";
            this.findNext.UseVisualStyleBackColor = true;
            this.findNext.Click += new System.EventHandler(this.findNext_Click);
            // 
            // replaceOne
            // 
            this.replaceOne.Enabled = false;
            this.replaceOne.Location = new System.Drawing.Point(140, 108);
            this.replaceOne.Name = "replaceOne";
            this.replaceOne.Size = new System.Drawing.Size(101, 33);
            this.replaceOne.TabIndex = 4;
            this.replaceOne.Text = "替换";
            this.replaceOne.UseVisualStyleBackColor = true;
            this.replaceOne.Click += new System.EventHandler(this.replaceOne_Click);
            // 
            // replaceAll
            // 
            this.replaceAll.Enabled = false;
            this.replaceAll.Location = new System.Drawing.Point(258, 108);
            this.replaceAll.Name = "replaceAll";
            this.replaceAll.Size = new System.Drawing.Size(101, 33);
            this.replaceAll.TabIndex = 5;
            this.replaceAll.Text = "替换全部";
            this.replaceAll.UseVisualStyleBackColor = true;
            this.replaceAll.Click += new System.EventHandler(this.replaceAll_Click);
            // 
            // FindAndReplace
            // 
            this.AcceptButton = this.findNext;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(371, 153);
            this.Controls.Add(this.replaceAll);
            this.Controls.Add(this.replaceOne);
            this.Controls.Add(this.findNext);
            this.Controls.Add(this.replaceContext);
            this.Controls.Add(this.findContext);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FindAndReplace";
            this.Text = "查找或替换";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox findContext;
        private System.Windows.Forms.TextBox replaceContext;
        private System.Windows.Forms.Button findNext;
        private System.Windows.Forms.Button replaceOne;
        private System.Windows.Forms.Button replaceAll;
    }
}