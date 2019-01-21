package com.bingo.request.callback;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by bingo on 2019/1/17.
 * Time:2019/1/17
 * 抽象类，用于请求成功后的回调
 */
public abstract class ResultCallback<T> {
    //这是请求数据的返回类型，包含常见的（Bean，List等）
    public Type mType;

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 通过反射想要的返回类型
     *
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 请求失败的时候
     *
     * @param code    接口服务器正确返回的响应码
     * @param errorMsg
     */
    public abstract void onError(int code, String errorMsg);

    /**
     * @param response
     */
    public abstract void onResponse(T response);
}