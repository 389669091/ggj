package com.hxh.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

public class ExamineSqlProvider {

    /**
     *
     * @param params   {officeId:xxx,type:xxx,name:xxx}
     * @return
     */
    public String selectPage(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                "    ex.*, " +
                "    su.NAME user_name, " +
                "    so.NAME office_name  " +
                "FROM " +
                "    examine ex, " +
                "    sys_user su, " +
                "    sys_office so  " +
                "WHERE " +
                "    ex.del_flag = 0 ");
        if(params.containsKey("officeId")&& !StringUtils.isEmpty(params.get("officeId"))){
            sb.append("AND so.id = #{officeId} ");
        }
        if(params.containsKey("type")&& !StringUtils.isEmpty(params.get("type"))){
            sb.append("AND ex.type = #{type} ");
        }
        sb.append("AND ex.examine_user_id = su.id " +
                "  AND su.office_id = so.id ");
        if(params.containsKey("name")&& !StringUtils.isEmpty(params.get("name"))){
            sb.append("AND su.NAME LIKE concat( '%', #{name}, '%' ) ");
        }
        return sb.toString();
    }
}



