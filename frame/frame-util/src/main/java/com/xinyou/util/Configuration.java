package com.xinyou.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@Deprecated
public class Configuration {
	static String configFile = "JavaServiceConfig.xml";
	
	private static Map<String,String> config = new HashMap<String,String>();

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
	public static String get(String key) throws Exception{
		if(configFileChanged()){
			readConfig();
		}
		
		if(!config.containsKey(key.toLowerCase())){
			throw new Exception("未找到配置项："+key);
		}
		return config.get(key.toLowerCase());
	}
	/**
	 * 在内部调用{@link #get(String)}，然后将值转换成boolean类型
	 * @param key
	 * @return
	 * @throws Exception
	 * @see #get(String)
	 */
	public static boolean getBoolean(String key) throws Exception{
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
	public static int getInt(String key) throws Exception{
		int ret = 0;
		ret = Integer.parseInt(get(key));
		return ret;
	}
 	
	public static String getDriver() throws Exception {
		if(configFileChanged()){
			readConfig();
		}
		return driver;
	}
	public static String getUrl() throws Exception {
		if(configFileChanged()){
			readConfig();
		}
		return url;
	}
	public static String getUserName() throws Exception {
		if(configFileChanged()){
			readConfig();
		}
		return userName;
	}
	public static String getPassword() throws Exception {
		if(configFileChanged()){
			readConfig();
		}
		return password;
	}
	
	public static boolean containsKey(String key){
		return config.containsKey(key);
	}

	private static File getConfigFile(){
		return new File(Configuration.class.getClassLoader().getResource(configFile).getFile().replaceAll("%20"," "));
	}
	
	private static long lastmodifidtime = -1L;
	public static boolean configFileChanged(){
		File file = getConfigFile();
		long _lm = file.lastModified();
		if(_lm==lastmodifidtime){
			return false;
		}else{
			lastmodifidtime=_lm;
		}
		return true;
	}
	
	private static void clearConfig(){
		driver = null;
		url = null;
		userName = null;
		password = null;
		config.clear();
	}
	
	private static void readConfig() throws Exception {
		try {
			clearConfig();
			
			SAXReader reader = new SAXReader();
			Document document = null;
		
			document = reader.read(getConfigFile());
			Element root = document.getRootElement();
			
	        Element connection = root.element("datasource");
	        if(connection!=null){
		        driver = connection.element("driver").getTextTrim();
				url = connection.element("url").getTextTrim();
				userName = connection.element("username").getText();
				password = connection.element("password").getText();
	        }
	        
	        Element appsettings = root.element("appsettings");
	        if(appsettings!=null){
	            //getSubElements(appsettings, "");
	        	
	        	@SuppressWarnings("unchecked")
	    		List<Element> elements = appsettings.elements();
	            if (elements.size() > 0) {
	                //有子元素
	                for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
	                    Element elem = it.next();
	                    getSubElements(elem, "");
	                }
	            }else{
	                @SuppressWarnings("unchecked")
	        		List<Attribute> attributes = appsettings.attributes();
	                for(Iterator<Attribute> it = attributes.iterator(); it.hasNext();){
	                	Attribute attr = it.next();
	                    if(!config.containsKey("@"+attr.getName().toLowerCase())){
	                    	config.put("@"+attr.getName().toLowerCase(), attr.getValue());
	                    }
	                }
	            }
	            
	        }
	        
		} catch (Exception e){
			throw new Exception("读取配置文件错误："+e.getMessage());
		}
	}

	private static void getSubElements(Element element, String path) {
		if(path.equals("")){
			path=element.getName().toLowerCase();
		}else{
	    	path=path+"/"+element.getName().toLowerCase();
		}
    	
        if(!config.containsKey(path)){
        	String v = element.getTextTrim();
        	if(v!=null&&v.length()>0)
        		config.put(path, v);
        }
        
		@SuppressWarnings("unchecked")
		List<Element> elements = element.elements();
        if (elements.size() > 0) {
            //有子元素
            for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
                Element elem = it.next();
                getSubElements(elem, path);
            }
        }else{
            @SuppressWarnings("unchecked")
    		List<Attribute> attributes = element.attributes();
            for(Iterator<Attribute> it = attributes.iterator(); it.hasNext();){
            	Attribute attr = it.next();
                if(!config.containsKey(path+"@"+attr.getName().toLowerCase())){
                	config.put(path+"@"+attr.getName().toLowerCase(), attr.getValue());
                }
            }
        }
    }

	private static String driver;
	private static String url;
	private static String userName;
	private static String password;
}
