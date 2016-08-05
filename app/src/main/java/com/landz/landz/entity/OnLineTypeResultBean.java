package com.landz.landz.entity;

import java.util.List;

/**
 * Created by Lizhangfeng on 2016/7/15 0015.
 * Description: 豪宅类型result
 */
public class OnLineTypeResultBean {

    public String paramType;
    public List<ParamListBean> paramList;

    @Override
    public String toString() {
        return "OnLineTypeResultBean{" +
                "paramType='" + paramType + '\'' +
                ", paramList=" + paramList +
                '}';
    }
}
