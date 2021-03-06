﻿using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Inventory.Request;
using Newtonsoft.Json;
using Common;
using Inventory.Response;
using Inventory.Model;
using Entrance;

namespace Inventory.Frm
{
    public partial class frmUpdateWork : Form
    {
        private SWS_DOC swsDoc = new SWS_DOC();
        string swsId = string.Empty;

        public frmUpdateWork()
        {
            InitializeComponent();
        }

        public static frmUpdateWork GetInstance(string title)
        {
            frmUpdateWork Own = new frmUpdateWork();
            Own.Text = title;

            Own.lvRPList.View = View.Details;
            Own.lvRPList.Activation = ItemActivation.Standard;
            Own.lvRPList.FullRowSelect = true;
            Own.lvRPList.CheckBoxes = false;
            Own.lvRPList.Columns.Add("工序", 80, HorizontalAlignment.Left);
            Own.lvRPList.Columns.Add("工艺名", 160, HorizontalAlignment.Left);


            Own.Show();
            Own.txtSwsId.Focus();
            return Own;
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            ClearAll();
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
                    this.lvRPList.Focus();
                }
            }
        }

        private void txtSwsId_LostFocus(object sender, EventArgs e)
        {
            if (this.txtSwsId.Text.Trim().Length == 0) return;
            try
            {
                swsId = this.txtSwsId.Text.Trim();

                LoadRpList();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void LoadRpList()
        {
            BasicRequest<string, string> Request = new BasicRequest<string, string>();
            Request.token = HttpWebRequestProxy.token;
            Request.data_char = swsId;

            string RequestStr = JsonConvert.SerializeObject(Request);
            string ResponseStr = HttpWebRequestProxy.PostRest("wo/GetSwsRpBySwsId", "POST", "application/json", RequestStr);
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
                swsDoc = Data.dataEntity;


                if (swsDoc.swsdoc==null||String.IsNullOrEmpty(swsDoc.swsdoc.guid))
                {
                    MessageBox.Show("未找到流程票！");
                    ClearAll();
                    return;
                }

                if (swsDoc.swsdoc.sws_status == 1)
                {
                    MessageBox.Show("流程票已入库！");
                    ClearAll();
                    return;
                }

                if (swsDoc.raclist == null || swsDoc.raclist.Count == 0)
                {
                    MessageBox.Show("未找到流程票开工信息！");
                    ClearAll();
                    return;
                }

                this.lblWoId.Text = "工单：" + swsDoc.swsdoc.wo_id;

                this.txtSwsId.Text = swsDoc.swsdoc.itm_id;
                this.txtSwsId.Enabled = false;

                BindRpList();
            }
        }

        private void BindRpList()
        {
            this.lvRPList.Items.Clear();
            for (int i = 0; i < swsDoc.raclist.Count; i++)
            {
                string[] cols = new string[2];
                cols[0] = swsDoc.raclist[i].rac_id;
                cols[1] = swsDoc.raclist[i].rac_name;

                ListViewItem item = new ListViewItem(cols);

                this.lvRPList.Items.Add(item);
            }
        }

        private void miOK_Click(object sender, EventArgs e)
        {
            try
            {
                if (this.lvRPList.Items.Count == 0) return;
                if (this.lvRPList.SelectedIndices.Count < 1) return;

                string swsRpGuid = "";

                string racId = this.lvRPList.Items[this.lvRPList.SelectedIndices[0]].SubItems[0].Text;
                for (int i = 0; i < swsDoc.raclist.Count; i++)
                {
                    if (swsDoc.raclist[i].rac_id.Equals(racId))
                    {
                        swsRpGuid = swsDoc.raclist[i].sws_rp_guid;
                    }
                }

                frmUpdateWorkDetail frmRefer = new frmUpdateWorkDetail(swsRpGuid);
                frmRefer.Show();
            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }
        }

        private void ClearAll()
        {
            this.txtSwsId.Text = "";
            this.txtSwsId.Enabled = true;

            this.lblWoId.Text = "";
            this.lvRPList.Items.Clear();
            this.swsDoc = new SWS_DOC();

            swsId = string.Empty;

            this.txtSwsId.Focus();
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

        private void btnDelBegin_Click(object sender, EventArgs e)
        {
            try
            {
                if (this.lvRPList.Items.Count == 0) return;
                if (this.lvRPList.SelectedIndices.Count < 1) return;

                string swsRpGuid = "";

                string racId = this.lvRPList.Items[this.lvRPList.SelectedIndices[0]].SubItems[0].Text;
                for (int i = 0; i < swsDoc.raclist.Count; i++)
                {
                    if (swsDoc.raclist[i].rac_id.Equals(racId))
                    {
                        swsRpGuid = swsDoc.raclist[i].sws_rp_guid;
                    }
                }

                if (string.IsNullOrEmpty(swsRpGuid))
                {
                    return;
                }

                if (MessageBox.Show("确认删除开工信息？", "Confirm Message", MessageBoxButtons.OKCancel, MessageBoxIcon.Question, MessageBoxDefaultButton.Button3) != DialogResult.OK)
                {
                    return;
                }

                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = swsRpGuid;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("wo/delSwsRpPDA", "POST", "application/json", RequestStr);
                BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    MessageBox.Show("删除成功！");

                    LoadRpList();

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