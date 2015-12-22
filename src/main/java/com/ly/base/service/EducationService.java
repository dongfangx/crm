package  com.ly.base.service;

import com.ly.base.vo.Education;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class EducationService extends IdEntityService<Education> {

	public static String CACHE_NAME = "education";
    public static String CACHE_COUNT_KEY = "education_count";

    public List<Education> queryCache(Cnd c,Page p)
    {
        List<Education> list_education = null;
        String cacheKey = "education_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_education = this.query(c, p);
            cache.put(new Element(cacheKey, list_education));
        }else{
            list_education = (List<Education>)cache.get(cacheKey).getObjectValue();
        }
        return list_education;
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


