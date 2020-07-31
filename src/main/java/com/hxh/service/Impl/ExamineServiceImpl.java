package com.hxh.service.Impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.Examine;
import com.hxh.entity.Qualification;
import com.hxh.entity.QualificationCondition;
import com.hxh.entity.SysUser;
import com.hxh.mapper.ExamineMapper;
import com.hxh.mapper.QualificationMapper;
import com.hxh.mapper.SysUserMapper;
import com.hxh.service.ExamineService;
import com.hxh.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExamineServiceImpl extends BaseServiceImpl<Examine> implements ExamineService {
//注入的对象其实跟当前的BaseServiceImpl的mapper是同一个对象
    @Autowired
QualificationMapper qualificationMapper;


    @Autowired
    ExamineMapper examineMapper;

    @Value("${path}")
    String path;
    /*
    * 重写通用分页，提供查询del_flag为0的数据
    * */
    @Override
    public PageInfo<Examine> selectPage(int pageNum, int pageSize, Map<String,Object> params) {
        PageHelper.startPage(pageNum,pageSize);//页码索引从1开始

        List<Examine> list = examineMapper.selectPage(params);//带条件查询

       /* for (Qualification qualification : qualifications) {
            System.out.println(qualification);
        }*/
       //后台处理
        Page page = (Page) list;
        if(page.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);//页码索引从1开始
            list = examineMapper.selectPage(params);//带条件查询
        }


        //处理成分页对象
        PageInfo<Examine> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


}
