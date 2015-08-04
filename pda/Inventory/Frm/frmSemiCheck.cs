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
    public partial class frmSemiCheck : Form
    {
        private List<CTN_MAIN_VIEW> invList = new List<CTN_MAIN_VIEW>();
        private List<STK_MAIN> stkList = new List<STK_MAIN>();
        private CTN_MAIN_VIEW stkInv = null;
        private CTN_MAIN_VIEW ctn = new CTN_MAIN_VIEW();

        public frmSemiCheck()
        {
            InitializeComponent();
        }

        public static frmSemiCheck GetInstance(string title)
        {
            frmSemiCheck Own = new frmSemiCheck();
            Own.Text = title;

            Own.LoadInvs();
            Own.cbInvs.SelectedIndexChanged += new System.EventHandler(Own.cbInvs_SelectedIndexChanged);
            if (Own.cbInvs.Items.Count > 0)
            {
                Own.cbInvs.SelectedIndex = 0;
                Own.LoadStks(Own.cbInvs.SelectedValue.ToString());
                Own.stkInv = Own.invList[Own.cbInvs.SelectedIndex];
            }

            Own.Show();
            Own.txtCtnBaco.Focus();
            return Own;
        }

        private void LoadInvs()
        {
            BasicRequest<string, string> Request = new BasicRequest<string, string>();
            Request.token = HttpWebRequestProxy.token;

            string RequestStr = JsonConvert.SerializeObject(Request);
            string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getwhs", "POST", "application/json", RequestStr);
            BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

            if (Data.status == "0")
            {
                invList = Data.dataList;
            }
            else
            {
            }

            if (invList != null)
            {
                this.cbInvs.DataSource = invList;
                this.cbInvs.ValueMember = "ctn_main_id";
                this.cbInvs.DisplayMember = "ctn_name";
            }
        }

        private void cbInvs_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.LoadStks(this.cbInvs.SelectedValue.ToString());
            stkInv = this.invList[this.cbInvs.SelectedIndex];
        }

        private void LoadStks(string invId)
        {
            BasicRequest<string, string> Request = new BasicRequest<string, string>();
            Request.token = HttpWebRequestProxy.token;
            Request.data_char = invId;

            string RequestStr = JsonConvert.SerializeObject(Request);
            string ResponseStr = HttpWebRequestProxy.PostRest("inv/GetStkPlanListByInv", "POST", "application/json", RequestStr);
            BasicResponse<STK_MAIN, STK_MAIN> Data = JsonConvert.DeserializeObject<BasicResponse<STK_MAIN, STK_MAIN>>(ResponseStr);

            if (Data.status == "0")
            {
                stkList = Data.dataList;
            }

            if (stkList != null)
            {
                this.cbCheckIds.DataSource = stkList;
                this.cbCheckIds.ValueMember = "id";
                this.cbCheckIds.DisplayMember = "id";
            }
        }

        private void txtCtnBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtCtnBaco.Text.Trim().Length == 0) return;

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
                        ctn = Data.dataEntity;

                        if (String.IsNullOrEmpty(ctn.ctn_main_guid))
                        {
                            MessageBox.Show("未找到流程票或合格证信息！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Tag = null;
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        if (ctn.ctn_type != 12 && ctn.ctn_type != 13)
                        {
                            MessageBox.Show("不是流程票或合格证条码！");
                            this.txtCtnBaco.Text = "";
                            this.txtCtnBaco.Tag = null;
                            this.txtCtnBaco.Focus();
                            return;
                        }

                        this.txtCtnBaco.Tag = ctn;

                        this.lblItmId.Text = ctn.itm_id;
                        this.lblItmQty.Text = ctn.itm_qty.ToString();
                        this.lblLoc.Text = ctn.wh_loc_baco;

                        //this.txtCountQty.Text = ctn.itm_qty.ToString();
                        //this.txtCheckLocId.Text = ctn.wh_loc_baco;

                        this.txtCountQty.Focus();
                        this.txtCountQty.SelectionStart = this.txtCountQty.Text.Length;

                        if (ctn.stk_guid == stkList[this.cbCheckIds.SelectedIndex].guid)
                        {
                            MessageBox.Show("该流程票已盘点！");
                            return;
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

        private void txtChecker_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtCtnBaco.Text.Trim().Length == 0) return;

                this.txtChecker.Text = this.txtChecker.Text.Split('.')[0];
                this.miOk_Click(this.miOk, null);
            }
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            if (this.txtCtnBaco.Text.Trim().Length == 0)
            {
                return;
            }
            if (this.txtCheckLocId.Text.Trim().Length == 0)
            {
                MessageBox.Show("缺少盘点库位信息！");
                return;
            }
            if (this.cbInvs.SelectedValue == null || this.cbCheckIds.SelectedValue == null)
            {
                return;
            }

            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.cbInvs.SelectedValue.ToString();
                Request.data_char2 = this.cbCheckIds.SelectedValue.ToString();
                Request.data_char3 = this.txtCtnBaco.Text.Trim();
                Request.data_char4 = System.Net.Dns.GetHostName();
                Request.data_char5 = this.txtCheckLocId.Text.Trim();
                Request.data_decimal = Decimal.Parse( this.txtCountQty.Text.Trim() );

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/doStkPlanItem", "POST", "application/json", RequestStr);
                BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    MessageBox.Show("盘点登记成功！");
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

        public void clearAll()
        {
            this.txtCtnBaco.Text = "";
            this.txtCtnBaco.Tag = null;
            this.txtChecker.Text = "";
            this.txtChecker.Tag = null;
            this.txtCheckLocId.Text = "";
            this.txtCheckLocId.Tag = null;

            this.lblItmId.Text = "";
            this.lblItmQty.Text = "";
            this.lblLoc.Text = "";
            this.txtCountQty.Text = "0";

            this.txtCtnBaco.Focus();
        }

        private void txtCountQty_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                try
                {
                    decimal countQty = Decimal.Parse(this.txtCountQty.Text.Trim());
                    this.txtCountQty.Text = countQty.ToString();
                    this.txtCheckLocId.Focus();
                    this.txtCheckLocId.SelectionStart = this.txtCheckLocId.Text.Length;
                }
                catch
                {
                   // this.txtCountQty.Text = this.ctn.itm_qty.ToString();
                }     
            }
        }

        private void txtCheckLocId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                try
                {
                    if (this.txtCheckLocId.Text.Trim() != this.ctn.wh_loc_baco)
                    {
                        BasicRequest<string, string> Request = new BasicRequest<string, string>();
                        Request.token = HttpWebRequestProxy.token;
                        Request.data_char = this.txtCheckLocId.Text;

                        string RequestStr = JsonConvert.SerializeObject(Request);
                        string ResponseStr = HttpWebRequestProxy.PostRest("InvBasic/getCtn", "POST", "application/json", RequestStr);
                        BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CTN_MAIN_VIEW, CTN_MAIN_VIEW>>(ResponseStr);

                        if (Data.status == "0")
                        {
                            CTN_MAIN_VIEW ctnData = Data.dataEntity;

                            if (String.IsNullOrEmpty(ctnData.ctn_main_guid))
                            {
                                MessageBox.Show("未找到库位信息！");
                                this.txtCheckLocId.Text = "";
                                this.txtCheckLocId.Focus();
                                return;
                            }

                            if (ctnData.ctn_type != 6)
                            {
                                MessageBox.Show("不是库位条码！");
                                this.txtCheckLocId.Text = "";
                                this.txtCheckLocId.Focus();
                                return;
                            }

                            if (ctnData.wh_id != this.stkInv.ctn_main_id)
                            {
                                MessageBox.Show("库位条码不属于盘点仓库！");
                                this.txtCheckLocId.Text = "";
                                this.txtCheckLocId.Focus();
                                return;
                            }

                            ctn.wh_loc_id = ctnData.wh_loc_id;
                            ctn.wh_loc_baco = ctnData.wh_loc_baco;
                            
                            //this.txtChecker.Focus();
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
                    else
                    {
                        //this.txtChecker.Focus();
                    }

                    this.miOk_Click(this.miOk, null);
                }
                catch
                {
                    //this.txtCheckLocId.Text = this.ctn.wh_loc_baco.ToString();
                }
            }
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            clearAll();
        }        
    }
}