package com.xinyou.util;

import org.apache.commons.mail.HtmlEmail;

public class MailHelper {
	
	private static String configName="ecp";
	
	public static void send(String subject, String body, String mailTo) throws Exception{
		HtmlEmail email = new HtmlEmail();
		try{
			Config config = new Config(configName);
			email.setHostName(config.get("mailSetting@host"));
			email.setCharset("UTF-8");
			email.addTo(mailTo);
			email.setFrom(config.get("mailSetting@from"),config.get("mailSetting@fromName"));
			email.setAuthentication(config.get("mailSetting@userName"), config.get("mailSetting@password"));
			email.setSubject(subject);
			email.setMsg(body);
			email.send();
		}catch(Exception e){
			throw e;
		}
	}
}
