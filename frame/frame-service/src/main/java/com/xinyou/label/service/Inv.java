package com.xinyou.label.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.request.BasicRequest;
import com.xinyou.frame.response.BasicResponse;
import com.xinyou.frame.service.BaseService;
import com.xinyou.label.domain.biz.Cert_Biz;
import com.xinyou.label.domain.biz.Common_Biz;
import com.xinyou.label.domain.biz.Erp_Biz;
import com.xinyou.label.domain.biz.InvBasic_Biz;
import com.xinyou.label.domain.biz.Inv_Biz;
import com.xinyou.label.domain.entities.CTN_MAIN;
import com.xinyou.label.domain.entities.RBA_CTN_RE;
import com.xinyou.label.domain.entities.STK_ITM_WKSITE;
import com.xinyou.label.domain.entities.STK_MAIN;
import com.xinyou.label.domain.entities.TRAN_BACO;
import com.xinyou.label.domain.entities.TRAN_BASE_DOC;
import com.xinyou.label.domain.entities.TRAN_ITM;
import com.xinyou.label.domain.entities.TRAN_ITM_BASE;
import com.xinyou.label.domain.models.PKG_DOC;
import com.xinyou.label.domain.models.TRAN_DOC;
import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;
import com.xinyou.label.domain.viewes.DSCHDA_VIEW;
import com.xinyou.label.domain.viewes.DSCHDB_VIEW;
import com.xinyou.label.domain.viewes.ITM_MAIN_VIEW;
import com.xinyou.label.domain.viewes.JSKJFA_VIEW;
import com.xinyou.label.domain.viewes.PUR_ITM_VIEW;
import com.xinyou.label.domain.viewes.PUR_PKG_VIEW;
import com.xinyou.label.domain.viewes.RBA_DOC_VIEW;
import com.xinyou.label.domain.viewes.RBA_ITM_VIEW;
import com.xinyou.label.domain.viewes.WO_DOC_VIEW;
import com.xinyou.util.ConnectionManager;

