package com.landz.landz.ui.welcome;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.landz.R;
import com.landz.framwork.base.BaseActivity;
import com.landz.framwork.share.SharePrefreceHelper;
import com.landz.framwork.utils.IntentUtils;
import com.landz.landz.ui.MainActivity;

/**
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity implements Handler.Callback {
    private Handler mHandler;

    @Override
    public int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        mHandler = new Handler(this);
        mHandler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                if (SharePrefreceHelper.getInstence(WelcomeActivity.this).isFirst())
                    IntentUtils.openActivity(this, GuidActivity.class);
                else
                    IntentUtils.openActivity(this, MainActivity.class);
                finish();
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

}
