package com.xinyou.label.console.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class rba_syn {
	private static SimpleDateFormat LDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void SynRba(Connection sourceConn,Connection destinationConn) throws SQLException
	{
		System.out.println(LDF.format(new Date())+" : "+"begin SYN rba_DOC");
		
		PreparedStatement psUpAll = destinationConn.prepareStatement("UPDATE RBA_DOC SET RBA_STATUS=1");
		psUpAll.execute();
		
		//1、获取所有未审核的领料单
		PreparedStatement psSource = sourceConn.prepareStatement("SELECT RBA002,RBA006,RBA004,RBA009,RBA005 FROM SGMRBA WHERE RBA001=82 AND RBA012='F' ORDER BY RBA002");
		ResultSet sourceRs = psSource.executeQuery();
		while(sourceRs.next())
		{
			//2、判断领料单是否已经存在
			PreparedStatement psDesRbaDoc = destinationConn.prepareStatement("SELECT RBA_DOC_ID FROM RBA_DOC WHERE RBA_DOC_ID='"+sourceRs.getString(1)+"'");
			ResultSet rsDesRbaDoc = psDesRbaDoc.executeQuery();
			//如果已经有记录，表体先删后插
			if(rsDesRbaDoc.next())
			{
				PreparedStatement psUpDesRbaDoc = destinationConn.prepareStatement("UPDATE RBA_DOC SET RBA_DOC_REMARK=?,RBA_DOC_DT=?,RBA_STATUS=0,ERP_EMP_ID=? WHERE RBA_DOC_ID=?");
				psUpDesRbaDoc.setString(1, sourceRs.getString(4));
				psUpDesRbaDoc.setString(2, sourceRs.getString(3));
				psUpDesRbaDoc.setString(3, sourceRs.getString(5));
				psUpDesRbaDoc.setString(4, sourceRs.getString(1));
				psUpDesRbaDoc.execute();
				psUpDesRbaDoc.close();
				psUpDesRbaDoc = null;
				
				PreparedStatement psDelDesRbaDocItms = destinationConn.prepareStatement("DELETE FROM RBA_ITM WHERE RBA_DOC_ID=?");
				psDelDesRbaDocItms.setString(1, rsDesRbaDoc.getString(1));
				psDelDesRbaDocItms.execute();
				psDelDesRbaDocItms.close();
				psDelDesRbaDocItms = null;
				
				PreparedStatement psRbaDocItms = sourceConn.prepareStatement("SELECT T1.RBB003,T1.RBB002,T1.RBB004,T1.RBB007,T1.RBB009 FROM SGMRBA T2,SGMRBB T1 WHERE T2.RBA001='82' AND T2.RBA002=? AND T1.RBB001='82' AND T1.RBB002=T2.RBA002");
				psRbaDocItms.setString(1,rsDesRbaDoc.getString(1));
				ResultSet rsRbaDocItms = psRbaDocItms.executeQuery();
				while(rsRbaDocItms.next())
				{
					PreparedStatement psInsDesRbaDocItm = destinationConn.prepareStatement("INSERT RBA_ITM(RBA_ITM_GUID,RBA_ITM_SEQNO,RBA_DOC_ID,ITM_ID,ITM_QTY,INV_ID) VALUES(?,?,?,?,?,?)");
					psInsDesRbaDocItm.setString(1, UUID.randomUUID().toString());
					psInsDesRbaDocItm.setString(2, rsRbaDocItms.getString(1));
					psInsDesRbaDocItm.setString(3, rsRbaDocItms.getString(2));
					psInsDesRbaDocItm.setString(4, rsRbaDocItms.getString(3));
					psInsDesRbaDocItm.setBigDecimal(5, rsRbaDocItms.getBigDecimal(5));
					psInsDesRbaDocItm.setString(6, rsRbaDocItms.getString(4));
					psInsDesRbaDocItm.execute();
					psInsDesRbaDocItm.close();
					psInsDesRbaDocItm = null;
				}
				rsRbaDocItms.close();
				rsRbaDocItms = null;
				psRbaDocItms.close();
				psRbaDocItms = null;
			}
			else
			{
				PreparedStatement psInsDesRbaDoc = destinationConn.prepareStatement("INSERT RBA_DOC(RBA_DOC_GUID,RBA_DOC_ID,WO_DOC_ID,LABEL_STATUS,RBA_STATUS,RBA_DOC_DT,RBA_DOC_REMARK,ERP_EMP_ID) VALUES(?,?,?,0,0,?,?,?)");
				psInsDesRbaDoc.setString(1, UUID.randomUUID().toString());
				psInsDesRbaDoc.setString(2, sourceRs.getString(1));
				psInsDesRbaDoc.setString(3, sourceRs.getString(2));
				psInsDesRbaDoc.setString(4, sourceRs.getString(3));
				psInsDesRbaDoc.setString(5, sourceRs.getString(4));
				psInsDesRbaDoc.setString(6, sourceRs.getString(5));
				psInsDesRbaDoc.execute();
				psInsDesRbaDoc.close();
				psInsDesRbaDoc = null;
				
				PreparedStatement psRbaDocItms = sourceConn.prepareStatement("SELECT T1.RBB003,T1.RBB002,T1.RBB004,T1.RBB007,T1.RBB009 FROM SGMRBA T2,SGMRBB T1 WHERE T2.RBA001='82' AND T2.RBA002=? AND T1.RBB001='82' AND T1.RBB002=T2.RBA002");
				psRbaDocItms.setString(1,sourceRs.getString(1));
				ResultSet rsRbaDocItms = psRbaDocItms.executeQuery();
				while(rsRbaDocItms.next())
				{
					PreparedStatement psInsDesRbaDocItm = destinationConn.prepareStatement("INSERT RBA_ITM(RBA_ITM_GUID,RBA_ITM_SEQNO,RBA_DOC_ID,ITM_ID,ITM_QTY,INV_ID) VALUES(?,?,?,?,?,?)");
					psInsDesRbaDocItm.setString(1, UUID.randomUUID().toString());
					psInsDesRbaDocItm.setString(2, rsRbaDocItms.getString(1));
					psInsDesRbaDocItm.setString(3, rsRbaDocItms.getString(2));
					psInsDesRbaDocItm.setString(4, rsRbaDocItms.getString(3));
					psInsDesRbaDocItm.setBigDecimal(5, rsRbaDocItms.getBigDecimal(5));
					psInsDesRbaDocItm.setString(6, rsRbaDocItms.getString(4));
					psInsDesRbaDocItm.execute();
					psInsDesRbaDocItm.close();
					psInsDesRbaDocItm = null;
				}
				rsRbaDocItms.close();
				rsRbaDocItms = null;
				psRbaDocItms.close();
			}
			
			rsDesRbaDoc.close();
			rsDesRbaDoc=null;
			psDesRbaDoc.close();
			psDesRbaDoc=null;
		}

		sourceRs.close();
		sourceRs = null;
		psSource.close();
		psSource=null;

		System.out.println(LDF.format(new Date())+" : "+"end SYN rba_DOC");
	}
}
