package com.example.kodulf.utilsdemo.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.kodulf.utilsdemo.entity.City;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kodulf on 2017/5/14.
 * FastJson的测试
 */

public class FastJsonTest{

    public static void main(String[] args){

        //{"reason":"允许充值的手机号码及金额","result":null,"error_code":0}
        //{"reason":"success","result":[{"city":"全国","company":"中国联通","companytype":"1","name":"中国联通全国流量套餐","type":"1","flows":[{"id":"34","p":"20M","v":"20","inprice":"2.880"},{"id":"1","p":"50M","v":"50","inprice":"5.760"},{"id":"35","p":"100M","v":"100","inprice":"9.600"},{"id":"2","p":"200M","v":"200","inprice":"14.400"},{"id":"36","p":"500M","v":"500","inprice":"28.800"},{"id":"37","p":"1G","v":"1024","inprice":"48.000"}]},{"city":"全国","company":"中国移动","companytype":"2","name":"中国移动全国流量套餐","type":"1","flows":[{"id":"3","p":"10M","v":"10","inprice":"2.985"},{"id":"4","p":"30M","v":"30","inprice":"4.975"},{"id":"5","p":"70M","v":"70","inprice":"9.950"},{"id":"49","p":"100M","v":"100","inprice":"9.950"},{"id":"6","p":"150M","v":"150","inprice":"19.900"},{"id":"50","p":"300M","v":"300","inprice":"19.900"},{"id":"7","p":"500M","v":"500","inprice":"29.850"},{"id":"26","p":"1G","v":"1024","inprice":"49.750"},{"id":"27","p":"2048M","v":"2048","inprice":"69.650"}]},{"city":"全国","company":"中国电信","companytype":"3","name":"中国电信全国流量套餐","type":"1","flows":[{"id":"8","p":"10M","v":"10","inprice":"1.860"},{"id":"9","p":"30M","v":"30","inprice":"4.650"},{"id":"32","p":"50M","v":"50","inprice":"6.510"},{"id":"10","p":"100M","v":"100","inprice":"9.300"},{"id":"11","p":"200M","v":"200","inprice":"13.950"},{"id":"12","p":"500M","v":"500","inprice":"27.900"},{"id":"28","p":"1G","v":"1024","inprice":"46.500"}]}],"error_code":0}

        parseTestResultString();

        parseTestResultCity();

        parseTestResultListCityList();

        parseTestResultCityList();

        testError();

        testFix();

    }

    public static void parseTestResultString(){

        String jsonResult = "{\"reason\":\"允许充值的手机号码及金额\",\"result\":\"hello world\",\"error_code\":0}";

        Result<String> result = (Result<String>)JSON.parseObject(jsonResult, new TypeReference<Result<String>>(){});

        System.out.println(result.toString());

    }

    public static void parseTestResultCity(){

        String jsonResult = "{\"reason\":\"允许充值的手机号码及金额\",\"result\":{\"city\":\"全国\",\"company\":\"中国联通\",\"companytype\":\"1\",\"name\":\"中国联通全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"34\",\"p\":\"20M\",\"v\":\"20\",\"inprice\":\"2.880\"},{\"id\":\"1\",\"p\":\"50M\",\"v\":\"50\",\"inprice\":\"5.760\"},{\"id\":\"35\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.600\"},{\"id\":\"2\",\"p\":\"200M\",\"v\":\"200\",\"inprice\":\"14.400\"},{\"id\":\"36\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"28.800\"},{\"id\":\"37\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"48.000\"}]},\"error_code\":0}";

        Result<City> result = (Result<City>)JSON.parseObject(jsonResult, new TypeReference<Result<City>>(){});

        System.out.println(result.getResult().getCity());

        ArrayList<City.Flow> flows = result.getResult().getFlows();
        System.out.println(flows.get(0).getP());

    }


