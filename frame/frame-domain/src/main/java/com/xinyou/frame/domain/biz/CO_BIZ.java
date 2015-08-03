package com.xinyou.frame.domain.biz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.entities.CO_MAIN;
import com.xinyou.frame.domain.models.CO_DM;
import com.xinyou.util.Config;
import com.xinyou.util.StringUtil;

public class CO_BIZ extends StringUtil{

	public void addCo(CO_MAIN co, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle": addCo_oracle(co, conn);
	                 		break;
	        case "sqlserver": addCo_sqlserver(co, conn);
	        				break;
	        default: addCo_mysql(co, conn);
	        				break;
		}
	}

	public CO_DM getCos(String co_id, String co_name, int page_no,
			int page_size, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		CO_DM returnDM = new CO_DM();
		switch (dataSourceType) {
	        case "oracle": returnDM = getCos_oracle(co_id,co_name,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getCos_sqlserver(co_id,co_name,page_no,page_size,conn);
							break;
	        default: returnDM = getCos_mysql(co_id,co_name,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}

	public List<CO_MAIN> getSlCos(Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		List<CO_MAIN> returnList = new ArrayList<CO_MAIN>();
		switch (dataSourceType) {
	        case "oracle":  returnList = getSlCos_oracle(conn);
	                 		break;
	        case "sqlserver": returnList = getSlCos_sqlserver(conn);
							break;
	        default: returnList = getSlCos_mysql(conn);
	        				break;
		}
		return returnList;
	}

	public void delCo(String[] coguidArray, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  delCo_oracle(coguidArray,conn);
	                 		break;
	        case "sqlserver": delCo_sqlserver(coguidArray,conn);
							break;
	        default: delCo_mysql(coguidArray,conn);
	        				break;
		}
	}

	public CO_MAIN getCo(String coguid, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		CO_MAIN returnEntity = new CO_MAIN();
		switch (dataSourceType) {
	        case "oracle":  returnEntity = getCo_oracle(coguid,conn);
	                 		break;
	        case "sqlserver": returnEntity = getCo_sqlserver(coguid,conn);
							break;
	        default: returnEntity = getCo_mysql(coguid,conn);
	        				break;
		}
		return returnEntity;
	}

	public void updateCo(CO_MAIN co, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  updateCo_oracle(co,conn);
	                 		break;
	        case "sqlserver": updateCo_sqlserver(co,conn);
							break;
	        default: updateCo_mysql(co,conn);
	        				break;
		}
	}

	private void addCo_mysql(CO_MAIN co, Connection conn) throws Exception {
		co.setCo_main_id(Decode(co.getCo_main_id()));
		co.setCo_name(Decode(co.getCo_name()));
		co.setCo_addr(Decode(co.getCo_addr()));
		co.setCo_desc(Decode(co.getCo_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String cSQL="select 1 from CO_MAIN where IS_DELETED=0 and CO_MAIN_ID=? limit 1";
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

	private CO_DM getCos_mysql(String co_id, String co_name, int page_no,
			int page_size, Connection conn) throws Exception {
		co_id = Decode(co_id);
		co_name = Decode(co_name);
		
		CO_DM returnDM = new CO_DM();
		List<CO_MAIN> returnList = new ArrayList<CO_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select CO_MAIN_GUID, CO_MAIN_ID, CO_NAME from CO_MAIN";
			String sSQLWhere = " where IS_DELETED=0";
			if(co_id!=null&&co_id.length()>0){
				sSQLWhere +=" and CO_MAIN_ID like ?";
			}
			if(co_name!=null&&co_name.length()>0){
				sSQLWhere +=" and CO_NAME like ?";
			}
			pstmt = conn.prepareStatement(sSQL+sSQLWhere+" order by CREATED_DT desc limit "+ (page_no-1)*page_size + "," + page_size);
			int index = 0;
			if(co_id!=null&&co_id.length()>0){
				pstmt.setString(++index, co_id+"%");
			}
			if(co_name!=null&&co_name.length()>0){
				pstmt.setString(++index, "%"+co_name+"%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				CO_MAIN entity = new CO_MAIN();
				entity.setCo_main_guid(rs.getString(1));
				entity.setCo_main_id(rs.getString(2));
				entity.setCo_name(Encode(rs.getString(3)));
				
				returnList.add(entity);
			}
			returnDM.setCoListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("select count(*) from CO_MAIN"+sSQLWhere);
			index = 0;
			if(co_id!=null&&co_id.length()>0){
				pstmt.setString(++index, co_id+"%");
			}
			if(co_name!=null&&co_name.length()>0){
				pstmt.setString(++index, "%"+co_name+"%");
			}
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnDM.setCount(rs.getInt(1));
			}else{
				returnDM.setCount(0);
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnDM;
	}

	private List<CO_MAIN> getSlCos_mysql(Connection conn) throws Exception {
		List<CO_MAIN> returnList = new ArrayList<CO_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select CO_MAIN_GUID, CO_NAME from CO_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				CO_MAIN entity = new CO_MAIN();
				entity.setCo_main_guid(rs.getString(1));
				entity.setCo_name(Encode(rs.getString(2)));
				
				returnList.add(entity);
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnList;
	}

	private void delCo_mysql(String[] coguidArray, Connection conn) throws Exception {
		PreparedStatement orgPstmt = null;
		PreparedStatement coPstmt = null;
		try{
			conn.setAutoCommit(false);
			String dOrgSQL = "delete from ORG_MAIN where IS_DELETED=0 and CO_GUID=?";
			String dCoSQL = "delete from CO_MAIN where IS_DELETED=0 and CO_MAIN_GUID=?";
			orgPstmt = conn.prepareStatement(dOrgSQL);
			coPstmt = conn.prepareStatement(dCoSQL);
			for(String coguid : coguidArray){
				orgPstmt.setString(1, coguid);
				orgPstmt.addBatch();
				
				coPstmt.setString(1, coguid);
				coPstmt.addBatch();
			}
			orgPstmt.executeBatch();
			coPstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(orgPstmt!=null&&!orgPstmt.isClosed()){
				orgPstmt.close();
			}
			if(coPstmt!=null&&!coPstmt.isClosed()){
				coPstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private CO_MAIN getCo_mysql(String coguid, Connection conn) throws Exception {
		CO_MAIN returnEntity = new CO_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select CO_MAIN_GUID, CO_MAIN_ID, CO_NAME, CO_ADDR, CO_DESC from CO_MAIN where IS_DELETED=0 and CO_MAIN_GUID=? limit 1";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, coguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setCo_main_guid(rs.getString(1));
				returnEntity.setCo_main_id(Encode(rs.getString(2)));
				returnEntity.setCo_name(Encode(rs.getString(3)));
				returnEntity.setCo_addr(Encode(rs.getString(4)));
				returnEntity.setCo_desc(Encode(rs.getString(5)));
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnEntity;
	}

	private void updateCo_mysql(CO_MAIN co, Connection conn) throws Exception {
		co.setCo_name(Decode(co.getCo_name()));
		co.setCo_addr(Decode(co.getCo_addr()));
		co.setCo_desc(Decode(co.getCo_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update CO_MAIN set UPDATED_DT=?, UPDATED_BY=?, CO_NAME=?, CO_ADDR=?, CO_DESC=? where IS_DELETED=0 and CO_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, co.getUpdated_by());
			pstmt.setString(3, co.getCo_name());
			pstmt.setString(4, co.getCo_addr());
			pstmt.setString(5, co.getCo_desc());
			pstmt.setString(6, co.getCo_main_guid());
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

	private void addCo_oracle(CO_MAIN co, Connection conn) throws Exception {
		co.setCo_main_id(Decode(co.getCo_main_id()));
		co.setCo_name(Decode(co.getCo_name()));
		co.setCo_addr(Decode(co.getCo_addr()));
		co.setCo_desc(Decode(co.getCo_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String cSQL="select 1 from CO_MAIN where IS_DELETED=0 and CO_MAIN_ID=? and ROWNUM=1";
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

	private CO_DM getCos_oracle(String co_id, String co_name, int page_no,
			int page_size, Connection conn) throws Exception {
		co_id = Decode(co_id);
		co_name = Decode(co_name);

		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;;
		
		CO_DM returnDM = new CO_DM();
		List<CO_MAIN> returnList = new ArrayList<CO_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select CO_MAIN_GUID, CO_MAIN_ID, CO_NAME from CO_MAIN";
			String subSQLWhere = " where IS_DELETED=0";
			if(co_id!=null&&co_id.length()>0){
				subSQLWhere +=" and CO_MAIN_ID like ?";
			}
			if(co_name!=null&&co_name.length()>0){
				subSQLWhere +=" and CO_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere+" order by CREATED_DT desc";
			String sSQL = "select B.* from (select A.*, ROWNUM as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			if(co_id!=null&&co_id.length()>0){
				pstmt.setString(++index, co_id+"%");
			}
			if(co_name!=null&&co_name.length()>0){
				pstmt.setString(++index, "%"+co_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				CO_MAIN entity = new CO_MAIN();
				entity.setCo_main_guid(rs.getString(1));
				entity.setCo_main_id(rs.getString(2));
				entity.setCo_name(Encode(rs.getString(3)));
				
				returnList.add(entity);
			}
			returnDM.setCoListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("select count(*) from CO_MAIN"+subSQLWhere);
			index = 0;
			if(co_id!=null&&co_id.length()>0){
				pstmt.setString(++index, co_id+"%");
			}
			if(co_name!=null&&co_name.length()>0){
				pstmt.setString(++index, "%"+co_name+"%");
			}
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnDM.setCount(rs.getInt(1));
			}else{
				returnDM.setCount(0);
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnDM;
	}

	private List<CO_MAIN> getSlCos_oracle(Connection conn) throws Exception {
		List<CO_MAIN> returnList = new ArrayList<CO_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select CO_MAIN_GUID, CO_NAME from CO_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				CO_MAIN entity = new CO_MAIN();
				entity.setCo_main_guid(rs.getString(1));
				entity.setCo_name(Encode(rs.getString(2)));
				
				returnList.add(entity);
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnList;
	}

	private void delCo_oracle(String[] coguidArray, Connection conn) throws Exception {
		PreparedStatement orgPstmt = null;
		PreparedStatement coPstmt = null;
		try{
			conn.setAutoCommit(false);
			String dOrgSQL = "delete from ORG_MAIN where IS_DELETED=0 and CO_GUID=?";
			String dCoSQL = "delete from CO_MAIN where IS_DELETED=0 and CO_MAIN_GUID=?";
			orgPstmt = conn.prepareStatement(dOrgSQL);
			coPstmt = conn.prepareStatement(dCoSQL);
			for(String coguid : coguidArray){
				orgPstmt.setString(1, coguid);
				orgPstmt.addBatch();
				
				coPstmt.setString(1, coguid);
				coPstmt.addBatch();
			}
			orgPstmt.executeBatch();
			coPstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(orgPstmt!=null&&!orgPstmt.isClosed()){
				orgPstmt.close();
			}
			if(coPstmt!=null&&!coPstmt.isClosed()){
				coPstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private CO_MAIN getCo_oracle(String coguid, Connection conn) throws Exception {
		CO_MAIN returnEntity = new CO_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select CO_MAIN_GUID, CO_MAIN_ID, CO_NAME, CO_ADDR, CO_DESC from CO_MAIN where IS_DELETED=0 and CO_MAIN_GUID=? and ROWNUM=1";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, coguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setCo_main_guid(rs.getString(1));
				returnEntity.setCo_main_id(Encode(rs.getString(2)));
				returnEntity.setCo_name(Encode(rs.getString(3)));
				returnEntity.setCo_addr(Encode(rs.getString(4)));
				returnEntity.setCo_desc(Encode(rs.getString(5)));
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnEntity;
	}

	private void updateCo_oracle(CO_MAIN co, Connection conn) throws Exception {
		co.setCo_name(Decode(co.getCo_name()));
		co.setCo_addr(Decode(co.getCo_addr()));
		co.setCo_desc(Decode(co.getCo_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update CO_MAIN set UPDATED_DT=?, UPDATED_BY=?, CO_NAME=?, CO_ADDR=?, CO_DESC=? where IS_DELETED=0 and CO_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, co.getUpdated_by());
			pstmt.setString(3, co.getCo_name());
			pstmt.setString(4, co.getCo_addr());
			pstmt.setString(5, co.getCo_desc());
			pstmt.setString(6, co.getCo_main_guid());
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

	private CO_DM getCos_sqlserver(String co_id, String co_name, int page_no,
			int page_size, Connection conn) throws Exception {
		co_id = Decode(co_id);
		co_name = Decode(co_name);
	
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;;
		
		CO_DM returnDM = new CO_DM();
		List<CO_MAIN> returnList = new ArrayList<CO_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select CO_MAIN_GUID, CO_MAIN_ID, CO_NAME, CREATED_DT from CO_MAIN";
			String subSQLWhere = " where IS_DELETED=0";
			String subOrderby="order by CREATED_DT desc";
			if(co_id!=null&&co_id.length()>0){
				subSQLWhere +=" and CO_MAIN_ID like ?";
			}
			if(co_name!=null&&co_name.length()>0){
				subSQLWhere +=" and CO_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere;
			String sSQL = "select B.* from (select A.*, ROW_NUMBER() over("+subOrderby+") as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			if(co_id!=null&&co_id.length()>0){
				pstmt.setString(++index, co_id+"%");
			}
			if(co_name!=null&&co_name.length()>0){
				pstmt.setString(++index, "%"+co_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				CO_MAIN entity = new CO_MAIN();
				entity.setCo_main_guid(rs.getString(1));
				entity.setCo_main_id(rs.getString(2));
				entity.setCo_name(Encode(rs.getString(3)));
				
				returnList.add(entity);
			}
			returnDM.setCoListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("select count(CO_MAIN_GUID) from CO_MAIN"+subSQLWhere);
			index = 0;
			if(co_id!=null&&co_id.length()>0){
				pstmt.setString(++index, co_id+"%");
			}
			if(co_name!=null&&co_name.length()>0){
				pstmt.setString(++index, "%"+co_name+"%");
			}
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnDM.setCount(rs.getInt(1));
			}else{
				returnDM.setCount(0);
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnDM;
	}

	private List<CO_MAIN> getSlCos_sqlserver(Connection conn) throws Exception {
		List<CO_MAIN> returnList = new ArrayList<CO_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select CO_MAIN_GUID, CO_NAME from CO_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				CO_MAIN entity = new CO_MAIN();
				entity.setCo_main_guid(rs.getString(1));
				entity.setCo_name(Encode(rs.getString(2)));
				
				returnList.add(entity);
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnList;
	}

	private void delCo_sqlserver(String[] coguidArray, Connection conn) throws Exception {
		PreparedStatement orgPstmt = null;
		PreparedStatement coPstmt = null;
		try{
			conn.setAutoCommit(false);
			String dOrgSQL = "delete from ORG_MAIN where IS_DELETED=0 and CO_GUID=?";
			String dCoSQL = "delete from CO_MAIN where IS_DELETED=0 and CO_MAIN_GUID=?";
			orgPstmt = conn.prepareStatement(dOrgSQL);
			coPstmt = conn.prepareStatement(dCoSQL);
			for(String coguid : coguidArray){
				orgPstmt.setString(1, coguid);
				orgPstmt.addBatch();
				
				coPstmt.setString(1, coguid);
				coPstmt.addBatch();
			}
			orgPstmt.executeBatch();
			coPstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(orgPstmt!=null&&!orgPstmt.isClosed()){
				orgPstmt.close();
			}
			if(coPstmt!=null&&!coPstmt.isClosed()){
				coPstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private CO_MAIN getCo_sqlserver(String coguid, Connection conn) throws Exception {
		CO_MAIN returnEntity = new CO_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select top 1 CO_MAIN_GUID, CO_MAIN_ID, CO_NAME, CO_ADDR, CO_DESC from CO_MAIN where IS_DELETED=0 and CO_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, coguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setCo_main_guid(rs.getString(1));
				returnEntity.setCo_main_id(Encode(rs.getString(2)));
				returnEntity.setCo_name(Encode(rs.getString(3)));
				returnEntity.setCo_addr(Encode(rs.getString(4)));
				returnEntity.setCo_desc(Encode(rs.getString(5)));
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnEntity;
	}

	private void updateCo_sqlserver(CO_MAIN co, Connection conn) throws Exception {
		co.setCo_name(Decode(co.getCo_name()));
		co.setCo_addr(Decode(co.getCo_addr()));
		co.setCo_desc(Decode(co.getCo_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update CO_MAIN set UPDATED_DT=?, UPDATED_BY=?, CO_NAME=?, CO_ADDR=?, CO_DESC=? where IS_DELETED=0 and CO_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, co.getUpdated_by());
			pstmt.setString(3, co.getCo_name());
			pstmt.setString(4, co.getCo_addr());
			pstmt.setString(5, co.getCo_desc());
			pstmt.setString(6, co.getCo_main_guid());
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
}
