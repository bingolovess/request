package com.bingo.request.callback;

import java.io.File;

/**
 * Created by bingo on 2019/1/18.
 * Time:2019/1/18
 */

public interface ProgressCallback {
    /**
     * 响应进度更新
     */
    void onProgress(long total, long current);

    /**
     * 完成
     */
    void onResponse(File file);

    /**
     * 失敗
     * @param code
     * @param errorMsg
     */
    void onError(int code, String errorMsg);
}
