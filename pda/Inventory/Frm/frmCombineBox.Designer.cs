namespace Inventory.Frm
{
    partial class frmCombineBox
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
            this.menuItem1 = new System.Windows.Forms.MenuItem();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtGdBaco = new System.Windows.Forms.TextBox();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.panel1 = new System.Windows.Forms.Panel();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.lvGd = new System.Windows.Forms.ListView();
            this.panel2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuItem1
            // 
            this.menuItem1.Text = "确认";
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(34, 3);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(202, 21);
            this.textBox1.TabIndex = 83;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(2, 6);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(43, 20);
            this.label1.Text = "包装";
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.Color.Transparent;
            this.panel2.Controls.Add(this.textBox1);
            this.panel2.Controls.Add(this.label1);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 240);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 28);
            // 
            // txtGdBaco
            // 
            this.txtGdBaco.Location = new System.Drawing.Point(47, 4);
            this.txtGdBaco.Name = "txtGdBaco";
            this.txtGdBaco.Size = new System.Drawing.Size(189, 21);
            this.txtGdBaco.TabIndex = 82;
            // 
            // miBack
            // 
            this.miBack.Text = "返回";
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.menuItem1);
            this.mainMenu1.MenuItems.Add(this.miBack);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.Transparent;
            this.panel1.Controls.Add(this.txtGdBaco);
            this.panel1.Controls.Add(this.lblGdBaco);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 28);
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Location = new System.Drawing.Point(3, 6);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(55, 20);
            this.lblGdBaco.Text = "新包装";
            // 
            // lvGd
            // 
            this.lvGd.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvGd.Location = new System.Drawing.Point(0, 0);
            this.lvGd.Name = "lvGd";
            this.lvGd.Size = new System.Drawing.Size(240, 268);
            this.lvGd.TabIndex = 83;
            // 
            // frmCombineBox
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.lvGd);
            this.Menu = this.mainMenu1;
            this.Name = "frmCombineBox";
            this.Text = "成品拼箱";
            this.panel2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem menuItem1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtGdBaco;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.ListView lvGd;
    }
}