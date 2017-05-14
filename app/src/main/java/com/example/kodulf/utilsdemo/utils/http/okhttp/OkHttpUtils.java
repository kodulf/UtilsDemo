package com.example.kodulf.utilsdemo.utils.http.okhttp;

import com.example.kodulf.utilsdemo.utils.StringUtils;
import com.example.kodulf.utilsdemo.utils.http.HttpHelpUtils;
import com.example.kodulf.utilsdemo.utils.http.RequestCallback;
import com.example.kodulf.utilsdemo.utils.http.Result;
import com.example.kodulf.utilsdemo.utils.http.ResultList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Kodulf on 2017/5/3.
 * 在java 里面json 需要的jar包，http://download.csdn.net/detail/xushouwei/8888205
 * 在android 里面直接使用fastjson就可以了
 */

public class OkHttpUtils {

    //可以不用设置，直接使用默认的
//    public final static int CONNECT_TIMEOUT =10;
//    public final static int READ_TIMEOUT=10;
//    public final static int WRITE_TIMEOUT=10;

    /**
     * 初始化OkHttpClient
     */
    static OkHttpClient client =  new OkHttpClient.Builder()
//            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
//            .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
//            .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
            .build();


    /**
     * post 请求Result<T>
     * @param path
     * @param values
     * @param callback
     * @param <T>
     * @throws IOException
     */
    public static <T> void postRequestForResult(String path, HashMap<String,String> values, final OkHttpResponseCallback<Result<T>> callback,final T t) throws IOException {

        final Result<T> result = new Result<>();
        Call call = initPostCall(path, values);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(client==null){
                    client = new OkHttpClient.Builder().build();
                }
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    String string = response.body().string();
                    System.out.println(string);
                    try {
                        HttpHelpUtils.parseJsonStringToResultGeneric(result,string,t);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(call,e);
                        return;
                    }

                    if(result.isSuccess()){
                        callback.onResponse(call,result);
                    }else{
                        callback.onFailure(call,new IOException("Unexpected code " + response));
                    }

                } else {
                    callback.onFailure(call,new IOException("Unexpected code " + response));
                }

            }
        });

    }




    /**
     * post请求ResultList<T>
     * @param path
     * @param values
     * @param callback
     * @param <T>
     * @throws IOException
     */
    public static <T> void postRequestForResultList(String path, HashMap<String,String> values, final OkHttpResponseCallback<ResultList<T>> callback, final T t) throws IOException {

        final ResultList<T> result = new ResultList<>();

        //添加参数
        Call call = initPostCall(path, values);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(client==null){
                    client = new OkHttpClient.Builder().build();
                }
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    String string = response.body().string();
                    System.out.println(string);

                    try {
                        //HttpHelpUtils.parseJsonStringToResultListNew(result,string);
                        HttpHelpUtils.parseJsonStringToResultListGeneric(result,string,t);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(call,e);
                        return;
                    }

                    if(result.isSuccess()){
                        callback.onResponse(call,result);
                    }else{
                        callback.onFailure(call,new IOException("Unexpected code " + response));
                    }

                } else {
                    callback.onFailure(call,new IOException("Unexpected code " + response));
                }

            }
        });
    }


    /**
     * get 请求Result<T></>
     * @param path
     * @param values
     * @param callback
     * @param <T>
     */
    public static <T> void getRequestForResult(String path, HashMap<String,String> values, final OkHttpResponseCallback<Result<T>> callback,final T t){
        final Result<T> result = new Result<>();

        Call call = initGetCall(path, values);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(client==null){
                    client = new OkHttpClient.Builder().build();
                }
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    String string = response.body().string();

                    try {
                        HttpHelpUtils.parseJsonStringToResultGeneric(result,string,t);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(call,e);
                        return;
                    }

                    if(result.isSuccess()){
                        callback.onResponse(call,result);
                    }else{
                        callback.onFailure(call,new IOException("Unexpected code " + response));
                    }

                } else {
                    callback.onFailure(call,new IOException("Unexpected code " + response));
                }

            }
        });
    }



    /**
     * get 请求ResultList
     * @param path
     * @param values
     * @param callback
     * @param <T>
     */
    public static <T> void getRequestForResultList(String path, HashMap<String,String> values, final OkHttpResponseCallback<ResultList<T>> callback,final T t){
        final ResultList<T> result = new ResultList<>();

        Call call = initGetCall(path, values);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(client==null){
                    client = new OkHttpClient.Builder().build();
                }
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    String string = response.body().string();

                    try {
                        HttpHelpUtils.parseJsonStringToResultListGeneric(result,string,t);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(call,e);
                        return;
                    }

                    if(result.isSuccess()){
                        callback.onResponse(call,result);
                    }else{
                        callback.onFailure(call,new IOException("Unexpected code " + response));
                    }

                } else {
                    callback.onFailure(call,new IOException("Unexpected code " + response));
                }

            }
        });
    }


    /**
     * get 方法下载
     * @param urlString
     * @param values
     * @param returnFile
     * @param requestCallback
     * @param <T>
     * @throws Exception
     */
    private static <T> void getDownload(String urlString, HashMap<String,String> values,final File returnFile, final RequestCallback<Result<T>> requestCallback) throws Exception{
        //urlString = "http://pic.qiushibaike.com/system/pictures/11895/118959315/medium/app118959315.jpg";

        final Result<T> result = new Result();

        Call call = initGetCall(urlString, values);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(client==null){
                    client = new OkHttpClient.Builder().build();
                }
                requestCallback.onFailure(new Result(),e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                long contentLength = body.contentLength();
                if(returnFile.exists()){
                    long length = returnFile.length();
                    if(contentLength==length){

                        System.out.println("该文件已经创建了，并且文件完整");
                        result.setError_code(Result.SUCCESS);
                        requestCallback.onResponse(result);
                        return;
                    }else{
                        System.out.println("该文件已经创建了，但是文件不完整，重新下载");
                    }
                }


                InputStream inputStream =null;
                FileOutputStream fileOutputStream = null;

                try {
                    inputStream = body.byteStream();

                    fileOutputStream= new FileOutputStream(returnFile);
                    byte[] bytes = new byte[1024 * 1024];
                    int length = -1;
                    while ((length = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                }finally{
                    if(fileOutputStream!=null&&inputStream!=null){
                        fileOutputStream.close();
                        inputStream.close();
                        result.setError_code(Result.SUCCESS);
                    }else{
                        result.setError_code(Result.FAIL);
                    }

                    if(result.isSuccess()){
                        requestCallback.onResponse(result);
                    }else{
                        requestCallback.onFailure(result,new Exception("failed"));
                    }
                }
            }
        });

    }


    /**
     * post 请求下载
     * @param urlString
     * @param values
     * @param returnFile
     * @param requestCallback
     * @throws Exception
     */
    private static void postDownload(String urlString, HashMap<String,String> values, final File returnFile, final RequestCallback<Result> requestCallback) throws Exception{
        //urlString = "http://pic.qiushibaike.com/system/pictures/11895/118959315/medium/app118959315.jpg";

        final Result result = new Result();

        //添加参数
        Call call = initPostCall(urlString, values);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    if(client==null){
                        client = new OkHttpClient.Builder().build();
                    }
                    requestCallback.onFailure(new Result(),e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                long contentLength = body.contentLength();
                if(returnFile.exists()){
                    long length = returnFile.length();
                    if(contentLength==length){

                        System.out.println("该文件已经创建了，并且文件完整");
                        result.setError_code(Result.SUCCESS);
                        requestCallback.onResponse(result);
                        return;
                    }else{
                        System.out.println("该文件已经创建了，但是文件不完整，重新下载");
                    }
                }


                InputStream inputStream =null;
                FileOutputStream fileOutputStream = null;

                try {
                    inputStream = body.byteStream();

                    fileOutputStream= new FileOutputStream(returnFile);
                    byte[] bytes = new byte[1024 * 1024];
                    int length = -1;
                    while ((length = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                }finally{
                    if(fileOutputStream!=null&&inputStream!=null){
                        fileOutputStream.close();
                        inputStream.close();
                        result.setError_code(Result.SUCCESS);
                    }else{
                        result.setError_code(Result.FAIL);
                    }

                    if(result.isSuccess()){
                        requestCallback.onResponse(result);
                    }else{
                        requestCallback.onFailure(result,new Exception("failed"));
                    }
                }
            }
        });

    }

    /**
     * 请求里面，初始化post 请求的Call
     * @param path
     * @param values
     * @return
     */
    private static Call initPostCall(String path, HashMap<String, String> values) {
        //添加参数
        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = values.entrySet();
        for (Map.Entry<String,String> entry:entries){
            builder.add(entry.getKey(),entry.getValue());
        }
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url(path)
                .post(body)
                .build();

        if(client==null){
            client = new OkHttpClient.Builder().build();
        }

        return client.newCall(request);
    }


    /**
     * 初始化Get的请求的call
     * @param path
     * @param values
     * @return
     */
    private static Call initGetCall(String path, HashMap<String, String> values) {
        StringBuilder parametersString = HttpHelpUtils.getParametersString(values);

        if(parametersString!=null&& StringUtils.isNotEmpty(parametersString.toString())) {
            path = path + "?" + parametersString.toString();
        }

        Request request = new Request.Builder().url(path).build();

        if(client==null){
            client = new OkHttpClient.Builder().build();
        }
        return client.newCall(request);
    }

}
