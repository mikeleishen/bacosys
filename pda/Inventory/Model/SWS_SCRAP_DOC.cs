using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{

    public class SWS_SCRAP_DOC
    {
        public string sws_guid { get; set; }
        public string rp_guid { get; set; }
        public string sws_id { get; set; }
        public string itm_id { get; set; }
        public decimal sws_qty { get; set; }
        public long bg_dt { get; set; }
        public string rac_id { get; set; }
        public string rac_name { get; set; }
        public string ws_id { get; set; }
        public string ws_no { get; set; }
        public decimal tar_qty { get; set; }
        public decimal rp_qty { get; set; }
        public decimal scraped_qty { get; set; }
        public int scrap_on_rac { get; set; }
        public string wo_id { get; set; }
        public List<RAC_SCRAP_VIEW> scrap_list { get; set; }
        public List<RAC_VIEW> rac_list { get; set; }
    }
}