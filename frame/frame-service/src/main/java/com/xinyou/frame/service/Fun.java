package com.xinyou.frame.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xinyou.frame.domain.biz.FUN_BIZ;
import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.FUN_MAIN;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.request.FunRequest;
import com.xinyou.frame.response.FunResponse;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;
import com.xinyou.util.ServiceBase;


@Path("/Fun")
@Produces({MediaType.APPLICATION_JSON})
public class Fun extends ServiceBase{
	USR_MAIN ui = null;
	
	private void getUserInfo(String token, Connection conn) throws Exception {
		ui = new USR_MAIN();
		if(token==null||token.length()==0){
			ui.setStatus(1);
			return;
		}
		USR_BIZ biz = new USR_BIZ();
		ui = biz.getUserInfo(token, conn);
    }
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/addSysFun")
	public FunResponse addSysFun(FunRequest fun_request){
		FunResponse response = new FunResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(fun_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			FUN_MAIN fun_main = fun_request.getFun_main();
			fun_main.setCreated_by(ui.getUsr_main_guid());
			fun_main.setUpdated_by(ui.getUsr_main_guid());
			fun_main.setClient_guid("");
			FUN_BIZ biz = new FUN_BIZ();
			biz.addSysFun(fun_main, conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"java.lang.NullPointerException: null":ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), "Exception: "+ex.getMessage(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getSysFuns")
	public FunResponse getSysFuns(FunRequest fun_request){
		FunResponse response = new FunResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(fun_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			FUN_BIZ biz = new FUN_BIZ();
			response.setFunNodeListData(biz.getSysFuns(fun_request.getSys_guid(), conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"java.lang.NullPointerException: null":ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), "Exception: "+ex.getMessage(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/updateSysFun")
	public FunResponse updateSysFun(FunRequest fun_request){
		FunResponse response = new FunResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(fun_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			FUN_MAIN fun_main = fun_request.getFun_main();
			fun_main.setUpdated_by(ui.getUsr_main_guid());
			FUN_BIZ biz = new FUN_BIZ();
			biz.updateSysFun(fun_main, conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"java.lang.NullPointerException: null":ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), "Exception: "+ex.getMessage(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/delSysFun")
	public FunResponse delSysFun(FunRequest fun_request){
		FunResponse response = new FunResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(fun_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			FUN_BIZ biz = new FUN_BIZ();
			biz.delSysFun(fun_request.getSys_guid(), fun_request.getFun_seqno(), conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			response.setInfo(ex.getMessage()==null?"java.lang.NullPointerException: null":ex.getMessage());
			response.setStatus("1");
			// TODO Log
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), "Exception: "+ex.getMessage(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
}
