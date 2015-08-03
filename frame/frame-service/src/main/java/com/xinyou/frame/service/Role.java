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

import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.biz.ROLE_BIZ;
import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.PRIVILEGE_MAIN;
import com.xinyou.frame.domain.entities.ROLE_FUN;
import com.xinyou.frame.domain.entities.ROLE_SYS;
import com.xinyou.frame.domain.entities.ROLE_MAIN;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.domain.models.SYS_DM;
import com.xinyou.frame.response.DocResponse;
import com.xinyou.frame.response.RoleResponse;
import com.xinyou.frame.searchstruct.DocSearch;
import com.xinyou.frame.searchstruct.RoleSearch;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;
import com.xinyou.util.ServiceBase;


@Path("/Role")
@Produces({MediaType.APPLICATION_JSON})
public class Role extends ServiceBase{
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
	@Path(value = "/addSysRole")
	public RoleResponse addSysRole(ROLE_MAIN entity, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(entity.getRole_main_id()==null||entity.getRole_main_id().length()==0){
				throw new IllegalArgumentException("角色代码不能为空");
			}
			if(entity.getRole_name()==null||entity.getRole_name().length()==0){
				throw new IllegalArgumentException("角色名称不能为空");
			}
			if(entity.getRole_desc()==null)entity.setRole_desc("");
			entity.setCreated_by(ui.getUsr_main_guid());
			entity.setUpdated_by(ui.getUsr_main_guid());
			entity.setClient_guid("client");
			ROLE_BIZ biz = new ROLE_BIZ();
			biz.addSysRole(entity, conn);
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
	@Path(value = "/getSysRoles")
	public RoleResponse getSysRoles(RoleSearch search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setRoleDMData(biz.getSysRoles(search.getRole_id(), search.getRole_name()
					, search.getPage_no(), search.getPage_size(), conn));
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
	@Path(value = "/getSysRole")
	public RoleResponse getSysRole(RoleSearch search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(search.getRole_guid()==null||search.getRole_guid().length()==0){
				throw new IllegalArgumentException("角色GUID不能为空");
			}
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setRoleData(biz.getSysRole(search.getRole_guid(), conn));
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
	@Path(value = "/updateSysRole")
	public RoleResponse updateSysRole(ROLE_MAIN entity, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(entity.getRole_main_guid()==null||entity.getRole_main_guid().length()==0){
				throw new IllegalArgumentException("角色guid不能为空");
			}
			if(entity.getRole_name()==null||entity.getRole_name().length()==0){
				throw new IllegalArgumentException("角色名称不能为空");
			}
			if(entity.getRole_desc()==null)entity.setRole_desc("");
			entity.setUpdated_by(ui.getUsr_main_guid());
			entity.setClient_guid("client");
			ROLE_BIZ biz = new ROLE_BIZ();
			biz.updateSysRole(entity, conn);
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
	@Path(value = "/delSysRole")
	public RoleResponse delSysRole(RoleSearch search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(search.getRole_guid()==null||search.getRole_guid().length()==0){
				throw new IllegalArgumentException("丢失角色guid，删除失败");
			}
			ROLE_BIZ biz = new ROLE_BIZ();
			biz.delSysRole(search.getRole_guid(), conn);
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
	@Path(value = "/getSlSysRoles")
	public RoleResponse getSlSysRoles(@Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setRoleListData(biz.getSlSysRoles(conn));
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
	@Path(value = "/getRoleSyss")
	public RoleResponse getRoleSyss(RoleSearch search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setBizSysDMData(biz.getRoleSyss(search.getRole_guid(), search.getSys_id(), search.getSys_name()
					, search.getPage_no(), search.getPage_size(), conn));
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
	@Path(value = "/getSlRoleSyss")
	public RoleResponse getSlRoleSyss(ROLE_SYS entity, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(entity.getRole_guid()==null||entity.getRole_guid().length()==0){
				throw new Exception("角色guid不能为空");
			}
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setBizSysListData(biz.getSlRoleSyss(entity, conn));
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
	@Path(value = "/getRoleSyssLeft")
	public RoleResponse getRoleSyssLeft(RoleSearch search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setBizSysDMData(biz.getRoleSyssLeft(search.getRole_guid(), search.getSys_id(), search.getSys_name()
					, search.getPage_no(), search.getPage_size(), conn));
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
	@Path(value = "/addRoleSyss")
	public RoleResponse addRoleSyss(SYS_DM DM, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(DM.getRoleData()==null||DM.getRoleData().getRole_main_guid()==null
					||DM.getRoleData().getRole_main_guid().length()==0){
				throw new IllegalArgumentException("角色guid不能为空");
			}
			if(DM.getBizSysListData()==null||DM.getBizSysListData().size()==0){
				throw new IllegalArgumentException("请选择系统，可以选择多个");
			}
			DM.getRoleData().setCreated_by(ui.getUsr_main_guid());
			DM.getRoleData().setUpdated_by(ui.getUsr_main_guid());
			DM.getRoleData().setClient_guid("client");
			ROLE_BIZ biz = new ROLE_BIZ();
			biz.addRoleSyss(DM.getRoleData(), DM.getBizSysListData(), conn);
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
	@Path(value = "/defaultSys")
	public RoleResponse defaultSys(ROLE_SYS entity, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(entity.getRole_sys_guid()==null||entity.getRole_sys_guid().length()==0){
				throw new Exception("请选择系统，只能选择一个");
			}
			if(entity.getRole_guid()==null||entity.getRole_guid().length()==0){
				throw new Exception("角色guid不能为空");
			}
			entity.setUpdated_by(ui.getUsr_main_guid());
			ROLE_BIZ biz = new ROLE_BIZ();
			biz.defaultSys(entity, conn);
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
	@Path(value = "/delRoleSys")
	public RoleResponse delRoleSys(RoleSearch search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(search.getRole_sys_guid()==null||search.getRole_sys_guid().length()==0){
				throw new IllegalArgumentException("丢失系统guid，删除失败");
			}
			ROLE_BIZ biz = new ROLE_BIZ();
			biz.delRoleSys(search.getRole_sys_guid(), conn);
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
	@Path(value = "/getSysFuns")
	public RoleResponse getSysFuns(ROLE_SYS entity, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setFunNodeListData(biz.getSysFuns(entity, conn));
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
	@Path(value = "/addRoleFuns")
	public RoleResponse addRoleFuns(ROLE_FUN entity, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(entity.getRole_guid()==null||entity.getRole_guid().length()==0){
				throw new Exception("角色guid不能为空");
			}
			if(entity.getSys_guid()==null||entity.getSys_guid().length()==0){
				throw new Exception("系统guid不能为空");
			}
			String[] funGuidArray = null;
			ROLE_BIZ biz = new ROLE_BIZ();
			if(entity.getFun_guid()==null||entity.getFun_guid().length()==0){
				biz.delRoleFuns(entity.getRole_guid(), entity.getSys_guid(), conn);
			}else{
				funGuidArray = entity.getFun_guid().split(",");
				if(funGuidArray.length==0){
					biz.delRoleFuns(entity.getRole_guid(), entity.getSys_guid(), conn);
				}else{
					biz.addRoleFuns(ui.getUsr_main_guid(), "client", entity.getRole_guid(), entity.getSys_guid(), funGuidArray, conn);
				}
			}
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
	@Path(value = "/getRolePrivileges")
	public DocResponse getRolePrivileges(DocSearch doc_search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		DocResponse response = new DocResponse();
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
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setPrivilegeDMData(biz.getRolePrivileges(doc_search.getRole_guid(),doc_search.getPrivilege_id(), doc_search.getPrivilege_name(), doc_search.getDoc_guid()
					, doc_search.getPage_no(), doc_search.getPage_size(), conn));
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
	@Path(value = "/getRolePrivilegesLeft")
	public DocResponse getRolePrivilegesLeft(DocSearch doc_search, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		DocResponse response = new DocResponse();
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
			ROLE_BIZ biz = new ROLE_BIZ();
			response.setPrivilegeDMData(biz.getRolePrivilegesLeft(doc_search.getRole_guid(),doc_search.getPrivilege_id(), doc_search.getPrivilege_name(), doc_search.getDoc_guid()
					, doc_search.getPage_no(), doc_search.getPage_size(), conn));
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
	@Path(value = "/addRolePrivileges")
	public RoleResponse addRolePrivileges(PRIVILEGE_MAIN privilege, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(privilege.getRole_guid()==null||privilege.getRole_guid().length()==0){
				throw new IllegalArgumentException("角色guid不能为空");
			}
			if(privilege.getPrivilege_main_guid()==null||privilege.getPrivilege_main_guid().length()==0){
				throw new IllegalArgumentException("请选择权限代码，可以选择多个");
			}
			String[] privilegeguidArray = StringUtils.split(privilege.getPrivilege_main_guid(), ',');
			if(privilegeguidArray.length==0){
				throw new IllegalArgumentException("请选择权限代码，可以选择多个");
			}
			ROLE_BIZ biz = new ROLE_BIZ();
			biz.addRolePrivileges(ui.getUsr_main_guid(), "client", privilege.getRole_guid(), privilegeguidArray, conn);
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
	@Path(value = "/delRolePrivilege")
	public RoleResponse delRolePrivilege(String roleprivilegeguids, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		RoleResponse response = new RoleResponse();
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
			if(roleprivilegeguids==null||roleprivilegeguids.length()==0){
				throw new IllegalArgumentException("请选择权限代码，可以选择多个");
			}
			String[] roleprivilegeguidArray = StringUtils.split(roleprivilegeguids, ',');
			if(roleprivilegeguidArray.length==0){
				throw new IllegalArgumentException("请选择权限代码，可以选择多个");
			}
			ROLE_BIZ biz = new ROLE_BIZ();
			biz.delRolePrivilege(roleprivilegeguidArray, conn);
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
