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

import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.biz.ORG_BIZ;
import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.ORG_MAIN;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.response.OrgResponse;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;
import com.xinyou.util.ServiceBase;


@Path("/Org")
@Produces({MediaType.APPLICATION_JSON})
public class Org extends ServiceBase{
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
	@Path(value = "/addOrg")
	public OrgResponse addOrg(ORG_MAIN org, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		OrgResponse response = new OrgResponse();
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
			org.setCreated_by(ui.getUsr_main_guid());
			org.setUpdated_by(ui.getUsr_main_guid());
			org.setClient_guid("client");
			ORG_BIZ biz = new ORG_BIZ();
			biz.addOrg(org, conn);
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
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};;
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getOrgs")
	public OrgResponse getOrgs(String coguid, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		OrgResponse response = new OrgResponse();
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
			ORG_BIZ biz = new ORG_BIZ();
			response.setOrgNodeListData(biz.getOrgs(coguid, conn));
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
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};;
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delOrg")
	public OrgResponse delOrg(ORG_MAIN org, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		OrgResponse response = new OrgResponse();
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
			ORG_BIZ biz = new ORG_BIZ();
			biz.delOrg(org, conn);
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
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};;
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getOrg")
	public OrgResponse getOrg(String orgguid, @Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse){
		OrgResponse response = new OrgResponse();
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
			ORG_BIZ biz = new ORG_BIZ();
			response.setOrgData(biz.getOrg(orgguid, conn));
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
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};;
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/updateOrg")
	public OrgResponse updateOrg(ORG_MAIN org, @Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse){
		OrgResponse response = new OrgResponse();
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
			org.setUpdated_by(ui.getUsr_main_guid());
			ORG_BIZ biz = new ORG_BIZ();
			biz.updateOrg(org, conn);
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
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};;
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSlOrg")
	public OrgResponse getSlOrg(String co_guid, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		OrgResponse response = new OrgResponse();
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
			ORG_BIZ biz = new ORG_BIZ();
			response.setOrgListData(biz.getSlOrg(co_guid, conn));
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
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};;
		}
		return response;
	}
}
