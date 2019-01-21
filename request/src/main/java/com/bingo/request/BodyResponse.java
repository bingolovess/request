package com.bingo.request;

/**
 * Created by bingo on 2018/12/28.
 * Time:2018/12/28
 * 示例接口响应体，可根据项目实际需要处理
 */

public class BodyResponse<T> {
    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "BodyResponse{" +
                "code='" + code + '\'' +
                ", message='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
