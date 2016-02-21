package com.ly.base.action;

import com.ly.comm.Bjui;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.base.vo.Customertype;
import com.ly.base.service.CustomertypeService;


@IocBean
@At("/customertype")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class CustomertypeAction {

	private static final Log log = Logs.getLog(CustomertypeAction.class);
	
	@Inject
	private CustomertypeService customertypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/customertype_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Customertype customertype,
                      HttpServletRequest request){

        Cnd c = new ParseObj(customertype).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(customertypeService.listCount(c));
            request.setAttribute("list_obj", customertypeService.queryCache(c,p));
        }else{
            p.setRecordCount(customertypeService.count(c));
            request.setAttribute("list_obj", customertypeService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("customertype", customertype);
    }

    @At
    @Ok("beetl:/WEB-INF/base/customertype.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("customertype", null);
        }else{
            request.setAttribute("customertype", customertypeService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Customertype customertype){
        Object rtnObject;
        if (customertype.getId() == null || customertype.getId() == 0) {
            rtnObject = customertypeService.dao().insert(customertype);
        }else{
            rtnObject = customertypeService.dao().updateIgnoreNull(customertype);
        }
        CacheManager.getInstance().getCache(CustomertypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_customertype", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  customertypeService.delete(id);
        CacheManager.getInstance().getCache(CustomertypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_customertype",false);
    }

}
