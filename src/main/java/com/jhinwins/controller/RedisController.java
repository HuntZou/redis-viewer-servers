package com.jhinwins.controller;

import com.jhinwins.Utils.JsonRespUtils;
import com.jhinwins.domain.RedisConnCfg;
import com.jhinwins.redis.RedisOpts;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jhinwins on 2017/9/1  10:05.
 * Desc:
 */
@RestController
public class RedisController {

    /**
     * 测试redis连接
     */
    @PostMapping("testConnection")
    public String testConnection(RedisConnCfg redisConnCfg) {
        System.out.println(redisConnCfg.getIp() + ":" + redisConnCfg.getPort());
        try {
            if (RedisOpts.getJedis(redisConnCfg).testConn()) {
                return JsonRespUtils.createSucResp(redisConnCfg).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonRespUtils.createFileResp(redisConnCfg).toString();
        }
        return JsonRespUtils.createFileResp(redisConnCfg).toString();
    }


    @PostMapping("getKeys")
    public String getKeys(RedisConnCfg redisConnCfg, @RequestParam(value = "matching", defaultValue = "*") String m) {
        Set<String> keys = RedisOpts.getJedis(redisConnCfg).getKeys(m);
        Map<String, String> keysType = RedisOpts.getJedis(redisConnCfg).getKeyTypeByKeys(keys);
        return JsonRespUtils.createSucResp(keysType).toString();
    }

    @PostMapping("getStringValueByKey")
    public String getStringValueByKey(RedisConnCfg redisConnCfg, @RequestParam(value = "key") String key) {
        String stringValue = RedisOpts.getJedis(redisConnCfg).getStringValueByKey(key);
        Map<String, String> map = new HashMap<>();
        map.put(key, stringValue);
        return JsonRespUtils.createSucResp(map).toString();
    }

    @PostMapping("getListValueByKey")
    public String getListValueByKey(RedisConnCfg redisConnCfg, @RequestParam(value = "key") String key, @RequestParam(value = "start", defaultValue = "0", required = false) long start, @RequestParam(value = "start", defaultValue = "50", required = false) long end) {
        Map<Integer, String> listValue = RedisOpts.getJedis(redisConnCfg).getListValueByKey(key, start, end);
        return JsonRespUtils.createSucResp(listValue).toString();
    }

    @PostMapping("getSetValueByKey")
    public String getSetValueByKey(RedisConnCfg redisConnCfg, @RequestParam(value = "key") String key) {
        Map<Integer, String> setValue = RedisOpts.getJedis(redisConnCfg).getSetValueByKey(key);
        return JsonRespUtils.createSucResp(setValue).toString();
    }

    @PostMapping("getZsetValueByKey")
    public String getZsetValueByKey(RedisConnCfg redisConnCfg, @RequestParam(value = "key") String key, @RequestParam(value = "start", defaultValue = "0", required = false) int cursor) {
        Map<Double, String> zsetValue = RedisOpts.getJedis(redisConnCfg).getZsetValueByKey(key, cursor);
        return JsonRespUtils.createSucResp(zsetValue).toString();
    }

    @PostMapping("getHashValueByKey")
    public String getHashValueByKey(RedisConnCfg redisConnCfg, @RequestParam(value = "key") String key) {
        Map<String, String> hashValue = RedisOpts.getJedis(redisConnCfg).getHashValueByKey(key);
        return JsonRespUtils.createSucResp(hashValue).toString();
    }

    @RequestMapping(value = "test")
    public String test() {
        return "bingo";
    }
}
