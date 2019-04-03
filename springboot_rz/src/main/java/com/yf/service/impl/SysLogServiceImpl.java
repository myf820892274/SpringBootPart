package com.yf.service.impl;

import com.yf.mapper.SysLogMapper;
import com.yf.pojo.SysLog;
import com.yf.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogServiceImpl implements SysLogService {

    //注入mapper
    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public int saveLog(SysLog sysLog) {
        return sysLogMapper.insert(sysLog);
    }
}
