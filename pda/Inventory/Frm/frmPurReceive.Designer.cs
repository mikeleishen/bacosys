namespace Inventory.Frm
{
    partial class frmPurReceive
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
            this.lvPurItms = new System.Windows.Forms.ListView();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtQty_After = new System.Windows.Forms.TextBox();
            this.txtQty_Pre = new System.Windows.Forms.TextBox();
            this.lblItmUnit = new System.Windows.Forms.Label();
            this.lblItmId = new System.Windows.Forms.Label();
            this.lblGdQty = new System.Windows.Forms.Label();
            this.txtBoxBaco = new System.Windows.Forms.TextBox();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.lblDot = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.txtPurId = new System.Windows.Forms.TextBox();
            this.tabBaco = new System.Windows.Forms.TabPage();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miOk = new System.Windows.Forms.MenuItem();
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
            this.tabOther.Controls.Add(this.lvPurItms);
            this.tabOther.Controls.Add(this.panel2);
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 243);
            this.tabOther.Text = "物料";
            // 
            // lvPurItms
            // 
            this.lvPurItms.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvPurItms.Location = new System.Drawing.Point(0, 28);
            this.lvPurItms.Name = "lvPurItms";
            this.lvPurItms.Size = new System.Drawing.Size(240, 167);
            this.lvPurItms.TabIndex = 42;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.txtQty_After);
            this.panel2.Controls.Add(this.txtQty_Pre);
            this.panel2.Controls.Add(this.lblItmUnit);
            this.panel2.Controls.Add(this.lblItmId);
            this.panel2.Controls.Add(this.lblGdQty);
            this.panel2.Controls.Add(this.txtBoxBaco);
            this.panel2.Controls.Add(this.lblGdBaco);
            this.panel2.Controls.Add(this.lblDot);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 195);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 48);
            // 
            // txtQty_After
            // 
            this.txtQty_After.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_After.Location = new System.Drawing.Point(103, 25);
            this.txtQty_After.Name = "txtQty_After";
            this.txtQty_After.Size = new System.Drawing.Size(37, 21);
            this.txtQty_After.TabIndex = 39;
            this.txtQty_After.Tag = "";
            this.txtQty_After.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_After_KeyUp);
            // 
            // txtQty_Pre
            // 
            this.txtQty_Pre.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_Pre.Location = new System.Drawing.Point(36, 25);
            this.txtQty_Pre.Name = "txtQty_Pre";
            this.txtQty_Pre.Size = new System.Drawing.Size(60, 21);
            this.txtQty_Pre.TabIndex = 33;
            this.txtQty_Pre.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_Pre_KeyUp);
            // 
            // lblItmUnit
            // 
            this.lblItmUnit.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblItmUnit.Location = new System.Drawing.Point(142, 28);
            this.lblItmUnit.Name = "lblItmUnit";
            this.lblItmUnit.Size = new System.Drawing.Size(95, 20);
            // 
            // lblItmId
            // 
            this.lblItmId.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblItmId.Location = new System.Drawing.Point(97, 5);
            this.lblItmId.Name = "lblItmId";
            this.lblItmId.Size = new System.Drawing.Size(143, 20);
            // 
            // lblGdQty
            // 
            this.lblGdQty.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdQty.Location = new System.Drawing.Point(3, 28);
            this.lblGdQty.Name = "lblGdQty";
            this.lblGdQty.Size = new System.Drawing.Size(43, 18);
            this.lblGdQty.Text = "数量";
            // 
            // txtBoxBaco
            // 
            this.txtBoxBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBoxBaco.Location = new System.Drawing.Point(36, 3);
            this.txtBoxBaco.Name = "txtBoxBaco";
            this.txtBoxBaco.Size = new System.Drawing.Size(60, 21);
            this.txtBoxBaco.TabIndex = 27;
            this.txtBoxBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBoxBaco_KeyUp);
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdBaco.Location = new System.Drawing.Point(3, 6);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(59, 20);
            this.lblGdBaco.Text = "库位";
            // 
            // lblDot
            // 
            this.lblDot.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.lblDot.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.lblDot.Location = new System.Drawing.Point(94, 27);
            this.lblDot.Name = "lblDot";
            this.lblDot.Size = new System.Drawing.Size(10, 18);
            this.lblDot.Text = ".";
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.txtPurId);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 28);
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(3, 6);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(45, 20);
            this.label1.Text = "采购单";
            // 
            // txtPurId
            // 
            this.txtPurId.Location = new System.Drawing.Point(50, 3);
            this.txtPurId.Name = "txtPurId";
            this.txtPurId.Size = new System.Drawing.Size(187, 21);
            this.txtPurId.TabIndex = 38;
            this.txtPurId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtPurId_KeyUp);
            // 
            // tabBaco
            // 
            this.tabBaco.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabBaco.Controls.Add(this.lvBaco);
            this.tabBaco.Location = new System.Drawing.Point(0, 0);
            this.tabBaco.Name = "tabBaco";
            this.tabBaco.Size = new System.Drawing.Size(240, 243);
            this.tabBaco.Text = "条码";
            // 
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 0);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(240, 243);
            this.lvBaco.TabIndex = 4;
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
            this.tabControl.TabIndex = 19;
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
            // frmPurReceive
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmPurReceive";
            this.Text = "采购收料";
            this.tabOther.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabBaco.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.TabPage tabBaco;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtPurId;
        private System.Windows.Forms.ListView lvPurItms;
        private System.Windows.Forms.TextBox txtQty_Pre;
        private System.Windows.Forms.Label lblItmUnit;
        private System.Windows.Forms.Label lblItmId;
        private System.Windows.Forms.Label lblGdQty;
        private System.Windows.Forms.TextBox txtBoxBaco;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.Label lblDot;
        private System.Windows.Forms.TextBox txtQty_After;
    }
}