package com.ly.friend.action;

import com.ly.base.service.*;
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


import com.ly.friend.vo.Friend;
import com.ly.friend.service.FriendService;


@IocBean
@At("/friend")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class FriendAction {

	private static final Log log = Logs.getLog(FriendAction.class);
	
	@Inject
	private FriendService friendService;

    @Inject
    private GenderService genderService;

    @Inject
    private EducationService educationService;

    @Inject
    private PartyService partyService;

    @Inject
    private BloodService bloodService;


    @Inject
    private MaritalService maritalService;

    @Inject
    private GuanxiService guanxiService;

    @Inject
    private PinlvService pinlvService;

    @Inject
    private XinyongService xinyongService;


    @At("/")
    @Ok("beetl:/WEB-INF/friend/friend_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Friend friend,
                      HttpServletRequest request){

        Cnd c = new ParseObj(friend).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(friendService.listCount(c));
            request.setAttribute("list_obj", friendService.queryCache(c,p));
        }else{
            p.setRecordCount(friendService.count(c));
            request.setAttribute("list_obj", friendService.query(c,p));
        }

        request.setAttribute("genderList", genderService.queryCache(null, new Page()));
        request.setAttribute("educationList", educationService.queryCache(null, new Page()));
        request.setAttribute("guanxiList",guanxiService.queryCache(null, new Page()));
        request.setAttribute("pinlvList",pinlvService.queryCache(null, new Page()));
        request.setAttribute("xinyongList",xinyongService.queryCache(null, new Page()));


        request.setAttribute("page", p);
        request.setAttribute("friend", friend);
    }

    @At
    @Ok("beetl:/WEB-INF/friend/friend.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("friend", null);
        }else{
            request.setAttribute("friend", friendService.fetch(id));
        }
        request.setAttribute("genderList", genderService.queryCache(null, new Page()));
        request.setAttribute("partyList", partyService.queryCache(null, new Page()));
        request.setAttribute("bloodList", bloodService.queryCache(null, new Page()));
        request.setAttribute("maritalList", maritalService.queryCache(null, new Page()));
        request.setAttribute("guanxiList",guanxiService.queryCache(null, new Page()));
        request.setAttribute("pinlvList",pinlvService.queryCache(null, new Page()));
        request.setAttribute("xinyongList",xinyongService.queryCache(null, new Page()));
        request.setAttribute("educationList", educationService.queryCache(null, new Page()));
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Friend friend){
        Object rtnObject;
        if (friend.getId() == null || friend.getId() == 0) {
            rtnObject = friendService.dao().insert(friend);
        }else{
            rtnObject = friendService.dao().updateIgnoreNull(friend);
        }
        CacheManager.getInstance().getCache(FriendService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_friend", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  friendService.delete(id);
        CacheManager.getInstance().getCache(FriendService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_friend",false);
    }

    @At
    @Ok("beetl:/WEB-INF/friend/friend_lookup.html")
    public void lookup(@Param("..")Page p,
                       @Param("..")Friend friend,
                       HttpServletRequest request){

        Cnd c = new ParseObj(friend).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(friendService.listCount(c));
            request.setAttribute("list_obj", friendService.queryCache(c,p));
        }else{
            p.setRecordCount(friendService.count(c));
            request.setAttribute("list_obj", friendService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("friend", friend);
    }

}
