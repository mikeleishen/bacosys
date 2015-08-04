using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class CTN_MAIN_VIEW
    {
        public string ctn_main_guid
        {
            get;
            set;
        }
        public string ctn_main_id
        {
            get;
            set;
        }
        public int is_deleted
        {
            get;
            set;
        }
        public string ctn_baco
        {
            get;
            set;
        }
        public string parent_ctn_baco
        {
            get;
            set;
        }
        public int ctn_type
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
        public string wh_guid
        {
            get;
            set;
        }
        public string wh_id
        {
            get;
            set;
        }
        public string wh_name
        {
            get;
            set;
        }
        public string wh_area_baco
        {
            get;
            set;
        }
        public string wh_area_id
        {
            get;
            set;
        }
        public string wh_loc_baco
        {
            get;
            set;
        }
        public string wh_loc_id
        {
            get;
            set;
        }
        public string wh_package_baco
        {
            get;
            set;
        }
        public string wh_package_id
        {
            get;
            set;
        }
        public string lot_id { get; set; }

        public string f_wh_id { get; set; }

        public int base_type { get; set; }
        public string base_id { get; set; }
        public string base_seqno { get; set; }
        public string def_loc_id { get; set; }
        public string stk_guid { get; set; }
        public string updated_by { get; set; }
    }
}
