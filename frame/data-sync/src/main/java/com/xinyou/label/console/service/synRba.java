package com.xinyou.label.console.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class synRba {
	private static int sleep = 1000;
	public static void main(String[] args) {
		try {
			while (true) {
				Connection sourceConn=null, destinationConn=null;
				
				try
				{
					sourceConn = getSourceConnection();
					destinationConn = getDestinationConnection();
					
					PreparedStatement ps = destinationConn.prepareStatement("SELECT IS_FINISHED FROM TASK_MAIN WHERE TASK_MAIN_ID='SYN_RAC'");
					ResultSet rs = ps.executeQuery();
					if(rs.next())
					{
						if(rs.getInt(1)==0){
							PreparedStatement psUp = destinationConn.prepareStatement("UPDATE TASK_MAIN SET BG_DT=? WHERE TASK_MAIN_ID='SYN_RAC'");
							psUp.setLong(1, new Date().getTime());
							psUp.execute();
							psUp.close();
							
							wo_syn.SynWo2(sourceConn, destinationConn);
							
							 psUp = destinationConn.prepareStatement("UPDATE TASK_MAIN SET ED_DT=?,IS_FINISHED=1 WHERE TASK_MAIN_ID='SYN_RAC'");
							psUp.setLong(1, new Date().getTime());
							psUp.execute();
							psUp.close();
						}
					}
					rs.close();
					ps.close();
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
					
					if(sourceConn!=null)
					{
						try {
							if(!sourceConn.isClosed())
							{
								sourceConn.close();
							}
						} catch (SQLException e) {
						}
						sourceConn=null;
					}
					
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
				finally{
					if(sourceConn!=null)
					{
						try {
							if(!sourceConn.isClosed())
							{
								sourceConn.close();
							}
						} catch (SQLException e) {
						}
						sourceConn=null;
					}
					
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
				
				Thread.sleep(sleep);
			}
			
		} catch (Exception e) {
			System.out.println("出现异常："+e.getMessage());
		}
	}
	
	
	
	public static Connection getSourceConnection() throws Exception {
		Connection conn = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=KORYU", "sa", "admin2014");
		}catch(Exception e){
			throw e;
		}
		return conn;
	}
	
	public static Connection getDestinationConnection() throws Exception {
		Connection conn = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=LABEL-GL", "sa", "admin2014");
		}catch(Exception e){
			throw e;
		}
		return conn;
	}
}
