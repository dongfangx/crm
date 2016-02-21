package  com.ly.customer.service;

import com.ly.customer.vo.Linkcustomer;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class LinkcustomerService extends IdEntityService<Linkcustomer> {

	public static String CACHE_NAME = "linkcustomer";
    public static String CACHE_COUNT_KEY = "linkcustomer_count";

    public List<Linkcustomer> queryCache(Cnd c,Page p)
    {
        List<Linkcustomer> list_linkcustomer = null;
        String cacheKey = "linkcustomer_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_linkcustomer = this.query(c, p);
            cache.put(new Element(cacheKey, list_linkcustomer));
        }else{
            list_linkcustomer = (List<Linkcustomer>)cache.get(cacheKey).getObjectValue();
        }
        return list_linkcustomer;
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


