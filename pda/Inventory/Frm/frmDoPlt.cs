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
    public partial class frmDoPlt : Form
    {
        private List<ITM_MAIN_VIEW> itmList = new List<ITM_MAIN_VIEW>();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW pltCtn = new CTN_MAIN_VIEW();

        public frmDoPlt()
        {
            InitializeComponent();
        }

        public static frmDoPlt GetInstance(string title)
        {
            frmDoPlt Own = new frmDoPlt();
            Own.Text = title;

            Own.lvItm.View = View.Details;
            Own.lvItm.Activation = ItemActivation.Standard;
            Own.lvItm.FullRowSelect = true;
            Own.lvItm.CheckBoxes = false;
            Own.lvItm.Columns.Add("品号", 150, HorizontalAlignment.Left);
            Own.lvItm.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvItm.Columns.Add("单位", 40, HorizontalAlignment.Left);

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("条码号", 120, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBaco.ContextMenu = lvMenu;

            Own.Show();
            Own.txtPlt.Focus();
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

                string pkgBaco = lvBaco.Items[lvBaco.SelectedIndices[0]].SubItems[0].Text;

                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = pkgBaco;

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getItmListByPkgBaco", "POST", "application/json", RequestStr);
                    BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        List<ITM_MAIN_VIEW> itms = Data.dataList2;

                        for (int i = 0; i < itms.Count; i++)
                        {
                            for (int j = 0; j < itmList.Count; j++)
                            {
                                if (itms[i].itm_main_id == itmList[j].itm_main_id)
                                {
                                    itmList[j].itm_qty = itmList[j].itm_qty - itms[i].itm_qty;
                                    if (itmList[j].itm_qty == 0)
                                    {
                                        itmList.RemoveAt(j);
                                        j--;
                                    }
                                    break;
                                }
                            }
                        }

                        for (int i = 0; i < bacoList.Count; i++)
                        {
                            if (bacoList[i].ctn_baco == pkgBaco)
                            {
                                bacoList.RemoveAt(i);
                                break;
                            }
                        }

                        itmListBind();
                        bacoListBind();
                        clear();
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

        private void txtPlt_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtPlt.Text.Trim().Length == 0) return;

                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtPlt.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        CTN_MAIN_VIEW ctn = Data.dataEntity;

                        if (!String.IsNullOrEmpty(ctn.ctn_main_guid))
                        {
                            MessageBox.Show("该条码号已经被使用，请更换未使用条码！");
                            this.txtPlt.Text = "";
                            this.txtPlt.Focus();
                            return;
                        }

                        pltCtn.ctn_baco = this.txtPlt.Text.Trim();
                        this.txtLoc.Focus();
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

        private void txtLoc_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtLoc.Text.Trim().Length == 0) return;

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
                        CTN_MAIN_VIEW ctn = Data.dataEntity;

                        if (ctn.ctn_type!=6)
                        {
                            MessageBox.Show("该条吗不是库位条码!");
                            this.txtLoc.Text = "";
                            this.txtLoc.Focus();
                            return;
                        }

                        this.txtLoc.Tag = ctn;
                        this.pltCtn.parent_ctn_baco = this.txtLoc.Text.Trim();
                        this.txtPkgBaco.Focus();
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

        private void txtPkgBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtPkgBaco.Text.Trim().Length == 0) return;

                for (int i = 0; i < this.bacoList.Count; i++)
                {
                    if (this.bacoList[i].ctn_baco == this.txtPkgBaco.Text.Trim())
                    {
                        MessageBox.Show("该条码号已经被使用，请更换未使用条码！");
                        this.txtPlt.Text = "";
                        this.txtPlt.Focus();
                        return;
                    }
                }

                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtPkgBaco.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getItmListByPkgBaco", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, ITM_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, ITM_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        List<ITM_MAIN_VIEW> itms = Data.dataList2;
                        bool isInList = false;

                        for (int i = 0; i < itms.Count; i++)
                        {
                            for (int j = 0; j < itmList.Count; j++)
                            {
                                if (itms[i].itm_main_id == itmList[j].itm_main_id)
                                {
                                    isInList = true;
                                    itmList[j].itm_qty = itmList[j].itm_qty + itms[i].itm_qty;
                                    break;
                                }
                            }

                            if (!isInList)
                            {
                                itmList.Add(itms[i]);
                            }
                        }

                        CTN_MAIN_VIEW baco = Data.dataEntity;
                        bacoList.Add(baco);

                        itmListBind();
                        bacoListBind();
                        clear();
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
                catch (Exception ex) { MessageBox.Show(ex.Message); this.txtPkgBaco.Text = ""; this.txtPkgBaco.Focus(); }
            }
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            try
            {
                BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Request = new BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = this.pltCtn;
                Request.data_list = this.bacoList;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/DoPlt", "POST", "application/json", RequestStr);
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
            string[] cols = new string[3];

            for (int i = 0; i < itmList.Count; i++)
            {
                cols[0] = itmList[i].itm_main_id;
                cols[1] = itmList[i].itm_qty + "";
                cols[2] = itmList[i].itm_unit;

                ListViewItem lvItem = new ListViewItem(cols);
                lvItm.Items.Add(lvItem);
            }
        }

        public void bacoListBind()
        {
            this.lvBaco.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[1];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].ctn_baco;

                ListViewItem lvItem = new ListViewItem(cols);
                lvBaco.Items.Add(lvItem);
            }
        }

        public void clear()
        {
            this.txtPkgBaco.Text = "";
            this.txtPkgBaco.Tag = null; ;

            this.txtPkgBaco.Focus();
        }

        public void clearAll()
        {
            itmList.Clear();
            bacoList.Clear();
            lvItm.Items.Clear();
            lvBaco.Items.Clear();

            clear();

            this.txtLoc.Text = "";
            this.txtLoc.Tag = null;
            this.txtPlt.Text = "";
            this.txtPlt.Tag = null;
            this.txtPlt.Focus();
        }
    }
}