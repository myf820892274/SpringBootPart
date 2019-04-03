package com.yf.service;

import com.yf.pojo.SysMenu;
import com.yf.utils.Pager;
import com.yf.utils.R;
import com.yf.utils.ResultData;
import com.yf.utils.Sorter;

import java.util.List;

public interface SysMenuService {

    public ResultData findByPage(Pager pager);
    public ResultData findByPage(Pager pager, String search);
    public ResultData findByPage(Pager pager, String search, Sorter sorter);
    public R del(List<Long> ids);
    public R selectMenu();
    public R save(SysMenu sysMenu);
    public R findMenu(long menuId);
    public R updateMenu(SysMenu sysMenu);
    public List<String> findPermsByUserId(long userId);
    public R findUserMenu();
}
