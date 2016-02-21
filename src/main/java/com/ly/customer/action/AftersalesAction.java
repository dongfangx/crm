package com.ly.customer.action;

import com.ly.base.service.AftersalestypeService;
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


import com.ly.customer.vo.Aftersales;
import com.ly.customer.service.AftersalesService;


@IocBean
@At("/aftersales")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class AftersalesAction {

	private static final Log log = Logs.getLog(AftersalesAction.class);
	
	@Inject
	private AftersalesService aftersalesService;

    @Inject
    private AftersalestypeService aftersalestypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/customer/aftersales_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Aftersales aftersales,
                      @Param("customerid")long customerid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(aftersales).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(aftersalesService.listCount(c));
            request.setAttribute("list_obj", aftersalesService.queryCache(c,p));
        }else{
            p.setRecordCount(aftersalesService.count(c));
            request.setAttribute("list_obj", aftersalesService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("aftersales", aftersales);
        request.setAttribute("aftersalestypeList",aftersalestypeService.queryCache(null, new Page()));
        request.setAttribute("customerid",customerid);

    }

    @At
    @Ok("beetl:/WEB-INF/customer/aftersales.html")
    public void edit(@Param("id")Long id,
                     @Param("action")int action,
                     @Param("customerid")long customerid,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("aftersales", null);
        }else{
            Aftersales aftersales = aftersalesService.fetch(id);
            if (action == 3)
            {
                aftersales.setId(null);
            }
            request.setAttribute("aftersales", aftersales);
        }
        request.setAttribute("aftersalestypeList",aftersalestypeService.queryCache(null, new Page()));
        request.setAttribute("customerid",customerid);

    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Aftersales aftersales){
        Object rtnObject;
        if (aftersales.getId() == null || aftersales.getId() == 0) {
            aftersales.setAdddate(new Date());
            rtnObject = aftersalesService.dao().insert(aftersales);
        }else{
            rtnObject = aftersalesService.dao().updateIgnoreNull(aftersales);
        }
        CacheManager.getInstance().getCache(AftersalesService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_aftersales", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  aftersalesService.delete(id);
        CacheManager.getInstance().getCache(AftersalesService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_aftersales",false);
    }

}
