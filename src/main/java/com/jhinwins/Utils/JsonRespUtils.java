package com.jhinwins.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Jhinwins on 2017/9/1  11:23.
 * Desc:
 */
public class JsonRespUtils {
    public static final String RESP_STATUS_SUCCESS = "success";
    public static final String RESP_STATUS_FILE = "fail";

    public static JSONObject createSucResp(Object obj, String msg) {
        return createResp(RESP_STATUS_SUCCESS, obj, msg);
    }

    public static JSONObject createSucResp(String msg) {
        return createResp(RESP_STATUS_SUCCESS, null, msg);
    }

    public static JSONObject createSucResp(Object obj) {
        return createResp(RESP_STATUS_SUCCESS, obj, null);
    }

    public static JSONObject createFileResp(Object obj, String msg) {
        return createResp(RESP_STATUS_FILE, obj, msg);
    }

    public static JSONObject createFileResp(String msg) {
        return createResp(RESP_STATUS_FILE, null, msg);
    }

    public static JSONObject createFileResp(Object obj) {
        return createResp(RESP_STATUS_FILE, obj, null);
    }

    public static JSONObject createResp(String status, Object obj, String msg) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            if (obj != null)
                try {
                    jsonObject.put("content", obj instanceof Collection ? obj : obj instanceof Map ? new JSONObject((Map) obj) : new JSONObject(obj));
                } catch (JSONException e) {
                    e.printStackTrace();
                    jsonObject.put("createRespStatus", RESP_STATUS_FILE);
                }
            if (msg != null && msg.length() > 0)
                jsonObject.put("msg", msg);
        } catch (JSONException e) {
            return null;
        }
        return jsonObject;
    }
}
