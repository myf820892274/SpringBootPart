package com.yf.controller;

import com.yf.log.Mylog;
import com.yf.pojo.SysMenu;
import com.yf.service.SysMenuService;
import com.yf.utils.Pager;
import com.yf.utils.R;
import com.yf.utils.ResultData;
import com.yf.utils.Sorter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @Mylog(value = "查询菜单信息",description = "分页查询并且按照名称查询菜单列表")
    @RequestMapping("/sys/menu/list")
    @RequiresPermissions("sys:menu:list")
    public ResultData menuList(Pager pager, String search , Sorter sorter){
        return sysMenuService.findByPage(pager, search, sorter);
    }

    @Mylog(value = "删除菜单",description = "根据菜单编号删除菜单")
    @RequestMapping("/sys/menu/del")
    @RequiresPermissions("sys:menu:delete")
    public R del(@RequestBody List<Long> ids){
        return  sysMenuService.del(ids);
    }

    /**
     *  查询菜单和目录
     */
    @Mylog(value = "查询菜单和目录",description = "查询菜单和目录")
    @RequestMapping("/sys/menu/select")
    @RequiresPermissions("sys:menu:select")
    public R selectMenu(){
        return sysMenuService.selectMenu();
    }

    @Mylog(value = "新增菜单，目录，按钮",description = "新增菜单，目录，按钮")
    @RequestMapping("/sys/menu/save")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenu sysMenu){
        return sysMenuService.save(sysMenu);
    }

    @Mylog(value = "查询菜单",description = "查询菜单")
    @RequestMapping("/sys/menu/info/{menuId}")
    @RequiresPermissions("sys:menu:select")
    public R findMenu(@PathVariable long menuId){
        return sysMenuService.findMenu(menuId);
    }

    @Mylog(value = "修改菜单",description = "根据菜单编号修改菜单")
    @RequestMapping("/sys/menu/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenu sysMenu){
        return sysMenuService.updateMenu(sysMenu);
    }

    /**
     * 查询当前用户能够方法的菜单  type：0 目录  1  菜单  2  按钮
     * {
     *   "menuList": [{
     *     "menuId": 1,
     *     "parentId": 0,
     *     "parentName": null,
     *     "name": "系统管理",
     *     "url": null,
     *     "perms": null,
     *     "type": 0,
     *     "icon": "fa fa-cog",
     *     "orderNum": 0,
     *     "open": null,
     *     "list": [{
     *       "menuId": 2,
     *       "parentId": 1,
     *       "parentName": null,
     *       "name": "用户管理",
     *       "url": "sys/user.html",
     *       "perms": null,
     *       "type": 1,
     *       "icon": "fa fa-user",
     *       "orderNum": 1,
     *       "open": null,
     *       "list": null
     *     }],
     *   "code": 0,
     *   "permissions": ["sys:schedule:info", "sys:menu:update", "sys:menu:delete", "sys:config:info", "sys:generator:list", "sys:menu:list", "sys:config:save", "sys:menu:perms", "sys:config:update", "sys:schedule:resume", "sys:user:delete", "sys:config:list", "sys:user:update", "sys:role:list", "sys:menu:info", "sys:menu:select", "sys:schedule:update", "sys:schedule:save", "sys:role:select", "sys:user:list", "sys:menu:save", "sys:role:save", "sys:schedule:log", "sys:role:info", "sys:schedule:delete", "sys:role:update", "sys:schedule:list", "sys:user:info", "sys:generator:code", "sys:schedule:run", "sys:config:delete", "sys:role:delete", "sys:user:save", "sys:schedule:pause", "sys:log:list"]
     * }
     * @return
     */
    @Mylog(value = "查询用户能访问的菜单",description = "根据菜单编号查询用户能访问的菜单等信息 ")
    @RequestMapping("/sys/menu/user")
    public R menuUser(){
        return sysMenuService.findUserMenu();
    }


}