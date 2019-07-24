package com.baizhi.service;

import com.baizhi.dto.AlbumPageDto;
import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    List<Album> queryAll();
    //根据id查一个
    Album queryById(String albumId);

    //分页
    AlbumPageDto queryByPage(Integer page, Integer rows);

    //添加
    String insert(Album album);
    //删除
    void remove(String id);

    //修改
    void modifyImgPath(Album album);
    String update(Album album);

    //根据id修改一个
    void modifyOneById(Album album);
}
