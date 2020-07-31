package com.hxh.service;
import com.github.pagehelper.PageInfo;
import com.hxh.entity.Statute;

public interface StatuteService extends  BaseService<Statute> {


    PageInfo<Statute> select(int pageNum, int pageSize, Statute statute);
}
