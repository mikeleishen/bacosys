using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Common;
using Inventory.Response;
using Inventory.Model;
using Entrance;
using Inventory.Request;
using Newtonsoft.Json;

namespace Inventory.Frm
{
    public partial class frmUpdateWorkDetail : Form
    {
        public string swsRpGuid = string.Empty;

        SWS_DOC swsRpDoc = new SWS_DOC();
        List<EMP_MAIN_VIEW> empList = new List<EMP_MAIN_VIEW>();
        SWS_RAC_VIEW editRac = new SWS_RAC_VIEW();

        public frmUpdateWorkDetail()
        {
            InitializeComponent();
        }

        public frmUpdateWorkDetail(string paramSwsRpGuid)
        {
            InitializeComponent();

            this.swsRpGuid = paramSwsRpGuid;
            if (string.IsNullOrEmpty(swsRpGuid)) return;

            this.lvEmp.View = View.Details;
            this.lvEmp.Activation = ItemActivation.Standard;
            this.lvEmp.FullRowSelect = true;
            this.lvEmp.CheckBoxes = false;
            this.lvEmp.Columns.Add("工号", 80, HorizontalAlignment.Left);
            this.lvEmp.Columns.Add("姓名", 160, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            this.lvEmp.ContextMenu = lvMenu;

            LoadData();
        }

        void lvMenuItem_Click(object sender, EventArgs e)
        {
            string action = (sender as MenuItem).Text;
            if (action == "删除")
            {
                if (lvEmp.SelectedIndices.Count < 1)
                {
                    return;
                }

                string empId = lvEmp.Items[lvEmp.SelectedIndices[0]].SubItems[0].Text;
                for (int i = 0; i < empList.Count; i++)
                {
                    if (empList[i].emp_main_id == empId)
                    {
                        empList.Remove(empList[i]);
                        break;
                    }
                }
                BindEmpList();
            }
        }

        private void LoadData()
        {

            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char =swsRpGuid;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/GetSwsRpDocByRpGuid", "POST", "application/json", RequestStr);
                BasicResponse<SWS_DOC, SWS_DOC> Data = JsonConvert.DeserializeObject<BasicResponse<SWS_DOC, SWS_DOC>>(ResponseStr);
                
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
                    swsRpDoc = Data.dataEntity;
                    if (swsRpDoc.raclist == null || string.IsNullOrEmpty(swsRpDoc.raclist[0].sws_rp_guid))
                    {
                        throw new Exception("未找到工单信息！");
                    }

                    editRac = swsRpDoc.raclist[0];

                    this.lblSwsId.Text = swsRpDoc.swsdoc.id;
                    this.lblRacId.Text = editRac.rac_name;
                    this.lblWs.Text = editRac.rp_ws;
                    this.txtWsNo.Text = editRac.rp_ws_no;

                    DateTime bgDt = new DateTime(1970, 1, 1, 8, 0, 0, DateTimeKind.Local).AddMilliseconds(editRac.bg_date).ToLocalTime();
                    this.txtBDate.Text = bgDt.ToString("yyMMdd");
                    this.txtBHour.Text = bgDt.Hour.ToString();
                    this.txtBMinute.Text = bgDt.Minute.ToString();

                    empList = swsRpDoc.emplist;
                    BindEmpList();
                }
            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }
        }


