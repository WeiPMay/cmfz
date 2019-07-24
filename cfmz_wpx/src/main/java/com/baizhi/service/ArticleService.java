package com.baizhi.service;

import com.baizhi.dto.ArticlePageDto;
import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;

public interface ArticleService {
    //分页
    ArticlePageDto queryByPage(Integer page, Integer rows);

    //添加
    String insert(Article article);

    //修改
    String update(Article article);

    //删除
    void delete(String id);

    //根据id查一个
    Article queryOneById(String id);

}
