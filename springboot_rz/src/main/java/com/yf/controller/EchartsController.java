package com.yf.controller;

import com.yf.service.SysUsersService;
import com.yf.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EchartsController {
    @Resource
    private SysUsersService sysUsersService;

    @RequestMapping("/sys/echarts/pie")
    public R pir(){
        return sysUsersService.findPieData();
    }

    @RequestMapping("/sys/echarts/bar")
    public R bar(){
        return sysUsersService.findBarData();
    }

}