        private void txtWsNo_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtWsNo.Text.Trim().Length == 0)
                {
                    this.txtWsNo.Text = "";
                    this.txtWsNo.Focus();

                    return;
                }

                this.txtBMinute.Focus();
            }
        }

        private void btnNow_Click(object sender, EventArgs e)
        {
            this.txtBDate.Text = DateTime.Now.ToString("yyMMdd");
            this.txtBHour.Text = DateTime.Now.Hour.ToString();
            this.txtBMinute.Text = DateTime.Now.Minute.ToString();
        }

        private void txtBMinute_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtBMinute.Text.Trim().Length == 0)
                {
                    this.txtBMinute.Text = "";
                    this.txtBMinute.Focus();

                    return;
                }

                this.tabControl.SelectedIndex = 1;
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
                    this.lvEmp.Focus();
                }
            }
        }

        private void txtEmpId_LostFocus(object sender, EventArgs e)
        {
            if (this.txtEmpId.Text.Trim().Length == 0) return;

            try
            {
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

                            int permitEmpCount = editRac.tar_emp_num;

                            if (permitEmpCount > 0 && (this.lvEmp.Items.Count + empNewList.Count) > permitEmpCount)
                            {
                                MessageBox.Show("本工序最多只能有：" + permitEmpCount.ToString() + "个员工！");
                                return;
                            }

                            foreach (string empParams in empNewList)
                            {
                                if (!CheckEmp(empParams.Split('.')))
                                {
                                    return;
                                }
                            }
                        }
                    }
                    else if (Data.status == "1")
                    {
                        MessageBox.Show(Data.info);
                        this.txtEmpId.Text = "";
                        this.txtEmpId.Focus();
                        this.txtEmpId.Tag = null;
                    }
                    else if (Data.status == "2")
                    {
                        Login.ReLogin(Data.info);
                        this.txtEmpId.Text = "";
                        this.txtEmpId.Focus();
                        this.txtEmpId.Tag = null;
                    }
                }
                else
                {
                    string[] empParams = this.txtEmpId.Text.Trim().Split('.');

                    int permitEmpCount = editRac.tar_emp_num;

                    if (permitEmpCount > 0 && (this.lvEmp.Items.Count + 1) > permitEmpCount)
                    {
                        MessageBox.Show("本工序最多只能有：" + permitEmpCount.ToString() + "个员工！");
                        return;
                    }

                    if (!CheckEmp(empParams))
                    {
                        return;
                    }
                }

                this.BindEmpList();
            }
            catch (Exception ex) { MessageBox.Show(ex.Message); }
        }

        private void tabControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.tabControl.SelectedIndex == 1)
            {
                this.txtEmpId.Focus();
            }
        }

        public void BindEmpList()
        {
            lvEmp.Items.Clear();

            if (empList == null||empList.Count==0) return;

            string[] cols = new string[2];
            for (int i = 0; i < empList.Count; i++)
            {
                cols[0] = empList[i].emp_main_id;
                cols[1] = empList[i].emp_name;

                ListViewItem lvItem = new ListViewItem(cols);
                lvEmp.Items.Add(lvItem);
            }
        }

        private bool CheckEmp(string[] empParams)
        {
            string empId = "";
            List<string> techIds = new List<string>();
            List<string> racTechs = new List<string>();
            bool canOper = false;

            if (empParams.Length == 0)
            {
                MessageBox.Show("员工条码格式不正确！");
                return false;
            }

            if (empParams.Length == 1)
            {
                empId = empParams[0];
            }

            if (empParams.Length == 2)
            {
                if (empParams[1].Length % 2 != 0)
                {
                    MessageBox.Show("员工工艺代码不正确！");
                    return false;
                }

                empId = empParams[0];
                while (empParams[1].Length > 0)
                {
                    techIds.Add(empParams[1].Substring(0, 2));
                    empParams[1] = empParams[1].Substring(2, empParams[1].Length - 2);
                }
            }

            if (this.editRac.rac_tech_id.Length > 0)
            {
                string racTech = this.editRac.rac_tech_id;

                if (racTech.Length % 2 != 0)
                {
                    MessageBox.Show("工艺岗位代码不正确！");
                    return false;
                }

                while (racTech.Length > 0)
                {
                    racTechs.Add(racTech.Substring(0, 2));
                    racTech = racTech.Substring(2, racTech.Length - 2);
                }
            }
            else
            {
                //如果工序没有任何限制，就是任何人都可以参与
                canOper = true;
            }

            ;
            for (int i = 0; i < techIds.Count; i++)
            {
                if (techIds[i] == "15")
                {
                    canOper = true;
                    break;
                }

                for (int j = 0; j < racTechs.Count; j++)
                {
                    if (techIds[i] == racTechs[j])
                    {
                        canOper = true;
                        break;
                    }
                }
                if (canOper) break;
            }

            if (!canOper)
            {
                if (HttpWebRequestProxy.userroleid == "GBM")
                {
                    if (DialogResult.OK != MessageBox.Show("（" + empId + "）该员工不能参与此项工序，确认加入此员工!", "确认消息", MessageBoxButtons.OKCancel, MessageBoxIcon.Question, MessageBoxDefaultButton.Button2))
                    {
                        this.txtEmpId.Text = "";
                        this.txtEmpId.Focus();
                        return false;
                    }
                }
                else
                {
                    MessageBox.Show("（" + empId + "）该员工不能参与此项工序！");
                    this.txtEmpId.Text = "";
                    this.txtEmpId.Focus();
                    return false;
                }
            }

            for (int i = 0; i < this.empList.Count; i++)
            {
                if (this.empList[i].emp_main_id == empId)
                {
                    MessageBox.Show("（" + empId + "）该员工已经在列表中！");
                    this.txtEmpId.Text = "";
                    this.txtEmpId.Focus();
                    return false;
                }
            }

            ////校验 员工是否存在于本流程票其他开工状态的工序上
            //if (IsEmpContainedInOtherRac(empParams[0]))
            //{
            //    this.txtEmpId.Text = "";
            //    this.txtEmpId.Focus();

            //    return false;
            //}

            //校验 员工是否存在 如果存在则增加该员工
            return IsEmpExist(empParams[0]);
        }

        private bool IsEmpContainedInOtherRac(string empId)
        {
            bool result = false;

            BasicRequest<string, string> Request = new BasicRequest<string, string>();
            Request.token = HttpWebRequestProxy.token;
            Request.data_char = swsRpDoc.swsdoc.guid;

            string RequestStr = JsonConvert.SerializeObject(Request);
            string ResponseStr = HttpWebRequestProxy.PostRest("wo/GetSwsRpEmpsBySwsGuid", "POST", "application/json", RequestStr);
            BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW>>(ResponseStr);

            if (Data.status == "0")
            {
                List<EMP_MAIN_VIEW> emps = Data.dataList;
                if (emps == null || emps.Count == 0)
                {
                    return false;
                }

                var obj = emps.Where(p => p.emp_main_id.Equals(empId) && !p.rp_rac_id.Equals(editRac.rac_seqno));
                if (obj != null && obj.Count() > 0)
                {
                    MessageBox.Show("员工：" + obj.First().emp_name + " 已经在工序（" + obj.First().rp_rac_name + "）中！");
                    result = true;
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

            return result;
        }

        private bool IsEmpExist(string empId)
        {
            bool result = false;
            
            BasicRequest<string, string> Request = new BasicRequest<string, string>();
            Request.token = HttpWebRequestProxy.token;
            Request.data_char =empId;

            string RequestStr = JsonConvert.SerializeObject(Request);
            string ResponseStr = HttpWebRequestProxy.PostRest("wo/getEmpById", "POST", "application/json", RequestStr);
            BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW>>(ResponseStr);

            if (Data.status == "0")
            {
                EMP_MAIN_VIEW emp = Data.dataEntity;

                if (!String.IsNullOrEmpty(emp.emp_main_id))
                {
                    empList.Add(emp);
                    result = true;
                }
                else
                {
                    MessageBox.Show("未找到员工（" + empId + "）信息！");
                    result=false;
                }

                this.txtEmpId.Text = "";
                this.txtEmpId.Focus();
            }
            else if (Data.status == "1")
            {
                throw new Exception(Data.info);
            }
            else if (Data.status == "2")
            {
                Login.ReLogin(Data.info);
            }

            return result;
        }

        private void miOK_Click(object sender, EventArgs e)
        {
            #region 校验员工

            if (this.empList.Count == 0)
            {
                MessageBox.Show("请扫入开工员工！");
                return;
            }

            int permitEmpCount = editRac.tar_emp_num; 
            if (permitEmpCount > 0 && this.empList.Count != permitEmpCount)
            {
                MessageBox.Show("本工序需要：" + permitEmpCount.ToString() + "个员工！");
                return;
            }

            #endregion

            try
            {
                DateTime date = new DateTime(Int32.Parse("20" + this.txtBDate.Text.Substring(0, 2)), Int32.Parse(this.txtBDate.Text.Substring(2, 2)), Int32.Parse(this.txtBDate.Text.Substring(4, 2)), Int32.Parse(this.txtBHour.Text.Trim()), Int32.Parse(this.txtBMinute.Text.Trim()), 0);
                DateTime javaDate = new DateTime(1970, 1, 1, 8, 0, 0);
                TimeSpan javaDiff = date - javaDate;

                SWS_RP_DOC doc = new SWS_RP_DOC();
                doc.head = new SWS_RP();
                doc.head.bg_dt = (long)javaDiff.TotalMilliseconds;
                doc.head.sws_guid = this.swsRpDoc.swsdoc.guid;
                doc.head.rp_rac_id = this.editRac.rac_seqno;
                doc.head.rp_ws_no = this.txtWsNo.Text.Trim();
                doc.head.guid = this.editRac.sws_rp_guid;

                doc.body = new List<SWS_STAFF>();

                for (int i = 0; i < this.empList.Count; i++)
                {
                    SWS_STAFF staff = new SWS_STAFF();
                    staff.sws_guid = this.swsRpDoc.swsdoc.guid;
                    staff.emp_guid = this.empList[i].emp_main_guid;
                    doc.body.Add(staff);
                }

                BasicRequest<SWS_RP_DOC, SWS_RP_DOC> Request = new BasicRequest<SWS_RP_DOC, SWS_RP_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/upSwsRpDoc", "POST", "application/json", RequestStr);
                BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    MessageBox.Show("修改开工成功！");
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

        private void miCancel_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("确定取消？", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.None, MessageBoxDefaultButton.Button2);
            if (result == DialogResult.Yes)
            {
                this.Close();
                this.Dispose();
            }
        }
    }
}