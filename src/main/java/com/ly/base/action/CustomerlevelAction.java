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


import com.ly.base.vo.Customerlevel;
import com.ly.base.service.CustomerlevelService;


@IocBean
@At("/customerlevel")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class CustomerlevelAction {

	private static final Log log = Logs.getLog(CustomerlevelAction.class);
	
	@Inject
	private CustomerlevelService customerlevelService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/customerlevel_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Customerlevel customerlevel,
                      HttpServletRequest request){

        Cnd c = new ParseObj(customerlevel).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(customerlevelService.listCount(c));
            request.setAttribute("list_obj", customerlevelService.queryCache(c,p));
        }else{
            p.setRecordCount(customerlevelService.count(c));
            request.setAttribute("list_obj", customerlevelService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("customerlevel", customerlevel);
    }

    @At
    @Ok("beetl:/WEB-INF/base/customerlevel.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("customerlevel", null);
        }else{
            request.setAttribute("customerlevel", customerlevelService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Customerlevel customerlevel){
        Object rtnObject;
        if (customerlevel.getId() == null || customerlevel.getId() == 0) {
            rtnObject = customerlevelService.dao().insert(customerlevel);
        }else{
            rtnObject = customerlevelService.dao().updateIgnoreNull(customerlevel);
        }
        CacheManager.getInstance().getCache(CustomerlevelService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_customerlevel", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  customerlevelService.delete(id);
        CacheManager.getInstance().getCache(CustomerlevelService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_customerlevel",false);
    }

}
