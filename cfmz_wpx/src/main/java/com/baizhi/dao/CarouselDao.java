package com.baizhi.dao;

import com.baizhi.entity.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarouselDao {
    //查询所有轮播图
    List<Carousel> selectAll();

    //添加轮播图
    void insertimg(Carousel carousel);

    //删除
    void deleteimg(String id);

    //修改
    void updateImgPath(Carousel carousel);

    void update(Carousel carousel);

    //查询总行数
    Integer selectTotalCount();

    //分页
    List<Carousel> selectByPage(@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize);



}
