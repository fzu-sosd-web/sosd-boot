package org.sosd.api;

import org.sosd.api.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class IdCardAuth {
    public static String AuthIdCard(String cardNo,String realName) {
        String host = "https://zidv2.market.alicloudapi.com";
        String path = "/idcheck/Post";
        String method = "POST";
        String appcode = "608f262c5b2348ba8f123b7a5075b166";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("cardNo", cardNo);
        bodys.put("realName", realName);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response =  HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            // 获取响应格式：匹配年龄是否成年和是否正确匹配身份证和名字
            return EntityUtils.toString(response.getEntity());
            //获取response的body


            // 错误
            /*{
                "error_code": 0,
                    "reason": "证件格式错误",
                    "result": {
                "realname": "严**",
                        "idcard": "350427************",
                        "isok": false,
                        "IdCardInfor": null
            },
                "sn": "0212184354683651232001593234"
            }*/

            //正确
            /*{
                "error_code": 0,*//*当error_code为0 通过不通过isok值判断*//*
                    "reason": "Success",
                    "result": {
                "realname": "张*", *//*用户传递上来真实姓名脱敏返回*//*
                        "idcard": "3303***********", *//*用户传递上来IdcardNo的脱敏返回*//*
                        "isok": false
                        *//*true：匹配 false：不匹配*//* ,
                        "IdCardInfor": {
                    "province":"浙江省",
                            "city":"杭州市",
                            "district":"xx县",
                            "area": "浙江省杭州市区xx县",
                            "sex": "男",
                            "birthday": "1965-3-10"
                } }}*/
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
