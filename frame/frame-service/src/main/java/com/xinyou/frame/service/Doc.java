package com.xinyou.frame.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.xinyou.frame.domain.biz.DOC_BIZ;
import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.DOC_MAIN;
import com.xinyou.frame.domain.entities.PRIVILEGE_MAIN;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.request.DocRequest;
import com.xinyou.frame.response.DocResponse;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;
import com.xinyou.util.ServiceBase;

@Path("/Doc")
@Produces({MediaType.APPLICATION_JSON})
public class Doc extends ServiceBase{
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
	@Path(value = "/addDoc")
	public DocResponse addDoc(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			DOC_MAIN doc = doc_request.getDoc();
			doc.setCreated_by(ui.getUsr_main_guid());
			doc.setUpdated_by(ui.getUsr_main_guid());
			doc.setClient_guid("");
			DOC_BIZ biz = new DOC_BIZ();
			biz.addDoc(doc, conn);
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
	@Path(value = "/getDocs")
	public DocResponse getDocs(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			DOC_BIZ biz = new DOC_BIZ();
			response.setDocDMData(biz.getDocs(doc_request.getDoc_id(), doc_request.getDoc_name(),
					doc_request.getPage_no(), doc_request.getPage_size(), conn));
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
	@Path(value = "/getDoc")
	public DocResponse getDoc(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			DOC_BIZ biz = new DOC_BIZ();
			response.setDocData(biz.getDoc(doc_request.getDoc_guid(), conn));
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
	@Path(value = "/delDoc")
	public DocResponse delDoc(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			String docguids = doc_request.getDoc_guid();
			if(docguids==null||docguids.length()==0){
				throw new IllegalArgumentException("请选择凭证，可以选择多个");
			}
			String[] docguidArray = StringUtils.split(docguids, ',');
			if(docguidArray.length==0){
				throw new IllegalArgumentException("请选择凭证，可以选择多个");
			}
			DOC_BIZ biz = new DOC_BIZ();
			biz.delDoc(docguidArray, conn);
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
	@Path(value = "/updateDoc")
	public DocResponse updateDoc(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			DOC_MAIN doc = doc_request.getDoc();
			if(doc.getDoc_name()==null||doc.getDoc_name().isEmpty()){
				throw new IllegalArgumentException("凭证名称不能为空");
			}
			doc.setUpdated_by(ui.getUsr_main_guid());
			DOC_BIZ biz = new DOC_BIZ();
			biz.updateDoc(doc, conn);
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
	@Path(value = "/getSlDocs")
	public DocResponse getSlDocs(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			DOC_BIZ biz = new DOC_BIZ();
			response.setDocListData(biz.getSlDocs(conn));
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
	@Path(value = "/getSlParam")
	public DocResponse getSlParam(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			DOC_BIZ biz = new DOC_BIZ();
			response.setParamListData(biz.getSlParam(doc_request.getParam_type(), conn));
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
	@Path(value = "/addPrivilege")
	public DocResponse addPrivilege(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			PRIVILEGE_MAIN privilege = doc_request.getPrivilege();
			privilege.setCreated_by(ui.getUsr_main_guid());
			privilege.setUpdated_by(ui.getUsr_main_guid());
			privilege.setClient_guid("");
			DOC_BIZ biz = new DOC_BIZ();
			biz.addPrivilege(privilege, conn);
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
	@Path(value = "/getPrivileges")
	public DocResponse getPrivileges(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			DOC_BIZ biz = new DOC_BIZ();
			response.setPrivilegeDMData(biz.getPrivileges(doc_request.getPrivilege_id(), doc_request.getPrivilege_name(),
					doc_request.getDoc_guid(), doc_request.getPage_no(), doc_request.getPage_size(), conn));
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
	@Path(value = "/delPrivilege")
	public DocResponse delPrivilege(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			String privilegeguids = doc_request.getPrivilege_guid();
			if(privilegeguids==null||privilegeguids.isEmpty()){
				throw new IllegalArgumentException("请选择权限，可以选择多个");
			}
			String[] privilegeguidArray = StringUtils.split(privilegeguids, ',');
			DOC_BIZ biz = new DOC_BIZ();
			biz.delPrivilege(privilegeguidArray, conn);
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
	@Path(value = "/getPrivilege")
	public DocResponse getPrivilege(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			String privilegeguid = doc_request.getPrivilege_guid();
			if(privilegeguid==null||privilegeguid.isEmpty()){
				throw new Exception("权限guid不能为空");
			}
			DOC_BIZ biz = new DOC_BIZ();
			response.setPrivilegeData(biz.getPrivilege(privilegeguid, conn));
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
	@Path(value = "/updatePrivilege")
	public DocResponse updatePrivilege(DocRequest doc_request){
		DocResponse response = new DocResponse();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			getUserInfo(doc_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			PRIVILEGE_MAIN privilege = doc_request.getPrivilege();
			if(privilege.getPrivilege_name()==null||privilege.getPrivilege_name().isEmpty()){
				throw new IllegalArgumentException("权限名称不能为空");
			}
			if(privilege.getDoc_guid()==null||privilege.getDoc_guid().isEmpty()){
				throw new IllegalArgumentException("请选择所属凭证");
			}
			privilege.setUpdated_by(ui.getUsr_main_guid());
			DOC_BIZ biz = new DOC_BIZ();
			biz.updatePrivilege(privilege, conn);
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
