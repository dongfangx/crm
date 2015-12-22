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


import com.ly.friend.vo.Familymembers;
import com.ly.friend.service.FamilymembersService;


@IocBean
@At("/familymembers")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class FamilymembersAction {

	private static final Log log = Logs.getLog(FamilymembersAction.class);
	
	@Inject
	private FamilymembersService familymembersService;

    @At("/")
    @Ok("beetl:/WEB-INF/friend/familymembers_list.html")
    public void index(@Param("..")Page p,
                      @Param("friendid")long friendid,
                      @Param("..")Familymembers familymembers,
                      HttpServletRequest request){

        Cnd c = new ParseObj(familymembers).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(familymembersService.listCount(c));
            request.setAttribute("list_obj", familymembersService.queryCache(c,p));
        }else{
            p.setRecordCount(familymembersService.count(c));
            request.setAttribute("list_obj", familymembersService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("familymembers", familymembers);
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("beetl:/WEB-INF/friend/familymembers.html")
    public void edit(@Param("id")Long id,
                     @Param("friendid")long friendid,
                     HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("familymembers", null);
        }else{
            request.setAttribute("familymembers", familymembersService.fetch(id));
        }
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Familymembers familymembers){
        Object rtnObject;
        if (familymembers.getId() == null || familymembers.getId() == 0) {
            rtnObject = familymembersService.dao().insert(familymembers);
        }else{
            rtnObject = familymembersService.dao().updateIgnoreNull(familymembers);
        }
        CacheManager.getInstance().getCache(FamilymembersService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_familymembers", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  familymembersService.delete(id);
        CacheManager.getInstance().getCache(FamilymembersService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_familymembers",false);
    }

}
