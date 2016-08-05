package com.landz.landz.ui.welcome.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.landz.R;
import com.landz.framwork.base.BaseFragment;
import com.landz.framwork.share.SharePrefreceHelper;
import com.landz.framwork.utils.DisplayUtil;
import com.landz.framwork.utils.IntentUtils;
import com.landz.landz.ui.MainActivity;

/**
 * 引导页Fragment 共用页面
 */
public class GuidFragment extends BaseFragment {
    private LinearLayout ll_description;
    private TextView tv_title, tv_content;
    private int position;
    private Button btn_tiyan;

    public GuidFragment() {

    }

    public static GuidFragment newInstance(int position) {
        GuidFragment guidFragment = new GuidFragment();
        guidFragment.position = position;
        return guidFragment;
    }

    @Override
    protected int getResource() {
        return R.layout.fragment_guid;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView(View rootView) {
        ll_description = findViewByIdNoCast(R.id.ll_description);
        tv_title = findViewByIdNoCast(R.id.tv_title);
        tv_content = findViewByIdNoCast(R.id.tv_content);
        btn_tiyan = findViewByIdNoCast(R.id.btn_tiyan);
    }

    @Override
    protected void initData() {
        setOnClick(btn_tiyan);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_description.getLayoutParams();
        params.width = DisplayUtil.getDensity_Width(getContext());
        ll_description.setLayoutParams(params);
        switch (position) {
            case 0:
                tv_title.setText(getString(R.string.splash_tip1));
                tv_content.setText(getString(R.string.splash_tip01));
                break;
            case 1:
                tv_title.setText(getString(R.string.splash_tip2));
                tv_content.setText(getString(R.string.splash_tip02));
                break;
            case 2:
                tv_title.setText(getString(R.string.splash_tip3));
                tv_content.setText(getString(R.string.splash_tip03));
                btn_tiyan.setVisibility(View.VISIBLE);
                btn_tiyan.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) btn_tiyan.getLayoutParams();
                        param.leftMargin = (DisplayUtil.getDensity_Width(getContext()) - btn_tiyan.getWidth()) / 2;
                        btn_tiyan.setLayoutParams(param);
                    }
                }, 500);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_tiyan) {
            SharePrefreceHelper.getInstence(getActivity()).setIsFrist(false);
            IntentUtils.openActivity(getActivity(), MainActivity.class);
            getActivity().finish();
        }
    }
}
