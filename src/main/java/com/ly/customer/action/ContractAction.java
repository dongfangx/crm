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


import com.ly.customer.vo.Contract;
import com.ly.customer.service.ContractService;


@IocBean
@At("/contract")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ContractAction {

	private static final Log log = Logs.getLog(ContractAction.class);
	
	@Inject
	private ContractService contractService;

    @At("/")
    @Ok("beetl:/WEB-INF/customer/contract_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Contract contract,
                      @Param("customerid")long customerid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(contract).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(contractService.listCount(c));
            request.setAttribute("list_obj", contractService.queryCache(c,p));
        }else{
            p.setRecordCount(contractService.count(c));
            request.setAttribute("list_obj", contractService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("contract", contract);
        request.setAttribute("customerid",customerid);

    }

    @At
    @Ok("beetl:/WEB-INF/customer/contract.html")
    public void edit(@Param("id")Long id,
                     @Param("action")int action,
                     @Param("customerid")long customerid,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("contract", null);
        }else{
            Contract contract = contractService.fetch(id);
            if (action == 3)
            {
                contract.setId(null);
            }
            request.setAttribute("contract", contract);
        }
        request.setAttribute("customerid",customerid);

    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Contract contract){
        Object rtnObject;
        if (contract.getId() == null || contract.getId() == 0) {
            rtnObject = contractService.dao().insert(contract);
        }else{
            rtnObject = contractService.dao().updateIgnoreNull(contract);
        }
        CacheManager.getInstance().getCache(ContractService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_contract", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  contractService.delete(id);
        CacheManager.getInstance().getCache(ContractService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_contract",false);
    }

}
