using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class CERT_SWS_RE_VIEW
    {
        public String cert_doc_guid
        {
            get;
            set;
        }
        public String cert_doc_id
        {
            get;
            set;
        }
        public String sws_guid
        {
            get;
            set;
        }
        public String sws_id
        {
            get;
            set;
        }
        public decimal sws_qty
        {
            get;
            set;
        }
    }
}