    /**
     * 测试使用ResultList<City> 获取citylist
     */
    public static void parseTestResultListCityList(){

        String jsonResultList = "{\"reason\":\"success\",\"result\":[{\"city\":\"全国\",\"company\":\"中国联通\",\"companytype\":\"1\",\"name\":\"中国联通全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"34\",\"p\":\"20M\",\"v\":\"20\",\"inprice\":\"2.880\"},{\"id\":\"1\",\"p\":\"50M\",\"v\":\"50\",\"inprice\":\"5.760\"},{\"id\":\"35\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.600\"},{\"id\":\"2\",\"p\":\"200M\",\"v\":\"200\",\"inprice\":\"14.400\"},{\"id\":\"36\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"28.800\"},{\"id\":\"37\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"48.000\"}]},{\"city\":\"全国\",\"company\":\"中国移动\",\"companytype\":\"2\",\"name\":\"中国移动全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"3\",\"p\":\"10M\",\"v\":\"10\",\"inprice\":\"2.985\"},{\"id\":\"4\",\"p\":\"30M\",\"v\":\"30\",\"inprice\":\"4.975\"},{\"id\":\"5\",\"p\":\"70M\",\"v\":\"70\",\"inprice\":\"9.950\"},{\"id\":\"49\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.950\"},{\"id\":\"6\",\"p\":\"150M\",\"v\":\"150\",\"inprice\":\"19.900\"},{\"id\":\"50\",\"p\":\"300M\",\"v\":\"300\",\"inprice\":\"19.900\"},{\"id\":\"7\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"29.850\"},{\"id\":\"26\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"49.750\"},{\"id\":\"27\",\"p\":\"2048M\",\"v\":\"2048\",\"inprice\":\"69.650\"}]},{\"city\":\"全国\",\"company\":\"中国电信\",\"companytype\":\"3\",\"name\":\"中国电信全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"8\",\"p\":\"10M\",\"v\":\"10\",\"inprice\":\"1.860\"},{\"id\":\"9\",\"p\":\"30M\",\"v\":\"30\",\"inprice\":\"4.650\"},{\"id\":\"32\",\"p\":\"50M\",\"v\":\"50\",\"inprice\":\"6.510\"},{\"id\":\"10\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.300\"},{\"id\":\"11\",\"p\":\"200M\",\"v\":\"200\",\"inprice\":\"13.950\"},{\"id\":\"12\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"27.900\"},{\"id\":\"28\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"46.500\"}]}],\"error_code\":0}";

        ResultList<City> resultList = (ResultList<City>) JSON.parseObject(jsonResultList, new TypeReference<ResultList<City>>(){});

        System.out.println(resultList.toString());

        List<City> result1 = resultList.getResult();
        for (City city : result1) {
            System.out.println(city.getCity());
        }

    }

    /**
     * 测试解析里面是ResultList，json 是CityList的
     */
    public static void parseTestResultCityList(){
        String jsonResultList = "{\"reason\":\"success\",\"result\":[{\"city\":\"全国\",\"company\":\"中国联通\",\"companytype\":\"1\",\"name\":\"中国联通全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"34\",\"p\":\"20M\",\"v\":\"20\",\"inprice\":\"2.880\"},{\"id\":\"1\",\"p\":\"50M\",\"v\":\"50\",\"inprice\":\"5.760\"},{\"id\":\"35\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.600\"},{\"id\":\"2\",\"p\":\"200M\",\"v\":\"200\",\"inprice\":\"14.400\"},{\"id\":\"36\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"28.800\"},{\"id\":\"37\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"48.000\"}]},{\"city\":\"全国\",\"company\":\"中国移动\",\"companytype\":\"2\",\"name\":\"中国移动全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"3\",\"p\":\"10M\",\"v\":\"10\",\"inprice\":\"2.985\"},{\"id\":\"4\",\"p\":\"30M\",\"v\":\"30\",\"inprice\":\"4.975\"},{\"id\":\"5\",\"p\":\"70M\",\"v\":\"70\",\"inprice\":\"9.950\"},{\"id\":\"49\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.950\"},{\"id\":\"6\",\"p\":\"150M\",\"v\":\"150\",\"inprice\":\"19.900\"},{\"id\":\"50\",\"p\":\"300M\",\"v\":\"300\",\"inprice\":\"19.900\"},{\"id\":\"7\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"29.850\"},{\"id\":\"26\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"49.750\"},{\"id\":\"27\",\"p\":\"2048M\",\"v\":\"2048\",\"inprice\":\"69.650\"}]},{\"city\":\"全国\",\"company\":\"中国电信\",\"companytype\":\"3\",\"name\":\"中国电信全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"8\",\"p\":\"10M\",\"v\":\"10\",\"inprice\":\"1.860\"},{\"id\":\"9\",\"p\":\"30M\",\"v\":\"30\",\"inprice\":\"4.650\"},{\"id\":\"32\",\"p\":\"50M\",\"v\":\"50\",\"inprice\":\"6.510\"},{\"id\":\"10\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.300\"},{\"id\":\"11\",\"p\":\"200M\",\"v\":\"200\",\"inprice\":\"13.950\"},{\"id\":\"12\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"27.900\"},{\"id\":\"28\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"46.500\"}]}],\"error_code\":0}";

        Result<List<City>> listResult = JSON.parseObject(jsonResultList, new TypeReference<Result<List<City>>>() {
        });
        System.out.println(listResult.toString());

        List<City> result1 = (List<City>)listResult.getResult();
        for (City city : result1) {
            System.out.println(city.getCity());
        }
    }

