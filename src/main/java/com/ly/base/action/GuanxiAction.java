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


import com.ly.base.vo.Guanxi;
import com.ly.base.service.GuanxiService;


@IocBean
@At("/guanxi")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class GuanxiAction {

	private static final Log log = Logs.getLog(GuanxiAction.class);
	
	@Inject
	private GuanxiService guanxiService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/guanxi_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Guanxi guanxi,
                      HttpServletRequest request){

        Cnd c = new ParseObj(guanxi).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(guanxiService.listCount(c));
            request.setAttribute("list_obj", guanxiService.queryCache(c,p));
        }else{
            p.setRecordCount(guanxiService.count(c));
            request.setAttribute("list_obj", guanxiService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("guanxi", guanxi);
    }

    @At
    @Ok("beetl:/WEB-INF/base/guanxi.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("guanxi", null);
        }else{
            request.setAttribute("guanxi", guanxiService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Guanxi guanxi){
        Object rtnObject;
        if (guanxi.getId() == null || guanxi.getId() == 0) {
            rtnObject = guanxiService.dao().insert(guanxi);
        }else{
            rtnObject = guanxiService.dao().updateIgnoreNull(guanxi);
        }
        CacheManager.getInstance().getCache(GuanxiService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_guanxi", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  guanxiService.delete(id);
        CacheManager.getInstance().getCache(GuanxiService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_guanxi",false);
    }

}
