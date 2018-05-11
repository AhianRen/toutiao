package com.nowcoder.toutiao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisAdapter implements InitializingBean{
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool();
    }


    public String get(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            LOGGER.error("redis异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public void set(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            LOGGER.error("redis异常" + e.getMessage());
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public long sadd(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key,value);
        } catch (Exception e) {
            LOGGER.error("redis异常" + e.getMessage());
            e.printStackTrace();
            return -10;
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public long srem(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key,value);
        } catch (Exception e) {
            LOGGER.error("redis异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public long scard(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            LOGGER.error("redis异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public boolean sismember(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key,value);
        } catch (Exception e) {
            LOGGER.error("redis异常" + e.getMessage());
            return false;
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

}