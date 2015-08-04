using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Inventory.Model;
using Entrance;
using Inventory.Request;
using Common;
using Newtonsoft.Json;
using Inventory.Response;

namespace Inventory.Frm
{
    public partial class frmCertSWSBind : Form
    {
        List<CERT_SWS_RE_VIEW> lstRe = new List<CERT_SWS_RE_VIEW>();
        CERT_DOC_VIEW certDoc = new CERT_DOC_VIEW();
        
        public frmCertSWSBind()
        {
            InitializeComponent();
        }

        public static frmCertSWSBind GetInstance(string title)
        {
            frmCertSWSBind Own = new frmCertSWSBind();
            Own.Text = title;

            Own.lvCertRe.View = View.Details;
            Own.lvCertRe.Activation = ItemActivation.Standard;
            Own.lvCertRe.FullRowSelect = true;
            Own.lvCertRe.CheckBoxes = false;
            Own.lvCertRe.Columns.Add("流程票号", 200, HorizontalAlignment.Left);
            Own.lvCertRe.Columns.Add("数量", 60, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvCertRe.ContextMenu = lvMenu;

            Own.Show();

            Own.txtCertId.Focus();

            return Own;
        }

        private void lvMenuItem_Click(object sender, EventArgs e)
        {
            string action = (sender as MenuItem).Text;
            if (action == "删除")
            {
                if (this.lvCertRe.SelectedIndices.Count < 1)
                {
                    return;
                }

                string swsId = lvCertRe.Items[lvCertRe.SelectedIndices[0]].SubItems[0].Text;

                for (int i = 0; i < lstRe.Count; i++)
                {
                    if (lstRe[i].sws_id == swsId)
                    {
                        lstRe.RemoveAt(i);
                        break;
                    }
                }

                BindCertRe();
            }
        }

        private void txtCertId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtCertId.Text.Trim().Length == 0)
                {
                    return;
                }
                else
                {
                    this.btnClear.Focus();
                }
            }
        }

        private void txtCertId_LostFocus(object sender, EventArgs e)
        {
            if (this.txtCertId.Text.Trim().Length == 0)
            { return; }

            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtCertId.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/getCertById", "POST", "application/json", RequestStr);
                BasicResponse<CERT_DOC_VIEW, CERT_DOC_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<CERT_DOC_VIEW, CERT_DOC_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    certDoc = Data.dataEntity;

                    if (String.IsNullOrEmpty(certDoc.guid))
                    {
                        throw new Exception("未找到合格证："+this.txtCertId.Text.Trim());
                    }

                    if (certDoc.cert_status ==1)
                    {
                        throw new Exception("合格证：" + this.txtCertId.Text.Trim()+" 已经绑定！");
                    }
                    else if (certDoc.cert_status == 2)
                    {
                        throw new Exception("合格证：" + this.txtCertId.Text.Trim() + " 已经入库！");
                    }
                    else if (certDoc.cert_status != 0)
                    {
                        throw new Exception("合格证：" + this.txtCertId.Text.Trim() + " 状态异常！");
                    }


                    txtCertId.Enabled = false;
                    txtCertId.Tag = certDoc;

                    lblItmId.Text = certDoc.itm_id;
                    lblItmName.Text = certDoc.itm_name;
                    lblItmQty.Text = certDoc.itm_qty.ToString();

                    txtSWSId.Focus();

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
                ClearAll();
            }
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            ClearAll();
        }

        private void txtSWSId_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtSWSId.Text.Trim().Length == 0)
                {
                    return;
                }
                else
                {
                    this.btnClear.Focus();
                }
            }
        }

        private void txtSWSId_LostFocus(object sender, EventArgs e)
        {
            if (this.txtCertId.Text.Trim().Length == 0 || this.txtCertId.Tag == null)
            {
                return;
            }
            if (this.txtSWSId.Text.Trim().Length == 0)
            {
                return;
            }

            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtSWSId.Text.Trim();
                Request.data_char2 = this.lblItmId.Text;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/getSwsForCertBind", "POST", "application/json", RequestStr);
                BasicResponse<SUB_WO_SUB_VIEW, SUB_WO_SUB_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<SUB_WO_SUB_VIEW, SUB_WO_SUB_VIEW>>(ResponseStr);

                if (Data.status == "0")
                {
                    SUB_WO_SUB_VIEW sws = Data.dataEntity;

                    if (String.IsNullOrEmpty(sws.guid))
                    {
                        throw new Exception("未找到流程票：" + this.txtSWSId.Text.Trim());
                    }

                    if (sws.finish_qty <= 0)
                    {
                        throw new Exception("流程票：" + this.txtSWSId.Text.Trim() + "的可用数量为0！");
                    }

                    txtSWSId.Tag = sws;

                    txtSWSQty.Text = sws.finish_qty.ToString();
                    txtSWSQty.Focus();

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
                ClearSWSId();
            }
        }

        private void txtSWSQty_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Enter)
            {
                if (this.txtSWSQty.Text.Trim().Length == 0)
                {
                    return;
                }
                else
                {
                    this.btnClear.Focus();
                }
            }          

        }

        private void txtSWSQty_LostFocus(object sender, EventArgs e)
        {
            if (this.txtSWSQty.Text.Trim().Length == 0)
            {
                return;
            }
            if (this.txtSWSId.Text.Trim().Length == 0||this.txtSWSId.Tag==null)
            {
                return;
            }

            try
            {
                decimal swsQty = 0;
                try
                {
                    swsQty = decimal.Parse(this.txtSWSQty.Text.Trim());
                }
                catch
                {

                    throw new Exception("流程票数量格式错误！");
                }

                SUB_WO_SUB_VIEW sub = (SUB_WO_SUB_VIEW)txtSWSId.Tag;
                if (swsQty > sub.finish_qty)
                {
                    throw new Exception("流程票数量不可大于可用数量（" + sub.finish_qty.ToString() + "）！");
                }

                decimal swsQtyCount = swsQty;
                foreach (CERT_SWS_RE_VIEW re in lstRe)
                {
                    swsQtyCount += re.sws_qty;
                    if (re.sws_id.Equals(txtSWSId.Text.Trim()))
                    {
                        throw new Exception("流程票（" + txtSWSId.Text.Trim() + "）已经添加！");
                    }
                }

                if (swsQtyCount > certDoc.itm_qty)
                {
                    throw new Exception("流程票总数量不可大于合格证装箱数量（" + certDoc.itm_qty.ToString() + "）！");
                }

                lstRe.Add(new CERT_SWS_RE_VIEW()
                {
                    cert_doc_guid = certDoc.guid,
                    cert_doc_id = txtCertId.Text.Trim(),
                    sws_guid = sub.guid,
                    sws_id = txtSWSId.Text.Trim(),
                    sws_qty = swsQty
                });

                BindCertRe();

                ClearSWSId();

            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
                ClearSWSQty();
            }          
        }

        private void miOK_Click(object sender, EventArgs e)
        {
            if (lstRe.Count == 0)
            {
                return;
            }

            try
            {
                CERT_SWS_DOC doc=new CERT_SWS_DOC ();
                doc.certDoc=certDoc;
                doc.swsList=lstRe;


                BasicRequest<CERT_SWS_DOC, CERT_SWS_DOC> Request = new BasicRequest<CERT_SWS_DOC, CERT_SWS_DOC>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = doc;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/doCertSwsBind", "POST", "application/json", RequestStr);
                BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    MessageBox.Show("操作成功！");
                    ClearAll();
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

        private void BindCertRe()
        {
            this.lvCertRe.Items.Clear();
            if (lstRe == null) return;
            string[] cols = new string[2];

            for (int i = 0; i < lstRe.Count; i++)
            {
                cols[0] = lstRe[i].sws_id;
                cols[1] = lstRe[i].sws_qty.ToString();

                ListViewItem lvItem = new ListViewItem(cols);
                lvCertRe.Items.Add(lvItem);
            }
        }

        private void ClearAll()
        {
            ClearCertDoc();

            txtSWSId.Text = "";
            txtSWSId.Tag = null;
            txtSWSQty.Text = "";

            this.lvCertRe.Items.Clear();

            lstRe = new List<CERT_SWS_RE_VIEW>();
            certDoc = new CERT_DOC_VIEW();
        }

        private void ClearCertDoc()
        {
            txtCertId.Text = "";
            txtCertId.Enabled = true;
            txtCertId.Tag = null;

            lblItmId.Text = "";
            lblItmName.Text = "";
            lblItmQty.Text = "";

            txtCertId.Focus();

        }

        private void ClearSWSId()
        {
            txtSWSId.Text = "";
            txtSWSId.Tag = null;

            txtSWSQty.Text = "";

            txtSWSId.Focus();
        }

        private void ClearSWSQty()
        {
            txtSWSQty.Text = "";
            txtSWSQty.Focus();
        }
    }
}