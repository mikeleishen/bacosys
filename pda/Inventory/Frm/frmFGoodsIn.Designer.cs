namespace Inventory.Frm
{
    partial class frmFGoodsIn
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
            this.panel1 = new System.Windows.Forms.Panel();
            this.txtLocBaco = new System.Windows.Forms.TextBox();
            this.txtInvId = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtPkgBaco = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.tabOther = new System.Windows.Forms.TabPage();
            this.lvItms = new System.Windows.Forms.ListView();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.panel1.SuspendLayout();
            this.tabOther.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.SuspendLayout();
            // 
            // miOk
            // 
            this.miOk.Text = "确认";
            this.miOk.Click += new System.EventHandler(this.miOk_Click);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.txtLocBaco);
            this.panel1.Controls.Add(this.txtInvId);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.txtPkgBaco);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.lblGdBaco);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 193);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 50);
            // 
            // txtLocBaco
            // 
            this.txtLocBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtLocBaco.Location = new System.Drawing.Point(156, 27);
            this.txtLocBaco.Name = "txtLocBaco";
            this.txtLocBaco.Size = new System.Drawing.Size(81, 21);
            this.txtLocBaco.TabIndex = 94;
            this.txtLocBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtLocBaco_KeyUp);
            // 
            // txtInvId
            // 
            this.txtInvId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtInvId.Location = new System.Drawing.Point(47, 27);
            this.txtInvId.Name = "txtInvId";
            this.txtInvId.Size = new System.Drawing.Size(66, 21);
            this.txtInvId.TabIndex = 88;
            this.txtInvId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtInvId_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(3, 29);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(72, 20);
            this.label2.Text = "出仓库";
            // 
            // txtPkgBaco
            // 
            this.txtPkgBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtPkgBaco.Location = new System.Drawing.Point(71, 4);
            this.txtPkgBaco.Name = "txtPkgBaco";
            this.txtPkgBaco.Size = new System.Drawing.Size(166, 21);
            this.txtPkgBaco.TabIndex = 89;
            this.txtPkgBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtPkgBaco_KeyUp);
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.Location = new System.Drawing.Point(112, 29);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(72, 20);
            this.label1.Text = "入库位";
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdBaco.Location = new System.Drawing.Point(2, 5);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(72, 20);
            this.lblGdBaco.Text = "合格证条码";
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
            // lvItms
            // 
            this.lvItms.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvItms.Location = new System.Drawing.Point(0, 0);
            this.lvItms.Name = "lvItms";
            this.lvItms.Size = new System.Drawing.Size(240, 243);
            this.lvItms.TabIndex = 27;
            // 
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 0);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(240, 243);
            this.lvBaco.TabIndex = 28;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.lvBaco);
            this.tabPage1.Location = new System.Drawing.Point(0, 0);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Size = new System.Drawing.Size(240, 243);
            this.tabPage1.Text = "条码";
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
            this.tabControl.TabIndex = 24;
            // 
            // frmFGoodsIn
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmFGoodsIn";
            this.Text = "成品入库";
            this.panel1.ResumeLayout(false);
            this.tabOther.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox txtPkgBaco;
        private System.Windows.Forms.TextBox txtInvId;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.ListView lvItms;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtLocBaco;

    }
}