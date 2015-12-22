package  com.ly.base.service;

import com.ly.base.vo.Gender;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class GenderService extends IdEntityService<Gender> {

	public static String CACHE_NAME = "gender";
    public static String CACHE_COUNT_KEY = "gender_count";

    public List<Gender> queryCache(Cnd c,Page p)
    {
        List<Gender> list_gender = null;
        String cacheKey = "gender_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_gender = this.query(c, p);
            cache.put(new Element(cacheKey, list_gender));
        }else{
            list_gender = (List<Gender>)cache.get(cacheKey).getObjectValue();
        }
        return list_gender;
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


