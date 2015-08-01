package com.xinyou.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/** Log 日志记录帮助类  */
public class Log {
	
	/**
	 *  记录日志 ，可以把日志记录在文件中或者芒果db中 ，取决于 config 文件中的
	 *   <Config>
	 *    ...
	 *   <appException logMode="2" />
	      <appInfo      logMode="2" />
	     <appLog logPath="c:\log\GuangLong" />
	     </config>
	     
	     注意： logmode 取值 0 和1 是有效的。 其他的值不会记录日志。
	 *  */
	public static void logSys(String configName, String className, String methodName
			, String logInfo, String logType) {
		try {
			Config config = new Config(configName);
			String filePath = config.get("applog@logpath");
			DBCollection logColl = GetDB.getLogCollection(configName);
			if(logType.equals("Exception")){
				String logMode = config.get("appException@logmode");
				if(logMode.equals("0")){
					logMongo(logColl, className, methodName, logInfo, logType);
				}else if(logMode.equals("1")){
					logFile(filePath, className, methodName, logInfo, logType);
				}
			}else if(logType.equals("Info")){
				String logMode = config.get("appinfo@logmode");
				if(logMode.equals("0")){
					logMongo(logColl, className, methodName, logInfo, logType);
				}else if(logMode.equals("1")){
					logFile(filePath, className, methodName, logInfo, logType);
				}
			}
		} catch (Exception e) {
		}

	}
	private static void logFile(String logPath, String className, String methodName
			, String logInfo, String logType){
		try{
			SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat second = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String strLogDay = day.format(new Date());
			String strLogSecond = second.format(new Date());
			
			StringBuffer sb = new StringBuffer();
			sb.append(strLogSecond + " "+logType+" "+className+" "+methodName+" "+logInfo);
			
			File foldPath = new File(logPath);
			if (!(foldPath.isDirectory())) foldPath.mkdirs();
			
			logPath = logPath + File.separator + strLogDay + ".txt";

			File filePath = new File(logPath);
			if(!filePath.exists()){
				filePath.createNewFile();
			}
			
			FileWriter fw = new FileWriter(logPath, true);
	        PrintWriter pw=new PrintWriter(fw);  
	        pw.println(sb.toString());  
	        pw.close () ;  
	        fw.close () ;  
		}catch(Exception e){
		}
	}
	private static void logMongo(DBCollection coll, String className, String methodName
			, String logInfo, String logType){
		try {
			DBObject doc = new BasicDBObject();
			doc.put("log_type", logType);
			doc.put("class_name", className);
			doc.put("method_name",methodName);
			doc.put("log_info",logInfo);
			doc.put("log_date", new Date().getTime());
			coll.insert(doc);
		} catch (Exception e) {
		}	
	}
	
	public static void showInfo(String info){
		System.out.println(new SimpleDateFormat("[yyyy/MM/dd hh:mm:ss] ").format(new Date())+info);
	}
}

