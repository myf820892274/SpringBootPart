package com.yf.log;

import com.alibaba.fastjson.JSON;
import com.yf.pojo.SysLog;
import com.yf.service.SysLogService;
import com.yf.utils.HttpContextUtils;
import com.yf.utils.IPUtils;
import com.yf.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class MyLogAspect {
    //注入service
    @Resource
    private SysLogService sysLogService;

    //@Pointcut(value = "execution(* com.itqf.service.impl.*.*(..))")
    //@Pointcut(value = "execution(* com.itqf.controller.*.*(..))")//描述方法的
    @Pointcut(value = "@annotation(com.yf.log.Mylog)")//描述注解的被MyLog修饰的方法会被增强
    public void myPointcut(){}

    @AfterReturning(pointcut = "myPointcut()")
    public void after(JoinPoint joinPoint){//哪一个类能得到跟目标方法有关的信息?
        System.out.println("后置增强!"+joinPoint.getTarget()+joinPoint.getSignature());
        System.out.println("操作人："+ShiroUtils.getCurrentUser().getUsername());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Mylog mylog = method.getAnnotation(Mylog.class);

        String uname =ShiroUtils.getCurrentUser().getUsername();
        String opration = mylog.value();
        String methodName = joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName();
        String params = JSON.toJSONString(joinPoint.getArgs());
        String ip = IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest());

        SysLog sysLog = new SysLog();
        sysLog.setCreateDate(new Date());
        sysLog.setIp(ip);
        sysLog.setMethod(methodName);
        sysLog.setParams(params);
        sysLog.setUsername(uname);
        sysLog.setOperation(opration);

        int i = sysLogService.saveLog(sysLog);
        System.out.println(i>0?"保存日志成功":"失败");
    }

}
