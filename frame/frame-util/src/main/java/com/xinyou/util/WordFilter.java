package com.xinyou.util;

import java.io.File;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class WordFilter {

	/**
	 * 过滤关键字
	 * @param str 待过滤字符串
	 * @param with 将关键字替换为
	 * @return
	 * @throws Exception 
	 */
	public static String filter(String str, String with) throws Exception{
		if(str==null||str.length()==0){
			return str;
		}
		for(String s :getKeywords()){
			str = str.replace(s, StringUtil.repeat(with,s.length()));
		}
		return str;
	}
	
	/**
	 * 判断字符串是否包含关键字
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public static boolean check(String str) throws Exception{
		if(str==null||str.length()==0){
			return false;
		}
		for(String s : getKeywords()){
			if(str.contains(s)){
				return true;
			}
		}
		return false;
	}

	public static String[] getKeywords() throws Exception{
		if(keywords == null){
			ObjectMapper mapper=new ObjectMapper();
			keywords =mapper.readValue(getConfigFile(), new TypeReference<String[]>(){});
		}
		return keywords;
	}

	private static String[] keywords =null;
	
	private static File getConfigFile() throws Exception{
		return new File(Config.getWordfilterFilePath());
	}
	
	private static long lastmodifidtime = -1L;
	public static boolean configFileChanged() throws Exception{
		File file = getConfigFile();
		long _lm = file.lastModified();
		if(_lm==lastmodifidtime){
			return false;
		}else{
			lastmodifidtime=_lm;
		}
		return true;
	}
}
