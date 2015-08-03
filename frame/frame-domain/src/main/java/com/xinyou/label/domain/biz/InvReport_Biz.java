package com.xinyou.label.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.label.domain.entities.STK_ITM_WKSITE;
import com.xinyou.label.domain.entities.TRAN_BACO;
import com.xinyou.label.domain.entities.WORKING_MINUTS;
import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;
import com.xinyou.label.domain.viewes.EMP_SWS_RP_VIEW;
import com.xinyou.label.domain.viewes.ITM_INV_REPORT_VIEW;
import com.xinyou.label.domain.viewes.ITM_TRAN_REPORT_VIEW;
import com.xinyou.label.domain.viewes.ITM_WO_REPORT_VIEW;
import com.xinyou.label.domain.viewes.RBA_CTN_RE_VIEW;
import com.xinyou.label.domain.viewes.SCRAP_REPORT_VIEW;
import com.xinyou.label.domain.viewes.STK_ITM_VIEW;
import com.xinyou.label.domain.viewes.SUB_WO_SUB_VIEW;
import com.xinyou.label.domain.viewes.TRAN_BACO_VIEW;
import com.xinyou.label.domain.viewes.WO_DOC_REPORT_VIEW;

public class InvReport_Biz {
	public static EntityListDM<ITM_INV_REPORT_VIEW,ITM_INV_REPORT_VIEW> getItmInvReport(String invGuid, String itmId, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<ITM_INV_REPORT_VIEW,ITM_INV_REPORT_VIEW> result = new EntityListDM<ITM_INV_REPORT_VIEW,ITM_INV_REPORT_VIEW>();
		result.setDataList(new ArrayList<ITM_INV_REPORT_VIEW>());
		
		String subSQL = "SELECT ITM_ID,ITM_NAME,ITM_TOTAL_QTY,ITM_UNIT FROM (SELECT T1.ITM_ID,T2.ITM_NAME, SUM(ISNULL(ITM_QTY,0)) AS ITM_TOTAL_QTY,T2.ITM_UNIT FROM CTN_MAIN T1,ITM_MAIN T2 ";
		String subSQLWhere = " WHERE T1.WH_GUID=? AND T1.ITM_ID=T2.ITM_MAIN_ID ";
		String subOrderby = " ORDER BY ITM_ID";
		
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T1.ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + subSQLWhere + " GROUP BY T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT) A WHERE A.ITM_TOTAL_QTY>0 ";
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		PreparedStatement ps = conn.prepareStatement(sSQL);
		int index = 0;
		ps.setString(++index, invGuid);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		ps.setInt(++index, iRowStart);
		ps.setInt(++index, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ITM_INV_REPORT_VIEW tempView = new ITM_INV_REPORT_VIEW();
			tempView.setItm_main_id(rs.getString(1));
			tempView.setItm_name(rs.getString(2));
			tempView.setItm_qty(rs.getBigDecimal(3));
			tempView.setItm_unit(rs.getString(4));
			result.getDataList().add(tempView);
		}
		rs.close();
		ps.close();
		
		subSQL = "SELECT COUNT(*) FROM (SELECT ITM_ID,SUM(ISNULL(ITM_QTY,0)) AS ITM_TOTAL_QTY FROM CTN_MAIN WHERE WH_GUID=? ";
		if (!StringUtils.isEmpty(itmId)) {
			subSQL += " AND ITM_ID LIKE ? ";
		}
		subSQL = subSQL+ " GROUP BY ITM_ID) A WHERE ITM_TOTAL_QTY>0";
		ps = conn.prepareStatement(subSQL);
		 index = 0;
		ps.setString(++ index, invGuid);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++ index, itmId+"%");
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
	
	public static List<CTN_MAIN_VIEW> getBoxInvReportByItm(String invGuid,String itmId, Connection conn) throws SQLException
	{
		List<CTN_MAIN_VIEW> resultList = new ArrayList<CTN_MAIN_VIEW>();
		PreparedStatement ps = conn.prepareStatement("SELECT T1.CTN_MAIN_GUID,T1.CTN_BACO,T1.ITM_QTY,T2.CTN_BACO FROM CTN_MAIN T1 JOIN CTN_MAIN T2 ON T2.CTN_MAIN_GUID=T1.WH_LOC_GUID WHERE T1.WH_GUID=? AND T1.ITM_ID=?");
		ps.setString(1, invGuid);
		ps.setString(2, itmId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			CTN_MAIN_VIEW tempData = new CTN_MAIN_VIEW();
			tempData.setCtn_main_guid(rs.getString(1));
			tempData.setCtn_baco(rs.getString(2));
			tempData.setItm_qty(rs.getBigDecimal(3));
			tempData.setWh_loc_baco(rs.getString(4));
			
			resultList.add(tempData);
		}
		
		return resultList;
	}
	
