package com.xinyou.label.service;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.frame.request.BasicRequest;
import com.xinyou.frame.response.BasicResponse;
import com.xinyou.frame.service.BaseService;
import com.xinyou.label.domain.biz.InvReport_Biz;
import com.xinyou.label.domain.entities.STK_ITM_WKSITE;
import com.xinyou.label.domain.entities.WORKING_MINUTS;
import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;
import com.xinyou.label.domain.viewes.EMP_SWS_RP_VIEW;
import com.xinyou.label.domain.viewes.ITM_INV_REPORT_VIEW;
import com.xinyou.label.domain.viewes.ITM_TRAN_REPORT_VIEW;
import com.xinyou.label.domain.viewes.ITM_WO_REPORT_VIEW;
import com.xinyou.label.domain.viewes.RBA_CTN_RE_VIEW;
import com.xinyou.label.domain.viewes.SCRAP_REPORT_VIEW;
import com.xinyou.label.domain.viewes.STK_ITM_VIEW;
import com.xinyou.label.domain.viewes.SUB_WO_SUB_VIEW;
import com.xinyou.label.domain.viewes.TRAN_BACO_VIEW;
import com.xinyou.label.domain.viewes.WO_DOC_REPORT_VIEW;
import com.xinyou.util.ConnectionManager;
import com.xinyou.util.StringUtil;

