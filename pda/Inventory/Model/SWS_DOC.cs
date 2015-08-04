using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class SWS_DOC
    {
        public SUB_WO_SUB_VIEW swsdoc { get; set; }
        public List<SWS_RAC_VIEW> raclist { get; set; }
        public List<EMP_MAIN_VIEW> emplist { get; set; }
    }
}
