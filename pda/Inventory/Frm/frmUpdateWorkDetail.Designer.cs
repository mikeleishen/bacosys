namespace Inventory.Frm
{
    partial class frmUpdateWorkDetail
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
            this.miCancel = new System.Windows.Forms.MenuItem();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabOther = new System.Windows.Forms.TabPage();
            this.panel1 = new System.Windows.Forms.Panel();
            this.lblSwsId = new System.Windows.Forms.Label();
            this.lblWs = new System.Windows.Forms.Label();
            this.lblRacId = new System.Windows.Forms.Label();
            this.txtWsNo = new System.Windows.Forms.TextBox();
            this.btnNow = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.txtBDate = new System.Windows.Forms.TextBox();
            this.txtBMinute = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.txtBHour = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.lvEmp = new System.Windows.Forms.ListView();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtEmpId = new System.Windows.Forms.TextBox();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.tabControl.SuspendLayout();
            this.tabOther.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.miOK);
            this.mainMenu1.MenuItems.Add(this.miCancel);
            // 
            // miOK
            // 
            this.miOK.Text = "确定";
            this.miOK.Click += new System.EventHandler(this.miOK_Click);
            // 
            // miCancel
            // 
            this.miCancel.Text = "取消";
            this.miCancel.Click += new System.EventHandler(this.miCancel_Click);
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
            this.tabControl.TabIndex = 23;
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
            this.panel1.Controls.Add(this.lblSwsId);
            this.panel1.Controls.Add(this.lblWs);
            this.panel1.Controls.Add(this.lblRacId);
            this.panel1.Controls.Add(this.txtWsNo);
            this.panel1.Controls.Add(this.btnNow);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.txtBDate);
            this.panel1.Controls.Add(this.txtBMinute);
            this.panel1.Controls.Add(this.label14);
            this.panel1.Controls.Add(this.txtBHour);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.label4);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 243);
            // 
            // lblSwsId
            // 
            this.lblSwsId.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.lblSwsId.Location = new System.Drawing.Point(7, 42);
            this.lblSwsId.Name = "lblSwsId";
            this.lblSwsId.Size = new System.Drawing.Size(222, 26);
            // 
            // lblWs
            // 
            this.lblWs.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.lblWs.Location = new System.Drawing.Point(109, 127);
            this.lblWs.Name = "lblWs";
            this.lblWs.Size = new System.Drawing.Size(120, 26);
            // 
            // lblRacId
            // 
            this.lblRacId.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.lblRacId.Location = new System.Drawing.Point(109, 91);
            this.lblRacId.Name = "lblRacId";
            this.lblRacId.Size = new System.Drawing.Size(120, 26);
            // 
            // txtWsNo
            // 
            this.txtWsNo.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtWsNo.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.txtWsNo.Location = new System.Drawing.Point(109, 154);
            this.txtWsNo.Name = "txtWsNo";
            this.txtWsNo.Size = new System.Drawing.Size(128, 32);
            this.txtWsNo.TabIndex = 107;
            this.txtWsNo.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtWsNo_KeyUp);
            // 
            // btnNow
            // 
            this.btnNow.BackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.btnNow.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.btnNow.Location = new System.Drawing.Point(157, 192);
            this.btnNow.Name = "btnNow";
            this.btnNow.Size = new System.Drawing.Size(80, 26);
            this.btnNow.TabIndex = 104;
            this.btnNow.Text = "当前时间";
            this.btnNow.Click += new System.EventHandler(this.btnNow_Click);
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.label3.Location = new System.Drawing.Point(2, 127);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(110, 26);
            this.label3.Text = "移出机器";
            // 
            // label1
            // 
            this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label1.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.label1.Location = new System.Drawing.Point(1, 91);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(80, 26);
            this.label1.Text = "工序";
            // 
            // txtBDate
            // 
            this.txtBDate.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBDate.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.txtBDate.Location = new System.Drawing.Point(43, 192);
            this.txtBDate.Name = "txtBDate";
            this.txtBDate.ReadOnly = true;
            this.txtBDate.Size = new System.Drawing.Size(60, 26);
            this.txtBDate.TabIndex = 80;
            this.txtBDate.Text = "140207";
            // 
            // txtBMinute
            // 
            this.txtBMinute.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBMinute.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.txtBMinute.Location = new System.Drawing.Point(129, 192);
            this.txtBMinute.Name = "txtBMinute";
            this.txtBMinute.ReadOnly = true;
            this.txtBMinute.Size = new System.Drawing.Size(26, 26);
            this.txtBMinute.TabIndex = 77;
            this.txtBMinute.Text = "01";
            // 
            // label14
            // 
            this.label14.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label14.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.label14.Location = new System.Drawing.Point(3, 195);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(64, 23);
            this.label14.Text = "时间";
            // 
            // txtBHour
            // 
            this.txtBHour.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBHour.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Regular);
            this.txtBHour.Location = new System.Drawing.Point(103, 192);
            this.txtBHour.Name = "txtBHour";
            this.txtBHour.ReadOnly = true;
            this.txtBHour.Size = new System.Drawing.Size(26, 26);
            this.txtBHour.TabIndex = 76;
            this.txtBHour.Text = "01";
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.label2.Location = new System.Drawing.Point(3, 10);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(126, 30);
            this.label2.Text = "流程票";
            // 
            // label4
            // 
            this.label4.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label4.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.label4.Location = new System.Drawing.Point(0, 159);
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
            // frmUpdateWorkDetail
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.tabControl);
            this.Menu = this.mainMenu1;
            this.Name = "frmUpdateWorkDetail";
            this.Text = "开工信息更新";
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
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox txtWsNo;
        private System.Windows.Forms.Button btnNow;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtBDate;
        private System.Windows.Forms.TextBox txtBMinute;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.TextBox txtBHour;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.ListView lvEmp;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtEmpId;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.MenuItem miOK;
        private System.Windows.Forms.MenuItem miCancel;
        private System.Windows.Forms.Label lblSwsId;
        private System.Windows.Forms.Label lblWs;
        private System.Windows.Forms.Label lblRacId;
    }
}