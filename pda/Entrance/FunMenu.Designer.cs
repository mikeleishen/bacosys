namespace Entrance
{
    partial class FunMenu
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.MainMenu mainMenu1;

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
            this.btnConfirm = new System.Windows.Forms.MenuItem();
            this.btnExit = new System.Windows.Forms.MenuItem();
            this.listMenu = new System.Windows.Forms.ListBox();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.btnConfirm);
            this.mainMenu1.MenuItems.Add(this.btnExit);
            // 
            // btnConfirm
            // 
            this.btnConfirm.Text = "确定";
            this.btnConfirm.Click += new System.EventHandler(this.btnConfirm_Click);
            // 
            // btnExit
            // 
            this.btnExit.Text = "退出";
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // listMenu
            // 
            this.listMenu.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.listMenu.Dock = System.Windows.Forms.DockStyle.Fill;
            this.listMenu.Font = new System.Drawing.Font("Tahoma", 15F, System.Drawing.FontStyle.Bold);
            this.listMenu.Location = new System.Drawing.Point(0, 0);
            this.listMenu.Name = "listMenu";
            this.listMenu.Size = new System.Drawing.Size(240, 266);
            this.listMenu.TabIndex = 0;
            this.listMenu.KeyDown += new System.Windows.Forms.KeyEventHandler(this.listMenu_KeyDown);
            // 
            // FunMenu
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.listMenu);
            this.Menu = this.mainMenu1;
            this.Name = "FunMenu";
            this.Text = "菜单页面";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem btnConfirm;
        private System.Windows.Forms.MenuItem btnExit;
        private System.Windows.Forms.ListBox listMenu;
    }
}