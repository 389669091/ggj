package com.hxh.service;



import com.github.pagehelper.PageInfo;
import com.hxh.entity.AppVersion;
import com.hxh.entity.Examine;

import java.util.Map;

public interface ExamineService extends BaseService<Examine> {
    PageInfo<Examine> selectPage(int pageNum, int pageSize, Map<String,Object> params);
}
