package com.ly.customer.action;

import com.ly.base.service.EventtypeService;
import com.ly.comm.Bjui;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import com.ly.customer.vo.Customer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.customer.vo.Customerevent;
import com.ly.customer.service.CustomereventService;


@IocBean
@At("/customerevent")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class CustomereventAction {

	private static final Log log = Logs.getLog(CustomereventAction.class);
	
	@Inject
	private CustomereventService customereventService;

    @Inject
    private EventtypeService eventtypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/customer/customerevent_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Customerevent customerevent,
                      @Param("customerid")long customerid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(customerevent).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(customereventService.listCount(c));
            request.setAttribute("list_obj", customereventService.queryCache(c,p));
        }else{
            p.setRecordCount(customereventService.count(c));
            request.setAttribute("list_obj", customereventService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("customerevent", customerevent);
        request.setAttribute("eventtypeList",eventtypeService.queryCache(null,new Page()));
        request.setAttribute("customerid",customerid);

    }

    @At
    @Ok("beetl:/WEB-INF/customer/customerevent.html")
    public void edit(@Param("id")Long id,
                     @Param("action")int action,
                     @Param("customerid")long customerid,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("customerevent", null);
        }else{
            Customerevent customerevent = customereventService.fetch(id);
            if (action == 3)
            {
                customerevent.setId(null);
            }
            request.setAttribute("customerevent", customerevent);
        }
        request.setAttribute("eventtypeList",eventtypeService.queryCache(null,new Page()));
        request.setAttribute("customerid",customerid);


    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Customerevent customerevent){
        Object rtnObject;
        if (customerevent.getId() == null || customerevent.getId() == 0) {
            customerevent.setAdddate(new Date());
            rtnObject = customereventService.dao().insert(customerevent);
        }else{
            rtnObject = customereventService.dao().updateIgnoreNull(customerevent);
        }
        CacheManager.getInstance().getCache(CustomereventService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_customerevent", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  customereventService.delete(id);
        CacheManager.getInstance().getCache(CustomereventService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_customerevent",false);
    }

}
