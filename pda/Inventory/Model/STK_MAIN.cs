using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class STK_MAIN
    {
        public string id { get; set; }
        public string inv_id{get;set;}
        public int stk_status { get; set; }
        public string stk_memo { get; set; }
        public long stk_p_bdt { get; set; }
        public long stk_p_edt { get; set; }
        public long stk_bdt { get; set; }
        public long stk_edt { get; set; }
        public string guid { get; set; }
        public string is_stk { get; set; }
    }
}
