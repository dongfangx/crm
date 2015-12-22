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


import com.ly.base.vo.Marital;
import com.ly.base.service.MaritalService;


@IocBean
@At("/marital")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class MaritalAction {

	private static final Log log = Logs.getLog(MaritalAction.class);
	
	@Inject
	private MaritalService maritalService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/marital_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Marital marital,
                      HttpServletRequest request){

        Cnd c = new ParseObj(marital).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(maritalService.listCount(c));
            request.setAttribute("list_obj", maritalService.queryCache(c,p));
        }else{
            p.setRecordCount(maritalService.count(c));
            request.setAttribute("list_obj", maritalService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("marital", marital);
    }

    @At
    @Ok("beetl:/WEB-INF/base/marital.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("marital", null);
        }else{
            request.setAttribute("marital", maritalService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Marital marital){
        Object rtnObject;
        if (marital.getId() == null || marital.getId() == 0) {
            rtnObject = maritalService.dao().insert(marital);
        }else{
            rtnObject = maritalService.dao().updateIgnoreNull(marital);
        }
        CacheManager.getInstance().getCache(MaritalService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_marital", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  maritalService.delete(id);
        CacheManager.getInstance().getCache(MaritalService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_marital",false);
    }

}
