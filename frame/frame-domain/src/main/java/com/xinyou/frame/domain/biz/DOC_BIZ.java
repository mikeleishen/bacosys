package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.entities.DOC_MAIN;
import com.xinyou.frame.domain.entities.PARAM_MAIN;
import com.xinyou.frame.domain.entities.PRIVILEGE_MAIN;
import com.xinyou.frame.domain.models.DOC_DM;
import com.xinyou.frame.domain.models.PRIVILEGE_DM;
import com.xinyou.util.Config;
import com.xinyou.util.StringUtil;

public class DOC_BIZ extends StringUtil{

	public void addDoc(DOC_MAIN doc, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  addDoc_oracle(doc, conn);
	                 		break;
	        case "sqlserver": addDoc_sqlserver(doc, conn);
							break;
	        default: addDoc_mysql(doc, conn);
	        				break;
		}
	}

	public DOC_DM getDocs(String doc_id, String doc_name, int page_no,
			int page_size, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		DOC_DM returnDM = new DOC_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getDoc_oracle(doc_id,doc_name,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getDoc_sqlserver(doc_id,doc_name,page_no,page_size,conn);
							break;
	        default: returnDM = getDoc_mysql(doc_id,doc_name,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}

	public void delDoc(String[] docguidArray, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  delDoc_oracle(docguidArray, conn);
	                 		break;
	        case "sqlserver": delDoc_sqlserver(docguidArray, conn);
							break;
	        default: delDoc_mysql(docguidArray, conn);
	        				break;
		}
	}

	public DOC_MAIN getDoc(String docguid, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		DOC_MAIN returnEntity = new DOC_MAIN();
		switch (dataSourceType) {
	        case "oracle":  returnEntity = getDoc_oracle(docguid, conn);
	                 		break;
	        case "sqlserver": returnEntity = getDoc_sqlserver(docguid, conn);
							break;
	        default: returnEntity = getDoc_mysql(docguid, conn);
	        				break;
		}
		return returnEntity;
	}

	public void updateDoc(DOC_MAIN doc, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  updateDoc_oracle(doc, conn);
	                 		break;
	        case "sqlserver": updateDoc_sqlserver(doc, conn);
							break;
	        default: updateDoc_mysql(doc, conn);
	        				break;
		}
	}

	private void updateDoc_mysql(DOC_MAIN doc, Connection conn) throws Exception {
		doc.setDoc_name(Decode(doc.getDoc_name()));
		doc.setDoc_desc(Decode(doc.getDoc_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update DOC_MAIN set UPDATED_DT=?, UPDATED_BY=?, DOC_NAME=?, DOC_DESC=?, DOC_PRE_TAG=?, DOC_MID_TAG_ID=?, DOC_SEQ_NO_LEN=?, DOC_STATUS_ID=? where IS_DELETED=0 and DOC_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, doc.getUpdated_by());
			pstmt.setString(3, doc.getDoc_name());
			pstmt.setString(4, doc.getDoc_desc());
			pstmt.setString(5, doc.getDoc_pre_tag());
			pstmt.setString(6, doc.getDoc_mid_tag_id());
			pstmt.setInt(7, doc.getDoc_seq_no_len());
			pstmt.setString(8, doc.getDoc_status_id());
			pstmt.setString(9, doc.getDoc_main_guid());
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

	private void updateDoc_oracle(DOC_MAIN doc, Connection conn) throws Exception {
		doc.setDoc_name(Decode(doc.getDoc_name()));
		doc.setDoc_desc(Decode(doc.getDoc_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update DOC_MAIN set UPDATED_DT=?, UPDATED_BY=?, DOC_NAME=?, DOC_DESC=?, DOC_PRE_TAG=?, DOC_MID_TAG_ID=?, DOC_SEQ_NO_LEN=?, DOC_STATUS_ID=? where IS_DELETED=0 and DOC_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, doc.getUpdated_by());
			pstmt.setString(3, doc.getDoc_name());
			pstmt.setString(4, doc.getDoc_desc());
			pstmt.setString(5, doc.getDoc_pre_tag());
			pstmt.setString(6, doc.getDoc_mid_tag_id());
			pstmt.setInt(7, doc.getDoc_seq_no_len());
			pstmt.setString(8, doc.getDoc_status_id());
			pstmt.setString(9, doc.getDoc_main_guid());
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

	private void updateDoc_sqlserver(DOC_MAIN doc, Connection conn) throws Exception {
		doc.setDoc_name(Decode(doc.getDoc_name()));
		doc.setDoc_desc(Decode(doc.getDoc_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update DOC_MAIN set UPDATED_DT=?, UPDATED_BY=?, DOC_NAME=?, DOC_DESC=?, DOC_PRE_TAG=?, DOC_MID_TAG_ID=?, DOC_SEQ_NO_LEN=?, DOC_STATUS_ID=? where IS_DELETED=0 and DOC_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, doc.getUpdated_by());
			pstmt.setString(3, doc.getDoc_name());
			pstmt.setString(4, doc.getDoc_desc());
			pstmt.setString(5, doc.getDoc_pre_tag());
			pstmt.setString(6, doc.getDoc_mid_tag_id());
			pstmt.setInt(7, doc.getDoc_seq_no_len());
			pstmt.setString(8, doc.getDoc_status_id());
			pstmt.setString(9, doc.getDoc_main_guid());
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

	public List<DOC_MAIN> getSlDocs(Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		List<DOC_MAIN> returnList = new ArrayList<DOC_MAIN>();
		switch (dataSourceType) {
	        case "oracle":  returnList = getSlDocs_oracle(conn);
	                 		break;
	        case "sqlserver": returnList = getSlDocs_sqlserver(conn);
							break;
	        default: returnList = getSlDocs_mysql(conn);
	        				break;
		}
		return returnList;
	}

	public void addPrivilege(PRIVILEGE_MAIN privilege, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  addPrivilege_oracle(privilege, conn);
	                 		break;
	        case "sqlserver": addPrivilege_sqlserver(privilege, conn);
							break;
	        default: addPrivilege_mysql(privilege, conn);
	        				break;
		}
	}

	public PRIVILEGE_DM getPrivileges(String privilege_id,
			String privilege_name, String doc_guid, int page_no, int page_size,
			Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		switch (dataSourceType) {
	        case "oracle":  returnDM = getPrivileges_oracle(privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
	                 		break;
	        case "sqlserver": returnDM = getPrivileges_sqlserver(privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
							break;
	        default: returnDM = getPrivileges_mysql(privilege_id,privilege_name,doc_guid,page_no,page_size,conn);
	        				break;
		}
		return returnDM;
	}

	public void delPrivilege(String[] privilegeguidArray, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  delPrivilege_oracle(privilegeguidArray, conn);
	                 		break;
	        case "sqlserver": delPrivilege_sqlserver(privilegeguidArray, conn);
							break;
	        default: delPrivilege_mysql(privilegeguidArray, conn);
	        				break;
		}
	}

	public PRIVILEGE_MAIN getPrivilege(String privilegeguid, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		PRIVILEGE_MAIN returnEntity = new PRIVILEGE_MAIN();
		switch (dataSourceType) {
	        case "oracle":  returnEntity = getPrivilege_oracle(privilegeguid, conn);
	                 		break;
	        case "sqlserver": returnEntity = getPrivilege_sqlserver(privilegeguid, conn);
							break;
	        default: returnEntity = getPrivilege_mysql(privilegeguid, conn);
	        				break;
		}
		return returnEntity;
	}

	public void updatePrivilege(PRIVILEGE_MAIN privilege, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  updatePrivilege_oracle(privilege, conn);
	                 		break;
	        case "sqlserver": updatePrivilege_sqlserver(privilege, conn);
							break;
	        default: updatePrivilege_mysql(privilege, conn);
	        				break;
		}
	}

	private void addDoc_mysql(DOC_MAIN doc, Connection conn) throws Exception {
		doc.setDoc_main_id(Decode(doc.getDoc_main_id()));
		doc.setDoc_name(Decode(doc.getDoc_name()));
		doc.setDoc_status_id(Decode(doc.getDoc_status_id()));
		doc.setDoc_pre_tag(Decode(doc.getDoc_pre_tag()));
		doc.setDoc_mid_tag_id(Decode(doc.getDoc_mid_tag_id()));
		doc.setDoc_desc(Decode(doc.getDoc_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String cSQL="select 1 from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_ID=? limit 1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, doc.getDoc_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("权限代码 "+doc.getDoc_main_id()+" 已存在");
			}
			String iSQL = "insert into DOC_MAIN (DOC_MAIN_GUID, DOC_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, DOC_NAME, DOC_STATUS_ID ,DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_DESC) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, doc.getDoc_main_id());
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, doc.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, doc.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, doc.getClient_guid());
			pstmt.setString(9,doc.getDoc_name());
			pstmt.setString(10, doc.getDoc_status_id());
			pstmt.setString(11, doc.getDoc_pre_tag());
			pstmt.setString(12, doc.getDoc_mid_tag_id());
			pstmt.setInt(13, 0);
			pstmt.setInt(14, doc.getDoc_seq_no_len());
			pstmt.setString(15, doc.getDoc_desc());
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

	private DOC_DM getDoc_mysql(String doc_id, String doc_name, int page_no,
			int page_size, Connection conn) throws Exception {
		doc_id = Decode(doc_id);
		doc_name = Decode(doc_name);
		
		DOC_DM returnDM = new DOC_DM();
		List<DOC_MAIN> returnList = new ArrayList<DOC_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC from DOC_MAIN";
			String sSQLWhere = " where IS_DELETED=0";
			if(doc_id!=null&&doc_id.length()>0){
				sSQLWhere +=" and DOC_MAIN_ID like ?";
			}
			if(doc_name!=null&&doc_name.length()>0){
				sSQLWhere +=" and DOC_NAME like ?";
			}
			pstmt = conn.prepareStatement(sSQL+sSQLWhere+" order by CREATED_DT desc limit "+ (page_no-1)*page_size + "," + page_size);
			int index = 0;
			if(doc_id!=null&&doc_id.length()>0){
				pstmt.setString(++index, doc_id+"%");
			}
			if(doc_name!=null&&doc_name.length()>0){
				pstmt.setString(++index, "%"+doc_name+"%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				DOC_MAIN entity = new DOC_MAIN();
				entity.setDoc_main_guid(rs.getString(1));
				entity.setDoc_main_id(rs.getString(2));
				entity.setDoc_name(Encode(rs.getString(3)));
				entity.setDoc_desc(Encode(rs.getString(4)));
				
				returnList.add(entity);
			}
			returnDM.setDocListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("select count(*) from DOC_MAIN"+sSQLWhere);
			index = 0;
			if(doc_id!=null&&doc_id.length()>0){
				pstmt.setString(++index, doc_id+"%");
			}
			if(doc_name!=null&&doc_name.length()>0){
				pstmt.setString(++index, "%"+doc_name+"%");
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

	private void delDoc_mysql(String[] docguidArray, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String docguid : docguidArray){
				pstmt.setString(1, docguid);
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

	private DOC_MAIN getDoc_mysql(String docguid, Connection conn) throws Exception {
		DOC_MAIN returnEntity = new DOC_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC, DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_STATUS_ID from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_GUID=? limit 1";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, docguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setDoc_main_guid(rs.getString(1));
				returnEntity.setDoc_main_id(Encode(rs.getString(2)));
				returnEntity.setDoc_name(Encode(rs.getString(3)));
				returnEntity.setDoc_desc(Encode(rs.getString(4)));
				returnEntity.setDoc_pre_tag(Encode(rs.getString(5)));
				returnEntity.setDoc_mid_tag_id(Encode(rs.getString(6)));
				returnEntity.setDoc_seq_no(rs.getInt(7));
				returnEntity.setDoc_seq_no_len(rs.getInt(8));
				returnEntity.setDoc_status_id(Encode(rs.getString(9)));
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

	private List<DOC_MAIN> getSlDocs_mysql(Connection conn) throws Exception {
		List<DOC_MAIN> returnList = new ArrayList<DOC_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC from DOC_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				DOC_MAIN entity = new DOC_MAIN();
				entity.setDoc_main_guid(rs.getString(1));
				entity.setDoc_main_id(rs.getString(2));
				entity.setDoc_name(Encode(rs.getString(3)));
				entity.setDoc_desc(Encode(rs.getString(4)));
				
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

	private void addPrivilege_mysql(PRIVILEGE_MAIN privilege, Connection conn) throws Exception {
		privilege.setPrivilege_main_id(Decode(privilege.getPrivilege_main_id()));
		privilege.setPrivilege_name(Decode(privilege.getPrivilege_name()));
		privilege.setPrivilege_desc(Decode(privilege.getPrivilege_desc()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String cSQL = "select 1 from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_ID=? limit 1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, privilege.getPrivilege_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("权限代码 "+privilege.getPrivilege_main_id()+" 已存在");
			}
			
			String iSQL = "insert into PRIVILEGE_MAIN (PRIVILEGE_MAIN_GUID, PRIVILEGE_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, PRIVILEGE_NAME, PRIVILEGE_DESC, DOC_GUID) values (?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, privilege.getPrivilege_main_id());
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, privilege.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, privilege.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, privilege.getClient_guid());
			pstmt.setString(9, privilege.getPrivilege_name());
			pstmt.setString(10, privilege.getPrivilege_desc());
			pstmt.setString(11, privilege.getDoc_guid());
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

	private PRIVILEGE_DM getPrivileges_mysql(String privilege_id,
			String privilege_name, String doc_guid, int page_no, int page_size,
			Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select t.PRIVILEGE_MAIN_GUID, t.PRIVILEGE_MAIN_ID, t.PRIVILEGE_NAME, t.PRIVILEGE_DESC, t1.DOC_MAIN_ID, t1.DOC_NAME from PRIVILEGE_MAIN as t inner join DOC_MAIN as t1 on t1.IS_DELETED=0 and t.DOC_GUID = t1.DOC_MAIN_GUID";
			String sSQLWhere = " where t.IS_DELETED=0";
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
			
			sSQLWhere = " where t.IS_DELETED=0";
			if(doc_guid!=null&&doc_guid.length()!=0){
				sSQLWhere +=" and t.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				sSQLWhere +=" and t.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				sSQLWhere +=" and t.PRIVILEGE_NAME like ?";
			}
			pstmt = conn.prepareStatement("select count(*) from PRIVILEGE_MAIN t inner join DOC_MAIN t1 on t1.IS_DELETED=0 and t.DOC_GUID = t1.DOC_MAIN_GUID"+sSQLWhere);
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

	private void delPrivilege_mysql(String[] privilegeguidArray, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String privilegeguid : privilegeguidArray){
				pstmt.setString(1, privilegeguid);
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

	private PRIVILEGE_MAIN getPrivilege_mysql(String privilegeguid,
			Connection conn) throws Exception {
		PRIVILEGE_MAIN returnEntity = new PRIVILEGE_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select PRIVILEGE_MAIN_GUID, PRIVILEGE_MAIN_ID, PRIVILEGE_NAME, PRIVILEGE_DESC, DOC_GUID from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=? limit 1";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, privilegeguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setPrivilege_main_guid(rs.getString(1));
				returnEntity.setPrivilege_main_id(rs.getString(2));
				returnEntity.setPrivilege_name(rs.getString(3));
				returnEntity.setPrivilege_desc(rs.getString(4));
				returnEntity.setDoc_guid(rs.getString(5));
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

	private void updatePrivilege_mysql(PRIVILEGE_MAIN privilege, Connection conn) throws Exception {
		privilege.setPrivilege_name(Decode(privilege.getPrivilege_name()));
		privilege.setPrivilege_desc(Decode(privilege.getPrivilege_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update PRIVILEGE_MAIN set UPDATED_DT=?, UPDATED_BY=?, PRIVILEGE_NAME=?, PRIVILEGE_DESC=? where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, privilege.getUpdated_by());
			pstmt.setString(3, privilege.getPrivilege_name());
			pstmt.setString(4, privilege.getPrivilege_desc());
			pstmt.setString(5, privilege.getPrivilege_main_guid());
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

	private void addDoc_oracle(DOC_MAIN doc, Connection conn) throws Exception {
		doc.setDoc_main_id(Decode(doc.getDoc_main_id()));
		doc.setDoc_name(Decode(doc.getDoc_name()));
		doc.setDoc_status_id(Decode(doc.getDoc_status_id()));
		doc.setDoc_pre_tag(Decode(doc.getDoc_pre_tag()));
		doc.setDoc_mid_tag_id(Decode(doc.getDoc_mid_tag_id()));
		doc.setDoc_desc(Decode(doc.getDoc_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String cSQL="select 1 from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_ID=? and ROWNUM=1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, doc.getDoc_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("权限代码 "+doc.getDoc_main_id()+" 已存在");
			}
			String iSQL = "insert into DOC_MAIN (DOC_MAIN_GUID, DOC_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, DOC_NAME, DOC_STATUS_ID ,DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_DESC) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, doc.getDoc_main_id());
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, doc.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, doc.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, doc.getClient_guid());
			pstmt.setString(9,doc.getDoc_name());
			pstmt.setString(10, doc.getDoc_status_id());
			pstmt.setString(11, doc.getDoc_pre_tag());
			pstmt.setString(12, doc.getDoc_mid_tag_id());
			pstmt.setInt(13, 0);
			pstmt.setInt(14, doc.getDoc_seq_no_len());
			pstmt.setString(15, doc.getDoc_desc());
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

	private DOC_DM getDoc_oracle(String doc_id, String doc_name, int page_no,
			int page_size, Connection conn) throws Exception {
		doc_id = Decode(doc_id);
		doc_name = Decode(doc_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;;
		
		DOC_DM returnDM = new DOC_DM();
		List<DOC_MAIN> returnList = new ArrayList<DOC_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC from DOC_MAIN";
			String subSQLWhere = " where IS_DELETED=0";
			if(doc_id!=null&&doc_id.length()>0){
				subSQLWhere +=" and DOC_MAIN_ID like ?";
			}
			if(doc_name!=null&&doc_name.length()>0){
				subSQLWhere +=" and DOC_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere+" order by CREATED_DT desc";
			String sSQL = "select B.* from (select A.*, ROWNUM as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if(doc_id!=null&&doc_id.length()>0){
				pstmt.setString(++index, doc_id+"%");
			}
			if(doc_name!=null&&doc_name.length()>0){
				pstmt.setString(++index, "%"+doc_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				DOC_MAIN entity = new DOC_MAIN();
				entity.setDoc_main_guid(rs.getString(1));
				entity.setDoc_main_id(rs.getString(2));
				entity.setDoc_name(Encode(rs.getString(3)));
				entity.setDoc_desc(Encode(rs.getString(4)));
				
				returnList.add(entity);
			}
			returnDM.setDocListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("select count(*) from DOC_MAIN"+subSQLWhere);
			index = 0;
			if(doc_id!=null&&doc_id.length()>0){
				pstmt.setString(++index, doc_id+"%");
			}
			if(doc_name!=null&&doc_name.length()>0){
				pstmt.setString(++index, "%"+doc_name+"%");
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

	private void delDoc_oracle(String[] docguidArray, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String docguid : docguidArray){
				pstmt.setString(1, docguid);
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

	private DOC_MAIN getDoc_oracle(String docguid, Connection conn) throws Exception {
		DOC_MAIN returnEntity = new DOC_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC, DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_STATUS_ID from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_GUID=? and ROWNUM=1";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, docguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setDoc_main_guid(rs.getString(1));
				returnEntity.setDoc_main_id(Encode(rs.getString(2)));
				returnEntity.setDoc_name(Encode(rs.getString(3)));
				returnEntity.setDoc_desc(Encode(rs.getString(4)));
				returnEntity.setDoc_pre_tag(Encode(rs.getString(5)));
				returnEntity.setDoc_mid_tag_id(Encode(rs.getString(6)));
				returnEntity.setDoc_seq_no(rs.getInt(7));
				returnEntity.setDoc_seq_no_len(rs.getInt(8));
				returnEntity.setDoc_status_id(Encode(rs.getString(9)));
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

	private List<DOC_MAIN> getSlDocs_oracle(Connection conn) throws Exception {
		List<DOC_MAIN> returnList = new ArrayList<DOC_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC from DOC_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				DOC_MAIN entity = new DOC_MAIN();
				entity.setDoc_main_guid(rs.getString(1));
				entity.setDoc_main_id(rs.getString(2));
				entity.setDoc_name(Encode(rs.getString(3)));
				entity.setDoc_desc(Encode(rs.getString(4)));
				
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

	private void addPrivilege_oracle(PRIVILEGE_MAIN privilege, Connection conn) throws Exception {
		privilege.setPrivilege_main_id(Decode(privilege.getPrivilege_main_id()));
		privilege.setPrivilege_name(Decode(privilege.getPrivilege_name()));
		privilege.setPrivilege_desc(Decode(privilege.getPrivilege_desc()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String cSQL = "select 1 from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_ID=? and ROWNUM=1";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, privilege.getPrivilege_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("权限代码 "+privilege.getPrivilege_main_id()+" 已存在");
			}
			
			String iSQL = "insert into PRIVILEGE_MAIN (PRIVILEGE_MAIN_GUID, PRIVILEGE_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, PRIVILEGE_NAME, PRIVILEGE_DESC, DOC_GUID) values (?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, privilege.getPrivilege_main_id());
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, privilege.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, privilege.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, privilege.getClient_guid());
			pstmt.setString(9, privilege.getPrivilege_name());
			pstmt.setString(10, privilege.getPrivilege_desc());
			pstmt.setString(11, privilege.getDoc_guid());
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

	private PRIVILEGE_DM getPrivileges_oracle(String privilege_id,
			String privilege_name, String doc_guid, int page_no, int page_size,
			Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;;
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select t.PRIVILEGE_MAIN_GUID, t.PRIVILEGE_MAIN_ID, t.PRIVILEGE_NAME, t.PRIVILEGE_DESC, t1.DOC_MAIN_ID, t1.DOC_NAME from PRIVILEGE_MAIN t inner join DOC_MAIN t1 on t1.IS_DELETED=0 and t.DOC_GUID = t1.DOC_MAIN_GUID";
			String subSQLWhere = " where t.IS_DELETED=0";
			if(doc_guid!=null&&doc_guid.length()!=0){
				subSQLWhere +=" and t.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				subSQLWhere +=" and t.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				subSQLWhere +=" and t.PRIVILEGE_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere+" order by t.CREATED_DT desc";
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
			
			subSQLWhere = " where t.IS_DELETED=0";
			if(doc_guid!=null&&doc_guid.length()!=0){
				subSQLWhere +=" and t.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				subSQLWhere +=" and t.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				subSQLWhere +=" and t.PRIVILEGE_NAME like ?";
			}
			pstmt = conn.prepareStatement("select count(*) from PRIVILEGE_MAIN t inner join DOC_MAIN t1 on t1.IS_DELETED=0 and t.DOC_GUID = t1.DOC_MAIN_GUID"+subSQLWhere);
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

	private void delPrivilege_oracle(String[] privilegeguidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String privilegeguid : privilegeguidArray){
				pstmt.setString(1, privilegeguid);
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

	private PRIVILEGE_MAIN getPrivilege_oracle(String privilegeguid,
			Connection conn) throws Exception {
		PRIVILEGE_MAIN returnEntity = new PRIVILEGE_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select PRIVILEGE_MAIN_GUID, PRIVILEGE_MAIN_ID, PRIVILEGE_NAME, PRIVILEGE_DESC, DOC_GUID from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=? and ROWNUM=1";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, privilegeguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setPrivilege_main_guid(rs.getString(1));
				returnEntity.setPrivilege_main_id(rs.getString(2));
				returnEntity.setPrivilege_name(rs.getString(3));
				returnEntity.setPrivilege_desc(rs.getString(4));
				returnEntity.setDoc_guid(rs.getString(5));
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

	private void updatePrivilege_oracle(PRIVILEGE_MAIN privilege,
			Connection conn) throws Exception {
		privilege.setPrivilege_name(Decode(privilege.getPrivilege_name()));
		privilege.setPrivilege_desc(Decode(privilege.getPrivilege_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update PRIVILEGE_MAIN set UPDATED_DT=?, UPDATED_BY=?, PRIVILEGE_NAME=?, PRIVILEGE_DESC=? where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, privilege.getUpdated_by());
			pstmt.setString(3, privilege.getPrivilege_name());
			pstmt.setString(4, privilege.getPrivilege_desc());
			pstmt.setString(5, privilege.getPrivilege_main_guid());
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

	private void addDoc_sqlserver(DOC_MAIN doc, Connection conn) throws Exception {
		doc.setDoc_main_id(Decode(doc.getDoc_main_id()));
		doc.setDoc_name(Decode(doc.getDoc_name()));
		doc.setDoc_status_id(Decode(doc.getDoc_status_id()));
		doc.setDoc_pre_tag(Decode(doc.getDoc_pre_tag()));
		doc.setDoc_mid_tag_id(Decode(doc.getDoc_mid_tag_id()));
		doc.setDoc_desc(Decode(doc.getDoc_desc()));
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			String cSQL="select top 1 1 from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_ID=?";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, doc.getDoc_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("权限代码 "+doc.getDoc_main_id()+" 已存在");
			}
			String iSQL = "insert into DOC_MAIN (DOC_MAIN_GUID, DOC_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, DOC_NAME, DOC_STATUS_ID ,DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_DESC) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, doc.getDoc_main_id());
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, doc.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, doc.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, doc.getClient_guid());
			pstmt.setString(9,doc.getDoc_name());
			pstmt.setString(10, doc.getDoc_status_id());
			pstmt.setString(11, doc.getDoc_pre_tag());
			pstmt.setString(12, doc.getDoc_mid_tag_id());
			pstmt.setInt(13, 0);
			pstmt.setInt(14, doc.getDoc_seq_no_len());
			pstmt.setString(15, doc.getDoc_desc());
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

	private DOC_DM getDoc_sqlserver(String doc_id, String doc_name,
			int page_no, int page_size, Connection conn) throws Exception {
		doc_id = Decode(doc_id);
		doc_name = Decode(doc_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;;
		
		DOC_DM returnDM = new DOC_DM();
		List<DOC_MAIN> returnList = new ArrayList<DOC_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC, CREATED_DT from DOC_MAIN";
			String subSQLWhere = " where IS_DELETED=0";
			String subOrderby="order by CREATED_DT desc";
			if(doc_id!=null&&doc_id.length()>0){
				subSQLWhere +=" and DOC_MAIN_ID like ?";
			}
			if(doc_name!=null&&doc_name.length()>0){
				subSQLWhere +=" and DOC_NAME like ?";
			}
			subSQL = subSQL+subSQLWhere;
			String sSQL = "select B.* from (select A.*, ROW_NUMBER() over("+subOrderby+") as RN from ("+ subSQL +") A) B where B.RN>=? and B.RN <=?";
			pstmt = conn.prepareStatement(sSQL);
			
			int index = 0;
			if(doc_id!=null&&doc_id.length()>0){
				pstmt.setString(++index, doc_id+"%");
			}
			if(doc_name!=null&&doc_name.length()>0){
				pstmt.setString(++index, "%"+doc_name+"%");
			}
			pstmt.setInt(++index, iRowStart);
			pstmt.setInt(++index, iRowEnd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				DOC_MAIN entity = new DOC_MAIN();
				entity.setDoc_main_guid(rs.getString(1));
				entity.setDoc_main_id(rs.getString(2));
				entity.setDoc_name(Encode(rs.getString(3)));
				entity.setDoc_desc(Encode(rs.getString(4)));
				
				returnList.add(entity);
			}
			returnDM.setDocListData(returnList);
			pstmt.close();
			
			pstmt = conn.prepareStatement("select count(DOC_MAIN_GUID) from DOC_MAIN"+subSQLWhere);
			index = 0;
			if(doc_id!=null&&doc_id.length()>0){
				pstmt.setString(++index, doc_id+"%");
			}
			if(doc_name!=null&&doc_name.length()>0){
				pstmt.setString(++index, "%"+doc_name+"%");
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

	private void delDoc_sqlserver(String[] docguidArray, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String docguid : docguidArray){
				pstmt.setString(1, docguid);
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

	private DOC_MAIN getDoc_sqlserver(String docguid, Connection conn) throws Exception {
		DOC_MAIN returnEntity = new DOC_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select top 1 DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC, DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_STATUS_ID from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, docguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setDoc_main_guid(rs.getString(1));
				returnEntity.setDoc_main_id(Encode(rs.getString(2)));
				returnEntity.setDoc_name(Encode(rs.getString(3)));
				returnEntity.setDoc_desc(Encode(rs.getString(4)));
				returnEntity.setDoc_pre_tag(Encode(rs.getString(5)));
				returnEntity.setDoc_mid_tag_id(Encode(rs.getString(6)));
				returnEntity.setDoc_seq_no(rs.getInt(7));
				returnEntity.setDoc_seq_no_len(rs.getInt(8));
				returnEntity.setDoc_status_id(Encode(rs.getString(9)));
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

	private List<DOC_MAIN> getSlDocs_sqlserver(Connection conn) throws Exception {
		List<DOC_MAIN> returnList = new ArrayList<DOC_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select DOC_MAIN_GUID, DOC_MAIN_ID, DOC_NAME, DOC_DESC from DOC_MAIN where IS_DELETED=0";
			pstmt = conn.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				DOC_MAIN entity = new DOC_MAIN();
				entity.setDoc_main_guid(rs.getString(1));
				entity.setDoc_main_id(rs.getString(2));
				entity.setDoc_name(Encode(rs.getString(3)));
				entity.setDoc_desc(Encode(rs.getString(4)));
				
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

	private void addPrivilege_sqlserver(PRIVILEGE_MAIN privilege,
			Connection conn) throws Exception {
		privilege.setPrivilege_main_id(Decode(privilege.getPrivilege_main_id()));
		privilege.setPrivilege_name(Decode(privilege.getPrivilege_name()));
		privilege.setPrivilege_desc(Decode(privilege.getPrivilege_desc()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String cSQL = "select top 1 1 from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_ID=?";
			pstmt = conn.prepareStatement(cSQL);
			pstmt.setString(1, privilege.getPrivilege_main_id());
			rs = pstmt.executeQuery();
			boolean exist = false;
			if(rs.next()){
				exist = true;
			}
			pstmt.close();
			if(exist){
				throw new Exception("权限代码 "+privilege.getPrivilege_main_id()+" 已存在");
			}
			
			String iSQL = "insert into PRIVILEGE_MAIN (PRIVILEGE_MAIN_GUID, PRIVILEGE_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, PRIVILEGE_NAME, PRIVILEGE_DESC, DOC_GUID) values (?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(iSQL);
			String guidNew = UUID.randomUUID().toString();
			pstmt.setString(1, guidNew);
			pstmt.setString(2, privilege.getPrivilege_main_id());
			long lDate = new Date().getTime();
			pstmt.setLong(3, lDate);
			pstmt.setString(4, privilege.getCreated_by());
			pstmt.setLong(5, lDate);
			pstmt.setString(6, privilege.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, privilege.getClient_guid());
			pstmt.setString(9, privilege.getPrivilege_name());
			pstmt.setString(10, privilege.getPrivilege_desc());
			pstmt.setString(11, privilege.getDoc_guid());
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

	private PRIVILEGE_DM getPrivileges_sqlserver(String privilege_id,
			String privilege_name, String doc_guid, int page_no, int page_size,
			Connection conn) throws Exception {
		privilege_id = Decode(privilege_id);
		privilege_name = Decode(privilege_name);
		
		if(page_no<=0)page_no=1;
		if(page_size<=0)page_size=10;
		int iRowStart = (page_no-1)*page_size+1;
		int iRowEnd = iRowStart + page_size - 1;;
		
		PRIVILEGE_DM returnDM = new PRIVILEGE_DM();
		List<PRIVILEGE_MAIN> returnList = new ArrayList<PRIVILEGE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String subSQL = "select t.PRIVILEGE_MAIN_GUID, t.PRIVILEGE_MAIN_ID, t.PRIVILEGE_NAME, t.PRIVILEGE_DESC, t1.DOC_MAIN_ID, t1.DOC_NAME, t.CREATED_DT from PRIVILEGE_MAIN t inner join DOC_MAIN t1 on t1.IS_DELETED=0 and t.DOC_GUID = t1.DOC_MAIN_GUID";
			String subSQLWhere = " where t.IS_DELETED=0";
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
			subSQL = subSQL+subSQLWhere;
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
			
			subSQLWhere = " where t.IS_DELETED=0";
			if(doc_guid!=null&&doc_guid.length()!=0){
				subSQLWhere +=" and t.DOC_GUID=?";
			}
			if(privilege_id!=null&&privilege_id.length()!=0){
				subSQLWhere +=" and t.PRIVILEGE_MAIN_ID like ?";
			}
			if(privilege_name!=null&&privilege_name.length()>0){
				subSQLWhere +=" and t.PRIVILEGE_NAME like ?";
			}
			pstmt = conn.prepareStatement("select count(*) from PRIVILEGE_MAIN t inner join DOC_MAIN t1 on t1.IS_DELETED=0 and t.DOC_GUID = t1.DOC_MAIN_GUID"+subSQLWhere);
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

	private void delPrivilege_sqlserver(String[] privilegeguidArray,
			Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			String dSQL = "delete from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(dSQL);
			for(String privilegeguid : privilegeguidArray){
				pstmt.setString(1, privilegeguid);
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

	private PRIVILEGE_MAIN getPrivilege_sqlserver(String privilegeguid,
			Connection conn) throws Exception {
		PRIVILEGE_MAIN returnEntity = new PRIVILEGE_MAIN();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sSQL = "select top 1 PRIVILEGE_MAIN_GUID, PRIVILEGE_MAIN_ID, PRIVILEGE_NAME, PRIVILEGE_DESC, DOC_GUID from PRIVILEGE_MAIN where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(sSQL);
			pstmt.setString(1, privilegeguid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				returnEntity.setPrivilege_main_guid(rs.getString(1));
				returnEntity.setPrivilege_main_id(rs.getString(2));
				returnEntity.setPrivilege_name(rs.getString(3));
				returnEntity.setPrivilege_desc(rs.getString(4));
				returnEntity.setDoc_guid(rs.getString(5));
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

	private void updatePrivilege_sqlserver(PRIVILEGE_MAIN privilege,
			Connection conn) throws Exception {
		privilege.setPrivilege_name(Decode(privilege.getPrivilege_name()));
		privilege.setPrivilege_desc(Decode(privilege.getPrivilege_desc()));
		PreparedStatement pstmt = null;
		try{
			String uSQL = "update PRIVILEGE_MAIN set UPDATED_DT=?, UPDATED_BY=?, PRIVILEGE_NAME=?, PRIVILEGE_DESC=? where IS_DELETED=0 and PRIVILEGE_MAIN_GUID=?";
			pstmt = conn.prepareStatement(uSQL);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, privilege.getUpdated_by());
			pstmt.setString(3, privilege.getPrivilege_name());
			pstmt.setString(4, privilege.getPrivilege_desc());
			pstmt.setString(5, privilege.getPrivilege_main_guid());
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

	public List<PARAM_MAIN> getSlParam(String param_type, Connection conn) throws Exception {
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		List<PARAM_MAIN> returnList = new ArrayList<PARAM_MAIN>();
		switch (dataSourceType) {
	        case "oracle":  returnList = getSlParam_oracle(param_type, conn);
	                 		break;
	        case "sqlserver": returnList = getSlParam_sqlserver(param_type, conn);
							break;
	        default: returnList = getSlParam_mysql(param_type, conn);
	        				break;
		}
		return returnList;
	}

	private List<PARAM_MAIN> getSlParam_oracle(String param_type, Connection conn) throws Exception {
		List<PARAM_MAIN> returnList = new ArrayList<PARAM_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = conn.prepareStatement("select PARAM_MAIN_GUID, PARAM_MAIN_ID, PARAM_VALUE from PARAM_MAIN where IS_DELETED=0 and PARAM_TYPE=?");
		pstmt.setString(1, param_type);
		rs = pstmt.executeQuery();
		while(rs.next()){
			PARAM_MAIN entity =  new PARAM_MAIN();
			entity.setParam_main_guid(rs.getString(1));
			entity.setParam_main_id(Encode(rs.getString(2)));
			entity.setParam_value(Encode(rs.getString(3)));
			
			returnList.add(entity);
		}
		return returnList;
	}

	private List<PARAM_MAIN> getSlParam_mysql(String param_type, Connection conn) throws Exception {
		List<PARAM_MAIN> returnList = new ArrayList<PARAM_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = conn.prepareStatement("select PARAM_MAIN_GUID, PARAM_MAIN_ID, PARAM_VALUE from PARAM_MAIN where IS_DELETED=0 and PARAM_TYPE=?");
		pstmt.setString(1, param_type);
		rs = pstmt.executeQuery();
		while(rs.next()){
			PARAM_MAIN entity =  new PARAM_MAIN();
			entity.setParam_main_guid(rs.getString(1));
			entity.setParam_main_id(Encode(rs.getString(2)));
			entity.setParam_value(Encode(rs.getString(3)));
			
			returnList.add(entity);
		}
		return returnList;
	}

	private List<PARAM_MAIN> getSlParam_sqlserver(String param_type, Connection conn) throws Exception {
		List<PARAM_MAIN> returnList = new ArrayList<PARAM_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = conn.prepareStatement("select PARAM_MAIN_GUID, PARAM_MAIN_ID, PARAM_VALUE from PARAM_MAIN where IS_DELETED=0 and PARAM_TYPE=?");
		pstmt.setString(1, param_type);
		rs = pstmt.executeQuery();
		while(rs.next()){
			PARAM_MAIN entity =  new PARAM_MAIN();
			entity.setParam_main_guid(rs.getString(1));
			entity.setParam_main_id(Encode(rs.getString(2)));
			entity.setParam_value(Encode(rs.getString(3)));
			
			returnList.add(entity);
		}
		return returnList;
	}

	public DOC_MAIN getDocSeqnoID(String user_guid, String doc_id, Connection conn) throws Exception {
		DOC_MAIN returnEntity = new DOC_MAIN();
		String dataSourceType=new Config(FrameConfig.CONFIGNAME).get("datasource@type");
		switch (dataSourceType) {
	        case "oracle":  returnEntity = getDocSeqnoID_oracle(user_guid, doc_id, conn);
	                 		break;
	        case "sqlserver": returnEntity = getDocSeqnoID_sqlserver(user_guid, doc_id, conn);
							break;
	        default: returnEntity = getDocSeqnoID_mysql(user_guid, doc_id, conn);
	        				break;
		}
		return returnEntity;
	}

	private DOC_MAIN getDocSeqnoID_oracle(String user_guid, String doc_id,
			Connection conn) throws Exception {
		DOC_MAIN returnEntity = new DOC_MAIN();
		String docSeqnoID = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select DOC_MAIN_ID, DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_STATUS_ID from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_ID=? for update");
			pstmt.setString(1, doc_id);
			rs = pstmt.executeQuery();
			DOC_MAIN entity = new DOC_MAIN();
			int icount = 0;
			if(rs.next()){
				icount++;
				entity.setDoc_main_id(rs.getString(1));
				entity.setDoc_pre_tag(rs.getString(2));
				entity.setDoc_mid_tag_id(rs.getString(3));
				entity.setDoc_seq_no(rs.getInt(4));
				entity.setDoc_seq_no_len(rs.getInt(5));
				entity.setDoc_status_id(rs.getString(6));
			}
			pstmt.close();
			if(icount==0){
				throw new Exception("未知的凭证");
			}
			String midTag = this.getDoc_Mid_Str(entity.getDoc_mid_tag_id());
			String str_seqno=entity.getDoc_seq_no()+1+"";
			int seqno_len=entity.getDoc_seq_no_len();
			while(str_seqno.length()<seqno_len){
				str_seqno = "0"+str_seqno;
			}
			docSeqnoID=entity.getDoc_pre_tag() + midTag + str_seqno;
			
			pstmt = conn.prepareStatement("update DOC_MAIN set UPDATED_DT=?, UPDATED_BY=?, DOC_SEQ_NO = ? where IS_DELETED=0 and DOC_MAIN_ID=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, user_guid);
			pstmt.setString(3, str_seqno);
			pstmt.setString(4, doc_id);
			pstmt.execute();
			
			entity.setDoc_seqno_id(docSeqnoID);
			returnEntity = entity;
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnEntity;
	}

	private DOC_MAIN getDocSeqnoID_sqlserver(String user_guid, String doc_id,
			Connection conn) throws Exception {
		DOC_MAIN returnEntity = new DOC_MAIN();
		String docSeqnoID = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT DOC_MAIN_ID, DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_STATUS_ID FROM DOC_MAIN WITH(UPDLOCK) WHERE IS_DELETED=0 AND DOC_MAIN_ID=?");
			pstmt.setString(1, doc_id);
			rs = pstmt.executeQuery();
			DOC_MAIN entity = new DOC_MAIN();
			int icount = 0;
			if(rs.next()){
				icount++;
				entity.setDoc_main_id(rs.getString(1));
				entity.setDoc_pre_tag(rs.getString(2));
				entity.setDoc_mid_tag_id(rs.getString(3));
				entity.setDoc_seq_no(rs.getInt(4));
				entity.setDoc_seq_no_len(rs.getInt(5));
				entity.setDoc_status_id(rs.getString(6));
			}
			pstmt.close();
			if(icount==0){
				throw new Exception("未知的凭证");
			}
			String midTag = this.getDoc_Mid_Str(entity.getDoc_mid_tag_id());
			String str_seqno=entity.getDoc_seq_no()+1+"";
			int seqno_len=entity.getDoc_seq_no_len();
			while(str_seqno.length()<seqno_len){
				str_seqno = "0"+str_seqno;
			}
			docSeqnoID=entity.getDoc_pre_tag() + midTag + str_seqno;
			
			pstmt = conn.prepareStatement("UPDATE DOC_MAIN SET UPDATED_DT=?, UPDATED_BY=?, DOC_SEQ_NO = ? WHERE IS_DELETED=0 AND DOC_MAIN_ID=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, user_guid);
			pstmt.setString(3, str_seqno);
			pstmt.setString(4, doc_id);
			pstmt.execute();
			
			entity.setDoc_seqno_id(docSeqnoID);
			returnEntity = entity;
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnEntity;
	}

	private DOC_MAIN getDocSeqnoID_mysql(String user_guid, String doc_id,
			Connection conn) throws Exception {
		DOC_MAIN returnEntity = new DOC_MAIN();
		String docSeqnoID = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select DOC_MAIN_ID, DOC_PRE_TAG, DOC_MID_TAG_ID, DOC_SEQ_NO, DOC_SEQ_NO_LEN, DOC_STATUS_ID from DOC_MAIN where IS_DELETED=0 and DOC_MAIN_ID=? for update");
			pstmt.setString(1, doc_id);
			rs = pstmt.executeQuery();
			DOC_MAIN entity = new DOC_MAIN();
			int icount = 0;
			if(rs.next()){
				icount++;
				entity.setDoc_main_id(rs.getString(1));
				entity.setDoc_pre_tag(rs.getString(2));
				entity.setDoc_mid_tag_id(rs.getString(3));
				entity.setDoc_seq_no(rs.getInt(4));
				entity.setDoc_seq_no_len(rs.getInt(5));
				entity.setDoc_status_id(rs.getString(6));
			}
			pstmt.close();
			if(icount==0){
				throw new Exception("未知的凭证");
			}
			String midTag = this.getDoc_Mid_Str(entity.getDoc_mid_tag_id());
			String str_seqno=entity.getDoc_seq_no()+1+"";
			int seqno_len=entity.getDoc_seq_no_len();
			while(str_seqno.length()<seqno_len){
				str_seqno = "0"+str_seqno;
			}
			docSeqnoID=entity.getDoc_pre_tag() + midTag + str_seqno;
			
			pstmt = conn.prepareStatement("update DOC_MAIN set UPDATED_DT=?, UPDATED_BY=?, DOC_SEQ_NO = ? where IS_DELETED=0 and DOC_MAIN_ID=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, user_guid);
			pstmt.setString(3, str_seqno);
			pstmt.setString(4, doc_id);
			pstmt.execute();
			
			entity.setDoc_seqno_id(docSeqnoID);
			returnEntity = entity;
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed())pstmt.close();
		}
		return returnEntity;
	}
	
	private String getDoc_Mid_Str(String doc_mid_tag_id){
		String doc_mid_str="";
		if(doc_mid_tag_id!=null&&!doc_mid_tag_id.isEmpty()){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyMMdd");
			String dateText = dateFormat.format(new Date());
			String dateText1 = dateFormat1.format(new Date());
			switch (doc_mid_tag_id) {
	        	case "year":	doc_mid_str = dateText.substring(0, 4);break;
		        case "year_month":	doc_mid_str = dateText.substring(0, 6);break;
		        case "year_month_day":	doc_mid_str = dateText;break;
		        case "2_year":	doc_mid_str = dateText1.substring(0, 2);break;
		        case "2_year_month":	doc_mid_str = dateText1.substring(0, 4);break;
		        case "2_year_month_day":	doc_mid_str = dateText1;break;
		        default:	doc_mid_str = "";break;
			}
		}
		return doc_mid_str;
	}
}
