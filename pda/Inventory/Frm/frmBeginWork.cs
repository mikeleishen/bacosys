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
    public partial class frmBeginWork : Form
    {
        private SWS_DOC editSws = new SWS_DOC();
        private SWS_RAC_VIEW editRac;
        private List<EMP_MAIN_VIEW> empList = new List<EMP_MAIN_VIEW>();
        private List<String> wsList = new List<string>();
        private bool loadingData = true;

        public frmBeginWork()
        {
            InitializeComponent();
        }

        public static frmBeginWork GetInstance(string title)
        {
            frmBeginWork Own = new frmBeginWork();
            Own.Text = title;

            Own.lvEmp.View = View.Details;
            Own.lvEmp.Activation = ItemActivation.Standard;
            Own.lvEmp.FullRowSelect = true;
            Own.lvEmp.CheckBoxes = false;
            Own.lvEmp.Columns.Add("工号", 80, HorizontalAlignment.Left);
            Own.lvEmp.Columns.Add("姓名", 160, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvEmp.ContextMenu = lvMenu;

            DateTime bgDt = DateTime.Now;
            Own.txtBDate.Text = bgDt.ToString("yyMMdd");
            Own.txtBHour.Text = bgDt.Hour.ToString();
            Own.txtBMinute.Text = bgDt.Minute.ToString();

            Own.Show();
            Own.txtSwsId.Focus();
            return Own;
        }

        private void lvMenuItem_Click(object sender, EventArgs e)
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

                empListBind();
                clear();
            }
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
                    this.txtBDate.Focus();
                }
            }
        }

        private void txtSwsId_LostFocus(object sender, EventArgs e)
        {
            if (this.txtSwsId.Text.Trim().Length == 0) return;

            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtSwsId.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/getSwsById", "POST", "application/json", RequestStr);
                BasicResponse<SWS_DOC, SWS_DOC> Data = JsonConvert.DeserializeObject<BasicResponse<SWS_DOC, SWS_DOC>>(ResponseStr);

                if (Data.status == "0")
                {
                    loadingData = true;
                    editSws = Data.dataEntity;

                    if (String.IsNullOrEmpty(editSws.swsdoc.guid))
                    {
                        MessageBox.Show("未找到流程票！");
                        this.txtSwsId.Text = "";
                        this.txtSwsId.Focus();
                        editSws = null;
                        return;
                    }

                    if (editSws.swsdoc.sws_status == 1)
                    {
                        MessageBox.Show("流程票已入库！");
                        this.txtSwsId.Text = "";
                        this.txtSwsId.Focus();
                        editSws = null;
                        return;
                    }

                    if (editSws.raclist != null && editSws.raclist.Count > 0)
                    {
                        this.lblWoId.Text = "工单：" + editSws.swsdoc.wo_id;

                        this.txtSwsId.Text = editSws.swsdoc.itm_id;
                        this.txtSwsId.Enabled = false;

                        this.cbRac.DataSource = editSws.raclist;
                        this.cbRac.ValueMember = "rac_seqno";
                        this.cbRac.DisplayMember = "rac_name";

                        this.editRac = editSws.raclist[this.cbRac.SelectedIndex];
                        MessageBox.Show("工序：" + this.editRac.rac_name + "，可移出量为：" + this.editRac.rp_qty.ToString());

                        this.txtBDate.Text = DateTime.Now.ToString("yyMMdd");
                        this.txtBHour.Text = DateTime.Now.Hour.ToString();
                        this.txtBMinute.Text = DateTime.Now.Minute.ToString();

                        bindWsList();
                        this.cbWs.Focus();
                    }
                    else
                    {
                        MessageBox.Show("未找到可开工的工序信息！");

                        this.txtSwsId.Text = "";
                        this.txtSwsId.Enabled = true;
                        this.txtSwsId.Focus();

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
            finally
            {
                loadingData = false;
            }
        }

        private void txtBMinute_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtBMinute.Text.Trim().Length == 0)
                {
                    this.txtBMinute.Text = "";
                    this.txtBMinute.Focus();
                }
                else
                {
                    this.tabControl.SelectedIndex = 1;
                    this.txtEmpId.Focus();
                }
            }
        }

        private void cbWs_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.txtWsNo.Focus();
            this.txtWsNo.SelectionStart = this.txtWsNo.Text.Length;
        }

        private void txtWsNo_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtWsNo.Text.Trim().Length == 0)
                {
                    this.txtWsNo.Text = "";
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

                            int permitEmpCount = 0;
                            if (this.wsList.Count > 0)
                            {
                                permitEmpCount = Int32.Parse(this.wsList[this.cbWs.SelectedIndex].Split(',')[1]);
                            }

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

                    int permitEmpCount = 0;
                    if (this.wsList.Count > 0)
                    {
                        permitEmpCount = Int32.Parse(this.wsList[this.cbWs.SelectedIndex].Split(',')[1]);
                    }
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

                this.empListBind();
            }
            catch (Exception ex) { MessageBox.Show(ex.Message); }
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

            ////Add by xiz for 一个员工只能在一个开工状态的工序上
            //var obj = this.editSws.emplist.Where(p => p.emp_main_id.Equals(empId));
            //if (obj != null && obj.Count() > 0)
            //{
            //    EMP_MAIN_VIEW empView=obj.First();
            //    MessageBox.Show("（" + empId + "）该员工已经在工序（"+empView.rp_rac_name+"）中！");
            //    return false;
            //}

            BasicRequest<string, string> Request = new BasicRequest<string, string>();
            Request.token = HttpWebRequestProxy.token;
            Request.data_char = empParams[0];

            string RequestStr = JsonConvert.SerializeObject(Request);
            string ResponseStr = HttpWebRequestProxy.PostRest("wo/getEmpById", "POST", "application/json", RequestStr);
            BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<EMP_MAIN_VIEW, EMP_MAIN_VIEW>>(ResponseStr);

            if (Data.status == "0")
            {
                EMP_MAIN_VIEW emp = Data.dataEntity;

                if (!String.IsNullOrEmpty(emp.emp_main_id))
                {
                    empList.Add(emp);
                    this.txtEmpId.Text = "";
                    this.txtEmpId.Focus();
                }
                else
                {
                    MessageBox.Show("未找到员工（" + empId + "）信息！");

                    this.txtEmpId.Text = "";
                    this.txtEmpId.Focus();

                    return false;
                }

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

            return true;
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

        public void clear()
        {
            this.txtEmpId.Text = "";
            this.txtEmpId.Tag = null;
 
            this.txtEmpId.Focus();
        }

        public void clearAll()
        {
            this.cbWs.Items.Clear();
            this.txtWsNo.Text = "";
            this.txtSwsId.Text = "";
            this.txtSwsId.Tag = null;
            this.txtSwsId.Enabled = true;
            this.cbRac.Enabled = true;
            this.cbRac.DataSource = new List<SWS_RAC_VIEW>();
            this.txtBDate.Text = "";
            this.txtBHour.Text = "";
            this.txtBMinute.Text = "";
            this.txtEmpId.Text = "";
            this.txtEmpId.Tag = null;
            this.lblWoId.Text = "";

            empList.Clear();
            lvEmp.Items.Clear();

            this.tabControl.SelectedIndex = 0;
            this.txtSwsId.Focus();
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            this.clearAll();
        }

        private void miOk_Click(object sender, EventArgs e)
        {
            if (this.empList.Count == 0)
            {
                MessageBox.Show("请扫入开工员工！");
                return;
            }

            int permitEmpCount = 0;
            if (this.wsList.Count > 0)
            {
                permitEmpCount = Int32.Parse(this.wsList[this.cbWs.SelectedIndex].Split(',')[1]);
            }

            if (permitEmpCount > 0 && this.empList.Count != permitEmpCount)
            {
                MessageBox.Show("本工序需要：" + permitEmpCount.ToString() + "个员工！");
                return;
            }

            try
            {
                DateTime date = new DateTime(Int32.Parse("20" + this.txtBDate.Text.Substring(0, 2)), Int32.Parse(this.txtBDate.Text.Substring(2, 2)), Int32.Parse(this.txtBDate.Text.Substring(4, 2)), Int32.Parse(this.txtBHour.Text.Trim()), Int32.Parse(this.txtBMinute.Text.Trim()), 0);
                DateTime javaDate = new DateTime(1970, 1, 1, 8, 0, 0);
                TimeSpan javaDiff = date - javaDate;

                SWS_RP_DOC doc = new SWS_RP_DOC();
                doc.head = new SWS_RP();
                doc.body = new List<SWS_STAFF>();

                doc.head.bg_dt = (long)javaDiff.TotalMilliseconds;
                doc.head.finish_qty = 0;
                doc.head.rp_dt = 0;
                doc.head.rp_rac_id = this.editRac.rac_seqno;
                doc.head.rp_rac_name = this.editRac.rac_name;
                doc.head.rp_status = 1;
                doc.head.scrap_qty = 0;
                doc.head.sws_guid = this.editSws.swsdoc.guid;
                doc.head.rp_ws = "";
                doc.head.b_pda_id = System.Net.Dns.GetHostName();
                doc.head.e_pda_id = "";
                if (this.cbWs.Items.Count > 0)
                {
                    doc.head.rp_ws = this.cbWs.SelectedItem.ToString();
                    doc.head.rp_tar_value = Decimal.Parse(this.wsList[this.cbWs.SelectedIndex].Split(',')[2]);
                }
                doc.head.rp_ws_no = this.txtWsNo.Text.Trim();
                doc.head.itm_id = this.txtSwsId.Text.Trim();

                for (int i = 0; i < this.empList.Count; i++)
                {
                    SWS_STAFF staff = new SWS_STAFF();
                    staff.sws_guid = this.editSws.swsdoc.guid;
                    staff.emp_guid = this.empList[i].emp_main_guid;
                    doc.body.Add(staff);
                }

                BasicRequest<SWS_RP_DOC, SWS_RP_DOC> Request = new BasicRequest<SWS_RP_DOC, SWS_RP_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/addSwsRpDoc", "POST", "application/json", RequestStr);
                BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    MessageBox.Show("开工成功！");
                    this.clearAll();

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

        private void cbRac_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (!loadingData)
            {
                this.editRac = editSws.raclist[this.cbRac.SelectedIndex];
                MessageBox.Show("工序：" + this.editRac.rac_name + "，可移出量为：" + this.editRac.rp_qty.ToString());
                bindWsList();
            }
        }

        /// <summary>
        /// 绑定移出机器
        /// </summary>
        private void bindWsList()
        {
            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.editSws.swsdoc.wo_id;
                Request.data_char2 = this.editRac.rac_seqno;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/getRacWsList", "POST", "application/json", RequestStr);
                BasicResponse<List<string>, string> Data = JsonConvert.DeserializeObject<BasicResponse<List<string>, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    this.wsList = Data.dataEntity;

                    this.cbWs.Items.Clear();
                    for (int i = 0; i < this.wsList.Count; i++)
                    {
                        this.cbWs.Items.Add(this.wsList[i].Split(',')[0]);
                    }

                    if (this.wsList.Count > 0)
                    {
                        this.cbWs.SelectedIndex = 0;
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
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return;
            }
        }

        private void btnRp_Click(object sender, EventArgs e)
        {
            FunMenu.GetInstanceHide(Login.LoginData, "ST002");
            this.Close();
        }

        private void btnNow_Click(object sender, EventArgs e)
        {
            this.txtBDate.Text = DateTime.Now.ToString("yyMMdd");
            this.txtBHour.Text = DateTime.Now.Hour.ToString();
            this.txtBMinute.Text = DateTime.Now.Minute.ToString();
        }

        private void tabControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.tabControl.SelectedIndex == 1)
            {
                this.txtEmpId.Focus();
            }
        }

        private void btnDelBegin_Click(object sender, EventArgs e)
        {
            if (this.editRac != null && !String.IsNullOrEmpty(this.editRac.sws_rp_guid) && editSws.swsdoc.rp_status == 1)
            {
                if (MessageBox.Show("确认删除开工信息？","Confirm Message",MessageBoxButtons.OKCancel,MessageBoxIcon.Question,MessageBoxDefaultButton.Button3) != DialogResult.OK)
                {
                    return;
                }

                try
                {
                    BasicRequest<string, string> Request = new BasicRequest<string, string>();
                    Request.token = HttpWebRequestProxy.token;
                    Request.data_char = this.editRac.sws_rp_guid;

                    string RequestStr = JsonConvert.SerializeObject(Request);
                    string ResponseStr = HttpWebRequestProxy.PostRest("wo/delSwsRpPDA", "POST", "application/json", RequestStr);
                    BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                    if (Data.status == "0")
                    {
                        MessageBox.Show("删除成功！");
                        this.clearAll();
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
                    return;
                }
            }
        }
    }
}