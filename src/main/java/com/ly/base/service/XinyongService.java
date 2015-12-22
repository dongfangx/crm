package  com.ly.base.service;

import com.ly.base.vo.Xinyong;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class XinyongService extends IdEntityService<Xinyong> {

	public static String CACHE_NAME = "xinyong";
    public static String CACHE_COUNT_KEY = "xinyong_count";

    public List<Xinyong> queryCache(Cnd c,Page p)
    {
        List<Xinyong> list_xinyong = null;
        String cacheKey = "xinyong_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_xinyong = this.query(c, p);
            cache.put(new Element(cacheKey, list_xinyong));
        }else{
            list_xinyong = (List<Xinyong>)cache.get(cacheKey).getObjectValue();
        }
        return list_xinyong;
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


