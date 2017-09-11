package com.jhinwins.redis;

import com.jhinwins.domain.RedisConnCfg;
import com.jhinwins.redis.impl.RedisOptsImpl;

import java.util.Map;
import java.util.Set;

/**
 * Created by Jhinwins on 2017/9/4  9:19.
 * Desc:
 */
public interface RedisOpts {
    static RedisOptsImpl getJedis(RedisConnCfg redisConnCfg) {
        return new RedisOptsImpl(redisConnCfg);
    }

    boolean testConn();

    Set<String> getKeys(String match);

    Map<String, String> getKeyTypeByKeys(Set<String> keys);

    String getStringValueByKey(String key);

    Map<Integer, String> getListValueByKey(String key, long start, long stop);

    Map<Double, String> getZsetValueByKey(String key);

    Map<Integer, String> getSetValueByKey(String key);

    Map<String, String> getHashValueByKey(String key);
}
