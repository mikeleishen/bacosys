package com.xinyou.label.domain.biz;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.xinyou.label.domain.entities.CTN_MAIN;
import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;
import com.xinyou.label.domain.viewes.ITM_MAIN_VIEW;

public class Common_Biz {
	public static String addBaco(CTN_MAIN ctn, String operator, String data_ver, String client_guid, Connection conn) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		String bacoGUID = "";
		
		try
		{
			//判断ID
			if(StringUtils.isEmpty( ctn.getCtn_main_id()))
			{
				throw new Exception("代码为空！");
			}
			
			//判断重复
			ps = conn.prepareStatement("SELECT CTN_MAIN_ID FROM CTN_MAIN WHERE CTN_MAIN_ID=? AND CTN_TYPE=?");
			ps.setString(1, ctn.getCtn_main_id());
			ps.setInt(2, ctn.getCtn_type());
			rs = ps.executeQuery();
			if(rs.next())
			{
				throw new Exception("代码重复！");
			}
			rs.close();
			ps.close();
			
			//判断条码重复
			if(!StringUtils.isEmpty( ctn.getCtn_baco()))
			{
				ps = conn.prepareStatement("SELECT CTN_MAIN_ID FROM CTN_MAIN WHERE CTN_BACO=? AND CTN_TYPE<>111");
				ps.setString(1, ctn.getCtn_baco());
				rs = ps.executeQuery();
				if(rs.next())
				{
					throw new Exception("条码已使用！");
				}
				rs.close();
				ps.close();
			}

			bacoGUID = UUID.randomUUID().toString();
			long nowDt = new Date().getTime();
			ps=conn.prepareStatement("INSERT INTO CTN_MAIN(CTN_MAIN_GUID, CTN_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, CLIENT_GUID, IS_DELETED, DATA_VER, CTN_TYPE, PARENT_CTN_GUID, CTN_HEIGHT, CTN_WIDTH, CTN_LENGTH, ST_USE_DT, CTN_LIFE, CTN_BACO, CTN_NAME, CTN_STATUS, PST_DESC, ITM_ID, ITM_QTY, WH_GUID, WH_AREA_GUID, WH_SHELF_GUID, WH_LOC_GUID, WH_PLT_GUID, WH_PACKAGE_GUID,LOT_ID,BASE_TYPE,BASE_ID,BASE_SEQNO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, bacoGUID);
			ps.setString(2, ctn.getCtn_main_id());
			ps.setLong(3, nowDt);
			ps.setString(4, operator);
			ps.setLong(5, nowDt);
			ps.setString(6, operator);
			ps.setString(7, client_guid);
			ps.setInt(8, 0);
			ps.setString(9, data_ver);
			ps.setInt(10, ctn.getCtn_type());
			ps.setString(11, ctn.getParent_ctn_guid());
			ps.setInt(12, ctn.getCtn_height());
			ps.setInt(13, ctn.getCtn_width());
			ps.setInt(14, ctn.getCtn_length());
			ps.setLong(15, 0);
			ps.setInt(16, 0);
			ps.setString(17, ctn.getCtn_baco());
			ps.setString(18, ctn.getCtn_name());
			ps.setInt(19, 0);
			ps.setString(20, "");
			ps.setString(21, ctn.getItm_id());
			ps.setBigDecimal(22, ctn.getItm_qty());
			ps.setString(23, ctn.getWh_guid());
			ps.setString(24, ctn.getWh_area_guid());
			ps.setString(25, ctn.getWh_shelf_guid());
			ps.setString(26, ctn.getWh_loc_guid());
			ps.setString(27, ctn.getWh_plt_guid());
			ps.setString(28, ctn.getWh_package_guid());
			ps.setString(29, ctn.getLot_id());
			ps.setInt(30, ctn.getBase_type());
			ps.setString(31, ctn.getBase_id());
			ps.setString(32, ctn.getBase_seqno());
			
			ps.execute();
			
			if(!StringUtils.isEmpty(ctn.getParent_ctn_guid()))
			{
				updCtnStatus(ctn.getParent_ctn_guid(),conn);
			}
			return bacoGUID;
		}
		catch(Exception e){
			throw e;
		}finally{
			if(ps!=null&&!ps.isClosed())ps.close();
		}
	}
	
