package com.generation.cricle.fragment;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.generation.cricle.activity.BlogDetailActivity;
import com.generation.cricle.activity.MainActivity;
import com.generation.cricle.activity.PublishActivity;
import com.generation.cricle.adapter.BlogAdapter;
import com.generation.cricle.base.BaseFragment;
import com.generation.cricle.entity.Blog;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.IView;
import com.generation.cricle.presenter.ExplorePresenter;
import com.generation.cxq.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ExploreFragment extends BaseFragment implements IView.ExploreView {

    @BindView(R.id.blog_list)
    RecyclerView blog_recycleView;
    @BindView(R.id.fab_add_blog)
    ImageButton add_button;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout smartRefreshLayout;


    List<Blog> blogList = new ArrayList<Blog>();
    BlogAdapter blogAdapter = new BlogAdapter();

    ExplorePresenter explorePresenter;

    Integer pageNum = 1;
    Integer pageSize = 5;

    long blogCount;  //记录最大页数
    BaseLoadMoreModule loadMoreModule;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_explore;
    }

    @Override
    public void initView(View view) { //初始化视图，被onCreateView包装
        explorePresenter = (ExplorePresenter) getModel();
        explorePresenter.getBlogs(pageNum, pageSize);
        blog_recycleView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        blog_recycleView.setAdapter(blogAdapter);


        //条目点击事件
        blogAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Toast.makeText(getContext(), "点击了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
                // 创建 Intent 对象
                Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
                blogAdapter.getItem(position);
                // 附加数据到 Intent
                intent.putExtra("blogData", blogAdapter.getData().get(position));

                // 启动目标页面
                startActivity(intent);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublishActivity.startActivity(getContext());
            }
        });

        //添加加载动画
        blogAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        // 获取加载更多模块
        loadMoreModule = blogAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (blogCount <= (long) (pageNum-1) *pageSize) {
                    loadMoreModule.loadMoreEnd();
                    return;
                }
                blog_recycleView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 触发加载更多数据的操作
                        Log.d("触底", "加载");
                        explorePresenter.getBlogs(pageNum, pageSize);
                    }
                }, 1000);


            }
        });

        // 设置下拉刷新监听器
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 在这里执行下拉刷新的操作，例如重新加载数据
                loadData();
            }
        });


    }

    @Override
    protected BasePresenter initModel() {
        return new ExplorePresenter(this);
    }


    @Override
    public void showBlogs(List<Blog> blogList, long blogCount) {
        this.blogCount = blogCount;     //最大数量，用于判断是否加载完成
        blogAdapter.addData(blogList);
        loadMoreModule.loadMoreComplete(); // 标记加载更多完成
        pageNum++;
    }
    //刷新数据
    private void loadData() {
        // 模拟加载数据的过程
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // 更新数据集
                blogList.clear();
                blogAdapter.setList(blogList);
                pageNum=1;
                explorePresenter.getBlogs(pageNum,pageSize);
                // 停止刷新动画
                smartRefreshLayout.finishRefresh();
            }
        });
    }

}
