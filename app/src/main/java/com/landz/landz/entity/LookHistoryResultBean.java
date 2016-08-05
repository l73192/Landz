package com.landz.landz.entity;


import com.landz.framwork.base.BaseBean;

import java.util.List;

/**
 * Created by Lizhangfeng on 2016/7/28 0028.
 * Description: 顾问看房列表
 */
public class LookHistoryResultBean extends BaseBean {

    public LookHistoryBean result;

    public class LookHistoryBean  {
        public List<GuWenResultBean.ShowArr> showArr;
        public String totalAmount;

        @Override
        public String toString() {
            return "LookHistoryBean{" +
                    "showArr=" + showArr +
                    ", totalAmount='" + totalAmount + '\'' +
                    '}';
        }


    }

    @Override
    public String toString() {
        return "LookHistoryResultBean{" +
                "result=" + result +
                '}';
    }
}
