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


import com.ly.base.vo.Education;
import com.ly.base.service.EducationService;


@IocBean
@At("/education")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class EducationAction {

	private static final Log log = Logs.getLog(EducationAction.class);
	
	@Inject
	private EducationService educationService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/education_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Education education,
                      HttpServletRequest request){

        Cnd c = new ParseObj(education).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(educationService.listCount(c));
            request.setAttribute("list_obj", educationService.queryCache(c,p));
        }else{
            p.setRecordCount(educationService.count(c));
            request.setAttribute("list_obj", educationService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("education", education);
    }

    @At
    @Ok("beetl:/WEB-INF/base/education.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("education", null);
        }else{
            request.setAttribute("education", educationService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Education education){
        Object rtnObject;
        if (education.getId() == null || education.getId() == 0) {
            rtnObject = educationService.dao().insert(education);
        }else{
            rtnObject = educationService.dao().updateIgnoreNull(education);
        }
        CacheManager.getInstance().getCache(EducationService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_education", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  educationService.delete(id);
        CacheManager.getInstance().getCache(EducationService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_education",false);
    }

}
