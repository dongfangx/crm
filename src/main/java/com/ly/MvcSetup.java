package com.ly;

import com.ly.comm.AppContext;
//import com.ly.comm.CBData;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;


public class MvcSetup implements Setup {

	public void destroy(NutConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	public void init(NutConfig config) {
		AppContext.ioc = config.getIoc();
		
		//初始化基础信息数据
//		CBData.getInstance().init();
	}

}
