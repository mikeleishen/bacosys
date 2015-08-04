using System;
using System.Windows.Forms;
using Newtonsoft.Json;
using Entrance.Model;
using Entrance.Response;
using Common;

namespace Entrance
{
    public partial class Login : Form
    {
        private static int LoginType = 0;
        private static string StartFormId = "";
        public static EntityListDM LoginData;

        public Login()
        {
            InitializeComponent();
        }

        public static Login Own;
        public static Login GetInstance()
        {
            if (Own == null)
            {
                Own = new Login();
            }
            HttpWebRequestProxy.token = null;
            Own.Show();
            return Own;
        }

        private void Login_KeyDown(object sender, KeyEventArgs e)
        {
            if ((e.KeyCode == System.Windows.Forms.Keys.Enter))
            {
                btnLogin_Click(sender, e);
            }

        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            try
            {
                lblTip.Text = " ";

                string username = txtUserName.Text;
                if (string.IsNullOrEmpty(username))
                {
                    lblTip.Text = "用户名不能空";
                    txtUserName.Text = "";
                    txtUserName.Focus();
                    return;

                }

                string passwd = txtPasswd.Text;
                if (string.IsNullOrEmpty(passwd))
                {
                    lblTip.Text = "密码不能空";
                    txtPasswd.Text = "";
                    txtPasswd.Focus();
                    return;
                }

                Cursor.Current = Cursors.WaitCursor;

                USR_MAIN_VIEW usr = new USR_MAIN_VIEW();
                usr.usr_name = username.Trim();
                usr.usr_pswd = passwd.Trim();
                usr.sys_id = MobileConfiguration.GetSetting("SysID");

                string RequestStr = JsonConvert.SerializeObject(usr);
                string ResponseStr = HttpWebRequestProxy.PostRest("Usr/signIn_pda", "POST", "application/json", RequestStr);
                UsrResponse Data = JsonConvert.DeserializeObject<UsrResponse>(ResponseStr);

                if (Data.status == "0")
                {
                    LoginData = Data.dataDM;
                    if (LoginData != null)
                    {
                        HttpWebRequestProxy.token = LoginData.dataEntity.token;
                        HttpWebRequestProxy.username = LoginData.dataEntity.usr_name;
                        HttpWebRequestProxy.userroleid = LoginData.dataEntity.role_id;

                        if (LoginType == 0)
                        {
                            FunMenu.GetInstance(LoginData);
                        }
                        else if (LoginType == 1)
                        {
                            FunMenu.GetInstanceHide(LoginData, StartFormId);
                        }

                        this.Hide();
                    }
                }
                else if (Data.status == "1")
                {
                    lblTip.Text = Data.info;
                }
                else if (Data.status == "2")
                {
                    lblTip.Text = Data.info;
                }
            }
            catch (Exception ex)
            {
                lblTip.Text = ex.Message;
                txtPasswd.Focus();
            }
            txtPasswd.Text = "";
            Cursor.Current = Cursors.Default;
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        public static void ReLogin(string message)
        {
            if (MessageBox.Show("服务超时请重新登录", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.None, MessageBoxDefaultButton.Button2) == DialogResult.Yes)
            {
                Login.GetInstance();
            }
        }

        private void btnBWork_Click(object sender, EventArgs e)
        {
            LoginType = 1;
            StartFormId = "ST001";
            this.txtUserName.Text = "999901";
            this.txtPasswd.Text = "123123123";
            this.btnLogin_Click(this.btnLogin, null);
         
        }


        private void btnRpWork_Click(object sender, EventArgs e)
        {
            LoginType = 1;
            StartFormId = "ST002";
            this.txtUserName.Text = "999901";
            this.txtPasswd.Text = "123123123";
            this.btnLogin_Click(this.btnLogin, null);
        }

        private void txtUserName_KeyDown(object sender, KeyEventArgs e)
        {
            LoginType = 0;
            StartFormId = "";
        }
    }
}
