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
    public partial class frmCodeBind : Form
    {
        private CODE_MAIN editCode = new CODE_MAIN();
        private List<string> infoList = new List<string>();

        public frmCodeBind()
        {
            InitializeComponent();
        }

        public static frmCodeBind GetInstance(string title)
        {
            frmCodeBind Own = new frmCodeBind();
            Own.Text = title;

            Own.lvInfo.View = View.Details;
            Own.lvInfo.Activation = ItemActivation.Standard;
            Own.lvInfo.FullRowSelect = true;
            Own.lvInfo.CheckBoxes = false;
            Own.lvInfo.Columns.Add("绑定信息", 300, HorizontalAlignment.Left);

            ContextMenu lvMenu = new ContextMenu();
            MenuItem lvMenuItem = new MenuItem();
            lvMenuItem.Text = "删除";
            lvMenuItem.Click += new EventHandler(Own.lvMenuItem_Click);
            lvMenu.MenuItems.Add(lvMenuItem);
            Own.lvInfo.ContextMenu = lvMenu;

            Own.Show();
            return Own;
        }

        private void lvMenuItem_Click(object sender, EventArgs e)
        {
            string action = (sender as MenuItem).Text;
            if (action == "删除")
            {
                if (lvInfo.SelectedIndices.Count < 1)
                {
                    return;
                }

                string infoValue = lvInfo.Items[lvInfo.SelectedIndices[0]].SubItems[0].Text;
                for (int i = 0; i < infoList.Count; i++)
                {
                    if (infoList[i] == infoValue)
                    {
                        infoList.RemoveAt(i);
                        break;
                    }
                }

                infoListBind();
                clear();
            }
        }

        private void txtBindBaco_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode != Keys.Enter)
            {
                return;
            }

            if (this.txtBindBaco.Text.Trim().Length == 0)
            {
                this.txtBindBaco.Text = "";
                this.txtBindBaco.Focus();
                return;
            }


            if (this.txtBindBaco.Text.Trim().Length < 2)
            {
                MessageBox.Show("条码格式不正确！");
                this.txtBindBaco.Text = "";
                this.txtBindBaco.Focus();
                return;
            }

            if (this.txtBindBaco.Text.Trim().Substring(0,2)!="SP")
            {
                MessageBox.Show("条码格式不正确！");
                this.txtBindBaco.Text = "";
                this.txtBindBaco.Focus();
                return;
            }

            try
            {
                BasicRequest<string, string> Request = new BasicRequest<string, string>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_char = this.txtBindBaco.Text.Trim();

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("SpecialCode/GetCodeMain", "POST", "application/json", RequestStr);
                BasicResponse<CODE_MAIN, CODE_MAIN> Data = JsonConvert.DeserializeObject<BasicResponse<CODE_MAIN, CODE_MAIN>>(ResponseStr);

                if (Data.status == "0")
                {
                    CODE_MAIN co = Data.dataEntity;
                    if (!String.IsNullOrEmpty(co.code_main_id))
                    {
                        infoList = co.code_value.Split(';').ToList<string>();
                    }
                    else
                    {
                        infoList = new List<string>();
                    }
                    this.txtBindBaco.Tag = co;
                    this.infoListBind();
                    this.txtBindInfo.Focus();
                }
                else if (Data.status == "1")
                {
                    MessageBox.Show(Data.info);
                    this.txtBindBaco.Text = "";
                    this.txtBindBaco.Focus();
                    this.txtBindBaco.Tag = null;
                }
                else if (Data.status == "2")
                {
                    Login.ReLogin(Data.info);
                    this.txtBindBaco.Text = "";
                    this.txtBindBaco.Focus();
                    this.txtBindBaco.Tag = null;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void txtBindInfo_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.KeyCode != Keys.Enter)
            {
                return;
            }

            try
            {
                if (this.txtBindInfo.Text.Trim().Length > 0)
                {
                    this.infoList.Add(this.txtBindInfo.Text.Trim());
                    this.infoListBind();
                    this.txtBindInfo.Text = "";
                    this.txtBindInfo.Focus();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void miSave_Click(object sender, EventArgs e)
        {
            if (this.txtBindBaco.Text.Trim().Length == 0)
            {
                this.txtBindBaco.Text = "";
                this.txtBindBaco.Focus();
                return;
            }

            try
            {
                this.editCode.code_main_id = this.txtBindBaco.Text.Trim();
                this.editCode.code_value = "";
                for (int i = 0; i < this.infoList.Count; i++)
                {
                    if (i == this.infoList.Count - 1)
                    {
                        this.editCode.code_value = this.editCode.code_value + this.infoList[i];
                        continue;
                    }
                    this.editCode.code_value = this.editCode.code_value + this.infoList[i] + ";";
                }


                BasicRequest<CODE_MAIN, CODE_MAIN> Request = new BasicRequest<CODE_MAIN, CODE_MAIN>();
                Request.token = HttpWebRequestProxy.token;
                Request.data_entity = this.editCode;

                string RequestStr = JsonConvert.SerializeObject(Request);
                string ResponseStr = HttpWebRequestProxy.PostRest("SpecialCode/SaveCode", "POST", "application/json", RequestStr);
                BasicResponse<string, string> Data = JsonConvert.DeserializeObject<BasicResponse<string, string>>(ResponseStr);

                if (Data.status == "0")
                {
                    MessageBox.Show("保存成功！");
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

        public void infoListBind()
        {
            lvInfo.Items.Clear();
            if (infoList == null) return;
            string[] cols = new string[1];

            for (int i = 0; i < infoList.Count; i++)
            {
                cols[0] = infoList[i];
                ListViewItem lvItem = new ListViewItem(cols);
                lvInfo.Items.Add(lvItem);
            }
        }

        public void clear()
        {
            this.txtBindInfo.Text = "";
            this.txtBindInfo.Focus();
        }

        public void clearAll()
        {
            clear();
            this.txtBindBaco.Text = "";
            this.txtBindBaco.Tag = null;
            this.lvInfo.Items.Clear();
            this.infoList = new List<string>();
            this.editCode = new CODE_MAIN();
            this.txtBindBaco.Focus();
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            this.clearAll();
        }
    }
}