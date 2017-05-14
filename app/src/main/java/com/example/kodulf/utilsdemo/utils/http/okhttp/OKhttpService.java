package com.example.kodulf.utilsdemo.utils.http.okhttp;

import com.example.kodulf.utilsdemo.entity.City;
import com.example.kodulf.utilsdemo.utils.http.Result;
import com.example.kodulf.utilsdemo.utils.http.ResultList;
import com.example.kodulf.utilsdemo.utils.http.services.HttpStaticPath;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by Kodulf on 2017/5/4.
 */

public class OKhttpService {


    /**
     * phoneno=&cardnum=&key=
     * @param phoneno
     * @param cardnum
     * @param callback
     */
    public void getPhoneCanCharge(String phoneno, String cardnum, final OkHttpResponseCallback<Result<String>> callback){


        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("key","4e7ec8da4685b822748ce472f408ea86");
        parameters.put("phoneno",phoneno);
        parameters.put("cardnum",cardnum);


        try {
            OkHttpUtils.postRequestForResult(HttpStaticPath.TELCHECK, parameters, new OkHttpResponseCallback<Result<String>>() {
                @Override
                public void onFailure(Call call, Exception e) {
                    callback.onFailure(call,e);
                }

                @Override
                public void onResponse(Call call, Result<String> stringResult) {
                    callback.onResponse(call,stringResult);
                }
            },new String());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(null,new Exception(e));
        }
    }

    public void getList(final OkHttpResponseCallback<ResultList<City>> callback){
        HashMap<String,String> values = new HashMap<>();
        values.put("key","8bc68ad662de727f393c3fe5490e17b9");
        try {
            OkHttpUtils.postRequestForResultListT(HttpStaticPath.FLOW_LIST, values, new OkHttpResponseCallback<ResultList<City>>() {
                @Override
                public void onFailure(Call call, Exception e) {
                    callback.onFailure(call,e);
                }

                @Override
                public void onResponse(Call call, ResultList<City> cityResultList) {
                    callback.onResponse(call,cityResultList);
                }

            },new City());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(null,e);
        }
    }
}
