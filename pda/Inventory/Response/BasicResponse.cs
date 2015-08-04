using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Inventory.Model;

namespace Inventory.Response
{
    public class BasicResponse<T,T1>
    {
        public string status
        {
            get;
            set;
        }
        public string info
        {
            get;
            set;
        }
        public long svrdt
        {
            get;
            set;
        }
        public T dataEntity
        {
            get;
            set;
        }
        public List<T> dataList
        {
            get;
            set;
        }
        public List<T1> dataList2
        {
            get;
            set;
        }
        public EntityListDM<T, T1> dataDM
        {
            get;
            set;
        }
    }
}
