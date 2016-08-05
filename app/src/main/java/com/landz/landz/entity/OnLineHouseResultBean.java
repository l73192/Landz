package com.landz.landz.entity;


import com.landz.framwork.base.BaseBean;

import java.util.List;

/**
 * Created by Lizhangfeng on 2016/7/19 0019.
 * Description:
 */
public class OnLineHouseResultBean extends BaseBean {

    public List<HouseArrBean> houseArr;
    public List<HouseOneArrBean> houseOneArr;

    @Override
    public String toString() {
        return "OnLineHouseResultBean{" +
                "houseArr=" + houseArr +
                ", houseOneArr=" + houseOneArr +
                '}';
    }
}
