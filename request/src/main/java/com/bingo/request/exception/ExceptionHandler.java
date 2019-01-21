package com.bingo.request.exception;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * 异常处理器
 */
public class ExceptionHandler {
    private static ExceptionHandler sInstance;

    public static ExceptionHandler getInstance() {
        if (sInstance == null) {
            synchronized (ExceptionHandler.class) {
                sInstance = new ExceptionHandler();
            }
        }

        return sInstance;
    }

    /**
     * 私有化构造函数
     */
    private ExceptionHandler() {

    }

    /**
     * 处理异常
     *
     * @param e 异常接口
     * @return
     */
    public NetRuntimeException handle(Throwable e) {
        if (e instanceof ConnectException || e instanceof UnknownHostException) {
            return new NetRuntimeException(e, "网络异常，请检查网络");
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            return new NetRuntimeException(e, "网络不畅，请稍后再试！");
        } else if (e instanceof JsonSyntaxException) {
            return new NetRuntimeException(e, "数据解析异常");
        } else {
            return new NetRuntimeException(e, e.getMessage());
        }
    }
}
