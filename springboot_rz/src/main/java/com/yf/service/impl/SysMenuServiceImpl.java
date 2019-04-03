package com.yf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.mapper.SysMenuMapper;
import com.yf.pojo.SysMenu;
import com.yf.pojo.SysMenuExample;
import com.yf.service.SysMenuService;
import com.yf.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "sysMenuServiceImpl")
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * pageNum  页码
     * pageSize 每页显示数量
     */
    //PageHelper.startPage(int pageNum,int pageSize);

    /**
     *  offset  起始记录
     *  limit 每页显示数量
     */
    @Override
    /**
     * 分页查询
     */
    public ResultData findByPage(Pager pager) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());

        List<SysMenu> list = sysMenuMapper.selectByExample(null);
        PageInfo info = new PageInfo(list);

        ResultData resultData = new ResultData(info.getTotal(),info.getList());

        return resultData;
    }

    @Override
    /**
     * 分页+条件查询
     */
    public ResultData findByPage(Pager pager, String search) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysMenuExample example = null;
        if (search!=null&&"".equals(search)){
            example = new SysMenuExample();
            SysMenuExample.Criteria criteria = example.createCriteria();
            criteria.andNameLike("%"+search+"%");
        }
        List<SysMenu> list = sysMenuMapper.selectByExample(example);

        PageInfo info = new PageInfo(list);

        ResultData resultData = new ResultData(info.getTotal(),info.getList());

        return resultData;
    }

    @Override
    /**
     * int limit , int offset:
     * String search:条件
     * String sort:排序字段
     * String order:排序方式
     * 分页+条件+排序查询
     */
    public ResultData findByPage(Pager pager, String search, Sorter sorter) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysMenuExample example = new SysMenuExample();

        if (sorter!=null&&StringUtils.isNotEmpty(sorter.getSort())){
            example.setOrderByClause("menu_id"+" "+sorter.getOrder());
        }

        SysMenuExample.Criteria criteria = example.createCriteria();

        if(search!=null&&!"".equals(search)){
            criteria.andNameLike("%"+search+"%");
        }

        List<SysMenu> list = sysMenuMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        ResultData resultData = new ResultData(info.getTotal(),info.getList());

        return resultData;
    }

    @Override
    public R del(List<Long> ids) {
        SysMenuExample example  = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria();
        for (Long id :ids){
            if (id<31){
                return R.error("系统菜单，不能删除！请核对");
            }
        }
        criteria.andMenuIdIn(ids);
        int i = sysMenuMapper.deleteByExample(example);
        if (i>0){
            return R.ok();
        }
        return R.error("删除失败");
    }

//    @Override
//    /**
//     * select * from sys_menu where type!=2
//     */
//    public R selectMenu() {
//        SysMenuExample example = new SysMenuExample();
//        SysMenuExample.Criteria criteria = example.createCriteria();
//        criteria.andTypeNotEqualTo(2);
//        List<SysMenu> list = sysMenuMapper.selectByExample(example);
//        return R.ok().put("menuList",list);
//    }

    @Override
    /**
     * select * from sys_menu where type!=2
     */
    public R selectMenu() {

        List<SysMenu> list = sysMenuMapper.findMenuNotButton();
        //数据库
        SysMenu sysMenu= new SysMenu();
        sysMenu.setMenuId(0l);
        sysMenu.setParentId(-1l);
        sysMenu.setName("一级目录");
        sysMenu.setOrderNum(0);
        list.add(sysMenu);
        return R.ok().put("menuList",list);
    }

    @Override
    public R save(SysMenu sysMenu) {
        int i = sysMenuMapper.insert(sysMenu);
        return i>0?R.ok("新增成功"):R.error("新增失败");
    }

    @Override
    public R findMenu(long menuId) {
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);
        return R.ok().put("menu",sysMenu);
    }

    @Override
    public R updateMenu(SysMenu sysMenu) {
        int i = sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        if(i>0){
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }

    @Override
    public List<String> findPermsByUserId(long userId) {
        //null  或者 "sys:user:list,sys:user:info,sys:user:select"
        List<String> list = sysMenuMapper.findPermsByUserId(userId);
        Set set = new HashSet<String>();
        for (String s : list) {
            if (StringUtils.isNotEmpty(s)){
                String ss[] = s.split(",");
                for (String s1 : ss) {
                    set.add(s1);
                }
            }
        }
        List<String> newList = new ArrayList<>();
        newList.addAll(set);
        return newList;
    }

    /**
     * {menuList:[{
     *     目录信息
     *     list:{
     *         子菜单
     *     }
     * }
     * ,code:0,
     * permissions:[{sys:menu:list},{}]
     * ]}
     * @return
     */
    @Override
    public R findUserMenu() {
        long userId = ShiroUtils.getUserId();
        //目录
        List<SysMenu> dir = sysMenuMapper.findDir(userId);
        for (SysMenu sysMenu : dir) {
            //查询菜单
            List<SysMenu> menuList = sysMenuMapper.findMenu(sysMenu.getMenuId(),userId);
            sysMenu.setList(menuList);
        }
        List<String> permissions = this.findPermsByUserId(userId);
        return R.ok().put("menuList",dir).put("permissions",permissions);
    }
}
