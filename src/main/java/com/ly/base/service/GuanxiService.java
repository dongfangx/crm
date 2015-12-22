package  com.ly.base.service;

import com.ly.base.vo.Guanxi;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class GuanxiService extends IdEntityService<Guanxi> {

	public static String CACHE_NAME = "guanxi";
    public static String CACHE_COUNT_KEY = "guanxi_count";

    public List<Guanxi> queryCache(Cnd c,Page p)
    {
        List<Guanxi> list_guanxi = null;
        String cacheKey = "guanxi_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_guanxi = this.query(c, p);
            cache.put(new Element(cacheKey, list_guanxi));
        }else{
            list_guanxi = (List<Guanxi>)cache.get(cacheKey).getObjectValue();
        }
        return list_guanxi;
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


