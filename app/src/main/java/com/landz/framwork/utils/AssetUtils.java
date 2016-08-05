package com.landz.framwork.utils;

import android.content.Context;
import android.content.res.AssetManager;
import com.google.gson.Gson;
import java.io.InputStream;

/**
 * Created by Lizhangfeng on 2016/7/15 0015.
 * Description: 获取asset目录下的文件并解析
 */
public class AssetUtils {

    public static void getOnlineTypeData(final Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //通过上下文拿到一个AssetManager对象
                AssetManager assetManager = context.getAssets();
                try {
                    InputStream inputStream = assetManager.open("study_type.txt");//通过文件名获取一个InputStream
                    int length = inputStream.available();//通过available获取输入流的长度
                    byte[] bytes = new byte[length];
                    inputStream.read(bytes);//把InputStream写入到byte数组中
                    String result = new String(bytes, "utf-8");//byte转换成编码格式为utf-8的string
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
