namespace Inventory.Frm
{
    partial class frmPartScrap
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
            this.lvScrap = new System.Windows.Forms.ListView();
            this.panel2 = new System.Windows.Forms.Panel();
            this.cbScrapReason = new System.Windows.Forms.ComboBox();
            this.label9 = new System.Windows.Forms.Label();
            this.txtInspectorId = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.txtEmpId = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.txtItmId = new System.Windows.Forms.TextBox();
            this.txtRacName = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.btnClear = new System.Windows.Forms.Button();
            this.txtSwsId = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtPartItmId = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtScrapQty = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.cbScrapContent = new System.Windows.Forms.ComboBox();
            this.label11 = new System.Windows.Forms.Label();
            this.cbRac = new System.Windows.Forms.ComboBox();
            this.label10 = new System.Windows.Forms.Label();
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
            // lvScrap
            // 
            this.lvScrap.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvScrap.Location = new System.Drawing.Point(0, 0);
            this.lvScrap.Name = "lvScrap";
            this.lvScrap.Size = new System.Drawing.Size(240, 85);
            this.lvScrap.TabIndex = 33;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.cbRac);
            this.panel2.Controls.Add(this.label10);
            this.panel2.Controls.Add(this.cbScrapContent);
            this.panel2.Controls.Add(this.label11);
            this.panel2.Controls.Add(this.cbScrapReason);
            this.panel2.Controls.Add(this.label9);
            this.panel2.Controls.Add(this.txtInspectorId);
            this.panel2.Controls.Add(this.label6);
            this.panel2.Controls.Add(this.txtEmpId);
            this.panel2.Controls.Add(this.label5);
            this.panel2.Controls.Add(this.txtItmId);
            this.panel2.Controls.Add(this.txtRacName);
            this.panel2.Controls.Add(this.label1);
            this.panel2.Controls.Add(this.btnClear);
            this.panel2.Controls.Add(this.txtSwsId);
            this.panel2.Controls.Add(this.label2);
            this.panel2.Controls.Add(this.txtPartItmId);
            this.panel2.Controls.Add(this.label3);
            this.panel2.Controls.Add(this.txtScrapQty);
            this.panel2.Controls.Add(this.label7);
            this.panel2.Controls.Add(this.label8);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 85);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 183);
            // 
            // cbScrapReason
            // 
            this.cbScrapReason.Location = new System.Drawing.Point(73, 114);
            this.cbScrapReason.Name = "cbScrapReason";
            this.cbScrapReason.Size = new System.Drawing.Size(165, 22);
            this.cbScrapReason.TabIndex = 125;
            this.cbScrapReason.SelectedIndexChanged += new System.EventHandler(this.cbScrapReason_SelectedIndexChanged);
            // 
            // label9
            // 
            this.label9.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label9.Location = new System.Drawing.Point(1, 118);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(64, 20);
            this.label9.Text = "产生原因";
            // 
            // txtInspectorId
            // 
            this.txtInspectorId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtInspectorId.Location = new System.Drawing.Point(178, 69);
            this.txtInspectorId.Name = "txtInspectorId";
            this.txtInspectorId.Size = new System.Drawing.Size(59, 21);
            this.txtInspectorId.TabIndex = 124;
            this.txtInspectorId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtInspectorId_KeyUp);
            // 
            // label6
            // 
            this.label6.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label6.Location = new System.Drawing.Point(123, 72);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(63, 20);
            this.label6.Text = "巡检工号";
            // 
            // txtEmpId
            // 
            this.txtEmpId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtEmpId.Location = new System.Drawing.Point(57, 69);
            this.txtEmpId.Name = "txtEmpId";
            this.txtEmpId.Size = new System.Drawing.Size(65, 21);
            this.txtEmpId.TabIndex = 123;
            this.txtEmpId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtEmpId_KeyUp);
            // 
            // label5
            // 
            this.label5.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label5.Location = new System.Drawing.Point(1, 73);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(66, 20);
            this.label5.Text = "员工工号";
            // 
            // txtItmId
            // 
            this.txtItmId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtItmId.BackColor = System.Drawing.SystemColors.Info;
            this.txtItmId.Enabled = false;
            this.txtItmId.Location = new System.Drawing.Point(57, 25);
            this.txtItmId.Name = "txtItmId";
            this.txtItmId.ReadOnly = true;
            this.txtItmId.Size = new System.Drawing.Size(181, 21);
            this.txtItmId.TabIndex = 117;
            // 
            // txtRacName
            // 
            this.txtRacName.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtRacName.BackColor = System.Drawing.SystemColors.Info;
            this.txtRacName.Enabled = false;
            this.txtRacName.Location = new System.Drawing.Point(57, 47);
            this.txtRacName.Name = "txtRacName";
            this.txtRacName.ReadOnly = true;
            this.txtRacName.Size = new System.Drawing.Size(65, 21);
            this.txtRacName.TabIndex = 116;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(1, 49);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(63, 20);
            this.label1.Text = "当前工艺";
            // 
            // btnClear
            // 
            this.btnClear.Location = new System.Drawing.Point(189, 4);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(48, 20);
            this.btnClear.TabIndex = 100;
            this.btnClear.Text = "清  空";
            // 
            // txtSwsId
            // 
            this.txtSwsId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtSwsId.Location = new System.Drawing.Point(57, 3);
            this.txtSwsId.Name = "txtSwsId";
            this.txtSwsId.Size = new System.Drawing.Size(134, 21);
            this.txtSwsId.TabIndex = 95;
            this.txtSwsId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSwsId_KeyUp);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(1, 6);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(64, 20);
            this.label2.Text = "流程票号";
            // 
            // txtPartItmId
            // 
            this.txtPartItmId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtPartItmId.Location = new System.Drawing.Point(73, 137);
            this.txtPartItmId.Name = "txtPartItmId";
            this.txtPartItmId.Size = new System.Drawing.Size(165, 21);
            this.txtPartItmId.TabIndex = 83;
            this.txtPartItmId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtPartItmId_KeyUp);
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.Location = new System.Drawing.Point(1, 139);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(64, 20);
            this.label3.Text = "报废子件";
            // 
            // txtScrapQty
            // 
            this.txtScrapQty.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtScrapQty.Location = new System.Drawing.Point(73, 159);
            this.txtScrapQty.Name = "txtScrapQty";
            this.txtScrapQty.Size = new System.Drawing.Size(165, 21);
            this.txtScrapQty.TabIndex = 76;
            this.txtScrapQty.Text = "0";
            this.txtScrapQty.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtScrapQty_KeyUp);
            // 
            // label7
            // 
            this.label7.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label7.Location = new System.Drawing.Point(1, 162);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(64, 20);
            this.label7.Text = "报废数量";
            // 
            // label8
            // 
            this.label8.Location = new System.Drawing.Point(1, 27);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(63, 20);
            this.label8.Text = "图号";
            // 
            // cbScrapContent
            // 
            this.cbScrapContent.Location = new System.Drawing.Point(73, 91);
            this.cbScrapContent.Name = "cbScrapContent";
            this.cbScrapContent.Size = new System.Drawing.Size(165, 22);
            this.cbScrapContent.TabIndex = 135;
            // 
            // label11
            // 
            this.label11.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label11.Location = new System.Drawing.Point(-1, 94);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(76, 20);
            this.label11.Text = "不合格内容";
            // 
            // cbRac
            // 
            this.cbRac.Location = new System.Drawing.Point(178, 46);
            this.cbRac.Name = "cbRac";
            this.cbRac.Size = new System.Drawing.Size(59, 22);
            this.cbRac.TabIndex = 138;
            // 
            // label10
            // 
            this.label10.Location = new System.Drawing.Point(123, 49);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(63, 20);
            this.label10.Text = "发生工艺";
            // 
            // frmPartScrap
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.lvScrap);
            this.Controls.Add(this.panel2);
            this.Menu = this.mainMenu1;
            this.Name = "frmPartScrap";
            this.Text = "子件报废";
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.ListView lvScrap;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtScrapQty;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox txtPartItmId;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtSwsId;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.TextBox txtItmId;
        private System.Windows.Forms.TextBox txtRacName;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.ComboBox cbScrapReason;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox txtInspectorId;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox txtEmpId;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.ComboBox cbScrapContent;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.ComboBox cbRac;
        private System.Windows.Forms.Label label10;
    }
}