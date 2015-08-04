using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class RBA_CTN_RE_VIEW
    {
        public string rba_ctn_re_guid { get; set; }
        public string rba_ctn_re_id { get; set; }
        public string rba_doc_id { get; set; }
        public string rba_itm_seqno { get; set; }
        public string ctn_baco { get; set; }
        public string lot_id { get; set; }

        public string itm_id { get; set; }
        public string wo_doc_id { get; set; }
        public long created_dt { get; set; }
        public string itm_name { get; set; }
        public decimal itm_qty { get; set; }
        public string m_qc_doc { get; set; }
        public string sp_name { get; set; }
    }
}

