package com.bingo.request.engine;

import com.bingo.request.callback.ProgressCallback;
import com.bingo.request.callback.ResultCallback;

import java.io.File;
import java.util.Map;

/**
 * 网络引擎的规范
 */
public interface HttpEngine {

    /**
     * Get请求
     *
     * @param url       请求地址
     * @param paramsMap 请求参数
     * @param callback  请求回调
     */
    void get(String url, Map<String, String> paramsMap, ResultCallback callback);

    /**
     * Post请求
     *
     * @param url       请求地址
     * @param paramsMap 请求参数
     * @param callback  请求回调
     */
    void post(String url, Map<String, String> paramsMap, ResultCallback callback);

    /**
     * 取消请求
     */
    void cancle();

    /**
     * 下载文件请求
     *
     * @param url
     * @param paramsMap
     * @param fileDir
     * @param fileName
     * @param callback
     */
    void downLoad(String url, Map<String, String> paramsMap, String fileDir, String fileName, ProgressCallback callback);

    /**
     * 上传请求
     *
     * @param url
     * @param paramsMap
     * @param fileName
     * @param file
     * @param callback
     */
    void upload(String url, Map<String, String> paramsMap, String fileName, File file, ResultCallback callback);

    /**
     * 多文件上传请求
     *
     * @param url
     * @param paramsMap 请求参数
     * @param files
     * @param callback
     */
    void uploadMore(String url, Map<String, String> paramsMap, Map<String, Object> files, ResultCallback callback);
    //TODO https添加安全证书

}