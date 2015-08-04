using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class CERT_DOC_VIEW
    {
        public string guid
        {
            get;
            set;
        }
        public string id
        {
            get;
            set;
        }
        
        public string itm_id
        {
            get;
            set;
        }
        public string itm_name
        {
            get;
            set;
        }
        public decimal itm_qty
        {
            get;
            set;
        }
        public string cert_year
        {
            get;
            set;
        }
        public int cert_status
        {
            get;
            set;
        }
        public decimal pack_qty
        {
            get;
            set;
        }
        public decimal total_qty
        {
            get;
            set;
        }
    }
}
