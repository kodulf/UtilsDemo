package com.example.kodulf.utilsdemo.utils.http.okhttp;

import okhttp3.Call;

/**
 * Created by Kodulf on 2017/5/4.
 */

public interface OkHttpResponseCallback<T> {

    void onFailure(Call call, Exception e);

    void onResponse(Call call, T t);
}
