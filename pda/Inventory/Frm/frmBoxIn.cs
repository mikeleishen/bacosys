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
    public partial class frmBoxIn : Form
    {
        private List<ITM_MAIN_VIEW> itmList = new List<ITM_MAIN_VIEW>();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmBoxIn()
        {
            InitializeComponent();
        }

        public static frmBoxIn GetInstance(string title)
        {
            frmBoxIn Own = new frmBoxIn();
            Own.Text = title;

            Own.lvItm.View = View.Details;
            Own.lvItm.Activation = ItemActivation.Standard;
            Own.lvItm.FullRowSelect = true;
            Own.lvItm.CheckBoxes = false;
            Own.lvItm.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvItm.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvItm.Columns.Add("单位", 60, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("流程票", 120, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("单位", 60, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.Show();
            Own.txtCtnBaco.Focus();
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

                string itmId = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[1].Text;
                decimal itmQty = decimal.Parse(lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[2].Text);
                string ctnBaco = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[0].Text;
                for (int i = 0; i < bacoList.Count; i++)
                {
                    if (bacoList[i].ctn_baco == ctnBaco)
                    {
                        bacoList.Remove(bacoList[i]);
                        break;
                    }
                }

                for (int i = 0; i < itmList.Count; i++)
                {
                    if (itmList[i].itm_main_id == itmId)
                    {
                        itmList[i].itm_qty = itmList[i].itm_qty - itmQty;

                        if (itmList[i].itm_qty <= 0)
                        {
                            itmList.RemoveAt(i);
                        }
                    }
                }

                itmListBind();
                bacoListBind();
                clear();
            }
        }

        private void txtCtnBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtCtnBaco.Text.Trim().Length == 0) return;

                for (int i = 0; i < this.bacoList.Count; i++)
                {
                    if (this.bacoList[i].ctn_baco == this.txtCtnBaco.Text.Trim())
                    {
                        MessageBox.Show("该流程票已经扫描！");
                        this.txtCtnBaco.Text = "";
                        this.txtCtnBaco.Focus();
                        return;
                    }
                }
                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtCtnBaco.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtnBox", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        CTN_MAIN_VIEW ctn = Data.dataEntity;

                        if (String.IsNullOrEmpty(ctn.ctn_main_guid))
                        {
                            MessageBox.Show("未找到流程票信息！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        if (!String.IsNullOrEmpty(ctn.wh_loc_id))
                        {
                            MessageBox.Show("该流程票已经入库！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        if (ctn.base_type != 0)
                        {
                            MessageBox.Show("不是生产流程票，不可调拨入库！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        this.txtCtnBaco.Text = ctn.itm_id;
                        this.txtCtnBaco.Enabled = false;

                        this.txtQty.Text = ctn.itm_qty.ToString();

                        editCtn.ctn_baco = ctn.ctn_baco;
                        editCtn.itm_id = ctn.itm_id;
                        editCtn.itm_qty = ctn.itm_qty;
                        editCtn.itm_unit = ctn.itm_unit;
                        editCtn.lot_id = ctn.lot_id;

                        if (this.txtOutInvId.Text.Trim().Length == 0)
                        {
                            this.txtOutInvId.Focus();
                            //for 广隆
                            this.txtOutInvId.Text = "02";
                            this.txtOutInvId_KeyUp(this.txtOutInvId, new KeyEventArgs(Keys.Enter));
                        }
                        else
                        {
                            editCtn.f_wh_id = this.txtOutInvId.Text;
                            this.txtLocBaco.Focus();

                            /*
                            BasicRequest<string, string> RequestItem = new BasicRequest<string, string>();
                            RequestItem.token = HttpWebRequestProxy.token;
                            RequestItem.data_char = this.editCtn.itm_id;

                            string RequestItemStr = JsonConvert.SerializeObject(RequestItem);
                            string ResponseItemStr = HttpWebRequestProxy.PostRest("InvBasic/getItmById", "POST", "application/json", RequestItemStr);
                            BasicResponse<ITM_MAIN, ITM_MAIN> ItemData = JsonConvert.DeserializeObject<BasicResponse<ITM_MAIN, ITM_MAIN>>(ResponseItemStr);

                            if (ItemData.status == "0")
                            {
                                if (ItemData.dataEntity != null && !String.IsNullOrEmpty(ItemData.dataEntity.itm_main_id))
                                {
                                    this.txtLocBaco.Text = ItemData.dataEntity.def_loc_id;
                                    this.txtLocBaco_KeyUp(this.txtLocBaco, new KeyEventArgs(Keys.Enter));
                                }
                                else
                                {
                                    this.txtLocBaco.Focus();
                                }
                            }
                            else if (ItemData.status == "1")
                            {
                                throw new Exception(Data.info);
                            }
                            else if (ItemData.status == "2")
                            {
                                Login.ReLogin(ItemData.info);
                            }
                             */
                        }
                    }
                    else if (Data.status == "1")
                    {
                        throw new Exception(Data.info);
                    }
                    else if (Data.status == "2")
                    {
                        Login.ReLogin(Data.info);
                    }
                }
                catch (Exception ex) { MessageBox.Show(ex.Message); }
            }
        }

        private void txtOutInvId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtOutInvId.Text.Trim().Length == 0)
                {
                    this.txtOutInvId.Text = "";
                    this.txtOutInvId.Focus();
                    return;
                }

                this.txtOutInvId.Text = this.txtOutInvId.Text.Trim();
                editCtn.f_wh_id = this.txtOutInvId.Text;
                this.txtLocBaco.Focus();

                /*
                try
                {
                    BasicRequest<string, string> RequestItem = new BasicRequest<string, string>();
                    RequestItem.token = HttpWebRequestProxy.token;
                    RequestItem.data_char = this.editCtn.itm_id;

                    string RequestItemStr = JsonConvert.SerializeObject(RequestItem);
                    string ResponseItemStr = HttpWebRequestProxy.PostRest("InvBasic/getItmById", "POST", "application/json", RequestItemStr);
                    BasicResponse<ITM_MAIN, ITM_MAIN> ItemData = JsonConvert.DeserializeObject<BasicResponse<ITM_MAIN, ITM_MAIN>>(ResponseItemStr);

                    if (ItemData.status == "0")
                    {
                        if (ItemData.dataEntity != null && !String.IsNullOrEmpty(ItemData.dataEntity.itm_main_id))
                        {
                            this.txtLocBaco.Text = ItemData.dataEntity.def_loc_id;
                            this.txtLocBaco_KeyUp(this.txtLocBaco, new KeyEventArgs(Keys.Enter));
                        }
                        else
                        {
                            this.txtLocBaco.Focus();
                        }
                    }
                    else if (ItemData.status == "1")
                    {
                        throw new Exception(ItemData.info);
                    }
                    else if (ItemData.status == "2")
                    {
                        Login.ReLogin(ItemData.info);
                    }
                }
                catch (Exception ex) { MessageBox.Show(ex.Message); }
                */
            }
        }

        private void txtLocBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtLocBaco.Text.Trim().Length == 0)
                {
                    this.txtLocBaco.Text = "";
                    this.txtLocBaco.Focus();
                    return;
                }

                this.txtLocBaco.Text = this.txtLocBaco.Text.Trim();

                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtLocBaco.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtnBox", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        CTN_MAIN_VIEW ctn = Data.dataEntity;

                        if (String.IsNullOrEmpty(ctn.wh_id))
                        {
                            MessageBox.Show("库位不在仓库中！");
                            this.txtLocBaco.Text = "";
                            this.txtLocBaco.Focus();
                            this.txtLocBaco.Tag = null;
                            return;
                        }

                        this.editCtn.parent_ctn_baco = ctn.ctn_baco;
                        this.editCtn.wh_id = ctn.wh_id;
                        this.txtLocBaco.Tag = ctn;
                        this.txtQty.Focus();
                        this.txtQty.SelectionStart = this.txtQty.Text.Length;
                    }
                    else if (Data.status == "1")
                    {
                        MessageBox.Show(Data.info);
                        this.txtLocBaco.Text = "";
                        this.txtLocBaco.Focus();
                        this.txtLocBaco.Tag = null;
                    }
                    else if (Data.status == "2")
                    {
                        Login.ReLogin(Data.info);
                        this.txtLocBaco.Text = "";
                        this.txtLocBaco.Focus();
                        this.txtLocBaco.Tag = null;
                    }
                }
                catch(Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void txtQty_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtQty.Text.Trim().Length == 0)
                {
                    this.txtQty.Text = "";
                    this.txtQty.Focus();
                    return;
                }

                this.txtQty.Text = this.txtQty.Text.Trim();

                try
                {
                    this.editCtn.itm_qty = Decimal.Parse(this.txtQty.Text.Trim());
                    if (this.editCtn.itm_qty <= 0)
                    {
                        this.editCtn.itm_qty = 0;
                        this.txtQty.Text = "";
                        this.txtQty.Focus();
                        throw new Exception("请输入正确的数量信息！");
                    }
                    this.doAddItm();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void doAddItm()
        {
            bool inItmList = false;

            for (int i = 0; i < itmList.Count; i++)
            {
                if (itmList[i].itm_main_id == editCtn.itm_id)
                {
                    inItmList = true;
                    itmList[i].itm_qty = itmList[i].itm_qty + editCtn.itm_qty;
                    break;
                }
            }

            if (!inItmList)
            {
                ITM_MAIN_VIEW tempItm = new ITM_MAIN_VIEW();
                tempItm.itm_main_id = editCtn.itm_id;
                tempItm.itm_name = editCtn.itm_name;
                tempItm.itm_qty = editCtn.itm_qty;
                tempItm.itm_unit = editCtn.itm_unit;

                this.itmList.Add(tempItm);
            }

            bacoList.Add(editCtn);

            itmListBind();
            bacoListBind();

            clear();
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            if (this.bacoList.Count == 0)
            {
                return;
            }

            if (MessageBox.Show("扫描入库箱数：" + this.bacoList.Count.ToString(), "确认", MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1) == DialogResult.Yes)
            {
                try
                {
                    TRAN_DOC doc = new TRAN_DOC();
                    doc.head.wh_id = this.bacoList[0].wh_id;
                    doc.head.in_out = 0;

                    foreach (ITM_MAIN_VIEW itm in itmList)
                    {
                        if (itm.itm_qty > 0)
                        {
                            TRAN_ITM tranItm = new TRAN_ITM();
                            tranItm.itm_id = itm.itm_main_id;
                            tranItm.itm_qty = itm.itm_qty;
                            doc.body_itm.Add(tranItm);
                        }
                    }
                    foreach (CTN_MAIN_VIEW baco in bacoList)
                    {
                        if (doc.head.wh_id != baco.wh_id)
                        {
                            MessageBox.Show("所有条码必须在同一仓库内！");
                            return;
                        }

                        TRAN_BACO tranBaco = new TRAN_BACO();
                        tranBaco.ctn_baco = baco.ctn_baco;
                        tranBaco.tran_qty = baco.itm_qty;
                        tranBaco.wh_id = baco.wh_id;
                        tranBaco.f_wh_id = baco.f_wh_id;
                        tranBaco.lot_id = baco.lot_id;
                        tranBaco.parent_baco = baco.parent_ctn_baco;
                        tranBaco.itm_id = baco.itm_id;
                        doc.body_baco.Add(tranBaco);
                    }

                    BasicRequest<TRAN_DOC, TRAN_DOC> Request = new BasicRequest<TRAN_DOC, TRAN_DOC>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_entity = doc;

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("inv/addSemiIn", "POST", "application/json", RequestStr);
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
            lvItm.Items.Clear();
            if (itmList == null) return;
            string[] cols = new string[3];

            for (int i = 0; i < itmList.Count; i++)
            {
                cols[0] = itmList[i].itm_main_id;
                cols[1] = itmList[i].itm_qty.ToString();
                cols[2] = itmList[i].itm_unit;

                ListViewItem lvItem = new ListViewItem(cols);
                lvItm.Items.Add(lvItem);
            }
        }

        public void bacoListBind()
        {
            this.lvBaco.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[4];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].ctn_baco;
                cols[1] = bacoList[i].itm_id;
                cols[2] = bacoList[i].itm_qty + "";
                cols[3] = bacoList[i].itm_unit + "";

                ListViewItem lvItem = new ListViewItem(cols);
                lvBaco.Items.Add(lvItem);
            }
        }

        public void clear()
        {
            this.txtCtnBaco.Text = "";
            this.txtCtnBaco.Tag = null;
            this.txtCtnBaco.Enabled = true;
            this.txtQty.Text = "";
            this.txtLocBaco.Text = "";
            this.txtLocBaco.Tag = null;
            editCtn = new CTN_MAIN_VIEW();

            this.txtCtnBaco.Focus();
        }

        public void clearAll()
        {
            clear();
            this.itmList.Clear();
            this.bacoList.Clear();
            this.lvBaco.Items.Clear();
            this.lvItm.Items.Clear();
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            this.clear();
        }

        private void tabControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.tabControl.SelectedIndex == 0)
            {
                this.txtCtnBaco.Focus();
                this.txtCtnBaco.SelectionStart = this.txtCtnBaco.Text.Trim().Length;
            }
        }
    }
}