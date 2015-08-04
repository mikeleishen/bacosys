using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class SWS_RAC_VIEW
    {
        public string rac_seqno { get; set; }
        public string rac_id { get; set; }
        public string rac_name { get; set; }
        public decimal rac_pkg_qty { get; set; }
        public decimal finish_qty { get; set; }
        public decimal scrap_qty { get; set; }
        public int status { get; set; }
        public string main_worker { get; set; }
        public long rp_date { get; set; }
        public long bg_date { get; set; }
        public string sws_rp_guid { get; set; }
        public string rac_tech_id { get; set; }
        public int rac_emp_num { get; set; }
        public decimal rp_qty { get; set; }
        public string rp_ws { get; set; }
        public string rp_ws_no { get; set; }
        public int tar_emp_num { get; set; }
    }
}
