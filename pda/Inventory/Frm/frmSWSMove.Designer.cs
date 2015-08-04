namespace Inventory.Frm
{
    partial class frmSWSMove
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
            this.miOK = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tab1 = new System.Windows.Forms.TabPage();
            this.btnClear = new System.Windows.Forms.Button();
            this.txtLocBaco = new System.Windows.Forms.TextBox();
            this.txtSwsBaco = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.lvBacos = new System.Windows.Forms.ListView();
            this.tabControl1.SuspendLayout();
            this.tab1.SuspendLayout();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.miOK);
            this.mainMenu1.MenuItems.Add(this.miBack);
            // 
            // miOK
            // 
            this.miOK.Text = "确认";
            this.miOK.Click += new System.EventHandler(this.miOK_Click);
            // 
            // miBack
            // 
            this.miBack.Text = "返回";
            this.miBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tab1);
            this.tabControl1.Location = new System.Drawing.Point(0, 0);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(240, 268);
            this.tabControl1.TabIndex = 0;
            // 
            // tab1
            // 
            this.tab1.Controls.Add(this.btnClear);
            this.tab1.Controls.Add(this.txtLocBaco);
            this.tab1.Controls.Add(this.txtSwsBaco);
            this.tab1.Controls.Add(this.label2);
            this.tab1.Controls.Add(this.label1);
            this.tab1.Controls.Add(this.lvBacos);
            this.tab1.Location = new System.Drawing.Point(0, 0);
            this.tab1.Name = "tab1";
            this.tab1.Size = new System.Drawing.Size(240, 245);
            this.tab1.Text = "条码";
            // 
            // btnClear
            // 
            this.btnClear.Location = new System.Drawing.Point(186, 200);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(53, 20);
            this.btnClear.TabIndex = 88;
            this.btnClear.Text = "清空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtLocBaco
            // 
            this.txtLocBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtLocBaco.Location = new System.Drawing.Point(54, 220);
            this.txtLocBaco.Name = "txtLocBaco";
            this.txtLocBaco.Size = new System.Drawing.Size(185, 21);
            this.txtLocBaco.TabIndex = 87;
            this.txtLocBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtLocBaco_KeyUp);
            // 
            // txtSwsBaco
            // 
            this.txtSwsBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtSwsBaco.Location = new System.Drawing.Point(54, 198);
            this.txtSwsBaco.Name = "txtSwsBaco";
            this.txtSwsBaco.Size = new System.Drawing.Size(131, 21);
            this.txtSwsBaco.TabIndex = 86;
            this.txtSwsBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSwsBaco_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(4, 202);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(95, 20);
            this.label2.Text = "流程票";
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.Location = new System.Drawing.Point(4, 222);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(55, 20);
            this.label1.Text = "库   位";
            // 
            // lvBacos
            // 
            this.lvBacos.Location = new System.Drawing.Point(0, 0);
            this.lvBacos.Name = "lvBacos";
            this.lvBacos.Size = new System.Drawing.Size(240, 192);
            this.lvBacos.TabIndex = 85;
            // 
            // frmSWSMove
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl1);
            this.Menu = this.mainMenu1;
            this.Name = "frmSWSMove";
            this.Text = "半成品整箱移位";
            this.tabControl1.ResumeLayout(false);
            this.tab1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tab1;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.TextBox txtLocBaco;
        private System.Windows.Forms.TextBox txtSwsBaco;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ListView lvBacos;
        private System.Windows.Forms.MenuItem miOK;
        private System.Windows.Forms.MenuItem miBack;

    }
}