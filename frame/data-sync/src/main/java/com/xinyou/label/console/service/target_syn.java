package com.xinyou.label.console.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class target_syn {
	private static SimpleDateFormat LDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void SynTarget( Connection sourceConn,Connection destinationConn ) throws SQLException
	{
		System.out.println(LDF.format(new Date())+":"+" target_syn begin.");
		
		PreparedStatement psGetSourceRp = destinationConn.prepareStatement("SELECT T2.WO_ID,T.RP_RAC_ID,ISNULL(T.RP_WS,''),SWS_RP_GUID FROM SWS_RP T,SUB_WO_SUB T1,SUB_WO_MAIN T2 WHERE ISNULL(IS_SYN_TARGET,0)=0 AND T.SWS_GUID=T1.SUB_WO_SUB_GUID AND T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID ");
		ResultSet rsSourceRp = psGetSourceRp.executeQuery();
		while(rsSourceRp.next()){
//			PreparedStatement psGetSource = sourceConn.prepareStatement("SELECT QBZ005 FROM SGMQBZ WHERE QBZ001=? AND QBZ002=? AND QBZ003=? ");
			PreparedStatement psGetSource = destinationConn.prepareStatement("SELECT TAR_VALUE FROM WO_RAC_TAR WHERE WO_DOC_ID=? AND WO_RAC_ID=? AND WS_ID=? ");
			psGetSource.setString(1, rsSourceRp.getString(1));
			psGetSource.setString(2, rsSourceRp.getString(2));
			psGetSource.setString(3, rsSourceRp.getString(3));
			
			ResultSet rsSource = psGetSource.executeQuery();
			if(rsSource.next())
			{
				PreparedStatement psUpdateD = destinationConn.prepareStatement("UPDATE SWS_RP SET WORK_TARGET=? WHERE SWS_RP_GUID=?");
				psUpdateD.setBigDecimal(1, rsSource.getBigDecimal(1));
				psUpdateD.setString(2, rsSourceRp.getString(4));
				psUpdateD.execute();
				psUpdateD.close();
			}
			
			PreparedStatement psUpdateD = destinationConn.prepareStatement("UPDATE SWS_RP SET IS_SYN_TARGET=1 WHERE SWS_RP_GUID=?");
			psUpdateD.setString(1, rsSourceRp.getString(4));
			psUpdateD.execute();
			psUpdateD.close();
			
			rsSource.close();
			psGetSource.close();
		}
		
		rsSourceRp.close();
		psGetSourceRp.close();
		
		System.out.println(LDF.format(new Date())+":"+" target_syn end.");
	}
}

