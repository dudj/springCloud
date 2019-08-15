package com.linziyi.user.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param expire
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           Integer expire
    ){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(expire);
        response.addCookie(cookie);
    }
    public static Cookie get(HttpServletRequest request,
                             String name){
        Cookie[] cookies = request.getCookies();
        if(null != cookies){
            for (Cookie cookie: cookies) {
                if(name.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }
}
