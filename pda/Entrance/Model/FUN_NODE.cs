using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Entrance.Model

{
   public  class FUN_NODE
    {
       public string title
       {
           get;
           set;
       }
       public string key
       {
           get;
           set;

       }
       public Boolean isFolder
       {
           get;
           set;
       }
       public Boolean isLazy
       {
           get;
           set;
       }
       public string toolip
       {
           get;
           set;
       }
       public string href
       {
           get;
           set;
       }
       public string icon
       {
           get;
           set;
       }
       public string addClass
       {
           get;
           set;
       }
       public Boolean noLink
       {
           get;
           set;
       }
       public Boolean activate
       {
           get;
           set;
       }
       public Boolean focus
       {
           get;
           set;
       }
       public Boolean expand
       {
           get;
           set;
       }
       public Boolean select
       {
           get;
           set;
       }
       public Boolean hideCheckbox
       {
           get;
           set;
       }
       public Boolean unselectable
       {
           get;
           set;
       }
       public string fun_seqno
       {
           get;
           set;
       }
       public string parent_seqno
       {
           get;
           set;
       }
       public string fun_id
       {
           get;
           set;
       }
       public string fun_name
       {
           get;
           set;
       }
       public string fun_param
       {
           get;
           set;
       }
       public string fun_url
       {
           get;
           set;

       }
       public string node_img
       {
           get;
           set;

       }
       public string fun_desc
       {
           get;
           set;

       }
       public string biz_sys_guid
       {
           get;
           set;

       }
       public List<FUN_NODE> children
       {
           get;
           set;
       }
       public string fun_ass
       {
           get;
           set;
       }
       public string fun_class
       {
           get;
           set;
       }
       public string fun_method
       {
           get;
           set;
       }
    }
}
