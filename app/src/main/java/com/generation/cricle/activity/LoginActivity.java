package com.generation.cricle.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.generation.cricle.base.BaseActivity;
import com.generation.cricle.entity.User;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.IView;
import com.generation.cricle.presenter.LoginPresenter;
import com.generation.cxq.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements IView.LoginView {

    // 绑定视图控件
    @BindView(R.id.buttonLogin)
    Button button_login;
    @BindView(R.id.editTextUsername)
    EditText editText_userName;
    @BindView(R.id.editTextPassword)
    EditText editText_password;

    // 登录Presenter
    LoginPresenter loginPresenter;

    // 初始化Activity的布局文件
    @Override
    protected void initContentView() {
        // 去掉标题栏
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置状态栏为半透明状态
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // 设置导航栏为半透明状态
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        // 设置 Activity 的布局文件为 activity_launch.xml
        setContentView(R.layout.activity_login);
    }

    // 初始化视图控件
    @Override
    protected void initView() {
        // 获取presenter
        loginPresenter = (LoginPresenter) getModel();
    }

    // Activity启动时设置登录按钮的点击事件
    @Override
    protected void onStart() {
        super.onStart();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 将用户名和密码封装成JSON格式的字符串
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("name",editText_userName.getText().toString());
                    jsonObject.put("password",editText_password.getText().toString());
                    String str=jsonObject.toString();

                    // 调用登录Presenter的方法进行登录验证
                    loginPresenter.checkLogin(str);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // 初始化登录Presenter
    @Override
    protected BasePresenter initModel() {
        return new LoginPresenter(this);
    }

    // 启动LoginActivity
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    // 登录成功的回调方法
    @Override
    public void successLogin() {
        Toast.makeText(this, "验证成功", Toast.LENGTH_SHORT).show();
        MainActivity.startActivity(this);
        finish();
    }

    // 登录失败的回调方法
    @Override
    public void failLogin() {
        Toast.makeText(this, "验证失败", Toast.LENGTH_SHORT).show();
    }

    // 在这里可以添加其他生命周期方法和自定义方法
}
