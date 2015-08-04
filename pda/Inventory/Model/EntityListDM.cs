using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Model
{
    public class EntityListDM<T,T1>
    {
        public T dataEntity
        {
            get;
            set;
        }
        public List<T1> dataList
        {
            get;
            set;
        }
    }
}
