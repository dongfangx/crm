package  com.ly.friend.service;

import com.ly.friend.vo.Friend;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class FriendService extends IdEntityService<Friend> {

	public static String CACHE_NAME = "friend";
    public static String CACHE_COUNT_KEY = "friend_count";

    public List<Friend> queryCache(Cnd c,Page p)
    {
        List<Friend> list_friend = null;
        String cacheKey = "friend_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_friend = this.query(c, p);
            cache.put(new Element(cacheKey, list_friend));
        }else{
            list_friend = (List<Friend>)cache.get(cacheKey).getObjectValue();
        }
        return list_friend;
    }

    public int listCount(Cnd c)
    {
        Long num = 0L;
        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(CACHE_COUNT_KEY) == null)
        {
            num = Long.valueOf(this.count(c));
            cache.put(new Element(CACHE_COUNT_KEY, num));
        }else{
            num = (Long)cache.get(CACHE_COUNT_KEY).getObjectValue();
        }
        return num.intValue();
    }



}


