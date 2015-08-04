using System;
using System.Collections.Generic;
using System.Text;
using System.Runtime.InteropServices;
using System.Threading;
using System.Collections;

namespace Connection
{
    public class ConnectManager
    {
        const int S_OK = 0;
        const uint CONNMGR_PARAM_GUIDDESTNET = 0x1;
        const uint CONNMGR_PRIORITY_USERINTERACTIVE = 0x08000;
        const uint INFINITE = 0xffffffff;
        const uint CONNMGR_STATUS_CONNECTED = 0x10;
        const int CONNMGR_MAX_DESC = 128;    // @constdefine Max size of a network description

        const int CONNMGR_FLAG_PROXY_HTTP = 0x1; // @constdefine HTTP Proxy supported
        const int CONNMGR_FLAG_PROXY_WAP = 0x2; // @constdefine WAP Proxy (gateway) supported
        const int CONNMGR_FLAG_PROXY_SOCKS4 = 0x4; // @constdefine SOCKS4 Proxy supported
        const int CONNMGR_FLAG_PROXY_SOCKS5 = 0x8; // @constdefine SOCKS5 Proxy supported

        const UInt16 IDC_WAIT = 32514;
        const UInt16 IDC_ARROW = 32512;

        private IntPtr m_hConnection = IntPtr.Zero;

        public ConnectManager()
        {
        }

        ~ConnectManager()
        {
            ReleaseConnection();
        }

        /// <summary>
        /// 查看连接是否可用
        /// </summary>
        /// <returns></returns>
        public bool GetConnMgrAvailable()
        {
            IntPtr hConnMgr = ConnMgrApiReadyEvent();
            bool bAvailbale = false;
            uint dwResult = WaitForSingleObject ( hConnMgr, 2000 );

            if (dwResult == 0)
            {
                bAvailbale = true;
            }

            // 关闭
            if (hConnMgr.ToInt32() != 0) CloseHandle(hConnMgr);

            return bAvailbale;
        }
        /// <summary>
        /// 映射URL
        /// </summary>
        /// <param name="lpszURL"></param>
        /// <param name="guidNetworkObject"></param>
        /// <param name="pcsDesc"></param>
        /// <returns></returns>
        public int MapURLAndGUID(string lpszURL, ref GUID guidNetworkObject, ref string pcsDesc)
        {
            if (lpszURL == null || lpszURL.Length < 1)
                return 0;

            uint nIndex = 0;
            int hResult = ConnMgrMapURL(lpszURL,ref guidNetworkObject, ref nIndex);
            if (hResult < 0)
            {
                throw new Exception("Could not map a request to a network identifier");
            }
            else
            {
                if (pcsDesc != null)
                {
                    CONNMGR_DESTINATION_INFO DestInfo = new CONNMGR_DESTINATION_INFO();
                    if (ConnMgrEnumDestinations((int)nIndex, ref DestInfo) >= 0)
                    {
                        pcsDesc = DestInfo.szDescription;
                    }
                }
            }

            return (int)nIndex;
        }
        /// <summary>
        /// 枚举网络标识符信息
        /// </summary>
        /// <param name="lstNetIdentifiers"></param>
        public List<CONNMGR_DESTINATION_INFO> EnumNetIdentifier()
        {
            List<CONNMGR_DESTINATION_INFO> lstNetIdentifiers = new List<CONNMGR_DESTINATION_INFO>();
            // 得到网络列表
            for (uint dwEnumIndex = 0; ; dwEnumIndex++)
            {
                CONNMGR_DESTINATION_INFO networkDestInfo = new CONNMGR_DESTINATION_INFO();
                
                if (ConnMgrEnumDestinations((int)dwEnumIndex,ref networkDestInfo) != 0)
                {
                    break;
                }
                lstNetIdentifiers.Add(networkDestInfo);
            }

            return lstNetIdentifiers;
        }
        
