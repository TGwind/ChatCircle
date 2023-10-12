package com.generation.cricle.retrofit;

import com.generation.cricle.entity.Blog;
import com.generation.cricle.entity.Comment;
import com.generation.cricle.entity.PageResult;
import com.generation.cricle.entity.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

//retrofit定义接口
public interface ApiService {
    //    @GET("")里面一定要有数据，不能为@GET 或者@GET("")，没有就加 /
    @GET
    Call<String> getSentence(@Url String url);     //每日一言
    @GET("api/getBlogs/")
    Observable<Result<PageResult<Blog>>> getBlogs(@Query("pageNum") Integer pageNum, @Query("pageSize") Integer pageSize);
    @POST("api/login")
    Observable<Result<User>> login(@Body String body);
    @POST("api/addBlog")
    Observable<Result<String>> addBlog(@Body String body);
    // 添加其他接口方法...

    @GET("api/getComments")
    Observable<Result<List<Comment>>> getBlogComments(@Query("blogId") Long blogId);
}