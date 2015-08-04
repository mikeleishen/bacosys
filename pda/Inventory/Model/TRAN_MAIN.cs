using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class TRAN_MAIN
    {
        public int tran_type { get; set; }
        public string tran_reason_id { get; set; }
        public int base_doc_type { get; set; }
        public string base_doc_id { get; set; }
        public int need_syn { get; set; }
        public int is_syned { get; set; }
        public int syn_doc_type { get; set; }
        public string syn_doc_id { get; set; }
        public string wh_id { get; set; }
        public int in_out { get; set; }
    }
}
