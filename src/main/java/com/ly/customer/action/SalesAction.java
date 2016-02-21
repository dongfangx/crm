package com.ly.customer.action;

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


import com.ly.customer.vo.Sales;
import com.ly.customer.service.SalesService;


@IocBean
@At("/sales")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class SalesAction {

	private static final Log log = Logs.getLog(SalesAction.class);
	
	@Inject
	private SalesService salesService;

    @At("/")
    @Ok("beetl:/WEB-INF/customer/sales_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Sales sales,
                      @Param("customerid")long customerid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(sales).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(salesService.listCount(c));
            request.setAttribute("list_obj", salesService.queryCache(c,p));
        }else{
            p.setRecordCount(salesService.count(c));
            request.setAttribute("list_obj", salesService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("sales", sales);
        request.setAttribute("customerid",customerid);
    }

    @At
    @Ok("beetl:/WEB-INF/customer/sales.html")
    public void edit(@Param("id")Long id,
                     @Param("action")int action,
                     @Param("customerid")long customerid,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("sales", null);
        }else{
            Sales sales = salesService.fetch(id);
            if (action == 3)
            {
                sales.setId(null);
            }
            request.setAttribute("sales", sales);
        }
        request.setAttribute("customerid",customerid);

    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Sales sales){
        Object rtnObject;
        if (sales.getId() == null || sales.getId() == 0) {
            rtnObject = salesService.dao().insert(sales);
        }else{
            rtnObject = salesService.dao().updateIgnoreNull(sales);
        }
        CacheManager.getInstance().getCache(SalesService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_sales", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  salesService.delete(id);
        CacheManager.getInstance().getCache(SalesService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_sales",false);
    }

}
