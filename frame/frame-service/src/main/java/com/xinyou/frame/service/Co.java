package com.xinyou.frame.service;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;



import org.apache.commons.lang3.StringUtils;

import com.xinyou.frame.domain.biz.CO_BIZ;
import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.CO_MAIN;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.response.CoResponse;
import com.xinyou.frame.searchstruct.CoSearch;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;
import com.xinyou.util.ServiceBase;


@Path("/Co")
@Produces({ MediaType.APPLICATION_JSON })
public class Co extends ServiceBase {

	USR_MAIN ui = null;
	private void getUserInfo(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Connection conn) throws Exception {
		ui = new USR_MAIN();
		if(httpRequest.getCookies()==null||httpRequest.getCookies().length==0){
			ui.setStatus(1);
			return;
		}
		int iCookie = httpRequest.getCookies().length;
		if(iCookie==0){
			ui.setStatus(1);
			return;
		}
		String token = "";
		for(int i=0;i<iCookie;i++){
			if(httpRequest.getCookies()[i].getName().equals("frame-token")){
				token =  httpRequest.getCookies()[i].getValue();
			}
		}
		if(token==null||token.length()==0){
			ui.setStatus(1);
			return;
		}
		USR_BIZ biz = new USR_BIZ();
		ui = biz.getUserInfo(token, conn);
	}
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/addCo")
	public CoResponse addCo(CO_MAIN co, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		CoResponse response = new CoResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(httpRequest, httpResponse, conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(co.getCo_main_id()==null||co.getCo_main_id().length()==0){
				throw new Exception("公司代码不能为空");
			}
			if(co.getCo_name()==null||co.getCo_name().length()==0){
				throw new Exception("公司名称不能为空");
			}
			if(co.getCo_addr()==null||co.getCo_addr().length()==0){
				throw new Exception("公司地址不能为空");
			}
			co.setCreated_by(ui.getUsr_main_guid());
			co.setUpdated_by(ui.getUsr_main_guid());
			co.setClient_guid("client");
			CO_BIZ biz = new CO_BIZ();
			biz.addCo(co, conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getCos")
	public CoResponse getCos(CoSearch co_search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		CoResponse response = new CoResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(httpRequest, httpResponse, conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			CO_BIZ biz = new CO_BIZ();
			response.setCoDMData(biz.getCos(co_search.getCo_id(), co_search.getCo_name(),
					co_search.getPage_no(), co_search.getPage_size(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}

	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSlCos")
	public CoResponse getSlCos(@Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		CoResponse response = new CoResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(httpRequest, httpResponse, conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			CO_BIZ biz = new CO_BIZ();
			response.setCoListData(biz.getSlCos(conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delCo")
	public CoResponse delCo(String coguids, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		CoResponse response = new CoResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(httpRequest, httpResponse, conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(coguids==null||coguids.length()==0){
				throw new IllegalArgumentException("请选择公司，可以选择多个");
			}
			String[] coguidArray = StringUtils.split(coguids, ',');
			if(coguidArray.length==0){
				throw new IllegalArgumentException("请选择公司，可以选择多个");
			}
			CO_BIZ biz = new CO_BIZ();
			biz.delCo(coguidArray, conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getCo")
	public CoResponse getCo(String coguid, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		CoResponse response = new CoResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(httpRequest, httpResponse, conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(coguid==null||coguid.length()==0){
				throw new Exception("公司guid不能为空");
			}
			CO_BIZ biz = new CO_BIZ();
			response.setCoData(biz.getCo(coguid, conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/updateCo")
	public CoResponse updateCo(CO_MAIN co, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		CoResponse response = new CoResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(httpRequest, httpResponse, conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(co.getCo_name()==null||co.getCo_name().length()==0){
				throw new IllegalArgumentException("公司名称不能为空");
			}
			co.setUpdated_by(ui.getUsr_main_guid());
			CO_BIZ biz = new CO_BIZ();
			biz.updateCo(co, conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	 
}
