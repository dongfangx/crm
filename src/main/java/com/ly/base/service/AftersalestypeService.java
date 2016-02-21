package  com.ly.base.service;

import com.ly.base.vo.Aftersalestype;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class AftersalestypeService extends IdEntityService<Aftersalestype> {

	public static String CACHE_NAME = "aftersalestype";
    public static String CACHE_COUNT_KEY = "aftersalestype_count";

    public List<Aftersalestype> queryCache(Cnd c,Page p)
    {
        List<Aftersalestype> list_aftersalestype = null;
        String cacheKey = "aftersalestype_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_aftersalestype = this.query(c, p);
            cache.put(new Element(cacheKey, list_aftersalestype));
        }else{
            list_aftersalestype = (List<Aftersalestype>)cache.get(cacheKey).getObjectValue();
        }
        return list_aftersalestype;
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


