package com.example.kodulf.utilsdemo.utils.http;

/**
 * Created by zhangyinshan on 2017/5/3.
 *
 * 请求的接口回调
 */
public interface RequestCallback<T> {
    void onFailure(T t, Exception e);
    void onResponse(T t);
}

