package com.landz.framwork.share;

import android.content.Context;

/**
 * 作者/日期: admin on 2016/8/2.
 * 描述: 文件存储帮助类(轻量级)
 */
public class SharePrefreceHelper extends PrefrenceWrapper{
    /**
     * 单例
     */
    private static SharePrefreceHelper sharePrefreceHelper;

    private SharePrefreceHelper(Context context) {
        super(context);
    }

    public static SharePrefreceHelper getInstence(Context context){
        if (sharePrefreceHelper == null)
            sharePrefreceHelper = new SharePrefreceHelper(context);
        return sharePrefreceHelper;
    }

    public void setUserName(String userName){
        putString("userName",userName);
    }

    public String getUserName(){
        return getString("userName");
    }

    public void setIsFrist(boolean isFrist){
        setBoolean("isFrist",isFrist);
    }

    public boolean isFirst(){
        return getBoolean("isFrist",true);
    }

}
