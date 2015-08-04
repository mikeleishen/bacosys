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
    public partial class frmSWSMove : Form
    {
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmSWSMove()
        {
            InitializeComponent();
        }

        public static frmSWSMove GetInstance(string title)
        {
            frmSWSMove Own = new frmSWSMove();
            Own.Text = title;

            Own.lvBacos.View = View.Details;
            Own.lvBacos.Activation = ItemActivation.Standard;
            Own.lvBacos.FullRowSelect = true;
            Own.lvBacos.CheckBoxes = false;
            Own.lvBacos.Columns.Add("条码号", 120, HorizontalAlignment.Left);
            Own.lvBacos.Columns.Add("库位", 100, HorizontalAlignment.Left);
            Own.lvBacos.Columns.Add("品号", 120, HorizontalAlignment.Left);
            Own.lvBacos.Columns.Add("数量", 60, HorizontalAlignment.Left);
            Own.lvBacos.Columns.Add("单位", 40, HorizontalAlignment.Left);
            Own.lvBacos.Columns.Add("品名", 150, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvBacos.ContextMenu = lvMenu;

            Own.Show();
            Own.txtSwsBaco.Focus();
            return Own;
        }

        private void lvMenuItem_Click(object sender, EventArgs e)
        {
            string action = (sender as MenuItem).Text;
            if (action == "删除")
            {
                if (lvBacos.SelectedIndices.Count < 1)
                {
                    return;
                }

                string itmBaco = lvBacos.Items[lvBacos.SelectedIndices[0]].SubItems[0].Text;

                for (int i = 0; i < bacoList.Count; i++)
                {
                    if (bacoList[i].ctn_baco == itmBaco)
                    {
                        bacoList.Remove(bacoList[i]);
                        break;
                    }
                }

                bacoListBind();
                clear();
            }
        }

        public void bacoListBind()
        {
            this.lvBacos.Items.Clear();
            if (bacoList == null) return;
            string[] cols = new string[6];

            for (int i = 0; i < bacoList.Count; i++)
            {
                cols[0] = bacoList[i].ctn_baco;
                cols[1] = bacoList[i].parent_ctn_baco + "";
                cols[2] = bacoList[i].itm_id;
                cols[3] = bacoList[i].itm_qty + "";
                cols[4] = bacoList[i].itm_unit + "";
                cols[5] = bacoList[i].itm_name;

                ListViewItem lvItem = new ListViewItem(cols);
                lvBacos.Items.Add(lvItem);
            }
        }

        private void txtSwsBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData != Keys.Enter)
            {
                return;
            }

            if (this.txtSwsBaco.Text.Trim().Length == 0)
            {
                return;
            }

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
                        MessageBox.Show("流程票未入库！");
                        this.txtSwsBaco.Text = "";
                        this.txtSwsBaco.Focus();
                        return;
                    }

                    this.txtSwsBaco.Enabled = false;
                    this.txtSwsBaco.Tag = ctn;

                    editCtn.ctn_baco = ctn.ctn_baco;
                    editCtn.itm_id = ctn.itm_id;
                    editCtn.itm_name = ctn.itm_name;
                    editCtn.itm_qty = ctn.itm_qty;
                    editCtn.itm_unit = ctn.itm_unit;

                    this.txtLocBaco.Focus();
                    this.txtLocBaco.SelectionStart = this.txtLocBaco.Text.Length;
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
            }
        }

        private void txtLocBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData != Keys.Enter)
            {
                return;
            }

            if (this.txtLocBaco.Text.Trim().Length == 0)
            {
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

                    if (String.IsNullOrEmpty(ctn.ctn_main_guid))
                    {
                        MessageBox.Show("未找到库位信息！");
                        this.txtLocBaco.Text = "";
                        this.txtLocBaco.Focus();
                        return;
                    }

                    if (ctn.ctn_type != 6)
                    {
                        MessageBox.Show("不是库位条码！");
                        this.txtLocBaco.Text = "";
                        this.txtLocBaco.Focus();
                        return;
                    }

                    editCtn.parent_ctn_baco = this.txtLocBaco.Text.Trim();
                    bacoList.Add(editCtn);

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
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            this.clear();
        }

        public void clear()
        {
            this.txtSwsBaco.Enabled = true;
            this.txtSwsBaco.Text = "";
            this.txtSwsBaco.Tag = null;
            this.txtLocBaco.Text = "";
            this.txtLocBaco.Tag = null;

            editCtn = new CTN_MAIN_VIEW();

            this.txtSwsBaco.Focus();
        }

        public void clearAll()
        {
            bacoList.Clear();
            lvBacos.Items.Clear();

            clear();
        }

        private void miOK_Click(object sender, EventArgs e)
        {
            if (this.bacoList.Count == 0)
            {
                return;
            }

            try
            {
                string whId = this.bacoList[0].wh_id; 

                foreach (CTN_MAIN_VIEW pur_baco in bacoList)
                {
                    if (whId != pur_baco.wh_id)
                    {
                        MessageBox.Show("所有条码必须在同一仓库内！");
                        return;
                    }

                    TRAN_BACO baco = new TRAN_BACO();
                    baco.ctn_baco = pur_baco.ctn_baco;
                    baco.tran_qty = pur_baco.itm_qty;
                    baco.itm_id = pur_baco.itm_id;
                }
                
                BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Request = new BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_list = bacoList;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/moveSWSWHLoc", "POST", "application/json", RequestStr);
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
    }
}