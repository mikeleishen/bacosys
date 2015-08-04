namespace Inventory.Frm
{
    partial class frmBeginWork
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
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabOther = new System.Windows.Forms.TabPage();
            this.panel1 = new System.Windows.Forms.Panel();
            this.cbWs = new System.Windows.Forms.ComboBox();
            this.btnDelBegin = new System.Windows.Forms.Button();
            this.txtWsNo = new System.Windows.Forms.TextBox();
            this.btnNow = new System.Windows.Forms.Button();
            this.btnRp = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.cbRac = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.btnClear = new System.Windows.Forms.Button();
            this.txtBDate = new System.Windows.Forms.TextBox();
            this.txtSwsId = new System.Windows.Forms.TextBox();
            this.txtBMinute = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.txtBHour = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.lblWoId = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.lvEmp = new System.Windows.Forms.ListView();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtEmpId = new System.Windows.Forms.TextBox();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miOk = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabControl.SuspendLayout();
            this.tabOther.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabControl
            // 
            this.tabControl.Controls.Add(this.tabOther);
            this.tabControl.Controls.Add(this.tabPage1);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.tabControl.Location = new System.Drawing.Point(0, 0);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(240, 268);
            this.tabControl.TabIndex = 22;
            this.tabControl.SelectedIndexChanged += new System.EventHandler(this.tabControl_SelectedIndexChanged);
            // 
            // tabOther
            // 
            this.tabOther.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 235);
            this.tabOther.Text = "单证";
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.cbWs);
            this.panel1.Controls.Add(this.btnDelBegin);
            this.panel1.Controls.Add(this.txtWsNo);
            this.panel1.Controls.Add(this.btnNow);
            this.panel1.Controls.Add(this.btnRp);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.cbRac);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.btnClear);
            this.panel1.Controls.Add(this.txtBDate);
            this.panel1.Controls.Add(this.txtSwsId);
            this.panel1.Controls.Add(this.txtBMinute);
            this.panel1.Controls.Add(this.label14);
            this.panel1.Controls.Add(this.txtBHour);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.lblWoId);
            this.panel1.Controls.Add(this.label4);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 243);
            // 
            // cbWs
            // 
            this.cbWs.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.cbWs.Location = new System.Drawing.Point(109, 101);
            this.cbWs.Name = "cbWs";
            this.cbWs.Size = new System.Drawing.Size(128, 33);
            this.cbWs.TabIndex = 121;
            this.cbWs.SelectedIndexChanged += new System.EventHandler(this.cbWs_SelectedIndexChanged);
            // 
            // btnDelBegin
            // 
            this.btnDelBegin.BackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.btnDelBegin.Font = new System.Drawing.Font("Tahoma", 15F, System.Drawing.FontStyle.Regular);
            this.btnDelBegin.Location = new System.Drawing.Point(3, 201);
            this.btnDelBegin.Name = "btnDelBegin";
            this.btnDelBegin.Size = new System.Drawing.Size(100, 34);
            this.btnDelBegin.TabIndex = 114;
            this.btnDelBegin.Text = "删除开工";
            this.btnDelBegin.Click += new System.EventHandler(this.btnDelBegin_Click);
            // 
            // txtWsNo
            // 
            this.txtWsNo.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtWsNo.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.txtWsNo.Location = new System.Drawing.Point(109, 134);
            this.txtWsNo.Name = "txtWsNo";
            this.txtWsNo.Size = new System.Drawing.Size(128, 32);
            this.txtWsNo.TabIndex = 107;
            this.txtWsNo.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtWsNo_KeyUp);
            // 
            // btnNow
            // 
            this.btnNow.BackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.btnNow.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.btnNow.Location = new System.Drawing.Point(157, 167);
            this.btnNow.Name = "btnNow";
            this.btnNow.Size = new System.Drawing.Size(80, 26);
            this.btnNow.TabIndex = 104;
            this.btnNow.Text = "当前时间";
            this.btnNow.Click += new System.EventHandler(this.btnNow_Click);
            // 
            // btnRp
            // 
            this.btnRp.BackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.btnRp.Font = new System.Drawing.Font("Tahoma", 15F, System.Drawing.FontStyle.Regular);
            this.btnRp.Location = new System.Drawing.Point(137, 201);
            this.btnRp.Name = "btnRp";
            this.btnRp.Size = new System.Drawing.Size(100, 34);
            this.btnRp.TabIndex = 98;
            this.btnRp.Text = "进入报工";
            this.btnRp.Click += new System.EventHandler(this.btnRp_Click);
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.label3.Location = new System.Drawing.Point(2, 107);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(110, 26);
            this.label3.Text = "移出机器";
            // 
            // cbRac
            // 
            this.cbRac.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.cbRac.Location = new System.Drawing.Point(58, 67);
            this.cbRac.Name = "cbRac";
            this.cbRac.Size = new System.Drawing.Size(179, 33);
            this.cbRac.TabIndex = 89;
            this.cbRac.SelectedIndexChanged += new System.EventHandler(this.cbRac_SelectedIndexChanged);
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.label1.Location = new System.Drawing.Point(1, 71);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(80, 26);
            this.label1.Text = "工序";
            // 
            // btnClear
            // 
            this.btnClear.Location = new System.Drawing.Point(185, 8);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(52, 20);
            this.btnClear.TabIndex = 84;
            this.btnClear.Text = "清空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtBDate
            // 
            this.txtBDate.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBDate.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.txtBDate.Location = new System.Drawing.Point(43, 167);
            this.txtBDate.Name = "txtBDate";
            this.txtBDate.ReadOnly = true;
            this.txtBDate.Size = new System.Drawing.Size(60, 26);
            this.txtBDate.TabIndex = 80;
            this.txtBDate.Text = "140207";
            // 
            // txtSwsId
            // 
            this.txtSwsId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtSwsId.Location = new System.Drawing.Point(47, 7);
            this.txtSwsId.Name = "txtSwsId";
            this.txtSwsId.Size = new System.Drawing.Size(132, 21);
            this.txtSwsId.TabIndex = 58;
            this.txtSwsId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSwsId_KeyUp);
            this.txtSwsId.LostFocus += new System.EventHandler(this.txtSwsId_LostFocus);
            // 
            // txtBMinute
            // 
            this.txtBMinute.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBMinute.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.txtBMinute.Location = new System.Drawing.Point(129, 167);
            this.txtBMinute.Name = "txtBMinute";
            this.txtBMinute.ReadOnly = true;
            this.txtBMinute.Size = new System.Drawing.Size(26, 26);
            this.txtBMinute.TabIndex = 77;
            this.txtBMinute.Text = "01";
            this.txtBMinute.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBMinute_KeyUp);
            // 
            // label14
            // 
            this.label14.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label14.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.label14.Location = new System.Drawing.Point(3, 170);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(64, 23);
            this.label14.Text = "时间";
            // 
            // txtBHour
            // 
            this.txtBHour.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBHour.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.txtBHour.Location = new System.Drawing.Point(103, 167);
            this.txtBHour.Name = "txtBHour";
            this.txtBHour.ReadOnly = true;
            this.txtBHour.Size = new System.Drawing.Size(26, 26);
            this.txtBHour.TabIndex = 76;
            this.txtBHour.Text = "01";
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(3, 10);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(50, 20);
            this.label2.Text = "流程票";
            // 
            // lblWoId
            // 
            this.lblWoId.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblWoId.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.lblWoId.Location = new System.Drawing.Point(7, 34);
            this.lblWoId.Name = "lblWoId";
            this.lblWoId.Size = new System.Drawing.Size(230, 31);
            // 
            // label4
            // 
            this.label4.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label4.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.label4.Location = new System.Drawing.Point(0, 139);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(103, 26);
            this.label4.Text = "设备号";
            // 
            // tabPage1
            // 
            this.tabPage1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabPage1.Controls.Add(this.lvEmp);
            this.tabPage1.Controls.Add(this.panel2);
            this.tabPage1.Location = new System.Drawing.Point(0, 0);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Size = new System.Drawing.Size(240, 235);
            this.tabPage1.Text = "员工";
            // 
            // lvEmp
            // 
            this.lvEmp.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvEmp.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.lvEmp.Location = new System.Drawing.Point(0, 0);
            this.lvEmp.Name = "lvEmp";
            this.lvEmp.Size = new System.Drawing.Size(240, 196);
            this.lvEmp.TabIndex = 89;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.txtEmpId);
            this.panel2.Controls.Add(this.lblGdBaco);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 196);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 39);
            // 
            // txtEmpId
            // 
            this.txtEmpId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtEmpId.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.txtEmpId.Location = new System.Drawing.Point(75, 4);
            this.txtEmpId.Name = "txtEmpId";
            this.txtEmpId.Size = new System.Drawing.Size(162, 32);
            this.txtEmpId.TabIndex = 87;
            this.txtEmpId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtEmpId_KeyUp);
            this.txtEmpId.LostFocus += new System.EventHandler(this.txtEmpId_LostFocus);
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdBaco.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.lblGdBaco.Location = new System.Drawing.Point(2, 8);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(88, 28);
            this.lblGdBaco.Text = "员工码";
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.miOk);
            this.mainMenu1.MenuItems.Add(this.miBack);
            // 
            // miOk
            // 
            this.miOk.Text = "开工";
            this.miOk.Click += new System.EventHandler(this.miOk_Click);
            // 
            // miBack
            // 
            this.miBack.Text = "返回";
            this.miBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // frmBeginWork
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmBeginWork";
            this.Text = "饿";
            this.tabControl.ResumeLayout(false);
            this.tabOther.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.ComboBox cbRac;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.TextBox txtBDate;
        private System.Windows.Forms.TextBox txtSwsId;
        private System.Windows.Forms.TextBox txtBMinute;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.TextBox txtBHour;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lblWoId;
        private System.Windows.Forms.ListView lvEmp;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtEmpId;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.Button btnRp;
        private System.Windows.Forms.Button btnNow;
        private System.Windows.Forms.TextBox txtWsNo;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button btnDelBegin;
        private System.Windows.Forms.ComboBox cbWs;
    }
}