package com.xinyou.label.service;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xinyou.frame.domain.biz.EMP_BIZ;
import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.models.EMP_MAIN_VIEW;
import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.frame.request.BasicRequest;
import com.xinyou.frame.response.BasicResponse;
import com.xinyou.frame.service.BaseService;
import com.xinyou.label.domain.biz.Cert_Biz;
import com.xinyou.label.domain.biz.Common_Biz;
import com.xinyou.label.domain.biz.Erp_Biz;
import com.xinyou.label.domain.biz.InvReport_Biz;
import com.xinyou.label.domain.biz.Wo_Biz;
import com.xinyou.label.domain.entities.SUB_WO_MAIN;
import com.xinyou.label.domain.entities.SWS_RP;
import com.xinyou.label.domain.models.CERT_SWS_DOC;
import com.xinyou.label.domain.models.ERP_RDA_DOC;
import com.xinyou.label.domain.models.SWS_DOC;
import com.xinyou.label.domain.models.SWS_RP_DOC;
import com.xinyou.label.domain.models.SWS_SCRAP_DOC;
import com.xinyou.label.domain.models.WO_PRINT_DOC;
import com.xinyou.label.domain.viewes.CERT_DOC_VIEW;
import com.xinyou.label.domain.viewes.CERT_SWS_RE_VIEW;
import com.xinyou.label.domain.viewes.RAC_VIEW;
import com.xinyou.label.domain.viewes.SCRAP_REPORT_VIEW;
import com.xinyou.label.domain.viewes.SUB_WO_SUB_VIEW;
import com.xinyou.label.domain.viewes.SWS_RP_VIEW;
import com.xinyou.label.domain.viewes.SWS_SCRAP_VIEW;
import com.xinyou.label.domain.viewes.WO_DOC_VIEW;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;
import com.xinyou.util.StringUtil;

