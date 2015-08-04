namespace Inventory.Frm
{
    partial class frmCodeBind
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.MainMenu miOk;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.miOk = new System.Windows.Forms.MainMenu();
            this.miSave = new System.Windows.Forms.MenuItem();
            this.miExit = new System.Windows.Forms.MenuItem();
            this.panel1 = new System.Windows.Forms.Panel();
            this.btnClear = new System.Windows.Forms.Button();
            this.txtBindBaco = new System.Windows.Forms.TextBox();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtBindInfo = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.lvInfo = new System.Windows.Forms.ListView();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // miOk
            // 
            this.miOk.MenuItems.Add(this.miSave);
            this.miOk.MenuItems.Add(this.miExit);
            // 
            // miSave
            // 
            this.miSave.Text = "确定";
            this.miSave.Click += new System.EventHandler(this.miSave_Click);
            // 
            // miExit
            // 
            this.miExit.Text = "退出";
            this.miExit.Click += new System.EventHandler(this.miBack_Click);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.btnClear);
            this.panel1.Controls.Add(this.txtBindBaco);
            this.panel1.Controls.Add(this.lblGdBaco);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 26);
            // 
            // btnClear
            // 
            this.btnClear.Location = new System.Drawing.Point(187, 3);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(50, 20);
            this.btnClear.TabIndex = 93;
            this.btnClear.Text = "清空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtBindBaco
            // 
            this.txtBindBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBindBaco.Location = new System.Drawing.Point(36, 2);
            this.txtBindBaco.Name = "txtBindBaco";
            this.txtBindBaco.Size = new System.Drawing.Size(150, 21);
            this.txtBindBaco.TabIndex = 91;
            this.txtBindBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBindBaco_KeyUp);
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdBaco.Location = new System.Drawing.Point(3, 5);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(58, 20);
            this.lblGdBaco.Text = "条码";
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.txtBindInfo);
            this.panel2.Controls.Add(this.label1);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 240);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 28);
            // 
            // txtBindInfo
            // 
            this.txtBindInfo.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBindInfo.Location = new System.Drawing.Point(36, 3);
            this.txtBindInfo.Name = "txtBindInfo";
            this.txtBindInfo.Size = new System.Drawing.Size(201, 21);
            this.txtBindInfo.TabIndex = 93;
            this.txtBindInfo.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBindInfo_KeyUp);
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.Location = new System.Drawing.Point(3, 6);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(58, 20);
            this.label1.Text = "信息";
            // 
            // lvInfo
            // 
            this.lvInfo.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvInfo.Location = new System.Drawing.Point(0, 26);
            this.lvInfo.Name = "lvInfo";
            this.lvInfo.Size = new System.Drawing.Size(240, 214);
            this.lvInfo.TabIndex = 31;
            // 
            // frmCodeBind
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.lvInfo);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.Menu = this.miOk;
            this.Name = "frmCodeBind";
            this.Text = "条码绑定";
            this.panel1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem miSave;
        private System.Windows.Forms.MenuItem miExit;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox txtBindBaco;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtBindInfo;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.ListView lvInfo;
    }
}