package com.landz.landz.ui.online;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.landz.R;
import com.landz.framwork.base.BaseActivity;
import com.landz.framwork.http.HttpHelper;
import com.landz.framwork.http.HttpRequestAsyncTask;
import com.landz.framwork.utils.IntentUtils;
import com.landz.framwork.utils.ToastUtil;
import com.landz.landz.MyApplication;
import com.landz.landz.entity.HouseArrBean;
import com.landz.landz.entity.HouseOneArrBean;
import com.landz.landz.entity.OnLineHouseResult;
import com.landz.landz.entity.OnLineHouseResultBean;
import com.landz.landz.entity.OnLineTypeResultBean;
import com.landz.landz.entity.OnlineTypeBean;
import com.landz.landz.entity.ParamListBean;
import com.landz.landz.request.OnLineHouseRequest;
import com.landz.landz.ui.online.adapter.OnlineHouseAdapter;
import com.landz.landz.ui.search.SearchActivity;
import com.landz.landz.view.MyPopwindow;
import com.landz.landz.view.RefreshListView;
import java.util.ArrayList;
import java.util.List;

/**
 * 在售豪宅
 */
public class OnlineHouseActivity extends BaseActivity implements MyPopwindow.PopwindowItemClickCallBack {
    private OnlineTypeBean onlineTypeBean;
    private TextView tv_location, tv_price, tv_room, tv_type, tv_more;
    private RelativeLayout rl_location, rl_price, rl_room, rl_type, rl_more;
    /* 地区，价格，居室，类型数据 */
    private List<ParamListBean> location_paramList, price_paramList, room_paramList, type_paramList;
    private RefreshListView lv_online_house;
    private int currentPos = 1;//popwindow标志位
    private List<Object> totalList;
    private OnLineHouseRequest request;
    private OnlineHouseAdapter onlineHouseAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_online_house;
    }

    @Override
    public void beforeInitView() {
        request = new OnLineHouseRequest();
        request.resblockName = getIntent().getStringExtra("resblockName");
        request.circleTypeCode = getIntent().getStringExtra("circleTypeCode");

        totalList = new ArrayList<>();
        onlineTypeBean = MyApplication.getApplication().getOnlineTypeBean();
        if (onlineTypeBean != null) {
            for (OnLineTypeResultBean onLineTypeResultBean : onlineTypeBean.result) {
                if ("1001".equals(onLineTypeResultBean.paramType))//地区
                    location_paramList = onLineTypeResultBean.paramList;
                if ("1008".equals(onLineTypeResultBean.paramType))//价格
                    price_paramList = onLineTypeResultBean.paramList;
                if ("1005".equals(onLineTypeResultBean.paramType))//居室
                    room_paramList = onLineTypeResultBean.paramList;
                if ("1006".equals(onLineTypeResultBean.paramType))//类型
                    type_paramList = onLineTypeResultBean.paramList;
            }
        }
    }

    @Override
    public void initView() {
        tv_location = findViewByIdNoCast(R.id.tv_location);
        tv_price = findViewByIdNoCast(R.id.tv_price);
        tv_room = findViewByIdNoCast(R.id.tv_room);
        tv_type = findViewByIdNoCast(R.id.tv_type);
        tv_more = findViewByIdNoCast(R.id.tv_more);
        rl_location = findViewByIdNoCast(R.id.rl_location);
        rl_price = findViewByIdNoCast(R.id.rl_price);
        rl_room = findViewByIdNoCast(R.id.rl_room);
        rl_type = findViewByIdNoCast(R.id.rl_type);
        rl_more = findViewByIdNoCast(R.id.rl_more);
        lv_online_house = findViewByIdNoCast(R.id.lv_online_house);
    }

    @Override
    public void initData() {
        setOnClick(rl_location, rl_more, rl_price, rl_room, rl_type);
        onlineHouseAdapter = new OnlineHouseAdapter(this);
        lv_online_house.setAdapter(onlineHouseAdapter);
        //加载更多
        lv_online_house.setOnLoadMoreListener(new RefreshListView.OnLoadMoreListener() {
            @Override
            public void loadMoew() {
                request.pageNo++;
                getData();
            }

            @Override
            public void onRefresh() {
                request.pageNo = 0;
                getData();
            }
        });

        //自定义ListView Item点击事件
        lv_online_house.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position > 0) {
                    if (onlineHouseAdapter.getItem(position - 1) instanceof HouseArrBean) {
                        HouseOneArrBean houseOneArrBean = (HouseOneArrBean) onlineHouseAdapter.getItem(position - 1);
                        Bundle bundle = new Bundle();
                        bundle.putString("houseOneId", houseOneArrBean.houseOneId);
                        bundle.putString("resblockId", houseOneArrBean.resblockOneId);
                        IntentUtils.openActivity(OnlineHouseActivity.this, OnlineHouseActivity.class, bundle);
                    }
                }
            }
        });
        getData();
    }

    /**
     * 获取在线房源数据
     */
    public void getData() {
        showProgressBar();
        if (request.pageNo == 0)
            totalList.clear();
        HttpHelper.getOnLineHouseList(OnlineHouseActivity.this, request, new HttpRequestAsyncTask.CallBack() {
            @Override
            public void OnSuccess(String result) {
                lv_online_house.setOnComplete();
                dismissProgressBar();
                //将数据解析成对象
                OnLineHouseResult resultBean = new Gson().fromJson(result, OnLineHouseResult.class);
                resultBean.initListData(resultBean, totalList);
                onlineHouseAdapter.setTotalList(totalList);
                onlineHouseAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnFailed(String errMsg) {
//                lv_online_house.setOnComplete();
                dismissProgressBar();
                ToastUtil.showToast("加载失败");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_location://区域
                currentPos = 0;
                MyPopwindow myPopwindow = new MyPopwindow(OnlineHouseActivity.this, false);
                myPopwindow.setParamListBean(location_paramList, this, false);
                myPopwindow.showPopWindow(rl_location);
                break;
            case R.id.rl_price://价格
                currentPos = 1;
                MyPopwindow myPopwindow1 = new MyPopwindow(OnlineHouseActivity.this, true);
                myPopwindow1.setParamListBean(price_paramList, this, true);
                myPopwindow1.showPopWindow(rl_price);
                break;
            case R.id.rl_room://居室
                currentPos = 2;
                MyPopwindow myPopwindow2 = new MyPopwindow(OnlineHouseActivity.this, true);
                myPopwindow2.setParamListBean(room_paramList, this, true);
                myPopwindow2.showPopWindow(rl_room);
                break;
            case R.id.rl_type://类型
                currentPos = 3;
                MyPopwindow myPopwindow3 = new MyPopwindow(OnlineHouseActivity.this, true);
                myPopwindow3.setParamListBean(type_paramList, this, true);
                myPopwindow3.showPopWindow(rl_type);
                break;
            case R.id.rl_more://更多
                currentPos = 4;
                Intent intent = new Intent(OnlineHouseActivity.this, SearchActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onItemClick(ParamListBean paramListBean) {
        if (paramListBean == null)
            return;
        switch (currentPos) {
            case 0://区域
                tv_location.setText(paramListBean.name);
                request.circleTypeCode = paramListBean.key;
                break;
            case 1://价格
                tv_price.setText(paramListBean.name);
                request.totalPricesBegin = paramListBean.minValue;
                request.totalPricesEnd = paramListBean.minValue;
                break;
            case 2://居室
                tv_room.setText(paramListBean.name);
                request.bedroomAmount = paramListBean.value;
                break;
            case 3://类型
                tv_type.setText(paramListBean.name);
                request.buildingType = paramListBean.name;
                break;
        }
        request.pageNo = 0;
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 100){
            if (data != null){
                request.sort = data.getStringExtra("sort");
                request.buildSizeBegin = data.getStringExtra("buildSizeBegin");
                request.buildSizeEnd = data.getStringExtra("buildSizeEnd");
                request.feature = data.getStringExtra("feature");
                request.onlyLook = data.getStringExtra("onlyLook");
                request.pageNo = 0;
                getData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
