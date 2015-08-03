package com.xinyou.label.domain.biz;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.models.EMP_MAIN_VIEW;
import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.label.domain.entities.CTN_MAIN;
import com.xinyou.label.domain.entities.SUB_WO_MAIN;
import com.xinyou.label.domain.entities.SWS_RP;
import com.xinyou.label.domain.entities.SWS_STAFF;
import com.xinyou.label.domain.models.ERP_RDA_DOC;
import com.xinyou.label.domain.models.SWS_DOC;
import com.xinyou.label.domain.models.SWS_PRINT_DOC;
import com.xinyou.label.domain.models.SWS_RP_DOC;
import com.xinyou.label.domain.models.SWS_SCRAP_DOC;
import com.xinyou.label.domain.models.WO_PRINT_DOC;
import com.xinyou.label.domain.viewes.RAC_SCRAP_VIEW;
import com.xinyou.label.domain.viewes.RAC_VIEW;
import com.xinyou.label.domain.viewes.SCRAP_REPORT_VIEW;
import com.xinyou.label.domain.viewes.SUB_WO_MAIN_VIEW;
import com.xinyou.label.domain.viewes.SUB_WO_SUB_VIEW;
import com.xinyou.label.domain.viewes.SWS_RAC_VIEW;
import com.xinyou.label.domain.viewes.SWS_RP_VIEW;
import com.xinyou.label.domain.viewes.SWS_SCRAP_VIEW;
import com.xinyou.label.domain.viewes.WO_DOC_VIEW;
import com.mysql.jdbc.StringUtils;

public class Wo_Biz {
	public static EntityListDM<WO_DOC_VIEW,WO_DOC_VIEW> getWoList(String itmId, int page_no, int page_size,Connection conn,Connection connErp) throws Exception
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<WO_DOC_VIEW,WO_DOC_VIEW> returnDM = new EntityListDM<WO_DOC_VIEW,WO_DOC_VIEW>();
		ArrayList<WO_DOC_VIEW> returnList = new ArrayList<WO_DOC_VIEW>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String subSQL = "SELECT T1.WO_DOC_GUID,T1.WO_DOC_ID,T1.WO_ITM_ID,T2.ITM_NAME,T2.ITM_UNIT,T1.WO_QTY,T1.WO_FINISH_QTY,T1.WO_SCRAP_QTY,T1.WO_STATUS,T1.WO_BIND_QTY,ISNULL(T2.DEF_LOC_ID,'') AS DEF_LOC_ID,ISNULL(T2.SWS_MEMO,'') AS SWS_MEMO,ISNULL(T2.SWM_MEMO,'') AS SWM_MEMO,ISNULL(T2.PTN_NAME,'') AS PTN_NAME,ISNULL(T2.M_QUALITY,'') AS M_QUALITY,ISNULL(T2.ITM_DESC,'') AS M_MODEL,ISNULL(T2.NEXT_TEXT,'') AS NEXT_TEXT FROM WO_DOC T1,ITM_MAIN T2 ";
			String subSQLWhere = " WHERE T1.WO_ITM_ID=T2.ITM_MAIN_ID AND T1.WO_STATUS=1";
			
			if (itmId != null && !itmId.isEmpty()) {
				subSQLWhere += " AND T1.WO_ITM_ID LIKE ?";
			}
			
			subSQL = subSQL + subSQLWhere;
			String subOrderby = " ORDER BY WO_DOC_ID DESC ";
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";
			
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			if (itmId != null && !itmId.isEmpty()) {
				pstmt.setString(++index, itmId+"%");
			}
			
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {				
				WO_DOC_VIEW entity = new WO_DOC_VIEW();
				entity.setWo_doc_guid(rs.getString(1));
				entity.setWo_doc_id(rs.getString(2));
				entity.setItm_id(rs.getString(3));
				entity.setItm_name(rs.getString(4));
				entity.setItm_unit(rs.getString(5));
				entity.setItm_qty(rs.getBigDecimal(6));
				entity.setItm_finish_qty(rs.getBigDecimal(7));
				entity.setItm_scrap_qty(rs.getBigDecimal(8));
				entity.setWo_status(rs.getInt(9));
				entity.setItm_bind_qty(rs.getBigDecimal(10));
				entity.setDef_loc_id(rs.getString(11));
				//,ISNULL(T2.SWS_MEMO,''),ISNULL(T2.SWM_MEMO,''),ISNULL(T2.PTN_NAME,''),ISNULL(T2.M_QUALITY,''),ISNULL(T2.M_MODEL,''),ISNULL(T2.NEXT_TEXT,'')
				entity.setSws_memo(rs.getString(12));
				entity.setSwm_memo(rs.getString(13));
				entity.setPtn_name(rs.getString(14));
				entity.setM_quality(rs.getString(15));
				entity.setM_model(rs.getString(16));
				entity.setNext_text(rs.getString(17));
				
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			
			returnDM.setDataList(returnList);
			
			subSQL = "SELECT COUNT(*) FROM WO_DOC WHERE WO_STATUS=1 ";
			if (itmId != null && !itmId.isEmpty()) {
				subSQL += " AND WO_ITM_ID LIKE ?";
			}
			pstmt = conn.prepareStatement(subSQL);
			index = 0;
			if (itmId != null && !itmId.isEmpty()) {
				pstmt.setString(++index, itmId+"%");
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returnDM.setCount(rs.getInt(1));
			} else {
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
			
			for(WO_DOC_VIEW entity:returnDM.getDataList())
			{				
				pstmt = conn.prepareStatement("SELECT TOP 1 T.LOT_ID FROM SUB_WO_MAIN T WHERE T.WO_ID=? ORDER BY T.CREATED_DT DESC");
				pstmt.setString(1,entity.getWo_doc_id());
				
				rs = pstmt.executeQuery();
				if (rs.next()) {
					entity.setLot_id(rs.getString(1));
				}
				else
				{
					SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
					entity.setLot_id(dateFormat.format(new Date()));
				}
				rs.close();
				pstmt.close();
			}
		}
		catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}
	
