package com.bingo.request.utils;


import android.content.Context;
import android.text.TextUtils;

import com.bingo.request.BodyResponse;
import com.bingo.request.callback.ExceptionListener;
import com.bingo.request.callback.ResultCallback;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bingo on 2019/1/17.
 * Time:2019/1/17
 * 针对接口请求成功后的通用处理
 */

public class CommonResultHandler {
    /*接口返回成功后的code参考定义*/
    public final static int CODE_SUCCESS = 200;//成功
    public final static int CODE_UNKNOWN_ERROR = -1;//未知错误
    public final static int CODE_FAIL = 400;//失败
    public final static int CODE_UNAUTHORIZED = 401;//未认证（签名错误）
    public final static int CODE_NOT_FOUND = 404;//接口不存在
    public final static int CODE_DATA_NULL = 405;//数据异常
    public final static int CODE_INTERNAL_SERVER_ERROR = 500;//服务器内部错误

    /*用户储存响应的map*/
    private Map<Integer, String> mResponseMap;
    /*全局服务器返回数据异常监听*/
    private ExceptionListener listener;
    private static CommonResultHandler resultHandler;
    private CommonResultHandler() {
        initDefaultConfig();
    }

    public static CommonResultHandler getInstance() {
        if (resultHandler == null) {
            synchronized (CommonResultHandler.class) {
                if (resultHandler == null) {
                    resultHandler = new CommonResultHandler();
                }
            }
        }
        return resultHandler;
    }

    public void addExceptionListener(ExceptionListener listener) {
        this.listener = listener;
    }

    /**
     * 初始化默认配置
     */
    private void initDefaultConfig() {
        mResponseMap = new HashMap<>();
        mResponseMap.put(CODE_SUCCESS, "请求成功！");
        mResponseMap.put(CODE_FAIL, "请求失败！");
        mResponseMap.put(CODE_UNKNOWN_ERROR, "未知错误！");
        mResponseMap.put(CODE_UNAUTHORIZED, "未认证（签名错误）！");
        mResponseMap.put(CODE_NOT_FOUND, "接口不存在！");
        mResponseMap.put(CODE_DATA_NULL, "数据异常！");
        mResponseMap.put(CODE_INTERNAL_SERVER_ERROR, "服务器内部错误！");
    }

    /**
     * 配置map
     *
     * @param map
     */
    public void setResponseConfig(Map<Integer, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();
            mResponseMap.put(key, value);
        }
    }

    /**
     * 更新配置
     *
     * @param key
     * @param value
     */
    public void updateConfig(int key, String value) {
        if (mResponseMap.containsKey(key)) {
            mResponseMap.put(key, value);
        }
    }

   /* public <T> void handle(String json, ResultCallback<T> callback) {
        //这里的BodyResponse可以根据实际项目的后台返回值确定，只作为示范，处理逻辑一致
        BodyResponse bodyResponse = new Gson().fromJson(json, BodyResponse.class);
        if (bodyResponse != null) {
            LogUtils.e("bodyResponse != null  code = " + bodyResponse.code);
            int code = bodyResponse.code;
            switch (code) {
                case CODE_SUCCESS:
                    if (callback.mType == String.class) {
                        callback.onResponse((T) json);
                    } else {
                        T t = new Gson().fromJson(json, callback.mType);
                        callback.onResponse(t);
                    }
                    break;
                case CODE_FAIL:
                case CODE_UNKNOWN_ERROR:
                case CODE_UNAUTHORIZED:
                case CODE_NOT_FOUND:
                case CODE_DATA_NULL:
                case CODE_INTERNAL_SERVER_ERROR:
                default:
                    String errorMsg = TextUtils.isEmpty(bodyResponse.msg) ? mResponseMap.get(code) : bodyResponse.msg;
                    callback.onError(code, errorMsg);
                    if (listener != null) {
                        listener.onError(code,errorMsg);
                    }
                    break;
            }
        } else {
            callback.onError(CODE_DATA_NULL, mResponseMap.get(CODE_DATA_NULL));
            if (listener != null) {
                listener.onError(CODE_DATA_NULL,mResponseMap.get(CODE_DATA_NULL));
            }
        }
    }*/

    /**
     * 这可以使用自定义配置的code
     *
     * @param json
     * @param callback
     * @param <T>
     */
    public <T> void handle(String json, ResultCallback<T> callback) {
        //这里的BodyResponse可以根据实际项目的后台返回值确定，只作为示范，处理逻辑一致
        BodyResponse bodyResponse = new Gson().fromJson(json, BodyResponse.class);
        if (bodyResponse != null) {
            LogUtils.e("bodyResponse != null  code = " + bodyResponse.code);
            int code = bodyResponse.code;
            if (CODE_SUCCESS == code) {
                if (callback.mType == String.class) {
                    callback.onResponse((T) json);
                } else {
                    T t = new Gson().fromJson(json, callback.mType);
                    callback.onResponse(t);
                }
            } else {
                //msg数据为空时，使用我们配置的错误消息
                String errorMsg = TextUtils.isEmpty(bodyResponse.msg) ? mResponseMap.get(code) : bodyResponse.msg;
                callback.onError(code, errorMsg);
                if (listener != null) {
                    listener.onError(code, errorMsg);
                }
            }
        } else {
            callback.onError(CODE_DATA_NULL, mResponseMap.get(CODE_DATA_NULL));
            if (listener != null) {
                listener.onError(CODE_DATA_NULL, mResponseMap.get(CODE_DATA_NULL));
            }
        }
    }
}
