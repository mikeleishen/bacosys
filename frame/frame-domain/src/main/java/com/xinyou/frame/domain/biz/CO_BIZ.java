package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.UUID;

import com.xinyou.frame.domain.entities.CO_MAIN;
import com.xinyou.util.Config;
import com.xinyou.util.StringUtil;

public class CO_BIZ extends StringUtil {

	public void addCo(CO_MAIN co,Connection conn) throws Exception {
		String dataSourceType = new Config( FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
		case "oracle":
			 addCo_oracle(co,conn);
			break;
		case "sqlserver":
			 addCo_sqlserver(co,conn);
			break;
		}
		
	}
	
	private void addCo_sqlserver(CO_MAIN co, Connection conn) throws Exception {
		co.setCo_main_id(Decode(co.getCo_main_id()));
		co.setCo_name(Decode(co.getCo_name()));
		co.setCo_addr(Decode(co.getCo_addr()));
		co.setCo_desc(Decode(co.getCo_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String cSQL="select top 1 1 from CO_MAIN where IS_DELETED=0 and CO_MAIN_ID=?";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, co.getCo_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("公司代码 "+co.getCo_main_id()+" 已存在");
			}
			String iSQL = "insert into CO_MAIN (CO_MAIN_GUID, CO_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, CO_NAME, CO_ADDR, CO_DESC) values (?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, co.getCo_main_id());
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, co.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, co.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, co.getClient_guid());
			pstmt.setString(9,co.getCo_name());
			pstmt.setString(10, co.getCo_addr());
			pstmt.setString(11, co.getCo_desc());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
	}
	
	private void addCo_oracle(CO_MAIN co,Connection conn) throws Exception {
		 co.setCo_main_id(Decode(co.getCo_main_id()));
		 co.setCo_name(Decode(co.getCo_name()));
		 co.setCo_addr(Decode(co.getCo_addr()));
		 co.setCo_desc(Decode(co.getCo_desc()));
		 
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 try {
		    
			  //判断公司是否存在（而且未删除）
			  String cSQL = "select 1 from CO_MAIN where IS_DELETED = 0 and CO_MAIN_ID= ? and ROWNUM=1" ;
			  pstmt = conn.prepareStatement(cSQL);
			  pstmt.setString(1, co.getCo_main_id());
			  rs = pstmt.executeQuery();
			  boolean exist = false;
			  if(rs.next()){
				  exist = true;
			  }
			  pstmt.close();
			 
			  if(exist) {
				  throw new Exception("公司代码" + co.getCo_main_id() + "已存在");
			  }
			  
			  //插入新公司
			  String iSQL = "insert into CO_MAIN (CO_MAIN_GUID, CO_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, CO_NAME, CO_ADDR, CO_DESC) values (?,?,?,?,?,?,?,?,?,?,?)";
			  pstmt = conn.prepareStatement(iSQL);
			  pstmt.setString(1, UUID.randomUUID().toString());
			  pstmt.setString(2, co.getCo_main_id());
			  long lDate = new Date().getTime();
			  pstmt.setLong(3,lDate);
			  pstmt.setString(4, co.getCreated_by());
			  pstmt.setLong(5,lDate);
			  pstmt.setString(6, co.getUpdated_by());
			  pstmt.setLong(7,0);
			  pstmt.setString(8, co.getClient_guid());
			  pstmt.setString(9,co.getCo_name());
			  pstmt.setString(10, co.getCo_addr());
			  pstmt.setString(11, co.getCo_desc());
			  pstmt.execute();
			  pstmt.close();
		 }catch (Exception e) {
			 throw e;
		 }finally {
			 if(pstmt != null && !pstmt.isClosed()) {
				 pstmt.close();
			 }
		 }
		 
		 
	}


}
