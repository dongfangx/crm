package com.ly.friend.action;

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


import com.ly.friend.vo.Contact;
import com.ly.friend.service.ContactService;


@IocBean
@At("/contact")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class ContactAction {

	private static final Log log = Logs.getLog(ContactAction.class);
	
	@Inject
	private ContactService contactService;

    @At("/")
    @Ok("beetl:/WEB-INF/friend/contact_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Contact contact,
                      @Param("friendid")long friendid,
                      HttpServletRequest request){

        Cnd c = new ParseObj(contact).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(contactService.listCount(c));
            request.setAttribute("list_obj", contactService.queryCache(c,p));
        }else{
            p.setRecordCount(contactService.count(c));
            request.setAttribute("list_obj", contactService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("contact", contact);
        request.setAttribute("friendid",friendid);

    }

    @At
    @Ok("beetl:/WEB-INF/friend/contact.html")
    public void edit(@Param("id")Long id,
                     @Param("friendid")long friendid,
                     HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("contact", null);
        }else{
            request.setAttribute("contact", contactService.fetch(id));
        }
        request.setAttribute("friendid",friendid);
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Contact contact){
        Object rtnObject;
        if (contact.getId() == null || contact.getId() == 0) {
            rtnObject = contactService.dao().insert(contact);
        }else{
            rtnObject = contactService.dao().updateIgnoreNull(contact);
        }
        CacheManager.getInstance().getCache(ContactService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_contact", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  contactService.delete(id);
        CacheManager.getInstance().getCache(ContactService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_contact",false);
    }

}
