using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class SWS_RP
    {
        public decimal finish_qty { get; set; }
        public decimal scrap_qty { get; set; }
        public long bg_dt { get; set; }
        public long rp_dt { get; set; }
        public string sws_guid { get; set; }
        public string rp_rac_id { get; set; }
        public string rp_rac_name { get; set; }
        public int rp_status { get; set; }
        public string rp_ws { get; set; }
        public decimal rp_tar_value { get; set; }
        public string rp_ws_no { get; set; }
        public string itm_id { get; set; }
        public string guid { get; set; }
        public string sws_id { get; set; }
        public string b_pda_id { get; set; }
        public string e_pda_id { get; set; }
    }
}
