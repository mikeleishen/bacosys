using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class ITM_MAIN_VIEW
    {
        public string itm_main_id
        {
            get;
            set;
        }
        public string itm_name
        {
            get;
            set;
        }
        public string itm_spec
        {
            get;
            set;
        }
        public string itm_unit
        {
            get;
            set;
        }
        public decimal itm_qty
        {
            get;
            set;
        }
        public decimal itm_got_qty
        {
            get;
            set;
        }
    }
}
