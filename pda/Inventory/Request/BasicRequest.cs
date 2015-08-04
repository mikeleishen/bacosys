using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Inventory.Request
{
    public class BasicRequest<T,T1>
    {
        public string token
        {
            get;
            set;
        }
        public int data_int
        {
            get;
            set;
        }
        public string data_char
        {
            get;
            set;
        }
        public string data_char2
        {
            get;
            set;
        }
        public string data_char3
        {
            get;
            set;
        }
        public string data_char4
        {
            get;
            set;
        }
        public string data_char5
        {
            get;
            set;
        }

        public decimal data_decimal
        {
            get;
            set;
        }

        public T data_entity
        {
            get;
            set;
        }
        public List<T1> data_list
        {
            get;
            set;
        }
        public int page_no { get; set; }
        public int page_size { get; set; }
    }
}
