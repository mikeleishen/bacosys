package com.xinyou.label.console.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class repairData {

	public static void main(String[] args) {
		try {
			while (true) {
				Connection destinationConn=null;
				
				try
				{
					//sourceConn = getSourceConnection();
					destinationConn = getDestinationConnection();
					
					PreparedStatement ps = destinationConn.prepareStatement("select SWS_RP_GUID from SWS_RP where RP_DT>0 AND EMP_ID_LIST IS NULL order by CREATED_DT desc");
					ResultSet rs = ps.executeQuery();
					while(rs.next())
					{
						PreparedStatement getStaffs = destinationConn.prepareStatement("SELECT T2.EMP_MAIN_ID FROM SWS_STAFF T1,EMP_MAIN T2 WHERE T1.EMP_GUID=T2.EMP_MAIN_GUID AND T1.SWS_RP_GUID=?");
						getStaffs.setString(1, rs.getString(1));
						ResultSet rsStaffids = getStaffs.executeQuery();
						StringBuilder sbStaffids = new StringBuilder();
						while(rsStaffids.next())						{
							sbStaffids.append(rsStaffids.getString(1));
							sbStaffids.append("/");
						}
						
						rsStaffids.close();
						getStaffs.close();
						
						if(sbStaffids.length()>0){
							PreparedStatement psUp = destinationConn.prepareStatement("UPDATE SWS_RP SET EMP_ID_LIST=? WHERE SWS_RP_GUID=?");
							psUp.setString(1,sbStaffids.substring(0, sbStaffids.length()-1));
							psUp.setString(2, rs.getString(1));
							psUp.execute();
							psUp.close();
						}
					}
					rs.close();
					ps.close();
					
					break;
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
					
					if(destinationConn!=null)
					{
						try {
							if(!destinationConn.isClosed())
							{
								destinationConn.close();
							}
						} catch (SQLException e) {
						}
						destinationConn=null;
					}
					
					break;
				}
				finally{
					
					if(destinationConn!=null)
					{
						try {
							if(!destinationConn.isClosed())
							{
								destinationConn.close();
							}
						} catch (SQLException e) {
						}
						destinationConn=null;
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("出现异常："+e.getMessage());
		}
	}
	
	
	
	public static Connection getSourceConnection() throws Exception {
		Connection conn = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://192.168.0.2:1433; DatabaseName=KORYU", "label", "sa");
		}catch(Exception e){
			throw e;
		}
		return conn;
	}
	
	public static Connection getDestinationConnection() throws Exception {
		Connection conn = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=LABEL-GL", "sa", "koryu");
		}catch(Exception e){
			throw e;
		}
		return conn;
	}
}
