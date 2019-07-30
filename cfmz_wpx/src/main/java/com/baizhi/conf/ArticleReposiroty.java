package com.baizhi.conf;

import com.baizhi.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleReposiroty extends ElasticsearchRepository<Article,String> {
}
