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


import com.ly.base.vo.Aftersalestype;
import com.ly.base.service.AftersalestypeService;


@IocBean
@At("/aftersalestype")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class AftersalestypeAction {

	private static final Log log = Logs.getLog(AftersalestypeAction.class);
	
	@Inject
	private AftersalestypeService aftersalestypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/aftersalestype_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Aftersalestype aftersalestype,
                      HttpServletRequest request){

        Cnd c = new ParseObj(aftersalestype).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(aftersalestypeService.listCount(c));
            request.setAttribute("list_obj", aftersalestypeService.queryCache(c,p));
        }else{
            p.setRecordCount(aftersalestypeService.count(c));
            request.setAttribute("list_obj", aftersalestypeService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("aftersalestype", aftersalestype);
    }

    @At
    @Ok("beetl:/WEB-INF/base/aftersalestype.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("aftersalestype", null);
        }else{
            request.setAttribute("aftersalestype", aftersalestypeService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Aftersalestype aftersalestype){
        Object rtnObject;
        if (aftersalestype.getId() == null || aftersalestype.getId() == 0) {
            rtnObject = aftersalestypeService.dao().insert(aftersalestype);
        }else{
            rtnObject = aftersalestypeService.dao().updateIgnoreNull(aftersalestype);
        }
        CacheManager.getInstance().getCache(AftersalestypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_aftersalestype", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  aftersalestypeService.delete(id);
        CacheManager.getInstance().getCache(AftersalestypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_aftersalestype",false);
    }

}
