using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class TRAN_ITM_BASE
    {
        public string itm_id { get; set; }
        public decimal itm_qty { get; set; }
        public string tran_guid { get; set; }
        public string base_seqno { get; set; }
    }
}
