package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    public ChapterPageDto queryByPage(Integer page, Integer rows,String albumId) {
        ChapterPageDto chapterPageDto = new ChapterPageDto();
        chapterPageDto.setPage(page);
        Integer totalCount=chapterDao.selectTotalCount();
        chapterPageDto.setTotal(totalCount%rows==0 ? totalCount/rows:totalCount/rows+1);
        chapterPageDto.setRecords(totalCount);
        chapterPageDto.setRows(chapterDao.selectByPage(page,rows,albumId));
        return chapterPageDto;
    }

    @Override
    public String insert(Chapter chapter,String albumId) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setAlbumId(albumId);
        chapterDao.insert(chapter);
        return chapter.getId();
    }

    @Override
    public String update(Chapter chapter) {
        chapterDao.update(chapter);
        return chapter.getId();
    }

    @Override
    public void delete(String id) {
        chapterDao.delete(id);
    }

    @Override
    public void modifyPath(Chapter chapter) {
        chapterDao.updatePath(chapter);

    }
}
