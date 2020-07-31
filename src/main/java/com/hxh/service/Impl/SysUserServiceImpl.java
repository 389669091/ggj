package com.hxh.service.Impl;

import com.hxh.entity.SysUser;
import com.hxh.mapper.SysUserMapper;
import com.hxh.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
//注入的对象其实跟当前的BaseServiceImpl的mapper是同一个对象
    @Autowired
SysUserMapper sysUserMapper;

}
