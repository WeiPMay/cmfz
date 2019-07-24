package com.baizhi.dao;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    //分页
    List<Chapter> selectByPage(@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize,@Param("albumId") String albumId);

    //查询总行数
    Integer selectTotalCount();

    //添加
    void insert(Chapter chapter);
    //修改
    void update(Chapter chapter);
    //删除
    void delete(String id);

    void updatePath(Chapter chapter);
}
