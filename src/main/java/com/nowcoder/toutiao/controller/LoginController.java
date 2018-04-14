package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @RequestMapping(path = {"/reg/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String reg(@RequestParam("username") String userName,
                                   @RequestParam("password") String password,
                                    @RequestParam(value = "rember",defaultValue = "0") int rememberme,
                                    HttpServletResponse response){

        Map<String,Object> map = userService.register(userName,password);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/");
            if(rememberme > 0){
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);
            return ToutiaoUtils.getJSONString(0,"注册成功");
        }else {
            return ToutiaoUtils.getJSONString(1,map);
        }
    }
    @RequestMapping(path = {"/login/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String login(@RequestParam("username") String userName,
                        @RequestParam("password") String password,
                        @RequestParam(value = "remember",defaultValue = "0") int rememberme,
                        HttpServletResponse response){
        Map<String,Object> map = userService.login(userName,password);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/");
            if(rememberme > 0){
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);
            return ToutiaoUtils.getJSONString(0,"登陆成功");
        }else {
            return ToutiaoUtils.getJSONString(1,map);
        }
    }

    @RequestMapping(path = {"/logout/"},method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }

}
