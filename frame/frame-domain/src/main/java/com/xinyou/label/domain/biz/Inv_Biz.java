package com.xinyou.label.domain.biz;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.label.domain.entities.CTN_MAIN;
import com.xinyou.label.domain.entities.STK_MAIN;
import com.xinyou.label.domain.entities.TRAN_BACO;
import com.xinyou.label.domain.entities.TRAN_BASE_DOC;
import com.xinyou.label.domain.entities.TRAN_ITM;
import com.xinyou.label.domain.entities.TRAN_MAIN;
import com.xinyou.label.domain.models.PKG_DOC;
import com.xinyou.label.domain.models.TRAN_DOC;
import com.xinyou.label.domain.viewes.CTN_MAIN_VIEW;
import com.xinyou.label.domain.viewes.ITM_MAIN_VIEW;
import com.xinyou.label.domain.viewes.PUR_ITM_VIEW;
import com.xinyou.label.domain.viewes.PUR_PKG_VIEW;
import com.xinyou.label.domain.viewes.RBA_DOC_VIEW;
import com.xinyou.label.domain.viewes.RBA_ITM_VIEW;
import com.xinyou.label.domain.viewes.STK_ITM_VIEW;
import com.xinyou.label.domain.entities.STK_ITM_WKSITE;
import com.xinyou.label.domain.viewes.WO_DOC_VIEW;
import com.mysql.jdbc.StringUtils;

public class Inv_Biz {
	
	
	
	/** 获取采购单单体信息 
	 *  @param purId  -- 采购单ID PUR_DOC_ID 字段
	 *  @param conn   -- 数据库链接
	 * */
	public static List<PUR_ITM_VIEW> getPurById(String purId, Connection conn) throws Exception
	{
		List<PUR_ITM_VIEW> result = new ArrayList<PUR_ITM_VIEW>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT T1.PUR_ITM_GUID,T1.PUR_DOC_ID,T1.PUR_ITM_SEQNO,T1.ITM_MAIN_ID,T2.ITM_NAME,T2.ITM_UNIT,T1.ITM_QTY,T1.ITM_DELIVERY_QTY FROM PUR_ITM T1,ITM_MAIN T2 WHERE T2.ITM_MAIN_ID=T1.ITM_MAIN_ID AND T1.PUR_DOC_ID=? ORDER BY T1.PUR_ITM_SEQNO");
			pstmt.setString(1, purId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PUR_ITM_VIEW itm = new PUR_ITM_VIEW();
				
				itm.setPur_itm_guid(rs.getString(1));
				itm.setPur_doc_id(rs.getString(2));
				itm.setPur_itm_seqno(rs.getString(3));
				itm.setItm_main_id(rs.getString(4));
				itm.setItm_name(rs.getString(5));
				itm.setItm_unit(rs.getString(6));
				itm.setItm_qty(rs.getBigDecimal(7));
				itm.setItm_delivery_qty(rs.getBigDecimal(8));
				
				result.add(itm);
			}
			rs.close();
			pstmt.close();
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return result;
	}
	
	//周转箱必须放在库位上，可以更新条码和位置
	public static void purInCtnBox(String boxBaco,BigDecimal inValue, String operator, String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			//更新数量
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=ISNULL(ITM_QTY,0)+? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, inValue);
			pstmt.setString(4, boxBaco);
			pstmt.execute();
			pstmt.close();
				
			//更新状态
			Common_Biz.updCtnStatus(Common_Biz.getCtnByBaco(boxBaco, conn).getCtn_main_guid(), conn);

		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	//更新原始单证信息
	/** 
	 * 更新 PUR_ITM 表中的ITM_DELIVERY_QTY .注意是累加，不是替换
	 * @param purDocId     单头id
	 * @param purItemId    单体id
	 * @param intValue 累加到ITM_DELIVERY_QTY 字段上的数值
	 * */
	public static void purInPurDoc(String purDocId,String purItmId,String purItmSeqno, BigDecimal inValue, Connection conn) throws Exception {
	PreparedStatement pstmt = null;
	
	try {
		pstmt=conn.prepareStatement("UPDATE PUR_ITM SET ITM_DELIVERY_QTY=ISNULL(ITM_DELIVERY_QTY,0)+? WHERE PUR_DOC_ID=? AND ITM_MAIN_ID=? AND PUR_ITM_SEQNO=?");
			pstmt.setBigDecimal(1, inValue);
			pstmt.setString(2, purDocId);
			pstmt.setString(3, purItmId);
			pstmt.setString(4, purItmSeqno);
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	/**
	 *  获取领料单单体上的信息。已经领过料的领料单，不能查询明细
	 *  @param rbaId 领料单 RBA_DOC_ID字段的值
	 * */
	public static List<RBA_ITM_VIEW> getRbaById(String rbaId, Connection conn) throws Exception
	{
		List<RBA_ITM_VIEW> result = new ArrayList<RBA_ITM_VIEW>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT LABEL_STATUS FROM RBA_DOC WHERE RBA_DOC_ID=?");
			pstmt.setString(1, rbaId);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(rs.getInt(1)==1)
				{
					throw new Exception("该领料单已领料过！");
				}
			}
			rs.close();
			pstmt.close();
					
//			pstmt = conn.prepareStatement("SELECT T1.RBA_ITM_GUID,T1.RBA_DOC_ID,T1.RBA_ITM_SEQNO,T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT,T1.ITM_QTY FROM RBA_ITM T1,ITM_MAIN T2 WHERE T2.ITM_MAIN_ID=T1.ITM_ID AND T1.RBA_DOC_ID=?  AND T1.INV_ID='08' ORDER BY T1.RBA_ITM_SEQNO");
			pstmt = conn.prepareStatement("SELECT T1.RBA_ITM_GUID,T1.RBA_DOC_ID,T1.RBA_ITM_SEQNO,T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT,T1.ITM_QTY FROM RBA_ITM T1,ITM_MAIN T2 WHERE T2.ITM_MAIN_ID=T1.ITM_ID AND T1.RBA_DOC_ID=?  ORDER BY T1.RBA_ITM_SEQNO");
			pstmt.setString(1, rbaId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RBA_ITM_VIEW itm = new RBA_ITM_VIEW();
				
				itm.setRba_itm_guid(rs.getString(1));
				itm.setRba_doc_id(rs.getString(2));
				itm.setRba_itm_seqno(rs.getString(3));
				itm.setItm_main_id(rs.getString(4));
				itm.setItm_name(rs.getString(5));
				itm.setItm_unit(rs.getString(6));
				itm.setItm_qty(rs.getBigDecimal(7));
				
				result.add(itm);
			}
			rs.close();
			pstmt.close();
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return result;
	}
	
	/** 领料出库
	 *  从具体的存放位置，取出一定数量的料
	 *  @param  boxBaco   容器上的条码
	 *  @param  intValue  要去多少数量的货
	 * */
	public static void rbaOutCtnBox(String boxBaco,BigDecimal inValue, String operator, String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		BigDecimal itmQty = BigDecimal.ZERO;
		
		try {
			
			//判断该容器内的货物的量是否满足
			pstmt=conn.prepareStatement("SELECT ISNULL(ITM_QTY,0) FROM CTN_MAIN  WHERE CTN_BACO=?");
			pstmt.setString(1, boxBaco);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				itmQty = rs.getBigDecimal(1);
				if(itmQty.subtract(inValue).doubleValue()<0)
				{
					rs.close();
					rs=null;
					pstmt.close();
					pstmt=null;
					throw new Exception("剩余量不足！");
				}
			}
			rs.close();
			rs=null;
			pstmt.close();
			
			//如果满足，那么更新改容器的量 （原有的量-要取得量
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=ITM_QTY-? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, inValue);
			pstmt.setString(4, boxBaco);
			pstmt.execute();
			pstmt.close();
			
			
			if(itmQty.compareTo(inValue)==0)
			{
				
				CTN_MAIN_VIEW ctn=Common_Biz.getCtnByBaco(boxBaco, conn);
				if(ctn.getCtn_type()==12||ctn.getCtn_type()==13)
				{  
					//如果是生产周转箱或者物流周转箱类型的容器 
					//如果容器现有量和要取的量完全相等。那么清空这笔记录：仓库，库区、货架、库位，托盘、包装信息
					pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?, CTN_STATUS=0,WH_GUID='',WH_AREA_GUID='',WH_SHELF_GUID='',WH_LOC_GUID='',WH_PLT_GUID='',WH_PACKAGE_GUID='',PARENT_CTN_GUID='' WHERE CTN_BACO=?");
					pstmt.setLong(1, new Date().getTime());
					pstmt.setString(2, operator);
					pstmt.setString(3, boxBaco);
					pstmt.execute();
					pstmt.close();
				}
			}
		
			//
			Common_Biz.updCtnStatus(Common_Biz.getCtnByBaco(boxBaco, conn).getCtn_main_guid(), conn);

		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	//更新原始单证信息
	/**
	 *  更新领料单领料状态为已经领料
	 *  @param rbaDocId 领料单的单头ID
	 * */
	public static void rbaOutDoc(String rbaDocId, Connection conn) throws Exception {
	PreparedStatement pstmt = null;
	
	try {
		pstmt=conn.prepareStatement("UPDATE RBA_DOC SET LABEL_STATUS=1 WHERE RBA_DOC_ID=?");
			pstmt.setString(1, rbaDocId);
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	
	/**
	 * 其他入库 （只有虚拟周转箱可以进行其他入库的操作）
	 * @param boxBarco 容器条码
	 * @param intValue 要入库的数量 
	 * */
	public static void otherInCtnBox(String boxBaco,BigDecimal inValue, String operator, String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
		  
			//检查实物周转箱的条码是否有效
			pstmt=conn.prepareStatement("SELECT COUNT(*) FROM CTN_MAIN  WHERE CTN_BACO=? AND CTN_TYPE=11");
			pstmt.setString(1, boxBaco);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(rs.getInt(1)!=1)
				{
					throw new Exception("条码信息不存在！");
				}
			}
			rs.close();
			rs=null;
			pstmt.close();
			
			//更新数量 
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=ISNULL(ITM_QTY,0)+? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, inValue);
			pstmt.setString(4, boxBaco);
			pstmt.execute();
			pstmt.close();
				
			Common_Biz.updCtnStatus(Common_Biz.getCtnByBaco(boxBaco, conn).getCtn_main_guid(), conn);

		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	/**
	 * 其他出库 （只有虚拟周转箱可以进行其他入库的操作）
	 * 
	 * */
	public static void otherOutCtnBox(String boxBaco,BigDecimal outValue, String operator, String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			
			//检查条码和数量是否足够
			pstmt=conn.prepareStatement("SELECT ISNULL(ITM_QTY,0) FROM CTN_MAIN  WHERE CTN_BACO=? AND CTN_TYPE=11");
			pstmt.setString(1, boxBaco);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(rs.getBigDecimal(1).compareTo(outValue)<0)
				{
					throw new Exception(boxBaco+" 剩余数量不够！");
				}
			}
			else
			{
				throw new Exception(boxBaco+" 条码未找到或类型不正确！");
			}
			rs.close();
			rs=null;
			pstmt.close();
			
			//数量减少
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=ISNULL(ITM_QTY,0)-? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, outValue);
			pstmt.setString(4, boxBaco);
			pstmt.execute();
			pstmt.close();
			
			//更新状态
			Common_Biz.updCtnStatus(Common_Biz.getCtnByBaco(boxBaco, conn).getCtn_main_guid(), conn);

		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	/**
	 * 库存移位  ， 物料从一个虚拟周转箱转移的另一个虚拟周转箱
	 * @param  fromBoxBaco 旧位置条码
	 * @param  toBoxBaco   新位置条码
	 * @param  moveValue   移动数量
	 * 
	 * */
	public static void moveCtnBox(String fromBoxBaco,String toBoxBaco,BigDecimal moveValue, String operator, String client, String data_ver, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			
			//检查要移动的数量是否足够
			pstmt=conn.prepareStatement("SELECT ISNULL(ITM_QTY,0) FROM CTN_MAIN  WHERE CTN_BACO=? AND CTN_TYPE=11");
			pstmt.setString(1, fromBoxBaco);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(rs.getBigDecimal(1).compareTo(moveValue)<0)
				{
					throw new Exception(fromBoxBaco+" 剩余数量不够！");
				}
			}
			else
			{
				throw new Exception(fromBoxBaco+" 条码未找到或类型不正确！");
			}
			rs.close();
			rs=null;
			pstmt.close();
			
			//目标位要检查条码是否有效
			pstmt=conn.prepareStatement("SELECT ISNULL(ITM_QTY,0) FROM CTN_MAIN  WHERE CTN_BACO=? AND CTN_TYPE=11");
			pstmt.setString(1, toBoxBaco);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
			}
			else
			{
				throw new Exception(toBoxBaco+" 条码未找到或类型不正确！");
			}
			rs.close();
			rs=null;
			pstmt.close();
			
