package com.hxh.service.Impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.Qualification;
import com.hxh.entity.QualificationCondition;
import com.hxh.entity.SysUser;
import com.hxh.mapper.QualificationMapper;
import com.hxh.mapper.SysUserMapper;
import com.hxh.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QualificationServiceImpl extends BaseServiceImpl<Qualification> implements QualificationService {
//注入的对象其实跟当前的BaseServiceImpl的mapper是同一个对象
    @Autowired
QualificationMapper qualificationMapper;


    @Autowired
    SysUserMapper userMapper;

    @Value("${path}")
    String path;
    /*
    * 重写通用分页，提供查询del_flag为0的数据
    * */
    @Override
    public PageInfo<Qualification> selectPage(int pageNum, int pageSize, QualificationCondition qualificationCondition) {
        PageHelper.startPage(pageNum,pageSize);//页码索引从1开始

        List<Qualification> list = qualificationMapper.selectPage(qualificationCondition);//带条件查询

       /* for (Qualification qualification : qualifications) {
            System.out.println(qualification);
        }*/
       //后台处理
        Page page = (Page) list;
        if(page.getPages()<pageNum){
            PageHelper.startPage(1,pageSize);//页码索引从1开始
            list = qualificationMapper.selectPage(qualificationCondition);//带条件查询
        }


        //处理成分页对象
        PageInfo<Qualification> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    /*
     * 根据上传用户id获取用户的公司id，动态平均出用户保存图片目录
     * path：虚拟路径+公司id
     *
     * */
    @Override
    public String getPath(long uid){
        SysUser sysUser = userMapper.selectByPrimaryKey(uid);
        return path+sysUser.getOfficeId();
    }

}
