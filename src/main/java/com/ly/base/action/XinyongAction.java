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


import com.ly.base.vo.Xinyong;
import com.ly.base.service.XinyongService;


@IocBean
@At("/xinyong")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class XinyongAction {

	private static final Log log = Logs.getLog(XinyongAction.class);
	
	@Inject
	private XinyongService xinyongService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/xinyong_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Xinyong xinyong,
                      HttpServletRequest request){

        Cnd c = new ParseObj(xinyong).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(xinyongService.listCount(c));
            request.setAttribute("list_obj", xinyongService.queryCache(c,p));
        }else{
            p.setRecordCount(xinyongService.count(c));
            request.setAttribute("list_obj", xinyongService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("xinyong", xinyong);
    }

    @At
    @Ok("beetl:/WEB-INF/base/xinyong.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("xinyong", null);
        }else{
            request.setAttribute("xinyong", xinyongService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Xinyong xinyong){
        Object rtnObject;
        if (xinyong.getId() == null || xinyong.getId() == 0) {
            rtnObject = xinyongService.dao().insert(xinyong);
        }else{
            rtnObject = xinyongService.dao().updateIgnoreNull(xinyong);
        }
        CacheManager.getInstance().getCache(XinyongService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_xinyong", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  xinyongService.delete(id);
        CacheManager.getInstance().getCache(XinyongService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_xinyong",false);
    }

}
