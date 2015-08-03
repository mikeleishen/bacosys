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

import com.xinyou.frame.domain.biz.EMP_BIZ;
import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.EMP_MAIN;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.domain.models.EMP_MAIN_VIEW;
import com.xinyou.frame.request.BasicRequest;
import com.xinyou.frame.response.BasicResponse;
import com.xinyou.frame.response.EmpResponse;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.Log;

@Path("/Emp")
@Produces({MediaType.APPLICATION_JSON})
public class Emp extends BaseService{
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
	@Path(value = "/getSlNations")
	public EmpResponse getSlNations(@Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		EmpResponse response = new EmpResponse();
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
			EMP_BIZ biz = new EMP_BIZ();
			response.setNationListData(biz.getSlNations(conn));
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
	@Path(value = "/getSlStates")
	public EmpResponse getSlStates(String nation_guid, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		EmpResponse response = new EmpResponse();
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
			EMP_BIZ biz = new EMP_BIZ();
			response.setStateListData(biz.getSlStates(nation_guid, conn));
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
	@Path(value = "/getSlCitys")
	public EmpResponse getSlCitys(String state_guid, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		EmpResponse response = new EmpResponse();
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
			EMP_BIZ biz = new EMP_BIZ();
			response.setCityListData(biz.getSlCitys(state_guid, conn));
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
	@Path(value = "/getSlCountrys")
	public EmpResponse getSlCountrys(String city_guid, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		EmpResponse response = new EmpResponse();
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
			EMP_BIZ biz = new EMP_BIZ();
			response.setCountryListData(biz.getSlCountrys(city_guid, conn));
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
	@Path(value = "/addEmp")
	public BasicResponse<EMP_MAIN,EMP_MAIN> addEmp(BasicRequest<EMP_MAIN,EMP_MAIN> request){
		BasicResponse<EMP_MAIN,EMP_MAIN> response = new BasicResponse<EMP_MAIN,EMP_MAIN>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			USR_MAIN ui = getUserInfo(request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			EMP_MAIN emp_main = request.getData_entity();
			if(StringUtils.isEmpty(emp_main.getEmp_main_id())){
				throw new Exception("员工ID不能为空");
			}
			if(StringUtils.isEmpty(emp_main.getEmp_name())){
				throw new Exception("员工姓名不能为空");
			}
			if(emp_main.getEmp_type()==0){
				throw new Exception("请选择员工类型");
			}
			emp_main.setCreated_by(ui.getUsr_main_guid());
			emp_main.setUpdated_by(ui.getUsr_main_guid());
			emp_main.setClient_guid("");
			emp_main.setData_ver("1.0.0.0");
			
			EMP_BIZ.addEmp(emp_main, conn);
			response.setInfo("");
			response.setStatus("0");
		}catch(Exception ex){
			response.setInfo(ex.getMessage()==null?"null":ex.getMessage());
			response.setStatus("1");
			}finally{
			try{if(conn!=null&&!conn.isClosed())conn.close();}catch(SQLException e){};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/updateEmp")
	public BasicResponse<EMP_MAIN,EMP_MAIN> updateEmp(BasicRequest<EMP_MAIN,EMP_MAIN> basic_request){
		BasicResponse<EMP_MAIN,EMP_MAIN> response = new BasicResponse<EMP_MAIN,EMP_MAIN>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			USR_MAIN ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			EMP_MAIN emp_main = basic_request.getData_entity();
			emp_main.setCreated_by(ui.getUsr_main_guid());
			emp_main.setUpdated_by(ui.getUsr_main_guid());
			emp_main.setClient_guid("");
			emp_main.setData_ver("1.0.0.0");
			EMP_BIZ.updateEmp(emp_main, conn);
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
	@Path(value = "/delEmp")
	public BasicResponse<EMP_MAIN,EMP_MAIN> delEmp(BasicRequest<EMP_MAIN,EMP_MAIN> basic_request){
		BasicResponse<EMP_MAIN,EMP_MAIN> response = new BasicResponse<EMP_MAIN,EMP_MAIN>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			conn.setAutoCommit(false);
			USR_MAIN ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			if(basic_request.getData_char()==null||basic_request.getData_char().isEmpty()){
				throw new Exception("员工删除失败：丢失员工GUID");
			}

			EMP_BIZ.delEmp(basic_request.getData_char(), conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(conn!=null)try {conn.rollback();} catch (SQLException e) {;}
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
	@Path(value = "/getEmps")
	public BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW> getEmps(BasicRequest<EMP_MAIN_VIEW,EMP_MAIN_VIEW> basic_request){
		BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW> response = new BasicResponse<EMP_MAIN_VIEW,EMP_MAIN_VIEW>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			USR_MAIN ui = getUserInfo(basic_request.getToken(), conn);
			if(ui==null||ui.getStatus()!=0){
				response.setStatus("2");
				
				if(ui.getStatus()==1)
					response.setInfo("您还未登录系统");
				else if(ui.getStatus()==2)
					response.setInfo("登录已超时");
				
				return response;
			}
			response.setDataDM(EMP_BIZ.getEmps(basic_request.getData_char(),basic_request.getData_char2(), basic_request.getPage_no(), 
					basic_request.getPage_size(), conn));
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
	@Path(value = "/getEmp")
	public EmpResponse getEmp(String emp_guid, @Context HttpServletRequest httpRequest,
			@Context HttpServletResponse httpResponse){
		EmpResponse response = new EmpResponse();
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
			EMP_BIZ biz = new EMP_BIZ();
			response.setEmpDMData(biz.getEmp(emp_guid, conn));
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
			StackTraceElement StackTrace = new Exception().getStackTrace()[0];
			Log.logSys(FrameConfig.CONFIGNAME, StackTrace.getClassName(), StackTrace.getMethodName(), response.getInfo(), "Exception");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
}
