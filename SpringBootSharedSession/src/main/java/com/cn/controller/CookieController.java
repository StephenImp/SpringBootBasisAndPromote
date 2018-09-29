package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by admin on 2018/6/26.
 * <p>
 * <p>
 * 结果：
 * <p>
 * 不存在session，设置browser=chrome
 * JSESSIONID : 5918A6B930467926ECE4092FC693CEE1  --- 这个JSESSIONID是上一次会话存在浏览器中的JSESSIONID
 * 存在session，browser=chrome
 * JSESSIONID : 8EBDF41E3C3CFCCD1194871D37F6316B  ---新的会话往session中存值时，自动保存的JSESSIONID
 * 存在session，browser=chrome
 * JSESSIONID : 8EBDF41E3C3CFCCD1194871D37F6316B  ---同一个会话，JSESSIONID不变
 * <p>
 * 稍微解读下这个现象，可以验证一些结论。
 * 当服务端往session中保存一些数据时，
 * Response中自动添加了一个Cookie：JSESSIONID：xxxx,
 * 再后续的请求中，浏览器也是自动的带上了这个Cookie，
 * 服务端根据Cookie中的JSESSIONID取到了对应的session。
 * 这验证了一开始的说法，客户端服务端是通过JSESSIONID进行交互的，
 * 并且，添加和携带key为JSESSIONID的Cookie都是tomcat和浏览器自动帮助我们完成的，这很关键。
 * <p>
 * Jsessionid只是tomcat的对sessionid的叫法，其实就是sessionid；在其它的容器也许就不叫jsessionid了。
 */
@Controller
public class CookieController {

    @RequestMapping("/test/cookie/{token}")
    @ResponseBody
    public String cookie(@PathVariable("token") String token, HttpServletRequest request, HttpSession session) {

        System.out.println(token);
        //取出session中的token
        //这里传进来的每一个不同的token代表的就是不同的用户
        //session.getAttribute(token) 这里是向 存在 reids 中的session 的键值为token的键 去 取值
        //普通的session，重启服务，第一次请求，session应该不存在了，但这里的session是存在的，应该就是从redis中取的
        Object sessionToken = session.getAttribute(token);
        if (sessionToken == null) {
            System.out.println("不存在session，设置token=" + token);
            session.setAttribute(token, token);
        } else {
            System.out.println("存在session，token=" + sessionToken.toString());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
            }
        }
        return "index";
    }


}