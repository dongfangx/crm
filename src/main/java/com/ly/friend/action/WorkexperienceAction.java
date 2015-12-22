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


import com.ly.friend.vo.Workexperience;
import com.ly.friend.service.WorkexperienceService;


@IocBean
@At("/workexperience")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class WorkexperienceAction {

	private static final Log log = Logs.getLog(WorkexperienceAction.class);
	
	@Inject
	private WorkexperienceService workexperienceService;

    @At("/")
    @Ok("beetl:/WEB-INF/friend/workexperience_list.html")
    public void index(@Param("..")Page p,
                      @Param("friendid")long friendid,
                      @Param("..")Workexperience workexperience,
                      HttpServletRequest request){

        Cnd c = new ParseObj(workexperience).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(workexperienceService.listCount(c));
            request.setAttribute("list_obj", workexperienceService.queryCache(c,p));
        }else{
            p.setRecordCount(workexperienceService.count(c));
            request.setAttribute("list_obj", workexperienceService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("workexperience", workexperience);
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("beetl:/WEB-INF/friend/workexperience.html")
    public void edit(@Param("id")Long id,
                     @Param("friendid")long friendid,
                     HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("workexperience", null);
        }else{
            request.setAttribute("workexperience", workexperienceService.fetch(id));
        }
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Workexperience workexperience){
        Object rtnObject;
        if (workexperience.getId() == null || workexperience.getId() == 0) {
            rtnObject = workexperienceService.dao().insert(workexperience);
        }else{
            rtnObject = workexperienceService.dao().updateIgnoreNull(workexperience);
        }
        CacheManager.getInstance().getCache(WorkexperienceService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_workexperience", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  workexperienceService.delete(id);
        CacheManager.getInstance().getCache(WorkexperienceService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_workexperience",false);
    }

}
