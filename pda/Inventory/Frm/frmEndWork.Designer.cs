namespace Inventory.Frm
{
    partial class frmEndWork
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
            this.miOk = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabOther = new System.Windows.Forms.TabPage();
            this.panel2 = new System.Windows.Forms.Panel();
            this.lblRestRPQty = new System.Windows.Forms.Label();
            this.txtScrapQty = new System.Windows.Forms.TextBox();
            this.btnBg = new System.Windows.Forms.Button();
            this.txtBDate = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.txtBMinute = new System.Windows.Forms.TextBox();
            this.txtBHour = new System.Windows.Forms.TextBox();
            this.lblBg = new System.Windows.Forms.Label();
            this.lblRac = new System.Windows.Forms.Label();
            this.txtRpQty = new System.Windows.Forms.TextBox();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.lblSwsId = new System.Windows.Forms.Label();
            this.txtInfo = new System.Windows.Forms.Label();
            this.txtRPWsNo = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.txtEmpId = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.btnClear = new System.Windows.Forms.Button();
            this.txtSwsId = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.tabEmps = new System.Windows.Forms.TabPage();
            this.lvEmp = new System.Windows.Forms.ListView();
            this.tabControl.SuspendLayout();
            this.tabOther.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabEmps.SuspendLayout();
            this.SuspendLayout();
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
            // tabControl
            // 
            this.tabControl.Controls.Add(this.tabOther);
            this.tabControl.Controls.Add(this.tabEmps);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Regular);
            this.tabControl.Location = new System.Drawing.Point(0, 0);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(240, 268);
            this.tabControl.TabIndex = 23;
            // 
            // tabOther
            // 
            this.tabOther.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabOther.Controls.Add(this.panel2);
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 243);
            this.tabOther.Text = "报工";
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.lblRestRPQty);
            this.panel2.Controls.Add(this.txtScrapQty);
            this.panel2.Controls.Add(this.btnBg);
            this.panel2.Controls.Add(this.txtBDate);
            this.panel2.Controls.Add(this.label14);
            this.panel2.Controls.Add(this.txtBMinute);
            this.panel2.Controls.Add(this.txtBHour);
            this.panel2.Controls.Add(this.lblBg);
            this.panel2.Controls.Add(this.lblRac);
            this.panel2.Controls.Add(this.txtRpQty);
            this.panel2.Controls.Add(this.lblGdBaco);
            this.panel2.Controls.Add(this.label1);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 111);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 132);
            // 
            // lblRestRPQty
            // 
            this.lblRestRPQty.Location = new System.Drawing.Point(145, 30);
            this.lblRestRPQty.Name = "lblRestRPQty";
            this.lblRestRPQty.Size = new System.Drawing.Size(83, 20);
            // 
            // txtScrapQty
            // 
            this.txtScrapQty.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtScrapQty.Location = new System.Drawing.Point(145, 56);
            this.txtScrapQty.Name = "txtScrapQty";
            this.txtScrapQty.ReadOnly = true;
            this.txtScrapQty.Size = new System.Drawing.Size(92, 21);
            this.txtScrapQty.TabIndex = 120;
            this.txtScrapQty.Text = "0";
            this.txtScrapQty.LostFocus += new System.EventHandler(this.txtScrapQty_LostFocus);
            // 
            // btnBg
            // 
            this.btnBg.BackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.btnBg.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.btnBg.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.btnBg.Location = new System.Drawing.Point(145, 107);
            this.btnBg.Name = "btnBg";
            this.btnBg.Size = new System.Drawing.Size(92, 25);
            this.btnBg.TabIndex = 113;
            this.btnBg.Text = "进入开工";
            this.btnBg.Click += new System.EventHandler(this.btnBg_Click);
            // 
            // txtBDate
            // 
            this.txtBDate.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBDate.Location = new System.Drawing.Point(145, 80);
            this.txtBDate.Name = "txtBDate";
            this.txtBDate.Size = new System.Drawing.Size(50, 21);
            this.txtBDate.TabIndex = 108;
            this.txtBDate.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBDate_KeyUp);
            // 
            // label14
            // 
            this.label14.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label14.Location = new System.Drawing.Point(109, 82);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(38, 20);
            this.label14.Text = "完工";
            // 
            // txtBMinute
            // 
            this.txtBMinute.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBMinute.Location = new System.Drawing.Point(217, 80);
            this.txtBMinute.Name = "txtBMinute";
            this.txtBMinute.Size = new System.Drawing.Size(20, 21);
            this.txtBMinute.TabIndex = 104;
            this.txtBMinute.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBMinute_KeyUp);
            // 
            // txtBHour
            // 
            this.txtBHour.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBHour.Location = new System.Drawing.Point(196, 80);
            this.txtBHour.Name = "txtBHour";
            this.txtBHour.Size = new System.Drawing.Size(20, 21);
            this.txtBHour.TabIndex = 103;
            this.txtBHour.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBHour_KeyUp);
            // 
            // lblBg
            // 
            this.lblBg.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblBg.Location = new System.Drawing.Point(3, 82);
            this.lblBg.Name = "lblBg";
            this.lblBg.Size = new System.Drawing.Size(100, 50);
            this.lblBg.Text = "开工：";
            // 
            // lblRac
            // 
            this.lblRac.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblRac.Location = new System.Drawing.Point(3, 4);
            this.lblRac.Name = "lblRac";
            this.lblRac.Size = new System.Drawing.Size(230, 20);
            this.lblRac.Text = "工艺：";
            // 
            // txtRpQty
            // 
            this.txtRpQty.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtRpQty.Location = new System.Drawing.Point(45, 30);
            this.txtRpQty.Name = "txtRpQty";
            this.txtRpQty.Size = new System.Drawing.Size(92, 21);
            this.txtRpQty.TabIndex = 91;
            this.txtRpQty.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtRpQty_KeyUp);
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdBaco.Location = new System.Drawing.Point(3, 31);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(62, 20);
            this.lblGdBaco.Text = "数量：";
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.Location = new System.Drawing.Point(3, 57);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(100, 20);
            this.label1.Text = "整体报废数量：";
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.lblSwsId);
            this.panel1.Controls.Add(this.txtInfo);
            this.panel1.Controls.Add(this.txtRPWsNo);
            this.panel1.Controls.Add(this.label4);
            this.panel1.Controls.Add(this.txtEmpId);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.btnClear);
            this.panel1.Controls.Add(this.txtSwsId);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 112);
            // 
            // lblSwsId
            // 
            this.lblSwsId.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Regular);
            this.lblSwsId.Location = new System.Drawing.Point(50, 27);
            this.lblSwsId.Name = "lblSwsId";
            this.lblSwsId.Size = new System.Drawing.Size(183, 20);
            // 
            // txtInfo
            // 
            this.txtInfo.ForeColor = System.Drawing.Color.Red;
            this.txtInfo.Location = new System.Drawing.Point(4, 92);
            this.txtInfo.Name = "txtInfo";
            this.txtInfo.Size = new System.Drawing.Size(229, 20);
            // 
            // txtRPWsNo
            // 
            this.txtRPWsNo.Location = new System.Drawing.Point(50, 69);
            this.txtRPWsNo.Name = "txtRPWsNo";
            this.txtRPWsNo.Size = new System.Drawing.Size(120, 21);
            this.txtRPWsNo.TabIndex = 94;
            this.txtRPWsNo.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtRPWsNo_KeyUp);
            this.txtRPWsNo.LostFocus += new System.EventHandler(this.txtRPWsNo_LostFocus);
            // 
            // label4
            // 
            this.label4.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label4.Location = new System.Drawing.Point(4, 73);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(49, 20);
            this.label4.Text = "设备号";
            // 
            // txtEmpId
            // 
            this.txtEmpId.Location = new System.Drawing.Point(50, 47);
            this.txtEmpId.Name = "txtEmpId";
            this.txtEmpId.Size = new System.Drawing.Size(120, 21);
            this.txtEmpId.TabIndex = 89;
            this.txtEmpId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtEmpId_KeyUp);
            this.txtEmpId.LostFocus += new System.EventHandler(this.txtEmpId_LostFocus);
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.Location = new System.Drawing.Point(4, 51);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(49, 20);
            this.label3.Text = "员工码";
            // 
            // btnClear
            // 
            this.btnClear.Location = new System.Drawing.Point(175, 4);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(60, 20);
            this.btnClear.TabIndex = 85;
            this.btnClear.Text = "清空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtSwsId
            // 
            this.txtSwsId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtSwsId.Location = new System.Drawing.Point(50, 2);
            this.txtSwsId.Name = "txtSwsId";
            this.txtSwsId.Size = new System.Drawing.Size(120, 21);
            this.txtSwsId.TabIndex = 60;
            this.txtSwsId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSwsId_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(4, 9);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(49, 20);
            this.label2.Text = "流程票";
            // 
            // tabEmps
            // 
            this.tabEmps.Controls.Add(this.lvEmp);
            this.tabEmps.Location = new System.Drawing.Point(0, 0);
            this.tabEmps.Name = "tabEmps";
            this.tabEmps.Size = new System.Drawing.Size(232, 241);
            this.tabEmps.Text = "员工";
            // 
            // lvEmp
            // 
            this.lvEmp.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvEmp.Location = new System.Drawing.Point(0, 0);
            this.lvEmp.Name = "lvEmp";
            this.lvEmp.Size = new System.Drawing.Size(232, 241);
            this.lvEmp.TabIndex = 92;
            // 
            // frmEndWork
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmEndWork";
            this.Text = "报工";
            this.tabControl.ResumeLayout(false);
            this.tabOther.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabEmps.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Label lblRac;
        private System.Windows.Forms.TextBox txtRpQty;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.TextBox txtSwsId;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtBMinute;
        private System.Windows.Forms.TextBox txtBHour;
        private System.Windows.Forms.Label lblBg;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.TextBox txtBDate;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Button btnBg;
        private System.Windows.Forms.TextBox txtScrapQty;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtEmpId;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtRPWsNo;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TabPage tabEmps;
        private System.Windows.Forms.ListView lvEmp;
        private System.Windows.Forms.Label txtInfo;
        private System.Windows.Forms.Label lblSwsId;
        private System.Windows.Forms.Label lblRestRPQty;
    }
}