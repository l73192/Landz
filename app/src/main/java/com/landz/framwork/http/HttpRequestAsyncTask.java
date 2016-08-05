package com.landz.framwork.http;

import android.app.Activity;
import android.os.AsyncTask;
import com.google.gson.Gson;
import com.landz.framwork.base.BaseBean;
import com.landz.framwork.utils.LogUtils;
import com.landz.framwork.utils.NetWorkUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
/**
 * 作者/日期: admin on 2016/8/2.
 * 描述: 网络请求
 * AsyncTask 内部实现了线程池，默认5个线程同时存在
 */
public class HttpRequestAsyncTask extends AsyncTask<Request,Integer,String> {
    /* 设置编码格式 */
    private static final String CHARSET = "UTF-8";
    private CallBack callBack;
    private Activity activity;
    private static final String TAG = "HttpRequestAsyncTask";
    public HttpRequestAsyncTask(Activity activity,CallBack callBack) {
        this.callBack = callBack;
        this.activity = activity;
    }

    private HttpURLConnection buildHttpClient(String urlStr, int timeout){//创建网络连接
        URL url = null;
        HttpURLConnection conn = null;
        try{
            LogUtils.e("请求地址：" + urlStr);
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeout);// 连接超时
            conn.setReadTimeout(timeout);// 读取超时 单位毫秒
            conn.setUseCaches(false);//是否使用缓存
            conn.setDoOutput(true);//是否输入参数
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    protected String doInBackground(Request... requests) {//相当于Thread run  执行网络请求，返回请求结果对象result（String型）
        String resultString = "";
        Request request = requests[0];
        if (!NetWorkUtils.isNetDeviceAvailable(activity)) //判断网络连接是否已开
            return "网络不可用";
        try {
            HttpURLConnection connection = buildHttpClient(request.getUrl(), request.getREQUEST_TIME_OUT());
            connection.setRequestMethod(request.getMethod().getMethed());// 请求方式
            StringBuffer params = new StringBuffer();
            if (request.getParams() != null) {
                for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                    params.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                    LogUtils.e("请求参数：" + entry.getKey() + " : " + entry.getValue());
                }
                byte[] bypes = params.toString().substring(1, params.length()).getBytes();
                connection.getOutputStream().write(bypes);
            }
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                LogUtils.e("请求接口失败：responseCode ＝ " + responseCode);
                return null;
            }
            InputStream in = connection.getInputStream();
            InputStreamReader isReader = new InputStreamReader(in, CHARSET);
            BufferedReader bufReader = new BufferedReader(isReader);
            resultString = bufReader.readLine();
            LogUtils.e("接口返回结果：" + resultString);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resultString;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {//进度
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {//运行于主线程，请求结果result 反馈到主线程，使主线程Ui做出响应
        super.onPostExecute(result);
        //activity结束掉之后不希望返回给主线程，防止activity还有null对象调用出现NullPointExcption
        if (activity != null && !activity.isFinishing()) {
            if ("网络不可用".equals(result)){
                if (callBack != null)
                    callBack.OnFailed("网络不可用");
            }
            if (callBack != null){
                try {
                    BaseBean baseBean = new Gson().fromJson(result,BaseBean.class);
                    if ("200".equals(baseBean.resultStatus))
                        callBack.OnSuccess(result);
                }catch (Exception e){
                   callBack.OnFailed("数据解析异常");
                }
            }
        }else{
            if (callBack != null)
                callBack.OnFailed("请求失败");
        }
    }

    public interface CallBack {
        void OnSuccess(String result);
        void OnFailed(String errMsg);
    }


}
