package com.tensquare.backmanager.filrer;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class BackmanagerFilter extends ZuulFilter{

    private JwtUtil jwtUtil;

    /**
     * 在请求前pre或者后post执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序，数字越小，表示越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启，true表示开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作return任何object的值都表示继续执行
     * setsendzuulResponse(false)
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        //得到request上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到request域
        HttpServletRequest request = requestContext.getRequest();
        //得到头信息

        //当时OPTIONS方法时，不需要请求头自动跳过
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        //当方法是登录时，也不需要请求头,getRequestURI得到请求路径
        if(request.getRequestURI().indexOf("login")>0){
            return null;
        }

        String header = request.getHeader("Authorization");
        if(header!=null  &&  "".equals(header)){
           if(header.startsWith("Bearer ")){
               //得到token
               String token = header.substring(7);
               try{
                   Claims claims = jwtUtil.parseJWT(token);
                   String roles = (String) claims.get("roles");
                   //判断是否是admin用户
                   if(roles.equals("admin")){
                       //把头信息转发过去，并且放行
                       requestContext.addZuulRequestHeader("Authorization" , header);
                       return null;
                   }
               }catch( Exception e){
                   e.printStackTrace();
                   requestContext.setSendZuulResponse(false); //终止运行
               }
           }
        }
        requestContext.setSendZuulResponse(false); //终止运行
        return null;
    }
}
