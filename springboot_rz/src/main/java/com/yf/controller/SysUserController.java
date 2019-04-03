package com.yf.controller;


import com.google.code.kaptcha.Producer;
import com.yf.dto.SysUserDTO;
import com.yf.log.Mylog;
import com.yf.pojo.SysUser;
import com.yf.service.SysUsersService;
import com.yf.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;

@RestController
public class SysUserController {

    @Resource
    private SysUsersService sysUsersService;
    @Resource
    private Producer producer;//config包下的KaptchaConfig类定义


    @RequestMapping("/findAll")
    public List<SysUser> findAll(){
        return sysUsersService.findAll();
    }

    @RequestMapping("/sys/login")
    public R login(@RequestBody SysUserDTO sysUser){
        //服务端生成的验证码
        String code = ShiroUtils.getCaptcha();
        //用户输入的验证码
        String c = sysUser.getCaptcha();
        if(code!=null&&!code.equalsIgnoreCase(c)){
            return R.error("验证码错误");
        }
//        传统登录
//        return sysUsersService.login(sysUser);
        String s = null;
        try{
            //1.得到subject
            Subject subject = SecurityUtils.getSubject();
            //密码加密加私盐
            String pwd = sysUser.getPassword();
            Md5Hash md5Hash = new Md5Hash(pwd,sysUser.getUsername(),1024);
            pwd = md5Hash.toString();
            //2.把用户名和密码封装成UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(),pwd);
            if(sysUser.isRememberMe()){
                token.setRememberMe(true);
            }
            //3.登录
            subject.login(token);//跳到自定义Realm的认证方法中执行
            System.out.println(subject.hasRole("管理员"));
            System.out.println(subject.isPermitted("sys:menu:save"));
            return R.ok();
        }catch(Exception e){
            s = e.getMessage();
        }
        return R.error(s);
    }

    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response){
        try{
            String text = producer.createText();//生成的验证码
            System.out.println("验证码为---->"+text);
            ShiroUtils.setAttribute("code",text);
            BufferedImage bufferedImage = producer.createImage(text);
            OutputStream os = response.getOutputStream();
            //把生成的验证码展示到客户端
            ImageIO.write(bufferedImage,"jpg",os);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Mylog(value = "用户列表",description = "分页显示用户")
    @RequiresPermissions("sys:user:list")
    @RequestMapping("/sys/user/list")
    public ResultData findUserByPage(Pager pager, String search, Sorter sorter){
        return sysUsersService.findByPage(pager,search,sorter);
    }

    @Mylog(value = "用户信息",description = "显示用户信息")
    @RequestMapping("/sys/user/info")
    public R userInfo(){
        SysUser user = ShiroUtils.getCurrentUser();
        return R.ok().put("user",user);
    }

    @RequestMapping("/logout")
    public R logout(){
        //清空session
        ShiroUtils.logout();
        return R.ok();
    }
}
