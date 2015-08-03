package com.xinyou.label.service;

import java.sql.Connection;
import java.sql.SQLException;

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
import com.xinyou.label.domain.biz.Common_Biz;
import com.xinyou.label.domain.biz.InvBasic_Biz;
import com.xinyou.label.domain.entities.CTN_MAIN;
import com.xinyou.label.domain.entities.ITM_MAIN;
import com.xinyou.label.domain.entities.TRAN_BACO;
import com.xinyou.label.domain.entities.TRAN_ITM;
import com.xinyou.label.domain.models.TRAN_DOC;
import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;
import com.xinyou.label.domain.viewes.ITM_MAIN_VIEW;
import com.xinyou.util.ConnectionManager;

@Path("/InvBasic")
@Produces({MediaType.APPLICATION_JSON})
public class InvBasic extends BaseService {	
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
	@Path(value = "/addwh")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> addWh(BasicRequest<CTN_MAIN,CTN_MAIN_VIEW> request) throws Exception{
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
			CTN_MAIN ctn = request.getData_entity();
			if(StringUtils.isEmpty(ctn.getCtn_main_id())){
				throw new Exception("维护仓库信息失败：丢失仓库编号");
			}
			if(StringUtils.isEmpty(ctn.getCtn_baco())){
				throw new Exception("仓库条码不能为空");
			}
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			ctn.setCtn_type(3);
			
			Common_Biz.addBaco(ctn, operator, data_ver, "sys", conn);
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
	@Path(value = "/updatewh")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> updateWh(BasicRequest<CTN_MAIN,CTN_MAIN_VIEW> request) throws Exception{
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
			CTN_MAIN ctn = request.getData_entity();
			if(StringUtils.isEmpty(ctn.getCtn_main_id())){
				throw new Exception("维护仓库信息失败：丢失仓库编号");
			}
			if(StringUtils.isEmpty(ctn.getCtn_baco())){
				throw new Exception("仓库条码不能为空");
			}
			String operator = ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			ctn.setCtn_type(3);
			
			InvBasic_Biz.updateWh(ctn, operator, data_ver, conn);
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
	@Path(value = "/getwhs")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getWhs(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
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
			
			response.setDataList(InvBasic_Biz.getWhs(basic_request.getData_char(),basic_request.getData_char2(),conn));
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
	@Path(value = "/addWhArea")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> addWhArea(BasicRequest<CTN_MAIN,CTN_MAIN_VIEW> request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			conn.setAutoCommit(false);
			ui = getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}

			CTN_MAIN ctn_area=request.getData_entity();
			if(StringUtils.isEmpty(ctn_area.getParent_ctn_guid())){
				throw new Exception("新增库区失败：丢失仓库GUID");
			}
			if(StringUtils.isEmpty(ctn_area.getCtn_main_id())){
				throw new Exception("库区编号不能为空");
			}

			String operator=ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			ctn_area.setCtn_type(4);
			ctn_area.setWh_guid(ctn_area.getParent_ctn_guid());
			
			Common_Biz.addBaco(ctn_area, operator, data_ver, "sys", conn);
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
	@Path(value = "/delWhArea")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> delWhArea(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
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
			if(StringUtils.isEmpty(request.getData_char())){
				throw new Exception("库区删除失败：丢失库区GUID");
			}
			Common_Biz.delBaco(request.getData_char(), conn);
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
	@Path(value = "/getWhAreas")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getWhAreas(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
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
			response.setDataList(InvBasic_Biz.getCtnAreas(basic_request.getData_char(), conn));
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
	@Path(value = "/addWhShelf")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> addWhShelf(BasicRequest<CTN_MAIN,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			conn.setAutoCommit(false);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			CTN_MAIN ctn_shelf=basic_request.getData_entity();
			if(StringUtils.isEmpty(ctn_shelf.getWh_area_guid())){
				throw new Exception("新增货架缺失库区信息！");
			}
			if(StringUtils.isEmpty(ctn_shelf.getCtn_main_id())){
				throw new Exception("货架编号不能为空");
			}
			if(StringUtils.isEmpty(ctn_shelf.getCtn_baco())){
				throw new Exception("货架条码不能为空");
			}
			ctn_shelf.setCtn_type(5);
			CTN_MAIN_VIEW areaCtn = Common_Biz.getCtnByGuid(ctn_shelf.getWh_area_guid(), conn);
			ctn_shelf.setParent_ctn_guid(areaCtn.getCtn_main_guid());
			ctn_shelf.setWh_guid(areaCtn.getWh_guid());
			ctn_shelf.setWh_area_guid(areaCtn.getCtn_main_guid());
			
			String operator=ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			Common_Biz.addBaco(ctn_shelf, operator, data_ver, "sys", conn);
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
	@Path(value = "/delWhShelf")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> delWhShelf(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
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
			if(StringUtils.isEmpty(request.getData_char())){
				throw new Exception("货架删除失败：丢失货架GUID");
			}
			Common_Biz.delBaco(request.getData_char(), conn);
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
	@Path(value = "/getWhShelfs")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getWhShelfs(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
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

			response.setDataList(InvBasic_Biz.getCtnShelfs(basic_request.getData_char(), basic_request.getData_char2(), conn));
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
	@Path(value = "/addWhLoc")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> addWhLoc(BasicRequest<CTN_MAIN,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			conn.setAutoCommit(false);
			ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			CTN_MAIN ctn_loc=basic_request.getData_entity();

			if(StringUtils.isEmpty(ctn_loc.getCtn_main_id())){
				throw new Exception("库位编号不能为空");
			}
			if(StringUtils.isEmpty(ctn_loc.getCtn_baco())){
				throw new Exception("库位条码不能为空");
			}
			
			ctn_loc.setCtn_type(6);
			
			if(!StringUtils.isEmpty(ctn_loc.getWh_shelf_guid()))
			{
				CTN_MAIN_VIEW shelfCtn = Common_Biz.getCtnByGuid(ctn_loc.getWh_shelf_guid(), conn);
				ctn_loc.setParent_ctn_guid(shelfCtn.getCtn_main_guid());
				ctn_loc.setWh_guid(shelfCtn.getWh_guid());
				ctn_loc.setWh_area_guid(shelfCtn.getWh_area_guid());
				ctn_loc.setWh_shelf_guid(shelfCtn.getCtn_main_guid());
			}
			else if(!StringUtils.isEmpty(ctn_loc.getWh_area_guid()))
			{
				CTN_MAIN_VIEW areaCtn = Common_Biz.getCtnByGuid(ctn_loc.getWh_area_guid(), conn);
				ctn_loc.setParent_ctn_guid(areaCtn.getCtn_main_guid());
				ctn_loc.setWh_guid(areaCtn.getWh_guid());
				ctn_loc.setWh_area_guid(areaCtn.getCtn_main_guid());
			}
			else
			{
				throw new Exception("缺失位置信息！");
			}
			
			String operator=ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			Common_Biz.addBaco(ctn_loc, operator, data_ver, "sys", conn);
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
	@Path(value = "/delWhLoc")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> delWhLoc(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
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
			if(StringUtils.isEmpty(request.getData_char())){
				throw new Exception("货架删除失败：丢失货架GUID");
			}
			Common_Biz.delBaco(request.getData_char(), conn);
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
	@Path(value = "/getWhLocs")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getWhLocs(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
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
			response.setDataDM(InvBasic_Biz.getCtnLocs(basic_request.getData_char(), basic_request.getData_char2(), basic_request.getData_char3(),
					basic_request.getPage_no(), 	basic_request.getPage_size(), conn));
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
	@Path(value = "/getWhLoc")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getWhLoc(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
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
			response.setDataEntity(Common_Biz.getCtnById(basic_request.getData_char(), conn));
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
	@Path(value = "/addCtnBox")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> addCtnBox(BasicRequest<CTN_MAIN,CTN_MAIN_VIEW> request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			conn.setAutoCommit(false);
			ui= getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				return response;
			}
			
			CTN_MAIN ctn_box=request.getData_entity();
			if(StringUtils.isEmpty(ctn_box.getCtn_main_id())){
				throw new Exception("周转箱编号不能为空");
			}
			if(StringUtils.isEmpty(ctn_box.getCtn_baco())){
				throw new Exception("周转箱条码不能为空");
			}
			if(StringUtils.isEmpty(ctn_box.getItm_id())){
				throw new Exception("品号不能为空");
			}
			if(StringUtils.isEmpty(ctn_box.getWh_loc_guid())){
				throw new Exception("库位不能为空");
			}
			
			String operator=ui.getUsr_main_guid();
			String data_ver="1.0.0.0";
			
			CTN_MAIN_VIEW locView = Common_Biz.getCtnByGuid(ctn_box.getWh_loc_guid(), conn);
			ctn_box.setWh_shelf_guid(locView.getWh_shelf_guid());
			ctn_box.setWh_area_guid(locView.getWh_area_guid());
			ctn_box.setWh_guid(locView.getWh_guid());
			ctn_box.setWh_loc_guid(locView.getCtn_main_guid());
			ctn_box.setParent_ctn_guid(locView.getCtn_main_guid());
			
			Common_Biz.addBaco(ctn_box, operator, data_ver, "sys", conn);
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
	@Path(value = "/getCtnBoxs")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getCtnBoxs(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui= getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			response.setDataDM(InvBasic_Biz.getCtnBoxs(basic_request.getData_char(), basic_request.getData_char2(), basic_request.getData_char3(), basic_request.getData_char4(), basic_request.getData_char5(), basic_request.getData_char6(), basic_request.getPage_no(), basic_request.getPage_size(), conn));
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
	@Path(value = "/getCtnBox")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getCtnBoxByBaco(BasicRequest<String,String> basic_request){
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
			if(basic_request.getData_char()==null||basic_request.getData_char().isEmpty()){
				throw new Exception("无条码信息");
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
	@Path(value = "/delCtnBox")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> delCtnBox(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			conn.setAutoCommit(false);
			ui= getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			
			Common_Biz.delBaco(basic_request.getData_char(), conn);
			
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
	@Path(value = "/updateCtnBox")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> updateCtnBox(BasicRequest<CTN_MAIN,CTN_MAIN_VIEW> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			conn.setAutoCommit(false);
			ui= getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			CTN_MAIN ctn_box=basic_request.getData_entity();
			if(StringUtils.isEmpty(ctn_box.getCtn_main_guid())){
				throw new Exception("周转箱维护失败：周转箱GUID丢失");
			}
			if(StringUtils.isEmpty(ctn_box.getCtn_baco())){
				throw new Exception("周转箱条码不能为空");
			}
			if(StringUtils.isEmpty(ctn_box.getParent_ctn_guid())){
				throw new Exception("库位条码不能为空");
			}

			String operator=ui.getUsr_main_guid();
			String client="";
			String data_ver="1.0.0.0";

			InvBasic_Biz.updateCtnBox(ctn_box, operator, client, data_ver, conn);
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
	@Path(value = "/getItmById")
	public BasicResponse<ITM_MAIN,ITM_MAIN> getItmById(BasicRequest<ITM_MAIN,ITM_MAIN> basic_request){
		BasicResponse<ITM_MAIN,ITM_MAIN> response = new BasicResponse<ITM_MAIN,ITM_MAIN>();
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
				throw new Exception("加载物料信息失败：丢失物料ID");
			}
			
			response.setDataEntity(InvBasic_Biz.getItmById(basic_request.getData_char(), conn));
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
	@Path(value = "/getItmListByPkgBaco")
	public BasicResponse<CTN_MAIN_VIEW,ITM_MAIN_VIEW> getItmListByPkgBaco(BasicRequest<String,String> basic_request){
		BasicResponse<CTN_MAIN_VIEW,ITM_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,ITM_MAIN_VIEW>();
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
				throw new Exception("加载物料信息失败：丢失物料ID");
			}
			
			response.setDataList2(InvBasic_Biz.getItmListByPkgBaco(basic_request.getData_char(), conn));
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
	@Path(value = "/getItmListByBacoList")
	public BasicResponse<ITM_MAIN_VIEW,ITM_MAIN_VIEW> getItmListByBacoList(BasicRequest<CTN_MAIN_VIEW,CTN_MAIN_VIEW> basic_request){
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
			
			response.setDataList(InvBasic_Biz.getItmListByBacoList(basic_request.getData_list(), conn));
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
	@Path(value = "/doOutInv")
	public BasicResponse<String,String> doOutInv(BasicRequest<TRAN_DOC,CTN_MAIN_VIEW> request){
		BasicResponse<String,String> response = new BasicResponse<String,String>();
		Connection conn = null;
		
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
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
			doc.getHead().setTran_type(210);
			doc.getHead().setBase_doc_type(30);
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
			}
			
			InvBasic_Biz.doOutInv(request.getData_list(), operator, conn);
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
	@Path(value = "/getCtnListByItmId")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> getCtnListByItmId(BasicRequest<String,String> basic_request){
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
			
			response.setDataList(InvBasic_Biz.getCtnListByItmId(basic_request.getData_char(), conn));
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

