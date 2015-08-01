package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.entities.ADDR_MAIN;
import com.xinyou.frame.domain.entities.CITY_MAIN;
import com.xinyou.frame.domain.entities.COUNTRY_MAIN;
import com.xinyou.frame.domain.entities.EMP_MAIN;
import com.xinyou.frame.domain.entities.NATION_MAIN;
import com.xinyou.frame.domain.entities.STATE_MAIN;
import com.xinyou.frame.domain.models.EMP_DM;
import com.xinyou.frame.domain.models.EMP_MAIN_VIEW;
import com.xinyou.frame.domain.models.EntityListDM;
import com.xinyou.util.StringUtil;

public class EMP_BIZ extends StringUtil {

	public List<NATION_MAIN> getSlNations(Connection conn) throws Exception {
		List<NATION_MAIN> returnList = new ArrayList<NATION_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select NATION_MAIN_GUID, NATION_MAIN_ID, NATION_NAME from NATION_MAIN where IS_DELETED=0");
			rs = pstmt.executeQuery();
			while(rs.next()){
				NATION_MAIN entity = new NATION_MAIN();
				entity.setNation_main_guid(rs.getString(1));
				entity.setNation_main_id(rs.getString(2));
				entity.setNation_name(rs.getString(3));
				
				returnList.add(entity);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null)
				pstmt.close();
		}
		return returnList;
	}

	public List<STATE_MAIN> getSlStates(String nation_guid, Connection conn) throws Exception {
		List<STATE_MAIN> returnList = new ArrayList<STATE_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select STATE_MAIN_GUID, STATE_MAIN_ID, STATE_NAME from STATE_MAIN where IS_DELETED=0 and NATION_GUID=?");
			pstmt.setString(1, nation_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				STATE_MAIN entity = new STATE_MAIN();
				entity.setState_main_guid(rs.getString(1));
				entity.setState_main_id(rs.getString(2));
				entity.setState_name(rs.getString(3));
				
				returnList.add(entity);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null)
				pstmt.close();
		}
		return returnList;
	}

	public List<CITY_MAIN> getSlCitys(String state_guid, Connection conn) throws Exception {
		List<CITY_MAIN> returnList = new ArrayList<CITY_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select CITY_MAIN_GUID, CITY_MAIN_ID, CITY_NAME from CITY_MAIN where IS_DELETED=0 and STATE_GUID=?");
			pstmt.setString(1, state_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				CITY_MAIN entity = new CITY_MAIN();
				entity.setCity_main_guid(rs.getString(1));
				entity.setCity_main_id(rs.getString(2));
				entity.setCity_name(rs.getString(3));
				
				returnList.add(entity);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null)
				pstmt.close();
		}
		return returnList;
	}

	public List<COUNTRY_MAIN> getSlCountrys(String city_guid, Connection conn) throws Exception {
		List<COUNTRY_MAIN> returnList = new ArrayList<COUNTRY_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select COUNTRY_MAIN_GUID, COUNTRY_MAIN_ID, COUNTRY_NAME from COUNTRY_MAIN where IS_DELETED=0 and CITY_GUID=?");
			pstmt.setString(1, city_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				COUNTRY_MAIN entity = new COUNTRY_MAIN();
				entity.setCountry_main_guid(rs.getString(1));
				entity.setCountry_main_id(rs.getString(2));
				entity.setCountry_name(rs.getString(3));
				
				returnList.add(entity);
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null)
				pstmt.close();
		}
		return returnList;
	}

	public static void addEmp(EMP_MAIN emp_main, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt=conn.prepareStatement("SELECT EMP_MAIN_ID FROM EMP_MAIN WHERE EMP_MAIN_ID=?");
			pstmt.setString(1, emp_main.getEmp_main_id());
			rs=pstmt.executeQuery();
			boolean exist = false;
			if (rs.next()) {
				exist=true;
			}
			rs.close();
			pstmt.close();
			if (exist) {
				throw new Exception("员工编号 " + emp_main.getEmp_main_id()+ " 已存在");
			}
			
			pstmt=conn.prepareStatement("SELECT EMP_BACO FROM EMP_MAIN WHERE EMP_BACO=?");
			pstmt.setString(1, emp_main.getEmp_baco());
			rs=pstmt.executeQuery();
			exist = false;
			if (rs.next()) {
				exist=true;
			}
			rs.close();
			pstmt.close();
			if (exist) {
				throw new Exception("员工条码 " + emp_main.getEmp_baco()+ " 已存在");
			}
			
			pstmt=conn.prepareStatement("INSERT INTO EMP_MAIN (EMP_MAIN_GUID, EMP_MAIN_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, IS_DELETED, CLIENT_GUID, DATA_VER, EMP_NAME, EMP_TYPE, EMP_LP, EMP_SP, EMP_STATUS, EMP_MEMO, EMP_SYS_ID,EMP_BACO)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			String empguid = UUID.randomUUID().toString();
			pstmt.setString(1, empguid);
			pstmt.setString(2, emp_main.getEmp_main_id());
			long ldate = new Date().getTime();
			pstmt.setLong(3, ldate);
			pstmt.setString(4, emp_main.getCreated_by());
			pstmt.setLong(5, ldate);
			pstmt.setString(6, emp_main.getUpdated_by());
			pstmt.setInt(7, 0);
			pstmt.setString(8, emp_main.getClient_guid());
			pstmt.setString(9, emp_main.getData_ver());
			pstmt.setString(10, emp_main.getEmp_name());
			if(emp_main.getEmp_type()>4){
				emp_main.setEmp_type(3);
			}
			pstmt.setInt(11, emp_main.getEmp_type());
			pstmt.setString(12, emp_main.getEmp_lp());
			pstmt.setString(13, emp_main.getEmp_sp());
			if(emp_main.getEmp_status()!=1){
				emp_main.setEmp_status(0);
			}
			pstmt.setInt(14, emp_main.getEmp_status());
			pstmt.setString(15, emp_main.getEmp_memo());
			pstmt.setString(16, emp_main.getEmp_sys_id());
			pstmt.setString(17, emp_main.getEmp_baco());
			
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}
	
	public static void delEmp(String emp_guid, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT TOP 1 USR_MAIN_GUID FROM USR_MAIN WHERE EMP_GUID=?");
			pstmt.setString(1, emp_guid);
			rs = pstmt.executeQuery();
			boolean exist = false;
			if (rs.next()) {
				exist = true;
			}
			rs.close();
			pstmt.close();
			if (exist) {
				throw new Exception("员工已绑定系统用户,不能删除");
			}
	
			pstmt = conn.prepareStatement("DELETE FROM EMP_MAIN WHERE EMP_MAIN_GUID=?");
			pstmt.setString(1, emp_guid);
			pstmt.execute();
			pstmt.close();
			
			conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null && !pstmt.isClosed())pstmt.close();
		}
	}
	
	public static void updateEmp(EMP_MAIN emp_main, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("UPDATE EMP_MAIN SET UPDATED_DT=?, UPDATED_BY=?, EMP_NAME=?, EMP_TYPE=?, EMP_STATUS=?, EMP_MEMO=?,EMP_BACO=? WHERE EMP_MAIN_ID=?");
		pstmt.setLong(1, new Date().getTime());
		pstmt.setString(2, emp_main.getUpdated_by());
		pstmt.setString(3, emp_main.getEmp_name());
		if(emp_main.getEmp_type()>4){
			emp_main.setEmp_type(3);
		}
		pstmt.setInt(4, emp_main.getEmp_type());
		if(emp_main.getEmp_status()!=1){
			emp_main.setEmp_status(0);
		}
		pstmt.setInt(5, emp_main.getEmp_status());
		pstmt.setString(6, emp_main.getEmp_memo());
		pstmt.setString(7, emp_main.getEmp_baco());
		pstmt.setString(8, emp_main.getEmp_main_id());
		pstmt.execute();
		pstmt.close();
	}

	public static EntityListDM getEmps(String emp_name,String emp_id, int page_no, int page_size,
			Connection conn) throws Exception {
		if (page_no <= 0)page_no = 1;
		if (page_size <= 0)page_size = 10;
		int iRowStart = (page_no - 1) * page_size + 1;
		int iRowEnd = iRowStart + page_size - 1;
	
		EntityListDM returnDM = new EntityListDM();
		List<Object> returnList = new ArrayList<Object>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String subSQL = "SELECT T.EMP_MAIN_GUID, T.EMP_MAIN_ID, T.EMP_NAME, T.EMP_TYPE, T.EMP_STATUS, T.EMP_MEMO, T.EMP_BACO, T.CREATED_DT FROM EMP_MAIN T";
		String subSQLWhere = " WHERE 1=1";
		String subOrderby = "ORDER BY EMP_MAIN_ID DESC";
		if (emp_name != null && !emp_name.isEmpty()) {
			subSQLWhere += " AND T.EMP_NAME LIKE ?";
		}
		if (emp_id != null && !emp_id.isEmpty()) {
			subSQLWhere += " AND T.EMP_MAIN_ID=?";
		}
		subSQL = subSQL + subSQLWhere;
		String sSQL = "SELECT B.* FROM (SELECT A.*, ROW_NUMBER() OVER("+ subOrderby + ") AS RN FROM (" + subSQL+ ") A) B WHERE B.RN>=? AND B.RN <=?";
		pstmt = conn.prepareStatement(sSQL);
		int index = 0;
		if (emp_name != null && !emp_name.isEmpty()) {
			pstmt.setString(++index, "%" + emp_name + "%");
		}
		if (emp_id != null && !emp_id.isEmpty()) {
			pstmt.setString(++index,  emp_id );
		}
		pstmt.setInt(++index, iRowStart);
		pstmt.setInt(++index, iRowEnd);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			EMP_MAIN_VIEW entity = new EMP_MAIN_VIEW();
			entity.setEmp_main_guid(rs.getString(1));
			entity.setEmp_main_id(rs.getString(2));
			entity.setEmp_name(rs.getString(3));
			entity.setEmp_type(rs.getInt(4));
			entity.setEmp_status(rs.getInt(5));
			entity.setEmp_memo(rs.getString(6));
			entity.setEmp_baco(rs.getString(7));
			
			returnList.add(entity);
		}
		rs.close();
		pstmt.close();
		returnDM.setDataList(returnList);

		subSQLWhere = " WHERE 1=1";
		if (emp_name != null && !emp_name.isEmpty()) {
			subSQLWhere += " AND EMP_NAME LIKE ?";
		}
		if (emp_id != null && !emp_id.isEmpty()) {
			subSQLWhere += " AND EMP_MAIN_ID=?";
		}
		pstmt = conn.prepareStatement("SELECT COUNT(*) FROM EMP_MAIN"+ subSQLWhere);
		index = 0;
		if (emp_name != null && !emp_name.isEmpty()) {
			pstmt.setString(++index, "%" + emp_name + "%");
		}
		if (emp_id != null && !emp_id.isEmpty()) {
			pstmt.setString(++index, emp_id);
		}
		rs = pstmt.executeQuery();
		if (rs.next()) {
			returnDM.setCount(rs.getInt(1));
		} else {
			returnDM.setCount(0);
		}
		rs.close();
		pstmt.close();

		return returnDM;
	}

	public EMP_DM getEmp(String emp_guid, Connection conn) throws Exception {
		EMP_DM returnDM = new EMP_DM();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select EMP_MAIN_GUID, EMP_MAIN_ID, EMP_FULLNAME, POST_GUID, ORG_GUID, FIRST_NAME, LAST_NAME, OFFICE_PHONE, CELL_PHONE, HOME_PHONE, EMP_EMAIL, EMP_PHOTO, EMP_DESC, EMP_GENDER_ID, EMP_BIRTH_DT, BIRTH_NATION_GUID, MARITAL_STATUS_ID, ID_NO, NATIONALITY_GUID, CHILD_NUM, PASSPORT_NO, PASSPORT_END_DT from EMP_MAIN where IS_DELETED=0 and EMP_MAIN_GUID=?");
			pstmt.setString(1, emp_guid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				EMP_MAIN entity = new EMP_MAIN();
				entity.setEmp_main_guid(rs.getString(1));
				entity.setEmp_main_id(rs.getString(2));
				
				returnDM.setEmpData(entity);
			}
			pstmt.close();
			
			pstmt = conn.prepareStatement("select t.ADDR_TYPE_ID, t1.ADDR_STREET, t1.ADDR_BLOCK, t1.ADDR_BUILDING, t1.ADDR_FLOOR, t1.ADDR_ROOM, t1.ADDR_ZIP_CODE, t1.ADDR_NATION_GUID, t1.ADDR_STATE_GUID, t1.ADDR_CITY_GUID, t1.ADDR_COUNTRY_GUID from EMP_ADDR t left join ADDR_MAIN t1 on t.ADDR_GUID = t1.ADDR_MAIN_GUID where t.EMP_GUID=?");
			pstmt.setString(1, emp_guid);
			rs = pstmt.executeQuery();
			List<ADDR_MAIN> returnList = new ArrayList<ADDR_MAIN>();
			while(rs.next()){
				ADDR_MAIN entity = new ADDR_MAIN();
				entity.setAddr_type_id(rs.getString(1));
				entity.setAddr_street(Encode(rs.getString(2)));
				entity.setAddr_block(Encode(rs.getString(3)));
				entity.setAddr_building(Encode(rs.getString(4)));
				entity.setAddr_floor(Encode(rs.getString(5)));
				entity.setAddr_room(Encode(rs.getString(6)));
				entity.setAddr_zip_code(rs.getString(7));
				entity.setAddr_nation_guid(rs.getString(8));
				entity.setAddr_state_guid(rs.getString(9));
				entity.setAddr_city_guid(rs.getString(10));
				entity.setAddr_country_guid(rs.getString(11));
				
				returnList.add(entity);
			}
			pstmt.close();
			returnDM.setAddrListData(returnList);
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return returnDM;
	}
	
	public static EMP_MAIN_VIEW getEmpById(String emp_id, Connection conn) throws Exception {
		EMP_MAIN_VIEW result = new EMP_MAIN_VIEW();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SELECT EMP_MAIN_GUID, EMP_MAIN_ID, EMP_NAME FROM EMP_MAIN WHERE  IS_DELETED=0 AND EMP_STATUS=1 AND EMP_MAIN_ID=?");
			pstmt.setString(1, emp_id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result.setEmp_main_guid(rs.getString(1));
				result.setEmp_main_id(rs.getString(2));
				result.setEmp_name(rs.getString(3));
			}
			pstmt.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(pstmt!=null&&!pstmt.isClosed()){
				pstmt.close();
			}
		}
		return result;
	}
	
}
