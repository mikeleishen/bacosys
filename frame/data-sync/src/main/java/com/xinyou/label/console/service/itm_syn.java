package com.xinyou.label.console.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class itm_syn {

	private static SimpleDateFormat LDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void GetItms(Connection sourceConn,Connection destinationConn,boolean IsInsert,boolean IsUpdate) throws SQLException
	{
		System.out.println(LDF.format(new Date())+":"+" GetItms begin");
		if(IsInsert){
			System.out.println(" GetItms Insert begin");
		}
		if(IsUpdate){
			System.out.println(" GetItms Update begin");
		}
		
		boolean hasData = false;
		
		//DEA001   品号,DEA002   品名,DEA003   库存单位,DEA004   条码编号,DEA008   主要仓库,DEA013   注销,DEA019   安全存量,DEA057   规格,DEA058   型号
		PreparedStatement psSource = sourceConn.prepareStatement("SELECT DEA001,DEA002,DEA003,DEA004,DEA008,DEA013,DEA019,DEA031,DEA963,DEA981,DEA982,DEA983,DEA012 FROM TPADEA ORDER BY DEA001");
		ResultSet sourceRs = psSource.executeQuery();
		while(sourceRs.next())
		{
			hasData = true;
			String update_id = sourceRs.getString(1);
			
			PreparedStatement psFindDes = destinationConn.prepareStatement("SELECT COUNT(*) FROM ITM_MAIN WHERE ITM_MAIN_ID='"+update_id+"'");
			ResultSet rsFindDes = psFindDes.executeQuery();
			
			if(rsFindDes.next())
			{
				if(rsFindDes.getInt(1)==0)
				{
					if(IsInsert){
						InsertItem(sourceConn,destinationConn, sourceRs);
					}
				}
				else
				{
					if(IsUpdate){
						UpdateItem(sourceConn,destinationConn, sourceRs);
					}
				}
			}
		}
		
		sourceRs.close();
		psSource.close();
		
		if(!hasData)
		{
			System.out.println(LDF.format(new Date())+":"+" GetItms finish");
		}
		
		System.out.println(LDF.format(new Date())+":"+" GetItms finish");
	}

	private static void UpdateItem(Connection sourceConn,Connection destinationConn,
			ResultSet sourceRs) throws SQLException {
		
		PreparedStatement psRacSource = sourceConn.prepareStatement("SELECT QBB960,QBB961,QBB963,QBB964,QBB004,QBB962 FROM SGMQBB WHERE QBB001=?");
		psRacSource.setString(1, sourceRs.getString(1));
		ResultSet rsRacSource = psRacSource.executeQuery();
		if(rsRacSource.next()){
			
			PreparedStatement psDestination = destinationConn.prepareStatement("UPDATE ITM_MAIN SET UPDATED_DT=?,UPDATED_BY=?,IS_DELETED=?,ITM_NAME=?,ITM_SPEC=?,ITM_MODEL=?,ITM_UNIT=?,ITM_DFT_INV=?,ITM_SAFE_QTY=?,ITM_SN=?,ITM_DESC=?,M_QUALITY=?,M_MODEL=?,PTN_NAME=?,NEXT_TEXT=?,SWS_MEMO=?,PAPER_COLOR=?,DEF_LOC_ID=?,PUR_PKG_QTY=? WHERE ITM_MAIN_ID=?");
			
			//UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER
			psDestination.setLong(1, new Date().getTime());
			psDestination.setString(2, "syn");
			if(sourceRs.getString(6).equals("F"))
			{
				psDestination.setInt(3, 0);
			}
			else
			{
				psDestination.setInt(3, 1);
			}
			
			//ITM_NAME,ITM_SPEC,ITM_MODEL,ITM_UNIT,ITM_DFT_INV,ITM_SAFE_QTY,ITM_SN
			psDestination.setString(4, sourceRs.getString(2));
			psDestination.setString(5, "");
			psDestination.setString(6, "");//ITM_MODEL
			psDestination.setString(7, sourceRs.getString(3));//ITM_UNIT
//			psDestination.setString(8, rsRacSource.getString(2));//ITM_DFT_INV
			psDestination.setString(8, sourceRs.getString(5));//ITM_DFT_INV
			psDestination.setBigDecimal(9, sourceRs.getBigDecimal(7));
			psDestination.setString(10, sourceRs.getString(4));
			//,ITM_DESC=?,M_QUALITY=?,M_MODEL=?
			psDestination.setString(11, sourceRs.getString(8));
			psDestination.setString(12, sourceRs.getString(9));//M_QUALITY
			psDestination.setString(13, String.valueOf(sourceRs.getDouble(10))+"X"+String.valueOf(sourceRs.getDouble(11))+"-L="+String.valueOf(sourceRs.getDouble(12)));//M_MODEL
			
			psDestination.setString(14,rsRacSource.getString(1));
			if( rsRacSource.getString(3)!=null&&rsRacSource.getString(3).length()>0){
				String next_text = "至"+rsRacSource.getString(3);
				if( rsRacSource.getString(4)!=null&&rsRacSource.getString(4).length()>0){
					next_text = next_text+rsRacSource.getString(4)+"工序";
					psDestination.setString(15, next_text);
				}
				else{
					psDestination.setString(15, "");
				}
			}
			else{
				psDestination.setString(15, "");
			}
			if( rsRacSource.getString(5)!=null&&rsRacSource.getString(5).length()>0){
				psDestination.setString(16, "注：1、请保持表格清晰。2、安全生产。3、"+rsRacSource.getString(5));
			}
			else{
				psDestination.setString(16, "注：1、请保持表格清晰。2、安全生产。");
			}
			
			if(rsRacSource.getString(6)!=null&&rsRacSource.getString(6).length()<=10){
				psDestination.setString(17, rsRacSource.getString(6));
			}
			else{
				psDestination.setString(17, "");
			}
			
//			psDestination.setString(18, rsRacSource.getString(2));
			psDestination.setString(18, sourceRs.getString(13));
			
			String itmId = sourceRs.getString(1)==null?"   ":sourceRs.getString(1);
			BigDecimal purPkgQty = BigDecimal.ZERO;
			if(itmId.substring(0,3).equals("388")){
				String itmDes = sourceRs.getString(8);
				if(itmDes!=null&&itmDes.length()>=6){
					if(itmDes.substring(0,5).equals("采购装箱数")){
						itmDes = itmDes.replace("采购装箱数", "");
						try{
						purPkgQty = new BigDecimal(itmDes);
						}
						catch(Exception ex){
						}
					}
				}
			}
			psDestination.setBigDecimal(19, purPkgQty);
			
			if(sourceRs.getString(1).equals("388-4P401947-1")){
				System.out.println("388-4P401947-1装箱数："+purPkgQty+" "+sourceRs.getString(8));
			}
			
			psDestination.setString(20, sourceRs.getString(1));
			
			psDestination.execute();
			psDestination.close();
		}
		else
		{
//			if(sourceRs.getString(1).equals("388-3P137932-1A")){
//				int i=0;
//				i=i++;
//			}
			
			try{

			PreparedStatement psDestination = destinationConn.prepareStatement("UPDATE ITM_MAIN SET UPDATED_DT=?,UPDATED_BY=?,IS_DELETED=?,PUR_PKG_QTY=?,ITM_DFT_INV=?,DEF_LOC_ID=? WHERE ITM_MAIN_ID=?");
			
			//UPDATED_DT,UPDATED_BY,IS_DELETED
			psDestination.setLong(1, new Date().getTime());
			psDestination.setString(2, "syn");
			if(sourceRs.getString(6).equals("F"))
			{
				psDestination.setInt(3, 0);
			}
			else
			{
				psDestination.setInt(3, 1);
			}
			
			String itmId = sourceRs.getString(1)==null?"   ":sourceRs.getString(1);
//			BigDecimal purPkgQty = BigDecimal.ZERO;
//			if(itmId.length()>3){
//				if(itmId.substring(0,3).equals("388")){
//					String itmDes = sourceRs.getString(8);
//					if(itmDes!=null&&itmDes.length()>=6){
//						if(itmDes.substring(0,5).equals("采购装箱数")){
//							itmDes = itmDes.replace("采购装箱数", "");
//							try{
//							purPkgQty = new BigDecimal(itmDes);
//							}
//							catch(Exception ex){
//							}
//						}
//					}
//				}
//			}
			
			BigDecimal purPkgQty = BigDecimal.ZERO;
			if(itmId.substring(0,3).equals("388")){
				String itmDes = sourceRs.getString(8);
				if(itmDes!=null&&itmDes.length()>=6){
					if(itmDes.substring(0,5).equals("采购装箱数")){
						itmDes = itmDes.replace("采购装箱数", "").replace(" ", "").trim();
						try{
						purPkgQty = new BigDecimal(itmDes);
						}
						catch(Exception ex){
						}
					}
				}
			}
			psDestination.setBigDecimal(4, purPkgQty);
			
//			if(sourceRs.getString(1).equals("388-4P401947-1")){
//				System.out.println("388-4P401947-1装箱数："+purPkgQty);
//				System.out.println(itmId.substring(0,3).equals("388"));
//				System.out.println(sourceRs.getString(8).substring(0,5).equals("采购装箱数"));
//			}
			
			psDestination.setString(5, sourceRs.getString(5));
			psDestination.setString(6, sourceRs.getString(13));
			psDestination.setString(7, sourceRs.getString(1));
			
			psDestination.execute();
			psDestination.close();
			
			
		}catch(Exception ex){
//			System.out.print(sourceRs.getString(1)+":更新失败！");
			
		}
		}
		
		rsRacSource.close();
		psRacSource.close();
	}

	private static void InsertItem(Connection sourceConn,Connection destinationConn,
			ResultSet sourceRs) throws SQLException {
		PreparedStatement psRacSource = sourceConn.prepareStatement("SELECT QBB960,QBB961,QBB963,QBB964,QBB004,QBB962 FROM SGMQBB WHERE QBB001=?");
		psRacSource.setString(1, sourceRs.getString(1));
		ResultSet rsRacSource = psRacSource.executeQuery();
		if(rsRacSource.next()){
			PreparedStatement psDestination = destinationConn.prepareStatement("INSERT INTO ITM_MAIN(ITM_MAIN_GUID,ITM_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,ITM_NAME,ITM_SPEC,ITM_MODEL,ITM_UNIT,ITM_DFT_INV,ITM_SAFE_QTY,ITM_SN,ITM_DESC,M_QUALITY,M_MODEL,PTN_NAME,NEXT_TEXT,SWS_MEMO,PAPER_COLOR,DEF_LOC_ID,PUR_PKG_QTY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			//ITM_MAIN_GUID,ITM_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER
			psDestination.setString(1, UUID.randomUUID().toString());
			psDestination.setString(2, sourceRs.getString(1));
			psDestination.setLong(3, new Date().getTime());
			psDestination.setString(4, "syn");
			psDestination.setLong(5, new Date().getTime());
			psDestination.setString(6, "syn");
			psDestination.setString(7, "gl");
			if(sourceRs.getString(6).equals("F"))
			{
				psDestination.setInt(8, 0);
			}
			else
			{
				psDestination.setInt(8, 1);
			}
			psDestination.setString(9, "1.0.0.0");
			
			//ITM_NAME,ITM_SPEC,ITM_MODEL,ITM_UNIT,ITM_DFT_INV,ITM_SAFE_QTY,ITM_SN
			psDestination.setString(10, sourceRs.getString(2));//ITM_NAME
			psDestination.setString(11, "");//ITM_SPEC
			psDestination.setString(12, "");//ITM_MODEL
			psDestination.setString(13, sourceRs.getString(3));//ITM_UNIT
//			psDestination.setString(14, rsRacSource.getString(2));//ITM_DFT_INV
			psDestination.setString(14, sourceRs.getString(5));//ITM_DFT_INV
			psDestination.setBigDecimal(15, sourceRs.getBigDecimal(7));//ITM_SAFE_QTY
			psDestination.setString(16, sourceRs.getString(4));//ITM_SN
			//ITM_DESC,M_QUALITY,M_MODEL
			psDestination.setString(17, sourceRs.getString(8));//ITM_DESC
			psDestination.setString(18, sourceRs.getString(9));//M_QUALITY
			psDestination.setString(19, String.valueOf(sourceRs.getDouble(10))+"X"+String.valueOf(sourceRs.getDouble(11))+"-L="+String.valueOf(sourceRs.getDouble(12)));//M_MODEL
			//,PTN_NAME,NEXT_TEXT,SWS_MEMO
			psDestination.setString(20,rsRacSource.getString(1));
			if( rsRacSource.getString(3)!=null&&rsRacSource.getString(3).length()>0){
				String next_text = "至"+rsRacSource.getString(3);
				if( rsRacSource.getString(4)!=null&&rsRacSource.getString(4).length()>0){
					next_text = rsRacSource.getString(4)+"工序";
					psDestination.setString(21, next_text);
				}
				else{
					psDestination.setString(21, "");
				}
			}
			else{
				psDestination.setString(21, "");
			}
			if( rsRacSource.getString(5)!=null&&rsRacSource.getString(5).length()>0){
				psDestination.setString(22, "注：1、请保持表格清晰。2、安全生产。3、"+rsRacSource.getString(5));
			}
			else{
				psDestination.setString(22, "注：1、请保持表格清晰。2、安全生产。");
			}
			
			if(rsRacSource.getString(6)!=null&&rsRacSource.getString(6).length()<=10){
				psDestination.setString(23, rsRacSource.getString(6));
			}
			else{
				psDestination.setString(23, "");
			}
//			psDestination.setString(24, rsRacSource.getString(2));
			psDestination.setString(24, sourceRs.getString(13));//DEF_LOC_ID
			
			String itmId = sourceRs.getString(1)==null?"   ":sourceRs.getString(1);
			BigDecimal purPkgQty = BigDecimal.ZERO;
			if(itmId.length()>3){
				if(itmId.substring(0,3).equals("388")){
					String itmDes = rsRacSource.getString(8);
					if(itmDes!=null&&itmDes.length()>=6){
						if(itmDes.substring(0,5).equals("采购装箱数")){
							itmDes = itmDes.replace("采购装箱数", "");
							try{
							purPkgQty = new BigDecimal(itmDes);
							}
							catch(Exception ex){
							}
						}
					}
				}
			}
			psDestination.setBigDecimal(25, purPkgQty);

			psDestination.execute();
			psDestination.close();
		}
		else{
			PreparedStatement psDestination = destinationConn.prepareStatement("INSERT INTO ITM_MAIN(ITM_MAIN_GUID,ITM_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,ITM_NAME,ITM_SPEC,ITM_MODEL,ITM_UNIT,ITM_DFT_INV,ITM_SAFE_QTY,ITM_SN,ITM_DESC,M_QUALITY,M_MODEL,PTN_NAME,NEXT_TEXT,SWS_MEMO,PAPER_COLOR,DEF_LOC_ID,PUR_PKG_QTY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			//ITM_MAIN_GUID,ITM_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER
			psDestination.setString(1, UUID.randomUUID().toString());
			psDestination.setString(2, sourceRs.getString(1));
			psDestination.setLong(3, new Date().getTime());
			psDestination.setString(4, "syn");
			psDestination.setLong(5, new Date().getTime());
			psDestination.setString(6, "syn");
			psDestination.setString(7, "gl");
			if(sourceRs.getString(6).equals("F"))
			{
				psDestination.setInt(8, 0);
			}
			else
			{
				psDestination.setInt(8, 1);
			}
			psDestination.setString(9, "1.0.0.0");
			
			//ITM_NAME,ITM_SPEC,ITM_MODEL,ITM_UNIT,ITM_DFT_INV,ITM_SAFE_QTY,ITM_SN
			psDestination.setString(10, sourceRs.getString(2));//ITM_NAME
			psDestination.setString(11, "");//ITM_SPEC
			psDestination.setString(12, "");//ITM_MODEL
			psDestination.setString(13, sourceRs.getString(3));//ITM_UNIT
//			psDestination.setString(14, rsRacSource.getString(2));//ITM_DFT_INV
			psDestination.setString(14, sourceRs.getString(5));//ITM_DFT_INV
			psDestination.setBigDecimal(15, sourceRs.getBigDecimal(7));//ITM_SAFE_QTY
			psDestination.setString(16, sourceRs.getString(4));//ITM_SN
			//ITM_DESC,M_QUALITY,M_MODEL
			psDestination.setString(17, sourceRs.getString(8));//ITM_DESC
			psDestination.setString(18, sourceRs.getString(9));//M_QUALITY
			psDestination.setString(19, String.valueOf(sourceRs.getDouble(10))+"X"+String.valueOf(sourceRs.getDouble(11))+"-L="+String.valueOf(sourceRs.getDouble(12)));//M_MODEL
			//,PTN_NAME,NEXT_TEXT,SWS_MEMO
			psDestination.setString(20,"");
			psDestination.setString(21, "");
			psDestination.setString(22, "");
			psDestination.setString(23, "");
			psDestination.setString(24, sourceRs.getString(13));//DEF_LOC_ID
			
			String itmId = sourceRs.getString(1)==null?"   ":sourceRs.getString(1);
			BigDecimal purPkgQty = BigDecimal.ZERO;
			if(itmId.length()>3){
				if(itmId.substring(0,3).equals("388")){
					String itmDes = sourceRs.getString(8);
					if(itmDes!=null&&itmDes.length()>=6){
						if(itmDes.substring(0,5).equals("采购装箱数")){
							itmDes = itmDes.replace("采购装箱数", "");
							try{
							purPkgQty = new BigDecimal(itmDes);
							}
							catch(Exception ex){
							}
						}
					}
				}
			}
			psDestination.setBigDecimal(25, purPkgQty);

			psDestination.execute();
			psDestination.close();
		}
	}
}
