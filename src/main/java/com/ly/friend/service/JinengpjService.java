package  com.ly.friend.service;

import com.ly.friend.vo.Jinengpj;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class JinengpjService extends IdEntityService<Jinengpj> {

	public static String CACHE_NAME = "jinengpj";
    public static String CACHE_COUNT_KEY = "jinengpj_count";

    public List<Jinengpj> queryCache(Cnd c,Page p)
    {
        List<Jinengpj> list_jinengpj = null;
        String cacheKey = "jinengpj_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_jinengpj = this.query(c, p);
            cache.put(new Element(cacheKey, list_jinengpj));
        }else{
            list_jinengpj = (List<Jinengpj>)cache.get(cacheKey).getObjectValue();
        }
        return list_jinengpj;
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


