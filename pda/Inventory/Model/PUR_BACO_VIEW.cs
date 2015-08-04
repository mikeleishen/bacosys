using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class PUR_BACO_VIEW
    {
        public string pur_id { get; set; }
        public string pur_seqno { get; set; }
        public string itm_id { get; set; }
        public decimal itm_qty { get; set; }
    }
}
