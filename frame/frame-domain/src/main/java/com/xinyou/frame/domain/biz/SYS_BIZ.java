package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.entities.BIZ_SYS;
import com.xinyou.frame.domain.models.SYS_DM;
import com.xinyou.util.Config;
import com.xinyou.util.StringUtil;

public class SYS_BIZ extends StringUtil{
	public void addBizSys(BIZ_SYS biz_sys, Connection conn) throws Exception{
		biz_sys.setBiz_sys_id(Decode(biz_sys.getBiz_sys_id()));
		biz_sys.setSys_name(Decode(biz_sys.getSys_name()));
		biz_sys.setSys_url(Decode(biz_sys.getSys_url()));
		biz_sys.setSys_desc(Decode(biz_sys.getSys_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT BIZ_SYS_ID FROM BIZ_SYS WHERE  BIZ_SYS_ID=? AND IS_DELETED=0");
			pstmt.setString(1, biz_sys.getBiz_sys_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			rs.close();
			pstmt.close();
			if(exist){
				throw new Exception("系统代码 "+biz_sys.getBiz_sys_id()+" 已存在");
			}
			pstmt = conn.prepareStatement("INSERT INTO BIZ_SYS (BIZ_SYS_GUID,BIZ_SYS_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,SYS_NAME,SYS_URL,SYS_DESC,SYS_PC,SYS_MOBILE)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, biz_sys.getBiz_sys_id());
			long ldate = new Date().getTime();
			pstmt.setLong(3, ldate);
			pstmt.setString(4, biz_sys.getCreated_by());
			pstmt.setLong(5, ldate);
			pstmt.setString(6, biz_sys.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, biz_sys.getClient_guid());
			pstmt.setString(9, biz_sys.getSys_name());
			pstmt.setString(10, biz_sys.getSys_url());
			pstmt.setString(11, biz_sys.getSys_desc());
			pstmt.setInt(12, biz_sys.getSys_pc());
			pstmt.setInt(13, biz_sys.getSys_mobile());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}
	
	public SYS_DM getBizSyss(String sysID, String sysName, int pageNo, int pageSize, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		SYS_DM returnDM = new SYS_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getBizSyss_oracle(sysID,sysName,pageNo,pageSize,conn);
	                 		break;
	        case "sqlserver": returnDM = getBizSyss_sqlserver(sysID,sysName,pageNo,pageSize,conn);
							break;
	        default: returnDM = getBizSyss_mysql(sysID,sysName,pageNo,pageSize,conn);
	        				break;
		}
		return returnDM;
	}
	
	private SYS_DM getBizSyss_mysql(String sysID, String sysName, int page_no,
			int page_size, Connection conn) throws Exception {
		sysID=Decode(sysID);
		sysName=Decode(sysName);
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT BIZ_SYS_GUID, BIZ_SYS_ID, SYS_NAME, SYS_URL, SYS_DESC FROM BIZ_SYS";
			String subWhereSQL = " WHERE";
			boolean bfirstParam=true;
			if(sysID!=null&&!sysID.isEmpty()){
				if(bfirstParam){
					subWhereSQL+=" BIZ_SYS_ID LIKE ?";
					bfirstParam=false;
				}else{
					subWhereSQL+=" AND BIZ_SYS_ID LIKE ?";
				}
			}
			if(sysName!=null&&!sysName.isEmpty()){
				if(bfirstParam){
					subWhereSQL+=" SYS_NAME LIKE ?";
					bfirstParam=false;
				}else{
					subWhereSQL+=" AND SYS_NAME LIKE ?";
				}
			}
			if(bfirstParam){
				subWhereSQL+=" IS_DELETED=0";
				bfirstParam=false;
			}else{
				subWhereSQL+=" AND IS_DELETED=0";
			}
			pstmt = conn.prepareStatement(sSQL+subWhereSQL+" ORDER BY CREATED_DT DESC LIMIT "+ (page_no-1)*page_size + "," + page_size);
			int index=0;
			if(sysID!=null&&!sysID.isEmpty()){
				pstmt.setString(++index, sysID+"%");
			}
			if(sysName!=null&&!sysName.isEmpty()){
				pstmt.setString(++index, "%"+sysName+"%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(rs.getString(2));
				entity.setSys_name(Encode(rs.getString(3)));
				entity.setSys_url(Encode(rs.getString(4)));
				entity.setSys_desc(Encode(rs.getString(5)));
				
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM BIZ_SYS"+subWhereSQL);
			index=0;
			if(sysID!=null&&!sysID.isEmpty()){
				pstmt.setString(++index, sysID+"%");
			}
			if(sysName!=null&&!sysName.isEmpty()){
				pstmt.setString(++index, "%"+sysName+"%");
			}
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnDM.setCount(rs.getInt(1));
			}else{
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}

	private SYS_DM getBizSyss_sqlserver(String sysID, String sysName,
			int page_no, int page_size, Connection conn) throws Exception {
		sysID=Decode(sysID);
		sysName=Decode(sysName);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String subSQL = "SELECT BIZ_SYS_GUID, BIZ_SYS_ID, SYS_NAME, SYS_URL, SYS_DESC, SYS_PC, SYS_MOBILE, CREATED_DT FROM BIZ_SYS";
			String subWhereSQL = " WHERE";
			String subOrderby=" ORDER BY CREATED_DT DESC ";
			boolean bfirstParam=true;
			if(sysID!=null&&!sysID.isEmpty()){
				if(bfirstParam){
					subWhereSQL+=" BIZ_SYS_ID LIKE ?";
					bfirstParam=false;
				}else{
					subWhereSQL+=" AND BIZ_SYS_ID LIKE ?";
				}
			}
			if(sysName!=null&&!sysName.isEmpty()){
				if(bfirstParam){
					subWhereSQL+=" SYS_NAME LIKE ?";
					bfirstParam=false;
				}else{
					subWhereSQL+=" AND SYS_NAME LIKE ?";
				}
			}
			if(bfirstParam){
				subWhereSQL+=" IS_DELETED=0";
				bfirstParam=false;
			}else{
				subWhereSQL+=" AND IS_DELETED=0";
			}
			subSQL=subSQL+subWhereSQL;
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+subOrderby+") AS RN FROM ("+ subSQL +") A) B WHERE B.RN>=? AND B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index=0;
			if(sysID!=null&&!sysID.isEmpty()){
				pstmt.setString(++index, sysID+"%");
			}
			if(sysName!=null&&!sysName.isEmpty()){
				pstmt.setString(++index, "%"+sysName+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(rs.getString(2));
				entity.setSys_name(Encode(rs.getString(3)));
				entity.setSys_url(Encode(rs.getString(4)));
				entity.setSys_desc(Encode(rs.getString(5)));
				entity.setSys_pc(rs.getInt(6));
				entity.setSys_mobile(rs.getInt(7));
				
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM BIZ_SYS"+subWhereSQL);
			index=0;
			if(sysID!=null&&!sysID.isEmpty()){
				pstmt.setString(++index, sysID+"%");
			}
			if(sysName!=null&&!sysName.isEmpty()){
				pstmt.setString(++index, "%"+sysName+"%");
			}
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnDM.setCount(rs.getInt(1));
			}else{
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}

	private SYS_DM getBizSyss_oracle(String sysID, String sysName, int page_no,
			int page_size, Connection conn) throws Exception {
		sysID=Decode(sysID);
		sysName=Decode(sysName);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String subSQL = "SELECT BIZ_SYS_GUID, BIZ_SYS_ID, SYS_NAME, SYS_URL, SYS_DESC, SYS_PC, SYS_MOBILE FROM BIZ_SYS";
			String subWhereSQL = " WHERE";
			boolean bfirstParam=true;
			if(sysID!=null&&!sysID.isEmpty()){
				if(bfirstParam){
					subWhereSQL+=" BIZ_SYS_ID LIKE ?";
					bfirstParam=false;
				}else{
					subWhereSQL+=" AND BIZ_SYS_ID LIKE ?";
				}
			}
			if(sysName!=null&&!sysName.isEmpty()){
				if(bfirstParam){
					subWhereSQL+=" SYS_NAME LIKE ?";
					bfirstParam=false;
				}else{
					subWhereSQL+=" AND SYS_NAME LIKE ?";
				}
			}
			if(bfirstParam){
				subWhereSQL+=" IS_DELETED=0";
				bfirstParam=false;
			}else{
				subWhereSQL+=" AND IS_DELETED=0";
			}
			subSQL=subSQL+subWhereSQL+" ORDER BY CREATED_DT DESC";
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROWNUM AS RN FROM ("+ subSQL +") A) B WHERE B.RN>=? AND B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index=0;
			if(sysID!=null&&!sysID.isEmpty()){
				pstmt.setString(++index, sysID+"%");
			}
			if(sysName!=null&&!sysName.isEmpty()){
				pstmt.setString(++index, "%"+sysName+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(rs.getString(2));
				entity.setSys_name(Encode(rs.getString(3)));
				entity.setSys_url(Encode(rs.getString(4)));
				entity.setSys_desc(Encode(rs.getString(5)));
				entity.setSys_pc(rs.getInt(6));
				entity.setSys_mobile(rs.getInt(7));
				
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM BIZ_SYS"+subWhereSQL);
			
			index=0;
			if(sysID!=null&&!sysID.isEmpty()){
				pstmt.setString(++index, sysID+"%");
			}
			if(sysName!=null&&!sysName.isEmpty()){
				pstmt.setString(++index, "%"+sysName+"%");
			}
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnDM.setCount(rs.getInt(1));
			}else{
				returnDM.setCount(0);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}

	public BIZ_SYS getBizSys(String sys_guid, Connection conn) throws Exception{
		BIZ_SYS returnEntity = new BIZ_SYS();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT BIZ_SYS_GUID, BIZ_SYS_ID, SYS_NAME, SYS_URL, SYS_DESC, SYS_PC, SYS_MOBILE FROM BIZ_SYS WHERE BIZ_SYS_GUID=? AND IS_DELETED=0");
			pstmt.setString(1, sys_guid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setBiz_sys_guid(rs.getString(1));
				returnEntity.setBiz_sys_id(rs.getString(2));
				returnEntity.setSys_name(Encode(rs.getString(3)));
				returnEntity.setSys_url(rs.getString(4));
				returnEntity.setSys_desc(rs.getString(5));
				returnEntity.setSys_pc(rs.getInt(6));
				returnEntity.setSys_mobile(rs.getInt(7));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnEntity;
	}

	public void updateBizSys(BIZ_SYS entity, Connection conn) throws Exception{
		entity.setSys_name(Decode(entity.getSys_name()));
		entity.setSys_url(Decode(entity.getSys_url()));
		entity.setSys_desc(Decode(entity.getSys_desc()));
		PreparedStatement pstmt=null;
		try{
			pstmt = conn.prepareStatement("UPDATE BIZ_SYS SET UPDATED_DT=?, UPDATED_BY=?, SYS_NAME=?, SYS_URL=?, SYS_DESC=?, SYS_PC=?, SYS_MOBILE=? WHERE BIZ_SYS_GUID=? AND IS_DELETED=0");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getSys_name());
			pstmt.setString(4, entity.getSys_url());
			pstmt.setString(5, entity.getSys_desc());
			pstmt.setInt(6, entity.getSys_pc());
			pstmt.setInt(7, entity.getSys_mobile());
			pstmt.setString(8, entity.getBiz_sys_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	public void delBizSys(String sys_guid, Connection conn) throws Exception{
		PreparedStatement pstmt=null;
		try{
			pstmt = conn.prepareStatement("DELETE FROM BIZ_SYS WHERE BIZ_SYS_GUID=? AND IS_DELETED=0");
			pstmt.setString(1, sys_guid);
			pstmt.execute();
			pstmt.close();

			pstmt = conn.prepareStatement("DELETE FROM FUN_MAIN WHERE BIZ_SYS_GUID=? AND IS_DELETED=0");
			pstmt.setString(1, sys_guid);
			pstmt.execute();
			pstmt.close();

			pstmt = conn.prepareStatement("DELETE FROM ROLE_SYS WHERE SYS_GUID=? AND IS_DELETED=0");
			pstmt.setString(1, sys_guid);
			pstmt.execute();
			pstmt.close();
			conn.commit();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	public List<BIZ_SYS> loadBizSyss(String role_guid, Connection conn) throws Exception{
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT T1.BIZ_SYS_GUID, T1.SYS_NAME FROM ROLE_SYS T INNER JOIN BIZ_SYS T1 ON T.SYS_GUID = T1.BIZ_SYS_GUID AND T1.IS_DELETED=0 WHERE T.ROLE_GUID=? AND T.IS_DELETED=0 ORDER BY T.IS_DEFAULT_SYS DESC");
			pstmt.setString(1, role_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setSys_name(Encode(rs.getString(2)));
				
				returnList.add(entity);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnList;
	}
}
