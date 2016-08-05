package com.landz.landz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lizhangfeng on 2016/7/15 0015.
 * Description: 区域，价格，居室等具体数据
 */
public class ParamListBean implements Serializable {

    /* 如果要把对象变成json string，字段一定要用public不要使用set，get方法 */
    public String key;
    public String name;
    public String value;
    public String minValue;
    public String maxValue;
    public List<ParamListBean> childList;

    public boolean isSelect;//是否选中

    public ParamListBean(String name) {
        this.name = name;
    }

    public ParamListBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public ParamListBean(String name, String value, boolean isSelect) {
        this.name = name;
        this.value = value;
        this.isSelect = isSelect;
    }

    public ParamListBean(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }

    public ParamListBean(String name, boolean isSelect, List<ParamListBean> childList) {
        this.name = name;
        this.isSelect = isSelect;
        this.childList = childList;
    }

    @Override
    public String toString() {
        return "ParamListBean{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", minValue='" + minValue + '\'' +
                ", maxValue='" + maxValue + '\'' +
                ", childList=" + childList +
                ", isSelect=" + isSelect +
                '}';
    }



}
