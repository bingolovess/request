package com.bingo.request.exception;

import android.util.Log;

import com.bingo.request.utils.CommonResultHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * API异常类
 */
public class NetRuntimeException extends RuntimeException {

    private static final String TAG = "NetRuntimeException";

    public int code;
    public String message;

    public NetRuntimeException() {
        this(CommonResultHandler.CODE_UNKNOWN_ERROR, "未知错误");
    }

    public NetRuntimeException(Throwable e, String message) {
        super(e);
        this.code = CommonResultHandler.CODE_UNKNOWN_ERROR;
        this.message = message;
    }

    public NetRuntimeException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void printStackTrace() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            printStackTrace(new PrintStream(baos));
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String exceptionInfo = baos.toString();
        Log.e(TAG, exceptionInfo);
    }
}