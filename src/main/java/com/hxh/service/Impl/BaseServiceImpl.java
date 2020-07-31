package com.hxh.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.AppVersion;
import com.hxh.service.BaseService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 通用实现类
 */
public class BaseServiceImpl<T> implements BaseService<T> {


    //注入mapper层接口实例  每个模块注入的接口实例都不一样
    @Autowired   //泛型注入  spring5以后提供的功能
    Mapper<T> mapper;
    @Override
    public PageInfo<T> selectPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<T> list=mapper.selectAll();
        //处理成分页对象
        PageInfo<T> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
    @Override
    public int deleteByPrimaryKey(Object o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    public int insert(T t) {
       return mapper.insert(t);
    }

    @Override
    public int insertSelective(T t) {
        int result = 0;
        result = mapper.insertSelective(t);
        if(result==0){
            throw new RuntimeException("添加失败...");
        }
        return result;

    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return mapper.existsWithPrimaryKey(o);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }


    @Override
    public T selectByPrimaryKey(Object o) {
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    @Override
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {

        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
            int result = 0;
            result = mapper.updateByPrimaryKeySelective(t);
            if(result==0){
                throw new RuntimeException("更新失败...");
            }
            return result;
        }

    @Override
    public int deleteByExample(Object o) {
        return mapper.deleteByExample(o);
    }

    @Override
    public List<T> selectByExample(Object o) {
        return mapper.selectByExample(o);
    }

    @Override
    public int selectCountByExample(Object o) {
        return mapper.selectCountByExample(o);
    }

    @Override
    public T selectOneByExample(Object o) {
        return mapper.selectOneByExample(o);
    }

    @Override
    public int updateByExample(T t, Object o) {
        return mapper.updateByExample(t,o);
    }

    @Override
    public int updateByExampleSelective(T t, Object o) {
        return mapper.updateByExampleSelective(t,o);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object o, RowBounds rowBounds) {
        return mapper.selectByExampleAndRowBounds(o,rowBounds);
    }

    @Override
    public List<T> selectByRowBounds(T t, RowBounds rowBounds) {
        return mapper.selectByRowBounds(t,rowBounds);
    }
}
