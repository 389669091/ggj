package com.hxh.service.Impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.AppVersion;
import com.hxh.entity.Statute;
import com.hxh.mapper.StatuteMapper;
import com.hxh.service.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StatuteServiceImpl extends BaseServiceImpl<Statute> implements StatuteService {
//注入的对象其实跟当前的BaseServiceImpl的mapper是同一个对象
    @Autowired
StatuteMapper statuteMapper;


    @Override
    public PageInfo<Statute> select(int pageNum,int pageSize,Statute statute) {
//        PageHelper.startPage(pageNum,pageSize);
//        List<Statute> list = statuteMapper.select(statute);
//        Page page = (Page) list;
//        if(page.getPages()<pageNum){
//            PageHelper.startPage(1,pageSize);//页码索引从1开始
//            list = statuteMapper.select(statute);//带条件查询
//        }
//
//        return new PageInfo<>(list);
        PageHelper.startPage(pageNum,pageSize);
        List<Statute> list=statuteMapper.select(statute);
        Page page=(Page)list;
        if (page.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);
            list=statuteMapper.select(statute);
        }
        return new PageInfo<>(list);
    }


    @Override
    public int insertSelective(Statute statute) {
        statute.setCreateDate(new Date());
        statute.setUpdateDate(new Date());
        statute.setDelFlag("0");
        return mapper.insertSelective(statute);
    }
}
