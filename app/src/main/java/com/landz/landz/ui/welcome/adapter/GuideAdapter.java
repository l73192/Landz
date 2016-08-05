package com.landz.landz.ui.welcome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.landz.landz.ui.welcome.fragment.GuidFragment;

/**
 * 作者/日期: admin on 2016/8/3.
 * 描述:ViewPager适配器
 */
public class GuideAdapter extends FragmentPagerAdapter {
    public GuideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GuidFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
