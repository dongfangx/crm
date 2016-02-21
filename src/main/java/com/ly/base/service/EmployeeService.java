package  com.ly.base.service;

import com.ly.base.vo.Employee;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class EmployeeService extends IdEntityService<Employee> {

	public static String CACHE_NAME = "employee";
    public static String CACHE_COUNT_KEY = "employee_count";

    public List<Employee> queryCache(Cnd c,Page p)
    {
        List<Employee> list_employee = null;
        String cacheKey = "employee_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_employee = this.query(c, p);
            cache.put(new Element(cacheKey, list_employee));
        }else{
            list_employee = (List<Employee>)cache.get(cacheKey).getObjectValue();
        }
        return list_employee;
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


