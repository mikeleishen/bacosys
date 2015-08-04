using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    class PUR_ITM_VIEW
    {
        public string pur_itm_guid
        {
            get;
            set;
        }
        public string pur_doc_id
        {
            get;
            set;
        }
        public string pur_itm_seqno
        {
            get;
            set;
        }
        public string itm_main_id
        {
            get;
            set;
        }
        public string itm_name
        {
            get;
            set;
        }
        public string itm_unit
        {
            get;
            set;
        }
        public decimal itm_qty
        {
            get;
            set;
        }
        public decimal itm_delivery_qty
        {
            get;
            set;
        }
        public decimal itm_got_qty
        {
            get;
            set;
        }
    }
}
