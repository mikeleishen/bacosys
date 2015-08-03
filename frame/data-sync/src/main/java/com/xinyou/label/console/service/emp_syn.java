package com.xinyou.label.console.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class emp_syn {
	
	private static SimpleDateFormat LDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

		
		
    public static void SynEmp( Connection sourceConn,Connection destinationConn ) throws SQLException
    {
			System.out.println(LDF.format(new Date())+":"+" emp_syn begin.");
			
//			PreparedStatement psGetSource = sourceConn.prepareStatement("SELECT DBA001,DBA002 FROM TPADBA WHERE DBA006 IN(N'手焊线',N'包装线',N'配管线')");
			PreparedStatement psGetSource = sourceConn.prepareStatement("SELECT DBA001,DBA002 FROM TPADBA");
			ResultSet rsSource = psGetSource.executeQuery();
			while(rsSource.next()){
				PreparedStatement psGetDes = destinationConn.prepareStatement("SELECT EMP_MAIN_ID FROM EMP_MAIN WHERE EMP_MAIN_ID=?");
				psGetDes.setString(1, rsSource.getString(1));
				
				ResultSet rsDes = psGetDes.executeQuery();
				if(!rsDes.next())
				{
					PreparedStatement insertDes = destinationConn.prepareStatement("INSERT INTO EMP_MAIN(EMP_MAIN_GUID,EMP_MAIN_ID,CREATED_DT,CREATED_BY,UPDATED_DT,UPDATED_BY,CLIENT_GUID,IS_DELETED,DATA_VER,EMP_NAME,EMP_TYPE,EMP_STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
					insertDes.setString(1, UUID.randomUUID().toString());
					insertDes.setString(2, rsSource.getString(1));
					insertDes.setLong(3, new Date().getTime());
					insertDes.setString(4, "syn");
					insertDes.setLong(5, new Date().getTime());
					insertDes.setString(6, "syn");
					insertDes.setString(7, "gl");
					insertDes.setInt(8, 0);
					insertDes.setString(9, "1.0.0.0");
					//EMP_NAME,EMP_TYPE,EMP_STATUS
					insertDes.setString(10, rsSource.getString(2));
					insertDes.setInt(11, 3);
					insertDes.setInt(12, 1);
					insertDes.execute();
					insertDes.close();
				}
				rsDes.close();
				psGetDes.close();
			}
			
			rsSource.close();
			psGetSource.close();
			
			System.out.println(LDF.format(new Date())+":"+" emp_syn end.");
		
	}

}
