package  com.ly.base.service;

import com.ly.base.vo.Linktype;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class LinktypeService extends IdEntityService<Linktype> {

	public static String CACHE_NAME = "linktype";
    public static String CACHE_COUNT_KEY = "linktype_count";

    public List<Linktype> queryCache(Cnd c,Page p)
    {
        List<Linktype> list_linktype = null;
        String cacheKey = "linktype_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_linktype = this.query(c, p);
            cache.put(new Element(cacheKey, list_linktype));
        }else{
            list_linktype = (List<Linktype>)cache.get(cacheKey).getObjectValue();
        }
        return list_linktype;
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


