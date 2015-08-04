namespace Inventory.Frm
{
    partial class frmSemiPurRec
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
            this.btnClear = new System.Windows.Forms.Button();
            this.txtCtnBaco = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.txtLotId = new System.Windows.Forms.TextBox();
            this.txtQty_Pre = new System.Windows.Forms.TextBox();
            this.lblGdQty = new System.Windows.Forms.Label();
            this.txtLocBaco = new System.Windows.Forms.TextBox();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.tabBaco = new System.Windows.Forms.TabPage();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miOk = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabOther.SuspendLayout();
            this.panel2.SuspendLayout();
            this.tabBaco.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabOther
            // 
            this.tabOther.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabOther.Controls.Add(this.lvPurItms);
            this.tabOther.Controls.Add(this.panel2);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 243);
            this.tabOther.Text = "物料";
            // 
            // lvPurItms
            // 
            this.lvPurItms.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvPurItms.Location = new System.Drawing.Point(0, 0);
            this.lvPurItms.Name = "lvPurItms";
            this.lvPurItms.Size = new System.Drawing.Size(240, 174);
            this.lvPurItms.TabIndex = 42;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.btnClear);
            this.panel2.Controls.Add(this.txtCtnBaco);
            this.panel2.Controls.Add(this.label1);
            this.panel2.Controls.Add(this.txtLotId);
            this.panel2.Controls.Add(this.txtQty_Pre);
            this.panel2.Controls.Add(this.lblGdQty);
            this.panel2.Controls.Add(this.txtLocBaco);
            this.panel2.Controls.Add(this.lblGdBaco);
            this.panel2.Controls.Add(this.label3);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 174);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 69);
            // 
            // btnClear
            // 
            this.btnClear.Location = new System.Drawing.Point(7, 25);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(85, 20);
            this.btnClear.TabIndex = 56;
            this.btnClear.Text = "清  空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtCtnBaco
            // 
            this.txtCtnBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtCtnBaco.Location = new System.Drawing.Point(74, 2);
            this.txtCtnBaco.Name = "txtCtnBaco";
            this.txtCtnBaco.Size = new System.Drawing.Size(163, 21);
            this.txtCtnBaco.TabIndex = 57;
            this.txtCtnBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtCtnBaco_KeyUp);
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(3, 5);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(73, 20);
            this.label1.Text = "采购流程票";
            // 
            // txtLotId
            // 
            this.txtLotId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtLotId.Location = new System.Drawing.Point(32, 46);
            this.txtLotId.Name = "txtLotId";
            this.txtLotId.Size = new System.Drawing.Size(60, 21);
            this.txtLotId.TabIndex = 51;
            this.txtLotId.Text = "13-22-22";
            this.txtLotId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtLotId_KeyUp);
            // 
            // txtQty_Pre
            // 
            this.txtQty_Pre.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_Pre.Location = new System.Drawing.Point(130, 46);
            this.txtQty_Pre.Name = "txtQty_Pre";
            this.txtQty_Pre.Size = new System.Drawing.Size(107, 21);
            this.txtQty_Pre.TabIndex = 33;
            this.txtQty_Pre.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_Pre_KeyUp);
            // 
            // lblGdQty
            // 
            this.lblGdQty.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdQty.Location = new System.Drawing.Point(98, 49);
            this.lblGdQty.Name = "lblGdQty";
            this.lblGdQty.Size = new System.Drawing.Size(43, 18);
            this.lblGdQty.Text = "数量";
            // 
            // txtLocBaco
            // 
            this.txtLocBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtLocBaco.Location = new System.Drawing.Point(130, 24);
            this.txtLocBaco.Name = "txtLocBaco";
            this.txtLocBaco.Size = new System.Drawing.Size(107, 21);
            this.txtLocBaco.TabIndex = 27;
            this.txtLocBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtLocBaco_KeyUp);
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdBaco.Location = new System.Drawing.Point(98, 25);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(59, 20);
            this.lblGdBaco.Text = "库位";
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.Location = new System.Drawing.Point(3, 47);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(45, 20);
            this.label3.Text = "批号";
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
            this.tabControl.TabIndex = 20;
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
            // frmSemiPurRec
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmSemiPurRec";
            this.Text = "半成品采购入库";
            this.tabOther.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.tabBaco.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.ListView lvPurItms;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtQty_Pre;
        private System.Windows.Forms.Label lblGdQty;
        private System.Windows.Forms.TextBox txtLocBaco;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.TabPage tabBaco;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.TextBox txtLotId;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.TextBox txtCtnBaco;
        private System.Windows.Forms.Label label1;

    }
}