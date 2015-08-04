namespace Inventory.Frm
{
    partial class frmConfirmSO
    {
        /// <summary>
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
            this.tabOther = new System.Windows.Forms.TabPage();
            this.txtQty = new System.Windows.Forms.TextBox();
            this.txtBaco = new System.Windows.Forms.TextBox();
            this.lblBaco = new System.Windows.Forms.Label();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabBaco = new System.Windows.Forms.TabPage();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miOk = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.panel1 = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.txtSoId = new System.Windows.Forms.TextBox();
            this.lvItm = new System.Windows.Forms.ListView();
            this.tabOther.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.tabBaco.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabOther
            // 
            this.tabOther.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabOther.Controls.Add(this.lvItm);
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 243);
            this.tabOther.Text = "物料";
            // 
            // txtQty
            // 
            this.txtQty.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty.Location = new System.Drawing.Point(159, 47);
            this.txtQty.Name = "txtQty";
            this.txtQty.Size = new System.Drawing.Size(78, 21);
            this.txtQty.TabIndex = 38;
            this.txtQty.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_KeyUp);
            // 
            // txtBaco
            // 
            this.txtBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBaco.Location = new System.Drawing.Point(3, 47);
            this.txtBaco.Name = "txtBaco";
            this.txtBaco.Size = new System.Drawing.Size(150, 21);
            this.txtBaco.TabIndex = 5;
            this.txtBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBaco_KeyUp);
            // 
            // lblBaco
            // 
            this.lblBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblBaco.Location = new System.Drawing.Point(4, 30);
            this.lblBaco.Name = "lblBaco";
            this.lblBaco.Size = new System.Drawing.Size(138, 20);
            this.lblBaco.Text = "大、小包装/合格证";
            // 
            // tabControl
            // 
            this.tabControl.Controls.Add(this.tabOther);
            this.tabControl.Controls.Add(this.tabBaco);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Regular);
            this.tabControl.Location = new System.Drawing.Point(0, 0);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(240, 268);
            this.tabControl.TabIndex = 20;
            // 
            // tabBaco
            // 
            this.tabBaco.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabBaco.Controls.Add(this.lvBaco);
            this.tabBaco.Location = new System.Drawing.Point(0, 0);
            this.tabBaco.Name = "tabBaco";
            this.tabBaco.Size = new System.Drawing.Size(240, 245);
            this.tabBaco.Text = "条码";
            // 
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 0);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(240, 245);
            this.lvBaco.TabIndex = 4;
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.miOk);
            this.mainMenu1.MenuItems.Add(this.miBack);
            // 
            // miOk
            // 
            this.miOk.Text = "确认";
            this.miOk.Click += new System.EventHandler(this.miOk_Click);
            // 
            // miBack
            // 
            this.miBack.Text = "返回";
            this.miBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.txtQty);
            this.panel1.Controls.Add(this.txtSoId);
            this.panel1.Controls.Add(this.txtBaco);
            this.panel1.Controls.Add(this.lblBaco);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 72);
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(4, 5);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(45, 20);
            this.label1.Text = "销货单";
            // 
            // txtSoId
            // 
            this.txtSoId.Location = new System.Drawing.Point(50, 3);
            this.txtSoId.Name = "txtSoId";
            this.txtSoId.Size = new System.Drawing.Size(187, 21);
            this.txtSoId.TabIndex = 38;
            this.txtSoId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSoId_KeyUp);
            // 
            // lvItm
            // 
            this.lvItm.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvItm.Location = new System.Drawing.Point(0, 72);
            this.lvItm.Name = "lvItm";
            this.lvItm.Size = new System.Drawing.Size(240, 171);
            this.lvItm.TabIndex = 42;
            // 
            // frmConfirmSO
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmConfirmSO";
            this.Text = "销货确认";
            this.tabOther.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.tabBaco.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.TextBox txtBaco;
        private System.Windows.Forms.Label lblBaco;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage tabBaco;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.TextBox txtQty;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtSoId;
        private System.Windows.Forms.ListView lvItm;
    }
}