package com.jhinwins.redis.impl;

import com.jhinwins.domain.RedisConnCfg;
import com.jhinwins.redis.RedisOpts;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by Jhinwins on 2017/9/1  10:38.
 * Desc:
 */
public class RedisOptsImpl implements RedisOpts {

    private Jedis jedis;

    public RedisOptsImpl(RedisConnCfg redisConnCfg) {
        this.jedis = new Jedis(redisConnCfg.getIp(), redisConnCfg.getPort());
        if (redisConnCfg.getPwd() != null && redisConnCfg.getPwd().length() > 0)
            jedis.auth(redisConnCfg.getPwd());
        //切换数据库
        jedis.select(redisConnCfg.getDbindex());
    }

    private void releaseResource() {
        if (this.jedis != null) {
            this.jedis.close();
        }
    }

    /**
     * 测试连接
     */
    @Override
    public boolean testConn() {
        if (jedis != null) {
            try {
                jedis.connect();
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set<String> getKeys(String match) {
        Set<String> keys = jedis.keys(match);
        releaseResource();
        return keys;
    }

    @Override
    public Map<String, String> getKeyTypeByKeys(Set<String> keys) {
        Map<String, String> map = new HashMap<>();

        for (String key : keys) {
            String type = jedis.type(key);
            map.put(key, type);
        }

        releaseResource();
        return map;
    }

    /**
     * 根据key获取string的值
     */
    @Override
    public String getStringValueByKey(String key) {
        String result = jedis.get(key);
        releaseResource();
        return result;
    }

    @Override
    public Map<Integer, String> getListValueByKey(String key, long start, long stop) {
        List<String> lrange = jedis.lrange(key, start, stop);
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < lrange.size(); i++) {
            map.put(i, lrange.get(i));
        }
        releaseResource();
        return map;
    }

    @Override
    public Map<Double, String> getZsetValueByKey(String key) {
        Set<Tuple> result = jedis.zrangeWithScores(key, 0, -1);
        Map<Double, String> map = new HashMap<>();
        for (Tuple tuple : result) {
            map.put(tuple.getScore(), tuple.getElement());
        }
        releaseResource();
        return map;
    }

    @Override
    public Map<Integer, String> getSetValueByKey(String key) {
        Set<String> smembers = jedis.smembers(key);
        Map<Integer, String> map = new HashMap<>();
        Iterator<String> iterator = smembers.iterator();
        for (int i = 0; i < smembers.size(); i++) {
            map.put(i, iterator.next());
        }
        releaseResource();
        return map;
    }

    @Override
    public Map<String, String> getHashValueByKey(String key) {
        Map<String, String> stringStringMap = jedis.hgetAll(key);
        releaseResource();
        return stringStringMap;
    }
}
