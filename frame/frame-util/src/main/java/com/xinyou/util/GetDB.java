package com.xinyou.util;


import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;


/**
 *   芒果db 访问
 *  */
public class GetDB {
	private static Mongo server;
	private static DB db;
    private static DBCollection logCollection;

	private static DB getDb(String configName) throws Exception{
		if (db == null) {
			Config config = new Config(configName);
			server = new Mongo(config.get("mongo@url"));
			db = server.getDB(config.get("mongo@db"));
		}
		return db;
	}

	public static DBCollection getLogCollection(String configName) throws Exception{
		if(logCollection==null){
			logCollection = getDb(configName).getCollection("log");
		}
		return logCollection;
	}

	public static DBCollection getCollection(String configName, String collName) throws Exception{
		return getDb(configName).getCollection(collName);
	}
}

