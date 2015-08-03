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
import com.xinyou.frame.domain.biz.Param_Biz;
import com.xinyou.frame.domain.entities.CODE_MAIN;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.request.BasicRequest;
import com.xinyou.frame.response.BasicResponse;
import com.xinyou.util.ConnectionManager;

@Path("/SpecialCode")
@Produces({MediaType.APPLICATION_JSON})
public class SpecialCode {
	USR_MAIN ui = null;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/SaveCode")
	public BasicResponse<String, String> SaveCode(BasicRequest<CODE_MAIN,CODE_MAIN> basic_request, @Context HttpServletRequest httpRequest,@Context HttpServletResponse httpResponse){
		BasicResponse<String, String> response = new BasicResponse<String,String>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);

			if(basic_request.getData_entity().getCode_main_id()==null||basic_request.getData_entity().getCode_main_id().length()==0){
				throw new Exception("条码不能为空");
			}

			Param_Biz.SaveCodeMain(basic_request.getData_entity(), conn);
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetCodeMain")
	public BasicResponse<CODE_MAIN, CODE_MAIN> GetCodeMain(BasicRequest<CODE_MAIN,CODE_MAIN> basic_request, @Context HttpServletRequest httpRequest,@Context HttpServletResponse httpResponse){
		BasicResponse<CODE_MAIN, CODE_MAIN> response = new BasicResponse<CODE_MAIN,CODE_MAIN>();
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);

			response.setDataEntity( Param_Biz.GetCodeMain(basic_request.getData_char(), conn) );
			response.setInfo("");
			response.setStatus("0");
		}catch (Exception ex){
			if(ex.getMessage()==null){
				response.setInfo("Exception: "+ex.getMessage());
			}
			response.setInfo(ex.getMessage());
			response.setStatus("1");
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
		return response;
	}
}
