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

public class Config {
	
	/** 主配置文件路径，值为{@value #configfilesPath}。（Linux下为"/"根目录，Windows下为程序所在盘的根目录） */
//	public final static String configfilesPath = "/config/configfiles.xml";
	public final static String configfilesPath = "C:/config/configfiles.xml";
	
	/** 配置文件中指定配置文件路径的标签名，值为{@value #configfileTag}*/
	public final static String configfileTag = "configfile";
	/** 配置文件中指定过滤关键词文件路径的标签名，值为{@value #wordfilterTag}*/
	public final static String wordfilterTag = "wordfilter";
	
	/**
	 * 创建配置对象以读取指定配置文件的配置项
	 * @param name 在{@value #configfilesPath}中指定的配置文件名
	 * @throws NullPointerException 如果未找到指定name的配置文件，则抛出异常
	 */
	public Config(String name) throws Exception{
		String lower = name.toLowerCase();
		if(configfiles==null){
			readConfigFiles();
		}
		if(!configfiles.containsKey(lower)){
			throw new NullPointerException("未指定name为“"+name+"”的配置文件");
		}
		
		currentconfig = readConfigFileOnDemand(lower);
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
		String lower = key.toLowerCase();
		
		if(!currentconfig.containsKey(lower)){
			throw new Exception("未找到配置项："+key);
		}
		return currentconfig.get(lower);
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
		if(key==null) return false;
		return currentconfig.containsKey(key.toLowerCase());
	}

	/**
	 * 如果存在指定name的配置文件，则返回true；否则，返回false
	 * @param name 在{@value #configfilesPath}中指定的配置文件名
	 * @return
	 */
	public static boolean containsConfig(String name){
		if(name==null) return false;
		return configs.containsKey(name.toLowerCase());
	}

	public static String getWordfilterFilePath() throws Exception{
		if(configfiles==null){
			readConfigFiles();
		}
		return wordfilter;
	}
	
	/** 配置文件信息，包括配置文件路径和最后修改时间 */
	static class ConfigFile{
		ConfigFile(String path){
			this.path = path;
			lastmodified=-1L;
		}
		String path;
		long lastmodified;
	}

	/** 用于保存配置文件列表，包括文件路径及最后修改时间 */
	private static Map<String, ConfigFile> configfiles = null;
	private static String wordfilter = null;
	/** 用于保存所有配置文件的配置项 */
	private static Map<String, Map<String, String>> configs = new HashMap<String, Map<String, String>>();
	/** 当前配置文件下的配置项 */
	private Map<String, String> currentconfig = null;

	/**
	 * 从{@value #configfilesPath}读取配置文件列表
	 * @throws Exception
	 */
	private static void readConfigFiles() throws Exception {
		configfiles = new HashMap<String, ConfigFile>();
		try {
			SAXReader reader = new SAXReader();
			Document document = null;
			
			document = reader.read(new File(configfilesPath));
			Element root = document.getRootElement();
	
	    	@SuppressWarnings("unchecked")
			List<Element> elements = root.elements();
			if(elements!=null){
				for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
					Element elem = it.next();
					String tagName = elem.getName();
					if(configfileTag.equals(tagName)){
						Attribute name = elem.attribute("name");
						Attribute path = elem.attribute("path");
						if(name!=null&&path!=null){
							configfiles.put(name.getText().toLowerCase(), new ConfigFile(path.getText()));
						}
					}else if(wordfilterTag.equals(tagName)){
						wordfilter = elem.attributeValue("path");
					}
				}
			}
	    	
		} catch (Exception e){
			throw new Exception("读取配置文件错误："+e.getMessage());
		}
	}

	/**
	 * 返回指定配置文件的配置项
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private static Map<String, String> readConfigFileOnDemand(String name) throws Exception{
		ConfigFile cf = configfiles.get(name);
		File file = new File(cf.path);
		
		long _lm = file.lastModified();

		Map<String, String> _config = null;
		if(_lm==cf.lastmodified&&configs.containsKey(name)){
			_config = configs.get(name);
		}else{
			_config = new HashMap<String, String>();

			try {
				SAXReader reader = new SAXReader();
				
				Document document = reader.read(file);
				Element root = document.getRootElement();
				
				readElement(root, _config, null);
				
			} catch (Exception e){
				throw new Exception("读取配置文件错误："+e.getMessage());
			}
			
	        configs.put(name, _config);

			if(cf.lastmodified<0L){
				Log.showInfo("Loading Config with name ["+name+"] is completed");
			}else{
				Log.showInfo("Reloading Config with name ["+name+"] is completed");
			}
			cf.lastmodified=_lm;
		}
		
		return _config;
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
