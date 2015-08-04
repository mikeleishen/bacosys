using System;
using System.Windows.Forms;
using System.Reflection;
using Entrance.Model;
using Entrance.Response;
using System.Collections.Generic;


namespace Entrance
{
    public partial class menu : Form
    {
        public menu()
        {
            InitializeComponent();
        }
        public static menu Own;
        public static menu GetInstance(UsrResponse data)
        {
            if (Own == null)
            {
                Own = new menu();
            }
            Own.loadMenu(data);
            Own.Show();
            return Own;
        }
        public void loadMenu(UsrResponse usrLoginData)
        {
            List<FUN_NODE> fun_node_list = usrLoginData.usrLoginDMData.funNodeListData;
            int iloop=0;
            foreach (FUN_NODE entity in fun_node_list)
            {
                entity.fun_name = ++iloop +". "+ entity.fun_name;
            }

            Own.listMenu.DisplayMember ="fun_name";
            Own.listMenu.DataSource = fun_node_list;
            Own.listMenu.SelectedIndexChanged += new EventHandler(listMenu_SelectedIndexChanged);
        }
        private void btnReturn_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("确定退出吗？", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.None, MessageBoxDefaultButton.Button1) == DialogResult.Yes)
            {
                login Login = new login();
                Login.Show();
                this.Close();
                this.menu_Closed(sender, e);
            }
            else
            {
                return;
            }
        }
        private void listMenu_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == '1')
            {
                listMenu.SelectedIndex = 0;
                //menuItem1_Click(menuItem1, null);
                btnConfirm_Click(sender, e);
            }
            else if (e.KeyChar == '2')
            {
                listMenu.SelectedIndex = 1;
                btnConfirm_Click(sender, e);
            }
            else if (e.KeyChar == '3')
            {
                listMenu.SelectedIndex = 2;
                btnConfirm_Click(sender, e);
            }
        }
        private void btnConfirm_Click(object sender, EventArgs e)
        {
            try
            {
                if (listMenu.SelectedItem != null)
                {
                    FUN_NODE NodeSelected = (FUN_NODE)listMenu.SelectedItem;
                    if (NodeSelected.fun_ass == null) return;
                    if (NodeSelected.fun_class == null) return;
                    if (NodeSelected.fun_method == null) return;


                    Assembly assembly = Assembly.Load(NodeSelected.fun_ass);
                    if (assembly == null) return;
                    Type type = assembly.GetType(NodeSelected.fun_ass + "." + NodeSelected.fun_class);
                    if (type == null) return;
                    MethodInfo method = type.GetMethod(NodeSelected.fun_method);
                    if (method == null) return;
                    string test = NodeSelected.fun_name;

                    Object[] paeametors = new Object[] { test };

                    Object obj = assembly.CreateInstance(NodeSelected.fun_ass + "." + NodeSelected.fun_class);

                    method.Invoke(obj, paeametors);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void listMenu_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                btnConfirm_Click(sender, e);
            }
        }
        //private void menuItem1_Click(object sender, EventArgs e)
        //{
        //    this.listMenu_SelectedIndexChanged(sender, e);

        //}

        /// <summary>
        /// 反射
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void listMenu_SelectedIndexChanged(object sender, EventArgs e)
        {
            //FUN_NODE NodeSelected = (FUN_NODE)this.listMenu.SelectedItem;
            //if (NodeSelected.fun_ass == null) return;
            //if (NodeSelected.fun_class == null) return;
            //if (NodeSelected.fun_method == null) return;


            //Assembly assembly = Assembly.Load(NodeSelected.fun_ass);
            //if (assembly == null) return;
            //Type type = assembly.GetType(NodeSelected.fun_ass + "." + NodeSelected.fun_class);
            //if (type == null) return;
            //MethodInfo method = type.GetMethod(NodeSelected.fun_method);
            //if (method == null) return;
            //string test = NodeSelected.fun_name;

            //Object[] paeametors = new Object[] { test };

            //Object obj = assembly.CreateInstance(NodeSelected.fun_ass + "." + NodeSelected.fun_class);

            //method.Invoke(obj, paeametors);
            //Own.Hide();
        }

        public void menu_Closed(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}