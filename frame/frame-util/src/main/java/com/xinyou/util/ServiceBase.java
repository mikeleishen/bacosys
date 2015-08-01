package com.xinyou.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *  提供了数据库连接的开启和关闭功能
 * */
public class ServiceBase {
	private Connection conn;
	
	@Deprecated
	public Connection getConn() throws Exception {
		if (conn == null||conn.isClosed()) {
				try{Class.forName(Configuration.getDriver());
				conn = DriverManager.getConnection(Configuration.getUrl(), Configuration.getUserName(),Configuration.getPassword());
			}catch(Exception e){
				throw e;
			}
		}
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	@Deprecated
	public void close() {
		try {
			if(this.getConn() != null)
			{
				if (!this.getConn().isClosed()) {
					this.conn.close();
				}
			}
		}
		catch(Exception ex)
		{}
	}
}