	public static void delBaco(String ctn_guid, Connection conn) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int ctnType = -1;
		int parentCtnType = -1;
		String parentCtnGuid = "";
		
		try{
			pstmt=conn.prepareStatement("SELECT T.CTN_TYPE,T1.CTN_TYPE,T1.CTN_MAIN_GUID FROM CTN_MAIN T LEFT JOIN CTN_MAIN T1 ON T1.CTN_MAIN_GUID=T.PARENT_CTN_GUID WHERE T.CTN_MAIN_GUID=?");
			pstmt.setString(1, ctn_guid);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				ctnType = rs.getInt(1);
				parentCtnType = rs.getInt(2);
				parentCtnGuid = rs.getString(3);
			}
			else
			{
				rs.close();
				pstmt.close();
				throw new Exception("未找到记录！");
			}
			rs.close();
			pstmt.close();
			
			pstmt=conn.prepareStatement("SELECT COUNT(*) FROM CTN_MAIN WHERE PARENT_CTN_GUID=?");
			pstmt.setString(1, ctn_guid);
			rs=pstmt.executeQuery();
			if(rs.next()&&rs.getInt(1)>0){
				rs.close();
				pstmt.close();
				throw new Exception("因包含其他物品信息，不可删除！");
			}
			rs.close();
			pstmt.close();
			
			
			if(ctnType==10||ctnType==12||ctnType==13)
			{
				pstmt=conn.prepareStatement("SELECT ITM_QTY FROM CTN_MAIN WHERE CTN_MAIN_GUID=?");
				pstmt.setString(1, ctn_guid);
				rs=pstmt.executeQuery();
				if(rs.next())
				{
					if(rs.getBigDecimal(1)!=null&&rs.getBigDecimal(1).compareTo(new BigDecimal(0))>0)
					{
						rs.close();
						pstmt.close();
						throw new Exception("因包含其他物品数量大于0，不可删除！");
					}
				}
			}
			rs.close();
			pstmt.close();
			
			pstmt=conn.prepareStatement("DELETE FROM  CTN_MAIN WHERE CTN_MAIN_GUID=?");
			pstmt.setString(1, ctn_guid);
			pstmt.execute();
			pstmt.close();
			
			if(parentCtnType>0)
			{
				updCtnStatus(parentCtnGuid,conn);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}
	
	public static CTN_MAIN_VIEW getCtnByBaco( String ctn_baco, Connection conn ) throws Exception
	{
		return getCtn(ctn_baco,"","",conn);
	}
	
	public static CTN_MAIN_VIEW getCtnById( String id, Connection conn ) throws Exception
	{
		return getCtn("",id,"",conn);
	}
	
