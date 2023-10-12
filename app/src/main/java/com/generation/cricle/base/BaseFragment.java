package com.generation.cricle.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.BaseView;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements BaseView {
    private BaseActivity mActivity;
    private View mLayoutView;
    protected BasePresenter basePresenter;

    public BasePresenter getModel() {
        return basePresenter;
    }


    public abstract int getLayoutRes();
    public abstract void initView(View view);

    protected abstract BasePresenter initModel();

    public BaseActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }


    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        //绑定fragment布局
        return inflater.inflate(getLayoutRes(), container, false);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //20160727 修复该方法多次调用 bug
        if (mLayoutView != null) {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null) {
                parent.removeView(mLayoutView);
            }
        } else {
            mLayoutView = getCreateView(inflater, container);
            ButterKnife.bind(this, mLayoutView);
            basePresenter =initModel();
            initView(mLayoutView);     //初始化布局
        }
        return mLayoutView;
    }


}
