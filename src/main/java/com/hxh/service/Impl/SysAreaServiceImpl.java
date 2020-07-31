package com.hxh.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.SysArea;
import com.hxh.listener.SysAreaListener;
import com.hxh.mapper.SysAreaMapper;
import com.hxh.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
@Transactional
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea> implements SysAreaService {
    //注入的对象其实跟当前的BaseServiceImpl的mapper是同一个对象
    @Autowired
    SysAreaMapper sysAreaMapper;


    @Override
    public PageInfo<SysArea> selectByPid(int pageNum, int pageSize, long id) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysArea> list = sysAreaMapper.selectByPid(id);
        Page page = (Page) list;
        if (page.getPages() < pageNum) {
            PageHelper.startPage(1, pageSize);
            list = sysAreaMapper.selectByPid(id);
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<SysArea> select(int pageNum, int pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysArea> list = sysAreaMapper.selectByName(name);
        Page page = (Page) list;
        if (page.getPages() < pageNum) {
            PageHelper.startPage(1, pageSize);
            list = sysAreaMapper.selectByName(name);
        }
        return new PageInfo<>(list);
    }

    @Override
    public int updateByPrimaryKeySelective(SysArea sysArea) {
        int result = 0;
        try {
            result = sysAreaMapper.updateByPrimaryKeySelective(sysArea);
            if (!sysArea.getParentIds().equals(sysArea.getOldParentIds())) {
                result = sysAreaMapper.updateParentIds(sysArea);
            }
        } catch (Exception e) {
            throw new RuntimeException("修改失败。。。");
        }
        return result;
    }


    @Override
    public void upload(InputStream inputStream) {
        EasyExcel.read(inputStream,SysArea.class,new  SysAreaListener(sysAreaMapper)).sheet().doRead();
    }
//导出excel表格
    @Override
    public void download(OutputStream outputStream) {
        SysArea sysArea=new SysArea();
        sysArea.setDelFlag("0");
        List<SysArea>list=sysAreaMapper.select(sysArea);
        EasyExcel.write(outputStream,SysArea.class).sheet("区域").doWrite(list);
    }

    @Override
    public int doUpdate(SysArea sysArea) {
        int result=sysAreaMapper.doDelete(sysArea);
        return result;
    }
}
