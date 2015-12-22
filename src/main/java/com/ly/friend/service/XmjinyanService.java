package  com.ly.friend.service;

import com.ly.friend.vo.Xmjinyan;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class XmjinyanService extends IdEntityService<Xmjinyan> {

	public static String CACHE_NAME = "xmjinyan";
    public static String CACHE_COUNT_KEY = "xmjinyan_count";

    public List<Xmjinyan> queryCache(Cnd c,Page p)
    {
        List<Xmjinyan> list_xmjinyan = null;
        String cacheKey = "xmjinyan_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_xmjinyan = this.query(c, p);
            cache.put(new Element(cacheKey, list_xmjinyan));
        }else{
            list_xmjinyan = (List<Xmjinyan>)cache.get(cacheKey).getObjectValue();
        }
        return list_xmjinyan;
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


