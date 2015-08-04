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
    public partial class frmFGoodsIn : Form
    {
        private List<ITM_MAIN_VIEW> itmList = new List<ITM_MAIN_VIEW>();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();


        public frmFGoodsIn()
        {
            InitializeComponent();
        }

        public static frmFGoodsIn GetInstance(string title)
        {
            frmFGoodsIn Own = new frmFGoodsIn();
            Own.Text = title;

            Own.lvItms.View = View.Details;
            Own.lvItms.Activation = ItemActivation.Standard;
            Own.lvItms.FullRowSelect = true;
            Own.lvItms.CheckBoxes = false;
            Own.lvItms.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("单位", 40, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("包装条码", 150, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("库位", 120, HorizontalAlignment.Left);

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

                string ctnBaco = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[0].Text;
                for (int i = 0; i < bacoList.Count; i++)
                {
                    if (bacoList[i].ctn_baco == ctnBaco)
                    {
                        bacoList.Remove(bacoList[i]);
                        break;
                    }
                }

                //for (int i = 0; i < editCtnChilders.Count; i++)
                //{
                //    if (editCtnChilders[i].parent_ctn_baco == ctnBaco)
                //    {
                //        editCtnChilders.RemoveAt(i);
                //        i--;
                //    }
                //}

                itmListBind();
                bacoListBind();
                clear();
            }
        }

        private void txtPkgBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtPkgBaco.Text.Trim().Length == 0) return;

                for (int i = 0; i < this.bacoList.Count; i++)
                {
                    if (this.bacoList[i].ctn_baco == this.txtPkgBaco.Text.Trim())
                    {
                        MessageBox.Show("该包装已经扫描！");
                        this.txtPkgBaco.Text = "";
                        this.txtPkgBaco.Focus();
                        return;
                    }
                }
                try
                {

                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtPkgBaco.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtnBox", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        CTN_MAIN_VIEW box = Data.dataEntity;
                        if (box == null || string.IsNullOrEmpty(box.ctn_main_guid))
                        {
                            throw new Exception("合格证：" + this.txtPkgBaco.Text.Trim() + "不存在！");
                        }

                        if (!string.IsNullOrEmpty(box.wh_id)&&box.wh_id!="0301")
                        {
                            throw new Exception("合格证：" + this.txtPkgBaco.Text.Trim()+"已经入库！");
                        }

                        if (box.ctn_type != 13)
                        {
                            throw new Exception(this.txtPkgBaco.Text.Trim()+" 不是合格证！");
                        }


                        this.txtPkgBaco.Tag = box;
                        this.txtInvId.Text = "0301";

                        editCtn.ctn_baco = txtPkgBaco.Text.Trim();
                        editCtn.f_wh_id = this.txtInvId.Text;
                        editCtn.itm_qty = box.itm_qty;
                        editCtn.itm_id = box.itm_id;
                        editCtn.itm_name = box.itm_name;
                        editCtn.itm_unit = box.itm_unit;


                        this.txtLocBaco.Focus();

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
                catch (Exception ex) 
                { 
                    MessageBox.Show(ex.Message);

                    this.txtPkgBaco.Text = "";
                    this.txtPkgBaco.Focus();
                }
            }
        }

        private void txtInvId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtInvId.Text.Trim().Length == 0)
                {
                    this.txtInvId.Text = "";
                    this.txtInvId.Focus();
                    return;
                }

                this.txtInvId.Text = this.txtInvId.Text.Trim();
                editCtn.f_wh_id = this.txtInvId.Text;
                this.txtLocBaco.Focus();
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

                if (this.txtPkgBaco.Tag == null)
                {
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
                        this.editCtn.wh_loc_baco = ctn.ctn_baco;
                        this.editCtn.wh_id = ctn.wh_id;

                        this.txtLocBaco.Tag = ctn;

                        this.doAddItm();
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

        private void doAddItm()
        {
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
                    tranBaco.itm_id = baco.itm_id;
                    tranBaco.ctn_baco = baco.ctn_baco;
                    tranBaco.tran_qty = baco.itm_qty;

                    //库位
                    tranBaco.parent_baco = baco.parent_ctn_baco;
                    //入仓库
                    tranBaco.wh_id = baco.wh_id;
                    //出仓库
                    tranBaco.f_wh_id = baco.f_wh_id;


                    doc.body_baco.Add(tranBaco);
                }

                BasicRequest<TRAN_DOC, TRAN_DOC> Request = new BasicRequest<TRAN_DOC, TRAN_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/doProductTranIn", "POST", "application/json", RequestStr);
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
            this.lvItms.Items.Clear();

            itmList = new List<ITM_MAIN_VIEW>();

            var obj = from p in bacoList
                      group p by new { p.itm_id, p.itm_name, p.itm_unit } into b
                      select new
                      {
                          itm_id = b.Key.itm_id,
                          itm_name = b.Key.itm_name,
                          itm_unit = b.Key.itm_unit,
                          itm_qty = b.Sum(c => c.itm_qty)
                      };

            if (obj != null && obj.Count() > 0)
            {
                foreach (var objItm in obj)
                {
                    ITM_MAIN_VIEW itm = new ITM_MAIN_VIEW();
                    itm.itm_main_id = objItm.itm_id;
                    itm.itm_name = objItm.itm_name;
                    itm.itm_qty = objItm.itm_qty;
                    itm.itm_unit = objItm.itm_unit;

                    itmList.Add(itm);
                }
            }

            if (itmList == null || itmList.Count==0) return;
            string[] cols = new string[3];

            for (int i = 0; i < itmList.Count; i++)
            {
                cols[0] = itmList[i].itm_main_id;
                cols[1] = itmList[i].itm_qty.ToString();
                cols[2] = itmList[i].itm_unit;

                ListViewItem lvItem = new ListViewItem(cols);
                lvItms.Items.Add(lvItem);
            }
        }

        public void bacoListBind()
        {
            this.lvBaco.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[2];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].ctn_baco;
                cols[1] = bacoList[i].parent_ctn_baco + "";

                ListViewItem lvItem = new ListViewItem(cols);
                lvBaco.Items.Add(lvItem);
            }
        }

        public void clear()
        {
            this.txtPkgBaco.Text = "";
            this.txtPkgBaco.Tag = null;
            this.txtPkgBaco.Enabled = true;

            this.txtLocBaco.Text = "";
            this.txtLocBaco.Tag = null;

            this.txtInvId.Text = "";

            editCtn = new CTN_MAIN_VIEW();

            this.txtPkgBaco.Focus();
        }

        public void clearAll()
        {
            clear();
            this.itmList.Clear();
            this.bacoList.Clear();
            this.lvBaco.Items.Clear();
            this.lvItms.Items.Clear();
        }
    }
}