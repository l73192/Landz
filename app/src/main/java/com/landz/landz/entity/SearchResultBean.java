package com.landz.landz.entity;


import com.landz.framwork.base.BaseBean;

import java.util.List;

/**
 * Created by Lizhangfeng on 2016/7/25 0025.
 * Description: 搜索返回数据bean
 */
public class SearchResultBean extends BaseBean {

    public List<SearchBean> result;

    @Override
    public String toString() {
        return "SearchResultBean{" +
                "result=" + result +
                '}';
    }
}
