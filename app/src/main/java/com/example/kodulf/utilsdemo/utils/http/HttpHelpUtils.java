package com.example.kodulf.utilsdemo.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kodulf on 2017/5/4.
 */

public class HttpHelpUtils {

    /**
     * 获取参数的String格式的
     * @param values
     * @return
     */
    public static StringBuilder getParametersString(HashMap<String, String> values) {
        StringBuilder parameters = new StringBuilder();
        Set<Map.Entry<String, String>> entries = values.entrySet();
        for (Map.Entry entry :entries){
            parameters.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        if(parameters.length()>0){
            parameters.deleteCharAt(parameters.length()-1);//删除掉最后的&
        }
        return parameters;
    }


    /**
     * 进行json 解析
     *  在java 里面json 需要的jar包，http://download.csdn.net/detail/xushouwei/8888205
     * 在android 里面直接使用fastjson就可以了
     * @param result
     * @param readString
     * @param <T>
     */
    public static <T> void parseJsonStringToResultList(ResultList<T> result, String readString) throws Exception{

        JSONObject jsonObject = JSON.parseObject(readString);

        String reason = null;
        int error_code = Result.FAIL;
        ArrayList<T> arrayList = new ArrayList<T>();
        try {
            reason = jsonObject.getString("reason");

            error_code= jsonObject.getInteger("error_code");

            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.size(); i++) {
                T t = (T) jsonArray.get(i);
                arrayList.add(t);
            }
        }finally {
            result.setError_code(error_code);
            if(reason!=null)
                result.setReason(reason);
            result.setResult(arrayList);
        }

    }




    /**
     * 进行json 解析
     * 在java 里面json 需要的jar包，http://download.csdn.net/detail/xushouwei/8888205
     * 在android 里面直接使用fastjson就可以了
     * @param result
     * @param readString
     * @param <T>
     */
    public static <T> void parseJsonStringToResult(Result<T> result, String readString) throws Exception{
        JSONObject jsonObject = JSON.parseObject(readString);

        String reason = null;
        int error_code = Result.FAIL;
        T resultT =null;
        try {
            reason = jsonObject.getString("reason");
            error_code= jsonObject.getInteger("error_code");
            resultT = (T) jsonObject.getJSONObject("result");//这里可能有报错
        }finally {
            result.setError_code(error_code);
            if(reason!=null)
                result.setReason(reason);
            if(resultT!=null)
                result.setResult(resultT);
        }

    }

//
//    /**
//     * java 上面下载进行json 解析
//     * @param result
//     * @param readString
//     * @param <T>
//     */
//    private static <T> void parseJsonStringToResultList(ResultList<T> result, String readString) throws Exception{
//        JSONObject jsonObject = JSONObject.fromObject(readString);
//        String reason = null;
//        int error_code = Result.FAIL;
//        ArrayList<T> arrayList = new ArrayList<T>();
//        try {
//            reason = jsonObject.getString("reason");
//            error_code= jsonObject.getInt("error_code");
//
//            JSONArray jsonArray = jsonObject.getJSONArray("result");
//            for (int i = 0; i < jsonArray.size(); i++) {
//                T t = (T) jsonArray.get(i);
//                arrayList.add(t);
//            }
//        }finally {
//            result.setError_code(error_code);
//            if(reason!=null)
//                result.setReason(reason);
//            result.setResult(arrayList);
//        }
//
//    }
//
//
//
//
//    /**
//     * 进行json 解析
//     * @param result
//     * @param readString
//     * @param <T>
//     */
//    private static <T> void parseJsonStringToResult(Result<T> result, String readString) throws Exception{
//        JSONObject jsonObject = JSONObject.fromObject(readString);
//        String reason = null;
//        int error_code = Result.FAIL;
//        T resultT =null;
//        try {
//            reason = jsonObject.getString("reason");
//            error_code= jsonObject.getInt("error_code");
//            resultT = (T) jsonObject.getJSONObject("result");//这里可能有报错
//        }finally {
//            result.setError_code(error_code);
//            if(reason!=null)
//                result.setReason(reason);
//            if(resultT!=null)
//                result.setResult(resultT);
//        }
//
//    }


    /**
     * 500mi的
     */
    public static <K> void parseJsonStringToResult(Result<K> result, String responseStr,K k) throws Exception {


        Result<K> responseObject = JSON.parseObject(responseStr, new TypeReference<Result<K>>() {
        });
        if (responseObject.getResult() != null) {
            K valueObject = null;
            Class<?> targetType = k.getClass();
            if (targetType == String.class) {
                valueObject = (K) responseObject.getResult().toString();
            } else if ((targetType == Integer.class) || (targetType == Integer.TYPE))
                valueObject = (K) Integer.valueOf(responseObject.getResult().toString());
            else if ((targetType == Byte.class) || (targetType == Byte.TYPE))
                valueObject = (K) Byte.valueOf(responseObject.getResult().toString());
            else if ((targetType == Double.class) || (targetType == Double.TYPE))
                valueObject = (K) Double.valueOf(responseObject.getResult().toString());
            else if ((targetType == Float.class) || (targetType == Float.TYPE))
                valueObject = (K) Float.valueOf(responseObject.getResult().toString());
            else if ((targetType == Long.class) || (targetType == Long.TYPE))
                valueObject = (K) Long.valueOf(responseObject.getResult().toString());
            else if ((targetType == Short.class) || (targetType == Short.TYPE))
                valueObject = (K) Short.valueOf(responseObject.getResult().toString());
            else if (targetType == BigDecimal.class)
                valueObject = (K) new BigDecimal(responseObject.getResult().toString());
            else {
                valueObject = (K) JSONObject.toJavaObject((JSONObject) responseObject.getResult(), k.getClass());
            }
            responseObject.setResult(valueObject);
        }
    }

}
