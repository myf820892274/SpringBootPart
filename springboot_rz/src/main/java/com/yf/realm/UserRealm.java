package com.yf.realm;

import com.yf.pojo.SysUser;
import com.yf.service.SysMenuService;
import com.yf.service.SysRoleService;
import com.yf.service.SysUsersService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component(value = "userRealm")//衍生出的三个注解用来创建对象：@Repository @Service @Controller
public class UserRealm extends AuthorizingRealm {

    //注入service
    @Resource
    private SysUsersService sysUsersService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysRoleService sysRoleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("---->授权");
        //得到当前登录的用户
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        //根据当前用户id查询角色名
        List<String> roles = sysRoleService.findRoleByUserId(user.getUserId());
        //再查询该用户的权限
        List<String> perms = sysMenuService.findPermsByUserId(user.getUserId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRoles(roles);
        info.addStringPermissions(perms);
        System.out.println("---->授权over！");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("---->认证");
        UsernamePasswordToken u = (UsernamePasswordToken) token;
        String uname = u.getUsername();
        String upass = new String(u.getPassword());

        //调用service层方法
        SysUser user = sysUsersService.findByUname(uname);
        if (user==null){
            throw new UnknownAccountException("账号未知！");
        }
        if (!user.getPassword().equals(upass)){
            throw new IncorrectCredentialsException("密码错误！");
        }
        if (user.getStatus()==0){
            throw new LockedAccountException("账号被冻结！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,upass,getName());

        return info;
    }
}
