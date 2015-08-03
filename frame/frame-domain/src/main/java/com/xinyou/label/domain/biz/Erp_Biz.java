package com.xinyou.label.domain.biz;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xinyou.label.domain.models.ERP_RDA_DOC;
import com.xinyou.label.domain.models.TRAN_DOC;
import com.xinyou.label.domain.viewes.DSCHDA_VIEW;
import com.xinyou.label.domain.viewes.DSCHDB_VIEW;
import com.xinyou.label.domain.viewes.ITM_MAIN_VIEW;
import com.xinyou.label.domain.viewes.JSKJFA_VIEW;
import com.xinyou.label.domain.viewes.WO_DOC_VIEW;
import com.mysql.jdbc.StringUtils;

public class Erp_Biz {
	public static void upSgmrba( TRAN_DOC doc,Connection conn) throws SQLException
	{
		if(doc!=null)
		{
			PreparedStatement ps = null;
			for(int i=0;i<doc.getBody_itm_base().size();i++)
			{
				ps = conn.prepareStatement("UPDATE SGMRBB SET RBB009=? WHERE RBB002=? AND RBB003=? AND RBB004=?");
				ps.setBigDecimal(1, doc.getBody_itm_base().get(i).getItm_qty());
				ps.setString(2, doc.getHead().getBase_doc_id());
				ps.setString(3, doc.getBody_itm_base().get(i).getBase_seqno());
				ps.setString(4, doc.getBody_itm_base().get(i).getItm_id());
				ps.execute();
			}
		}
	}
	
	public static void upSgmrbaByid(String rba002,Connection conn) throws Exception{
		PreparedStatement ps = null;
		ps = conn.prepareStatement("UPDATE SGMRBA SET RBA960=? WHERE RBA002=?");
		ps.setString(1, "已扫描");
		ps.setString(2,rba002);
		ps.execute();
		ps.close();
	}
	
