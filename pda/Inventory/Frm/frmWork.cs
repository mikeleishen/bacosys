using System;
using System.Collections.Generic;
using System.Windows.Forms;
using Inventory.Model;
using Inventory.Request;
using Common;
using Newtonsoft.Json;
using Inventory.Response;
using Entrance;
using System.Collections;

namespace Inventory.Frm
{
    public partial class frmWork : Form
    {
        private List<ITM_MAIN_VIEW> itmList = new List<ITM_MAIN_VIEW>();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private List<string> docList = new List<string>();
        private List<CTN_MAIN_VIEW> posList = new List<CTN_MAIN_VIEW>();

        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmWork()
        {
            InitializeComponent();
        }

        public static frmWork GetInstance(string title)
        {
            frmWork Own = new frmWork();
            Own.Text = title;

            Own.lvRbaItms.View = View.Details;
            Own.lvRbaItms.Activation = ItemActivation.Standard;
            Own.lvRbaItms.FullRowSelect = true;
            Own.lvRbaItms.CheckBoxes = false;
            
            Own.lvRbaItms.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvRbaItms.Columns.Add("应领数量", 100, HorizontalAlignment.Left);
            Own.lvRbaItms.Columns.Add("实领数量", 100, HorizontalAlignment.Left);
            Own.lvRbaItms.Columns.Add("单位", 60, HorizontalAlignment.Left);
            Own.lvRbaItms.Columns.Add("品名", 200, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("条码号", 150, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("本次进量", 100, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("单位", 60, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品名", 200, HorizontalAlignment.Left);

            Own.lvDoc.View = View.Details;
            Own.lvDoc.Activation = ItemActivation.Standard;
            Own.lvDoc.FullRowSelect = true;
            Own.lvDoc.CheckBoxes = false;
            Own.lvDoc.Columns.Add("领料单号", 300, HorizontalAlignment.Left);

            Own.lvPos.View = View.Details;
            Own.lvPos.Activation = ItemActivation.Standard;
            Own.lvPos.FullRowSelect = true;
            Own.lvPos.CheckBoxes = false;
            Own.lvPos.Columns.Add("库位",150,HorizontalAlignment.Left);
            Own.lvPos.Columns.Add("库区",100,HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.Show();
            Own.txtRbaId.Focus();
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
                string itmBaco = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[0].Text;

                for (int i = 0; i < bacoList.Count; i++)
                {
                    if (bacoList[i].ctn_baco == itmBaco)
                    {
                        bacoList.Remove(bacoList[i]);
                        break;
                    }
                }

                for (int i = 0; i < itmList.Count; i++)
                {
                    if (itmList[i].itm_main_id == itmId)
                    {
                        itmList[i].itm_got_qty = itmList[i].itm_got_qty - itmQty;
                    }
                }

                rbaItmListBind();
                rbaBacoListBind();
                clear();
            }
        }

        private void txtRbaId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtRbaId.Text.Trim().Length == 0)
                {
                    this.txtRbaId.Text = "";
                    this.txtRbaId.Focus();
                    return;
                }

                this.txtRbaId.Text = this.txtRbaId.Text.Trim();

                if (this.docList.Contains(this.txtRbaId.Text))
                {
                    MessageBox.Show("该领料单已经扫码！");
                    this.txtRbaId.Text = "";
                    this.txtRbaId.Focus();
                    return;
                }

                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtRbaId.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/getRbaById", "POST", "application/json", RequestStr);
                BasicResponse<RBA_ITM_VIEW, RBA_ITM_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<RBA_ITM_VIEW, RBA_ITM_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    if (Data.dataList != null && Data.dataList.Count > 0)
                    {
                        this.docList.Add(this.txtRbaId.Text);

                        bool isInList = false;
                        for (int i = 0; i < Data.dataList.Count; i++)
                        {
                            isInList = false;
                            for (int j = 0; j < this.itmList.Count; j++)
                            {
                                if (Data.dataList[i].itm_main_id == itmList[j].itm_main_id)
                                {
                                    isInList = true;
                                    itmList[j].itm_qty = itmList[j].itm_qty + Data.dataList[i].itm_qty;
                                    break;
                                }
                            }

                            if (!isInList)
                            {
                                ITM_MAIN_VIEW itm = new ITM_MAIN_VIEW();
                                itm.itm_got_qty = 0;
                                itm.itm_main_id = Data.dataList[i].itm_main_id;
                                itm.itm_name = Data.dataList[i].itm_name;
                                itm.itm_qty = Data.dataList[i].itm_qty;
                                itm.itm_unit = Data.dataList[i].itm_unit;

                                this.itmList.Add(itm);

                                //获取位置信息
                                BasicRequest<string, string> Request2 = new BasicRequest<string, string>();
                                Request2.token = HttpWebRequestProxy.token;
                                Request2.data_char = itm.itm_main_id;

                                string RequestStr2 = JsonConvert.SerializeObject(Request2);
                                string ResponseStr2 = HttpWebRequestProxy.PostRest("InvBasic/getCtnListByItmId", "POST", "application/json", RequestStr2);
                                BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data2 = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr2);

                                bool isInList2 = false;
                                for (int j = 0; j < Data2.dataList.Count; j++)
                                {
                                    isInList2 = false;
                                    for (int n = 0; n < posList.Count; n++)
                                    {
                                        if (Data2.dataList[j].ctn_baco == posList[n].ctn_baco)
                                        {
                                            isInList2 = true;
                                            break;
                                        }
                                    }

                                    if (!isInList2)
                                    {
                                        posList.Add(Data2.dataList[j]);
                                    }
                                }
                            }
                        }
                    }

                    rbaItmListBind();
                    docListBind();
                    posBacoListBind();
                    this.txtRbaId.Text = "";
                    this.txtRbaId.Focus();
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    this.txtRbaId.Text = "";
                    this.txtRbaId.Focus();
                    return;
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                }
            }
        }

