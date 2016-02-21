package  com.ly.customer.service;

import com.ly.customer.vo.Contract;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class ContractService extends IdEntityService<Contract> {

	public static String CACHE_NAME = "contract";
    public static String CACHE_COUNT_KEY = "contract_count";

    public List<Contract> queryCache(Cnd c,Page p)
    {
        List<Contract> list_contract = null;
        String cacheKey = "contract_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_contract = this.query(c, p);
            cache.put(new Element(cacheKey, list_contract));
        }else{
            list_contract = (List<Contract>)cache.get(cacheKey).getObjectValue();
        }
        return list_contract;
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


