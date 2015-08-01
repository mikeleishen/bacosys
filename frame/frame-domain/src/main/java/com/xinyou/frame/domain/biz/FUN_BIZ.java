package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.entities.FUN_MAIN;
import com.xinyou.frame.domain.models.FUN_NODE;
import com.xinyou.util.Config;
import com.xinyou.util.StringUtil;


public class FUN_BIZ extends StringUtil{
	public void addSysFun(FUN_MAIN fun_main, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  addSysFun_oracle(fun_main, conn);
	                 		break;
	        case "sqlserver": addSysFun_sqlserver(fun_main, conn);
							break;
	        default: addSysFun_mysql(fun_main, conn);
	        				break;
		}
	}
	
	public FUN_MAIN getSysFun(FUN_MAIN entity, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		FUN_MAIN returnEntity = new FUN_MAIN();
		switch (dataSourceType) {
	        case "oracle":  returnEntity = getSysFun_oracle(entity, conn);
	                 		break;
	        case "sqlserver": returnEntity = getSysFun_sqlserver(entity, conn);
							break;
	        default: returnEntity = getSysFun_mysql(entity, conn);
	        				break;
		}
		return returnEntity;
	}

	public List<FUN_NODE> getSysFuns(String sys_guid, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		switch (dataSourceType) {
	        case "oracle":  nodeTreeList = getSysFuns_oracle(sys_guid, conn);
	                 		break;
	        case "sqlserver": nodeTreeList = getSysFuns_sqlserver(sys_guid, conn);
							break;
	        default: nodeTreeList = getSysFuns_mysql(sys_guid, conn);
	        				break;
		}
		return nodeTreeList;
	}

	public void updateSysFun(FUN_MAIN fun_main, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  updateSysFun_oracle(fun_main, conn);
	                 		break;
	        case "sqlserver": updateSysFun_sqlserver(fun_main, conn);
							break;
	        default: updateSysFun_mysql(fun_main, conn);
	        				break;
		}
	}

	public void delSysFun(String sys_guid, String fun_seqno, Connection conn) throws Exception{
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		fun_seqno=Decode(fun_seqno);
		switch (dataSourceType) {
	        case "oracle":  delSysFun_oracle(sys_guid, fun_seqno, conn);
	                 		break;
	        case "sqlserver": delSysFun_sqlserver(sys_guid, fun_seqno, conn);
							break;
	        default: delSysFun_mysql(sys_guid, fun_seqno, conn);
	        				break;
		}
	}

	private void addSysFun_mysql(FUN_MAIN fun_main, Connection conn) throws Exception {
		fun_main.setFun_main_id(Decode(fun_main.getFun_main_id()));
		fun_main.setFun_name(Decode(fun_main.getFun_name()));
		fun_main.setFun_seqno(Decode(fun_main.getFun_seqno()));
		fun_main.setFun_param(Decode(fun_main.getFun_param()));
		fun_main.setFun_ass(Decode(fun_main.getFun_ass()));
		fun_main.setFun_class(Decode(fun_main.getFun_class()));
		fun_main.setFun_method(Decode(fun_main.getFun_method()));
		fun_main.setFun_url(Decode(fun_main.getFun_url()));
		fun_main.setNode_img(Decode(fun_main.getNode_img()));
		fun_main.setFun_desc(Decode(fun_main.getFun_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT T.FUN_MAIN_GUID, T1.SYS_NAME FROM FUN_MAIN AS T INNER JOIN BIZ_SYS AS T1 ON T1.IS_DELETED=0 AND T.BIZ_SYS_GUID = T1.BIZ_SYS_GUID WHERE T.IS_DELETED=0 AND T.BIZ_SYS_GUID = ? AND T.FUN_MAIN_ID=? LIMIT 1");
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_main_id());
			rs = pstmt.executeQuery();
			String funGuid="", sysName="";
			if(rs.next()){
				funGuid=rs.getString(1);
				sysName=rs.getString(2);
			}
			rs.close();
			pstmt.close();
			
			if(funGuid!=null&&funGuid.length()!=0)throw new Exception("系统 "+sysName+" 中功能代码 "+fun_main.getFun_main_id()+" 已存在");
			
			pstmt = conn.prepareStatement("SELECT 1 FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID = ? AND FUN_SEQNO=? LIMIT 1");
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			rs.close();
			pstmt.close();
			
			if(exist){
				throw new Exception("功能序号 "+fun_main.getFun_seqno()+" 已存在");
			}
			
			String iSQL = "INSERT INTO FUN_MAIN (FUN_MAIN_GUID,FUN_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,FUN_NAME,FUN_SEQNO,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,NODE_EXPAND,FUN_DESC,BIZ_SYS_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(iSQL);
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, fun_main.getFun_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(3, ldate);
			pstmt.setString(4, fun_main.getCreated_by());
			pstmt.setLong(5, ldate);
			pstmt.setString(6, fun_main.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, fun_main.getClient_guid());
			pstmt.setString(9, fun_main.getFun_name());
			pstmt.setString(10, fun_main.getFun_seqno());
			pstmt.setString(11, fun_main.getFun_param());
			pstmt.setString(12, fun_main.getFun_ass());
			pstmt.setString(13, fun_main.getFun_class());
			pstmt.setString(14, fun_main.getFun_method());
			pstmt.setString(15, fun_main.getFun_url());
			pstmt.setString(16, fun_main.getNode_img());
			pstmt.setInt(17, fun_main.getNode_expand());
			pstmt.setString(18, fun_main.getFun_desc());
			pstmt.setString(19, fun_main.getBiz_sys_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	private FUN_MAIN getSysFun_mysql(FUN_MAIN entity, Connection conn) throws Exception {
		FUN_MAIN returnEntity = new FUN_MAIN();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT FUN_MAIN_GUID,FUN_MAIN_ID,FUN_NAME,FUN_SEQNO,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,FUN_DESC,BIZ_SYS_GUID FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, entity.getBiz_sys_guid());
			pstmt.setString(2, entity.getFun_main_guid());
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setFun_main_guid(rs.getString(1));
				returnEntity.setFun_main_id(Encode(rs.getString(2)));
				returnEntity.setFun_name(Encode(rs.getString(3)));
				returnEntity.setFun_seqno(Encode(rs.getString(4)));
				returnEntity.setFun_param(Encode(rs.getString(5)));
				returnEntity.setFun_ass(Encode(rs.getString(6)));
				returnEntity.setFun_class(Encode(rs.getString(7)));
				returnEntity.setFun_method(Encode(rs.getString(8)));
				returnEntity.setNode_img(Encode(rs.getString(9)));
				returnEntity.setFun_url(Encode(rs.getString(10)));
				returnEntity.setFun_desc(Encode(rs.getString(11)));
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

	private List<FUN_NODE> getSysFuns_mysql(String sys_guid, Connection conn) throws Exception {
		List<FUN_NODE> nodeList = new ArrayList<FUN_NODE>();
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "SELECT FUN_SEQNO,FUN_MAIN_GUID,FUN_MAIN_ID,FUN_NAME,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,NODE_EXPAND,FUN_DESC,BIZ_SYS_GUID FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? ORDER BY FUN_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, sys_guid);
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
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setHref(node.getFun_url());
				
				if(rs.getInt(++index)==0)node.setExpand(false);
				else node.setExpand(true);
				
				node.setFun_desc(rs.getString(++index));
				node.setBiz_sys_guid(rs.getString(++index));
				node.setExpand(true);
				
				nodeList.add(node);
			}
			rs.close();
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

	private void updateSysFun_mysql(FUN_MAIN fun_main, Connection conn) throws Exception {
		fun_main.setFun_name(Decode(fun_main.getFun_name()));
		fun_main.setFun_seqno(Decode(fun_main.getFun_seqno()));
		fun_main.setFun_param(Decode(fun_main.getFun_param()));
		fun_main.setFun_ass(Decode(fun_main.getFun_ass()));
		fun_main.setFun_class(Decode(fun_main.getFun_class()));
		fun_main.setFun_method(Decode(fun_main.getFun_method()));
		fun_main.setFun_url(Decode(fun_main.getFun_url()));
		fun_main.setNode_img(Decode(fun_main.getNode_img()));
		fun_main.setFun_desc(Decode(fun_main.getFun_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String cSQL = "SELECT 1 FROM FUN_MAIN WHERE BIZ_SYS_GUID=? AND FUN_MAIN_GUID!=? AND FUN_SEQNO=? LIMIT 1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_main_guid());
			pstmt.setString(3, fun_main.getFun_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			rs.close();
			pstmt.close();
			
			if(exist){
				throw new Exception("功能序号 "+fun_main.getFun_seqno()+" 已存在");
			}
			String sSQL = "UPDATE FUN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,FUN_NAME=?,FUN_SEQNO=?,FUN_PARAM=?,FUN_ASS=?,FUN_CLASS=?,FUN_METHOD=?,FUN_URL=?,NODE_IMG=?,NODE_EXPAND=?,FUN_DESC=? WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, fun_main.getUpdated_by());
			pstmt.setString(3, fun_main.getFun_name());
			pstmt.setString(4, fun_main.getFun_seqno());
			pstmt.setString(5, fun_main.getFun_param());
			pstmt.setString(6, fun_main.getFun_ass());
			pstmt.setString(7, fun_main.getFun_class());
			pstmt.setString(8, fun_main.getFun_method());
			pstmt.setString(9, fun_main.getFun_url());
			pstmt.setString(10, fun_main.getNode_img());
			pstmt.setInt(11, fun_main.getNode_expand());
			pstmt.setString(12, fun_main.getFun_desc());
			pstmt.setString(13, fun_main.getBiz_sys_guid());
			pstmt.setString(14, fun_main.getFun_main_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	private void delSysFun_mysql(String sys_guid, String fun_seqno, Connection conn) throws Exception {
		PreparedStatement pstmt=null;
		try{
			String sSQL = "DELETE FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_SEQNO LIKE ?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, sys_guid);
			pstmt.setString(2, fun_seqno+"%");
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	private void addSysFun_oracle(FUN_MAIN fun_main, Connection conn) throws Exception {
		fun_main.setFun_main_id(Decode(fun_main.getFun_main_id()));
		fun_main.setFun_name(Decode(fun_main.getFun_name()));
		fun_main.setFun_seqno(Decode(fun_main.getFun_seqno()));
		fun_main.setFun_param(Decode(fun_main.getFun_param()));
		fun_main.setFun_ass(Decode(fun_main.getFun_ass()));
		fun_main.setFun_class(Decode(fun_main.getFun_class()));
		fun_main.setFun_method(Decode(fun_main.getFun_method()));
		fun_main.setFun_url(Decode(fun_main.getFun_url()));
		fun_main.setNode_img(Decode(fun_main.getNode_img()));
		fun_main.setFun_desc(Decode(fun_main.getFun_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT T.FUN_MAIN_GUID, T1.SYS_NAME FROM FUN_MAIN T INNER JOIN BIZ_SYS T1 ON T1.IS_DELETED=0 AND T.BIZ_SYS_GUID = T1.BIZ_SYS_GUID WHERE T.IS_DELETED=0 AND T.BIZ_SYS_GUID = ? AND T.FUN_MAIN_ID=? AND ROWNUM=1");
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_main_id());
			rs = pstmt.executeQuery();
			String funGuid="", sysName="";
			if(rs.next()){
				funGuid=rs.getString(1);
				sysName=rs.getString(2);
			}
			rs.close();
			pstmt.close();
			
			if(funGuid!=null&&funGuid.length()!=0)throw new Exception("系统 "+sysName+" 中功能代码 "+fun_main.getFun_main_id()+" 已存在");
			
			pstmt = conn.prepareStatement("select 1 FROM FUN_MAIN where IS_DELETED=0 and BIZ_SYS_GUID = ? and FUN_SEQNO=? and ROWNUM=1");
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			rs.close();
			pstmt.close();
			
			if(exist){
				throw new Exception("功能序号 "+fun_main.getFun_seqno()+" 已存在");
			}
			
			String iSQL = "INSERT INTO FUN_MAIN (FUN_MAIN_GUID,FUN_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,FUN_NAME,FUN_SEQNO,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,NODE_EXPAND,FUN_DESC,BIZ_SYS_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(iSQL);
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, fun_main.getFun_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(3, ldate);
			pstmt.setString(4, fun_main.getCreated_by());
			pstmt.setLong(5, ldate);
			pstmt.setString(6, fun_main.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, fun_main.getClient_guid());
			pstmt.setString(9, fun_main.getFun_name());
			pstmt.setString(10, fun_main.getFun_seqno());
			pstmt.setString(11, fun_main.getFun_param());
			pstmt.setString(12, fun_main.getFun_ass());
			pstmt.setString(13, fun_main.getFun_class());
			pstmt.setString(14, fun_main.getFun_method());
			pstmt.setString(15, fun_main.getFun_url());
			pstmt.setString(16, fun_main.getNode_img());
			pstmt.setInt(17, fun_main.getNode_expand());
			pstmt.setString(18, fun_main.getFun_desc());
			pstmt.setString(19, fun_main.getBiz_sys_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	private FUN_MAIN getSysFun_oracle(FUN_MAIN entity, Connection conn) throws Exception {
		FUN_MAIN returnEntity = new FUN_MAIN();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT FUN_MAIN_GUID,FUN_MAIN_ID,FUN_NAME,FUN_SEQNO,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,FUN_DESC,BIZ_SYS_GUID FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, entity.getBiz_sys_guid());
			pstmt.setString(2, entity.getFun_main_guid());
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setFun_main_guid(rs.getString(1));
				returnEntity.setFun_main_id(Encode(rs.getString(2)));
				returnEntity.setFun_name(Encode(rs.getString(3)));
				returnEntity.setFun_seqno(Encode(rs.getString(4)));
				returnEntity.setFun_param(Encode(rs.getString(5)));
				returnEntity.setFun_ass(Encode(rs.getString(6)));
				returnEntity.setFun_class(Encode(rs.getString(7)));
				returnEntity.setFun_method(Encode(rs.getString(8)));
				returnEntity.setNode_img(Encode(rs.getString(9)));
				returnEntity.setFun_url(Encode(rs.getString(10)));
				returnEntity.setFun_desc(Encode(rs.getString(11)));
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

	private List<FUN_NODE> getSysFuns_oracle(String sys_guid, Connection conn) throws Exception {
		List<FUN_NODE> nodeList = new ArrayList<FUN_NODE>();
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "SELECT FUN_SEQNO,FUN_MAIN_GUID,FUN_MAIN_ID,FUN_NAME,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,NODE_EXPAND,FUN_DESC,BIZ_SYS_GUID FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? ORDER BY FUN_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, sys_guid);
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
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setHref(node.getFun_url());
				
				if(rs.getInt(++index)==0)node.setExpand(false);
				else node.setExpand(true);
				
				node.setFun_desc(rs.getString(++index));
				node.setBiz_sys_guid(rs.getString(++index));
				node.setExpand(true);
				
				nodeList.add(node);
			}
			rs.close();
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

	private void updateSysFun_oracle(FUN_MAIN fun_main, Connection conn) throws Exception {
		fun_main.setFun_name(Decode(fun_main.getFun_name()));
		fun_main.setFun_seqno(Decode(fun_main.getFun_seqno()));
		fun_main.setFun_param(Decode(fun_main.getFun_param()));
		fun_main.setFun_ass(Decode(fun_main.getFun_ass()));
		fun_main.setFun_class(Decode(fun_main.getFun_class()));
		fun_main.setFun_method(Decode(fun_main.getFun_method()));
		fun_main.setFun_url(Decode(fun_main.getFun_url()));
		fun_main.setNode_img(Decode(fun_main.getNode_img()));
		fun_main.setFun_desc(Decode(fun_main.getFun_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String cSQL = "SELECT 1 FROM FUN_MAIN WHERE BIZ_SYS_GUID=? AND FUN_MAIN_GUID!=? AND FUN_SEQNO=? AND ROWNUM=1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_main_guid());
			pstmt.setString(3, fun_main.getFun_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			rs.close();
			pstmt.close();
			
			if(exist){
				throw new Exception("功能序号 "+fun_main.getFun_seqno()+" 已存在");
			}
			
			String sSQL = "UPDATE FUN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,FUN_NAME=?,FUN_SEQNO=?,FUN_PARAM=?,FUN_ASS=?,FUN_CLASS=?,FUN_METHOD=?,FUN_URL=?,NODE_IMG=?,NODE_EXPAND=?,FUN_DESC=? WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, fun_main.getUpdated_by());
			pstmt.setString(3, fun_main.getFun_name());
			pstmt.setString(4, fun_main.getFun_seqno());
			pstmt.setString(5, fun_main.getFun_param());
			pstmt.setString(6, fun_main.getFun_ass());
			pstmt.setString(7, fun_main.getFun_class());
			pstmt.setString(8, fun_main.getFun_method());
			pstmt.setString(9, fun_main.getFun_url());
			pstmt.setString(10, fun_main.getNode_img());
			pstmt.setInt(11, fun_main.getNode_expand());
			pstmt.setString(12, fun_main.getFun_desc());
			pstmt.setString(13, fun_main.getBiz_sys_guid());
			pstmt.setString(14, fun_main.getFun_main_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	private void delSysFun_oracle(String sys_guid, String fun_seqno, Connection conn) throws Exception {
		PreparedStatement pstmt=null;
		try{
			String sSQL = "DELETE FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_SEQNO LIKE ?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, sys_guid);
			pstmt.setString(2, fun_seqno+"%");
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	private void addSysFun_sqlserver(FUN_MAIN fun_main, Connection conn) throws Exception {
		fun_main.setFun_main_id(Decode(fun_main.getFun_main_id()));
		fun_main.setFun_name(Decode(fun_main.getFun_name()));
		fun_main.setFun_seqno(Decode(fun_main.getFun_seqno()));
		fun_main.setFun_param(Decode(fun_main.getFun_param()));
		fun_main.setFun_ass(Decode(fun_main.getFun_ass()));
		fun_main.setFun_class(Decode(fun_main.getFun_class()));
		fun_main.setFun_method(Decode(fun_main.getFun_method()));
		fun_main.setFun_url(Decode(fun_main.getFun_url()));
		fun_main.setNode_img(Decode(fun_main.getNode_img()));
		fun_main.setFun_desc(Decode(fun_main.getFun_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT T.FUN_MAIN_GUID, T1.SYS_NAME FROM FUN_MAIN T INNER JOIN BIZ_SYS T1 ON T1.IS_DELETED=0 AND T.BIZ_SYS_GUID = T1.BIZ_SYS_GUID WHERE T.IS_DELETED=0 AND T.BIZ_SYS_GUID = ? AND T.FUN_MAIN_ID=?");
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_main_id());
			rs = pstmt.executeQuery();
			String funGuid="", sysName="";
			if(rs.next()){
				funGuid=rs.getString(1);
				sysName=rs.getString(2);
			}
			rs.close();
			pstmt.close();
			
			if(funGuid!=null&&funGuid.length()!=0)throw new Exception("系统 "+sysName+" 中功能代码 "+fun_main.getFun_main_id()+" 已存在");
			
			pstmt = conn.prepareStatement("SELECT TOP 1 1 FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID = ? AND FUN_SEQNO=?");
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			rs.close();
			pstmt.close();
			
			if(exist){
				throw new Exception("功能序号 "+fun_main.getFun_seqno()+" 已存在");
			}
			
			String iSQL = "INSERT INTO FUN_MAIN (FUN_MAIN_GUID,FUN_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,FUN_NAME,FUN_SEQNO,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,NODE_EXPAND,FUN_DESC,BIZ_SYS_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
			pstmt = conn.prepareStatement(iSQL);
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, fun_main.getFun_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(3, ldate);
			pstmt.setString(4, fun_main.getCreated_by());
			pstmt.setLong(5, ldate);
			pstmt.setString(6, fun_main.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, fun_main.getClient_guid());
			pstmt.setString(9, fun_main.getFun_name());
			pstmt.setString(10, fun_main.getFun_seqno());
			pstmt.setString(11, fun_main.getFun_param());
			pstmt.setString(12, fun_main.getFun_ass());
			pstmt.setString(13, fun_main.getFun_class());
			pstmt.setString(14, fun_main.getFun_method());
			pstmt.setString(15, fun_main.getFun_url());
			pstmt.setString(16, fun_main.getNode_img());
			pstmt.setInt(17, fun_main.getNode_expand());
			pstmt.setString(18, fun_main.getFun_desc());
			pstmt.setString(19, fun_main.getBiz_sys_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	private FUN_MAIN getSysFun_sqlserver(FUN_MAIN entity, Connection conn) throws Exception {
		FUN_MAIN returnEntity = new FUN_MAIN();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String sSQL = "SELECT FUN_MAIN_GUID,FUN_MAIN_ID,FUN_NAME,FUN_SEQNO,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,FUN_DESC,BIZ_SYS_GUID FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, entity.getBiz_sys_guid());
			pstmt.setString(2, entity.getFun_main_guid());
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setFun_main_guid(rs.getString(1));
				returnEntity.setFun_main_id(Encode(rs.getString(2)));
				returnEntity.setFun_name(Encode(rs.getString(3)));
				returnEntity.setFun_seqno(Encode(rs.getString(4)));
				returnEntity.setFun_param(Encode(rs.getString(5)));
				returnEntity.setFun_ass(Encode(rs.getString(6)));
				returnEntity.setFun_class(Encode(rs.getString(7)));
				returnEntity.setFun_method(Encode(rs.getString(8)));
				returnEntity.setNode_img(Encode(rs.getString(9)));
				returnEntity.setFun_url(Encode(rs.getString(10)));
				returnEntity.setFun_desc(Encode(rs.getString(11)));
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

	private List<FUN_NODE> getSysFuns_sqlserver(String sys_guid, Connection conn) throws Exception {
		List<FUN_NODE> nodeList = new ArrayList<FUN_NODE>();
		List<FUN_NODE> nodeTreeList = new ArrayList<FUN_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "SELECT FUN_SEQNO,FUN_MAIN_GUID,FUN_MAIN_ID,FUN_NAME,FUN_PARAM,FUN_ASS,FUN_CLASS,FUN_METHOD,FUN_URL,NODE_IMG,NODE_EXPAND,FUN_DESC,BIZ_SYS_GUID FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? ORDER BY FUN_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, sys_guid);
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
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setHref(node.getFun_url());
				
				if(rs.getInt(++index)==0)node.setExpand(true);
				else node.setExpand(false);
				
				node.setFun_desc(rs.getString(++index));
				node.setBiz_sys_guid(rs.getString(++index));
				
				nodeList.add(node);
			}
			rs.close();
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

	private void updateSysFun_sqlserver(FUN_MAIN fun_main, Connection conn) throws Exception {
		fun_main.setFun_name(Decode(fun_main.getFun_name()));
		fun_main.setFun_seqno(Decode(fun_main.getFun_seqno()));
		fun_main.setFun_param(Decode(fun_main.getFun_param()));
		fun_main.setFun_ass(Decode(fun_main.getFun_ass()));
		fun_main.setFun_class(Decode(fun_main.getFun_class()));
		fun_main.setFun_method(Decode(fun_main.getFun_method()));
		fun_main.setFun_url(Decode(fun_main.getFun_url()));
		fun_main.setNode_img(Decode(fun_main.getNode_img()));
		fun_main.setFun_desc(Decode(fun_main.getFun_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String cSQL = "SELECT TOP 1 1 FROM FUN_MAIN WHERE BIZ_SYS_GUID=? AND FUN_MAIN_GUID!=? AND FUN_SEQNO=?";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, fun_main.getBiz_sys_guid());
			pstmt.setString(2, fun_main.getFun_main_guid());
			pstmt.setString(3, fun_main.getFun_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			rs.close();
			pstmt.close();
			
			if(exist){
				throw new Exception("功能序号 "+fun_main.getFun_seqno()+" 已存在");
			}
			String sSQL = "UPDATE FUN_MAIN SET UPDATED_DT=?,UPDATED_BY=?,FUN_NAME=?,FUN_SEQNO=?,FUN_PARAM=?,FUN_ASS=?,FUN_CLASS=?,FUN_METHOD=?,FUN_URL=?,NODE_IMG=?,NODE_EXPAND=?,FUN_DESC=? WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, fun_main.getUpdated_by());
			pstmt.setString(3, fun_main.getFun_name());
			pstmt.setString(4, fun_main.getFun_seqno());
			pstmt.setString(5, fun_main.getFun_param());
			pstmt.setString(6, fun_main.getFun_ass());
			pstmt.setString(7, fun_main.getFun_class());
			pstmt.setString(8, fun_main.getFun_method());
			pstmt.setString(9, fun_main.getFun_url());
			pstmt.setString(10, fun_main.getNode_img());
			pstmt.setInt(11, fun_main.getNode_expand());
			pstmt.setString(12, fun_main.getFun_desc());
			pstmt.setString(13, fun_main.getBiz_sys_guid());
			pstmt.setString(14, fun_main.getFun_main_guid());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}

	private void delSysFun_sqlserver(String sys_guid, String fun_seqno, Connection conn) throws Exception {
		PreparedStatement pstmt=null;
		try{
			String sSQL = "DELETE FROM FUN_MAIN WHERE IS_DELETED=0 AND BIZ_SYS_GUID=? AND FUN_SEQNO LIKE ?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, sys_guid);
			pstmt.setString(2, fun_seqno+"%");
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
	}
}
