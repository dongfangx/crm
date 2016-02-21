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


import com.ly.base.vo.Linktype;
import com.ly.base.service.LinktypeService;


@IocBean
@At("/linktype")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class LinktypeAction {

	private static final Log log = Logs.getLog(LinktypeAction.class);
	
	@Inject
	private LinktypeService linktypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/linktype_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Linktype linktype,
                      HttpServletRequest request){

        Cnd c = new ParseObj(linktype).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(linktypeService.listCount(c));
            request.setAttribute("list_obj", linktypeService.queryCache(c,p));
        }else{
            p.setRecordCount(linktypeService.count(c));
            request.setAttribute("list_obj", linktypeService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("linktype", linktype);
    }

    @At
    @Ok("beetl:/WEB-INF/base/linktype.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("linktype", null);
        }else{
            request.setAttribute("linktype", linktypeService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Linktype linktype){
        Object rtnObject;
        if (linktype.getId() == null || linktype.getId() == 0) {
            rtnObject = linktypeService.dao().insert(linktype);
        }else{
            rtnObject = linktypeService.dao().updateIgnoreNull(linktype);
        }
        CacheManager.getInstance().getCache(LinktypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_linktype", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  linktypeService.delete(id);
        CacheManager.getInstance().getCache(LinktypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_linktype",false);
    }

}
