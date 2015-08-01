package com.xinyou.frame.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.frame.domain.models.FUN_NODE;
import com.xinyou.frame.domain.models.USR_MAIN_VIEW;
import com.xinyou.frame.request.UsrRequest;
import com.xinyou.frame.response.UsrResponse;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;
import com.xinyou.util.ServiceBase;

@Path("/Usr")
@Produces({MediaType.APPLICATION_JSON})
public class Usr extends ServiceBase {
	
	  USR_MAIN ui = null;
	  private void getUserInfo(String  token,Connection conn) throws Exception {
		   ui = new USR_MAIN();
		   if(token == null || token.length() == 0) {
			   ui.setStatus(1);
			   return;
		   }
		   
		   USR_BIZ biz = new USR_BIZ();
		   ui = biz.getUserInfo(token, conn);
	  }
	  /**
	   *  用户登录（网页）
	   * */
	  @POST
	  @Consumes( {MediaType.APPLICATION_JSON })
	  @Path(value="/signIn")
	  public UsrResponse signIn(UsrRequest usr_request,
			                 @Context HttpServletResponse httpResponse) {
		   UsrResponse response = new UsrResponse();
		   Connection conn = null;
  
		   try {
			   
			   conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			   if(usr_request.getUsr_name() == null || usr_request.getUsr_name().isEmpty()) {
				    throw new IllegalArgumentException("");
			   }
			   
			   if(usr_request.getUsr_pswd()==null||usr_request.getUsr_pswd().isEmpty()){
					throw new IllegalArgumentException("密码不能为空");
				}
				if(usr_request.getUsr_pswd().length()<1){
					throw new IllegalArgumentException("密码长度不小于1位");
				}
				
				USR_BIZ biz = new USR_BIZ();
				USR_MAIN_VIEW usr_main_view = biz.signIn(usr_request.getUsr_name(), usr_request.getUsr_pswd(), conn);
				Cookie tokenCookie = new Cookie("frame_token",usr_main_view.getToken());
				tokenCookie.setPath("/");
				tokenCookie.setMaxAge(7200);
				httpResponse.addCookie(tokenCookie);
				response.setInfo("");
				response.setStatus("0");
				
			   
		   }catch(Exception ex) {
			   
			   response.setInfo(ex.getMessage()== null ? "null":ex.getMessage());
			   response.setStatus("1");
			   
			   StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			   Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
			
			   
			   
		   }finally {
			   
				try {if(conn!=null&&!conn.isClosed())conn.close();} 
				catch (SQLException e) {};
		   }
		   
		   return response;
		  
		  
	  }
 
	  /**
	   * PDA 用户登录
	   * */
	  @POST
	  @Consumes({MediaType.APPLICATION_JSON})
	  @Path(value="/signIn_pda")
	  public UsrResponse signIn_pda(USR_MAIN usr) {
		  UsrResponse response = new UsrResponse();
		  Connection conn=null;
		  try {
			  conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			  if(usr.getUsr_name()==null||usr.getUsr_name().isEmpty()){
					throw new IllegalArgumentException("用户名不能为空");
				}
				if(usr.getUsr_pswd()==null||usr.getUsr_pswd().isEmpty()){
					throw new IllegalArgumentException("密码不能为空");
				}
				if(usr.getUsr_pswd().length()<6){
					throw new IllegalArgumentException("密码长度不小于6位");
				}
				USR_BIZ biz = new USR_BIZ();
			  EntityListDM<USR_MAIN,FUN_NODE>	 dm =   biz.signIn_pda(usr, conn);
			  response.setDataDM( dm );
			  response.setInfo("");
			  response.setStatus("0");
			    
		  }catch(Exception ex) {
			  
			  response.setInfo(ex.getMessage()==null?"java.lang.NullPointerException: null":ex.getMessage());
			  response.setStatus("1");
				
		  }finally {
				try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
				  
		  }
		  
		  return response;
	  }
}
