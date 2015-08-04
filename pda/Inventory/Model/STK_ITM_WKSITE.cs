using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class STK_ITM_WKSITE
    {
        public string stk_itm_wksite_guid { get; set; }
        public string stk_itm_wksite_id { get; set; }
        public string stk_main_guid { get; set; }
        public string stk_main_id { get; set; }
        public string stk_emp_id { get; set; }
        public string sws_guid { get; set; }
        public string sws_id { get; set; }
        public string wo_doc_id { get; set; }
        public string itm_id { get; set; }
        public string stk_rac_id { get; set; }
        public string stk_rac_name { get; set; }
        public decimal stk_value { get; set; }
    }
}
    