        /// <summary>
        /// 建立连接
        /// </summary>
        /// <param name="nIndex"></param>
        /// <returns></returns>
        public bool EstablishConnection(uint nIndex)
        {
            ReleaseConnection();
            // 得到正确的连接信息
            CONNMGR_DESTINATION_INFO DestInfo = new CONNMGR_DESTINATION_INFO();
            int hResult = ConnMgrEnumDestinations((int)nIndex, ref DestInfo);

            if (hResult >= 0)
            {
                // 初始化连接结构
                CONNMGR_CONNECTIONINFO ConnInfo = new CONNMGR_CONNECTIONINFO();

                ConnInfo.cbSize = (uint)Marshal.SizeOf(ConnInfo);
                ConnInfo.dwParams = CONNMGR_PARAM_GUIDDESTNET;
                ConnInfo.dwFlags = CONNMGR_FLAG_PROXY_HTTP | CONNMGR_FLAG_PROXY_WAP | CONNMGR_FLAG_PROXY_SOCKS4 | CONNMGR_FLAG_PROXY_SOCKS5;
                ConnInfo.dwPriority = CONNMGR_PRIORITY_USERINTERACTIVE;
                ConnInfo.guidDestNet = DestInfo.guid;
                ConnInfo.bExclusive = 0;
                ConnInfo.bDisabled = 0;

                uint dwStatus = 0;
                hResult = ConnMgrEstablishConnectionSync(ref ConnInfo, ref m_hConnection, 10 * 1000, ref dwStatus);
                if (hResult < 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }

            return false;
        }
        /// <summary>
        /// 连接状态
        /// </summary>
        /// <param name="nTimeoutSec"></param>
        /// <param name="pdwStatus"></param>
        /// <returns></returns>
        public bool WaitForConnected(int nTimeoutSec, ref uint pdwStatus)
        {
            uint dwStartTime = GetTickCount();
            bool bRet = false;

            while (GetTickCount() - dwStartTime < (uint)nTimeoutSec * 1000)
            {
                if (m_hConnection.ToInt32() != 0)
                {
                    uint dwStatus = 0;
                    int hr = ConnMgrConnectionStatus(m_hConnection, ref dwStatus);
                    if (dwStatus != 0) pdwStatus = dwStatus;
                    if (hr >= 0)
                    {
                        if (dwStatus == CONNMGR_STATUS_CONNECTED)
                        {
                            bRet = true;
                            break;
                        }
                    }
                }
                Thread.Sleep(100);
            }

            return bRet;
        }

        /// <summary>
        /// 释放所有连接
        /// </summary>
        public void ReleaseConnection()
        {

            if (m_hConnection.ToInt32() != 0)
            {
                ConnMgrReleaseConnection(m_hConnection, 0);
                m_hConnection = IntPtr.Zero;
            }
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct CONNMGR_CONNECTIONINFO
        {
            public uint cbSize;
            public uint dwParams;
            public uint dwFlags;
            public uint dwPriority;
            public int bExclusive;
            public int bDisabled;
            public GUID guidDestNet;
            public IntPtr hWnd;
            public uint uMsg;
            public uint lParam;
            public uint ulMaxCost;
            public uint ulMinRcvBw;
            public uint ulMaxConnLatency;
        }

        [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
        public struct CONNMGR_DESTINATION_INFO
        {
            public GUID guid;  // @field GUID associated with network
            [MarshalAs(UnmanagedType.ByValTStr,SizeConst = CONNMGR_MAX_DESC)]
            public string szDescription;  // @field Description of network
            public int fSecure; // @field Is it OK to allow multi-homing on the network
        }

        public struct GUID
        {          // size is 16
            public uint Data1;
            public UInt16 Data2;
            public UInt16 Data3;
            [MarshalAs(UnmanagedType.ByValArray, SizeConst = 8)]
            public byte[] Data4;
        }

        [DllImport("coredll.dll")]
        public static extern uint GetTickCount();

        [DllImport("coredll.dll")]
        public static extern uint WaitForSingleObject(IntPtr hHandle,uint dwMilliseconds);

        [DllImport("cellcore.dll")]
        public static extern int ConnMgrMapURL(string pwszURL, ref GUID pguid, ref uint pdwIndex);

        [DllImport("cellcore.dll")]
        public static extern int ConnMgrEstablishConnectionSync(ref CONNMGR_CONNECTIONINFO ci, ref IntPtr phConnection, uint dwTimeout, ref uint pdwStatus);

        [DllImport("cellcore.dll")]
        private static extern IntPtr ConnMgrApiReadyEvent();

        [DllImport("cellcore.dll")]
        public static extern int ConnMgrReleaseConnection(IntPtr hConnection, int bCache);

        [DllImport("cellcore.dll")]
        public static extern int ConnMgrEnumDestinations(int nIndex,ref CONNMGR_DESTINATION_INFO pDestInfo);

        [DllImport("cellcore.dll")]
        public static extern int ConnMgrConnectionStatus(IntPtr hConnection,    // @parm Handle to connection, returned from ConnMgrEstablishConnection
            ref uint pdwStatus       // @parm Returns current connection status, one of CONNMGR_STATUS_*
            );

        [DllImport("coredll.dll")]
        private static extern int CloseHandle(IntPtr hObject);
    }
}