        private void txtBoxBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtBoxBaco.Text.Trim().Length == 0)
                {
                    this.txtBoxBaco.Text = "";
                    this.txtBoxBaco.Focus();
                }

                this.txtBoxBaco.Text = this.txtBoxBaco.Text.Trim();

                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtBoxBaco.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtnBox", "POST", "application/json", RequestStr);
                BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    CTN_MAIN_VIEW box = Data.dataEntity;

                    if (box==null||box.ctn_baco.Length==0)
                    {
                        MessageBox.Show("库位信息未找到！");
                        return;
                    }

                    if (box!=null&&box.itm_qty == 0)
                    {
                        MessageBox.Show("库位物料数为0！");
                        this.txtBoxBaco.Text = "";
                        this.txtBoxBaco.Focus();
                        this.txtBoxBaco.Tag = null;
                        this.lblItmId.Text = "";
                        this.lblItmUnit.Text = "";
                        return;
                    }

                    bool hasItm = false;
                    for (int i = 0; i < itmList.Count; i++)
                    {
                        if (itmList[i].itm_main_id == box.itm_id)
                        {
                            hasItm = true;
                            break;
                        }
                    }

                    if (!hasItm)
                    {
                        MessageBox.Show("不存在该库位的领料！");
                        this.txtBoxBaco.Text = "";
                        this.txtBoxBaco.Focus();
                        this.txtBoxBaco.Tag = null;
                        this.lblItmId.Text = "";
                        this.lblItmUnit.Text = "";
                        return;
                    }

                    if (String.IsNullOrEmpty(box.wh_id))
                    {
                        MessageBox.Show("库位不在仓库中！");
                        this.txtBoxBaco.Text = "";
                        this.txtBoxBaco.Focus();
                        this.txtBoxBaco.Tag = null;
                        this.lblItmId.Text = "";
                        this.lblItmUnit.Text = "";
                        return;
                    }

                    this.txtBoxBaco.Tag = box;
                    this.lblItmId.Text = box.itm_id;
                    this.lblItmUnit.Text = box.itm_unit;

                    editCtn.ctn_main_guid = box.ctn_main_guid;
                    editCtn.ctn_type = box.ctn_type;
                    editCtn.ctn_baco = box.ctn_baco;
                    editCtn.itm_id = box.itm_id;
                    editCtn.itm_name = box.itm_name;
                    editCtn.itm_unit = box.itm_unit;
                    editCtn.wh_id = box.wh_id;

                    this.txtQty_Pre.Focus();
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    this.txtBoxBaco.Text = "";
                    this.txtBoxBaco.Focus();
                    this.txtBoxBaco.Tag = null;
                    this.lblItmId.Text = "";
                    this.lblItmUnit.Text = "";
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    this.txtBoxBaco.Text = "";
                    this.txtBoxBaco.Focus();
                    this.txtBoxBaco.Tag = null;
                    this.lblItmId.Text = "";
                    this.lblItmUnit.Text = "";
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
            for (int i = 0; i < this.itmList.Count; i++)
            {
                if (this.itmList[i].itm_main_id == editCtn.itm_id)
                {
                    if ((this.itmList[i].itm_qty - this.itmList[i].itm_got_qty) < editCtn.itm_qty)
                    {
                        MessageBox.Show("实际数量不能大于应领料数量！");
                        this.txtQty_Pre.Text = "";
                        this.txtQty_After.Text = "";
                        this.txtQty_Pre.Focus();
                        return;
                    }

                    if (((CTN_MAIN_VIEW)this.txtBoxBaco.Tag).itm_qty < editCtn.itm_qty)
                    {
                        MessageBox.Show("库位中剩余数量不够！");
                        this.txtQty_Pre.Focus();
                        return;
                    }

                    this.itmList[i].itm_got_qty = this.itmList[i].itm_got_qty + editCtn.itm_qty;
                    bacoList.Add(editCtn);
                    rbaItmListBind();
                    rbaBacoListBind();

                    clear();
                    break;
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

        public void rbaItmListBind()
        {
            lvRbaItms.Items.Clear();
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
                lvRbaItms.Items.Add(lvItem);
            }
        }

        public void docListBind()
        {
            lvDoc.Items.Clear();
            if (docList == null) return;
            string[] cols = new string[1];

            for (int i = 0; i < docList.Count; i++)
            {
                cols[0] = docList[i];

                ListViewItem lvItem = new ListViewItem(cols);
                lvDoc.Items.Add(lvItem);
            }
        }

        public void rbaBacoListBind()
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

        public void posBacoListBind()
        {
            this.lvPos.Items.Clear();
            if (posList == null) return;
            string[] cols = new string[2];

            for (int i = 0; i < posList.Count; i++)
            {
                cols[0] = posList[i].ctn_baco;
                cols[1] = posList[i].wh_area_id;

                ListViewItem lvItem = new ListViewItem(cols);
                lvPos.Items.Add(lvItem);
            }
        }

        public void clear()
        {
            this.txtBoxBaco.Text = "";
            this.txtBoxBaco.Tag = null;
            this.lblItmId.Text = "";
            this.txtQty_Pre.Text = "";
            this.txtQty_After.Text = "";
            this.lblItmUnit.Text = "";
            this.txtBoxBaco.Focus();

            editCtn = new CTN_MAIN_VIEW();
        }

        public void clearAll()
        {
            clear();

            this.txtRbaId.Text = "";
            this.txtRbaId.Tag = null;

            itmList.Clear();
            bacoList.Clear();
            docList.Clear();
            posList.Clear();
            lvRbaItms.Items.Clear();
            lvBaco.Items.Clear();
            lvDoc.Items.Clear();
            lvPos.Items.Clear();

            this.txtRbaId.Focus();
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            if (this.bacoList.Count == 0)
            {
                return;
            }

            try
            {
                for (int i = 0; i < this.itmList.Count; i++)
                {
                    if (this.itmList[i].itm_got_qty != this.itmList[i].itm_qty)
                    {
                        MessageBox.Show("料号为：" + this.itmList[i].itm_main_id + " 领料不匹配");
                        return;
                    }
                }

                TRAN_DOC doc = new TRAN_DOC();
                doc.head.wh_id = this.bacoList[0].wh_id;
                doc.head.in_out = 1;

                foreach (ITM_MAIN_VIEW rba_itm in itmList)
                {
                    TRAN_ITM_BASE itm_base = new TRAN_ITM_BASE();
                    itm_base.itm_id = rba_itm.itm_main_id;
                    itm_base.itm_qty = rba_itm.itm_got_qty;
                    doc.body_itm_base.Add(itm_base);

                    if (rba_itm.itm_got_qty > 0)
                    {
                        TRAN_ITM itm = new TRAN_ITM();
                        itm.itm_id = rba_itm.itm_main_id;
                        itm.itm_qty = rba_itm.itm_got_qty;
                        doc.body_itm.Add(itm);
                    }
                }
                foreach (CTN_MAIN_VIEW pur_baco in bacoList)
                {
                    if (doc.head.wh_id != pur_baco.wh_id)
                    {
                        MessageBox.Show("所有条码必须在同一仓库内！");
                        return;
                    }

                    TRAN_BACO baco = new TRAN_BACO();
                    baco.ctn_baco = pur_baco.ctn_baco;
                    baco.tran_qty = pur_baco.itm_qty;
                    doc.body_baco.Add(baco);
                }
                foreach (string baseDocId in docList)
                {
                    TRAN_BASE_DOC baseDoc = new TRAN_BASE_DOC();
                    baseDoc.base_doc_id = baseDocId;
                    doc.doc_base.Add(baseDoc);
                }

                BasicRequest<TRAN_DOC, TRAN_DOC> Request = new BasicRequest<TRAN_DOC, TRAN_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/AddBasicRbaTran", "POST", "application/json", RequestStr);
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