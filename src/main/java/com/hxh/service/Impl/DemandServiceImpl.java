package com.hxh.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.AppVersion;
import com.hxh.entity.Demand;
import com.hxh.service.DemandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth hxh
 * @date 2020/7/25 15:14
 * @Description
 */
@Service
@Transactional
public class DemandServiceImpl extends BaseServiceImpl<Demand> implements DemandService {

    @Override
    public PageInfo<Demand> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Demand demand = new Demand();
        demand.setDelFlag("0");
        List<Demand> list = mapper.select(demand);//带条件查询

       /* for (AppVersion appVersion : appVersions) {
            System.out.println(appVersion);
        }*/
        //处理成分页对象
        PageInfo<Demand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
