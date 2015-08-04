namespace Inventory.Frm
{
    partial class frmSemiScatteredOtherIn
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
            this.lvItms = new System.Windows.Forms.ListView();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtQty_Pre = new System.Windows.Forms.TextBox();
            this.lblItmUnit = new System.Windows.Forms.Label();
            this.txtSwsBaco = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.cbInOutReason = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.cbSynErp = new System.Windows.Forms.CheckBox();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.tabBaco = new System.Windows.Forms.TabPage();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.miOk = new System.Windows.Forms.MenuItem();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabOther.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabBaco.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabOther
            // 
            this.tabOther.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabOther.Controls.Add(this.lvItms);
            this.tabOther.Controls.Add(this.panel2);
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 243);
            this.tabOther.Text = "物料";
            // 
            // lvItms
            // 
            this.lvItms.Location = new System.Drawing.Point(0, 35);
            this.lvItms.Name = "lvItms";
            this.lvItms.Size = new System.Drawing.Size(240, 162);
            this.lvItms.TabIndex = 61;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.txtQty_Pre);
            this.panel2.Controls.Add(this.lblItmUnit);
            this.panel2.Controls.Add(this.txtSwsBaco);
            this.panel2.Controls.Add(this.label2);
            this.panel2.Controls.Add(this.label4);
            this.panel2.Location = new System.Drawing.Point(0, 197);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 46);
            // 
            // txtQty_Pre
            // 
            this.txtQty_Pre.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_Pre.Location = new System.Drawing.Point(94, 24);
            this.txtQty_Pre.Name = "txtQty_Pre";
            this.txtQty_Pre.Size = new System.Drawing.Size(89, 21);
            this.txtQty_Pre.TabIndex = 64;
            this.txtQty_Pre.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_Pre_KeyUp);
            // 
            // lblItmUnit
            // 
            this.lblItmUnit.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblItmUnit.Location = new System.Drawing.Point(184, 24);
            this.lblItmUnit.Name = "lblItmUnit";
            this.lblItmUnit.Size = new System.Drawing.Size(53, 20);
            // 
            // txtSwsBaco
            // 
            this.txtSwsBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtSwsBaco.Location = new System.Drawing.Point(94, 2);
            this.txtSwsBaco.Name = "txtSwsBaco";
            this.txtSwsBaco.Size = new System.Drawing.Size(143, 21);
            this.txtSwsBaco.TabIndex = 54;
            this.txtSwsBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSwsBaco_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(3, 6);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(95, 20);
            this.label2.Text = "流程票/合格证";
            // 
            // label4
            // 
            this.label4.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label4.Location = new System.Drawing.Point(33, 25);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(55, 20);
            this.label4.Text = "数   量";
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.cbInOutReason);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.cbSynErp);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 35);
            // 
            // cbInOutReason
            // 
            this.cbInOutReason.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.cbInOutReason.Location = new System.Drawing.Point(45, 4);
            this.cbInOutReason.Name = "cbInOutReason";
            this.cbInOutReason.Size = new System.Drawing.Size(152, 27);
            this.cbInOutReason.TabIndex = 55;
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.label3.Location = new System.Drawing.Point(1, 6);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(43, 20);
            this.label3.Text = "原因";
            // 
            // cbSynErp
            // 
            this.cbSynErp.Checked = true;
            this.cbSynErp.CheckState = System.Windows.Forms.CheckState.Checked;
            this.cbSynErp.Location = new System.Drawing.Point(208, 6);
            this.cbSynErp.Name = "cbSynErp";
            this.cbSynErp.Size = new System.Drawing.Size(28, 20);
            this.cbSynErp.TabIndex = 54;
            this.cbSynErp.Text = "更新ERP数量";
            this.cbSynErp.Visible = false;
            // 
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 0);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(232, 241);
            this.lvBaco.TabIndex = 4;
            // 
            // tabBaco
            // 
            this.tabBaco.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabBaco.Controls.Add(this.lvBaco);
            this.tabBaco.Location = new System.Drawing.Point(0, 0);
            this.tabBaco.Name = "tabBaco";
            this.tabBaco.Size = new System.Drawing.Size(232, 241);
            this.tabBaco.Text = "条码";
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
            this.tabControl.TabIndex = 22;
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
            // miBack
            // 
            this.miBack.Text = "返回";
            this.miBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // frmSemiScatteredOtherIn
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmSemiScatteredOtherIn";
            this.Text = "半成品零散其它入";
            this.tabOther.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabBaco.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.ListView lvItms;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtQty_Pre;
        private System.Windows.Forms.Label lblItmUnit;
        private System.Windows.Forms.TextBox txtSwsBaco;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.ComboBox cbInOutReason;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.CheckBox cbSynErp;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.TabPage tabBaco;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miBack;
    }
}