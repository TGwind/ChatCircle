package com.generation.cricle.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.generation.cricle.base.BaseActivity;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.fragment.ConversationFragment;
import com.generation.cricle.fragment.ExploreFragment;
import com.generation.cricle.fragment.UserFragment;
import com.generation.cxq.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    private int lastSelectedPosition; // 记录上一个选中的位置
    private ExploreFragment exploreFragment;    // 首页fragment
    private UserFragment userFragment;  // 用户fragment
    private ConversationFragment conversationFragment;  // AI会话
    FragmentManager fragmentManager;    // fragment管理器

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        initNavigationBar();    // 初始化底部导航栏
        setDefaultFragment();   // 设置默认fragment
    }

    // 初始化底部导航栏
    private void initNavigationBar() {
        lastSelectedPosition = 0;   // 初始化上一个选中的位置为0
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED); // 设置底部导航栏模式为固定
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC); // 设置底部导航栏背景样式为静态
        mBottomNavigationBar.setBarBackgroundColor(R.color.bottom_actation); // 设置底部导航栏背景颜色
        mBottomNavigationBar // 初始化底部导航栏，设置每个按钮样式属性
                .addItem(new BottomNavigationItem(R.drawable.play_selected_icon, R.string.firstFragment).setActiveColorResource(R.color.button_yellow).setInactiveIconResource(R.drawable.play_unselected_icon).setInActiveColorResource(R.color.bottom_text))
                .addItem(new BottomNavigationItem(R.drawable.explore_selected_icon, R.string.secondFragment).setActiveColorResource(R.color.button_yellow).setInactiveIconResource(R.drawable.explore_unselected_icon).setInActiveColorResource(R.color.bottom_text))
                .addItem(new BottomNavigationItem(R.drawable.setting_selected_icon, R.string.thirdFragment).setActiveColorResource(R.color.button_yellow).setInactiveIconResource(R.drawable.setting_unselected_icon).setInActiveColorResource(R.color.bottom_text))
                .setFirstSelectedPosition(lastSelectedPosition) // 设置默认选中的位置
                .initialise(); // 初始化底部导航栏
        mBottomNavigationBar.setTabSelectedListener(this);  // 设置点击导航栏的监听器
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;    // 记录当前选中的位置
        hideFragment(); // 隐藏所有fragment
        switch (position) {
            case 0:   // 小圈广场
                if (exploreFragment == null) {   // 如果首页fragment为空，则新建一个
                    exploreFragment = new ExploreFragment();
                    addFragment(R.id.fragment_content, exploreFragment); // 添加到fragment容器中
                } else {
                    showFragment(exploreFragment);    // 否则显示该fragment
                }
                break;

            case 2: // user
                if (userFragment == null) { // 如果用户fragment为空，则新建一个
                    userFragment = new UserFragment();
                    addFragment(R.id.fragment_content, userFragment); // 添加到fragment容器中
                } else {
                    showFragment(userFragment); // 否则显示该fragment
                }
                break;
            case 1: // 其他
                if (conversationFragment == null) { // 如果用户fragment为空，则新建一个
                    conversationFragment = new ConversationFragment();
                    addFragment(R.id.fragment_content, conversationFragment); // 添加到fragment容器中
                } else {
                    showFragment(conversationFragment); // 否则显示该fragment
                }
                break;

        }

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    // 隐藏所有fragment
    private void hideFragment() {
        if (userFragment != null) {
            hideFragment(userFragment);
        }
        if (exploreFragment != null) {
            hideFragment(exploreFragment);
        }
        if (conversationFragment != null) {
            hideFragment(conversationFragment);
        }
    }

    // 设置默认Fragment
    private void setDefaultFragment() {
        exploreFragment = getInfoFragment();  // 获取首页fragment
        addFragment(R.id.fragment_content, exploreFragment); // 添加到fragment容器中
    }

    // 获取首页fragment
    private ExploreFragment getInfoFragment() {
        return new ExploreFragment();
    }

    // 添加fragment到fragment容器中
    public void addFragment(int res, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment);
        fragmentTransaction.commit();
    }

    // 获取fragment事务
    public FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }

    // 获取fragment管理器
    public FragmentManager getBaseFragmentManager() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        return fragmentManager;
    }

    @Override
    protected BasePresenter initModel() {
        return null;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
