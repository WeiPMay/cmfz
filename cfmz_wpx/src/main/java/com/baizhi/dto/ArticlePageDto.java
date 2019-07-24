package com.baizhi.dto;

import com.baizhi.entity.Article;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticlePageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Article> rows;
}
