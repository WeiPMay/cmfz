package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dto.AlbumPageDto;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<Album> queryAll() {
        List<Album> albums = albumDao.selectAll();
        return albums;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public AlbumPageDto queryByPage(Integer page, Integer rows) {
        AlbumPageDto albumPageDto = new AlbumPageDto();
        albumPageDto.setPage(page);
        Integer totalCount =albumDao.selectTotalCount();
        albumPageDto.setTotal(totalCount%rows==0 ? totalCount/rows:totalCount/rows+1);
        albumPageDto.setRecords(totalCount);
        albumPageDto.setRows(albumDao.selectByPage(page,rows));
        return albumPageDto;
    }

    @Override
    public String insert(Album album) {
        String s = UUID.randomUUID().toString();
        album.setId(s);
        albumDao.insert(album);
        return s;
    }

    @Override
    public void remove(String id) {
        albumDao.delete(id);
    }

    @Override
    public void modifyImgPath(Album album) {
        albumDao.updateImgPath(album);
    }

    @Override
    public Album queryById(String albumId) {
        Album album = albumDao.selectById(albumId);
        return album;
    }

    @Override
    public void modifyOneById(Album album) {

        albumDao.updateOneById(album);
    }

    @Override
    public String update(Album album) {
        albumDao.update(album);
        return album.getId();
    }
}
