using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Inventory.Frm
{
    public partial class frmTakeStock : Form
    {
        public frmTakeStock()
        {
            InitializeComponent();
        }

        public static frmTakeStock GetInstance(string title)
        {
            frmTakeStock Own = new frmTakeStock();
            Own.Text = title;
            Own.Show();
            return Own;
        }

        private void miBack_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("确定返回？", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.None, MessageBoxDefaultButton.Button2);
            if (result == DialogResult.Yes)
            {
                this.Close();
                this.Dispose();
            }
            else
            {
                result = DialogResult.OK;
            }
        }
    }
}