	public static List<ITM_INV_REPORT_VIEW> getItmInvReport_Exp(String invGuid, String itmId, int exportSize, Connection conn) throws SQLException
	{		
		List<ITM_INV_REPORT_VIEW> result = new ArrayList<ITM_INV_REPORT_VIEW>();
		
		String subSQL = "SELECT TOP "+String.valueOf(exportSize)+" ITM_ID,ITM_NAME,ITM_TOTAL_QTY,ITM_UNIT FROM (SELECT T1.ITM_ID,T2.ITM_NAME, SUM(ISNULL(ITM_QTY,0)) AS ITM_TOTAL_QTY,T2.ITM_UNIT FROM CTN_MAIN T1,ITM_MAIN T2 WHERE T1.WH_GUID=? AND T1.ITM_ID=T2.ITM_MAIN_ID ";
		
		if (!StringUtils.isEmpty(itmId)) {
			subSQL += " AND T1.ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + " GROUP BY T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT) A WHERE ITM_TOTAL_QTY>0 ORDER BY ITM_ID";

		PreparedStatement ps = conn.prepareStatement(subSQL);
		int index=0;
		ps.setString(++index, invGuid);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ITM_INV_REPORT_VIEW tempView = new ITM_INV_REPORT_VIEW();
			tempView.setItm_main_id(rs.getString(1));
			tempView.setItm_name(rs.getString(2));
			tempView.setItm_qty(rs.getBigDecimal(3));
			tempView.setItm_unit(rs.getString(4));
			result.add(tempView);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static List<CTN_MAIN_VIEW> getBoxInvReport_Exp(String invGuid, String itmId, int exportSize, Connection conn) throws SQLException
	{		
		List<CTN_MAIN_VIEW> result = new ArrayList<CTN_MAIN_VIEW>();
		
		String subSQL = "SELECT T.CTN_BACO,T.ITM_ID,T.ITM_QTY,T2.CTN_BACO FROM CTN_MAIN T JOIN CTN_MAIN T2 ON T2.CTN_MAIN_GUID=T.WH_LOC_GUID WHERE T.WH_GUID=? AND ISNULL(T.ITM_QTY,0)>0 ";
		
		if (!StringUtils.isEmpty(itmId)) {
			subSQL += " AND T.ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + " ORDER BY T.ITM_ID";

		PreparedStatement ps = conn.prepareStatement(subSQL);
		int index=0;
		ps.setString(++index, invGuid);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			CTN_MAIN_VIEW tempView = new CTN_MAIN_VIEW();
			tempView.setCtn_baco(rs.getString(1));
			tempView.setItm_id(rs.getString(2));
			tempView.setItm_qty(rs.getBigDecimal(3));
			tempView.setWh_loc_baco(rs.getString(4));
			result.add(tempView);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static EntityListDM<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW> getItmTranReport(String whId, String itmId, long bg, long ed, int in_out, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW> result = new EntityListDM<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW>();
		result.setDataList(new ArrayList<ITM_TRAN_REPORT_VIEW>());
		
		String subSQL = "SELECT T1.CREATED_DT,T4.USR_NICKNAME,T1.TRAN_TYPE,T5.PARA_VALUE,T2.ITM_ID,T3.ITM_NAME,T2.ITM_QTY,T3.ITM_UNIT,T1.TRAN_MAIN_GUID "
				+ "FROM TRAN_MAIN T1 "
				+ "JOIN TRAN_ITM T2 ON T2.TRAN_GUID=T1.TRAN_MAIN_GUID "
				+ "JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID "
				+ "JOIN USR_MAIN T4 ON T1.CREATED_BY=T4.USR_MAIN_GUID "
				+ "LEFT JOIN PARA_MAIN T5 ON T1.TRAN_REASON_ID=T5.PARA_MAIN_ID AND T5.PARA_TYPE_ID='OtherInvReason' ";
		String subSQLWhere = " WHERE T1.WH_ID=? AND T1.IN_OUT=? ";
		String subOrderby = " ORDER BY CREATED_DT DESC";
		
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T2.ITM_ID LIKE ? ";
		}
		if (bg>0) {
			subSQLWhere += " AND T1.CREATED_DT>=? ";
		}
		if (ed>0) {
			subSQLWhere += " AND T1.CREATED_DT<=? ";
		}
		
		subSQL = subSQL + subSQLWhere;
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		PreparedStatement ps = conn.prepareStatement(sSQL);
		int index=0;
		ps.setString(++index, whId);
		ps.setInt(++index, in_out);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
		}
		ps.setInt(++index, iRowStart);
		ps.setInt(++index, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ITM_TRAN_REPORT_VIEW tempView = new ITM_TRAN_REPORT_VIEW();
			tempView.setTran_dt(rs.getLong(1));
			tempView.setOperator(rs.getString(2));
			tempView.setTran_type(rs.getInt(3));
			tempView.setTran_reason(rs.getString(4));
			tempView.setItm_main_id(rs.getString(5));
			tempView.setItm_name(rs.getString(6));
			tempView.setItm_qty(rs.getBigDecimal(7));
			tempView.setItm_unit(rs.getString(8));
			tempView.setTran_guid(rs.getString(9));
			
			result.getDataList().add(tempView);
		}
		rs.close();
		ps.close();
		
		subSQL = "SELECT COUNT(*) FROM TRAN_MAIN T1 JOIN TRAN_ITM T2 ON T2.TRAN_GUID=T1.TRAN_MAIN_GUID JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID JOIN USR_MAIN T4 ON T1.CREATED_BY=T4.USR_MAIN_GUID ";
		subSQL = subSQL+subSQLWhere;
		ps = conn.prepareStatement(subSQL);
		index=0;
		ps.setString(++index, whId);
		ps.setInt(++index, in_out);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
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
	
	public static List<ITM_TRAN_REPORT_VIEW> getItmTranReport_Exp(String whId, String itmId, long bg, long ed, int in_out, int exportSize, Connection conn) throws SQLException
	{
		List<ITM_TRAN_REPORT_VIEW> result = new ArrayList<ITM_TRAN_REPORT_VIEW>();
		
		String subSQL = "SELECT TOP "+String.valueOf(exportSize)+" T1.CREATED_DT,T4.USR_NICKNAME,T1.TRAN_TYPE,T5.PARA_VALUE,T2.ITM_ID,T3.ITM_NAME,T2.ITM_QTY,T3.ITM_UNIT FROM TRAN_MAIN T1 JOIN TRAN_ITM T2 ON T2.TRAN_GUID=T1.TRAN_MAIN_GUID JOIN ITM_MAIN T3 ON T2.ITM_ID=T3.ITM_MAIN_ID JOIN USR_MAIN T4 ON T1.CREATED_BY=T4.USR_MAIN_GUID LEFT JOIN PARA_MAIN T5 ON T1.TRAN_REASON_ID=T5.PARA_MAIN_ID AND T5.PARA_TYPE_ID='OtherInvReason' ";
		String subSQLWhere = " WHERE T1.WH_ID=? AND T1.IN_OUT=? ";
		String subOrderby = " ORDER BY T1.CREATED_DT DESC";
		
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T2.ITM_ID LIKE ? ";
		}
		if (bg>0) {
			subSQLWhere += " AND T1.CREATED_DT>=? ";
		}
		if (ed>0) {
			subSQLWhere += " AND T1.CREATED_DT<=? ";
		}
		
		subSQL = subSQL + subSQLWhere + subOrderby;
		
		PreparedStatement ps = conn.prepareStatement(subSQL);
		int index=0;
		ps.setString(++index, whId);
		ps.setInt(++index, in_out);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
		}

		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ITM_TRAN_REPORT_VIEW tempView = new ITM_TRAN_REPORT_VIEW();
			tempView.setTran_dt(rs.getLong(1));
			tempView.setOperator(rs.getString(2));
			tempView.setTran_type(rs.getInt(3));
			tempView.setTran_reason(rs.getString(4));
			tempView.setItm_main_id(rs.getString(5));
			tempView.setItm_name(rs.getString(6));
			tempView.setItm_qty(rs.getBigDecimal(7));
			tempView.setItm_unit(rs.getString(8));
			result.add(tempView);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static EntityListDM<ITM_WO_REPORT_VIEW,ITM_WO_REPORT_VIEW> getItmWoReport(long bg, long ed, String woId, String lotId,String itmId, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<ITM_WO_REPORT_VIEW,ITM_WO_REPORT_VIEW> result = new EntityListDM<ITM_WO_REPORT_VIEW,ITM_WO_REPORT_VIEW>();
		result.setDataList(new ArrayList<ITM_WO_REPORT_VIEW>());
		
		String subSQL = "SELECT T1.BG_DT,T1.RP_DT,T1.FINISH_QTY,T1.RP_RAC_NAME,T1.RP_WS,T1.EMP_ID_LIST,T2.SUB_WO_SUB_ID,T3.WO_ID,T3.LOT_ID,T3.ITM_ID,T4.ITM_NAME,T4.ITM_UNIT FROM SWS_RP T1 JOIN SUB_WO_SUB T2 ON T1.SWS_GUID=T2.SUB_WO_SUB_GUID JOIN SUB_WO_MAIN T3 ON T2.SUB_WO_MAIN_GUID=T3.SUB_WO_MAIN_GUID JOIN ITM_MAIN T4 ON T3.ITM_ID=T4.ITM_MAIN_ID ";
		String subSQLWhere = " WHERE T1.RP_STATUS=2 ";
		String subOrderby = " ORDER BY RP_DT DESC";
		
		if (bg>0) {
			subSQLWhere += " AND T1.RP_DT>=? ";
		}
		if (ed>0) {
			subSQLWhere += " AND T1.RP_DT<=? ";
		}
		if (!StringUtils.isEmpty(woId)) {
			subSQLWhere += " AND T3.WO_ID=? ";
		}
		if (!StringUtils.isEmpty(lotId)) {
			subSQLWhere += " AND T3.LOT_ID=? ";
		}
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T3.ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + subSQLWhere;
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		PreparedStatement ps = conn.prepareStatement(sSQL);
		int index=0;
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
		}
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(lotId)) {
			ps.setString(++index, lotId);
		}
		if (!StringUtils.isEmpty(itmId)) {
			ps.setString(++index, itmId+"%");
		}
		
		ps.setInt(++index, iRowStart);
		ps.setInt(++index, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ITM_WO_REPORT_VIEW tempView = new ITM_WO_REPORT_VIEW();
			tempView.setBdt(rs.getLong(1));
			tempView.setRdt(rs.getLong(2));
			tempView.setItm_qty(rs.getBigDecimal(3));
			tempView.setRac_name(rs.getString(4));
			tempView.setRp_ws(rs.getString(5));
			tempView.setEmp_id_list(rs.getString(6));
			tempView.setSws_id(rs.getString(7));
			tempView.setWo_id(rs.getString(8));
			tempView.setLot_id(rs.getString(9));
			tempView.setItm_main_id(rs.getString(10));
			tempView.setItm_name(rs.getString(11));
			tempView.setItm_unit(rs.getString(12));
			result.getDataList().add(tempView);
		}
		rs.close();
		ps.close();
		
