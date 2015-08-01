package com.xinyou.util;





import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class AppConfig {

	/** 主配置文件路径，值为{@value #configfilePath}。 */
	public final static String configfilePath = "configfiles.xml";
	
	/**
	 * 创建配置对象以读取指定配置文件的配置项
	 * @param name 在{@value #configfilePath}中指定的配置文件名
	 * @throws Exception 
	 * @throws NullPointerException 如果未找到指定name的配置文件，则抛出异常
	 */
	public AppConfig() throws Exception{
		readConfigFile(configfilePath);
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception{
		return getConnection(null);
	}
	
	/**
	 * 从指定数据库配置名获取数据库连接
	 * @param name 指定数据库配置名，指配置文件中“datasource_”之后的部分。<br>
	 * 例如：“datasource_db1”，则数据库配置名为“db1”
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection(String name) throws Exception{
		String pre = "";
		if(name==null||name.length()<=0){
			pre="datasource";
		}else{
			pre="datasource_"+name;
		}

		AppConfig config = new AppConfig();
		String url = config.get(pre+"@url");
		String driverClassName = config.get(pre+"@driverClassName");
		String username = config.get(pre+"@username");
		String password = config.get(pre+"@password");
		
		Connection conn = null;
		try{
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, username, password);
		}catch(Exception e){
			throw e;
		}
		return conn;
	}
	
	/**
	 * 获取配置文件&lt;appsettings&gt;下的配置<br>
	 * 示例：<pre>
	 *   &lt;baseurl url="http://www.bacobook.com/"/&gt;
	 *   &lt;test&gt;
	 *       &lt;test2&gt;value&lt;/test2&gt;
	 *   &lt;/test&gt;
	 * 则：
	 *   get("baseurl@url")="http://www.bacobook.com/";
	 *   get("test/test2")="value";
	 * </pre>
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String get(String key) throws Exception{
		String key_l = key.toLowerCase();
		
		if(!config.containsKey(key_l)){
			throw new Exception("未找到配置项："+key);
		}
		return config.get(key_l);
	}

	/**
	 * 在内部调用{@link #get(String)}，然后将值转换成boolean类型
	 * @param key
	 * @return
	 * @throws Exception
	 * @see #get(String)
	 */
	public boolean getBoolean(String key) throws Exception{
		boolean ret = false;
		ret = Boolean.parseBoolean(get(key));
		return ret;
	}

	/**
	 * 在内部调用{@link #get(String)}，然后将值转换成int类型
	 * @param key
	 * @return
	 * @throws Exception
	 * @see #get(String)
	 */
	public int getInt(String key) throws Exception{
		int ret = 0;
		ret = Integer.parseInt(get(key));
		return ret;
	}

	/**
	 * 在内部调用{@link #get(String)}，然后将值转换成long类型
	 * @param key
	 * @return
	 * @throws Exception
	 * @see #get(String)
	 */
	public long getLong(String key) throws Exception{
		long ret = 0;
		ret = Long.parseLong(get(key));
		return ret;
	}

	/**
	 * 如果存在指定key的配置项，则返回true；否则，返回false
	 * @param key 当前配置文件中的配置项名称
	 * @return
	 */
	public boolean containsKey(String key){
		return config.containsKey(key);
	}

	private static long lastModified = -1L;
	/** 当前配置文件下的配置项 */
	private static Map<String, String> config = null;

	/**
	 * 返回指定配置文件的配置项
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private static void readConfigFile(String name) throws Exception{
		
		File file = new File(configfilePath);
		
		long _lm = file.lastModified();

		if(_lm!=lastModified || config==null){
			Map<String, String> _config = new HashMap<String, String>();

			try {
				SAXReader reader = new SAXReader();
				
				Document document = reader.read(file);
				Element root = document.getRootElement();
				
				readElement(root, _config, null);
				
			} catch (Exception e){
				throw new Exception("读取配置文件错误："+e.getMessage());
			}
			
			config = _config;

			if(lastModified<0L){
				Log.showInfo("Loading Config is completed");
			}else{
				Log.showInfo("Reloading Config is completed");
			}
			lastModified=_lm;
		}
	}

	private static void readElement(Element element, Map<String, String> config, String path) {
		if(element==null) return;
		if(path==null) path="";
		
	    if(!config.containsKey(path)){
	    	String v = element.getTextTrim();
	    	if(v!=null&&v.length()>0)
	    		config.put(path, v);
	    }

		@SuppressWarnings("unchecked")
		List<Element> subelements = element.elements();
	    if (subelements.size() > 0) {
	        //有子元素
	        for (Iterator<Element> it = subelements.iterator(); it.hasNext();) {
	            Element subelement = it.next();
				String newpath = (path.length()==0)?subelement.getName().toLowerCase():path+"/"+subelement.getName().toLowerCase();
	            readElement(subelement, config, newpath);
	        }
	    }else{
	        @SuppressWarnings("unchecked")
			List<Attribute> attributes = element.attributes();
	        for(Iterator<Attribute> it = attributes.iterator(); it.hasNext();){
	        	Attribute attr = it.next();
	        	String name_l = attr.getName().toLowerCase();
	            if(!config.containsKey(path+"@"+name_l)){
	            	config.put(path+"@"+name_l, attr.getValue());
	            }
	        }
	    }
	}
}
