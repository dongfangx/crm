package com.ly.customer.action;

import com.ly.base.service.CustomerlevelService;
import com.ly.base.service.CustomertypeService;
import com.ly.base.service.XinyongService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;


import com.ly.customer.vo.Customer;
import com.ly.customer.service.CustomerService;


@IocBean
@At("/customer")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class CustomerAction {

	private static final Log log = Logs.getLog(CustomerAction.class);
	
	@Inject
	private CustomerService customerService;

    @Inject
    private XinyongService xinyongService;

    @Inject
    private CustomerlevelService customerlevelService;

    @Inject
    private CustomertypeService customertypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/customer/customer_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Customer customer,
                      HttpServletRequest request){

        Cnd c = new ParseObj(customer).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(customerService.listCount(c));
            request.setAttribute("list_obj", customerService.queryCache(c,p));
        }else{
            p.setRecordCount(customerService.count(c));
            request.setAttribute("list_obj", customerService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("customer", customer);
        request.setAttribute("xinyongList",xinyongService.queryCache(null,new Page()));
        request.setAttribute("customerlevelList",customerlevelService.queryCache(null,new Page()));
        request.setAttribute("customertypeList",customertypeService.queryCache(null,new Page()));

    }

    @At
    @Ok("beetl:/WEB-INF/customer/customer.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("customer", null);
        }else{
            request.setAttribute("customer", customerService.fetch(id));
        }
        request.setAttribute("xinyongList",xinyongService.queryCache(null,new Page()));
        request.setAttribute("customerlevelList",customerlevelService.queryCache(null,new Page()));
        request.setAttribute("customertypeList",customertypeService.queryCache(null,new Page()));

    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Customer customer){
        Object rtnObject;
        if (customer.getId() == null || customer.getId() == 0) {
            customer.setAdddate(new Date());
            rtnObject = customerService.dao().insert(customer);
        }else{
            customer.setEditdate(new Date());
            rtnObject = customerService.dao().updateIgnoreNull(customer);
        }
        CacheManager.getInstance().getCache(CustomerService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_customer", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  customerService.delete(id);
        CacheManager.getInstance().getCache(CustomerService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_customer",false);
    }

}
