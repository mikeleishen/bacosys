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

import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.label.domain.entities.CTN_MAIN;
import com.xinyou.label.domain.entities.ITM_MAIN;
import com.xinyou.label.domain.entities.RBA_CTN_RE;
import com.xinyou.label.domain.models.TRAN_DOC;
import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;
import com.xinyou.label.domain.viewes.ITM_MAIN_VIEW;

public class InvBasic_Biz {
	
	/**
	 * 根据仓库名称和仓库id获取仓库列表
	 * @param ctn_name   仓库名称（模糊查询）
	 * @param ctn_main_id 参考id （精确查询）
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static List<CTN_MAIN_VIEW> getWhs(String ctn_name,String ctn_main_id, Connection conn)throws Exception {
		List<CTN_MAIN_VIEW> returnList = new ArrayList<CTN_MAIN_VIEW>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int iParamIndex=0;
		
		try {
			String sSQL = "SELECT CTN_MAIN_GUID,CTN_MAIN_ID,CTN_NAME,CTN_BACO FROM CTN_MAIN WHERE CTN_TYPE=3 ";
			if (ctn_name != null && !ctn_name.isEmpty()) {
				sSQL += " AND CTN_NAME LIKE ?";
			}
			
			if(ctn_main_id!=null && !ctn_main_id.isEmpty()){
				sSQL+=" AND CTN_MAIN_ID = ? ";
			}
			
			pstmt = conn.prepareStatement(sSQL + " ORDER BY CTN_MAIN_ID ");

			if (ctn_name != null && !ctn_name.isEmpty()) {
				iParamIndex++;
				pstmt.setString(iParamIndex, "%" + ctn_name + "%");
			}
			
			if(ctn_main_id!=null && !ctn_main_id.isEmpty()){
				iParamIndex++;
				pstmt.setString(iParamIndex, ctn_main_id);
			}
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CTN_MAIN_VIEW entity = new CTN_MAIN_VIEW();
				entity.setCtn_main_guid(rs.getString(1));
				entity.setCtn_main_id(rs.getString(2));
				entity.setCtn_name(rs.getString(3));
				entity.setCtn_baco(rs.getString(4));
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnList;
	}

	/**
	 * 更新仓库信息，包括：仓库id，仓库名称及仓库条码 
	 * @param ctn
	 * @param operator
	 * @param data_ver
	 * @param conn
	 * @throws Exception
	 */
	public static void updateWh(CTN_MAIN ctn, String operator, String data_ver,  Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try{
			
			//判断条码是否已经使用 
			if(Common_Biz.bacoIsUsedForUpd(ctn.getCtn_baco(), ctn.getCtn_main_guid(), conn))
			{
				throw new Exception("条码("+ctn.getCtn_baco()+")已使用，请更换条码！");
			}
			
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,CTN_NAME=?,CTN_BACO=? WHERE CTN_MAIN_ID=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setString(3, ctn.getCtn_name());
			pstmt.setString(4, ctn.getCtn_baco());
			pstmt.setString(5, ctn.getCtn_main_id());
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 更新库区信息 
	 * @param ctn
	 * @param operator
	 * @param data_ver
	 * @param conn
	 * @throws Exception
	 */
	public void updateWhArea(CTN_MAIN ctn, String operator,String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			if(Common_Biz.bacoIsUsedForUpd(ctn.getCtn_baco(), ctn.getCtn_main_guid(), conn))
			{
				throw new Exception("条码("+ctn.getCtn_baco()+")已使用，请更换条码！");
			}
			
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,CTN_NAME=?,CTN_BACO=? WHERE CTN_MAIN_GUID=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setString(3, ctn.getCtn_name());
			pstmt.setString(4, ctn.getCtn_baco());
			pstmt.setString(5, ctn.getCtn_main_guid());
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}

	/**
	 * 获取仓库 库区列表 
	 * @param wh_id
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static List<CTN_MAIN_VIEW> getCtnAreas(String wh_id, Connection conn) throws Exception {
		List<CTN_MAIN_VIEW> returnList = new ArrayList<CTN_MAIN_VIEW>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sSQL = "SELECT T.CTN_MAIN_GUID, T.CTN_MAIN_ID, T.CTN_NAME, T.CTN_BACO, T1.CTN_MAIN_ID AS WH_ID, T1.CTN_NAME AS WH_NAME FROM CTN_MAIN T JOIN CTN_MAIN T1 ON T.WH_GUID=T1.CTN_MAIN_GUID ";
			String subSQLWhere = " WHERE T.CTN_TYPE=4";
			
			if (wh_id != null && !wh_id.isEmpty()) {
				subSQLWhere += " AND T1.CTN_MAIN_ID=?";
			}
			sSQL = sSQL + subSQLWhere;
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			if (wh_id != null && !wh_id.isEmpty()) {
				pstmt.setString(++index, wh_id);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CTN_MAIN_VIEW entity = new CTN_MAIN_VIEW();
				entity.setCtn_main_guid(rs.getString(1));
				entity.setCtn_main_id(rs.getString(2));
				entity.setCtn_name(rs.getString(3));
				entity.setCtn_baco(rs.getString(4));
				entity.setWh_id(rs.getString(5));
				entity.setWh_name(rs.getString(6));
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnList;
	}

	/**
	 * 更新货架信息
	 * @param ctn
	 * @param operator
	 * @param client
	 * @param data_ver
	 * @param conn
	 * @throws Exception
	 */
	public void updateWhShelf(CTN_MAIN ctn, String operator,
			String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			if(Common_Biz.bacoIsUsedForUpd(ctn.getCtn_baco(), ctn.getCtn_main_guid(), conn))
			{
				throw new Exception("货架条码 "+ ctn.getCtn_baco()+ " 已使用！");
			}
			
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,CTN_BACO=?,WH_AREA_GUID=? WHERE CTN_MAIN_GUID=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setString(3, ctn.getCtn_baco());
			pstmt.setString(4, ctn.getWh_area_guid());
			pstmt.setString(5, ctn.getCtn_main_guid());
			pstmt.execute();
			pstmt.close();
			
			Common_Biz.updCtnStatus(ctn.getParent_ctn_guid(), conn);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}
	
	/**
	 * 获取仓库某一库区的货架信息列表 
	 * @param wh_id    仓库id
	 * @param area_id  库区id
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static List<CTN_MAIN_VIEW> getCtnShelfs(String wh_id, String area_id, Connection conn) throws Exception {
		List<CTN_MAIN_VIEW> returnList = new ArrayList<CTN_MAIN_VIEW>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 0;
		
		try {
			String sSQL = "SELECT T.CTN_MAIN_GUID, T.CTN_MAIN_ID, T.CTN_NAME, T.CTN_BACO, T1.CTN_MAIN_ID AS AREA_ID, T2.CTN_MAIN_ID AS WH_ID, T2.CTN_NAME AS WH_NAME FROM CTN_MAIN T LEFT JOIN CTN_MAIN T1 ON T.WH_AREA_GUID=T1.CTN_MAIN_GUID JOIN CTN_MAIN T2 ON T.WH_GUID=T2.CTN_MAIN_GUID";
			String subSQLWhere = " WHERE T.CTN_TYPE=5";
			
			if (wh_id != null && !wh_id.isEmpty()) {
				subSQLWhere += " AND T2.CTN_MAIN_ID=?";
			}
			if (area_id != null && !area_id.isEmpty()) {
				subSQLWhere += " AND T1.CTN_MAIN_ID=?";
			}
			sSQL = sSQL + subSQLWhere;
			
			pstmt = conn.prepareStatement(sSQL);
			if (wh_id != null && !wh_id.isEmpty()) {
				pstmt.setString(++index, wh_id);
			}
			if (area_id != null && !area_id.isEmpty()) {
				pstmt.setString(++index, area_id);
			}
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CTN_MAIN_VIEW entity = new CTN_MAIN_VIEW();
				entity.setCtn_main_guid(rs.getString(1));
				entity.setCtn_main_id(rs.getString(2));
				entity.setCtn_name(rs.getString(3));
				entity.setCtn_baco(rs.getString(4));
				entity.setWh_area_id(rs.getString(5));
				entity.setWh_id(rs.getString(6));
				entity.setWh_name(rs.getString(7));
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnList;
	}
	
	/**
	 * 更新库位信息 
	 * @param ctn
	 * @param operator
	 * @param client
	 * @param data_ver
	 * @param conn
	 * @throws Exception
	 */
	public void updateCtnLoc(CTN_MAIN ctn, String operator,
			String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			if(Common_Biz.bacoIsUsedForUpd(ctn.getCtn_baco(),ctn.getCtn_main_guid(), conn))
			{
				throw new Exception("库位条码 "+ ctn.getCtn_baco()+ " 已使用！");
			}
			
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,CTN_BACO=? WHERE CTN_MAIN_GUID=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setString(3, ctn.getCtn_baco());
			pstmt.setString(4, ctn.getCtn_main_guid());
			pstmt.execute();
			pstmt.close();
			
			Common_Biz.updCtnStatus(ctn.getParent_ctn_guid(), conn);
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}
	
	/**
	 * 获取库位信息列表 
	 * @param wh_id    仓库id
	 * @param area_id  仓库库区id
	 * @param shelf_id 货架id
	 * @param page_no   
	 * @param page_size
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getCtnLocs(String wh_id, String area_id, String shelf_id, int page_no, int page_size, Connection conn) throws Exception
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
	
		EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW> returnDM = new EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		List<CTN_MAIN_VIEW> returnList = new ArrayList<CTN_MAIN_VIEW>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 0;
		
		try {
			String subSQL = "SELECT T.CTN_MAIN_GUID, T.CTN_MAIN_ID, T.CTN_BACO, T1.CTN_MAIN_ID AS WH_SHELF_ID, T2.CTN_MAIN_ID AS WH_AREA_ID, T3.CTN_MAIN_ID AS WH_ID, T3.CTN_NAME AS WH_NAME FROM CTN_MAIN T LEFT JOIN CTN_MAIN T1 ON T.WH_SHELF_GUID=T1.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T2 ON T.WH_AREA_GUID=T2.CTN_MAIN_GUID JOIN CTN_MAIN T3 ON T.WH_GUID=T3.CTN_MAIN_GUID";
			String subSQLWhere = " WHERE T.CTN_TYPE=6";
			String subOrderby = " ORDER BY WH_ID, WH_AREA_ID, WH_SHELF_ID, CTN_BACO";
			
			if (wh_id != null && !wh_id.isEmpty()) {
				subSQLWhere += " AND T3.CTN_MAIN_ID=?";
			}
			if (shelf_id != null && !shelf_id.isEmpty()) {
				subSQLWhere += " AND T1.CTN_MAIN_ID=?";
			}
			if (area_id != null && !area_id.isEmpty()) {
				subSQLWhere += " AND T2.CTN_MAIN_ID=?";
			}
			subSQL = subSQL + subSQLWhere;
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			if (wh_id != null && !wh_id.isEmpty()) {
				pstmt.setString(++index, wh_id);
			}
			if (shelf_id != null && !shelf_id.isEmpty()) {
				pstmt.setString(++index, shelf_id);
			}
			if (area_id != null && !area_id.isEmpty()) {
				pstmt.setString(++index, area_id);
			}
			
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CTN_MAIN_VIEW entity = new CTN_MAIN_VIEW();
				entity.setCtn_main_guid(rs.getString(1));
				entity.setCtn_main_id(rs.getString(2));
				entity.setCtn_baco(rs.getString(3));
				entity.setWh_shelf_id(rs.getString(4));
				entity.setWh_area_id(rs.getString(5));
				entity.setWh_id(rs.getString(6));
				entity.setWh_name(rs.getString(7));
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			returnDM.setDataList(returnList);
	
			subSQL = "SELECT COUNT(*) FROM CTN_MAIN T LEFT JOIN CTN_MAIN T1 ON T.WH_SHELF_GUID=T1.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T2 ON T.WH_AREA_GUID=T2.CTN_MAIN_GUID JOIN CTN_MAIN T3 ON T.WH_GUID=T3.CTN_MAIN_GUID";
			subSQL = subSQL + subSQLWhere;
			pstmt = conn.prepareStatement(subSQL);
			index = 0;
			if (wh_id != null && !wh_id.isEmpty()) {
				pstmt.setString(++index, wh_id);
			}
			if (shelf_id != null && !shelf_id.isEmpty()) {
				pstmt.setString(++index, shelf_id);
			}
			if (area_id != null && !area_id.isEmpty()) {
				pstmt.setString(++index, area_id);
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returnDM.setCount(rs.getInt(1));
			} else {
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}
	
	/**
	 * TODO Mike 获取托盘信息 ？？ CTN_TYPE = 14???  
	 * @param plt_id
	 * @param page_no
	 * @param page_size
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getPlts(String plt_id, int page_no, int page_size,Connection conn) throws Exception {
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
	
		EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW> returnDM = new EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		List<CTN_MAIN_VIEW> returnList = new ArrayList<CTN_MAIN_VIEW>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String subSQL = "SELECT CTN_MAIN_GUID, CTN_MAIN_ID, CTN_BACO, CTN_TYPE FROM CTN_MAIN";
			String subSQLWhere = " WHERE CTN_TYPE=14";
			String subOrderby = " ORDER BY CTN_MAIN_ID";
			if (plt_id != null && !plt_id.isEmpty()) {
				subSQLWhere += " AND CTN_MAIN_ID=?";
			}
			subSQL = subSQL + subSQLWhere;
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if (plt_id != null && !plt_id.isEmpty()) {
				pstmt.setString(++index, plt_id);
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CTN_MAIN_VIEW entity = new CTN_MAIN_VIEW();
				entity.setCtn_main_guid(rs.getString(1));
				entity.setCtn_main_id(rs.getString(2));
				entity.setCtn_baco(rs.getString(3));
				entity.setCtn_type(rs.getInt(4));
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			returnDM.setDataList(returnList);
	
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM CTN_MAIN"+ subSQLWhere);
			index = 0;
			if (plt_id != null && !plt_id.isEmpty()) {
				pstmt.setString(++index, plt_id);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returnDM.setCount(rs.getInt(1));
			} else {
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}
	
	public void updatePlt(CTN_MAIN_VIEW ctn_view, String operator, String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try {
			if(!Common_Biz.bacoIsUsedForUpd(ctn_view.getCtn_baco(),ctn_view.getCtn_main_guid(), conn))
			{
				pstmt = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?, CTN_BACO=?,CTN_TYPE=? WHERE CTN_MAIN_GUID=?");
				pstmt.setLong(1, new Date().getTime());
				pstmt.setString(2, operator);
				pstmt.setString(3, ctn_view.getCtn_baco());
				pstmt.setInt(4, ctn_view.getCtn_type());
				pstmt.setString(5, ctn_view.getCtn_main_guid());
				pstmt.execute();
				pstmt.close();
			}
			else
			{
				throw new Exception("条码:"+ctn_view.getCtn_baco()+" 已经被使用过！");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}

	/**
	 *  TODO Mike Shen 
	 * @param wh_id
	 * @param area_id
	 * @param shelf_id
	 * @param loc_id
	 * @param ctn_box_id
	 * @param itm_id
	 * @param page_no
	 * @param page_size
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getCtnBoxs(String wh_id, String area_id, String shelf_id,String loc_id,String ctn_box_id, String itm_id, 
			int page_no, int page_size,Connection conn) throws Exception {
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
	
		EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW> returnDM = new EntityListDM<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		List<CTN_MAIN_VIEW> returnList = new ArrayList<CTN_MAIN_VIEW>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String subSQL = "SELECT T.CTN_MAIN_GUID, T.CTN_MAIN_ID, T.CTN_NAME, T.CTN_BACO, T.ITM_ID, T1.ITM_NAME, T1.ITM_SPEC, T1.ITM_UNIT, T2.CTN_MAIN_ID AS WH_LOC_ID,T3.CTN_MAIN_ID AS WH_SHELF_ID, T4.CTN_MAIN_ID AS WH_AREA_ID, T5.CTN_MAIN_ID AS WH_ID, T5.CTN_NAME AS WH_NAME,T.ITM_QTY FROM CTN_MAIN T LEFT JOIN ITM_MAIN T1 ON T1.ITM_MAIN_ID=T.ITM_ID LEFT JOIN CTN_MAIN T2 ON T.WH_LOC_GUID=T2.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T3 ON T.WH_SHELF_GUID=T3.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T4 ON T.WH_AREA_GUID=T4.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T5 ON T.WH_GUID=T5.CTN_MAIN_GUID";
			String subSQLWhere = " WHERE T.CTN_TYPE IN(10,11) ";
			
			if (ctn_box_id != null && !ctn_box_id.isEmpty()) {
				subSQLWhere += " AND T.CTN_MAIN_ID=?";
			}
			if (itm_id != null && !itm_id.isEmpty()) {
				subSQLWhere += " AND T.ITM_ID=?";
			}
			if (wh_id != null && !wh_id.isEmpty()) {
				subSQLWhere += " AND T5.CTN_MAIN_ID=?";
			}
			if (area_id != null && !area_id.isEmpty()) {
				subSQLWhere += " AND T4.CTN_MAIN_ID=?";
			}
			if (shelf_id != null && !shelf_id.isEmpty()) {
				subSQLWhere += " AND T3.CTN_MAIN_ID=?";
			}
			if (loc_id != null && !loc_id.isEmpty()) {
				subSQLWhere += " AND T2.CTN_MAIN_ID=?";
			}
			
			subSQL = subSQL + subSQLWhere;
			String subOrderby = " ORDER BY CTN_MAIN_ID";
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			if (ctn_box_id != null && !ctn_box_id.isEmpty()) {
				pstmt.setString(++index, ctn_box_id);
			}
			if (itm_id != null && !itm_id.isEmpty()) {
				pstmt.setString(++index, itm_id);
			}
			if (wh_id != null && !wh_id.isEmpty()) {
				pstmt.setString(++index, wh_id);
			}
			if (area_id != null && !area_id.isEmpty()) {
				pstmt.setString(++index, area_id);
			}
			if (shelf_id != null && !shelf_id.isEmpty()) {
				pstmt.setString(++index, shelf_id);
			}
			if (loc_id != null && !loc_id.isEmpty()) {
				pstmt.setString(++index, loc_id);
			}
			
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CTN_MAIN_VIEW entity = new CTN_MAIN_VIEW();
				entity.setCtn_main_guid(rs.getString(1));
				entity.setCtn_main_id(rs.getString(2));
				entity.setCtn_name(rs.getString(3));
				entity.setCtn_baco(rs.getString(4));
				entity.setItm_id(rs.getString(5));
				entity.setItm_name(rs.getString(6));
				entity.setItm_spec(rs.getString(7));
				entity.setItm_unit(rs.getString(8));
				entity.setWh_loc_id(rs.getString(9));
				entity.setWh_shelf_id(rs.getString(10));
				entity.setWh_area_id(rs.getString(11));
				entity.setWh_id(rs.getString(12));
				entity.setWh_name(rs.getString(13));
				entity.setItm_qty(rs.getBigDecimal(14));
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			returnDM.setDataList(returnList);
	
			subSQL = "SELECT COUNT(*) FROM CTN_MAIN T LEFT JOIN ITM_MAIN T1 ON T1.ITM_MAIN_ID=T.ITM_ID LEFT JOIN CTN_MAIN T2 ON T.WH_LOC_GUID=T2.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T3 ON T.WH_SHELF_GUID=T3.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T4 ON T.WH_AREA_GUID=T4.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T5 ON T.WH_GUID=T5.CTN_MAIN_GUID ";
			
			pstmt = conn.prepareStatement(subSQL+subSQLWhere);
			index = 0;
			if (ctn_box_id != null && !ctn_box_id.isEmpty()) {
				pstmt.setString(++index, ctn_box_id);
			}
			if (itm_id != null && !itm_id.isEmpty()) {
				pstmt.setString(++index, itm_id);
			}
			if (wh_id != null && !wh_id.isEmpty()) {
				pstmt.setString(++index, wh_id);
			}
			if (area_id != null && !area_id.isEmpty()) {
				pstmt.setString(++index, area_id);
			}
			if (shelf_id != null && !shelf_id.isEmpty()) {
				pstmt.setString(++index, shelf_id);
			}
			if (loc_id != null && !loc_id.isEmpty()) {
				pstmt.setString(++index, loc_id);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returnDM.setCount(rs.getInt(1));
			} else {
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}

	/**
	 * 周转箱必须放在库位上，可以更新条码和位置
	 * @param ctn_mod
	 * @param operator
	 * @param client
	 * @param data_ver
	 * @param conn
	 * @throws Exception
	 */
	public static void updateCtnBox(CTN_MAIN ctn_mod, String operator, String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			if(!Common_Biz.bacoIsUsedForUpd(ctn_mod.getCtn_baco(), ctn_mod.getCtn_main_guid(), conn))
			{
				CTN_MAIN_VIEW locView = Common_Biz.getCtnByGuid(ctn_mod.getParent_ctn_guid(), conn);
				
				pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?, CTN_BACO=?, WH_GUID=?, WH_AREA_GUID=?, WH_SHELF_GUID=?,WH_LOC_GUID=? WHERE CTN_MAIN_GUID=?");
				pstmt.setLong(1, new Date().getTime());
				pstmt.setString(2, operator);
				pstmt.setString(3, ctn_mod.getCtn_baco());
				pstmt.setString(4, locView.getWh_guid());
				pstmt.setString(5, locView.getWh_area_guid());
				pstmt.setString(6, locView.getWh_shelf_guid());
				pstmt.setString(7, locView.getCtn_main_guid());
				pstmt.setString(8, ctn_mod.getCtn_main_guid());
				pstmt.execute();
				pstmt.close();
				
				Common_Biz.updCtnStatus(locView.getCtn_main_guid(), conn);
			}
			else
			{
				throw new Exception("条码:"+ctn_mod.getCtn_baco()+" 已经被使用过！");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}

	/**
	 * 获取物料代码获取物料信息 
	 * @param itmId  物料代码 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static ITM_MAIN getItmById(String itmId, Connection conn) throws Exception
	{
		ITM_MAIN entity = new ITM_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT ITM_MAIN_GUID, ITM_MAIN_ID, ITM_NAME, ITM_SPEC, ITM_UNIT, ITM_DFT_INV, ITM_SAFE_QTY, ITM_SN,DEF_LOC_ID FROM ITM_MAIN WHERE ITM_MAIN_ID=?");
			pstmt.setString(1, itmId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				entity.setItm_main_guid(rs.getString(1));
				entity.setItm_main_id(rs.getString(2));
				entity.setItm_name(rs.getString(3));
				entity.setItm_spec(rs.getString(4));
				entity.setItm_unit(rs.getString(5));
				entity.setItm_dft_inv(rs.getString(6));
				entity.setItm_safe_qty(rs.getBigDecimal(7));
				entity.setItm_sn(rs.getString(8));
				entity.setDef_loc_id(rs.getString(9));
			}
			rs.close();
			pstmt.close();
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return entity;
	}
	
	/**
	 * 根据物料SN 获取物料详细信息 
	 * @param itmSn
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public ITM_MAIN getItmBySn(String itmSn, Connection conn) throws Exception
	{
		ITM_MAIN entity = new ITM_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT ITM_MAIN_GUID, ITM_MAIN_ID, ITM_NAME, ITM_SPEC, ITM_UNIT, ITM_DFT_INV, ITM_SAFE_QTY, ITM_SN FROM ITM_MAIN WHERE ITM_SN=?");
			pstmt.setString(1, itmSn);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				entity.setItm_main_guid(rs.getString(1));
				entity.setItm_main_id(rs.getString(2));
				entity.setItm_name(rs.getString(3));
				entity.setItm_spec(rs.getString(4));
				entity.setItm_unit(rs.getString(5));
				entity.setItm_dft_inv(rs.getString(6));
				entity.setItm_safe_qty(rs.getBigDecimal(7));
				entity.setItm_sn(rs.getString(8));
			}
			rs.close();
			pstmt.close();
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return entity;
	}
	
	/** 
	 *  TODO 
	 * @param doc
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String AddTran(TRAN_DOC doc,Connection conn) throws Exception
	{
		PreparedStatement pstmtInsertHead = null;
		PreparedStatement pstmtInsertBodyItm = null;
		PreparedStatement pstmtInsertBodyBaco = null;
		PreparedStatement pstmtInsertDocId = null;
		String headGuid = UUID.randomUUID().toString();
		
		pstmtInsertHead = conn.prepareStatement("INSERT INTO TRAN_MAIN(TRAN_MAIN_GUID,TRAN_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,TRAN_TYPE,TRAN_REASON_ID,NEED_SYN,IS_SYNED,BASE_DOC_TYPE,BASE_DOC_ID,SYN_DOC_TYPE,SYN_DOC_ID,WH_ID,IN_OUT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		//TRAN_MAIN_GUID,TRAN_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER
		pstmtInsertHead.setString(1, headGuid);
		pstmtInsertHead.setString(2, headGuid);
		pstmtInsertHead.setLong(3, new Date().getTime());
		pstmtInsertHead.setString(4, doc.getHead().getCreated_by());
		pstmtInsertHead.setLong(5, new Date().getTime());
		pstmtInsertHead.setString(6, doc.getHead().getUpdated_by());
		pstmtInsertHead.setString(7, doc.getHead().getClient_guid());
		pstmtInsertHead.setInt(8, doc.getHead().getIs_deleted());
		pstmtInsertHead.setString(9, doc.getHead().getData_ver());
			
		//TRAN_TYPE,TRAN_REASON_ID,NEED_SYN,IS_SYNED,BASE_DOC_TYPE,BASE_DOC_ID
		pstmtInsertHead.setInt(10, doc.getHead().getTran_type());
		pstmtInsertHead.setString(11, doc.getHead().getTran_reason_id());
		pstmtInsertHead.setInt(12, doc.getHead().getNeed_syn());
		pstmtInsertHead.setInt(13, doc.getHead().getIs_syned());
		pstmtInsertHead.setInt(14, doc.getHead().getBase_doc_type());
		pstmtInsertHead.setString(15, doc.getHead().getBase_doc_id());
		pstmtInsertHead.setInt(16, doc.getHead().getSyn_doc_type());
		pstmtInsertHead.setString(17, doc.getHead().getSyn_doc_id());
		pstmtInsertHead.setString(18, doc.getHead().getWh_id());
		pstmtInsertHead.setInt(19, doc.getHead().getIn_out());
		
		pstmtInsertHead.execute();
		pstmtInsertHead.close();
		pstmtInsertHead = null;
			
		String bodyItmGuid = "";
		for(int i=0;i<doc.getBody_itm().size();i++)
		{
			bodyItmGuid = UUID.randomUUID().toString();
			
			pstmtInsertBodyItm = conn.prepareStatement("	INSERT INTO TRAN_ITM(TRAN_ITM_GUID,TRAN_ITM_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,ITM_ID,ITM_QTY,TRAN_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmtInsertBodyItm.setString(1, bodyItmGuid);
			pstmtInsertBodyItm.setString(2, bodyItmGuid);
			pstmtInsertBodyItm.setLong(3, new Date().getTime());
			pstmtInsertBodyItm.setString(4, doc.getBody_itm().get(i).getCreated_by());
			pstmtInsertBodyItm.setLong(5, new Date().getTime());
			pstmtInsertBodyItm.setString(6, doc.getBody_itm().get(i).getUpdated_by());
			pstmtInsertBodyItm.setString(7, doc.getBody_itm().get(i).getClient_guid());
			pstmtInsertBodyItm.setInt(8, doc.getBody_itm().get(i).getIs_deleted());
			pstmtInsertBodyItm.setString(9, doc.getBody_itm().get(i).getData_ver());
			
			pstmtInsertBodyItm.setString(10, doc.getBody_itm().get(i).getItm_id());
			pstmtInsertBodyItm.setBigDecimal(11, doc.getBody_itm().get(i).getItm_qty());
			pstmtInsertBodyItm.setString(12, headGuid);
			
			pstmtInsertBodyItm.execute();
			pstmtInsertBodyItm.close();
		}
			
		String bodyBacoGuid = "";
		for(int i=0;i<doc.getBody_baco().size();i++)
		{
			bodyBacoGuid = UUID.randomUUID().toString();
			
			pstmtInsertBodyBaco = conn.prepareStatement("	INSERT INTO TRAN_BACO(TRAN_BACO_GUID,TRAN_BACO_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,CTN_BACO,TRAN_QTY,TRAN_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmtInsertBodyBaco.setString(1, bodyBacoGuid);
			pstmtInsertBodyBaco.setString(2, bodyBacoGuid);
			pstmtInsertBodyBaco.setLong(3, new Date().getTime());
			pstmtInsertBodyBaco.setString(4, doc.getBody_baco().get(i).getCreated_by());
			pstmtInsertBodyBaco.setLong(5, new Date().getTime());
			pstmtInsertBodyBaco.setString(6, doc.getBody_baco().get(i).getUpdated_by());
			pstmtInsertBodyBaco.setString(7, doc.getBody_baco().get(i).getClient_guid());
			pstmtInsertBodyBaco.setInt(8, doc.getBody_baco().get(i).getIs_deleted());
			pstmtInsertBodyBaco.setString(9, doc.getBody_baco().get(i).getData_ver());
			
			pstmtInsertBodyBaco.setString(10, doc.getBody_baco().get(i).getCtn_baco());
			pstmtInsertBodyBaco.setBigDecimal(11, doc.getBody_baco().get(i).getTran_qty());
			pstmtInsertBodyBaco.setString(12, headGuid);
			//pstmtInsertBodyBaco.setString(13, doc.getBody_baco().get(i).getTo_ctn_baco());
			
			pstmtInsertBodyBaco.execute();
			pstmtInsertBodyBaco.close();
		}
		
		String docBaseGuid = "";
		if(doc.getDoc_base()!=null){
			for(int i=0;i<doc.getDoc_base().size();i++)
			{
				docBaseGuid = UUID.randomUUID().toString();
				
				pstmtInsertDocId = conn.prepareStatement("	INSERT INTO TRAN_BASE_DOC(TRAN_BASE_DOC_GUID,TRAN_BASE_DOC_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,TRAN_GUID,BASE_DOC_TYPE,BASE_DOC_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmtInsertDocId.setString(1, docBaseGuid);
				pstmtInsertDocId.setString(2, docBaseGuid);
				pstmtInsertDocId.setLong(3, new Date().getTime());
				pstmtInsertDocId.setString(4, doc.getBody_baco().get(i).getCreated_by());
				pstmtInsertDocId.setLong(5, new Date().getTime());
				pstmtInsertDocId.setString(6, doc.getBody_baco().get(i).getUpdated_by());
				pstmtInsertDocId.setString(7, doc.getBody_baco().get(i).getClient_guid());
				pstmtInsertDocId.setInt(8, doc.getBody_baco().get(i).getIs_deleted());
				pstmtInsertDocId.setString(9, doc.getBody_baco().get(i).getData_ver());
				
				pstmtInsertDocId.setString(10, headGuid);
				pstmtInsertDocId.setInt(11, doc.getHead().getBase_doc_type());
				pstmtInsertDocId.setString(12, doc.getDoc_base().get(i).getBase_doc_id());
	
				pstmtInsertDocId.execute();
				pstmtInsertDocId.close();
				
				
				try{
					//备份领料单，方便日后查询
					if(doc.getDoc_base().get(i).getBase_doc_type()==20){
						PreparedStatement ps=conn.prepareStatement("INSERT INTO RBA_DOC_BACK(RBA_DOC_BACK_GUID,RBA_DOC_ID,WO_DOC_ID,LABEL_STATUS,RBA_STATUS,RBA_DOC_REMARK,RBA_DOC_DT,ERP_EMP_ID,TRAN_BASE_DOC_GUID) "
								+ " SELECT NEWID(),RBA_DOC_ID,WO_DOC_ID,LABEL_STATUS,RBA_STATUS,RBA_DOC_REMARK,RBA_DOC_DT,ERP_EMP_ID,? FROM RBA_DOC WHERE RBA_DOC_ID=?");
						ps.setString(1, docBaseGuid);
						ps.setString(2, doc.getDoc_base().get(i).getBase_doc_id());
						ps.execute();
						ps.close();
						
						ps=conn.prepareStatement("INSERT INTO RBA_ITM_BACK(RBA_ITM_BACK_GUID,RBA_ITM_SEQNO,RBA_DOC_ID,ITM_ID,ITM_QTY,INV_ID,TRAN_BASE_DOC_GUID) "
								+ " SELECT NEWID(),RBA_ITM_SEQNO ,RBA_DOC_ID,ITM_ID,ITM_QTY,INV_ID,? FROM RBA_ITM WHERE RBA_DOC_ID=?");
						ps.setString(1, docBaseGuid);
						ps.setString(2, doc.getDoc_base().get(i).getBase_doc_id());
						ps.execute();
						ps.close();
					}
				}catch(Exception e){
					
				}
			}
		}
		return headGuid;
	}
	
	/**
	 * TODO 
	 * @param pkgBaco
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static List<ITM_MAIN_VIEW> getItmListByPkgBaco(String pkgBaco,Connection conn) throws Exception
	{
		List<ITM_MAIN_VIEW> resultList = new ArrayList<ITM_MAIN_VIEW>();
		CTN_MAIN_VIEW pkg = Common_Biz.getCtnByBaco(pkgBaco, conn);
		if(StringUtils.isEmpty(pkg.getCtn_main_guid())){
			throw new Exception("未找到包装条码！");
		}
		if(pkg.getCtn_type()!=20){
			throw new Exception("不是包装条码！");
		}
		
		PreparedStatement ps = conn.prepareStatement("SELECT T.ITM_ID,SUM(T.ITM_QTY),T1.ITM_NAME,T1.ITM_UNIT FROM CTN_MAIN T,ITM_MAIN T1 WHERE WH_PACKAGE_GUID=? AND T.CTN_TYPE=13 AND T.ITM_ID=T1.ITM_MAIN_ID GROUP BY T.ITM_ID,T.ITM_ID,T1.ITM_NAME,T1.ITM_UNIT ORDER BY T.ITM_ID");
		ps.setString(1, pkg.getCtn_main_guid());
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			ITM_MAIN_VIEW itm = new ITM_MAIN_VIEW();
			
			itm.setItm_main_id(rs.getString(1));
			itm.setItm_qty(rs.getBigDecimal(2));
			itm.setItm_name(rs.getString(3));
			itm.setItm_unit(rs.getString(4));
			
			resultList.add(itm);
		}

		return resultList;
	}
	
	/**
	 * TODO 
	 * @param bacoList
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static List<ITM_MAIN_VIEW> getItmListByBacoList(List<CTN_MAIN_VIEW> bacoList,Connection conn) throws Exception
	{
		List<ITM_MAIN_VIEW> itmList = new ArrayList<ITM_MAIN_VIEW>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		for(CTN_MAIN_VIEW ctn : bacoList)
		{
			CTN_MAIN_VIEW ctn2 = Common_Biz.getCtnByBaco(ctn.getCtn_baco(), conn);

			if(ctn.getCtn_type()==7){//托盘
				ps = conn.prepareStatement("SELECT T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT,SUM(T1.ITM_QTY) FROM CTN_MAIN T1,ITM_MAIN T2 WHERE T1.CTN_TYPE=13 AND T1.WH_PLT_GUID=? AND T1.ITM_ID=T2.ITM_MAIN_ID GROUP BY T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT ORDER BY T1.ITM_ID");
			}
			else if(ctn.getCtn_type()==20){//包装盒
				ps = conn.prepareStatement("SELECT T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT,SUM(T1.ITM_QTY) FROM CTN_MAIN T1,ITM_MAIN T2 WHERE T1.CTN_TYPE=13 AND T1.WH_PACKAGE_GUID=? AND T1.ITM_ID=T2.ITM_MAIN_ID GROUP BY T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT ORDER BY T1.ITM_ID");
			}
			else if(ctn.getCtn_type()==13){//合格证
				ps = conn.prepareStatement("SELECT T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT,"+ctn.getItm_qty()+" FROM CTN_MAIN T1,ITM_MAIN T2 WHERE T1.CTN_TYPE=13 AND T1.CTN_MAIN_GUID=? AND T1.ITM_ID=T2.ITM_MAIN_ID ORDER BY T1.ITM_ID");
			}
			
			ps.setString(1, ctn2.getCtn_main_guid());
			rs = ps.executeQuery();
			
			boolean isInList = false;
			while(rs.next()){
				for(ITM_MAIN_VIEW itm:itmList){
					if(itm.getItm_main_id().equals(rs.getString(1))){
						isInList = true;
						itm.setItm_qty(itm.getItm_qty().add(rs.getBigDecimal(4)));
						break;
					}
				}
				
				if(!isInList){
					ITM_MAIN_VIEW itm= new ITM_MAIN_VIEW();
					itm.setItm_main_id(rs.getString(1));
					itm.setItm_name(rs.getString(2));
					itm.setItm_unit(rs.getString(3));
					itm.setItm_qty(rs.getBigDecimal(4));
					itmList.add(itm);
				}
			}
		}
		
		return itmList;
	}
	
	/**
	 * 
	 * @param bacoList
	 * @param operator
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String doOutInv(List<CTN_MAIN_VIEW> bacoList,String operator,Connection conn) throws Exception
	{
		PreparedStatement ps = null;
		for(CTN_MAIN_VIEW ctn : bacoList)
		{
			if(ctn.getCtn_type()!=13){
				ps= conn.prepareStatement("UPDATE CTN_MAIN SET WH_GUID='',WH_AREA_GUID='',WH_SHELF_GUID='',WH_LOC_GUID='',UPDATED_DT=?,UPDATED_BY=? WHERE CTN_BACO=?");
				ps.setLong(1, new Date().getTime());
				ps.setString(2, operator);
				ps.setString(3, ctn.getCtn_baco());
				ps.execute();
				ps.close();
			}
			else{
				CTN_MAIN_VIEW tempctn = Common_Biz.getCtnByBaco(ctn.getCtn_baco(), conn);
				if(ctn.getItm_qty().compareTo(tempctn.getItm_qty())==0)	{
					ps= conn.prepareStatement("UPDATE CTN_MAIN SET WH_GUID='',WH_AREA_GUID='',WH_SHELF_GUID='',WH_LOC_GUID='',UPDATED_DT=?,UPDATED_BY=? WHERE CTN_BACO=?");
					ps.setLong(1, new Date().getTime());
					ps.setString(2, operator);
					ps.setString(3, ctn.getCtn_baco());
					ps.execute();
					ps.close();
				}
				else{
					ps= conn.prepareStatement("UPDATE CTN_MAIN SET ITM_QTY=ITM_QTY-?,UPDATED_DT=?,UPDATED_BY=? WHERE CTN_BACO=?");
					ps.setBigDecimal(1, ctn.getItm_qty());
					ps.setLong(2, new Date().getTime());
					ps.setString(3, operator);
					ps.setString(4, ctn.getCtn_baco());
					ps.execute();
					ps.close();
				}
			}
		}
		
		return "";
	}
	
	/**
	 * 
	 * @param itmId
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static List<CTN_MAIN_VIEW> getCtnListByItmId(String itmId,Connection conn) throws SQLException
	{
		List<CTN_MAIN_VIEW> result = new ArrayList<CTN_MAIN_VIEW>();
		
		PreparedStatement ps = conn.prepareStatement("SELECT T1.ITM_ID,T1.ITM_QTY,T1.CTN_BACO,T2.CTN_MAIN_ID,T3.CTN_MAIN_ID,T4.CTN_MAIN_ID,T5.CTN_MAIN_ID,T1.CTN_MAIN_ID,T1.CTN_TYPE FROM CTN_MAIN T1 JOIN CTN_MAIN T2 ON T1.WH_GUID = T2.CTN_MAIN_GUID JOIN CTN_MAIN T3 ON T1.WH_AREA_GUID = T3.CTN_MAIN_GUID LEFT JOIN CTN_MAIN T4 ON T1.WH_SHELF_GUID = T4.CTN_MAIN_GUID JOIN CTN_MAIN T5 ON T1.WH_LOC_GUID = T5.CTN_MAIN_GUID WHERE T1.CTN_TYPE>=10 AND T1.CTN_TYPE<=13 AND T1.ITM_QTY>0 AND T1.ITM_ID=? ORDER BY T1.CREATED_DT,T1.ITM_QTY ");
		ps.setString(1, itmId);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			CTN_MAIN_VIEW view = new CTN_MAIN_VIEW();
			view.setItm_id(rs.getString(1));
			view.setItm_qty(rs.getBigDecimal(2));
			view.setItm_got_qty(BigDecimal.ZERO);
			view.setCtn_baco(rs.getString(3));
			view.setWh_id(rs.getString(4));
			view.setWh_area_id(rs.getString(5));
			view.setWh_shelf_id(rs.getString(6));
			view.setWh_loc_id(rs.getString(7));
			view.setCtn_main_id(rs.getString(8));
			view.setCtn_type(rs.getInt(9));
			
			result.add(view);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String AddRbaCtnRe(RBA_CTN_RE entity,Connection conn) throws Exception{
		PreparedStatement pstmtInsert = null;
		try{
			String sql="INSERT INTO RBA_CTN_RE"
					+ "(RBA_CTN_RE_GUID "
					+ ",RBA_CTN_RE_ID"
					+ ",CREATED_DT"
					+ ",CREATED_BY"
					+ ",UPDATED_DT"
					+ ",UPDATED_BY"
					+ ",CLIENT_GUID"
					+ ",IS_DELETED"
					+ ",DATA_VER"
					+ ",RBA_DOC_ID"
					+ ",RBA_ITM_SEQNO"
					+ ",CTN_BACO"
					+ ",LOT_ID)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmtInsert = conn.prepareStatement(sql);
			
			String headGuid = UUID.randomUUID().toString();
			int i=0;

			pstmtInsert.setString(++i, headGuid);
			pstmtInsert.setString(++i,headGuid);
			pstmtInsert.setLong(++i, new Date().getTime());
			pstmtInsert.setString(++i, entity.getCreated_by());
			pstmtInsert.setLong(++i, new Date().getTime());
			pstmtInsert.setString(++i,entity.getUpdated_by());
			pstmtInsert.setString(++i, entity.getClient_guid());
			pstmtInsert.setInt(++i, 0);
			pstmtInsert.setString(++i, entity.getData_ver());
			pstmtInsert.setString(++i, entity.getRba_doc_id());
			pstmtInsert.setString(++i, entity.getRba_itm_seqno());
			pstmtInsert.setString(++i, entity.getCtn_baco());
			pstmtInsert.setString(++i, entity.getLot_id());
			
			pstmtInsert.execute();
			pstmtInsert.close();
					
			return headGuid;
			
			
		}catch(Exception ex){
			throw ex;
		} finally{			
			if (pstmtInsert != null && !pstmtInsert.isClosed())pstmtInsert.close();
		}
	}
}

