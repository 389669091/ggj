package com.hxh.mapper;


import com.hxh.entity.SysArea;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysAreaMapper extends Mapper<SysArea> {

    @Select("SELECT " +
            "sub.*, " +
            "parent.NAME parent_name  " +
            "FROM " +
            "sys_area sub, " +
            "sys_area parent  " +
            "WHERE " +
            "sub.del_flag = '0' " +
            "AND sub.parent_ids LIKE concat( '%,', '${id}', ',%' )  " +
            "AND sub.parent_id = parent.id")
    List<SysArea> selectByPid(long id);

    @SelectProvider(type = SysAreaSqlProvider.class, method = "selectByName")
    List<SysArea> selectByName(String name);

    /*根据父区域的旧parentIds和新parentIds进行更新所有的子区域的parentIds
     * 参数parentArea是父区域对象
     * */
    @Update("UPDATE sys_area " +
            "SET parent_ids = REPLACE ( parent_ids, #{oldParentIds}, #{parentIds} )," +
            "update_date = NOW( )  " +
            "WHERE " +
            "parent_ids LIKE concat( '%,', #{id}, ',%' )")
    int updateParentIds(SysArea parentArea);

    @InsertProvider(type = SysAreaSqlProvider.class, method = "insertBatch")
    int insertBatch(@Param("areas") List<SysArea> areas);

@Update("UPDATE sys_area  " +
        "SET del_flag =#{delFlag} " +
        "WHERE " +
        "parent_ids LIKE concat( '%,',#{id},'%,')"+"or id=#{id}")
    int doDelete(SysArea sysArea);
}