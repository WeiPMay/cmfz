package com.baizhi.service;

import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import com.baizhi.entity.Chapter;

public interface ChapterService {
    //分页
    ChapterPageDto queryByPage(Integer page, Integer rows,String albumId);

    //添加
    String insert(Chapter chapter,String albumId);

    //修改
    String update(Chapter chapter);

    //删除
    void delete(String id);

    void modifyPath(Chapter chapter);
}
