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
    public partial class frmDoBox : Form
    {
        private List<ITM_MAIN_VIEW> itmList = new List<ITM_MAIN_VIEW>();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmDoBox()
        {
            InitializeComponent();
        }

        public static frmDoBox GetInstance(string title)
        {
            frmDoBox Own = new frmDoBox();
            Own.Text = title;

            Own.lvItms.View = View.Details;
            Own.lvItms.Activation = ItemActivation.Standard;
            Own.lvItms.FullRowSelect = true;
            Own.lvItms.CheckBoxes = false;
            Own.lvItms.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("单位", 40, HorizontalAlignment.Left);
            Own.lvItms.Columns.Add("品名", 150, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("条码号", 120, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("单位", 40, HorizontalAlignment.Left);
            Own.lvBaco.Columns.Add("品名", 150, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.Show();
            Own.txtPkgBaco.Focus();
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

        private void txtPkgBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtPkgBaco.Text.Trim().Length == 0) return;

                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtPkgBaco.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        CTN_MAIN_VIEW ctn = Data.dataEntity;

                        if (!String.IsNullOrEmpty(ctn.ctn_main_guid))
                        {
                            MessageBox.Show("该条码号已经被使用，请更换未使用条码！");
                            this.txtPkgBaco.Text = "";
                            this.txtPkgBaco.Focus();
                            return;
                        }

                        this.txtPkgBaco.Enabled = false;
                        this.txtSwsBaco.Focus();
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

        private void txtSwsBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtSwsBaco.Text.Trim().Length == 0) return;

                for (int i = 0; i < this.bacoList.Count; i++)
                {
                    if (this.bacoList[i].ctn_baco == this.txtSwsBaco.Text.Trim())
                    {
                        MessageBox.Show("该流程票已经扫描！");
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
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        CTN_MAIN_VIEW ctn = Data.dataEntity;

                        if (String.IsNullOrEmpty(ctn.ctn_main_guid))
                        {
                            MessageBox.Show("未找到合格证信息！");
                            this.txtSwsBaco.Text = "";
                            this.txtSwsBaco.Focus();
                            return;
                        }

                        if (ctn.ctn_type != 13)
                        {
                            MessageBox.Show("不是合格证条码！");
                            this.txtSwsBaco.Text = "";
                            this.txtSwsBaco.Focus();
                            return;
                        }

                        editCtn.ctn_baco = ctn.ctn_baco;
                        editCtn.itm_id = ctn.itm_id;
                        editCtn.itm_name = ctn.itm_name;
                        editCtn.itm_unit = ctn.itm_unit;
                        editCtn.lot_id = ctn.lot_id;
                        editCtn.itm_qty = ctn.itm_qty;

                        doAddItm();
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


        private void doAddItm()
        {
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
            try
            {
                BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Request = new BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtPkgBaco.Text.Trim();
                Request.data_list = this.bacoList;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/PkgCert", "POST", "application/json", RequestStr);
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
            this.txtSwsBaco.Tag = null;;

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

            this.txtPkgBaco.Enabled = true;
            this.txtPkgBaco.Text = "";
            this.txtPkgBaco.Tag = null;
            this.txtPkgBaco.Focus();
        }
    }
}