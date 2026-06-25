package com.shumei.filter;

import com.shumei.pojo.Admin;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/Madd", "/Mindex","/Mdel"})
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterChain) throws IOException,ServletException {
        System.out.println("拦住了吗");
        // 补上判断管理员是否登录的代码，如果登录，放行，否则，拦截
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session=req.getSession();
        Admin admin=(Admin) session.getAttribute("admin");

        if(admin!=null){
            // 登录过了
            // 放行
            filterChain.doFilter(req, response);
        }else {
            // 没有登陆，存储提示信息，跳转到登录页面
            req.getRequestDispatcher("/adminlogin").forward(req, response);
        }
    }

    @Override
    public void destroy() {
    }
}