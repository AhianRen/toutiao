package com.nowcoder.toutiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping(path = {"/","/index"})
    @ResponseBody
    public String index(){
        return "hello index";
    }
    @RequestMapping(value = {"/profile/{groudID}/{userID}"})
    @ResponseBody
    public String profile(@PathVariable("groudID") String groudID,
                          @PathVariable("userID") int userID,
                          @RequestParam(value = "type",defaultValue = "0") int type,
                           @RequestParam(value = "key",defaultValue = "defaultKey") String key){
        return String.format("groudID=%s,userID=%d,type=%d,key=%s",groudID,userID,type,key);
    }
    @RequestMapping(value = {"/vm"})
    public String news(Model model){
        model.addAttribute("value","ftl1");
        List<String> colors = Arrays.asList(new String[]{"red","green","blue"});
        Map<String,String> map = new HashMap<String,String>();
        for(int i = 0;i < 4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("colors",colors);
        model.addAttribute("map",map);
   //     model.addAttribute("user",new User("小强"));
        return "news";
    }
}
