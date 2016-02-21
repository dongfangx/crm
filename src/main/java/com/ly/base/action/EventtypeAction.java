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


import com.ly.base.vo.Eventtype;
import com.ly.base.service.EventtypeService;


@IocBean
@At("/eventtype")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class EventtypeAction {

	private static final Log log = Logs.getLog(EventtypeAction.class);
	
	@Inject
	private EventtypeService eventtypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/eventtype_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Eventtype eventtype,
                      HttpServletRequest request){

        Cnd c = new ParseObj(eventtype).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(eventtypeService.listCount(c));
            request.setAttribute("list_obj", eventtypeService.queryCache(c,p));
        }else{
            p.setRecordCount(eventtypeService.count(c));
            request.setAttribute("list_obj", eventtypeService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("eventtype", eventtype);
    }

    @At
    @Ok("beetl:/WEB-INF/base/eventtype.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("eventtype", null);
        }else{
            request.setAttribute("eventtype", eventtypeService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Eventtype eventtype){
        Object rtnObject;
        if (eventtype.getId() == null || eventtype.getId() == 0) {
            rtnObject = eventtypeService.dao().insert(eventtype);
        }else{
            rtnObject = eventtypeService.dao().updateIgnoreNull(eventtype);
        }
        CacheManager.getInstance().getCache(EventtypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_eventtype", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  eventtypeService.delete(id);
        CacheManager.getInstance().getCache(EventtypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_eventtype",false);
    }

}
