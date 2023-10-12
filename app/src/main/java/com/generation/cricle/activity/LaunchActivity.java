package com.generation.cricle.activity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.generation.cricle.base.BaseActivity;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cxq.R;

import butterknife.BindView;

public class LaunchActivity extends BaseActivity  {

    @BindView(R.id.progress)
    ProgressBar processBar;
    @Override
    protected void initContentView() {
        // 去掉标题栏
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // 去掉Activity上面的状态栏
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 设置状态栏为半透明状态
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // 设置导航栏为半透明状态
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        // 设置 Activity 的布局文件为 activity_launch.xml
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void initView() {
        processBar.setVisibility(View.VISIBLE);
        new Thread() {
            //开启一个线程
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);//休眠3秒
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //跳转主页面
                LoginActivity.startActivity(LaunchActivity.this);
//                processBar.setVisibility(View.INVISIBLE);
                finish();
            }
        }.start();
    }

    @Override
    protected BasePresenter initModel() {
        return null;
    }
}
