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

import org.apache.commons.lang3.StringUtils;

import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.label.domain.entities.CTN_MAIN;
import com.xinyou.label.domain.models.CERT_SWS_DOC;
import com.xinyou.label.domain.models.SWS_PRINT_DOC;
import com.xinyou.label.domain.models.TRAN_DOC;
import com.xinyou.label.domain.models.WO_PRINT_DOC;
import com.xinyou.label.domain.viewes.CERT_DOC_VIEW;
import com.xinyou.label.domain.viewes.CERT_SWS_RE_VIEW;
import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;
import com.xinyou.label.domain.viewes.ITM_INV_REPORT_VIEW;
import com.xinyou.label.domain.viewes.SUB_WO_MAIN_VIEW;
import com.xinyou.label.domain.viewes.SUB_WO_SUB_VIEW;
import com.xinyou.label.domain.viewes.SWS_RAC_VIEW;



public class Cert_Biz {
	
	/**
	 * 添加合格证
	 * @param doc
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public static String addCert(CERT_DOC_VIEW doc, Connection conn) throws Exception{
		PreparedStatement ps=null;
		
		try{
			String sql="INSERT INTO CERT_DOC(CERT_DOC_GUID,CERT_DOC_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,DATA_VER,ITM_QTY,ITM_ID,ITM_NAME,CERT_STATUS,CERT_YEAR) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, doc.getGuid());
			ps.setString(2, doc.getId());
			ps.setLong(3, new Date().getTime());
			ps.setString(4, doc.getCreated_by());
			ps.setLong(5, new Date().getTime());
			ps.setString(6, doc.getUpdated_by());
			ps.setInt(7, 0);
			ps.setString(8, doc.getClient_guid());
			ps.setString(9, doc.getData_ver());
			
			//ITM_QTY,ITM_ID,ITM_NAME,CERT_STATUS,CERT_YEAR
			ps.setBigDecimal(10, doc.getItm_qty());
			ps.setString(11, doc.getItm_id());
			ps.setString(12, doc.getItm_name());
			ps.setInt(13, doc.getCert_status());
			ps.setString(14, doc.getCert_year());	
			
			ps.execute();
			
		}catch(Exception ex){
			throw ex;
		} finally{
			if(ps!=null&&!ps.isClosed()){
				ps.close();
			}
		}
		
		return doc.getId();
	}
	
	/**
	 * 生成合格证  以C打头
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public static String GenerateDocId(String maxId) throws Exception{
			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_str = sdf.format(new Date());
		
		maxId=maxId==null?"0":maxId.substring(1);
		String doc_seqno = maxId.replaceFirst(date_str, ""); 
		doc_seqno = Long.parseLong(doc_seqno)+1+"";
		while(doc_seqno.length()<6){
			doc_seqno="0"+doc_seqno;
		}
		return "C"+date_str+doc_seqno;		
	}
	
	public static void addCerts(CERT_DOC_VIEW doc, Connection conn,String operator, String client, String data_ver) throws Exception{
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try{
			
			//1.计算箱数			
			BigDecimal totalQty=doc.getTotal_qty();//总入库量
			BigDecimal packQty=doc.getPack_qty();//装箱数量
			
			if(totalQty.compareTo(BigDecimal.ZERO)<=0){
				return;
			}
			if(packQty.compareTo(BigDecimal.ZERO)<=0){
				return;
			}
			
			BigDecimal bPackNum=totalQty.divide(packQty, 0,BigDecimal.ROUND_DOWN);
			int iPackNum=bPackNum.intValue();//箱数
			
			BigDecimal bLastQty=totalQty.subtract(packQty.multiply(bPackNum));//不足整箱的数量
			if(bLastQty.compareTo(BigDecimal.ZERO)>0){
				iPackNum++;
			}
			
			//获取最大的合格证号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date_str = sdf.format(new Date());
			ps = conn.prepareStatement("SELECT MAXNO = MAX(CERT_DOC_ID) FROM CERT_DOC WHERE CERT_DOC_ID LIKE ?");
			ps.setString(1, "C"+date_str+"%");
		    rs = ps.executeQuery();
			String maxId = null;
			if(rs.next()){
				maxId = rs.getString(1);

			}
			
			String currentId=maxId;
			
			for(int i=1;i<=iPackNum;i++){
				CERT_DOC_VIEW newDoc=new CERT_DOC_VIEW();
				
				String guid=UUID.randomUUID().toString();
				currentId=GenerateDocId(currentId);
				
				newDoc.setGuid(guid);
				newDoc.setId(currentId);
				newDoc.setCreated_by(operator);
				newDoc.setUpdated_by(operator);
				newDoc.setClient_guid(client);
				newDoc.setData_ver(data_ver);				
				
				if(i==iPackNum && bLastQty.compareTo(BigDecimal.ZERO)>0){
					newDoc.setItm_qty(bLastQty);
				}else {
					newDoc.setItm_qty(packQty);
				}
								
				newDoc.setItm_id(doc.getItm_id());
				newDoc.setItm_name(doc.getItm_name());
				newDoc.setCert_status(0);
				newDoc.setCert_year(doc.getCert_year());
				
				addCert(newDoc,conn);
			}			
			
		}catch(Exception ex){
			throw ex;
		} finally{
			if(rs!=null&&!rs.isClosed()){
				rs.close();
			}
			if(ps!=null&&!ps.isClosed()){
				ps.close();
			}
			
		}
	}
	
	public static EntityListDM<CERT_DOC_VIEW,CERT_DOC_VIEW> getCerts(CERT_DOC_VIEW doc, int page_no, int page_size, Connection conn) throws SQLException
	{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
		
		EntityListDM<CERT_DOC_VIEW,CERT_DOC_VIEW> result = new EntityListDM<CERT_DOC_VIEW,CERT_DOC_VIEW>();
		result.setDataList(new ArrayList<CERT_DOC_VIEW>());
		
		String subSQL = "SELECT CERT_DOC_GUID,CERT_DOC_ID,ITM_ID,ITM_NAME,ITM_QTY,CERT_STATUS,CERT_YEAR,CREATED_DT FROM CERT_DOC ";
		String subSQLWhere = " WHERE 1=1 ";
		String subOrderby = " ORDER BY CREATED_DT DESC ";
		
		if (!StringUtils.isEmpty(doc.getItm_id())) {
			subSQLWhere += " AND ITM_ID LIKE ? ";
		}
		if(!StringUtils.isEmpty(doc.getId())){
			subSQLWhere+=" AND CERT_DOC_ID LIKE ? ";
		}
		
		subSQL = subSQL + subSQLWhere;
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";

		PreparedStatement ps = conn.prepareStatement(sSQL);
		int index = 0;
		if (!StringUtils.isEmpty(doc.getItm_id())) {
			ps.setString(++index, doc.getItm_id());
		}
		if(!StringUtils.isEmpty(doc.getId())){
			ps.setString(++index, doc.getId());
		}
		ps.setInt(++index, iRowStart);
		ps.setInt(++index, iRowEnd);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			CERT_DOC_VIEW view=new CERT_DOC_VIEW();
			view.setGuid(rs.getString(1));
			view.setId(rs.getString(2));
			view.setItm_id(rs.getString(3));
			view.setItm_name(rs.getString(4));
			view.setItm_qty(rs.getBigDecimal(5));
			view.setCert_status(rs.getInt(6));
			view.setCert_year(rs.getString(7));
			
			result.getDataList().add(view);
		}
		rs.close();
		ps.close();
		
		String sSQLCount = "SELECT COUNT(1) FROM ("+subSQL+") A";
		ps = conn.prepareStatement(sSQLCount);
		index = 0;
		if (!StringUtils.isEmpty(doc.getItm_id())) {
			ps.setString(++index, doc.getItm_id());
		}
		if(!StringUtils.isEmpty(doc.getId())){
			ps.setString(++index, doc.getId());
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
	
	public static List<CERT_DOC_VIEW> getCertPrintList(List<String> guids,Connection conn) throws Exception
	{
		ArrayList<CERT_DOC_VIEW> returnList = new ArrayList<CERT_DOC_VIEW>();
		
		StringBuilder sb = new  StringBuilder();
		String sbGuidString;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			for( String guid : guids)
			{
				sb.append("'");
				sb.append(guid);
				sb.append("',");
			}
			
			sbGuidString = sb.toString();
			if(sbGuidString.length()>0)
			{
				sbGuidString = sbGuidString.substring(0, sbGuidString.length()-1);
			}
			else
			{
				return returnList;
			}

			ps = conn.prepareStatement("SELECT CERT_DOC_GUID,CERT_DOC_ID,ITM_ID,ITM_NAME,ITM_QTY,CERT_STATUS,CERT_YEAR FROM CERT_DOC WHERE CERT_DOC_GUID IN("+sbGuidString+")");
			rs=ps.executeQuery();
			while(rs.next())
			{
				CERT_DOC_VIEW view=new CERT_DOC_VIEW();
				view.setGuid(rs.getString(1));
				view.setId(rs.getString(2));
				view.setItm_id(rs.getString(3));
				view.setItm_name(rs.getString(4));
				view.setItm_qty(rs.getBigDecimal(5));
				view.setCert_status(rs.getInt(6));
				view.setCert_year(rs.getString(7));
				
				returnList.add(view);			
			}
			rs.close();
			ps.close();			
		}
		catch (Exception e) {
			throw e;
		} finally {
			if(rs!=null&&!rs.isClosed()){
				rs.close();
			}
			if(ps!=null&&!ps.isClosed()){
				ps.close();
			}
		}
		
		return returnList;
	}
	
	public static void delCertByGuid(String guid,Connection conn) throws Exception{
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try{
			String certId="";

			String sqlSelect="SELECT CERT_STATUS,CERT_DOC_ID FROM CERT_DOC WHERE CERT_DOC_GUID=?";
			ps=conn.prepareStatement(sqlSelect);
			ps.setString(1, guid);
			rs=ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)==2){
					throw new Exception("已经存在出入库记录,不可删除！");
				}
				certId=rs.getString(2);
			}
			rs.close();
			ps.close();
			
			CTN_MAIN_VIEW ctn=Common_Biz.getCtnByBaco(certId,conn);
			if(ctn!=null){
				if(ctn.getWh_id()!=null&&!ctn.getWh_id().isEmpty()&&!ctn.getWh_id().equals("0301")){					
				throw new Exception("已经入库,不可删除！");	
				}
			}
			
			String sqlDel="DELETE FROM CTN_MAIN WHERE CTN_BACO=?";
			ps=conn.prepareStatement(sqlDel);
			ps.setString(1, certId);
			ps.execute();
			ps.close();
			
			String sql="DELETE FROM CERT_DOC WHERE CERT_DOC_GUID=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, guid);
			ps.execute();
			ps.close();
			
			String sqlRe="DELETE FROM CERT_SWS_RE WHERE CERT_DOC_GUID=?";
			ps=conn.prepareStatement(sqlRe);
			ps.setString(1, guid);
			ps.execute();
			ps.close();
			
		}catch(Exception e){
			throw e;
		}finally{
			if(rs!=null&&!rs.isClosed()){
				rs.close();
			}
			if(ps!=null&&!ps.isClosed()){
				ps.close();
			}
		}
	}
	
	public static CERT_DOC_VIEW getCertById(String id,Connection conn) throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		CERT_DOC_VIEW view=new CERT_DOC_VIEW();
		
		try{
			String sql="SELECT CERT_DOC_GUID,CERT_DOC_ID,ITM_ID,ITM_NAME,ITM_QTY,CERT_STATUS FROM CERT_DOC WHERE CERT_DOC_ID=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				view.setGuid(rs.getString(1));
				view.setId(rs.getString(2));
				view.setItm_id(rs.getString(3));
				view.setItm_name(rs.getString(4));
				view.setItm_qty(rs.getBigDecimal(5));
				view.setCert_status(rs.getInt(6));
				
			}
			rs.close();
			ps.close();
			
			
		}catch(Exception e){
			throw e;
		}finally{
			if(rs!=null&&!rs.isClosed()){
				rs.close();
			}
			if(ps!=null&&!ps.isClosed()){
				ps.close();
			}
		}
		
		return view;
	}
	
	public static void doCertSwsBind(CERT_SWS_DOC doc,Connection conn,String operator, String client, String data_ver) throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try{
			if(doc==null){
				return;
			}
			
			List<CERT_SWS_RE_VIEW> lst=doc.getSwsList();
			if(lst==null||lst.size()==0){
				return;
			}
			
			CERT_DOC_VIEW cert=doc.getCertDoc();
			
			for(CERT_SWS_RE_VIEW view:lst){
				
				//1.插入合格证-流程票 关联表
				String guid=UUID.randomUUID().toString();
				
				String sqlAdd="INSERT INTO CERT_SWS_RE(CERT_SWS_RE_GUID,CERT_SWS_RE_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,DATA_VER,CERT_DOC_GUID,CERT_DOC_ID,SWS_GUID,SWS_ID,SWS_QTY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps=conn.prepareStatement(sqlAdd);
				ps.setString(1, guid);
				ps.setString(2, guid);
				ps.setLong(3, new Date().getTime());
				ps.setString(4, operator);
				ps.setLong(5, new Date().getTime());
				ps.setString(6, operator);
				ps.setInt(7, 0);
				ps.setString(8, client);
				ps.setString(9, data_ver);
				ps.setString(10, view.getCert_doc_guid());
				ps.setString(11, view.getCert_doc_id());
				ps.setString(12, view.getSws_guid());
				ps.setString(13, view.getSws_id());
				ps.setBigDecimal(14, view.getSws_qty());
				
				ps.execute();
				ps.close();				
			}
			
			//2.修改合格证的状态为：1：已绑定
			String sqlU="UPDATE CERT_DOC SET CERT_STATUS=1 WHERE CERT_DOC_GUID=?";
			ps=conn.prepareStatement(sqlU);
			ps.setString(1, cert.getGuid());
			ps.execute();
			ps.close();
			
			//3.入虚拟库0301
			String whGuid="";
			ps=conn.prepareStatement("SELECT CTN_MAIN_GUID FROM CTN_MAIN WHERE CTN_MAIN_ID='0301' AND CTN_TYPE=3");
			rs=ps.executeQuery();
			if(rs.next()){
				whGuid=rs.getString(1);
				
			}else{
				throw new Exception("虚拟仓库0301不存在！");
			}
			rs.close();
			ps.close();
			
			CTN_MAIN ctn = new CTN_MAIN();
			ctn.setCtn_baco(cert.getId());
			ctn.setCtn_main_id(cert.getId());
			ctn.setCtn_status(3);
			ctn.setCtn_type(13);//合格证
			ctn.setItm_id(cert.getItm_id());
			ctn.setItm_qty(cert.getItm_qty());
			ctn.setParent_ctn_guid("");
			ctn.setWh_area_guid("");
			ctn.setWh_guid(whGuid);
			ctn.setWh_loc_guid("");
			ctn.setWh_package_guid("");
			ctn.setWh_plt_guid("");
			ctn.setWh_shelf_guid("");
			
			//工单是否需要记录？
//			ctn.setLot_id(subWo.getLot_id());
//			ctn.setBase_type(0);
//			ctn.setBase_id(subWo.getWo_id());
			
			Common_Biz.addBaco(ctn, operator, data_ver, client, conn);
			
		}catch(Exception e){
			throw e;
		}finally{
			if(rs!=null&&!rs.isClosed()){
				rs.close();
			}
			if(ps!=null&&!ps.isClosed()){
				ps.close();
			}
		}
	}
	
	public static SUB_WO_SUB_VIEW getSwsForCertBind(String swsId,String itmId,Connection conn) throws Exception{
		SUB_WO_SUB_VIEW sub=new SUB_WO_SUB_VIEW();
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try{
			BigDecimal swsFinishedQty=BigDecimal.ZERO;
			
			ps=conn.prepareStatement("SELECT SUB_WO_SUB_GUID,FINISH_QTY,ITM_ID FROM SUB_WO_SUB WHERE SUB_WO_SUB_ID=?");
			ps.setString(1, swsId);
			rs=ps.executeQuery();
			if(rs.next()){
				if(rs.getString(1)==null||rs.getString(1)==""){
					throw new Exception("流程票："+swsId+" 不存在!");
				}
				if(rs.getString(3)==null||rs.getString(3)==""||!rs.getString(3).equals(itmId)){
					throw new Exception("流程票："+swsId+" 图号与合格证不符!");
				}
				
				swsFinishedQty=rs.getBigDecimal(2);
				
				sub.setGuid(rs.getString(1));
			}else{
				throw new Exception("流程票："+swsId+" 不存在!");
			}
			rs.close();
			ps.close();
			
			ps=conn.prepareStatement("SELECT ISNULL(SUM(SWS_QTY),0) FROM CERT_SWS_RE WHERE SWS_ID=? ");
			ps.setString(1, swsId);
			rs=ps.executeQuery();
			if(rs.next()){
				swsFinishedQty=swsFinishedQty.subtract(rs.getBigDecimal(1));
			}
			
			sub.setFinish_qty(swsFinishedQty);
			
		}catch(Exception e){
			throw e;
		}finally{
			if(rs!=null&&!rs.isClosed()){
				rs.close();
			}
			if(ps!=null&&!ps.isClosed()){
				ps.close();
			}
		}
		
		return sub;
	}	
	
	
	
	public static void  updCertStatusById(String id,int certStatus,Connection conn) throws Exception{
		PreparedStatement ps=null;
		
		try{
			String sqlU="UPDATE CERT_DOC SET CERT_STATUS=? WHERE CERT_DOC_ID=?";
			ps=conn.prepareStatement(sqlU);
			ps.setInt(1, certStatus);
			ps.setString(2, id);
			ps.execute();
			ps.close();
		}catch(Exception e){
			throw e;
		}finally{
			
		}
	}
}

