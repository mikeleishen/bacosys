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
    public partial class frmAllScrap : Form
    {
        private SWS_SCRAP_DOC editSws = new SWS_SCRAP_DOC();
        private List<RAC_SCRAP_VIEW> scrapList = new List<RAC_SCRAP_VIEW>();

        private string swsRpGuid = string.Empty;

        public frmAllScrap()
        {
            InitializeComponent();
        }

        public static frmAllScrap GetInstance(string title,string paramSwsRpGuid)
        {
            frmAllScrap Own = new frmAllScrap();
            Own.Text = title;
            Own.swsRpGuid = paramSwsRpGuid;

            Own.lvScrap.View = View.Details;
            Own.lvScrap.Activation = ItemActivation.Standard;
            Own.lvScrap.FullRowSelect = true;
            Own.lvScrap.CheckBoxes = false;
            Own.lvScrap.Columns.Add("已报废量", 80, HorizontalAlignment.Right);
            Own.lvScrap.Columns.Add("报废原因", 120, HorizontalAlignment.Left);
            Own.lvScrap.Columns.Add("员工工号", 80, HorizontalAlignment.Left);
            Own.lvScrap.Columns.Add("巡检工号", 80, HorizontalAlignment.Left);
            Own.LoadScrapReasons();
            Own.LoadScrapContent();
            Own.LoadSwsDoc();

            Own.Show();
            Own.txtSwsId.Focus();

            return Own;
        }

        private void LoadScrapReasons()
        {
            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = "ScrapReason";

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("Basic/GetParas", "POST", "application/json", RequestStr);
                BasicResponse<PARA_MAIN, PARA_MAIN> Data = JsonConvert.DeserializeObject<BasicResponse<PARA_MAIN, PARA_MAIN>>(ResponseStr);

                if (Data.status == "0")
                {
                    List<PARA_MAIN> paramList = Data.dataList;

                    this.cbScrapReason.ValueMember = "id";
                    this.cbScrapReason.DisplayMember = "para_value";
                    this.cbScrapReason.Items.Clear();
                    for (int i = 0; i < paramList.Count; i++)
                    {
                        this.cbScrapReason.Items.Add(paramList[i]);
                    }
                    if (this.cbScrapReason.Items.Count > 0)
                    {
                        this.cbScrapReason.SelectedIndex = 0;
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

        private void LoadScrapContent()
        {
            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = "ScrapContent";

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("Basic/GetParas", "POST", "application/json", RequestStr);
                BasicResponse<PARA_MAIN, PARA_MAIN> Data = JsonConvert.DeserializeObject<BasicResponse<PARA_MAIN, PARA_MAIN>>(ResponseStr);

                if (Data.status == "0")
                {
                    List<PARA_MAIN> paramList = Data.dataList;

                    this.cbScrapContent.ValueMember = "id";
                    this.cbScrapContent.DisplayMember = "para_value";
                    this.cbScrapContent.Items.Clear();
                    for (int i = 0; i < paramList.Count; i++)
                    {
                        this.cbScrapContent.Items.Add(paramList[i]);
                    }
                    if (this.cbScrapContent.Items.Count > 0)
                    {
                        this.cbScrapContent.SelectedIndex = 0;
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

        private void LoadSwsDoc()
        {
            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.swsRpGuid;
                Request.data_int = 0;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/getSwsScrapDocBySwsRpGuid", "POST", "application/json", RequestStr);
                BasicResponse<SWS_SCRAP_DOC, SWS_SCRAP_DOC> Data = JsonConvert.DeserializeObject<BasicResponse<SWS_SCRAP_DOC, SWS_SCRAP_DOC>>(ResponseStr);

                if (Data.status == "0")
                {
                    editSws = Data.dataEntity;
                    this.txtSwsId.Text = editSws.sws_id;
                    this.txtItmId.Text = editSws.itm_id;
                    this.txtRacName.Text = editSws.rac_name;
                    this.txtRpQty.Text = editSws.rp_qty.ToString();
                    this.txtScrapedQty.Text = editSws.scraped_qty.ToString();

                    scrapList = editSws.scrap_list;
                    this.scrapListBind();

                    this.txtEmpId.Focus();
                    this.txtEmpId.SelectionStart = this.txtEmpId.Text.Length;

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

                this.Close();
                this.Dispose();
            }
        }

        private void txtEmpId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtEmpId.Text.Trim().Length == 0)
                {
                    this.txtEmpId.Focus();
                    return;
                }

                this.txtInspectorId.Focus();
                this.txtInspectorId.SelectionStart = this.txtInspectorId.Text.Length;
                
            }
        }

        private void txtEmpId_LostFocus(object sender, EventArgs e)
        {
            if (this.txtEmpId.Text.Trim().Length == 0)
            {
                return;
            }
            
            try
            {
                string empId = this.txtEmpId.Text.Trim().Split('.')[0];

                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = empId;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/getEmpById", "POST", "application/json", RequestStr);
                BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    EMP_MAIN_VIEW emp = Data.dataEntity;

                    if (!String.IsNullOrEmpty(emp.emp_main_id))
                    {
                        this.txtEmpId.Tag = this.txtEmpId.Text.Trim();
                        this.txtEmpId.Text = emp.emp_main_id;
                    }
                    else
                    {
                        MessageBox.Show("未找到员工（" + this.txtEmpId.Text + "）信息！");

                        this.txtEmpId.Text = "";
                        this.txtEmpId.Tag = "";
                        this.txtEmpId.Focus();
                        return;
                    }
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    return;
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    return;
                }
            }
            catch(Exception ex) 
            {
                MessageBox.Show(ex.Message);

                this.txtEmpId.Text = "";
                this.txtEmpId.Tag = "";
                this.txtEmpId.Focus();
                return;
            }

        }

        private void txtInspectorId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtInspectorId.Text.Trim().Length == 0)
                {
                    this.txtInspectorId.Focus();
                    return;
                }

                this.cbScrapContent.Focus();
          
            }
        }

        private void txtInspectorId_LostFocus(object sender, EventArgs e)
        {
            if (this.txtInspectorId.Text.Trim().Length == 0)
            {
                return;
            }
            
            try
            {
                string empId = this.txtInspectorId.Text.Trim().Split('.')[0];

                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = empId;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/getEmpById", "POST", "application/json", RequestStr);
                BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    EMP_MAIN_VIEW emp = Data.dataEntity;

                    if (!String.IsNullOrEmpty(emp.emp_main_id))
                    {
                        this.txtInspectorId.Text = emp.emp_main_id;
                    }
                    else
                    {
                        MessageBox.Show("未找到员工（" + this.txtInspectorId.Text + "）信息！");

                        this.txtInspectorId.Text = "";
                        this.txtInspectorId.Focus();
                        return;
                    }
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    return;
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    return;
                }
            }
            catch(Exception ex) 
            {
                MessageBox.Show(ex.Message);

                this.txtInspectorId.Text = "";
                this.txtInspectorId.Focus();
                return;
            }
        }

        private void txtScrapQty_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtScrapQty.Text.Trim().Length == 0)
                {
                    this.txtScrapQty.Focus();
                    return;
                }

                this.miOk_Click(this.miOk, null);
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

        //public void clear()
        //{
        //    this.txtSwsId.Text = "";
        //    this.txtSwsId.Tag = null;
        //    this.txtSwsId.Enabled = true;
        //    this.txtItmId.Text = "";
        //    this.txtRacName.Text = "";
        //    this.txtRpQty.Text = "0";
        //    this.txtScrapedQty.Text = "0";
        //    this.txtScrapQty.Text = "0";
        //    this.txtEmpId.Text = "";
        //    this.txtInspectorId.Text = "";
        //    this.txtScrapQty.Text = "";
        //    this.cbScrapReason.SelectedIndex = 0;
        //    this.cbScrapContent.SelectedIndex = 0;

        //    this.txtSwsId.Focus();
        //}

        //public void clearAll()
        //{
        //    this.scrapList.Clear();
        //    this.lvScrap.Items.Clear();
        //    clear();
        //}

        public void scrapListBind()
        {
            lvScrap.Items.Clear();
            if (scrapList == null) return;
            string[] cols = new string[4];

            for (int i = 0; i < scrapList.Count; i++)
            {
                cols[0] = scrapList[i].scrap_qty.ToString();
                cols[1] = scrapList[i].scrap_reason;
                cols[2] = scrapList[i].emp_id;
                cols[3] = scrapList[i].inspector_id;

                ListViewItem lvItem = new ListViewItem(cols);
                lvScrap.Items.Add(lvItem);
            }
        }

        private void miOk_Click(object sender, EventArgs e)
        {

            #region 校验
            if (this.txtEmpId.Tag==null||this.txtEmpId.Tag.ToString().Length==0)
            {
                MessageBox.Show("请扫入员工工号！");
                return;
            }

            if (this.txtScrapQty.Text.Trim().Length == 0)
            {
                MessageBox.Show("请输入报废数量！");
                return;
            }

            try
            {
                if (Int32.Parse(this.txtScrapQty.Text.Trim()) <= 0)
                {
                    MessageBox.Show("请输入正确报废数量！");
                    return;
                }

                if (this.editSws.rp_qty < Decimal.Parse(this.txtScrapQty.Text.Trim()))
                {
                    MessageBox.Show("报废数量不可大于可报工量！");
                    return;
                }
            }
            catch
            {
                MessageBox.Show("请输入正确报废数量！");
                this.txtScrapQty.Text = "0";
                this.txtScrapQty.Focus();
                return;
            }

            #endregion


            DialogResult result = MessageBox.Show("确定报废？", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.None, MessageBoxDefaultButton.Button1);
            if (result == DialogResult.Yes)
            {

                try
                {
                    SWS_SCRAP_VIEW requestEntity = new SWS_SCRAP_VIEW();
          
                    requestEntity.emp_id = this.txtEmpId.Text.Trim();
                    requestEntity.inspector = this.txtInspectorId.Text.Trim();
                    requestEntity.rac_id = this.editSws.rac_id;
                    requestEntity.rac_name = this.editSws.rac_name;
                    requestEntity.rp_guid = swsRpGuid;
                    requestEntity.scrap_part = "";
                    requestEntity.scrap_qty = Decimal.Parse(this.txtScrapQty.Text);
                    requestEntity.scrap_reason_id = ((PARA_MAIN)this.cbScrapReason.SelectedItem).id;
                    requestEntity.scrap_reason_name = ((PARA_MAIN)this.cbScrapReason.SelectedItem).para_value;
                    requestEntity.scrap_content_id = ((PARA_MAIN)this.cbScrapContent.SelectedItem).id;
                    requestEntity.scrap_content_name = ((PARA_MAIN)this.cbScrapContent.SelectedItem).para_value;
                    requestEntity.scrap_type = 0;  //scrap type . full scrap
                    requestEntity.sws_guid = this.editSws.sws_guid;
                    requestEntity.sws_id = this.editSws.sws_id;
                    requestEntity.wo_id = this.editSws.wo_id;

                    //requestEntity.happen_rac_seqno = ((RAC_VIEW)this.cbRac.SelectedItem).rac_seqno;
                    //requestEntity.happen_rac_name = ((RAC_VIEW)this.cbRac.SelectedItem).rac_name;
                    //requestEntity.scrap_on_rac = this.editSws.scrap_on_rac;

                    BasicRequest<SWS_SCRAP_VIEW, SWS_SCRAP_VIEW> Request = new BasicRequest<SWS_SCRAP_VIEW, SWS_SCRAP_VIEW>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_entity = requestEntity;

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("wo/addScrap", "POST", "application/json", RequestStr);
                    BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        MessageBox.Show("保存成功！");

                        this.Close();
                        this.Dispose();

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
        }

    }
}