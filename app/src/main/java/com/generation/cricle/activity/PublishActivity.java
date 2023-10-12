package com.generation.cricle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.generation.cricle.base.BaseActivity;
import com.generation.cricle.essential.BasePresenter;
import com.generation.cricle.essential.IView;
import com.generation.cricle.presenter.PublishPresenter;
import com.generation.cricle.util.SelfUser;
import com.generation.cxq.R;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import butterknife.BindView;

public class PublishActivity extends BaseActivity implements IView.PublishView {
    @BindView(R.id.pub_title)
    EditText title;
    @BindView(R.id.pub_content)
    EditText content;
    @BindView(R.id.pub_image)
    ImageView image;
    @BindView(R.id.pub_select_image)
    Button select_image;
    @BindView(R.id.publish)
    Button publish;

    PublishPresenter publishPresenter;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_add_blog);
        setTitle("发布帖子"); // 设置标题

    }

    @Override
    protected void initView() {
        publishPresenter = (PublishPresenter) initModel();
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(view.getContext())
                        .openSystemGallery(SelectMimeType.ofImage())
                        .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(ArrayList<LocalMedia> result) {
                                if (result != null && result.size() > 0) {
                                    LocalMedia localMedia = result.get(0);  //获取第一张图片
                                    String imagePath = localMedia.getRealPath(); // 获取本地图片的路径
//                                    Uri imageUri = localMedia.getUri(); // 获取图片的URI

                                    // 在这里可以使用图片路径或URI进行后续操作，比如加载图片、显示图片等
                                    Log.e("选择成功", "ok");
                                    Glide.with(view.getContext())  // 传入上下文
                                            .load(imagePath)  // 传入图片的 URL 或本地文件路径
                                            .into(image); // 传入要设置图片的 CircleImageView 对象
                                }
                            }
                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先上传图片到云端

                //再插入新帖
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("title", title.getText());
                    jsonObject.put("userId", SelfUser.getInstance().getCurrentUser().getId());
                    jsonObject.put("content", content.getText());
                    jsonObject.put("createdAt", new Timestamp(System.currentTimeMillis()));
                    // jsonObject.put("pictureList", pictureList); // 如果有图片列表，可以添加对应的字段
                    publishPresenter.addBlog(jsonObject.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

    @Override
    protected BasePresenter initModel() {
        return new PublishPresenter(this);
    }

    @Override
    public void successAdd() {
        Toast.makeText(this, "发帖成功", Toast.LENGTH_LONG).show();

        // 创建一个Handler对象
        Handler handler = new Handler(Looper.getMainLooper());

        // 延迟0.5秒后执行跳转操作
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                // 执行你的跳转逻辑
            }
        }, 800); // 延迟时间，单位为毫秒
    }
}
