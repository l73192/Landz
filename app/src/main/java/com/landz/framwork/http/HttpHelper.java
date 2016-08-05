package com.landz.framwork.http;

import android.app.Activity;

import com.landz.framwork.utils.UrlUtils;
import com.landz.landz.entity.OnLineHouseResult;
import com.landz.landz.request.OnLineHouseRequest;

import java.util.HashMap;
import java.util.Map;
/**
 * 作者/日期: admin on 2016/8/3.
 * 描述:网络访问帮助类
 */
public class HttpHelper{
    /**
     * 测试登录服务器
     */
    public static void login(HttpRequestAsyncTask.CallBack callBack, Activity activity){
        Map<String,String> map = new HashMap<>();
        map.put("userName","landz");
        map.put("pwd","123456");
        Request request = new Request("http://baidu.com",Request.Method.POST,map);//请求对象，封装请求对象的信息
        HttpRequestAsyncTask httpRequestAsyncTask = new HttpRequestAsyncTask(activity,callBack);//网络请求线程池
        httpRequestAsyncTask.execute(request);//执行网络请求
    }

    /**
     * 在售房源访问
     */
    public static void getOnLineHouseList(Activity activity, OnLineHouseRequest requestBean, HttpRequestAsyncTask.CallBack callBack) {
        Request request = new Request(UrlUtils.ONLINE_HOUSE, Request.Method.POST, requestBean == null ? null : requestBean.getRequestMap());
        HttpRequestAsyncTask httpRequestAsyncTask = new HttpRequestAsyncTask(activity, callBack);
        httpRequestAsyncTask.execute(request);
    }

    /**
     * 搜索
     * @param content 搜索内容
     * @param type     搜索类型
     */
    public static void getSearchDatas(Activity activity,String content,String type,HttpRequestAsyncTask.CallBack callBack){
        Map<String,String> map = new HashMap<>();
        map.put("keyWords",content);
        map.put("type",type);
        Request request = new Request(UrlUtils.SEARCH_URL, Request.Method.GET,map);
        HttpRequestAsyncTask httpRequestAsyncTask = new HttpRequestAsyncTask(activity,callBack);
        httpRequestAsyncTask.execute(request);
    }
    /**
     * 一手房房子详情
     * @param houseOneId
     */
    public static void getOneHouseDetail(Activity activity, String houseOneId, HttpRequestAsyncTask.CallBack callBack){
        Map<String,String> map = new HashMap<>();
        map.put("houseOneId",houseOneId);
        Request request = new Request(UrlUtils.HOUSE_DETAIL, Request.Method.GET, map);
        HttpRequestAsyncTask httpRequestAsyncTask = new HttpRequestAsyncTask(activity,callBack);
        httpRequestAsyncTask.execute(request);
    }

    /**
     * 一手房看房记录——本房顾问列表
     * @param houseOneId
     */
    public static void getOneHouseDetailLook(Activity activity,String houseOneId, HttpRequestAsyncTask.CallBack callBack){
        Map<String, String> map = new HashMap<>();
        map.put("houseOneId",houseOneId);
        Request request = new Request(UrlUtils.HOUSE_DETAIL_LOOK, Request.Method.GET,map);
        HttpRequestAsyncTask httpRequestAsyncTask = new HttpRequestAsyncTask(activity,callBack);
        httpRequestAsyncTask.execute(request);
    }

    /**
     * 更多一手房源推荐
     * @param houseId 房源Id
     * @param resblockId 楼盘Id
     */
    public static void getOneHouseDetailMore(Activity activity,String houseId,String resblockId,HttpRequestAsyncTask.CallBack callBack){




    }



}