@Path("/wo")
@Produces({MediaType.APPLICATION_JSON})
public class Wo extends BaseService {
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getWoList")
	public BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW> getWoList(BasicRequest<String,String> basic_request){
		BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW> response = new BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW>();
		Connection conn = null;
		Connection erp_conn=null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erp_conn = ConnectionManager.getConnection("jdbc/erp");
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataDM(Wo_Biz.getWoList(basic_request.getData_char(), basic_request.getPage_no(), basic_request.getPage_size(), conn,erp_conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
			try {if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();} catch (SQLException e) {};
		}
		return response;
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getRacListByWoId")
	public BasicResponse<RAC_VIEW,RAC_VIEW> getRacListByWoId(BasicRequest<String,String> basic_request){
		BasicResponse<RAC_VIEW,RAC_VIEW> response = new BasicResponse<RAC_VIEW,RAC_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataList(Wo_Biz.getRacListByWoId(basic_request.getData_char(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/upRacSpec")
	public BasicResponse<String,String> upRacSpec(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			Wo_Biz.updateRacSpec(basic_request.getData_char(), basic_request.getData_char2(), basic_request.getData_char3(), basic_request.getData_char4(), conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/addSubWo")
	public BasicResponse<String,String> addSubWo(BasicRequest<SUB_WO_MAIN,SUB_WO_MAIN> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			String preSwsId=basic_request.getData_entity().getPre_sws_id();
			if(preSwsId!=null&&!preSwsId.equals("")){
				SUB_WO_SUB_VIEW swsView=Wo_Biz.getSwsOnlyById(preSwsId,conn);
				if(swsView.getId()==null||swsView.getId()==""){
					throw new Exception("未找到前置流程票号的数据！");
				}
			}
			
			conn.setAutoCommit(false);
			
			String operator = ui.getUsr_main_guid();
			basic_request.getData_entity().setCreated_by(operator);
			basic_request.getData_entity().setUpdated_by(operator);
			basic_request.getData_entity().setClient_guid("gl");
			basic_request.getData_entity().setData_ver("1.0.0.0");
			
			Wo_Biz.addSubWo(basic_request.getData_entity(), conn);
			
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSwsList")
	public BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> getSwsList(BasicRequest<String,String> basic_request){
		BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> response = new BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataDM(Wo_Biz.getSwsList(basic_request.getData_char2(),basic_request.getData_char(), basic_request.getPage_no(), basic_request.getPage_size(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getWoPrintList")
	public BasicResponse<WO_PRINT_DOC,WO_PRINT_DOC> getWoPrintList(BasicRequest<ArrayList<String>,String> basic_request){
		BasicResponse<WO_PRINT_DOC,WO_PRINT_DOC> response = new BasicResponse<WO_PRINT_DOC,WO_PRINT_DOC>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			ArrayList<String> swsGuidList = new ArrayList<String>();
			String[] swsGuids = basic_request.getData_char().split(",");
			for(int i=0;i<swsGuids.length;i++)
			{
				swsGuidList.add(swsGuids[i]);
			}
			
			response.setDataList(Wo_Biz.getWoPrintList(swsGuidList, conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSwsOnlyById")
	public BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> getSwsOnlyById(BasicRequest<String,String> basic_request){
		BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> response = new BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW>();
		response.setSvrdt(new Date().getTime());
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataEntity(Wo_Biz.getSwsOnlyById(basic_request.getData_char(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSwsById")
	public BasicResponse<SWS_DOC,SWS_DOC> getSwsById(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_DOC,SWS_DOC> response = new BasicResponse<SWS_DOC,SWS_DOC>();
		response.setSvrdt(new Date().getTime());
		Connection conn = null;
		Connection erp_conn =null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erp_conn = ConnectionManager.getConnection(FrameConfig.ERPDSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataEntity(Wo_Biz.getSwsById(basic_request.getData_char(), conn,erp_conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
			try {if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSwsScrapById")
	public BasicResponse<SWS_SCRAP_DOC,SWS_SCRAP_DOC> getSwsScrapById(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_SCRAP_DOC,SWS_SCRAP_DOC> response = new BasicResponse<SWS_SCRAP_DOC,SWS_SCRAP_DOC>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataEntity(Wo_Biz.getSwsScrapDoc(basic_request.getData_char(),basic_request.getData_int(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	
	/**
	 * 
	 * @param basic_request : 其中的有效参数  token data_char ,data_int
	 * @return
	 */
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSwsScrapDocBySwsRpGuid")
	public BasicResponse<SWS_SCRAP_DOC,SWS_SCRAP_DOC> getSwsScrapDocBySwsRpGuid(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_SCRAP_DOC,SWS_SCRAP_DOC> response = new BasicResponse<SWS_SCRAP_DOC,SWS_SCRAP_DOC>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataEntity(Wo_Biz.getSwsScrapDocBySwsRpGuid(basic_request.getData_char(),basic_request.getData_int(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getEmpById")
	public BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW> getEmpById(BasicRequest<String,String> basic_request){
		BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW> response = new BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
//			String inCodeResult = Wo_Biz.IsInCodeBind(basic_request.getData_char(), conn);
//			if(inCodeResult.length()!=0){
//				response.setStatus("1");
//				response.setInfo("已经绑定代码："+inCodeResult);
//				return response;
//			}
			
			response.setDataEntity(EMP_BIZ.getEmpById(basic_request.getData_char(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/addSwsRpDoc")
	public BasicResponse<String,String> addSwsRpDoc(BasicRequest<SWS_RP_DOC,SWS_RP_DOC> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			conn.setAutoCommit(false);
			String operator = ui.getUsr_main_guid();
			
			SWS_RP_DOC doc = basic_request.getData_entity();
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver("1.0.0.0");
			
			for(int i=0;i<doc.getBody().size();i++)
			{
				doc.getBody().get(i).setCreated_by(operator);
				doc.getBody().get(i).setUpdated_by(operator);
				doc.getBody().get(i).setClient_guid("gl");
				doc.getBody().get(i).setData_ver("1.0.0.0");
			}
			
			Wo_Biz.addSwsRp(doc, conn);
			
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/upSwsRpDoc")
	public BasicResponse<String,String> upSwsRpDoc(BasicRequest<SWS_RP_DOC,SWS_RP_DOC> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			conn.setAutoCommit(false);
			String operator = ui.getUsr_main_guid();
			
			SWS_RP_DOC doc = basic_request.getData_entity();
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver("1.0.0.0");
			
			for(int i=0;i<doc.getBody().size();i++)
			{
				doc.getBody().get(i).setCreated_by(operator);
				doc.getBody().get(i).setUpdated_by(operator);
				doc.getBody().get(i).setClient_guid("gl");
				doc.getBody().get(i).setData_ver("1.0.0.0");
			}
			
			Wo_Biz.upSwsRp(doc, conn);
			
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/rpSwsRp")
	public BasicResponse<String,String> rpSwsRp(BasicRequest<SWS_RP_DOC,SWS_RP_DOC> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		Connection erp_conn =null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erp_conn = ConnectionManager.getConnection("jdbc/erp");
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			String operator = ui.getUsr_main_guid();
			SWS_RP_DOC doc = basic_request.getData_entity();
			doc.getHead().setUpdated_by(operator);
			
//			String erpDocId = "";
//			String erpDocType = "";
//			ERP_RDA_DOC rdaDoc = Wo_Biz.getErpRdaDocBySwsRpDoc(doc, conn);
//			if(rdaDoc.getWh_id().length()==0){
//				erpDocId =Erp_Biz.addSgmrda(rdaDoc, ui.getUsr_sys_id(), "93", erp_conn);
//				erpDocType = "ZYD";
//			}
//			else{
//				erpDocId =Erp_Biz.addSgmrda(rdaDoc, ui.getUsr_sys_id(), "94", erp_conn);
//				erpDocType = "GYRK";
//			}
//			
//			Wo_Biz.doSwsRp(doc,erpDocId,erpDocType, conn,erp_conn);
			
			//Updated by xiz for:改用服务插入工艺转移单和入库单
			Wo_Biz.doSwsRp(doc,"","", conn,erp_conn);
			
			erp_conn.commit();
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(erp_conn!=null)
				try {
					erp_conn.rollback();
				} catch (SQLException e) {
			}
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();} catch (SQLException e) {};
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/addScrap")
	public BasicResponse<String,String> addScrap(BasicRequest<SWS_SCRAP_VIEW,SWS_SCRAP_VIEW> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			
			String operator = ui.getUsr_main_guid();
			SWS_SCRAP_VIEW scrap = basic_request.getData_entity();

			Wo_Biz.addScrap(scrap, operator, conn);
			
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSwsRpListBySws")
	public BasicResponse<SWS_RP_VIEW,SWS_RP_VIEW> getSwsRpListBySwsGuid(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_RP_VIEW,SWS_RP_VIEW> response = new BasicResponse<SWS_RP_VIEW,SWS_RP_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataList(Wo_Biz.getSwsRpListBySwsGuid(basic_request.getData_char(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetSwsRpListBySwsExp")
	public void getSwsRpListBySwsExp(@FormParam("param0") String token, @FormParam("param1") String swsGuid, @Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<SWS_RP_VIEW> rpList =  Wo_Biz.getSwsRpListBySwsGuid(swsGuid, conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("报工明细"); 
				            
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("完工数量");
			row.createCell(1).setCellValue("开工时间");
			row.createCell(2).setCellValue("报工时间");
			row.createCell(3).setCellValue("工序代码");
			row.createCell(4).setCellValue("工序名称");
			row.createCell(5).setCellValue("报工状态");
			row.createCell(6).setCellValue("设备");
			row.createCell(7).setCellValue("员工");
			row.createCell(8).setCellValue("ERP单证类型");
			row.createCell(9).setCellValue("ERP单证ID");

			for(int i=0;i<rpList.size();i++)
			{
				SWS_RP_VIEW data = (SWS_RP_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue( data.getFinish_qty().doubleValue());
				row.createCell(1).setCellValue( dateFormat.format( new Date(data.getBg_dt()) ) );
				if(data.getRp_dt()>10000){
					row.createCell(2).setCellValue( dateFormat.format( new Date(data.getRp_dt()) ) );
				}
				row.createCell(3).setCellValue( data.getRp_rac_id());
				row.createCell(4).setCellValue( data.getRp_rac_name());
				
				if(data.getRp_status()==1){
					row.createCell(5).setCellValue( "未报工" );
		    	}
		    	else if(data.getRp_status()==2){
		    		row.createCell(5).setCellValue( "已报工" );
		    	}
		    	else{
		    		row.createCell(5).setCellValue( "未知" );
		    	}
				
				row.createCell(6).setCellValue(data.getRp_ws());
				row.createCell(7).setCellValue(data.getEmp_ids());

				if(data.getErp_doc_type()=="ZYD"){
					row.createCell(8).setCellValue( "转移单" );
		    	}
		    	else if(data.getErp_doc_type()=="GYRK"){
		    		row.createCell(8).setCellValue( "工艺入库单" );
		    	}
		    	else{
		    		row.createCell(8).setCellValue( "未知" );
		    	}
				row.createCell(9).setCellValue(data.getErp_doc_id());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("报工明细")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delSwsRp")
	public BasicResponse<String,String> delSwsRp(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		Connection erpConn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erpConn = ConnectionManager.getConnection("jdbc/erp");
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			erpConn.setAutoCommit(false);
			
			Wo_Biz.delSwsRp(basic_request.getData_char(), conn,erpConn);
			
			erpConn.commit();
			conn.commit();
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(erpConn!=null)
				try {
					erpConn.rollback();
				} catch (SQLException e) {
			}
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
			try {if(erpConn!=null&&!erpConn.isClosed())erpConn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delSwsRpPDA")
	public BasicResponse<String,String> delSwsRpPDA(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			Wo_Biz.delSwsRp_pda(basic_request.getData_char(), conn);
			
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delSws")
	public BasicResponse<String,String> delSws(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			Wo_Biz.delSws(basic_request.getData_char(), conn);
			
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/runTask")
	public BasicResponse<String,String> runTask(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			Common_Biz.RunTask(basic_request.getData_char(),0, conn);
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getRacWsList")
	public BasicResponse<List<String>,String> getRacWsList(BasicRequest<String,String> basic_request){
		BasicResponse<List<String>,String> response = new BasicResponse<List<String>,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataEntity(Wo_Biz.getRacWsList(basic_request.getData_char(),basic_request.getData_char2(), conn));
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getExistWoPrintList")
	public BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW> getExistWoPrintList(BasicRequest<String,String> basic_request){
		BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW> response = new BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW>();
		Connection conn = null;
		Connection erpConn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erpConn = ConnectionManager.getConnection("jdbc/erp");
			List<WO_DOC_VIEW> resultList = new ArrayList<WO_DOC_VIEW>();
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			erpConn.setAutoCommit(false);
			
			String[] docIdAndSplitQty  = basic_request.getData_char().split("A");
			for(int i=0;i<docIdAndSplitQty.length;i++){
				String docId = docIdAndSplitQty[i].split("B")[0];
				BigDecimal splitQty = new BigDecimal(docIdAndSplitQty[i].split("B")[1]);
				String lotId = docIdAndSplitQty[i].split("B")[2];
				
				WO_DOC_VIEW erpWo = Erp_Biz.getSgmRaa(docId, erpConn);
				resultList.add(Wo_Biz.getWoPrintList(erpWo, splitQty,lotId, conn));
			}

			response.setDataList(resultList);
			response.setInfo("");
			response.setStatus("0");
			
			erpConn.commit();
			conn.commit();
		}catch (Exception ex){
			if(erpConn!=null)
				try {
					erpConn.rollback();
				} catch (SQLException e) {
			}
			if(conn!=null)
				try {
					conn.rollback();
				} catch (SQLException e) {
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(erpConn!=null&&!erpConn.isClosed())erpConn.close();} catch (SQLException e) {};
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/runSgmraaSyn")
	public BasicResponse<String,String> runSgmraaSyn(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		Connection erpConn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erpConn = ConnectionManager.getConnection("jdbc/erp");
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			WO_DOC_VIEW erpWoDoc = Erp_Biz.getSgmRaa(basic_request.getData_char(), erpConn);
			List<String> racList = Wo_Biz.getWoRacList(basic_request.getData_char(),conn);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String today = dateFormat.format( new Date() );

			for(int i=0;i<racList.size();i++){
				Wo_Biz.delWoRacWs(basic_request.getData_char(),racList.get(i),conn);
				List<String> erpRacWsValue = Erp_Biz.getSgmQbz(erpWoDoc.getItm_id(), racList.get(i),  erpConn);
				
				for(int j=0;j<erpRacWsValue.size();j++){
					if(Integer.valueOf(today)>=Integer.valueOf(erpRacWsValue.get(j).split(",")[1])){
						Wo_Biz.addWoRacWsValue(basic_request.getData_char(), racList.get(i), erpRacWsValue.get(j).split(",")[0], new BigDecimal(erpRacWsValue.get(j).split(",")[2]), Integer.valueOf(erpRacWsValue.get(j).split(",")[3]),conn);
					}
				}
			}
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
			try {if(erpConn!=null&&!erpConn.isClosed())erpConn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delWo")
	public BasicResponse<String,String> delWo(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			Wo_Biz.DeleteWo(basic_request.getData_char(), conn);
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetSwsRpBySwsId")
	public BasicResponse<SWS_DOC,SWS_DOC> GetSwsRpBySwsId(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_DOC,SWS_DOC> response = new BasicResponse<SWS_DOC,SWS_DOC>();
		Connection conn = null;
		Connection erp_conn =null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erp_conn = ConnectionManager.getConnection(FrameConfig.ERPDSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataEntity(Wo_Biz.GetSwsRpBySwsId(basic_request.getData_char(), conn,erp_conn));
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
			try {if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetSwsRpDocByRpGuid")
	public BasicResponse<SWS_DOC,SWS_DOC> GetSwsRpDocByRpGuid(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_DOC,SWS_DOC> response = new BasicResponse<SWS_DOC,SWS_DOC>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataEntity(Wo_Biz.GetSwsRpDocByRpGuid(basic_request.getData_char(), conn));
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetSwsRpEmpsBySwsGuid")
	public BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW> GetSwsRpEmpsBySwsGuid(BasicRequest<String,String> basic_request){
		BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW> response = new BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataList(Wo_Biz.GetSwsRpEmpsBySwsGuid(basic_request.getData_char(), conn));
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetSwsRpDoc")
	public BasicResponse<SWS_DOC,SWS_DOC> GetSwsRpDoc(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_DOC,SWS_DOC> response = new BasicResponse<SWS_DOC,SWS_DOC>();
		response.setSvrdt(new Date().getTime());
		Connection conn = null;
		Connection erp_conn =null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erp_conn = ConnectionManager.getConnection(FrameConfig.ERPDSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataEntity(Wo_Biz.GetSwsRpDoc(basic_request.getData_char(),basic_request.getData_char2(),basic_request.getData_char3(), conn,erp_conn));
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
			try {if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetSwsRpBySwsIdAndEmpId")
	public BasicResponse<SWS_RP,SWS_RP> GetSwsRpBySwsIdAndEmpId(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_RP,SWS_RP> response = new BasicResponse<SWS_RP,SWS_RP>();
		response.setSvrdt(new Date().getTime());
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataList(Wo_Biz.GetSwsRpBySwsIdAndEmpId(basic_request.getData_char(),basic_request.getData_char2(), conn));
						
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetScraps")
	public BasicResponse<SWS_SCRAP_VIEW,SWS_SCRAP_VIEW> GetScraps(BasicRequest<String,String> basic_request){
		BasicResponse<SWS_SCRAP_VIEW,SWS_SCRAP_VIEW> response = new BasicResponse<SWS_SCRAP_VIEW,SWS_SCRAP_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataDM(Wo_Biz.GetScraps(basic_request.getData_long(),basic_request.getData_long2(),
					basic_request.getData_char(),basic_request.getData_char2(),basic_request.getData_char3(),
					basic_request.getPage_no(),basic_request.getPage_size(),conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delScrap")
	public BasicResponse<String,String> delScrap(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			Wo_Biz.delScrap(basic_request.getData_char(), conn);
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/addCerts")
	public BasicResponse<String,String> addCerts(BasicRequest<CERT_DOC_VIEW,CERT_DOC_VIEW> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			String operator = ui.getUsr_main_guid();
			String client="gl";
			String data_ver="1.0.0.0";
			
			conn.setAutoCommit(false);
			
			Cert_Biz.addCerts(basic_request.getData_entity(), conn, operator, client, data_ver);
			
			conn.commit();
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getCerts")
	public BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW> getCerts(BasicRequest<CERT_DOC_VIEW,CERT_DOC_VIEW> basic_request){
		BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW> response = new BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			EntityListDM<CERT_DOC_VIEW,CERT_DOC_VIEW> returnDM= Cert_Biz.getCerts(basic_request.getData_entity(),basic_request.getPage_no(),basic_request.getPage_size(), conn);
			
			response.setDataDM(returnDM);
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getCertPrintList")
	public BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW> getCertPrintList(BasicRequest<String,String> basic_request){
		BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW> response = new BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			ArrayList<String> guidList = new ArrayList<String>();
			String[] guids = basic_request.getData_char().split(",");
			for(int i=0;i<guids.length;i++)
			{
				guidList.add(guids[i]);
			}
			List<CERT_DOC_VIEW> returnLst= Cert_Biz.getCertPrintList(guidList, conn);
			
			response.setDataList(returnLst);
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delCertByGuid")
	public BasicResponse<String,String> delCertByGuid(BasicRequest<String,String> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			
			Cert_Biz.delCertByGuid(basic_request.getData_char(), conn);
			
			conn.commit();
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getCertById")
	public BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW> getCertById(BasicRequest<String,String> basic_request){
		BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW> response = new BasicResponse<CERT_DOC_VIEW,CERT_DOC_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			

			CERT_DOC_VIEW returnView= Cert_Biz.getCertById(basic_request.getData_char(), conn);
			
			response.setDataEntity(returnView);
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/doCertSwsBind")
	public BasicResponse<String,String> doCertSwsBind(BasicRequest<CERT_SWS_DOC,CERT_SWS_DOC> basic_request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			conn.setAutoCommit(false);
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			String client="gl";
			
			Cert_Biz.doCertSwsBind(basic_request.getData_entity(), conn,operator,client,data_ver);
			
			conn.commit();
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSwsForCertBind")
	public BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> getSwsForCertBind(BasicRequest<String,String> basic_request){
		BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> response = new BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
		
			SUB_WO_SUB_VIEW returnView= Cert_Biz.getSwsForCertBind(basic_request.getData_char(),basic_request.getData_char2(), conn);
			
			response.setDataEntity(returnView);
			
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
}
