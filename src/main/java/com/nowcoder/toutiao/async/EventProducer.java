package com.nowcoder.toutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.toutiao.utils.JedisAdapter;
import com.nowcoder.toutiao.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try {
            String jsonString = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            if(jedisAdapter.lpush(key, jsonString) > 0){
                return true;
            }else {
                LOGGER.error("事件插入失败");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("事件插入错误" + e.getMessage());
            return false;
        }
    }



}
