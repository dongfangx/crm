package  com.ly.customer.service;

import com.ly.customer.vo.Sales;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class SalesService extends IdEntityService<Sales> {

	public static String CACHE_NAME = "sales";
    public static String CACHE_COUNT_KEY = "sales_count";

    public List<Sales> queryCache(Cnd c,Page p)
    {
        List<Sales> list_sales = null;
        String cacheKey = "sales_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_sales = this.query(c, p);
            cache.put(new Element(cacheKey, list_sales));
        }else{
            list_sales = (List<Sales>)cache.get(cacheKey).getObjectValue();
        }
        return list_sales;
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


