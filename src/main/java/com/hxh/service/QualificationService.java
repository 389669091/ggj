package com.hxh.service;

import com.github.pagehelper.PageInfo;
import com.hxh.entity.Qualification;
import com.hxh.entity.QualificationCondition;

public interface QualificationService extends  BaseService<Qualification> {


        /*
         * 重写通用分页，提供查询del_flag为0的数据
         * */
        PageInfo<Qualification> selectPage(int pageNum, int pageSize, QualificationCondition qualificationCondition);
        /*
         * 根据上传用户id获取用户的公司id，动态平均出用户保存图片目录
         * path：虚拟路径+公司id
         *
         * */
        String getPath(long uid);
        }
