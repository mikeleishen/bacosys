package com.xinyou.frame.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.biz.SYS_BIZ;
import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.BIZ_SYS;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.request.SysRequest;
import com.xinyou.frame.response.BizSysResponse;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;
import com.xinyou.util.ServiceBase;

@Path("/BizSys")
public class BizSys extends ServiceBase {
	    USR_MAIN ui= null;
	    
	    /**
	     *  登录以后，根据token获取用户的信息 
	     * */
	    private void getUserInfo(String token,Connection conn) throws Exception {
	    	  ui = new USR_MAIN();
	    	  if(token==null || token.length() == 0) {
	    		   ui.setStatus(1); //无效用户
	    		   return;
	    	  }
	    	  
	    	  USR_BIZ biz = new USR_BIZ();
	    	  ui = biz.getUserInfo(token, conn);
	    }
	    
	    private void getUserInfo(HttpServletRequest httpRequest,
	    		   HttpServletResponse httpResponse,Connection conn) throws Exception {
	    	 ui = new USR_MAIN();
	    	 if(httpRequest.getCookies() == null || httpRequest.getCookies().length == 0) {
	    		  ui.setStatus(1);
	    		  return;
	    	 }
	    	 
	    	 int iCookie = httpRequest.getCookies().length;
	    	 if(iCookie == 0) {
	    		  ui.setStatus(1);
	    		  return;
	    	 }
	    	 
	    	 String token="";
	    	 for(int i=0;i<iCookie;i++) {
	    		 if(httpRequest.getCookies()[i].getName().equals("frame-token")) {
	    			  token = httpRequest.getCookies()[i].getValue();
	    			  break;
	    		 }
	    	 }
	    	 
	    	 if(token == null || token.length()== 0) {
	    		  ui.setStatus(1);
	    		  return ;
	    	 }
	    	 
	         USR_BIZ biz = new USR_BIZ();
	         ui = biz.getUserInfo(token, conn);
	    	 
	    	
	    }

	    /**
	     *  添加系统功能模块
	     * */
	    @POST
	    @Consumes({MediaType.APPLICATION_JSON})
	    @Path("/addBizSys")
	    public BizSysResponse addBizSys(SysRequest sys_request) {
	    	
	    	 BizSysResponse  response = new BizSysResponse();
	    	 Connection conn = null;
	    	 try {
	    		 conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
	    		 getUserInfo( sys_request.getToken(),conn);
	    		 if(ui == null || ui.getStatus() != 0) {
	    			 
	    			  response.setStatus("2");
	    			  if(ui.getStatus()==1) {
	    				  response.setInfo("您没有登录系统");
	    			  }else if(ui.getStatus() == 2) {
	    				  response.setInfo("登录已经超时");
	    			  }  
	    			  return response;
	    		 }
	    		 
	    		 //to save data 
	    		 BIZ_SYS biz_sys= sys_request.getBiz_sys();
	    		 if(biz_sys.getBiz_sys_id() == null || biz_sys.getBiz_sys_id().isEmpty()) {
	    			 throw new Exception("系统代码不能为空");
	    		 }
	    		 
	    		 if(biz_sys.getSys_name() == null || biz_sys.getSys_name().isEmpty()) {
	    			 throw new Exception("系统名称不能为空");
	    		 }
	    		 
	    		 biz_sys.setCreated_by(ui.getUsr_main_guid());
	    		 biz_sys.setUpdated_by(ui.getUsr_main_guid());
	    		 biz_sys.setClient_guid("");
	    		 SYS_BIZ biz = new SYS_BIZ();
	    		 biz.addBizSys(biz_sys, conn);
	    		 response.setInfo("");
	    		 response.setStatus("0");

	    	 }catch(Exception ex) {
	    		 response.setInfo( ex.getMessage() == null? "java.lang.NullPointerException: null":ex.getMessage());
	    		 response.setStatus("1");
	    		 StackTraceElement StackTrace = new Exception().getStackTrace()[0];
	    		 Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), "Exception:" + ex.getMessage(), "Exception");

	    	 }finally {
	    		 
	    		  try {
					if(conn != null && conn.isClosed() == false) conn.close();
				} catch (SQLException e) {
				}
	    	 }
	    	return response;
	    }
	
	   
	    @POST
	    @Consumes({MediaType.APPLICATION_JSON})
	    @Path(value="/getBizSyss")
	    public BizSysResponse getBizSyss(SysRequest sys_request) {
	    	BizSysResponse response = new BizSysResponse();
	    	Connection conn = null;
	    	try {
	    		conn = ConnectionManager.getConnection(FrameConfig.CONFIGNAME);
	    		//user verification
	    		this.getUserInfo(sys_request.getToken(), conn);
	    		if(ui==null || ui.getStatus() != 0) {
	    			response.setStatus("2");
	    			
	    			if(ui.getStatus()==1)
						response.setInfo("您还未登录系统");
					else if(ui.getStatus()==2)
						response.setInfo("登录已超时");
	    			
	    			return response;
	    			
	    		}
	    		
	    		SYS_BIZ biz = new SYS_BIZ();
	    		response.setBizSysDMData(biz.getBizSyss(sys_request.getSys_id(), sys_request.getSys_name(), sys_request.getPage_no(), sys_request.getPage_size(), conn));
	    		response.setInfo("");
	    		response.setStatus("0");
	    		
	    	}catch(Exception ex) {
	    		response.setInfo(ex.getMessage()==null?"java.lang.NullPointerException: null":ex.getMessage());
				response.setStatus("1");
				// TODO Log
				StackTraceElement StackTrace = new Exception().getStackTrace()[0];
				Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), "Exception: "+ex.getMessage(), "Exception");
			
	    		
	    	}finally {
	    		try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};	
	    	}
	    	
	    	
	    	return response;
	    }
}
