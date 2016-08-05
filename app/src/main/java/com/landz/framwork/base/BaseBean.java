package com.landz.framwork.base;

/**
 * 作者/日期: admin on 2016/8/2.
 * 描述:数据解析基类
 */
public class BaseBean<T> {
    public String resultStatus;//接口返回码 200表示请求成功，其他表示失败
    public String resultMsg;//返回msg

    @Override
    public String toString() {
        return "BaseBean{" +
                "resultStatus='" + resultStatus + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }
}
