package com.nowcoder.toutiao.async;

import com.alibaba.fastjson.JSON;
import com.nowcoder.toutiao.utils.JedisAdapter;
import com.nowcoder.toutiao.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType,List<EventHandler>> config = new HashMap<>();
    @Autowired
    JedisAdapter jedisAdapter;


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null){
            for (Map.Entry<String,EventHandler> entry : beans.entrySet()){
                List<EventType> supportType = entry.getValue().getSupportType();
                for (EventType eventType : supportType){
                    if (!config.containsKey(eventType)) {
                        config.put(eventType,new ArrayList<EventHandler>());
                    }
                    config.get(eventType).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0,key );
                    for (String event : events){
                        if (event.equals(key)){
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(event, EventModel.class);
                        if (!config.containsKey(eventModel.getType())){
                            LOGGER.error("无法识别的事件");
                            continue;
                        }
                        for (EventHandler handler : config.get(eventModel.getType())){
                            handler.doHandler(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
