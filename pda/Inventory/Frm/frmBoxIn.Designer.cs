namespace Inventory.Frm
{
    partial class frmBoxIn
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
            this.miOk = new System.Windows.Forms.MenuItem();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabOther = new System.Windows.Forms.TabPage();
            this.panel1 = new System.Windows.Forms.Panel();
            this.btnClear = new System.Windows.Forms.Button();
            this.txtQty = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtOutInvId = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtCtnBaco = new System.Windows.Forms.TextBox();
            this.txtLocBaco = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.lvItm = new System.Windows.Forms.ListView();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.tabOther.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.SuspendLayout();
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
            // tabOther
            // 
            this.tabOther.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Controls.Add(this.lvItm);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 243);
            this.tabOther.Text = "物料";
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.btnClear);
            this.panel1.Controls.Add(this.txtQty);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.txtOutInvId);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.txtCtnBaco);
            this.panel1.Controls.Add(this.txtLocBaco);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.lblGdBaco);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 174);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 69);
            // 
            // btnClear
            // 
            this.btnClear.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.btnClear.Location = new System.Drawing.Point(163, 48);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(74, 19);
            this.btnClear.TabIndex = 102;
            this.btnClear.Text = "清空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtQty
            // 
            this.txtQty.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty.Location = new System.Drawing.Point(46, 47);
            this.txtQty.Name = "txtQty";
            this.txtQty.Size = new System.Drawing.Size(76, 21);
            this.txtQty.TabIndex = 99;
            this.txtQty.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_KeyUp);
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.Location = new System.Drawing.Point(2, 49);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(58, 20);
            this.label3.Text = "数量";
            // 
            // txtOutInvId
            // 
            this.txtOutInvId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtOutInvId.Location = new System.Drawing.Point(47, 25);
            this.txtOutInvId.Name = "txtOutInvId";
            this.txtOutInvId.Size = new System.Drawing.Size(75, 21);
            this.txtOutInvId.TabIndex = 95;
            this.txtOutInvId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtOutInvId_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(4, 28);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(58, 20);
            this.label2.Text = "出仓库";
            // 
            // txtCtnBaco
            // 
            this.txtCtnBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtCtnBaco.Location = new System.Drawing.Point(47, 2);
            this.txtCtnBaco.Name = "txtCtnBaco";
            this.txtCtnBaco.Size = new System.Drawing.Size(190, 21);
            this.txtCtnBaco.TabIndex = 89;
            this.txtCtnBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtCtnBaco_KeyUp);
            // 
            // txtLocBaco
            // 
            this.txtLocBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtLocBaco.Location = new System.Drawing.Point(163, 25);
            this.txtLocBaco.Name = "txtLocBaco";
            this.txtLocBaco.Size = new System.Drawing.Size(75, 21);
            this.txtLocBaco.TabIndex = 88;
            this.txtLocBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtLocBaco_KeyUp);
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.Location = new System.Drawing.Point(122, 29);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(58, 20);
            this.label1.Text = "入库位";
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdBaco.Location = new System.Drawing.Point(4, 5);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(58, 20);
            this.lblGdBaco.Text = "流程票";
            // 
            // lvItm
            // 
            this.lvItm.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvItm.Location = new System.Drawing.Point(0, 0);
            this.lvItm.Name = "lvItm";
            this.lvItm.Size = new System.Drawing.Size(240, 243);
            this.lvItm.TabIndex = 27;
            // 
            // tabControl
            // 
            this.tabControl.Controls.Add(this.tabOther);
            this.tabControl.Controls.Add(this.tabPage1);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Regular);
            this.tabControl.Location = new System.Drawing.Point(0, 0);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(240, 268);
            this.tabControl.TabIndex = 23;
            this.tabControl.SelectedIndexChanged += new System.EventHandler(this.tabControl_SelectedIndexChanged);
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.lvBaco);
            this.tabPage1.Location = new System.Drawing.Point(0, 0);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Size = new System.Drawing.Size(232, 241);
            this.tabPage1.Text = "条码";
            // 
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 0);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(232, 241);
            this.lvBaco.TabIndex = 28;
            // 
            // frmBoxIn
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmBoxIn";
            this.Text = "半成品调拨入库";
            this.tabOther.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.ListView lvItm;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox txtCtnBaco;
        private System.Windows.Forms.TextBox txtLocBaco;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.TextBox txtOutInvId;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtQty;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button btnClear;

    }
}