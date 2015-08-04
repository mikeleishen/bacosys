namespace Inventory.Frm
{
    partial class frmOtherIn
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
            this.lblLocItmId = new System.Windows.Forms.Label();
            this.txtLocBaco = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtQty_After = new System.Windows.Forms.TextBox();
            this.lblGdQty = new System.Windows.Forms.Label();
            this.lblDot = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.cbInOutReason = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.cbSynErp = new System.Windows.Forms.CheckBox();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabBaco = new System.Windows.Forms.TabPage();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miOk = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabOther.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.tabBaco.SuspendLayout();
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
            this.lvItms.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvItms.Location = new System.Drawing.Point(0, 29);
            this.lvItms.Name = "lvItms";
            this.lvItms.Size = new System.Drawing.Size(240, 168);
            this.lvItms.TabIndex = 61;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.txtQty_Pre);
            this.panel2.Controls.Add(this.lblItmUnit);
            this.panel2.Controls.Add(this.lblLocItmId);
            this.panel2.Controls.Add(this.txtLocBaco);
            this.panel2.Controls.Add(this.label2);
            this.panel2.Controls.Add(this.txtQty_After);
            this.panel2.Controls.Add(this.lblGdQty);
            this.panel2.Controls.Add(this.lblDot);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 197);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 46);
            // 
            // txtQty_Pre
            // 
            this.txtQty_Pre.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_Pre.Location = new System.Drawing.Point(33, 24);
            this.txtQty_Pre.Name = "txtQty_Pre";
            this.txtQty_Pre.Size = new System.Drawing.Size(69, 21);
            this.txtQty_Pre.TabIndex = 64;
            this.txtQty_Pre.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_Pre_KeyUp);
            // 
            // lblItmUnit
            // 
            this.lblItmUnit.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblItmUnit.Location = new System.Drawing.Point(148, 27);
            this.lblItmUnit.Name = "lblItmUnit";
            this.lblItmUnit.Size = new System.Drawing.Size(88, 20);
            // 
            // lblLocItmId
            // 
            this.lblLocItmId.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblLocItmId.Location = new System.Drawing.Point(148, 4);
            this.lblLocItmId.Name = "lblLocItmId";
            this.lblLocItmId.Size = new System.Drawing.Size(91, 20);
            // 
            // txtLocBaco
            // 
            this.txtLocBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtLocBaco.Location = new System.Drawing.Point(33, 2);
            this.txtLocBaco.Name = "txtLocBaco";
            this.txtLocBaco.Size = new System.Drawing.Size(114, 21);
            this.txtLocBaco.TabIndex = 54;
            this.txtLocBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtLocBaco_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(2, 5);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(43, 20);
            this.label2.Text = "库位";
            // 
            // txtQty_After
            // 
            this.txtQty_After.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_After.Location = new System.Drawing.Point(111, 24);
            this.txtQty_After.Name = "txtQty_After";
            this.txtQty_After.Size = new System.Drawing.Size(36, 21);
            this.txtQty_After.TabIndex = 53;
            this.txtQty_After.Tag = "";
            this.txtQty_After.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_After_KeyUp);
            // 
            // lblGdQty
            // 
            this.lblGdQty.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdQty.Location = new System.Drawing.Point(2, 27);
            this.lblGdQty.Name = "lblGdQty";
            this.lblGdQty.Size = new System.Drawing.Size(43, 18);
            this.lblGdQty.Text = "数量";
            // 
            // lblDot
            // 
            this.lblDot.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.lblDot.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.lblDot.Location = new System.Drawing.Point(102, 24);
            this.lblDot.Name = "lblDot";
            this.lblDot.Size = new System.Drawing.Size(10, 18);
            this.lblDot.Text = ".";
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
            this.panel1.Size = new System.Drawing.Size(240, 29);
            // 
            // cbInOutReason
            // 
            this.cbInOutReason.Location = new System.Drawing.Point(33, 4);
            this.cbInOutReason.Name = "cbInOutReason";
            this.cbInOutReason.Size = new System.Drawing.Size(90, 22);
            this.cbInOutReason.TabIndex = 55;
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.Location = new System.Drawing.Point(1, 6);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(43, 20);
            this.label3.Text = "原因";
            // 
            // cbSynErp
            // 
            this.cbSynErp.Checked = true;
            this.cbSynErp.CheckState = System.Windows.Forms.CheckState.Checked;
            this.cbSynErp.Location = new System.Drawing.Point(136, 4);
            this.cbSynErp.Name = "cbSynErp";
            this.cbSynErp.Size = new System.Drawing.Size(109, 20);
            this.cbSynErp.TabIndex = 54;
            this.cbSynErp.Text = "更新ERP数量";
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
            this.tabBaco.Size = new System.Drawing.Size(232, 241);
            this.tabBaco.Text = "条码";
            // 
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 0);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(232, 241);
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
            // frmOtherIn
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmOtherIn";
            this.Text = "其他入库（原材料）";
            this.tabOther.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.tabBaco.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage tabBaco;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtLocBaco;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtQty_After;
        private System.Windows.Forms.Label lblGdQty;
        private System.Windows.Forms.Label lblDot;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.ComboBox cbInOutReason;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.CheckBox cbSynErp;
        private System.Windows.Forms.Label lblLocItmId;
        private System.Windows.Forms.ListView lvItms;
        private System.Windows.Forms.Label lblItmUnit;
        private System.Windows.Forms.TextBox txtQty_Pre;
    }
}