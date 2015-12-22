package  com.ly.friend.service;

import com.ly.friend.vo.Workexperience;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class WorkexperienceService extends IdEntityService<Workexperience> {

	public static String CACHE_NAME = "workexperience";
    public static String CACHE_COUNT_KEY = "workexperience_count";

    public List<Workexperience> queryCache(Cnd c,Page p)
    {
        List<Workexperience> list_workexperience = null;
        String cacheKey = "workexperience_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_workexperience = this.query(c, p);
            cache.put(new Element(cacheKey, list_workexperience));
        }else{
            list_workexperience = (List<Workexperience>)cache.get(cacheKey).getObjectValue();
        }
        return list_workexperience;
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


