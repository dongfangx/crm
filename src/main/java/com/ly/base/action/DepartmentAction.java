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


import com.ly.base.vo.Department;
import com.ly.base.service.DepartmentService;


@IocBean
@At("/department")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class DepartmentAction {

	private static final Log log = Logs.getLog(DepartmentAction.class);
	
	@Inject
	private DepartmentService departmentService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/department_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Department department,
                      HttpServletRequest request){

        Cnd c = new ParseObj(department).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(departmentService.listCount(c));
            request.setAttribute("list_obj", departmentService.queryCache(c,p));
        }else{
            p.setRecordCount(departmentService.count(c));
            request.setAttribute("list_obj", departmentService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("department", department);
    }

    @At
    @Ok("beetl:/WEB-INF/base/department.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("department", null);
        }else{
            request.setAttribute("department", departmentService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Department department){
        Object rtnObject;
        if (department.getId() == null || department.getId() == 0) {
            rtnObject = departmentService.dao().insert(department);
        }else{
            rtnObject = departmentService.dao().updateIgnoreNull(department);
        }
        CacheManager.getInstance().getCache(DepartmentService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_department", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  departmentService.delete(id);
        CacheManager.getInstance().getCache(DepartmentService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_department",false);
    }

}
