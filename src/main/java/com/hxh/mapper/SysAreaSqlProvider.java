package com.hxh.mapper;
import com.hxh.entity.SysArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @auth hxh
 * @date 2020/7/29 17:03
 * @Description
 */
public class SysAreaSqlProvider {
    public String selectByName(String name){
      return   new SQL(){{
          SELECT("sub.*,parent.name parent_name");
          FROM("sys_area sub,sys_area parent");
          WHERE("sub.del_flag='0'");
          if (!StringUtils.isEmpty(name)){
              WHERE("sub.name like concat('%',#{name},'%')");
          }
          WHERE("sub.parent_id=parent.id");
        }}.toString();
    }

    public String insertBatch(@Param("areas") List<SysArea> areas){
        return new SQL(){{
            INSERT_INTO("sys_area");
            INTO_COLUMNS("`parent_id`, `parent_ids`, `code`, `name`, `type`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `icon`");
            for (int i = 0; i < areas.size(); i++) {
                INTO_VALUES("#{areas["+i+"].parentId}, #{areas["+i+"].parentIds}, #{areas["+i+"].code}, " +
                        "#{areas["+i+"].name}, #{areas["+i+"].type}, #{areas["+i+"].createBy}, " +
                        "now(), #{areas["+i+"].updateBy}, now(), " +
                        "#{areas["+i+"].remarks}, #{areas["+i+"].delFlag}, #{areas["+i+"].icon}");
                ADD_ROW();//添加一行 要不格式会错误，会将所有循环内容看成一行
            }

        }}.toString();
    }
}
