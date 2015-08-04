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
    public partial class frmSemiOtherOut : Form
    {
        private List<ITM_MAIN_VIEW> itmList = new List<ITM_MAIN_VIEW>();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmSemiOtherOut()
        {
            InitializeComponent();
        }

        public static frmSemiOtherOut GetInstance(string title)
        {
            frmSemiOtherOut Own = new frmSemiOtherOut();
            Own.Text = title;

            Own.lvItms.View = View.Details;
            Own.lvItms.Activation = ItemActivation.Standard;
            Own.lvItms.FullRowSelect = true;
            Own.lvItms.CheckBoxes = false;
            Own.lvItms.Columns.Add("品号", 120, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("单位", 40, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("品名", 150, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("条码号", 120, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品号", 120, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("单位", 40, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品名", 150, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.LoadInOutReasons();

            Own.Show();
            Own.txtSwsBaco.Focus();
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
                string itmId = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[1].Text;
                decimal itmQty = decimal.Parse(lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[2].Text);

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

        private void LoadInOutReasons()
        {
            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = "OtherInvReason";

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("Basic/GetParas", "POST", "application/json", RequestStr);
                BasicResponse<PARA_MAIN, PARA_MAIN> Data = JsonConvert.DeserializeObject<BasicResponse<PARA_MAIN, PARA_MAIN>>(ResponseStr);

                if (Data.status == "0")
                {
                    List<PARA_MAIN> paramList = Data.dataList;

                    this.cbInOutReason.ValueMember = "id";
                    this.cbInOutReason.DisplayMember = "para_value";
                    this.cbInOutReason.Items.Clear();
                    for (int i = 0; i < paramList.Count; i++)
                    {
                        this.cbInOutReason.Items.Add(paramList[i]);
                    }
                    if (this.cbInOutReason.Items.Count > 0)
                    {
                        this.cbInOutReason.SelectedIndex = 0;
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

        private void txtSwsBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtSwsBaco.Text.Trim().Length == 0) return;

                for (int i = 0; i < this.bacoList.Count; i++)
                {
                    if (this.bacoList[i].ctn_baco == this.txtSwsBaco.Text.Trim())
                    {
                        MessageBox.Show("该流程票或合格证已经扫描！");
                        this.txtSwsBaco.Focus();
                        return;
                    }
                }
                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtSwsBaco.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtnBox", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        CTN_MAIN_VIEW ctn = Data.dataEntity;

                        if (String.IsNullOrEmpty(ctn.ctn_main_guid))
                        {
                            MessageBox.Show("未找到流程票信息！");
                            this.txtSwsBaco.Text = "";
                            this.txtSwsBaco.Focus();
                            return;
                        }

                        if (ctn.ctn_type != 12 && ctn.ctn_type != 13)
                        {
                            MessageBox.Show("不是流程票条码！");
                            this.txtSwsBaco.Text = "";
                            this.txtSwsBaco.Focus();
                            return;
                        }

                        if (String.IsNullOrEmpty(ctn.wh_id))
                        {
                            MessageBox.Show("流程票不在仓库里！");
                            this.txtSwsBaco.Text = "";
                            this.txtSwsBaco.Focus();
                            return;
                        }

                        this.txtSwsBaco.Text = ctn.itm_id;
                        this.txtSwsBaco.Enabled = false;
                        this.txtSwsBaco.Tag = ctn;

                        this.lblItmUnit.Text = ctn.itm_unit;

                        editCtn.ctn_baco = ctn.ctn_baco;
                        editCtn.itm_id = ctn.itm_id;
                        editCtn.itm_name = ctn.itm_name;
                        editCtn.itm_unit = ctn.itm_unit;
                        editCtn.lot_id = ctn.lot_id;
                        editCtn.wh_id = ctn.wh_id;

                        this.txtQty_Pre.Focus();
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
                    editCtn.itm_qty = Decimal.Parse(this.txtQty_Pre.Text);
                    this.doAddItm();
                }
                catch
                {
                    this.txtQty_Pre.Text = "";
                    this.txtQty_Pre.Focus();
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

        private void miOk_Click(object sender, EventArgs e)
        {
            if (this.bacoList.Count == 0)
            {
                return;
            }

            try
            {
                TRAN_DOC doc = new TRAN_DOC();
                doc.head.base_doc_id = "";
                doc.head.tran_reason_id = ((PARA_MAIN)this.cbInOutReason.SelectedItem).id;
                doc.head.wh_id = this.bacoList[0].wh_id;
                doc.head.in_out = 1;

                doc.head.need_syn = 1;
                //if (this.cbSynErp.Checked)
                //{
                //    doc.head.need_syn = 1;
                //}
                //else
                //{
                //    doc.head.need_syn = 0;
                //}


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

                BasicRequest<TRAN_DOC, TRAN_DOC> Request = new BasicRequest<TRAN_DOC, TRAN_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/AddSemiOtherOut", "POST", "application/json", RequestStr);
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
            this.txtSwsBaco.Enabled = true;
            this.txtSwsBaco.Text = "";
            this.txtSwsBaco.Tag = null;
            this.txtQty_Pre.Text = "";
            this.lblItmUnit.Text = "";

            editCtn = new CTN_MAIN_VIEW();

            this.txtSwsBaco.Focus();
        }

        public void clearAll()
        {
            itmList.Clear();
            bacoList.Clear();
            lvItms.Items.Clear();
            lvBaco.Items.Clear();

            clear();
        }
    }
}