			//更新库位数量
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=ISNULL(ITM_QTY,0)-? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, moveValue);
			pstmt.setString(4, fromBoxBaco);
			pstmt.execute();
			pstmt.close();
			
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=ISNULL(ITM_QTY,0)+? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, moveValue);
			pstmt.setString(4, toBoxBaco);
			pstmt.execute();
			pstmt.close();
		    
			//更新库位信息
			Common_Biz.updCtnStatus(Common_Biz.getCtnByBaco(fromBoxBaco, conn).getCtn_main_guid(), conn);
			Common_Biz.updCtnStatus(Common_Biz.getCtnByBaco(toBoxBaco, conn).getCtn_main_guid(), conn);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}

	/**
	 *   获取存放某一物料的所有容器列表（CTN_MAIN)
	 *   @param itmId    物料ID
	 *   @param whGuid   仓库的Guid
	 * */
	public static List<CTN_MAIN_VIEW> getBoxListByItmId(String itmId,String whGuid, Connection conn) throws Exception
	{
		List<CTN_MAIN_VIEW> result = new ArrayList<CTN_MAIN_VIEW>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			/*
SELECT T1.CTN_MAIN_GUID,T1.CTN_MAIN_ID,T1.CTN_BACO,T1.ITM_ID,T1.ITM_QTY,T2.ITM_NAME,T2.ITM_UNIT,T3.CTN_MAIN_ID AS SHELF_ID,T3.CTN_BACO AS SHELF_BACO,T4.CTN_MAIN_ID AS AREA_ID,T4.CTN_BACO AS AREA_BACO 
FROM CTN_MAIN T1
JOIN ITM_MAIN T2 ON T1.ITM_ID=T2.ITM_MAIN_ID
LEFT JOIN CTN_MAIN T3 ON T3.CTN_MAIN_GUID = T1.WH_SHELF_GUID
LEFT JOIN CTN_MAIN T4 ON T4.CTN_MAIN_GUID = T1.WH_AREA_GUID
WHERE T1.CTN_TYPE=? AND T1.ITM_ID=? AND T1.WH_GUID=?
ORDER BY T4.CTN_MAIN_ID,T3.CTN_MAIN_ID,T1.CTN_BACO
			 * */
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT T1.CTN_MAIN_GUID,T1.CTN_MAIN_ID,T1.CTN_BACO,T1.ITM_ID,T1.ITM_QTY,T2.ITM_NAME,T2.ITM_UNIT,T3.CTN_MAIN_ID AS SHELF_ID,T3.CTN_BACO AS SHELF_BACO,T4.CTN_MAIN_ID AS AREA_ID,T4.CTN_BACO AS AREA_BACO,T5.CTN_MAIN_ID AS WH_ID");
			sb.append(" FROM CTN_MAIN T1");
			sb.append(" JOIN ITM_MAIN T2 ON T1.ITM_ID=T2.ITM_MAIN_ID");
			sb.append(" LEFT JOIN CTN_MAIN T3 ON T3.CTN_MAIN_GUID = T1.WH_SHELF_GUID");
			sb.append(" LEFT JOIN CTN_MAIN T4 ON T4.CTN_MAIN_GUID = T1.WH_AREA_GUID");
			sb.append(" LEFT JOIN CTN_MAIN T5 ON T5.CTN_MAIN_GUID = T1.WH_GUID");
			sb.append(" WHERE T1.CTN_TYPE=? AND T1.ITM_ID=?");
			if(!StringUtils.isNullOrEmpty(whGuid))
			{
				sb.append(" AND T1.WH_GUID=?");
			}
			sb.append(" ORDER BY T5.CTN_MAIN_ID,T4.CTN_MAIN_ID,T3.CTN_MAIN_ID,T1.CTN_BACO");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, 11);//11：虚拟周转箱  
			pstmt.setString(2, itmId);
			if(!StringUtils.isNullOrEmpty(whGuid))
			{
				pstmt.setString(3, whGuid);
			}
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CTN_MAIN_VIEW itm = new CTN_MAIN_VIEW();
				
				itm.setCtn_main_guid(rs.getString(1));
				itm.setCtn_main_id(rs.getString(2));
				itm.setCtn_baco(rs.getString(3));
				itm.setItm_id(rs.getString(4));
				itm.setItm_qty(rs.getBigDecimal(5));
				itm.setItm_name(rs.getString(6));
				itm.setItm_unit(rs.getString(7));
				itm.setWh_shelf_id(rs.getString(8));
				itm.setWh_shelf_baco(rs.getString(9));
				itm.setWh_area_id(rs.getString(10));
				itm.setWh_area_baco(rs.getString(11));
				itm.setWh_id(rs.getString(12));
				
				result.add(itm);
			}
			rs.close();
			pstmt.close();
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return result;
	}
	
	/**
	 * */
	public static void getInSemi(String ctn_baco,String locBaco,BigDecimal qty,Connection conn) throws Exception
	{
		CTN_MAIN_VIEW ctn = Common_Biz.getCtnByBaco(locBaco, conn);
		if(ctn!=null&&ctn.getCtn_main_guid().length()>0)
		{
			PreparedStatement ps = conn.prepareStatement("UPDATE CTN_MAIN SET WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,PARENT_CTN_GUID=?,ITM_QTY=? WHERE CTN_BACO=?");
			ps.setString(1, ctn.getWh_guid());
			ps.setString(2, ctn.getWh_area_guid());
			ps.setString(3, ctn.getWh_shelf_guid());
			ps.setString(4, ctn.getCtn_main_guid());
			ps.setString(5, ctn.getCtn_main_guid());
			ps.setBigDecimal(6, qty);
			ps.setString(7, ctn_baco);
			ps.execute();
			ps.close();
			
			ps = conn.prepareStatement("UPDATE SUB_WO_SUB SET SWS_STATUS=1 WHERE SUB_WO_SUB_ID=?");
			ps.setString(1, ctn_baco);
			ps.execute();
			ps.close();
		}
		else
		{
			throw new Exception("未找到库位信息！");
		}
	}
	
	/**
	 *  半成品整箱其他入库   （半成品存放在 -- 12：生产周转箱（流程票） -- 13：物流周转箱（合格证 ） 中
	 *  本质上 包locBaco关联的库位信息填写到 boxBaco 对应的 位置上去。 同时关联着两个条码
	 *  @param boxBaco 包装箱条码
	 *  @param locBaco 库位条码 
	 *  @param itmQty  入库数量
	 * */
	public static void semiBoxOtherIn(String boxBaco,BigDecimal itmQty,String locBaco, String operator, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			
			//判断条码是否这 11,12 两种类型的条码
			pstmt=conn.prepareStatement("SELECT COUNT(*) FROM CTN_MAIN  WHERE CTN_BACO=? AND CTN_TYPE IN (12,13)");
			pstmt.setString(1, boxBaco);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(rs.getInt(1)!=1)
				{
					throw new Exception("条码信息不存在！");
				}
			}
			rs.close();
			rs=null;
			pstmt.close();
			
			//检查库位条码 
			CTN_MAIN_VIEW ctnLoc = Common_Biz.getCtnByBaco(locBaco, conn);
			if(ctnLoc==null||StringUtils.isNullOrEmpty(ctnLoc.getCtn_main_guid()))
			{
				throw new Exception("未找到库位信息！");
			}
			if(ctnLoc.getCtn_type()!=6)  //ctn_type = 6 是库位条码
			{
				throw new Exception("库位条码不正确！");
			}
			
			//
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=?,WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,PARENT_CTN_GUID=? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, itmQty);
			pstmt.setString(4, ctnLoc.getWh_guid());
			pstmt.setString(5, ctnLoc.getWh_area_guid());
			pstmt.setString(6, ctnLoc.getWh_shelf_guid());
			pstmt.setString(7, ctnLoc.getCtn_main_guid()); //WH_LOC_GUID
			pstmt.setString(8, ctnLoc.getCtn_main_guid()); //PARENT_CTN_GUID
			pstmt.setString(9, boxBaco);
			pstmt.execute();
			pstmt.close();
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	/**
	 *  半成品零散其他入库   12,13 两种类型的箱子 的零散入库，仅仅把数量加入到这两种 箱子里
	 *  @param boxBaco  要入的箱子条码
	 *  @param itmQty   数量 
	 * */
	public static void semiScatteredOtherIn(String boxBaco,BigDecimal itmQty, String operator, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {			
			//12：生产周转箱（流程票） -- 13：物流周转箱（合格证 ） 中
			CTN_MAIN_VIEW ctnBox = Common_Biz.getCtnByBaco(boxBaco, conn);
			if(ctnBox==null||StringUtils.isNullOrEmpty(ctnBox.getCtn_main_guid()))
			{
				throw new Exception("未找到流程票或者合格证信息！");
			}
			if(ctnBox.getCtn_type()!=12&&ctnBox.getCtn_type()!=13)
			{
				throw new Exception("不是流程票或者合格证条码！");
			}
			if(StringUtils.isNullOrEmpty(ctnBox.getWh_loc_guid()))
			{
				throw new Exception("流程票或者合格证不在库位上！");
			}
			
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=ISNULL(ITM_QTY,0)+? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, itmQty);
			pstmt.setString(4, boxBaco);
			pstmt.execute();
			pstmt.close();
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	/**
	 *  半成品其他出。 
	 *  @param boxBaco
	 *  @param outQty
	 *  
	 * */
	public static void semiOtherOut(String boxBaco,BigDecimal outQty, String operator, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try {	
			
			//判断boxBaco是否是流程票或合格证，而且要判定已经在库位上了。
			CTN_MAIN_VIEW ctnBox = Common_Biz.getCtnByBaco(boxBaco, conn);
			if(ctnBox==null||StringUtils.isNullOrEmpty(ctnBox.getCtn_main_guid()))
			{
				throw new Exception("未找到流程票或者合格证信息！");
			}
			if(ctnBox.getCtn_type()!=12&&ctnBox.getCtn_type()!=13)
			{
				throw new Exception("不是流程票或者合格证条码！");
			}
			if(StringUtils.isNullOrEmpty(ctnBox.getWh_loc_guid()))
			{
				throw new Exception("流程票或者合格证不在库位上！");
			}
			
			//判断量是否做够
			BigDecimal itmHasQty = BigDecimal.ZERO;
			pstmt=conn.prepareStatement("SELECT ISNULL(ITM_QTY,0) FROM CTN_MAIN  WHERE CTN_BACO=?");
			pstmt.setString(1, boxBaco);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				itmHasQty = rs.getBigDecimal(1);
				if(itmHasQty.compareTo(outQty)<0)
				{
					rs.close();
					rs=null;
					pstmt.close();
					pstmt=null;
					throw new Exception("剩余量不足！");
				}
			}
			rs.close();
			rs=null;
			pstmt.close();
			
			//减少量 
			pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?,ITM_QTY=ITM_QTY-? WHERE CTN_BACO=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, operator);
			pstmt.setBigDecimal(3, outQty);
			pstmt.setString(4, boxBaco);
			pstmt.execute();
			pstmt.close();
			
			if(itmHasQty.compareTo(outQty)==0)
			{
				CTN_MAIN_VIEW ctn=Common_Biz.getCtnByBaco(boxBaco, conn);
				//如果是生产周转箱和物流周转箱 ，那么需要把库位信息移除 
				if(ctn.getCtn_type()==12||ctn.getCtn_type()==13)
				{
					pstmt=conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?, UPDATED_BY=?, CTN_STATUS=0,WH_GUID='',WH_AREA_GUID='',WH_SHELF_GUID='',WH_LOC_GUID='',WH_PLT_GUID='',WH_PACKAGE_GUID='',PARENT_CTN_GUID='' WHERE CTN_BACO=?");
					pstmt.setLong(1, new Date().getTime());
					pstmt.setString(2, operator);
					pstmt.setString(3, boxBaco);
					pstmt.execute();
					pstmt.close();
				}
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed()) pstmt.close();
		}
	}
	
	/**
	 *   添加盘点计划 
	 *   
	 * */
	public static String addTakeStockPlan(STK_MAIN stkData, Connection conn) throws Exception{
		PreparedStatement pstmtGet = conn.prepareStatement("SELECT STK_MAIN_ID FROM STK_MAIN WHERE STK_MAIN_ID=?");
		pstmtGet.setString(1, stkData.getId());
		ResultSet rs = pstmtGet.executeQuery();
		if(rs.next())
		{
			rs.close();
			pstmtGet.close();
			throw new Exception("盘点代码已经存在！");
		}
		
		PreparedStatement pstmtInsert = null;
		String headGuid = UUID.randomUUID().toString();
		pstmtInsert = conn.prepareStatement("INSERT INTO STK_MAIN(STK_MAIN_GUID,STK_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,INV_ID,STK_STATUS,STK_MEMO,STK_P_BDT,STK_P_EDT,STK_BDT,STK_EDT,IS_STK) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			//TRAN_MAIN_GUID,TRAN_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER
		pstmtInsert.setString(1, headGuid);
		pstmtInsert.setString(2, stkData.getId());
		pstmtInsert.setLong(3, new Date().getTime());
		pstmtInsert.setString(4, stkData.getCreated_by());
		pstmtInsert.setLong(5, new Date().getTime());
		pstmtInsert.setString(6, stkData.getCreated_by());
		pstmtInsert.setString(7, stkData.getClient_guid());
		pstmtInsert.setInt(8, 0);
		pstmtInsert.setString(9, stkData.getData_ver());
				
			//INV_ID,STK_STATUS,STK_MEMO,STK_P_BDT,STK_P_EDT,STK_BDT,STK_EDT
		pstmtInsert.setString(10, stkData.getInv_id());
		pstmtInsert.setInt(11, stkData.getStk_status());
		pstmtInsert.setString(12, stkData.getStk_memo());
		pstmtInsert.setLong(13, stkData.getStk_p_bdt());
		pstmtInsert.setLong(14, stkData.getStk_p_edt());
		pstmtInsert.setLong(15, 0);
		pstmtInsert.setLong(16, 0);
		pstmtInsert.setString(17, stkData.getIs_stk());
			
		pstmtInsert.execute();
		pstmtInsert.close();
		pstmtInsert = null;
		
		
		return headGuid;
	}
	
	/**
	 * 更新盘点状态 
	 * */
	public static void upTakeStockPlan(STK_MAIN stkData, Connection conn) throws Exception{
		PreparedStatement pstmtUp = conn.prepareStatement("UPDATE STK_MAIN SET UPDATED_DT=?,UPDATED_BY=?,STK_STATUS=?,STK_MEMO=?,STK_P_BDT=?,STK_P_EDT=? WHERE STK_MAIN_ID=?");
		
		pstmtUp.setLong(1, new Date().getTime());
		pstmtUp.setString(2, stkData.getUpdated_by());
		pstmtUp.setInt(3, stkData.getStk_status());
		pstmtUp.setString(4, stkData.getStk_memo());
		pstmtUp.setLong(5, stkData.getStk_p_bdt());
		pstmtUp.setLong(6, stkData.getStk_p_edt());
		pstmtUp.setString(7, stkData.getId());
		pstmtUp.execute();
	}
	
	/**
	 *   删除盘点计划，已经有盘点记录的，不允许删除 
	 *   STK_MAIN ,STK_ITM 
	 * 
	 * */
	public static void delTakeStockPlan(String guid, Connection conn) throws Exception{
		PreparedStatement pstmtUp = conn.prepareStatement("SELECT COUNT(*) FROM STK_ITM WHERE STK_MAIN_GUID=?");
		pstmtUp.setString(1, guid);
		ResultSet rsStkItm = pstmtUp.executeQuery();
		if(rsStkItm.next())
		{
			if(rsStkItm.getInt(1)>0)
			{
				rsStkItm.close();
				pstmtUp.close();
				throw new Exception("已有盘点记录，不可删除！");
			}
		}
		
		pstmtUp = conn.prepareStatement("DELETE FROM STK_MAIN WHERE STK_MAIN_GUID=?");
		pstmtUp.setString(1, guid);
		pstmtUp.execute();
	}
	
	/**
	 *  分页获取盘点计划列表 
	 * */
	public static EntityListDM<STK_MAIN,STK_MAIN> getTakeStockPlanList(String planId, int page_no,int page_size, Connection conn) throws Exception{
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
	
		EntityListDM<STK_MAIN,STK_MAIN> returnDM = new EntityListDM<STK_MAIN,STK_MAIN>();
		List<STK_MAIN> returnList = new ArrayList<STK_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 0;
		
		try {
			String subSQL = "SELECT STK_MAIN_GUID, STK_MAIN_ID, INV_ID, STK_STATUS, STK_MEMO, STK_P_BDT, STK_P_EDT,STK_BDT,STK_EDT,IS_STK FROM STK_MAIN ";
			String subSQLWhere = " WHERE IS_DELETED=0";
			String subOrderby = " ORDER BY STK_MAIN_ID DESC ";
			
			if (planId != null && !planId.isEmpty()) {
				subSQLWhere += " AND STK_MAIN_ID LIKE ?";
			}
			
			subSQL = subSQL + subSQLWhere;
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			if (planId != null && !planId.isEmpty()) {
				pstmt.setString(++index, planId+"%");
			}

			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				STK_MAIN entity = new STK_MAIN();
				entity.setGuid(rs.getString(1));
				entity.setId(rs.getString(2));
				entity.setInv_id(rs.getString(3));
				entity.setStk_status(rs.getInt(4));
				entity.setStk_memo(rs.getString(5));
				entity.setStk_p_bdt(rs.getLong(6));
				entity.setStk_p_edt(rs.getLong(7));
				entity.setStk_bdt(rs.getLong(8));
				entity.setStk_edt(rs.getLong(9));
				entity.setIs_stk(rs.getString(10));
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			returnDM.setDataList(returnList);
	
			subSQL = "SELECT COUNT(*) FROM STK_MAIN ";
			subSQL = subSQL + subSQLWhere;
			pstmt = conn.prepareStatement(subSQL);
			index = 0;
			if (planId != null && !planId.isEmpty()) {
				pstmt.setString(++index, planId+"%");
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returnDM.setCount(rs.getInt(1));
			} else {
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}
	
	
	/**
	 *  根据 invid 来获取 盘点计划列表 
	 * */
	public static List<STK_MAIN> getStockPlanListByInvId(String invId, Connection conn) throws Exception{
		List<STK_MAIN> returnList = new ArrayList<STK_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 0;
		
		try {
			String subSQL = "SELECT STK_MAIN_GUID, STK_MAIN_ID, INV_ID, STK_STATUS, STK_MEMO, STK_P_BDT, STK_P_EDT,STK_BDT,STK_EDT FROM STK_MAIN ";
			String subSQLWhere = " WHERE IS_DELETED=0 AND INV_ID=? AND STK_STATUS=1 ";
			String subOrderby = " ORDER BY STK_MAIN_ID DESC ";
			
			pstmt = conn.prepareStatement(subSQL+subSQLWhere+subOrderby);
			pstmt.setString(++index, invId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				STK_MAIN entity = new STK_MAIN();
				entity.setGuid(rs.getString(1));
				entity.setId(rs.getString(2));
				entity.setInv_id(rs.getString(3));
				entity.setStk_status(rs.getInt(4));
				entity.setStk_memo(rs.getString(5));
				entity.setStk_p_bdt(rs.getLong(6));
				entity.setStk_p_edt(rs.getLong(7));
				entity.setStk_bdt(rs.getLong(8));
				entity.setStk_edt(rs.getLong(9));
				returnList.add(entity);
			}
			
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnList;
	}
	
	public static List<STK_MAIN> getStockPlanListByFields(STK_MAIN entityRequest, Connection conn) throws Exception{
		List<STK_MAIN> returnList = new ArrayList<STK_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 0;
		
		try {
			String subSQL = "SELECT STK_MAIN_GUID, STK_MAIN_ID, INV_ID, STK_STATUS, STK_MEMO, STK_P_BDT, STK_P_EDT,STK_BDT,STK_EDT FROM STK_MAIN ";
			String subSQLWhere = " WHERE IS_DELETED=0 AND STK_STATUS=1 ";
			String subOrderby = " ORDER BY STK_MAIN_ID DESC ";
			
			String isStk=entityRequest.getIs_stk();
			
			if(isStk!=null && isStk.trim().length()>0){
				subSQLWhere+=" AND IS_STK=? ";
			}
				
			pstmt = conn.prepareStatement(subSQL+subSQLWhere+subOrderby);
			
			if(isStk!=null && isStk.trim().length()>0){
				pstmt.setString(++index, isStk);
			}
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				STK_MAIN entity = new STK_MAIN();
				entity.setGuid(rs.getString(1));
				entity.setId(rs.getString(2));
				entity.setInv_id(rs.getString(3));
				entity.setStk_status(rs.getInt(4));
				entity.setStk_memo(rs.getString(5));
				entity.setStk_p_bdt(rs.getLong(6));
				entity.setStk_p_edt(rs.getLong(7));
				entity.setStk_bdt(rs.getLong(8));
				entity.setStk_edt(rs.getLong(9));
				returnList.add(entity);
			}
			
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return returnList;
	}
	
	/**
	 *  TODO 
	 * */
	public static String pkgSws(String pkgBaco, List<CTN_MAIN_VIEW> swsList,String operator, String dataVer, String clientId, Connection conn) throws Exception
	{
		PreparedStatement ps;
		String itmId = "";
		String lotId = "";
		String pkgGuid;
		BigDecimal itmQty = BigDecimal.ZERO;

		for(CTN_MAIN_VIEW sws : swsList)
		{
			itmId = sws.getItm_id();
			itmQty = itmQty.add(sws.getItm_qty());
			lotId = sws.getLot_id();
			
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_BY=?,UPDATED_DT=?,ITM_QTY=ITM_QTY-? WHERE CTN_BACO=?");
			ps.setString(1, operator);
			ps.setLong(2, new Date().getTime());
			ps.setBigDecimal(3, sws.getItm_qty());
			ps.setString(4, sws.getCtn_baco());
			ps.execute();
			ps.close();
			
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_BY=?,UPDATED_DT=?,PARENT_CTN_GUID='',CTN_STATUS=0,WH_GUID='',WH_AREA_GUID='',WH_SHELF_GUID='',WH_LOC_GUID='',WH_PLT_GUID='',WH_PACKAGE_GUID='' WHERE CTN_BACO=? AND ITM_QTY=0");
			ps.setString(1, operator);
			ps.setLong(2, new Date().getTime());
			ps.setString(3, sws.getCtn_baco());
			ps.execute();
			ps.close();
		}
		
		CTN_MAIN newPkg = new CTN_MAIN();
		newPkg.setCtn_baco(pkgBaco);
		newPkg.setCtn_main_id(pkgBaco);
		newPkg.setCtn_status(1);
		newPkg.setCtn_type(13);
		newPkg.setItm_id(itmId);
		newPkg.setItm_qty(itmQty);
		newPkg.setLot_id(lotId);
		newPkg.setParent_ctn_guid("");
		newPkg.setWh_area_guid("");
		newPkg.setWh_guid("");
		newPkg.setWh_loc_guid("");
		newPkg.setWh_package_guid("");
		newPkg.setWh_plt_guid("");
		newPkg.setWh_shelf_guid("");
		
		pkgGuid = Common_Biz.addBaco(newPkg, operator, dataVer, clientId, conn);
		
		return pkgGuid;
	}
	
	/**
	 *  TODO 
	 * */
	public static String pkgCert(String pkgBaco, List<CTN_MAIN_VIEW> swsList,String operator, String dataVer, String clientId, Connection conn) throws Exception
	{
		PreparedStatement ps;
		String pkgGuid;

		CTN_MAIN newPkg = new CTN_MAIN();
		newPkg.setCtn_baco(pkgBaco);
		newPkg.setCtn_main_id(pkgBaco);
		newPkg.setCtn_status(1);
		newPkg.setCtn_type(20);
		newPkg.setItm_id("");
		newPkg.setItm_qty(BigDecimal.ZERO);
		newPkg.setLot_id("");
		newPkg.setParent_ctn_guid("");
		newPkg.setWh_area_guid("");
		newPkg.setWh_guid("");
		newPkg.setWh_loc_guid("");
		newPkg.setWh_package_guid("");
		newPkg.setWh_plt_guid("");
		newPkg.setWh_shelf_guid("");
		
		pkgGuid = Common_Biz.addBaco(newPkg, operator, dataVer, clientId, conn);
		
		for(CTN_MAIN_VIEW sws : swsList)
		{		
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_BY=?,UPDATED_DT=?,PARENT_CTN_GUID=?,WH_PACKAGE_GUID=? WHERE CTN_BACO=?");
			ps.setString(1, operator);
			ps.setLong(2, new Date().getTime());
			ps.setString(3, pkgGuid);
			ps.setString(4, pkgGuid);
			ps.setString(5, sws.getCtn_baco());
			ps.execute();
			ps.close();
		}
		
		return pkgGuid;
	}
	
	/**
	 *    合格证被哪些包装箱使用到 （一个合格证，可以对应多个物流包装箱 
	 *     TYPE=13 物流周转箱 （合格证）
	 * */
	public static PKG_DOC getCertPkg(String pkgBaco, Connection conn) throws Exception
	{
		PKG_DOC result = new PKG_DOC();
		result.setChildrens(new ArrayList<CTN_MAIN_VIEW>());
		
		CTN_MAIN_VIEW pkg = Common_Biz.getCtnByBaco(pkgBaco, conn);
		if(pkg!=null&&StringUtils.isNullOrEmpty(pkg.getCtn_main_guid())){
			result.setPkg(pkg);
		}

		PreparedStatement ps;
		ps = conn.prepareStatement("SELECT CTN_BACO FROM CTN_MAIN WHERE WH_PACKAGE_GUID=? AND CTN_TYPE=13");
		ps.setString(1, pkg.getCtn_main_guid());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			CTN_MAIN_VIEW cert = Common_Biz.getCtnByBaco(rs.getString(1),conn);
			
			if(cert!=null&&StringUtils.isNullOrEmpty(cert.getCtn_main_guid())){
				result.getChildrens().add(cert);
			}
		}
		
		return result;
	}
	/**
	 * TODO 
	 * */
	public static void getInProduct(String ctn_baco,String locBaco,Connection conn) throws Exception
	{
		CTN_MAIN_VIEW ctn = Common_Biz.getCtnByBaco(locBaco, conn);
		CTN_MAIN_VIEW ctnProduct = Common_Biz.getCtnByBaco(ctn_baco, conn);
		
		if(ctn!=null&&ctn.getCtn_main_guid().length()>0)
		{
			PreparedStatement ps = conn.prepareStatement("UPDATE CTN_MAIN SET WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,PARENT_CTN_GUID=? WHERE CTN_BACO=?");
			ps.setString(1, ctn.getWh_guid());
			ps.setString(2, ctn.getWh_area_guid());
			ps.setString(3, ctn.getWh_shelf_guid());
			ps.setString(4, ctn.getCtn_main_guid());
			ps.setString(5, ctn.getCtn_main_guid());
			ps.setString(6, ctn_baco);
			ps.execute();
			ps.close();
			
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=? WHERE PARENT_CTN_GUID=?");
			ps.setString(1, ctn.getWh_guid());
			ps.setString(2, ctn.getWh_area_guid());
			ps.setString(3, ctn.getWh_shelf_guid());
			ps.setString(4, ctn.getCtn_main_guid());
			ps.setString(5, ctnProduct.getCtn_main_guid());
			ps.execute();
			ps.close();
		}
		else
		{
			throw new Exception("未找到库位信息！");
		}
	}
	
	/**
	 *  TODO 
	 * */
	public static List<TRAN_BACO> getCertBacoListByPkgBacoList(List<TRAN_BACO> pkgBacoList,Connection conn) throws Exception
	{
		List<TRAN_BACO> certBacoList = new ArrayList<TRAN_BACO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		CTN_MAIN_VIEW ctnPkg;
		
		for(int i=0;i<pkgBacoList.size();i++){
			ctnPkg = Common_Biz.getCtnByBaco(pkgBacoList.get(i).getCtn_baco(), conn);
			ps = conn.prepareStatement("SELECT CTN_BACO,ITM_ID,ITM_QTY,LOT_ID FROM CTN_MAIN WHERE WH_PACKAGE_GUID=? AND CTN_TYPE=13");
			ps.setString(1, ctnPkg.getCtn_main_guid());
			rs = ps.executeQuery();
			while(rs.next()){
				TRAN_BACO tempBaco = new TRAN_BACO();
				tempBaco.setCtn_baco(rs.getString(1));
				tempBaco.setF_wh_id(pkgBacoList.get(i).getF_wh_id());
				tempBaco.setItm_id(rs.getString(2));
				tempBaco.setLot_id(rs.getString(4));
				tempBaco.setParent_baco(pkgBacoList.get(i).getCtn_baco());
				tempBaco.setTran_guid(pkgBacoList.get(i).getTran_guid());
				tempBaco.setTran_qty(rs.getBigDecimal(3));
				tempBaco.setWh_id(pkgBacoList.get(i).getWh_id());
				certBacoList.add(tempBaco);
			}
		}
		return certBacoList;
	}
	
	/**
	 * TODO
	 * */
	public static void doPlt(CTN_MAIN_VIEW plt,List<CTN_MAIN_VIEW> pkgList, String operator,String data_ver,String client_guid,Connection conn) throws Exception
	{
		CTN_MAIN_VIEW loc = Common_Biz.getCtnByBaco(plt.getParent_ctn_baco(), conn);
		if(loc.getCtn_type()!=6)
		{
			throw new Exception("库位条码不正确！");
		}
		
		CTN_MAIN pltCtn = new CTN_MAIN();
		pltCtn.setCtn_baco(plt.getCtn_baco());
		pltCtn.setCtn_main_id(plt.getCtn_baco());
		pltCtn.setCtn_type(7);
		pltCtn.setParent_ctn_guid(loc.getCtn_main_guid());
		pltCtn.setWh_area_guid(loc.getWh_area_guid());
		pltCtn.setWh_guid(loc.getWh_guid());
		pltCtn.setWh_loc_guid(loc.getCtn_main_guid());
		pltCtn.setWh_shelf_guid(loc.getWh_shelf_guid());
		
		String pltGuid = Common_Biz.addBaco(pltCtn, operator, data_ver, client_guid, conn);
		
		PreparedStatement ps = null;
		for(CTN_MAIN_VIEW pkg : pkgList)
		{
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=? WHERE PARENT_CTN_GUID=?");
			ps.setString(1, loc.getWh_guid());
			ps.setString(2, loc.getWh_area_guid());
			ps.setString(3, loc.getWh_shelf_guid());
			ps.setString(4, loc.getCtn_main_guid());
			ps.setString(5, pltGuid);
			ps.setString(6, pkg.getCtn_main_guid());
			ps.execute();
			ps.close();
			
			ps = conn.prepareStatement("UPDATE CTN_MAIN SET PARENT_CTN_GUID=?,WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=? WHERE CTN_BACO=?");
			ps.setString(1, pltGuid);
			ps.setString(2, loc.getWh_guid());
			ps.setString(3, loc.getWh_area_guid());
			ps.setString(4, loc.getWh_shelf_guid());
			ps.setString(5, loc.getCtn_main_guid());
			ps.setString(6, pltGuid);
			ps.setString(7, pkg.getCtn_baco());
			ps.execute();
			ps.close();
		}
	}

	/**
	 * */
	public static void doTakeStockItem(String invId,String planId, String ctnBaco, String pda_id, String locBaco,
			BigDecimal countQty, String operator,String erpUserId,String data_ver,String client_guid,
			Connection conn) throws Exception
	{
		PreparedStatement ps = null;
		PreparedStatement psGetCtn = null;
		PreparedStatement psUpdate = null;
		PreparedStatement psInsert = null;
		ResultSet rs = null;
		ResultSet rsGetCtn = null;

		ps = conn.prepareStatement("SELECT STK_MAIN_GUID FROM STK_MAIN WHERE INV_ID=? AND STK_MAIN_ID=? AND STK_STATUS=1");
		ps.setString(1, invId);
		ps.setString(2, planId);
		rs = ps.executeQuery();
		
		if(rs.next()){
			psUpdate = conn.prepareStatement("UPDATE CTN_MAIN SET STK_GUID=? WHERE CTN_BACO=?");
			psUpdate.setString(1, rs.getString(1));
			psUpdate.setString(2, ctnBaco);
			psUpdate.execute();
			psUpdate.close();
			
			psGetCtn = conn.prepareStatement("SELECT STK_ITM_GUID FROM STK_ITM WHERE STK_MAIN_GUID=? AND CTN_BACO=?");
			psGetCtn.setString(1, rs.getString(1));
			psGetCtn.setString(2, ctnBaco);
			
			rsGetCtn = psGetCtn.executeQuery();
			if(rsGetCtn.next()){
				psUpdate = conn.prepareStatement("UPDATE STK_ITM SET UPDATED_DT=?,UPDATED_BY=?,STK_EMP_ID=?,STK_VALUE=?,STK_LOC_BACO=?,PDA_ID=? WHERE STK_ITM_GUID=?");
				psUpdate.setLong(1, new Date().getTime());
				psUpdate.setString(2, operator);
				psUpdate.setString(3, erpUserId);
				psUpdate.setBigDecimal(4, countQty);
				psUpdate.setString(5, locBaco);
				psUpdate.setString(6, pda_id);
				psUpdate.setString(7, rsGetCtn.getString(1));
				psUpdate.execute();
				
				psUpdate.close();
			}
			else{
				psInsert = conn.prepareStatement("INSERT INTO STK_ITM(STK_ITM_GUID,STK_ITM_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DAVA_VER,STK_MAIN_GUID,STK_EMP_ID,CTN_BACO,STK_VALUE,STK_LOC_BACO,PDA_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				String dataGuid = UUID.randomUUID().toString();
				
				psInsert.setString(1, dataGuid);
				psInsert.setString(2, dataGuid);
				psInsert.setLong(3, new Date().getTime());
				psInsert.setString(4, operator);
				psInsert.setLong(5, new Date().getTime());
				psInsert.setString(6, operator);
				psInsert.setString(7, client_guid);
				psInsert.setInt(8, 0);
				psInsert.setString(9, data_ver);
				
				//STK_MAIN_GUID,STK_EMP_ID,CTN_BACO
				psInsert.setString(10, rs.getString(1));
				psInsert.setString(11, erpUserId);
				psInsert.setString(12,ctnBaco);
				psInsert.setBigDecimal(13,countQty);
				psInsert.setString(14,locBaco);
				psInsert.setString(15,pda_id);
				
				psInsert.execute();
				psInsert.close();
			}
			
			rsGetCtn.close();
			psGetCtn.close();
			
			rs.close();
			ps.close();
		}
		else{
			throw new Exception("未找到对应仓库盘点计划，或盘点计划未启动！");
		}
	}
	
	//盘盈调整
	/**
	 * TODO 
	 * */
	public static void doCfmMoreStockItem(String planGuid, String operator,String data_ver,String client_guid,	Connection conn) throws Exception
	{
		List<STK_ITM_VIEW> resultList = new ArrayList<STK_ITM_VIEW>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T1.ITM_ID,T.CTN_BACO,T.STK_VALUE,T1.ITM_QTY,T.STK_EMP_ID,T.CREATED_DT,T.STK_LOC_BACO,ISNULL(T11.CTN_BACO,''),");
		sb.append("CASE WHEN ISNULL(T1.WH_GUID,'')='' THEN 0 WHEN T2.CTN_MAIN_ID<>T3.INV_ID THEN 1 ELSE 2 END AS REASON  ");
		sb.append("FROM STK_ITM T ");
		sb.append("JOIN CTN_MAIN T1 ON T1.CTN_BACO=T.CTN_BACO");
		sb.append(" LEFT JOIN CTN_MAIN T11 ON T11.CTN_MAIN_GUID=T1.WH_LOC_GUID");
		sb.append(" LEFT JOIN CTN_MAIN T2 ON T2.CTN_MAIN_GUID=T1.WH_GUID");
		sb.append(" JOIN STK_MAIN T3 ON T3.STK_MAIN_GUID=T.STK_MAIN_GUID");
		sb.append(" WHERE ((T.STK_VALUE>T1.ITM_QTY) OR (T2.CTN_MAIN_GUID IS NULL) OR (T2.CTN_MAIN_ID<>T3.INV_ID))");
		sb.append(" AND T3.STK_MAIN_GUID=?");
		sb.append(" ORDER BY T1.ITM_ID");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, planGuid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			STK_ITM_VIEW tempData = new STK_ITM_VIEW();
			tempData.setItm_id(rs.getString(1));
			tempData.setCtn_baco(rs.getString(2));
			tempData.setStk_itm_qty(rs.getBigDecimal(3));
			tempData.setItm_qty(rs.getBigDecimal(4));
			tempData.setEmp_id(rs.getString(5));
			tempData.setStk_dt(rs.getLong(6));
			tempData.setStkloc(rs.getString(7));
			tempData.setCtnloc(rs.getString(8));
			tempData.setReason(rs.getInt(9));
			
			resultList.add(tempData);
		}
		ps.close();
		rs.close();
		
		ps = conn.prepareStatement("SELECT INV_ID FROM STK_MAIN WHERE STK_MAIN_GUID=?");
		ps.setString(1, planGuid);
		rs = ps.executeQuery();
		String invId = "";
		if(rs.next()){
			invId = rs.getString(1);
		}
		else{
			ps.close();
			rs.close();
			throw new Exception("未找到盘点计划。");
		}
		ps.close();
		rs.close();
		
		TRAN_DOC  doc= new TRAN_DOC();
		doc.setHead(new TRAN_MAIN());
		doc.setBody_itm(new ArrayList<TRAN_ITM>());
		doc.setBody_baco(new ArrayList<TRAN_BACO>());
		
		doc.getHead().setCreated_by(operator);
		doc.getHead().setUpdated_by(operator);
		doc.getHead().setClient_guid("gl");
		doc.getHead().setData_ver(data_ver);
		doc.getHead().setIs_syned(0);
		doc.getHead().setWh_id(invId);
		doc.getHead().setIn_out(0);
		doc.getHead().setTran_type(181);
		
		for(int i=0;i<resultList.size();i++){
			BigDecimal tranQty = BigDecimal.ZERO;
			
			//没有入口或者仓库不同，需要更新位置信息
			if(resultList.get(i).getReason()==0||resultList.get(i).getReason()==1){
				CTN_MAIN_VIEW ctnLoc = Common_Biz.getCtnByBaco(resultList.get(i).getStkloc(), conn);

				ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,PARENT_CTN_GUID=?,ITM_QTY=? WHERE CTN_BACO=?");
				ps.setLong(1, new Date().getTime());
				ps.setString(2, operator);
				ps.setString(3, ctnLoc.getWh_guid());
				ps.setString(4, ctnLoc.getWh_area_guid());
				ps.setString(5, ctnLoc.getWh_shelf_guid());
				ps.setString(6, ctnLoc.getCtn_main_guid());
				ps.setString(7, ctnLoc.getCtn_main_guid());
				ps.setBigDecimal(8,resultList.get(i).getStk_itm_qty());
				ps.setString(9, resultList.get(i).getCtn_baco());
				ps.execute();
				ps.close();
				
				tranQty = resultList.get(i).getStk_itm_qty();
			}
			//数量不同
			else if(resultList.get(i).getReason()==2){
				ps = conn.prepareStatement("UPDATE CTN_MAIN SET ITM_QTY=? WHERE CTN_BACO=?");
				ps.setBigDecimal(1, resultList.get(i).getStk_itm_qty());
				ps.setString(2, resultList.get(i).getCtn_baco());
				ps.execute();
				ps.close();
				tranQty = resultList.get(i).getStk_itm_qty().subtract(resultList.get(i).getItm_qty());
			}
			else{
				tranQty = resultList.get(i).getStk_itm_qty().subtract(resultList.get(i).getItm_qty());
			}
				
			TRAN_BACO baco = new TRAN_BACO();
			baco.setCreated_by(operator);
			baco.setUpdated_by(operator);
			baco.setClient_guid("gl");
			baco.setData_ver(data_ver);
			baco.setCtn_baco(resultList.get(i).getCtn_baco());
			baco.setItm_id(resultList.get(i).getItm_id());
			baco.setParent_baco("");
			baco.setTran_qty(tranQty);
			baco.setWh_id(invId);
			doc.getBody_baco().add(baco);
			
			int bodyItmCount = doc.getBody_itm().size();
			boolean isInList = false;
			for(int j=0;j<bodyItmCount;j++){
				if(doc.getBody_itm().get(j).equals(resultList.get(i).getItm_id())){
					doc.getBody_itm().get(j).setItm_qty(doc.getBody_itm().get(j).getItm_qty().add(tranQty));
					isInList = true;
					break;
				}
			}
			
			if(!isInList){
				TRAN_ITM itm = new TRAN_ITM();
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
				itm.setItm_id(resultList.get(i).getItm_id());
				itm.setItm_qty(tranQty);
				
				doc.getBody_itm().add(itm);
			}
		}
		
		InvBasic_Biz.AddTran(doc, conn);
	}
	
	/**
	 * 盘亏调整 
	 **/
	public static void doCfmLessStockItem(String planGuid, String operator, String data_ver,String client_guid,	Connection conn) throws Exception
	{
		List<STK_ITM_VIEW> resultList = new ArrayList<STK_ITM_VIEW>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T.ITM_ID,T.CTN_BACO,ISNULL(T3.STK_VALUE,0),T.ITM_QTY,T3.STK_EMP_ID,ISNULL(T3.CREATED_DT,0),ISNULL(T3.STK_LOC_BACO,''),T0.CTN_BACO,");
		sb.append("CASE WHEN ISNULL(T.STK_GUID,'')<>T2.STK_MAIN_GUID THEN 0 ELSE 2 END AS REASON  ");
		sb.append("FROM CTN_MAIN T ");
		sb.append(" JOIN CTN_MAIN T0 ON T0.CTN_MAIN_GUID=T.WH_LOC_GUID");
		sb.append(" JOIN CTN_MAIN T1 ON T1.CTN_MAIN_GUID=T.WH_GUID");
		sb.append(" JOIN STK_MAIN T2 ON T2.STK_MAIN_GUID=?");
		sb.append(" LEFT JOIN STK_ITM T3 ON T3.STK_MAIN_GUID=T2.STK_MAIN_GUID AND T3.CTN_BACO=T.CTN_BACO");
		sb.append(" WHERE T1.CTN_MAIN_ID=T2.INV_ID AND (ISNULL(T.STK_GUID,'')<>T2.STK_MAIN_GUID");
		sb.append("  OR (T3.STK_ITM_GUID IS NOT NULL AND T3.STK_VALUE<T.ITM_QTY)) AND T.CTN_TYPE=12");
		sb.append(" ORDER BY T.ITM_ID");
		
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1, planGuid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			STK_ITM_VIEW tempData = new STK_ITM_VIEW();
			tempData.setItm_id(rs.getString(1));
			tempData.setCtn_baco(rs.getString(2));
			tempData.setStk_itm_qty(rs.getBigDecimal(3));
			tempData.setItm_qty(rs.getBigDecimal(4));
			tempData.setEmp_id(rs.getString(5));
			tempData.setStk_dt(rs.getLong(6));
			tempData.setStkloc(rs.getString(7));
			tempData.setCtnloc(rs.getString(8));
			tempData.setReason(rs.getInt(9));
			
			resultList.add(tempData);
		}
		ps.close();
		rs.close();
		
		ps = conn.prepareStatement("SELECT INV_ID FROM STK_MAIN WHERE STK_MAIN_GUID=?");
		ps.setString(1, planGuid);
		rs = ps.executeQuery();
		String invId = "";
		if(rs.next()){
			invId = rs.getString(1);
		}
		else{
			throw new Exception("未找到盘点计划。");
		}
		
		TRAN_DOC  doc= new TRAN_DOC();
		doc.setHead(new TRAN_MAIN());
		doc.setBody_itm(new ArrayList<TRAN_ITM>());
		doc.setBody_baco(new ArrayList<TRAN_BACO>());
		
		doc.getHead().setCreated_by(operator);
		doc.getHead().setUpdated_by(operator);
		doc.getHead().setClient_guid("gl");
		doc.getHead().setData_ver(data_ver);
		doc.getHead().setIs_syned(0);
		doc.getHead().setWh_id(invId);
		doc.getHead().setIn_out(0);
		doc.getHead().setTran_type(191);
		
		for(int i=0;i<resultList.size();i++){
			BigDecimal tranQty = BigDecimal.ZERO;
			
			//在仓库里面，但是没有盘点到；
			if(resultList.get(i).getReason()==0){
				ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=?,PARENT_CTN_GUID=? WHERE CTN_BACO=?");
				ps.setLong(1, new Date().getTime());
				ps.setString(2, operator);
				ps.setString(3, "");
				ps.setString(4, "");
				ps.setString(5, "");
				ps.setString(6, "");
				ps.setString(7, "");
				ps.setString(8, "");
				ps.setString(9, resultList.get(i).getCtn_baco());
				ps.execute();
				ps.close();
				
				tranQty = resultList.get(i).getItm_qty();
			}
			//数量不同
			else if(resultList.get(i).getReason()==2){
				ps = conn.prepareStatement("UPDATE CTN_MAIN SET ITM_QTY=? WHERE CTN_BACO=?");
				ps.setBigDecimal(1, resultList.get(i).getStk_itm_qty());
				ps.setString(2, resultList.get(i).getCtn_baco());
				ps.execute();
				ps.close();
				tranQty = resultList.get(i).getItm_qty().subtract(resultList.get(i).getStk_itm_qty());
			}
			else{
				tranQty = resultList.get(i).getItm_qty();
			}
				
			TRAN_BACO baco = new TRAN_BACO();
			baco.setCreated_by(operator);
			baco.setUpdated_by(operator);
			baco.setClient_guid("gl");
			baco.setData_ver(data_ver);
			baco.setCtn_baco(resultList.get(i).getCtn_baco());
			baco.setItm_id(resultList.get(i).getItm_id());
			baco.setParent_baco("");
			baco.setTran_qty(tranQty);
			baco.setWh_id(invId);
			doc.getBody_baco().add(baco);
			
			int bodyItmCount = doc.getBody_itm().size();
			boolean isInList = false;
			for(int j=0;j<bodyItmCount;j++){
				if(doc.getBody_itm().get(j).equals(resultList.get(i).getItm_id())){
					doc.getBody_itm().get(j).setItm_qty(doc.getBody_itm().get(j).getItm_qty().add(tranQty));
					isInList = true;
					break;
				}
			}
			
			if(!isInList){
				TRAN_ITM itm = new TRAN_ITM();
				itm.setCreated_by(operator);
				itm.setUpdated_by(operator);
				itm.setClient_guid("gl");
				itm.setData_ver(data_ver);
				itm.setItm_id(resultList.get(i).getItm_id());
				itm.setItm_qty(tranQty);
				
				doc.getBody_itm().add(itm);
			}
		}
		
		InvBasic_Biz.AddTran(doc, conn);
	}
	
	/**
	 * TODO 
	 * */
	public static RBA_DOC_VIEW getRbaDocById(String rbaId, Connection conn) throws Exception
	{
		RBA_DOC_VIEW result = new RBA_DOC_VIEW();
		result.setItm_list(new ArrayList<RBA_ITM_VIEW>());

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT T1.RBA_DOC_ID,T1.WO_DOC_ID,T1.LABEL_STATUS,T1.RBA_STATUS,T1.RBA_DOC_REMARK,T1.RBA_DOC_DT,T2.WO_ITM_ID FROM RBA_DOC T1,WO_DOC T2 WHERE T1.WO_DOC_ID=T2.WO_DOC_ID AND T1.RBA_DOC_ID=?");
			pstmt.setString(1, rbaId);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result.setRba_doc_id(rs.getString(1));
				result.setWo_doc_id(rs.getString(2));
				result.setLabel_status(rs.getInt(3));
				result.setRba_status(rs.getInt(4));
				result.setRba_doc_remark(rs.getString(5));
				result.setRba_doc_dt(rs.getString(6));
				result.setItm_id(rs.getString(7));
			}
			else
			{
				rs.close();
				pstmt.close();
				throw new Exception("未找到领料单信息！");
			}
			rs.close();
			pstmt.close();
					
			pstmt = conn.prepareStatement("SELECT T1.RBA_ITM_GUID,T1.RBA_DOC_ID,T1.RBA_ITM_SEQNO,T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT,T1.ITM_QTY,T1.INV_ID FROM RBA_ITM T1,ITM_MAIN T2 WHERE T2.ITM_MAIN_ID=T1.ITM_ID AND T1.RBA_DOC_ID=? ORDER BY T1.RBA_ITM_SEQNO");
			pstmt.setString(1, rbaId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RBA_ITM_VIEW itm = new RBA_ITM_VIEW();
				
				itm.setRba_itm_guid(rs.getString(1));
				itm.setRba_doc_id(rs.getString(2));
				itm.setRba_itm_seqno(rs.getString(3));
				itm.setItm_main_id(rs.getString(4));
				itm.setItm_name(rs.getString(5));
				itm.setItm_unit(rs.getString(6));
				itm.setItm_qty(rs.getBigDecimal(7));
				itm.setInv_id(rs.getString(8));
				
				result.getItm_list().add(itm);
			}
			rs.close();
			pstmt.close();
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return result;
	}
	
	/**  TODO
	 * */
	public static List<RBA_DOC_VIEW> getRbaDocByIds(String ids, Connection conn) throws Exception
	{
		List<RBA_DOC_VIEW> resultList = new ArrayList<RBA_DOC_VIEW>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String[] rbaDocIds = ids.split(",");
			for( int i=0;i<rbaDocIds.length;i++){
				RBA_DOC_VIEW result = new RBA_DOC_VIEW();
				String rbaId = rbaDocIds[i];
				
				pstmt = conn.prepareStatement("SELECT T1.RBA_DOC_ID,T1.WO_DOC_ID,T1.LABEL_STATUS,T1.RBA_STATUS,T1.RBA_DOC_REMARK,T1.RBA_DOC_DT,T2.WO_ITM_ID FROM RBA_DOC T1,WO_DOC T2 WHERE T1.WO_DOC_ID=T2.WO_DOC_ID AND T1.RBA_DOC_ID=?");
				pstmt.setString(1, rbaId);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					result.setRba_doc_id(rs.getString(1));
					result.setWo_doc_id(rs.getString(2));
					result.setLabel_status(rs.getInt(3));
					result.setRba_status(rs.getInt(4));
					result.setRba_doc_remark(rs.getString(5));
					result.setRba_doc_dt(rs.getString(6));
					result.setItm_id(rs.getString(7));
				}
				else
				{
					rs.close();
					pstmt.close();
					throw new Exception("未找到领料单信息！");
				}
				rs.close();
				pstmt.close();
					
				result.setItm_list(new ArrayList<RBA_ITM_VIEW>());
				pstmt = conn.prepareStatement("SELECT T1.RBA_ITM_GUID,T1.RBA_DOC_ID,T1.RBA_ITM_SEQNO,T1.ITM_ID,T2.ITM_NAME,T2.ITM_UNIT,T1.ITM_QTY,T1.INV_ID FROM RBA_ITM T1,ITM_MAIN T2 WHERE T2.ITM_MAIN_ID=T1.ITM_ID AND T1.RBA_DOC_ID=? ORDER BY T1.RBA_ITM_SEQNO");
				pstmt.setString(1, rbaId);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					RBA_ITM_VIEW itm = new RBA_ITM_VIEW();
					
					itm.setRba_itm_guid(rs.getString(1));
					itm.setRba_doc_id(rs.getString(2));
					itm.setRba_itm_seqno(rs.getString(3));
					itm.setItm_main_id(rs.getString(4));
					itm.setItm_name(rs.getString(5));
					itm.setItm_unit(rs.getString(6));
					itm.setItm_qty(rs.getBigDecimal(7));
					itm.setInv_id(rs.getString(8));
					
					result.getItm_list().add(itm);
				}
				rs.close();
				pstmt.close();

				resultList.add(result);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		return resultList;
	}
	
	
	/*
	 * TODO 
	 **/
	public static List<PUR_PKG_VIEW> getPurPrintList(List<String> purIds, String seqnos, Connection conn) throws Exception
	{
		List<PUR_PKG_VIEW> resultList = new ArrayList<PUR_PKG_VIEW>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			for( int i=0;i<purIds.size();i++){
				PUR_PKG_VIEW result = null;
				
				if(StringUtils.isNullOrEmpty(seqnos)){
					pstmt = conn.prepareStatement("SELECT ISNULL(T1.PUR_PKG_QTY,0) AS PKGQTY,T2.ITM_MAIN_ID,T2.PUR_DOC_ID,T2.PUR_ITM_SEQNO,T2.ITM_QTY-T2.ITM_DELIVERY_QTY,T1.DEF_LOC_ID,T2.FCT_RECIEVE_DT FROM ITM_MAIN T1,PUR_ITM T2 WHERE T1.ITM_MAIN_ID=T2.ITM_MAIN_ID AND T1.ITM_MAIN_ID LIKE '388%'  AND T2.PUR_DOC_ID=? ORDER BY T2.FCT_RECIEVE_DT,T2.PUR_ITM_SEQNO");
					pstmt.setString(1, purIds.get(i));
				}
				else
				{
					pstmt = conn.prepareStatement("SELECT ISNULL(T1.PUR_PKG_QTY,0) AS PKGQTY,T2.ITM_MAIN_ID,T2.PUR_DOC_ID,T2.PUR_ITM_SEQNO,T2.ITM_QTY-T2.ITM_DELIVERY_QTY,T1.DEF_LOC_ID,T2.FCT_RECIEVE_DT FROM ITM_MAIN T1,PUR_ITM T2 WHERE T1.ITM_MAIN_ID=T2.ITM_MAIN_ID AND T1.ITM_MAIN_ID LIKE '388%'  AND T2.PUR_DOC_ID=? AND T2.PUR_ITM_SEQNO IN ("+seqnos+") ORDER BY T2.FCT_RECIEVE_DT,T2.PUR_ITM_SEQNO");
					pstmt.setString(1, purIds.get(i));
				}
				
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					BigDecimal pkgQty = rs.getBigDecimal(1);
					BigDecimal totalQty = rs.getBigDecimal(5);
					
					if(pkgQty.compareTo(BigDecimal.ZERO)==0){
						String itmId=  rs.getString(2);
						rs.close();
						pstmt.close();
						throw new Exception("品号："+itmId+" 没有采购装箱数量！");
					}
					
					String lotId = "";
					PreparedStatement pstmtHDB010 = conn.prepareStatement("SELECT HDB010 FROM DCSHDB WHERE HDB001=? AND HDB002=?");
					pstmtHDB010.setString(1, purIds.get(i));
					pstmtHDB010.setString(2, rs.getString(4));
					ResultSet rsHDB010 = pstmtHDB010.executeQuery();
					if(rsHDB010.next())
					{
						lotId = rsHDB010.getString(1);
					}
					pstmtHDB010.close();
					rsHDB010.close();
					
					if(!StringUtils.isNullOrEmpty(lotId) && lotId.length()>0){
						lotId = lotId.substring(2, 8);
						String tempLotId = lotId.substring(0,2) +"-";
						tempLotId = tempLotId + lotId.substring(2,4) + "-";
						tempLotId = tempLotId + lotId.substring(4,6);
						
						lotId = tempLotId;
					}
					
					while(totalQty.compareTo(BigDecimal.ZERO)>0){
						result = new PUR_PKG_VIEW();
						result.setLot_id(lotId);
						
						if(totalQty.compareTo(pkgQty)>0){
							result.setPkgqty(pkgQty);
							result.setItm_id(rs.getString(2));
							result.setPur_id(rs.getString(3));
							result.setPur_seqno(rs.getString(4));
							result.setStock_area(rs.getString(6));
							resultList.add(result);
							totalQty = totalQty.subtract(pkgQty);
						}
						else
						{
							result.setPkgqty(totalQty);
							result.setItm_id(rs.getString(2));
							result.setPur_id(rs.getString(3));
							result.setPur_seqno(rs.getString(4));
							result.setStock_area(rs.getString(6));
							resultList.add(result);
							totalQty = BigDecimal.ZERO;
						}
					}
				}
				
				rs.close();
				pstmt.close();
			}
			
			String maxno = "";
			for(int i=0;i<resultList.size();i++){
				
				String doc_seqno = "";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String date_str = sdf.format(new Date());
				if(StringUtils.isNullOrEmpty(maxno)){
					pstmt = conn.prepareStatement("SELECT MAXNO = MAX(CTN_BACO) FROM CTN_MAIN WHERE CTN_BACO LIKE ?");
					pstmt.setString(1, date_str+"%");
					rs = pstmt.executeQuery();
					
					if(rs.next()){
						maxno = rs.getString(1);
						maxno=maxno==null?"0":maxno;
						doc_seqno = maxno.replaceFirst(date_str, ""); 
						doc_seqno = Long.parseLong(doc_seqno)+1+"";
						
					}
					rs.close();
					pstmt.close();
				}
				else{
					doc_seqno = maxno.replaceFirst(date_str, ""); 
					doc_seqno = Long.parseLong(doc_seqno)+1+"";
				}
				
				while(doc_seqno.length()<6){
					doc_seqno="0"+doc_seqno;
				}
				String bacoId = date_str+doc_seqno;
				maxno = bacoId;
				
				resultList.get(i).setCtn_baco(bacoId);
				
				CTN_MAIN ctn = new CTN_MAIN();
				ctn.setCtn_baco(bacoId);
				ctn.setCtn_main_id(bacoId);
				ctn.setCtn_status(1);
				ctn.setCtn_type(12);
				ctn.setItm_id(resultList.get(i).getItm_id());
				ctn.setItm_qty(resultList.get(i).getPkgqty());
				ctn.setParent_ctn_guid("");
				ctn.setWh_area_guid("");
				ctn.setWh_guid("");
				ctn.setWh_loc_guid("");
				ctn.setWh_package_guid("");
				ctn.setWh_plt_guid("");
				ctn.setWh_shelf_guid("");
				ctn.setLot_id(resultList.get(i).getLot_id());
				ctn.setCtn_name("采购流程票");
				ctn.setBase_type(5);
				ctn.setBase_id(resultList.get(i).getPur_id());
				ctn.setBase_seqno(resultList.get(i).getPur_seqno());
				Common_Biz.addBaco(ctn, "subwo", "1.0.0.0", "gl", conn);
			}
		} 
		catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		
		return resultList;
	}
	
	/**
	 * TODO 
	 * */
	public static List<PUR_PKG_VIEW> getRePurPrintList(List<String> purIds,Connection conn) throws Exception
	{
		List<PUR_PKG_VIEW> resultList = new ArrayList<PUR_PKG_VIEW>();

		if(purIds.size()==0){
			return resultList;
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmtItm = null;
		ResultSet rsItm = null;

		String purIdListStr = "";
		try {
			for( int i=0;i<purIds.size()-1;i++){
				purIdListStr = purIdListStr+purIds.get(i)+",";
			}
			purIdListStr = purIdListStr + purIds.get(purIds.size()-1);
			PUR_PKG_VIEW result = null;
			BigDecimal pkgQty = BigDecimal.ZERO;
			BigDecimal totalQty = BigDecimal.ZERO;
			String defLocId = "";

			pstmt = conn.prepareStatement("SELECT T2.HDB003,T2.HDB001,T2.HDB002,T2.HDB010,T.JFB007 FROM JSKJFB T,JSKJCB T1,DCSHDB T2 WHERE T.JFB017=T1.JCB001 AND T.JFB018=T1.JCB002 AND T1.JCB010=T2.HDB001 AND T1.JCB011=T2.HDB002 AND T.JFB016=15 AND T.JFB019='T' AND T2.HDB003 LIKE '388%' AND T.JFB001 IN ("+purIdListStr+") ORDER BY T2.HDB010,T2.HDB001,T2.HDB002");

			rs = pstmt.executeQuery();
			while(rs.next())
			{
				pstmtItm = conn.prepareStatement("SELECT ISNULL(PUR_PKG_QTY,0),DEF_LOC_ID FROM ITM_MAIN WHERE ITM_MAIN_ID=?");
				pstmtItm.setString(1, rs.getString(1));
				rsItm = pstmtItm.executeQuery();
				if(rsItm.next()){
					pkgQty =rsItm.getBigDecimal(1);
					if(pkgQty.compareTo(BigDecimal.ZERO)<=0){
						rsItm.close();
						pstmtItm.close();
						rs.close();
						pstmt.close();
						throw new Exception("品号："+rs.getString(1)+" 没有采购装箱数量！");
					}
					defLocId = rsItm.getString(2);
				}
				else{
					rsItm.close();
					pstmtItm.close();
					rs.close();
					pstmt.close();
					throw new Exception("品号："+rs.getString(1)+" 未找到记录！");
				}
				rsItm.close();
				pstmtItm.close();
				
				totalQty = rs.getBigDecimal(5);
				String lotId = rs.getString(4);
					
				if(!StringUtils.isNullOrEmpty(lotId) && lotId.length()>0){
					lotId = lotId.substring(2, 8);
					String tempLotId = lotId.substring(0,2) +"-";
					tempLotId = tempLotId + lotId.substring(2,4) + "-";
					tempLotId = tempLotId + lotId.substring(4,6);
					
					lotId = tempLotId;
				}
					
				while(totalQty.compareTo(BigDecimal.ZERO)>0){
					result = new PUR_PKG_VIEW();
					result.setLot_id(lotId);
					
					if(totalQty.compareTo(pkgQty)>0){
						result.setPkgqty(pkgQty);
						result.setItm_id(rs.getString(1));
						result.setPur_id(rs.getString(2));
						result.setPur_seqno(rs.getString(3));
						result.setStock_area(defLocId);
						resultList.add(result);
						totalQty = totalQty.subtract(pkgQty);
					}
					else
					{
						result.setPkgqty(totalQty);
						result.setItm_id(rs.getString(1));
						result.setPur_id(rs.getString(2));
						result.setPur_seqno(rs.getString(3));
						result.setStock_area(defLocId);
						resultList.add(result);
						totalQty = BigDecimal.ZERO;
					}
				}
			}
			rs.close();
			pstmt.close();
			
			String maxno = "";
			for(int i=0;i<resultList.size();i++){
				
				String doc_seqno = "";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String date_str = sdf.format(new Date());
				if(StringUtils.isNullOrEmpty(maxno)){
					pstmt = conn.prepareStatement("SELECT MAXNO = MAX(CTN_BACO) FROM CTN_MAIN WHERE CTN_BACO LIKE ?");
					pstmt.setString(1, date_str+"%");
					rs = pstmt.executeQuery();
					
					if(rs.next()){
						maxno = rs.getString(1);
						maxno=maxno==null?"0":maxno;
						doc_seqno = maxno.replaceFirst(date_str, ""); 
						doc_seqno = Long.parseLong(doc_seqno)+1+"";
						
					}
					rs.close();
					pstmt.close();
				}
				else{
					doc_seqno = maxno.replaceFirst(date_str, ""); 
					doc_seqno = Long.parseLong(doc_seqno)+1+"";
				}
				
				while(doc_seqno.length()<6){
					doc_seqno="0"+doc_seqno;
				}
				String bacoId = date_str+doc_seqno;
				maxno = bacoId;
				
				resultList.get(i).setCtn_baco(bacoId);
				
				CTN_MAIN ctn = new CTN_MAIN();
				ctn.setCtn_baco(bacoId);
				ctn.setCtn_main_id(bacoId);
				ctn.setCtn_status(1);
				ctn.setCtn_type(12);
				ctn.setItm_id(resultList.get(i).getItm_id());
				ctn.setItm_qty(resultList.get(i).getPkgqty());
				ctn.setParent_ctn_guid("");
				ctn.setWh_area_guid("");
				ctn.setWh_guid("");
				ctn.setWh_loc_guid("");
				ctn.setWh_package_guid("");
				ctn.setWh_plt_guid("");
				ctn.setWh_shelf_guid("");
				ctn.setLot_id(resultList.get(i).getLot_id());
				ctn.setCtn_name("退货采购流程票");
				ctn.setBase_type(5);
				ctn.setBase_id(resultList.get(i).getPur_id());
				ctn.setBase_seqno(resultList.get(i).getPur_seqno());
				Common_Biz.addBaco(ctn, "subwo", "1.0.0.0", "gl", conn);
			}
		} 
		catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		
		return resultList;
	}
	
	/**
	 *  TODO 
	 * */
	public static WO_DOC_VIEW getItmPrintList(String itmId,BigDecimal splitQty,String lotId,Connection conn) throws Exception{
		WO_DOC_VIEW tempData= new WO_DOC_VIEW();
		String maxno = "";
		
		tempData.setItm_id(itmId);
		PreparedStatement pstmt = conn.prepareStatement("SELECT ISNULL(DEF_LOC_ID,'') FROM ITM_MAIN WHERE ITM_MAIN_ID=?");
		pstmt.setString(1, itmId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			tempData.setStock_area(rs.getString(1));
			rs.close();
			pstmt.close();
		}
		else
		{
			rs.close();
			pstmt.close();
			throw new Exception("物料号："+itmId+" 未找到！");
		}
			
		String doc_seqno = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date_str = sdf.format(new Date());
		if(StringUtils.isNullOrEmpty(maxno)){
			pstmt = conn.prepareStatement("SELECT MAXNO = MAX(CTN_BACO) FROM CTN_MAIN WHERE CTN_BACO LIKE ?");
			pstmt.setString(1, date_str+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				maxno = rs.getString(1);
				
				maxno=maxno==null?"0":maxno;
				doc_seqno = maxno.replaceFirst(date_str, ""); 
				doc_seqno = Long.parseLong(doc_seqno)+1+"";
			}
			rs.close();
			pstmt.close();
		}
		else{
			doc_seqno = maxno.replaceFirst(date_str, ""); 
			doc_seqno = Long.parseLong(doc_seqno)+1+"";
		}
			
		while(doc_seqno.length()<6){
			doc_seqno="0"+doc_seqno;
		}
		String swsId = date_str+doc_seqno;
		maxno = swsId;
		
		tempData.setWo_doc_guid(swsId);
			
		tempData.setItm_qty(splitQty);
		tempData.setLot_id(lotId);
			
		CTN_MAIN ctn = new CTN_MAIN();
		ctn.setCtn_baco(swsId);
		ctn.setCtn_main_id(swsId);
		ctn.setCtn_status(1);
		ctn.setCtn_type(12);
		ctn.setItm_id(tempData.getItm_id());
		ctn.setItm_qty(tempData.getItm_qty());
		ctn.setParent_ctn_guid("");
		ctn.setWh_area_guid("");
		ctn.setWh_guid("");
		ctn.setWh_loc_guid("");
		ctn.setWh_package_guid("");
		ctn.setWh_plt_guid("");
		ctn.setWh_shelf_guid("");
		ctn.setLot_id(lotId);
		ctn.setCtn_name("工单流程票");
		Common_Biz.addBaco(ctn, "subwo", "1.0.0.0", "gl", conn);
		
		return tempData;
	}
	
	/**
	 * TODO 
	 * */
	public static List<RBA_DOC_VIEW> getRbaDocList(String rbaId, Connection conn) throws Exception
	{
		List<RBA_DOC_VIEW> resultList = new ArrayList<RBA_DOC_VIEW>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT T1.RBA_DOC_ID,T1.WO_DOC_ID,T1.LABEL_STATUS,T1.RBA_STATUS,T1.RBA_DOC_REMARK,T1.RBA_DOC_DT,T2.WO_ITM_ID,T1.ERP_EMP_ID FROM RBA_DOC T1,WO_DOC T2 WHERE T1.WO_DOC_ID=T2.WO_DOC_ID AND T1.RBA_DOC_ID LIKE ?");
			pstmt.setString(1, rbaId+"%");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				RBA_DOC_VIEW result = new RBA_DOC_VIEW(); 
				result.setRba_doc_id(rs.getString(1));
				result.setWo_doc_id(rs.getString(2));
				result.setLabel_status(rs.getInt(3));
				result.setRba_status(rs.getInt(4));
				result.setRba_doc_remark(rs.getString(5));
				result.setRba_doc_dt(rs.getString(6));
				result.setItm_id(rs.getString(7));
				result.setErp_emp_id(rs.getString(8));
				
				resultList.add(result);
			}
			
			rs.close();
			pstmt.close();
					
			return resultList;
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null && !rs.isClosed())rs.close();
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}
	
	/** TODO 
	 * */
	public static List<ITM_MAIN_VIEW> getItmInQty(List<String> itmIds,String whGuid,Connection conn) throws Exception
	{
		List<ITM_MAIN_VIEW> resultList = new ArrayList<ITM_MAIN_VIEW>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			for(int i=0;i<itmIds.size();i++){
				pstmt = conn.prepareStatement("SELECT SUM(ISNULL(ITM_QTY,0)) FROM CTN_MAIN WHERE ITM_ID=? AND WH_GUID=?");
				pstmt.setString(1, itmIds.get(i));
				pstmt.setString(2, whGuid);
				
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					ITM_MAIN_VIEW result = new ITM_MAIN_VIEW(); 
					result.setItm_main_id(itmIds.get(i));
					result.setItm_qty(rs.getBigDecimal(1));
					
					resultList.add(result);
				}
				rs.close();
				pstmt.close();
			}		
			return resultList;
	
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null && !rs.isClosed())rs.close();
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}
	
	/**
	 * TODO 
	 * */
	public static STK_ITM_WKSITE getWorkSiteItemBySwsId(String swsId,Connection conn) throws Exception{
		
		STK_ITM_WKSITE resultEntity = new STK_ITM_WKSITE();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			//获取工单号，图号
			String sql="SELECT T1.SUB_WO_SUB_GUID,t1.SUB_WO_SUB_ID,T3.WO_DOC_ID,T3.WO_ITM_ID FROM SUB_WO_SUB T1"
+" INNER JOIN SUB_WO_MAIN T2 ON T1.SUB_WO_MAIN_GUID=T2.SUB_WO_MAIN_GUID"
+" INNER JOIN WO_DOC T3 ON T2.WO_ID=T3.WO_DOC_ID"
+" WHERE T1.SUB_WO_SUB_ID=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, swsId);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()){
		    resultEntity.setSws_guid(rs.getString(1));
		    resultEntity.setSws_id(rs.getString(2));
			resultEntity.setWo_doc_id(rs.getString(3));
			resultEntity.setItm_id(rs.getString(4));			
			}
			else{
				
				rs.close();
				pstmt.close();
				
				return resultEntity;
				
			}
			
			rs.close();
			pstmt.close();
			
			//获取最后一道工序的报工量
			String sql2="SELECT TOP 1 ISNULL(SUM(T1.FINISH_QTY),0) AS FINISH_QTY_TOTAL,T1.RP_RAC_ID,T1.RP_RAC_NAME FROM SWS_RP T1"
