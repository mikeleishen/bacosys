package com.xinyou.label.console.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class pur_syn {

	private static SimpleDateFormat LDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void SynPurs(Connection sourceConn,Connection destinationConn) throws SQLException
	{
		System.out.println(LDF.format(new Date())+" : "+"begin SYN PUR_DOC");
		
		//1、将所有数据更新为未被同步
		PreparedStatement psUpDestination = destinationConn.prepareStatement("UPDATE PUR_DOC SET SYN_STATUS=0");
		psUpDestination.execute();
		psUpDestination.close();
		psUpDestination=null;

		PreparedStatement psSource = sourceConn.prepareStatement("SELECT DISTINCT HDB001 FROM DCSHDB WHERE HDB012='N'");
		ResultSet sourceRs = psSource.executeQuery();
		while(sourceRs.next())
		{
			//HDA001   单号  ,HDA004   供应商  
			PreparedStatement psPurDoc = sourceConn.prepareStatement("SELECT HDA001,HDA004 FROM DCSHDA WHERE HDA001='"+sourceRs.getString(1)+"'");
			ResultSet rsPurDoc = psPurDoc.executeQuery();
			
			if(rsPurDoc.next())
			{
				PreparedStatement psDesPurDoc = destinationConn.prepareStatement("SELECT PUR_DOC_ID FROM PUR_DOC WHERE PUR_DOC_ID='"+rsPurDoc.getString(1)+"'");
				ResultSet rsDesPurDoc = psDesPurDoc.executeQuery();
				//如果已经有记录，先删除，再插入
				if(rsDesPurDoc.next())
				{
					PreparedStatement psDelDesPurDoc = destinationConn.prepareStatement("DELETE FROM PUR_DOC WHERE PUR_DOC_ID='"+rsPurDoc.getString(1)+"'");
					psDelDesPurDoc.execute();
					psDelDesPurDoc.close();
					psDelDesPurDoc = null;
					
					PreparedStatement psDelDesPurDocItms = destinationConn.prepareStatement("DELETE FROM PUR_ITM WHERE PUR_DOC_ID='"+rsPurDoc.getString(1)+"'");
					psDelDesPurDocItms.execute();
					psDelDesPurDocItms.close();
					psDelDesPurDocItms = null;
					
				}

				PreparedStatement psInsDesPurDoc = destinationConn.prepareStatement("INSERT PUR_DOC(PUR_DOC_GUID,PUR_DOC_ID,SUP_NO,SYN_STATUS) VALUES(?,?,?,1)");
				psInsDesPurDoc.setString(1, UUID.randomUUID().toString());
				psInsDesPurDoc.setString(2, rsPurDoc.getString(1));
				psInsDesPurDoc.setString(3, rsPurDoc.getString(2));
				psInsDesPurDoc.execute();
				psInsDesPurDoc.close();
				psInsDesPurDoc = null;
				
				PreparedStatement psPurDocItms = sourceConn.prepareStatement("SELECT HDB002,HDB003,HDB006,HDB018,HDB010 FROM DCSHDB WHERE HDB001='"+rsPurDoc.getString(1)+"' AND HDB012='N'");
				ResultSet rsPurDocItms = psPurDocItms.executeQuery();
				while(rsPurDocItms.next())
				{
					PreparedStatement psInsDesPurDocItm = destinationConn.prepareStatement("INSERT PUR_ITM(PUR_ITM_GUID,PUR_DOC_ID,PUR_ITM_SEQNO,ITM_MAIN_ID,ITM_QTY,ITM_DELIVERY_QTY,FCT_RECIEVE_DT) VALUES(?,?,?,?,?,?,?)");
					psInsDesPurDocItm.setString(1, UUID.randomUUID().toString());
					psInsDesPurDocItm.setString(2, rsPurDoc.getString(1));
					psInsDesPurDocItm.setString(3, rsPurDocItms.getString(1));
					psInsDesPurDocItm.setString(4, rsPurDocItms.getString(2));
					psInsDesPurDocItm.setBigDecimal(5, rsPurDocItms.getBigDecimal(3));
					psInsDesPurDocItm.setBigDecimal(6, rsPurDocItms.getBigDecimal(4));
					psInsDesPurDocItm.setString(7, rsPurDocItms.getString(5));
					psInsDesPurDocItm.execute();
					psInsDesPurDocItm.close();
					psInsDesPurDocItm = null;
				}
				
				rsPurDocItms.close();
				rsPurDocItms = null;
				psPurDocItms.close();
				psPurDocItms = null;
				
				rsDesPurDoc.close();
				rsDesPurDoc=null;
				psDesPurDoc.close();
				psDesPurDoc = null;
			}
			
			rsPurDoc.close();
			rsPurDoc = null;
			psPurDoc.close();
			psPurDoc = null;
		}
		
		sourceRs.close();
		sourceRs = null;
		psSource.close();
		psSource=null;

		PreparedStatement psDelDestination = destinationConn.prepareStatement("DELETE FROM PUR_ITM WHERE PUR_DOC_ID IN(SELECT PUR_DOC_ID FROM PUR_DOC WHERE SYN_STATUS=0)");
		psDelDestination.execute();
		psDelDestination.close();
		psDelDestination=null;
		psDelDestination = destinationConn.prepareStatement("DELETE FROM PUR_DOC WHERE SYN_STATUS=0");
		psDelDestination.execute();
		psDelDestination.close();
		psDelDestination=null;

		System.out.println(LDF.format(new Date())+" : "+"end SYN PUR_DOC");
	}
}
