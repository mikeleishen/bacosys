using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Inventory.Request;
using Common;
using Newtonsoft.Json;
using Inventory.Response;
using Inventory.Model;
using Entrance;

namespace Inventory.Frm
{
    public partial class frmPurReceive : Form
    {
        private List<PUR_ITM_VIEW> purItmList;
        private Dictionary<string, decimal> itmDicQty = new Dictionary<string, decimal>();

        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmPurReceive()
        {
            InitializeComponent();
        }

        public static frmPurReceive GetInstance(string title)
        {
            frmPurReceive Own = new frmPurReceive();

            Own.Text = title;

            Own.lvPurItms.View = View.Details;
            Own.lvPurItms.Activation = ItemActivation.Standard;
            Own.lvPurItms.FullRowSelect = true;
            Own.lvPurItms.CheckBoxes = false;
            Own.lvPurItms.Columns.Add("序号", 60, HorizontalAlignment.Left);
            Own.lvPurItms.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvPurItms.Columns.Add("未进数量", 100, HorizontalAlignment.Left);
            Own.lvPurItms.Columns.Add("本次进量", 100, HorizontalAlignment.Left);
            Own.lvPurItms.Columns.Add("单位", 60, HorizontalAlignment.Left);
            Own.lvPurItms.Columns.Add("品名", 200, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("条码号", 100, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("本次进量", 100, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("单位", 60, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品名", 200, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.Show();
            Own.txtPurId.Focus();
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

                for (int i = 0; i < purItmList.Count; i++)
                {
                    if (purItmList[i].itm_main_id == itmId)
                    {
                        if (purItmList[i].itm_got_qty >= itmQty)
                        {
                            purItmList[i].itm_got_qty = purItmList[i].itm_got_qty - itmQty;
                            break;
                        }
                        else
                        {
                            itmQty = itmQty - purItmList[i].itm_got_qty;
                            purItmList[i].itm_got_qty = 0;
                        }
                    }
                }

                purItmListBind();
                purBacoListBind();
                clear();
            }
        }

        private void txtPurId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                try
                {
                    if (this.txtPurId.Text.Trim().Length == 0)
                    {
                        this.txtPurId.Text = "";
                        this.txtPurId.Focus();
                        return;
                    }

                    this.txtPurId.Text = this.txtPurId.Text.Trim();

                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtPurId.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("inv/getPurById", "POST", "application/json", RequestStr);
                    BasicResponse<PUR_ITM_VIEW, PUR_ITM_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<PUR_ITM_VIEW, PUR_ITM_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        if (Data.dataList == null || Data.dataList.Count == 0)
                        {
                            MessageBox.Show("无收料信息！");
                            this.txtPurId.Text = "";
                            this.txtPurId.Focus();
                            return;
                        }

                        purItmList = Data.dataList;
                        purItmListBind();
                        this.txtBoxBaco.Focus();
                    }
                    else if (Data.status == "1")
                    {
                        MessageBox.Show(Data.info);
                        this.txtPurId.Text = "";
                        this.txtPurId.Focus();
                        return;
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

        private void txtBoxBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtBoxBaco.Text.Trim().Length == 0)
                {
                    this.txtBoxBaco.Text = "";
                    this.txtBoxBaco.Focus();
                    return;
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
                    bool hasItm = false;
                    for (int i = 0; i < purItmList.Count; i++)
                    {
                        if (purItmList[i].itm_main_id == box.itm_id)
                        {
                            hasItm = true;
                            break;
                        }
                    }

                    if (!hasItm)
                    {
                        MessageBox.Show("不存在该库位的采购物料！");
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
                    return;
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

            for (int i = 0; i < purItmList.Count; i++)
            {
                if (purItmList[i].itm_main_id == editCtn.itm_id)
                {
                    if ((purItmList[i].itm_qty - purItmList[i].itm_delivery_qty - purItmList[i].itm_got_qty) >= addQty)
                    {
                        purItmList[i].itm_got_qty = purItmList[i].itm_got_qty + addQty;
                        break;
                    }
                    else
                    {
                        addQty = addQty - purItmList[i].itm_qty - purItmList[i].itm_delivery_qty - purItmList[i].itm_got_qty;
                        purItmList[i].itm_got_qty = purItmList[i].itm_qty - purItmList[i].itm_delivery_qty;
                         
                    }
                }
            }

            bacoList.Add(editCtn);

            purItmListBind();
            purBacoListBind();

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

        public void purItmListBind()
        {
            lvPurItms.Items.Clear();
            if (purItmList == null) return;
            string[] cols = new string[6];

            for(int i=0;i<purItmList.Count;i++)
            {
                cols[0] = purItmList[i].pur_itm_seqno;
                cols[1] = purItmList[i].itm_main_id;
                cols[2] = (purItmList[i].itm_qty - purItmList[i].itm_delivery_qty)+"";
                cols[3] = purItmList[i].itm_got_qty + "" ;
                cols[4] = purItmList[i].itm_unit;
                cols[5] = purItmList[i].itm_name;

                ListViewItem lvItem = new ListViewItem(cols);
                lvPurItms.Items.Add(lvItem);
            }

            UpdateItmDicQty();
        }

        public void purBacoListBind()
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
            this.txtBoxBaco.Text = "";
            this.txtBoxBaco.Tag = null;
            this.lblItmId.Text = "";
            this.txtQty_Pre.Text = "";
            this.txtQty_After.Text = "";
            this.lblItmUnit.Text = "";
            this.editCtn = new CTN_MAIN_VIEW();
            this.txtBoxBaco.Focus();
        }

        public void clearAll()
        {
            this.txtPurId.Text = "";
            this.txtPurId.Tag = null;
            this.txtBoxBaco.Text = "";
            this.txtBoxBaco.Tag = null;
            this.lblItmId.Text = "";
            this.txtQty_Pre.Text = "";
            this.txtQty_After.Text = "";
            this.lblItmUnit.Text = "";

            purItmList.Clear();
            bacoList.Clear();
            itmDicQty.Clear();
            lvPurItms.Items.Clear();
            lvBaco.Items.Clear();

            this.txtPurId.Focus();
        }

        public void UpdateItmDicQty()
        {
            itmDicQty.Clear();
            for (int i = 0; i < this.purItmList.Count; i++)
            {
                if (itmDicQty.ContainsKey(this.purItmList[i].itm_main_id))
                {
                    itmDicQty[this.purItmList[i].itm_main_id] = itmDicQty[this.purItmList[i].itm_main_id] + this.purItmList[i].itm_qty - this.purItmList[i].itm_delivery_qty - this.purItmList[i].itm_got_qty;
                }
                else
                {
                    itmDicQty.Add(this.purItmList[i].itm_main_id,this.purItmList[i].itm_qty - this.purItmList[i].itm_delivery_qty - this.purItmList[i].itm_got_qty);
                }
            }
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            if (this.bacoList.Count == 0)
            {
                return;
            }
            
            try
            {
                TRAN_DOC doc = new TRAN_DOC();
                doc.head.base_doc_id = this.txtPurId.Text.Trim();
                doc.head.wh_id = this.bacoList[0].wh_id;
                doc.head.in_out = 0;

                foreach (PUR_ITM_VIEW pur_itm in purItmList)
                {
                    if (pur_itm.itm_got_qty > 0)
                    {
                        TRAN_ITM_BASE itm_base = new TRAN_ITM_BASE();
                        itm_base.base_seqno = pur_itm.pur_itm_seqno;
                        itm_base.itm_id = pur_itm.itm_main_id;
                        itm_base.itm_qty = pur_itm.itm_got_qty;
                        doc.body_itm_base.Add(itm_base);

                        TRAN_ITM itm = new TRAN_ITM();
                        itm.itm_id = pur_itm.itm_main_id;
                        itm.itm_qty = pur_itm.itm_got_qty;
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

                BasicRequest<TRAN_DOC, TRAN_DOC> Request = new BasicRequest<TRAN_DOC, TRAN_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/addpurtran", "POST", "application/json", RequestStr);
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