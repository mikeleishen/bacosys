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
import com.xinyou.frame.domain.biz.Param_Biz;
import com.xinyou.frame.request.BasicRequest;
import com.xinyou.frame.response.BasicResponse;
import com.xinyou.frame.service.BaseService;
import com.xinyou.label.domain.entities.PARA_MAIN;
import com.xinyou.util.ConnectionManager;

@Path("/Basic")
@Produces({MediaType.APPLICATION_JSON})
public class BasicValues extends BaseService{
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/AddPara")
	public BasicResponse<String,String> addPara(BasicRequest<PARA_MAIN,PARA_MAIN> request) throws Exception{
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
			
			PARA_MAIN para = request.getData_entity();
			if(StringUtils.isEmpty(para.getId())){
				throw new Exception("缺少编码！");
			}
			if(StringUtils.isEmpty(para.getPara_type_id())){
				throw new Exception("缺少类型编码！");
			}
			if(StringUtils.isEmpty(para.getPara_value())){
				throw new Exception("缺少值！");
			}
			
			String operator = ui.getUsr_main_guid();
			para.setCreated_by(operator);
			para.setUpdated_by(operator);
			para.setClient_guid("gl");
			para.setData_ver("1.0.0.0");
			
			Param_Biz.addPara(para, conn);
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
	@Path(value = "/UpdatePara")
	public BasicResponse<String,String> updatePara(BasicRequest<PARA_MAIN,PARA_MAIN> request) throws Exception{
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
			PARA_MAIN para = request.getData_entity();
			if(StringUtils.isEmpty(para.getId())){
				throw new Exception("缺少编码！");
			}
			if(StringUtils.isEmpty(para.getPara_type_id())){
				throw new Exception("缺少类型编码！");
			}
			if(StringUtils.isEmpty(para.getPara_value())){
				throw new Exception("缺少值！");
			}
			
			String operator = ui.getUsr_main_guid();
			para.setUpdated_by(operator);
			
			Param_Biz.updatePara(para, conn);
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
	@Path(value = "/DelPara")
	public BasicResponse<String,String> delPara(BasicRequest<String,String> request) throws Exception{
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
			
			Param_Biz.deletePara(request.getData_char(), conn);
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
	@Path(value = "/GetParas")
	public BasicResponse<PARA_MAIN,PARA_MAIN> getParas(BasicRequest<String,String> basic_request){
		BasicResponse<PARA_MAIN,PARA_MAIN> response = new BasicResponse<PARA_MAIN,PARA_MAIN>();
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
			
			response.setDataList(Param_Biz.getParas(basic_request.getData_char(),conn));
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
