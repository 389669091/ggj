package com.hxh.mapper;
import com.hxh.entity.Qualification;
import com.hxh.entity.QualificationCondition;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QualificationMapper extends Mapper<Qualification> {

    //分页查询
    @SelectProvider(type =QualificationSqlProvider.class,method = "selectPage")
    List<Qualification> selectPage(QualificationCondition condition);

}