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


import com.ly.friend.vo.Jinengpj;
import com.ly.friend.service.JinengpjService;


@IocBean
@At("/jinengpj")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class JinengpjAction {

	private static final Log log = Logs.getLog(JinengpjAction.class);
	
	@Inject
	private JinengpjService jinengpjService;

    @At("/")
    @Ok("beetl:/WEB-INF/friend/jinengpj_list.html")
    public void index(@Param("..")Page p,
                      @Param("friendid")long friendid,
                      @Param("..")Jinengpj jinengpj,
                      HttpServletRequest request){

        Cnd c = new ParseObj(jinengpj).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(jinengpjService.listCount(c));
            request.setAttribute("list_obj", jinengpjService.queryCache(c,p));
        }else{
            p.setRecordCount(jinengpjService.count(c));
            request.setAttribute("list_obj", jinengpjService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("jinengpj", jinengpj);
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("beetl:/WEB-INF/friend/jinengpj.html")
    public void edit(@Param("id")Long id,
                     @Param("friendid")long friendid,
                     HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("jinengpj", null);
        }else{
            request.setAttribute("jinengpj", jinengpjService.fetch(id));
        }
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Jinengpj jinengpj){
        Object rtnObject;
        if (jinengpj.getId() == null || jinengpj.getId() == 0) {
            rtnObject = jinengpjService.dao().insert(jinengpj);
        }else{
            rtnObject = jinengpjService.dao().updateIgnoreNull(jinengpj);
        }
        CacheManager.getInstance().getCache(JinengpjService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_jinengpj", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  jinengpjService.delete(id);
        CacheManager.getInstance().getCache(JinengpjService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_jinengpj",false);
    }

}
