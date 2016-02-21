package com.ly.base.action;

import com.ly.base.service.DepartmentService;
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


import com.ly.base.vo.Employee;
import com.ly.base.service.EmployeeService;


@IocBean
@At("/employee")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class EmployeeAction {

	private static final Log log = Logs.getLog(EmployeeAction.class);
	
	@Inject
	private EmployeeService employeeService;

    @Inject
    private DepartmentService departmentService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/employee_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Employee employee,
                      HttpServletRequest request){

        Cnd c = new ParseObj(employee).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(employeeService.listCount(c));
            request.setAttribute("list_obj", employeeService.queryCache(c,p));
        }else{
            p.setRecordCount(employeeService.count(c));
            request.setAttribute("list_obj", employeeService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("employee", employee);
        request.setAttribute("departmentList", departmentService.queryCache(null,new Page()));


    }

    @At
    @Ok("beetl:/WEB-INF/base/employee.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("employee", null);
        }else{
            request.setAttribute("employee", employeeService.fetch(id));
        }
        request.setAttribute("departmentList", departmentService.queryCache(null,new Page()));
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Employee employee){
        Object rtnObject;
        if (employee.getId() == null || employee.getId() == 0) {
            rtnObject = employeeService.dao().insert(employee);
        }else{
            rtnObject = employeeService.dao().updateIgnoreNull(employee);
        }
        CacheManager.getInstance().getCache(EmployeeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_employee", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  employeeService.delete(id);
        CacheManager.getInstance().getCache(EmployeeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_employee",false);
    }

}
