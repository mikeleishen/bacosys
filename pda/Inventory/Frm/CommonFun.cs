using Inventory.Model;
using System.Collections.Generic;
using Inventory.Request;
using Common;
using Inventory.Response;
using Newtonsoft.Json;

namespace Inventory.Frm
{
    public class CommonFun
    {
        public static List<ITM_MAIN_VIEW> GetItemListByBacoList(List<CTN_MAIN_VIEW> bacoList)
        {
            bool hasSame = false;
            ITM_MAIN_VIEW tempView = null;
            List<ITM_MAIN_VIEW> result = new List<ITM_MAIN_VIEW>();

            if (bacoList == null || bacoList.Count == 0)
            {
                return result;
            }

            for (int i = 0; i < bacoList.Count; i++)
            {
                hasSame = false;

                BasicRequest<ITM_MAIN_VIEW, ITM_MAIN_VIEW> Request = new BasicRequest<ITM_MAIN_VIEW, ITM_MAIN_VIEW>() { token = HttpWebRequestProxy.token, data_char = bacoList[i].ctn_baco };
                BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW> Data = JsonConvert.DeserializeObject<BasicResponse<ITM_MAIN_VIEW, ITM_MAIN_VIEW>>(
                    HttpWebRequestProxy.PostRest("inv/getctnitms", "POST", "application/json", JsonConvert.SerializeObject(Request)));

                if ((Data.dataList == null && bacoList[i].ctn_type == 12) || bacoList[i].ctn_type == 10)
                {
                    hasSame = false;

                    for (int j = 0; j < result.Count; j++)
                    {
                        if (bacoList[i].itm_id == result[j].itm_main_id)
                        {
                            hasSame = true;
                            result[j].itm_qty = result[j].itm_qty + bacoList[i].itm_qty;
                            break;
                        }
                    }

                    if (!hasSame)
                    {
                        tempView = new ITM_MAIN_VIEW();
                        tempView.itm_main_id = bacoList[i].itm_id;
                        tempView.itm_name = bacoList[i].itm_name;
                        tempView.itm_qty = bacoList[i].itm_qty;
                        tempView.itm_spec = bacoList[i].itm_spec;
                        tempView.itm_unit = bacoList[i].itm_unit;
                        result.Add(tempView);
                    }

                    continue;
                }

                for (int n = 0; n < Data.dataList.Count; n++)
                {
                    hasSame = false;

                    for (int j = 0; j < result.Count; j++)
                    {
                        if (Data.dataList[n].itm_main_id == result[j].itm_main_id)
                        {
                            hasSame = true;
                            result[j].itm_qty = result[j].itm_qty + Data.dataList[n].itm_qty;
                            break;
                        }
                    }

                    if (!hasSame)
                    {
                        tempView = new ITM_MAIN_VIEW();
                        tempView.itm_main_id = Data.dataList[n].itm_main_id;
                        tempView.itm_name = Data.dataList[n].itm_name;
                        tempView.itm_qty = Data.dataList[n].itm_qty;
                        tempView.itm_spec = Data.dataList[n].itm_spec;
                        tempView.itm_unit = Data.dataList[n].itm_unit;
                        result.Add(tempView);
                    }
                }
            }

            return result;
        }
    }
}