    /**
     * 错误的复现
     */
    public static void testError(){
        String jsonResult = "{\"reason\":\"允许充值的手机号码及金额\",\"result\":{\"city\":\"全国\",\"company\":\"中国联通\",\"companytype\":\"1\",\"name\":\"中国联通全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"34\",\"p\":\"20M\",\"v\":\"20\",\"inprice\":\"2.880\"},{\"id\":\"1\",\"p\":\"50M\",\"v\":\"50\",\"inprice\":\"5.760\"},{\"id\":\"35\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.600\"},{\"id\":\"2\",\"p\":\"200M\",\"v\":\"200\",\"inprice\":\"14.400\"},{\"id\":\"36\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"28.800\"},{\"id\":\"37\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"48.000\"}]},\"error_code\":0}";


        Result<City> t=parseTest(jsonResult,new Result<City>(),new City());

        //只要在这里是获取范型里面的东西的时候，例如这里是City,获取city的属性，一定是会报错的
        try {
            System.out.println(t.getResult().getCity());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 测试，这里面 T ret = (T) tResult;应该是导致错误的地方
     * 多级范型，解析的时候报错，
     * 参考一下：http://blog.csdn.net/lanmo555/article/details/52769372
     * 范型的
     * @param jsonString
     * @param t
     * @param k
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T extends Result<K>> T parseTest(String jsonString,T t,final K k){
        Result<K> tResult = JSON.parseObject(jsonString, new TypeReference<Result<K>>() {
        });

        System.out.println(tResult.toString());
        T ret = (T) tResult;
        System.out.println(t.getReason());

        //在500mi的项目里面报错的是因为强制转换的时候不能转换
        //java.lang.ClassCastException: com.alibaba.fastjson.JSONObject cannot be cast to com.wbm.app.business.model.Cashier
        
        return ret;
    }


    /**
     * 测试修复
     */
    public static void testFix(){
        String jsonResult = "{\"reason\":\"允许充值的手机号码及金额\",\"result\":{\"city\":\"全国\",\"company\":\"中国联通\",\"companytype\":\"1\",\"name\":\"中国联通全国流量套餐\",\"type\":\"1\",\"flows\":[{\"id\":\"34\",\"p\":\"20M\",\"v\":\"20\",\"inprice\":\"2.880\"},{\"id\":\"1\",\"p\":\"50M\",\"v\":\"50\",\"inprice\":\"5.760\"},{\"id\":\"35\",\"p\":\"100M\",\"v\":\"100\",\"inprice\":\"9.600\"},{\"id\":\"2\",\"p\":\"200M\",\"v\":\"200\",\"inprice\":\"14.400\"},{\"id\":\"36\",\"p\":\"500M\",\"v\":\"500\",\"inprice\":\"28.800\"},{\"id\":\"37\",\"p\":\"1G\",\"v\":\"1024\",\"inprice\":\"48.000\"}]},\"error_code\":0}";


        Result<City> t=parseTestWithFixMethod(jsonResult,new Result<City>(),new City());

        try {
            System.out.println(t.getResult().getCity());
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static <K, T extends Result<K>> T parseTestWithFixMethod(String jsonString,T t,final K k){
        Result<K> responseObject = JSON.parseObject(jsonString, new TypeReference<Result<K>>() {
        });

        System.out.println(responseObject.toString());
        //修复的方法

                        if (responseObject.getResult() != null) {
                            K valueObject = null;
                            Class<?> targetType= k.getClass();
                            if (targetType == String.class) {
                                valueObject =  (K)responseObject.getResult().toString();
                            }
                            else if ((targetType == Integer.class) || (targetType == Integer.TYPE))
                                valueObject =  (K)Integer.valueOf(responseObject.getResult().toString());
                            else if ((targetType == Byte.class) || (targetType == Byte.TYPE))
                                valueObject =  (K)Byte.valueOf(responseObject.getResult().toString());
                            else if ((targetType == Double.class) || (targetType == Double.TYPE))
                                valueObject =  (K)Double.valueOf(responseObject.getResult().toString());
                            else if ((targetType == Float.class) || (targetType == Float.TYPE))
                                valueObject =  (K)Float.valueOf(responseObject.getResult().toString());
                            else if ((targetType == Long.class) || (targetType == Long.TYPE))
                                valueObject =  (K)Long.valueOf(responseObject.getResult().toString());
                            else if ((targetType == Short.class) || (targetType == Short.TYPE))
                                valueObject =  (K)Short.valueOf(responseObject.getResult().toString());
                            else if (targetType == BigDecimal.class )
                                valueObject =  (K)new BigDecimal(responseObject.getResult().toString());
                            else{
                                valueObject = (K) JSONObject.toJavaObject((JSONObject) responseObject.getResult(), k.getClass());
                            }
                            responseObject.setResult(valueObject);
                        }

        T ret = (T) responseObject;
        System.out.println(t.getReason());

        return ret;
    }





}
