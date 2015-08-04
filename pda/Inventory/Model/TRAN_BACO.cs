using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class TRAN_BACO
    {
        public string ctn_baco { get; set; }
        public decimal tran_qty { get; set; }

        public string itm_id { get; set; }
        public string parent_baco { get; set; }
        public string lot_id { get; set; }

        public string wh_id { get; set; }
        public string f_wh_id { get; set; }

        public int base_type { get; set; }
        public string base_id { get; set; }
        public string base_seqno { get; set; }
    }
}
