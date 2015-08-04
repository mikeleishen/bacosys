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
    public partial class frmMove : Form
    {
        private List<ITM_MAIN_VIEW> itmList = new List<ITM_MAIN_VIEW>();
        private List<CTN_MOVE_VIEW> bacoList = new List<CTN_MOVE_VIEW>();
        private CTN_MOVE_VIEW editCtn = new CTN_MOVE_VIEW();

        public frmMove()
        {
            InitializeComponent();
        }

        public static frmMove GetInstance(string title)
        {
            frmMove Own = new frmMove();
            Own.Text = title;

            Own.lvItms.View = View.Details;
            Own.lvItms.Activation = ItemActivation.Standard;
            Own.lvItms.FullRowSelect = true;
            Own.lvItms.CheckBoxes = false;
            Own.lvItms.Columns.Add("品号", 80, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("单位", 40, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("品名", 100, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("从库位", 80, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("到库位", 80, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品号", 80, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("单位", 40, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品名", 100, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.Show();
            return Own;
        }

        private void lvMenuItem_Click(object sender, EventArgs e)
        {
            string action = (sender as MenuItem).Text;
            if (action == "删除")
            {
                if (lvBaco.SelectedIndices.Count < 1)
                {
                    return;
                }

                string itmFromBaco = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[0].Text;
                string itmId = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[1].Text;
                decimal itmQty = decimal.Parse(lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[2].Text);

                for (int i = 0; i < bacoList.Count; i++)
                {
                    if (bacoList[i].from_ctn_baco == itmFromBaco)
                    {
                        bacoList.Remove(bacoList[i]);
                        break;
                    }
                }

                for (int i = 0; i < itmList.Count; i++)
                {
                    if (itmList[i].itm_main_id == itmId)
                    {
                        if (itmList[i].itm_qty >= itmQty)
                        {
                            itmList[i].itm_qty = itmList[i].itm_qty - itmQty;
                            if (itmList[i].itm_qty == 0) itmList.RemoveAt(i);
                            break;
                        }
                        else
                        {
                            itmQty = itmQty - itmList[i].itm_qty;
                            itmList.RemoveAt(i);
                            i--;
                        }
                    }
                }

                itmListBind();
                bacoListBind();
                clear();
            }
        }

        private void txtLocFrom_KeyUp(object sender, KeyEventArgs e)
        {
            //回车键
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtLocFrom.Text.Trim().Length == 0)
                {
                    this.txtLocFrom.Text = "";
                    this.txtLocFrom.Focus();
                }

                this.txtLocFrom.Text = this.txtLocFrom.Text.Trim();

                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtLocFrom.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    CTN_MAIN_VIEW box = Data.dataEntity;

                    this.txtLocFrom.Tag = box;
                    this.lblItmId.Text = box.itm_id;
                    this.lblItmUnit.Text = box.itm_unit;

                    editCtn.from_ctn_main_guid = box.ctn_main_guid;
                    editCtn.from_ctn_type = box.ctn_type;
                    editCtn.from_ctn_baco = box.ctn_baco;
                    editCtn.itm_id = box.itm_id;
                    editCtn.itm_name = box.itm_name;
                    editCtn.itm_unit = box.itm_unit;

                    this.txtLocTo.Focus();
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    this.txtLocFrom.Text = "";
                    this.txtLocFrom.Focus();
                    this.txtLocFrom.Tag = null;
                    this.lblItmId.Text = "";
                    this.lblItmUnit.Text = "";
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    this.txtLocFrom.Text = "";
                    this.txtLocFrom.Focus();
                    this.txtLocFrom.Tag = null;
                    this.lblItmId.Text = "";
                    this.lblItmUnit.Text = "";
                }
            }
        }

        private void txtLocTo_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtLocTo.Text.Trim().Length == 0)
                {
                    this.txtLocTo.Text = "";
                    this.txtLocTo.Focus();
                }

                this.txtLocTo.Text = this.txtLocTo.Text.Trim();

                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtLocTo.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    CTN_MAIN_VIEW box = Data.dataEntity;
                    if (box.itm_id != this.lblItmId.Text)
                    {
                        MessageBox.Show("库位绑定物料信息不一致，不可移动！");
                        this.txtLocTo.Text = "";
                        this.txtLocTo.Focus();
                        return;
                    }

                    this.txtLocTo.Tag = box;

                    editCtn.to_ctn_main_guid = box.ctn_main_guid;
                    editCtn.to_ctn_type = box.ctn_type;
                    editCtn.to_ctn_baco = box.ctn_baco;

                    this.txtQty_Pre.Focus();
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    this.txtLocTo.Text = "";
                    this.txtLocTo.Focus();
                    this.txtLocTo.Tag = null;
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    this.txtLocTo.Text = "";
                    this.txtLocTo.Focus();
                    this.txtLocTo.Tag = null;
                }
            }
        }

        private void txtQty_Pre_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtQty_Pre.Text.Trim().Length == 0)
                {
                    this.txtQty_Pre.Text = "";
                    this.txtQty_Pre.Focus();
                }

                this.txtQty_Pre.Text = this.txtQty_Pre.Text.Trim();

                try
                {
                    this.txtQty_Pre.Text = Int32.Parse(this.txtQty_Pre.Text.Trim()).ToString();
                    this.txtQty_After.Focus();
                }
                catch
                {
                    this.txtQty_Pre.Text = "";
                    this.txtQty_Pre.Focus();
                }
            }
        }

        private void txtQty_After_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtQty_After.Text.Trim().Length == 0)
                {
                    this.txtQty_After.Text = "0";
                }

                this.txtQty_After.Text = this.txtQty_After.Text.Trim();

                try
                {
                    this.txtQty_After.Text = Int32.Parse(this.txtQty_After.Text.Trim()).ToString();

                    editCtn.itm_qty = Decimal.Parse(this.txtQty_Pre.Text + "." + this.txtQty_After.Text);
                    this.doAddItm();
                }
                catch
                {
                    this.txtQty_After.Text = "";
                    this.txtQty_After.Focus();
                }
            }
        }
        
         
        private void doAddItm()
        {
            decimal addQty = editCtn.itm_qty;

            if (itmList.Count == 0)
            {
                ITM_MAIN_VIEW tempItm = new ITM_MAIN_VIEW();
                tempItm.itm_main_id = editCtn.itm_id;
                tempItm.itm_qty = editCtn.itm_qty;
                tempItm.itm_unit = editCtn.itm_unit;
                tempItm.itm_name = editCtn.itm_name;
                itmList.Add(tempItm);
            }
            else
            {
                int itmListCount = itmList.Count;
                for (int i = 0; i < itmListCount; i++)
                {
                    if (itmList[i].itm_main_id == editCtn.itm_id)
                    {
                        //
                        itmList[i].itm_qty = itmList[i].itm_qty + editCtn.itm_qty;
                        break;
                    }
                    else
                    {  
                        ITM_MAIN_VIEW tempItm = new ITM_MAIN_VIEW();
                        tempItm.itm_main_id = editCtn.itm_id;
                        tempItm.itm_qty = editCtn.itm_qty;
                        tempItm.itm_unit = editCtn.itm_unit;
                        tempItm.itm_name = editCtn.itm_name;
                        itmList.Add(tempItm);
                        break;
                    }
                }
            }

            bacoList.Add(editCtn);

            itmListBind();
            bacoListBind();

            clear();
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

        public void itmListBind()
        {
            lvItms.Items.Clear();
            if (itmList == null) return;
            string[] cols = new string[4];

            for (int i = 0; i < itmList.Count; i++)
            {
                cols[0] = itmList[i].itm_main_id;
                cols[1] = itmList[i].itm_qty + "";
                cols[2] = itmList[i].itm_unit;
                cols[3] = itmList[i].itm_name;

                ListViewItem lvItem = new ListViewItem(cols);
                lvItms.Items.Add(lvItem);
            }
        }

        public void bacoListBind()
        {
            this.lvBaco.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[6];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].from_ctn_baco;
                cols[1] = bacoList[i].to_ctn_baco;
                cols[2] = bacoList[i].itm_id;
                cols[3] = bacoList[i].itm_qty + "";
                cols[4] = bacoList[i].itm_unit + "";
                cols[5] = bacoList[i].itm_name;

                ListViewItem lvItem = new ListViewItem(cols);
                lvBaco.Items.Add(lvItem);
            }
        }

        public void clear()
        {
            this.txtLocFrom.Text = "";
            this.txtLocFrom.Tag = null;
            this.txtLocTo.Text = "";
            this.txtLocTo.Tag = null;
            this.lblItmId.Text = "";
            this.txtQty_Pre.Text = "";
            this.txtQty_After.Text = "";
            this.lblItmUnit.Text = "";

            editCtn = new CTN_MOVE_VIEW();

            this.txtLocFrom.Focus();
        }

        public void clearAll()
        {
            itmList.Clear();
            bacoList.Clear();
            lvItms.Items.Clear();
            lvBaco.Items.Clear();

            clear();
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            try
            {
                TRAN_DOC doc = new TRAN_DOC();
                doc.head.base_doc_id = "";
                foreach (ITM_MAIN_VIEW itmInList in itmList)
                {
                    TRAN_ITM itm = new TRAN_ITM();
                    itm.itm_id = itmInList.itm_main_id;
                    itm.itm_qty = itmInList.itm_qty;
                    doc.body_itm.Add(itm);
                }
                foreach (CTN_MOVE_VIEW move_baco in bacoList)
                {
                    TRAN_BACO baco = new TRAN_BACO();
                    baco.ctn_baco = move_baco.from_ctn_baco;
                    baco.parent_baco = move_baco.to_ctn_baco;
                    baco.tran_qty = move_baco.itm_qty;
                    doc.body_baco.Add(baco);
                }

                BasicRequest<TRAN_DOC, TRAN_DOC> Request = new BasicRequest<TRAN_DOC, TRAN_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/addmoveboxtran", "POST", "application/json", RequestStr);
                BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    MessageBox.Show("操作成功！");
                    clearAll();
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}