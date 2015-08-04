namespace Inventory.Frm
{
    partial class frmSemiCheck
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
            this.panel1 = new System.Windows.Forms.Panel();
            this.cbCheckIds = new System.Windows.Forms.ComboBox();
            this.cbInvs = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtCheckLocId = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.btnClear = new System.Windows.Forms.Button();
            this.txtCountQty = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.txtChecker = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.lblLoc = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.lblItmQty = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.lblItmId = new System.Windows.Forms.Label();
            this.txtCtnBaco = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
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
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.cbCheckIds);
            this.panel1.Controls.Add(this.cbInvs);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 30);
            // 
            // cbCheckIds
            // 
            this.cbCheckIds.Location = new System.Drawing.Point(132, 4);
            this.cbCheckIds.Name = "cbCheckIds";
            this.cbCheckIds.Size = new System.Drawing.Size(105, 22);
            this.cbCheckIds.TabIndex = 3;
            // 
            // cbInvs
            // 
            this.cbInvs.Location = new System.Drawing.Point(33, 4);
            this.cbInvs.Name = "cbInvs";
            this.cbInvs.Size = new System.Drawing.Size(67, 22);
            this.cbInvs.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(3, 7);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(33, 20);
            this.label1.Text = "仓库";
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(101, 7);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(100, 20);
            this.label2.Text = "代码";
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.txtCheckLocId);
            this.panel2.Controls.Add(this.label9);
            this.panel2.Controls.Add(this.btnClear);
            this.panel2.Controls.Add(this.txtCountQty);
            this.panel2.Controls.Add(this.label8);
            this.panel2.Controls.Add(this.txtChecker);
            this.panel2.Controls.Add(this.label7);
            this.panel2.Controls.Add(this.lblLoc);
            this.panel2.Controls.Add(this.label6);
            this.panel2.Controls.Add(this.lblItmQty);
            this.panel2.Controls.Add(this.label5);
            this.panel2.Controls.Add(this.lblItmId);
            this.panel2.Controls.Add(this.txtCtnBaco);
            this.panel2.Controls.Add(this.label3);
            this.panel2.Controls.Add(this.label4);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel2.Location = new System.Drawing.Point(0, 30);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 238);
            // 
            // txtCheckLocId
            // 
            this.txtCheckLocId.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.txtCheckLocId.Location = new System.Drawing.Point(101, 181);
            this.txtCheckLocId.Name = "txtCheckLocId";
            this.txtCheckLocId.Size = new System.Drawing.Size(136, 29);
            this.txtCheckLocId.TabIndex = 37;
            this.txtCheckLocId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtCheckLocId_KeyUp);
            // 
            // label9
            // 
            this.label9.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.label9.Location = new System.Drawing.Point(4, 181);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(100, 30);
            this.label9.Text = "盘存库位:";
            // 
            // btnClear
            // 
            this.btnClear.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Bold);
            this.btnClear.Location = new System.Drawing.Point(164, 27);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(72, 30);
            this.btnClear.TabIndex = 25;
            this.btnClear.Text = "清空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtCountQty
            // 
            this.txtCountQty.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.txtCountQty.Location = new System.Drawing.Point(101, 151);
            this.txtCountQty.Name = "txtCountQty";
            this.txtCountQty.Size = new System.Drawing.Size(136, 29);
            this.txtCountQty.TabIndex = 24;
            this.txtCountQty.Text = "0";
            this.txtCountQty.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtCountQty_KeyUp);
            // 
            // label8
            // 
            this.label8.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.label8.Location = new System.Drawing.Point(4, 152);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(115, 30);
            this.label8.Text = "盘存数量:";
            // 
            // txtChecker
            // 
            this.txtChecker.Location = new System.Drawing.Point(60, 214);
            this.txtChecker.Name = "txtChecker";
            this.txtChecker.Size = new System.Drawing.Size(177, 21);
            this.txtChecker.TabIndex = 13;
            this.txtChecker.Visible = false;
            this.txtChecker.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtChecker_KeyUp);
            // 
            // label7
            // 
            this.label7.Location = new System.Drawing.Point(4, 217);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(53, 20);
            this.label7.Text = "盘点员";
            this.label7.Visible = false;
            // 
            // lblLoc
            // 
            this.lblLoc.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.lblLoc.Location = new System.Drawing.Point(60, 120);
            this.lblLoc.Name = "lblLoc";
            this.lblLoc.Size = new System.Drawing.Size(177, 30);
            // 
            // label6
            // 
            this.label6.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.label6.Location = new System.Drawing.Point(4, 120);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(77, 30);
            this.label6.Text = "库位:";
            // 
            // lblItmQty
            // 
            this.lblItmQty.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.lblItmQty.Location = new System.Drawing.Point(60, 60);
            this.lblItmQty.Name = "lblItmQty";
            this.lblItmQty.Size = new System.Drawing.Size(177, 30);
            this.lblItmQty.Visible = false;
            // 
            // label5
            // 
            this.label5.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.label5.Location = new System.Drawing.Point(4, 60);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(77, 30);
            this.label5.Text = "数量:";
            this.label5.Visible = false;
            // 
            // lblItmId
            // 
            this.lblItmId.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.lblItmId.Location = new System.Drawing.Point(60, 90);
            this.lblItmId.Name = "lblItmId";
            this.lblItmId.Size = new System.Drawing.Size(177, 30);
            // 
            // txtCtnBaco
            // 
            this.txtCtnBaco.Location = new System.Drawing.Point(98, 3);
            this.txtCtnBaco.Name = "txtCtnBaco";
            this.txtCtnBaco.Size = new System.Drawing.Size(139, 21);
            this.txtCtnBaco.TabIndex = 1;
            this.txtCtnBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtCtnBaco_KeyUp);
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(4, 7);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(100, 20);
            this.label3.Text = "流程票\\合格证";
            // 
            // label4
            // 
            this.label4.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.label4.Location = new System.Drawing.Point(4, 90);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(77, 30);
            this.label4.Text = "品号:";
            // 
            // frmSemiCheck
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.Menu = this.mainMenu1;
            this.Name = "frmSemiCheck";
            this.Text = "半成品盘点";
            this.panel1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.ComboBox cbInvs;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox cbCheckIds;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtCtnBaco;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label lblLoc;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label lblItmQty;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label lblItmId;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtChecker;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox txtCountQty;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.TextBox txtCheckLocId;
        private System.Windows.Forms.Label label9;
    }
}