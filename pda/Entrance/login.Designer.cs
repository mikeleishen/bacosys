namespace Entrance
{
    partial class Login
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
            this.btnLogin = new System.Windows.Forms.MenuItem();
            this.btnExit = new System.Windows.Forms.MenuItem();
            this.lbluser = new System.Windows.Forms.Label();
            this.lblpassword = new System.Windows.Forms.Label();
            this.txtUserName = new System.Windows.Forms.TextBox();
            this.txtPasswd = new System.Windows.Forms.TextBox();
            this.lblTip = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.btnRpWork = new System.Windows.Forms.Button();
            this.btnBWork = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.btnLogin);
            this.mainMenu1.MenuItems.Add(this.btnExit);
            // 
            // btnLogin
            // 
            this.btnLogin.Text = "登录";
            this.btnLogin.Click += new System.EventHandler(this.btnLogin_Click);
            // 
            // btnExit
            // 
            this.btnExit.Text = "退出";
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // lbluser
            // 
            this.lbluser.Font = new System.Drawing.Font("Courier New", 10F, System.Drawing.FontStyle.Regular);
            this.lbluser.Location = new System.Drawing.Point(11, 82);
            this.lbluser.Name = "lbluser";
            this.lbluser.Size = new System.Drawing.Size(65, 20);
            this.lbluser.Text = "用户名：";
            this.lbluser.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // lblpassword
            // 
            this.lblpassword.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Regular);
            this.lblpassword.Location = new System.Drawing.Point(11, 112);
            this.lblpassword.Name = "lblpassword";
            this.lblpassword.Size = new System.Drawing.Size(65, 20);
            this.lblpassword.Text = "密   码：";
            this.lblpassword.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // txtUserName
            // 
            this.txtUserName.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.txtUserName.Location = new System.Drawing.Point(82, 81);
            this.txtUserName.MaxLength = 18;
            this.txtUserName.Name = "txtUserName";
            this.txtUserName.Size = new System.Drawing.Size(120, 21);
            this.txtUserName.TabIndex = 2;
            this.txtUserName.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtUserName_KeyDown);
            // 
            // txtPasswd
            // 
            this.txtPasswd.AcceptsReturn = true;
            this.txtPasswd.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.txtPasswd.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular);
            this.txtPasswd.Location = new System.Drawing.Point(82, 112);
            this.txtPasswd.MaxLength = 16;
            this.txtPasswd.Name = "txtPasswd";
            this.txtPasswd.PasswordChar = '*';
            this.txtPasswd.Size = new System.Drawing.Size(120, 20);
            this.txtPasswd.TabIndex = 3;
            this.txtPasswd.WordWrap = false;
            // 
            // lblTip
            // 
            this.lblTip.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Regular);
            this.lblTip.ForeColor = System.Drawing.Color.OrangeRed;
            this.lblTip.Location = new System.Drawing.Point(0, 218);
            this.lblTip.Name = "lblTip";
            this.lblTip.Size = new System.Drawing.Size(240, 50);
            this.lblTip.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.label1.Location = new System.Drawing.Point(0, 30);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(237, 50);
            this.label1.Text = "条码管理系统";
            this.label1.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // btnRpWork
            // 
            this.btnRpWork.BackColor = System.Drawing.SystemColors.Window;
            this.btnRpWork.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.btnRpWork.Location = new System.Drawing.Point(142, 151);
            this.btnRpWork.Name = "btnRpWork";
            this.btnRpWork.Size = new System.Drawing.Size(60, 40);
            this.btnRpWork.TabIndex = 6;
            this.btnRpWork.Text = "报 工";
            this.btnRpWork.Click += new System.EventHandler(this.btnRpWork_Click);
            // 
            // btnBWork
            // 
            this.btnBWork.BackColor = System.Drawing.SystemColors.Window;
            this.btnBWork.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.btnBWork.Location = new System.Drawing.Point(16, 151);
            this.btnBWork.Name = "btnBWork";
            this.btnBWork.Size = new System.Drawing.Size(60, 40);
            this.btnBWork.TabIndex = 7;
            this.btnBWork.Text = "开 工";
            this.btnBWork.Click += new System.EventHandler(this.btnBWork_Click);
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(73, 237);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(113, 20);
            this.label2.Text = "ver.2015-04-22";
            // 
            // Login
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.btnBWork);
            this.Controls.Add(this.btnRpWork);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lblTip);
            this.Controls.Add(this.txtPasswd);
            this.Controls.Add(this.txtUserName);
            this.Controls.Add(this.lblpassword);
            this.Controls.Add(this.lbluser);
            this.KeyPreview = true;
            this.Menu = this.mainMenu1;
            this.Name = "Login";
            this.Text = "登录页面";
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.Login_KeyDown);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label lbluser;
        private System.Windows.Forms.Label lblpassword;
        private System.Windows.Forms.TextBox txtUserName;
        private System.Windows.Forms.TextBox txtPasswd;
        private System.Windows.Forms.MenuItem btnLogin;
        private System.Windows.Forms.MenuItem btnExit;
        private System.Windows.Forms.Label lblTip;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnRpWork;
        private System.Windows.Forms.Button btnBWork;
        private System.Windows.Forms.Label label2;
    }
}

