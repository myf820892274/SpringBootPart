package com.yf.config;

import com.yf.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * 等价于在ssm环境下：applicationContext-shiro.xml
 */

@Configuration//标记这个类是spring的配置文件 也可以用SpringBootConfiguration
public class ShiroConfig {

    @Bean(value = "sessionManager")//<bean class="xxx.XXX">
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //禁止url栏拼接sessionid
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //session默认过期时间是半小时，这里改成一小时
        sessionManager.setGlobalSessionTimeout(1000*60*60);
        //定期清除过期会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;

    }

    @Bean(value = "securityManager")//方法的参数相当于传入spring容器中创建的对象
    public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(userRealm);

        //缓存管理
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        securityManager.setCacheManager(ehCacheManager);

        //cookie
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        Cookie cookie = rememberMeManager.getCookie();
        //默认为一年改为一月
        cookie.setMaxAge(60*60*24*30);
        securityManager.setRememberMeManager(rememberMeManager);


        return securityManager;
    }

    //shiro注解在spring容器中生效
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);//aop cglib
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置登陆页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //设置成功页面
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //设置没有权限跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.html");
        //LinkedHashMap 能保证存取的顺序
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        /**
         *                 /login = anon
         *                 /logout = logout
         *                 /admin/** = perms["sys:user:*"]
         *                 #多个角色同时满足
         *                 /users/** = roles["admin"]
         *                 /menu/**  = user
         *                 #其他请求都需要认证后才能访问
         *                 /** = authc
         */
        map.put("/public/**","anon");//静态js,css放行
        map.put("/json/**","anon");//假数据
        map.put("/captcha.jpg","anon");//验证码
        map.put("/sys/login","anon");
        //map.put("/sys/menu/*","perms[\"sys:menu\"]");
        map.put("/**","user");//选中记住我能访问的资源
        //map.put("/**","authc");//登陆后 才能访问 放最后

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}
