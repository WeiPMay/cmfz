package com.baizhi.conf;

import com.baizhi.entity.Article;

import java.util.List;

public interface CustomizeRepository {
    //实现分页的方法
    List<Article> findByPageable(int page, int size);
    //term查询高亮
    List<Article> findByNameAndHighlightAndPageable(String name, int page, int size);
    //term查询高亮总数
    Integer findByNameAndHighlightAndPageableRecords(String name);
}
