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
using Newtonsoft.Json;
using Inventory.Response;
using Common;
using Entrance;

namespace Inventory.Frm
{
    public partial class frmSemiPurRec : Form
    {
        private List<PUR_BACO_VIEW> purItmList = new List<PUR_BACO_VIEW>();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmSemiPurRec()
        {
            InitializeComponent();
        }

        public static frmSemiPurRec GetInstance(string title)
        {
            frmSemiPurRec Own = new frmSemiPurRec();

            Own.Text = title;

            Own.lvPurItms.View = View.Details;
            Own.lvPurItms.Activation = ItemActivation.Standard;
            Own.lvPurItms.FullRowSelect = true;
            Own.lvPurItms.CheckBoxes = false;
            Own.lvPurItms.Columns.Add("采购单号", 120, HorizontalAlignment.Left);
            Own.lvPurItms.Columns.Add("序号", 60, HorizontalAlignment.Left);
            Own.lvPurItms.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvPurItms.Columns.Add("数量", 100, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("条码号", 120, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("数量", 100, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.txtLotId.Text = DateTime.Now.ToString("yy-MM-dd");
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

                bacoList.RemoveAt(lvBaco.SelectedIndices[0]);
                RefreshPurItmList();

                purItmListBind();
                purBacoListBind();
                clear();
            }
        }

        private void RefreshPurItmList()
        {
            purItmList.Clear();

            bool inList = false;
            for (int i = 0; i < bacoList.Count; i++)
            {
                inList = false;
                for (int j = 0; j < purItmList.Count; j++)
                {
                    if (bacoList[i].base_id == purItmList[j].pur_id &&
                        bacoList[i].base_seqno == purItmList[j].pur_seqno && 
                        bacoList[i].itm_id == purItmList[j].itm_id)
                    {
                        purItmList[j].itm_qty = purItmList[j].itm_qty + bacoList[i].itm_qty;
                        inList = true;
                        break;
                    }
                }

                if (!inList)
                {
                    PUR_BACO_VIEW tempData = new PUR_BACO_VIEW();
                    tempData.itm_id = bacoList[i].itm_id;
                    tempData.itm_qty = bacoList[i].itm_qty;
                    tempData.pur_id = bacoList[i].base_id;
                    tempData.pur_seqno = bacoList[i].base_seqno;

                    purItmList.Add(tempData);
                }
            }
        }

        private void txtCtnBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtCtnBaco.Text.Trim().Length == 0)
                {
                    this.txtCtnBaco.Text = "";
                    this.txtCtnBaco.Focus();
                    return;
                }
                this.txtCtnBaco.Text = this.txtCtnBaco.Text.Trim();

                for (int i = 0; i < this.bacoList.Count; i++)
                {
                    if (this.bacoList[i].ctn_baco == this.txtCtnBaco.Text)
                    {
                        MessageBox.Show("该流程票已经扫入列表！");
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

                        if (ctn.ctn_type != 12)
                        {
                            MessageBox.Show("不是流程票条码！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        if (ctn.base_type != 5)
                        {
                            MessageBox.Show("不是采购流程票条码！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        if (!String.IsNullOrEmpty(ctn.wh_id))
                        {
                            MessageBox.Show("采购流程票条码，已经在仓库：" + ctn.wh_id + " 里！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        if (ctn.updated_by != "subwo")
                        {
                            MessageBox.Show("该采购流程票已收货！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        this.txtCtnBaco.Text = ctn.itm_id;
                        this.txtCtnBaco.Tag = ctn;

                        editCtn = ctn; 
                        this.txtQty_Pre.Text = ctn.itm_qty.ToString();

                        if (!String.IsNullOrEmpty(editCtn.def_loc_id))
                        {
                            this.txtLocBaco.Text = editCtn.def_loc_id;
                            this.txtLocBaco_KeyUp(this.txtLocBaco, new KeyEventArgs(Keys.Enter));
                        }
                        else
                        {
                            this.txtLocBaco.Focus();
                            this.txtLocBaco.SelectionStart = this.txtLocBaco.Text.Length;
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

                        this.txtLotId.Focus();
                        this.txtLotId.SelectionStart = this.txtLotId.Text.Length;
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
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void txtLotId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtLotId.Text.Trim().Length == 0)
                {
                    this.txtLotId.Text = "";
                    this.txtLotId.Focus();
                    return;
                }
                this.txtLotId.Text = this.txtLotId.Text.Trim();

                try
                {
                    editCtn.lot_id = this.txtLotId.Text;

                    this.txtQty_Pre.SelectionStart = this.txtQty_Pre.Text.Length;
                    this.txtQty_Pre.Focus();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
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
                    editCtn.itm_qty = Decimal.Parse(this.txtQty_Pre.Text);

                    this.doAddItm();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                    this.txtQty_Pre.Text = "";
                    this.txtQty_Pre.Focus();
                }
            }
        }

        private void doAddItm()
        {
            if (String.IsNullOrEmpty(editCtn.ctn_baco))
            {
                return;
            }

            try
            {
                this.editCtn.itm_qty = Decimal.Parse(this.txtQty_Pre.Text.Trim());
                bacoList.Add(editCtn);
                RefreshPurItmList();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return;
            }
            purItmListBind();
            purBacoListBind();

            clear();
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            if (this.bacoList.Count == 0)
            {
                return;
            }

            if (MessageBox.Show("扫描入库箱数："+this.bacoList.Count.ToString(), "确认", MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1) == DialogResult.Yes)
            {
                try
                {
                    TRAN_DOC doc = new TRAN_DOC();
                    doc.head.base_doc_id = "";
                    doc.head.wh_id = this.bacoList[0].wh_id;
                    doc.head.in_out = 0;

                    foreach (PUR_BACO_VIEW pur_itm in purItmList)
                    {
                        if (pur_itm.itm_qty > 0)
                        {
                            TRAN_ITM itm = new TRAN_ITM();
                            itm.itm_id = pur_itm.itm_id;
                            itm.itm_qty = pur_itm.itm_qty;
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
                        baco.lot_id = pur_baco.lot_id;
                        baco.itm_id = pur_baco.itm_id;
                        baco.parent_baco = pur_baco.parent_ctn_baco;
                        baco.wh_id = pur_baco.wh_id;
                        baco.base_type = pur_baco.base_type;
                        baco.base_id = pur_baco.base_id;
                        baco.base_seqno = pur_baco.base_seqno;

                        doc.body_baco.Add(baco);
                    }

                    BasicRequest<TRAN_DOC, TRAN_DOC> Request = new BasicRequest<TRAN_DOC, TRAN_DOC>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_entity = doc;

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("inv/addSemiPurTran", "POST", "application/json", RequestStr);
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

        public void purItmListBind()
        {
            lvPurItms.Items.Clear();
            if (purItmList == null) return;
            string[] cols = new string[4];

            for (int i = 0; i < purItmList.Count; i++)
            {
                cols[0] = purItmList[i].pur_id;
                cols[1] = purItmList[i].pur_seqno;
                cols[2] = purItmList[i].itm_id;
                cols[3] = purItmList[i].itm_qty + "";

                ListViewItem lvItem = new ListViewItem(cols);
                lvPurItms.Items.Add(lvItem);
            }
        }

        public void purBacoListBind()
        {
            this.lvBaco.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[3];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].ctn_baco;
                cols[1] = bacoList[i].itm_id;
                cols[2] = bacoList[i].itm_qty + "";

                ListViewItem lvItem = new ListViewItem(cols);
                lvBaco.Items.Add(lvItem);
            }
        }

        public void clear()
        {
            this.txtCtnBaco.Text = "";
            this.txtCtnBaco.Tag = null;
            this.txtLocBaco.Text = "";
            this.txtLocBaco.Tag = null;
            this.txtQty_Pre.Text = "";
            this.editCtn = new CTN_MAIN_VIEW();
            this.txtCtnBaco.Focus();
        }

        public void clearAll()
        {
            clear();

            purItmList.Clear();
            bacoList.Clear();
            lvPurItms.Items.Clear();
            lvBaco.Items.Clear();

            this.txtCtnBaco.Text = "";
            this.txtCtnBaco.Tag = null;
            this.txtCtnBaco.Focus();
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            this.clear();
        }
    }
}