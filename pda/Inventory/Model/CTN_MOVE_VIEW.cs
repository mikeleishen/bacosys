using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class CTN_MOVE_VIEW
    {
        public string from_ctn_main_guid
        {
            get;
            set;
        }
        public string from_ctn_main_id
        {
            get;
            set;
        }
        public string from_ctn_baco
        {
            get;
            set;
        }
        public int from_ctn_type
        {
            get;
            set;
        }
        public string to_ctn_main_guid
        {
            get;
            set;
        }
        public string to_ctn_main_id
        {
            get;
            set;
        }
        public string to_ctn_baco
        {
            get;
            set;
        }
        public int to_ctn_type
        {
            get;
            set;
        }
        public string itm_id
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
    }
}
