package com.atguigu.crowd.constant;

import java.util.HashSet;
import java.util.Set;

public class AccessPassResource {

    public static final Set<String> PASS_RESOURCE_SET=new HashSet<>();

    static {

        PASS_RESOURCE_SET.add("/");
        PASS_RESOURCE_SET.add("/auth/member/to/reg/page.html");
        PASS_RESOURCE_SET.add("/auth/member/to/login/page");
        PASS_RESOURCE_SET.add("/auth/do/member/layout");
        PASS_RESOURCE_SET.add("/auth/do/member/login");
        PASS_RESOURCE_SET.add("/auth/do/member/register");
        PASS_RESOURCE_SET.add("/auth/member/send/short/message.json");
    }
    public static final Set<String> STATIC_RESOURCE_SET=new HashSet<>();
    static {
        STATIC_RESOURCE_SET.add("bootstrap");
        STATIC_RESOURCE_SET.add("css");
        STATIC_RESOURCE_SET.add("fonts");
        STATIC_RESOURCE_SET.add("img");
        STATIC_RESOURCE_SET.add("jquery");
        STATIC_RESOURCE_SET.add("layer");
        STATIC_RESOURCE_SET.add("srcipt");
        STATIC_RESOURCE_SET.add("ztree");
    }
    // 判断某个servletpath值是否对应一个静态资源
    // 返回值
    public static boolean judegCurrentServletPathWhetherStaticResource(String servletpath){
             if (servletpath == null || servletpath.length() == 0){
                 throw new RuntimeException("字符串无意义！");
             }
        String[] split = servletpath.split("/");
        String firstServlet=split[1];
        return STATIC_RESOURCE_SET.contains(firstServlet);
    }

}
