namespace Inventory.Frm
{
    partial class frmAllScrap
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
            this.panel2 = new System.Windows.Forms.Panel();
            this.cbScrapContent = new System.Windows.Forms.ComboBox();
            this.cbScrapReason = new System.Windows.Forms.ComboBox();
            this.label9 = new System.Windows.Forms.Label();
            this.txtItmId = new System.Windows.Forms.TextBox();
            this.txtInspectorId = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.txtEmpId = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.txtRpQty = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.txtScrapedQty = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtRacName = new System.Windows.Forms.TextBox();
            this.txtSwsId = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtScrapQty = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miOk = new System.Windows.Forms.MenuItem();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.lvScrap = new System.Windows.Forms.ListView();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.cbScrapContent);
            this.panel2.Controls.Add(this.cbScrapReason);
            this.panel2.Controls.Add(this.label9);
            this.panel2.Controls.Add(this.txtItmId);
            this.panel2.Controls.Add(this.txtInspectorId);
            this.panel2.Controls.Add(this.label6);
            this.panel2.Controls.Add(this.txtEmpId);
            this.panel2.Controls.Add(this.label5);
            this.panel2.Controls.Add(this.txtRpQty);
            this.panel2.Controls.Add(this.label4);
            this.panel2.Controls.Add(this.txtScrapedQty);
            this.panel2.Controls.Add(this.label3);
            this.panel2.Controls.Add(this.txtRacName);
            this.panel2.Controls.Add(this.txtSwsId);
            this.panel2.Controls.Add(this.label2);
            this.panel2.Controls.Add(this.txtScrapQty);
            this.panel2.Controls.Add(this.label7);
            this.panel2.Controls.Add(this.label1);
            this.panel2.Controls.Add(this.label8);
            this.panel2.Controls.Add(this.label11);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 83);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 185);
            // 
            // cbScrapContent
            // 
            this.cbScrapContent.Location = new System.Drawing.Point(75, 116);
            this.cbScrapContent.Name = "cbScrapContent";
            this.cbScrapContent.Size = new System.Drawing.Size(165, 22);
            this.cbScrapContent.TabIndex = 131;
            // 
            // cbScrapReason
            // 
            this.cbScrapReason.Location = new System.Drawing.Point(75, 139);
            this.cbScrapReason.Name = "cbScrapReason";
            this.cbScrapReason.Size = new System.Drawing.Size(165, 22);
            this.cbScrapReason.TabIndex = 116;
            // 
            // label9
            // 
            this.label9.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label9.Location = new System.Drawing.Point(1, 142);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(64, 20);
            this.label9.Text = "产生原因";
            // 
            // txtItmId
            // 
            this.txtItmId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtItmId.BackColor = System.Drawing.SystemColors.Info;
            this.txtItmId.Enabled = false;
            this.txtItmId.Location = new System.Drawing.Point(59, 24);
            this.txtItmId.Name = "txtItmId";
            this.txtItmId.ReadOnly = true;
            this.txtItmId.Size = new System.Drawing.Size(181, 21);
            this.txtItmId.TabIndex = 113;
            // 
            // txtInspectorId
            // 
            this.txtInspectorId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtInspectorId.Location = new System.Drawing.Point(184, 94);
            this.txtInspectorId.Name = "txtInspectorId";
            this.txtInspectorId.Size = new System.Drawing.Size(56, 21);
            this.txtInspectorId.TabIndex = 110;
            this.txtInspectorId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtInspectorId_KeyUp);
            this.txtInspectorId.LostFocus += new System.EventHandler(this.txtInspectorId_LostFocus);
            // 
            // label6
            // 
            this.label6.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label6.Location = new System.Drawing.Point(128, 97);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(77, 20);
            this.label6.Text = "巡检工号";
            // 
            // txtEmpId
            // 
            this.txtEmpId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtEmpId.Location = new System.Drawing.Point(59, 94);
            this.txtEmpId.Name = "txtEmpId";
            this.txtEmpId.Size = new System.Drawing.Size(68, 21);
            this.txtEmpId.TabIndex = 107;
            this.txtEmpId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtEmpId_KeyUp);
            this.txtEmpId.LostFocus += new System.EventHandler(this.txtEmpId_LostFocus);
            // 
            // label5
            // 
            this.label5.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label5.Location = new System.Drawing.Point(1, 97);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(66, 20);
            this.label5.Text = "员工工号";
            // 
            // txtRpQty
            // 
            this.txtRpQty.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtRpQty.BackColor = System.Drawing.SystemColors.Info;
            this.txtRpQty.Enabled = false;
            this.txtRpQty.Location = new System.Drawing.Point(59, 70);
            this.txtRpQty.Name = "txtRpQty";
            this.txtRpQty.ReadOnly = true;
            this.txtRpQty.Size = new System.Drawing.Size(63, 21);
            this.txtRpQty.TabIndex = 104;
            this.txtRpQty.Text = "0";
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(1, 71);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(63, 20);
            this.label4.Text = "可报工量";
            // 
            // txtScrapedQty
            // 
            this.txtScrapedQty.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtScrapedQty.BackColor = System.Drawing.SystemColors.Info;
            this.txtScrapedQty.Enabled = false;
            this.txtScrapedQty.Location = new System.Drawing.Point(184, 68);
            this.txtScrapedQty.Name = "txtScrapedQty";
            this.txtScrapedQty.ReadOnly = true;
            this.txtScrapedQty.Size = new System.Drawing.Size(56, 21);
            this.txtScrapedQty.TabIndex = 101;
            this.txtScrapedQty.Text = "0";
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(128, 71);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(61, 20);
            this.label3.Text = "已报废量";
            // 
            // txtRacName
            // 
            this.txtRacName.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtRacName.BackColor = System.Drawing.SystemColors.Info;
            this.txtRacName.Enabled = false;
            this.txtRacName.Location = new System.Drawing.Point(59, 46);
            this.txtRacName.Name = "txtRacName";
            this.txtRacName.ReadOnly = true;
            this.txtRacName.Size = new System.Drawing.Size(181, 21);
            this.txtRacName.TabIndex = 98;
            // 
            // txtSwsId
            // 
            this.txtSwsId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtSwsId.BackColor = System.Drawing.SystemColors.Info;
            this.txtSwsId.Enabled = false;
            this.txtSwsId.Location = new System.Drawing.Point(59, 2);
            this.txtSwsId.Name = "txtSwsId";
            this.txtSwsId.Size = new System.Drawing.Size(178, 21);
            this.txtSwsId.TabIndex = 92;
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(1, 5);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(66, 20);
            this.label2.Text = "流程票号";
            // 
            // txtScrapQty
            // 
            this.txtScrapQty.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtScrapQty.Location = new System.Drawing.Point(75, 162);
            this.txtScrapQty.Name = "txtScrapQty";
            this.txtScrapQty.Size = new System.Drawing.Size(165, 21);
            this.txtScrapQty.TabIndex = 76;
            this.txtScrapQty.Text = "0";
            this.txtScrapQty.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtScrapQty_KeyUp);
            // 
            // label7
            // 
            this.label7.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label7.Location = new System.Drawing.Point(1, 164);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(64, 20);
            this.label7.Text = "报废量";
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(1, 50);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(63, 20);
            this.label1.Text = "工艺";
            // 
            // label8
            // 
            this.label8.Location = new System.Drawing.Point(1, 28);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(63, 20);
            this.label8.Text = "图号";
            // 
            // label11
            // 
            this.label11.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label11.Location = new System.Drawing.Point(1, 119);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(76, 20);
            this.label11.Text = "不合格内容";
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
            // lvScrap
            // 
            this.lvScrap.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvScrap.Location = new System.Drawing.Point(0, 0);
            this.lvScrap.Name = "lvScrap";
            this.lvScrap.Size = new System.Drawing.Size(240, 83);
            this.lvScrap.TabIndex = 30;
            // 
            // frmAllScrap
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.lvScrap);
            this.Controls.Add(this.panel2);
            this.Menu = this.mainMenu1;
            this.Name = "frmAllScrap";
            this.Text = "整体报废";
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtScrapQty;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.ListView lvScrap;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtSwsId;
        private System.Windows.Forms.TextBox txtRacName;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtScrapedQty;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtInspectorId;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox txtEmpId;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox txtRpQty;
        private System.Windows.Forms.ComboBox cbScrapReason;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox txtItmId;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.ComboBox cbScrapContent;
        private System.Windows.Forms.Label label11;
    }
}