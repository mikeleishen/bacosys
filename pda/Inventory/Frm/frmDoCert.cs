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
    public partial class frmDoCert : Form
    {
        private CTN_MAIN_VIEW pkgCtn = new CTN_MAIN_VIEW();
        private List<CTN_MAIN_VIEW> bacoList = new List<CTN_MAIN_VIEW>();
        private CTN_MAIN_VIEW editCtn = new CTN_MAIN_VIEW();

        public frmDoCert()
        {
            InitializeComponent();
        }

        public static frmDoCert GetInstance(string title)
        {
            frmDoCert Own = new frmDoCert();
            Own.Text = title;

            Own.lvBaco.View = View.Details;
            Own.lvBaco.Activation = ItemActivation.Standard;
            Own.lvBaco.FullRowSelect = true;
            Own.lvBaco.CheckBoxes = false;
            Own.lvBaco.Columns.Add("流程票", 120, HorizontalAlignment.Left);
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

            Own.Show();
            Own.txtCertBaco.Focus();
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

                this.pkgCtn.itm_qty = this.pkgCtn.itm_qty - itmQty;

                bacoListBind();
                clear();
            }
        }

        private void txtCertBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtCertBaco.Text.Trim().Length == 0) return;

                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.txtCertBaco.Text.Trim();

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                    BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        CTN_MAIN_VIEW ctn = Data.dataEntity;

                        if (!String.IsNullOrEmpty(ctn.ctn_main_guid))
                        {
                            MessageBox.Show("该条码号已经被使用，请更换未使用条码！");
                            this.txtCertBaco.Text = "";
                            this.txtCertBaco.Focus();
                            return;
                        }

                        this.txtCertBaco.Enabled = false;
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
                            MessageBox.Show("未找到流程票信息！");
                            this.txtSwsBaco.Text = "";
                            this.txtSwsBaco.Focus();
                            return;
                        }

                        if (ctn.ctn_type != 12)
                        {
                            MessageBox.Show("不是流程票条码！");
                            this.txtSwsBaco.Text = "";
                            this.txtSwsBaco.Focus();
                            return;
                        }

                        if (this.lblItmId.Text.Trim().Length > 0)
                        {
                            if (ctn.itm_id.Trim() != this.lblItmId.Text.Trim())
                            {
                                MessageBox.Show("所扫流转票内容与已包内容不一致！");
                                this.txtSwsBaco.Text = "";
                                this.txtSwsBaco.Focus();
                                return;
                            }
                        }

                        this.txtSwsBaco.Text = ctn.itm_id;
                        this.txtSwsBaco.Tag = ctn;
                        this.lblItmId.Text = ctn.itm_id;
                        this.lblLastQty.Text = ctn.itm_qty.ToString();
                        this.lblUnit.Text = ctn.itm_unit;

                        editCtn.ctn_baco = ctn.ctn_baco;
                        editCtn.itm_id = ctn.itm_id;
                        editCtn.itm_name = ctn.itm_name;
                        editCtn.itm_unit = ctn.itm_unit;
                        editCtn.lot_id = ctn.lot_id;

                        this.txtQty.Focus();
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

        private void txtQty_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtQty.Text.Trim().Length == 0)
                {
                    this.txtQty.Text = "";
                    this.txtQty.Focus();
                }

                this.txtQty.Text = this.txtQty.Text.Trim();

                try
                {
                    this.txtQty.Text = Int32.Parse(this.txtQty.Text.Trim()).ToString();
                    if (Decimal.Parse(this.txtQty.Text) > ((CTN_MAIN_VIEW)this.txtSwsBaco.Tag).itm_qty)
                    {
                        MessageBox.Show("包装数量不能大于流程票可包数量！");
                        return;
                    }
                    editCtn.itm_qty = Decimal.Parse(this.txtQty.Text);
                    this.doAddItm();
                }
                catch
                {
                    this.txtQty.Text = "";
                    this.txtQty.Focus();
                }
            }
        }

        private void doAddItm()
        {
            this.pkgCtn.itm_id = this.editCtn.itm_id;
            this.pkgCtn.itm_qty = this.pkgCtn.itm_qty + this.editCtn.itm_qty;
            this.lblItmId.Text = this.pkgCtn.itm_id;
            this.lblItmQty.Text = this.pkgCtn.itm_qty.ToString();
            bacoList.Add(editCtn);
            bacoListBind();
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

        private void miOk_Click(object sender, EventArgs e)
        {
            try
            {
                BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Request = new BasicRequest<CTN_MAIN_VIEW, CTN_MAIN_VIEW>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtCertBaco.Text.Trim();
                Request.data_list = this.bacoList;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/PkgSws", "POST", "application/json", RequestStr);
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
            this.txtSwsBaco.Text = "";
            this.txtSwsBaco.Tag = null;
            this.lblLastQty.Text = "";
            this.txtQty.Text = "";
            this.lblUnit.Text = "";

            editCtn = new CTN_MAIN_VIEW();

            this.txtSwsBaco.Focus();
        }

        public void clearAll()
        {
            this.txtCertBaco.Text = "";
            this.txtCertBaco.Tag = null;
            this.txtCertBaco.Enabled = true;
            this.lblItmId.Text = "";
            this.lblItmQty.Text = "";
            bacoList.Clear();
            lvBaco.Items.Clear();

            clear();

            this.txtCertBaco.Focus();
        }
    }
}