+" INNER JOIN SUB_WO_SUB T2 ON T1.SWS_GUID=t2.SUB_WO_SUB_GUID"
+" AND T2.IS_DELETED='0' AND T2.SUB_WO_SUB_ID=?"
+" AND T1.IS_DELETED='0' AND T1.RP_STATUS='2'"
+" GROUP BY T1.RP_RAC_ID,T1.RP_RAC_NAME"
+" ORDER BY T1.RP_RAC_ID DESC";
			
			pstmt=conn.prepareStatement(sql2);
			pstmt.setString(1,swsId);
			
			rs=pstmt.executeQuery();
			
			String sRacId="";
			String sRacName="";
			if(rs.next()){
				resultEntity.setStk_value(rs.getBigDecimal(1));
				sRacId=rs.getString(2);
				sRacName=rs.getString(3);
			}
			else{
				//流程票未开工,默认报工量为0
				resultEntity.setStk_value(BigDecimal.ZERO);
			}
			rs.close();
			pstmt.close();
			
			if(sRacId.isEmpty()){
						
				//流程票未开工，获取工单的第一道工序
				String sql3="SELECT TOP 1 WO_RAC_ID,RAC_NAME FROM WO_RAC WHERE WO_DOC_ID=? ORDER BY WO_RAC_ID";
				pstmt=conn.prepareStatement(sql3);
				pstmt.setString(1, resultEntity.getWo_doc_id());
				
				rs=pstmt.executeQuery();
				if(rs.next()){
					resultEntity.setStk_rac_id(rs.getString(1));
					resultEntity.setStk_rac_name(rs.getString(2));
				}
				
				rs.close();
				pstmt.close();
			}
			else{
				
			//获取当前工序的下一道工序
			String sql3="SELECT TOP 1 WO_RAC_ID,RAC_NAME FROM WO_RAC WHERE WO_DOC_ID=? AND WO_RAC_ID>? ORDER BY WO_RAC_ID";
			pstmt=conn.prepareStatement(sql3);
			pstmt.setString(1, resultEntity.getWo_doc_id());
			pstmt.setString(2, sRacId);
			
			rs=pstmt.executeQuery();
			if(rs.next()){
				resultEntity.setStk_rac_id(rs.getString(1));
				resultEntity.setStk_rac_name(rs.getString(2));
			}
			else{
				//已经是工单的最后一道工序
				resultEntity.setStk_rac_id(sRacId);
				resultEntity.setStk_rac_name(sRacName);
			}
			
			rs.close();
			pstmt.close();
			}
			
			return resultEntity;
			
		}catch(Exception ex){
			throw ex;
		} finally{
			if (rs != null && !rs.isClosed())rs.close();
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		
	}
	
	/**
	 * TODO 
	 * */
	public static String addStkItmWKSite(STK_ITM_WKSITE stkData,String isUpdate, Connection conn) throws Exception{
		
			
		PreparedStatement pstmtInsert = null;
		
		try{

			String result=CheckStkItmWKSite(stkData,conn);
			//result: 不为空 表示已经盘点
			if(!result.equals("")){
				//isUpdate: Y表示需要重新盘点 
				if(isUpdate.equals("Y")){	
					pstmtInsert = conn.prepareStatement("DELETE FROM STK_ITM_WKSITE WHERE STK_ITM_WKSITE_GUID=?");
					pstmtInsert.setString(1, result);
					
					pstmtInsert.execute();
					pstmtInsert.close();
				} else {
					return "1";
				}
			}
						
			String sql= "INSERT INTO STK_ITM_WKSITE"
		    		+ "(STK_ITM_WKSITE_GUID"
		    		+ ",STK_ITM_WKSITE_ID"
		    		+ ",CREATED_DT"
		    		+ ",CREATED_BY"
		    		+ ",UPDATED_DT"
		    		+ ",UPDATED_BY"
		    		+ ",CLIENT_GUID"
		    		+ ",IS_DELETED"
		    		+ ",DAVA_VER"
		    		+ ",STK_MAIN_GUID"
		    		+ ",STK_EMP_ID"
		    		+ ",SWS_GUID"
		    		+ ",SWS_ID"
		    		+ ",WO_DOC_ID"
		    		+ ",ITM_ID"
		    		+ ",STK_RAC_ID"
		    		+ ",STK_RAC_NAME"
		    		+ ",STK_VALUE)"
		    		+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtInsert = conn.prepareStatement(sql);
			
			String headGuid = UUID.randomUUID().toString();
			int i=0;

			pstmtInsert.setString(++i, headGuid);
			pstmtInsert.setString(++i,headGuid);
			pstmtInsert.setLong(++i, new Date().getTime());
			pstmtInsert.setString(++i, stkData.getCreated_by());
			pstmtInsert.setLong(++i, new Date().getTime());
			pstmtInsert.setString(++i,stkData.getUpdated_by());
			pstmtInsert.setString(++i, stkData.getClient_guid());
			pstmtInsert.setInt(++i, 0);
			pstmtInsert.setString(++i, stkData.getData_ver());
			
			pstmtInsert.setString(++i, stkData.getStk_main_guid());
			pstmtInsert.setString(++i, stkData.getStk_emp_id());
			pstmtInsert.setString(++i, stkData.getSws_guid());
			pstmtInsert.setString(++i, stkData.getSws_id());
			pstmtInsert.setString(++i, stkData.getWo_doc_id());
			pstmtInsert.setString(++i, stkData.getItm_id());
			pstmtInsert.setString(++i, stkData.getStk_rac_id());
			pstmtInsert.setString(++i, stkData.getStk_rac_name());
			pstmtInsert.setBigDecimal(++i, stkData.getStk_value());
				
			pstmtInsert.execute();
			pstmtInsert.close();
					
			return headGuid;
			
		}catch(Exception ex){
			throw ex;
		} finally{			
			if (pstmtInsert != null && !pstmtInsert.isClosed())pstmtInsert.close();
		}
		
	}
	
	
	/** TODO 校验 盘点计划GUID+流程票GUID+工序ID  是否已经存在
	 * */
	public static String CheckStkItmWKSite(STK_ITM_WKSITE stkData, Connection conn) throws Exception{
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String result="";
		try{
			pstmt = conn.prepareStatement("SELECT STK_ITM_WKSITE_GUID FROM STK_ITM_WKSITE WHERE STK_MAIN_GUID=? AND SWS_GUID=? AND IS_DELETED='0'");
			pstmt.setString(1, stkData.getStk_main_guid());
			pstmt.setString(2, stkData.getSws_guid());
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result= rs.getString(1);
			}
			rs.close();
			pstmt.close();
			
			return result;
			
		}catch(Exception ex){
			throw ex;
		} finally{
			if (rs != null && !rs.isClosed())rs.close();
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}

	}
	
    
	/**
	 *  TODO 
	 * */
	public static void moveSWSWHLoc(CTN_MAIN_VIEW ctnData, Connection conn) throws Exception{
		PreparedStatement pstmt=null;

		try{
			String sql="UPDATE C1 SET C1.PARENT_CTN_GUID=C2.CTN_MAIN_GUID,C1.WH_GUID=C2.WH_GUID ,C1.WH_AREA_GUID=C2.WH_AREA_GUID,C1.WH_SHELF_GUID=C2.WH_SHELF_GUID,C1.WH_LOC_GUID=C2.CTN_MAIN_GUID"
					+ " FROM CTN_MAIN C1,CTN_MAIN C2"
					+ " WHERE C1.CTN_BACO=? AND C1.IS_DELETED='0'"
					+ " AND C2.CTN_BACO=? AND C2.IS_DELETED='0'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctnData.getCtn_baco());
			pstmt.setString(2, ctnData.getParent_ctn_baco());
			
		    pstmt.execute();
		    pstmt.close();
			
		}catch(Exception ex){
			throw ex;
		} finally{
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}

	}
	
	/**调整盘点库位
	 * 
	 * 
	 * TODO 
	 * @param planGuid
	 * @param conn
	 * @throws Exception
	 */
	public static void updateStockWHLoc(String planGuid, Connection conn ) throws Exception{
		PreparedStatement pstmt=null;
		try{
			String sql="UPDATE C SET C.PARENT_CTN_GUID=T.WH_LOC_GUID,C.WH_GUID=T.WH_GUID,C.WH_AREA_GUID=T.WH_AREA_GUID,C.WH_SHELF_GUID=T.WH_SHELF_GUID,C.WH_LOC_GUID=T.WH_LOC_GUID "
+ " FROM CTN_MAIN C "
+ " INNER JOIN "
+ " ( "
+ " 	SELECT T1.CTN_BACO,T1.STK_LOC_BACO,T2.WH_GUID,T2.WH_AREA_GUID,T2.WH_SHELF_GUID,T2.CTN_MAIN_GUID AS WH_LOC_GUID "
+ " 	FROM STK_ITM T1 "
+ " 	INNER JOIN CTN_MAIN T2 ON T1.STK_LOC_BACO=T2.CTN_BACO AND T2.IS_DELETED='0' "
+ " 	WHERE T1.IS_DELETED='0' "
+ " 	AND T1.STK_MAIN_GUID=? "
+ " ) AS T "
+ " ON C.CTN_BACO=T.CTN_BACO "
+ " AND C.IS_DELETED='0' "
+ " AND (C.WH_LOC_GUID IS NOT NULL AND LEN(C.WH_LOC_GUID)>0) "
+ " AND (C.ITM_QTY IS NOT NULL AND LEN(C.ITM_QTY)>0 AND C.ITM_QTY>0)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, planGuid);
			
			pstmt.execute();
			pstmt.close();
			
		}catch(Exception ex){
			throw ex;
		}finally{
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
		
	}
	
    /**
     *  TODO 
     * @param ctn
     * @param operator
     * @param conn
     * @throws Exception
     */
	public static void updateCtnForSemiPurTran(CTN_MAIN ctn,String operator,Connection conn) throws Exception {
		
		PreparedStatement ps = conn.prepareStatement("UPDATE CTN_MAIN SET UPDATED_DT=?,UPDATED_BY=?, WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,WH_PLT_GUID=?,LOT_ID=?,PARENT_CTN_GUID=?,ITM_QTY=? WHERE CTN_MAIN_GUID=?");
		
		ps.setLong(1, new Date().getTime());
		ps.setString(2, operator);
		ps.setString(3, ctn.getWh_guid());
		ps.setString(4, ctn.getWh_area_guid());
		ps.setString(5, ctn.getWh_shelf_guid());
		ps.setString(6, ctn.getWh_loc_guid());
		ps.setString(7, ctn.getWh_plt_guid());
		ps.setString(8, ctn.getLot_id());
		ps.setString(9, ctn.getParent_ctn_guid());
	    ps.setBigDecimal(10, ctn.getItm_qty());
		ps.setString(11, ctn.getCtn_main_guid());
		ps.execute();
		ps.close();
	}
	

	/**
	 * TODO
	 * @param ctn_baco
	 * @param locBaco
	 * @param conn
	 * @throws Exception
	 */
	public static void updProductLoc(String ctn_baco,String locBaco,Connection conn) throws Exception
	{
		CTN_MAIN_VIEW ctn = Common_Biz.getCtnByBaco(locBaco, conn);
				
		if(ctn!=null&&ctn.getCtn_main_guid().length()>0)
		{						
			PreparedStatement ps = conn.prepareStatement("UPDATE CTN_MAIN SET WH_GUID=?,WH_AREA_GUID=?,WH_SHELF_GUID=?,WH_LOC_GUID=?,PARENT_CTN_GUID=? WHERE CTN_BACO=?");
			ps.setString(1, ctn.getWh_guid());
			ps.setString(2, ctn.getWh_area_guid());
			ps.setString(3, ctn.getWh_shelf_guid());
			ps.setString(4, ctn.getCtn_main_guid());
			ps.setString(5, ctn.getCtn_main_guid());
			ps.setString(6, ctn_baco);
			ps.execute();
			ps.close();
		}
		else
		{
			throw new Exception("未找到库位信息！");
		}
	}
	
	
	/**
	 * TODO
	 * @param doc
	 * @param conn
	 * @throws Exception
	 */
	public static void doRbaBack(TRAN_BASE_DOC doc,Connection conn) throws Exception{
		PreparedStatement ps=null;
		
		try{
			
			ps=conn.prepareStatement("INSERT INTO RBA_DOC_BACK(RBA_DOC_BACK_GUID,RBA_DOC_ID,WO_DOC_ID,LABEL_STATUS,RBA_STATUS,RBA_DOC_REMARK,RBA_DOC_DT,ERP_EMP_ID,TRAN_BASE_DOC_GUID) SELECT NEWID(),RBA_DOC_ID, WO_DOC_ID,LABEL_STATUS ,RBA_STATUS,RBA_DOC_REMARK,RBA_DOC_DT,ERP_EMP_ID,? FROM RBA_DOC WHERE RBA_DOC_ID=?");
			ps.setString(1, doc.getGuid());
			ps.setString(2, doc.getBase_doc_id());
			ps.execute();
			ps.close();
			
			ps=conn.prepareStatement("INSERT INTO RBA_ITM_BACK(RBA_ITM_BACK_GUID,RBA_ITM_SEQNO,RBA_DOC_ID ,ITM_ID,ITM_QTY,INV_ID,TRAN_BASE_DOC_GUID)SELECT NEWID(),RBA_ITM_SEQNO ,RBA_DOC_ID,ITM_ID,ITM_QTY,INV_ID ,? FROM RBA_ITM WHERE RBA_DOC_ID=?");
			ps.setString(1, doc.getGuid());
			ps.setString(2, doc.getBase_doc_id());
			ps.execute();
			ps.close();
						
		}catch(Exception ex){
			throw ex;
		}finally{
			if(ps!=null&&!ps.isClosed()){
				ps.close();
			}
		}
	}
}
