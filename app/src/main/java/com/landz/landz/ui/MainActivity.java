package com.landz.landz.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.landz.R;
import com.landz.framwork.base.BaseActivity;
import com.landz.framwork.utils.AssetUtils;
import com.landz.framwork.utils.DrawableUtils;
import com.landz.framwork.utils.IntentUtils;
import com.landz.framwork.utils.ToastUtil;
import com.landz.landz.ui.online.OnlineHouseActivity;
import com.landz.landz.ui.search.SearchActivity;
import com.landz.landz.ui.welcome.WelcomeActivity;

/**
 * 首页
 */
public class MainActivity extends BaseActivity implements Handler.Callback {
    private TextView tv_city,tv_map,tv_main_online,tv_main_wait_rent,tv_main_seebuild,
             tv_main_onehouse,tv_main_map,tv_main_study,tv_main_man,tv_main_myhouse;
    private LinearLayout ll_online_house,ll_wait_rent_luxuryhouse,ll_house_appreciate,ll_new_luxuryhouse,
            ll_map_find_house,ll_luxuryhouse_research,ll_luxuryhouse_consultant,ll_my_luxuryhouse;
    private EditText et_main;

    private TextView[] textViews;

    private Integer[] normal_ids = new Integer[]{R.mipmap.main_online_normal, R.mipmap.main_wait_rent_normal,
            R.mipmap.main_seebuild_normal, R.mipmap.main_onehouse_normal,R.mipmap.main_map_normal,
            R.mipmap.main_study_normal,R.mipmap.main_man_normal,R.mipmap.main_myhouse_normal};

    private Integer[] select_ids = new Integer[]{R.mipmap.main_online, R.mipmap.main_wait_rent,
            R.mipmap.main_seebuild, R.mipmap.main_onehouse,R.mipmap.main_map,
            R.mipmap.main_study,R.mipmap.main_man,R.mipmap.main_myhouse};

    private Handler mHandler;
    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void beforeInitView() {
        mHandler = new Handler(this);
    }

    @Override
    public void initView() {
        tv_city = findViewByIdNoCast(R.id.tv_city);
        tv_map = findViewByIdNoCast(R.id.tv_map);
        tv_main_online = findViewByIdNoCast(R.id.tv_main_online);
        tv_main_seebuild = findViewByIdNoCast(R.id.tv_main_seebuild);
        tv_main_wait_rent = findViewByIdNoCast(R.id.tv_main_wait_rent);
        tv_main_onehouse = findViewByIdNoCast(R.id.tv_main_onehouse);
        tv_main_map = findViewByIdNoCast(R.id.tv_main_map);
        tv_main_study = findViewByIdNoCast(R.id.tv_main_study);
        tv_main_man = findViewByIdNoCast(R.id.tv_main_man);
        tv_main_myhouse = findViewByIdNoCast(R.id.tv_main_myhouse);
        ll_online_house = findViewByIdNoCast(R.id.ll_online_house);
        ll_wait_rent_luxuryhouse = findViewByIdNoCast(R.id.ll_wait_rent_luxuryhouse);
        ll_house_appreciate = findViewByIdNoCast(R.id.ll_house_appreciate);
        ll_new_luxuryhouse = findViewByIdNoCast(R.id.ll_new_luxuryhouse);
        ll_map_find_house = findViewByIdNoCast(R.id.ll_map_find_house);
        ll_luxuryhouse_research = findViewByIdNoCast(R.id.ll_luxuryhouse_research);
        ll_luxuryhouse_consultant = findViewByIdNoCast(R.id.ll_luxuryhouse_consultant);
        ll_my_luxuryhouse = findViewByIdNoCast(R.id.ll_my_luxuryhouse);
        et_main = findViewByIdNoCast(R.id.et_main);
    }

    @Override
    public void initData() {
        textViews = new TextView[]{tv_main_online,tv_main_wait_rent,tv_main_seebuild,
                tv_main_onehouse,tv_main_map,tv_main_study,tv_main_man,tv_main_myhouse};
        setOnClick(tv_city,tv_map,et_main,ll_online_house,ll_wait_rent_luxuryhouse,
                ll_house_appreciate,ll_new_luxuryhouse,ll_map_find_house,ll_luxuryhouse_research,
                ll_luxuryhouse_consultant,ll_my_luxuryhouse);
        AssetUtils.getOnlineTypeData(this); //解析本地文件
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_main://跳转搜索页面
                Bundle bundle = new Bundle();
                bundle.putInt("type",5);
                IntentUtils.openActivity(MainActivity.this, SearchActivity.class,bundle);
                break;
            case R.id.ll_online_house://在售豪宅
                setSelect(0);
                IntentUtils.openActivity(MainActivity.this, OnlineHouseActivity.class);
                break;
            case R.id.ll_wait_rent_luxuryhouse://待租豪宅
                setSelect(1);
                IntentUtils.openActivity(MainActivity.this, WelcomeActivity.class);
                break;
            case R.id.ll_house_appreciate://楼盘鉴赏
                setSelect(2);
                IntentUtils.openActivity(MainActivity.this, WelcomeActivity.class);
                break;
            case R.id.ll_new_luxuryhouse://一手豪宅
                setSelect(3);
                IntentUtils.openActivity(MainActivity.this, WelcomeActivity.class);
                break;
            case R.id.ll_map_find_house://地图找房
                setSelect(4);
                IntentUtils.openActivity(MainActivity.this, WelcomeActivity.class);
                break;
            case R.id.ll_luxuryhouse_research://豪宅研究
                setSelect(5);
                IntentUtils.openActivity(MainActivity.this, WelcomeActivity.class);
                break;
            case R.id.ll_luxuryhouse_consultant://豪宅顾问
                setSelect(6);
                IntentUtils.openActivity(MainActivity.this, WelcomeActivity.class);
                break;
            case R.id.ll_my_luxuryhouse://我的豪宅
                setSelect(7);
                IntentUtils.openActivity(MainActivity.this, WelcomeActivity.class);
                break;
        }
    }

    /**
     * 设置被选中状态
     */
    public void setSelect(int position){
        for (int i = 0; i <textViews.length ; i++) {
            if (position == i){
                textViews[i].setTextColor(Color.parseColor("#fff0cb7e"));
                DrawableUtils.drawableTop(MainActivity.this,textViews[i],select_ids[i]);
            }else {
                textViews[i].setTextColor(Color.WHITE);
                DrawableUtils.drawableTop(MainActivity.this,textViews[i],normal_ids[i]);
            }
        }
    }

    /**
     *  2m内连续点击2次返回键退出app,使用Handler延时消息处理
     */
    private static final int TIMES = 2000;
    private boolean isBack;
    //按下监听
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){//按下返回键
            if (isBack){
                ToastUtil.showToast("再点一次退出");
                isBack = false;
                mHandler.sendEmptyMessageDelayed(1,TIMES);
            }else
                System.exit(0);//退出app
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what){
            case 1:
                isBack = true;
                break;
        }
        return false;
    }


}
