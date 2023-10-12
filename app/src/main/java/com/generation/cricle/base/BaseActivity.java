package com.generation.cricle.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.BaseView;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    FragmentManager fragmentManager;

    protected BasePresenter basePresenter;

    protected abstract void initContentView(); //初始化视图

    protected abstract void initView(); //初始化页面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initContentView();  // 初始化View注入  //先初始化视图再初始化页面
            ButterKnife.bind(this);
            basePresenter = initModel();
            initView();

        } catch (Exception e) {
            Log.e("a", e.toString());
        }
    }
    public FragmentManager getBaseFragmentManager() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        return fragmentManager;
    }
    public FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }
    //打开fragment
    public void showFragment(Fragment fragment) {
        if (fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }

    //隐藏fragment
    public void hideFragment(Fragment fragment) {
        if (!fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
        }
    }

    protected abstract BasePresenter initModel();

    public BasePresenter getModel() { //接口作为函数参数或者返回值，必须传入或者返回这个接口的实例对象
        return basePresenter;
    }


}
