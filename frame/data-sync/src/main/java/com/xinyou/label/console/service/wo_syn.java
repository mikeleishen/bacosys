package com.xinyou.label.console.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class wo_syn {
	
	private static SimpleDateFormat LDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void SynWo( Connection sourceConn,Connection destinationConn ) throws SQLException
	{
		System.out.println(LDF.format(new Date())+":"+" wo_syn begin.");
		
		//0、标记所有工单结束
		//1、获取未结束工单列表
		//2、循环，如果已经存在，则更新，并且更新同步标记为1；如果不存在，则新增
		//3、循环同时，更新工单工艺信息
		//RAA001-工单单号 , RAA015-主件品号 , RAA018-生产数量 ,  RAA019-已生产量   ,  RAA039-报废数量 
		PreparedStatement psSetDesWo = destinationConn.prepareStatement("UPDATE WO_DOC SET WO_STATUS=0 WHERE WO_STATUS=1");
		psSetDesWo.execute();
		psSetDesWo.close();
		psSetDesWo=null;
		
		PreparedStatement psGetSourceWo = sourceConn.prepareStatement("SELECT RAA001,RAA015,RAA018,RAA019,RAA039 FROM SGMRAA WHERE RAA020='N' AND RAA024='T' ORDER BY RAA001 DESC");
		ResultSet rsSourceWo = psGetSourceWo.executeQuery();
		while(rsSourceWo.next())
		{
			PreparedStatement psGetDesWo = destinationConn.prepareStatement("SELECT WO_DOC_ID FROM WO_DOC WHERE WO_DOC_ID=?");
			psGetDesWo.setString(1, rsSourceWo.getString(1));
			ResultSet rsDesWo = psGetDesWo.executeQuery();
			if(rsDesWo.next())
			{
				PreparedStatement psUpDesWo = destinationConn.prepareStatement("UPDATE  WO_DOC SET WO_ITM_ID=?,WO_QTY=?,WO_FINISH_QTY=?,WO_SCRAP_QTY=?,WO_STATUS=? WHERE WO_DOC_ID=?");
				psUpDesWo.setString(1, rsSourceWo.getString(2));
				psUpDesWo.setBigDecimal(2, rsSourceWo.getBigDecimal(3));
				psUpDesWo.setBigDecimal(3, rsSourceWo.getBigDecimal(4));
				psUpDesWo.setBigDecimal(4, rsSourceWo.getBigDecimal(5));
				psUpDesWo.setInt(5, 1);
				psUpDesWo.setString(6, rsSourceWo.getString(1));
				psUpDesWo.execute();
				psUpDesWo.close();
				psUpDesWo = null;
				
				PreparedStatement psHadWoRac=destinationConn.prepareStatement("SELECT COUNT(*) FROM WO_RAC WHERE WO_DOC_ID=?");
				psHadWoRac.setString(1, rsSourceWo.getString(1));
				ResultSet rsHadWoRac = psHadWoRac.executeQuery();
				if(rsHadWoRac.next())
				{
					if(rsHadWoRac.getInt(1)==0)
					{					
						PreparedStatement psGetSourceRac = sourceConn.prepareStatement("SELECT T1.RAC002,T1.RAC003,'',T3.QBC981,ISNULL(T4.QBA961,''),ISNULL(T3.QBC980,0),T3.QBC007,T3.QBC962,T4.QBA002,T3.QBC961 FROM SGMRAC T1 JOIN SGMRAA T2 ON T1.RAC001=T2.RAA001 JOIN SGMQBC T3 ON T3.QBC001=T2.RAA015 AND T1.RAC002=T3.QBC002 JOIN SGMQBA T4 ON T1.RAC003=T4.QBA001 WHERE T1.RAC001=? ORDER BY T1.RAC002");
						psGetSourceRac.setString(1, rsSourceWo.getString(1));
						ResultSet rsSourceRac = psGetSourceRac.executeQuery();
								
						while(rsSourceRac.next())
						{
							PreparedStatement psIsDesRac = destinationConn.prepareStatement("INSERT  WO_RAC(WO_RAC_GUID,WO_RAC_ID,WO_DOC_ID,RAC_ID,RAC_NAME,RAC_PKG_QTY,RAC_TECH_ID,RAC_TARGET) VALUES(?,?,?,?,?,?,?,?)");
							psIsDesRac.setString(1, UUID.randomUUID().toString());
							psIsDesRac.setString(2, rsSourceRac.getString(1));//WO_RAC_ID
							psIsDesRac.setString(3, rsSourceWo.getString(1));//WO_DOC_ID
							psIsDesRac.setString(4, rsSourceRac.getString(2));//RAC_ID
							if(rsSourceRac.getString(7).contains("小")){
								if(rsSourceRac.getString(8).length()>0){
									if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
										psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
									}
									else{
										psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
									}
								}
								else{
									if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
										psIsDesRac.setString(5, rsSourceRac.getString(9)+"(小)");//RAC_NAME
									}
									else{
										psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"(小)");//RAC_NAME
									}
								}
							}
							else{
								if(rsSourceRac.getString(8).length()>0){
									if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
										psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
									}
									else{
										psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
									}
								}
								else{
									if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
										psIsDesRac.setString(5, rsSourceRac.getString(9));//RAC_NAME
									}
									else{
										psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10));//RAC_NAME
									}
								}
							}
							psIsDesRac.setInt(6, rsSourceRac.getInt(4));//RAC_PKG_QTY
							psIsDesRac.setString(7, rsSourceRac.getString(5));//RAC_TECH_ID
							psIsDesRac.setBigDecimal(8, rsSourceRac.getBigDecimal(6));//RAC_TARGET
							
							psIsDesRac.execute();
							psIsDesRac.close();
							psIsDesRac = null;
						}
						
						rsSourceRac.close();
						rsSourceRac=null;
						psGetSourceRac.close();
						psGetSourceRac=null;			
					}
				}
				rsHadWoRac.close();
				rsHadWoRac = null;
				psHadWoRac.close();
				psHadWoRac=null;
			}
			else
			{
				String woGuid = UUID.randomUUID().toString();
				
				PreparedStatement psIsDesWo = destinationConn.prepareStatement("INSERT  WO_DOC(WO_DOC_GUID,WO_DOC_ID,WO_ITM_ID,WO_QTY,WO_FINISH_QTY,WO_SCRAP_QTY,WO_STATUS,WO_BIND_QTY) VALUES(?,?,?,?,?,?,?,0)");
				psIsDesWo.setString(1, woGuid);
				psIsDesWo.setString(2, rsSourceWo.getString(1));
				psIsDesWo.setString(3, rsSourceWo.getString(2));
				psIsDesWo.setBigDecimal(4, rsSourceWo.getBigDecimal(3));
				psIsDesWo.setBigDecimal(5, rsSourceWo.getBigDecimal(4));
				psIsDesWo.setBigDecimal(6, rsSourceWo.getBigDecimal(5));
				psIsDesWo.setInt(7, 1);
				psIsDesWo.execute();
				psIsDesWo.close();
				psIsDesWo = null;
				
				PreparedStatement psGetSourceRac = sourceConn.prepareStatement("SELECT T1.RAC002,T1.RAC003,'',T3.QBC981,ISNULL(T4.QBA961,''),ISNULL(T3.QBC980,0),T3.QBC007,T3.QBC962,T4.QBA002,T3.QBC961 FROM SGMRAC T1 JOIN SGMRAA T2 ON T1.RAC001=T2.RAA001 JOIN SGMQBC T3 ON T3.QBC001=T2.RAA015 AND T1.RAC002=T3.QBC002 JOIN SGMQBA T4 ON T1.RAC003=T4.QBA001 WHERE T1.RAC001=? ORDER BY T1.RAC002");
				psGetSourceRac.setString(1, rsSourceWo.getString(1));
				ResultSet rsSourceRac = psGetSourceRac.executeQuery();
				
				while(rsSourceRac.next())
				{
					PreparedStatement psIsDesRac = destinationConn.prepareStatement("INSERT  WO_RAC(WO_RAC_GUID,WO_RAC_ID,WO_DOC_ID,RAC_ID,RAC_NAME,RAC_PKG_QTY,RAC_TECH_ID,RAC_TARGET) VALUES(?,?,?,?,?,?,?,?)");
					psIsDesRac.setString(1, UUID.randomUUID().toString());
					psIsDesRac.setString(2, rsSourceRac.getString(1));//WO_RAC_ID
					psIsDesRac.setString(3, rsSourceWo.getString(1));//WO_DOC_ID
					psIsDesRac.setString(4, rsSourceRac.getString(2));//RAC_ID
					if(rsSourceRac.getString(7).contains("小")){
						if(rsSourceRac.getString(8).length()>0){
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
							}
						}
						else{
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"(小)");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"(小)");//RAC_NAME
							}
						}
					}
					else{
						if(rsSourceRac.getString(8).length()>0){
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
							}
						}
						else{
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9));//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10));//RAC_NAME
							}
						}
					}
					psIsDesRac.setInt(6, rsSourceRac.getInt(4));//RAC_PKG_QTY
					psIsDesRac.setString(7, rsSourceRac.getString(5));//RAC_TECH_ID
					psIsDesRac.setBigDecimal(8, rsSourceRac.getBigDecimal(6));//RAC_TARGET
					
					psIsDesRac.execute();
					psIsDesRac.close();
					psIsDesRac = null;
				}
				rsSourceRac.close();
				rsSourceRac=null;
				psGetSourceRac.close();
				psGetSourceRac=null;	
			}
			
			rsDesWo.close();
			rsDesWo=null;
			psGetDesWo.close();
			psGetDesWo = null;
		}
		
		rsSourceWo.close();
		rsSourceWo = null;
		psGetSourceWo.close();
		psGetSourceWo = null;
		
		System.out.println(LDF.format(new Date())+":"+" wo_syn end.");
	}
	
	public static void SynWo2( Connection sourceConn,Connection destinationConn ) throws SQLException
	{
		System.out.println(LDF.format(new Date())+":"+" wo_syn2 begin.");
		
		//0、标记所有工单结束
		//1、获取未结束工单列表
		//2、循环，如果已经存在，则更新，并且更新同步标记为1；如果不存在，则新增
		//3、循环同时，更新工单工艺信息
		//RAA001-工单单号 , RAA015-主件品号 , RAA018-生产数量 ,  RAA019-已生产量   ,  RAA039-报废数量 
		PreparedStatement psSetDesWo = destinationConn.prepareStatement("UPDATE WO_DOC SET WO_STATUS=0 WHERE WO_STATUS=1");
		psSetDesWo.execute();
		psSetDesWo.close();
		psSetDesWo=null;
		
		PreparedStatement psGetSourceWo = sourceConn.prepareStatement("SELECT RAA001,RAA015,RAA018,RAA019,RAA039 FROM SGMRAA WHERE RAA020='N' AND RAA024='T' ORDER BY RAA001 DESC");
		ResultSet rsSourceWo = psGetSourceWo.executeQuery();
		while(rsSourceWo.next())
		{
			PreparedStatement psGetDesWo = destinationConn.prepareStatement("SELECT WO_DOC_ID FROM WO_DOC WHERE WO_DOC_ID=?");
			psGetDesWo.setString(1, rsSourceWo.getString(1));
			ResultSet rsDesWo = psGetDesWo.executeQuery();
			if(rsDesWo.next())
			{
				PreparedStatement psUpDesWo = destinationConn.prepareStatement("UPDATE  WO_DOC SET WO_ITM_ID=?,WO_QTY=?,WO_FINISH_QTY=?,WO_SCRAP_QTY=?,WO_STATUS=? WHERE WO_DOC_ID=?");
				psUpDesWo.setString(1, rsSourceWo.getString(2));
				psUpDesWo.setBigDecimal(2, rsSourceWo.getBigDecimal(3));
				psUpDesWo.setBigDecimal(3, rsSourceWo.getBigDecimal(4));
				psUpDesWo.setBigDecimal(4, rsSourceWo.getBigDecimal(5));
				psUpDesWo.setInt(5, 1);
				psUpDesWo.setString(6, rsSourceWo.getString(1));
				psUpDesWo.execute();
				psUpDesWo.close();
				psUpDesWo = null;
				
				PreparedStatement psDelWoRac=destinationConn.prepareStatement("DELETE FROM WO_RAC WHERE WO_DOC_ID=?");
				psDelWoRac.setString(1, rsSourceWo.getString(1));
				psDelWoRac.execute();
				psDelWoRac.close();

				PreparedStatement psGetSourceRac = sourceConn.prepareStatement("SELECT T1.RAC002,T1.RAC003,'',T3.QBC981,ISNULL(T4.QBA961,''),ISNULL(T3.QBC980,0),T3.QBC007,T3.QBC962,T4.QBA002,T3.QBC961 FROM SGMRAC T1 JOIN SGMRAA T2 ON T1.RAC001=T2.RAA001 JOIN SGMQBC T3 ON T3.QBC001=T2.RAA015 AND T1.RAC002=T3.QBC002 JOIN SGMQBA T4 ON T1.RAC003=T4.QBA001 WHERE T1.RAC001=? ORDER BY T1.RAC002");
				psGetSourceRac.setString(1, rsSourceWo.getString(1));
				ResultSet rsSourceRac = psGetSourceRac.executeQuery();
						
				while(rsSourceRac.next())
				{
					PreparedStatement psIsDesRac = destinationConn.prepareStatement("INSERT  WO_RAC(WO_RAC_GUID,WO_RAC_ID,WO_DOC_ID,RAC_ID,RAC_NAME,RAC_PKG_QTY,RAC_TECH_ID,RAC_TARGET) VALUES(?,?,?,?,?,?,?,?)");
					psIsDesRac.setString(1, UUID.randomUUID().toString());
					psIsDesRac.setString(2, rsSourceRac.getString(1));//WO_RAC_ID
					psIsDesRac.setString(3, rsSourceWo.getString(1));//WO_DOC_ID
					psIsDesRac.setString(4, rsSourceRac.getString(2));//RAC_ID
					if(rsSourceRac.getString(7).contains("小")){
						if(rsSourceRac.getString(8).length()>0){
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
							}
						}
						else{
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"(小)");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"(小)");//RAC_NAME
							}
						}
					}
					else{
						if(rsSourceRac.getString(8).length()>0){
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
							}
						}
						else{
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9));//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10));//RAC_NAME
							}
						}
					}
					psIsDesRac.setInt(6, rsSourceRac.getInt(4));//RAC_PKG_QTY
					psIsDesRac.setString(7, rsSourceRac.getString(5));//RAC_TECH_ID
					psIsDesRac.setBigDecimal(8, rsSourceRac.getBigDecimal(6));//RAC_TARGET
					
					psIsDesRac.execute();
					psIsDesRac.close();
					psIsDesRac = null;
				}
						
				rsSourceRac.close();
				rsSourceRac=null;
				psGetSourceRac.close();
				psGetSourceRac=null;			
			}
			else
			{
				String woGuid = UUID.randomUUID().toString();
				
				PreparedStatement psIsDesWo = destinationConn.prepareStatement("INSERT  WO_DOC(WO_DOC_GUID,WO_DOC_ID,WO_ITM_ID,WO_QTY,WO_FINISH_QTY,WO_SCRAP_QTY,WO_STATUS,WO_BIND_QTY) VALUES(?,?,?,?,?,?,?,0)");
				psIsDesWo.setString(1, woGuid);
				psIsDesWo.setString(2, rsSourceWo.getString(1));
				psIsDesWo.setString(3, rsSourceWo.getString(2));
				psIsDesWo.setBigDecimal(4, rsSourceWo.getBigDecimal(3));
				psIsDesWo.setBigDecimal(5, rsSourceWo.getBigDecimal(4));
				psIsDesWo.setBigDecimal(6, rsSourceWo.getBigDecimal(5));
				psIsDesWo.setInt(7, 1);
				psIsDesWo.execute();
				psIsDesWo.close();
				psIsDesWo = null;
				
				PreparedStatement psGetSourceRac = sourceConn.prepareStatement("SELECT T1.RAC002,T1.RAC003,'',T3.QBC981,ISNULL(T4.QBA961,''),ISNULL(T3.QBC980,0),T3.QBC007,T3.QBC962,T4.QBA002,T3.QBC961 FROM SGMRAC T1 JOIN SGMRAA T2 ON T1.RAC001=T2.RAA001 JOIN SGMQBC T3 ON T3.QBC001=T2.RAA015 AND T1.RAC002=T3.QBC002 JOIN SGMQBA T4 ON T1.RAC003=T4.QBA001 WHERE T1.RAC001=? ORDER BY T1.RAC002");
				psGetSourceRac.setString(1, rsSourceWo.getString(1));
				ResultSet rsSourceRac = psGetSourceRac.executeQuery();
				
				while(rsSourceRac.next())
				{
					PreparedStatement psIsDesRac = destinationConn.prepareStatement("INSERT  WO_RAC(WO_RAC_GUID,WO_RAC_ID,WO_DOC_ID,RAC_ID,RAC_NAME,RAC_PKG_QTY,RAC_TECH_ID,RAC_TARGET) VALUES(?,?,?,?,?,?,?,?)");
					psIsDesRac.setString(1, UUID.randomUUID().toString());
					psIsDesRac.setString(2, rsSourceRac.getString(1));//WO_RAC_ID
					psIsDesRac.setString(3, rsSourceWo.getString(1));//WO_DOC_ID
					psIsDesRac.setString(4, rsSourceRac.getString(2));//RAC_ID
					if(rsSourceRac.getString(7).contains("小")){
						if(rsSourceRac.getString(8).length()>0){
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
							}
						}
						else{
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"(小)");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"(小)");//RAC_NAME
							}
						}
					}
					else{
						if(rsSourceRac.getString(8).length()>0){
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
							}
						}
						else{
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9));//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10));//RAC_NAME
							}
						}
					}
					psIsDesRac.setInt(6, rsSourceRac.getInt(4));//RAC_PKG_QTY
					psIsDesRac.setString(7, rsSourceRac.getString(5));//RAC_TECH_ID
					psIsDesRac.setBigDecimal(8, rsSourceRac.getBigDecimal(6));//RAC_TARGET
					
					psIsDesRac.execute();
					psIsDesRac.close();
					psIsDesRac = null;
				}
				rsSourceRac.close();
				rsSourceRac=null;
				psGetSourceRac.close();
				psGetSourceRac=null;	
			}
			
			rsDesWo.close();
			rsDesWo=null;
			psGetDesWo.close();
			psGetDesWo = null;
		}
		
		rsSourceWo.close();
		rsSourceWo = null;
		psGetSourceWo.close();
		psGetSourceWo = null;
		
		System.out.println(LDF.format(new Date())+":"+" wo_syn2 end.");
	}
	
	public static void SynWoNew( Connection sourceConn,Connection destinationConn ) throws SQLException
	{
		System.out.println(LDF.format(new Date())+":"+" wo_syn_new begin.");
		
		//0、获取审核通过没有完工的工单
		//1、判断该工单在条码系统中是否存在
		//2、如果不存在，则同步条码工单，同步信息包括工单、工艺、工艺路线、指标
		//如果发生变更：1、删除所有转移单、工单信息。2、删除条码系统所有信息，包括工单
		
		//RAA001-工单单号 , RAA015-主件品号 , RAA018-生产数量 ,  RAA019-已生产量   ,  RAA039-报废数量 
		//工单未完工（RAA020='N' ）且已经审核通过（RAA024='T'）
		PreparedStatement psGetSourceWo = sourceConn.prepareStatement("SELECT RAA001,RAA015,RAA018,RAA019,RAA039 FROM SGMRAA WHERE RAA020='N' AND RAA024='T' ORDER BY RAA001 DESC");
		ResultSet rsSourceWo = psGetSourceWo.executeQuery();
		while(rsSourceWo.next())
		{
			PreparedStatement psGetDesWo = destinationConn.prepareStatement("SELECT WO_DOC_ID FROM WO_DOC WHERE WO_DOC_ID=?");
			psGetDesWo.setString(1, rsSourceWo.getString(1));
			ResultSet rsDesWo = psGetDesWo.executeQuery();
			if(rsDesWo.next())			{
				continue;
			}
			else
			{
				String woGuid = UUID.randomUUID().toString();
				
				PreparedStatement psIsDesWo = destinationConn.prepareStatement("INSERT  WO_DOC(WO_DOC_GUID,WO_DOC_ID,WO_ITM_ID,WO_QTY,WO_FINISH_QTY,WO_SCRAP_QTY,WO_STATUS,WO_BIND_QTY) VALUES(?,?,?,?,?,?,?,0)");
				psIsDesWo.setString(1, woGuid);
				psIsDesWo.setString(2, rsSourceWo.getString(1));//工单号
				psIsDesWo.setString(3, rsSourceWo.getString(2));//主件品号
				psIsDesWo.setBigDecimal(4, rsSourceWo.getBigDecimal(3));//生产数量
				psIsDesWo.setBigDecimal(5, rsSourceWo.getBigDecimal(4));//已生产数量
				psIsDesWo.setBigDecimal(6, rsSourceWo.getBigDecimal(5));//报废数量
				psIsDesWo.setInt(7, 1);//工单状态
				psIsDesWo.execute();
				psIsDesWo.close();
				psIsDesWo = null;
				
				//获取工艺信息
				PreparedStatement psGetSourceRac = sourceConn.prepareStatement("SELECT T1.RAC002,T1.RAC003,'',T3.QBC981,ISNULL(T4.QBA961,''),ISNULL(T3.QBC980,0),T3.QBC007,T3.QBC962,T4.QBA002,T3.QBC961,ISNULL(T4.QBA980,0),ISNULL(T3.QBC013,'') FROM SGMRAC T1 JOIN SGMRAA T2 ON T1.RAC001=T2.RAA001 JOIN SGMQBC T3 ON T3.QBC001=T2.RAA015 AND T1.RAC002=T3.QBC002 JOIN SGMQBA T4 ON T1.RAC003=T4.QBA001 WHERE T1.RAC001=? ORDER BY T1.RAC002");
				psGetSourceRac.setString(1, rsSourceWo.getString(1));
				ResultSet rsSourceRac = psGetSourceRac.executeQuery();

				while(rsSourceRac.next())
				{
					PreparedStatement psGetRacTar = sourceConn.prepareStatement("SELECT QBZ003,QBZ005,ISNULL(QBA980,0) FROM SGMQBZ LEFT JOIN SGMQBA ON QBA001=QBZ003 WHERE QBZ001=? AND QBZ002=? ORDER BY QBZ005 ");
					psGetRacTar.setString(1, rsSourceWo.getString(2));
					psGetRacTar.setString(2, rsSourceRac.getString(1));
					ResultSet rsGetRacTar = psGetRacTar.executeQuery();
					BigDecimal smallestTar = BigDecimal.ZERO;
					while(rsGetRacTar.next()){
						PreparedStatement psInsertWs = destinationConn.prepareStatement("INSERT WO_RAC_TAR(WO_RAC_TAR_GUID,WO_RAC_ID,WO_DOC_ID,WS_ID,TAR_VALUE,TAR_EMP_NUM)  VALUES(?,?,?,?,?,?)");
						psInsertWs.setString(1, UUID.randomUUID().toString());
						psInsertWs.setString(2, rsSourceRac.getString(1));
						psInsertWs.setString(3, rsSourceWo.getString(1));
						psInsertWs.setString(4, rsGetRacTar.getString(1));
						psInsertWs.setBigDecimal(5, rsGetRacTar.getBigDecimal(2));
						psInsertWs.setInt(6, rsGetRacTar.getInt(3));
						psInsertWs.execute();
						psInsertWs.close();
						
						if(smallestTar.compareTo(BigDecimal.ZERO)<=0){
							smallestTar = rsGetRacTar.getBigDecimal(2);
						}
					}
					rsGetRacTar.close();
					psGetRacTar.close();
					

					PreparedStatement psIsDesRac = destinationConn.prepareStatement("INSERT  WO_RAC(WO_RAC_GUID,WO_RAC_ID,WO_DOC_ID,RAC_ID,RAC_NAME,RAC_PKG_QTY,RAC_TECH_ID,RAC_TARGET,RAC_EMP_NUM,RAC_DESC) VALUES(?,?,?,?,?,?,?,?,?,?)");
					psIsDesRac.setString(1, UUID.randomUUID().toString());
					psIsDesRac.setString(2, rsSourceRac.getString(1));//WO_RAC_ID,工序
					psIsDesRac.setString(3, rsSourceWo.getString(1));//WO_DOC_ID
					psIsDesRac.setString(4, rsSourceRac.getString(2));//RAC_ID，工艺
					if(rsSourceRac.getString(7).contains("小")){
						if(rsSourceRac.getString(8).length()>0){
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")(小)");//RAC_NAME
							}
						}
						else{
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"(小)");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"(小)");//RAC_NAME
							}
						}
					}
					else{
						if(rsSourceRac.getString(8).length()>0){
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10)+"("+rsSourceRac.getString(8)+")");//RAC_NAME
							}
						}
						else{
							if(rsSourceRac.getString(10)==null||rsSourceRac.getString(10).length()==0){
								psIsDesRac.setString(5, rsSourceRac.getString(9));//RAC_NAME
							}
							else{
								psIsDesRac.setString(5, rsSourceRac.getString(9)+"\\"+rsSourceRac.getString(10));//RAC_NAME
							}
						}
					}
					psIsDesRac.setInt(6, rsSourceRac.getInt(4));//RAC_PKG_QTY
					psIsDesRac.setString(7, rsSourceRac.getString(5));//RAC_TECH_ID
					psIsDesRac.setBigDecimal(8, smallestTar);//RAC_TARGET
					psIsDesRac.setInt(9, rsSourceRac.getInt(11));
					psIsDesRac.setString(10, rsSourceRac.getString(12));
					
					psIsDesRac.execute();
					psIsDesRac.close();
					psIsDesRac = null;
				}
				rsSourceRac.close();
				rsSourceRac=null;
				psGetSourceRac.close();
				psGetSourceRac=null;	
			}
			
			rsDesWo.close();
			rsDesWo=null;
			psGetDesWo.close();
			psGetDesWo = null;
		}
		
		rsSourceWo.close();
		rsSourceWo = null;
		psGetSourceWo.close();
		psGetSourceWo = null;
		
		System.out.println(LDF.format(new Date())+":"+" wo_syn_new end.");
	}
	
	public static void RepareTar( Connection sourceConn,Connection destinationConn ) throws SQLException
	{
		System.out.println(LDF.format(new Date())+":"+" wo_syn_repair begin.");
		
		//0、获取审核通过没有完工的工单
		//1、判断该工单在条码系统中是否存在
		//2、如果不存在，则同步条码工单，同步信息包括工单、工艺、工艺路线、指标
		//如果发生变更：1、删除所有转移单、工单信息。2、删除条码系统所有信息，包括工单
		
		//RAA001-工单单号 , RAA015-主件品号 , RAA018-生产数量 ,  RAA019-已生产量   ,  RAA039-报废数量 
		//工单未完工（RAA020='N' ）且已经审核通过（RAA024='T'）
		PreparedStatement psGetSourceWo = sourceConn.prepareStatement("SELECT RAA001,RAA015,RAA018,RAA019,RAA039 FROM SGMRAA WHERE RAA020='N' AND RAA024='T' ORDER BY RAA001 DESC");
		ResultSet rsSourceWo = psGetSourceWo.executeQuery();
		while(rsSourceWo.next())
		{
			PreparedStatement psGetDesWo = destinationConn.prepareStatement("SELECT WO_DOC_ID FROM WO_DOC WHERE WO_DOC_ID=?");
			psGetDesWo.setString(1, rsSourceWo.getString(1));
			ResultSet rsDesWo = psGetDesWo.executeQuery();
			if(rsDesWo.next())			{
				
				//获取工艺信息
				PreparedStatement psGetSourceRac = sourceConn.prepareStatement("SELECT T1.RAC002,T1.RAC003,'',T3.QBC981,ISNULL(T4.QBA961,''),ISNULL(T3.QBC980,0),T3.QBC007,T3.QBC962,T4.QBA002,T3.QBC961,ISNULL(T4.QBA980,0) FROM SGMRAC T1 JOIN SGMRAA T2 ON T1.RAC001=T2.RAA001 JOIN SGMQBC T3 ON T3.QBC001=T2.RAA015 AND T1.RAC002=T3.QBC002 JOIN SGMQBA T4 ON T1.RAC003=T4.QBA001 WHERE T1.RAC001=? ORDER BY T1.RAC002");
				psGetSourceRac.setString(1, rsSourceWo.getString(1));
				ResultSet rsSourceRac = psGetSourceRac.executeQuery();
				
				while(rsSourceRac.next())
				{
					PreparedStatement psGetRacTar = sourceConn.prepareStatement("SELECT QBZ003,QBZ005,ISNULL(QBA980,0) FROM SGMQBZ LEFT JOIN SGMQBA ON QBA001=QBZ003 WHERE QBZ001=? AND QBZ002=? ORDER BY QBZ005 ");
					psGetRacTar.setString(1, rsSourceWo.getString(2));
					psGetRacTar.setString(2, rsSourceRac.getString(1));
					ResultSet rsGetRacTar = psGetRacTar.executeQuery();
					BigDecimal smallestTar = BigDecimal.ZERO;
					while(rsGetRacTar.next()){
						if(smallestTar.compareTo(BigDecimal.ZERO)<=0){
							smallestTar = rsGetRacTar.getBigDecimal(2);
						}
					}
					rsGetRacTar.close();
					psGetRacTar.close();
					
					PreparedStatement psIsDesRac = destinationConn.prepareStatement("UPDATE WO_RAC SET RAC_TARGET=? WHERE WO_DOC_ID=? AND WO_RAC_ID=?");
					psIsDesRac.setBigDecimal(1, smallestTar);
					psIsDesRac.setString(2, rsSourceWo.getString(1));
					psIsDesRac.setString(3, rsSourceRac.getString(1));
					psIsDesRac.execute();
					psIsDesRac.close();
				}
				rsSourceRac.close();
				rsSourceRac=null;
				psGetSourceRac.close();
				psGetSourceRac=null;	
			}
			
			rsDesWo.close();
			rsDesWo=null;
			psGetDesWo.close();
			psGetDesWo = null;
		}
		
		rsSourceWo.close();
		rsSourceWo = null;
		psGetSourceWo.close();
		psGetSourceWo = null;
		
		System.out.println(LDF.format(new Date())+":"+" wo_syn_repair end.");
	}
}

