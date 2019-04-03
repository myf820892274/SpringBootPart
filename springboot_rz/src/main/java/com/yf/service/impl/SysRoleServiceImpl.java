package com.yf.service.impl;

import com.yf.mapper.SysRoleMapper;
import com.yf.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sysRoleServiceImpl")
public class SysRoleServiceImpl implements SysRoleService {

    //注入mapper
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<String> findRoleByUserId(long userId) {
        return sysRoleMapper.findRolesByUserId(userId);
    }
}
