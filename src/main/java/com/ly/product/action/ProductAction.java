package com.ly.product.action;

import com.ly.base.service.UnitService;
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


import com.ly.product.vo.Product;
import com.ly.product.service.ProductService;


@IocBean
@At("/product")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ProductAction {

	private static final Log log = Logs.getLog(ProductAction.class);
	
	@Inject
	private ProductService productService;

    @Inject
    private UnitService unitService;

    @At("/")
    @Ok("beetl:/WEB-INF/product/product_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Product product,
                      HttpServletRequest request){

        Cnd c = new ParseObj(product).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(productService.listCount(c));
            request.setAttribute("list_obj", productService.queryCache(c,p));
        }else{
            p.setRecordCount(productService.count(c));
            request.setAttribute("list_obj", productService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("product", product);
        request.setAttribute("unitList",unitService.queryCache(null,new Page()));
    }

    @At
    @Ok("beetl:/WEB-INF/product/product.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("product", null);
        }else{
            request.setAttribute("product", productService.fetch(id));
        }
        request.setAttribute("unitList",unitService.queryCache(null,new Page()));
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Product product){
        Object rtnObject;
        if (product.getId() == null || product.getId() == 0) {
            product.setAdddate(new Date());
            rtnObject = productService.dao().insert(product);
        }else{
            rtnObject = productService.dao().updateIgnoreNull(product);
        }
        CacheManager.getInstance().getCache(ProductService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_product", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  productService.delete(id);
        CacheManager.getInstance().getCache(ProductService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_product",false);
    }

}
