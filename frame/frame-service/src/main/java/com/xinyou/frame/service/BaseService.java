package com.xinyou.frame.service;

import java.sql.Connection;

import com.xinyou.frame.domain.biz.USR_BIZ;
import com.xinyou.frame.domain.entities.USR_MAIN;



public class BaseService {

	  protected USR_MAIN ui;
	  protected USR_MAIN getUserInfo(String token ,Connection conn) throws Exception {
		  
		  if ( token == null || token.length() == 0 ) {
			   ui = new USR_MAIN();
			   ui.setStatus(1);
			   return ui;
		  }  
		  USR_BIZ biz = new USR_BIZ();
		  ui = biz.getUserInfo(token, conn);
		  return ui; 
	  }
	
}
