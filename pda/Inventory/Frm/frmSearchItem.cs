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
    public partial class frmSearchItem : Form
    {
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();

        public frmSearchItem()
        {
            InitializeComponent();
        }

        public static frmSearchItem GetInstance(string title)
        {
            frmSearchItem Own = new frmSearchItem();
            Own.Text = title;

            Own.lvCtns.View = View.Details;
            Own.lvCtns.Activation = ItemActivation.Standard;
            Own.lvCtns.FullRowSelect = true;
            Own.lvCtns.CheckBoxes = false;
            Own.lvCtns.Columns.Add("数量", 80, HorizontalAlignment.Left);
            Own.lvCtns.Columns.Add("库位条码", 80, HorizontalAlignment.Left);
            Own.lvCtns.Columns.Add("库区代码", 100, HorizontalAlignment.Left);

            Own.Show();
            return Own;
        }

        private void txtLoc_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                this.lvCtns.Focus();
            }
        }

        private void txtLoc_LostFocus(object sender, EventArgs e)
        {
            if (this.txtLoc.Text.Trim().Length == 0)
            {
                this.txtLoc.Text = "";
                this.txtLoc.Focus();
            }

            this.txtLoc.Text = this.txtLoc.Text.Trim();

            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtLoc.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    CTN_MAIN_VIEW box = Data.dataEntity;

                    this.txtLoc.Tag = box;
                    this.lblItmId.Text = box.itm_id;
                    this.lvCtns.Focus();

                    showResults(box.ctn_baco);
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    this.txtLoc.Text = "";
                    this.txtLoc.Focus();
                    this.txtLoc.Tag = null;
                    this.lblItmId.Text = "";
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    this.txtLoc.Text = "";
                    this.txtLoc.Focus();
                    this.txtLoc.Tag = null;
                    this.lblItmId.Text = "";
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void showResults(string ctnBaco)
        {
            BasicRequest<string, string> Request = new BasicRequest<string, string>();
            Request.token = HttpWebRequestProxy.token;
            Request.data_char = ((CTN_MAIN_VIEW)this.txtLoc.Tag).itm_id;
            Request.data_char2 = ((CTN_MAIN_VIEW)this.txtLoc.Tag).wh_guid;

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
                this.txtLoc.Focus();
            }
            else if (Data.status == "2")
            {
                Login.ReLogin(Data.info);
                this.txtLoc.Focus();
            }
        }

        public void bacoListBind()
        {
            this.lvCtns.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[3];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].itm_qty + "";
                cols[1] = bacoList[i].ctn_baco;
                cols[2] = bacoList[i].wh_area_id;

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