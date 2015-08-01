package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.xinyou.frame.domain.entities.BIZ_SYS;
import com.xinyou.frame.domain.entities.USR_MAIN;
import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.frame.domain.models.FUN_NODE;
import com.xinyou.frame.domain.models.USR_DM;
import com.xinyou.frame.domain.models.USR_LOGIN_DM;
import com.xinyou.frame.domain.models.USR_MAIN_VIEW;
import com.xinyou.util.Config;
import com.xinyou.util.StringUtil;

public class USR_BIZ extends StringUtil {
	
	/**
	 * 
	 * */
	public void addSysUsr(USR_MAIN usr, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  addSysUsr_oracle(usr, conn);
	                 		break;
	        case "sqlserver": addSysUsr_sqlserver(usr, conn);
							break;
	        default: addSysUsr_mysql(usr, conn);
	        				break;
		}
	}
	
	
	public USR_DM getSysUsrs(String usr_name, String role_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		USR_DM returnDM = new USR_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getSysUsrs_oracle(usr_name,role_guid,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getSysUsrs_sqlserver(usr_name,role_guid,page_no,page_size,conn);
							break;
	        default: returnDM = getSysUsrs_mysql(usr_name,role_guid,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}
	
	public USR_MAIN getSysUsr(String usr_guid, Connection conn) throws Exception {
		USR_MAIN returnEntity = new USR_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT USR_MAIN_GUID, USR_MAIN_ID, USR_NAME, USR_NICKNAME, USR_PSWD, USR_PIC, USR_DESC, ROLE_GUID, EMP_GUID,USR_SYS_ID FROM USR_MAIN WHERE USR_MAIN_GUID=? AND IS_DELETED=0");
			pstmt.setString(1, usr_guid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setUsr_main_guid(rs.getString(1));
				returnEntity.setUsr_main_id(rs.getString(2));
				returnEntity.setUsr_name(Encode(rs.getString(3)));
				returnEntity.setUsr_nickname(Encode(rs.getString(4)));
				returnEntity.setUsr_pswd(rs.getString(5));
				returnEntity.setUsr_pic(Encode(rs.getString(6)));
				returnEntity.setUsr_desc(Encode(rs.getString(7)));
				returnEntity.setRole_guid(rs.getString(8));
				returnEntity.setEmp_guid(rs.getString(9));
				returnEntity.setUsr_sys_id(rs.getString(10));
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

	public void updateSysUsr(USR_MAIN usr, Connection conn) throws Exception {
		usr.setUsr_nickname(Decode(usr.getUsr_nickname()));
		usr.setUsr_pic(Decode(usr.getUsr_pic()));
		usr.setUsr_desc(Decode(usr.getUsr_desc()));
		
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement("UPDATE USR_MAIN SET UPDATED_DT=?, UPDATED_BY=?, USR_NICKNAME=?, USR_PIC=?, USR_DESC=?, ROLE_GUID=?, EMP_GUID=?, USR_PSWD=?,USR_SYS_ID=? WHERE USR_MAIN_GUID=? AND IS_DELETED=0");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, usr.getUpdated_by());
			pstmt.setString(3, usr.getUsr_nickname());
			pstmt.setString(4, usr.getUsr_pic());
			pstmt.setString(5, usr.getUsr_desc());
			pstmt.setString(6, usr.getRole_guid());
			pstmt.setString(7, usr.getEmp_guid());
			pstmt.setString(8, usr.getUsr_pswd());
			pstmt.setString(9, usr.getUsr_sys_id());
			pstmt.setString(10, usr.getUsr_main_guid());
			
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
	
    public void changeUsrPsw(USR_MAIN usr, Connection conn) throws Exception {
		
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement("UPDATE USR_MAIN SET UPDATED_DT=?, UPDATED_BY=?, USR_PSWD=? WHERE USR_MAIN_GUID=? AND IS_DELETED=0");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, usr.getUpdated_by());
			pstmt.setString(3, usr.getUsr_pswd());
			pstmt.setString(4, usr.getUsr_main_guid());
			
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

	public boolean checkPassword(String usr_main_guid, String usr_pswd, Connection conn) throws Exception {
		boolean checked=false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String true_psw=null;
			pstmt = conn.prepareStatement("SELECT USR_PSWD FROM USR_MAIN WHERE USR_MAIN_GUID=? AND IS_DELETED=0");
			pstmt.setString(1, usr_main_guid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				true_psw = rs.getString(1);
			}else{
				throw new Exception("用户名不存在");
			}
			rs.close();
			pstmt.close();
			
			if(StringUtils.isNotEmpty(usr_pswd)&&usr_pswd.equals(true_psw)){
				checked = true;
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return checked;
	}
	
	public void delSysUsrs(String[] usrguidArray, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement("DELETE FROM USR_MAIN WHERE USR_MAIN_GUID=? AND IS_DELETED=0");
			for(String usrguid : usrguidArray){
				pstmt.setString(1, usrguid);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}
	
	public void delSysUsr(String usr_guid, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement("DELETE FROM USR_MAIN WHERE USR_MAIN_GUID=? AND IS_DELETED=0");
			pstmt.setString(1, usr_guid);
			pstmt.execute();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}
	
	public USR_MAIN getUserInfo(String token, Connection conn) throws Exception {
		USR_MAIN returnEntity = new USR_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL ="SELECT T.USR_MAIN_GUID, T.USR_MAIN_ID, T.USR_NAME, T.USR_NICKNAME, T.USR_PIC, T.ROLE_GUID, T.TOKEN_TIMEOUT, T.EMP_GUID, T1.EMP_MAIN_ID, T1.EMP_NAME, T1.EMP_TYPE, T.USR_SYS_ID FROM USR_MAIN T LEFT JOIN EMP_MAIN T1 ON T.EMP_GUID=T1.EMP_MAIN_GUID AND T1.IS_DELETED=0 WHERE T.TOKEN=? AND T.IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, token);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setUsr_main_guid(rs.getString(1));
				returnEntity.setUsr_main_id(rs.getString(2));
				returnEntity.setUsr_name(rs.getString(3));
				returnEntity.setUsr_nickname(Encode(rs.getString(4)));
				returnEntity.setUsr_pic(Encode(rs.getString(5)));
				returnEntity.setRole_guid(rs.getString(6));
				returnEntity.setToken(token);
				returnEntity.setToken_timeout(rs.getLong(7));
				returnEntity.setEmp_guid(rs.getString(8));
				returnEntity.setEmp_id(rs.getString(9));
				returnEntity.setEmp_name(Encode(rs.getString(10)));
				returnEntity.setEmp_type(rs.getInt(11));
				returnEntity.setUsr_sys_id(rs.getString(12));
			}
			pstmt.close();
			
			if(returnEntity.getUsr_main_guid()==null||returnEntity.getUsr_main_guid().isEmpty()){
				returnEntity.setStatus(1);
				return returnEntity;
			}
			if(returnEntity.getToken_timeout()<new Date().getTime()){
				returnEntity.setStatus(2);
				return returnEntity;
			}
			
			pstmt = conn.prepareStatement("UPDATE USR_MAIN SET UPDATED_DT=?, UPDATED_BY=?, TOKEN_TIMEOUT=? WHERE USR_MAIN_GUID=? AND IS_DELETED=0");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, returnEntity.getUsr_main_guid());
			Calendar cal = Calendar.getInstance(); 
			int timeout = 30;
			try{timeout = Integer.parseInt(new Config(FrameConfig.CONFIGNAME).get("appsetting@tokentimeout"));}catch(Exception e){}
			cal.add(Calendar.MINUTE, timeout);
			pstmt.setLong(3, cal.getTime().getTime());
			pstmt.setString(4, returnEntity.getUsr_main_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnEntity;
	}
	
	
	public USR_MAIN_VIEW signIn(String usr_name, String usr_pswd, Connection conn) throws Exception {
		usr_name = Decode(usr_name);
		usr_pswd = Decode(usr_pswd);
		
		USR_MAIN_VIEW returnEntity = new USR_MAIN_VIEW();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
//			pstmt = conn.prepareStatement("SELECT T.USR_MAIN_GUID, T.USR_NICKNAME, T.USR_PSWD, T.ROLE_GUID, T.TOKEN, T.TOKEN_TIMEOUT, T.EMP_GUID, T1.EMP_MAIN_ID, T1.EMP_NAME, T1.EMP_TYPE FROM USR_MAIN T LEFT JOIN EMP_MAIN T1 ON T.EMP_GUID=T1.EMP_MAIN_GUID AND T1.IS_DELETED=0 WHERE T.USR_NAME=? AND T.IS_DELETED=0");
			pstmt = conn.prepareStatement("SELECT T.USR_MAIN_GUID, T.USR_NICKNAME, T.USR_PSWD, T.ROLE_GUID, T.TOKEN, T.TOKEN_TIMEOUT, T.EMP_GUID, T1.EMP_MAIN_ID, T1.EMP_NAME, T1.EMP_TYPE FROM USR_MAIN T "
					+ " LEFT JOIN EMP_MAIN T1 ON T.EMP_GUID=T1.EMP_MAIN_GUID AND T1.EMP_STATUS=1 AND T1.IS_DELETED=0 WHERE T.USR_NAME=? AND T.IS_DELETED=0");
			pstmt.setString(1, usr_name);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setUsr_main_guid(rs.getString(1));
				returnEntity.setUsr_name(usr_name);
				returnEntity.setUsr_nickname(Encode(rs.getString(2)));
				returnEntity.setUsr_pswd(rs.getString(3));
				returnEntity.setRole_guid(rs.getString(4));
				returnEntity.setToken(rs.getString(5));
				returnEntity.setToken_timeout(rs.getLong(6));
				returnEntity.setEmp_guid(rs.getString(7));
				returnEntity.setEmp_id(rs.getString(8));
				returnEntity.setEmp_name(Encode(rs.getString(9)));
				returnEntity.setEmp_type(rs.getInt(10));
			}
			rs.close();
			pstmt.close();
			
			if(returnEntity.getEmp_id()==null||returnEntity.getEmp_id().isEmpty()){
				throw new Exception("员工信息不存在或被禁用！");
			}
			if(returnEntity.getUsr_main_guid()==null||returnEntity.getUsr_main_guid().isEmpty()){
				throw new Exception("用户名不存在");
			}
			if(!returnEntity.getUsr_pswd().equals(usr_pswd)){
				throw new Exception("用户名或密码错误");
			}
			
			String uSQL = "UPDATE USR_MAIN SET UPDATED_DT=?, UPDATED_BY=?, TOKEN_TIMEOUT=?";
			if(returnEntity.getToken_timeout()<new Date().getTime()){
				uSQL+=" ,TOKEN=? ";
			}
			uSQL+=" WHERE USR_MAIN_GUID=? AND IS_DELETED=0 ";
			
			pstmt = conn.prepareStatement(uSQL);
			int index = 0;
			pstmt.setLong(++index, new Date().getTime());
			pstmt.setString(++index, returnEntity.getUsr_main_guid());
			
			Calendar cal = Calendar.getInstance(); 
			cal.add(Calendar.MINUTE, new Config(FrameConfig.CONFIGNAME).getInt("appsetting@tokentimeout"));
			
			pstmt.setLong(++index, cal.getTime().getTime());
			
			if(returnEntity.getToken_timeout()<new Date().getTime()){
				returnEntity.setToken(UUID.randomUUID().toString());
				pstmt.setString(++index, returnEntity.getToken());
			}
			
			pstmt.setString(++index, returnEntity.getUsr_main_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnEntity;
	}
	
	public USR_LOGIN_DM getSysFunByUsr(String role_guid, Connection conn) throws Exception {
		USR_LOGIN_DM returnDM = new USR_LOGIN_DM();
		List<BIZ_SYS> sysList = new ArrayList<BIZ_SYS>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT T.IS_DEFAULT_SYS, T.ROLE_GUID, T1.BIZ_SYS_GUID, T1.BIZ_SYS_ID, T1.SYS_NAME, T1.SYS_URL FROM ROLE_SYS AS T INNER JOIN BIZ_SYS AS T1 ON T.SYS_GUID = T1.BIZ_SYS_GUID AND T1.SYS_PC=1 AND T1.IS_DELETED=0 WHERE T.IS_DELETED=0 AND T.ROLE_GUID=? ORDER BY T.IS_DEFAULT_SYS DESC";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, role_guid);
			rs = pstmt.executeQuery();
			String defaultSysGuid = "";
			int isDefaultSys = 0;
			while(rs.next()){
				BIZ_SYS sys = new BIZ_SYS();
				isDefaultSys = rs.getInt(1);
				sys.setIs_default_sys(isDefaultSys);
				sys.setRole_sys_guid(rs.getString(2));
				String g = rs.getString(3);
				if(isDefaultSys==1){
					defaultSysGuid = g;
				}
				sys.setBiz_sys_guid(g);
				sys.setBiz_sys_id(Encode(rs.getString(4)));
				sys.setSys_name(Encode(rs.getString(5)));
				sys.setSys_url(Encode(rs.getString(6)));
				sysList.add(sys);
			}
			rs.close();
			pstmt.close();
			
			returnDM.setBizSysListData(sysList);
			if(sysList.size()!=0){
				if(defaultSysGuid==null||defaultSysGuid.length()==0){
					defaultSysGuid = sysList.get(0).getBiz_sys_guid();
					sysList.get(0).setIs_default_sys(1);
				}
				returnDM.setFunNodeListData(getSysFunBySysGuid(defaultSysGuid, role_guid, conn));
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}
	
	
	public List<FUN_NODE> getSysFunBySysGuid(String sys_guid, String role_guid, Connection conn) throws Exception {
		List<FUN_NODE> nodeList = new ArrayList<FUN_NODE>();
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "SELECT T1.FUN_SEQNO,T1.FUN_MAIN_GUID,T1.FUN_MAIN_ID,T1.FUN_NAME,T1.FUN_PARAM,T1.FUN_ASS,T1.FUN_CLASS,T1.FUN_METHOD,T1.FUN_URL,T1.NODE_IMG,T1.NODE_EXPAND,T1.FUN_DESC,T1.BIZ_SYS_GUID FROM ROLE_FUN AS T INNER JOIN FUN_MAIN AS T1 ON T1.IS_DELETED=0 AND T.FUN_GUID = T1.FUN_MAIN_GUID WHERE T.IS_DELETED=0 AND T.ROLE_GUID=? AND T.SYS_GUID=? ORDER BY T1.FUN_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, role_guid);
			pstmt.setString(2, sys_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				index = 0;
				FUN_NODE node =new FUN_NODE();
				node.setFun_seqno(rs.getString(++index));
				node.setKey(rs.getString(++index));
				node.setFun_id(rs.getString(++index));
				node.setTitle(rs.getString(++index));
				node.setFun_name(node.getTitle());
				node.setFun_param(rs.getString(++index));
				node.setFun_ass(rs.getString(++index));
				node.setFun_class(rs.getString(++index));
				node.setFun_method(rs.getString(++index));
				node.setFun_url(rs.getString(++index));
				node.setHref(node.getFun_url());
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				if(rs.getInt(++index)==0)node.setExpand(true);
				else node.setExpand(false);
				node.setFun_desc(rs.getString(++index));
				node.setBiz_sys_guid(rs.getString(++index));
				
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


	public EntityListDM<USR_MAIN,FUN_NODE> signIn_pda(USR_MAIN usr, Connection conn) throws Exception {
		EntityListDM<USR_MAIN,FUN_NODE> returnDM = new EntityListDM<USR_MAIN,FUN_NODE>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			USR_MAIN entity = new USR_MAIN();
			pstmt = conn.prepareStatement("SELECT T1.USR_MAIN_GUID, T1.USR_MAIN_ID, T1.USR_NICKNAME, T1.USR_PSWD, T1.ROLE_GUID, T1.TOKEN, T1.TOKEN_TIMEOUT,T2.ROLE_MAIN_ID FROM USR_MAIN T1,ROLE_MAIN T2 WHERE T1.USR_NAME=? AND T1.IS_DELETED=0 AND T1.ROLE_GUID=T2.ROLE_MAIN_GUID");
			pstmt.setString(1, usr.getUsr_name());
			rs = pstmt.executeQuery();
			if(rs.next()){
				entity.setUsr_main_guid(rs.getString(1));
				entity.setUsr_main_id(rs.getString(2));
				entity.setUsr_name(usr.getUsr_name());
				entity.setUsr_nickname(rs.getString(3));
				entity.setUsr_pswd(rs.getString(4));
				entity.setRole_guid(rs.getString(5));
				entity.setToken(rs.getString(6));
				entity.setToken_timeout(rs.getLong(7));
				entity.setRole_id(rs.getString(8));
			}
			rs.close();
			pstmt.close();
			
			if(entity.getUsr_main_guid()==null||entity.getUsr_main_guid().isEmpty()){
				throw new Exception("用户名不存在");
			}
			if(!entity.getUsr_pswd().equals(usr.getUsr_pswd())){
				throw new Exception("用户名或密码错误");
			}
			String sql = "UPDATE USR_MAIN SET UPDATED_DT=?, UPDATED_BY=?, TOKEN_TIMEOUT=?";
			if(entity.getToken_timeout()<new Date().getTime()){
				sql+=" ,TOKEN=? ";
			}
			sql+=" WHERE USR_MAIN_GUID=? AND IS_DELETED=0";
			pstmt = conn.prepareStatement(sql);
			int index = 0;
			pstmt.setLong(++index, new Date().getTime());
			pstmt.setString(++index, entity.getUsr_main_guid());
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, new Config(FrameConfig.CONFIGNAME).getInt("appsetting@tokentimeout"));
			pstmt.setLong(++index, cal.getTime().getTime());
			if(entity.getToken_timeout()<new Date().getTime()){
				entity.setToken(UUID.randomUUID().toString());
				pstmt.setString(++index, entity.getToken());
			}
			pstmt.setString(++index, entity.getUsr_main_guid());
			pstmt.execute();
			pstmt.close();
			
			returnDM.setDataEntity(entity);
			returnDM.setDataList(getSysFuns_pda(usr.getSys_id(), entity.getRole_guid(), conn));
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}
	
	
	private String getSysGuidzByID(String sys_id, Connection conn) throws Exception{
		String sys_guid=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT BIZ_SYS_GUID FROM BIZ_SYS WHERE BIZ_SYS_ID=? AND IS_DELETED=0");
			pstmt.setString(1, sys_id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				sys_guid=rs.getString(1);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return sys_guid==null?"":sys_guid;
	}
	
	private List<FUN_NODE> getSysFuns_pda(String sys_id,
			String role_guid, Connection conn) throws Exception {
		List<FUN_NODE> nodeList = new ArrayList<FUN_NODE>();
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			pstmt = conn.prepareStatement("SELECT T1.FUN_SEQNO,T1.FUN_MAIN_GUID,T1.FUN_MAIN_ID,T1.FUN_NAME,T1.FUN_PARAM,T1.FUN_ASS,T1.FUN_CLASS,T1.FUN_METHOD,T1.FUN_URL,T1.NODE_IMG,T1.NODE_EXPAND,T1.FUN_DESC,T1.BIZ_SYS_GUID FROM ROLE_FUN T INNER JOIN FUN_MAIN T1 ON T.FUN_GUID = T1.FUN_MAIN_GUID AND T1.IS_DELETED=0 WHERE T.ROLE_GUID=? AND T.SYS_GUID=? AND T.IS_DELETED=0 ORDER BY T1.FUN_SEQNO");
			pstmt.setString(1, role_guid);
			pstmt.setString(2, getSysGuidzByID(sys_id, conn));
			rs = pstmt.executeQuery();
			while(rs.next()){
				index = 0;
				FUN_NODE node =new FUN_NODE();
				node.setFun_seqno(rs.getString(++index));
				node.setKey(rs.getString(++index));
				node.setFun_id(rs.getString(++index));
				node.setTitle(rs.getString(++index));
				node.setFun_name(node.getTitle());
				node.setFun_param(rs.getString(++index));
				node.setFun_ass(rs.getString(++index));
				node.setFun_class(rs.getString(++index));
				node.setFun_method(rs.getString(++index));
				node.setFun_url(rs.getString(++index));
				node.setHref(node.getFun_url());
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				if(rs.getInt(++index)==0)node.setExpand(true);
				else node.setExpand(false);
				node.setFun_desc(rs.getString(++index));
				node.setBiz_sys_guid(rs.getString(++index));
				
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
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return nodeTreeList;
	}
	
	
	private void addSysUsr_mysql(USR_MAIN usr, Connection conn) throws Exception {
		usr.setUsr_main_id(Decode(usr.getUsr_main_id()));
		usr.setUsr_name(Decode(usr.getUsr_name()));
		usr.setUsr_nickname(Decode(usr.getUsr_nickname()));
		usr.setUsr_pswd(Decode(usr.getUsr_pswd()));
		usr.setUsr_desc(Decode(usr.getUsr_desc()));
		usr.setUsr_pic(Decode(usr.getUsr_pic()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String cSQL = "select 1 from USR_MAIN where IS_DELETED=0 and USR_NAME =? limit 1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, usr.getUsr_name());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			
			if(exist){
				throw new Exception("用户名已存在");
			}
			
			String iSQL = "INSERT INTO USR_MAIN (USR_MAIN_GUID, USR_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, USR_NAME, USR_NICKNAME, USR_PSWD, USR_PIC, USR_DESC, ROLE_GUID,USR_SYS_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, guidNew);
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, usr.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, usr.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, usr.getClient_guid());
			pstmt.setString(9, usr.getUsr_name());
			pstmt.setString(10, usr.getUsr_nickname());
			pstmt.setString(11, usr.getUsr_pswd());
			pstmt.setString(12, usr.getUsr_pic());
			pstmt.setString(13, usr.getUsr_desc());
			pstmt.setString(14, usr.getRole_guid());
			pstmt.setString(15, usr.getUsr_sys_id());
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

	
	private void addSysUsr_sqlserver(USR_MAIN usr_main, Connection conn) throws Exception {
		usr_main.setUsr_name(usr_main.getUsr_name());
		usr_main.setUsr_nickname(Decode(usr_main.getUsr_nickname()));
		usr_main.setUsr_pswd(usr_main.getUsr_pswd());
		usr_main.setUsr_desc(Decode(usr_main.getUsr_desc()));
		usr_main.setUsr_pic(Decode(usr_main.getUsr_pic()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT TOP 1 USR_NAME FROM USR_MAIN WHERE USR_NAME=? AND IS_DELETED=0");
			pstmt.setString(1, usr_main.getUsr_name());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			
			if(exist){
				throw new Exception("用户名 "+usr_main.getUsr_name()+" 已存在");
			}
			
			pstmt = conn.prepareStatement("INSERT INTO USR_MAIN (USR_MAIN_GUID, USR_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, USR_NAME, USR_NICKNAME, USR_PSWD, USR_PIC, USR_DESC, ROLE_GUID, TOKEN, TOKEN_TIMEOUT, EMP_GUID,USR_SYS_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			String usr_guid = UUID.randomUUID().toString();
			pstmt.setString(1, usr_guid);
			pstmt.setString(2, usr_guid);
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, usr_main.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, usr_main.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, usr_main.getClient_guid());
			pstmt.setString(9, usr_main.getUsr_name());
			pstmt.setString(10, usr_main.getUsr_nickname());
			pstmt.setString(11, usr_main.getUsr_pswd());
			pstmt.setString(12, usr_main.getUsr_pic());
			pstmt.setString(13, usr_main.getUsr_desc());
			pstmt.setString(14, usr_main.getRole_guid());
			pstmt.setString(15, "");
			pstmt.setLong(16, 0);
			pstmt.setString(17, usr_main.getEmp_guid());
			pstmt.setString(18, usr_main.getUsr_sys_id());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	
	private void addSysUsr_oracle(USR_MAIN usr, Connection conn) throws Exception {
		usr.setUsr_main_id(Decode(usr.getUsr_main_id()));
		usr.setUsr_name(Decode(usr.getUsr_name()));
		usr.setUsr_nickname(Decode(usr.getUsr_nickname()));
		usr.setUsr_pswd(Decode(usr.getUsr_pswd()));
		usr.setUsr_desc(Decode(usr.getUsr_desc()));
		usr.setUsr_pic(Decode(usr.getUsr_pic()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String cSQL = "select 1 from USR_MAIN where IS_DELETED=0 and USR_NAME =? and ROWNUM=1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, usr.getUsr_name());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			
			if(exist){
				throw new Exception("用户名已存在");
			}
			
			String iSQL = "insert into USR_MAIN (USR_MAIN_GUID, USR_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, USR_NAME, USR_NICKNAME, USR_PSWD, USR_PIC, USR_DESC, ROLE_GUID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, guidNew);
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, usr.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, usr.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, usr.getClient_guid());
			pstmt.setString(9, usr.getUsr_name());
			pstmt.setString(10, usr.getUsr_nickname());
			pstmt.setString(11, usr.getUsr_pswd());
			pstmt.setString(12, usr.getUsr_pic());
			pstmt.setString(13, usr.getUsr_desc());
			pstmt.setString(14, usr.getRole_guid());
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

	private USR_DM getSysUsrs_oracle(String usr_name, String role_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		usr_name=Decode(usr_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		USR_DM returnDM = new USR_DM();
		List<USR_MAIN> returnList = new ArrayList<USR_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "SELECT T.USR_MAIN_GUID, T.USR_MAIN_ID, T.USR_NAME, T.USR_NICKNAME, T.USR_PSWD, T.ROLE_GUID, T1.ROLE_NAME FROM USR_MAIN T LEFT JOIN ROLE_MAIN T1 ON T1.IS_DELETED=0 AND T.ROLE_GUID = T1.ROLE_MAIN_GUID WHERE T.IS_DELETED=0";
			String subSQLWhere ="";
			if(usr_name!=null&&usr_name.length()!=0){
				subSQLWhere +=" AND T.USR_NAME LIKE ?";
			}
			if(role_guid!=null&&role_guid.length()!=0){
				subSQLWhere +=" AND T.ROLE_GUID =?";
			}
			subSQL = subSQL+subSQLWhere+" ORDER BY T.CREATED_DT DESC";
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROWNUM AS RN FROM ("+ subSQL +") A) B WHERE B.RN>=? AND B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if(usr_name!=null&&usr_name.length()!=0){
				pstmt.setString(++index, "%"+usr_name+"%");
			}
			if(role_guid!=null&&role_guid.length()!=0){
				pstmt.setString(++index, role_guid);
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				USR_MAIN entity = new USR_MAIN();
				entity.setUsr_main_guid(rs.getString(1));
				entity.setUsr_main_id(rs.getString(2));
				entity.setUsr_name(Encode(rs.getString(3)));
				entity.setUsr_nickname(Encode(rs.getString(4)));
				entity.setUsr_pswd(rs.getString(5));
				entity.setRole_guid(rs.getString(6));
				entity.setRole_name(Encode(rs.getString(7)));
				
				returnList.add(entity);
			}
			pstmt.close();
			
			returnDM.setUsrListData(returnList);
			subSQL="";
			subSQLWhere="";
			subSQL+="SELECT COUNT(*) FROM USR_MAIN WHERE IS_DELETED=0";
			if(usr_name!=null&&usr_name.length()!=0){
				subSQLWhere +=" AND USR_NAME LIKE ?";
			}
			if(role_guid!=null&&role_guid.length()!=0){
				subSQLWhere +=" AND ROLE_GUID =?";
			}
			pstmt = conn.prepareStatement(subSQL+subSQLWhere);
			index = 0;
			if(usr_name!=null&&usr_name.length()!=0){
				pstmt.setString(++index, "%"+usr_name+"%");
			}
			if(role_guid!=null&&role_guid.length()!=0){
				pstmt.setString(++index, role_guid);
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

	private USR_DM getSysUsrs_sqlserver(String usr_name, String role_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		usr_name=Decode(usr_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;
		
		USR_DM returnDM = new USR_DM();
		List<USR_MAIN> returnList = new ArrayList<USR_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "SELECT T.USR_MAIN_GUID, T.USR_MAIN_ID, T.USR_NAME, T.USR_NICKNAME, T.USR_PSWD, T.ROLE_GUID, T1.ROLE_NAME, T.CREATED_DT FROM USR_MAIN T LEFT JOIN ROLE_MAIN T1 ON T1.IS_DELETED=0 AND T.ROLE_GUID = T1.ROLE_MAIN_GUID WHERE T.IS_DELETED=0";
			String subSQLWhere ="";
			String subOrderby=" ORDER BY CREATED_DT DESC ";
			if(usr_name!=null&&usr_name.length()!=0){
				subSQLWhere +=" AND T.USR_NAME LIKE ?";
			}
			if(role_guid!=null&&role_guid.length()!=0){
				subSQLWhere +=" AND T.ROLE_GUID =?";
			}
			subSQL = subSQL+subSQLWhere;
			String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+subOrderby+") AS RN FROM ("+ subSQL +") A) B WHERE B.RN>=? AND B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if(usr_name!=null&&usr_name.length()!=0){
				pstmt.setString(++index, "%"+usr_name+"%");
			}
			if(role_guid!=null&&role_guid.length()!=0){
				pstmt.setString(++index, role_guid);
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				USR_MAIN entity = new USR_MAIN();
				entity.setUsr_main_guid(rs.getString(1));
				entity.setUsr_main_id(rs.getString(2));
				entity.setUsr_name(Encode(rs.getString(3)));
				entity.setUsr_nickname(Encode(rs.getString(4)));
				entity.setUsr_pswd(rs.getString(5));
				entity.setRole_guid(rs.getString(6));
				entity.setRole_name(Encode(rs.getString(7)));
				
				returnList.add(entity);
			}
			pstmt.close();
			
			returnDM.setUsrListData(returnList);
			subSQL="";
			subSQLWhere="";
			subSQL+="SELECT COUNT(*) FROM USR_MAIN WHERE IS_DELETED=0";
			if(usr_name!=null&&usr_name.length()!=0){
				subSQLWhere +=" AND USR_NAME LIKE ?";
			}
			if(role_guid!=null&&role_guid.length()!=0){
				subSQLWhere +=" AND ROLE_GUID =?";
			}
			pstmt = conn.prepareStatement(subSQL+subSQLWhere);
			index = 0;
			if(usr_name!=null&&usr_name.length()!=0){
				pstmt.setString(++index, "%"+usr_name+"%");
			}
			if(role_guid!=null&&role_guid.length()!=0){
				pstmt.setString(++index, role_guid);
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
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}
	
	
	
	private USR_DM getSysUsrs_mysql(String usr_name, String role_guid,
			int page_no, int page_size, Connection conn) throws Exception {
		usr_name=Decode(usr_name);
		USR_DM returnDM = new USR_DM();
		List<USR_MAIN> returnList = new ArrayList<USR_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT T.USR_MAIN_GUID, T.USR_MAIN_ID, T.USR_NAME, T.USR_NICKNAME, T.USR_PSWD, T.ROLE_GUID, T1.ROLE_NAME FROM USR_MAIN AS T LEFT JOIN ROLE_MAIN AS T1 ON T1.IS_DELETED=0 AND T.ROLE_GUID = T1.ROLE_MAIN_GUID WHERE T.IS_DELETED=0";
			String sSQLWhere ="";
			if(usr_name!=null&&usr_name.length()!=0){
				sSQLWhere +=" AND T.USR_NAME LIKE ?";
			}
			if(role_guid!=null&&role_guid.length()!=0){
				sSQLWhere +=" AND T.ROLE_GUID =?";
			}
			pstmt = conn.prepareStatement(sSQL+sSQLWhere+" ORDER BY T.CREATED_DT DESC LIMIT "+ (page_no-1)*page_size + "," + page_size);
			int index = 0;
			if(usr_name!=null&&usr_name.length()!=0){
				pstmt.setString(++index, "%"+usr_name+"%");
			}
			if(role_guid!=null&&role_guid.length()!=0){
				pstmt.setString(++index, role_guid);
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				USR_MAIN entity = new USR_MAIN();
				entity.setUsr_main_guid(rs.getString(1));
				entity.setUsr_main_id(rs.getString(2));
				entity.setUsr_name(Encode(rs.getString(3)));
				entity.setUsr_nickname(Encode(rs.getString(4)));
				entity.setUsr_pswd(rs.getString(5));
				entity.setRole_guid(rs.getString(6));
				entity.setRole_name(Encode(rs.getString(7)));
				
				returnList.add(entity);
			}
			pstmt.close();
			
			returnDM.setUsrListData(returnList);
			sSQL="";
			sSQLWhere="";
			sSQL+="SELECT COUNT(*) FROM USR_MAIN WHERE IS_DELETED=0";
			if(usr_name!=null&&usr_name.length()!=0){
				sSQLWhere +=" AND USR_NAME LIKE ?";
			}
			if(role_guid!=null&&role_guid.length()!=0){
				sSQLWhere +=" AND ROLE_GUID =?";
			}
			pstmt = conn.prepareStatement(sSQL+sSQLWhere);
			index = 0;
			if(usr_name!=null&&usr_name.length()!=0){
				pstmt.setString(++index, "%"+usr_name+"%");
			}
			if(role_guid!=null&&role_guid.length()!=0){
				pstmt.setString(++index, role_guid);
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
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnDM;
	}


}
