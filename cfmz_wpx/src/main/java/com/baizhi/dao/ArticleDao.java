package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    //分页
    List<Article> selectByPage(@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize);

    //查询总行数
    Integer selectTotalCount();

    //添加
    void insert(Article article);
    //修改
    void update(Article article);
    //删除
    void delete(String id);

    //根据id查一个
    Article queryOneById(String id);

}
