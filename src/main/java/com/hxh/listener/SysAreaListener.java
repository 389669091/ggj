package com.hxh.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hxh.entity.SysArea;
import com.hxh.mapper.SysAreaMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth hxh
 * @date 2020/7/30 17:34
 * @Description
 */
public class SysAreaListener extends AnalysisEventListener<SysArea> {

    SysAreaMapper mapper;
    List<SysArea>list=new ArrayList<>();
    public SysAreaListener(SysAreaMapper mapper) {
        this.mapper=mapper;
    }

    public SysAreaListener() {
    }

    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        list.add(sysArea);
        if (list.size()==10){
            mapper.insertBatch(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (list.size()>0){
            mapper.insertBatch(list);
        }
    }
}
