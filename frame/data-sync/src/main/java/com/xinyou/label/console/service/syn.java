package com.xinyou.label.console.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class syn {
	private static int sleep = 300000;
	private static String update_Id = "";
	private static boolean IsInsert = true;
	private static boolean IsUpdate = false;
	private static int step = 0;
	private static int sleepCount = 0;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			while (true) {
				Connection sourceConn=null, destinationConn=null;
				
				if(step>10000){step=0;}
				step++;
				if(step%2==0){
					IsUpdate = false;
				}
				else{
					IsUpdate = true;
				}
				try
				{
					sourceConn = getSourceConnection();
					destinationConn = getDestinationConnection();
					
					
					/*
					PreparedStatement ps = destinationConn.prepareStatement("SELECT SUB_WO_SUB_ID,SUB_WO_MAIN_GUID,SUB_WO_SUB_GUID FROM SUB_WO_SUB ORDER BY SUB_WO_SUB_ID DESC");
					ResultSet rs = ps.executeQuery();
					while(rs.next())
					{
						PreparedStatement ps2 = destinationConn.prepareStatement("SELECT WO_ID FROM SUB_WO_MAIN WHERE SUB_WO_MAIN_GUID=?");
						ps2.setString(1, rs.getString(2));
						ResultSet  rs2 = ps2.executeQuery();
						if(rs2.next())
						{
							PreparedStatement ps3 = destinationConn.prepareStatement("SELECT TOP 1 WO_RAC_ID FROM WO_RAC WHERE WO_DOC_ID=? ORDER BY WO_RAC_ID DESC");
							ps3.setString(1, rs2.getString(1));
							ResultSet  rs3 = ps3.executeQuery();
							
							if(rs3.next())
							{
								PreparedStatement ps4 = destinationConn.prepareStatement("SELECT ISNULL(SUM(ISNULL(FINISH_QTY,0)),0) FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=?");
								ps4.setString(1, rs.getString(3));
								ps4.setString(2, rs3.getString(1));
								ResultSet  rs4 = ps4.executeQuery();
								
								if(rs4.next()){
									PreparedStatement ps5 = destinationConn.prepareStatement("UPDATE SUB_WO_SUB SET FINISH_QTY=? WHERE SUB_WO_SUB_GUID=?");
									ps5.setBigDecimal(1, rs4.getBigDecimal(1));
									ps5.setString(2, rs.getString(3));
									ps5.execute();
									ps5.close();
								}
								rs4.close();
								ps4.close();
							}
							rs3.close();
							ps3.close();
						}
						rs2.close();
						ps2.close();
					}
					rs.close();
					ps.close();
					
					return;
					*/
					
					if(args!=null && args.length>0)
					{
						if(args[0].equals("wo")){
							return;
						}
						else{
							if(sleepCount==0)
							{
							}
							itm_syn.GetItms(sourceConn,destinationConn,true,false);
							pur_syn.SynPurs(sourceConn, destinationConn);
							rba_syn.SynRba(sourceConn, destinationConn);
							target_syn.SynTarget(sourceConn, destinationConn);
							emp_syn.SynEmp(sourceConn, destinationConn);
							wo_syn.SynWoNew(sourceConn, destinationConn);
							
							if(new Date().getHours()<3 && new Date().getHours()>0){
								PreparedStatement ps= destinationConn.prepareStatement("DELETE FROM CODE_MAIN");
								ps.execute();
							}
						}
					}
					else{
						//wo_syn.RepareTar(sourceConn, destinationConn);
						
						if(sleepCount==0)
						{
							itm_syn.GetItms(sourceConn,destinationConn,true,true);
						}

						itm_syn.GetItms(sourceConn,destinationConn,true,false);
						pur_syn.SynPurs(sourceConn, destinationConn);
						rba_syn.SynRba(sourceConn, destinationConn);
						target_syn.SynTarget(sourceConn, destinationConn);
						emp_syn.SynEmp(sourceConn, destinationConn);
						wo_syn.SynWoNew(sourceConn, destinationConn);
						if(new Date().getHours()<3 && new Date().getHours()>0){
							PreparedStatement ps= destinationConn.prepareStatement("DELETE FROM CODE_MAIN");
							ps.execute();
						}
						
					}
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
				
				System.out.println("");
				Thread.sleep(sleep);
				sleepCount++;
				if(sleepCount==24){
					sleepCount=0;
				}
			}
			
		} catch (Exception e) {
			System.out.println("出现异常："+e.getMessage());
		}
	}
	
//	public static Connection getSourceConnection() throws Exception {
//		Connection conn = null;
//		try{
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			conn = DriverManager.getConnection("jdbc:sqlserver://192.168.137.2:1433; DatabaseName=DSB", "sa", "admin2014");
//		}catch(Exception e){
//			throw e;
//		}
//		return conn;
//	}
//	
//	public static Connection getDestinationConnection() throws Exception {
//		Connection conn = null;
//		try{
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=GL", "sa", "admin2014");
//		}catch(Exception e){
//			throw e;
//		}
//		return conn;
//	}
	
	
	public static Connection getSourceConnection() throws Exception {
		Connection conn = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=koryu", "label", "sa");
		}catch(Exception e){
			throw e;
		}
		return conn;
	}

	public static Connection getDestinationConnection() throws Exception {
		Connection conn = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=LABEL-GL", "label", "sa");
		}catch(Exception e){
			throw e;
		}
		return conn;

	}
}

