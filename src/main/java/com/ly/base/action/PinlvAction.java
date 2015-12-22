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


import com.ly.base.vo.Pinlv;
import com.ly.base.service.PinlvService;


@IocBean
@At("/pinlv")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class PinlvAction {

	private static final Log log = Logs.getLog(PinlvAction.class);
	
	@Inject
	private PinlvService pinlvService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/pinlv_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Pinlv pinlv,
                      HttpServletRequest request){

        Cnd c = new ParseObj(pinlv).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(pinlvService.listCount(c));
            request.setAttribute("list_obj", pinlvService.queryCache(c,p));
        }else{
            p.setRecordCount(pinlvService.count(c));
            request.setAttribute("list_obj", pinlvService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("pinlv", pinlv);
    }

    @At
    @Ok("beetl:/WEB-INF/base/pinlv.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("pinlv", null);
        }else{
            request.setAttribute("pinlv", pinlvService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Pinlv pinlv){
        Object rtnObject;
        if (pinlv.getId() == null || pinlv.getId() == 0) {
            rtnObject = pinlvService.dao().insert(pinlv);
        }else{
            rtnObject = pinlvService.dao().updateIgnoreNull(pinlv);
        }
        CacheManager.getInstance().getCache(PinlvService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_pinlv", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  pinlvService.delete(id);
        CacheManager.getInstance().getCache(PinlvService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_pinlv",false);
    }

}
