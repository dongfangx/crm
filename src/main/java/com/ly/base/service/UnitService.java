package  com.ly.base.service;

import com.ly.base.vo.Unit;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class UnitService extends IdEntityService<Unit> {

	public static String CACHE_NAME = "unit";
    public static String CACHE_COUNT_KEY = "unit_count";

    public List<Unit> queryCache(Cnd c,Page p)
    {
        List<Unit> list_unit = null;
        String cacheKey = "unit_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_unit = this.query(c, p);
            cache.put(new Element(cacheKey, list_unit));
        }else{
            list_unit = (List<Unit>)cache.get(cacheKey).getObjectValue();
        }
        return list_unit;
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


