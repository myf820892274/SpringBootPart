package com.yf.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName = "statFilter",urlPatterns = "/*",
            initParams = {
                @WebInitParam(name="exclusions",value = "*.js,*.css,*.jpg,*.png,*.gif,*.bmp,/druid/*")
            })  //web.xml<filter>
public class DruidWebStatFilter extends WebStatFilter {
}
