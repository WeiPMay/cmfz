package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //查询所有
    List<Album> selectAll();

    //分页
    List<Album> selectByPage(@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize);

    //查询总行数
    Integer selectTotalCount();

    //根据id查一个
    Album selectById(String albumId);

    //添加
    void insert(Album album);

    //删除
    void delete(String id);

    //修改
    void updateImgPath(Album album);

    void update(Album album);

    //根据id修改一个
    void updateOneById(Album album);
}
