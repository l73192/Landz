package com.landz.landz.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.landz.R;
import com.landz.framwork.utils.DisplayUtil;
import com.landz.landz.entity.ParamListBean;
import com.landz.landz.ui.online.adapter.LocationAdapter;

import java.util.List;

/**
 * 作者/日期: admin on 2016/8/4.
 * 描述:自定义下拉菜单popwindow
 */
public class MyPopwindow extends PopupWindow {
    private LocationAdapter adapter, adapter2;
    private ListView listView, listView2;
    private View view;
    private boolean isOneList;//是否是一个列表
    public PopwindowItemClickCallBack callBack;

    public MyPopwindow(Context context, boolean isOneList) {
        super(context);
        this.isOneList = isOneList;
        init(context);
    }

    public void init(Context context) {
        view = View.inflate(context, R.layout.pop_location, null);

        //PopWindow相关设置
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(DisplayUtil.getDensity_Height(context));
        this.setFocusable(true);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        this.setBackgroundDrawable(new BitmapDrawable());

        listView = (ListView) view.findViewById(R.id.lv_location);
        listView2 = (ListView) view.findViewById(R.id.lv_location2);
        this.view = view.findViewById(R.id.view);
        adapter = new LocationAdapter(context, false);
        listView.setAdapter(adapter);

        //判断是否是一个列表
        if (isOneList)
            listView2.setVisibility(View.GONE);
        else {
            listView2.setVisibility(View.VISIBLE);
            adapter2 = new LocationAdapter(context, true);
            listView2.setAdapter(adapter2);
        }
        this.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing())
                    dismiss();
            }
        });
    }

    /**
     * 放ParamListBean数据
     * 点击 一级列表popwindow是否消
     */
    public void setParamListBean(final List<ParamListBean> paramListBeen, final PopwindowItemClickCallBack callBack, final boolean isOneDismiss) {
        this.callBack = callBack;
        if (paramListBeen == null || paramListBeen.isEmpty())
            return;
        adapter.setItems(paramListBeen);
        adapter.notifyDataSetChanged();
        setSelectDatas(paramListBeen, 0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                setSelectDatas(paramListBeen, position);
                if (isOneDismiss) {
                    dismiss();
                    if (callBack != null)
                        callBack.onItemClick(paramListBeen.get(position));
                }
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ParamListBean paramList = (ParamListBean) adapter2.getItem(position);
                if (callBack != null)
                    callBack.onItemClick(paramList);
                dismiss();
            }
        });
    }

    public void setSelectDatas(List<ParamListBean> beanList, int position) {
        //更新一级列表选中状态
        for (int i = 0; i < beanList.size(); i++) {
            if (i == position)
                beanList.get(i).isSelect = true;
            else
                beanList.get(i).isSelect = false;
        }
        adapter.notifyDataSetChanged();

        if (!isOneList) {//二级列表
            ParamListBean bean = beanList.get(position);
            List<ParamListBean> childList = bean.childList;
            adapter2.setItems(childList);
            adapter2.notifyDataSetChanged();
        }
    }

    /**
     * 显示popwindow
     */
    public void showPopWindow(View view) {
        if (!isShowing()) {
            this.showAsDropDown(view);//显示在view的下方
            // this.showAtLocation(view, Gravity.TOP, 0, 0);//可以显示在指定view的指定位置
        }
    }

    /**
     * Item点击回调
     */
    public interface PopwindowItemClickCallBack {
        void onItemClick(ParamListBean paramListBean);
    }

}
