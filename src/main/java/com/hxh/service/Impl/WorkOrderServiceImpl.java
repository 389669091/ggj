package com.hxh.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.WorkOrder;
import com.hxh.mapper.DetailMapper;
import com.hxh.mapper.TransferMapper;
import com.hxh.mapper.WorkOrderMapper;
import com.hxh.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WorkOrderServiceImpl extends BaseServiceImpl<WorkOrder> implements WorkOrderService {
//注入的对象其实跟当前的BaseServiceImpl的mapper是同一个对象
    @Autowired
WorkOrderMapper workOrderMapper;
    @Autowired
    DetailMapper detailMapper;

    @Autowired
    TransferMapper transferMapper;

    /*
    * 重写通用分页，提供查询del_flag为0的数据
    * */
    @Override
    public PageInfo<WorkOrder> selectPage(int pageNum, int pageSize, Map<String,Object> params) {
        PageHelper.startPage(pageNum,pageSize);//页码索引从1开始

        List<WorkOrder> list = workOrderMapper.selectPage(params);//带条件查询

       //后台处理
        Page page = (Page) list;
        if(page.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);//页码索引从1开始
            list = workOrderMapper.selectPage(params);//带条件查询
        }
        //处理成分页对象
        PageInfo<WorkOrder> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    /**
     * 根据工单id查询其详单信息
     * 1.根据工单id查询工单、用户、公司信息
     * 2.根据工单id查询转运详单
     * 3.根据工单id查询详单信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> selectDetail(long id) {
        Map<String ,Object> workOrder=workOrderMapper.selectById(id);
        List<Map<String ,Object>> details=detailMapper.selectById(id);
        List<Map<String ,Object>> transfers=transferMapper.selectById(id);
        workOrder.put("details",details);
        workOrder.put("transfers",transfers);
        return workOrder;
    }
}
