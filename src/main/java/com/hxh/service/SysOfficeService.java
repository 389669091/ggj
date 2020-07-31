package com.hxh.service;

import com.hxh.entity.SysOffice;
import org.springframework.cache.annotation.CachePut;

public interface SysOfficeService  extends BaseService<SysOffice> {
    @CachePut(/*cacheNames = "officeCache",*/key = "'cn.nyse.service.impl.SysOfficeServiceImpl.select:object:'+#office.id" )
    SysOffice updateByPrimaryKeySelectiveCache(SysOffice office);
}