	public static CTN_MAIN_VIEW getCtnByGuid( String guid, Connection conn ) throws Exception
	{
		return getCtn("","",guid,conn);
	}
	
	
	/**
	 *   更新CTN_MAIN 表的 CTN_STATUS 状态
	 *   如果 传进去的参数 guid 是 其他Container 的 Parent Container，
	 *   那么 CTN_STATUS 设置为1 
	 *   如果不是，那么设置为 0 
	 *   
	 *   
	 * */
	public static void updCtnStatus( String guid, Connection conn ) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt=conn.prepareStatement("SELECT COUNT(*) FROM CTN_MAIN WHERE PARENT_CTN_GUID=?");
			pstmt.setString(1, guid);
			rs=pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)==0)
				{
					rs.close();
					pstmt.close();
					
					pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET CTN_STATUS=0 WHERE CTN_MAIN_GUID=?");
					pstmt.setString(1, guid);
					pstmt.execute();
				}
				else
				{
					rs.close();
					pstmt.close();
					
					pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET CTN_STATUS=1 WHERE CTN_MAIN_GUID=?");
					pstmt.setString(1, guid);
					pstmt.execute();
				}
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}
	
	public static boolean bacoIsUsed( String baco, Connection conn) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt=conn.prepareStatement("SELECT COUNT(*) FROM CTN_MAIN WHERE CTN_BACO=?");
			pstmt.setString(1, baco);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}
	
	/**
	 * TODO 
	 * @param baco      条码 
	 * @param ctn_guid  CTN_MAIN表的CTN_MAIN_GUID 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static boolean bacoIsUsedForUpd( String baco, String ctn_guid, Connection conn) throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt=conn.prepareStatement("SELECT CTN_MAIN_ID FROM CTN_MAIN WHERE CTN_BACO=? AND CTN_MAIN_GUID<>?");
			pstmt.setString(1, baco);
			pstmt.setString(2, ctn_guid);
			rs=pstmt.executeQuery();
			if(rs.next()){
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}
	
	private static CTN_MAIN_VIEW getCtn(String ctn_baco, String ctn_id, String ctn_guid, Connection conn) throws Exception {
		
		/**
		 *   根据 CTN_BACO、CTN_MAIN_ID、CTN_MAIN_GUID 获取 库位及存放的物料信息 
		 * **/
		
		CTN_MAIN_VIEW entity=new CTN_MAIN_VIEW();
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs=null;

		try
		{
			sb.append("SELECT T.CTN_MAIN_GUID,T.CTN_MAIN_ID,T.CTN_TYPE,T.CTN_BACO,T.CTN_STATUS,");
			sb.append("T1.CTN_TYPE,T1.CTN_MAIN_ID,T1.CTN_BACO,T2.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_SPEC,T2.ITM_UNIT,T.ITM_QTY,0,");
			sb.append("T3.CTN_MAIN_ID,T3.CTN_NAME,T3.CTN_BACO,");
			sb.append("T4.CTN_MAIN_ID,T4.CTN_BACO,T5.CTN_MAIN_ID,T5.CTN_BACO,T6.CTN_MAIN_ID,T6.CTN_BACO,");
			sb.append("T7.CTN_MAIN_ID,T7.CTN_BACO,T8.CTN_MAIN_ID,T8.CTN_BACO,");
			sb.append("T3.CTN_MAIN_GUID,T4.CTN_MAIN_GUID,T5.CTN_MAIN_GUID,T6.CTN_MAIN_GUID,T7.CTN_MAIN_GUID, ");
			sb.append("T8.CTN_MAIN_GUID,T.LOT_ID,ISNULL(T.BASE_TYPE,-1),T.BASE_ID,T.BASE_SEQNO,T2.DEF_LOC_ID,T.STK_GUID,T.UPDATED_BY,T.CREATED_BY ");
			sb.append(" FROM CTN_MAIN T");
			sb.append(" LEFT JOIN CTN_MAIN T1 ON T1.CTN_MAIN_GUID=T.PARENT_CTN_GUID");
			sb.append(" LEFT JOIN ITM_MAIN T2 ON T2.ITM_MAIN_ID=T.ITM_ID");
			sb.append(" LEFT JOIN CTN_MAIN T3 ON T3.CTN_MAIN_GUID=T.WH_GUID");
			sb.append(" LEFT JOIN CTN_MAIN T4 ON T4.CTN_MAIN_GUID=T.WH_AREA_GUID");
			sb.append(" LEFT JOIN CTN_MAIN T5 ON T5.CTN_MAIN_GUID=T.WH_SHELF_GUID");
			sb.append(" LEFT JOIN CTN_MAIN T6 ON T6.CTN_MAIN_GUID=T.WH_LOC_GUID");
			sb.append(" LEFT JOIN CTN_MAIN T7 ON T7.CTN_MAIN_GUID=T.WH_PLT_GUID");
			sb.append(" LEFT JOIN CTN_MAIN T8 ON T8.CTN_MAIN_GUID=T.WH_PACKAGE_GUID");
			sb.append(" WHERE 1=1");
			
			if(!StringUtils.isEmpty(ctn_baco))
			{
				sb.append( " AND T.CTN_BACO=?" );
			}
			if(!StringUtils.isEmpty(ctn_id))
			{
				sb.append( " AND T.CTN_MAIN_ID=?" );
			}
			if(!StringUtils.isEmpty(ctn_guid))
			{
				sb.append( "AND T.CTN_MAIN_GUID=?" );
			}

			ps=conn.prepareStatement(sb.toString());

			int index = 0;
			if(!StringUtils.isEmpty(ctn_baco))
			{
				ps.setString(++index, ctn_baco);
			}
			if(!StringUtils.isEmpty(ctn_id))
			{
				ps.setString(++index, ctn_id);
			}
			if(!StringUtils.isEmpty(ctn_guid))
			{
				ps.setString(++index, ctn_guid);
			}
			
			rs=ps.executeQuery();
			if(rs.next()){				
				entity.setCtn_main_guid(rs.getString(1));
				entity.setCtn_main_id(rs.getString(2));
				entity.setCtn_type(rs.getInt(3));
				entity.setCtn_baco(rs.getString(4));
				entity.setCtn_status(rs.getInt(5));
				
				//T1.CTN_TYPE,T1.CTN_MAIN_ID,T1.CTN_BACO,T2.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_SPEC,T2.ITM_UNIT,T.GD_QTY,0,
				entity.setParent_ctn_type(rs.getInt(6));
				entity.setParent_ctn_id(rs.getString(7));
				entity.setParent_ctn_baco(rs.getString(8));
				entity.setItm_id(rs.getString(9));
				entity.setItm_name(rs.getString(10));
				entity.setItm_spec(rs.getString(11));
				entity.setItm_unit(rs.getString(12));
				entity.setItm_qty(rs.getBigDecimal(13));
				entity.setItm_got_qty(rs.getBigDecimal(14));
				
				//T3.CTN_MAIN_ID,T3.CTN_NAME,T3.CTN_BACO,
				entity.setWh_id(rs.getString(15));
				entity.setWh_name(rs.getString(16));
				entity.setWh_baco(rs.getString(17));
				
				//T4.CTN_MAIN_ID,T4.CTN_BACO,T5.CTN_MAIN_ID,T5.CTN_BACO,T6.CTN_MAIN_ID,T6.CTN_BACO,
				entity.setWh_area_id(rs.getString(18));
				entity.setWh_area_baco(rs.getString(19));
				entity.setWh_shelf_id(rs.getString(20));
				entity.setWh_shelf_baco(rs.getString(21));
				entity.setWh_loc_id(rs.getString(22));
				entity.setWh_loc_baco(rs.getString(23));
				
				//T7.CTN_MAIN_ID,T7.CTN_BACO,T8.CTN_MAIN_ID,T8.CTN_BACO
				entity.setWh_plt_id(rs.getString(24));
				entity.setWh_plt_baco(rs.getString(25));
				entity.setWh_package_id(rs.getString(26));
				entity.setWh_package_baco(rs.getString(27));
				
				//T3.CTN_MAIN_GUID,T4.CTN_MAIN_GUID,T5.CTN_MAIN_GUID,T6.CTN_MAIN_GUID,T7.CTN_MAIN_GUID,T8.CTN_MAIN_GUID
				entity.setWh_guid(rs.getString(28));
				entity.setWh_area_guid(rs.getString(29));
				entity.setWh_shelf_guid(rs.getString(30));
				entity.setWh_loc_guid(rs.getString(31));
				entity.setWh_plt_guid(rs.getString(32));
				entity.setWh_package_guid(rs.getString(33));
				entity.setLot_id(rs.getString(34));
				entity.setBase_type(rs.getInt(35));
				entity.setBase_id(rs.getString(36));
				entity.setBase_seqno(rs.getString(37));
				entity.setDef_loc_id(rs.getString(38));
				entity.setStk_guid(rs.getString(39));
				entity.setUpdated_by(rs.getString(40));
				entity.setCreated_by(rs.getString(41));
			}
			rs.close();
			ps.close();
		}
		catch(Exception e){
			throw e;
		}finally{
			if(rs!=null&&!rs.isClosed())rs.close();
			if(ps!=null&&!ps.isClosed())ps.close();
		}
		
		return entity;
	}

	public static CTN_MAIN getCtnByView(CTN_MAIN_VIEW view)
	{
		CTN_MAIN result = new CTN_MAIN();
		
		result.setCtn_main_guid(view.getCtn_main_guid());
		
		result.setCtn_baco(view.getCtn_baco());
		result.setCtn_main_id(view.getCtn_main_id());
		result.setCtn_status(view.getCtn_status());
		result.setCtn_type(view.getCtn_type());
		result.setItm_id(view.getItm_id());
		result.setItm_qty(view.getItm_qty());
		result.setParent_ctn_guid(view.getParent_ctn_guid());
		result.setWh_area_guid(view.getWh_area_guid());
		result.setWh_guid(view.getWh_guid());
		result.setWh_loc_guid(view.getWh_loc_guid());
		result.setWh_package_guid(view.getWh_package_guid());
		result.setWh_plt_guid(view.getWh_plt_guid());
		result.setWh_shelf_guid(view.getWh_shelf_guid());
		result.setLot_id(view.getLot_id());
		
		return result;
	}

	public static void updCtnPos(CTN_MAIN ctn,String operator,Connection conn) throws SQLException
	{
		PreparedStatement ps = null;
		
		ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=?,LOT_ID=?,PARENT_CTN_GUID=? WHERE CTN_MAIN_GUID=?");
		
		ps.setLong(1, new Date().getTime());
		ps.setString(2, operator);
		ps.setString(3, ctn.getWh_guid());
		ps.setString(4, ctn.getWh_area_guid());
		ps.setString(5, ctn.getWh_shelf_guid());
		ps.setString(6, ctn.getWh_loc_guid());
		ps.setString(7, ctn.getWh_plt_guid());
		ps.setString(8, ctn.getLot_id());
		ps.setString(9, ctn.getParent_ctn_guid());
		ps.setString(10, ctn.getCtn_main_guid());
		ps.execute();
		ps.close();
		
		//条码箱和无条码箱
		if(ctn.getCtn_type()==13 || ctn.getCtn_type()==12||ctn.getCtn_type()==11||ctn.getCtn_type()==10)
		{
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=? WHERE WH_PACKAGE_GUID=?");
			
			ps.setLong(1, new Date().getTime());
			ps.setString(2, operator);
			ps.setString(3, ctn.getWh_guid());
			ps.setString(4, ctn.getWh_area_guid());
			ps.setString(5, ctn.getWh_shelf_guid());
			ps.setString(6, ctn.getWh_loc_guid());
			ps.setString(7, ctn.getWh_plt_guid());
			ps.setString(8, ctn.getCtn_main_guid());
			ps.execute();
			ps.close();
		}
		else//箱中箱，目前系统仅支持到两级箱
		{
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,PARENT_CTN_GUID=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=?,WH_PACKAGE_GUID=? WHERE CTN_MAIN_GUID=?");
			
			ps.setLong(1, new Date().getTime());
			ps.setString(2, operator);
			ps.setString(3, ctn.getParent_ctn_guid());
			ps.setString(4, ctn.getWh_guid());
			ps.setString(5, ctn.getWh_area_guid());
			ps.setString(6, ctn.getWh_shelf_guid());
			ps.setString(7, ctn.getWh_loc_guid());
			ps.setString(8, ctn.getWh_plt_guid());
			ps.setString(9, ctn.getWh_package_guid());
			ps.setString(10, ctn.getCtn_main_guid());
			ps.execute();
			ps.close();
		}
		/*
		//托盘
		if(ctn.getCtn_type()==14)
		{
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=? WHERE WH_PLT_GUID=?");
			
			ps.setLong(1, new Date().getTime());
			ps.setString(2, operator);
			ps.setString(3, ctn.getWh_guid());
			ps.setString(4, ctn.getWh_area_guid());
			ps.setString(5, ctn.getWh_shelf_guid());
			ps.setString(6, ctn.getWh_loc_guid());
			ps.setString(7, ctn.getCtn_main_guid());
			ps.execute();
			ps.close();
		}
		*/
		
	}

	public static void updPltPos(CTN_MAIN_VIEW ctn,String operator,Connection conn) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,PARENT_CTN_GUID=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=? WHERE CTN_MAIN_GUID=?");
		
		ps.setLong(1, new Date().getTime());
		ps.setString(2, operator);
		ps.setString(3, ctn.getParent_ctn_guid());
		ps.setString(4, ctn.getWh_guid());
		ps.setString(5, ctn.getWh_area_guid());
		ps.setString(6, ctn.getWh_shelf_guid());
		ps.setString(7, ctn.getWh_loc_guid());
		ps.setString(8, ctn.getCtn_main_guid());
		ps.execute();
		ps.close();	
		
		updPltChildPos(ctn,ctn.getCtn_main_guid(),operator,conn);
	}
	
	public static void updPltChildPos( CTN_MAIN_VIEW plt, String parentGuid, String operator,Connection conn) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("SELECT CTN_MAIN_GUID FROM CTN_MAIN WHERE PARENT_CTN_GUID=?");
		ps.setString(1, parentGuid);
		ResultSet rs = ps.executeQuery();
		PreparedStatement psUpdate;
		
		while(rs.next())
		{
			psUpdate = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,PARENT_CTN_GUID=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=? WHERE CTN_MAIN_GUID=?");
			
			psUpdate.setLong(1, new Date().getTime());
			psUpdate.setString(2, operator);
			psUpdate.setString(3, parentGuid);
			psUpdate.setString(4, plt.getWh_guid());
			psUpdate.setString(5, plt.getWh_area_guid());
			psUpdate.setString(6, plt.getWh_shelf_guid());
			psUpdate.setString(7, plt.getWh_loc_guid());
			psUpdate.setString(8, plt.getCtn_main_guid());
			psUpdate.setString(9, rs.getString(1));
			psUpdate.execute();
			psUpdate.close();
			
			updPltChildPos(plt,rs.getString(1),operator,conn);
		}
	}
	
	public static void updItmPos(CTN_MAIN_VIEW ctn,String operator,Connection conn) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,PARENT_CTN_GUID=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=?,WH_PACKAGE_GUID=? WHERE CTN_MAIN_GUID=?");
		
		ps.setLong(1, new Date().getTime());
		ps.setString(2, operator);
		ps.setString(3, ctn.getParent_ctn_guid());
		ps.setString(4, ctn.getWh_guid());
		ps.setString(5, ctn.getWh_area_guid());
		ps.setString(6, ctn.getWh_shelf_guid());
		ps.setString(7, ctn.getWh_loc_guid());
		ps.setString(8, ctn.getWh_plt_guid());
		ps.setString(9, ctn.getWh_package_guid());
		ps.setString(10, ctn.getCtn_main_guid());
		
		ps.execute();
	}
	
	public static List<CTN_MAIN_VIEW> getBoxItms(String ctnGUID,Connection conn) throws Exception
	{
		List<CTN_MAIN_VIEW> result = new ArrayList<CTN_MAIN_VIEW>();
		CTN_MAIN_VIEW ctn = null;
		CTN_MAIN_VIEW tempItm = null;
		String cmd = "";
		
		ctn = getCtnByGuid(ctnGUID,conn);
		
		if(ctn!=null)
		{
			switch(ctn.getCtn_type())
			{
				case 10:
					break;
				case 11:
					cmd = "SELECT T2.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_SPEC,T2.ITM_UNIT,T1.ITM_QTY,T1.CTN_BACO,T1.CTN_MAIN_GUID FROM CTN_MAIN T1 JOIN ITM_MAIN T2 ON T1.ITM_ID=T2.ITM_MAIN_ID WHERE PARENT_CTN_GUID=? AND CTN_TYPE=111 ORDER BY T1.ITM_ID";
					break;
				case 13:
					cmd = "SELECT T2.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_SPEC,T2.ITM_UNIT,T1.ITM_QTY,T1.CTN_BACO,T1.CTN_MAIN_GUID FROM CTN_MAIN T1 JOIN ITM_MAIN T2 ON T1.ITM_ID=T2.ITM_MAIN_ID WHERE PARENT_CTN_GUID=? AND CTN_TYPE=12 ORDER BY T1.ITM_ID";
					break;
				case 14:
					break;
				default:
					break;	
			}
		}
		
		PreparedStatement ps = conn.prepareStatement(cmd);
		ps.setString(1, ctnGUID);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			tempItm = new CTN_MAIN_VIEW();
			
			tempItm.setItm_id(rs.getString(1));
			tempItm.setItm_name(rs.getString(2));
			tempItm.setItm_spec(rs.getString(3));
			tempItm.setItm_unit(rs.getString(4));
			tempItm.setItm_qty(rs.getBigDecimal(5));
			tempItm.setCtn_baco(rs.getString(6));
			tempItm.setCtn_main_guid(rs.getString(7));
			
			result.add(tempItm);
		}
		
		return result;
	}
	
	/*获取该条码下所包含的所有条码
	 * 如果是无条码箱、物料、周转箱，则返回自身，（属于不正常操作）
	 * 否则返回包含的条码，不包括自身
	 */
	public static List<CTN_MAIN_VIEW> getCtnBacoes(String ctnBaco,Connection conn) throws Exception
	{
		List<CTN_MAIN_VIEW> result = new ArrayList<CTN_MAIN_VIEW>();
		CTN_MAIN_VIEW tempItm = null;
		StringBuilder sb = new StringBuilder();
		
		CTN_MAIN_VIEW mainCtn = getCtnByBaco(ctnBaco,conn);
		if(mainCtn==null||StringUtils.isEmpty(mainCtn.getCtn_main_guid()))
		{
			throw new Exception("未找到条码信息："+ctnBaco);
		}
		
		switch(mainCtn.getCtn_type())
		{
			case 10:
			case 12:
				result.add(mainCtn);
				return result;
			case 11:
				sb.append("SELECT T1.CTN_MAIN_GUID,T1.CTN_BACO,T1.CTN_TYPE,T2.ITM_MAIN_ID,T1.ITM_QTY,T2.ITM_UNIT,T2.ITM_NAME,T2.ITM_SPEC FROM CTN_MAIN T1 JOIN ITM_MAIN T2 ON T2.ITM_MAIN_ID=T1.ITM_ID WHERE T1.WH_PACKAGE_GUID=? AND T1.CTN_TYPE=111 ORDER BY T2.ITM_MAIN_ID");
				break;
			case 13:
				sb.append("SELECT T1.CTN_MAIN_GUID,T1.CTN_BACO,T1.CTN_TYPE,T2.ITM_MAIN_ID,T1.ITM_QTY,T2.ITM_UNIT,T2.ITM_NAME,T2.ITM_SPEC FROM CTN_MAIN T1 JOIN ITM_MAIN T2 ON T2.ITM_MAIN_ID=T1.ITM_ID WHERE T1.WH_PACKAGE_GUID=? AND T1.CTN_TYPE<>111 ORDER BY T1.CTN_BACO");
				break;
			case 14:
				sb.append("SELECT CTN_MAIN_GUID,CTN_BACO,CTN_TYPE FROM CTN_MAIN WHERE WH_PLT_GUID=? AND CTN_TYPE<>111 ORDER BY CTN_BACO");
				break;
			default:
				return result;
		}

		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, mainCtn.getCtn_main_guid());
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			tempItm = new CTN_MAIN_VIEW();
			
			tempItm.setCtn_main_guid(rs.getString(1));
			tempItm.setCtn_baco(rs.getString(2));
			tempItm.setCtn_type(rs.getInt(3));
			if(mainCtn.getCtn_type()==11 || mainCtn.getCtn_type()==13)
			{
				tempItm.setItm_id(rs.getString(4));
				tempItm.setItm_qty(rs.getBigDecimal(5));
				tempItm.setItm_unit(rs.getString(6));
				tempItm.setItm_name(rs.getString(7));
				tempItm.setItm_spec(rs.getString(8));
			}
			
			result.add(tempItm);
		}
		
		return result;
	}
	
	//以汇总的方式，获取某个容器的所有包含物料的汇总信息
	public static List<ITM_MAIN_VIEW> getCtnItems(String ctnBaco,Connection conn) throws Exception
	{
		List<ITM_MAIN_VIEW> result = new ArrayList<ITM_MAIN_VIEW>();
		ITM_MAIN_VIEW tempItm = null;
		StringBuilder sb = new StringBuilder();
		
		CTN_MAIN_VIEW mainCtn = getCtnByBaco(ctnBaco,conn);
		if(mainCtn==null||StringUtils.isEmpty(mainCtn.getCtn_main_guid()))
		{
			throw new Exception("未找到条码信息："+ctnBaco);
		}
		
		switch(mainCtn.getCtn_type())
		{
			case 10:
			case 12:
				/*
				 SELECT T2.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_SPEC,T2.ITM_UNIT,SUM(T1.ITM_QTY) FROM CTN_MAIN T1
					JOIN ITM_MAIN T2 ON T1.ITM_ID=T2.ITM_MAIN_ID 
					WHERE T1.CTN_BACO=? AND 
					GROUP BY T2.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_SPEC,T2.ITM_UNIT 
					ORDER BY T2.ITM_MAIN_ID
				 * */
				sb.append("SELECT T2.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_SPEC,T2.ITM_UNIT,SUM(T1.ITM_QTY) FROM CTN_MAIN T1 JOIN ITM_MAIN T2 ON T1.ITM_ID=T2.ITM_MAIN_ID WHERE T1.CTN_BACO=? GROUP BY T2.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_SPEC,T2.ITM_UNIT ORDER BY T2.ITM_MAIN_ID");
				break;
			case 11:
				/*
				 SELECT T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT,SUM(T2.ITM_QTY) FROM CTN_MAIN T1
					JOIN CTN_MAIN T2 ON T2.WH_PACKAGE_GUID=T1.CTN_MAIN_GUID AND T2.CTN_TYPE=111
					JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID 
					WHERE T1.CTN_BACO=? 
					GROUP BY T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT 
					ORDER BY T3.ITM_MAIN_ID
				 * */
				sb.append("SELECT T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT,SUM(T2.ITM_QTY) FROM CTN_MAIN T1 JOIN CTN_MAIN T2 ON T2.WH_PACKAGE_GUID=T1.CTN_MAIN_GUID AND T2.CTN_TYPE=111 JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID WHERE T1.CTN_BACO=? GROUP BY T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT ORDER BY T3.ITM_MAIN_ID");
				break;
			case 13:
				/*
				 SELECT T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT,SUM(T2.ITM_QTY) FROM CTN_MAIN T1
					JOIN CTN_MAIN T2 ON T2.WH_PACKAGE_GUID=T1.CTN_MAIN_GUID AND T2.CTN_TYPE=12
					JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID 
					WHERE T1.CTN_BACO=? 
					GROUP BY T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT 
					ORDER BY T3.ITM_MAIN_ID
				 * */
				sb.append("SELECT T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT,SUM(T2.ITM_QTY) FROM CTN_MAIN T1 JOIN CTN_MAIN T2 ON T2.WH_PACKAGE_GUID=T1.CTN_MAIN_GUID AND T2.CTN_TYPE=12 JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID WHERE T1.CTN_BACO=? GROUP BY T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT ORDER BY T3.ITM_MAIN_ID");
				break;
			case 14:
				/*
				 SELECT T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT,SUM(T2.ITM_QTY) FROM CTN_MAIN T1
					JOIN CTN_MAIN T2 ON T2.WH_PLT_GUID=T1.CTN_MAIN_GUID AND T2.CTN_TYPE IN (12,111)
					JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID 
					WHERE T1.CTN_BACO=? 
					GROUP BY T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT 
					ORDER BY T3.ITM_MAIN_ID
				 */
				sb.append("SELECT T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT,SUM(T2.ITM_QTY) FROM CTN_MAIN T1 JOIN CTN_MAIN T2 ON T2.WH_PLT_GUID=T1.CTN_MAIN_GUID AND T2.CTN_TYPE IN (12,111) JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID WHERE T1.CTN_BACO=? GROUP BY T3.ITM_MAIN_ID,T3.ITM_NAME,T3.ITM_SPEC,T3.ITM_UNIT ORDER BY T3.ITM_MAIN_ID");
				break;
			default:
				return result;
		}

		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, ctnBaco);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			tempItm = new ITM_MAIN_VIEW();
			
			tempItm.setItm_main_id(rs.getString(1));
			tempItm.setItm_name(rs.getString(2));
			tempItm.setItm_spec(rs.getString(3));
			tempItm.setItm_unit(rs.getString(4));
			tempItm.setItm_qty(rs.getBigDecimal(5));
			
			result.add(tempItm);
		}
		
		return result;
	}
	
	public static void RunTask( String taskId,long runDt,Connection conn) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("UPDATE TASK_MAIN SET IS_FINISHED=0,RUN_DT=? WHERE TASK_MAIN_ID=?");
		ps.setLong(1, runDt);
		ps.setString(2, taskId);
		ps.execute();
		ps.close();
	}
}
