package com.xinyou.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionManager {
	
	private static DataSource getDs(String name) throws Exception{
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/"+name );
		if ( ds == null ) {
		   throw new Exception("Data source not found!");
		}
		return ds;
	}
	
	public static synchronized Connection getConnection(String name) throws Exception{
		return getDs(name).getConnection();
	}
	
	private static BasicDataSource ds = null;

	private static void loadDataSourceConfig() throws Exception{
		try{
			ds.setInitialSize(Configuration.getInt("datasource@InitialSize"));
		}catch(Exception e){}
		try{
			ds.setMaxActive(Configuration.getInt("datasource@MaxActive"));
		}catch(Exception e){}
		try{
			ds.setMaxIdle(Configuration.getInt("datasource@MaxIdle"));
		}catch(Exception e){}
		try{
			ds.setMaxWait(Configuration.getInt("datasource@MaxWait"));
		}catch(Exception e){}
		try{
			ds.setRemoveAbandoned(Configuration.getBoolean("datasource@RemoveAbandoned"));
		}catch(Exception e){}
		try{
			ds.setRemoveAbandonedTimeout(Configuration.getInt("datasource@RemoveAbandonedTimeout"));
		}catch(Exception e){}
		try{
			ds.setTestOnBorrow(Configuration.getBoolean("datasource@TestOnBorrow"));
		}catch(Exception e){}
		try{
			ds.setLogAbandoned(Configuration.getBoolean("datasource@LogAbandoned"));
		}catch(Exception e){}
	}

	@Deprecated
	/**
	 * 关闭现有数据连接池，然后重创建建数据库连接池
	 * @throws Exception
	 */
	public static void setupDataSource() throws Exception {
		if (ds != null) {
			try {
				ds.close();
			} catch (Exception e) {
			}
			ds = null;
		}

		ds = new BasicDataSource();
		loadDataSourceConfig();
		
		ds.setDriverClassName(Configuration.getDriver());
		ds.setUrl(Configuration.getUrl());
		ds.setUsername(Configuration.getUserName());
		ds.setPassword(Configuration.getPassword());
	}

	@Deprecated
	/**
	 * 关闭数据库连接池
	 * @throws SQLException
	 */
	public static void closeDataSource() throws SQLException {
		if (ds != null) {
				ds.close();
			ds = null;
		}
	}

	@Deprecated
	/**
	 * 从数据库连接池获得一个Connection，如果数据库连接池未创建则先创建数据库连接池
	 * @return
	 * @throws Exception
	 */
	public static synchronized Connection getConnection() throws Exception {
		Connection conn = null;
		
		if (ds == null) {
			setupDataSource();
		}
		if (ds != null) {
			if(Configuration.configFileChanged()){
				loadDataSourceConfig();
			}
			conn = ds.getConnection();
		}
		return conn;
	}
}