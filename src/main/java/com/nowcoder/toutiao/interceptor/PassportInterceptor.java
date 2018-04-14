package com.nowcoder.toutiao.interceptor;

import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.HostHold;
import com.nowcoder.toutiao.model.LoginTicket;
import com.nowcoder.toutiao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor{

    @Autowired
    private LoginTicketDAO loginTicketDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private HostHold hostHold;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()){
                if ("ticket".equals(cookie.getName())){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket != null){
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if (loginTicket != null && loginTicket.getExpired().after(new Date()) && loginTicket.getStatus() == 0){
                User user = userDAO.selectUserById(loginTicket.getUserId());
                hostHold.setUsers(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
       if (modelAndView != null && hostHold.getUser() != null){
           modelAndView.addObject(hostHold.getUser());
       }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        hostHold.clear();
    }
}