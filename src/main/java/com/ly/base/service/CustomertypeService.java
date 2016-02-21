package  com.ly.base.service;

import com.ly.base.vo.Customertype;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class CustomertypeService extends IdEntityService<Customertype> {

	public static String CACHE_NAME = "customertype";
    public static String CACHE_COUNT_KEY = "customertype_count";

    public List<Customertype> queryCache(Cnd c,Page p)
    {
        List<Customertype> list_customertype = null;
        String cacheKey = "customertype_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_customertype = this.query(c, p);
            cache.put(new Element(cacheKey, list_customertype));
        }else{
            list_customertype = (List<Customertype>)cache.get(cacheKey).getObjectValue();
        }
        return list_customertype;
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


