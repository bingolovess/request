package com.bingo.request;

import android.util.Log;

import com.bingo.request.callback.ProgressCallback;
import com.bingo.request.callback.ResultCallback;
import com.bingo.request.engine.HttpEngine;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 网络请求帮助类
 * Created by bingo on 2018/9/7.
 * Time:2018/9/7
 */

public class OkGo implements HttpEngine {

    private HttpEngine mEngine = null;

    public void init(HttpEngine engine) {
        mEngine = engine;
    }

    private static OkGo helper;

    private OkGo() {
    }

    public static OkGo get() {
        if (helper == null) {
            synchronized (OkGo.class) {
                if (helper == null) {
                    helper = new OkGo();
                }
            }
        }
        return helper;
    }

    /**
     * 拼接Url 参数
     *
     * @param url
     * @param map
     * @return String
     */
    public String getUrlLink(String url, Map<String, String> map) {
        if (map == null) return url;
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : map.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(map.get(key), "utf-8")));
                pos++;
            }
            return String.format("%s?%s", url, tempParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 参数拼接处理
     */
    private static String appendParams(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder(url);
        if (stringBuilder.indexOf("?") <= 0) {
            stringBuilder.append("?");
        } else {
            if (!stringBuilder.toString().endsWith("?")) {
                stringBuilder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuilder.append("&" + entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        }
        Log.e("bingo", stringBuilder.toString());
        return stringBuilder.toString();
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void get(String url, Map<String, String> paramsMap, ResultCallback callback) {
        mEngine.get(url, paramsMap, callback);
    }

    @Override
    public void post(String url, Map<String, String> paramsMap, ResultCallback callback) {
        mEngine.post(url, paramsMap, callback);
    }

    @Override
    public void cancle() {
        mEngine.cancle();
    }

    @Override
    public void downLoad(String url, Map<String, String> paramsMap, String fileDir, String fileName, ProgressCallback callback) {
        mEngine.downLoad(url, paramsMap, fileDir, fileName, callback);
    }

    @Override
    public void upload(String url, Map<String, String> paramsMap, String fileName, File file, ResultCallback callback) {
        mEngine.upload(url, paramsMap, fileName, file, callback);
    }

    @Override
    public void uploadMore(String url, Map<String, String> paramsMap, Map<String, Object> files, ResultCallback callback) {
        mEngine.uploadMore(url, paramsMap, files, callback);
    }
}
