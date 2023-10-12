# 创小圈校园资讯APP

该项目是一个校园资讯平台，为校园用户提供发帖、评论和接入ChatGPT多会话聊天等功能。
此项目还在持续更新中，目前只实现部分功能

## 功能特点

- 用户管理：包括用户登录和注册功能。
- 发帖功能：用户可以发布校园资讯和帖子。
- 评论功能：用户可以对帖子进行评论和讨论。
- ChatGPT接入：项目集成了ChatGPT，用户可以与ChatGPT进行对话交互。


## 依赖项

以下是项目所依赖的一些库和组件：

- Google Material库：`implementation 'com.google.android.material:material:1.2.1'`
- PictureSelector库：`implementation 'io.github.lucksiege:pictureselector:v3.11.1'`
- BaseRecyclerViewAdapterHelper库：`implementation "io.github.cymchad:BaseRecyclerViewAdapterHelper:3.0.14"`
- ButterKnife库：`implementation 'com.jakewharton:butterknife:10.0.0'`
- BottomNavigationBar库：`implementation 'com.ashokvarma.android:bottom-navigation-bar:2.2.0'`
- SmartRefreshLayout库：`implementation 'io.github.scwang90:refresh-layout-kernel:2.0.5'` 和 `implementation 'io.github.scwang90:refresh-header-classics:2.0.5'`
- 轮播图组件：`implementation 'com.youth.banner:banner:1.4.9'`
- Retrofit库：`implementation 'com.squareup.okhttp3:okhttp:3.14.9'`, `implementation 'com.squareup.okhttp3:logging-interceptor:3.9.0'`, `implementation 'com.squareup.retrofit2:retrofit:2.9.0'`, `implementation 'com.squareup.retrofit2:converter-gson:2.9.0'`, `implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'`, `implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'`, `implementation 'io.reactivex.rxjava2:rxjava:2.2.20'`
- CircleImageView库：`implementation 'de.hdodenhof:circleimageview:3.1.0'`
- EventBus库：`implementation("org.greenrobot:eventbus:3.3.1")`
- RxPermissions库：`implementation 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar'`, `implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'`, `implementation "io.reactivex.rxjava2:rxjava:2.0.0"`
- Glide库：`implementation 'com.github.bumptech.glide:glide:4.12.0'`, `annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'`, `implementation("com.github.bumptech.glide:okhttp3-integration:4.11.0") { transitive = false }`
- AndroidX库：`implementation 'androidx.appcompat:appcompat:1.4.1'`, `implementation 'androidx.constraintlayout:constraintlayout:2.1.3'`, `implementation 'com.google.android.material:material:1.5.0'`

请注意，这只是一个示例的README文件结构，你可以根据你的项目实际情况进行适当的修改和扩展。确保在README文件中提供足够的信息，使其他人能够了解和使用你的项目。
