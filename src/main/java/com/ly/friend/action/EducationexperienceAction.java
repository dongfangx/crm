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


import com.ly.friend.vo.Educationexperience;
import com.ly.friend.service.EducationexperienceService;


@IocBean
@At("/educationexperience")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class EducationexperienceAction {

	private static final Log log = Logs.getLog(EducationexperienceAction.class);
	
	@Inject
	private EducationexperienceService educationexperienceService;

    @At("/")
    @Ok("beetl:/WEB-INF/friend/educationexperience_list.html")
    public void index(@Param("..")Page p,
                      @Param("friendid")long friendid,
                      @Param("..")Educationexperience educationexperience,
                      HttpServletRequest request){

        Cnd c = new ParseObj(educationexperience).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(educationexperienceService.listCount(c));
            request.setAttribute("list_obj", educationexperienceService.queryCache(c,p));
        }else{
            p.setRecordCount(educationexperienceService.count(c));
            request.setAttribute("list_obj", educationexperienceService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("educationexperience", educationexperience);
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("beetl:/WEB-INF/friend/educationexperience.html")
    public void edit(@Param("id")Long id,
                     @Param("friendid")long friendid,
                     HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("educationexperience", null);
        }else{
            request.setAttribute("educationexperience", educationexperienceService.fetch(id));
        }
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Educationexperience educationexperience){
        Object rtnObject;
        if (educationexperience.getId() == null || educationexperience.getId() == 0) {
            rtnObject = educationexperienceService.dao().insert(educationexperience);
        }else{
            rtnObject = educationexperienceService.dao().updateIgnoreNull(educationexperience);
        }
        CacheManager.getInstance().getCache(EducationexperienceService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_educationexperience", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  educationexperienceService.delete(id);
        CacheManager.getInstance().getCache(EducationexperienceService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_educationexperience",false);
    }

}
