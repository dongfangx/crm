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


import com.ly.base.vo.Unit;
import com.ly.base.service.UnitService;


@IocBean
@At("/unit")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class UnitAction {

	private static final Log log = Logs.getLog(UnitAction.class);
	
	@Inject
	private UnitService unitService;

    @At("/")
    @Ok("beetl:/WEB-INF/base/unit_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Unit unit,
                      HttpServletRequest request){

        Cnd c = new ParseObj(unit).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(unitService.listCount(c));
            request.setAttribute("list_obj", unitService.queryCache(c,p));
        }else{
            p.setRecordCount(unitService.count(c));
            request.setAttribute("list_obj", unitService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("unit", unit);
    }

    @At
    @Ok("beetl:/WEB-INF/base/unit.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("unit", null);
        }else{
            request.setAttribute("unit", unitService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Unit unit){
        Object rtnObject;
        if (unit.getId() == null || unit.getId() == 0) {
            rtnObject = unitService.dao().insert(unit);
        }else{
            rtnObject = unitService.dao().updateIgnoreNull(unit);
        }
        CacheManager.getInstance().getCache(UnitService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_unit", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  unitService.delete(id);
        CacheManager.getInstance().getCache(UnitService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_unit",false);
    }

}
