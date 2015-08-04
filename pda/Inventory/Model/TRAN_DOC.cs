using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class TRAN_DOC
    {
        public TRAN_MAIN head { get; set; }
        public List<TRAN_ITM> body_itm { get; set; }
        public List<TRAN_BACO> body_baco { get; set; }
        public List<TRAN_ITM_BASE> body_itm_base { get; set; }
        public List<TRAN_BASE_DOC> doc_base { get; set; }
        public List<RBA_CTN_RE> rba_ctn_re { get; set; }

        public TRAN_DOC()
        {
            head = new TRAN_MAIN();
            body_itm = new List<TRAN_ITM>();
            body_baco = new List<TRAN_BACO>();
            body_itm_base = new List<TRAN_ITM_BASE>();
            doc_base = new List<TRAN_BASE_DOC>();
            rba_ctn_re = new List<RBA_CTN_RE>();
        }
    }
}
