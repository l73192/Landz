package com.landz.landz.entity;


import com.landz.framwork.base.BaseBean;

import java.util.List;

/**
 * Created by Lizhangfeng on 2016/7/15 0015.
 * Description: 获取筛选豪宅类型
 */
public class OnlineTypeBean extends BaseBean {

    public List<OnLineTypeResultBean> result;

    @Override
    public String toString() {
        return "OnlineTypeBean{" +
                "result=" + result +
                '}';
    }
}
