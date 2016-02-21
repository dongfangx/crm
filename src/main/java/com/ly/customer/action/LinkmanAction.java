package com.ly.customer.action;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.customer.vo.Linkman;
import com.ly.customer.service.LinkmanService;


@IocBean
@At("/linkman")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class LinkmanAction {

	private static final Log log = Logs.getLog(LinkmanAction.class);
	
	@Inject
	private LinkmanService linkmanService;

    @At("/")
    @Ok("beetl:/WEB-INF/customer/linkman_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Linkman linkman,
                      HttpServletRequest request){

        Cnd c = new ParseObj(linkman).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(linkmanService.listCount(c));
            request.setAttribute("list_obj", linkmanService.queryCache(c,p));
        }else{
            p.setRecordCount(linkmanService.count(c));
            request.setAttribute("list_obj", linkmanService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("linkman", linkman);
    }

    @At
    @Ok("beetl:/WEB-INF/customer/linkman.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("linkman", null);
        }else{
            request.setAttribute("linkman", linkmanService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Linkman linkman){
        Object rtnObject;
        if (linkman.getId() == null || linkman.getId() == 0) {
            linkman.setAdddate(new Date());
            rtnObject = linkmanService.dao().insert(linkman);
        }else{
            rtnObject = linkmanService.dao().updateIgnoreNull(linkman);
        }
        CacheManager.getInstance().getCache(LinkmanService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_linkman", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  linkmanService.delete(id);
        CacheManager.getInstance().getCache(LinkmanService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_linkman",false);
    }

}
