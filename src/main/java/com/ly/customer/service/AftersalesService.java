package  com.ly.customer.service;

import com.ly.customer.vo.Aftersales;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class AftersalesService extends IdEntityService<Aftersales> {

	public static String CACHE_NAME = "aftersales";
    public static String CACHE_COUNT_KEY = "aftersales_count";

    public List<Aftersales> queryCache(Cnd c,Page p)
    {
        List<Aftersales> list_aftersales = null;
        String cacheKey = "aftersales_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_aftersales = this.query(c, p);
            cache.put(new Element(cacheKey, list_aftersales));
        }else{
            list_aftersales = (List<Aftersales>)cache.get(cacheKey).getObjectValue();
        }
        return list_aftersales;
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


