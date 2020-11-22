package com.atguigu.crowd.filter;

import com.atguigu.crowd.constant.AccessPassResource;
import com.atguigu.crowd.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CrowdAccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        // 这里返回pre 意思是在目标微服务执行前执行过滤
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }
    // 进行特定功能 验证
    @Override
    public boolean shouldFilter() {
        // 获取RequestContext对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 通过RequestContext对象获取当前请求对象(框架底层是借助ThreadLocal从当前线程上获取实现绑定好的Request对象)
        HttpServletRequest request = currentContext.getRequest();
        // 获取ServletPath值
        String servletPath = request.getServletPath();
        System.out.println(servletPath+"123123123456");
        // 根据servletPath判断当前请求是否应该可以直接放行的特定功能
        boolean contains = AccessPassResource.PASS_RESOURCE_SET.contains(servletPath);

        if (contains){
            // 如果当前请求是可以直接放行的特定功能请求则返回false放行

            return false;
        }

        // 工具方法返回true，说明当前请求是静态资源请求，取反为false表示放行不做登陆检查
        // 工具方法返回false，说明当前请求不是静态资源请求，取反为true表示不放行做登陆检查
        return  !AccessPassResource.judegCurrentServletPathWhetherStaticResource(servletPath);
    }

    @Override
    public Object run() throws ZuulException {
        // 获取RequestContext对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 通过RequestContext对象获取当前请求对象(框架底层是借助ThreadLocal从当前线程上获取实现绑定好的Request对象)
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session=request.getSession();
        // 获得登录信息       这个对象需要依赖在pom
        //       这个对象需要依赖在pom
        //       这个对象需要依赖在pom
        //       这个对象需要依赖在pom
        //       这个对象需要依赖在pom
        Object attribute = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        if (attribute == null){
            // 如果为空 则就是没登录
            // 从currentContext对象中获取Response对象
            HttpServletResponse response = currentContext.getResponse();
            // 将提示消息存入到Session  在yml设置了面干透，不设置 就不会将信息带过去
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"请登陆以后再访问!");
            // 重定向到auth-consumer工程中登录页面
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
