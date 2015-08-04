using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
	public class SWS_SCRAP_VIEW
	{
        public string sws_guid { get; set; }
        public string rac_id { get; set; }
        public string rp_guid { get; set; }
        public int scrap_type { get; set; }
        public string scrap_type_name { get; set; }
        public string scrap_reason_id { get; set; }
        public string scrap_reason_name { get; set; }
        public string scrap_content_id { get; set; }
        public string scrap_content_name { get; set; }
        public string rac_name { get; set; }
        public decimal scrap_qty { get; set; }
        public string scrap_part { get; set; }
        public string emp_id { get; set; }
        public string inspector { get; set; }
        public string happen_rac_seqno { get; set; }
        public string happen_rac_name { get; set; }
        public int scrap_on_rac { get; set; }
        public string guid { get; set; }
        public string sws_id { get; set; }
        public string erp_doc_id { get; set; }
        public long scrap_dt { get; set; }
        public string wo_id { get; set; }
        public string itm_id { get; set; }
        public string lot_id { get; set; }
        public string rac_seqno { get; set; }
	}
}
