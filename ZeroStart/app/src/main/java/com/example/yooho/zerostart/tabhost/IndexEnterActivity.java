package com.example.yooho.zerostart.tabhost;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


import com.example.yooho.zerostart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yooho on 2016/10/17.
 *
 * 主要的点
 * 1、主xml布局文件,要有两个对象(ViewPage,FragmentTabHost(id固定android:id="@android:id/tabhost"))
 *
 * 2、mTabHost.setup(context, fragmentManager, containerID(就是viewPageId))
 * 3、TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tagString).setIndicator(view);
 * 4、mTabHost.addTab(tabSpec, Fragment.class)
 *
 * 5、如果想操作某一个tab: mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
 * 6、ViewPage.setAdapter(FragmentPageAdapter)
 * 7、mTabHost.getTabWidget().setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS) 强制父控件拦截点击事件
 */
public class IndexEnterActivity extends FragmentActivity {

    private ViewPager mViewPage;
    private FragmentTabHost mTabHost;
    private String[] mTextArray;
    private int[] mImageArray;
    private Class[] mFragmentArray;
    private LayoutInflater layoutInflater;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_enter);
        initData();
        initView();//初始化控件
        initPage();//初始化页面
    }

    private void initData() {
        mTextArray = new String[]{ "首页", "分类"};
        mImageArray = new int[]{R.drawable.index_enter_course_select, R.drawable.index_enter_mine_select};
        mFragmentArray = new Class[]{EnterCourseFragment.class, EnterMineFragment.class};
        mFragmentList = new ArrayList<>();
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(this);//加载布局管理器
        mViewPage = (ViewPager) findViewById(R.id.pager);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.pager);
        mViewPage.addOnPageChangeListener(new EnterViewPageAdapter());
        mTabHost.setOnTabChangedListener(new EnterTabChangeListener());

        for (int i = 0; i < mTextArray.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            mTabHost.setTag(i);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }
    }

    private void initPage() {
        EnterCourseFragment fragment1 = new EnterCourseFragment();
        EnterMineFragment fragment2 = new EnterMineFragment();

        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);

        //绑定Fragment适配器
        mViewPage.setAdapter(new MyEnterFragmentAdapter(getSupportFragmentManager(), mFragmentList));
//        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.enter_tab_content, null);
        ImageView mImageView = (ImageView) view.findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setImageDrawable(getResources().getDrawable(mImageArray[index]));
        mTextView.setText(mTextArray[index]);
        return view;
    }

    private class MyEnterFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;

        public MyEnterFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    private class EnterTabChangeListener implements TabHost.OnTabChangeListener {

        @Override
        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPage.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
        }
    }

    private class EnterViewPageAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置View覆盖子类控件而直接获得焦点
            mTabHost.setCurrentTab(position);//根据位置Postion设置当前的Tab
            widget.setDescendantFocusability(oldFocusability);//设置取消分割线
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
