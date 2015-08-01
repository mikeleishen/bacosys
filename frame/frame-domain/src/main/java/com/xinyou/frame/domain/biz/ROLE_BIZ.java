package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.entities.BIZ_SYS;
import com.xinyou.frame.domain.entities.PRIVILEGE_MAIN;
import com.xinyou.frame.domain.entities.ROLE_SYS;
import com.xinyou.frame.domain.entities.ROLE_MAIN;
import com.xinyou.frame.domain.models.PRIVILEGE_DM;
import com.xinyou.frame.domain.models.SYS_DM;
import com.xinyou.frame.domain.models.FUN_NODE;
import com.xinyou.frame.domain.models.ROLE_DM;
import com.xinyou.util.Config;
import com.xinyou.util.StringUtil;


public class ROLE_BIZ extends StringUtil{
	
	
	public void addSysRole(ROLE_MAIN entity, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  addSysRole_oracle(entity, conn);
	                 		break;
	        case "sqlserver": addSysRole_sqlserver(entity, conn);
							break;
	        default: addSysRole_mysql(entity, conn);
	        				break;
		}
	}

	public ROLE_DM getSysRoles(String role_id, String role_name,
			int page_no, int page_size, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		ROLE_DM returnDM = new ROLE_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getSysRoles_oracle(role_id,role_name,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getSysRoles_sqlserver(role_id,role_name,page_no,page_size,conn);
							break;
	        default: returnDM = getSysRoles_mysql(role_id,role_name,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}

	public ROLE_MAIN getSysRole(String role_guid, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		ROLE_MAIN returnEntity = new ROLE_MAIN();
		switch (dataSourceType) {
	        case "oracle":  returnEntity = getSysRole_oracle(role_guid,conn);
	                 		break;
	        case "sqlserver": returnEntity = getSysRole_sqlserver(role_guid,conn);
							break;
	        default: returnEntity = getSysRole_mysql(role_guid,conn);
	        				break;
		}
		return returnEntity;
	}

	public void updateSysRole(ROLE_MAIN entity, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  updateSysRole_oracle(entity, conn);
	                 		break;
	        case "sqlserver": updateSysRole_sqlserver(entity, conn);
							break;
	        default: updateSysRole_mysql(entity, conn);
	        				break;
		}
	}

	public void delSysRole(String role_guid, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  delSysRole_oracle(role_guid, conn);
	                 		break;
	        case "sqlserver": delSysRole_sqlserver(role_guid, conn);
							break;
	        default: delSysRole_mysql(role_guid, conn);
	        				break;
		}
	}

	public SYS_DM getRoleSyss(String role_guid, String sys_id, String sys_name, int page_no,
			int page_size, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		SYS_DM returnDM = new SYS_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getRoleSyss_oracle(role_guid,sys_id,sys_name,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getRoleSyss_sqlserver(role_guid,sys_id,sys_name,page_no,page_size,conn);
							break;
	        default: returnDM = getRoleSyss_mysql(role_guid,sys_id,sys_name,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}

	public SYS_DM getRoleSyssLeft(String role_guid, String sys_id, String sys_name, int page_no,
			int page_size, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		SYS_DM returnDM = new SYS_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getRoleSyssLeft_oracle(role_guid,sys_id,sys_name,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getRoleSyssLeft_sqlserver(role_guid,sys_id,sys_name,page_no,page_size,conn);
							break;
	        default: returnDM = getRoleSyssLeft_mysql(role_guid,sys_id,sys_name,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}

	public void addRoleSyss(ROLE_MAIN role, List<BIZ_SYS> bizSysList, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  addRoleSyss_oracle(role,bizSysList,conn);
	                 		break;
	        case "sqlserver": addRoleSyss_sqlserver(role,bizSysList,conn);
							break;
	        default: addRoleSyss_mysql(role,bizSysList,conn);
	        				break;
		}
	}

	public void defaultSys(ROLE_SYS entity, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  defaultSys_oracle(entity,conn);
	                 		break;
	        case "sqlserver": defaultSys_sqlserver(entity,conn);
							break;
	        default: defaultSys_mysql(entity,conn);
	        				break;
		}
	}

	public void delRoleSys(String role_guid, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  delRoleSys_oracle(role_guid,conn);
	                 		break;
	        case "sqlserver": delRoleSys_sqlserver(role_guid,conn);
							break;
	        default: delRoleSys_mysql(role_guid,conn);
	        				break;
		}
	}

	public List<BIZ_SYS> getSlRoleSyss(ROLE_SYS role, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		switch (dataSourceType) {
	        case "oracle":  returnList = getSlRoleSyss_oracle(role,conn);
	                 		break;
	        case "sqlserver": returnList = getSlRoleSyss_sqlserver(role,conn);
							break;
	        default: returnList = getSlRoleSyss_mysql(role,conn);
	        				break;
		}
		return returnList;
	}

	public List<FUN_NODE> getSysFuns(ROLE_SYS entity, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		switch (dataSourceType) {
	        case "oracle":  nodeTreeList = getSysFuns_oracle(entity,conn);
	                 		break;
	        case "sqlserver": nodeTreeList = getSysFuns_sqlserver(entity,conn);
							break;
	        default: nodeTreeList = getSysFuns_mysql(entity,conn);
	        				break;
		}
		return nodeTreeList;
	}

	public void addRoleFuns(String user_id, String client_id, String role_guid,
			String sys_guid, String[] funGuidArray, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  addRoleFuns_oracle(user_id,client_id,role_guid,sys_guid,funGuidArray,conn);
	                 		break;
	        case "sqlserver": addRoleFuns_sqlserver(user_id,client_id,role_guid,sys_guid,funGuidArray,conn);
							break;
	        default: addRoleFuns_mysql(user_id,client_id,role_guid,sys_guid,funGuidArray,conn);
	        				break;
		}
	}

	public void delRoleFuns(String role_guid, String sys_guid, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  delRoleFuns_oracle(role_guid,sys_guid,conn);
	                 		break;
	        case "sqlserver": delRoleFuns_sqlserver(role_guid,sys_guid,conn);
							break;
	        default: delRoleFuns_mysql(role_guid,sys_guid,conn);
	        				break;
		}
	}

	public List<ROLE_MAIN> getSlSysRoles(Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		List<ROLE_MAIN> returnList = new ArrayList<ROLE_MAIN>();
		switch (dataSourceType) {
	        case "oracle":  returnList = getSlSysRoles_oracle(conn);
	                 		break;
	        case "sqlserver": returnList = getSlSysRoles_sqlserver(conn);
							break;
	        default: returnList = getSlSysRoles_mysql(conn);
	        				break;
		}
		return returnList;
	}

	public PRIVILEGE_DM getRolePrivileges(String role_guid,
			String privilege_id, String privilege_name, String doc_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getRolePrivileges_oracle(role_guid,privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getRolePrivileges_sqlserver(role_guid,privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
							break;
	        default: returnDM = getRolePrivileges_mysql(role_guid,privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}

	public void delRolePrivilege(String[] roleprivilegeguidArray,
			Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle": delRolePrivilege_oracle(roleprivilegeguidArray,conn);
	                 		break;
	        case "sqlserver": delRolePrivilege_sqlserver(roleprivilegeguidArray,conn);
							break;
	        default: delRolePrivilege_mysql(roleprivilegeguidArray,conn);
	        				break;
		}
	}

	public PRIVILEGE_DM getRolePrivilegesLeft(String role_guid,
			String privilege_id, String privilege_name, String doc_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getRolePrivilegesLeft_oracle(role_guid,privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getRolePrivilegesLeft_sqlserver(role_guid,privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
							break;
	        default: returnDM = getRolePrivilegesLeft_mysql(role_guid,privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}

	public void addRolePrivileges(String user_guid, String client_guid, String role_guid,
			String[] privilegeguidArray, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle": addRolePrivileges_oracle(user_guid,client_guid,role_guid,privilegeguidArray,conn);
	                 		break;
	        case "sqlserver": addRolePrivileges_sqlserver(user_guid,client_guid,role_guid,privilegeguidArray,conn);
							break;
	        default: addRolePrivileges_mysql(user_guid,client_guid,role_guid,privilegeguidArray,conn);
	        				break;
		}
	}

	private void addSysRole_mysql(ROLE_MAIN entity, Connection conn) throws Exception {
		entity.setRole_main_id(Decode(entity.getRole_main_id()));
		entity.setRole_name(Decode(entity.getRole_name()));
		entity.setRole_desc(Decode(entity.getRole_desc()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT 1 FROM ROLE_MAIN WHERE IS_DELETED=0 AND ROLE_MAIN_ID=? LIMIT 1");
			pstmt.setString(1, entity.getRole_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("角色代码 "+entity.getRole_main_id()+" 已存在");
			}
			
			String iSQL = "INSERT INTO ROLE_MAIN (ROLE_MAIN_GUID,ROLE_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_NAME,ROLE_DESC) VALUES(?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			int index=0;
			pstmt.setString(++index, UUID.randomUUID().toString());
			pstmt.setString(++index, entity.getRole_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, entity.getCreated_by());
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, entity.getUpdated_by());
			pstmt.setInt(++index, 0);
			pstmt.setString(++index, entity.getClient_guid());
			pstmt.setString(++index, entity.getRole_name());
			pstmt.setString(++index, entity.getRole_desc());
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

	private ROLE_DM getSysRoles_mysql(String role_id, String role_name,
			int page_no, int page_size, Connection conn) throws Exception {
		role_id=Decode(role_id);
		role_name=Decode(role_name);
		ROLE_DM returnDM = new ROLE_DM();
		List<ROLE_MAIN> returnList = new ArrayList<ROLE_MAIN>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT ROLE_MAIN_GUID, ROLE_MAIN_ID, ROLE_NAME, ROLE_DESC FROM ROLE_MAIN";
			String sWhereSQL = " where IS_DELETED=0";
			if(role_id!=null&&role_id.length()>0){
				sWhereSQL+=" and ROLE_MAIN_ID LIKE ?";
			}
			if(role_name!=null&&role_name.length()>0){
				sWhereSQL+=" and ROLE_NAME LIKE ?";
			}
			pstmt = conn.prepareStatement(sSQL+sWhereSQL+" ORDER BY CREATED_DT DESC LIMIT "+ (page_no-1)*page_size + "," + page_size);
			int index=0;
			if(role_id!=null&&role_id.length()>0){
				pstmt.setString(++index, role_id+"%");
			}
			if(role_name!=null&&role_name.length()>0){
				pstmt.setString(++index, "%"+role_name+"%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				ROLE_MAIN entity = new ROLE_MAIN();
				entity.setRole_main_guid(rs.getString(1));
				entity.setRole_main_id(Encode(rs.getString(2)));
				entity.setRole_name(Encode(rs.getString(3)));
				entity.setRole_desc(Encode(rs.getString(4)));
				
				returnList.add(entity);
			}
			returnDM.setRoleListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("SELECT COUNT(ROLE_MAIN_GUID) FROM ROLE_MAIN"+sWhereSQL);
			index=0;
			if(role_id!=null&&role_id.length()>0){
				pstmt.setString(++index, role_id+"%");
			}
			if(role_name!=null&&role_name.length()>0){
				pstmt.setString(++index, "%"+role_name+"%");
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

	private ROLE_MAIN getSysRole_mysql(String role_guid, Connection conn) throws Exception {
		ROLE_MAIN returnEntity = new ROLE_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT ROLE_MAIN_GUID, ROLE_MAIN_ID, ROLE_NAME, ROLE_DESC FROM ROLE_MAIN WHERE IS_DELETED=0 AND ROLE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, role_guid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setRole_main_guid(rs.getString(1));
				returnEntity.setRole_main_id(Encode(rs.getString(2)));
				returnEntity.setRole_name(Encode(rs.getString(3)));
				returnEntity.setRole_desc(Encode(rs.getString(4)));
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

	private void updateSysRole_mysql(ROLE_MAIN entity, Connection conn) throws Exception {
		entity.setRole_main_id(Decode(entity.getRole_main_id()));
		entity.setRole_name(Decode(entity.getRole_name()));
		entity.setRole_desc(Decode(entity.getRole_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update ROLE_MAIN set UPDATED_DT=?, UPDATED_BY=?, ROLE_NAME=?, ROLE_DESC=? where IS_DELETED=0 AND ROLE_MAIN_GUID=? ";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_name());
			pstmt.setString(4, entity.getRole_desc());
			pstmt.setString(5, entity.getRole_main_guid());
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

	private void delSysRole_mysql(String role_guid, Connection conn) throws Exception {
		PreparedStatement rolePstmt = null;
		PreparedStatement sysPstmt = null;
		PreparedStatement funPstmt = null;
		try{
			String dRoleSQL = "delete from ROLE_MAIN where IS_DELETED=0 and ROLE_MAIN_GUID=?";
			String dSysSQL = "delete from ROLE_SYS where IS_DELETED=0 and ROLE_GUID=?";
			String dFunSQL = "delete from ROLE_FUN where IS_DELETED=0 and ROLE_GUID=?";
			conn.setAutoCommit(false);
			funPstmt = conn.prepareStatement(dRoleSQL);
			sysPstmt = conn.prepareStatement(dSysSQL);
			rolePstmt = conn.prepareStatement(dFunSQL);

			funPstmt.setString(1, role_guid);
			funPstmt.addBatch();
			
			sysPstmt.setString(1, role_guid);
			sysPstmt.addBatch();
			
			rolePstmt.setString(1, role_guid);
			rolePstmt.addBatch();

			funPstmt.executeBatch();
			sysPstmt.executeBatch();
			rolePstmt.executeBatch();
			
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(funPstmt!=null)
				funPstmt.close();
			if(sysPstmt!=null)
				sysPstmt.close();
			if(rolePstmt!=null)
				rolePstmt.close();
			if(conn!=null&&!conn.getAutoCommit())
				conn.setAutoCommit(true);
		}
	}

	private SYS_DM getRoleSyss_mysql(String role_guid, String sys_id,
			String sys_name, int page_no, int page_size, Connection conn) throws Exception {
		sys_id=Decode(sys_id);
		sys_name=Decode(sys_name);
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL="select t.ROLE_SYS_GUID,t.SYS_GUID, t.IS_DEFAULT_SYS,t1.BIZ_SYS_ID,t1.SYS_NAME from ROLE_SYS as t inner join BIZ_SYS as t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0";
			String sSQLWhere = "";
			if(sys_id!=null&&sys_id.length()>0){
				sSQLWhere+=" and t1.BIZ_SYS_ID like ?";
			}
			if(sys_name!=null&&sys_name.length()>0){
				sSQLWhere+=" and t1.SYS_NAME like ?";
			}
			sSQL=sSQL+sSQLWhere+" where t.IS_DELETED=0 AND t.ROLE_GUID=? order by t.SYS_GUID desc limit "+ (page_no-1)*page_size + "," + page_size;
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			pstmt.setString(++index, role_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setRole_sys_guid(rs.getString(1));
				entity.setBiz_sys_guid(rs.getString(2));
				entity.setIs_default_sys(rs.getInt(3));
				entity.setBiz_sys_id(Encode(rs.getString(4)));
				entity.setSys_name(Encode(rs.getString(5)));
				
				returnList.add(entity);
			}
			pstmt.close();
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("select count(*) from ROLE_SYS as t inner join BIZ_SYS as t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0"+sSQLWhere+" where t.IS_DELETED=0 AND t.ROLE_GUID=?");
			index = 0;
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			pstmt.setString(++index, role_guid);
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

	private SYS_DM getRoleSyssLeft_mysql(String role_guid, String sys_id,
			String sys_name, int page_no, int page_size, Connection conn) throws Exception {
		sys_id=Decode(sys_id);
		sys_name=Decode(sys_name);
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL="select t.BIZ_SYS_GUID,t.BIZ_SYS_ID,t.SYS_NAME,t1.ROLE_SYS_GUID from BIZ_SYS as t left join ROLE_SYS as t1" +
					" on t.BIZ_SYS_GUID=t1.SYS_GUID and t1.IS_DELETED=0 AND t1.ROLE_GUID=?";
			String sSQLWhere = " where t.IS_DELETED=0 and t1.ROLE_SYS_GUID is null";
			if(sys_id!=null&&sys_id.length()>0){
				sSQLWhere+=" and t.BIZ_SYS_ID like ?";
			}
			if(sys_name!=null&&sys_name.length()>0){
				sSQLWhere+=" and t.SYS_NAME like ?";
			}
			sSQL=sSQL+sSQLWhere+" order by t.BIZ_SYS_GUID desc limit "+ (page_no-1)*page_size + "," + page_size;
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			pstmt.setString(++index, role_guid);
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(Encode(rs.getString(2)));
				entity.setSys_name(Encode(rs.getString(3)));
				
				returnList.add(entity);
			}
			pstmt.close();
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("select count(*) from BIZ_SYS as t left join ROLE_SYS as t1" +
					" on t.BIZ_SYS_GUID=t1.SYS_GUID and t1.IS_DELETED=0 AND t1.ROLE_GUID=?"+sSQLWhere);
			index = 0;
			pstmt.setString(++index, role_guid);
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
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

	private void addRoleSyss_mysql(ROLE_MAIN role, List<BIZ_SYS> bizSysList,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String iSQL = "insert into ROLE_SYS (ROLE_SYS_GUID,ROLE_SYS_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,SYS_GUID,IS_DEFAULT_SYS) values (?,?,?,?,?,?,?,?,?,?,?)";
			conn.setAutoCommit(false);
			for(BIZ_SYS entity : bizSysList){
				pstmt = conn.prepareStatement(iSQL);
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, role.getCreated_by());
				pstmt.setLong(5, lDate);
				pstmt.setString(6, role.getUpdated_by());
				pstmt.setInt(7, 0);
				pstmt.setString(8, role.getClient_guid());
				pstmt.setString(9, role.getRole_main_guid());
				pstmt.setString(10, entity.getBiz_sys_guid());
				pstmt.setInt(11, 0);
				pstmt.execute();
				pstmt.close();
			}
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void defaultSys_mysql(ROLE_SYS entity, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			String cSQL = "select t.SYS_GUID, t1.SYS_NAME from ROLE_SYS as t inner join BIZ_SYS as t1 on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0 where t.IS_DELETED=0 and t.ROLE_SYS_GUID=? and t.IS_DEFAULT_SYS=1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, entity.getRole_sys_guid());
			rs = pstmt.executeQuery();
			String sysGuid="";
			String sysName="";
			if(rs.next()){
				sysGuid = rs.getString(1);
				sysName = rs.getString(2);
			}
			pstmt.close();
			if(sysGuid!=null&&sysGuid.length()>0){
				throw new Exception("系统 "+(sysName==null?"":sysName)+" 已是默认系统");
			}
			
			String uSQL="update ROLE_SYS set UPDATED_DT=?, UPDATED_BY=?, IS_DEFAULT_SYS=0 where IS_DELETED=0 and ROLE_GUID =? AND IS_DEFAULT_SYS=1";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_guid());
			pstmt.execute();
			pstmt.close();
			
			uSQL = "update ROLE_SYS set UPDATED_DT=?, UPDATED_BY=?, IS_DEFAULT_SYS=1 where IS_DELETED=0 and ROLE_GUID =? and ROLE_SYS_GUID=? ";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_guid());
			pstmt.setString(4, entity.getRole_sys_guid());
			pstmt.execute();
			pstmt.close();
			
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void delRoleSys_mysql(String role_guid, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String dSQL = "delete from ROLE_SYS where IS_DELETED=0 and ROLE_SYS_GUID=?";
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dSQL);

			pstmt.setString(1, role_guid);
			pstmt.addBatch();

			pstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private List<BIZ_SYS> getSlRoleSyss_mysql(ROLE_SYS role, Connection conn) throws Exception {
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL="select t.SYS_GUID,t1.BIZ_SYS_ID,t1.SYS_NAME from ROLE_SYS as t inner join BIZ_SYS as t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0 where t.IS_DELETED=0 AND t.ROLE_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, role.getRole_guid());
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(Encode(rs.getString(2)));
				entity.setSys_name(Encode(rs.getString(3)));
				
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

	private List<FUN_NODE> getSysFuns_mysql(ROLE_SYS entity, Connection conn) throws Exception {
		List<FUN_NODE> nodeList = new ArrayList<FUN_NODE>();
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "select t1.ROLE_FUN_GUID,t.FUN_SEQNO,t.FUN_MAIN_GUID,t.FUN_MAIN_ID,t.FUN_NAME,t.FUN_PARAM,t.FUN_URL,t.NODE_IMG,t.FUN_DESC,t.BIZ_SYS_GUID from FUN_MAIN as t left join ROLE_FUN as t1" +
					" on t.FUN_MAIN_GUID = t1.FUN_GUID and t1.IS_DELETED=0 and t1.ROLE_GUID =?" +
					" where t.IS_DELETED=0 and t.BIZ_SYS_GUID=? order by t.FUN_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, entity.getRole_guid());
			pstmt.setString(2, entity.getSys_guid());
			rs = pstmt.executeQuery();
			while(rs.next()){
				index = 0;
				FUN_NODE node =new FUN_NODE();
				String roleFunGuid = rs.getString(++index);
				if(roleFunGuid==null){
					node.setSelect(false);
				}else{
					node.setSelect(true);
				}
				node.setFun_seqno(rs.getString(++index));
				node.setKey(rs.getString(++index));
				node.setFun_id(rs.getString(++index));
				node.setTitle(rs.getString(++index));
				node.setFun_name(node.getTitle());
				node.setFun_param(rs.getString(++index));
				node.setFun_url(rs.getString(++index));
				node.setHref(node.getFun_url());
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setFun_desc(rs.getString(++index));
				node.setBiz_sys_guid(rs.getString(++index));
				node.setExpand(true);
				
				nodeList.add(node);
			}
			pstmt.close();
			
			FUN_NODE currentNode = new FUN_NODE();
			
			int currentDepth = 0;
			for(FUN_NODE node :nodeList) {
	
				int nextDepth = (node.getFun_seqno().length() + 2) / 3;
				
				if(nextDepth - currentDepth > 1) {
	                node.setFun_seqno(node.getFun_seqno().substring(0, 3 * (currentDepth + 1)));
	                nextDepth = currentDepth + 1;
	            }
				
				if(nextDepth <= 1) {
	                currentDepth = 1;
	                nodeTreeList.add(node);
	            }else{
	                if(nextDepth == currentDepth){
	                	node.setParent(currentNode.getParent());
	                	currentNode.getParent().getChildren().add(node);
	                }else if(nextDepth > currentDepth){
	                    currentDepth++;
	                    node.setParent(currentNode);
	                    currentNode.getChildren().add(node);
	                }else{
	                    while (nextDepth < currentDepth){
	                    	currentNode = currentNode.getParent();
	                        currentDepth--;
	                    }
	                    node.setParent(currentNode.getParent());
	                    currentNode.getParent().getChildren().add(node);
	                }
	            }
				currentNode = node;
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return nodeTreeList;
	}

	private void addRoleFuns_mysql(String user_id, String client_id,
			String role_guid, String sys_guid, String[] funGuidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			
			String dSQL = "delete from ROLE_FUN where IS_DELETED=0 AND ROLE_GUID=? AND SYS_GUID=?";
			String iSQL = "insert into ROLE_FUN (ROLE_FUN_GUID,ROLE_FUN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,FUN_GUID,SYS_GUID) values (?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(dSQL);
			pstmt.setString(1, role_guid);
			pstmt.setString(2, sys_guid);
			pstmt.execute();
			pstmt.close();
			
			for(String funGuid : funGuidArray){
				pstmt = conn.prepareStatement(iSQL);
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, user_id);
				pstmt.setLong(5, lDate);
				pstmt.setString(6, user_id);
				pstmt.setInt(7, 0);
				pstmt.setString(8, client_id);
				pstmt.setString(9, role_guid);
				pstmt.setString(10, funGuid);
				pstmt.setString(11, sys_guid);
				pstmt.execute();
				pstmt.close();
			}
			conn.commit();
			
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void delRoleFuns_mysql(String role_guid, String sys_guid,
			Connection conn) throws SQLException {
		String dSQL = "delete from ROLE_FUN where IS_DELETED=0 AND ROLE_GUID=? AND SYS_GUID=?";
		PreparedStatement pstmt = conn.prepareStatement(dSQL);
		pstmt.setString(1, role_guid);
		pstmt.setString(2, sys_guid);
		pstmt.execute();
		pstmt.close();
	}

	private List<ROLE_MAIN> getSlSysRoles_mysql(Connection conn) throws Exception {
		List<ROLE_MAIN> returnList = new ArrayList<ROLE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String  sSQL = "select ROLE_MAIN_GUID, ROLE_NAME from ROLE_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ROLE_MAIN entity = new ROLE_MAIN();
				entity.setRole_main_guid(Encode(rs.getString(1)));
				entity.setRole_name(Encode(rs.getString(2)));
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

	private PRIVILEGE_DM getRolePrivileges_mysql(String role_guid,
			String privilege_id, String privilege_name, String doc_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select t.ROLE_PRIVILEGE_GUID, t1.PRIVILEGE_MAIN_GUID, t1.PRIVILEGE_MAIN_ID, t1.PRIVILEGE_NAME, t1.PRIVILEGE_DESC, t2.DOC_MAIN_ID, t2.DOC_NAME from ROLE_PRIVILEGE as t inner join PRIVILEGE_MAIN as t1 on t1.IS_DELETED=0 and t.PRIVILEGE_GUID = t1.PRIVILEGE_MAIN_GUID";
			
			if(doc_guid!=null&&doc_guid.length()!=0){
				sSQL +=" and t1.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				sSQL +=" and t1.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				sSQL +=" and t1.PRIVILEGE_NAME like ?";
			}
			sSQL +=" inner join DOC_MAIN as t2 on t2.IS_DELETED=0 and t1.DOC_GUID = t2.DOC_MAIN_GUID where t.IS_DELETED=0 and t.ROLE_GUID=?";
			pstmt = conn.prepareStatement(sSQL+" order by t.CREATED_DT desc limit "+ (page_no-1)*page_size + "," + page_size);
			int index = 0;
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			pstmt.setString(++index, role_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				PRIVILEGE_MAIN entity = new PRIVILEGE_MAIN();
				entity.setRole_privilege_guid(rs.getString(1));
				entity.setPrivilege_main_guid(rs.getString(2));
				entity.setPrivilege_main_id(Encode(rs.getString(3)));
				entity.setPrivilege_name(Encode(rs.getString(4)));
				entity.setPrivilege_desc(Encode(rs.getString(5)));
				entity.setDoc_id(Encode(rs.getString(6)));
				entity.setDoc_name(Encode(rs.getString(7)));
				
				returnList.add(entity);
			}
			returnDM.setPrivilegeListData(returnList);
			pstmt.close();
			
			sSQL = "select count(*) from ROLE_PRIVILEGE as t inner join PRIVILEGE_MAIN as t1 on t1.IS_DELETED=0 and t.PRIVILEGE_GUID = t1.PRIVILEGE_MAIN_GUID";
			if(doc_guid!=null&&doc_guid.length()!=0){
				sSQL +=" and t1.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				sSQL +=" and t1.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				sSQL +=" and t1.PRIVILEGE_NAME like ?";
			}
			sSQL +=" inner join DOC_MAIN as t2 on t2.IS_DELETED=0 and t1.DOC_GUID = t2.DOC_MAIN_GUID where t.IS_DELETED=0 and t.ROLE_GUID=?";
			
			pstmt = conn.prepareStatement(sSQL);
			index = 0;
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			pstmt.setString(++index, role_guid);
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

	private void delRolePrivilege_mysql(String[] roleprivilegeguidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from ROLE_PRIVILEGE where IS_DELETED=0 and ROLE_PRIVILEGE_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String roleprivilegeguid : roleprivilegeguidArray){
				pstmt.setString(1, roleprivilegeguid);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private PRIVILEGE_DM getRolePrivilegesLeft_mysql(String role_guid,
			String privilege_id, String privilege_name, String doc_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select t.PRIVILEGE_MAIN_GUID, t.PRIVILEGE_MAIN_ID, t.PRIVILEGE_NAME, t.PRIVILEGE_DESC, t2.DOC_MAIN_ID, t2.DOC_NAME from PRIVILEGE_MAIN as t left join ROLE_PRIVILEGE as t1 on t1.IS_DELETED=0 and t1.ROLE_GUID=? and t.PRIVILEGE_MAIN_GUID = t1.PRIVILEGE_GUID inner join DOC_MAIN as t2 on t2.IS_DELETED=0 and t.DOC_GUID = t2.DOC_MAIN_GUID";
			String sSQLWhere = " where t.IS_DELETED=0 and t1.ROLE_PRIVILEGE_GUID is null";
			if(doc_guid!=null&&doc_guid.length()!=0){
				sSQLWhere +=" and t.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				sSQLWhere +=" and t.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				sSQLWhere +=" and t.PRIVILEGE_NAME like ?";
			}
			pstmt = conn.prepareStatement(sSQL+sSQLWhere+" order by t.CREATED_DT desc limit "+ (page_no-1)*page_size + "," + page_size);
			int index = 0;
			pstmt.setString(++index, role_guid);
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				PRIVILEGE_MAIN entity = new PRIVILEGE_MAIN();
				entity.setPrivilege_main_guid(rs.getString(1));
				entity.setPrivilege_main_id(Encode(rs.getString(2)));
				entity.setPrivilege_name(Encode(rs.getString(3)));
				entity.setPrivilege_desc(Encode(rs.getString(4)));
				entity.setDoc_id(Encode(rs.getString(5)));
				entity.setDoc_name(Encode(rs.getString(6)));
				
				returnList.add(entity);
			}
			returnDM.setPrivilegeListData(returnList);
			pstmt.close();
			
			sSQL = "select count(*) from PRIVILEGE_MAIN as t left join ROLE_PRIVILEGE as t1 on t1.IS_DELETED=0 and t1.ROLE_GUID=? and t.PRIVILEGE_MAIN_GUID = t1.PRIVILEGE_GUID inner join DOC_MAIN as t2 on t2.IS_DELETED=0 and t.DOC_GUID = t2.DOC_MAIN_GUID";			
			pstmt = conn.prepareStatement(sSQL+sSQLWhere);
			index = 0;
			pstmt.setString(++index, role_guid);
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
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

	private void addRolePrivileges_mysql(String user_guid, String client_guid,
			String role_guid, String[] privilegeguidArray, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String iSQL = "insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_GUID,ROLE_PRIVILEGE_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,PRIVILEGE_GUID) values (?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			for(String privilegeguid : privilegeguidArray){
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, user_guid);
				pstmt.setLong(5, lDate);
				pstmt.setString(6, user_guid);
				pstmt.setInt(7, 0);
				pstmt.setString(8, client_guid);
				pstmt.setString(9, role_guid);
				pstmt.setString(10, privilegeguid);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void addSysRole_oracle(ROLE_MAIN entity, Connection conn) throws Exception {
		entity.setRole_main_id(Decode(entity.getRole_main_id()));
		entity.setRole_name(Decode(entity.getRole_name()));
		entity.setRole_desc(Decode(entity.getRole_desc()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT 1 FROM ROLE_MAIN WHERE IS_DELETED=0 AND ROLE_MAIN_ID=? and ROWNUM=1");
			pstmt.setString(1, entity.getRole_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("角色代码 "+entity.getRole_main_id()+" 已存在");
			}
			
			String iSQL = "INSERT INTO ROLE_MAIN (ROLE_MAIN_GUID,ROLE_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_NAME,ROLE_DESC) VALUES(?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			int index=0;
			pstmt.setString(++index, UUID.randomUUID().toString());
			pstmt.setString(++index, entity.getRole_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, entity.getCreated_by());
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, entity.getUpdated_by());
			pstmt.setInt(++index, 0);
			pstmt.setString(++index, entity.getClient_guid());
			pstmt.setString(++index, entity.getRole_name());
			pstmt.setString(++index, entity.getRole_desc());
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

	private ROLE_DM getSysRoles_oracle(String role_id, String role_name,
			int page_no, int page_size, Connection conn) throws Exception {
		role_id=Decode(role_id);
		role_name=Decode(role_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;;
		
		ROLE_DM returnDM = new ROLE_DM();
		List<ROLE_MAIN> returnList = new ArrayList<ROLE_MAIN>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String subSQL = "select ROLE_MAIN_GUID, ROLE_MAIN_ID, ROLE_NAME, ROLE_DESC from ROLE_MAIN";
			String subWhereSQL = " where IS_DELETED=0";
			if(role_id!=null&&role_id.length()>0){
				subWhereSQL+=" and ROLE_MAIN_ID LIKE ?";
			}
			if(role_name!=null&&role_name.length()>0){
				subWhereSQL+=" and ROLE_NAME LIKE ?";
			}
			subSQL = subSQL+subWhereSQL+" order by CREATED_DT desc ";
			String sSQL = "select B.* from (select A.*, ROWNUM as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index=0;
			if(role_id!=null&&role_id.length()>0){
				pstmt.setString(++index, role_id+"%");
			}
			if(role_name!=null&&role_name.length()>0){
				pstmt.setString(++index, "%"+role_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				ROLE_MAIN entity = new ROLE_MAIN();
				entity.setRole_main_guid(rs.getString(1));
				entity.setRole_main_id(Encode(rs.getString(2)));
				entity.setRole_name(Encode(rs.getString(3)));
				entity.setRole_desc(Encode(rs.getString(4)));
				
				returnList.add(entity);
			}
			returnDM.setRoleListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("select count(ROLE_MAIN_GUID) from ROLE_MAIN"+subWhereSQL);
			index=0;
			if(role_id!=null&&role_id.length()>0){
				pstmt.setString(++index, role_id+"%");
			}
			if(role_name!=null&&role_name.length()>0){
				pstmt.setString(++index, "%"+role_name+"%");
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

	private ROLE_MAIN getSysRole_oracle(String role_guid, Connection conn) throws Exception {
		ROLE_MAIN returnEntity = new ROLE_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT ROLE_MAIN_GUID, ROLE_MAIN_ID, ROLE_NAME, ROLE_DESC FROM ROLE_MAIN WHERE IS_DELETED=0 AND ROLE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, role_guid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setRole_main_guid(rs.getString(1));
				returnEntity.setRole_main_id(Encode(rs.getString(2)));
				returnEntity.setRole_name(Encode(rs.getString(3)));
				returnEntity.setRole_desc(Encode(rs.getString(4)));
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

	private void updateSysRole_oracle(ROLE_MAIN entity, Connection conn) throws Exception {
		entity.setRole_main_id(Decode(entity.getRole_main_id()));
		entity.setRole_name(Decode(entity.getRole_name()));
		entity.setRole_desc(Decode(entity.getRole_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update ROLE_MAIN set UPDATED_DT=?, UPDATED_BY=?, ROLE_NAME=?, ROLE_DESC=? where IS_DELETED=0 AND ROLE_MAIN_GUID=? ";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_name());
			pstmt.setString(4, entity.getRole_desc());
			pstmt.setString(5, entity.getRole_main_guid());
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

	private void delSysRole_oracle(String role_guid, Connection conn) throws Exception {
		PreparedStatement rolePstmt = null;
		PreparedStatement sysPstmt = null;
		PreparedStatement funPstmt = null;
		try{
			String dRoleSQL = "delete from ROLE_MAIN where IS_DELETED=0 and ROLE_MAIN_GUID=?";
			String dSysSQL = "delete from ROLE_SYS where IS_DELETED=0 and ROLE_GUID=?";
			String dFunSQL = "delete from ROLE_FUN where IS_DELETED=0 and ROLE_GUID=?";
			conn.setAutoCommit(false);
			funPstmt = conn.prepareStatement(dRoleSQL);
			sysPstmt = conn.prepareStatement(dSysSQL);
			rolePstmt = conn.prepareStatement(dFunSQL);
			
			funPstmt.setString(1, role_guid);
			funPstmt.addBatch();
			
			sysPstmt.setString(1, role_guid);
			sysPstmt.addBatch();
			
			rolePstmt.setString(1, role_guid);
			rolePstmt.addBatch();

			funPstmt.executeBatch();
			sysPstmt.executeBatch();
			rolePstmt.executeBatch();
			
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(funPstmt!=null)
				funPstmt.close();
			if(sysPstmt!=null)
				sysPstmt.close();
			if(rolePstmt!=null)
				rolePstmt.close();
			if(conn!=null&&!conn.getAutoCommit())
				conn.setAutoCommit(true);
		}
	}

	private SYS_DM getRoleSyss_oracle(String role_guid, String sys_id,
			String sys_name, int page_no, int page_size, Connection conn) throws Exception {
		sys_id=Decode(sys_id);
		sys_name=Decode(sys_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL="select t.ROLE_SYS_GUID,t.SYS_GUID, t.IS_DEFAULT_SYS,t1.BIZ_SYS_ID,t1.SYS_NAME from ROLE_SYS t inner join BIZ_SYS t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0";
			String subSQLWhere = "";
			if(sys_id!=null&&sys_id.length()>0){
				subSQLWhere+=" and t1.BIZ_SYS_ID like ?";
			}
			if(sys_name!=null&&sys_name.length()>0){
				subSQLWhere+=" and t1.SYS_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere+" where t.IS_DELETED=0 AND t.ROLE_GUID=? order by t.SYS_GUID desc";
			String sSQL = "select B.* from (select A.*, ROWNUM as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			pstmt.setString(++index, role_guid);
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setRole_sys_guid(rs.getString(1));
				entity.setBiz_sys_guid(rs.getString(2));
				entity.setIs_default_sys(rs.getInt(3));
				entity.setBiz_sys_id(Encode(rs.getString(4)));
				entity.setSys_name(Encode(rs.getString(5)));
				
				returnList.add(entity);
			}
			pstmt.close();
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("select count(*) from ROLE_SYS t inner join BIZ_SYS t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0"+subSQLWhere+" where t.IS_DELETED=0 AND t.ROLE_GUID=?");
			index = 0;
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			pstmt.setString(++index, role_guid);
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

	private SYS_DM getRoleSyssLeft_oracle(String role_guid, String sys_id,
			String sys_name, int page_no, int page_size, Connection conn) throws Exception {
		sys_id=Decode(sys_id);
		sys_name=Decode(sys_name);

		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL="select t.BIZ_SYS_GUID,t.BIZ_SYS_ID,t.SYS_NAME,t1.ROLE_SYS_GUID from BIZ_SYS t left join ROLE_SYS t1" +
					" on t.BIZ_SYS_GUID=t1.SYS_GUID and t1.IS_DELETED=0 AND t1.ROLE_GUID=?";
			String subSQLWhere = " where t.IS_DELETED=0 and t1.ROLE_SYS_GUID is null";
			if(sys_id!=null&&sys_id.length()>0){
				subSQLWhere+=" and t.BIZ_SYS_ID like ?";
			}
			if(sys_name!=null&&sys_name.length()>0){
				subSQLWhere+=" and t.SYS_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere+" order by t.BIZ_SYS_GUID desc";
			String sSQL = "select B.* from (select A.*, ROWNUM as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			pstmt.setString(++index, role_guid);
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(Encode(rs.getString(2)));
				entity.setSys_name(Encode(rs.getString(3)));
				
				returnList.add(entity);
			}
			pstmt.close();
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("select count(*) from BIZ_SYS t left join ROLE_SYS t1" +
					" on t.BIZ_SYS_GUID=t1.SYS_GUID and t1.IS_DELETED=0 AND t1.ROLE_GUID=?"+subSQLWhere);
			index = 0;
			pstmt.setString(++index, role_guid);
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
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

	private void addRoleSyss_oracle(ROLE_MAIN role, List<BIZ_SYS> bizSysList,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String iSQL = "insert into ROLE_SYS (ROLE_SYS_GUID,ROLE_SYS_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,SYS_GUID,IS_DEFAULT_SYS) values (?,?,?,?,?,?,?,?,?,?,?)";
			conn.setAutoCommit(false);
			for(BIZ_SYS entity : bizSysList){
				pstmt = conn.prepareStatement(iSQL);
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, role.getCreated_by());
				pstmt.setLong(5, lDate);
				pstmt.setString(6, role.getUpdated_by());
				pstmt.setInt(7, 0);
				pstmt.setString(8, role.getClient_guid());
				pstmt.setString(9, role.getRole_main_guid());
				pstmt.setString(10, entity.getBiz_sys_guid());
				pstmt.setInt(11, 0);
				pstmt.execute();
				pstmt.close();
			}
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void defaultSys_oracle(ROLE_SYS entity, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			String cSQL = "select t.SYS_GUID, t1.SYS_NAME from ROLE_SYS t inner join BIZ_SYS t1 on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0 where t.IS_DELETED=0 and t.ROLE_SYS_GUID=? and t.IS_DEFAULT_SYS=1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, entity.getRole_sys_guid());
			rs = pstmt.executeQuery();
			String sysGuid="";
			String sysName="";
			if(rs.next()){
				sysGuid = rs.getString(1);
				sysName = rs.getString(2);
			}
			pstmt.close();
			if(sysGuid!=null&&sysGuid.length()>0){
				throw new Exception("系统 "+(sysName==null?"":sysName)+" 已是默认系统");
			}
			
			String uSQL="update ROLE_SYS set UPDATED_DT=?, UPDATED_BY=?, IS_DEFAULT_SYS=0 where IS_DELETED=0 and ROLE_GUID =? AND IS_DEFAULT_SYS=1";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_guid());
			pstmt.execute();
			pstmt.close();
			
			uSQL = "update ROLE_SYS set UPDATED_DT=?, UPDATED_BY=?, IS_DEFAULT_SYS=1 where IS_DELETED=0 and ROLE_GUID =? and ROLE_SYS_GUID=? ";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_guid());
			pstmt.setString(4, entity.getRole_sys_guid());
			pstmt.execute();
			pstmt.close();
			
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void delRoleSys_oracle(String role_guid, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String dSQL = "delete from ROLE_SYS where IS_DELETED=0 and ROLE_SYS_GUID=?";
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dSQL);
			
			pstmt.setString(1, role_guid);
			pstmt.addBatch();
				
			pstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private List<BIZ_SYS> getSlRoleSyss_oracle(ROLE_SYS role, Connection conn) throws Exception {
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL="select t.SYS_GUID,t1.BIZ_SYS_ID,t1.SYS_NAME from ROLE_SYS t inner join BIZ_SYS t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0 where t.IS_DELETED=0 AND t.ROLE_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, role.getRole_guid());
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(Encode(rs.getString(2)));
				entity.setSys_name(Encode(rs.getString(3)));
				
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

	private List<FUN_NODE> getSysFuns_oracle(ROLE_SYS entity, Connection conn) throws Exception {
		List<FUN_NODE> nodeList = new ArrayList<FUN_NODE>();
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "select t1.ROLE_FUN_GUID,t.FUN_SEQNO,t.FUN_MAIN_GUID,t.FUN_MAIN_ID,t.FUN_NAME,t.FUN_PARAM,t.FUN_URL,t.NODE_IMG,t.FUN_DESC,t.BIZ_SYS_GUID from FUN_MAIN t left join ROLE_FUN t1" +
					" on t.FUN_MAIN_GUID = t1.FUN_GUID and t1.IS_DELETED=0 and t1.ROLE_GUID =?" +
					" where t.IS_DELETED=0 and t.BIZ_SYS_GUID=? order by t.FUN_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, entity.getRole_guid());
			pstmt.setString(2, entity.getSys_guid());
			rs = pstmt.executeQuery();
			while(rs.next()){
				index = 0;
				FUN_NODE node =new FUN_NODE();
				String roleFunGuid = rs.getString(++index);
				if(roleFunGuid==null){
					node.setSelect(false);
				}else{
					node.setSelect(true);
				}
				node.setFun_seqno(rs.getString(++index));
				node.setKey(rs.getString(++index));
				node.setFun_id(rs.getString(++index));
				node.setTitle(rs.getString(++index));
				node.setFun_name(node.getTitle());
				node.setFun_param(rs.getString(++index));
				node.setFun_url(rs.getString(++index));
				node.setHref(node.getFun_url());
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setFun_desc(rs.getString(++index));
				node.setBiz_sys_guid(rs.getString(++index));
				node.setExpand(true);
				
				nodeList.add(node);
			}
			pstmt.close();
			
			FUN_NODE currentNode = new FUN_NODE();
			
			int currentDepth = 0;
			for(FUN_NODE node :nodeList) {

				int nextDepth = (node.getFun_seqno().length() + 2) / 3;
				
				if(nextDepth - currentDepth > 1) {
                    node.setFun_seqno(node.getFun_seqno().substring(0, 3 * (currentDepth + 1)));
                    nextDepth = currentDepth + 1;
                }
				
				if(nextDepth <= 1) {
                    currentDepth = 1;
                    nodeTreeList.add(node);
                }else{
                    if(nextDepth == currentDepth){
                    	node.setParent(currentNode.getParent());
                    	currentNode.getParent().getChildren().add(node);
                    }else if(nextDepth > currentDepth){
                        currentDepth++;
                        node.setParent(currentNode);
                        currentNode.getChildren().add(node);
                    }else{
                        while (nextDepth < currentDepth){
                        	currentNode = currentNode.getParent();
                            currentDepth--;
                        }
                        node.setParent(currentNode.getParent());
                        currentNode.getParent().getChildren().add(node);
                    }
                }
				currentNode = node;
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return nodeTreeList;
	}

	private void addRoleFuns_oracle(String user_id, String client_id,
			String role_guid, String sys_guid, String[] funGuidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			
			String dSQL = "delete from ROLE_FUN where IS_DELETED=0 AND ROLE_GUID=? AND SYS_GUID=?";
			String iSQL = "insert into ROLE_FUN (ROLE_FUN_GUID,ROLE_FUN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,FUN_GUID,SYS_GUID) values (?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(dSQL);
			pstmt.setString(1, role_guid);
			pstmt.setString(2, sys_guid);
			pstmt.execute();
			pstmt.close();
			
			for(String funGuid : funGuidArray){
				pstmt = conn.prepareStatement(iSQL);
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, user_id);
				pstmt.setLong(5, lDate);
				pstmt.setString(6, user_id);
				pstmt.setInt(7, 0);
				pstmt.setString(8, client_id);
				pstmt.setString(9, role_guid);
				pstmt.setString(10, funGuid);
				pstmt.setString(11, sys_guid);
				pstmt.execute();
				pstmt.close();
			}
			conn.commit();
			
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void delRoleFuns_oracle(String role_guid, String sys_guid,
			Connection conn) throws SQLException {
		String dSQL = "delete from ROLE_FUN where IS_DELETED=0 AND ROLE_GUID=? AND SYS_GUID=?";
		PreparedStatement pstmt = conn.prepareStatement(dSQL);
		pstmt.setString(1, role_guid);
		pstmt.setString(2, sys_guid);
		pstmt.execute();
		pstmt.close();
	}

	private List<ROLE_MAIN> getSlSysRoles_oracle(Connection conn) throws Exception {
		List<ROLE_MAIN> returnList = new ArrayList<ROLE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String  sSQL = "select ROLE_MAIN_GUID, ROLE_NAME from ROLE_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ROLE_MAIN entity = new ROLE_MAIN();
				entity.setRole_main_guid(Encode(rs.getString(1)));
				entity.setRole_name(Encode(rs.getString(2)));
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

	private PRIVILEGE_DM getRolePrivileges_oracle(String role_guid,
			String privilege_id, String privilege_name, String doc_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select t.ROLE_PRIVILEGE_GUID, t1.PRIVILEGE_MAIN_GUID, t1.PRIVILEGE_MAIN_ID, t1.PRIVILEGE_NAME, t1.PRIVILEGE_DESC, t2.DOC_MAIN_ID, t2.DOC_NAME from ROLE_PRIVILEGE t inner join PRIVILEGE_MAIN t1 on t1.IS_DELETED=0 and t.PRIVILEGE_GUID = t1.PRIVILEGE_MAIN_GUID";
			String subSQLWhere = "";
			if(doc_guid!=null&&doc_guid.length()!=0){
				subSQLWhere +=" and t1.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				subSQLWhere +=" and t1.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				subSQLWhere +=" and t1.PRIVILEGE_NAME like ?";
			}
			subSQL= subSQL+subSQLWhere+" inner join DOC_MAIN t2 on t2.IS_DELETED=0 and t1.DOC_GUID = t2.DOC_MAIN_GUID where t.IS_DELETED=0 and t.ROLE_GUID=? order by t.CREATED_DT desc";
			String sSQL = "select B.* from (select A.*, ROWNUM as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			pstmt.setString(++index, role_guid);
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				PRIVILEGE_MAIN entity = new PRIVILEGE_MAIN();
				entity.setRole_privilege_guid(rs.getString(1));
				entity.setPrivilege_main_guid(rs.getString(2));
				entity.setPrivilege_main_id(Encode(rs.getString(3)));
				entity.setPrivilege_name(Encode(rs.getString(4)));
				entity.setPrivilege_desc(Encode(rs.getString(5)));
				entity.setDoc_id(Encode(rs.getString(6)));
				entity.setDoc_name(Encode(rs.getString(7)));
				
				returnList.add(entity);
			}
			returnDM.setPrivilegeListData(returnList);
			pstmt.close();
			subSQL = "";
			subSQL = "select count(*) from ROLE_PRIVILEGE t inner join PRIVILEGE_MAIN t1 on t1.IS_DELETED=0 and t.PRIVILEGE_GUID = t1.PRIVILEGE_MAIN_GUID";
			subSQL = subSQL+subSQLWhere+" inner join DOC_MAIN t2 on t2.IS_DELETED=0 and t1.DOC_GUID = t2.DOC_MAIN_GUID where t.IS_DELETED=0 and t.ROLE_GUID=?";
			
			pstmt = conn.prepareStatement(subSQL);
			index = 0;
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			pstmt.setString(++index, role_guid);
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

	private void delRolePrivilege_oracle(String[] roleprivilegeguidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from ROLE_PRIVILEGE where IS_DELETED=0 and ROLE_PRIVILEGE_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String roleprivilegeguid : roleprivilegeguidArray){
				pstmt.setString(1, roleprivilegeguid);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private PRIVILEGE_DM getRolePrivilegesLeft_oracle(String role_guid,
			String privilege_id, String privilege_name, String doc_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select t.PRIVILEGE_MAIN_GUID, t.PRIVILEGE_MAIN_ID, t.PRIVILEGE_NAME, t.PRIVILEGE_DESC, t2.DOC_MAIN_ID, t2.DOC_NAME from PRIVILEGE_MAIN t left join ROLE_PRIVILEGE t1 on t1.IS_DELETED=0 and t1.ROLE_GUID=? and t.PRIVILEGE_MAIN_GUID = t1.PRIVILEGE_GUID inner join DOC_MAIN t2 on t2.IS_DELETED=0 and t.DOC_GUID = t2.DOC_MAIN_GUID";
			String subSQLWhere = " where t.IS_DELETED=0 and t1.ROLE_PRIVILEGE_GUID is null";
			if(doc_guid!=null&&doc_guid.length()!=0){
				subSQLWhere +=" and t.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				subSQLWhere +=" and t.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				subSQLWhere +=" and t.PRIVILEGE_NAME like ?";
			}
			subSQL=subSQL+subSQLWhere+" order by t.CREATED_DT desc";
			String sSQL = "select B.* from (select A.*, ROWNUM as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			pstmt.setString(++index, role_guid);
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				PRIVILEGE_MAIN entity = new PRIVILEGE_MAIN();
				entity.setPrivilege_main_guid(rs.getString(1));
				entity.setPrivilege_main_id(Encode(rs.getString(2)));
				entity.setPrivilege_name(Encode(rs.getString(3)));
				entity.setPrivilege_desc(Encode(rs.getString(4)));
				entity.setDoc_id(Encode(rs.getString(5)));
				entity.setDoc_name(Encode(rs.getString(6)));
				
				returnList.add(entity);
			}
			returnDM.setPrivilegeListData(returnList);
			pstmt.close();
			
			subSQL = "select count(*) from PRIVILEGE_MAIN t left join ROLE_PRIVILEGE t1 on t1.IS_DELETED=0 and t1.ROLE_GUID=? and t.PRIVILEGE_MAIN_GUID = t1.PRIVILEGE_GUID inner join DOC_MAIN t2 on t2.IS_DELETED=0 and t.DOC_GUID = t2.DOC_MAIN_GUID";			
			pstmt = conn.prepareStatement(subSQL+subSQLWhere);
			index = 0;
			pstmt.setString(++index, role_guid);
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
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

	private void addRolePrivileges_oracle(String user_guid, String client_guid,
			String role_guid, String[] privilegeguidArray, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String iSQL = "insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_GUID,ROLE_PRIVILEGE_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,PRIVILEGE_GUID) values (?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			for(String privilegeguid : privilegeguidArray){
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, user_guid);
				pstmt.setLong(5, lDate);
				pstmt.setString(6, user_guid);
				pstmt.setInt(7, 0);
				pstmt.setString(8, client_guid);
				pstmt.setString(9, role_guid);
				pstmt.setString(10, privilegeguid);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void addSysRole_sqlserver(ROLE_MAIN entity, Connection conn) throws Exception {
		entity.setRole_main_id(Decode(entity.getRole_main_id()));
		entity.setRole_name(Decode(entity.getRole_name()));
		entity.setRole_desc(Decode(entity.getRole_desc()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select top 1 1 from ROLE_MAIN where IS_DELETED=0 and ROLE_MAIN_ID=?");
			pstmt.setString(1, entity.getRole_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("角色代码 "+entity.getRole_main_id()+" 已存在");
			}
			
			String iSQL = "insert into ROLE_MAIN (ROLE_MAIN_GUID,ROLE_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_NAME,ROLE_DESC) values(?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			int index=0;
			pstmt.setString(++index, UUID.randomUUID().toString());
			pstmt.setString(++index, entity.getRole_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, entity.getCreated_by());
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, entity.getUpdated_by());
			pstmt.setInt(++index, 0);
			pstmt.setString(++index, entity.getClient_guid());
			pstmt.setString(++index, entity.getRole_name());
			pstmt.setString(++index, entity.getRole_desc());
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

	private ROLE_DM getSysRoles_sqlserver(String role_id, String role_name,
			int page_no, int page_size, Connection conn) throws Exception {
		role_id=Decode(role_id);
		role_name=Decode(role_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;;
		
		ROLE_DM returnDM = new ROLE_DM();
		List<ROLE_MAIN> returnList = new ArrayList<ROLE_MAIN>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String subSQL = "select ROLE_MAIN_GUID, ROLE_MAIN_ID, ROLE_NAME, ROLE_DESC, CREATED_DT from ROLE_MAIN";
			String subWhereSQL = " where IS_DELETED=0";
			String subOrderby=" order by CREATED_DT desc ";
			if(role_id!=null&&role_id.length()>0){
				subWhereSQL+=" and ROLE_MAIN_ID LIKE ?";
			}
			if(role_name!=null&&role_name.length()>0){
				subWhereSQL+=" and ROLE_NAME LIKE ?";
			}
			subSQL = subSQL+subWhereSQL;
			String sSQL = "select B.* from (select A.*, ROW_NUMBER() over("+subOrderby+") as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index=0;
			if(role_id!=null&&role_id.length()>0){
				pstmt.setString(++index, role_id+"%");
			}
			if(role_name!=null&&role_name.length()>0){
				pstmt.setString(++index, "%"+role_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				ROLE_MAIN entity = new ROLE_MAIN();
				entity.setRole_main_guid(rs.getString(1));
				entity.setRole_main_id(Encode(rs.getString(2)));
				entity.setRole_name(Encode(rs.getString(3)));
				entity.setRole_desc(Encode(rs.getString(4)));
				
				returnList.add(entity);
			}
			returnDM.setRoleListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("select count(ROLE_MAIN_GUID) from ROLE_MAIN"+subWhereSQL);
			index=0;
			if(role_id!=null&&role_id.length()>0){
				pstmt.setString(++index, role_id+"%");
			}
			if(role_name!=null&&role_name.length()>0){
				pstmt.setString(++index, "%"+role_name+"%");
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

	private ROLE_MAIN getSysRole_sqlserver(String role_guid, Connection conn) throws Exception {
		ROLE_MAIN returnEntity = new ROLE_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT top 1 ROLE_MAIN_GUID, ROLE_MAIN_ID, ROLE_NAME, ROLE_DESC FROM ROLE_MAIN WHERE IS_DELETED=0 AND ROLE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, role_guid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setRole_main_guid(rs.getString(1));
				returnEntity.setRole_main_id(Encode(rs.getString(2)));
				returnEntity.setRole_name(Encode(rs.getString(3)));
				returnEntity.setRole_desc(Encode(rs.getString(4)));
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

	private void updateSysRole_sqlserver(ROLE_MAIN entity, Connection conn) throws Exception {
		entity.setRole_main_id(Decode(entity.getRole_main_id()));
		entity.setRole_name(Decode(entity.getRole_name()));
		entity.setRole_desc(Decode(entity.getRole_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update ROLE_MAIN set UPDATED_DT=?, UPDATED_BY=?, ROLE_NAME=?, ROLE_DESC=? where IS_DELETED=0 AND ROLE_MAIN_GUID=? ";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_name());
			pstmt.setString(4, entity.getRole_desc());
			pstmt.setString(5, entity.getRole_main_guid());
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

	private void delSysRole_sqlserver(String role_guid,
			Connection conn) throws Exception {
		PreparedStatement rolePstmt = null;
		PreparedStatement sysPstmt = null;
		PreparedStatement funPstmt = null;
		try{
			String dRoleSQL = "delete from ROLE_MAIN where IS_DELETED=0 and ROLE_MAIN_GUID=?";
			String dSysSQL = "delete from ROLE_SYS where IS_DELETED=0 and ROLE_GUID=?";
			String dFunSQL = "delete from ROLE_FUN where IS_DELETED=0 and ROLE_GUID=?";
			conn.setAutoCommit(false);
			funPstmt = conn.prepareStatement(dRoleSQL);
			sysPstmt = conn.prepareStatement(dSysSQL);
			rolePstmt = conn.prepareStatement(dFunSQL);

			funPstmt.setString(1, role_guid);
			funPstmt.addBatch();
			
			sysPstmt.setString(1, role_guid);
			sysPstmt.addBatch();
			
			rolePstmt.setString(1, role_guid);
			rolePstmt.addBatch();

			funPstmt.executeBatch();
			sysPstmt.executeBatch();
			rolePstmt.executeBatch();
			
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(funPstmt!=null)
				funPstmt.close();
			if(sysPstmt!=null)
				sysPstmt.close();
			if(rolePstmt!=null)
				rolePstmt.close();
			if(conn!=null&&!conn.getAutoCommit())
				conn.setAutoCommit(true);
		}
	}

	private SYS_DM getRoleSyss_sqlserver(String role_guid, String sys_id,
			String sys_name, int page_no, int page_size, Connection conn) throws Exception {
		sys_id=Decode(sys_id);
		sys_name=Decode(sys_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL="select t.ROLE_SYS_GUID,t.SYS_GUID, t.IS_DEFAULT_SYS,t1.BIZ_SYS_ID,t1.SYS_NAME from ROLE_SYS t inner join BIZ_SYS t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0";
			String subSQLWhere = "";
			String subOrderby="order by SYS_GUID desc";
			if(sys_id!=null&&sys_id.length()>0){
				subSQLWhere+=" and t1.BIZ_SYS_ID like ?";
			}
			if(sys_name!=null&&sys_name.length()>0){
				subSQLWhere+=" and t1.SYS_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere+" where t.IS_DELETED=0 AND t.ROLE_GUID=?";
			String sSQL = "select B.* from (select A.*, ROW_NUMBER() over("+subOrderby+") as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			pstmt.setString(++index, role_guid);
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setRole_sys_guid(rs.getString(1));
				entity.setBiz_sys_guid(rs.getString(2));
				entity.setIs_default_sys(rs.getInt(3));
				entity.setBiz_sys_id(Encode(rs.getString(4)));
				entity.setSys_name(Encode(rs.getString(5)));
				
				returnList.add(entity);
			}
			pstmt.close();
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("select count(*) from ROLE_SYS t inner join BIZ_SYS t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0"+subSQLWhere+" where t.IS_DELETED=0 AND t.ROLE_GUID=?");
			index = 0;
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			pstmt.setString(++index, role_guid);
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

	private SYS_DM getRoleSyssLeft_sqlserver(String role_guid, String sys_id,
			String sys_name, int page_no, int page_size, Connection conn) throws Exception {
		sys_id=Decode(sys_id);
		sys_name=Decode(sys_name);
	
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		SYS_DM returnDM = new SYS_DM();
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL="select t.BIZ_SYS_GUID,t.BIZ_SYS_ID,t.SYS_NAME,t1.ROLE_SYS_GUID from BIZ_SYS t left join ROLE_SYS t1" +
					" on t.BIZ_SYS_GUID=t1.SYS_GUID and t1.IS_DELETED=0 AND t1.ROLE_GUID=?";
			String subSQLWhere = " where t.IS_DELETED=0 and t1.ROLE_SYS_GUID is null";
			String subOrderby=" order by BIZ_SYS_GUID desc";
			if(sys_id!=null&&sys_id.length()>0){
				subSQLWhere+=" and t.BIZ_SYS_ID like ?";
			}
			if(sys_name!=null&&sys_name.length()>0){
				subSQLWhere+=" and t.SYS_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere;
			String sSQL = "select B.* from (select A.*, ROW_NUMBER() over("+subOrderby+") as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			pstmt.setString(++index, role_guid);
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(Encode(rs.getString(2)));
				entity.setSys_name(Encode(rs.getString(3)));
				
				returnList.add(entity);
			}
			pstmt.close();
			returnDM.setBizSysListData(returnList);
			
			pstmt = conn.prepareStatement("select count(*) from BIZ_SYS t left join ROLE_SYS t1" +
					" on t.BIZ_SYS_GUID=t1.SYS_GUID and t1.IS_DELETED=0 AND t1.ROLE_GUID=?"+subSQLWhere);
			index = 0;
			pstmt.setString(++index, role_guid);
			if(sys_id!=null&&sys_id.length()>0){
				pstmt.setString(++index, sys_id+"%");
			}
			if(sys_name!=null&&sys_name.length()>0){
				pstmt.setString(++index, "%"+sys_name+"%");
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

	private void addRoleSyss_sqlserver(ROLE_MAIN role,
			List<BIZ_SYS> bizSysList, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String iSQL = "insert into ROLE_SYS (ROLE_SYS_GUID,ROLE_SYS_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,SYS_GUID,IS_DEFAULT_SYS) values (?,?,?,?,?,?,?,?,?,?,?)";
			conn.setAutoCommit(false);
			for(BIZ_SYS entity : bizSysList){
				pstmt = conn.prepareStatement(iSQL);
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, role.getCreated_by());
				pstmt.setLong(5, lDate);
				pstmt.setString(6, role.getUpdated_by());
				pstmt.setInt(7, 0);
				pstmt.setString(8, role.getClient_guid());
				pstmt.setString(9, role.getRole_main_guid());
				pstmt.setString(10, entity.getBiz_sys_guid());
				pstmt.setInt(11, 0);
				pstmt.execute();
				pstmt.close();
			}
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void defaultSys_sqlserver(ROLE_SYS entity, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			String cSQL = "select t.SYS_GUID, t1.SYS_NAME from ROLE_SYS t inner join BIZ_SYS t1 on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0 where t.IS_DELETED=0 and t.ROLE_SYS_GUID=? and t.IS_DEFAULT_SYS=1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, entity.getRole_sys_guid());
			rs = pstmt.executeQuery();
			String sysGuid="";
			String sysName="";
			if(rs.next()){
				sysGuid = rs.getString(1);
				sysName = rs.getString(2);
			}
			pstmt.close();
			if(sysGuid!=null&&sysGuid.length()>0){
				throw new Exception("系统 "+(sysName==null?"":sysName)+" 已是默认系统");
			}
			
			String uSQL="update ROLE_SYS set UPDATED_DT=?, UPDATED_BY=?, IS_DEFAULT_SYS=0 where IS_DELETED=0 and ROLE_GUID =? AND IS_DEFAULT_SYS=1";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_guid());
			pstmt.execute();
			pstmt.close();
			
			uSQL = "update ROLE_SYS set UPDATED_DT=?, UPDATED_BY=?, IS_DEFAULT_SYS=1 where IS_DELETED=0 and ROLE_GUID =? and ROLE_SYS_GUID=? ";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, entity.getUpdated_by());
			pstmt.setString(3, entity.getRole_guid());
			pstmt.setString(4, entity.getRole_sys_guid());
			pstmt.execute();
			pstmt.close();
			
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void delRoleSys_sqlserver(String role_guid, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			String dSQL = "delete from ROLE_SYS where IS_DELETED=0 and ROLE_SYS_GUID=?";
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dSQL);

			pstmt.setString(1, role_guid);
			pstmt.addBatch();

			pstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private List<BIZ_SYS> getSlRoleSyss_sqlserver(ROLE_SYS role, Connection conn) throws Exception {
		List<BIZ_SYS> returnList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL="select t.SYS_GUID,t1.BIZ_SYS_ID,t1.SYS_NAME from ROLE_SYS t inner join BIZ_SYS t1" +
					" on t.SYS_GUID=t1.BIZ_SYS_GUID and t1.IS_DELETED=0 where t.IS_DELETED=0 AND t.ROLE_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, role.getRole_guid());
			rs = pstmt.executeQuery();
			while(rs.next()){
				BIZ_SYS entity = new BIZ_SYS();
				entity.setBiz_sys_guid(rs.getString(1));
				entity.setBiz_sys_id(Encode(rs.getString(2)));
				entity.setSys_name(Encode(rs.getString(3)));
				
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

	private List<FUN_NODE> getSysFuns_sqlserver(ROLE_SYS entity, Connection conn) throws Exception {
		List<FUN_NODE> nodeList = new ArrayList<FUN_NODE>();
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "select t1.ROLE_FUN_GUID,t.FUN_SEQNO,t.FUN_MAIN_GUID,t.FUN_MAIN_ID,t.FUN_NAME,t.FUN_PARAM,t.FUN_URL,t.NODE_IMG,t.FUN_DESC,t.BIZ_SYS_GUID from FUN_MAIN t left join ROLE_FUN t1" +
					" on t.FUN_MAIN_GUID = t1.FUN_GUID and t1.IS_DELETED=0 and t1.ROLE_GUID =?" +
					" where t.IS_DELETED=0 and t.BIZ_SYS_GUID=? order by t.FUN_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, entity.getRole_guid());
			pstmt.setString(2, entity.getSys_guid());
			rs = pstmt.executeQuery();
			while(rs.next()){
				index = 0;
				FUN_NODE node =new FUN_NODE();
				String roleFunGuid = rs.getString(++index);
				if(roleFunGuid==null){
					node.setSelect(false);
				}else{
					node.setSelect(true);
				}
				node.setFun_seqno(rs.getString(++index));
				node.setKey(rs.getString(++index));
				node.setFun_id(rs.getString(++index));
				node.setTitle(rs.getString(++index));
				node.setFun_name(node.getTitle());
				node.setFun_param(rs.getString(++index));
				node.setFun_url(rs.getString(++index));
				node.setHref(node.getFun_url());
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setFun_desc(rs.getString(++index));
				node.setBiz_sys_guid(rs.getString(++index));
				node.setExpand(true);
				
				nodeList.add(node);
			}
			pstmt.close();
			
			FUN_NODE currentNode = new FUN_NODE();
			
			int currentDepth = 0;
			for(FUN_NODE node :nodeList) {
	
				int nextDepth = (node.getFun_seqno().length() + 2) / 3;
				
				if(nextDepth - currentDepth > 1) {
	                node.setFun_seqno(node.getFun_seqno().substring(0, 3 * (currentDepth + 1)));
	                nextDepth = currentDepth + 1;
	            }
				
				if(nextDepth <= 1) {
	                currentDepth = 1;
	                nodeTreeList.add(node);
	            }else{
	                if(nextDepth == currentDepth){
	                	node.setParent(currentNode.getParent());
	                	currentNode.getParent().getChildren().add(node);
	                }else if(nextDepth > currentDepth){
	                    currentDepth++;
	                    node.setParent(currentNode);
	                    currentNode.getChildren().add(node);
	                }else{
	                    while (nextDepth < currentDepth){
	                    	currentNode = currentNode.getParent();
	                        currentDepth--;
	                    }
	                    node.setParent(currentNode.getParent());
	                    currentNode.getParent().getChildren().add(node);
	                }
	            }
				currentNode = node;
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return nodeTreeList;
	}

	private void addRoleFuns_sqlserver(String user_id, String client_id,
			String role_guid, String sys_guid, String[] funGuidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			
			String dSQL = "delete from ROLE_FUN where IS_DELETED=0 AND ROLE_GUID=? AND SYS_GUID=?";
			String iSQL = "insert into ROLE_FUN (ROLE_FUN_GUID,ROLE_FUN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,FUN_GUID,SYS_GUID) values (?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(dSQL);
			pstmt.setString(1, role_guid);
			pstmt.setString(2, sys_guid);
			pstmt.execute();
			pstmt.close();
			
			for(String funGuid : funGuidArray){
				pstmt = conn.prepareStatement(iSQL);
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, user_id);
				pstmt.setLong(5, lDate);
				pstmt.setString(6, user_id);
				pstmt.setInt(7, 0);
				pstmt.setString(8, client_id);
				pstmt.setString(9, role_guid);
				pstmt.setString(10, funGuid);
				pstmt.setString(11, sys_guid);
				pstmt.execute();
				pstmt.close();
			}
			conn.commit();
			
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private void delRoleFuns_sqlserver(String role_guid, String sys_guid,
			Connection conn) throws SQLException {
		String dSQL = "delete from ROLE_FUN where IS_DELETED=0 AND ROLE_GUID=? AND SYS_GUID=?";
		PreparedStatement pstmt = conn.prepareStatement(dSQL);
		pstmt.setString(1, role_guid);
		pstmt.setString(2, sys_guid);
		pstmt.execute();
		pstmt.close();
	}

	private List<ROLE_MAIN> getSlSysRoles_sqlserver(Connection conn) throws Exception {
		List<ROLE_MAIN> returnList = new ArrayList<ROLE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String  sSQL = "select ROLE_MAIN_GUID, ROLE_NAME from ROLE_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ROLE_MAIN entity = new ROLE_MAIN();
				entity.setRole_main_guid(Encode(rs.getString(1)));
				entity.setRole_name(Encode(rs.getString(2)));
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

	private PRIVILEGE_DM getRolePrivileges_sqlserver(String role_guid,
			String privilege_id, String privilege_name, String doc_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select t.ROLE_PRIVILEGE_GUID, t1.PRIVILEGE_MAIN_GUID, t1.PRIVILEGE_MAIN_ID, t1.PRIVILEGE_NAME, t1.PRIVILEGE_DESC, t2.DOC_MAIN_ID, t2.DOC_NAME, t.CREATED_DT from ROLE_PRIVILEGE t inner join PRIVILEGE_MAIN t1 on t1.IS_DELETED=0 and t.PRIVILEGE_GUID = t1.PRIVILEGE_MAIN_GUID";
			String subSQLWhere = "";
			String subOrderby = " order by CREATED_DT desc";
			if(doc_guid!=null&&doc_guid.length()!=0){
				subSQLWhere +=" and t1.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				subSQLWhere +=" and t1.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				subSQLWhere +=" and t1.PRIVILEGE_NAME like ?";
			}
			subSQL= subSQL+subSQLWhere+" inner join DOC_MAIN t2 on t2.IS_DELETED=0 and t1.DOC_GUID = t2.DOC_MAIN_GUID where t.IS_DELETED=0 and t.ROLE_GUID=?";
			String sSQL = "select B.* from (select A.*, ROW_NUMBER() over("+subOrderby+") as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			pstmt.setString(++index, role_guid);
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				PRIVILEGE_MAIN entity = new PRIVILEGE_MAIN();
				entity.setRole_privilege_guid(rs.getString(1));
				entity.setPrivilege_main_guid(rs.getString(2));
				entity.setPrivilege_main_id(Encode(rs.getString(3)));
				entity.setPrivilege_name(Encode(rs.getString(4)));
				entity.setPrivilege_desc(Encode(rs.getString(5)));
				entity.setDoc_id(Encode(rs.getString(6)));
				entity.setDoc_name(Encode(rs.getString(7)));
				
				returnList.add(entity);
			}
			returnDM.setPrivilegeListData(returnList);
			pstmt.close();
			subSQL = "";
			subSQL = "select count(*) from ROLE_PRIVILEGE t inner join PRIVILEGE_MAIN t1 on t1.IS_DELETED=0 and t.PRIVILEGE_GUID = t1.PRIVILEGE_MAIN_GUID";
			subSQL = subSQL+subSQLWhere+" inner join DOC_MAIN t2 on t2.IS_DELETED=0 and t1.DOC_GUID = t2.DOC_MAIN_GUID where t.IS_DELETED=0 and t.ROLE_GUID=?";
			
			pstmt = conn.prepareStatement(subSQL);
			index = 0;
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			pstmt.setString(++index, role_guid);
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

	private void delRolePrivilege_sqlserver(String[] roleprivilegeguidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from ROLE_PRIVILEGE where IS_DELETED=0 and ROLE_PRIVILEGE_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String roleprivilegeguid : roleprivilegeguidArray){
				pstmt.setString(1, roleprivilegeguid);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}

	private PRIVILEGE_DM getRolePrivilegesLeft_sqlserver(String role_guid,
			String privilege_id, String privilege_name, String doc_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select t.PRIVILEGE_MAIN_GUID, t.PRIVILEGE_MAIN_ID, t.PRIVILEGE_NAME, t.PRIVILEGE_DESC, t2.DOC_MAIN_ID, t2.DOC_NAME, t.CREATED_DT from PRIVILEGE_MAIN t left join ROLE_PRIVILEGE t1 on t1.IS_DELETED=0 and t1.ROLE_GUID=? and t.PRIVILEGE_MAIN_GUID = t1.PRIVILEGE_GUID inner join DOC_MAIN t2 on t2.IS_DELETED=0 and t.DOC_GUID = t2.DOC_MAIN_GUID";
			String subSQLWhere = " where t.IS_DELETED=0 and t1.ROLE_PRIVILEGE_GUID is null";
			String subOrderby="order by CREATED_DT desc";
			if(doc_guid!=null&&doc_guid.length()!=0){
				subSQLWhere +=" and t.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				subSQLWhere +=" and t.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				subSQLWhere +=" and t.PRIVILEGE_NAME like ?";
			}
			subSQL=subSQL+subSQLWhere;
			String sSQL = "select B.* from (select A.*, ROW_NUMBER() over("+subOrderby+") as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			int index = 0;
			pstmt.setString(++index, role_guid);
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				PRIVILEGE_MAIN entity = new PRIVILEGE_MAIN();
				entity.setPrivilege_main_guid(rs.getString(1));
				entity.setPrivilege_main_id(Encode(rs.getString(2)));
				entity.setPrivilege_name(Encode(rs.getString(3)));
				entity.setPrivilege_desc(Encode(rs.getString(4)));
				entity.setDoc_id(Encode(rs.getString(5)));
				entity.setDoc_name(Encode(rs.getString(6)));
				
				returnList.add(entity);
			}
			returnDM.setPrivilegeListData(returnList);
			pstmt.close();
			
			subSQL = "select count(*) from PRIVILEGE_MAIN t left join ROLE_PRIVILEGE t1 on t1.IS_DELETED=0 and t1.ROLE_GUID=? and t.PRIVILEGE_MAIN_GUID = t1.PRIVILEGE_GUID inner join DOC_MAIN t2 on t2.IS_DELETED=0 and t.DOC_GUID = t2.DOC_MAIN_GUID";			
			pstmt = conn.prepareStatement(subSQL+subSQLWhere);
			index = 0;
			pstmt.setString(++index, role_guid);
			if(doc_guid!=null&&doc_guid.length()!=0){
				pstmt.setString(++index, doc_guid);
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				pstmt.setString(++index, privilege_id+"%");
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				pstmt.setString(++index, "%"+privilege_name+"%");
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

	private void addRolePrivileges_sqlserver(String user_guid,
			String client_guid, String role_guid, String[] privilegeguidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String iSQL = "insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_GUID,ROLE_PRIVILEGE_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ROLE_GUID,PRIVILEGE_GUID) values (?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			for(String privilegeguid : privilegeguidArray){
				String guidNew = UUID.randomUUID().toString();
				pstmt.setString(1, guidNew);
				pstmt.setString(2, guidNew);
				long lDate = new Date().getTime();
				pstmt.setLong(3, lDate);
				pstmt.setString(4, user_guid);
				pstmt.setLong(5, lDate);
				pstmt.setString(6, user_guid);
				pstmt.setInt(7, 0);
				pstmt.setString(8, client_guid);
				pstmt.setString(9, role_guid);
				pstmt.setString(10, privilegeguid);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			conn.commit();
		}catch(Exception e){
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
			if(conn!=null&&!conn.getAutoCommit()){
				conn.setAutoCommit(true);
			}
		}
	}
}

