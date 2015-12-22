package  com.ly.base.service;

import com.ly.base.vo.Marital;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class MaritalService extends IdEntityService<Marital> {

	public static String CACHE_NAME = "marital";
    public static String CACHE_COUNT_KEY = "marital_count";

    public List<Marital> queryCache(Cnd c,Page p)
    {
        List<Marital> list_marital = null;
        String cacheKey = "marital_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_marital = this.query(c, p);
            cache.put(new Element(cacheKey, list_marital));
        }else{
            list_marital = (List<Marital>)cache.get(cacheKey).getObjectValue();
        }
        return list_marital;
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


