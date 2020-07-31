package com.hxh.mapper;

import com.hxh.entity.Examine;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ExamineMapper extends Mapper<Examine>{
    //分页查询
    @SelectProvider(type =ExamineSqlProvider.class,method = "selectPage")
    List<Examine> selectPage(Map<String,Object> params);
}
