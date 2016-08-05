package com.landz.landz.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.landz.R;
import com.landz.framwork.Constants;

/**
 * 作者/日期: admin on 2016/8/4.
 * 描述:自定义ListView
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener, Handler.Callback {
    private static final int LOADING_MORE = 1;//正在加载更多
    private static final int DONE = 2;//加载完成
    private static final int PULL_TO_REFRESH = 3;//下拉状态
    private static final int RELASE_TO_REFRE = 4;//释放状态
    private static final int LOAD_REFRESH = 5;//下拉加载刷新
    private OnLoadMoreListener onLoadMoreListener;
    private int state;
    private Handler handler;

    /*-----footerView------*/
    private View footerView;//底部加载更多布局
    private ImageView img_footer_progressbar;

    /*-----headerView------*/
    private View headerView;//头部加载更多布局
    private int headerViewHeight;
    private ImageView img_header;//箭头
    private TextView tv_header;//下拉刷新字
    private ProgressBar header_progressBar;//进度条
    private RotateAnimation animation;//下拉动画
    private RotateAnimation relase_animation;//释放动画

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * 初始化View
     */
    private void initView(Context context) {
        //添加footView
        footerView = View.inflate(context, R.layout.footerview_listview, null);
        // addFooterView(footerView); 最后一个boolean 默认是true 可以被选中的
        addFooterView(footerView, null, false);
        img_footer_progressbar = (ImageView) footerView.findViewById(R.id.img_footer_progressbar);
        AnimationDrawable animationDrawable = (AnimationDrawable) img_footer_progressbar.getDrawable();
        animationDrawable.start();

        //添加headerView
        headerView = View.inflate(context, R.layout.headview_listview, null);
        addHeaderView(headerView);
        img_header = (ImageView) headerView.findViewById(R.id.img_header);
        tv_header = (TextView) headerView.findViewById(R.id.tv_header);
        header_progressBar = (ProgressBar) headerView.findViewById(R.id.header_progressBar);
        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(250);
        animation.setFillAfter(true);//停留在动画最后状态
        relase_animation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        relase_animation.setDuration(250);
        relase_animation.setFillAfter(true);

        //计算headerView的高度
        measureView(headerView);
        headerViewHeight = headerView.getMeasuredHeight();

        //一开始隐藏headerView、footerView
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        footerView.setVisibility(INVISIBLE);

        setOnScrollListener(this);
        state = DONE;
        handler = new Handler(this);
    }

    /**
     * 计算View的高度
     */
    public void measureView(View child) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams == null)
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int lpHeight = layoutParams.height;
        int childHeightSpec;
        if (lpHeight > 0)
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        else
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * listView滚动状态监听、判断rooterView是否执行加载更多
     */
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState != SCROLL_STATE_IDLE)
            return;
        if (state == LOADING_MORE || state == LOAD_REFRESH)
            return;
        if (getLastVisiblePosition() == (getCount() - 1)) {
            if ((getCount() - 2) % Constants.PAGE_SIZE == 0) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.loadMoew();
                    footerView.setVisibility(VISIBLE);
                    state = LOADING_MORE;
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    private int stateY, endY;

    /**
     * 触摸事件、判断headerView是的执行刷新加载数据
     * 1,按下事件  2，滑动事件  3，抬起事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //判断ListView当前是否滑动在顶部、或正在加载中
        if (getFirstVisiblePosition() != 0 || state == LOADING_MORE)
            return super.onTouchEvent(ev);
        //不同的触摸获得
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下事件
                if (state == DONE)
                    state = PULL_TO_REFRESH;
                stateY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE://滑动事件
                endY = (int) ev.getY();
                int moveY = endY - stateY;
                if (moveY > 0 && state != LOAD_REFRESH)
                    setSelection(0);
//                headerView.setPadding(0, moveY - headerViewHeight, 0, 0);
                if (moveY / 2 > headerViewHeight) {
                    if (state == PULL_TO_REFRESH) {
                        state = RELASE_TO_REFRE;
                        img_header.clearAnimation();
                        img_header.startAnimation(animation);
                        tv_header.setText("松开刷新数据");
                    }
                }
                if (moveY / 2 < headerViewHeight) {
                    if (state == RELASE_TO_REFRE) {
                        state = PULL_TO_REFRESH;
                        img_header.clearAnimation();
                        img_header.startAnimation(relase_animation);
                        tv_header.setText("下拉刷新");
                    }
                }
                break;
            case MotionEvent.ACTION_UP://抬起事件
                if (state == PULL_TO_REFRESH)
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                if (state == RELASE_TO_REFRE) {
                    headerView.setPadding(0, 0, 0, 0);
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onRefresh();
                        header_progressBar.setVisibility(VISIBLE);
                        img_header.setVisibility(GONE);
                        handler.sendEmptyMessage(1);
                    }
                    state = LOAD_REFRESH;
                }
                if (state == LOAD_REFRESH)
                    headerView.setPadding(0, 0, 0, 0);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 加载完成
     */
    public void setOnComplete() {
        header_progressBar.setVisibility(GONE);
        img_header.setVisibility(VISIBLE);
        footerView.setVisibility(INVISIBLE);
        tv_header.setText("下拉刷新");
        state = DONE;
        setSelection(0);
        headerView.setPadding(0, -headerViewHeight, 0, 0);
    }

    @Override
    public boolean handleMessage(Message message) {
        if (state != LOAD_REFRESH)
            return false;
        switch (message.what) {
            case 1:
                tv_header.setText("拼命加载中.");
                handler.sendEmptyMessageDelayed(2, 500);
                break;
            case 2:
                tv_header.setText("拼命加载中..");
                handler.sendEmptyMessageDelayed(3, 500);
                break;
            case 3:
                tv_header.setText("拼命加载中...");
                handler.sendEmptyMessageDelayed(1, 500);
                break;
        }
        return false;
    }

    /**
     * 加载更多
     */
    public interface OnLoadMoreListener {
        void loadMoew();//加载更多

        void onRefresh();//下拉刷新
    }


}
