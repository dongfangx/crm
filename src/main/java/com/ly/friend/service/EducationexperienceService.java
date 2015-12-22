package  com.ly.friend.service;

import com.ly.friend.vo.Educationexperience;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class EducationexperienceService extends IdEntityService<Educationexperience> {

	public static String CACHE_NAME = "educationexperience";
    public static String CACHE_COUNT_KEY = "educationexperience_count";

    public List<Educationexperience> queryCache(Cnd c,Page p)
    {
        List<Educationexperience> list_educationexperience = null;
        String cacheKey = "educationexperience_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_educationexperience = this.query(c, p);
            cache.put(new Element(cacheKey, list_educationexperience));
        }else{
            list_educationexperience = (List<Educationexperience>)cache.get(cacheKey).getObjectValue();
        }
        return list_educationexperience;
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


