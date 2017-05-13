package com.example.kodulf.utilsdemo.utils.http.services;


import com.example.kodulf.utilsdemo.entity.City;
import com.example.kodulf.utilsdemo.utils.http.HttpUtils;
import com.example.kodulf.utilsdemo.utils.http.RequestCallback;
import com.example.kodulf.utilsdemo.utils.http.Result;
import com.example.kodulf.utilsdemo.utils.http.ResultList;

import java.util.HashMap;

/**
 * Created by zhangyinshan on 2017/5/3.
 */
public class JuHeService {

    /**
     * phoneno=&cardnum=&key=
     * @param phoneno
     * @param cardnum
     * @param callback
     */
    public void getPhoneCanCharge(String phoneno, String cardnum, final RequestCallback<Result<String>> callback){


        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("key","4e7ec8da4685b822748ce472f408ea86");
        parameters.put("phoneno",phoneno);
        parameters.put("cardnum",cardnum);


        try {
            HttpUtils.postRequestForResult(HttpStaticPath.TELCHECK, parameters, new RequestCallback<Result<String>>() {
                @Override
                public void onFailure(Result<String> result, Exception e) {
                    callback.onFailure(result,e);
                }

                @Override
                public void onResponse(Result<String> result) {
                    callback.onResponse(result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(new Result<String>(),new Exception(e));
        }
    }

    public void getList(final RequestCallback<ResultList<City>> callback){
        HashMap<String,String> values = new HashMap<>();
        values.put("key","8bc68ad662de727f393c3fe5490e17b9");
        try {
            HttpUtils.postRequestForResultList(HttpStaticPath.FLOW_LIST, values, new RequestCallback<ResultList<City>>() {
                @Override
                public void onFailure(ResultList<City> resultList, Exception e) {
                    callback.onFailure(resultList,e);
                }

                @Override
                public void onResponse(ResultList<City> resultList) {
                    callback.onResponse(resultList);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(new ResultList<City>(),e);
        }
    }
}
