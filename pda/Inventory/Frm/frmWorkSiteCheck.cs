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
    public partial class frmWorkSiteCheck : Form
    {
        private List<STK_MAIN> stkList = new List<STK_MAIN>();
        private STK_ITM_WKSITE stkItm = new STK_ITM_WKSITE();
        
        public frmWorkSiteCheck()
        {
            InitializeComponent();
        }

        public static frmWorkSiteCheck GetInstance(string title)
        {
            frmWorkSiteCheck Own = new frmWorkSiteCheck();
            Own.Text = title;

            Own.LoadStks();

            Own.Show();
            Own.txtSwsId.Focus();
            return Own;

        }

        private void LoadStks()
        {
            BasicRequest<STK_MAIN, string> Request = new BasicRequest<STK_MAIN, string>();
            Request.token = HttpWebRequestProxy.token;
            STK_MAIN entityRequest = new STK_MAIN();
            entityRequest.is_stk = "0";//不进行盘盈盘亏
            Request.data_entity = entityRequest;

            string RequestStr = JsonConvert.SerializeObject(Request);
            string ResponseStr = HttpWebRequestProxy.PostRest("inv/getStockPlanListByFields", "POST", "application/json", RequestStr);
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

        private void txtSwsId_KeyUp(object sender, KeyEventArgs e)
        {
            if (txtSwsId.Text.Trim().Length == 0)
            {
                return;
            }

            if (e.KeyData != Keys.Enter)
            {
                return;
            }

            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = txtSwsId.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("inv/getWorkSiteItemBySwsId", "POST", "application/json", RequestStr);
                BasicResponse<STK_ITM_WKSITE, string> Data = JsonConvert.DeserializeObject<BasicResponse<STK_ITM_WKSITE, string>>(ResponseStr);

                if (Data.status == "1")
                {
                    throw new Exception(Data.info);
                }

                if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    return;
                }

                if (Data.status == "0")
                {
                    stkItm = Data.dataEntity;

                    if (stkItm == null || string.IsNullOrEmpty(stkItm.sws_guid))
                    {
                        throw new Exception("未找到流程票信息！");
                    }

                    lblItmID.Text = string.IsNullOrEmpty(stkItm.itm_id) ? "" : stkItm.itm_id;
                    lblSTKRacID.Text = string.IsNullOrEmpty(stkItm.stk_rac_id) ? "" : stkItm.stk_rac_id;
                    lblSTKRacName.Text = string.IsNullOrEmpty(stkItm.stk_rac_name) ? "" : stkItm.stk_rac_name;
                    lblSTKValue.Text = stkItm.stk_value.ToString();
                }
            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }
        }

        private void miOK_Click(object sender, EventArgs e)
        {
            #region 校验

            if (string.IsNullOrEmpty(stkItm.sws_guid))
            {
                MessageBox.Show("未找到流程票信息！");
                return;
            }

            if (string.IsNullOrEmpty(stkItm.stk_rac_id) || string.IsNullOrEmpty(stkItm.stk_rac_name))
            {
                MessageBox.Show("未找到工单工序信息！");
                return;
            }

            if (cbCheckIds.SelectedItem == null)
            {
                MessageBox.Show("请选择盘点计划代码！");
                return;
            }

            #endregion

            try
            {
                STK_MAIN stkMain = cbCheckIds.SelectedItem as STK_MAIN;
                stkItm.stk_main_guid = stkMain.guid;

                BasicResponse<string, string> Data = InsertStkItm("");

                #region 处理异常
                if (Data.status == "1")
                {
                    throw new Exception(Data.info);
                }

                if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    return;
                }
                #endregion

                if (Data.status == "0")
                {
                    string sResult = Data.dataEntity;
                    if (string.IsNullOrEmpty(sResult))
                    {
                        return;
                    }

                    if (sResult.Equals("1"))//已经存在盘点数据
                    {
                        DialogResult diaResult = MessageBox.Show("流程票已经盘点，是否覆盖？", "确认", MessageBoxButtons.YesNo, MessageBoxIcon.None, MessageBoxDefaultButton.Button1);
                        if (diaResult == DialogResult.Yes)
                        {
                            BasicResponse<string, string> DataConfirm = InsertStkItm("Y");

                            #region 处理异常
                            if (DataConfirm.status == "1")
                            {
                                throw new Exception(Data.info);
                            }

                            if (DataConfirm.status == "2")
                            {
                                Login.ReLogin(Data.info);
                                return;
                            }
                            #endregion

                            if (DataConfirm.status == "0")
                            {
                                MessageBox.Show("盘点成功！");
                                this.clearAll();

                            }
                        }

                    }
                    else
                    {
                        MessageBox.Show("盘点成功！");
                        this.clearAll();
                    }
                }
            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }


        }

        private void clearAll()
        {
            this.txtSwsId.Text = string.Empty;
            this.lblItmID.Text = string.Empty;
            this.lblSTKRacID.Text = string.Empty;
            this.lblSTKRacName.Text = string.Empty;
            this.lblSTKValue.Text = string.Empty;

            stkItm = new STK_ITM_WKSITE();
        }

        private BasicResponse<string, string> InsertStkItm(string isUpdate)
        {
            BasicRequest<STK_ITM_WKSITE, STK_ITM_WKSITE> Request = new BasicRequest<STK_ITM_WKSITE, STK_ITM_WKSITE>();
            Request.token = HttpWebRequestProxy.token;
            Request.data_char = isUpdate;
            Request.data_char2 = "test";
            Request.data_entity = stkItm;

            string RequestStr = JsonConvert.SerializeObject(Request);
            string ResponseStr = HttpWebRequestProxy.PostRest("inv/addStkItmWKSite", "POST", "application/json", RequestStr);
            BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);
          
            return Data;
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

        private void btnClear_Click(object sender, EventArgs e)
        {
            this.clearAll();
        }
    }
}