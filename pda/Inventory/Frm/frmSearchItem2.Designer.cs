namespace Inventory.Frm
{
    partial class frmSearchItem2
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
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabOther = new System.Windows.Forms.TabPage();
            this.lvCtns = new System.Windows.Forms.ListView();
            this.panel1 = new System.Windows.Forms.Panel();
            this.txtItmId = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabOther.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
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
            this.tabOther.Controls.Add(this.lvCtns);
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 243);
            this.tabOther.Text = "拣货";
            // 
            // lvCtns
            // 
            this.lvCtns.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvCtns.Location = new System.Drawing.Point(0, 27);
            this.lvCtns.Name = "lvCtns";
            this.lvCtns.Size = new System.Drawing.Size(240, 216);
            this.lvCtns.TabIndex = 61;
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.txtItmId);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 27);
            // 
            // txtItmId
            // 
            this.txtItmId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtItmId.Location = new System.Drawing.Point(39, 3);
            this.txtItmId.Name = "txtItmId";
            this.txtItmId.Size = new System.Drawing.Size(194, 21);
            this.txtItmId.TabIndex = 60;
            this.txtItmId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtItmId_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(5, 5);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(40, 20);
            this.label2.Text = "图号";
            // 
            // tabControl
            // 
            this.tabControl.Controls.Add(this.tabOther);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Regular);
            this.tabControl.Location = new System.Drawing.Point(0, 0);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(240, 268);
            this.tabControl.TabIndex = 23;
            // 
            // frmSearchItem2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmSearchItem2";
            this.Text = "拣货查询";
            this.tabOther.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox txtItmId;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ListView lvCtns;
    }
}