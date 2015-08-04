namespace Inventory.Frm
{
    partial class frmMove
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
            this.panel1 = new System.Windows.Forms.Panel();
            this.txtQty_Pre = new System.Windows.Forms.TextBox();
            this.lblItmUnit = new System.Windows.Forms.Label();
            this.lblItmId = new System.Windows.Forms.Label();
            this.txtLocTo = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtQty_After = new System.Windows.Forms.TextBox();
            this.lblGdQty = new System.Windows.Forms.Label();
            this.txtLocFrom = new System.Windows.Forms.TextBox();
            this.lblLocOut = new System.Windows.Forms.Label();
            this.lblDot = new System.Windows.Forms.Label();
            this.lvItms = new System.Windows.Forms.ListView();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miOk = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.tabBaco = new System.Windows.Forms.TabPage();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabOther.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabBaco.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabOther
            // 
            this.tabOther.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Controls.Add(this.lvItms);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 243);
            this.tabOther.Text = "物料";
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.txtQty_Pre);
            this.panel1.Controls.Add(this.lblItmUnit);
            this.panel1.Controls.Add(this.lblItmId);
            this.panel1.Controls.Add(this.txtLocTo);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.txtQty_After);
            this.panel1.Controls.Add(this.lblGdQty);
            this.panel1.Controls.Add(this.txtLocFrom);
            this.panel1.Controls.Add(this.lblLocOut);
            this.panel1.Controls.Add(this.lblDot);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 194);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 49);
            // 
            // txtQty_Pre
            // 
            this.txtQty_Pre.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_Pre.Location = new System.Drawing.Point(36, 25);
            this.txtQty_Pre.Name = "txtQty_Pre";
            this.txtQty_Pre.Size = new System.Drawing.Size(47, 21);
            this.txtQty_Pre.TabIndex = 60;
            this.txtQty_Pre.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_Pre_KeyUp);
            // 
            // lblItmUnit
            // 
            this.lblItmUnit.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblItmUnit.Location = new System.Drawing.Point(123, 28);
            this.lblItmUnit.Name = "lblItmUnit";
            this.lblItmUnit.Size = new System.Drawing.Size(30, 20);
            this.lblItmUnit.Text = "PCS";
            // 
            // lblItmId
            // 
            this.lblItmId.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblItmId.Location = new System.Drawing.Point(157, 28);
            this.lblItmId.Name = "lblItmId";
            this.lblItmId.Size = new System.Drawing.Size(83, 20);
            this.lblItmId.Text = "4P345432-1";
            // 
            // txtLocTo
            // 
            this.txtLocTo.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtLocTo.Location = new System.Drawing.Point(145, 3);
            this.txtLocTo.Name = "txtLocTo";
            this.txtLocTo.Size = new System.Drawing.Size(90, 21);
            this.txtLocTo.TabIndex = 54;
            this.txtLocTo.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtLocTo_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(127, 4);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(43, 20);
            this.label2.Text = "->";
            // 
            // txtQty_After
            // 
            this.txtQty_After.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_After.Location = new System.Drawing.Point(95, 26);
            this.txtQty_After.Name = "txtQty_After";
            this.txtQty_After.Size = new System.Drawing.Size(26, 21);
            this.txtQty_After.TabIndex = 53;
            this.txtQty_After.Tag = "";
            this.txtQty_After.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_After_KeyUp);
            // 
            // lblGdQty
            // 
            this.lblGdQty.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdQty.Location = new System.Drawing.Point(4, 29);
            this.lblGdQty.Name = "lblGdQty";
            this.lblGdQty.Size = new System.Drawing.Size(43, 18);
            this.lblGdQty.Text = "数量";
            // 
            // txtLocFrom
            // 
            this.txtLocFrom.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtLocFrom.Location = new System.Drawing.Point(36, 3);
            this.txtLocFrom.Name = "txtLocFrom";
            this.txtLocFrom.Size = new System.Drawing.Size(85, 21);
            this.txtLocFrom.TabIndex = 51;
            this.txtLocFrom.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtLocFrom_KeyUp);
            // 
            // lblLocOut
            // 
            this.lblLocOut.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblLocOut.Location = new System.Drawing.Point(4, 6);
            this.lblLocOut.Name = "lblLocOut";
            this.lblLocOut.Size = new System.Drawing.Size(43, 20);
            this.lblLocOut.Text = "库位";
            // 
            // lblDot
            // 
            this.lblDot.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.lblDot.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.lblDot.Location = new System.Drawing.Point(82, 27);
            this.lblDot.Name = "lblDot";
            this.lblDot.Size = new System.Drawing.Size(10, 18);
            this.lblDot.Text = ".";
            // 
            // lvItms
            // 
            this.lvItms.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvItms.Location = new System.Drawing.Point(0, 0);
            this.lvItms.Name = "lvItms";
            this.lvItms.Size = new System.Drawing.Size(240, 243);
            this.lvItms.TabIndex = 27;
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
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 0);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(240, 243);
            this.lvBaco.TabIndex = 4;
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
            this.tabControl.TabIndex = 21;
            // 
            // frmMove
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmMove";
            this.Text = "原材料移位";
            this.tabOther.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabBaco.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.ListView lvItms;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.TabPage tabBaco;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox txtLocTo;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtQty_After;
        private System.Windows.Forms.Label lblGdQty;
        private System.Windows.Forms.TextBox txtLocFrom;
        private System.Windows.Forms.Label lblLocOut;
        private System.Windows.Forms.Label lblDot;
        private System.Windows.Forms.Label lblItmUnit;
        private System.Windows.Forms.Label lblItmId;
        private System.Windows.Forms.TextBox txtQty_Pre;
    }
}