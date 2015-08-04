namespace Inventory.Frm
{
    partial class frmAllScrapList
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
            this.btnClear = new System.Windows.Forms.Button();
            this.txtSwsId = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.lvRPList = new System.Windows.Forms.ListView();
            this.lblWoId = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.miOK);
            this.mainMenu1.MenuItems.Add(this.miBack);
            // 
            // miOK
            // 
            this.miOK.Text = "确定";
            this.miOK.Click += new System.EventHandler(this.miOK_Click);
            // 
            // miBack
            // 
            this.miBack.Text = "返回";
            this.miBack.Click += new System.EventHandler(this.miBack_Click);
            // 
            // btnClear
            // 
            this.btnClear.Location = new System.Drawing.Point(185, 7);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(52, 20);
            this.btnClear.TabIndex = 87;
            this.btnClear.Text = "清空";
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtSwsId
            // 
            this.txtSwsId.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.txtSwsId.Location = new System.Drawing.Point(47, 6);
            this.txtSwsId.Name = "txtSwsId";
            this.txtSwsId.Size = new System.Drawing.Size(132, 21);
            this.txtSwsId.TabIndex = 86;
            this.txtSwsId.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtSwsId_KeyUp);
            this.txtSwsId.LostFocus += new System.EventHandler(this.txtSwsId_LostFocus);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.Location = new System.Drawing.Point(3, 9);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(50, 20);
            this.label2.Text = "流程票";
            // 
            // lvRPList
            // 
            this.lvRPList.Location = new System.Drawing.Point(3, 70);
            this.lvRPList.Name = "lvRPList";
            this.lvRPList.Size = new System.Drawing.Size(234, 195);
            this.lvRPList.TabIndex = 89;
            // 
            // lblWoId
            // 
            this.lblWoId.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.lblWoId.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Regular);
            this.lblWoId.Location = new System.Drawing.Point(5, 36);
            this.lblWoId.Name = "lblWoId";
            this.lblWoId.Size = new System.Drawing.Size(230, 31);
            // 
            // frmAllScrapList
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.lblWoId);
            this.Controls.Add(this.lvRPList);
            this.Controls.Add(this.btnClear);
            this.Controls.Add(this.txtSwsId);
            this.Controls.Add(this.label2);
            this.Menu = this.mainMenu1;
            this.Name = "frmAllScrapList";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.TextBox txtSwsId;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ListView lvRPList;
        private System.Windows.Forms.MenuItem miOK;
        private System.Windows.Forms.Label lblWoId;
        private System.Windows.Forms.MenuItem miBack;
    }
}