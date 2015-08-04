using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Inventory.Model;
using Inventory.Request;
using Common;
using Newtonsoft.Json;
using Inventory.Response;
using Entrance;

namespace Inventory.Frm
{
    public partial class frmSearchItem2 : Form
    {
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();

        public frmSearchItem2()
        {
            InitializeComponent();
        }

        public static frmSearchItem2 GetInstance(string title)
        {
            frmSearchItem2 Own = new frmSearchItem2();
            Own.Text = title;

            Own.lvCtns.View = View.Details;
            Own.lvCtns.Activation = ItemActivation.Standard;
            Own.lvCtns.FullRowSelect = true;
            Own.lvCtns.CheckBoxes = false;
            Own.lvCtns.Columns.Add("数量", 80, HorizontalAlignment.Left);
            Own.lvCtns.Columns.Add("库位条码", 80, HorizontalAlignment.Left);
            Own.lvCtns.Columns.Add("库区代码", 80, HorizontalAlignment.Left);
            Own.lvCtns.Columns.Add("仓库代码", 80, HorizontalAlignment.Left);

            Own.Show();
            return Own;
        }

        private void txtItmId_KeyUp(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyData == Keys.Enter)
                {
                    if (this.txtItmId.Text.Trim().Length == 0) return;

                    showResults(this.txtItmId.Text.Trim());
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

       
        private void showResults(string itmId)
        {
            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = itmId;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/getBoxListByItmId", "POST", "application/json", RequestStr);
                BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    bacoList = Data.dataList;
                    bacoListBind();
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    this.txtItmId.Focus();
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    this.txtItmId.Focus();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        public void bacoListBind()
        {
            this.lvCtns.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[4];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].itm_qty + "";
                cols[1] = bacoList[i].ctn_baco;
                cols[2] = bacoList[i].wh_area_id;
                cols[3] = bacoList[i].wh_id;

                ListViewItem lvItem = new ListViewItem(cols);
                lvCtns.Items.Add(lvItem);
            }
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