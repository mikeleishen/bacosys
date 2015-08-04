namespace Inventory.Frm
{
    partial class frmDoCert{
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

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
            this.miBack = new System.Windows.Forms.MenuItem();
            this.miOk = new System.Windows.Forms.MenuItem();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtCertBaco = new System.Windows.Forms.TextBox();
            this.lblCertBaco = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.lblItmQty = new System.Windows.Forms.Label();
            this.lblItmId = new System.Windows.Forms.Label();
            this.lblLastQty = new System.Windows.Forms.Label();
            this.txtQty = new System.Windows.Forms.TextBox();
            this.lblUnit = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.txtSwsBaco = new System.Windows.Forms.TextBox();
            this.lblGdQty = new System.Windows.Forms.Label();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // miBack
            // 
            this.miBack.Text = "返回";
            this.miBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // miOk
            // 
            this.miOk.Text = "确认";
            this.miOk.Click += new System.EventHandler(this.miOk_Click);
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.miOk);
            this.mainMenu1.MenuItems.Add(this.miBack);
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl1.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Regular);
            this.tabControl1.Location = new System.Drawing.Point(0, 0);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(240, 268);
            this.tabControl1.TabIndex = 0;
            // 
            // tabPage1
            // 
            this.tabPage1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabPage1.Controls.Add(this.lvBaco);
            this.tabPage1.Controls.Add(this.panel2);
            this.tabPage1.Controls.Add(this.panel1);
            this.tabPage1.Location = new System.Drawing.Point(0, 0);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Size = new System.Drawing.Size(240, 243);
            this.tabPage1.Text = "物料";
            // 
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 26);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(240, 145);
            this.lvBaco.TabIndex = 80;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.txtCertBaco);
            this.panel2.Controls.Add(this.lblCertBaco);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 26);
            // 
            // txtCertBaco
            // 
            this.txtCertBaco.Location = new System.Drawing.Point(48, 3);
            this.txtCertBaco.Name = "txtCertBaco";
            this.txtCertBaco.Size = new System.Drawing.Size(189, 21);
            this.txtCertBaco.TabIndex = 69;
            this.txtCertBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtCertBaco_KeyUp);
            // 
            // lblCertBaco
            // 
            this.lblCertBaco.Location = new System.Drawing.Point(4, 4);
            this.lblCertBaco.Name = "lblCertBaco";
            this.lblCertBaco.Size = new System.Drawing.Size(55, 20);
            this.lblCertBaco.Text = "合格证";
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.lblItmQty);
            this.panel1.Controls.Add(this.lblItmId);
            this.panel1.Controls.Add(this.lblLastQty);
            this.panel1.Controls.Add(this.txtQty);
            this.panel1.Controls.Add(this.lblUnit);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.txtSwsBaco);
            this.panel1.Controls.Add(this.lblGdQty);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 171);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 72);
            // 
            // lblItmQty
            // 
            this.lblItmQty.Location = new System.Drawing.Point(160, 6);
            this.lblItmQty.Name = "lblItmQty";
            this.lblItmQty.Size = new System.Drawing.Size(77, 18);
            // 
            // lblItmId
            // 
            this.lblItmId.Location = new System.Drawing.Point(4, 6);
            this.lblItmId.Name = "lblItmId";
            this.lblItmId.Size = new System.Drawing.Size(155, 18);
            // 
            // lblLastQty
            // 
            this.lblLastQty.Location = new System.Drawing.Point(160, 30);
            this.lblLastQty.Name = "lblLastQty";
            this.lblLastQty.Size = new System.Drawing.Size(77, 18);
            // 
            // txtQty
            // 
            this.txtQty.Location = new System.Drawing.Point(48, 49);
            this.txtQty.Name = "txtQty";
            this.txtQty.Size = new System.Drawing.Size(111, 21);
            this.txtQty.TabIndex = 77;
            this.txtQty.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_KeyUp);
            // 
            // lblUnit
            // 
            this.lblUnit.Location = new System.Drawing.Point(160, 53);
            this.lblUnit.Name = "lblUnit";
            this.lblUnit.Size = new System.Drawing.Size(77, 18);
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(4, 53);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(60, 18);
            this.label1.Text = "数量";
            // 
            // txtSwsBaco
            // 
            this.txtSwsBaco.Location = new System.Drawing.Point(48, 27);
            this.txtSwsBaco.Name = "txtSwsBaco";
            this.txtSwsBaco.Size = new System.Drawing.Size(111, 21);
            this.txtSwsBaco.TabIndex = 72;
            this.txtSwsBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSwsBaco_KeyUp);
            // 
            // lblGdQty
            // 
            this.lblGdQty.Location = new System.Drawing.Point(4, 30);
            this.lblGdQty.Name = "lblGdQty";
            this.lblGdQty.Size = new System.Drawing.Size(55, 18);
            this.lblGdQty.Text = "流程票";
            // 
            // frmDoCert
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl1);
            this.Menu = this.mainMenu1;
            this.Name = "frmDoCert";
            this.Text = "成品质检包装";
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtSwsBaco;
        private System.Windows.Forms.Label lblGdQty;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtCertBaco;
        private System.Windows.Forms.Label lblCertBaco;
        private System.Windows.Forms.TextBox txtQty;
        private System.Windows.Forms.Label lblUnit;
        private System.Windows.Forms.Label lblLastQty;
    
        /// <summary>
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.Label lblItmQty;
        private System.Windows.Forms.Label lblItmId;
    }
}