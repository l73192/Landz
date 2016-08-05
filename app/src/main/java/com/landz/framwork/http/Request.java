package com.landz.framwork.http;

import java.util.Map;

/**
 * 作者/日期: admin on 2016/8/2.
 * 描述:请求对象
 */
public class Request {
    private String url;//请求地址
    private int REQUEST_TIME_OUT = 10000;//请求超时时间
    private Method method;//请求方式 get,post
    private Map<String,String> params;//传入参数

    public Request(String url, Method method, Map<String, String> params) {
        this.url = url;
        this.method = method;
        this.params = params;
    }

    public Request(String url, int REQUEST_TIME_OUT, Method method, Map<String, String> params) {
        this.url = url;
        this.REQUEST_TIME_OUT = REQUEST_TIME_OUT;
        this.method = method;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public int getREQUEST_TIME_OUT() {
        return REQUEST_TIME_OUT;
    }

    public Method getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    /**
     * 请求方式get，post(get 接口读取数据，post可以插入数据，更新数据，删除数据)
     * 枚举：1.书写规范 2，限定了传入类型，节省了编译过程
     */
    public enum Method {

        GET("GET"), POST("POST");

        private String methed;

        Method(String methed) {
            this.methed = methed;
        }

        public String getMethed() {
            return methed;
        }
    }

}
