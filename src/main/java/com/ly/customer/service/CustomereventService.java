package  com.ly.customer.service;

import com.ly.customer.vo.Customerevent;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class CustomereventService extends IdEntityService<Customerevent> {

	public static String CACHE_NAME = "customerevent";
    public static String CACHE_COUNT_KEY = "customerevent_count";

    public List<Customerevent> queryCache(Cnd c,Page p)
    {
        List<Customerevent> list_customerevent = null;
        String cacheKey = "customerevent_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_customerevent = this.query(c, p);
            cache.put(new Element(cacheKey, list_customerevent));
        }else{
            list_customerevent = (List<Customerevent>)cache.get(cacheKey).getObjectValue();
        }
        return list_customerevent;
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


