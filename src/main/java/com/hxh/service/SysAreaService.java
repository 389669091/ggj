package com.hxh.service;

import com.github.pagehelper.PageInfo;
import com.hxh.entity.SysArea;
import java.io.InputStream;
import java.io.OutputStream;


public interface SysAreaService extends BaseService<SysArea> {
    PageInfo<SysArea> select(int pageNum, int pageSize, String name);
    PageInfo<SysArea> selectByPid(int pageNum, int pageSize, long id);
    void upload(InputStream inputStream);
    void download(OutputStream outputStream);
    int doUpdate(SysArea sysArea);
}
