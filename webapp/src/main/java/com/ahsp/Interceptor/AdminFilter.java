package com.ahsp.Interceptor;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截静态资源
 */
@WebFilter(filterName = "AdminFilter", dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE,
        DispatcherType.ERROR
}, urlPatterns = {"/back/spiderManage/*","/back/dataManage/*","/back/index.html"})
public class AdminFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Cookie[] cookies = request.getCookies();
        String adminCookie = "";
        if(cookies !=null && cookies.length > 0){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("admin")){
                    adminCookie = cookie.getValue();
                }
            }
        }

        if(adminCookie == null || adminCookie.equals("")){
            response.sendRedirect(request.getContextPath() + "/back/admin/login.html");
        }else{
            chain.doFilter(request, resp);
        }
        chain.doFilter(req,resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
