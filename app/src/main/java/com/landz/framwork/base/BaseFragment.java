package com.landz.framwork.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landz.R;

/**
 * 作者/日期: admin on 2016/8/2.
 * 描述:Fragment基类
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getResource(), null);
            beforeInitView();
            initView(rootView);
            initData();
        }
        /**
         * 缓存的rootView需要判断是否加载过parent，如果有parent需要从parent删除，
         * 要不然会发生这个rootview已经有parent的错误。
         */
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
            parent.removeView(rootView);
        return rootView;
    }

    /**
     * 不用强转的findViewById
     */
    protected <T extends View> T findViewByIdNoCast(int id) {
        return rootView == null ? null : (T) rootView.findViewById(id);
    }

    protected abstract int getResource();//获取资源

    protected abstract void beforeInitView();//创建View之前做的事

    protected abstract void initView(View rootView);//初始化View

    protected abstract void initData();//初始化数据

    /**
     * 可扩展参数的监听
     */
    protected void setOnClick(int... ids) {
        for (int id : ids) {
            if (id != -1) {
                findViewByIdNoCast(id).setOnClickListener(this);
            }
        }
    }

    protected void setOnClick(View... views) {
        for (View view : views)
            view.setOnClickListener(this);
    }

}
