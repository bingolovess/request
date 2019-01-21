package com.bingo.request.callback;

/**
 * Created by bingo on 2019/1/21.
 * Time:2019/1/21
 * 全局服务器返回数据异常监听,方便app 统一处理
 */

public interface ExceptionListener {
    void onError(int code, String errorMsg);
}
