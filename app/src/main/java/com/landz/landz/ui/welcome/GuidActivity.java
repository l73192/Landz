package com.landz.landz.ui.welcome;

import android.animation.ObjectAnimator;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.landz.R;
import com.landz.framwork.base.BaseActivity;
import com.landz.framwork.utils.DisplayUtil;
import com.landz.landz.ui.welcome.adapter.GuideAdapter;
import com.landz.landz.ui.welcome.fragment.GuidFragment;

/**
 * 首次进入app的引导页面
 */
public class GuidActivity extends BaseActivity {
    private ViewPager viewPager;
    private LinearLayout ll_circle;
    private ImageView iv_splash_a,cicle_01,cicle_02,cicle_03;
    private Integer[] imgs = new Integer[]{R.mipmap.splash_a,R.mipmap.splash_b,R.mipmap.splash_c};
    private ImageView[] circles;
    private GuideAdapter guideAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_guid;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        iv_splash_a = findViewByIdNoCast(R.id.iv_splash_a);
        cicle_01 = findViewByIdNoCast(R.id.cicle_01);
        cicle_02 = findViewByIdNoCast(R.id.cicle_02);
        cicle_03 = findViewByIdNoCast(R.id.cicle_03);
        viewPager = findViewByIdNoCast(R.id.viewPager);
        ll_circle = findViewByIdNoCast(R.id.ll_circle);
    }

    @Override
    public void initData() {
        circles = new ImageView[]{cicle_01,cicle_02,cicle_03};
        //设置点和文字的位置
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_circle.getLayoutParams();
        params.width = DisplayUtil.getDensity_Width(this);
        ll_circle.setLayoutParams(params);

        guideAdapter = new GuideAdapter(getSupportFragmentManager());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               setCheck(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(3);//默认是2个view,setOffscreenPageLimit(3)创建3个view
        viewPager.setAdapter(guideAdapter);
        setCheck(0);
    }

    /**
     * 设置选中的圆点
     */
    public void setCheck(int position){
        iv_splash_a.setImageResource(imgs[position]);
        for (int i = 0; i <circles.length ; i++) {
            if (position == i)
                circles[i].setImageResource(R.mipmap.checked_page);
            else
                circles[i].setImageResource(R.mipmap.unchecked_page);
        }
        myAnimator();
    }

    /**
     * 图片动画
     */
    public void myAnimator(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_splash_a,"translationX", 0, DisplayUtil.getDensity_Width(this)-getResources().getDimension(R.dimen.splash_a),0);
        animator.setDuration(15000);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.setRepeatMode(ObjectAnimator.INFINITE);//无穷
        animator.start();
    }

    @Override
    public void onClick(View view) {

    }
}
