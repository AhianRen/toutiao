package com.nowcoder.toutiao.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObjiect {
    private Map<String,Object> objs = new HashMap<>();
    public void set(String key,Object value){
        objs.put(key,value);
    }
    public Object get(String key){
        return objs.get(key);
    }

}
