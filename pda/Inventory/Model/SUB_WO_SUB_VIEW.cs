using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class SUB_WO_SUB_VIEW
    {
        public string guid { get; set; }
        public string id { get; set; }
        public string parent_guid { get; set; }
        public string ctn_guid { get; set; }
        public int sub_seqno { get; set; }
        public decimal finish_qty { get; set; }
        public decimal scrap_qty { get; set; }
        public decimal sws_qty { get; set; }
        public string itm_id { get; set; }
        public int sws_status { get; set; }
        public int rp_status { get; set; }
        public string lot_id { get; set; }
        public string wo_id { get; set; }
        public string stock_area { get; set; }
        public string rp_ws { get; set; }
        public string rp_ws_no { get; set; }
        public string cut_seqno { get; set; }
    }
}
