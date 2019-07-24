package com.baizhi.service;

import com.baizhi.dto.CarouselPageDto;
import com.baizhi.entity.Carousel;

import java.util.List;

public interface CarouselService {
    List<Carousel> queryAll();

    //添加
    String insertimg(Carousel carousel);

    //删除
    void remove(String id);

    //修改
    void modifyImgPath(Carousel carousel);
    String update(Carousel carousel);

    //分页
    CarouselPageDto queryByPage(Integer page,Integer rows);
}
