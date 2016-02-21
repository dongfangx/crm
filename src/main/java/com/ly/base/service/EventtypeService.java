package  com.ly.base.service;

import com.ly.base.vo.Eventtype;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class EventtypeService extends IdEntityService<Eventtype> {

	public static String CACHE_NAME = "eventtype";
    public static String CACHE_COUNT_KEY = "eventtype_count";

    public List<Eventtype> queryCache(Cnd c,Page p)
    {
        List<Eventtype> list_eventtype = null;
        String cacheKey = "eventtype_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_eventtype = this.query(c, p);
            cache.put(new Element(cacheKey, list_eventtype));
        }else{
            list_eventtype = (List<Eventtype>)cache.get(cacheKey).getObjectValue();
        }
        return list_eventtype;
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


