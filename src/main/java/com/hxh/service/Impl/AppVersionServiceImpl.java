package com.hxh.service.Impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.AppVersion;
import com.hxh.mapper.AppVersionMapper;
import com.hxh.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersion> implements AppVersionService {
    /*
     * 重写通用分页，提供查询del_flag为0的数据
     * */
    @Override
    public PageInfo<AppVersion> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);//页码索引从1开始
        AppVersion appVersion = new AppVersion();
        appVersion.setDelFlag("0");
        List<AppVersion> list = mapper.select(appVersion);//带条件查询

       /* for (AppVersion appVersion : appVersions) {
            System.out.println(appVersion);
        }*/
        //处理成分页对象
        PageInfo<AppVersion> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int insertSelective(AppVersion appVersion) {
        appVersion.setDelFlag("0");
        appVersion.setCreateDate(new Date());
        appVersion.setUpdateDate(new Date());
        return mapper.insertSelective(appVersion);
    }
}
