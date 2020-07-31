package com.hxh.service.Impl;
import com.hxh.entity.SysOffice;
import com.hxh.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "officeCache")//开启全局缓存注解
public class SysOfficeServiceImpl extends BaseServiceImpl<SysOffice> implements SysOfficeService {
//    @Autowired
//    RedisTemplate<Object,Object> template;


//    @Override
//    public List<SysOffice> select(SysOffice sysOffice) {
//        String key = "com.hxh.service.impl.SysOfficeServiceImpl.select:sysOffice:"+sysOffice;
//        Object obj=template.opsForValue().get(key);
//        List<SysOffice> offices;
//        if (obj!=null){
//            offices= (List<SysOffice>) obj;
//        }else {
//            offices=mapper.select(sysOffice);
//            template.opsForValue().set(key,offices);
//        }
//        return offices;
//    }

    @Cacheable(value = "officeCache",key = "'com.hxh.service.impl.SysOfficeServiceImpl.select:sysOffice:'+#sysOffice")
    @Override
    public List<SysOffice> select(SysOffice sysOffice) {
        return super.select(sysOffice);
    }


    @Cacheable(value = "officeCache",key = "'com.hxh.service.impl.SysOfficeServiceImpl.selectByPrimaryKey:Object:'+#o")
    @Override
    public SysOffice selectByPrimaryKey(Object o) {
        return super.selectByPrimaryKey(o);
    }

    @CachePut(/*cacheNames = "officeCache",*/key = "'cn.nyse.service.impl.SysOfficeServiceImpl.select:object:'+#office.id" )
    @Override
    public SysOffice updateByPrimaryKeySelectiveCache(SysOffice office){
        updateByPrimaryKeySelective(office);
        return office;
    }

    /**
     * @CacheEvict 清空缓存中的数据 allEntries = true  增删改
     * @param sysOffice
     * @return
     */
    @CacheEvict(/*value = "officeCache",*/allEntries = true)
    @Override
    public int updateByPrimaryKeySelective(SysOffice sysOffice) {
        return super.updateByPrimaryKeySelective(sysOffice);
    }
}
