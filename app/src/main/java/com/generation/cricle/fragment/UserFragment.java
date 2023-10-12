package com.generation.cricle.fragment;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.generation.cricle.base.BaseFragment;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.IView;
import com.generation.cricle.presenter.UserPresenter;
import com.generation.cricle.util.SelfUser;
import com.generation.cxq.R;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends BaseFragment implements IView.UserView {

    @BindView(R.id.profile_image)
    CircleImageView profile_image;

    @BindView(R.id.Sentence)
    TextView textSentence;
    @BindView(R.id.profile_name)
    TextView profile_name;

    UserPresenter userPresenter;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(View view) {
        userPresenter = (UserPresenter) initModel();
        userPresenter.getDaySentence();
        //设置头像
        Glide.with(this)  // 传入上下文
                .load(SelfUser.getInstance().getCurrentUser().getAvatar())  // 传入图片的 URL 或本地文件路径
                .into(profile_image); // 传入要设置图片的 CircleImageView 对象
        //设置昵称
        profile_name.setText(SelfUser.getInstance().getCurrentUser().getName());
    }

    @Override
    protected BasePresenter initModel() {
        return new UserPresenter(this);
    }


    @Override
    public void showSentence(String s) {
        textSentence.setText(s);
    }
}