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
    public partial class frmEndWork : Form
    {
        private SWS_DOC editSws = new SWS_DOC();
        private SWS_RAC_VIEW editRac;
        private List<EMP_MAIN_VIEW> empList = new List<EMP_MAIN_VIEW>();
        private string empId = string.Empty;

        public frmEndWork()
        {
            InitializeComponent();
        }

        public static frmEndWork GetInstance(string title)
        {
            frmEndWork Own = new frmEndWork();
            Own.Text = title;

            Own.lvEmp.View = View.Details;
            Own.lvEmp.Activation = ItemActivation.Standard;
            Own.lvEmp.FullRowSelect = true;
            Own.lvEmp.CheckBoxes = false;
            Own.lvEmp.Columns.Add("工号", 80, HorizontalAlignment.Left);
            Own.lvEmp.Columns.Add("姓名", 160, HorizontalAlignment.Left);

            Own.Show();
            Own.txtSwsId.Focus();
            return Own;
        }

        private void txtSwsId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtSwsId.Text.Trim().Length == 0)
                {
                    this.txtSwsId.Text = "";
                    this.txtSwsId.Focus();
                }
                else
                {
                    this.txtEmpId.Focus();
                }
            }
        }

        private void txtEmpId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtEmpId.Text.Trim().Length == 0)
                {
                    this.txtEmpId.Text = "";
                    this.txtEmpId.Focus();
                }
                else
                {
                    this.txtRPWsNo.Focus();
                }
            }
        }

        private void txtEmpId_LostFocus(object sender, EventArgs e)
        {
            this.txtInfo.Text = "";
            
            if (this.txtSwsId.Text.Trim().Length == 0) return;
            if (this.txtEmpId.Text.Trim().Length == 0) return;

            try
            {
                empId = GetEmpId(txtEmpId.Text.Trim());
                if (string.IsNullOrEmpty(empId))
                {
                    throw new Exception("员工条码格式不正确！");
                }

                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtSwsId.Text.Trim();
                Request.data_char2 = empId;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/GetSwsRpDoc", "POST", "application/json", RequestStr);
                BasicResponse<SWS_DOC, SWS_DOC> Data = JsonConvert.DeserializeObject<BasicResponse<SWS_DOC, SWS_DOC>>(ResponseStr);

                if (Data.status == "0")
                {
                    List<SWS_RAC_VIEW> rpList = Data.dataEntity.raclist;
                    if (rpList.Count == 0)
                    {
                        throw new Exception("未找到符合条件的报工单！");
                    }

                    if (rpList.Count > 1)
                    {
                        txtInfo.Text = "员工在多个开工的工序中，请输入设备号";
                        this.txtRPWsNo.Focus();

                        return;
                    }


                    editSws = Data.dataEntity;
                    if (editSws.raclist != null && editSws.raclist.Count > 0)
                    {
                        editRac = Data.dataEntity.raclist.First();
                        long serviceTime = Data.svrdt;

                        GetRpInfo(serviceTime);
                    }
                    else
                    {
                        MessageBox.Show("未找到流程票信息！");

                        this.clearAll();

                        return;
                    }

                    this.txtRpQty.Focus();
                    this.txtRpQty.SelectionStart = this.txtRpQty.Text.Length;

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

                this.txtEmpId.Text = "";
                this.txtEmpId.Focus();
            }
        }

        private void txtRPWsNo_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtRPWsNo.Text.Trim().Length == 0)
                {
                    this.txtRPWsNo.Text = "";
                    this.txtRPWsNo.Focus();
                }
                else
                {
                    this.txtRpQty.Focus();
                }
            }
        }

        private void txtRPWsNo_LostFocus(object sender, EventArgs e)
        {
            if (this.txtSwsId.Text.Trim().Length == 0) return;
            if (this.txtEmpId.Text.Trim().Length == 0) return;
            if (this.txtRPWsNo.Text.Trim().Length == 0) return;
            
            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtSwsId.Text.Trim();
                Request.data_char2 = empId;
                Request.data_char3 = txtRPWsNo.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/GetSwsRpDoc", "POST", "application/json", RequestStr);
                BasicResponse<SWS_DOC, SWS_DOC> Data = JsonConvert.DeserializeObject<BasicResponse<SWS_DOC, SWS_DOC>>(ResponseStr);

                if (Data.status == "0")
                {
                    editSws = Data.dataEntity;

                    if (editSws.raclist != null && editSws.raclist.Count > 0)
                    {
                        editRac = Data.dataEntity.raclist.First();
                        long serviceTime = Data.svrdt;

                        GetRpInfo(serviceTime);
                    }
                    else
                    {
                        MessageBox.Show("未找到流程票信息！");

                        this.clearAll();

                        return;
                    }

                    this.txtRpQty.Focus();
                    this.txtRpQty.SelectionStart = this.txtRpQty.Text.Length;
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

                this.txtRPWsNo.Text = "";
                this.txtRPWsNo.Focus();
            }
        }

        private void GetRpInfo(long serviceTime)
        {
            //流程票号显示为图号
            this.txtSwsId.Text = editSws.swsdoc.itm_id;
            this.lblSwsId.Text = editSws.swsdoc.id;

            this.txtSwsId.Enabled = false;
            this.txtEmpId.Enabled = false;
            this.txtRPWsNo.Enabled = false;

            //工艺名称
            string racName = editRac.rac_name;
            if (racName.Contains('\\'))
            {
                racName = racName.Split('\\')[0];
            }
            if (racName.Contains('('))
            {
                racName = racName.Substring(0, racName.IndexOf("("));
            }
            this.lblRac.Text = "工艺：" + racName;

            //开工时间
            this.lblBg.Text = "开工：" + new DateTime(1970, 1, 1, 8, 0, 0, DateTimeKind.Local).AddMilliseconds(editRac.bg_date).ToString("yyMMddHHmm");

            //本工艺的整体报废数量
            this.txtScrapQty.Text = editRac.scrap_qty.ToString();
            //本工艺的可报工量
            this.lblRestRPQty.Text = "可报工量：" + editRac.rp_qty.ToString();


            //报废量为0，则报工量默认可报工数量
            if (editRac.scrap_qty <= 0)
            {
                this.txtRpQty.Text = editRac.rp_qty.ToString();
            }
            else//默认为0
            {
                this.txtRpQty.Text = "0";
            }

            //报工量的特殊处理
            long useTime = 0;
            if (editSws.swsdoc.itm_id == "123456" || editSws.swsdoc.itm_id == "654321" || editSws.swsdoc.itm_id == "1234567")
            {
                useTime = serviceTime - editRac.bg_date;//当前时间-开工时间
                int RpQtyCount = Convert.ToInt32((useTime - 1) / 60000 + 1);
                this.txtRpQty.Text = RpQtyCount.ToString();

            }
            else
            {
                this.txtRpQty.SelectionStart = this.txtRpQty.Text.Length;
                this.txtRpQty.Focus();
            }

            //完工日期
            this.txtBDate.Text = DateTime.Now.ToString("yyMMdd");
            this.txtBHour.Text = DateTime.Now.Hour.ToString();
            this.txtBMinute.Text = DateTime.Now.Minute.ToString();

            empList = editSws.emplist;
            this.empListBind();
        }



        private void btnBg_Click(object sender, EventArgs e)
        {
            FunMenu.GetInstanceHide(Login.LoginData, "ST001");
            this.Close();
        }

        private void txtScrapQty_LostFocus(object sender, EventArgs e)
        {
            int scrapQty = 0;
            try
            {
                scrapQty = Int32.Parse(this.txtScrapQty.Text.Trim());
                this.txtScrapQty.Text = scrapQty.ToString();
            }
            catch
            {
                this.txtScrapQty.Text = "0";
            }
        }

        private void txtRpQty_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtRpQty.Text.Trim().Length == 0)
                {
                    this.txtRpQty.Text = "0";
                    this.txtRpQty.Focus();
                }
                else
                {
                    this.txtBDate.SelectionStart = this.txtBDate.Text.Length;
                    this.txtBDate.Focus();
                }
            }
        }


        private void txtBDate_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtBDate.Text.Trim().Length == 0)
                {
                    this.txtBDate.Focus();
                    return;
                }
                else
                {
                    this.txtBHour.SelectionStart = this.txtBHour.Text.Length;
                    this.txtBHour.Focus();
                }
            }
        }

        private void txtBHour_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtBHour.Text.Trim().Length == 0)
                {
                    this.txtBHour.Focus();
                    return;
                }
                else
                {
                    this.txtBMinute.SelectionStart = this.txtBMinute.Text.Length;
                    this.txtBMinute.Focus();
                }
            }
        }

        private void txtBMinute_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtBMinute.Text.Trim().Length == 0)
                {
                    this.txtBMinute.Focus();
                    return;
                }
                else
                {
                    this.miOk_Click(this.miOk,null);
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

        private void btnClear_Click(object sender, EventArgs e)
        {
            clearAll();
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            if (this.txtRpQty.Text.Trim().Length == 0)
            {
                MessageBox.Show("请输入完工数量！");
                return;
            }

            try
            {
                if (Int32.Parse(this.txtRpQty.Text.Trim()) < 0)
                {
                    MessageBox.Show("请输入正确完工数量！");
                    return;
                }

                //完工量为0允许报工
                if (Int32.Parse(this.txtRpQty.Text.Trim()) != 0)
                {
                    if (this.editRac.rp_qty < Decimal.Parse(this.txtRpQty.Text.Trim()))
                    {
                        MessageBox.Show("完工数量不能大于上道工序完工量或者流程票数量！");
                        return;
                    }
                }
                else
                {
                    DialogResult result = MessageBox.Show("数量为0，确定报工？", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.None, MessageBoxDefaultButton.Button2);
                    if (result == DialogResult.No)
                    {
                        return;
                    }
                }
            }
            catch
            {
                MessageBox.Show("请输入正确完工数量！");
                return;
            }

            DateTime date = new DateTime(Int32.Parse("20" + this.txtBDate.Text.Substring(0, 2)), Int32.Parse(this.txtBDate.Text.Substring(2, 2)), Int32.Parse(this.txtBDate.Text.Substring(4, 2)), Int32.Parse(this.txtBHour.Text.Trim()), Int32.Parse(this.txtBMinute.Text.Trim()), 0);
            DateTime javaDate = new DateTime(1970, 1, 1, 8, 0, 0);
            TimeSpan javaDiff = DateTime.Now - javaDate;

            SWS_RP_DOC doc = new SWS_RP_DOC();
            doc.head = new SWS_RP();
            doc.body = new List<SWS_STAFF>();

            if (javaDiff.TotalMilliseconds < editRac.bg_date)
            {
                MessageBox.Show("报工时间必须晚于开工时间！");
                return;
            }

            doc.head.guid = editRac.sws_rp_guid;
            doc.head.bg_dt = editRac.bg_date;
            doc.head.rp_dt = (long)javaDiff.TotalMilliseconds;
            doc.head.finish_qty = Decimal.Parse(this.txtRpQty.Text.Trim());
            doc.head.rp_rac_id = this.editRac.rac_seqno;
            doc.head.sws_guid = this.editSws.swsdoc.guid;
            doc.head.scrap_qty = Decimal.Parse(this.txtScrapQty.Text.Trim());
            doc.head.sws_id = this.editSws.swsdoc.id;
            doc.head.e_pda_id = System.Net.Dns.GetHostName();

            doc.head.itm_id = this.editSws.swsdoc.itm_id;
            doc.head.rp_ws = editRac.rp_ws;
            doc.head.rp_ws_no = editRac.rp_ws_no;

            for (int i = 0; i < this.empList.Count; i++)
            {
                SWS_STAFF staff = new SWS_STAFF();
                staff.sws_guid = this.editSws.swsdoc.guid;
                staff.emp_guid = this.empList[i].emp_main_guid;
                staff.emp_id = this.empList[i].emp_main_id;

                doc.body.Add(staff);
            }

            try
            {
                BasicRequest<SWS_RP_DOC, SWS_RP_DOC> Request = new BasicRequest<SWS_RP_DOC, SWS_RP_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/rpSwsRp", "POST", "application/json", RequestStr);
                BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    MessageBox.Show("报工成功！");
                    this.clearAll();

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


        public void empListBind()
        {
            lvEmp.Items.Clear();
            if (empList == null) return;
            string[] cols = new string[2];

            for (int i = 0; i < empList.Count; i++)
            {
                cols[0] = empList[i].emp_main_id;
                cols[1] = empList[i].emp_name;

                ListViewItem lvItem = new ListViewItem(cols);
                lvEmp.Items.Add(lvItem);
            }
        }

        public void clearAll()
        {
            this.txtSwsId.Text = "";
            this.txtSwsId.Tag = null;
            this.txtSwsId.Enabled = true;

            this.txtEmpId.Text = "";
            this.txtEmpId.Enabled = true;
            empId = "";

            this.txtInfo.Text = "";

            this.txtRPWsNo.Text = "";
            this.txtRPWsNo.Enabled = true;

            this.lblSwsId.Text = "";

            this.txtBDate.Text = "";
            this.txtBHour.Text = "";
            this.txtBMinute.Text = "";

            this.txtRpQty.Text = "";
            this.lblRestRPQty.Text = "";

            this.lblBg.Text = "开工：";
            this.lblRac.Text = "工艺：";

            this.txtScrapQty.Text = "0";

            empList.Clear();
            lvEmp.Items.Clear();

            editSws = new SWS_DOC();
            editRac = new SWS_RAC_VIEW();

            this.txtSwsId.Focus();
        }

        private string GetEmpId(string empBaco)
        {
            string empId = "";
            
            //扫通码 即多个EMP_ID
            if (this.txtEmpId.Text.Trim().Substring(0, 2) == "SP")
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtEmpId.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("SpecialCode/GetCodeMain", "POST", "application/json", RequestStr);
                BasicResponse<CODE_MAIN, CODE_MAIN> Data = JsonConvert.DeserializeObject<BasicResponse<CODE_MAIN, CODE_MAIN>>(ResponseStr);

                if (Data.status == "0")
                {
                    CODE_MAIN co = Data.dataEntity;
                    if (!String.IsNullOrEmpty(co.code_main_id))
                    {
                        List<String> empNewList = co.code_value.Split(';').ToList<string>();
                        if (empNewList != null && empNewList.Count > 0)
                        {
                            empId = empNewList.First();
                        }
                    }
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    txtEmpId.Text = "";
                    txtEmpId.Focus();
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                }
            }
            else
            {
                string[] empParams = this.txtEmpId.Text.Trim().Split('.');
                if (empParams.Length > 0)
                {
                    empId = empParams[0];
                }
            }

            return empId;
        }
    }
}