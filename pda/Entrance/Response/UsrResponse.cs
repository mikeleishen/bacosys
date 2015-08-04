using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Entrance.Model;

namespace Entrance.Response

{
   public  class UsrResponse
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
       public EntityListDM dataDM
       {
           get;
           set;
       }
    }
}
