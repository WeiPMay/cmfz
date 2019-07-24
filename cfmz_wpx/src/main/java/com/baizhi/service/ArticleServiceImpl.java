package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.dto.ArticlePageDto;
import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    public ArticlePageDto queryByPage(Integer page, Integer rows) {
        ArticlePageDto articlePageDto = new ArticlePageDto();
        articlePageDto.setPage(page);
        Integer totalCount=articleDao.selectTotalCount();
        articlePageDto.setTotal(totalCount%rows==0 ? totalCount/rows:totalCount/rows+1);
        articlePageDto.setRecords(totalCount);
        articlePageDto.setRows(articleDao.selectByPage(page,rows));
        return articlePageDto;
    }

    @Override
    public String insert(Article article) {
        article.setId(UUID.randomUUID().toString());
        articleDao.insert(article);
        return article.getId();
    }

    @Override
    public String update(Article article) {
        articleDao.update(article);
        return article.getId();
    }

    @Override
    public void delete(String id) {
        articleDao.delete(id);
    }

    @Override
    public Article queryOneById(String id) {
        Article article = articleDao.queryOneById(id);
        return article;
    }
}