@Path("/InvReport")
@Produces({MediaType.APPLICATION_JSON})
public class InvReport extends BaseService {	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/ItmInvQtyReport")
	public BasicResponse<ITM_INV_REPORT_VIEW,ITM_INV_REPORT_VIEW> ItmInvQtyReport(BasicRequest<String,String> basic_request){
		BasicResponse<ITM_INV_REPORT_VIEW,ITM_INV_REPORT_VIEW> response = new BasicResponse<ITM_INV_REPORT_VIEW,ITM_INV_REPORT_VIEW>();
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
			
			response.setDataDM(InvReport_Biz.getItmInvReport(basic_request.getData_char(),basic_request.getData_char2(),basic_request.getPage_no(),basic_request.getPage_size(),conn));
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
	@Path(value = "/BoxInvItmQtyReport")
	public BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> BoxInvItmQtyReport(BasicRequest<String,String> basic_request){
		BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW> response = new BasicResponse<CTN_MAIN_VIEW,CTN_MAIN_VIEW>();
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
			
			response.setDataList(InvReport_Biz.getBoxInvReportByItm(basic_request.getData_char(),basic_request.getData_char2(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/ItmInvQtyReportExp")
	public void ItmInvQtyReportExp(@FormParam("param0") String token, @FormParam("param1") String invGuid, @FormParam("param2") String itmId,@FormParam("param3") int maxRecords,@Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<ITM_INV_REPORT_VIEW> rpList =  InvReport_Biz.getItmInvReport_Exp(invGuid,itmId,maxRecords,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("分库存量表"); 
			
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("品号");
			row.createCell(1).setCellValue("品名");
			row.createCell(2).setCellValue("数量");
			row.createCell(3).setCellValue("单位");
			
			for(int i=0;i<rpList.size();i++)
			{
				ITM_INV_REPORT_VIEW data = (ITM_INV_REPORT_VIEW)rpList.get(i);
				 row= sheet.createRow(i+1);
				 
				 row.createCell(0).setCellValue(data.getItm_main_id());
				row.createCell(1).setCellValue(data.getItm_name());
				row.createCell(2).setCellValue(data.getItm_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				row.createCell(3).setCellValue(data.getItm_unit());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("分库存量表")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/BoxInvReport_Exp")
	public void BoxInvReport_Exp(@FormParam("param0") String token, @FormParam("param1") String invGuid, @FormParam("param2") String itmId,@FormParam("param3") int maxRecords,@Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<CTN_MAIN_VIEW> rpList =  InvReport_Biz.getBoxInvReport_Exp(invGuid,itmId,maxRecords,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("分库存量流程票表"); 
			
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("流程票号");
			row.createCell(1).setCellValue("品名");
			row.createCell(2).setCellValue("数量");
			row.createCell(3).setCellValue("库位");
			
			for(int i=0;i<rpList.size();i++)
			{
				CTN_MAIN_VIEW data = (CTN_MAIN_VIEW)rpList.get(i);
				 row= sheet.createRow(i+1);
				 
				 row.createCell(0).setCellValue(data.getCtn_baco());
				row.createCell(1).setCellValue(data.getItm_id());
				row.createCell(2).setCellValue(data.getItm_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				row.createCell(3).setCellValue(data.getWh_loc_baco());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("分库存量流程票表")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/ItmTranQtyReport")
	public BasicResponse<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW> ItmTranQtyReport(BasicRequest<String,String> basic_request){
		BasicResponse<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW> response = new BasicResponse<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW>();
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
			
			//String whId, String itmId, long bg, long ed, int in_out, int page_no, int page_size
			response.setDataDM(InvReport_Biz.getItmTranReport(
					basic_request.getData_char(),basic_request.getData_char2(),
					basic_request.getData_long(),basic_request.getData_long2(),
					basic_request.getData_int(),
					basic_request.getPage_no(),basic_request.getPage_size(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/ItmTranQtyReportExp")
	public void ItmTranQtyReportExp(@FormParam("param0") String token, @FormParam("param1") String whId, @FormParam("param2") String itmId,
			@FormParam("param3") long bg, @FormParam("param4") long ed, @FormParam("param5") int in_out, @FormParam("param6") int export_size,
			@Context HttpServletResponse httpResponse){
		Connection conn = null; 
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<ITM_TRAN_REPORT_VIEW> rpList =  InvReport_Biz.getItmTranReport_Exp(whId,itmId,bg,ed,in_out,export_size,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("出入库明细表"); 
			
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("日期");
			row.createCell(1).setCellValue("操作人");
			row.createCell(2).setCellValue("交易类型");
			row.createCell(3).setCellValue("其它出入库原因");
			row.createCell(4).setCellValue("品号");
			row.createCell(5).setCellValue("品名");
			row.createCell(6).setCellValue("数量");
			row.createCell(7).setCellValue("单位");
			
			for(int i=0;i<rpList.size();i++)
			{
				ITM_TRAN_REPORT_VIEW data = (ITM_TRAN_REPORT_VIEW)rpList.get(i);
				 row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue(dateFormat.format(new Date(data.getTran_dt())));
				row.createCell(1).setCellValue(data.getOperator());
				if(data.getTran_type()==100){
					row.createCell(2).setCellValue("原材料采购收料");
				} else if(data.getTran_type()==101){
					row.createCell(2).setCellValue("半成品采购收料");
				} else if(data.getTran_type()==102){
					row.createCell(2).setCellValue("成品采购收料");
				} else if(data.getTran_type()==110){
					row.createCell(2).setCellValue("生产领料(原材料)");
				} else if(data.getTran_type()==120){
					row.createCell(2).setCellValue("其它入库(原材料)");
				} else if(data.getTran_type()==130){
					row.createCell(2).setCellValue("其它出库（原材料）");
				} else if(data.getTran_type()==150){
					row.createCell(2).setCellValue("半成品生产调拨入库");
				} else if(data.getTran_type()==160){
					row.createCell(2).setCellValue("生产领料(半成品)");
				} else if(data.getTran_type()==170){
					row.createCell(2).setCellValue("半成品整箱其它入库");
				} else if(data.getTran_type()==180){
					row.createCell(2).setCellValue("半成品零散其它入库");
				} else if(data.getTran_type()==190){
					row.createCell(2).setCellValue("半成品其它出库");
				} 
				else{
					row.createCell(2).setCellValue(data.getTran_type());
				}
				
				row.createCell(3).setCellValue(data.getTran_reason());
				row.createCell(4).setCellValue(data.getItm_main_id());
				row.createCell(5).setCellValue(data.getItm_name());
				row.createCell(6).setCellValue(data.getItm_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				row.createCell(7).setCellValue(data.getItm_unit());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("出入库明细表")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/ItmWoQtyReport")
	public BasicResponse<ITM_WO_REPORT_VIEW,ITM_WO_REPORT_VIEW> ItmWoQtyReport(BasicRequest<String,String> basic_request){
		BasicResponse<ITM_WO_REPORT_VIEW,ITM_WO_REPORT_VIEW> response = new BasicResponse<ITM_WO_REPORT_VIEW,ITM_WO_REPORT_VIEW>();
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
			
			response.setDataDM(InvReport_Biz.getItmWoReport(basic_request.getData_long(),basic_request.getData_long2(),
					basic_request.getData_char(),basic_request.getData_char2(),basic_request.getData_char3(),
					basic_request.getPage_no(),basic_request.getPage_size(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/ItmWoQtyReportExp")
	public void ItmWoQtyReportExp(@FormParam("param0") String token, @FormParam("param1") long bdt, @FormParam("param2") long rdt, 
			@FormParam("param3") String woId, @FormParam("param4") String lotId, @FormParam("param5") String itmId,
			@FormParam("param6") int maxRecords,@Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<ITM_WO_REPORT_VIEW> rpList =  InvReport_Biz.getItmWoReport_Exp(bdt,rdt,woId,lotId,itmId,maxRecords,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("工艺移转明细表"); 
			
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("工单号");
			row.createCell(1).setCellValue("批次号");
			row.createCell(2).setCellValue("生产图号");
			row.createCell(3).setCellValue("品名");
			row.createCell(4).setCellValue("开工时间");
			row.createCell(5).setCellValue("报工时间");
			row.createCell(6).setCellValue("报工数量");
			row.createCell(7).setCellValue("单位");
			row.createCell(8).setCellValue("工序名称");
			row.createCell(9).setCellValue("设备代码");
			row.createCell(10).setCellValue("参与员工");

			for(int i=0;i<rpList.size();i++)
			{
				ITM_WO_REPORT_VIEW data = (ITM_WO_REPORT_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue(data.getWo_id());
				row.createCell(1).setCellValue(data.getLot_id());
				row.createCell(2).setCellValue(data.getItm_main_id());
				row.createCell(3).setCellValue(data.getItm_name());
				row.createCell(4).setCellValue(dateFormat.format(new Date(data.getBdt())));
				row.createCell(5).setCellValue(dateFormat.format(new Date(data.getRdt())));
				row.createCell(6).setCellValue(data.getItm_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				row.createCell(7).setCellValue(data.getItm_unit());
				row.createCell(8).setCellValue(data.getRac_name());
				row.createCell(9).setCellValue(data.getWo_id());
				row.createCell(10).setCellValue(data.getEmp_id_list());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("工艺移转明细表")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/WoingReport")
	public BasicResponse<WO_DOC_REPORT_VIEW,WO_DOC_REPORT_VIEW> getWoingReport(BasicRequest<String,String> basic_request){
		BasicResponse<WO_DOC_REPORT_VIEW,WO_DOC_REPORT_VIEW> response = new BasicResponse<WO_DOC_REPORT_VIEW,WO_DOC_REPORT_VIEW>();
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
			
			response.setDataDM(InvReport_Biz.getWoingReport(basic_request.getData_char(),basic_request.getData_char2(),
					basic_request.getPage_no(),basic_request.getPage_size(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/WoingReportExp")
	public void getWoingReportExp(@FormParam("param0") String token,@FormParam("param1") String woId, @FormParam("param2") String itmId,
			@FormParam("param3") int maxRecords,@Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<WO_DOC_REPORT_VIEW> rpList =  InvReport_Biz.getWoingReport_Exp(woId,itmId,maxRecords,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("在制工单明细"); 

			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("工单号");
			row.createCell(1).setCellValue("主件品号");
			row.createCell(2).setCellValue("品名");
			row.createCell(3).setCellValue("生产数量");
			row.createCell(4).setCellValue("完工数量");
			row.createCell(5).setCellValue("单位");

			for(int i=0;i<rpList.size();i++)
			{
				WO_DOC_REPORT_VIEW data = (WO_DOC_REPORT_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue(data.getWo_id());
				row.createCell(1).setCellValue(data.getItm_id());
				row.createCell(2).setCellValue(data.getItm_name());
				row.createCell(3).setCellValue(data.getWo_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				row.createCell(4).setCellValue(data.getFinish_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				row.createCell(5).setCellValue(data.getItm_unit());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("在制工单明细")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetScrapReport")
	public BasicResponse<SCRAP_REPORT_VIEW,SCRAP_REPORT_VIEW> GetScrapReport(BasicRequest<String,String> basic_request){
		BasicResponse<SCRAP_REPORT_VIEW,SCRAP_REPORT_VIEW> response = new BasicResponse<SCRAP_REPORT_VIEW,SCRAP_REPORT_VIEW>();
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
			
			response.setDataDM(InvReport_Biz.GetScrapReport(basic_request.getData_long(),basic_request.getData_long2(),
					basic_request.getData_char(),basic_request.getData_char2(),basic_request.getData_char3(),
					basic_request.getPage_no(),basic_request.getPage_size(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetScrapReportExp")
	public void GetScrapReportExp(@FormParam("param0") String token, @FormParam("param1") long bdt, @FormParam("param2") long rdt, 
			@FormParam("param3") String woId, @FormParam("param4") String lotId, @FormParam("param5") String itmId,
			@FormParam("param6") int maxRecords,@Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<SCRAP_REPORT_VIEW> rpList =  InvReport_Biz.GetScrapReport_Exp(bdt, rdt, woId, lotId, itmId, maxRecords, conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("报废明细表"); 
			
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("报废日期");
			row.createCell(1).setCellValue("报废类型");
			row.createCell(2).setCellValue("工单号");
			row.createCell(3).setCellValue("工序");
			row.createCell(4).setCellValue("生产图号");
			row.createCell(5).setCellValue("批次号");
			row.createCell(6).setCellValue("工序代码");
			row.createCell(7).setCellValue("工序名称");
			row.createCell(8).setCellValue("报废数量");
			row.createCell(9).setCellValue("报废子件");
			row.createCell(10).setCellValue("不合格内容");
			row.createCell(11).setCellValue("产生原因");
			row.createCell(12).setCellValue("发生工艺");

			for(int i=0;i<rpList.size();i++)
			{
				SCRAP_REPORT_VIEW data = (SCRAP_REPORT_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue(dateFormat.format(new Date(data.getScrap_dt())));
				row.createCell(1).setCellValue(data.getScrap_type());
				row.createCell(2).setCellValue(data.getWo_id());
				row.createCell(3).setCellValue(data.getRac_seqno());
				row.createCell(4).setCellValue(data.getItm_id());
				row.createCell(5).setCellValue(data.getLot_id());
				row.createCell(6).setCellValue(data.getRac_id());
				row.createCell(7).setCellValue(data.getRac_name());
				row.createCell(8).setCellValue(data.getScrap_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				row.createCell(9).setCellValue(data.getScrap_part());
				row.createCell(10).setCellValue(data.getScrap_content_name());
				row.createCell(11).setCellValue(data.getScrap_reason_name());
				row.createCell(12).setCellValue(data.getHappen_rac_name());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("报废明细表")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetWorkMinuts")
	public BasicResponse<WORKING_MINUTS,WORKING_MINUTS> GetWorkMinuts(BasicRequest<String,String> basic_request){
		BasicResponse<WORKING_MINUTS,WORKING_MINUTS> response = new BasicResponse<WORKING_MINUTS,WORKING_MINUTS>();
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
			
			String tempStr = "";
			if(basic_request.getData_char()!=null&&basic_request.getData_char().length()>0){
				tempStr = basic_request.getData_char().replace("，", ",");
			}
			
			response.setDataList(InvReport_Biz.GetWorkMinuts(basic_request.getData_long(),basic_request.getData_long2(),tempStr.split(","),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetWorkMinutsExp")
	public void GetWorkMinutsExp(@FormParam("param0") String token, @FormParam("param1") long bdt, @FormParam("param2") long rdt, @FormParam("param3") String empIds, @Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			String tempStr = "";
			if(empIds!=null&&empIds.length()>0){
				tempStr = empIds.replace("，", ",");
			}
			
			List<WORKING_MINUTS> rpList =  InvReport_Biz.GetWorkMinuts(bdt, rdt,tempStr.split(","), conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("员工工时统计报表"); 
			
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("工号");
			row.createCell(1).setCellValue("员工姓名");
			row.createCell(2).setCellValue("工作分钟");
			row.createCell(3).setCellValue("工作小时");

			for(int i=0;i<rpList.size();i++)
			{
				WORKING_MINUTS data = (WORKING_MINUTS)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue(data.getEmp_id());
				row.createCell(1).setCellValue(data.getEmp_name());
				row.createCell(2).setCellValue(data.getMinuts());
				row.createCell(3).setCellValue( new BigDecimal(((double)data.getMinuts())/60).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("员工工时统计报表")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetEmpWorkRp")
	public BasicResponse<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW> GetEmpWorkRp(BasicRequest<String,String> basic_request){
		BasicResponse<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW> response = new BasicResponse<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW>();
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
			
			response.setDataList(InvReport_Biz.GetEmpWorkRp(basic_request.getData_long(),basic_request.getData_long2(),basic_request.getData_char(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetEmpWorkRpExp")
	public void GetEmpWorkRpExp(@FormParam("param0") String token, @FormParam("param1") long bdt, @FormParam("param2") long rdt, @FormParam("param3") String empGuid, @Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<EMP_SWS_RP_VIEW> rpList =  InvReport_Biz.GetEmpWorkRp(bdt, rdt, empGuid, conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("员工工时明细报表"); 			
			
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("开工时间");
			row.createCell(1).setCellValue("报工时间");
			row.createCell(2).setCellValue("工时");
			row.createCell(3).setCellValue("数量");
			row.createCell(4).setCellValue("工序代码");
			row.createCell(5).setCellValue("工序名称");
			row.createCell(6).setCellValue("机台");
			row.createCell(7).setCellValue("品号");
			row.createCell(8).setCellValue("流程票号");
			row.createCell(9).setCellValue("工单号");
			row.createCell(10).setCellValue("批号");

			for(int i=0;i<rpList.size();i++)
			{
				EMP_SWS_RP_VIEW data = (EMP_SWS_RP_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue(dateFormat.format(new Date(data.getBg_dt())));
				row.createCell(1).setCellValue(dateFormat.format(new Date(data.getRp_dt())));
				
				BigDecimal totalMs = new BigDecimal(data.getRp_dt()-data.getBg_dt());
				BigDecimal hours = totalMs.divide(new BigDecimal(3600000), 2,BigDecimal.ROUND_HALF_UP);
				row.createCell(2).setCellValue( String.valueOf(hours));
				row.createCell(3).setCellValue( String.valueOf(data.getFinish_qty().setScale(2,BigDecimal.ROUND_HALF_UP)) );
				row.createCell(4).setCellValue(data.getRp_rac_id());
				row.createCell(5).setCellValue(data.getRp_rac_name());
				row.createCell(6).setCellValue(data.getRp_ws());
				row.createCell(7).setCellValue(data.getItm_id());
				row.createCell(8).setCellValue(data.getSws_id());
				row.createCell(9).setCellValue(data.getWo_id());
				row.createCell(10).setCellValue(data.getLot_id());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("员工工时明细报表")+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetStkItems")
	public BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW> GetStkItems(BasicRequest<String,String> basic_request){
		BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW> response = new BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW>();
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
			
			response.setDataList(InvReport_Biz.GetTakeStockItemsByPlanGuid(basic_request.getData_char(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetStkItemsExp")
	public void GetStkItemsExp(@FormParam("param0") String token, @FormParam("param1") String stkGuid, @FormParam("param2") String invid,@FormParam("param3") String planid,  @Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<STK_ITM_VIEW> rpList =  InvReport_Biz.GetTakeStockItemsByPlanGuid(stkGuid, conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("盘点明细报表"); 
				            
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("日期");
			row.createCell(1).setCellValue("工号");
			row.createCell(2).setCellValue("姓名");
			row.createCell(3).setCellValue("条码号");
			row.createCell(4).setCellValue("类型");
			row.createCell(5).setCellValue("品号");
			row.createCell(6).setCellValue("账目数量");
			row.createCell(7).setCellValue("账目库位");
			row.createCell(8).setCellValue("盘点数量");
			row.createCell(9).setCellValue("盘点库位");

			for(int i=0;i<rpList.size();i++)
			{
				STK_ITM_VIEW data = (STK_ITM_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue(dateFormat.format(new Date(data.getStk_dt())));
				row.createCell(1).setCellValue(data.getEmp_id());
				row.createCell(2).setCellValue(data.getEmp_name());
				row.createCell(3).setCellValue( data.getCtn_baco());
				if(data.getCtn_type()==12){
					row.createCell(4).setCellValue("流程票");
				}
				else if(data.getCtn_type()==13){
					row.createCell(4).setCellValue("合格证");
				}
				row.createCell(5).setCellValue( data.getItm_id());
				row.createCell(6).setCellValue( data.getItm_qty().doubleValue());
				row.createCell(7).setCellValue(data.getLoc_baco());
				row.createCell(8).setCellValue( data.getStk_itm_qty().doubleValue());
				row.createCell(9).setCellValue(data.getStkloc());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("盘点明细报表-"+invid+"-"+planid)+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetSumStkItemsExp")
	public void GetSumStkItemsExp(@FormParam("param0") String token, @FormParam("param1") String stkGuid, @FormParam("param2") String invid,@FormParam("param3") String planid,  @Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<STK_ITM_VIEW> rpList =  InvReport_Biz.GetStkSumItemsByPlanGuid(stkGuid, conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("盘点明细汇总报表"); 
				            
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("品号");
			row.createCell(1).setCellValue("账目数量");
			row.createCell(2).setCellValue("盘点数量");

			for(int i=0;i<rpList.size();i++)
			{
				STK_ITM_VIEW data = (STK_ITM_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue( data.getItm_id());
				row.createCell(1).setCellValue( data.getItm_qty().doubleValue());
				row.createCell(2).setCellValue( data.getStk_itm_qty().doubleValue());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("盘点明细汇总报表-"+invid+"-"+planid)+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
			String test = ex.getMessage();
			test = test+"";
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetNoStkItems")
	public BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW> GetNoStkItems(BasicRequest<String,String> basic_request){
		BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW> response = new BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW>();
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
			
			response.setDataList(InvReport_Biz.GetNoTakeStockByPlanGuid(basic_request.getData_char(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetNoStkItemsExp")
	public void GetNoStkItemsExp(@FormParam("param0") String token, @FormParam("param1") String stkGuid, @FormParam("param2") String invid,@FormParam("param3") String planid,  @Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<STK_ITM_VIEW> rpList =  InvReport_Biz.GetNoTakeStockByPlanGuid(stkGuid, conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("未盘点明细报表"); 
				            
			XSSFRow row= sheet.createRow(0);
			row.createCell(0).setCellValue("条码号");
			row.createCell(1).setCellValue("类型");
			row.createCell(2).setCellValue("品号");
			row.createCell(3).setCellValue("数量");
			row.createCell(4).setCellValue("库位");

			for(int i=0;i<rpList.size();i++)
			{
				STK_ITM_VIEW data = (STK_ITM_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue( data.getCtn_baco());
				if(data.getCtn_type()==12){
					row.createCell(1).setCellValue("流程票");
				}
				else if(data.getCtn_type()==13){
					row.createCell(1).setCellValue("合格证");
				}
				row.createCell(2).setCellValue( data.getItm_id());
				row.createCell(3).setCellValue( data.getItm_qty().doubleValue());
				row.createCell(4).setCellValue(data.getLoc_baco());
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("未盘点明细报表-"+invid+"-"+planid)+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetMoreStkItems")
	public BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW> GetMoreStkItems(BasicRequest<String,String> basic_request){
		BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW> response = new BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW>();
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
			
			response.setDataList(InvReport_Biz.GetMoreTakeStockByPlanGuid(basic_request.getData_char(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetMoreStkItemsExp")
	public void GetMoreStkItemsExp(@FormParam("param0") String token, @FormParam("param1") String stkGuid, @FormParam("param2") String invid,@FormParam("param3") String planid,  @Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<STK_ITM_VIEW> rpList =  InvReport_Biz.GetMoreTakeStockByPlanGuid(stkGuid,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("盘盈明细报表"); 
				            
			XSSFRow row= sheet.createRow(0);

			row.createCell(0).setCellValue("品号");
			row.createCell(1).setCellValue("条码号");
			row.createCell(2).setCellValue("账目数量");
			row.createCell(3).setCellValue("账目库位");
			row.createCell(4).setCellValue("盘点数量");
			row.createCell(5).setCellValue("盘点库位");
			row.createCell(6).setCellValue("盘点员代码");
			row.createCell(7).setCellValue("盘盈原因");

			for(int i=0;i<rpList.size();i++)
			{
				STK_ITM_VIEW data = (STK_ITM_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue( data.getItm_id());
				row.createCell(1).setCellValue( data.getCtn_baco());
				row.createCell(2).setCellValue( data.getItm_qty().doubleValue());
				row.createCell(3).setCellValue( data.getCtnloc());
				row.createCell(4).setCellValue(data.getStk_itm_qty().doubleValue());
				row.createCell(5).setCellValue( data.getStkloc());
				row.createCell(6).setCellValue(data.getEmp_id());
				//--0 不在仓库里面，1 不在同一个仓库， 2 数量不一致
				if(data.getReason()==0){
					row.createCell(7).setCellValue("不在仓库里");
				}
				else if(data.getReason()==1){
					row.createCell(7).setCellValue("不在该仓库里");
				}
				else if(data.getReason()==2){
					row.createCell(7).setCellValue("数量不一致");
				}
				else{
					row.createCell(7).setCellValue("未知");
				}
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("盘盈明细报表-"+invid+"-"+planid)+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetLessStkItems")
	public BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW> GetLessStkItems(BasicRequest<String,String> basic_request){
		BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW> response = new BasicResponse<STK_ITM_VIEW,STK_ITM_VIEW>();
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
			
			response.setDataList(InvReport_Biz.GetLessTakeStockByPlanGuid(basic_request.getData_char(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetLessStkItemsExp")
	public void GetLessStkItemsExp(@FormParam("param0") String token, @FormParam("param1") String stkGuid, @FormParam("param2") String invid,@FormParam("param3") String planid,  @Context HttpServletResponse httpResponse){
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<STK_ITM_VIEW> rpList =  InvReport_Biz.GetLessTakeStockByPlanGuid(stkGuid,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			XSSFWorkbook workbook =new XSSFWorkbook(); 
			XSSFSheet sheet = workbook.createSheet("盘亏明细报表"); 
				            
			XSSFRow row= sheet.createRow(0);

			row.createCell(0).setCellValue("品号");
			row.createCell(1).setCellValue("条码号");
			row.createCell(2).setCellValue("账目数量");
			row.createCell(3).setCellValue("账目库位");
			row.createCell(4).setCellValue("盘点数量");
			row.createCell(5).setCellValue("盘点库位");
			row.createCell(6).setCellValue("盘点员代码");
			row.createCell(7).setCellValue("盘亏原因");

			for(int i=0;i<rpList.size();i++)
			{
				STK_ITM_VIEW data = (STK_ITM_VIEW)rpList.get(i);
				row= sheet.createRow(i+1);
				 
				row.createCell(0).setCellValue( data.getItm_id());
				row.createCell(1).setCellValue( data.getCtn_baco());
				row.createCell(2).setCellValue( data.getItm_qty().doubleValue());
				row.createCell(3).setCellValue( data.getCtnloc());
				row.createCell(4).setCellValue(data.getStk_itm_qty().doubleValue());
				row.createCell(5).setCellValue( data.getStkloc());
				row.createCell(6).setCellValue(data.getEmp_id());
				//--0 没有盘点到，2 数量不一致
				if(data.getReason()==0){
					row.createCell(7).setCellValue("未盘点到");
				}
				else if(data.getReason()==2){
					row.createCell(7).setCellValue("数量不一致");
				}
				else{
					row.createCell(7).setCellValue("未知");
				}
			}
			
			OutputStream out=httpResponse.getOutputStream();
	        httpResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
	        httpResponse.addHeader("Content-Disposition","attachment;filename="+StringUtil.Encode("盘亏明细报表-"+invid+"-"+planid)+"("+dateFormat.format(new Date())+")"+".xlsx");
	        workbook.write(out);
	        httpResponse.flushBuffer();
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetEmpWorkRpAll")
	public BasicResponse<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW> GetEmpWorkRpAll(BasicRequest<String,String> basic_request){
		BasicResponse<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW> response = new BasicResponse<EMP_SWS_RP_VIEW,EMP_SWS_RP_VIEW>();
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
			
			String tempStr = "";
			if(basic_request.getData_char()!=null&&basic_request.getData_char().length()>0){
				tempStr = basic_request.getData_char().replace("，", ",");
			}
			
			response.setDataDM(InvReport_Biz.GetEmpWorkRp(basic_request.getData_long(),basic_request.getData_long2(),tempStr.split(","), basic_request.getPage_no(),basic_request.getPage_size(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetEmpWorkRpAllExp2")
	public void GetEmpWorkRpAllExp(@FormParam("param0") String token, @FormParam("param1") long bdt, @FormParam("param2") long edt, @FormParam("param3") String empIds, @Context HttpServletResponse httpResponse){
		Connection conn = null;

		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			String tempStr = "";
			if(empIds!=null&&empIds.length()>0){
				tempStr = empIds.replace("，", ",");
			}
			
			List<EMP_SWS_RP_VIEW> rpList =  InvReport_Biz.GetEmpWorkRp2_Exp(bdt,edt, tempStr.split(","),conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			httpResponse.setContentType("text/comma-separated-values;charset=gb2312");
			PrintWriter out = httpResponse.getWriter();
			httpResponse.setHeader("Content-disposition",
				    "inline; filename="+StringUtil.Encode("员工工时明细报表")+"("+dateFormat.format(new Date()) + ".csv");
			
			out.print("工号"+","+"姓名"+","+"开工时间"+","+"报工时间"+","+"记录工时"+","+"产量工时"+","+"数量"+","+"指标"+","+"工序代码"+","+"工序名称"+","+"机台"+","+"设备号"+","+"品号"+","+"流程票号"+","+"工单号"+","+"批号"+"\n");
			for(int i=0;i<rpList.size();i++)
			{
				EMP_SWS_RP_VIEW data = (EMP_SWS_RP_VIEW)rpList.get(i);
				
				BigDecimal totalMs = new BigDecimal(data.getRp_dt()-data.getBg_dt());
				BigDecimal hours = totalMs.divide(new BigDecimal(3600000), 2,BigDecimal.ROUND_HALF_UP);
				BigDecimal tarHours = BigDecimal.ZERO;
				if(data.getRac_target().compareTo(BigDecimal.ZERO)>0){
					tarHours = data.getFinish_qty().divide(data.getRac_target(),2,BigDecimal.ROUND_HALF_UP);
				}
				
				out.print(data.getEmp_id()+","+data.getEmp_name()+","+dateFormat.format(new Date(data.getBg_dt()))+","+dateFormat.format(new Date(data.getRp_dt()))+","+
						String.valueOf(hours)+","+String.valueOf(tarHours.setScale(2,BigDecimal.ROUND_HALF_UP))+","+String.valueOf(data.getFinish_qty().setScale(2,BigDecimal.ROUND_HALF_UP)) +","+
						String.valueOf(data.getRac_target().doubleValue())+","+data.getRp_rac_id()+","+data.getRp_rac_name().split("　")[0]+","+data.getRp_ws()+","+data.getRp_ws_no()+","+data.getItm_id()+",'"+data.getSws_id()+",'"+
						data.getWo_id()+","+data.getLot_id()+"\n");
			}
			out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetSWSByConditon")
	public BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> GetSWSByConditon(BasicRequest<SUB_WO_SUB_VIEW,String> basic_request){
		BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW> response = new BasicResponse<SUB_WO_SUB_VIEW,SUB_WO_SUB_VIEW>();
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
			
			response.setDataList(InvReport_Biz.GetSWSByConditon(basic_request.getData_entity(),conn));
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
	@Path(value = "/GetStkItmWKSiteByConditonPaged")
	public BasicResponse<STK_ITM_WKSITE,STK_ITM_WKSITE> GetStkItmWKSiteByConditonPaged(BasicRequest<STK_ITM_WKSITE,String> basic_request){
		BasicResponse<STK_ITM_WKSITE,STK_ITM_WKSITE> response = new BasicResponse<STK_ITM_WKSITE,STK_ITM_WKSITE>();
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
			
			response.setDataDM(InvReport_Biz.GetStkItmWKSiteByConditonPaged(basic_request.getData_entity(),basic_request.getPage_no(),basic_request.getPage_size(),conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/GetStkItmWKSiteByConditonExp")
	public void GetStkItmWKSiteByConditonExp(@FormParam("param0") String token, @FormParam("param1") String stkMainId, @FormParam("param2") String swsId, @Context HttpServletResponse httpResponse){
		Connection conn = null;

		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
					
			List<STK_ITM_WKSITE> rpList =  InvReport_Biz.GetStkItmWKSiteByConditon(stkMainId,swsId,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			httpResponse.setContentType("text/comma-separated-values;charset=gb2312");
			PrintWriter out = httpResponse.getWriter();
			httpResponse.setHeader("Content-disposition",
				    "inline; filename="+StringUtil.Encode("现场盘点报表")+"("+dateFormat.format(new Date()) + ".csv");
			
			out.print("盘点计划代码号"+","+"流程票"+","+"图号"+","+"数量"+","+"工单工序"+","+"工艺名"+"\n");
			for(int i=0;i<rpList.size();i++)
			{
				STK_ITM_WKSITE data = (STK_ITM_WKSITE)rpList.get(i);

				out.print(((char)(9))+data.getStk_main_id()+","+((char)(9))+data.getSws_id()+","+((char)(9))+data.getItm_id()+","+data.getStk_value()+","+
						((char)(9))+data.getStk_rac_id()+","+data.getStk_rac_name()+"\n");
			}
			out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/GetRbaCtnReByConditon")
	public BasicResponse<RBA_CTN_RE_VIEW,RBA_CTN_RE_VIEW> GetRbaCtnReByConditon(BasicRequest<RBA_CTN_RE_VIEW,String> basic_request){
		BasicResponse<RBA_CTN_RE_VIEW,RBA_CTN_RE_VIEW> response = new BasicResponse<RBA_CTN_RE_VIEW,RBA_CTN_RE_VIEW>();
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
			
			response.setDataList(InvReport_Biz.GetRbaCtnReByConditon(basic_request.getData_entity(),conn));
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
	@Path(value = "/BacoTranQtyReport")
	public BasicResponse<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW> BacoTranQtyReport(BasicRequest<String,String> basic_request){
		BasicResponse<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW> response = new BasicResponse<ITM_TRAN_REPORT_VIEW,ITM_TRAN_REPORT_VIEW>();
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
			
			//String whId, String itmId,String ctnBaco, long bg, long ed, int in_out, int page_no, int page_size
			response.setDataDM(InvReport_Biz.getBacoTranReport(
					basic_request.getData_char(),
					basic_request.getData_char2(),
					basic_request.getData_char3(),
					basic_request.getData_long(),
					basic_request.getData_long2(),
					basic_request.getData_int(),
					basic_request.getPage_no(),
					basic_request.getPage_size(),
					conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/BacoTranQtyReportExp")
	public void BacoTranQtyReportExp(@FormParam("param0") String token, @FormParam("param1") String whId, @FormParam("param2") String itmId, @FormParam("param3") String ctnBaco,
			@FormParam("param4") long bg, @FormParam("param5") long ed, @FormParam("param6") int in_out, @FormParam("param7") int export_size,
			@Context HttpServletResponse httpResponse){
		Connection conn = null; 
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<ITM_TRAN_REPORT_VIEW> rpList =  InvReport_Biz.getBacoTranReport_Exp(whId,itmId,ctnBaco,bg,ed,in_out,export_size,conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			httpResponse.setContentType("text/comma-separated-values;charset=gb2312");
			PrintWriter out = httpResponse.getWriter();
			httpResponse.setHeader("Content-disposition",
				    "inline; filename="+StringUtil.Encode("出入库明细表")+"("+dateFormat.format(new Date()) + ".csv");
			
			out.print("日期"+","+"操作人"+","+"交易类型"+","+"其它出入库原因"+","+"图号"+","+"数量"+","+"箱号"+"\n");
			for(int i=0;i<rpList.size();i++)
			{
				ITM_TRAN_REPORT_VIEW data = (ITM_TRAN_REPORT_VIEW)rpList.get(i);

				String tranType="";
				if(data.getTran_type()==100){
					tranType="原材料采购收料";
				} else if(data.getTran_type()==101){
					tranType="半成品采购收料";
				} else if(data.getTran_type()==102){
					tranType="成品采购收料";
				} else if(data.getTran_type()==110){
					tranType="生产领料(原材料)";
				} else if(data.getTran_type()==120){
					tranType="其它入库(原材料)";
				} else if(data.getTran_type()==130){
					tranType="其它出库（原材料）";
				} else if(data.getTran_type()==150){
					tranType="半成品生产调拨入库";
				} else if(data.getTran_type()==160){
					tranType="生产领料(半成品)";
				} else if(data.getTran_type()==170){
					tranType="半成品整箱其它入库";
				} else if(data.getTran_type()==180){
					tranType="半成品零散其它入库";
				} else if(data.getTran_type()==190){
					tranType="半成品其它出库";
				} 
				else{
					tranType=data.getTran_type()+"";
				}
						
				out.print(dateFormat.format(new Date(data.getTran_dt()))+","
				+data.getOperator()+","
				+tranType+","
				+data.getTran_reason()+","
				+((char)(9))+data.getItm_main_id()+","
				+data.getItm_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+","
				+((char)(9))+data.getCtn_baco()+","
				+"\n");
			}
			
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON}) 
	@Path(value = "/getItmDetailTranReport")
	public BasicResponse<TRAN_BACO_VIEW,TRAN_BACO_VIEW> getItmDetailTranReport(BasicRequest<String,String> basic_request){
		BasicResponse<TRAN_BACO_VIEW,TRAN_BACO_VIEW> response = new BasicResponse<TRAN_BACO_VIEW,TRAN_BACO_VIEW>();
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
			
			response.setDataList(InvReport_Biz.getItmDetailTranReport(basic_request.getData_char(),basic_request.getData_char2(), conn));
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
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED}) 
	@Path(value = "/getItmDetailTranReportExp")
	public void getItmDetailTranReportExp(@FormParam("param0") String token, @FormParam("param1") String tranGuid, @FormParam("param2") String itmId,
			@Context HttpServletResponse httpResponse){
		Connection conn = null; 
		try{
			conn = ConnectionManager.getConnection(FrameConfig.DSNAME);
			ui = getUserInfo(token, conn);
			if(ui==null||ui.getStatus()!=0){
				return;
			}
			
			List<TRAN_BACO_VIEW> rpList =  InvReport_Biz.getItmDetailTranReport(tranGuid,itmId, conn);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			httpResponse.setContentType("text/comma-separated-values;charset=gb2312");
			PrintWriter out = httpResponse.getWriter();
			httpResponse.setHeader("Content-disposition",
				    "inline; filename="+StringUtil.Encode("出入库详细表")+"("+dateFormat.format(new Date()) + ".csv");
			
			out.print("图号"+","+"数量"+","+"箱号"+"\n");
			for(int i=0;i<rpList.size();i++)
			{
				TRAN_BACO_VIEW data = (TRAN_BACO_VIEW)rpList.get(i);

				
						
				out.print(((char)(9))+data.getItm_id()+","
				+data.getTran_qty().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+","
				+((char)(9))+data.getCtn_baco()+","
				+"\n");
			}
			
	        out.close();
		}catch (Exception ex){
		}finally{
			try {if(conn!=null&&!conn.isClosed())conn.close();} catch (SQLException e) {};
		}
	}
}
