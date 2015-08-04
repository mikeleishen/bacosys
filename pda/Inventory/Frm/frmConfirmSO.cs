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
    public partial class frmConfirmSO : Form
    {
        private List<ITM_MAIN_VIEW> itmList = new List<ITM_MAIN_VIEW>();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmConfirmSO()
        {
            InitializeComponent();
        }

        public static frmConfirmSO GetInstance(string title)
        {
            frmConfirmSO Own = new frmConfirmSO();
            Own.Text = title;

            Own.lvItm.View = View.Details;
            Own.lvItm.Activation = ItemActivation.Standard;
            Own.lvItm.FullRowSelect = true;
            Own.lvItm.CheckBoxes = false;
            Own.lvItm.Columns.Add("品号", 120, HorizontalAlignment.Left);
            Own.lvItm.Columns.Add("单据数量", 100, HorizontalAlignment.Left);
            Own.lvItm.Columns.Add("拣货数量", 100, HorizontalAlignment.Left);
            Own.lvItm.Columns.Add("单位", 60, HorizontalAlignment.Left);
            Own.lvItm.Columns.Add("品名", 200, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("条码号", 120, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品号", 120, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("单位", 60, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品名", 200, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.Show();
            Own.txtSoId.Focus();
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

                string itmBaco = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[0].Text;

                for (int i = 0; i < bacoList.Count; i++)
                {
                    if (bacoList[i].ctn_baco == itmBaco)
                    {
                        bacoList.Remove(bacoList[i]);
                        break;
                    }
                }

                UpdateItmList();

                itmListBind();
                bacoListBind();
                clear();
            }
        }

        private void UpdateItmList()
        {
            try
            {
                BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Request = new BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_list = bacoList;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getItmListByBacoList", "POST", "application/json", RequestStr);
                BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW>>(ResponseStr);

                bool isInList = false;

                if (Data.status == "0")
                {
                    for (int i = 0; i < itmList.Count; i++)
                    {
                        itmList[i].itm_got_qty = 0;
                    }

                    for (int i = 0; i < Data.dataList.Count; i++)
                    {
                        isInList = false;
                        for (int j = 0; j < itmList.Count; j++)
                        {
                            if (itmList[j].itm_main_id == Data.dataList[i].itm_main_id)
                            {
                                isInList = true;
                                itmList[j].itm_got_qty = itmList[j].itm_got_qty + Data.dataList[i].itm_qty;
                            }
                        }

                        if (!isInList)
                        {
                            MessageBox.Show("条码 " + this.editCtn.ctn_baco + " 中有不在销货单上的内容，不能销货！");
                            for (int j = 0; j < this.bacoList.Count; j++)
                            {
                                if (bacoList[j].ctn_baco == editCtn.ctn_baco)
                                {
                                    bacoList.RemoveAt(j);
                                    this.txtBaco.Text = "";
                                    this.txtBaco.Focus();
                                    break;
                                }
                            }

                            UpdateItmList();
                        }
                    }

                    itmListBind();
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

        private void txtSoId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtSoId.Text.Trim().Length == 0) return;
                try
                {
                    BasicRequest<String, String> Request = new BasicRequest<String, String>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtSoId.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("inv/getJskKea", "POST", "application/json", RequestStr);
                    BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        this.txtSoId.Tag = this.txtSoId.Text.Trim();
                        itmList = Data.dataList;
                        itmListBind();
                        this.txtBaco.Focus();
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

        private void txtBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtBaco.Text.Trim().Length == 0) return;

                try
                {
                    BasicRequest<String, String> Request = new BasicRequest<String, String>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtBaco.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        if (Data.dataEntity.ctn_type != 7 && Data.dataEntity.ctn_type != 20 && Data.dataEntity.ctn_type != 13)
                        {
                            MessageBox.Show("该条码不支持扫码出货！");
                            this.txtBaco.Text = "";
                            this.txtBaco.Focus();
                            return;
                        }

                        if (String.IsNullOrEmpty(Data.dataEntity.wh_loc_id))
                        {
                            MessageBox.Show("条码不在库位上！");
                            this.txtBaco.Text = "";
                            this.txtBaco.Focus();
                            return;
                        }

                        if (this.bacoList.Count>0)
                        {
                            if (Data.dataEntity.wh_guid != this.bacoList[0].wh_guid)
                            MessageBox.Show("条码不在相同仓库中！");
                            this.txtBaco.Text = "";
                            this.txtBaco.Focus();
                            return;
                        }

                        BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Request2 = new BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW>();
                        List<CTN_MAIN_VIEW> r2List = new List<CTN_MAIN_VIEW>();
                        r2List.Add(Data.dataEntity);
                        Request2.token = HttpWebRequestProxy.token;
                        Request2.data_list = r2List;

                        string RequestStr2 = JsonConvert.SerializeObject(Request2);
                        string ResponseStr2 = HttpWebRequestProxy.PostRest("InvBasic/getItmListByBacoList", "POST", "application/json", RequestStr2);
                        BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW> Data2 = JsonConvert.DeserializeObject<BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW>>(ResponseStr2);

                        bool isInList = false;

                        if (Data2.status == "0")
                        {
                            for (int i = 0; i < Data2.dataList.Count; i++)
                            {
                                isInList = false;
                                for (int j = 0; j < itmList.Count; j++)
                                {
                                    if (itmList[j].itm_main_id == Data2.dataList[i].itm_main_id)
                                    {
                                        isInList = true;
                                        break;
                                    }
                                }

                                if (!isInList)
                                {
                                    MessageBox.Show("条码 " + this.txtBaco.Text.Trim() + " 中有不在销货单上的内容，不能销货！");
                                    this.txtBaco.Text = "";
                                    this.txtBaco.Focus();
                                    return;
                                }
                            }

                            itmListBind();
                        }
                        else if (Data.status == "1")
                        {
                            MessageBox.Show(Data.info);
                        }
                        else if (Data.status == "2")
                        {
                            Login.ReLogin(Data.info);
                        }

                        this.editCtn = Data.dataEntity;

                        if (this.editCtn.ctn_type == 13)
                        {
                            this.txtQty.Focus();
                        }
                        else
                        {
                            this.bacoList.Add(Data.dataEntity);
                            this.bacoListBind();
                            UpdateItmList();
                            clear();
                        }
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
                    if (editCtn.itm_qty < Decimal.Parse(this.txtQty.Text))
                    {
                        MessageBox.Show("合格证内剩余量为："+editCtn.itm_qty.ToString()+"！");
                        this.txtQty.Text = "";
                        this.txtQty.Focus();
                        return;
                    }
                    this.txtQty.Text = Int32.Parse(this.txtQty.Text.Trim()).ToString();
                    editCtn.itm_qty = Decimal.Parse(this.txtQty.Text);

                    this.bacoList.Add(editCtn);
                    this.bacoListBind();
                    UpdateItmList();
                    clear();
                }
                catch
                {
                    this.txtQty.Text = "";
                    this.txtQty.Focus();
                }
            }
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            for (int i = 0; i < this.itmList.Count; i++)
            {
                if (itmList[i].itm_got_qty != itmList[i].itm_qty)
                {
                    MessageBox.Show("品号：" + itmList[i] .itm_main_id+ " ,实际销货数量与单据数量不符，请修改！");
                    return;
                }
            }

            try
            {
                TRAN_DOC doc = new TRAN_DOC();
                doc.head.base_doc_id = this.txtSoId.Tag.ToString();
                doc.head.wh_id = this.bacoList[0].wh_id;
                doc.head.in_out = 1;

                foreach (ITM_MAIN_VIEW itmInList in itmList)
                {
                    TRAN_ITM itm = new TRAN_ITM();
                    itm.itm_id = itmInList.itm_main_id;
                    itm.itm_qty = itmInList.itm_qty;
                    doc.body_itm.Add(itm);
                }
                foreach (CTN_MAIN_VIEW baco in bacoList)
                {
                    TRAN_BACO tranBaco = new TRAN_BACO();
                    tranBaco.ctn_baco = baco.ctn_baco;
                    tranBaco.tran_qty = baco.itm_qty;
                    tranBaco.itm_id = baco.itm_id;
                    doc.body_baco.Add(tranBaco);
                }

                if (doc.body_itm.Count == 0)
                {
                    MessageBox.Show("缺少出库内容！");
                    return;
                }

                BasicRequest<TRAN_DOC, CTN_MAIN_VIEW> Request = new BasicRequest<TRAN_DOC, CTN_MAIN_VIEW>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;
                Request.data_list = this.bacoList;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/doOutInv", "POST", "application/json", RequestStr);
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
            string[] cols = new string[5];

            for (int i = 0; i < itmList.Count; i++)
            {
                cols[0] = itmList[i].itm_main_id;
                cols[1] = itmList[i].itm_qty + "";
                cols[2] = itmList[i].itm_got_qty + "";
                cols[3] = itmList[i].itm_unit;
                cols[4] = itmList[i].itm_name;

                ListViewItem lvItem = new ListViewItem(cols);
                lvItm.Items.Add(lvItem);
            }
        }

        public void bacoListBind()
        {
            this.lvBaco.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[5];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].ctn_baco;
                cols[1] = bacoList[i].itm_id;
                cols[2] = bacoList[i].itm_qty + "";
                cols[3] = bacoList[i].itm_unit + "";
                cols[4] = bacoList[i].itm_name;

                ListViewItem lvItem = new ListViewItem(cols);
                lvBaco.Items.Add(lvItem);
            }
        }

        public void clear()
        {
            this.txtBaco.Enabled = true;
            this.txtBaco.Text = "";
            this.txtBaco.Tag = null;
            this.txtQty.Text = "";

            editCtn = new CTN_MAIN_VIEW();

            this.txtBaco.Focus();
        }

        public void clearAll()
        {
            clear();

            itmList.Clear();
            bacoList.Clear();
            lvItm.Items.Clear();
            lvBaco.Items.Clear();
            this.txtSoId.Focus();
        }
    }
}