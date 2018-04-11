package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @RequestMapping(path = {"/reg/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String reg(@RequestParam("username") String userName,
                                   @RequestParam("password") String password,
                                    @RequestParam(value = "rember",defaultValue = "0") int rememberme){

        Map<String,Object> map = userService.register(userName,password);
        if(map.isEmpty()){
            return ToutiaoUtils.getJSONString(0,"注册成功");
        }else {
            return ToutiaoUtils.getJSONString(1,map);
        }

    }



}
