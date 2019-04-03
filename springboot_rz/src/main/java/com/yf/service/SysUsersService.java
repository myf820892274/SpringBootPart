package com.yf.service;

import com.yf.pojo.SysUser;
import com.yf.utils.Pager;
import com.yf.utils.R;
import com.yf.utils.ResultData;
import com.yf.utils.Sorter;

import java.util.List;
import java.util.Map;

public interface SysUsersService {

    public List<SysUser> findAll();
    public R login(SysUser sysUser);
    public SysUser findByUname(String name);
    public ResultData findByPage(Pager pager , String search , Sorter sorter);
    public R findPieData();
    public R findBarData();
    public List<Map<String,Object>> exportExcel();


}
