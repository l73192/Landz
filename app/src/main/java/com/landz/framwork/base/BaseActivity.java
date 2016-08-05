package com.landz.framwork.base;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import com.landz.R;
import com.landz.framwork.tools.AppManager;
/**
 * 作者/日期: admin on 2016/8/2.
 * 描述:Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,Handler.Callback{
    private Dialog dialog;
    private Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        //隐藏ActionBar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        AppManager.getInstance().addActivity(this);
        setTranslucentStatus();//状态栏透明

        beforeInitView();
        initView();
        initData();
        mHandler = new Handler(this);
    }
    public abstract int getContentViewId();//放layoutId

    public abstract void beforeInitView();//初始化view之前做的事

    public abstract void initView();//初始化View

    public abstract void initData();//初始化数据

    /**
     * 不用强转的findViewById
     */
    public <T extends View> T findViewByIdNoCast(int id){
        return (T)super.findViewById(id) ;
    }

    /**
     * 可扩展参数的监听
     */
    public void setOnClick(int... ids){
        for (int id : ids)
           findViewById(id).setOnClickListener(this);
    }
    public void setOnClick(View...views){
        for (View view : views)
            view.setOnClickListener(this);
    }


    /**
     * 沉浸式状态栏(状态栏透明)只有Anroid4.4以上才支持
     */
    public void setTranslucentStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(layoutParams);
        }
    }

    /**
     * 显示进度条
     */
    public void showProgressBar(){
        if (dialog == null){
            dialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
            View view = View.inflate(this, R.layout.dialog_progressbar,null);
            dialog.setContentView(view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            //做图片动画
            ImageView img_progressBar = (ImageView) view.findViewById(R.id.img_progressbar);
            AnimationDrawable animationDrawable = (AnimationDrawable) img_progressBar.getDrawable();
            animationDrawable.start();
            dialog.show();
        }
    }

    /**
     * 结束进度条（通过发送Handler消息）
     */
    public void dismissProgressBar(){
        mHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    public boolean handleMessage(Message message) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        return false;
    }

    /**
     * 本段代码用来处理如果输入法还显示的话就消失掉输入键盘
     */
    protected void dismissSoftKeyboard(Activity activity){
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 显示键盘
     */
    protected void showKeyboard(View view) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Activity销毁时，移除Activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().remove(this);
    }

}
