using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class CERT_SWS_DOC
    {
        public CERT_DOC_VIEW certDoc
        {
            get;
            set;
        }
        public List<CERT_SWS_RE_VIEW> swsList
        {
            get;
            set;
        }
    }
}
