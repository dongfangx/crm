package  com.ly.base.service;

import com.ly.base.vo.Party;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class PartyService extends IdEntityService<Party> {

	public static String CACHE_NAME = "party";
    public static String CACHE_COUNT_KEY = "party_count";

    public List<Party> queryCache(Cnd c,Page p)
    {
        List<Party> list_party = null;
        String cacheKey = "party_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_party = this.query(c, p);
            cache.put(new Element(cacheKey, list_party));
        }else{
            list_party = (List<Party>)cache.get(cacheKey).getObjectValue();
        }
        return list_party;
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


