package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.entities.ORG_MAIN;
import com.xinyou.frame.domain.models.ORG_NODE;
import com.xinyou.util.Config;
import com.xinyou.util.StringUtil;

public class ORG_BIZ extends StringUtil{

	public void addOrg(ORG_MAIN org, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  addOrg_oracle(org, conn);
	                 		break;
	        case "sqlserver": addOrg_sqlserver(org, conn);
							break;
	        default: addOrg_mysql(org, conn);
	        				break;
		}
	}

	public List<ORG_NODE> getOrgs(String coguid, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		List<ORG_NODE> nodeTreeList = new ArrayList<ORG_NODE>();
		switch (dataSourceType) {
	        case "oracle":  nodeTreeList = getOrgs_oracle(coguid, conn);
	                 		break;
	        case "sqlserver": nodeTreeList = getOrgs_sqlserver(coguid, conn);
							break;
	        default: nodeTreeList = getOrgs_mysql(coguid, conn);
	        				break;
		}
		return nodeTreeList;
	}

	public void delOrg(ORG_MAIN org, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  delOrg_oracle(org, conn);
	                 		break;
	        case "sqlserver": delOrg_sqlserver(org, conn);
							break;
	        default: delOrg_mysql(org, conn);
	        				break;
		}
	}

	public ORG_MAIN getOrg(String orgguid, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		ORG_MAIN returnEntity = new ORG_MAIN();
		switch (dataSourceType) {
	        case "oracle":  returnEntity = getOrg_oracle(orgguid, conn);
	                 		break;
	        case "sqlserver": returnEntity = getOrg_sqlserver(orgguid, conn);
							break;
	        default: returnEntity = getOrg_mysql(orgguid, conn);
	        				break;
		}
		return returnEntity;
	}

	public void updateOrg(ORG_MAIN org, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  updateOrg_oracle(org, conn);
	                 		break;
	        case "sqlserver": updateOrg_sqlserver(org, conn);
							break;
	        default: updateOrg_mysql(org, conn);
	        				break;
		}
	}

	private void addOrg_mysql(ORG_MAIN org, Connection conn) throws Exception {
		org.setOrg_main_id(Decode(org.getOrg_main_id()));
		org.setOrg_name(Decode(org.getOrg_name()));
		org.setOrg_seqno(Decode(org.getOrg_seqno()));
		org.setNode_img(Decode(org.getNode_img()));
		org.setOrg_desc(Decode(org.getOrg_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select t.ORG_MAIN_GUID, t1.CO_NAME FROM ORG_MAIN as t inner join CO_MAIN as t1 on t1.IS_DELETED=0 and t.CO_GUID = t1.CO_MAIN_GUID where t.IS_DELETED=0 and t.CO_GUID = ? and t.ORG_MAIN_ID=? limit 1");
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_main_id());
			rs = pstmt.executeQuery();
			String orgGuid = "", coName="";
			if(rs.next()){
				orgGuid = rs.getString(1);
				coName = rs.getString(2);
			}
			pstmt.close();
			if(orgGuid!=null&&orgGuid.length()!=0){
				if(coName==null)
					coName="";
				throw new Exception("公司 "+coName+" 中组织代码 "+org.getOrg_main_id()+" 已存在");
			}
			
			pstmt = conn.prepareStatement("select 1 FROM ORG_MAIN where IS_DELETED=0 and CO_GUID = ? and ORG_SEQNO=? limit 1");
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("组织序号 "+org.getOrg_seqno()+" 已存在");
			}
			
			String iSQL = "insert into ORG_MAIN (ORG_MAIN_GUID,ORG_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ORG_NAME,ORG_SEQNO,NODE_IMG,ORG_DESC,CO_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(iSQL);
			int index=0;
			pstmt.setString(++index, UUID.randomUUID().toString());
			pstmt.setString(++index, org.getOrg_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, org.getCreated_by());
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, org.getUpdated_by());
			pstmt.setInt(++index, 0);
			pstmt.setString(++index, org.getClient_guid());
			pstmt.setString(++index, org.getOrg_name());
			pstmt.setString(++index, org.getOrg_seqno());
			pstmt.setString(++index, org.getNode_img());
			pstmt.setString(++index, org.getOrg_desc());
			pstmt.setString(++index, org.getCo_guid());
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

	private List<ORG_NODE> getOrgs_mysql(String coguid, Connection conn) throws Exception {
		List<ORG_NODE> nodeList = new ArrayList<ORG_NODE>();
		List<ORG_NODE> nodeTreeList = new ArrayList<ORG_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "select ORG_SEQNO,ORG_MAIN_GUID,ORG_MAIN_ID,ORG_NAME,NODE_IMG,ORG_DESC,CO_GUID from ORG_MAIN where IS_DELETED=0 and CO_GUID=? order by ORG_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, coguid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				index = 0;
				ORG_NODE node =new ORG_NODE();
				node.setOrg_seqno(rs.getString(++index));
				node.setKey(rs.getString(++index));
				node.setOrg_id(rs.getString(++index));
				node.setTitle(rs.getString(++index));
				node.setOrg_name(node.getTitle());
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setOrg_desc(rs.getString(++index));
				node.setCo_guid(rs.getString(++index));
				node.setExpand(true);
				
				nodeList.add(node);
			}
			pstmt.close();
			
			ORG_NODE currentNode = new ORG_NODE();
			
			int currentDepth = 0;
			for(ORG_NODE node :nodeList) {
				int nextDepth = (node.getOrg_seqno().length() + 2) / 3;
				
				if(nextDepth - currentDepth > 1) {
	                node.setOrg_seqno(node.getOrg_seqno().substring(0, 3 * (currentDepth + 1)));
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

	private void delOrg_mysql(ORG_MAIN org, Connection conn) throws Exception {
		PreparedStatement pstmt=null;
		try{
			String sSQL = "delete from ORG_MAIN where IS_DELETED=0 and CO_GUID=? and ORG_SEQNO like ?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_seqno()+"%");
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

	private ORG_MAIN getOrg_mysql(String orgguid, Connection conn) throws Exception {
		ORG_MAIN returnEntity = new ORG_MAIN();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String sSQL = "select ORG_MAIN_GUID,ORG_MAIN_ID,ORG_NAME,ORG_SEQNO,ORG_DESC,CO_GUID from ORG_MAIN where IS_DELETED=0 and ORG_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, orgguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setOrg_main_guid(rs.getString(1));
				returnEntity.setOrg_main_id(Encode(rs.getString(2)));
				returnEntity.setOrg_name(Encode(rs.getString(3)));
				returnEntity.setOrg_seqno(Encode(rs.getString(4)));
				returnEntity.setOrg_desc(Encode(rs.getString(7)));
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

	private void updateOrg_mysql(ORG_MAIN org, Connection conn) throws Exception {
		org.setOrg_name(Decode(org.getOrg_name()));
		org.setOrg_seqno(Decode(org.getOrg_seqno()));
		org.setNode_img(Decode(org.getNode_img()));
		org.setOrg_desc(Decode(org.getOrg_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String cSQL = "select 1 from ORG_MAIN where CO_GUID=? and ORG_MAIN_GUID!=? and ORG_SEQNO=? limit 1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_main_guid());
			pstmt.setString(3, org.getOrg_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("组织序号 "+org.getOrg_seqno()+" 已存在");
			}
			String sSQL = "update ORG_MAIN set UPDATED_DT=?,UPDATED_BY=?,ORG_NAME=?,ORG_SEQNO=?,NODE_IMG=?,ORG_DESC=? where IS_DELETED=0 and CO_GUID=? and ORG_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, org.getUpdated_by());
			pstmt.setString(3, org.getOrg_name());
			pstmt.setString(4, org.getOrg_seqno());
			pstmt.setString(5, org.getNode_img());
			pstmt.setString(6, org.getOrg_desc());
			pstmt.setString(7, org.getCo_guid());
			pstmt.setString(8, org.getOrg_main_guid());
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

	private void addOrg_oracle(ORG_MAIN org, Connection conn) throws Exception {
		org.setOrg_main_id(Decode(org.getOrg_main_id()));
		org.setOrg_name(Decode(org.getOrg_name()));
		org.setOrg_seqno(Decode(org.getOrg_seqno()));
		org.setNode_img(Decode(org.getNode_img()));
		org.setOrg_desc(Decode(org.getOrg_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select t.ORG_MAIN_GUID, t1.CO_NAME FROM ORG_MAIN t inner join CO_MAIN t1 on t1.IS_DELETED=0 and t.CO_GUID = t1.CO_MAIN_GUID where t.IS_DELETED=0 and t.CO_GUID = ? and t.ORG_MAIN_ID=? and ROWNUM=1");
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_main_id());
			rs = pstmt.executeQuery();
			String orgGuid = "", coName="";
			if(rs.next()){
				orgGuid = rs.getString(1);
				coName = rs.getString(2);
			}
			pstmt.close();
			if(orgGuid!=null&&orgGuid.length()!=0){
				if(coName==null)
					coName="";
				throw new Exception("公司 "+coName+" 中组织代码 "+org.getOrg_main_id()+" 已存在");
			}
			
			pstmt = conn.prepareStatement("select 1 FROM ORG_MAIN where IS_DELETED=0 and CO_GUID = ? and ORG_SEQNO=? and ROWNUM=1");
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("组织序号 "+org.getOrg_seqno()+" 已存在");
			}
			
			String iSQL = "insert into ORG_MAIN (ORG_MAIN_GUID,ORG_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ORG_NAME,ORG_SEQNO,NODE_IMG,ORG_DESC,CO_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(iSQL);
			int index=0;
			pstmt.setString(++index, UUID.randomUUID().toString());
			pstmt.setString(++index, org.getOrg_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, org.getCreated_by());
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, org.getUpdated_by());
			pstmt.setInt(++index, 0);
			pstmt.setString(++index, org.getClient_guid());
			pstmt.setString(++index, org.getOrg_name());
			pstmt.setString(++index, org.getOrg_seqno());
			pstmt.setString(++index, org.getNode_img());
			pstmt.setString(++index, org.getOrg_desc());
			pstmt.setString(++index, org.getCo_guid());
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

	private List<ORG_NODE> getOrgs_oracle(String coguid, Connection conn) throws Exception {
		List<ORG_NODE> nodeList = new ArrayList<ORG_NODE>();
		List<ORG_NODE> nodeTreeList = new ArrayList<ORG_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "select ORG_SEQNO,ORG_MAIN_GUID,ORG_MAIN_ID,ORG_NAME,NODE_IMG,ORG_DESC,CO_GUID from ORG_MAIN where IS_DELETED=0 and CO_GUID=? order by ORG_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, coguid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				index = 0;
				ORG_NODE node =new ORG_NODE();
				node.setOrg_seqno(rs.getString(++index));
				node.setKey(rs.getString(++index));
				node.setOrg_id(rs.getString(++index));
				node.setTitle(rs.getString(++index));
				node.setOrg_name(node.getTitle());
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setOrg_desc(rs.getString(++index));
				node.setCo_guid(rs.getString(++index));
				node.setExpand(true);
				
				nodeList.add(node);
			}
			pstmt.close();
			
			ORG_NODE currentNode = new ORG_NODE();
			
			int currentDepth = 0;
			for(ORG_NODE node :nodeList) {
				int nextDepth = (node.getOrg_seqno().length() + 2) / 3;
				
				if(nextDepth - currentDepth > 1) {
                    node.setOrg_seqno(node.getOrg_seqno().substring(0, 3 * (currentDepth + 1)));
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

	private void delOrg_oracle(ORG_MAIN org, Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			String sSQL = "delete from ORG_MAIN where IS_DELETED=0 and CO_GUID=? and ORG_SEQNO like ?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_seqno()+"%");
			pstmt.execute();
			pstmt.close();
		}catch(SQLException e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
	}

	private ORG_MAIN getOrg_oracle(String orgguid, Connection conn) throws Exception {
		ORG_MAIN returnEntity = new ORG_MAIN();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String sSQL = "select ORG_MAIN_GUID,ORG_MAIN_ID,ORG_NAME,ORG_SEQNO,ORG_DESC,CO_GUID from ORG_MAIN where IS_DELETED=0 and ORG_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, orgguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setOrg_main_guid(rs.getString(1));
				returnEntity.setOrg_main_id(Encode(rs.getString(2)));
				returnEntity.setOrg_name(Encode(rs.getString(3)));
				returnEntity.setOrg_seqno(Encode(rs.getString(4)));
				returnEntity.setOrg_desc(Encode(rs.getString(7)));
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

	private void updateOrg_oracle(ORG_MAIN org, Connection conn) throws Exception {
		org.setOrg_name(Decode(org.getOrg_name()));
		org.setOrg_seqno(Decode(org.getOrg_seqno()));
		org.setNode_img(Decode(org.getNode_img()));
		org.setOrg_desc(Decode(org.getOrg_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String cSQL = "select 1 from ORG_MAIN where CO_GUID=? and ORG_MAIN_GUID!=? and ORG_SEQNO=? and ROWNUM=1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_main_guid());
			pstmt.setString(3, org.getOrg_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("组织序号 "+org.getOrg_seqno()+" 已存在");
			}
			String sSQL = "update ORG_MAIN set UPDATED_DT=?,UPDATED_BY=?,ORG_NAME=?,ORG_SEQNO=?,NODE_IMG=?,ORG_DESC=? where IS_DELETED=0 and CO_GUID=? and ORG_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, org.getUpdated_by());
			pstmt.setString(3, org.getOrg_name());
			pstmt.setString(4, org.getOrg_seqno());
			pstmt.setString(5, org.getNode_img());
			pstmt.setString(6, org.getOrg_desc());
			pstmt.setString(7, org.getCo_guid());
			pstmt.setString(8, org.getOrg_main_guid());
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

	private void addOrg_sqlserver(ORG_MAIN org, Connection conn) throws Exception {
		org.setOrg_main_id(Decode(org.getOrg_main_id()));
		org.setOrg_name(Decode(org.getOrg_name()));
		org.setOrg_seqno(Decode(org.getOrg_seqno()));
		org.setNode_img(Decode(org.getNode_img()));
		org.setOrg_desc(Decode(org.getOrg_desc()));
		
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select t.ORG_MAIN_GUID, t1.CO_NAME FROM ORG_MAIN t inner join CO_MAIN t1 on t1.IS_DELETED=0 and t.CO_GUID = t1.CO_MAIN_GUID where t.IS_DELETED=0 and t.CO_GUID = ? and t.ORG_MAIN_ID=?");
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_main_id());
			rs = pstmt.executeQuery();
			String orgGuid = "", coName="";
			if(rs.next()){
				orgGuid = rs.getString(1);
				coName = rs.getString(2);
			}
			pstmt.close();
			if(orgGuid!=null&&orgGuid.length()!=0){
				if(coName==null)
					coName="";
				throw new Exception("公司 "+coName+" 中组织代码 "+org.getOrg_main_id()+" 已存在");
			}
			
			pstmt = conn.prepareStatement("select top 1 1 FROM ORG_MAIN where IS_DELETED=0 and CO_GUID = ? and ORG_SEQNO=?");
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("组织序号 "+org.getOrg_seqno()+" 已存在");
			}
			
			String iSQL = "insert into ORG_MAIN (ORG_MAIN_GUID,ORG_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,IS_DELETED,CLIENT_GUID,ORG_NAME,ORG_SEQNO,NODE_IMG,ORG_DESC,CO_GUID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
			pstmt = conn.prepareStatement(iSQL);
			int index=0;
			pstmt.setString(++index, UUID.randomUUID().toString());
			pstmt.setString(++index, org.getOrg_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, org.getCreated_by());
			pstmt.setLong(++index, ldate);
			pstmt.setString(++index, org.getUpdated_by());
			pstmt.setInt(++index, 0);
			pstmt.setString(++index, org.getClient_guid());
			pstmt.setString(++index, org.getOrg_name());
			pstmt.setString(++index, org.getOrg_seqno());
			pstmt.setString(++index, org.getNode_img());
			pstmt.setString(++index, org.getOrg_desc());
			pstmt.setString(++index, org.getCo_guid());
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

	private List<ORG_NODE> getOrgs_sqlserver(String coguid, Connection conn) throws Exception {
		List<ORG_NODE> nodeList = new ArrayList<ORG_NODE>();
		List<ORG_NODE> nodeTreeList = new ArrayList<ORG_NODE>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int index = 0;
		try{
			String sSQL = "select ORG_SEQNO,ORG_MAIN_GUID,ORG_MAIN_ID,ORG_NAME,NODE_IMG,ORG_DESC,CO_GUID from ORG_MAIN where IS_DELETED=0 and CO_GUID=? order by ORG_SEQNO";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, coguid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				index = 0;
				ORG_NODE node =new ORG_NODE();
				node.setOrg_seqno(rs.getString(++index));
				node.setKey(rs.getString(++index));
				node.setOrg_id(rs.getString(++index));
				node.setTitle(rs.getString(++index));
				node.setOrg_name(node.getTitle());
				node.setNode_img(rs.getString(++index));
				if(node.getNode_img()!=null){
					String[] urlArray = org.apache.commons.lang3.StringUtils.split(node.getNode_img(), '/');
					if(urlArray.length>2){
						node.setIcon(urlArray[urlArray.length-2]+"/"+urlArray[urlArray.length-1]);
					}
				}
				node.setOrg_desc(rs.getString(++index));
				node.setCo_guid(rs.getString(++index));
				node.setExpand(true);
				
				nodeList.add(node);
			}
			pstmt.close();
			
			ORG_NODE currentNode = new ORG_NODE();
			
			int currentDepth = 0;
			for(ORG_NODE node :nodeList) {
				int nextDepth = (node.getOrg_seqno().length() + 2) / 3;
				
				if(nextDepth - currentDepth > 1) {
	                node.setOrg_seqno(node.getOrg_seqno().substring(0, 3 * (currentDepth + 1)));
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

	private void delOrg_sqlserver(ORG_MAIN org, Connection conn) throws SQLException {
		PreparedStatement pstmt=null;
		try{
			String sSQL = "delete from ORG_MAIN where IS_DELETED=0 and CO_GUID=? and ORG_SEQNO like ?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_seqno()+"%");
			pstmt.execute();
			pstmt.close();
		}catch(SQLException e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
	}

	private ORG_MAIN getOrg_sqlserver(String orgguid, Connection conn) throws Exception {
		ORG_MAIN returnEntity = new ORG_MAIN();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String sSQL = "select top 1 ORG_MAIN_GUID,ORG_MAIN_ID,ORG_NAME,ORG_SEQNO,ORG_DESC,CO_GUID from ORG_MAIN where IS_DELETED=0 and ORG_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, orgguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setOrg_main_guid(rs.getString(1));
				returnEntity.setOrg_main_id(Encode(rs.getString(2)));
				returnEntity.setOrg_name(Encode(rs.getString(3)));
				returnEntity.setOrg_seqno(Encode(rs.getString(4)));
				returnEntity.setOrg_desc(Encode(rs.getString(7)));
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

	private void updateOrg_sqlserver(ORG_MAIN org, Connection conn) throws Exception {
		org.setOrg_name(Decode(org.getOrg_name()));
		org.setOrg_seqno(Decode(org.getOrg_seqno()));
		org.setNode_img(Decode(org.getNode_img()));
		org.setOrg_desc(Decode(org.getOrg_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			String cSQL = "select top 1 1 from ORG_MAIN where CO_GUID=? and ORG_MAIN_GUID!=? and ORG_SEQNO=?";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, org.getCo_guid());
			pstmt.setString(2, org.getOrg_main_guid());
			pstmt.setString(3, org.getOrg_seqno());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("组织序号 "+org.getOrg_seqno()+" 已存在");
			}
			String sSQL = "update ORG_MAIN set UPDATED_DT=?,UPDATED_BY=?,ORG_NAME=?,ORG_SEQNO=?,NODE_IMG=?,ORG_DESC=? where IS_DELETED=0 and CO_GUID=? and ORG_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, org.getUpdated_by());
			pstmt.setString(3, org.getOrg_name());
			pstmt.setString(4, org.getOrg_seqno());
			pstmt.setString(5, org.getNode_img());
			pstmt.setString(6, org.getOrg_desc());
			pstmt.setString(7, org.getCo_guid());
			pstmt.setString(8, org.getOrg_main_guid());
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

	public List<ORG_MAIN> getSlOrg(String co_guid, Connection conn) throws Exception {
		List<ORG_MAIN> returnList = new ArrayList<ORG_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select ORG_MAIN_GUID, ORG_MAIN_ID, ORG_NAME, ORG_SEQNO from ORG_MAIN where IS_DELETED=0 and CO_GUID=? order by ORG_SEQNO");
			pstmt.setString(1, co_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ORG_MAIN entity = new ORG_MAIN();
				entity.setOrg_main_guid(rs.getString(1));
				entity.setOrg_main_id(rs.getString(2));
				entity.setOrg_name(Encode(rs.getString(3)));
				entity.setOrg_seqno(rs.getString(4));
				
				returnList.add(entity);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnList;
	}

}