		subSQL = "SELECT COUNT(*) FROM SWS_RP T1 JOIN SUB_WO_SUB T2 ON T1.SWS_GUID=T2.SUB_WO_SUB_GUID JOIN SUB_WO_MAIN T3 ON T2.SUB_WO_MAIN_GUID=T3.SUB_WO_MAIN_GUID JOIN ITM_MAIN T4 ON T3.ITM_ID=T4.ITM_MAIN_ID ";
		subSQL = subSQL+subSQLWhere;
		ps = conn.prepareStatement(subSQL);
		index=0;
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
		}
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(lotId)) {
			ps.setString(++index, lotId);
		}
		if (!StringUtils.isEmpty(itmId)) {
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
	
	public static List<ITM_WO_REPORT_VIEW> getItmWoReport_Exp(long bg, long ed, String woId, String lotId,String itmId, int exportSize, Connection conn) throws SQLException
	{
		List<ITM_WO_REPORT_VIEW> result = new ArrayList<ITM_WO_REPORT_VIEW>();
		
		String subSQL = "SELECT TOP "+String.valueOf(exportSize)+" T1.BG_DT,T1.RP_DT,T1.FINISH_QTY,T1.RP_RAC_NAME,T1.RP_WS,T1.EMP_ID_LIST,T2.SUB_WO_SUB_ID,T3.WO_ID,T3.LOT_ID,T3.ITM_ID,T4.ITM_NAME,T4.ITM_UNIT FROM SWS_RP T1 JOIN SUB_WO_SUB T2 ON T1.SWS_GUID=T2.SUB_WO_SUB_GUID JOIN SUB_WO_MAIN T3 ON T2.SUB_WO_MAIN_GUID=T3.SUB_WO_MAIN_GUID  JOIN ITM_MAIN T4 ON T3.ITM_ID=T4.ITM_MAIN_ID ";
		String subSQLWhere = " WHERE T1.RP_STATUS=2";
		String subOrderby = " ORDER BY T1.RP_DT DESC";
		
		if (bg>0) {
			subSQLWhere += " AND T1.RP_DT>=? ";
		}
		if (ed>0) {
			subSQLWhere += " AND T1.RP_DT<=? ";
		}
		if (!StringUtils.isEmpty(woId)) {
			subSQLWhere += " AND T3.WO_ID=? ";
		}
		if (!StringUtils.isEmpty(lotId)) {
			subSQLWhere += " AND T3.LOT_ID=? ";
		}
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T3.ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + subSQLWhere + subOrderby;
		
		PreparedStatement ps = conn.prepareStatement(subSQL);
		int index=0;
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
		}
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(lotId)) {
			ps.setString(++index, lotId);
		}
		if (!StringUtils.isEmpty(itmId)) {
			ps.setString(++index, itmId+"%");
		}
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ITM_WO_REPORT_VIEW tempView = new ITM_WO_REPORT_VIEW();
			tempView.setBdt(rs.getLong(1));
			tempView.setRdt(rs.getLong(2));
			tempView.setItm_qty(rs.getBigDecimal(3));
			tempView.setRac_name(rs.getString(4));
			tempView.setRp_ws(rs.getString(5));
			tempView.setEmp_id_list(rs.getString(6));
			tempView.setSws_id(rs.getString(7));
			tempView.setWo_id(rs.getString(8));
			tempView.setLot_id(rs.getString(9));
			tempView.setItm_main_id(rs.getString(10));
			tempView.setItm_name(rs.getString(11));
			tempView.setItm_unit(rs.getString(12));
			result.add(tempView);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static EntityListDM<WO_DOC_REPORT_VIEW,WO_DOC_REPORT_VIEW> getWoingReport(String woId, String itmId, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<WO_DOC_REPORT_VIEW,WO_DOC_REPORT_VIEW> result = new EntityListDM<WO_DOC_REPORT_VIEW,WO_DOC_REPORT_VIEW>();
		result.setDataList(new ArrayList<WO_DOC_REPORT_VIEW>());
		
		String subSQL = "SELECT T1.WO_DOC_ID,T1.WO_ITM_ID,T4.ITM_NAME,T1.WO_QTY,SUM(T3.FINISH_QTY) AS FINISH_QTY,T4.ITM_UNIT FROM WO_DOC T1 JOIN SUB_WO_MAIN T2 ON T2.WO_ID=T1.WO_DOC_ID JOIN SUB_WO_SUB T3 ON T3.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID JOIN ITM_MAIN T4 ON T1.WO_ITM_ID=T4.ITM_MAIN_ID ";
		String subSQLWhere = " WHERE T1.WO_STATUS=1 ";
		String subOrderby = " ORDER BY WO_DOC_ID DESC ";
		
		if (!StringUtils.isEmpty(woId)) {
			subSQLWhere += " AND T1.WO_DOC_ID=? ";
		}
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T1.WO_ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + subSQLWhere + " GROUP BY T1.WO_DOC_ID,T1.WO_ITM_ID,T4.ITM_NAME,T1.WO_QTY,T4.ITM_UNIT ";
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		PreparedStatement ps = conn.prepareStatement(sSQL);
		int index=0;
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(itmId)) {
			ps.setString(++index, itmId+"%");
		}
		
		ps.setInt(++index, iRowStart);
		ps.setInt(++index, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			WO_DOC_REPORT_VIEW tempView = new WO_DOC_REPORT_VIEW();
			
			tempView.setWo_id(rs.getString(1));
			tempView.setItm_id(rs.getString(2));
			tempView.setItm_name(rs.getString(3));
			tempView.setWo_qty(rs.getBigDecimal(4));
			tempView.setFinish_qty(rs.getBigDecimal(5));
			tempView.setItm_unit(rs.getString(6));
			result.getDataList().add(tempView);
		}
		rs.close();
		ps.close();
		
		subSQL = "SELECT COUNT(*) FROM WO_DOC T1 JOIN SUB_WO_MAIN T2 ON T2.WO_ID=T1.WO_DOC_ID ";
		subSQL = subSQL+subSQLWhere;
		ps = conn.prepareStatement(subSQL);
		index=0;
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(itmId)) {
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
	
	public static List<WO_DOC_REPORT_VIEW> getWoingReport_Exp(String woId, String itmId, int exportSize, Connection conn) throws SQLException
	{
		List<WO_DOC_REPORT_VIEW> result = new ArrayList<WO_DOC_REPORT_VIEW>();
		
		String subSQL = "SELECT TOP "+String.valueOf(exportSize)+" T1.WO_DOC_ID,T1.WO_ITM_ID,T4.ITM_NAME,T1.WO_QTY,SUM(T3.FINISH_QTY) AS FINISH_QTY,T4.ITM_UNIT FROM WO_DOC T1 JOIN SUB_WO_MAIN T2 ON T2.WO_ID=T1.WO_DOC_ID JOIN SUB_WO_SUB T3 ON T3.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID JOIN ITM_MAIN T4 ON T1.WO_ITM_ID=T4.ITM_MAIN_ID ";
		String subSQLWhere = " WHERE T1.WO_STATUS=1 ";
		String subOrderby = " ORDER BY T1.WO_DOC_ID DESC";
		
		if (!StringUtils.isEmpty(woId)) {
			subSQLWhere += " AND T1.WO_DOC_ID=? ";
		}
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T1.WO_ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + subSQLWhere  + " GROUP BY T1.WO_DOC_ID,T1.WO_ITM_ID,T4.ITM_NAME,T1.WO_QTY,T4.ITM_UNIT " + subOrderby;
		
		PreparedStatement ps = conn.prepareStatement(subSQL);
		int index=0;
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(itmId)) {
			ps.setString(++index, itmId+"%");
		}
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			WO_DOC_REPORT_VIEW tempView = new WO_DOC_REPORT_VIEW();
			tempView.setWo_id(rs.getString(1));
			tempView.setItm_id(rs.getString(2));
			tempView.setItm_name(rs.getString(3));
			tempView.setWo_qty(rs.getBigDecimal(4));
			tempView.setFinish_qty(rs.getBigDecimal(5));
			tempView.setItm_unit(rs.getString(6));
			result.add(tempView);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static EntityListDM<SCRAP_REPORT_VIEW,SCRAP_REPORT_VIEW> GetScrapReport(long bdt,long edt, String woId, String lot_id, String itmId, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<SCRAP_REPORT_VIEW,SCRAP_REPORT_VIEW> result = new EntityListDM<SCRAP_REPORT_VIEW,SCRAP_REPORT_VIEW>();
		result.setDataList(new ArrayList<SCRAP_REPORT_VIEW>());
		
		String subSQL = "SELECT T1.CREATED_DT,T1.SCRAP_TYPE,T3.WO_ID,T3.ITM_ID,T3.LOT_ID,T1.RAC_ID AS RAC_SEQNO,T4.RAC_ID,T4.RAC_NAME,T1.SCRAP_QTY,T1.SCRAP_PART,T1.SCRAP_REASON_NAME,T1.HAPPEN_RAC_NAME,T1.SCRAP_CONTENT_NAME,T1.SWS_SCRAP_GUID,T2.SUB_WO_SUB_ID"
				+ " FROM SWS_SCRAP T1 JOIN SUB_WO_SUB T2 ON T2.SUB_WO_SUB_GUID = T1.SWS_GUID "
				+ " JOIN SUB_WO_MAIN T3 ON T2.SUB_WO_MAIN_GUID=T3.SUB_WO_MAIN_GUID "
				+ " JOIN WO_RAC T4 ON T4.WO_DOC_ID=T3.WO_ID AND T4.WO_RAC_ID=T1.RAC_ID ";
		String subSQLWhere = " WHERE 1=1 ";
		String subOrderby = " ORDER BY CREATED_DT DESC ";
		
		if(bdt>0){
			subSQLWhere += " AND T1.CREATED_DT>=? ";
		}
		if(edt>0){
			subSQLWhere += " AND T1.CREATED_DT<=? ";
		}
		if (!StringUtils.isEmpty(woId)) {
			subSQLWhere += " AND T3.WO_ID=? ";
		}
		if (!StringUtils.isEmpty(lot_id)) {
			subSQLWhere += " AND T3.LOT_ID=? ";
		}
		if (!StringUtils.isEmpty(itmId)) {
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
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(lot_id)) {
			ps.setString(++index, lot_id);
		}
		if (!StringUtils.isEmpty(itmId)) {
			ps.setString(++index, itmId+"%");
		}
		
		ps.setInt(++index, iRowStart);
		ps.setInt(++index, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			SCRAP_REPORT_VIEW tempView = new SCRAP_REPORT_VIEW();
			tempView.setScrap_dt(rs.getLong(1));
			if(rs.getInt(2)==0){
				tempView.setScrap_type("整体报废");
			}else{
				tempView.setScrap_type("部件报废");
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
			
			result.getDataList().add(tempView);
		}
		rs.close();
		ps.close();
		
		subSQL = "SELECT COUNT(*) FROM SWS_SCRAP T1 JOIN SUB_WO_SUB T2 ON T2.SUB_WO_SUB_GUID = T1.SWS_GUID JOIN SUB_WO_MAIN T3 ON T2.SUB_WO_MAIN_GUID=T3.SUB_WO_MAIN_GUID JOIN WO_RAC T4 ON T4.WO_DOC_ID=T3.WO_ID AND T4.WO_RAC_ID=T1.RAC_ID ";
		subSQL = subSQL+subSQLWhere;
		ps = conn.prepareStatement(subSQL);
		index=0;
		if(bdt>0){
			ps.setLong(++index, bdt);
		}
		if(edt>0){
			ps.setLong(++index, edt);
		}
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(lot_id)) {
			ps.setString(++index, lot_id);
		}
		if (!StringUtils.isEmpty(itmId)) {
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
	
	public static List<SCRAP_REPORT_VIEW> GetScrapReport_Exp(long bdt,long edt, String woId, String lot_id, String itmId, int exportSize, Connection conn) throws SQLException
	{
		List<SCRAP_REPORT_VIEW> result = new ArrayList<SCRAP_REPORT_VIEW>();
		
		String subSQL = "SELECT TOP "+String.valueOf(exportSize)+" T1.CREATED_DT,T1.SCRAP_TYPE,T3.WO_ID,T3.ITM_ID,T3.LOT_ID,T1.RAC_ID AS RAC_SEQNO,T4.RAC_ID,T4.RAC_NAME,T1.SCRAP_QTY,T1.SCRAP_PART,T1.SCRAP_REASON_NAME,T1.HAPPEN_RAC_NAME,T1.SCRAP_CONTENT_NAME FROM SWS_SCRAP T1 JOIN SUB_WO_SUB T2 ON T2.SUB_WO_SUB_GUID = T1.SWS_GUID JOIN SUB_WO_MAIN T3 ON T2.SUB_WO_MAIN_GUID=T3.SUB_WO_MAIN_GUID JOIN WO_RAC T4 ON T4.WO_DOC_ID=T3.WO_ID AND T4.WO_RAC_ID=T1.RAC_ID ";
		String subSQLWhere = " WHERE 1=1 ";
		String subOrderby = " ORDER BY T1.CREATED_DT DESC ";
		
		if(bdt>0){
			subSQLWhere += " AND T1.CREATED_DT>=? ";
		}
		if(edt>0){
			subSQLWhere += " AND T1.CREATED_DT<=? ";
		}
		if (!StringUtils.isEmpty(woId)) {
			subSQLWhere += " AND T3.WO_ID=? ";
		}
		if (!StringUtils.isEmpty(lot_id)) {
			subSQLWhere += " AND T3.LOT_ID=? ";
		}
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T3.ITM_ID LIKE ? ";
		}
		
		subSQL = subSQL + subSQLWhere  +  subOrderby;
		
		PreparedStatement ps = conn.prepareStatement(subSQL);
		int index=0;
		if(bdt>0){
			ps.setLong(++index, bdt);
		}
		if(edt>0){
			ps.setLong(++index, edt);
		}
		if (!StringUtils.isEmpty(woId)) {
			ps.setString(++index, woId);
		}
		if (!StringUtils.isEmpty(lot_id)) {
			ps.setString(++index, lot_id);
		}
		if (!StringUtils.isEmpty(itmId)) {
			ps.setString(++index, itmId+"%");
		}
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			SCRAP_REPORT_VIEW tempView = new SCRAP_REPORT_VIEW();
			tempView.setScrap_dt(rs.getLong(1));
			if(rs.getInt(2)==0){
				tempView.setScrap_type("整体报废");
			}else{
				tempView.setScrap_type("部件报废");
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
			result.add(tempView);
		}
		rs.close();
		ps.close();
		
		return result;
	}

	/////////////////////////////// GetWorkMinuts
	public static List<WORKING_MINUTS> GetWorkMinuts(long bgDt,long edDt,String[] empIds,Connection conn) throws SQLException{
		List<WORKING_MINUTS> resultList = new ArrayList<WORKING_MINUTS>();
		long tempBgDt = 0;
		long tempEdDt = 0;
		String sEmpIds = "";
		if(empIds!=null&&empIds.length>0)	{
			for(int i=0;i<empIds.length;i++){
				if(empIds[i].length()>0){
					if(i==empIds.length-1){
						sEmpIds = sEmpIds+"'"+empIds[i]+"'";
					}
					else{
						sEmpIds = sEmpIds+"'"+empIds[i]+"',";
					}
				}
			}
		}
		
		PreparedStatement ps = null;
		if(sEmpIds.length()>0){
			ps = conn.prepareStatement("SELECT T3.EMP_MAIN_GUID,T3.EMP_MAIN_ID,T3.EMP_NAME,T1.BG_DT,T1.RP_DT FROM SWS_RP T1 JOIN SWS_STAFF T2 ON T2.SWS_RP_GUID=T1.SWS_RP_GUID JOIN EMP_MAIN T3 ON T3.EMP_MAIN_GUID=T2.EMP_GUID AND T3.EMP_MAIN_ID IN("+sEmpIds+") WHERE T1.RP_DT>=? AND T1.RP_DT<=? AND T1.RP_STATUS=2 ORDER BY T3.EMP_MAIN_ID,T1.BG_DT");
		}
		else{
			ps = conn.prepareStatement("SELECT T3.EMP_MAIN_GUID,T3.EMP_MAIN_ID,T3.EMP_NAME,T1.BG_DT,T1.RP_DT FROM SWS_RP T1 JOIN SWS_STAFF T2 ON T2.SWS_RP_GUID=T1.SWS_RP_GUID JOIN EMP_MAIN T3 ON T3.EMP_MAIN_GUID=T2.EMP_GUID WHERE T1.RP_DT>=? AND T1.RP_DT<=? AND T1.RP_STATUS=2 ORDER BY T3.EMP_MAIN_ID,T1.BG_DT");
		}
		ps.setLong(1, bgDt);
		ps.setLong(2, edDt);
		ResultSet rs = ps.executeQuery();
		WORKING_MINUTS newWorking = null;
		
		while(rs.next()){
			if(!IsEmpExist(resultList,rs.getString(1))){
				tempBgDt = rs.getLong(4);
				tempEdDt = rs.getLong(5);
				
				newWorking= new WORKING_MINUTS();
				newWorking.setEmp_guid(rs.getString(1));
				newWorking.setEmp_id(rs.getString(2));
				newWorking.setEmp_name(rs.getString(3));
				newWorking.setMinuts((int)((tempEdDt-tempBgDt)/60000L));
				
				resultList.add(newWorking);
			}
			else{
				if(rs.getLong(4)<=tempEdDt){
					if(rs.getLong(5)<=tempEdDt){
						continue;
					}
					else{
						newWorking.setMinuts(newWorking.getMinuts()+(int)((rs.getLong(5)-tempEdDt)/60000L));
						tempEdDt = rs.getLong(5);
					}
				}
				else{
					tempBgDt = rs.getLong(4);
					tempEdDt = rs.getLong(5);
					
					newWorking.setMinuts(newWorking.getMinuts()+(int)((tempEdDt-tempBgDt)/60000L));
				}
			}
		}
		
		return resultList;
	}
	
	private static boolean IsEmpExist(List<WORKING_MINUTS> workingMinuts,String empGuid){
		for(WORKING_MINUTS empWorking:workingMinuts){
			if(empWorking.getEmp_guid().equals(empGuid)){
				return true;
			}
		}
		
		return false;
	}
	/////////////////////////////// GetWorkMinuts
	
	public static List<STK_ITM_VIEW> GetTakeStockItemsByPlanGuid(String planGuid, Connection conn) throws SQLException{
		List<STK_ITM_VIEW> resultList = new ArrayList<STK_ITM_VIEW>();
		
		PreparedStatement ps = conn.prepareStatement("SELECT T1.CREATED_DT,T2.EMP_MAIN_ID,T2.EMP_NAME,T3.CTN_BACO,T3.CTN_TYPE,T3.ITM_ID,T3.ITM_QTY,T4.CTN_BACO,T1.STK_LOC_BACO,T1.STK_VALUE  FROM STK_ITM T1 JOIN EMP_MAIN T2 ON T1.STK_EMP_ID=T2.EMP_MAIN_ID JOIN CTN_MAIN T3 ON T3.CTN_BACO=T1.CTN_BACO LEFT JOIN CTN_MAIN T4 ON T4.CTN_MAIN_GUID=T3.WH_LOC_GUID WHERE T1.STK_MAIN_GUID=? ORDER BY T4.CTN_BACO");
		ps.setString(1, planGuid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			STK_ITM_VIEW tempData = new STK_ITM_VIEW();
			tempData.setStk_dt(rs.getLong(1));
			tempData.setEmp_id(rs.getString(2));
			tempData.setEmp_name(rs.getString(3));
			tempData.setCtn_baco(rs.getString(4));
			tempData.setCtn_type(rs.getInt(5));
			tempData.setItm_id(rs.getString(6));
			tempData.setItm_qty(rs.getBigDecimal(7));
			tempData.setLoc_baco(rs.getString(8));
			tempData.setStkloc(rs.getString(9));
			tempData.setStk_itm_qty(rs.getBigDecimal(10));
			
			resultList.add(tempData);
		}
		
		return resultList;
	}
	
	public static List<STK_ITM_VIEW> GetStkSumItemsByPlanGuid(String planGuid, Connection conn) throws Exception{
		List<STK_ITM_VIEW> resultList = new ArrayList<STK_ITM_VIEW>();
		
		String invId = "";
		PreparedStatement ps = conn.prepareStatement("SELECT T.INV_ID FROM STK_MAIN T WHERE T.STK_MAIN_GUID=?");
		ps.setString(1, planGuid);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			invId = rs.getString(1);
		}
		else{
			rs.close();
			ps.close();
			throw new Exception("未找到盘点计划信息！");
		}
		rs.close();
		ps.close();
		
		String whGuid = "";
		ps = conn.prepareStatement("SELECT T.CTN_MAIN_GUID FROM CTN_MAIN T WHERE T.CTN_MAIN_ID=?");
		ps.setString(1, invId);
		rs = ps.executeQuery();
		if(rs.next()){
			whGuid = rs.getString(1);
		}
		else{
			rs.close();
			ps.close();
			throw new Exception("未找到仓库信息！");
		}
		rs.close();
		ps.close();
		
		
//		ps = conn.prepareStatement("SELECT T1.ITM_ID,SUM(ISNULL(T1.ITM_QTY,0)) AS ON_VALUE,SUM(ISNULL(T3.STK_VALUE,0)) AS STK_VALUE FROM CTN_MAIN T1 LEFT JOIN CTN_MAIN T2 ON T2.WH_GUID=? AND T2.STK_GUID=? AND T1.CTN_BACO=T2.CTN_BACO LEFT JOIN STK_ITM T3 ON T3.STK_MAIN_GUID=T2.STK_GUID AND T3.CTN_BACO=T2.CTN_BACO WHERE T1.WH_GUID=? AND T1.ITM_ID IS NOT NULL GROUP BY T1.ITM_ID ORDER BY T1.ITM_ID");
//		ps.setString(1, whGuid);
//		ps.setString(2, planGuid);
//		ps.setString(3, whGuid);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT A.ITM_ID,A.STK_VALUE,ISNULL(B.ON_VALUE,0) FROM( ");
		sb.append(" SELECT T1.ITM_ID,SUM(ISNULL(T.STK_VALUE,0)) AS STK_VALUE");
		sb.append(" FROM STK_ITM T ");
		sb.append(" JOIN CTN_MAIN T1 ON T.CTN_BACO=T1.CTN_BACO");
		sb.append(" WHERE T.STK_MAIN_GUID=?");
		sb.append(" GROUP BY T1.ITM_ID");
		sb.append(" ) A LEFT JOIN (");
		sb.append(" SELECT T.ITM_ID,SUM(ISNULL(T.ITM_QTY,0)) AS ON_VALUE ");
		sb.append(" FROM CTN_MAIN T ");
		sb.append(" WHERE T.WH_GUID=?");
		sb.append(" GROUP BY T.ITM_ID");
		sb.append(" ) B ON B.ITM_ID=A.ITM_ID");
		sb.append(" ORDER BY A.ITM_ID");
		ps = conn.prepareStatement(sb.toString());
		ps.setString(1, planGuid);
		ps.setString(2, whGuid);
		
		rs = ps.executeQuery();
		while(rs.next()){
			STK_ITM_VIEW tempData = new STK_ITM_VIEW();
			tempData.setItm_id(rs.getString(1));
			tempData.setItm_qty(rs.getBigDecimal(3));
			tempData.setStk_itm_qty(rs.getBigDecimal(2));
			
			resultList.add(tempData);
		}
		
		return resultList;
	}
	
	public static List<STK_ITM_VIEW> GetNoTakeStockByPlanGuid(String planGuid, Connection conn) throws SQLException{
		List<STK_ITM_VIEW> resultList = new ArrayList<STK_ITM_VIEW>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T3.CTN_BACO,T3.CTN_TYPE,T3.ITM_ID,T3.ITM_QTY,T5.CTN_BACO FROM STK_MAIN T1");
		sb.append(" JOIN CTN_MAIN T2 ON T2.CTN_MAIN_ID=T1.INV_ID");
		sb.append(" JOIN CTN_MAIN T3 ON T3.WH_GUID=T2.CTN_MAIN_GUID AND T3.CTN_TYPE IN (12,13)");
		sb.append(" LEFT JOIN STK_ITM T4 ON T4.STK_MAIN_GUID=T1.STK_MAIN_GUID AND T4.CTN_BACO=T3.CTN_BACO");
		sb.append(" JOIN CTN_MAIN T5 ON T5.CTN_MAIN_GUID=T3.WH_LOC_GUID");
		sb.append(" WHERE T1.STK_MAIN_GUID=? AND T4.CTN_BACO IS NULL");
		sb.append(" ORDER BY T5.CTN_BACO");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, planGuid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			STK_ITM_VIEW tempData = new STK_ITM_VIEW();
			tempData.setCtn_baco(rs.getString(1));
			tempData.setCtn_type(rs.getInt(2));
			tempData.setItm_id(rs.getString(3));
			tempData.setItm_qty(rs.getBigDecimal(4));
			tempData.setLoc_baco(rs.getString(5));
			
			resultList.add(tempData);
		}
		
		return resultList;
	}
	
	public static List<STK_ITM_VIEW> GetMoreTakeStockByPlanGuid(String planGuid, Connection conn) throws SQLException{
		List<STK_ITM_VIEW> resultList = new ArrayList<STK_ITM_VIEW>();
		
		/*
		 --盘盈，需要传入盘点仓库代码
			SELECT T1.ITM_ID,T.CTN_BACO,T.STK_VALUE,T.STK_VALUE,T1.ITM_QTY,T.STK_EMP_ID,T.CREATED_DT,
			--0 不在仓库里面，1 不在同一个仓库， 2 数量不一致
			CASE WHEN ISNULL(T1.WH_GUID,'')='' THEN 0 WHEN T2.CTN_MAIN_ID<>T3.INV_ID THEN 1 ELSE 2 END AS REASON 
			FROM STK_ITM T 
			JOIN CTN_MAIN T1 ON T1.CTN_BACO=T.CTN_BACO	--盘点容器
			LEFT JOIN CTN_MAIN T2 ON T2.CTN_MAIN_GUID=T1.WH_GUID  --容器仓库，可能不在仓库里面
			JOIN STK_MAIN T3 ON T3.STK_MAIN_GUID=T.STK_MAIN_GUID
			WHERE (T.STK_VALUE>T1.ITM_QTY) OR (T2.CTN_MAIN_GUID IS NULL) OR (T2.CTN_MAIN_ID<>T3.INV_ID) --盘点数量大于实际数量，不在仓库里面，不在这个仓库里面的
			AND T3.STK_MAIN_ID='080601'
		 * */
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T1.ITM_ID,T.CTN_BACO,T.STK_VALUE,T1.ITM_QTY,T.STK_EMP_ID,T.CREATED_DT,T.STK_LOC_BACO,ISNULL(T11.CTN_BACO,''),");
		//sb.append("CASE WHEN ISNULL(T1.WH_GUID,'')='' THEN '未入库' WHEN T2.CTN_MAIN_ID<>T3.INV_ID THEN '不在本仓库' ELSE '数量' END AS REASON ");
		sb.append("CASE WHEN ISNULL(T1.WH_GUID,'')='' THEN 0 WHEN T2.CTN_MAIN_ID<>T3.INV_ID THEN 1 ELSE 2 END AS REASON  ");
		sb.append("FROM STK_ITM T ");
		sb.append("JOIN CTN_MAIN T1 ON T1.CTN_BACO=T.CTN_BACO");
		sb.append(" LEFT JOIN CTN_MAIN T11 ON T11.CTN_MAIN_GUID=T1.WH_LOC_GUID");
		sb.append(" LEFT JOIN CTN_MAIN T2 ON T2.CTN_MAIN_GUID=T1.WH_GUID");
		sb.append(" JOIN STK_MAIN T3 ON T3.STK_MAIN_GUID=T.STK_MAIN_GUID");
		sb.append(" WHERE ((T.STK_VALUE>T1.ITM_QTY) OR (T2.CTN_MAIN_GUID IS NULL) OR (T2.CTN_MAIN_ID<>T3.INV_ID))");
		sb.append(" AND T3.STK_MAIN_GUID=?");
		sb.append(" ORDER BY T1.ITM_ID");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, planGuid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			STK_ITM_VIEW tempData = new STK_ITM_VIEW();
			tempData.setItm_id(rs.getString(1));
			tempData.setCtn_baco(rs.getString(2));
			tempData.setStk_itm_qty(rs.getBigDecimal(3));
			tempData.setItm_qty(rs.getBigDecimal(4));
			tempData.setEmp_id(rs.getString(5));
			tempData.setStk_dt(rs.getLong(6));
			tempData.setStkloc(rs.getString(7));
			tempData.setCtnloc(rs.getString(8));
			tempData.setReason(rs.getInt(9));
			
			resultList.add(tempData);
		}
		
		return resultList;
	}
	
	public static List<STK_ITM_VIEW> GetLessTakeStockByPlanGuid(String planGuid, Connection conn) throws SQLException{
		List<STK_ITM_VIEW> resultList = new ArrayList<STK_ITM_VIEW>();
		
		/*
		 --盘亏.
			SELECT T.CTN_BACO,T.ITM_ID,T.ITM_QTY,ISNULL(T3.STK_VALUE,0),T3.STK_EMP_ID,T3.CREATED_DT,
			--0 没有盘点到，2 数量不一致
			CASE WHEN ISNULL(T.STK_GUID,'')<>T2.STK_MAIN_GUID THEN 0 ELSE 2 END AS REASON 
			FROM CTN_MAIN T 
			JOIN CTN_MAIN T1 ON T1.CTN_MAIN_GUID=T.WH_GUID
			JOIN STK_MAIN T2 ON T2.STK_MAIN_ID='080601'
			LEFT JOIN STK_ITM T3 ON T3.STK_MAIN_GUID=T2.STK_MAIN_GUID AND T3.CTN_BACO=T.CTN_BACO
			WHERE T1.CTN_MAIN_ID=T2.INV_ID AND (ISNULL(T.STK_GUID,'')<>T2.STK_MAIN_GUID  --没有被盘点到的
			 OR (T3.STK_ITM_GUID IS NOT NULL AND T3.STK_VALUE<T.ITM_QTY)) AND T.CTN_TYPE=12
		 * */
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T.ITM_ID,T.CTN_BACO,ISNULL(T3.STK_VALUE,0),T.ITM_QTY,T3.STK_EMP_ID,ISNULL(T3.CREATED_DT,0),ISNULL(T3.STK_LOC_BACO,''),T0.CTN_BACO,");
		sb.append("CASE WHEN ISNULL(T.STK_GUID,'')<>T2.STK_MAIN_GUID THEN 0 ELSE 2 END AS REASON  ");
		sb.append("FROM CTN_MAIN T ");
		sb.append(" JOIN CTN_MAIN T0 ON T0.CTN_MAIN_GUID=T.WH_LOC_GUID");
		sb.append(" JOIN CTN_MAIN T1 ON T1.CTN_MAIN_GUID=T.WH_GUID");
		sb.append(" JOIN STK_MAIN T2 ON T2.STK_MAIN_GUID=?");
		sb.append(" LEFT JOIN STK_ITM T3 ON T3.STK_MAIN_GUID=T2.STK_MAIN_GUID AND T3.CTN_BACO=T.CTN_BACO");
		sb.append(" WHERE T1.CTN_MAIN_ID=T2.INV_ID AND (ISNULL(T.STK_GUID,'')<>T2.STK_MAIN_GUID");
		sb.append("  OR (T3.STK_ITM_GUID IS NOT NULL AND T3.STK_VALUE<T.ITM_QTY)) AND T.CTN_TYPE=12");
		sb.append(" ORDER BY T.ITM_ID");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, planGuid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			STK_ITM_VIEW tempData = new STK_ITM_VIEW();
			tempData.setItm_id(rs.getString(1));
			tempData.setCtn_baco(rs.getString(2));
			tempData.setStk_itm_qty(rs.getBigDecimal(3));
			tempData.setItm_qty(rs.getBigDecimal(4));
			tempData.setEmp_id(rs.getString(5));
			tempData.setStk_dt(rs.getLong(6));
			tempData.setStkloc(rs.getString(7));
			tempData.setCtnloc(rs.getString(8));
			tempData.setReason(rs.getInt(9));
			
			resultList.add(tempData);
		}
		
		return resultList;
	}
	
	public static List<EMP_SWS_RP_VIEW> GetEmpWorkRp(long bdt,long edt,String empGuid,Connection conn) throws SQLException{
		List<EMP_SWS_RP_VIEW> resultList = new ArrayList<EMP_SWS_RP_VIEW>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T1.BG_DT,T1.RP_DT,T1.RP_RAC_ID,T1.RP_RAC_NAME,T1.RP_WS,T1.FINISH_QTY,T3.SUB_WO_SUB_ID,T3.ITM_ID,T4.WO_ID,T4.LOT_ID FROM SWS_RP T1");
		sb.append(" JOIN SWS_STAFF T2 ON T2.SWS_RP_GUID=T1.SWS_RP_GUID AND T2.EMP_GUID=?");
		sb.append(" JOIN SUB_WO_SUB T3 ON T1.SWS_GUID=T3.SUB_WO_SUB_GUID");
		sb.append(" JOIN SUB_WO_MAIN T4 ON T3.SUB_WO_MAIN_GUID=T4.SUB_WO_MAIN_GUID");
		sb.append(" WHERE T1.RP_DT>=? AND T1.RP_DT<=? AND T1.RP_STATUS=2");
		sb.append(" ORDER BY T1.BG_DT");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		
		ps.setString(1, empGuid);
		ps.setLong(2, bdt);
		ps.setLong(3, edt);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			EMP_SWS_RP_VIEW tempData = new EMP_SWS_RP_VIEW();
			tempData.setBg_dt(rs.getLong(1));
			tempData.setRp_dt(rs.getLong(2));
			tempData.setRp_rac_id(rs.getString(3));
			tempData.setRp_rac_name(rs.getString(4));
			tempData.setRp_ws(rs.getString(5));
			tempData.setFinish_qty(rs.getBigDecimal(6));
			tempData.setSws_id(rs.getString(7));
			tempData.setItm_id(rs.getString(8));
			tempData.setWo_id(rs.getString(9));
			tempData.setLot_id(rs.getString(10));
			
			resultList.add(tempData);
		}
		return resultList;
	}
	
	public static EntityListDM<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW> GetEmpWorkRp(long bdt,long edt,String[] empIds, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		String sEmpIds = "";
		if(empIds!=null&&empIds.length>0)	{
			for(int i=0;i<empIds.length;i++){
				if(empIds[i].length()>0){
					if(i==empIds.length-1){
						sEmpIds = sEmpIds+"'"+empIds[i]+"'";
					}
					else{
						sEmpIds = sEmpIds+"'"+empIds[i]+"',";
					}
				}
			}
		}
		
		EntityListDM<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW> result = new EntityListDM<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW>();
		result.setDataList(new ArrayList<EMP_SWS_RP_VIEW>());
		
		String subSQL = "SELECT T1.BG_DT,T1.RP_DT,T1.RP_RAC_ID,T1.RP_RAC_NAME,T1.RP_WS,T1.FINISH_QTY,T3.SUB_WO_SUB_ID,T3.ITM_ID,T4.WO_ID,T4.LOT_ID,ISNULL(T1.WORK_TARGET,0) AS RAC_TARGET,T6.EMP_MAIN_ID,T6.EMP_NAME,T1.RP_WS_NO "
				+ "FROM SWS_RP T1 "
				+ "JOIN SWS_STAFF T2 ON T2.SWS_RP_GUID=T1.SWS_RP_GUID "
				+ "JOIN SUB_WO_SUB T3 ON T1.SWS_GUID=T3.SUB_WO_SUB_GUID "
				+ "JOIN SUB_WO_MAIN T4 ON T3.SUB_WO_MAIN_GUID=T4.SUB_WO_MAIN_GUID "
				+ "JOIN WO_RAC T5 ON T5.WO_DOC_ID=T4.WO_ID AND T1.RP_RAC_ID=T5.WO_RAC_ID "
				+ "JOIN EMP_MAIN T6 ON T6.EMP_MAIN_GUID=T2.EMP_GUID ";
		if(sEmpIds.length()>0){
			subSQL = subSQL+" AND T6.EMP_MAIN_ID IN ("+sEmpIds+") ";
		}
		String subSQLWhere = " WHERE T1.RP_DT>=? AND T1.RP_DT<=? AND T1.RP_STATUS=2 ";
		String subOrderby = " ORDER BY BG_DT";
		
		subSQL = subSQL + subSQLWhere;
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		PreparedStatement ps = conn.prepareStatement(sSQL);
		ps.setLong(1, bdt);
		ps.setLong(2, edt);
		ps.setInt(3, iRowStart);
		ps.setInt(4, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			EMP_SWS_RP_VIEW tempData = new EMP_SWS_RP_VIEW();
			tempData.setBg_dt(rs.getLong(1));
			tempData.setRp_dt(rs.getLong(2));
			tempData.setRp_rac_id(rs.getString(3));
			tempData.setRp_rac_name(rs.getString(4).split("　")[0]);
			tempData.setRp_ws(rs.getString(5));
			tempData.setFinish_qty(rs.getBigDecimal(6));
			tempData.setSws_id(rs.getString(7));
			tempData.setItm_id(rs.getString(8));
			tempData.setWo_id(rs.getString(9));
			tempData.setLot_id(rs.getString(10));
			tempData.setRac_target(rs.getBigDecimal(11));
			tempData.setEmp_id(rs.getString(12));
			tempData.setEmp_name(rs.getString(13));
			tempData.setRp_ws_no(rs.getString(14));
			
			result.getDataList().add(tempData);
		}
		rs.close();
		ps.close();
		
		subSQL = "SELECT COUNT(*) FROM SWS_RP T1 JOIN SWS_STAFF T2 ON T2.SWS_RP_GUID=T1.SWS_RP_GUID JOIN SUB_WO_SUB T3 ON T1.SWS_GUID=T3.SUB_WO_SUB_GUID JOIN SUB_WO_MAIN T4 ON T3.SUB_WO_MAIN_GUID=T4.SUB_WO_MAIN_GUID JOIN WO_RAC T5 ON T5.WO_DOC_ID=T4.WO_ID AND T1.RP_RAC_ID=T5.WO_RAC_ID JOIN EMP_MAIN T6 ON T6.EMP_MAIN_GUID=T2.EMP_GUID";
		if(sEmpIds.length()>0){
			subSQL = subSQL+" AND T6.EMP_MAIN_ID IN ("+sEmpIds+") ";
		}
		subSQL = subSQL+subSQLWhere;
		ps = conn.prepareStatement(subSQL);
		ps.setLong(1, bdt);
		ps.setLong(2, edt);
		
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
	
	public static List<EMP_SWS_RP_VIEW> GetEmpWorkRp2_Exp(long bdt,long edt, String[] empIds, Connection conn) throws SQLException{
		List<EMP_SWS_RP_VIEW> resultList = new ArrayList<EMP_SWS_RP_VIEW>();
		
		String sEmpIds = "";
		if(empIds!=null&&empIds.length>0)	{
			for(int i=0;i<empIds.length;i++){
				if(empIds[i].length()>0){
					if(i==empIds.length-1){
						sEmpIds = sEmpIds+"'"+empIds[i]+"'";
					}
					else{
						sEmpIds = sEmpIds+"'"+empIds[i]+"',";
					}
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T1.BG_DT,T1.RP_DT,T1.RP_RAC_ID,T1.RP_RAC_NAME,T1.RP_WS,T1.FINISH_QTY,T3.SUB_WO_SUB_ID,T3.ITM_ID,T4.WO_ID,T4.LOT_ID,ISNULL(T1.WORK_TARGET,0) AS RAC_TARGET,T6.EMP_MAIN_ID,T6.EMP_NAME,T1.RP_WS_NO FROM SWS_RP T1");
		sb.append(" JOIN SWS_STAFF T2 ON T2.SWS_RP_GUID=T1.SWS_RP_GUID");
		sb.append(" JOIN SUB_WO_SUB T3 ON T1.SWS_GUID=T3.SUB_WO_SUB_GUID");
		sb.append(" JOIN SUB_WO_MAIN T4 ON T3.SUB_WO_MAIN_GUID=T4.SUB_WO_MAIN_GUID");
		sb.append(" JOIN WO_RAC T5 ON T5.WO_DOC_ID=T4.WO_ID AND T1.RP_RAC_ID=T5.WO_RAC_ID");
		sb.append(" JOIN EMP_MAIN T6 ON T6.EMP_MAIN_GUID=T2.EMP_GUID");
		if(sEmpIds.length()>0)
		{
			sb.append(" AND T6.EMP_MAIN_ID IN("+sEmpIds+") ");
		}
		sb.append(" WHERE T1.RP_DT>=? AND T1.RP_DT<=? AND T1.RP_STATUS=2");
		sb.append(" ORDER BY T1.BG_DT");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		
		ps.setLong(1, bdt);
		ps.setLong(2, edt);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			EMP_SWS_RP_VIEW tempData = new EMP_SWS_RP_VIEW();
			tempData.setBg_dt(rs.getLong(1));
			tempData.setRp_dt(rs.getLong(2));
			tempData.setRp_rac_id(rs.getString(3));
			tempData.setRp_rac_name(rs.getString(4));
			tempData.setRp_ws(rs.getString(5));
			tempData.setFinish_qty(rs.getBigDecimal(6));
			tempData.setSws_id(rs.getString(7));
			tempData.setItm_id(rs.getString(8));
			tempData.setWo_id(rs.getString(9));
			tempData.setLot_id(rs.getString(10));
			tempData.setRac_target(rs.getBigDecimal(11));
			tempData.setEmp_id(rs.getString(12));
			tempData.setEmp_name(rs.getString(13));
			tempData.setRp_ws_no(rs.getString(14));
			
			resultList.add(tempData);
		}
		return resultList;
	}
	
	public static List<SUB_WO_SUB_VIEW> GetSWSByConditon(SUB_WO_SUB_VIEW entityRequest,Connection conn) throws SQLException{
		List<SUB_WO_SUB_VIEW> resultList=new ArrayList<SUB_WO_SUB_VIEW>();
		PreparedStatement ps =null;
		ResultSet rs = null;
		int index=0;
		
		try{
			String sqlSelect="SELECT T1.WO_ID,T3.PARA_VALUE,T1.M_QC_DOC,T1.SWS_QTY,T2.SUB_WO_SUB_ID,T1.LOT_ID,T1.ITM_ID,T1.PRE_SWS_ID FROM SUB_WO_MAIN T1"
+" JOIN SUB_WO_SUB T2 ON T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID AND T2.IS_DELETED='0' "
+" LEFT JOIN PARA_MAIN T3 ON T1.SP_ID=T3.PARA_MAIN_ID AND T3.PARA_TYPE_ID='SpInfo' AND T3.IS_DELETED='0' "
+" WHERE T1.IS_DELETED='0'";
		
			
			if(entityRequest.getLot_id()!=null && entityRequest.getLot_id().length()>0){
				sqlSelect+=" AND T1.LOT_ID=?";
			}
			
			if(entityRequest.getItm_id()!=null && entityRequest.getItm_id().length()>0){
				sqlSelect+=" AND T1.ITM_ID LIKE ?";
			}
			
			ps=conn.prepareStatement(sqlSelect);
		
			if(entityRequest.getLot_id()!=null && entityRequest.getLot_id().length()>0){
				index++;
				ps.setString(index, entityRequest.getLot_id());
			}
			
			if(entityRequest.getItm_id()!=null && entityRequest.getItm_id().length()>0){
				index++;
				ps.setString(index, entityRequest.getItm_id());
			}
			
			rs=ps.executeQuery();
			while(rs.next()){
				SUB_WO_SUB_VIEW entity=new SUB_WO_SUB_VIEW();
				entity.setWo_id(rs.getString(1));
				entity.setSp_id(rs.getString(2));
				entity.setM_qc_doc(rs.getString(3));
				entity.setSws_qty(rs.getBigDecimal(4));
				entity.setId(rs.getString(5));
				entity.setLot_id(rs.getString(6));
				entity.setItm_id(rs.getString(7));
				entity.setPre_sws_id(rs.getString(8));
			
			resultList.add(entity);
			}
			
			rs.close();
			ps.close();
			
			return resultList;
			
		}catch(Exception ex){
			throw ex;
		}finally{
			if (rs != null && !rs.isClosed())rs.close();
			if (ps != null && !ps.isClosed())ps.close();
		}
	}
	
	
	public static EntityListDM<STK_ITM_WKSITE,STK_ITM_WKSITE> GetStkItmWKSiteByConditonPaged(STK_ITM_WKSITE stkData, int page_no, int page_size, Connection conn) throws SQLException
	{
		ResultSet rs =null;
		PreparedStatement ps =null;
		
		EntityListDM<STK_ITM_WKSITE,STK_ITM_WKSITE> results = new EntityListDM<STK_ITM_WKSITE,STK_ITM_WKSITE>();
		results.setDataList(new ArrayList<STK_ITM_WKSITE>());
		int index=0;
		
		try{
			if (page_no <= 0)page_no = 1;
			if (page_size <= 0)page_size = 10;
			int iRowStart = (page_no - 1) * page_size + 1;
			int iRowEnd = iRowStart + page_size - 1;
			
			String subSQL="SELECT T2.STK_MAIN_ID,T1.SWS_ID,T1.ITM_ID,T1.STK_RAC_ID,T1.STK_RAC_NAME,T1.STK_VALUE FROM STK_ITM_WKSITE T1"
					+ " JOIN STK_MAIN T2 ON T1.STK_MAIN_GUID=T2.STK_MAIN_GUID AND T2.IS_DELETED='0' AND T2.IS_STK='0'"
					+ " WHERE T1.IS_DELETED='0' ";
			
			String subOrderby = " ORDER BY STK_MAIN_ID,SWS_ID";
			
			//查询条件---------------------------------------------------------
			if(stkData.getStk_main_id()!=null && stkData.getStk_main_id().length()>0){
				subSQL+=" AND T2.STK_MAIN_ID LIKE ? ";
			}
			if(stkData.getSws_id()!=null && stkData.getSws_id().length()>0){
				subSQL+=" AND T1.SWS_ID LIKE ?";
			}
			//------------------------------------------------------------------
			
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		    ps = conn.prepareStatement(sSQL);
			//查询条件---------------------------------------------------------
			if(stkData.getStk_main_id()!=null && stkData.getStk_main_id().length()>0){
				ps.setString(++index, stkData.getStk_main_id());
			}
			if(stkData.getSws_id()!=null && stkData.getSws_id().length()>0){
				ps.setString(++index, stkData.getSws_id());
			}
			//------------------------------------------------------------------
			ps.setInt(++index, iRowStart);
			ps.setInt(++index, iRowEnd);
			
		    rs = ps.executeQuery();
						
			while (rs.next()) {
				int i=0;
				STK_ITM_WKSITE entity=new STK_ITM_WKSITE();
				entity.setStk_main_id(rs.getString(++i));
				entity.setSws_id(rs.getString(++i));
				entity.setItm_id(rs.getString(++i));
				entity.setStk_rac_id(rs.getString(++i));
				entity.setStk_rac_name(rs.getString(++i));
				entity.setStk_value(rs.getBigDecimal(++i));
				
				results.getDataList().add(entity);
			}
			rs.close();
			ps.close();
			
			
			//统计总行数
			String sqlCount="SELECT COUNT(1) FROM ("+subSQL+") A";
			 ps = conn.prepareStatement(sqlCount);
			 index=0;
			//查询条件---------------------------------------------------------
			if(stkData.getStk_main_id()!=null && stkData.getStk_main_id().length()>0){
				ps.setString(++index, stkData.getStk_main_id());
			}
			if(stkData.getSws_id()!=null && stkData.getSws_id().length()>0){
				ps.setString(++index, stkData.getSws_id());
			}
			//------------------------------------------------------------------
			
			rs = ps.executeQuery();
			if (rs.next()) {
				results.setCount(rs.getInt(1));
			} else {
				results.setCount(0);
			}
			rs.close();
			ps.close();
					
			return results;
			
		}catch(Exception ex){
			throw ex;
		}finally{
			if (rs != null && !rs.isClosed())rs.close();
			if (ps != null && !ps.isClosed())ps.close();
		}
		
	}
	
	
	public static List<STK_ITM_WKSITE> GetStkItmWKSiteByConditon(String stkMainId,String swsId, Connection conn) throws SQLException
	{
		ResultSet rs =null;
		PreparedStatement ps =null;
		
		List<STK_ITM_WKSITE> results=new ArrayList<STK_ITM_WKSITE>();
		int index=0;
		
		try{
			
			String subSQL="SELECT T2.STK_MAIN_ID,T1.SWS_ID,T1.ITM_ID,T1.STK_RAC_ID,T1.STK_RAC_NAME,T1.STK_VALUE FROM STK_ITM_WKSITE T1"
					+ " JOIN STK_MAIN T2 ON T1.STK_MAIN_GUID=T2.STK_MAIN_GUID AND T2.IS_DELETED='0' AND T2.IS_STK='0'"
					+ " WHERE T1.IS_DELETED='0' ";
			
			String subOrderby = " ORDER BY T2.STK_MAIN_ID,T1.SWS_ID";
			
			//查询条件---------------------------------------------------------
			if(stkMainId!=null && stkMainId.length()>0){
				subSQL+=" AND T2.STK_MAIN_ID LIKE ? ";
			}
			if(swsId!=null && swsId.length()>0){
				subSQL+=" AND T1.SWS_ID LIKE ?";
			}
			//------------------------------------------------------------------
			
			String sSQL =subSQL+ subOrderby;

		    ps = conn.prepareStatement(sSQL);
			//查询条件---------------------------------------------------------
			if(stkMainId!=null && stkMainId.length()>0){
				ps.setString(++index, stkMainId);
			}
			if(swsId!=null && swsId.length()>0){
				ps.setString(++index, swsId);
			}
			//------------------------------------------------------------------
			
		    rs = ps.executeQuery();		
			while (rs.next()) {
				int i=0;
				STK_ITM_WKSITE entity=new STK_ITM_WKSITE();
				entity.setStk_main_id(rs.getString(++i));
				entity.setSws_id(rs.getString(++i));
				entity.setItm_id(rs.getString(++i));
				entity.setStk_rac_id(rs.getString(++i));
				entity.setStk_rac_name(rs.getString(++i));
				entity.setStk_value(rs.getBigDecimal(++i));
				
				results.add(entity);
			}
			rs.close();
			ps.close();
					
			return results;
			
		}catch(Exception ex){
			throw ex;
		}finally{
			if (rs != null && !rs.isClosed())rs.close();
			if (ps != null && !ps.isClosed())ps.close();
		}
		
	}
	
	public static List<RBA_CTN_RE_VIEW> GetRbaCtnReByConditon(RBA_CTN_RE_VIEW searchEntity, Connection conn) throws SQLException
	{
		ResultSet rs =null;
		PreparedStatement ps =null;
		
		List<RBA_CTN_RE_VIEW> results=new ArrayList<RBA_CTN_RE_VIEW>();
		int index=0;
		
		try{
			
			String subSQL=" SELECT T2.ITM_ID,T1.WO_DOC_ID,T3.CREATED_DT,ITM.ITM_NAME,T3.CTN_BACO,T2.ITM_QTY,T3.LOT_ID FROM RBA_DOC T1"
					+" INNER JOIN RBA_ITM T2 ON T1.RBA_DOC_ID=T2.RBA_DOC_ID"
					+" INNER JOIN ITM_MAIN ITM ON T2.ITM_ID=ITM.ITM_MAIN_ID AND ITM.IS_DELETED='0'"
					+" LEFT JOIN RBA_CTN_RE T3 ON T3.RBA_DOC_ID=T1.RBA_DOC_ID AND T3.RBA_ITM_SEQNO=T2.RBA_ITM_SEQNO AND T3.IS_DELETED='0'"
					+" WHERE 1=1  ";
						
			//查询条件---------------------------------------------------------
			if(searchEntity.getWo_doc_id()!=null && searchEntity.getWo_doc_id().length()>0){
				subSQL+=" AND T1.WO_DOC_ID LIKE ? ";
			}
			if(searchEntity.getItm_id()!=null && searchEntity.getItm_id().length()>0){
				subSQL+=" AND T2.ITM_ID =?";
			}
			//------------------------------------------------------------------
			
			String sSQL=" SELECT A.*,S2.M_QC_DOC,P.PARA_VALUE FROM("
	+subSQL
+" ) AS A"
+" LEFT JOIN SUB_WO_SUB S1 ON S1.SUB_WO_SUB_ID=A.CTN_BACO AND S1.IS_DELETED='0'"
+" LEFT JOIN SUB_WO_MAIN S2 ON S2.SUB_WO_MAIN_GUID=S1.SUB_WO_MAIN_GUID AND S2.IS_DELETED='0'"
+" LEFT JOIN PARA_MAIN P ON P.PARA_MAIN_ID=S2.SP_ID AND P.PARA_TYPE_ID='SpInfo' AND P.IS_DELETED='0'"
+" ORDER BY A.WO_DOC_ID";

		    ps = conn.prepareStatement(sSQL);
			//查询条件---------------------------------------------------------
			if(searchEntity.getWo_doc_id()!=null && searchEntity.getWo_doc_id().length()>0){
				ps.setString(++index, searchEntity.getWo_doc_id());
			}
			if(searchEntity.getItm_id()!=null && searchEntity.getItm_id().length()>0){
				ps.setString(++index, searchEntity.getItm_id());
			}
			//------------------------------------------------------------------
			
		    rs = ps.executeQuery();		
			while (rs.next()) {
				
				int i=0;
				RBA_CTN_RE_VIEW entity=new RBA_CTN_RE_VIEW();
				entity.setItm_id(rs.getString(++i));
				entity.setWo_doc_id(rs.getString(++i));
				entity.setCreated_dt(rs.getLong(++i));
				entity.setItm_name(rs.getString(++i));
				entity.setCtn_baco(rs.getString(++i));
				entity.setItm_qty(rs.getBigDecimal(++i));
				entity.setLot_id(rs.getString(++i));
				entity.setM_qc_doc(rs.getString(++i));
				entity.setSp_name(rs.getString(++i));				
				
				results.add(entity);
			}
			rs.close();
			ps.close();
					
			return results;
			
		}catch(Exception ex){
			throw ex;
		}finally{
			if (rs != null && !rs.isClosed())rs.close();
			if (ps != null && !ps.isClosed())ps.close();
		}
		
	}
	
	
	public static EntityListDM<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW> getBacoTranReport(String whId, String itmId, String ctnBaco,long bg, long ed, int in_out, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW> result = new EntityListDM<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW>();
		result.setDataList(new ArrayList<ITM_TRAN_REPORT_VIEW>());
		
		String subSQL = " SELECT T1.CREATED_DT,T4.USR_NICKNAME,T1.TRAN_TYPE,T5.PARA_VALUE,T3.ITM_ID,T2.TRAN_QTY,T2.CTN_BACO "
				+ " FROM TRAN_MAIN T1 "
				+ " INNER JOIN TRAN_BACO T2 ON T2.TRAN_GUID=T1.TRAN_MAIN_GUID "
				+ " INNER JOIN CTN_MAIN T3 ON T3.CTN_BACO=T2.CTN_BACO "
				+ " INNER JOIN USR_MAIN T4 ON T1.CREATED_BY=T4.USR_MAIN_GUID "
				+ " LEFT JOIN PARA_MAIN T5 ON T1.TRAN_REASON_ID=T5.PARA_MAIN_ID AND T5.PARA_TYPE_ID='OtherInvReason' ";
		
		String subSQLWhere = " WHERE T1.WH_ID=? AND T1.IN_OUT=? ";
		String subOrderby = " ORDER BY CREATED_DT DESC ";
		
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T3.ITM_ID LIKE ? ";
		}
		if (bg>0) {
			subSQLWhere += " AND T1.CREATED_DT>=? ";
		}
		if (ed>0) {
			subSQLWhere += " AND T1.CREATED_DT<=? ";
		}
		if(!StringUtils.isEmpty(ctnBaco)){
			subSQLWhere+=" AND T2.CTN_BACO LIKE ?";
		}
		
		subSQL = subSQL + subSQLWhere;
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		PreparedStatement ps = conn.prepareStatement(sSQL);
		int index=0;
		ps.setString(++index, whId);
		ps.setInt(++index, in_out);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
		}
		if(!StringUtils.isEmpty(ctnBaco)){
			ps.setString(++index, ctnBaco+"%");
		}
		
		ps.setInt(++index, iRowStart);
		ps.setInt(++index, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ITM_TRAN_REPORT_VIEW tempView = new ITM_TRAN_REPORT_VIEW();
			tempView.setTran_dt(rs.getLong(1));
			tempView.setOperator(rs.getString(2));
			tempView.setTran_type(rs.getInt(3));
			tempView.setTran_reason(rs.getString(4));
			tempView.setItm_main_id(rs.getString(5));
			tempView.setItm_qty(rs.getBigDecimal(6));
			tempView.setCtn_baco(rs.getString(7));
			result.getDataList().add(tempView);
		}
		rs.close();
		ps.close();
		
		
		String subSQLCount = "SELECT COUNT(A.CREATED_DT) FROM ("+subSQL+") AS A";
		ps = conn.prepareStatement(subSQLCount);
		index=0;
		
		ps.setString(++index, whId);
		ps.setInt(++index, in_out);
		
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
		}
		if(!StringUtils.isEmpty(ctnBaco)){
			ps.setString(++index, ctnBaco+"%");
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
	
	public static List<ITM_TRAN_REPORT_VIEW> getBacoTranReport_Exp(String whId, String itmId, String ctnBaco, long bg, long ed, int in_out, int exportSize, Connection conn) throws SQLException
	{
		List<ITM_TRAN_REPORT_VIEW> result = new ArrayList<ITM_TRAN_REPORT_VIEW>();
				
		String subSQL = " SELECT TOP "+String.valueOf(exportSize)+" T1.CREATED_DT,T4.USR_NICKNAME,T1.TRAN_TYPE,T5.PARA_VALUE,T3.ITM_ID,T2.TRAN_QTY,T2.CTN_BACO "
				+ " FROM TRAN_MAIN T1 "
				+ " INNER JOIN TRAN_BACO T2 ON T2.TRAN_GUID=T1.TRAN_MAIN_GUID "
				+ " INNER JOIN CTN_MAIN T3 ON T3.CTN_BACO=T2.CTN_BACO "
				+ " INNER JOIN USR_MAIN T4 ON T1.CREATED_BY=T4.USR_MAIN_GUID "
				+ " LEFT JOIN PARA_MAIN T5 ON T1.TRAN_REASON_ID=T5.PARA_MAIN_ID AND T5.PARA_TYPE_ID='OtherInvReason' ";
		
		String subSQLWhere = " WHERE T1.WH_ID=? AND T1.IN_OUT=? ";
		String subOrderby = " ORDER BY T1.CREATED_DT DESC ";
		
		if (!StringUtils.isEmpty(itmId)) {
			subSQLWhere += " AND T3.ITM_ID LIKE ? ";
		}
		if (bg>0) {
			subSQLWhere += " AND T1.CREATED_DT>=? ";
		}
		if (ed>0) {
			subSQLWhere += " AND T1.CREATED_DT<=? ";
		}
		if(!StringUtils.isEmpty(ctnBaco)){
			subSQLWhere+=" AND T2.CTN_BACO LIKE ?";
		}
		
		subSQL = subSQL + subSQLWhere + subOrderby;
		
		PreparedStatement ps = conn.prepareStatement(subSQL);
		int index=0;
		ps.setString(++index, whId);
		ps.setInt(++index, in_out);
		if(!StringUtils.isEmpty(itmId)){
			ps.setString(++index, itmId+"%");
		}
		if (bg>0) {
			ps.setLong(++index, bg);
		}
		if (ed>0) {
			ps.setLong(++index, ed);
		}
		if(!StringUtils.isEmpty(ctnBaco)){
			ps.setString(++index, ctnBaco+"%");
		}
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ITM_TRAN_REPORT_VIEW tempView = new ITM_TRAN_REPORT_VIEW();
			tempView.setTran_dt(rs.getLong(1));
			tempView.setOperator(rs.getString(2));
			tempView.setTran_type(rs.getInt(3));
			tempView.setTran_reason(rs.getString(4));
			tempView.setItm_main_id(rs.getString(5));
			tempView.setItm_qty(rs.getBigDecimal(6));
			tempView.setCtn_baco(rs.getString(7));
			result.add(tempView);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static List<TRAN_BACO_VIEW> getItmDetailTranReport(String tranGuid,String itmId, Connection conn) throws SQLException
	{
		List<TRAN_BACO_VIEW> result = new ArrayList<TRAN_BACO_VIEW>();
				
		String subSQL ="SELECT T2.ITM_ID,T1.CTN_BACO,T1.TRAN_QTY FROM TRAN_BACO T1 "
				+ " INNER JOIN CTN_MAIN T2 ON T2.CTN_BACO=T1.CTN_BACO AND T1.TRAN_GUID=? AND T2.ITM_ID=?"
				+ " ORDER BY T1.CTN_BACO ";
		
		PreparedStatement ps = conn.prepareStatement(subSQL);
		int index=0;
		ps.setString(++index, tranGuid);
		ps.setString(++index, itmId);
			
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			TRAN_BACO_VIEW tempView = new TRAN_BACO_VIEW();
			tempView.setItm_id(rs.getString(1));
			tempView.setCtn_baco(rs.getString(2));
			tempView.setTran_qty(rs.getBigDecimal(3));

			result.add(tempView);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
}