@Path("/inv")
@Produces({MediaType.APPLICATION_JSON})
public class Inv extends BaseService {		
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getCtn")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getCtnByBaco(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			response.setDataEntity(Common_Biz.getCtnByBaco(basic_request.getData_char(), conn));
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
	@Path(value = "/getPurById")
	public BasicResponse<PUR_ITM_VIEW,PUR_ITM_VIEW> getPurById(BasicRequest<String,String> basic_request){
		BasicResponse<PUR_ITM_VIEW,PUR_ITM_VIEW> response = new BasicResponse<PUR_ITM_VIEW,PUR_ITM_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(basic_request.getData_char()==null||basic_request.getData_char().isEmpty()){
				throw new Exception("加载信息失败：缺少采购单号");
			}
			
			response.setDataList(Inv_Biz.getPurById(basic_request.getData_char(), conn));
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
	@Path(value = "/getPurList")
	public BasicResponse<DSCHDA_VIEW,DSCHDA_VIEW> getPurList(BasicRequest<String,String> basic_request){
		BasicResponse<DSCHDA_VIEW,DSCHDA_VIEW> response = new BasicResponse<DSCHDA_VIEW,DSCHDA_VIEW>();
		Connection conn = null;
		try{
			Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataList(Erp_Biz.getDcsHdaList(basic_request.getData_char(), erp_conn));
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
	@Path(value = "/getPurPrintList")
	public BasicResponse<DSCHDB_VIEW,DSCHDB_VIEW> getPurPrintList(BasicRequest<String,String> basic_request){
		BasicResponse<DSCHDB_VIEW,DSCHDB_VIEW> response = new BasicResponse<DSCHDB_VIEW,DSCHDB_VIEW>();
		Connection conn = null;
		try{
			Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			System.out.println("getPurPrintList:"+basic_request.getData_char());
			response.setDataList(Erp_Biz.getDcsHdaPrintList(basic_request.getData_char(), erp_conn));
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
	@Path(value = "/addpurtran")
	public BasicResponse<String,String> addPurTran(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setBase_doc_type(10);
			doc.getHead().setIs_syned(0);
			doc.getHead().setNeed_syn(0);
			doc.getHead().setTran_type(100);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.purInCtnBox(baco.getCtn_baco(), baco.getTran_qty(), operator, "gl", data_ver, conn);
			}
			for (TRAN_ITM_BASE itm_base : doc.getBody_itm_base()) {
				Inv_Biz.purInPurDoc(doc.getHead().getBase_doc_id(), itm_base.getItm_id(), itm_base.getBase_seqno(), itm_base.getItm_qty(), conn);
			}
			
			doc.getHead().setSyn_doc_type(10);
			doc.getHead().setSyn_doc_id(Erp_Biz.addJskjca(doc, ui.getUsr_sys_id(), erp_conn));
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
		}catch (Exception ex){
			if(erp_conn!=null)try {erp_conn.rollback();} catch (SQLException e) {;}
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
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
	@Path(value = "/addSemiPurTran")
	public BasicResponse<String,String> addSemiPurTran(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setBase_doc_type(11);//11：采购单流程票
			doc.getHead().setIs_syned(0);
			doc.getHead().setNeed_syn(0);
			doc.getHead().setTran_type(101);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				CTN_MAIN_VIEW parent_ctn = Common_Biz.getCtnByBaco(baco.getParent_baco(), conn);
				if(parent_ctn!=null&&parent_ctn.getCtn_main_guid().length()>0)
				{
					if(parent_ctn.getCtn_type()!=6)
					{
						throw new Exception(parent_ctn.getCtn_baco()+" 不是库位条码！");
					}
				}
				else
				{
					throw new Exception("未找到库位信息！");
				}
				
				CTN_MAIN_VIEW ctnView = Common_Biz.getCtnByBaco(baco.getCtn_baco(), conn);
				CTN_MAIN ctn = new CTN_MAIN();	
				
				ctn.setCtn_main_guid(ctnView.getCtn_main_guid());
				ctn.setCtn_baco(ctnView.getCtn_baco());
				ctn.setCtn_main_id(ctnView.getCtn_baco());
				ctn.setCtn_status(1);
				ctn.setCtn_type(12);
				ctn.setItm_id(ctnView.getItm_id());
				ctn.setItm_qty(baco.getTran_qty());
				ctn.setParent_ctn_guid(parent_ctn.getCtn_main_guid());
				ctn.setWh_area_guid(parent_ctn.getWh_area_guid());
				ctn.setWh_guid(parent_ctn.getWh_guid());
				ctn.setWh_loc_guid(parent_ctn.getCtn_main_guid());
				ctn.setWh_shelf_guid(parent_ctn.getWh_shelf_guid());
				ctn.setLot_id(baco.getLot_id());
				
//				Common_Biz.updCtnPos(ctn, operator, conn);
				Inv_Biz.updateCtnForSemiPurTran(ctn, operator, conn);				
				
			}
			Erp_Biz.addSemiJskjca(doc, ui.getUsr_sys_id(), erp_conn);
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
		}catch (Exception ex){
			if(erp_conn!=null)try {erp_conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
			
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
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
	@Path(value = "/getRbaById")
	public BasicResponse<RBA_ITM_VIEW,RBA_ITM_VIEW> getRbaById(BasicRequest<String,String> basic_request){
		BasicResponse<RBA_ITM_VIEW,RBA_ITM_VIEW> response = new BasicResponse<RBA_ITM_VIEW,RBA_ITM_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(basic_request.getData_char()==null||basic_request.getData_char().isEmpty()){
				throw new Exception("加载信息失败：缺少领料单号");
			}
			
			response.setDataList(Inv_Biz.getRbaById(basic_request.getData_char(), conn));
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
	@Path(value = "/getRbaDocList")
	public BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW> getRbaDocList(BasicRequest<String,String> basic_request){
		BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW> response = new BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(basic_request.getData_char()==null||basic_request.getData_char().isEmpty()){
				throw new Exception("加载信息失败：缺少领料单号");
			}
			
			response.setDataList(Inv_Biz.getRbaDocList(basic_request.getData_char(), conn));
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
	@Path(value = "/getRbaDocById")
	public BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW> getRbaDocById(BasicRequest<String,String> basic_request){
		BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW> response = new BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(basic_request.getData_char()==null||basic_request.getData_char().isEmpty()){
				throw new Exception("加载信息失败：缺少领料单号");
			}
			
			response.setDataEntity(Inv_Biz.getRbaDocById(basic_request.getData_char(), conn));
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
	@Path(value = "/getRbaDocByIds")
	public BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW> getRbaDocByIds(BasicRequest<String,String> basic_request){
		BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW> response = new BasicResponse<RBA_DOC_VIEW,RBA_DOC_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(basic_request.getData_char()==null||basic_request.getData_char().isEmpty()){
				throw new Exception("加载信息失败：缺少领料单号");
			}
			
			response.setDataList(Inv_Biz.getRbaDocByIds(basic_request.getData_char(), conn));
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
	@Path(value = "/AddBasicRbaTran")
	public BasicResponse<String,String> addRbaTran(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		
		try{
			conn.setAutoCommit(false);
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setTran_type(110);
			doc.getHead().setBase_doc_type(20);
			doc.getHead().setIs_syned(0);
			doc.getHead().setNeed_syn(0);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.rbaOutCtnBox(baco.getCtn_baco(), baco.getTran_qty(), operator, "gl", data_ver, conn);
			}
			for( TRAN_BASE_DOC baseDoc : doc.getDoc_base() ){
				Inv_Biz.rbaOutDoc(baseDoc.getBase_doc_id(), conn);
			}
			
			//合并领料，必须完全匹配，所以不需要修改ERP单证信息
			//Erp_Biz.upSgmrba(doc, erp_conn);
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/AddBasicOtherIn")
	public BasicResponse<String,String> addBasicOtherIn(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setIs_syned(0);
			doc.getHead().setTran_type(120);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.otherInCtnBox(baco.getCtn_baco(), baco.getTran_qty(), operator, "gl", data_ver, conn);
			}
			
			if(doc.getHead().getNeed_syn()==1)
			{
				for (TRAN_BACO baco : doc.getBody_baco()) {
					CTN_MAIN_VIEW bacoCtn = Common_Biz.getCtnByBaco(baco.getCtn_baco(), conn);
					if(bacoCtn!=null&&StringUtils.isNotEmpty(bacoCtn.getCtn_main_guid()))
					{
						baco.setWh_id(bacoCtn.getWh_id());
					}
					else
					{
						throw new Exception("未找到库位所属仓库信息！");
					}
				}
				String erpDocId = Erp_Biz.addJsklha(doc, ui.getUsr_sys_id(), erp_conn);
				doc.getHead().setSyn_doc_type(20);
				doc.getHead().setSyn_doc_id(erpDocId);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
		}catch (Exception ex){
			if(erp_conn!=null){
				try {
					erp_conn.rollback();
				} catch (SQLException e) {;}
			}
			if(conn!=null){
				try {
					conn.rollback();
					}
				catch (SQLException e) {;}
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
	@Path(value = "/AddBasicOtherOut")
	public BasicResponse<String,String> addBasicOtherOut(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setIs_syned(0);
			doc.getHead().setTran_type(130);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.otherOutCtnBox(baco.getCtn_baco(), baco.getTran_qty(), operator, "gl", data_ver, conn);
			}
			
			if(doc.getHead().getNeed_syn()==1)
			{
				for (TRAN_BACO baco : doc.getBody_baco()) {
					CTN_MAIN_VIEW bacoCtn = Common_Biz.getCtnByBaco(baco.getCtn_baco(), conn);
					if(bacoCtn!=null&&StringUtils.isNotEmpty(bacoCtn.getCtn_main_guid()))
					{
						baco.setWh_id(bacoCtn.getWh_id());
					}
					else
					{
						throw new Exception("未找到库位所属仓库信息！");
					}
				}
				
				String erpDocId = Erp_Biz.addJsklia(doc, ui.getUsr_sys_id(), erp_conn);
				doc.getHead().setSyn_doc_type(30);
				doc.getHead().setSyn_doc_id(erpDocId);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
		}catch (Exception ex){
			if(erp_conn!=null){
				try {
					erp_conn.rollback();
					} catch (SQLException e) {}
			}
			if(conn!=null){
				try {
					conn.rollback();
					} catch (SQLException e) {;}
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
	@Path(value = "/addmoveboxtran")
	public BasicResponse<String,String> addMoveBoxTran(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			conn.setAutoCommit(false);
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			//解析到的请求参数 
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			//doc.getHead() return a Tran_Main Object
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setBase_doc_type(140);  // 移位 
			doc.getHead().setIs_syned(0);
			doc.getHead().setNeed_syn(0);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.moveCtnBox(baco.getCtn_baco(),baco.getParent_baco(), baco.getTran_qty(), operator, "gl", data_ver, conn);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/getBoxListByItmId")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getBoxListByItmId(BasicRequest<String,String> request) throws Exception{
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			conn.setAutoCommit(false);
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			response.setDataList(Inv_Biz.getBoxListByItmId(request.getData_char(),request.getData_char2(), conn));
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
//	@POST
//	@Consumes({MediaType.APPLICATION_JSON}) 
//	@Path(value = "/getCtn")
//	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getCtnByBaco(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
//		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
//		Connection conn = null;
//		try{
//			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
//			getUserInfo(basic_request.getToken(), conn);
//			if(ui==null||ui.getStatus()!=0){
//				response.setStatus("2");
//				
//				if(ui.getStatus()==1)
//					response.setInfo("您还未登录系统");
//				else if(ui.getStatus()==2)
//					response.setInfo("登录已超时");
//				
//				return response;
//			}
//			response.setDataEntity(Common_Biz.getCtnByBaco(basic_request.getData_char(), conn));
//			response.setInfo("");
//			response.setStatus("0");
//		}catch (Exception ex){
//			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
//			response.setStatus("1");
//		}finally{
//			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
//		}
//		return response;
//	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/addSemiIn")
	public BasicResponse<String,String> addSemiIn(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setIs_syned(0);
			doc.getHead().setNeed_syn(0);
			doc.getHead().setTran_type(150);//150:半成品生产调拨入库
			doc.getHead().setSyn_doc_type(40);//40；半成品生产入库调拨
			
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			//1、插入ERP调拨单，并获取单证ID
			String jsklcaId = Erp_Biz.addJsklca(doc, ui.getUsr_sys_id(), erp_conn);
			doc.getHead().setSyn_doc_id(jsklcaId);

			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				//更新容器位置
				Inv_Biz.getInSemi(baco.getCtn_baco(), baco.getParent_baco(), baco.getTran_qty(), conn);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
			
		}catch (Exception ex){
			
			if(conn!=null)try {
				erp_conn.rollback();
				conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close(); 
			if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/AddSemiRbaTran")
	public BasicResponse<String,String> addSemiRbaTran(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection(FrameConfig.ERPDSNAME);
		
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setTran_type(160);
			doc.getHead().setBase_doc_type(20);
			doc.getHead().setIs_syned(0);
			doc.getHead().setNeed_syn(0);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.rbaOutCtnBox(baco.getCtn_baco(), baco.getTran_qty(), operator, "gl", data_ver, conn);
			}
			for( TRAN_BASE_DOC baseDoc : doc.getDoc_base() ){
				
				baseDoc.setBase_doc_type(20);
				//更新领料单领料状态
				Inv_Biz.rbaOutDoc(baseDoc.getBase_doc_id(), conn);
				//更新ERP扫描状态
				Erp_Biz.upSgmrbaByid(baseDoc.getBase_doc_id(), erp_conn);			
				
			}
			
			////Updated by juanzi on 2014/9/29 for 增加领料记录
			for(RBA_CTN_RE rabCtn:doc.getRba_ctn_re()){
				
				rabCtn.setCreated_by(operator);
				rabCtn.setUpdated_by(operator);
				rabCtn.setClient_guid("gl");
				rabCtn.setData_ver(data_ver);
				
				InvBasic_Biz.AddRbaCtnRe(rabCtn, conn);
			}
			
			String tranGuid=InvBasic_Biz.AddTran(doc, conn);
						
			response.setDataEntity(tranGuid);
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
			
		}catch (Exception ex){
			if(conn!=null){
				try {
					erp_conn.rollback();
					conn.rollback();
					} catch (SQLException e) {;}
			}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {
				if(conn!=null&&!conn.isClosed())conn.close();
				if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();
				} catch (SQLException e) {};
			
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/AddSemiBoxOtherIn")
	public BasicResponse<String,String> addSemiBoxOtherIn(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setIs_syned(0);
			doc.getHead().setTran_type(170);
			doc.getHead().setSyn_doc_type(51);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.semiBoxOtherIn(baco.getCtn_baco(), baco.getTran_qty(),baco.getParent_baco(), operator, conn);
			}
			
			if(doc.getHead().getNeed_syn()==1)
			{
				for (TRAN_BACO baco : doc.getBody_baco()) {
					CTN_MAIN_VIEW bacoCtn = Common_Biz.getCtnByBaco(baco.getCtn_baco(), conn);
					if(bacoCtn!=null&&StringUtils.isNotEmpty(bacoCtn.getCtn_main_guid()))
					{
						baco.setWh_id(bacoCtn.getWh_id());
					}
					else
					{
						throw new Exception("未找到库位所属仓库信息！");
					}
				}
				String erpDocId = Erp_Biz.addJsklha(doc, ui.getUsr_sys_id(), erp_conn);
				doc.getHead().setSyn_doc_type(51);
				doc.getHead().setSyn_doc_id(erpDocId);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
		}catch (Exception ex){
			if(erp_conn!=null){
				try {
					erp_conn.rollback();
				} catch (SQLException e) {;}
			}
			if(conn!=null){
				try {
					conn.rollback();
					}
				catch (SQLException e) {;}
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
	@Path(value = "/AddSemiScatteredOtherIn")
	public BasicResponse<String,String> addSemiScatteredOtherIn(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setIs_syned(0);
			doc.getHead().setTran_type(180);
			doc.getHead().setSyn_doc_type(50);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.semiScatteredOtherIn(baco.getCtn_baco(), baco.getTran_qty(), operator, conn);
			}
			
			if(doc.getHead().getNeed_syn()==1)
			{
				for (TRAN_BACO baco : doc.getBody_baco()) {
					CTN_MAIN_VIEW bacoCtn = Common_Biz.getCtnByBaco(baco.getCtn_baco(), conn);
					if(bacoCtn!=null&&StringUtils.isNotEmpty(bacoCtn.getCtn_main_guid()))
					{
						baco.setWh_id(bacoCtn.getWh_id());
					}
					else
					{
						throw new Exception("未找到库位所属仓库信息！");
					}
				}
				String erpDocId = Erp_Biz.addJsklha(doc, ui.getUsr_sys_id(), erp_conn);
				doc.getHead().setSyn_doc_type(50);
				doc.getHead().setSyn_doc_id(erpDocId);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
		}catch (Exception ex){
			if(erp_conn!=null){
				try {
					erp_conn.rollback();
				} catch (SQLException e) {;}
			}
			if(conn!=null){
				try {
					conn.rollback();
					}
				catch (SQLException e) {;}
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
	@Path(value = "/AddSemiOtherOut")
	public BasicResponse<String,String> addSemiOtherOut(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setIs_syned(0);
			doc.getHead().setTran_type(190);
			doc.getHead().setSyn_doc_type(60);
			
			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				Inv_Biz.semiOtherOut(baco.getCtn_baco(), baco.getTran_qty(), operator, conn);
			}
			
			if(doc.getHead().getNeed_syn()==1)
			{
				for (TRAN_BACO baco : doc.getBody_baco()) {
					baco.setWh_id(doc.getHead().getWh_id());
				}
				String erpDocId = Erp_Biz.addJsklia(doc, ui.getUsr_sys_id(), erp_conn);
				doc.getHead().setSyn_doc_type(60);
				doc.getHead().setSyn_doc_id(erpDocId);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
		}catch (Exception ex){
			if(erp_conn!=null){
				try {
					erp_conn.rollback();
				} catch (SQLException e) {;}
			}
			if(conn!=null){
				try {
					conn.rollback();
					}
				catch (SQLException e) {;}
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
	@Path(value = "/AddStkPlan")
	public BasicResponse<String,String> addStkPlan(BasicRequest<STK_MAIN,STK_MAIN> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		
		try{
			conn.setAutoCommit(false);
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			STK_MAIN  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.setCreated_by(operator);
			doc.setUpdated_by(operator);
			doc.setClient_guid("gl");
			doc.setData_ver(data_ver);
			
			response.setDataEntity(Inv_Biz.addTakeStockPlan(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/UpStkPlan")
	public BasicResponse<String,String> upStkPlan(BasicRequest<STK_MAIN,STK_MAIN> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		
		try{
			conn.setAutoCommit(false);
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			STK_MAIN  doc= request.getData_entity();
			
			doc.setUpdated_by(ui.getUsr_main_guid());
			Inv_Biz.upTakeStockPlan(doc, conn);
			
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/DelStk")
	public BasicResponse<String,String> delStk(BasicRequest<String,String> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		
		try{
			conn.setAutoCommit(false);
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			Inv_Biz.delTakeStockPlan(request.getData_char(), conn);
			
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/GetStkPlanList")
	public BasicResponse<STK_MAIN,STK_MAIN> getStkPlanList(BasicRequest<String,String> request) throws Exception{
		BasicResponse<STK_MAIN,STK_MAIN> response = new BasicResponse<STK_MAIN,STK_MAIN>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			response.setDataDM(Inv_Biz.getTakeStockPlanList(request.getData_char(),request.getPage_no(),request.getPage_size(), conn));
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
	@Path(value = "/GetStkPlanListByInv")
	public BasicResponse<STK_MAIN,STK_MAIN> GetStkPlanListByInv(BasicRequest<String,String> request) throws Exception{
		BasicResponse<STK_MAIN,STK_MAIN> response = new BasicResponse<STK_MAIN,STK_MAIN>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			response.setDataList(Inv_Biz.getStockPlanListByInvId(request.getData_char(), conn));
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
	@Path(value = "/getStockPlanListByFields")
	public BasicResponse<STK_MAIN,STK_MAIN> getStockPlanListByFields(BasicRequest<STK_MAIN,String> request) throws Exception{
		BasicResponse<STK_MAIN,STK_MAIN> response = new BasicResponse<STK_MAIN,STK_MAIN>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			response.setDataList(Inv_Biz.getStockPlanListByFields(request.getData_entity(), conn));
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
	@Path(value = "/DoCfmMoreStockItem")
	public BasicResponse<STK_MAIN,STK_MAIN> doCfmMoreStockItem(BasicRequest<String,String> request) throws Exception{
		BasicResponse<STK_MAIN,STK_MAIN> response = new BasicResponse<STK_MAIN,STK_MAIN>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			conn.setAutoCommit(false);
			Inv_Biz.doCfmMoreStockItem(request.getData_char(),ui.getUsr_main_guid(),"1.0.0.0","gl", conn);
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			conn.rollback();
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
			System.out.println("DoCfmMoreStockItem:"+ex.getMessage());
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/DoCfmLessStockItem")
	public BasicResponse<STK_MAIN,STK_MAIN> doCfmLessStockItem(BasicRequest<String,String> request) throws Exception{
		BasicResponse<STK_MAIN,STK_MAIN> response = new BasicResponse<STK_MAIN,STK_MAIN>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			conn.setAutoCommit(false);
			Inv_Biz.doCfmLessStockItem(request.getData_char(),ui.getUsr_main_guid(),"1.0.0.0","gl", conn);
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			conn.rollback();
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/PkgSws")
	public BasicResponse<String,String> pkgSws(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			conn.setAutoCommit(false);
			Inv_Biz.pkgSws(request.getData_char(), request.getData_list(), ui.getUsr_main_guid(), "1.0.0.0", "gl", conn);
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null&&!conn.isClosed()){
				conn.rollback();
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
	@Path(value = "/PkgCert")
	public BasicResponse<String,String> pkgCert(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			conn.setAutoCommit(false);
			Inv_Biz.pkgCert(request.getData_char(), request.getData_list(), ui.getUsr_main_guid(), "1.0.0.0", "gl", conn);
			conn.commit();
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null&&!conn.isClosed()){
				conn.rollback();
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
	@Path(value = "/GetCertPkg")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> GetCertPkg(BasicRequest<String,String> request) throws Exception{
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			PKG_DOC certPkg = Inv_Biz.getCertPkg( request.getData_char(), conn);
			response.setDataEntity(certPkg.getPkg());
			response.setDataList(certPkg.getChildrens());
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
	@Path(value = "/addProductIn")
	public BasicResponse<String,String> addProductIn(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setIs_syned(0);
			doc.getHead().setNeed_syn(0);
			doc.getHead().setTran_type(200);//200:成品生产调拨入库
			doc.getHead().setSyn_doc_type(80);//80；成品生产调拨入库单
			
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			//1、插入ERP调拨单，并获取单证ID
			
			TRAN_DOC erpDoc = new TRAN_DOC();
			
			erpDoc.setHead(doc.getHead());
			erpDoc.setBody_baco(Inv_Biz.getCertBacoListByPkgBacoList(doc.getBody_baco(), conn));
			
			String jsklcaId = Erp_Biz.addJsklca(erpDoc, ui.getUsr_sys_id(), erp_conn);
			doc.getHead().setSyn_doc_id(jsklcaId);

			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				//更新容器位置
				Inv_Biz.getInProduct(baco.getCtn_baco(), baco.getParent_baco(), conn);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
			
		}catch (Exception ex){
			if(conn!=null)try {
				erp_conn.rollback();
				conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {
				if(conn!=null&&!conn.isClosed())conn.close();
				if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();
			} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getJskKea")
	public BasicResponse<ITM_MAIN_VIEW,ITM_MAIN_VIEW> getJskKea(BasicRequest<String,String> request){
		BasicResponse<ITM_MAIN_VIEW,ITM_MAIN_VIEW> response = new BasicResponse<ITM_MAIN_VIEW,ITM_MAIN_VIEW>();
		Connection conn = null;
		Connection erp_conn=null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			erp_conn = ConnectionManager.getConnection("jdbc/erp");

			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataList(Erp_Biz.getJskkea(request.getData_char(), erp_conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
			try {if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();}catch(SQLException e){};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/DoPlt")
	public BasicResponse<String,String> doPlt(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		
		try{
			conn.setAutoCommit(false);
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			Inv_Biz.doPlt(request.getData_entity(),request.getData_list(), operator,data_ver,"gl",conn);
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/doStkPlanItem")
	public BasicResponse<String,String> doStkPlanItem(BasicRequest<String,String> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		try{
			conn.setAutoCommit(false);

			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			Inv_Biz.doTakeStockItem(request.getData_char(),request.getData_char2(),request.getData_char3(),request.getData_char4(),request.getData_char5(), request.getData_decimal(),
					operator,ui.getUsr_sys_id(),data_ver,"gl",conn);
			response.setInfo("");
			response.setStatus("0");
			conn.commit();
		}catch (Exception ex){
			if(conn!=null){
				try {
					conn.rollback();
					}
				catch (SQLException e) {;}
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
	@Path(value = "/getPurPrintItemList")
	public BasicResponse<PUR_PKG_VIEW,PUR_PKG_VIEW> getPurPrintItemList(BasicRequest<String,String> basic_request){
		BasicResponse<PUR_PKG_VIEW,PUR_PKG_VIEW> response = new BasicResponse<PUR_PKG_VIEW,PUR_PKG_VIEW>();
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
			List<PUR_PKG_VIEW> resultList = Inv_Biz.getPurPrintList( basic_request.getData_list(), basic_request.getData_char().replace("，", ","), conn);

			response.setDataList(resultList);
			response.setInfo("");
			response.setStatus("0");
			
			conn.commit();
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
	@Path(value = "/getItmPrintList")
	public BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW> getItmPrintList(BasicRequest<String,String> basic_request){
		BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW> response = new BasicResponse<WO_DOC_VIEW,WO_DOC_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
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
			
			String[] docIdAndSplitQty  = basic_request.getData_char().split(";");
			for(int i=0;i<docIdAndSplitQty.length;i++){
				String itmId = docIdAndSplitQty[i].split(",")[0];
				BigDecimal splitQty = new BigDecimal(docIdAndSplitQty[i].split(",")[1]);
				String lotId = docIdAndSplitQty[i].split(",")[2];
				
				resultList.add(Inv_Biz.getItmPrintList(itmId, splitQty,lotId, conn));
			}

			response.setDataList(resultList);
			response.setInfo("");
			response.setStatus("0");
			
			conn.commit();
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
	@Path(value = "/getRePurList")
	public BasicResponse<JSKJFA_VIEW,JSKJFA_VIEW> getRePurList(BasicRequest<String,String> basic_request){
		BasicResponse<JSKJFA_VIEW,JSKJFA_VIEW> response = new BasicResponse<JSKJFA_VIEW,JSKJFA_VIEW>();
		Connection conn = null;
		Connection erp_conn =null;
		try{
		    erp_conn = ConnectionManager.getConnection("jdbc/erp");
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataList(Erp_Biz.getJskJfaList(basic_request.getData_char(), erp_conn));
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
	@Path(value = "/getRePurPrintList")
	public BasicResponse<PUR_PKG_VIEW,PUR_PKG_VIEW> getRePurPrintList(BasicRequest<String,String> basic_request){
		BasicResponse<PUR_PKG_VIEW,PUR_PKG_VIEW> response = new BasicResponse<PUR_PKG_VIEW,PUR_PKG_VIEW>();
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
			List<PUR_PKG_VIEW> resultList = Inv_Biz.getRePurPrintList( basic_request.getData_list(), conn);

			response.setDataList(resultList);
			response.setInfo("");
			response.setStatus("0");
			
			conn.commit();
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
	@Path(value = "/getItmInQty")
	public BasicResponse<ITM_MAIN_VIEW,ITM_MAIN_VIEW> getItmInQty(BasicRequest<String,String> basic_request){
		BasicResponse<ITM_MAIN_VIEW,ITM_MAIN_VIEW> response = new BasicResponse<ITM_MAIN_VIEW,ITM_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			response.setDataList(Inv_Biz.getItmInQty(basic_request.getData_list(), basic_request.getData_char(), conn));
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
	@Path(value = "/getWorkSiteItemBySwsId")
	public BasicResponse<STK_ITM_WKSITE,String> getWorkSiteItemBySwsId(BasicRequest<String,String> basic_request){
		BasicResponse<STK_ITM_WKSITE,String> response = new BasicResponse<STK_ITM_WKSITE,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			response.setDataEntity(Inv_Biz.getWorkSiteItemBySwsId(basic_request.getData_char(), conn));
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
	@Path(value = "/addStkItmWKSite")
	public BasicResponse<String,String> addStkItmWKSite(BasicRequest<STK_ITM_WKSITE,STK_ITM_WKSITE> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);

		try{		
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			String clientGuid="gl";
			String empId=ui.getEmp_id();
			
			String isUpdate=request.getData_char();
			
			STK_ITM_WKSITE entity=request.getData_entity();
			entity.setCreated_by(operator);
			entity.setUpdated_by(operator);
			entity.setClient_guid(clientGuid);
			entity.setData_ver(data_ver);
			entity.setStk_emp_id(empId);
			
						
			response.setDataEntity(Inv_Biz.addStkItmWKSite(entity,isUpdate, conn));
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
	@Path(value = "/moveSWSWHLoc")
	public BasicResponse<String,String> moveSWSWHLoc(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);

		try{	
			conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			List<CTN_MAIN_VIEW> ctnLst=request.getData_list();
			for(int i=0;i<ctnLst.size();i++){
				Inv_Biz.moveSWSWHLoc(ctnLst.get(i), conn);
			}
				
			response.setInfo("");
			response.setStatus("0");
			
			conn.commit();
			
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {};
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/updateStockWHLoc")
	public BasicResponse<String,String> updateStockWHLoc(BasicRequest<String,String> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);

		try{				
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			String planGuid=request.getData_char();
			Inv_Biz.updateStockWHLoc(planGuid, conn);
				
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
	@Path(value = "/doProductTranIn")
	public BasicResponse<String,String> doProductTranIn(BasicRequest<TRAN_DOC,TRAN_DOC> request) throws Exception{
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
		Connection erp_conn = ConnectionManager.getConnection("jdbc/erp");
		
		try{
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			TRAN_DOC  doc= request.getData_entity();
			
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			doc.getHead().setCreated_by(operator);
			doc.getHead().setUpdated_by(operator);
			doc.getHead().setClient_guid("gl");
			doc.getHead().setData_ver(data_ver);
			doc.getHead().setIs_syned(0);
			doc.getHead().setNeed_syn(0);
			doc.getHead().setTran_type(200);//200:成品生产调拨入库
			doc.getHead().setSyn_doc_type(80);//80；成品生产调拨入库单
			
			conn.setAutoCommit(false);
			erp_conn.setAutoCommit(false);
			
			//1、插入ERP调拨单，并获取单证ID			
			TRAN_DOC erpDoc = new TRAN_DOC();
			
			erpDoc.setHead(doc.getHead());
			erpDoc.setBody_baco(doc.getBody_baco());
			
			String jsklcaId = Erp_Biz.addJsklca(erpDoc, ui.getUsr_sys_id(), erp_conn);
			doc.getHead().setSyn_doc_id(jsklcaId);

			for (TRAN_ITM itm : doc.getBody_itm()) {
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
			}
			for (TRAN_BACO baco : doc.getBody_baco()) {
				baco.setCreated_by(operator);
				baco.setUpdated_by(operator);
				baco.setClient_guid("gl");
				baco.setData_ver(data_ver);
				
				//更新容器位置
				Inv_Biz.updProductLoc(baco.getCtn_baco(), baco.getParent_baco(), conn);
				//更新合格证状态 2:已入库
				Cert_Biz.updCertStatusById(baco.getCtn_baco(),2,conn);
			}
			
			response.setDataEntity(InvBasic_Biz.AddTran(doc, conn));
			response.setInfo("");
			response.setStatus("0");
			
			erp_conn.commit();
			conn.commit();
			
		}catch (Exception ex){
			if(conn!=null)
				try {
				erp_conn.rollback();
				conn.rollback();
				} catch (SQLException e) {;}
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
		}finally{
			try {
				if(conn!=null&&!conn.isClosed())conn.close();
				if(erp_conn!=null&&!erp_conn.isClosed())erp_conn.close();
			} catch (SQLException e) {};
		}
		return response;
	}
	
} 