	public static List<RAC_VIEW> getRacListByWoId(String woId,Connection conn) throws Exception
	{
		ArrayList<RAC_VIEW> returnList = new ArrayList<RAC_VIEW>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
//			pstmt = conn.prepareStatement("SELECT WO_RAC_ID,RAC_ID,ISNULL(RAC_SPEC,RAC_NAME),RAC_PKG_QTY FROM WO_RAC WHERE WO_DOC_ID=? ORDER BY WO_RAC_ID");
			pstmt = conn.prepareStatement("SELECT WO_RAC_ID,RAC_ID,RAC_NAME,RAC_PKG_QTY FROM WO_RAC WHERE WO_DOC_ID=? ORDER BY WO_RAC_ID");
			pstmt.setString(1,woId);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RAC_VIEW entity = new RAC_VIEW();
				entity.setRac_seqno(rs.getString(1));
				entity.setRac_id(rs.getString(2));
				entity.setRac_name(rs.getString(3));
				entity.setRac_pkg_qty(rs.getBigDecimal(4));
				
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			
//			String itm_id = "";
//			pstmt = conn.prepareStatement("SELECT WO_ITM_ID FROM WO_DOC WHERE WO_DOC_ID=?");
//			pstmt.setString(1,woId);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				itm_id = rs.getString(1);
//			}
//			else
//			{
//				throw new Exception("未找到工单信息！");
//			}
//			rs.close();
//			pstmt.close();
//			
//			for(RAC_VIEW entity:returnList)
//			{
//				pstmt = conn.prepareStatement("SELECT  TOP 1  SWS_QTY FROM SUB_WO_MAIN WHERE ITM_ID=? AND CUT_ID=? ORDER BY CREATED_DT DESC");
//				pstmt.setString(1,itm_id);
//				pstmt.setString(2, entity.getRac_id());
//				
//				rs = pstmt.executeQuery();
//				if (rs.next()) {
//					entity.setRac_pkg_qty(rs.getBigDecimal(1));
//				}
//				rs.close();
//				pstmt.close();
//			}
		}
		catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		
		return returnList;
	}
	
	public static void updateRacSpec(String racSeqno, String racId, String itmId,String racNewSpec,Connection conn) throws Exception
	{
		PreparedStatement pstmt = conn.prepareStatement("UPDATE WO_RAC SET RAC_SPEC=? FROM WO_DOC T2 WHERE WO_RAC.WO_DOC_ID=T2.WO_DOC_ID AND T2.WO_ITM_ID=? AND WO_RAC.WO_RAC_ID=? AND WO_RAC.RAC_ID=? AND WO_RAC.RAC_SPEC IS NULL");
		pstmt.setString(1, racNewSpec);
		pstmt.setString(2, itmId);
		pstmt.setString(3, racSeqno);
		pstmt.setString(4,  racId);
		pstmt.execute();
		pstmt.close();
	}
	
	public static void addSubWo(SUB_WO_MAIN subWo,Connection conn) throws Exception
	{
		PreparedStatement pstmt = null;
		
		try
		{
			pstmt = conn.prepareStatement("UPDATE  WO_DOC SET WO_BIND_QTY=ISNULL(WO_BIND_QTY,0)+? WHERE WO_DOC_ID=?");
			pstmt.setBigDecimal(1, subWo.getSub_qty());
			pstmt.setString(2, subWo.getWo_id());
			pstmt.execute();
			pstmt.close();
			
			String guid = UUID.randomUUID().toString();
			pstmt = conn.prepareStatement("INSERT INTO SUB_WO_MAIN(SUB_WO_MAIN_GUID,SUB_WO_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,SUB_QTY,WO_ID,LOT_ID,CUT_ID,CUT_NAME,M_MODEL,M_QUALITY,M_QC_DOC,CUT_SEQNO,ITM_ID,NEXT_TEXT,SWM_MEMO,STOCK_AREA,SWS_QTY,SPLIT_QTY,PTN_NAME,WORK_DEMAND,SWS_MEMO,SP_ID,PRE_SWS_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, guid);
			pstmt.setString(2, guid);
			pstmt.setLong(3, new Date().getTime());
			pstmt.setString(4, subWo.getCreated_by());
			pstmt.setLong(5, new Date().getTime());
			pstmt.setString(6, subWo.getUpdated_by());
			pstmt.setString(7, subWo.getClient_guid());
			pstmt.setInt(8, 0);
			pstmt.setString(9, subWo.getData_ver());
			
			//SUB_QTY,WO_ID,LOT_ID,CUT_ID,CUT_NAME,M_MODEL,M_QUALITY,M_QC_DOC
			pstmt.setBigDecimal(10, subWo.getSub_qty());
			pstmt.setString(11, subWo.getWo_id());
			pstmt.setString(12, subWo.getLot_id());
			pstmt.setString(13, subWo.getCut_id());
			pstmt.setString(14, subWo.getCut_name());
			pstmt.setString(15, subWo.getM_model());
			pstmt.setString(16, subWo.getM_quality());
			pstmt.setString(17, subWo.getM_qc_doc());
			pstmt.setString(18, subWo.getCut_seqno());
			pstmt.setString(19, subWo.getItm_id());
			
			//,NEXT_TEXT,SWM_MEMO,STOCK_AREA,SWS_QTY,SPLIT_QTY
			pstmt.setString(20, subWo.getNext_text());
			pstmt.setString(21, subWo.getSwm_memo());
			pstmt.setString(22, subWo.getStock_area());
			pstmt.setBigDecimal(23, subWo.getCut_qty());
			pstmt.setInt(24, subWo.getSws_qty());
			pstmt.setString(25, subWo.getPtn_name());
			pstmt.setString(26, subWo.getWork_demand());
			pstmt.setString(27, subWo.getSws_memo());
			pstmt.setString(28, subWo.getSp_info());
			pstmt.setString(29, subWo.getPre_sws_id());
			
			pstmt.execute();
			pstmt.close();
			
			//更新品号默认库位信息
			pstmt = conn.prepareStatement("UPDATE ITM_MAIN SET SWM_MEMO=? WHERE ITM_MAIN_ID=?");
			pstmt.setString(1, subWo.getSwm_memo());
			pstmt.setString(2, subWo.getItm_id());
			pstmt.execute();
			pstmt.close();
			
			//modify by jimmy 20140305
			subWo.setSws_qty(subWo.getSub_qty().divide(subWo.getCut_qty(), 0, BigDecimal.ROUND_CEILING).intValue());
			
			for(int i=0;i<subWo.getSws_qty();i++)
			{
				BigDecimal subQty = new BigDecimal(0);
				if(i==subWo.getSws_qty()-1)
				{
					subQty = subWo.getSub_qty();
				}
				else
				{
					subQty = subWo.getCut_qty();
					subWo.setSub_qty(subWo.getSub_qty().subtract(subWo.getCut_qty()));
				}
				
				String swsGuid = UUID.randomUUID().toString();
				//get swsId
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String date_str = sdf.format(new Date());
				pstmt = conn.prepareStatement("SELECT MAXNO = MAX(CTN_BACO) FROM CTN_MAIN WHERE CTN_BACO LIKE ?");
				pstmt.setString(1, date_str+"%");
				ResultSet rs = pstmt.executeQuery();
				String maxno = null;
				if(rs.next()){
					maxno = rs.getString(1);
				}
				rs.close();
				pstmt.close();
				
				maxno=maxno==null?"0":maxno;
				String doc_seqno = maxno.replaceFirst(date_str, ""); 
				doc_seqno = Long.parseLong(doc_seqno)+1+"";
				while(doc_seqno.length()<6){
					doc_seqno="0"+doc_seqno;
				}
				String swsId = date_str+doc_seqno;
				
				//add ctn
				CTN_MAIN ctn = new CTN_MAIN();
				ctn.setCtn_baco(swsId);
				ctn.setCtn_main_id(swsId);
				ctn.setCtn_status(3);
				ctn.setCtn_type(12);
				ctn.setItm_id(subWo.getItm_id());
				ctn.setItm_qty(BigDecimal.ZERO);
				ctn.setParent_ctn_guid("");
				ctn.setWh_area_guid("");
				ctn.setWh_guid("");
				ctn.setWh_loc_guid("");
				ctn.setWh_package_guid("");
				ctn.setWh_plt_guid("");
				ctn.setWh_shelf_guid("");
				ctn.setLot_id(subWo.getLot_id());
				ctn.setBase_type(0);
				ctn.setBase_id(subWo.getWo_id());
				String ctnGuid = Common_Biz.addBaco(ctn, "subwo", "1.0.0.0", "gl", conn);
				
				pstmt = conn.prepareStatement("INSERT INTO SUB_WO_SUB(SUB_WO_SUB_GUID,SUB_WO_SUB_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,SUB_WO_MAIN_GUID,CTN_GUID,SUB_SEQNO,FINISH_QTY,SCRAP_QTY,SWS_QTY,ITM_ID,SWS_STATUS,RP_STATUS,PRE_SWS_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,0,?)");
				pstmt.setString(1, swsGuid);
				pstmt.setString(2, swsId);
				pstmt.setLong(3, new Date().getTime());
				pstmt.setString(4, subWo.getCreated_by());
				pstmt.setLong(5, new Date().getTime());
				pstmt.setString(6, subWo.getUpdated_by());
				pstmt.setString(7, subWo.getClient_guid());
				pstmt.setInt(8, 0);
				pstmt.setString(9, subWo.getData_ver());
				
				//SUB_WO_MAIN_GUID,CTN_GUID,SUB_SEQNO,FINISH_QTY,SCRAP_QTY
				pstmt.setString(10, guid);
				pstmt.setString(11, ctnGuid);
				pstmt.setInt(12, i+1);
				pstmt.setBigDecimal(13, BigDecimal.ZERO);
				pstmt.setBigDecimal(14,BigDecimal.ZERO);
				pstmt.setBigDecimal(15, subQty);
				pstmt.setString(16, subWo.getItm_id());
				pstmt.setString(17, subWo.getPre_sws_id());
				
				pstmt.execute();
				pstmt.close();
			}
		}
		catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}
	
	public static EntityListDM<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> getSwsList(String woId, String itmId, int page_no, int page_size,Connection conn) throws Exception
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> returnDM = new EntityListDM<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW>();
		ArrayList<SUB_WO_SUB_VIEW> returnList = new ArrayList<SUB_WO_SUB_VIEW>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String subSQL = "SELECT T1.SUB_WO_SUB_GUID,T1.SUB_WO_MAIN_GUID,T1.CTN_GUID,T1.SUB_SEQNO,T1.FINISH_QTY,T1.SCRAP_QTY,T1.SWS_QTY,T1.ITM_ID,T1.SWS_STATUS,T1.RP_STATUS,T2.LOT_ID,T1.CREATED_DT,T2.WO_ID,T1.SUB_WO_SUB_ID,T3.PAPER_COLOR FROM SUB_WO_SUB T1,SUB_WO_MAIN T2,ITM_MAIN T3 ";
			String subSQLWhere = " WHERE T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID AND T3.ITM_MAIN_ID=T2.ITM_ID";
			
			if (woId != null && !woId.isEmpty()) {
				subSQLWhere += " AND T2.WO_ID LIKE ?";
			}
			if (itmId != null && !itmId.isEmpty()) {
				subSQLWhere += " AND T1.SUB_WO_SUB_ID LIKE ?";
			}
			
			subSQL = subSQL + subSQLWhere;
			String subOrderby = " ORDER BY CREATED_DT DESC,SUB_SEQNO  ";
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";
			
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			if (woId != null && !woId.isEmpty()) {
				pstmt.setString(++index, woId+"%");
			}
			if (itmId != null && !itmId.isEmpty()) {
				pstmt.setString(++index, itmId+"%");
			}
			
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				SUB_WO_SUB_VIEW entity = new SUB_WO_SUB_VIEW();
				//T1.SUB_WO_SUB_GUID,T1.SUB_WO_MAIN_GUID,T1.CTN_GUID,T1.SUB_SEQNO,T1.FINISH_QTY,
				//T1.SCRAP_QTY,T1.SWS_QTY,T1.ITM_ID,T1.SWS_STATUS,T1.RP_STATUS,T2.LOT_ID,T1.CREATED_DT
				entity.setGuid(rs.getString(1));
				entity.setParent_guid(rs.getString(2));
				entity.setCtn_guid(rs.getString(3));
				entity.setSub_seqno(rs.getInt(4));
				entity.setFinish_qty(rs.getBigDecimal(5));
				entity.setScrap_qty(rs.getBigDecimal(6));
				entity.setSws_qty(rs.getBigDecimal(7));
				entity.setItm_id(rs.getString(8));
				entity.setSws_status(rs.getInt(9));
				entity.setRp_status(rs.getInt(10));
				entity.setLot_id(rs.getString(11));
				entity.setWo_id(rs.getString(13));
				entity.setId(rs.getString(14));
				entity.setPaper_color(rs.getString(15));
				
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			returnDM.setDataList(returnList);
			
			subSQL = "SELECT COUNT(T1.SUB_WO_SUB_ID) FROM SUB_WO_SUB T1,SUB_WO_MAIN T2,ITM_MAIN T3 WHERE T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID AND T3.ITM_MAIN_ID=T2.ITM_ID ";
			if (woId != null && !woId.isEmpty()) {
				subSQL += " AND T2.WO_ID LIKE ?";
			}
			if (itmId != null && !itmId.isEmpty()) {
				subSQL += " AND T1.SUB_WO_SUB_ID LIKE ?";
			}
			
			pstmt = conn.prepareStatement(subSQL);
			index = 0;
			if (woId != null && !woId.isEmpty()) {
				pstmt.setString(++index, woId+"%");
			}
			if (itmId != null && !itmId.isEmpty()) {
				pstmt.setString(++index, itmId+"%");
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returnDM.setCount(rs.getInt(1));
			} else {
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}

	public static List<WO_PRINT_DOC> getWoPrintList(List<String> swsguids,Connection conn) throws Exception
	{
		ArrayList<WO_PRINT_DOC> returnList = new ArrayList<WO_PRINT_DOC>();
		
		StringBuilder sbSwsGuidSb = new  StringBuilder();
		String sbSwsGuidString;
		ArrayList<String> moGuidList = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			for( String swsguid : swsguids)
			{
				sbSwsGuidSb.append("'");
				sbSwsGuidSb.append(swsguid);
				sbSwsGuidSb.append("',");
			}
			
			sbSwsGuidString = sbSwsGuidSb.toString();
			if(sbSwsGuidString.length()>0)
			{
				sbSwsGuidString = sbSwsGuidString.substring(0, sbSwsGuidString.length()-1);
			}
			else
			{
				return returnList;
			}

			pstmt = conn.prepareStatement("SELECT DISTINCT SUB_WO_MAIN_GUID  FROM SUB_WO_SUB WHERE SUB_WO_SUB_GUID IN("+sbSwsGuidString+")");
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				moGuidList.add(rs.getString(1));
			}
			rs.close();
			pstmt.close();
			
			boolean firstRecord = true;
			for(String moguid:moGuidList)
			{
				firstRecord = true;
				WO_PRINT_DOC doc = new 	WO_PRINT_DOC();
				returnList.add(doc);
				
				doc.setSubdocs(new ArrayList<SWS_PRINT_DOC>());

				//流程票信息
				pstmt = conn.prepareStatement("SELECT T2.ITM_ID,T2.WO_ID,T2.LOT_ID,'' AS CTM_NAME,T2.M_MODEL,T2.M_QUALITY,T2.M_QC_DOC,T2.CUT_SEQNO,T1.SUB_WO_SUB_GUID,T1.SUB_WO_SUB_ID,T1.SUB_SEQNO,T1.SWS_QTY, "
						+ "T2.NEXT_TEXT,T2.SWM_MEMO,T2.SWS_MEMO,"
						+ "T2.STOCK_AREA,T2.PTN_NAME,T3.ITM_DESC,T2.WORK_DEMAND,T2.CUT_SEQNO,T3.ITM_DFT_INV "
						+ "FROM SUB_WO_SUB T1,SUB_WO_MAIN T2,ITM_MAIN T3 "
						+ "WHERE T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID AND T3.ITM_MAIN_ID=T2.ITM_ID AND T1.SUB_WO_MAIN_GUID=? AND T1.SUB_WO_SUB_GUID IN("+sbSwsGuidString+") ORDER BY T1.SUB_SEQNO");
				pstmt.setString(1, moguid);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					if(firstRecord)
					{
						doc.setHeaddoc(new SUB_WO_MAIN_VIEW());
						doc.getHeaddoc().setItm_id(rs.getString(1));
						doc.getHeaddoc().setWo_id(rs.getString(2));
						doc.getHeaddoc().setLot_id(rs.getString(3));
						doc.getHeaddoc().setCtm_name(rs.getString(4));
						doc.getHeaddoc().setM_model(rs.getString(5));
						doc.getHeaddoc().setM_quality(rs.getString(6));
						doc.getHeaddoc().setM_qc_doc(rs.getString(7));
						doc.getHeaddoc().setSplit_seqno(rs.getString(8));
						doc.getHeaddoc().setNext_text(rs.getString(13));
						doc.getHeaddoc().setSwm_memo(rs.getString(14));
						doc.getHeaddoc().setSws_memo(rs.getString(15));
						doc.getHeaddoc().setStock_area(rs.getString(16));
						doc.getHeaddoc().setPtn_name(rs.getString(17));
						doc.getHeaddoc().setItm_desc(rs.getString(18));
						doc.getHeaddoc().setWork_demand(rs.getString(19));
						doc.getHeaddoc().setDft_inv(rs.getString(21));
						
						firstRecord = false;
					}
					
					SWS_PRINT_DOC sws= new SWS_PRINT_DOC();
					sws.setSwsdoc(new SUB_WO_SUB_VIEW());
					
					sws.getSwsdoc().setGuid(rs.getString(9));
					sws.getSwsdoc().setId(rs.getString(10));
					sws.getSwsdoc().setSub_seqno(rs.getInt(11));
					sws.getSwsdoc().setSws_qty(rs.getBigDecimal(12));
					sws.getSwsdoc().setItm_id(rs.getString(1));
					sws.getSwsdoc().setLot_id(rs.getString(3));
					
					sws.setRaclist(new ArrayList<SWS_RAC_VIEW>());
					
					//工艺信息
					String sqlRac=" SELECT T3.WO_RAC_ID,T3.RAC_ID,ISNULL(T3.RAC_SPEC,T3.RAC_NAME),T3.RAC_PKG_QTY,ISNULL(SUM(T4.FINISH_QTY),0) AS FINISH_QTY,ISNULL(SUM(T4.SCRAP_QTY),0) AS SCRAP_QTY,T3.RAC_TARGET "
							+ " FROM SUB_WO_SUB T1 "
							+ " JOIN SUB_WO_MAIN T2 ON T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID "
							+ " JOIN WO_RAC T3 ON T3.WO_DOC_ID=T2.WO_ID "
							+ " LEFT JOIN SWS_RP T4 ON T4.SWS_GUID=T1.SUB_WO_SUB_GUID AND T4.RP_RAC_ID=T3.WO_RAC_ID "
							+ " WHERE T1.SUB_WO_SUB_GUID=? "
							+ " GROUP BY T3.WO_RAC_ID,T3.RAC_ID,T3.RAC_SPEC,T3.RAC_NAME,T3.RAC_PKG_QTY,T3.RAC_TARGET "
							+ " ORDER BY T3.WO_RAC_ID ";
					
					PreparedStatement psRac = conn.prepareStatement(sqlRac);
					psRac.setString(1, sws.getSwsdoc().getGuid());

					ResultSet rsRac = psRac.executeQuery();
					while(rsRac.next())
					{						
						//有切断的话，增加切断重量 20140712
						if(rsRac.getString(2).equals("002")){
							PreparedStatement psGetItmWt = conn.prepareStatement("SELECT ISNULL(DEA980,0) FROM TPADEA WHERE DEA001=?");
							psGetItmWt.setString(1, rs.getString(1));
							ResultSet rsItmWt = psGetItmWt.executeQuery();
							if(rsItmWt.next()){
								sws.getSwsdoc().setCutWt(rsItmWt.getBigDecimal(1).multiply(rs.getBigDecimal(12)));
							}
							rsItmWt.close();
							psGetItmWt.close();							
						}
						
						SWS_RAC_VIEW rac = new SWS_RAC_VIEW();
						rac.setRac_seqno(rsRac.getString(1));
						rac.setRac_id(rsRac.getString(2));
						rac.setRac_name(rsRac.getString(3));
						rac.setRac_pkg_qty(rsRac.getBigDecimal(4));
						rac.setFinish_qty(rsRac.getBigDecimal(5));
						rac.setScrap_qty(rsRac.getBigDecimal(6));
						rac.setTar_qty(rsRac.getBigDecimal(7));
												
						sws.getRaclist().add(rac);						
					}
					rsRac.close();
					rsRac=null;
					psRac.close();
					psRac=null;
					
					//工艺描述信息
					for(SWS_RAC_VIEW rac:sws.getRaclist()){
						PreparedStatement psRacDesc=conn.prepareStatement("SELECT RAC_DESC FROM WO_RAC WHERE WO_DOC_ID=? AND WO_RAC_ID=?");
						psRacDesc.setString(1, doc.getHeaddoc().getWo_id() );
						psRacDesc.setString(2, rac.getRac_seqno());
						ResultSet rsRacDesc=psRacDesc.executeQuery();
						if(rsRacDesc.next()){
							rac.setRac_desc(rsRacDesc.getString(1));
						}
						rsRacDesc.close();
						psRacDesc.close();
					}
					
					doc.getSubdocs().add(sws);
				}
			}
			
			rs.close();
			pstmt.close();
		}
		catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		
		return returnList;
	}
	
	//根据流程票号获取流程票信息
	public static SUB_WO_SUB_VIEW getSwsOnlyById(String swsId,Connection conn) throws Exception{
		SUB_WO_SUB_VIEW result=new SUB_WO_SUB_VIEW();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = conn.prepareStatement("SELECT T1.SUB_WO_SUB_GUID,T1.SUB_WO_SUB_ID,T1.SUB_WO_MAIN_GUID,T2.M_QC_DOC,T2.SP_ID,T2.LOT_ID,ISNULL(T1.SWS_QTY,0),T1.ITM_ID,T2.CUT_SEQNO,T2.WO_ID "
					+ " FROM SUB_WO_SUB T1,SUB_WO_MAIN T2 WHERE T1.SUB_WO_SUB_ID=? AND T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID AND T1.IS_DELETED='0' AND T2.IS_DELETED='0'");
			pstmt.setString(1,swsId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int i=0;
				result.setGuid(rs.getString(++i));
				result.setId(rs.getString(++i));
				result.setParent_guid(rs.getString(++i));
				result.setM_qc_doc(rs.getString(++i));
				result.setSp_id(rs.getString(++i));
				result.setLot_id(rs.getString(++i));
				result.setSws_qty(rs.getBigDecimal(++i));
				result.setItm_id(rs.getString(++i));
				result.setCut_seqno(rs.getString(++i));
				result.setWo_id(rs.getString(++i));
			}
			rs.close();
			pstmt.close();
			
			return result;
			
		}catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
			if (rs != null && !rs.isClosed())rs.close();
		}
	}
	
	public static SWS_DOC getSwsById(String swsId,Connection conn,Connection connErp) throws Exception
	{
		SWS_DOC result = new SWS_DOC();
		result.setSwsdoc(new SUB_WO_SUB_VIEW());
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT T1.SUB_WO_SUB_GUID,T1.SUB_WO_SUB_ID,T1.SUB_WO_MAIN_GUID,T1.CTN_GUID,T1.SUB_SEQNO,T1.FINISH_QTY,ISNULL(T1.SCRAP_QTY,0),T1.SWS_QTY,T1.ITM_ID,T1.SWS_STATUS,T1.RP_STATUS,T2.WO_ID,T2.STOCK_AREA,T2.CUT_SEQNO FROM SUB_WO_SUB T1,SUB_WO_MAIN T2 WHERE T1.SUB_WO_SUB_ID=? AND T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID");
			pstmt.setString(1,swsId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				if(!Erp_Biz.isWoDocNotFinished(rs.getString(12), connErp)){
					
					throw new Exception("工单："+rs.getString(12)+" 在ERP 系统中已完工！");
				}
				
				result.getSwsdoc().setGuid(rs.getString(1));
				result.getSwsdoc().setId(rs.getString(2));
				result.getSwsdoc().setParent_guid(rs.getString(3));
				result.getSwsdoc().setCtn_guid(rs.getString(4));
				result.getSwsdoc().setSub_seqno(rs.getInt(5));
				result.getSwsdoc().setFinish_qty(rs.getBigDecimal(6));
				result.getSwsdoc().setScrap_qty(rs.getBigDecimal(7));
				result.getSwsdoc().setSws_qty(rs.getBigDecimal(8));
				result.getSwsdoc().setItm_id(rs.getString(9));
				result.getSwsdoc().setSws_status(rs.getInt(10));
				result.getSwsdoc().setRp_status(rs.getInt(11));
				result.getSwsdoc().setWo_id(rs.getString(12));
				result.getSwsdoc().setStock_area(rs.getString(13));
				result.getSwsdoc().setCut_seqno(rs.getString(14));
				
				result.setRaclist(new ArrayList<SWS_RAC_VIEW>());
				
				
				List<SWS_RAC_VIEW> racListTemp=new ArrayList<SWS_RAC_VIEW>();
				//工单工序
				String sqlRac="SELECT T3.WO_RAC_ID,T3.RAC_ID,T3.RAC_NAME,T3.RAC_PKG_QTY,ISNULL(T3.RAC_TECH_ID,'') AS RAC_TECH_ID,T3.RAC_EMP_NUM "
						+ " FROM SUB_WO_SUB T1 "
						+ " JOIN SUB_WO_MAIN T2 ON T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID "
						+ " JOIN WO_RAC T3 ON T3.WO_DOC_ID=T2.WO_ID  "
						+ " WHERE T1.SUB_WO_SUB_GUID=?"
						+ " ORDER BY T3.WO_RAC_ID";
				PreparedStatement psRac=conn.prepareStatement(sqlRac);
				psRac.setString(1, result.getSwsdoc().getGuid());
				ResultSet rsRac = psRac.executeQuery();
				while(rsRac.next())
				{					
					SWS_RAC_VIEW rac = new SWS_RAC_VIEW();
					rac.setRac_seqno(rsRac.getString(1));
					rac.setRac_id(rsRac.getString(2));
					rac.setRac_name(rsRac.getString(3));
					rac.setRac_pkg_qty(rsRac.getBigDecimal(4));
					rac.setRac_tech_id(rsRac.getString(5));
					rac.setRac_emp_num(rsRac.getInt(6));
					
					racListTemp.add(rac);
				}
				rsRac.close();
				rsRac=null;
				psRac.close();
				psRac=null;
							
				boolean isStarted=false;
				for(int i=0;i<racListTemp.size();i++){
					
					SWS_RAC_VIEW rac=racListTemp.get(i);					
		
					//mod by jimmy 2014-09-08 开工时弹窗提示一下这张流程票本道工序可移出数量是多少，请员工自行核对箱内数量
					//mod by xiz 2014-10-13 流程票允许顺序开工，且每一道工序只允许一个开工状态---------------------------------------
				    BigDecimal lastRacQty=BigDecimal.ZERO ;
				
				    if( rac.getRac_seqno().equals(result.getSwsdoc().getCut_seqno())) {
						//当前工序为开始工序（开始工序不一定等于第一道工序，可能是整个工艺中的任何一道工序），可报工量等于流程票上的生产数量
					   lastRacQty = result.getSwsdoc().getSws_qty();
					   
					   isStarted=true;
					   
					 //校验本道工序是否正在开工,如果是则过滤  For 每道工序只允许存在一个开工状态	
					   boolean isThisRacInWork=IsRacInWork(result.getSwsdoc().getGuid(),rac.getRac_seqno(),conn);
					   if(isThisRacInWork){
						   continue;
					   }
					}
				    else{
				    	//过滤掉开始工序之前的工序
				    	if(!isStarted){
				    		continue;
				    	}
				    	
						 //校验本道工序是否正在开工,如果是则过滤  For 每道工序只允许存在一个开工状态	
						   boolean isThisRacInWork=IsRacInWork(result.getSwsdoc().getGuid(),rac.getRac_seqno(),conn);
						   if(isThisRacInWork){
							   continue;
						   }
				    				    	
				    	//前一道工序的ID
				    	String lastRacID=racListTemp.get(i-1).getRac_seqno();
				    	
				    	//前一道工序是否有开工记录
				    	PreparedStatement psLastRp=conn.prepareStatement("SELECT SWS_GUID FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=? AND RP_STATUS IN (1,2)");
				    	psLastRp.setString(1, result.getSwsdoc().getGuid());
				    	psLastRp.setString(2, lastRacID);
				    	ResultSet rsLastRp=psLastRp.executeQuery();
				    	if(!rsLastRp.next())
				    	{
				    		//前一道工序没有开工记录，则不允许后面的工序开工
							//For: 只允许顺序开工						
							break;	
				    	}
				    					    	
				    	//前一道工序的已完工数量汇总
				    	lastRacQty= GetRPTotalFinishedQty(result.getSwsdoc().getGuid(),lastRacID,conn);
				    }
	
					//本道工序的已完工数量汇总+本道工序的报废数量
					BigDecimal currentRacQty=GetRPTotalFinishedAndScrapQty(result.getSwsdoc().getGuid(),rac.getRac_seqno(),conn);
					//本道工序的可报工数量
					lastRacQty = lastRacQty.subtract(currentRacQty);
					
					//过滤掉已经报满工的工序
					if(currentRacQty.compareTo(result.getSwsdoc().getSws_qty())>=0){
						continue;
					}
								
					rac.setRp_qty(lastRacQty);
					
					result.getRaclist().add(rac);
				}
				
				result.setEmplist(new ArrayList<EMP_MAIN_VIEW>());
				PreparedStatement psEmp = conn.prepareStatement("SELECT T1.EMP_GUID,T2.EMP_NAME,T2.EMP_MAIN_ID,T3.RP_RAC_ID,T3.RP_RAC_NAME  FROM SWS_STAFF T1,EMP_MAIN T2,SWS_RP T3 WHERE T3.SWS_GUID=? AND T3.RP_STATUS=1 AND T1.SWS_RP_GUID=T3.SWS_RP_GUID AND T1.EMP_GUID=T2.EMP_MAIN_GUID");
				psEmp.setString(1, result.getSwsdoc().getGuid());
				ResultSet rsEmp = psEmp.executeQuery();
				while(rsEmp.next())
				{
					EMP_MAIN_VIEW emp = new EMP_MAIN_VIEW();
					emp.setEmp_main_guid(rsEmp.getString(1));
					emp.setEmp_name(rsEmp.getString(2));
					emp.setEmp_main_id(rsEmp.getString(3));
					emp.setRp_rac_id(rsEmp.getString(4));
					emp.setRp_rac_name(rsEmp.getString(5));
					
					result.getEmplist().add(emp);
				}
				rsEmp.close();
				rsEmp=null;
				psEmp.close();
				psEmp=null;
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		
		return result;
	}
	
	public static boolean IsRacInWork(String swsGuid,String racId,Connection conn) throws Exception{
		boolean result=false;
		
		PreparedStatement psCheckRac=conn.prepareStatement("SELECT SWS_RP_GUID FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=? AND RP_STATUS = 1");
		psCheckRac.setString(1,swsGuid);
		psCheckRac.setString(2, racId);
		ResultSet rsCheckRac = psCheckRac.executeQuery();
		if(rsCheckRac.next()){			
			result=true;
		}
		psCheckRac.close();
		rsCheckRac.close();
		
		return result;
	}
	
	//工序已完工数量汇总
	//Added by xiz on 2014/10/14 
	public static BigDecimal GetRPTotalFinishedQty(String swsGuid,String racId,Connection conn) throws Exception{
		try{
			BigDecimal result=BigDecimal.ZERO;
			
			PreparedStatement psGetLastQty = conn.prepareStatement("SELECT ISNULL(SUM(FINISH_QTY),0) AS TOTAL_FINISH_QTY FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=? AND RP_STATUS IN(1,2)");
			psGetLastQty.setString(1, swsGuid);
			psGetLastQty.setString(2, racId);
			ResultSet rsGetLastQty = psGetLastQty.executeQuery();
			if(rsGetLastQty.next()){
				result = rsGetLastQty.getBigDecimal(1);
			}
			rsGetLastQty.close();
			psGetLastQty.close();
			
			return result;
		}catch(Exception e){
			throw e;
		}
	}
	
	//工序报废数量汇总
	//Added by xiz on 2014/10/21
	public static BigDecimal GetRPTotalScrapedQty(String swsGuid,String racId,Connection conn) throws Exception{
		try{
			BigDecimal result=BigDecimal.ZERO;
			
			PreparedStatement ps = conn.prepareStatement("SELECT ISNULL(SUM(SCRAP_QTY),0) AS TOTAL_SCRAPED_QTY FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=? AND RP_STATUS IN(1,2)");
			ps.setString(1, swsGuid);
			ps.setString(2, racId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getBigDecimal(1);
			}
			rs.close();
			ps.close();
			
			return result;
		}catch(Exception e){
			throw e;
		}
	}
	
	//工序已完工数量汇总+报废数量汇总
	//Added by xiz on 2014/10/14 
	public static BigDecimal GetRPTotalFinishedAndScrapQty(String swsGuid,String racId,Connection conn) throws Exception{
		try{
			BigDecimal result=BigDecimal.ZERO;
			
			PreparedStatement psGetCurrentQty = conn.prepareStatement("SELECT ISNULL(SUM(FINISH_QTY),0)+ISNULL(SUM(SCRAP_QTY),0) AS CURRENT_QTY FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=? AND RP_STATUS IN (1,2)");
			psGetCurrentQty.setString(1, swsGuid);
			psGetCurrentQty.setString(2, racId);
			ResultSet rsGetCurrentQty = psGetCurrentQty.executeQuery();
			if(rsGetCurrentQty.next()){
				if(rsGetCurrentQty.getBigDecimal(1)!=null){
					result = rsGetCurrentQty.getBigDecimal(1);
				}
			}
			rsGetCurrentQty.close();
			psGetCurrentQty.close();
			
			return result;
		}catch(Exception e){
			throw e;
		}
	}
	
	//获取上道工序ID
	//Added by xiz on 2014/10/16
	public static String GetLastRacId(String swsGuid,String racId,Connection conn) throws Exception{
		
		String lastRacId="";
		try{
			PreparedStatement psGetLastRac = conn.prepareStatement("SELECT TOP 1 T3.WO_RAC_ID FROM SUB_WO_SUB T1 JOIN SUB_WO_MAIN T2 ON T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID JOIN WO_RAC T3 ON T3.WO_DOC_ID=T2.WO_ID AND T1.SUB_WO_SUB_GUID=? AND T3.WO_RAC_ID<? ORDER BY T3.WO_RAC_ID DESC");
			psGetLastRac.setString(1, swsGuid);
			psGetLastRac.setString(2, racId);
			ResultSet rsGetLastRac = psGetLastRac.executeQuery();
			if(rsGetLastRac.next()){								
				lastRacId= rsGetLastRac.getString(1);
			}

			psGetLastRac.close();
			rsGetLastRac.close();
			
			return lastRacId;
		}catch(Exception e){
			throw e;
		}
	}
	
	//获取本道工序的可报工数量
	//Added by xiz on 2014/10/16
	public static BigDecimal GetRacAvaliableQty(String swsGuid,String racId,String startRacId,BigDecimal swsQty,Connection conn) throws Exception{
		
		BigDecimal lastRacQty=BigDecimal.ZERO ;
		
		//本道工序已完工数量汇总+已报废数量汇总
		BigDecimal currentRacQty=GetRPTotalFinishedAndScrapQty(swsGuid,racId,conn);
		
		//上道工序已完工数量汇总			
	    if( racId.equals(startRacId)) {
			//当前工序为开始工序（开始工序不一定等于第一道工序，可能是整个工艺中的任何一道工序），可报工量等于流程票上的生产数量
		   lastRacQty = swsQty;
		}
	    else{
	    	//前一道工序的ID
	    	String lastRacID=GetLastRacId(swsGuid,racId,conn);
	    	//前一道工序的已完工数量汇总
	    	if(!lastRacID.equals(null) && !lastRacID.equals("")){
				lastRacQty= GetRPTotalFinishedQty(swsGuid,lastRacID,conn);
	    	}
	    }
	    
	    //本道工序的可报工数量
		lastRacQty = lastRacQty.subtract(currentRacQty);
		
		return lastRacQty;
	}
	
	//获取流程票下已开工未报工的报工单
	//Added by xiz on 2014/10/14 
	public static SWS_DOC GetSwsRpBySwsId(String swsId,Connection conn,Connection connErp) throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		PreparedStatement psRac=null;
		ResultSet rsRac=null;
		
		SWS_DOC doc=new SWS_DOC();
		try{
			
			doc.setRaclist(new ArrayList<SWS_RAC_VIEW>());
			
			ps=conn.prepareStatement("SELECT T1.SUB_WO_SUB_GUID,T2.WO_ID,T1.ITM_ID,T1.SUB_WO_SUB_ID FROM SUB_WO_SUB T1 INNER JOIN SUB_WO_MAIN T2 ON T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID AND T1.IS_DELETED='0' AND T2.IS_DELETED='0' AND T1.SUB_WO_SUB_ID=?");
			ps.setString(1, swsId);
			rs=ps.executeQuery();
			if(rs.next()){
				
				if(!Erp_Biz.isWoDocNotFinished(rs.getString(2), connErp)){
					
					throw new Exception("工单："+rs.getString(2)+" 在ERP 系统中已完工！");
				}
				
				doc.setSwsdoc(new SUB_WO_SUB_VIEW());
				doc.getSwsdoc().setGuid(rs.getString(1));
				doc.getSwsdoc().setWo_id(rs.getString(2));
				doc.getSwsdoc().setItm_id(rs.getString(3));
				doc.getSwsdoc().setId(rs.getString(4));
				
				psRac=conn.prepareStatement("SELECT RP_RAC_ID,RP_RAC_NAME,SWS_RP_GUID FROM SWS_RP WHERE SWS_GUID=? AND IS_DELETED='0' AND RP_STATUS=1");
				psRac.setString(1, doc.getSwsdoc().getGuid());
				rsRac=psRac.executeQuery();
				while(rsRac.next())
				{
					SWS_RAC_VIEW swsRp=new SWS_RAC_VIEW();
					swsRp.setRac_id(rsRac.getString(1));
					swsRp.setRac_name(rsRac.getString(2));
					swsRp.setSws_rp_guid(rsRac.getString(3));
					
					doc.getRaclist().add(swsRp);
				}
				
				psRac.close();
				rsRac.close();
			}
			ps.close();
			rs.close();
					
			return doc;
			
		}catch(Exception e){
			throw e;
		}finally{
			if(ps!=null&&ps.isClosed()==false){ps.close();}
			if(rs!=null&&rs.isClosed()==false){rs.close();}
			
			if(psRac!=null&&psRac.isClosed()==false){psRac.close();}
			if(rsRac!=null&&rsRac.isClosed()==false){rsRac.close();}
		}
	}
	
	//获取报工单 & 报工员工
	//Added by xiz on 2014/10/14 
	public static SWS_DOC GetSwsRpDocByRpGuid(String swsRpGuid,Connection conn) throws Exception{
		SWS_DOC doc=new SWS_DOC();
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		PreparedStatement psEmp=null;
		ResultSet rsEmp=null;
		
		PreparedStatement psRac=null;
		ResultSet rsRac=null;
		
		try{
			//报工单
			ps=conn.prepareStatement("SELECT RP_RAC_ID,RP_RAC_NAME,RP_WS,RP_WS_NO,BG_DT,SWS_RP_GUID,SWS_GUID FROM SWS_RP WHERE IS_DELETED='0' AND RP_STATUS=1 AND SWS_RP_GUID=?");
			ps.setString(1, swsRpGuid);
			rs=ps.executeQuery();
			if(rs.next()){
				doc.setSwsdoc(new SUB_WO_SUB_VIEW ());
				
				doc.setRaclist(new ArrayList<SWS_RAC_VIEW>());
				
				SWS_RAC_VIEW rac=new SWS_RAC_VIEW();
				rac.setRac_seqno(rs.getString(1));
				rac.setRac_name(rs.getString(2));
				rac.setRp_ws(rs.getString(3));
				rac.setRp_ws_no(rs.getString(4));
				rac.setBg_date(rs.getLong(5));
				rac.setSws_rp_guid(rs.getString(6));
	
				
				doc.getSwsdoc().setGuid(rs.getString(7));;
				
				//工单工序
				String sqlRac="SELECT T3.RAC_PKG_QTY,ISNULL(T3.RAC_TECH_ID,'') AS RAC_TECH_ID,T3.WO_DOC_ID,T1.SUB_WO_SUB_ID "
						+ " FROM SUB_WO_SUB T1 "
						+ " JOIN SUB_WO_MAIN T2 ON T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID "
						+ " JOIN WO_RAC T3 ON T3.WO_DOC_ID=T2.WO_ID  "
						+ " WHERE T1.SUB_WO_SUB_GUID=? AND T3.WO_RAC_ID=?";
			    psRac=conn.prepareStatement(sqlRac);
				psRac.setString(1, doc.getSwsdoc().getGuid());
				psRac.setString(2, rac.getRac_seqno());
				rsRac = psRac.executeQuery();
				if(rsRac.next())
				{					
					rac.setRac_pkg_qty(rsRac.getBigDecimal(1));
					rac.setRac_tech_id(rsRac.getString(2));
					
					doc.getSwsdoc().setWo_id(rsRac.getString(3));	
					doc.getSwsdoc().setId(rsRac.getString(4));
				}
				rsRac.close();
				psRac.close();
				
				//工序指标
				PreparedStatement psTar = conn.prepareStatement("SELECT ISNULL(TAR_EMP_NUM,0),ISNULL(TAR_VALUE,0) FROM WO_RAC_TAR WHERE WO_DOC_ID=? AND WO_RAC_ID=? AND WS_ID=?");
				psTar.setString(1,doc.getSwsdoc().getWo_id());
				psTar.setString(2, rac.getRac_seqno());
				psTar.setString(3, rac.getRp_ws());
			
				ResultSet rsTar = psTar.executeQuery();
				if(rsTar.next())
				{
					rac.setTar_emp_num(rsTar.getInt(1));										
					rac.setTar_qty(rsTar.getBigDecimal(2));
				}
				rsTar.close();
				psTar.close();
				
				doc.getRaclist().add(rac);
				
				//报工员工
				doc.setEmplist(new ArrayList<EMP_MAIN_VIEW>());
				
				psEmp=conn.prepareStatement("SELECT T1.EMP_GUID,T2.EMP_MAIN_ID,T2.EMP_NAME FROM SWS_STAFF T1,EMP_MAIN T2 WHERE T1.EMP_GUID=T2.EMP_MAIN_GUID AND T1.SWS_RP_GUID=? AND T1.SWS_GUID=?");
				psEmp.setString(1,swsRpGuid);
				psEmp.setString(2,  doc.getSwsdoc().getGuid());
				rsEmp=psEmp.executeQuery();
				while(rsEmp.next()){
					EMP_MAIN_VIEW emp=new EMP_MAIN_VIEW();
					emp.setEmp_main_guid(rsEmp.getString(1));
					emp.setEmp_main_id(rsEmp.getString(2));
					emp.setEmp_name(rsEmp.getString(3));
					
					doc.getEmplist().add(emp);
				}
				
				psEmp.close();
				rsEmp.close();
			}
			
			ps.close();
			rs.close();
					
			return doc;
			
		}catch(Exception e){
			throw e;
		}
	}
	
	//获取流程票所有开工工序中的员工
	//Added by xiz on 2014/10/14 
	public static List<EMP_MAIN_VIEW> GetSwsRpEmpsBySwsGuid(String swsGuid,Connection conn) throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		List<EMP_MAIN_VIEW> empList=new ArrayList<>();
		
		try{
			String sql="SELECT T3.EMP_MAIN_GUID,T3.EMP_MAIN_ID,T2.RP_RAC_NAME,T2.RP_RAC_ID "
					+ " FROM SWS_STAFF T1,SWS_RP T2,EMP_MAIN T3 "
					+ " WHERE T1.SWS_RP_GUID=T2.SWS_RP_GUID "
					+ " AND T1.EMP_GUID=T3.EMP_MAIN_GUID"
					+ " AND T1.IS_DELETED='0' AND T2.IS_DELETED='0' AND T3.IS_DELETED='0'"
					+ " AND T2.RP_STATUS=1 AND T2.SWS_GUID=? ";
			
		    ps=conn.prepareStatement(sql);
			ps.setString(1, swsGuid);
			
			rs=ps.executeQuery();

			while(rs.next()){
				int index=0;
				EMP_MAIN_VIEW empView=new EMP_MAIN_VIEW();
				empView.setEmp_main_guid(rs.getString(++index));
				empView.setEmp_main_id(rs.getString(++index));
				empView.setRp_rac_name(rs.getString(++index));
				empView.setRp_rac_id(rs.getString(++index));
				
				empList.add(empView);
			}
			ps.close();
			rs.close();
			
			return empList;
			
		}catch(Exception e){
			throw e;
		}finally{
			if(ps!=null&&ps.isClosed()==false){ps.close();}
			if(rs!=null&&rs.isClosed()==false){rs.close();}
		}
		
	}
	
	//根据流程票+员工Id+设备号 唯一确定报工单  
	//Added by xiz on 2014/10/16
	//Updated by xiz on 2014/11/3
	public static SWS_DOC GetSwsRpDoc(String swsId,String empId,String wsNo,Connection conn,Connection connErp) throws Exception{
		SWS_DOC doc=new SWS_DOC();
		try{
			//流程票
			SUB_WO_SUB_VIEW swsDoc=getSwsOnlyById(swsId,conn);
			if(swsDoc.getGuid().equals(null)||swsDoc.getGuid().equals("")){
				throw new Exception("未找到流程票！");
			}
			
			if(!Erp_Biz.isWoDocNotFinished(swsDoc.getWo_id(), connErp)){
				
				throw new Exception("工单："+swsDoc.getWo_id()+" 在ERP 系统中已完工！");
			}
			
			doc.setSwsdoc(swsDoc);
			doc.setRaclist(new ArrayList<SWS_RAC_VIEW>());
			
			//报工单
			String sql="SELECT T1.SWS_RP_GUID,T1.BG_DT,T1.RP_RAC_ID,T1.RP_RAC_NAME,T1.SCRAP_QTY,T1.RP_WS,T1.RP_WS_NO FROM SWS_RP T1 "
					+ " INNER JOIN SWS_STAFF T2 ON T1.SWS_RP_GUID=T2.SWS_RP_GUID "
					+ " INNER JOIN EMP_MAIN T3 ON T2.EMP_GUID=T3.EMP_MAIN_GUID "
					+ " AND T1.SWS_GUID=? AND T1.RP_STATUS=1 AND T3.EMP_MAIN_ID=? ";
			if(wsNo!=null&&!wsNo.equals("")){
				sql=sql+" AND T1.RP_WS_NO=?";
			}
			
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setString(1, swsDoc.getGuid());
			ps.setString(2, empId);
			
			if(wsNo!=null&&!wsNo.equals("")){
			ps.setString(3, wsNo);
			}
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				SWS_RAC_VIEW rac=new SWS_RAC_VIEW();
				rac.setSws_rp_guid(rs.getString(1));
				rac.setBg_date(rs.getLong(2));
				rac.setRac_seqno(rs.getString(3));
				rac.setRac_name(rs.getString(4));
				rac.setScrap_qty(rs.getBigDecimal(5));
				rac.setRp_ws(rs.getString(6));
				rac.setRp_ws_no(rs.getString(7));			
				
				//本道工序的可报工数量
				BigDecimal lastRacQty=GetRacAvaliableQty(swsDoc.getGuid(),rac.getRac_seqno()
						,swsDoc.getCut_seqno(),swsDoc.getSws_qty(),conn);
				rac.setRp_qty(lastRacQty);
				
				doc.getRaclist().add(rac);				
			}
			ps.close();
			rs.close();
			
			if(doc.getRaclist().size()<=0){
				throw new Exception("未找到符合条件的报工单！");
			}
		
//			if(doc.getRaclist().size()>1){
//				throw new Exception("找到多个报个单！");
//				
//			}
			
			//报工员工
			if(doc.getRaclist().size()==1){
			doc.setEmplist(new ArrayList<EMP_MAIN_VIEW>());
			
			PreparedStatement psEmp=conn.prepareStatement("SELECT T1.EMP_GUID,T2.EMP_MAIN_ID,T2.EMP_NAME FROM SWS_STAFF T1,EMP_MAIN T2 WHERE T1.EMP_GUID=T2.EMP_MAIN_GUID AND T1.SWS_RP_GUID=? AND T1.SWS_GUID=?");
			psEmp.setString(1,doc.getRaclist().get(0).getSws_rp_guid());
			psEmp.setString(2,  doc.getSwsdoc().getGuid());
			ResultSet rsEmp=psEmp.executeQuery();
			while(rsEmp.next()){
				EMP_MAIN_VIEW emp=new EMP_MAIN_VIEW();
				emp.setEmp_main_guid(rsEmp.getString(1));
				emp.setEmp_main_id(rsEmp.getString(2));
				emp.setEmp_name(rsEmp.getString(3));
				
				doc.getEmplist().add(emp);
			}
			
			psEmp.close();
			rsEmp.close();

			}
			
			return doc;
			
		}catch(Exception e){
			throw e;
		}
	}
	
	public static List<SWS_RP> GetSwsRpBySwsIdAndEmpId(String swsId,String empId,Connection conn) throws Exception{

		List<SWS_RP> result=new ArrayList<SWS_RP>();
				
		//流程票
		SUB_WO_SUB_VIEW swsDoc=getSwsOnlyById(swsId,conn);
		if(swsDoc.getGuid().equals(null)||swsDoc.getGuid().equals("")){
			throw new Exception("未找到流程票！");
		}					
		
		//报工单
		String sql="SELECT T1.SWS_RP_GUID,T1.BG_DT,T1.RP_RAC_ID,T1.RP_RAC_NAME,T1.SCRAP_QTY,T1.RP_WS,T1.RP_WS_NO FROM SWS_RP T1 "
				+ " INNER JOIN SWS_STAFF T2 ON T1.SWS_RP_GUID=T2.SWS_RP_GUID "
				+ " INNER JOIN EMP_MAIN T3 ON T2.EMP_GUID=T3.EMP_MAIN_GUID "
				+ " AND T1.SWS_GUID=? AND T1.RP_STATUS=1 AND T3.EMP_MAIN_ID=?";
		
		PreparedStatement ps=conn.prepareStatement(sql);		
		ps.setString(1, swsDoc.getGuid());
		ps.setString(2, empId);
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			SWS_RP rp=new SWS_RP();
			
			rp.setGuid(rs.getString(1));
			rp.setBg_dt(rs.getLong(2));
			rp.setRp_rac_id(rs.getString(3));
			rp.setRp_rac_name(rs.getString(4));
			rp.setScrap_qty(rs.getBigDecimal(5));
			rp.setRp_ws(rs.getString(6));
			rp.setRp_ws_no(rs.getString(7));
			
			result.add(rp);		
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static String addSwsRp(SWS_RP_DOC doc, Connection conn) throws Exception
	{
		//校验 本道工序是否正在开工--------------------------------------------
		//For 每道工序只允许存在一个开工状态
		PreparedStatement psCheckRac=conn.prepareStatement("SELECT SWS_RP_GUID FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=? AND RP_STATUS = 1");
		psCheckRac.setString(1, doc.getHead().getSws_guid());
		psCheckRac.setString(2, doc.getHead().getRp_rac_id());
		ResultSet rsCheckRac = psCheckRac.executeQuery();
		if(rsCheckRac.next()){
			psCheckRac.close();
			rsCheckRac.close();
			
			throw new Exception("流程票工序："+doc.getHead().getRp_rac_name()+ " 开工中，不允许再次开工！");
		}
		psCheckRac.close();
		rsCheckRac.close();
		
		//校验 是否已经报满工-------------------------------------------------------
		BigDecimal rpedQty = BigDecimal.ZERO;
		BigDecimal swsQty = BigDecimal.ZERO;
		
		//本道工序完工量汇总+本道工序报废量汇总
		rpedQty=GetRPTotalFinishedAndScrapQty(doc.getHead().getSws_guid(),doc.getHead().getRp_rac_id(),conn);
		
		//本流程票的生产数量
		PreparedStatement psGetswsQty = conn.prepareStatement("SELECT ISNULL(SWS_QTY,0) FROM SUB_WO_SUB WHERE SUB_WO_SUB_GUID=?");
		psGetswsQty.setString(1, doc.getHead().getSws_guid());
		ResultSet rsGetswsQty = psGetswsQty.executeQuery();
		if(rsGetswsQty.next()){
			swsQty = rsGetswsQty.getBigDecimal(1);
		}
		rsGetswsQty.close();
		psGetswsQty.close();
		
		if(swsQty.compareTo(rpedQty)<=0){
			throw new Exception("流程票工序："+doc.getHead().getRp_rac_name()+" 报工已满，不能再开工！");
		}			

		//插入报工单
		String docGuid = UUID.randomUUID().toString();
		
		PreparedStatement psInsertSwsRp = conn.prepareStatement("INSERT INTO SWS_RP(SWS_RP_GUID,SWS_RP_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,FINISH_QTY,SCRAP_QTY,RP_DT,BG_DT,SWS_GUID,RP_RAC_ID,RP_RAC_NAME,RP_STATUS,RP_WS,RP_WS_NO,WORK_TARGET,B_PDA_ID,E_PDA_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		psInsertSwsRp.setString(1, docGuid);
		psInsertSwsRp.setString(2, docGuid);
		psInsertSwsRp.setLong(3, new Date().getTime());
		psInsertSwsRp.setString(4, doc.getHead().getCreated_by());
		psInsertSwsRp.setLong(5, new Date().getTime());
		psInsertSwsRp.setString(6, doc.getHead().getUpdated_by());
		psInsertSwsRp.setString(7, doc.getHead().getClient_guid());
		psInsertSwsRp.setInt(8, 0);
		psInsertSwsRp.setString(9, doc.getHead().getData_ver());
		
		//FINISH_QTY,SCRAP_QTY,RP_DT,BG_DT,SWS_GUID,RP_RAC_ID,RP_RAC_NAME,RP_STATUS
		psInsertSwsRp.setBigDecimal(10, doc.getHead().getFinish_qty());
		psInsertSwsRp.setBigDecimal(11, doc.getHead().getScrap_qty());
		psInsertSwsRp.setLong(12, doc.getHead().getRp_dt());
		//psInsertSwsRp.setLong(13, doc.getHead().getBg_dt());
		psInsertSwsRp.setLong(13, new Date().getTime());
		psInsertSwsRp.setString(14, doc.getHead().getSws_guid());
		psInsertSwsRp.setString(15, doc.getHead().getRp_rac_id());
		psInsertSwsRp.setString(16, doc.getHead().getRp_rac_name());
		psInsertSwsRp.setInt(17, doc.getHead().getRp_status());
		psInsertSwsRp.setString(18, doc.getHead().getRp_ws());
		psInsertSwsRp.setString(19, doc.getHead().getRp_ws_no());
		psInsertSwsRp.setBigDecimal(20, doc.getHead().getRp_tar_value());
		psInsertSwsRp.setString(21, doc.getHead().getB_pda_id());
		psInsertSwsRp.setString(22, doc.getHead().getE_pda_id());
		
		psInsertSwsRp.execute();
		psInsertSwsRp.close();
		psInsertSwsRp = null;
		
		for(int i=0;i<doc.getBody().size();i++)
		{
			String bodyGuid = UUID.randomUUID().toString();
			
			PreparedStatement psInsertSwsStaff = conn.prepareStatement("INSERT INTO SWS_STAFF(SWS_STAFF_GUID,SWS_STAFF_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,SWS_GUID,EMP_GUID,SWS_RP_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			psInsertSwsStaff.setString(1, bodyGuid);
			psInsertSwsStaff.setString(2, bodyGuid);
			psInsertSwsStaff.setLong(3, new Date().getTime());
			psInsertSwsStaff.setString(4, doc.getBody().get(i).getCreated_by());
			psInsertSwsStaff.setLong(5, new Date().getTime());
			psInsertSwsStaff.setString(6, doc.getBody().get(i).getUpdated_by());
			psInsertSwsStaff.setString(7, doc.getBody().get(i).getClient_guid());
			psInsertSwsStaff.setInt(8, 0);
			psInsertSwsStaff.setString(9, doc.getBody().get(i).getData_ver());
			
			psInsertSwsStaff.setString(10, doc.getBody().get(i).getSws_guid());
			psInsertSwsStaff.setString(11, doc.getBody().get(i).getEmp_guid());
			psInsertSwsStaff.setString(12, docGuid);
			psInsertSwsStaff.execute();
			psInsertSwsStaff.close();
		}

		PreparedStatement psUpdateSws = conn.prepareStatement("UPDATE SUB_WO_SUB SET SWS_STATUS=0,RP_STATUS=1 WHERE SUB_WO_SUB_GUID=?");
		psUpdateSws.setString(1, doc.getHead().getSws_guid());
		psUpdateSws.execute();
		
		return docGuid;
	}
	
	public static void upSwsRp(SWS_RP_DOC doc, Connection conn) throws Exception
	{
		String rp_guid = "";
		PreparedStatement psGetRpGuid = conn.prepareStatement("SELECT SWS_RP_GUID FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=? AND RP_STATUS=1");
		psGetRpGuid.setString(1, doc.getHead().getSws_guid());
		psGetRpGuid.setString(2, doc.getHead().getRp_rac_id());
		ResultSet rsGetRpGuid= psGetRpGuid.executeQuery();
		if(rsGetRpGuid.next())
		{
			rp_guid = rsGetRpGuid.getString(1);
		}
		else
		{
			throw new Exception("未找到报工单！");
		}
		rsGetRpGuid.close();
		rsGetRpGuid = null;
		psGetRpGuid.close();
		psGetRpGuid=null;
		
		PreparedStatement psDelSwsStaff = conn.prepareStatement("DELETE FROM SWS_STAFF WHERE SWS_RP_GUID=?");
		psDelSwsStaff.setString(1, rp_guid);
		psDelSwsStaff.execute();
		psDelSwsStaff.close();
		psDelSwsStaff=null;
		
		for(int i=0;i<doc.getBody().size();i++)
		{
			String bodyGuid = UUID.randomUUID().toString();
			
			PreparedStatement psInsertSwsStaff = conn.prepareStatement("INSERT INTO SWS_STAFF(SWS_STAFF_GUID,SWS_STAFF_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,SWS_GUID,EMP_GUID,SWS_RP_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			psInsertSwsStaff.setString(1, bodyGuid);
			psInsertSwsStaff.setString(2, bodyGuid);
			psInsertSwsStaff.setLong(3, new Date().getTime());
			psInsertSwsStaff.setString(4, doc.getBody().get(i).getCreated_by());
			psInsertSwsStaff.setLong(5, new Date().getTime());
			psInsertSwsStaff.setString(6, doc.getBody().get(i).getUpdated_by());
			psInsertSwsStaff.setString(7, doc.getBody().get(i).getClient_guid());
			psInsertSwsStaff.setInt(8, 0);
			psInsertSwsStaff.setString(9, doc.getBody().get(i).getData_ver());
			
			psInsertSwsStaff.setString(10, doc.getBody().get(i).getSws_guid());
			psInsertSwsStaff.setString(11, doc.getBody().get(i).getEmp_guid());
			psInsertSwsStaff.setString(12, rp_guid);
			psInsertSwsStaff.execute();
			psInsertSwsStaff.close();
		}

		PreparedStatement psUpdateSws = conn.prepareStatement("UPDATE SUB_WO_SUB SET SWS_STATUS=0,RP_STATUS=1 WHERE SUB_WO_SUB_GUID=?");
		psUpdateSws.setString(1, doc.getHead().getSws_guid());
		psUpdateSws.execute();
		psUpdateSws.close();
		
		PreparedStatement psUpdateRp = conn.prepareStatement("UPDATE SWS_RP SET BG_DT=?,RP_WS_NO=? WHERE SWS_RP_GUID=?");		
		psUpdateRp.setLong(1, doc.getHead().getBg_dt());
		psUpdateRp.setString(2, doc.getHead().getRp_ws_no());
		psUpdateRp.setString(3, doc.getHead().getGuid());
		psUpdateRp.execute();
		psUpdateRp.close();
	}
	
	public static void doSwsRp(SWS_RP_DOC doc, String erpDocId, String erpDocType, Connection conn,Connection erp_conn) throws Exception
	{	
		//1.1校验工序是否是开工状态
		PreparedStatement psCheckRac=conn.prepareStatement("SELECT RP_STATUS FROM SWS_RP WHERE SWS_RP_GUID=? ");
		psCheckRac.setString(1, doc.getHead().getGuid());
		ResultSet rsCheckRac = psCheckRac.executeQuery();
		if(rsCheckRac.next()){			
			if(rsCheckRac.getInt(1)!=1)
			{
				psCheckRac.close();
				rsCheckRac.close();
				throw new Exception("报工单未开工或者已报工！");
			}
		}
		else{
			psCheckRac.close();
			rsCheckRac.close();
			
			throw new Exception("未找到报工单！");
		}
		psCheckRac.close();
		rsCheckRac.close();
		
		//////////////////////////////////////////////////////////////////////
		String workers = "";
		for(int i=0;i<doc.getBody().size();i++)
		{
			if(i==doc.getBody().size()-1){
				workers = workers+doc.getBody().get(i).getEmp_id();
				break;
			}
			workers = workers+doc.getBody().get(i).getEmp_id()+"/";
		}
		
		//2、报工
		PreparedStatement psUpRp = conn.prepareStatement("UPDATE SWS_RP SET RP_STATUS=2,RP_DT=?,FINISH_QTY=?,UPDATED_DT=?,UPDATED_BY=?,EMP_ID_LIST=?,ERP_DOC_TYPE=?,ERP_DOC_ID=?,E_PDA_ID=? WHERE SWS_RP_GUID=?");
		psUpRp.setLong(1, doc.getHead().getRp_dt());
		psUpRp.setBigDecimal(2, doc.getHead().getFinish_qty());
		psUpRp.setLong(3, new Date().getTime());
		psUpRp.setString(4, doc.getHead().getUpdated_by());
		psUpRp.setString(5, workers);
		psUpRp.setString(6, erpDocType);
		psUpRp.setString(7, erpDocId);
		psUpRp.setString(8, doc.getHead().getE_pda_id());
		psUpRp.setString(9, doc.getHead().getGuid());

		psUpRp.executeUpdate();
		psUpRp.close();
		psUpRp = null;

		//流程票所有报工单上的报废数量汇总（报废时会更新报工单上的报废数量）
		BigDecimal scrapedQty=BigDecimal.ZERO;

		PreparedStatement psSum=conn.prepareStatement("SELECT ISNULL(SUM(SCRAP_QTY),0) FROM SWS_RP WHERE SWS_GUID=?");
		psSum.setString(1, doc.getHead().getSws_guid());
		ResultSet rsSum=psSum.executeQuery();
		if(rsSum.next()){
			scrapedQty=rsSum.getBigDecimal(1);
		}
		psSum.close();
		rsSum.close();
		//更新流程票的报废数量
		PreparedStatement psUpdateSws = conn.prepareStatement("UPDATE SUB_WO_SUB SET SWS_STATUS=0,RP_STATUS=2,UPDATED_DT=?,UPDATED_BY=?,SCRAP_QTY=? WHERE SUB_WO_SUB_GUID=?");
		psUpdateSws.setLong(1, new Date().getTime());
		psUpdateSws.setString(2, doc.getHead().getUpdated_by());
		psUpdateSws.setBigDecimal(3, scrapedQty);
		psUpdateSws.setString(4, doc.getHead().getSws_guid());
		psUpdateSws.execute();
		psUpdateSws.close();
		psUpdateSws = null;
		
		//////////////////////////////////////////////////////////////////////
		
		//3、如果是最后一个工序报工，修改流程票完工数量，修改容器数量
		PreparedStatement psGetLastRacId = conn.prepareStatement("SELECT TOP 1 T3.WO_RAC_ID,T1.CTN_GUID FROM SUB_WO_SUB T1 JOIN SUB_WO_MAIN T2 ON T1.SUB_WO_MAIN_GUID = T2.SUB_WO_MAIN_GUID JOIN WO_RAC T3 ON T3.WO_DOC_ID=T2.WO_ID WHERE T1.SUB_WO_SUB_GUID=? ORDER BY T3.WO_RAC_ID DESC");
		psGetLastRacId.setString(1,  doc.getHead().getSws_guid());
		ResultSet rsGetLastRacId = psGetLastRacId.executeQuery();
		if(rsGetLastRacId.next())
		{
			if(rsGetLastRacId.getString(1).equals(doc.getHead().getRp_rac_id()))
			{
				BigDecimal finishQty=BigDecimal.ZERO;			
				//获取最后一道工序的完工数量
				PreparedStatement psTotalFinishQty=conn.prepareStatement("SELECT ISNULL(SUM(FINISH_QTY),0) FROM SWS_RP WHERE IS_DELETED='0' AND SWS_GUID=? AND RP_RAC_ID=?");
				psTotalFinishQty.setString(1, doc.getHead().getSws_guid());
				psTotalFinishQty.setString(2, doc.getHead().getRp_rac_id());
				ResultSet rsTotalFinishQty=psTotalFinishQty.executeQuery();
				if(rsTotalFinishQty.next()){
					finishQty=rsTotalFinishQty.getBigDecimal(1);
				}
				//更新流程票的完工数量
				PreparedStatement psUpdateSwsFinishQty = conn.prepareStatement("UPDATE SUB_WO_SUB SET FINISH_QTY=? WHERE SUB_WO_SUB_GUID=?");
				psUpdateSwsFinishQty.setBigDecimal(1, finishQty);
				psUpdateSwsFinishQty.setString(2,  doc.getHead().getSws_guid());
				psUpdateSwsFinishQty.execute();
				psUpdateSwsFinishQty.close();
				psUpdateSwsFinishQty = null;
				//更新容器数量
				PreparedStatement psUpdateCtnQty = conn.prepareStatement("UPDATE CTN_MAIN SET ITM_QTY=? WHERE CTN_MAIN_GUID=?");
				psUpdateCtnQty.setBigDecimal(1, finishQty);
				psUpdateCtnQty.setString(2,  rsGetLastRacId.getString(2));
				psUpdateCtnQty.execute();
				psUpdateCtnQty.close();
				psUpdateCtnQty = null;
			}
		}
		rsGetLastRacId.close();
		rsGetLastRacId = null;
		psGetLastRacId.close();
		psGetLastRacId = null;
	}
	
	public static String addScrap(SWS_SCRAP_VIEW scrap,String operator, Connection conn) throws SQLException
	{
		String guid = UUID.randomUUID().toString();
		
		PreparedStatement psInsert = conn.prepareStatement("INSERT INTO SWS_SCRAP(SWS_SCRAP_GUID,SWS_SCRAP_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,SCRAP_TYPE,SCRAP_REASON_ID,SCRAP_REASON_NAME,RP_GUID,SWS_GUID,RAC_ID,SCRAP_QTY,SCRAP_PART,EMP_ID,INSPECTOR_ID,HAPPEN_RAC_SEQNO,HAPPEN_RAC_NAME,SCRAP_CONTENT_ID,SCRAP_CONTENT_NAME,SWS_ID,WO_DOC_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		psInsert.setString(1, guid);
		psInsert.setString(2, guid);
		psInsert.setLong(3, new Date().getTime());
		psInsert.setString(4, operator);
		psInsert.setLong(5, new Date().getTime());
		psInsert.setString(6, operator);
		psInsert.setString(7, "gl");
		psInsert.setInt(8, 0);
		psInsert.setString(9, "1.0.0.0");
		psInsert.setInt(10, scrap.getScrap_type());
		psInsert.setString(11, scrap.getScrap_reason_id());
		psInsert.setString(12, scrap.getScrap_reason_name());
		psInsert.setString(13, scrap.getRp_guid());
		psInsert.setString(14, scrap.getSws_guid());
		psInsert.setString(15, scrap.getRac_id());
		psInsert.setBigDecimal(16, scrap.getScrap_qty());
		psInsert.setString(17, scrap.getScrap_part());
		psInsert.setString(18, scrap.getEmp_id());
		psInsert.setString(19, scrap.getInspector());
		
//		psInsert.setString(20, scrap.getHappen_rac_seqno());
//		psInsert.setString(21, scrap.getHappen_rac_name());
		psInsert.setString(20, scrap.getRac_id());
		psInsert.setString(21, scrap.getRac_name());
		
		psInsert.setString(22, scrap.getScrap_content_id());
		psInsert.setString(23, scrap.getScrap_content_name());
		
		psInsert.setString(24, scrap.getSws_id());
		psInsert.setString(25, scrap.getWo_id());
		
		psInsert.execute();
		psInsert.close();
		psInsert = null;
		
		//整体报废
		if(scrap.getScrap_type()==0){
			BigDecimal scrapedQty=BigDecimal.ZERO;
			//报工单报废数量汇总
			PreparedStatement psSum=conn.prepareStatement("SELECT ISNULL(SUM(SCRAP_QTY),0) FROM SWS_SCRAP WHERE RP_GUID=?");
			psSum.setString(1, scrap.getRp_guid());
			ResultSet rsSum=psSum.executeQuery();
			if(rsSum.next()){
				scrapedQty=rsSum.getBigDecimal(1);
			}
			psSum.close();
			rsSum.close();
			
			PreparedStatement psUpdate = conn.prepareStatement("UPDATE SWS_RP SET SCRAP_QTY=? WHERE SWS_RP_GUID=?");
			psUpdate.setBigDecimal(1, scrapedQty);
			psUpdate.setString(2, scrap.getRp_guid());
			psUpdate.execute();
			psUpdate.close();
		}
		
//		if(scrap.getScrap_type()==0&&scrap.getScrap_on_rac()==0){
//			PreparedStatement psUpdate = conn.prepareStatement("UPDATE SWS_RP SET SCRAP_QTY=ISNULL(SCRAP_QTY,0)+? WHERE SWS_RP_GUID=?");
//			psUpdate.setBigDecimal(1, scrap.getScrap_qty());
//			psUpdate.setString(2, scrap.getRp_guid());
//			psUpdate.execute();
//			psUpdate.close();
//			psUpdate =null;
//		}
//		
//		if(scrap.getScrap_type()==0&&scrap.getScrap_on_rac()==1)
//		{
//			PreparedStatement psUpdate = conn.prepareStatement("UPDATE SUB_WO_SUB SET SCRAP_QTY=ISNULL(SCRAP_QTY,0)+? WHERE SUB_WO_SUB_GUID=?");
//			psUpdate.setBigDecimal(1, scrap.getScrap_qty());
//			psUpdate.setString(2, scrap.getSws_guid());
//			psUpdate.execute();
//			psUpdate.close();
//			psUpdate =null;
//		}
		
		return guid;
	}
	
	public static ERP_RDA_DOC getErpRdaDocBySwsRpDoc(SWS_RP_DOC doc, Connection conn) throws Exception
	{
		ERP_RDA_DOC rdaDoc = new ERP_RDA_DOC();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		pstmt = conn.prepareStatement("SELECT T1.ITM_ID,T2.LOT_ID,T2.WO_ID,T2.STOCK_AREA FROM SUB_WO_SUB T1,SUB_WO_MAIN T2 WHERE T1.SUB_WO_SUB_GUID=? AND T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID");
		pstmt.setString(1,doc.getHead().getSws_guid());
		
		rs = pstmt.executeQuery();
		if (rs.next()) {
			rdaDoc.setBg_dt(doc.getHead().getBg_dt());
			rdaDoc.setFinish_qty(doc.getHead().getFinish_qty());
			rdaDoc.setItm_id(rs.getString(1));
			rdaDoc.setLot_id(rs.getString(2));
			
			PreparedStatement pstmtRac = conn.prepareStatement("SELECT TOP 2 WO_RAC_ID,RAC_ID FROM WO_RAC WHERE WO_DOC_ID=? AND WO_RAC_ID>=?");
			pstmtRac.setString(1, rs.getString(3));
			pstmtRac.setString(2, doc.getHead().getRp_rac_id());
			ResultSet rsRac = pstmtRac.executeQuery();
			if(rsRac.next()){ //当前工艺
				rdaDoc.setRac_id(rsRac.getString(2));
				rdaDoc.setRac_seqno(rsRac.getString(1));
				
				if(rsRac.next()){ //下个工艺
					rdaDoc.setNext_rac_id(rsRac.getString(2));
					rdaDoc.setNext_rac_seqno(rsRac.getString(1));
					rdaDoc.setWh_id("");
				}
				else{ //需要工艺入库

					rdaDoc.setWh_id("02");
				}
				rsRac.close();
				pstmtRac.close();
			}
			else{
				throw new Exception("未找到工艺信息！");
			}
			
			rdaDoc.setRp_dt(doc.getHead().getRp_dt());
			
			//获取报工单的报废数量
			BigDecimal scrapedQty=BigDecimal.ZERO;
			PreparedStatement psScrap=conn.prepareStatement("SELECT ISNULL(SCRAP_QTY,0) FROM SWS_RP WHERE SWS_RP_GUID=?");
			psScrap.setString(1, doc.getHead().getGuid());
			ResultSet rsScrap=psScrap.executeQuery();
			if(rsScrap.next()){
				scrapedQty=rsScrap.getBigDecimal(1);
				
			}
			psScrap.close();
			rsScrap.close();
			rdaDoc.setScrap_qty(scrapedQty);
			
//			rdaDoc.setScrap_qty(doc.getHead().getScrap_qty());
			
			rdaDoc.setWo_id(rs.getString(3));
			rdaDoc.setWs_id(doc.getHead().getRp_ws());
			rdaDoc.setSws_id(doc.getHead().getSws_id());
			rdaDoc.setRp_wo_no(doc.getHead().getRp_ws_no());
			
			rdaDoc.setEmpIds( new ArrayList<String>() );
			for(int i=0;i<doc.getBody().size();i++)
			{
				rdaDoc.getEmpIds().add(doc.getBody().get(i).getEmp_id());
			}
		}
		else{
			throw new Exception("未找到流程票信息！");
		}
			
		return rdaDoc;
	}
	
	public static List<SWS_RP_VIEW> getSwsRpListBySwsGuid(String SwsGuid,Connection conn) throws SQLException
	{
		List<SWS_RP_VIEW> resultList = new ArrayList<SWS_RP_VIEW>();
		
		PreparedStatement ps = conn.prepareStatement("SELECT FINISH_QTY,SCRAP_QTY,RP_DT,BG_DT,SWS_GUID,RP_RAC_ID,RP_RAC_NAME,RP_STATUS,RP_WS,EMP_ID_LIST,ERP_DOC_TYPE,ERP_DOC_ID,SWS_RP_GUID FROM SWS_RP WHERE SWS_GUID=? ORDER BY CREATED_DT DESC");
		ps.setString(1, SwsGuid);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			SWS_RP_VIEW tempData = new SWS_RP_VIEW();
			tempData.setFinish_qty(rs.getBigDecimal(1));
			tempData.setScrap_qty(rs.getBigDecimal(2));
			tempData.setRp_dt(rs.getLong(3));
			tempData.setBg_dt(rs.getLong(4));
			tempData.setSws_guid(rs.getString(5));
			tempData.setRp_rac_id(rs.getString(6));
			tempData.setRp_rac_name(rs.getString(7));
			tempData.setRp_status(rs.getInt(8));
			tempData.setRp_ws(rs.getString(9));
			tempData.setEmp_ids(rs.getString(10));
			tempData.setErp_doc_type(rs.getString(11));
			tempData.setErp_doc_id(rs.getString(12));
			tempData.setGuid(rs.getString(13));
			
			//获取已开工未报工的工序的员工
			if(tempData.getRp_status()==1){
				String emps="";
				
				PreparedStatement psEmp=conn.prepareStatement("SELECT T2.EMP_MAIN_ID FROM SWS_STAFF T1 INNER JOIN EMP_MAIN T2 ON T1.EMP_GUID=T2.EMP_MAIN_GUID WHERE SWS_RP_GUID=?");
				psEmp.setString(1, tempData.getGuid());
				ResultSet rsEmp=psEmp.executeQuery();
				while(rsEmp.next()){
					emps+="/"+rsEmp.getString(1);
				}
				
				if(emps.length()>0){
					emps=emps.substring(1);
					tempData.setEmp_ids(emps);
				}				
			}
			
			resultList.add(tempData);
		}
		rs.close();
		ps.close();
		
		return resultList;
	}
	
	public static void delSwsRp_pda(String rpGuid,Connection conn ) throws Exception{
		PreparedStatement psDel = null;
		PreparedStatement psUp = null;
		
		PreparedStatement ps = conn.prepareStatement("SELECT RP_STATUS,SWS_GUID,ERP_DOC_TYPE,ERP_DOC_ID FROM SWS_RP WHERE SWS_RP_GUID=?");
		ps.setString(1, rpGuid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			if(rs.getInt(1)==1){
				psDel = conn.prepareStatement("DELETE FROM SWS_STAFF WHERE SWS_RP_GUID=?");
				psDel.setString(1, rpGuid);
				psDel.execute();
				psDel.close();
				
				psDel = conn.prepareStatement("DELETE FROM SWS_RP WHERE SWS_RP_GUID=?");
				psDel.setString(1, rpGuid);
				psDel.execute();
				psDel.close();
				
				psUp = conn.prepareStatement("UPDATE SUB_WO_SUB SET RP_STATUS=2 WHERE SUB_WO_SUB_GUID=?");
				psUp.setString(1, rs.getString(2));
				psUp.execute();
				psUp.close();
				
				rs.close();
				ps.close();
			}
			else{
				rs.close();
				ps.close();
				
				throw new Exception("该单证不允许删除！");
			}
		}
		else{
			rs.close();
			ps.close();
			
			throw new Exception("该单证不存在！");
		}
	}
	
	public static void delSwsRp(String rpGuid,Connection conn,Connection erpConn) throws Exception{
		PreparedStatement psDel = null;
		PreparedStatement psUp = null;
		
		PreparedStatement ps = conn.prepareStatement("SELECT T1.RP_STATUS,T1.SWS_GUID,T1.ERP_DOC_TYPE,T1.ERP_DOC_ID,ISNULL(T1.FINISH_QTY,0),T2.SUB_WO_SUB_ID,T1.RP_RAC_ID,ISNULL(T1.SCRAP_QTY,0) "
				+ "FROM SWS_RP T1 JOIN SUB_WO_SUB T2 ON T1.SWS_GUID=T2.SUB_WO_SUB_GUID WHERE T1.SWS_RP_GUID=?");
		ps.setString(1, rpGuid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			if(rs.getInt(1)==1){
				//删除报废单
				psDel = conn.prepareStatement("DELETE FROM SWS_SCRAP WHERE RP_GUID=?");
				psDel.setString(1, rpGuid);
				psDel.execute();
				psDel.close();
				
				//删除报工员工
				psDel = conn.prepareStatement("DELETE FROM SWS_STAFF WHERE SWS_RP_GUID=?");
				psDel.setString(1, rpGuid);
				psDel.execute();
				psDel.close();
				
				//删除报工单
				psDel = conn.prepareStatement("DELETE FROM SWS_RP WHERE SWS_RP_GUID=?");
				psDel.setString(1, rpGuid);
				psDel.execute();
				psDel.close();
				
				//更新流程票报工状态
				psUp = conn.prepareStatement("UPDATE SUB_WO_SUB SET RP_STATUS=2 WHERE SUB_WO_SUB_GUID=?");
				psUp.setString(1, rs.getString(2));
				psUp.execute();
				psUp.close();
				
				rs.close();
				ps.close();
			}
			else if(rs.getInt(1)==2){
				if(Erp_Biz.SgmRdaIsExist(rs.getString(4), rs.getString(3),erpConn)){
					throw new Exception("需要先删除ERP相关单证！");
				}
				
				//当前工序的报废数量汇总
				BigDecimal scrapedQty=GetRPTotalScrapedQty(rs.getString(2),rs.getString(7),conn);
				//减去当前报工单的报废数量
				scrapedQty=scrapedQty.subtract(rs.getBigDecimal(8));
				//更新流程票上的报废数量
				psUp = conn.prepareStatement("UPDATE SUB_WO_SUB SET SCRAP_QTY=? WHERE SUB_WO_SUB_GUID=?");
				psUp.setBigDecimal(1, scrapedQty);
				psUp.setString(2, rs.getString(2));
				psUp.execute();
				psUp.close();
				
				if(rs.getString(3).equals("GYRK"))//最后一道工序
				{				
					//当前工序的完工量汇总
					BigDecimal finishQty=GetRPTotalFinishedQty(rs.getString(2),rs.getString(7),conn);
					//减去当前报工单的完工数量
					finishQty=finishQty.subtract(rs.getBigDecimal(5));					
					
					//更新流程票上的完工数量
					psUp = conn.prepareStatement("UPDATE SUB_WO_SUB SET FINISH_QTY=? WHERE SUB_WO_SUB_GUID=?");
					psUp.setBigDecimal(1, finishQty);
					psUp.setString(2, rs.getString(2));
					psUp.execute();
					psUp.close();
					//跟新容器表中的容器数量
					psUp = conn.prepareStatement("UPDATE CTN_MAIN SET ITM_QTY=? WHERE CTN_BACO=?");
					psUp.setBigDecimal(1, finishQty);
					psUp.setString(2, rs.getString(6));
					psUp.execute();
					psUp.close();
				}
				
				psDel = conn.prepareStatement("DELETE FROM SWS_STAFF WHERE SWS_RP_GUID=?");
				psDel.setString(1, rpGuid);
				psDel.execute();
				psDel.close();
				
				psDel = conn.prepareStatement("DELETE FROM SWS_RP WHERE SWS_RP_GUID=?");
				psDel.setString(1, rpGuid);
				psDel.execute();
				psDel.close();
				
				rs.close();
				ps.close();
			}
			else{
				throw new Exception("未知报工单状态！");
			}
		}
		else{
			throw new Exception("未找到记录！");
		}
	}
	
	public static void delSws(String swsGuid,Connection conn) throws Exception{
		PreparedStatement psDel = null;
		PreparedStatement psGetSwsMain = null;
		PreparedStatement psGetSws = null;
		PreparedStatement psGetSwsCount = null;
		PreparedStatement psUp = null;
		String woId = "";
		
		PreparedStatement ps = conn.prepareStatement("SELECT SWS_RP_ID FROM SWS_RP WHERE SWS_GUID=?");
		ps.setString(1, swsGuid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			rs.close();
			ps.close();
			
			throw new Exception("该流程票下有未删除报工信息，请先删除所有报工信息！");
		}
		else{
			psGetSws = conn.prepareStatement("SELECT SWS_QTY,SUB_WO_MAIN_GUID,SUB_WO_SUB_ID FROM SUB_WO_SUB WHERE SUB_WO_SUB_GUID=?");
			psGetSws.setString(1, swsGuid);
			ResultSet rsSws = psGetSws.executeQuery();
			if(rsSws.next()){
				psGetSwsMain = conn.prepareStatement("SELECT WO_ID FROM SUB_WO_MAIN WHERE SUB_WO_MAIN_GUID=?");
				psGetSwsMain.setString(1, rsSws.getString(2));
				ResultSet rsSwsMain = psGetSwsMain.executeQuery();
				if(rsSwsMain.next()){
					woId = rsSwsMain.getString(1);
					
					rsSwsMain.close();
					psGetSwsMain.close();
				}
				else{
					rsSwsMain.close();
					psGetSwsMain.close();
					
					throw new Exception("未找到大票数据！");
				}
				
				psGetSwsCount = conn.prepareStatement("SELECT COUNT(*) FROM SUB_WO_SUB WHERE SUB_WO_MAIN_GUID=?");
				psGetSwsCount.setString(1,rsSws.getString(2) );
				ResultSet rsSwsCount = psGetSwsCount.executeQuery();
				if(rsSwsCount.next()){
					if(rsSwsCount.getInt(1)>1){
						psUp = conn.prepareStatement("UPDATE SUB_WO_MAIN SET SUB_QTY=SUB_QTY-? WHERE SUB_WO_MAIN_GUID=?");
						psUp.setBigDecimal(1, rsSws.getBigDecimal(1));
						psUp.setString(2, rsSws.getString(2));
						psUp.execute();
						psUp.close();
					}
					else{
						psDel = conn.prepareStatement("DELETE FROM SUB_WO_MAIN WHERE SUB_WO_MAIN_GUID=?");
						psDel.setString(1, rsSws.getString(2));
						psDel.execute();
						psDel.close();;
					}
					
					rsSwsCount.close();
					psGetSwsCount.close();
				}
				
				psUp = conn.prepareStatement("UPDATE WO_DOC SET WO_BIND_QTY=WO_BIND_QTY-? WHERE WO_DOC_ID=?");
				psUp.setBigDecimal(1, rsSws.getBigDecimal(1));
				psUp.setString(2, woId);
				psUp.execute();
				psUp.close();
				
				psDel = conn.prepareStatement("DELETE FROM SUB_WO_SUB WHERE SUB_WO_SUB_GUID=?");
				psDel.setString(1, swsGuid);
				psDel.execute();
				psDel.close();
				
				psDel = conn.prepareStatement("DELETE FROM CTN_MAIN WHERE CTN_BACO=?");
				psDel.setString(1, rsSws.getString(3));
				psDel.execute();
				psDel.close();
				
				rsSws.close();
				psGetSws.close();
				
				rs.close();
				ps.close();
			}			
		}
	}
	
	public static String IsInCodeBind(String empId,Connection conn) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("SELECT CODE_MAIN_ID FROM CODE_MAIN WHERE CODE_VALUE LIKE ?");
		ps.setString(1,"%"+empId+".%");
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			return rs.getString(1);
		}
		return "";
	}
	
	public static List<String> getRacWsList(String woId,String woRacId,Connection conn) throws SQLException
	{
		List<String> resultList = new ArrayList<String>();
		PreparedStatement ps = conn.prepareStatement("SELECT WS_ID,ISNULL(TAR_EMP_NUM,0),ISNULL(TAR_VALUE,0) FROM WO_RAC_TAR WHERE WO_DOC_ID=? AND WO_RAC_ID=?");
		ps.setString(1,woId);
		ps.setString(2, woRacId);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			resultList.add(rs.getString(1)+","+String.valueOf(rs.getInt(2))+","+String.valueOf(rs.getInt(3)));
		}
		return resultList;
	}
	
	public static List<String> getWoRacList(String woId,Connection conn) throws SQLException
	{
		List<String> resultList = new ArrayList<String>();
		PreparedStatement ps = conn.prepareStatement("SELECT WO_RAC_ID FROM WO_RAC WHERE WO_DOC_ID=? ORDER BY WO_RAC_ID");
		ps.setString(1,woId);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			resultList.add(rs.getString(1));
		}
		rs.close();
		ps.close();
		
		return resultList;
	}
	
	public static void delWoRacWs(String woId,String woRacId, Connection conn) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("DELETE FROM WO_RAC_TAR WHERE WO_DOC_ID=? AND WO_RAC_ID=?");
		ps.setString(1,woId);
		ps.setString(2, woRacId);
		ps.execute();

		ps.close();
	}
	
	public static void addWoRacWsValue(String woId,String racId,String wsId,BigDecimal tarValue,int empNum, Connection conn) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("INSERT INTO WO_RAC_TAR(WO_RAC_TAR_GUID,WO_RAC_ID,WO_DOC_ID,WS_ID,TAR_VALUE,TAR_EMP_NUM) VALUES(?,?,?,?,?,?)");
		ps.setString(1, UUID.randomUUID().toString());
		ps.setString(2,racId);
		ps.setString(3,woId);
		ps.setString(4,wsId);
		ps.setBigDecimal(5, tarValue);
		ps.setInt(6, empNum);

		ps.execute();
		ps.close();
	}
	
	public static WO_DOC_VIEW getWoPrintList(WO_DOC_VIEW erpWoDoc,BigDecimal splitQty,String lotId,Connection conn) throws Exception{
		WO_DOC_VIEW tempData= new WO_DOC_VIEW();
		String maxno = "";
		
		//while(woDocQty.compareTo(BigDecimal.ZERO)>0){
			tempData.setItm_id(erpWoDoc.getItm_id());
			tempData.setWo_doc_id(erpWoDoc.getWo_doc_id());
			
			String doc_seqno = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date_str = sdf.format(new Date());
			if(StringUtils.isNullOrEmpty(maxno)){
				PreparedStatement pstmt = conn.prepareStatement("SELECT MAXNO = MAX(CTN_BACO) FROM CTN_MAIN WHERE CTN_BACO LIKE ?");
				pstmt.setString(1, date_str+"%");
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()){
					maxno = rs.getString(1);
					
					maxno=maxno==null?"0":maxno;
					doc_seqno = maxno.replaceFirst(date_str, ""); 
					doc_seqno = Long.parseLong(doc_seqno)+1+"";
					
				}
				rs.close();
				pstmt.close();
			}
			else{
				doc_seqno = maxno.replaceFirst(date_str, ""); 
				doc_seqno = Long.parseLong(doc_seqno)+1+"";
			}
			
			while(doc_seqno.length()<6){
				doc_seqno="0"+doc_seqno;
			}
			String swsId = date_str+doc_seqno;
			maxno = swsId;
			
			tempData.setWo_doc_guid(swsId);
			
			tempData.setItm_qty(splitQty);
			tempData.setLot_id(lotId);
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT ISNULL(DEF_LOC_ID,'') FROM ITM_MAIN WHERE ITM_MAIN_ID=?");
			pstmt.setString(1, erpWoDoc.getItm_id());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				tempData.setStock_area(rs.getString(1));
			}
			
			CTN_MAIN ctn = new CTN_MAIN();
			ctn.setCtn_baco(swsId);
			ctn.setCtn_main_id(swsId);
			ctn.setCtn_status(1);
			ctn.setCtn_type(12);
			ctn.setItm_id(tempData.getItm_id());
			ctn.setItm_qty(tempData.getItm_qty());
			ctn.setParent_ctn_guid("");
			ctn.setWh_area_guid("");
			ctn.setWh_guid("");
			ctn.setWh_loc_guid("");
			ctn.setWh_package_guid("");
			ctn.setWh_plt_guid("");
			ctn.setWh_shelf_guid("");
			ctn.setLot_id(lotId);
			ctn.setCtn_name("工单流程票");
			Common_Biz.addBaco(ctn, "subwo", "1.0.0.0", "gl", conn);
		
		return tempData;
	}
	
	public static void DeleteWo(String woId,Connection conn) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		pstmt = conn.prepareStatement("SELECT COUNT(*) FROM SWS_RP T1,SUB_WO_SUB T2,SUB_WO_MAIN T3 WHERE T1.SWS_GUID=T2.SUB_WO_SUB_GUID AND T2.SUB_WO_MAIN_GUID = T3.SUB_WO_MAIN_GUID AND T3.WO_ID=?");
		pstmt.setString(1,woId);
		rs = pstmt.executeQuery();
		if(rs.next()&&rs.getInt(1)!=0){
			rs.close();
			pstmt.close();
			throw new Exception("该工单有报工信息未删除，请先删除报工信息！");
		}
		rs.close();
		pstmt.close();
		
		pstmt = conn.prepareStatement("SELECT COUNT(*) FROM SWS_SCRAP T1,SUB_WO_SUB T2,SUB_WO_MAIN T3 WHERE T1.SWS_GUID=T2.SUB_WO_SUB_GUID AND T2.SUB_WO_MAIN_GUID = T3.SUB_WO_MAIN_GUID AND T3.WO_ID=?");
		pstmt.setString(1,woId);
		rs = pstmt.executeQuery();
		if(rs.next()&&rs.getInt(1)!=0){
			rs.close();
			pstmt.close();
			throw new Exception("该工单有报废信息未删除，请先删除报废信息！");
		}
		rs.close();
		pstmt.close();
		
		pstmt = conn.prepareStatement("SELECT COUNT(*) FROM SUB_WO_SUB T2,SUB_WO_MAIN T3 WHERE T2.SUB_WO_MAIN_GUID = T3.SUB_WO_MAIN_GUID AND T3.WO_ID=?");
		pstmt.setString(1,woId);
		rs = pstmt.executeQuery();
		if(rs.next()&&rs.getInt(1)!=0){
			rs.close();
			pstmt.close();
			throw new Exception("该工单有流程票信息未删除，请先删除流程票信息！");
		}
		rs.close();
		pstmt.close();
		
		pstmt = conn.prepareStatement("DELETE FROM SUB_WO_MAIN WHERE WO_ID=?");
		pstmt.setString(1,woId);
		pstmt.execute();
		pstmt.close();
		
		pstmt = conn.prepareStatement("DELETE FROM WO_RAC_TAR WHERE WO_DOC_ID=?");
		pstmt.setString(1,woId);
		pstmt.execute();
		pstmt.close();
		
		pstmt = conn.prepareStatement("DELETE FROM WO_RAC WHERE WO_DOC_ID=?");
		pstmt.setString(1,woId);
		pstmt.execute();
		pstmt.close();
		
		pstmt = conn.prepareStatement("DELETE FROM WO_DOC WHERE WO_DOC_ID=?");
		pstmt.setString(1,woId);
		pstmt.execute();
		pstmt.close();
	}
	
	public static SWS_SCRAP_DOC getSwsScrapDoc(String sws_id,int scrapType,Connection conn) throws Exception{
		SWS_SCRAP_DOC resultDoc = null;
		
		PreparedStatement ps = conn.prepareStatement("SELECT T1.SWS_STATUS,T1.RP_STATUS,T1.SUB_WO_SUB_GUID,T1.SUB_WO_SUB_ID,T1.ITM_ID,T1.SWS_QTY-ISNULL(T1.SCRAP_QTY,0),T2.WO_ID FROM SUB_WO_SUB T1,SUB_WO_MAIN T2 WHERE T1.SUB_WO_SUB_ID=? AND T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID");
		ps.setString(1, sws_id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			if(rs.getInt(1)!=0){
				throw new Exception("流程票已经入库！");
			}
			
			resultDoc = new SWS_SCRAP_DOC();
			
			if(rs.getInt(2)!=1){
				//throw new Exception("流程票不在开工中！");
				resultDoc.setScrap_on_rac(1);
			}
			else{
				resultDoc.setScrap_on_rac(0);
			}

			resultDoc.setSws_guid(rs.getString(3));
			resultDoc.setSws_id(rs.getString(4));
			resultDoc.setItm_id(rs.getString(5));
			resultDoc.setSws_qty(rs.getBigDecimal(6));
			
			PreparedStatement psRac = conn.prepareStatement("SELECT BG_DT,RP_RAC_ID,RP_RAC_NAME,RP_WS,RP_WS_NO,WORK_TARGET,SWS_RP_GUID FROM SWS_RP WHERE RP_STATUS=1 AND SWS_GUID=?");
			psRac.setString(1, rs.getString(3));
			ResultSet rsRac = psRac.executeQuery();
			while(rsRac.next()){
				resultDoc.setBg_dt(rsRac.getLong(1));
				resultDoc.setRac_id(rsRac.getString(2));
				resultDoc.setRac_name(rsRac.getString(3));
				resultDoc.setWs_id(rsRac.getString(4));
				resultDoc.setWs_no(rsRac.getString(5));
				resultDoc.setTar_qty(rsRac.getBigDecimal(6));
				resultDoc.setRp_guid(rsRac.getString(7));
			}
			rsRac.close();
			psRac.close();
			
			resultDoc.setRac_list(new ArrayList<RAC_VIEW>());
			PreparedStatement psRacList = conn.prepareStatement("SELECT WO_RAC_ID,RAC_ID,RAC_NAME FROM WO_RAC WHERE WO_DOC_ID=? ORDER BY WO_RAC_ID");
			psRacList.setString(1, rs.getString(7));
			ResultSet rsRacList = psRacList.executeQuery();
			while(rsRacList.next()){
				RAC_VIEW tempRac = new RAC_VIEW();
				tempRac.setRac_seqno(rsRacList.getString(1));
				tempRac.setRac_id(rsRacList.getString(2));
				tempRac.setRac_name(rsRacList.getString(3));
				
				resultDoc.getRac_list().add(tempRac);
			}
			rsRacList.close();
			psRacList.close();
			
			BigDecimal lastRacQty = resultDoc.getSws_qty();
			PreparedStatement psGetLastRac = conn.prepareStatement("SELECT TOP 1 RP_RAC_ID FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID<? ORDER BY RP_RAC_ID DESC");
			psGetLastRac.setString(1, resultDoc.getSws_guid());
			psGetLastRac.setString(2, resultDoc.getRac_id());
			ResultSet rsGetLastRac = psGetLastRac.executeQuery();
			if(rsGetLastRac.next()){
				PreparedStatement psGetLastQty = conn.prepareStatement("SELECT SUM(ISNULL(FINISH_QTY,0)) AS LAST_FINISH_QTY FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=? AND RP_STATUS=2");
				psGetLastQty.setString(1, resultDoc.getSws_guid());
				psGetLastQty.setString(2, rsGetLastRac.getString(1));
				ResultSet rsGetLastQty = psGetLastQty.executeQuery();
				if(rsGetLastQty.next()){
					lastRacQty = rsGetLastQty.getBigDecimal(1);
				}
				rsGetLastQty.close();
				psGetLastQty.close();
			}
			
			PreparedStatement psGetCurrentQty = conn.prepareStatement("SELECT ISNULL(SUM(ISNULL(FINISH_QTY,0)+ISNULL(SCRAP_QTY,0)),0) AS CURRENT_QTY FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=?");
			psGetCurrentQty.setString(1, resultDoc.getSws_guid());
			psGetCurrentQty.setString(2, resultDoc.getRac_id());
			ResultSet rsGetCurrentQty = psGetCurrentQty.executeQuery();
			if(rsGetCurrentQty.next()){
				lastRacQty = lastRacQty.subtract(rsGetCurrentQty.getBigDecimal(1));
			}
			rsGetCurrentQty.close();
			psGetCurrentQty.close();
			
			rsGetLastRac.close();
			psGetLastRac.close();
			resultDoc.setRp_qty(lastRacQty);
			
			if(StringUtils.isNullOrEmpty(resultDoc.getRac_id())){
				PreparedStatement psGetScapedQty = conn.prepareStatement("SELECT ISNULL(SCRAP_QTY,0) FROM SUB_WO_SUB WHERE SUB_WO_SUB_GUID=?");
				psGetScapedQty.setString(1, resultDoc.getSws_guid());
				ResultSet rsGetScrapedQty = psGetScapedQty.executeQuery();
				if(rsGetScrapedQty.next()){
					resultDoc.setScraped_qty(rsGetScrapedQty.getBigDecimal(1));
				}
				rsGetScrapedQty.close();
				psGetScapedQty.close();
			}
			else{
				PreparedStatement psGetScapedQty = conn.prepareStatement("SELECT SUM(ISNULL(SCRAP_QTY,0)) AS CURRENT_SCRAP_QTY FROM SWS_RP WHERE SWS_GUID=? AND RP_RAC_ID=?");
				psGetScapedQty.setString(1, resultDoc.getSws_guid());
				psGetScapedQty.setString(2, resultDoc.getRac_id());
				ResultSet rsGetScrapedQty = psGetScapedQty.executeQuery();
				if(rsGetScrapedQty.next()){
					resultDoc.setScraped_qty(rsGetScrapedQty.getBigDecimal(1));
				}
				rsGetScrapedQty.close();
				psGetScapedQty.close();
			}
			
			resultDoc.setScrap_list(new ArrayList<RAC_SCRAP_VIEW>());
			PreparedStatement psGetScapedList;
			if(StringUtils.isNullOrEmpty(resultDoc.getRac_id())){
				psGetScapedList = conn.prepareStatement("SELECT CREATED_DT,SCRAP_REASON_ID,SCRAP_REASON_NAME,SCRAP_QTY,EMP_ID,INSPECTOR_ID FROM SWS_SCRAP WHERE SWS_GUID=? AND SCRAP_TYPE=?");
				psGetScapedList.setString(1, resultDoc.getSws_guid());
				psGetScapedList.setInt(2, scrapType);
			}
			else{
				psGetScapedList = conn.prepareStatement("SELECT CREATED_DT,SCRAP_REASON_ID,SCRAP_REASON_NAME,SCRAP_QTY,EMP_ID,INSPECTOR_ID FROM SWS_SCRAP WHERE SWS_GUID=? AND RAC_ID=? AND SCRAP_TYPE=?");
				psGetScapedList.setString(1, resultDoc.getSws_guid());
				psGetScapedList.setString(2, resultDoc.getRac_id());
				psGetScapedList.setInt(3, scrapType);
			}
			ResultSet rsGetScrapedList = psGetScapedList.executeQuery();
			while(rsGetScrapedList.next()){
				RAC_SCRAP_VIEW tempData = new RAC_SCRAP_VIEW();
				tempData.setScrap_dt(rsGetScrapedList.getLong(1));
				tempData.setScrap_reason(rsGetScrapedList.getString(3));
				tempData.setScrap_qty(rsGetScrapedList.getBigDecimal(4));
				tempData.setEmp_id(rsGetScrapedList.getString(5));
				tempData.setInspector_id(rsGetScrapedList.getString(6));
				
				resultDoc.getScrap_list().add(tempData);
			}
			rsGetScrapedList.close();
			psGetScapedList.close();
		}
		else{
			ps.close();
			rs.close();
			throw new Exception("未找到流程票信息！");
		}
		ps.close();
		rs.close();
		
		return resultDoc;
	}
	
	public static SWS_SCRAP_DOC getSwsScrapDocBySwsRpGuid(String swsRpGuid,int scrapType,Connection conn) throws Exception{
		SWS_SCRAP_DOC resultDoc =  new SWS_SCRAP_DOC();
		
		//报工单信息
		PreparedStatement psRac = conn.prepareStatement("SELECT RP_STATUS,BG_DT,RP_RAC_ID,RP_RAC_NAME,RP_WS,RP_WS_NO,WORK_TARGET,SWS_GUID,SCRAP_QTY FROM SWS_RP WHERE SWS_RP_GUID=?");
		psRac.setString(1, swsRpGuid);
		ResultSet rsRac = psRac.executeQuery();
		if(rsRac.next()){
			if(rsRac.getInt(1)!=1){
				throw new Exception("工序:"+rsRac.getString(3)+"("+rsRac.getString(4)+") 不在开工中！");
			}
			
			resultDoc.setBg_dt(rsRac.getLong(2));
			resultDoc.setRac_id(rsRac.getString(3));
			resultDoc.setRac_name(rsRac.getString(4));
			resultDoc.setWs_id(rsRac.getString(5));
			resultDoc.setWs_no(rsRac.getString(6));
			resultDoc.setTar_qty(rsRac.getBigDecimal(7));
			resultDoc.setSws_guid(rsRac.getString(8));
			resultDoc.setScraped_qty(rsRac.getBigDecimal(9));
		} else{
			throw new Exception("未找到工序信息！");
		}
		rsRac.close();
		psRac.close();
		
		//工单工序
		PreparedStatement ps = conn.prepareStatement("SELECT T1.SWS_STATUS,T1.SUB_WO_SUB_ID,T1.ITM_ID,T1.SWS_QTY,T2.CUT_SEQNO,T2.WO_ID FROM SUB_WO_SUB T1,SUB_WO_MAIN T2 WHERE T1.SUB_WO_SUB_GUID=? AND T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID");
		ps.setString(1, resultDoc.getSws_guid());
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			if(rs.getInt(1)!=0){
				throw new Exception("流程票已经入库！");
			}
			resultDoc.setSws_id(rs.getString(2));
			resultDoc.setItm_id(rs.getString(3));
			resultDoc.setSws_qty(rs.getBigDecimal(4));
			resultDoc.setWo_id(rs.getString(6));
			
			String cutSeqNo=rs.getString(5);						
			
			//本道工序的可报工数量
			BigDecimal lastRacQty=GetRacAvaliableQty(resultDoc.getSws_guid(),resultDoc.getRac_id()
					,cutSeqNo,resultDoc.getSws_qty(),conn);
			resultDoc.setRp_qty(lastRacQty);
			
			
			//本报工单的报废记录
			resultDoc.setScrap_list(new ArrayList<RAC_SCRAP_VIEW>());
			PreparedStatement psGetScapedList;
			psGetScapedList = conn.prepareStatement("SELECT CREATED_DT,SCRAP_REASON_ID,SCRAP_REASON_NAME,SCRAP_QTY,EMP_ID,INSPECTOR_ID FROM SWS_SCRAP WHERE RP_GUID=? AND SCRAP_TYPE=?");
			psGetScapedList.setString(1, swsRpGuid);
			psGetScapedList.setInt(2, scrapType);
			ResultSet rsGetScrapedList = psGetScapedList.executeQuery();
			while(rsGetScrapedList.next()){
				RAC_SCRAP_VIEW tempData = new RAC_SCRAP_VIEW();
				tempData.setScrap_dt(rsGetScrapedList.getLong(1));
				tempData.setScrap_reason(rsGetScrapedList.getString(3));
				tempData.setScrap_qty(rsGetScrapedList.getBigDecimal(4));
				tempData.setEmp_id(rsGetScrapedList.getString(5));
				tempData.setInspector_id(rsGetScrapedList.getString(6));
				
				resultDoc.getScrap_list().add(tempData);
			}
			rsGetScrapedList.close();
			psGetScapedList.close();
			
			//以下为统计本道工序的报废数量和记录  暂留
//			//本道工序的已报废数量
//			BigDecimal scrapedQty=GetRPTotalScrapedQty(resultDoc.getSws_guid(),resultDoc.getRac_id(),conn);
//			resultDoc.setScraped_qty(scrapedQty);
			
//			//本道工序的报废记录
//			resultDoc.setScrap_list(new ArrayList<RAC_SCRAP_VIEW>());
//			PreparedStatement psGetScapedList;
//			psGetScapedList = conn.prepareStatement("SELECT CREATED_DT,SCRAP_REASON_ID,SCRAP_REASON_NAME,SCRAP_QTY,EMP_ID,INSPECTOR_ID FROM SWS_SCRAP WHERE SWS_GUID=? AND RAC_ID=? AND SCRAP_TYPE=?");
//			psGetScapedList.setString(1, resultDoc.getSws_guid());
//			psGetScapedList.setString(2, resultDoc.getRac_id());
//			psGetScapedList.setInt(3, scrapType);
//			ResultSet rsGetScrapedList = psGetScapedList.executeQuery();
//			while(rsGetScrapedList.next()){
//				RAC_SCRAP_VIEW tempData = new RAC_SCRAP_VIEW();
//				tempData.setScrap_dt(rsGetScrapedList.getLong(1));
//				tempData.setScrap_reason(rsGetScrapedList.getString(3));
//				tempData.setScrap_qty(rsGetScrapedList.getBigDecimal(4));
//				tempData.setEmp_id(rsGetScrapedList.getString(5));
//				tempData.setInspector_id(rsGetScrapedList.getString(6));
//				
//				resultDoc.getScrap_list().add(tempData);
//			}
//			rsGetScrapedList.close();
//			psGetScapedList.close();
		}
		else{
			ps.close();
			rs.close();
			throw new Exception("未找到流程票信息！");
		}
		ps.close();
		rs.close();
		
		return resultDoc;
	}
	
	public static EntityListDM<SWS_SCRAP_VIEW,SWS_SCRAP_VIEW> GetScraps(long bdt,long edt, String woId, String lot_id, String itmId, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<SWS_SCRAP_VIEW,SWS_SCRAP_VIEW> result = new EntityListDM<SWS_SCRAP_VIEW,SWS_SCRAP_VIEW>();
		result.setDataList(new ArrayList<SWS_SCRAP_VIEW>());
		
		String subSQL = "SELECT T1.CREATED_DT,T1.SCRAP_TYPE,T3.WO_ID,T3.ITM_ID,T3.LOT_ID,T1.RAC_ID AS RAC_SEQNO,T4.RAC_ID,T4.RAC_NAME,T1.SCRAP_QTY,T1.SCRAP_PART,T1.SCRAP_REASON_NAME,T1.HAPPEN_RAC_NAME,T1.SCRAP_CONTENT_NAME,T1.SWS_SCRAP_GUID,T2.SUB_WO_SUB_ID,T0.ERP_DOC_ID "
				+ " FROM SWS_SCRAP T1 LEFT JOIN SWS_RP T0 ON T1.RP_GUID=T0.SWS_RP_GUID "
				+ " LEFT JOIN SUB_WO_SUB T2 ON T2.SUB_WO_SUB_GUID = T1.SWS_GUID "
				+ " LEFT JOIN SUB_WO_MAIN T3 ON T2.SUB_WO_MAIN_GUID=T3.SUB_WO_MAIN_GUID "
				+ " LEFT JOIN WO_RAC T4 ON T4.WO_DOC_ID=T3.WO_ID AND T4.WO_RAC_ID=T1.RAC_ID ";
		String subSQLWhere = " WHERE 1=1 ";
		String subOrderby = " ORDER BY WO_ID,SUB_WO_SUB_ID,RAC_SEQNO DESC ";
		
		if(bdt>0){
			subSQLWhere += " AND T1.CREATED_DT>=? ";
		}
		if(edt>0){
			subSQLWhere += " AND T1.CREATED_DT<=? ";
		}
		if (!StringUtils.isNullOrEmpty(woId)) {
			subSQLWhere += " AND T3.WO_ID=? ";
		}
		if (!StringUtils.isNullOrEmpty(lot_id)) {
			subSQLWhere += " AND T3.LOT_ID=? ";
		}
		if (!StringUtils.isNullOrEmpty(itmId)) {
			subSQLWhere += " AND T3.ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + subSQLWhere;
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		PreparedStatement ps = conn.prepareStatement(sSQL);
		int index=0;
		if(bdt>0){
			ps.setLong(++index, bdt);
		}
		if(edt>0){
			ps.setLong(++index, edt);
		}
		if (!StringUtils.isNullOrEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isNullOrEmpty(lot_id)) {
			ps.setString(++index, lot_id);
		}
		if (!StringUtils.isNullOrEmpty(itmId)) {
			ps.setString(++index, itmId+"%");
		}
		
		ps.setInt(++index, iRowStart);
		ps.setInt(++index, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			SWS_SCRAP_VIEW tempView = new SWS_SCRAP_VIEW();
			tempView.setScrap_dt(rs.getLong(1));
			if(rs.getInt(2)==0){
				tempView.setScrap_type_name("整体报废");
			}else{
				tempView.setScrap_type_name("部件报废");
			}
			tempView.setWo_id(rs.getString(3));
			tempView.setItm_id(rs.getString(4));
			tempView.setLot_id(rs.getString(5));
			tempView.setRac_seqno(rs.getString(6));
			tempView.setRac_id(rs.getString(7));
			tempView.setRac_name(rs.getString(8));
			tempView.setScrap_qty(rs.getBigDecimal(9));
			tempView.setScrap_part(rs.getString(10));
			tempView.setScrap_reason_name(rs.getString(11));
			tempView.setHappen_rac_name(rs.getString(12));
			tempView.setScrap_content_name(rs.getString(13));
			tempView.setGuid(rs.getString(14));
			tempView.setSws_id(rs.getString(15));
			tempView.setErp_doc_id(rs.getString(16));
			
			result.getDataList().add(tempView);
		}
		rs.close();
		ps.close();
		
		String sqlCount= "SELECT COUNT(A.SWS_SCRAP_GUID) FROM ("+subSQL+") AS A";
		ps = conn.prepareStatement(sqlCount);
		index=0;
		if(bdt>0){
			ps.setLong(++index, bdt);
		}
		if(edt>0){
			ps.setLong(++index, edt);
		}
		if (!StringUtils.isNullOrEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isNullOrEmpty(lot_id)) {
			ps.setString(++index, lot_id);
		}
		if (!StringUtils.isNullOrEmpty(itmId)) {
			ps.setString(++index, itmId+"%");
		}
		
		rs = ps.executeQuery();
		if (rs.next()) {
			result.setCount(rs.getInt(1));
		} else {
			result.setCount(0);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static void delScrap(String scrapGuid,Connection conn) throws Exception{
		PreparedStatement psSws=conn.prepareStatement("SELECT T2.SWS_RP_GUID FROM SWS_SCRAP T1 INNER JOIN SWS_RP T2 ON T1.RP_GUID=T2.SWS_RP_GUID AND T1.SWS_SCRAP_GUID=?");
		psSws.setString(1, scrapGuid);
		ResultSet rsSws=psSws.executeQuery();
		if(rsSws.next()){
			psSws.close();
			rsSws.close();
			throw new Exception("请先删除报废单所属的报工单！");
		}
		
		psSws.close();
		rsSws.close();
		
		PreparedStatement psDel=conn.prepareStatement("DELETE FROM SWS_SCRAP WHERE SWS_SCRAP_GUID=?");
		psDel.setString(1, scrapGuid);
		psDel.execute();
		psDel.close();
		
	}
	
}