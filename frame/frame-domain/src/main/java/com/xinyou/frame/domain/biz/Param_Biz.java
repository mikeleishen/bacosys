package com.xinyou.frame.domain.biz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xinyou.frame.domain.entities.CODE_MAIN;
import com.xinyou.label.domain.entities.PARA_MAIN;
import com.mysql.jdbc.StringUtils;

public class Param_Biz {
	public static List<PARA_MAIN> getParas(String typeId,Connection conn) throws Exception
	{
		List<PARA_MAIN> returnList = new ArrayList<PARA_MAIN>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT PARA_MAIN_GUID,PARA_MAIN_ID,PARA_VALUE,PARA_TYPE_ID,PARA_MEMO FROM PARA_MAIN WHERE PARA_TYPE_ID=? ORDER BY PARA_MAIN_ID ");
			pstmt.setString(1, typeId);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PARA_MAIN entity = new PARA_MAIN();
				entity.setGuid(rs.getString(1));
				entity.setId(rs.getString(2));
				entity.setPara_value(rs.getString(3));
				entity.setPara_type_id(rs.getString(4));
				entity.setPara_memo(rs.getString(5));
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
	
	public static String addPara(PARA_MAIN para, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String guid = UUID.randomUUID().toString();
		try{
			pstmt=conn.prepareStatement("SELECT PARA_TYPE_ID,PARA_MAIN_ID FROM PARA_MAIN WHERE PARA_TYPE_ID=? AND PARA_MAIN_ID=?");
			pstmt.setString(1, para.getPara_type_id());
			pstmt.setString(2, para.getId());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				throw new Exception("在类型："+para.getPara_type_id()+" 已经存在代码为："+para.getId()+" 的记录。");
			}
			
			pstmt=conn.prepareStatement("INSERT INTO PARA_MAIN(PARA_MAIN_GUID,PARA_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,PARA_TYPE_ID,PARA_VALUE,PARA_MEMO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, guid);
			pstmt.setString(2, para.getId());
			pstmt.setLong(3, new Date().getTime());
			pstmt.setString(4, para.getCreated_by());
			pstmt.setLong(5, new Date().getTime());
			pstmt.setString(6, para.getCreated_by());
			pstmt.setString(7, para.getClient_guid());
			pstmt.setInt(8, 0);
			pstmt.setString(9, para.getData_ver());
			
			pstmt.setString(10, para.getPara_type_id());
			pstmt.setString(11, para.getPara_value());
			pstmt.setString(12, para.getPara_memo());
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		}
		
		return guid;
	}
	
	public static void updatePara(PARA_MAIN para,  Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try{			
			pstmt=conn.prepareStatement("UPDATE PARA_MAIN SET UPDATED_DT=?,UPDATED_BY=?,PARA_VALUE=?,PARA_MEMO=? WHERE PARA_TYPE_ID=? AND PARA_MAIN_ID=?");
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, para.getUpdated_by());
			pstmt.setString(3, para.getPara_value());
			pstmt.setString(4, para.getPara_memo());
			pstmt.setString(5, para.getPara_type_id());
			pstmt.setString(6, para.getId());
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void deletePara(String guid, Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		
		try{			
			pstmt=conn.prepareStatement("DELETE FROM PARA_MAIN WHERE PARA_MAIN_GUID=?");
			pstmt.setString(1, guid);
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void SaveCodeMain(CODE_MAIN data, Connection conn) throws Exception
	{
		if(data==null){
			throw new Exception("内容为空！");
		}
			
		if(StringUtils.isNullOrEmpty(data.getCode_main_id())){
			throw new Exception("代码为空！");
		}
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT CODE_MAIN_ID FROM CODE_MAIN WHERE CODE_MAIN_ID=?");
		pstmt.setString(1, data.getCode_main_id());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			PreparedStatement upPstmt = conn.prepareStatement("UPDATE CODE_MAIN SET CODE_VALUE=? WHERE  CODE_MAIN_ID=?");
			upPstmt.setString(1, data.getCode_value());
			upPstmt.setString(2, data.getCode_main_id());
			upPstmt.execute();
			upPstmt.close();
		}
		else
		{
			PreparedStatement inPstmt = conn.prepareStatement("INSERT INTO CODE_MAIN(CODE_MAIN_ID,CODE_VALUE) VALUES(?,?)");
			inPstmt.setString(1, data.getCode_main_id());
			inPstmt.setString(2, data.getCode_value());
			inPstmt.execute();
			inPstmt.close();
		}
		rs.close();
		pstmt.close();
	}
	
	public static CODE_MAIN GetCodeMain(String codeMainId, Connection conn) throws Exception
	{
		if(StringUtils.isNullOrEmpty(codeMainId)){
			throw new Exception("代码为空！");
		}
		
		CODE_MAIN result = new CODE_MAIN();
		PreparedStatement pstmt = conn.prepareStatement("SELECT CODE_MAIN_ID,CODE_VALUE FROM CODE_MAIN WHERE CODE_MAIN_ID=?");
		pstmt.setString(1, codeMainId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			result.setCode_main_id( rs.getString(1));
			result.setCode_value(rs.getString(2));
		}
		else
		{
			rs.close();
			pstmt.close();
			//throw new Exception("未找到记录");
		}
		
		return result;
	}
}
