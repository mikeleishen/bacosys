using System;
using System.Windows.Forms;
using System.Reflection;
using Entrance.Model;
using Entrance.Response;
using System.Collections.Generic;


namespace Entrance
{
    public partial class FunMenu : Form
    {
        public FunMenu()
        {
            InitializeComponent();
        }

        public static FunMenu Own;
        public static FunMenu GetInstance(EntityListDM Data)
        {
            if (Own == null)
            {
                Own = new FunMenu();
            }
            if (Data != null)
            {
                Own.loadMenus(Data.dataList);
            }
            Own.Show();
            return Own;
        }

        public static FunMenu GetInstanceHide(EntityListDM Data,string beginForm)
        {
            if (Own == null)
            {
                Own = new FunMenu();
            }
            if (Data != null)
            {
                Own.loadMenus(Data.dataList);
            }

            Own.SetSelectedMenuItem(beginForm);

            return Own;
        }

        public void loadMenus(List<FUN_NODE> ListMenuData)
        {
            if (ListMenuData != null || ListMenuData.Count > 0)
            {
                string temp = ListMenuData.Count + "";
                int temp_length = temp.Length;

                int iloop = 0;
                foreach (FUN_NODE entity in ListMenuData)
                {
                    iloop++;
                    string sn = iloop + "";
                    while (sn.Length < temp_length)
                    {
                        sn = "0" + sn;
                    }
                    entity.fun_name = sn + ". " + entity.fun_name;
                }

                Own.listMenu.DisplayMember = "fun_name";
                Own.listMenu.DataSource = ListMenuData;
            }
        }

        public void SetSelectedMenuItem(string beginForm)
        {
            try
            {
                for (int i = 0; i < Own.listMenu.Items.Count; i++)
                {
                    if (((FUN_NODE)Own.listMenu.Items[i]).fun_id == beginForm)
                    {
                        Own.listMenu.SelectedIndex = i;
                        break;
                    }
                }
            }
            catch
            {
            }

            Own.Show();
            btnConfirm_Click(null, null);
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

                    string fun_name = NodeSelected.fun_name;

                    Object[] paeametors = new Object[] { fun_name };

                    Object obj = assembly.CreateInstance(NodeSelected.fun_ass + "." + NodeSelected.fun_class);

                    method.Invoke(obj, paeametors);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("确定退出吗？", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.None, MessageBoxDefaultButton.Button2) == DialogResult.Yes)
            {
                Application.Exit();
            }
        }

        private void listMenu_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyValue == 13)
            {
                btnConfirm_Click(sender, e);
            }
            else if (e.KeyValue == 49)
            {
                listMenu.SelectedIndex = 0;
            }
            else if (e.KeyValue == 50)
            {
                listMenu.SelectedIndex = 1;
            }
        }
    }
}