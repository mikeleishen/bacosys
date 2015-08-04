using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class RAC_SCRAP_VIEW
    {
        public long scrap_dt { get; set; }
        public string emp_id { get; set; }
        public string inspector_id { get; set; }
        public decimal scrap_qty { get; set; }
        public string scrap_reason { get; set; }
    }
}
