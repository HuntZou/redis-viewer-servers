package com.jhinwins.domain;

import org.springframework.stereotype.Component;

/**
 * Created by Jhinwins on 2017/9/1  10:13.
 * Desc:
 */
@Component
public class RedisConnCfg {

    private Long id;

    private String ip;
    private int port;
    private String pwd;
    private int dbindex = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDbindex() {
        return dbindex;
    }

    public void setDbindex(int dbindex) {
        this.dbindex = dbindex;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