	public static String addJskjca(TRAN_DOC doc, String erpUserId,Connection conn) throws Exception
	{
		//1、获取单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_str = sdf.format(new Date());
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateTime_str = sdf2.format(new Date());
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT MAXNO = MAX(JCA001) FROM JSKJCA WHERE JCA001 LIKE ?");
		pstmt.setString(1, date_str+"%");
		ResultSet rs = pstmt.executeQuery();
		String maxno = null;
		if(rs.next()){
			maxno = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		
		maxno=maxno==null?"0":maxno;
		String doc_seqno = maxno.replaceFirst(date_str, ""); 
		doc_seqno = Long.parseLong(doc_seqno)+1+"";
		while(doc_seqno.length()<6){
			doc_seqno="0"+doc_seqno;
		}
		String new_doc_id = date_str+doc_seqno;
		
		//2、获取额外信息
		String ptn_id = "";
		String usr_dep_id="";
		pstmt = conn.prepareStatement("SELECT HDA004 FROM DCSHDA WHERE HDA001=?");
		pstmt.setString(1, doc.getHead().getBase_doc_id());
		rs=pstmt.executeQuery();
		if(rs.next()){
			ptn_id = rs.getString(1);
		}
		else
		{
			throw new Exception("未找到供应商信息！");
		}
		rs.close();
		pstmt.close();
		
		pstmt = conn.prepareStatement("SELECT DBA005 FROM TPADBA WHERE DBA001=?");
		pstmt.setString(1, erpUserId);
		rs=pstmt.executeQuery();
		if(rs.next()){
			usr_dep_id = rs.getString(1);
		}
		else
		{
			throw new Exception("未找到供应商信息！");
		}
		rs.close();
		pstmt.close();
		
		//3、插入单头
		pstmt = conn.prepareStatement("INSERT INTO JSKJCA(JCA001,JCA003,JCA004,JCA005,JCA006,JCA011,JCA901,JCA902,JCA903,JCA904,JCA905 ) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, new_doc_id);
		pstmt.setString(2, date_str);
		pstmt.setString(3, ptn_id);//JCA004
		pstmt.setString(4, erpUserId);
		pstmt.setString(5, usr_dep_id);//JCA006
		pstmt.setString(6, "F");//JCA011
		pstmt.setString(7, erpUserId);//JCA901
		pstmt.setString(8, dateTime_str);//JCA902
		pstmt.setString(9, erpUserId);
		pstmt.setString(10, dateTime_str);
		pstmt.setInt(11, 3);
		pstmt.execute();
		pstmt.close();
		
		//4、插入单身
		for(int i=0;i<doc.getBody_itm_base().size();i++)
		{	
			String itmName = "";
			String itmUnit = "";
			pstmt = conn.prepareStatement("SELECT DEA002,DEA003 FROM TPADEA WHERE DEA001=?");
			pstmt.setString(1, doc.getBody_itm_base().get(i).getItm_id());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				itmName = rs.getString(1);
				itmUnit = rs.getString(2);
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement("INSERT INTO JSKJCB(JCB001,JCB002,JCB003,JCB004,JCB005,JCB006,JCB007,JCB008,JCB009,JCB010,JCB011,JCB012,JCB014,JCB021,JCB022,JCB023,JCB024,JCB901,JCB902,JCB903,JCB904,JCB905 ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, new_doc_id);//JCB001
			pstmt.setString(2, "000".substring(0,3-String.valueOf(i+1).length())+String.valueOf(i+1));//JCB002
			pstmt.setString(3, doc.getBody_itm_base().get(i).getItm_id());//JCB003
			pstmt.setString(4, itmName);//JCB004
			pstmt.setString(5, itmUnit);//JCB005 
			pstmt.setBigDecimal(6, doc.getBody_itm_base().get(i).getItm_qty());//JCB006
			pstmt.setBigDecimal(7, BigDecimal.ZERO);//JCB007
			pstmt.setBigDecimal(8, BigDecimal.ZERO);//JCB008
			pstmt.setString(9,"14" );//JCB009
			pstmt.setString(10, doc.getHead().getBase_doc_id());//JCB010
			pstmt.setString(11, doc.getBody_itm_base().get(i).getBase_seqno());//JCB011
			pstmt.setString(12, "F");//JCB012
			pstmt.setString(13, "F");//JCB014
			pstmt.setBigDecimal(14, BigDecimal.ONE);//JCB021
			pstmt.setInt(15, 1);//JCB022
			pstmt.setString(16, itmUnit);//JCB023
			pstmt.setBigDecimal(17, doc.getBody_itm_base().get(i).getItm_qty());//JCB024
			pstmt.setString(18, erpUserId);//JCB901
			pstmt.setString(19, dateTime_str);//JCB902
			pstmt.setString(20, erpUserId);//JCB903
			pstmt.setString(21,dateTime_str );//JCB904
			pstmt.setInt(22, 1);//JCB905
			pstmt.execute();
			pstmt.close();
		}
		
		return new_doc_id;
	}
	
	public static void addSemiJskjca(TRAN_DOC doc, String erpUserId,Connection conn) throws Exception
	{
		String maxno = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_str = sdf.format(new Date());
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateTime_str = sdf2.format(new Date());
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String ptn_id = "";
		String baseId = "";
		String new_doc_id ="";
		int bodyCount = 0;
		
		for(int n=0;n<doc.getBody_baco().size();n++){
			
			String usr_dep_id="";
			pstmt = conn.prepareStatement("SELECT HDA004 FROM DCSHDA WHERE HDA001=?");
			pstmt.setString(1, doc.getBody_baco().get(n).getBase_id());
			rs=pstmt.executeQuery();
			if(rs.next()){
				if(!rs.getString(1).equals(ptn_id)){
					new_doc_id ="";
					ptn_id = rs.getString(1);
				}
			}
			else
			{
				ptn_id = "388";
				//throw new Exception("未找到供应商信息！");
			}
			rs.close();
			pstmt.close();
		
			pstmt = conn.prepareStatement("SELECT DBA005 FROM TPADBA WHERE DBA001=?");
			pstmt.setString(1, erpUserId);
			rs=pstmt.executeQuery();
			if(rs.next()){
				usr_dep_id = rs.getString(1);
			}
			else
			{
				throw new Exception("未找到ERP用户信息！");
			}
			rs.close();
			pstmt.close();
			
			if(new_doc_id.equals("")||!baseId.equals(doc.getBody_baco().get(n).getBase_id())){
				bodyCount = 0;
				baseId = doc.getBody_baco().get(n).getBase_id();
				if(StringUtils.isNullOrEmpty(maxno)){
					pstmt = conn.prepareStatement("SELECT MAXNO = MAX(JCA001) FROM JSKJCA WHERE JCA001 LIKE ?");
					pstmt.setString(1, date_str+"%");
					rs = pstmt.executeQuery();
					
					if(rs.next()){
						maxno = rs.getString(1);
					}
					rs.close();
					pstmt.close();
					maxno=maxno==null?"0":maxno;
				}
			
				String doc_seqno = maxno.replaceFirst(date_str, ""); 
				doc_seqno = Long.parseLong(doc_seqno)+1+"";
				while(doc_seqno.length()<6){
					doc_seqno="0"+doc_seqno;
				}
				new_doc_id = date_str+doc_seqno;
				maxno = new_doc_id;
				//3、插入单头
				pstmt = conn.prepareStatement("INSERT INTO JSKJCA(JCA001,JCA003,JCA004,JCA005,JCA006,JCA011,JCA901,JCA902,JCA903,JCA904,JCA905 ) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1, new_doc_id);
				pstmt.setString(2, date_str);
				pstmt.setString(3, ptn_id);//JCA004
				pstmt.setString(4, erpUserId);
				pstmt.setString(5, usr_dep_id);//JCA006
				pstmt.setString(6, "F");//JCA011
				pstmt.setString(7, erpUserId);//JCA901
				pstmt.setString(8, dateTime_str);//JCA902
				pstmt.setString(9, erpUserId);
				pstmt.setString(10, dateTime_str);
				pstmt.setInt(11, 3);
				pstmt.execute();
				pstmt.close();
			}
			
			bodyCount++;
		
			String itmName = "";
			String itmUnit = "";
			pstmt = conn.prepareStatement("SELECT DEA002,DEA003 FROM TPADEA WHERE DEA001=?");
			pstmt.setString(1, doc.getBody_baco().get(n).getItm_id());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				itmName = rs.getString(1);
				itmUnit = rs.getString(2);
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement("INSERT INTO JSKJCB(JCB001,JCB002,JCB003,JCB004,JCB005,JCB006,JCB007,JCB008,JCB009,JCB010,JCB011,JCB012,JCB014,JCB021,JCB022,JCB023,JCB024,JCB901,JCB902,JCB903,JCB904,JCB905 ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, new_doc_id);//JCB001
			pstmt.setString(2, "000".substring(0, 3-String.valueOf(bodyCount).length())+String.valueOf(bodyCount));//JCB002
			pstmt.setString(3, doc.getBody_baco().get(n).getItm_id());//JCB003
			pstmt.setString(4, itmName);//JCB004
			pstmt.setString(5, itmUnit);//JCB005 
			pstmt.setBigDecimal(6, doc.getBody_baco().get(n).getTran_qty());//JCB006
			pstmt.setBigDecimal(7, BigDecimal.ZERO);//JCB007
			pstmt.setBigDecimal(8, BigDecimal.ZERO);//JCB008
			pstmt.setString(9,"14" );//JCB009
//			pstmt.setString(10, doc.getHead().getBase_doc_id());//JCB010
			pstmt.setString(10, doc.getBody_baco().get(n).getBase_id());//JCB010
			pstmt.setString(11, doc.getBody_baco().get(n).getBase_seqno());//JCB011
			pstmt.setString(12, "F");//JCB012
			pstmt.setString(13, "F");//JCB014
			pstmt.setBigDecimal(14, BigDecimal.ONE);//JCB021
			pstmt.setInt(15, 1);//JCB022
			pstmt.setString(16, itmUnit);//JCB023
			pstmt.setBigDecimal(17, doc.getBody_baco().get(n).getTran_qty());//JCB024
			pstmt.setString(18, erpUserId);//JCB901
			pstmt.setString(19, dateTime_str);//JCB902
			pstmt.setString(20, erpUserId);//JCB903
			pstmt.setString(21,dateTime_str );//JCB904
			pstmt.setInt(22, 1);//JCB905
			pstmt.execute();
			pstmt.close();
		}
	}
	
	public static String addJsklca(TRAN_DOC doc, String erpUserId,Connection conn) throws Exception
	{
		//1、获取单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_str = sdf.format(new Date());
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateTime_str = sdf2.format(new Date());
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT MAXNO = MAX(LCA001) FROM JSKLCA WHERE LCA001 LIKE ?");
		pstmt.setString(1, date_str+"%");
		ResultSet rs = pstmt.executeQuery();
		String maxno = null;
		if(rs.next()){
			maxno = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		
		maxno=maxno==null?"0":maxno;
		String doc_seqno = maxno.replaceFirst(date_str, ""); 
		doc_seqno = Long.parseLong(doc_seqno)+1+"";
		while(doc_seqno.length()<6){
			doc_seqno="0"+doc_seqno;
		}
		String new_doc_id = date_str+doc_seqno;
		
		//2、获取额外信息		
		
		//3、插入单头
		pstmt = conn.prepareStatement("INSERT INTO JSKLCA(LCA001, LCA003, LCA004, LCA009, LCA901,LCA902,LCA903,LCA904,LCA905)VALUES(?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, new_doc_id);//LCA001,单号 
		pstmt.setString(2, sdf.format(new Date()));//LCA003, 日期   
		pstmt.setString(3, erpUserId);//LCA004,经办人 
		pstmt.setString(4, "F");//LCA009,审核码
		pstmt.setString(5, erpUserId);//LCA901,录入者编号
		pstmt.setString(6, dateTime_str);//LCA902,录入时间 
		pstmt.setString(7, erpUserId);//LCA903,更改者编号
		pstmt.setString(8, dateTime_str);//LCA904,更改时间
		pstmt.setInt(9, 3);//LCA905, 更新标记  
		pstmt.execute();
		pstmt.close();
		
		/////////////////////////////////////////////////////////////////////////////////////

		//4、插入单身
		for(int i=0;i<doc.getBody_baco().size();i++)
		{	
			String itmName = "";
			String itmUnit = "";
			pstmt = conn.prepareStatement("SELECT DEA002,DEA003 FROM TPADEA WHERE DEA001=?");
			pstmt.setString(1, doc.getBody_baco().get(i).getItm_id());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				itmName = rs.getString(1);
				itmUnit = rs.getString(2);
			}
			else
			{
				rs.close();
				pstmt.close();
				throw new Exception("未找到物料信息！");
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement("INSERT INTO JSKLCB(LCB001, LCB002, LCB003, LCB004, LCB005, LCB006, LCB007, LCB008, LCB009, LCB017, LCB018, LCB019,LCB020,LCB021,LCB901,LCB902,LCB903,LCB904,LCB905,LCB960)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, new_doc_id);//LCB001, 单号  
			pstmt.setString(2, "000".substring(0,3-String.valueOf(i+1).length())+String.valueOf(i+1));//LCB002,序号  
			pstmt.setString(3, doc.getBody_baco().get(i).getItm_id());//LCB003,品号  
			pstmt.setString(4, itmName);//LCB004, 品名  
			pstmt.setString(5, itmUnit);//LCB005, 单位 
			pstmt.setString(6, doc.getBody_baco().get(i).getF_wh_id());//LCB006,转出仓库
			pstmt.setString(7, doc.getBody_baco().get(i).getWh_id());//LCB007,转入仓库
			pstmt.setBigDecimal(8, doc.getBody_baco().get(i).getTran_qty());//LCB008,数量
			pstmt.setString(9, "F");//LCB009,审核码  
			pstmt.setString(10, "********************");//LCB017,批号
			pstmt.setBigDecimal(11, new BigDecimal(1));//LCB018,换算分子
			pstmt.setInt(12, 1);//LCB019,换算分母  
			pstmt.setString(13, itmUnit);//LCB020,   辅助单位 
			pstmt.setBigDecimal(14, doc.getBody_baco().get(i).getTran_qty());//LCB021,辅助数量 
			pstmt.setString(15, erpUserId);//LCB901,录入者编号
			pstmt.setString(16, dateTime_str);//LCB902,录入时间 
			pstmt.setString(17, erpUserId);//LCB903,更改者编号
			pstmt.setString(18, dateTime_str);//LCB904,更改时间
			pstmt.setInt(19, 1);//LCA905, 更新标记
			pstmt.setString(20, doc.getBody_baco().get(i).getLot_id());//LCB960,批号
			pstmt.execute();
			pstmt.close();
		}
		
		return new_doc_id;
	}
	
	public static String addJsklha(TRAN_DOC doc, String erpUserId,Connection conn) throws Exception
	{
		//1、获取单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_str = sdf.format(new Date());
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateTime_str = sdf2.format(new Date());
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT MAXNO = MAX(LHA001) FROM JSKLHA WHERE LHA001 LIKE ?");
		pstmt.setString(1, date_str+"%");
		ResultSet rs = pstmt.executeQuery();
		String maxno = null;
		if(rs.next()){
			maxno = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		
		maxno=maxno==null?"0":maxno;
		String doc_seqno = maxno.replaceFirst(date_str, ""); 
		doc_seqno = Long.parseLong(doc_seqno)+1+"";
		while(doc_seqno.length()<6){
			doc_seqno="0"+doc_seqno;
		}
		String new_doc_id = date_str+doc_seqno;
		
		//2、获取额外信息		
		String usr_dep_id="";
		
		pstmt = conn.prepareStatement("SELECT DBA005 FROM TPADBA WHERE DBA001=?");
		pstmt.setString(1, erpUserId);
		rs=pstmt.executeQuery();
		if(rs.next()){
			usr_dep_id = rs.getString(1);
		}
		else
		{
			throw new Exception("未找到供应商信息！");
		}
		rs.close();
		pstmt.close();
		
		//3、插入单头
		pstmt = conn.prepareStatement("INSERT INTO JSKLHA(LHA001, LHA003, LHA004, LHA005, LHA007, LHA012, LHA013, LHA016, LHA901, LHA902, LHA903, LHA904, LHA905)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, new_doc_id);//LHA001,单号 
		pstmt.setString(2, sdf.format(new Date()));//LHA003, 日期   
		pstmt.setString(3, "I99");//LHA004,单据类别
		pstmt.setString(4, erpUserId);//LHA005,经办人 
		pstmt.setString(5, "F");//LHA007,凭证抛转否
		pstmt.setString(6, "F");//LHA012,审核码  
		pstmt.setString(7, "1");//LHA013,调整方式 
		pstmt.setString(8, usr_dep_id);//LHA016,部门
		pstmt.setString(9, erpUserId);//LHA901,录入者编号
		pstmt.setString(10, dateTime_str);//LCA902,录入时间 
		pstmt.setString(11, erpUserId);//LCA903,更改者编号
		pstmt.setString(12, dateTime_str);//LCA904,更改时间
		pstmt.setInt(13, 3);//LCA905, 更新标记  
		pstmt.execute();
		pstmt.close();
		
		/////////////////////////////////////////////////////////////////////////////////////

		//4、插入单身
		for(int i=0;i<doc.getBody_baco().size();i++)
		{	
			String itmName = "";
			String itmUnit = "";
			pstmt = conn.prepareStatement("SELECT DEA002,DEA003 FROM TPADEA WHERE DEA001=?");
			pstmt.setString(1, doc.getBody_baco().get(i).getItm_id());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				itmName = rs.getString(1);
				itmUnit = rs.getString(2);
			}
			else
			{
				rs.close();
				pstmt.close();
				throw new Exception("未找到物料信息！");
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement("INSERT INTO JSKLHB(LHB001, LHB002, LHB003, LHB004, LHB005, LHB006, LHB007, LHB008, LHB011, LHB019,LHB020,LHB021,LHB022,LHB023,LHB901,LHB902,LHB903,LHB904,LHB905)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, new_doc_id);//LHB001, 单号  
			pstmt.setString(2, "000".substring(0,3-String.valueOf(i+1).length())+String.valueOf(i+1));//LHB002,序号  
			pstmt.setString(3, doc.getBody_baco().get(i).getItm_id());//LHB003,品号  
			pstmt.setString(4, itmName);//LHB004, 品名  
			pstmt.setString(5, itmUnit);//LHB005, 单位 
			pstmt.setString(6, doc.getBody_baco().get(i).getWh_id());//LHB006, 仓库编号
			pstmt.setInt(7, 1);//LHB007
			pstmt.setBigDecimal(8, doc.getBody_baco().get(i).getTran_qty());//LHB008,数量
			pstmt.setString(9, "F");//LHB011,审核码  
			pstmt.setString(10, "********************");//LHB019,批号
			pstmt.setBigDecimal(11, new BigDecimal(1));//LHB020,换算分子
			pstmt.setInt(12, 1);//LHB021,换算分母  
			pstmt.setString(13, itmUnit);//LHB022,   辅助单位 
			pstmt.setBigDecimal(14, doc.getBody_baco().get(i).getTran_qty());//LHB023,辅助数量 
			pstmt.setString(15, erpUserId);//LCB901,录入者编号
			pstmt.setString(16, dateTime_str);//LCB902,录入时间 
			pstmt.setString(17, erpUserId);//LCB903,更改者编号
			pstmt.setString(18, dateTime_str);//LCB904,更改时间
			pstmt.setInt(19, 1);//LCA905, 更新标记
			pstmt.execute();
			pstmt.close();
		}
		
		return new_doc_id;
	}
	
	public static String addJsklia(TRAN_DOC doc, String erpUserId,Connection conn) throws Exception
	{
		//1、获取单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_str = sdf.format(new Date());
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateTime_str = sdf2.format(new Date());
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT MAXNO = MAX(LIA001) FROM JSKLIA WHERE LIA001 LIKE ?");
		pstmt.setString(1, date_str+"%");
		ResultSet rs = pstmt.executeQuery();
		String maxno = null;
		if(rs.next()){
			maxno = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		
		maxno=maxno==null?"0":maxno;
		String doc_seqno = maxno.replaceFirst(date_str, ""); 
		doc_seqno = Long.parseLong(doc_seqno)+1+"";
		while(doc_seqno.length()<6){
			doc_seqno="0"+doc_seqno;
		}
		String new_doc_id = date_str+doc_seqno;
		
		//2、获取额外信息		
		String usr_dep_id="";
		
		pstmt = conn.prepareStatement("SELECT DBA005 FROM TPADBA WHERE DBA001=?");
		pstmt.setString(1, erpUserId);
		rs=pstmt.executeQuery();
		if(rs.next()){
			usr_dep_id = rs.getString(1);
		}
		else
		{
			throw new Exception("未找到供应商信息！");
		}
		rs.close();
		pstmt.close();
		
		//3、插入单头
		pstmt = conn.prepareStatement("INSERT INTO JSKLIA(LIA001, LIA003, LIA004, LIA005, LIA007, LIA012, LIA013, LIA016, LIA901, LIA902, LIA903, LIA904, LIA905)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, new_doc_id);//LIA001,单号 
		pstmt.setString(2, sdf.format(new Date()));//LIA003, 日期   
		pstmt.setString(3, "098");//LIA004,单据类别
		pstmt.setString(4, erpUserId);//LIA005,经办人 
		pstmt.setString(5, "F");//LIA007,凭证抛转否
		pstmt.setString(6, "F");//LIA012,审核码  
		pstmt.setString(7, "1");//LIA013,调整方式 
		pstmt.setString(8, usr_dep_id);//LIA016,部门
		pstmt.setString(9, erpUserId);//LIA901,录入者编号
		pstmt.setString(10, dateTime_str);//LIA902,录入时间 
		pstmt.setString(11, erpUserId);//LIA903,更改者编号
		pstmt.setString(12, dateTime_str);//LIA904,更改时间
		pstmt.setInt(13, 3);//LIA905, 更新标记  
		pstmt.execute();
		pstmt.close();
		
		/////////////////////////////////////////////////////////////////////////////////////

		//4、插入单身
		for(int i=0;i<doc.getBody_baco().size();i++)
		{	
			String itmName = "";
			String itmUnit = "";
			pstmt = conn.prepareStatement("SELECT DEA002,DEA003 FROM TPADEA WHERE DEA001=?");
			pstmt.setString(1, doc.getBody_baco().get(i).getItm_id());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				itmName = rs.getString(1);
				itmUnit = rs.getString(2);
			}
			else
			{
				rs.close();
				pstmt.close();
				throw new Exception("未找到物料信息！");
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement("INSERT INTO JSKLIB(LIB001, LIB002, LIB003, LIB004, LIB005, LIB006, LIB007, LIB008, LIB011, LIB019,LIB020,LIB021,LIB022,LIB023,LIB901,LIB902,LIB903,LIB904,LIB905)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, new_doc_id);//LIB001, 单号  
			pstmt.setString(2, "000".substring(0,3-String.valueOf(i+1).length())+String.valueOf(i+1));//LIB002,序号  
			pstmt.setString(3, doc.getBody_baco().get(i).getItm_id());//LIB003,品号  
			pstmt.setString(4, itmName);//LIB004, 品名  
			pstmt.setString(5, itmUnit);//LIB005, 单位 
			pstmt.setString(6, doc.getBody_baco().get(i).getWh_id());//LIB006, 仓库编号
			pstmt.setInt(7, -1);//LIB007
			pstmt.setBigDecimal(8, doc.getBody_baco().get(i).getTran_qty());//LIB008,数量
			pstmt.setString(9, "F");//LIB011,审核码  
			pstmt.setString(10, "********************");//LIB019,批号
			pstmt.setBigDecimal(11, new BigDecimal(1));//LIB020,换算分子
			pstmt.setInt(12, 1);//LIB021,换算分母  
			pstmt.setString(13, itmUnit);//LIB022,   辅助单位 
			pstmt.setBigDecimal(14, doc.getBody_baco().get(i).getTran_qty());//LIB023,辅助数量 
			pstmt.setString(15, erpUserId);//LIB901,录入者编号
			pstmt.setString(16, dateTime_str);//LIB902,录入时间 
			pstmt.setString(17, erpUserId);//LIB903,更改者编号
			pstmt.setString(18, dateTime_str);//LIB904,更改时间
			pstmt.setInt(19, 1);//LIB905, 更新标记
			pstmt.execute();
			pstmt.close();
		}
		
		return new_doc_id;
	}
	
	/*
	 * docType=93 转移单
	 * docType=94 工艺入库
	 * */
	public static String addSgmrda(ERP_RDA_DOC doc, String erpUserId, String docType,Connection conn) throws Exception
	{
		//1、获取单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_str = sdf.format(new Date());
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateTime_str = sdf2.format(new Date());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HHmm");
		
		
		
		//2、获取额外信息		
		List<String> outPosInfo = new ArrayList<String>();
		List<String> inPosInfo = new ArrayList<String>();
		String usr_dep_id="";
		String wh_name = "";

		PreparedStatement pstmt = conn.prepareStatement("SELECT QBA002,QBA006,QBA007 FROM SGMQBA WHERE QBA001=?");
		pstmt.setString(1, doc.getRac_id());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			outPosInfo.add(rs.getString(1));//工艺名称
			outPosInfo.add(rs.getString(2));//主生产者
			outPosInfo.add(rs.getString(3));//主生产者名称
		}
		else
		{
			throw new Exception("未找到工艺信息！");
		}
		rs.close();
		pstmt.close();
		
		if(docType.equals("93")){
			pstmt = conn.prepareStatement("SELECT QBA002,QBA006,QBA007 FROM SGMQBA WHERE QBA001=?");
			pstmt.setString(1, doc.getNext_rac_id());
			rs=pstmt.executeQuery();
			if(rs.next()){
				inPosInfo.add(rs.getString(1));//工艺名称
				inPosInfo.add(rs.getString(2));//主生产者
				inPosInfo.add(rs.getString(3));//主生产者名称
				rs.close();
				pstmt.close();
			}
			else
			{
				rs.close();
				pstmt.close();
				throw new Exception("未找到工艺信息！");
			}
		}
		else if(docType.equals("94"))
		{
			PreparedStatement pstmt94 = conn.prepareStatement("SELECT DDA003 FROM TPADDA WHERE DDA001=?");
			pstmt94.setString(1, doc.getWh_id());
			ResultSet rs94=pstmt94.executeQuery();
			if(rs94.next()){
				wh_name = rs94.getString(1);
				rs94.close();
				pstmt94.close();
			}
			else
			{
				rs94.close();
				pstmt94.close();
				throw new Exception("未找到仓库信息！");
			}
		}
		else{
			throw new Exception("不支持该交易！");
		}
		
		pstmt = conn.prepareStatement("SELECT DBA005 FROM TPADBA WHERE DBA001=?");
		pstmt.setString(1, erpUserId);
		rs=pstmt.executeQuery();
		if(rs.next()){
			usr_dep_id = rs.getString(1);
		}
		else
		{
			throw new Exception("未找到供应商信息！");
		}
		rs.close();
		pstmt.close();
		
		//获取指标
		BigDecimal targetQty = BigDecimal.ZERO;
		pstmt = conn.prepareStatement("SELECT ISNULL(QBZ005,0) FROM SGMQBZ WHERE QBZ001=? AND QBZ002=? AND QBZ003=?");
		pstmt.setString(1, doc.getItm_id());
		pstmt.setString(2, doc.getRac_seqno());
		pstmt.setString(3, doc.getWs_id());
		rs=pstmt.executeQuery();
		if(rs.next()){
			targetQty = rs.getBigDecimal(1);
		}
		rs.close();
		pstmt.close();
		
		////////////////////
		
		pstmt = conn.prepareStatement("SELECT MAXNO = MAX(RDA002) FROM SGMRDA WHERE RDA002 LIKE ?");
		pstmt.setString(1, date_str+"%");
		rs = pstmt.executeQuery();
		String maxno = null;
		if(rs.next()){
			maxno = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		
		maxno=maxno==null?"0":maxno;
		String doc_seqno = maxno.replaceFirst(date_str, ""); 
		doc_seqno = Long.parseLong(doc_seqno)+1+"";
		while(doc_seqno.length()<6){
			doc_seqno="0"+doc_seqno;
		}
		String new_doc_id = date_str+doc_seqno;
		
		//3、插入单头
		pstmt = conn.prepareStatement("INSERT INTO SGMRDA(RDA001, RDA002, RDA003, RDA005, RDA006, RDA007, RDA008, RDA009, RDA010,RDA011,RDA013,RDA015, RDA901, RDA902, RDA903, RDA904, RDA905,RDA960,RDA961,RDA962)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, docType);//RDA001,移转类型
		pstmt.setString(2, new_doc_id);//RDA002 , 移转单号
		pstmt.setString(3, sdf.format(new Date()));//RDA003 ,单据日期
		pstmt.setString(4, "0");//RDA005,移出类别
		pstmt.setString(5, outPosInfo.get(1));//RDA006,移出地
		pstmt.setString(6, outPosInfo.get(2));//RDA007,移出地名称
		if(docType.equals("93")){
			pstmt.setString(7, "0");//RDA008,移入类别 
			pstmt.setString(8, inPosInfo.get(1));//RDA009,移入地 
			pstmt.setString(9, inPosInfo.get(2));//RDA0010,移入地名称		
		}
		else if(docType.equals("94")){
			pstmt.setString(7, "2");//RDA008,移入类别 
			pstmt.setString(8, doc.getWh_id());//RDA009,移入地 
			pstmt.setString(9, wh_name);//RDA0010,移入地名称		
		}
		else{
			throw new Exception("不支持该交易！");
		}

		pstmt.setString(10, erpUserId);//RDA011,经办人
		pstmt.setString(11, "F");//RDA013,审核码
		pstmt.setString(12, usr_dep_id);//RDA015,部门
		pstmt.setString(13, erpUserId);//RDA901,录入者编号
		pstmt.setString(14, dateTime_str);//RDA902,录入时间 
		pstmt.setString(15, erpUserId);//RDA903,更改者编号
		pstmt.setString(16, dateTime_str);//RDA904,更改时间
		pstmt.setInt(17, 3);//RDA905, 更新标记  
		pstmt.setString(18, doc.getSws_id());//RDA960, 流程票号
		pstmt.setString(19, doc.getWo_id());//RDA961, 工单号
		pstmt.setString(20, doc.getRp_wo_no());//RDA962, 设备号
		pstmt.execute();
		pstmt.close();
		
		/////////////////////////////////////////////////////////////////////////////////////

		//4、插入单身
		pstmt = conn.prepareStatement("INSERT INTO SGMRDB(RDB001, RDB002, RDB003, RDB004, RDB005, RDB006, RDB007, RDB008, RDB009, RDB010,RDB011,RDB012, RDB013, RDB018, RDB019,RDB022, RDB901,RDB902,RDB903,RDB904,RDB905,RDB960,RDB963,RDB981,RDB982,RDB964)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, docType);//RDB001, 移转类型 
		pstmt.setString(2, new_doc_id);//RDB002 , 移转单号     
		pstmt.setString(3, "001");//RDB003 , 序号 
		pstmt.setString(4, doc.getWo_id());//RDB004 ,工单单号
		pstmt.setString(5, doc.getItm_id());//RDB005, 产品品号 
		pstmt.setString(6, doc.getRac_seqno());//RDB006 ,移出工序
		pstmt.setString(7, doc.getRac_id());//RDB007,移出工艺
		if(docType.equals("93")){
			pstmt.setString(8, doc.getNext_rac_seqno());//RDB008 ,移入工序 
			pstmt.setString(9, doc.getNext_rac_id());//RDB009,移入工艺  
		}
		else if(docType.equals("94")){
			pstmt.setString(8, "");//RDB008 ,移入工序 
			pstmt.setString(9, "");//RDB009,移入工艺  
		}
		else
		{
			throw new Exception("不支持该交易！");
		}
		pstmt.setString(10, "0");//RDB010,移转类型  
		pstmt.setBigDecimal(11, doc.getFinish_qty());//RDB011 ,移转数量
		pstmt.setString(12, doc.getLot_id());//RDB012,备注 
		pstmt.setString(13, "F");//RDB013,  审核码 
		pstmt.setString(14, sdf3.format(new Date(doc.getBg_dt())));//RDB018,   开始生产时间 hhMM 
		pstmt.setString(15, sdf3.format(new Date(doc.getRp_dt())));//RDB019,   结束生产时间 hhMM 
		pstmt.setBigDecimal(16, doc.getScrap_qty());//RDB022,    报废数量  
		
		pstmt.setString(17, erpUserId);//LIB901,录入者编号
		pstmt.setString(18, dateTime_str);//LIB902,录入时间 
		pstmt.setString(19, erpUserId);//LIB903,更改者编号
		pstmt.setString(20, dateTime_str);//LIB904,更改时间
		pstmt.setInt(21, 1);//RDB905, 更新标记
		pstmt.setString(22, doc.getWs_id());//RDB960, 机器代码
		String workers = "";
		for(int i=0;i<doc.getEmpIds().size();i++){
			if(i==doc.getEmpIds().size()-1){
				workers = workers+doc.getEmpIds().get(i);
				break;
			}
			workers = workers+doc.getEmpIds().get(i)+"/";
		}
		pstmt.setString(23, workers);//RDB963, 移出地生产人员
		pstmt.setInt(24, doc.getEmpIds().size());//RDB981, 员工人数
		pstmt.setBigDecimal(25, targetQty);//RDB982, 指标
		pstmt.setString(26, doc.getWo_id());//RDB964 ,工单单号
		pstmt.execute();
		pstmt.close();
		
		return new_doc_id;
	}
	
	public static List<ITM_MAIN_VIEW> getJskkea(String docId,Connection conn) throws SQLException
	{
		List<ITM_MAIN_VIEW> result = new ArrayList<ITM_MAIN_VIEW>();
		
		PreparedStatement pstmt=null;

		pstmt=conn.prepareStatement("SELECT KEB002,KEB003,KEB004,KEB005,SUM(KEB007) FROM JSKKEB WHERE KEB021='F' AND KEB001=? GROUP BY KEB002,KEB003,KEB004,KEB005 ORDER BY KEB002");
		pstmt.setString(1, docId);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			ITM_MAIN_VIEW view = new ITM_MAIN_VIEW();
			
			view.setItm_main_id(rs.getString(2));
			view.setItm_name(rs.getString(3));
			view.setItm_qty(rs.getBigDecimal(5));
			view.setItm_unit(rs.getString(4));
			
			result.add(view);
		}

		rs.close();
		pstmt.close();
		
		return result;
	}
	
	public static boolean SgmRdaIsExist(String docId,String docType,Connection conn) throws SQLException{
		PreparedStatement pstmt=null;
		
		if(docType.equals("ZYD")){
			pstmt = conn.prepareStatement("SELECT RDA002 FROM SGMRDA WHERE RDA002=? AND RDA001='93'");
		}
		else{
			pstmt = conn.prepareStatement("SELECT RDA002 FROM SGMRDA WHERE RDA002=? AND RDA001='94'");
		}
		pstmt.setString(1, docId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return true;
		}
		return false;
	}
	
	public static BigDecimal getItmTarget(String itmId,String racId,String wsId,Connection conn) throws SQLException
	{
		BigDecimal result = BigDecimal.ZERO;
		PreparedStatement pstmt=null;

		pstmt=conn.prepareStatement(" SELECT QBZ005 FROM SGMQBZ WHERE QBZ001=? AND QBZ002=? AND QBZ003=?");
		pstmt.setString(1, itmId);
		pstmt.setString(2, racId);
		pstmt.setString(3, wsId);
		
		//System.out.println(" SELECT QBZ005 FROM SGMQBZ WHERE QBZ001='"+itmId+"' AND QBZ002='"+racId+"' AND QBZ003='"+wsId+"'");
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			result = rs.getBigDecimal(1);
		}

		rs.close();
		pstmt.close();
		
		return result;
	}
	
	public static List<DSCHDA_VIEW> getDcsHdaList(String docId,Connection conn) throws SQLException
	{
		List<DSCHDA_VIEW> result = new ArrayList<DSCHDA_VIEW>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT TOP 500 T1.HDA001,T3.DGA002,T3.DGA003,T1.HDA003,T1.HDA012 FROM DCSHDA T1,DCSHDB T2,TPADGA T3 ");
		sb.append(" WHERE T1.HDA021='T' AND T2.HDB001=T1.HDA001 AND T2.HDB012='N' AND T1.HDA004=T3.DGA001 AND T3.DGA001='388'");
		if(!StringUtils.isNullOrEmpty(docId)){
			sb.append(" AND T1.HDA001 LIKE ? ");
		}
		sb.append(" ORDER BY  T1.HDA001 DESC");
		PreparedStatement pstmt=null;

		pstmt=conn.prepareStatement(sb.toString());
		if(!StringUtils.isNullOrEmpty(docId)){
			pstmt.setString(1, docId+"%");
		}
		
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			DSCHDA_VIEW view = new DSCHDA_VIEW();
			
			view.setHDA001(rs.getString(1));
			view.setDGA003(rs.getString(3));
			view.setHDA003(rs.getString(4));
			view.setHDA012(rs.getString(5));
			
			result.add(view);
		}

		rs.close();
		pstmt.close();
		
		return result;
	}
	
	public static List<JSKJFA_VIEW> getJskJfaList(String docId,Connection conn) throws SQLException
	{
		List<JSKJFA_VIEW> result = new ArrayList<JSKJFA_VIEW>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TOP 500 T1.JFA001,T3.DGA002,T3.DGA003,T1.JFA003,T1.JFA025 FROM JSKJFA T1,TPADGA T3 ");
		sb.append(" WHERE T1.JFA026='T' AND T1.JFA004=T3.DGA001 AND T3.DGA001='388'");
		if(!StringUtils.isNullOrEmpty(docId)){
			sb.append(" AND T1.JFA001 LIKE ? ");
		}
		sb.append(" ORDER BY  T1.JFA001 DESC");
		PreparedStatement pstmt=null;

		pstmt=conn.prepareStatement(sb.toString());
		if(!StringUtils.isNullOrEmpty(docId)){
			pstmt.setString(1, docId+"%");
		}
		
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			JSKJFA_VIEW view = new JSKJFA_VIEW();
			
			view.setJFA001(rs.getString(1));
			view.setDGA003(rs.getString(3));
			view.setJFA003(rs.getString(4));
			view.setJFA025(rs.getString(5));
			
			result.add(view);
		}

		rs.close();
		pstmt.close();
		
		return result;
	}
	
	public static List<DSCHDB_VIEW> getDcsHdaPrintList(String docIds,Connection conn) throws SQLException
	{
		List<DSCHDB_VIEW> result = new ArrayList<DSCHDB_VIEW>();
		System.out.println("docIds:"+docIds);
		if(StringUtils.isNullOrEmpty("docIds")){
			return result;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT HDB001,HDB002,HDB003,HDB004,HDB005,HDB006,ISNULL(DEA031,'') FROM DCSHDB,TPADEA WHERE HDB001 IN(?) AND HDB012='N' AND HDB019='T' AND HDB003 LIKE '388%' AND HDB003=DEA001 ORDER BY HDB001 DESC,HDB002");
		PreparedStatement pstmt=conn.prepareStatement(sb.toString());
		pstmt.setString(1, docIds);
		
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			DSCHDB_VIEW view = new DSCHDB_VIEW();
			
			String itmDesc = rs.getString(7);
			System.out.println("itmDesc:"+itmDesc);
			BigDecimal purPkgQty = BigDecimal.ZERO;
			if(itmDesc.length()>=6){
				
				if(itmDesc.substring(0,5).equals("采购装箱数")){
					itmDesc.replace("采购装箱数", "");
					try{
						purPkgQty = new BigDecimal(itmDesc);
					}
					catch(Exception ex){
						System.out.println("ex:"+ex.getMessage());
					}
				}
				else{
					continue;
				}
			}
			else{
				continue;
			}
			
			if(purPkgQty.compareTo(BigDecimal.ZERO)>0){
				BigDecimal purQty = rs.getBigDecimal(6);
				int pkgNum = purQty.divide(purPkgQty, 0, BigDecimal.ROUND_CEILING).intValue();
				
				for(int i=0;i<pkgNum;i++)
				{
					BigDecimal subQty = new BigDecimal(0);
					if(i==pkgNum-1)
					{
						subQty = purQty;
					}
					else
					{
						subQty = purPkgQty;
						purQty = purQty.subtract(purPkgQty);
					}
					
					view.setHdb001(rs.getString(1));
					view.setHdb002(rs.getString(2));
					view.setHdb003(rs.getString(3));
					view.setHdb004(rs.getString(4));
					view.setHdb005(rs.getString(5));
					view.setHdb006(rs.getBigDecimal(6));
					view.setPkgqty(subQty);
					
					result.add(view);
				}
			}
		}

		rs.close();
		pstmt.close();
		
		return result;
	}
	
	public static WO_DOC_VIEW getSgmRaa(String woId,Connection erpConn) throws Exception{
		WO_DOC_VIEW result = new WO_DOC_VIEW();
		PreparedStatement ps = erpConn.prepareStatement("SELECT RAA001,RAA015,RAA018 FROM SGMRAA WHERE RAA001=?");
		ps.setString(1, woId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			result.setWo_doc_id(rs.getString(1));
			result.setItm_id(rs.getString(2));
			result.setItm_qty(rs.getBigDecimal(3));
		}
		else
		{
			rs.close();
			ps.close();
			throw new Exception("未找到工单："+woId);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
	public static List<String> getSgmQbz(String itmId,String qbcSeqno,Connection erpConn) throws Exception{
		List<String> result = new ArrayList<String>();
		PreparedStatement ps = erpConn.prepareStatement("SELECT QBZ003,QBZ004 ,ISNULL(QBZ005,0),ISNULL(QBA980,0) FROM SGMQBZ,SGMQBA WHERE QBZ001=? AND QBZ002=? AND QBZ003=QBA001 ");
		ps.setString(1, itmId);
		ps.setString(2, qbcSeqno);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(rs.getString(1)+","+rs.getString(2)+","+ rs.getBigDecimal(3).toString()+","+String.valueOf(rs.getInt(4)));
		}
		rs.close();
		ps.close();
		return result;
	}
	
	
	//ERP 工单是否未完工
	public static boolean isWoDocNotFinished(String docId,Connection conn) throws Exception{
		boolean result=false;
		
		String sql="SELECT RAA001 FROM SGMRAA WHERE RAA020='N' AND RAA001=? ";				
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, docId);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			result=true;			
		} else{
			rs.close();
			ps.close();
			throw new Exception("ERP中未找到工单："+docId);
		}
		rs.close();
		ps.close();
		
		return result;
	}
	
}

