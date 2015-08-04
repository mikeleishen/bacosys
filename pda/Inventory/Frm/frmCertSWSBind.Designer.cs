namespace Inventory.Frm
{
    partial class frmCertSWSBind
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.MainMenu mainMenu1;

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
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miOK = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.panel1 = new System.Windows.Forms.Panel();
            this.txtSWSQty = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.btnClear = new System.Windows.Forms.Button();
            this.txtSWSId = new System.Windows.Forms.TextBox();
            this.lblItmQty = new System.Windows.Forms.Label();
            this.lblItmId = new System.Windows.Forms.Label();
            this.lblItmName = new System.Windows.Forms.Label();
            this.txtCertId = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.lvCertRe = new System.Windows.Forms.ListView();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.miOK);
            this.mainMenu1.MenuItems.Add(this.miBack);
            // 
            // miOK
            // 
            this.miOK.Text = "确定";
            this.miOK.Click += new System.EventHandler(this.miOK_Click);
            // 
            // miBack
            // 
            this.miBack.Text = "返回";
            this.miBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.txtSWSQty);
            this.panel1.Controls.Add(this.label6);
            this.panel1.Controls.Add(this.btnClear);
            this.panel1.Controls.Add(this.txtSWSId);
            this.panel1.Controls.Add(this.lblItmQty);
            this.panel1.Controls.Add(this.lblItmId);
            this.panel1.Controls.Add(this.lblItmName);
            this.panel1.Controls.Add(this.txtCertId);
            this.panel1.Controls.Add(this.label5);
            this.panel1.Controls.Add(this.label4);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 153);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 115);
            // 
            // txtSWSQty
            // 
            this.txtSWSQty.Location = new System.Drawing.Point(81, 92);
            this.txtSWSQty.Name = "txtSWSQty";
            this.txtSWSQty.Size = new System.Drawing.Size(118, 21);
            this.txtSWSQty.TabIndex = 19;
            this.txtSWSQty.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSWSQty_KeyUp);
            this.txtSWSQty.LostFocus += new System.EventHandler(this.txtSWSQty_LostFocus);
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(2, 92);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(91, 20);
            this.label6.Text = "流程票数量：";
            // 
            // btnClear
            // 
            this.btnClear.Location = new System.Drawing.Point(204, 2);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(33, 20);
            this.btnClear.TabIndex = 16;
            this.btnClear.Text = "清空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtSWSId
            // 
            this.txtSWSId.Location = new System.Drawing.Point(81, 68);
            this.txtSWSId.Name = "txtSWSId";
            this.txtSWSId.Size = new System.Drawing.Size(118, 21);
            this.txtSWSId.TabIndex = 15;
            this.txtSWSId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSWSId_KeyUp);
            this.txtSWSId.LostFocus += new System.EventHandler(this.txtSWSId_LostFocus);
            // 
            // lblItmQty
            // 
            this.lblItmQty.Location = new System.Drawing.Point(186, 47);
            this.lblItmQty.Name = "lblItmQty";
            this.lblItmQty.Size = new System.Drawing.Size(51, 20);
            // 
            // lblItmId
            // 
            this.lblItmId.Location = new System.Drawing.Point(46, 47);
            this.lblItmId.Name = "lblItmId";
            this.lblItmId.Size = new System.Drawing.Size(101, 20);
            // 
            // lblItmName
            // 
            this.lblItmName.Location = new System.Drawing.Point(46, 25);
            this.lblItmName.Name = "lblItmName";
            this.lblItmName.Size = new System.Drawing.Size(191, 20);
            // 
            // txtCertId
            // 
            this.txtCertId.Location = new System.Drawing.Point(81, 2);
            this.txtCertId.Name = "txtCertId";
            this.txtCertId.Size = new System.Drawing.Size(117, 21);
            this.txtCertId.TabIndex = 9;
            this.txtCertId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtCertId_KeyUp);
            this.txtCertId.LostFocus += new System.EventHandler(this.txtCertId_LostFocus);
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(150, 47);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(50, 20);
            this.label5.Text = "数量：";
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(2, 47);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(48, 20);
            this.label4.Text = "图号：";
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(2, 25);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(48, 20);
            this.label3.Text = "品名：";
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(2, 69);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(91, 20);
            this.label2.Text = "流程票条码：";
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(2, 2);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(91, 20);
            this.label1.Text = "合格证条码：";
            // 
            // lvCertRe
            // 
            this.lvCertRe.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvCertRe.Location = new System.Drawing.Point(0, 0);
            this.lvCertRe.Name = "lvCertRe";
            this.lvCertRe.Size = new System.Drawing.Size(240, 153);
            this.lvCertRe.TabIndex = 1;
            // 
            // frmCertSWSBind
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.lvCertRe);
            this.Controls.Add(this.panel1);
            this.Menu = this.mainMenu1;
            this.Name = "frmCertSWSBind";
            this.Text = "合格证绑定";
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblItmName;
        private System.Windows.Forms.TextBox txtCertId;
        private System.Windows.Forms.MenuItem miOK;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.TextBox txtSWSId;
        private System.Windows.Forms.Label lblItmQty;
        private System.Windows.Forms.Label lblItmId;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.ListView lvCertRe;
        private System.Windows.Forms.TextBox txtSWSQty;
        private System.Windows.Forms.Label label6;
    }
}