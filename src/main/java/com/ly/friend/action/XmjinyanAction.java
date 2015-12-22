package com.ly.friend.action;

import com.ly.comm.Bjui;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.friend.vo.Xmjinyan;
import com.ly.friend.service.XmjinyanService;


@IocBean
@At("/xmjinyan")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class XmjinyanAction {

	private static final Log log = Logs.getLog(XmjinyanAction.class);
	
	@Inject
	private XmjinyanService xmjinyanService;

    @At("/")
    @Ok("beetl:/WEB-INF/friend/xmjinyan_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Xmjinyan xmjinyan,
                      @Param("friendid")long friendid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(xmjinyan).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(xmjinyanService.listCount(c));
            request.setAttribute("list_obj", xmjinyanService.queryCache(c,p));
        }else{
            p.setRecordCount(xmjinyanService.count(c));
            request.setAttribute("list_obj", xmjinyanService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("xmjinyan", xmjinyan);
        request.setAttribute("friendid",friendid);
    }

    @At
    @Ok("beetl:/WEB-INF/friend/xmjinyan.html")
    public void edit(@Param("id")Long id,
                     @Param("friendid")long friendid,
                     HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("xmjinyan", null);
        }else{
            request.setAttribute("xmjinyan", xmjinyanService.fetch(id));
        }
        request.setAttribute("friendid",friendid);
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Xmjinyan xmjinyan){
        Object rtnObject;
        if (xmjinyan.getId() == null || xmjinyan.getId() == 0) {
            rtnObject = xmjinyanService.dao().insert(xmjinyan);
        }else{
            rtnObject = xmjinyanService.dao().updateIgnoreNull(xmjinyan);
        }
        CacheManager.getInstance().getCache(XmjinyanService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_xmjinyan", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  xmjinyanService.delete(id);
        CacheManager.getInstance().getCache(XmjinyanService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_xmjinyan",false);
    }

}
