namespace Inventory.Frm
{
    partial class frmSemiWork
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
            this.miOk = new System.Windows.Forms.MenuItem();
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.miBack = new System.Windows.Forms.MenuItem();
            this.panel3 = new System.Windows.Forms.Panel();
            this.btnTab4 = new System.Windows.Forms.Button();
            this.btnTab3 = new System.Windows.Forms.Button();
            this.btnTab2 = new System.Windows.Forms.Button();
            this.btnTab1 = new System.Windows.Forms.Button();
            this.tabControl = new System.Windows.Forms.TabControl();
            this.tabOther = new System.Windows.Forms.TabPage();
            this.lvRbaItms = new System.Windows.Forms.ListView();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtQty_Pre = new System.Windows.Forms.TextBox();
            this.txtBoxBaco = new System.Windows.Forms.TextBox();
            this.lblGdBaco = new System.Windows.Forms.Label();
            this.lblUnit = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.txtRbaId = new System.Windows.Forms.TextBox();
            this.tabBaco = new System.Windows.Forms.TabPage();
            this.lvBaco = new System.Windows.Forms.ListView();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.lvDoc = new System.Windows.Forms.ListView();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.lvPos = new System.Windows.Forms.ListView();
            this.btnOk = new System.Windows.Forms.Button();
            this.btnBack = new System.Windows.Forms.Button();
            this.panel3.SuspendLayout();
            this.tabControl.SuspendLayout();
            this.tabOther.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabBaco.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.SuspendLayout();
            // 
            // miOk
            // 
            this.miOk.Text = "确认";
            this.miOk.Click += new System.EventHandler(this.miOk_Click);
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.miOk);
            this.mainMenu1.MenuItems.Add(this.miBack);
            // 
            // miBack
            // 
            this.miBack.Text = "返回";
            this.miBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.btnTab4);
            this.panel3.Controls.Add(this.btnTab3);
            this.panel3.Controls.Add(this.btnTab2);
            this.panel3.Controls.Add(this.btnTab1);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel3.Location = new System.Drawing.Point(0, 0);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(240, 45);
            // 
            // btnTab4
            // 
            this.btnTab4.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.btnTab4.Location = new System.Drawing.Point(185, 9);
            this.btnTab4.Name = "btnTab4";
            this.btnTab4.Size = new System.Drawing.Size(50, 30);
            this.btnTab4.TabIndex = 3;
            this.btnTab4.Text = "位置";
            this.btnTab4.Click += new System.EventHandler(this.btnTab4_Click);
            // 
            // btnTab3
            // 
            this.btnTab3.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.btnTab3.Location = new System.Drawing.Point(110, 9);
            this.btnTab3.Name = "btnTab3";
            this.btnTab3.Size = new System.Drawing.Size(72, 30);
            this.btnTab3.TabIndex = 2;
            this.btnTab3.Text = "领料单";
            this.btnTab3.Click += new System.EventHandler(this.btnTab3_Click);
            // 
            // btnTab2
            // 
            this.btnTab2.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.btnTab2.Location = new System.Drawing.Point(57, 8);
            this.btnTab2.Name = "btnTab2";
            this.btnTab2.Size = new System.Drawing.Size(50, 30);
            this.btnTab2.TabIndex = 1;
            this.btnTab2.Text = "条码";
            this.btnTab2.Click += new System.EventHandler(this.btnTab2_Click);
            // 
            // btnTab1
            // 
            this.btnTab1.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Regular);
            this.btnTab1.Location = new System.Drawing.Point(4, 8);
            this.btnTab1.Name = "btnTab1";
            this.btnTab1.Size = new System.Drawing.Size(50, 30);
            this.btnTab1.TabIndex = 0;
            this.btnTab1.Text = "物料";
            this.btnTab1.Click += new System.EventHandler(this.btnTab1_Click);
            // 
            // tabControl
            // 
            this.tabControl.Controls.Add(this.tabOther);
            this.tabControl.Controls.Add(this.tabBaco);
            this.tabControl.Controls.Add(this.tabPage1);
            this.tabControl.Controls.Add(this.tabPage2);
            this.tabControl.Dock = System.Windows.Forms.DockStyle.None;
            this.tabControl.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Regular);
            this.tabControl.Location = new System.Drawing.Point(0, 45);
            this.tabControl.Name = "tabControl";
            this.tabControl.SelectedIndex = 0;
            this.tabControl.Size = new System.Drawing.Size(240, 175);
            this.tabControl.TabIndex = 23;
            // 
            // tabOther
            // 
            this.tabOther.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabOther.Controls.Add(this.lvRbaItms);
            this.tabOther.Controls.Add(this.panel2);
            this.tabOther.Controls.Add(this.panel1);
            this.tabOther.Location = new System.Drawing.Point(0, 0);
            this.tabOther.Name = "tabOther";
            this.tabOther.Size = new System.Drawing.Size(240, 150);
            this.tabOther.Text = "物料";
            // 
            // lvRbaItms
            // 
            this.lvRbaItms.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvRbaItms.Location = new System.Drawing.Point(0, 27);
            this.lvRbaItms.Name = "lvRbaItms";
            this.lvRbaItms.Size = new System.Drawing.Size(240, 99);
            this.lvRbaItms.TabIndex = 42;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel2.Controls.Add(this.txtQty_Pre);
            this.panel2.Controls.Add(this.txtBoxBaco);
            this.panel2.Controls.Add(this.lblGdBaco);
            this.panel2.Controls.Add(this.lblUnit);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel2.Location = new System.Drawing.Point(0, 126);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(240, 24);
            // 
            // txtQty_Pre
            // 
            this.txtQty_Pre.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtQty_Pre.Location = new System.Drawing.Point(153, 2);
            this.txtQty_Pre.Name = "txtQty_Pre";
            this.txtQty_Pre.Size = new System.Drawing.Size(44, 21);
            this.txtQty_Pre.TabIndex = 41;
            this.txtQty_Pre.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtQty_Pre_KeyUp);
            // 
            // txtBoxBaco
            // 
            this.txtBoxBaco.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtBoxBaco.Location = new System.Drawing.Point(47, 2);
            this.txtBoxBaco.Name = "txtBoxBaco";
            this.txtBoxBaco.Size = new System.Drawing.Size(105, 21);
            this.txtBoxBaco.TabIndex = 39;
            this.txtBoxBaco.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtBoxBaco_KeyUp);
            // 
            // lblGdBaco
            // 
            this.lblGdBaco.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblGdBaco.Location = new System.Drawing.Point(3, 5);
            this.lblGdBaco.Name = "lblGdBaco";
            this.lblGdBaco.Size = new System.Drawing.Size(59, 20);
            this.lblGdBaco.Text = "流程票";
            // 
            // lblUnit
            // 
            this.lblUnit.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblUnit.Location = new System.Drawing.Point(197, 4);
            this.lblUnit.Name = "lblUnit";
            this.lblUnit.Size = new System.Drawing.Size(59, 20);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.txtRbaId);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(240, 27);
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(4, 6);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(45, 20);
            this.label1.Text = "领料单";
            // 
            // txtRbaId
            // 
            this.txtRbaId.Location = new System.Drawing.Point(50, 3);
            this.txtRbaId.Name = "txtRbaId";
            this.txtRbaId.Size = new System.Drawing.Size(187, 21);
            this.txtRbaId.TabIndex = 38;
            this.txtRbaId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtRbaId_KeyUp);
            // 
            // tabBaco
            // 
            this.tabBaco.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.tabBaco.Controls.Add(this.lvBaco);
            this.tabBaco.Location = new System.Drawing.Point(0, 0);
            this.tabBaco.Name = "tabBaco";
            this.tabBaco.Size = new System.Drawing.Size(240, 150);
            this.tabBaco.Text = "条码";
            // 
            // lvBaco
            // 
            this.lvBaco.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvBaco.Location = new System.Drawing.Point(0, 0);
            this.lvBaco.Name = "lvBaco";
            this.lvBaco.Size = new System.Drawing.Size(240, 150);
            this.lvBaco.TabIndex = 4;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.lvDoc);
            this.tabPage1.Location = new System.Drawing.Point(0, 0);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Size = new System.Drawing.Size(240, 150);
            this.tabPage1.Text = "领料单";
            // 
            // lvDoc
            // 
            this.lvDoc.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvDoc.Location = new System.Drawing.Point(0, 0);
            this.lvDoc.Name = "lvDoc";
            this.lvDoc.Size = new System.Drawing.Size(240, 150);
            this.lvDoc.TabIndex = 6;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.lvPos);
            this.tabPage2.Location = new System.Drawing.Point(0, 0);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Size = new System.Drawing.Size(240, 150);
            this.tabPage2.Text = "位置";
            // 
            // lvPos
            // 
            this.lvPos.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lvPos.Location = new System.Drawing.Point(0, 0);
            this.lvPos.Name = "lvPos";
            this.lvPos.Size = new System.Drawing.Size(240, 150);
            this.lvPos.TabIndex = 8;
            // 
            // btnOk
            // 
            this.btnOk.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Bold);
            this.btnOk.Location = new System.Drawing.Point(35, 226);
            this.btnOk.Name = "btnOk";
            this.btnOk.Size = new System.Drawing.Size(72, 39);
            this.btnOk.TabIndex = 25;
            this.btnOk.Text = "确认";
            this.btnOk.Click += new System.EventHandler(this.miOk_Click);
            // 
            // btnBack
            // 
            this.btnBack.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Bold);
            this.btnBack.Location = new System.Drawing.Point(125, 226);
            this.btnBack.Name = "btnBack";
            this.btnBack.Size = new System.Drawing.Size(72, 39);
            this.btnBack.TabIndex = 26;
            this.btnBack.Text = "返回";
            this.btnBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // frmSemiWork
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.btnBack);
            this.Controls.Add(this.btnOk);
            this.Controls.Add(this.tabControl);
            this.Controls.Add(this.panel3);
            this.Menu = this.mainMenu1;
            this.Name = "frmSemiWork";
            this.Text = "半成品生产领料";
            this.panel3.ResumeLayout(false);
            this.tabControl.ResumeLayout(false);
            this.tabOther.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.tabBaco.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabPage2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem miOk;
        private System.Windows.Forms.MainMenu mainMenu1;
        private System.Windows.Forms.MenuItem miBack;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.TabControl tabControl;
        private System.Windows.Forms.TabPage tabOther;
        private System.Windows.Forms.ListView lvRbaItms;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtQty_Pre;
        private System.Windows.Forms.TextBox txtBoxBaco;
        private System.Windows.Forms.Label lblGdBaco;
        private System.Windows.Forms.Label lblUnit;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtRbaId;
        private System.Windows.Forms.TabPage tabBaco;
        private System.Windows.Forms.ListView lvBaco;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.ListView lvDoc;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.ListView lvPos;
        private System.Windows.Forms.Button btnTab1;
        private System.Windows.Forms.Button btnTab3;
        private System.Windows.Forms.Button btnTab2;
        private System.Windows.Forms.Button btnTab4;
        private System.Windows.Forms.Button btnOk;
        private System.Windows.Forms.Button btnBack;
    }
}