package com.generation.cricle.entity;
import java.util.List;

public class PageResult<T> {
    private List<Blog> blogList;
    private long blogCount;  //页数

    public List<Blog> getBlogList() {
        return blogList;
    }

    public long getBlogCount() {
        return blogCount;
    }
}
