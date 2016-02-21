package  com.ly.customer.service;

import com.ly.customer.vo.Customer;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class CustomerService extends IdEntityService<Customer> {

	public static String CACHE_NAME = "customer";
    public static String CACHE_COUNT_KEY = "customer_count";

    public List<Customer> queryCache(Cnd c,Page p)
    {
        List<Customer> list_customer = null;
        String cacheKey = "customer_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_customer = this.query(c, p);
            cache.put(new Element(cacheKey, list_customer));
        }else{
            list_customer = (List<Customer>)cache.get(cacheKey).getObjectValue();
        }
        return list_customer;
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


