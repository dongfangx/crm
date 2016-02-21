package  com.ly.base.service;

import com.ly.base.vo.Department;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class DepartmentService extends IdEntityService<Department> {

	public static String CACHE_NAME = "department";
    public static String CACHE_COUNT_KEY = "department_count";

    public List<Department> queryCache(Cnd c,Page p)
    {
        List<Department> list_department = null;
        String cacheKey = "department_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_department = this.query(c, p);
            cache.put(new Element(cacheKey, list_department));
        }else{
            list_department = (List<Department>)cache.get(cacheKey).getObjectValue();
        }
        return list_department;
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


