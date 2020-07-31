package com.hxh.mapper;

import com.hxh.entity.WorkOrder;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WorkOrderMapper extends Mapper<WorkOrder> {

    //分页查询
    @SelectProvider(type =WorkOrderSqlProvider.class,method = "selectPage")
    List<WorkOrder> selectPage(Map<String, Object> params);

    @Select("SELECT " +
            " wo.*, " +
            " cu.NAME create_user_name, " +
            " cu.phone create_user_phone, " +
            " co.NAME create_office_name, " +
            " tu.NAME transport_user_name, " +
            " tu.phone transport_user_phone, " +
            " `to`.name transport_office_name, " +
            " ru.NAME recipient_user_name, " +
            "  ru.phone recipient_user_phone, " +
            "  ro.name recipient_office_name  " +
            "FROM " +
            " work_order wo " +
            " LEFT JOIN sys_user cu ON wo.create_user_id = cu.id " +
            " LEFT JOIN sys_office co ON cu.office_id = co.id " +
            " LEFT JOIN sys_user tu ON wo.transport_user_id = tu.id " +
            " LEFT JOIN sys_office `to` ON tu.office_id = `to`.id " +
            " LEFT JOIN sys_user ru ON wo.recipient_user_id = ru.id " +
            " LEFT JOIN sys_office ro ON ru.office_id = ro.id  " +
            "WHERE " +
            " wo.del_flag = 0  " +
            " AND cu.del_flag = 0  " +
            " AND co.del_flag = 0  " +
            " and wo.id=#{id}")
    Map<String,Object> selectById(long id);

}