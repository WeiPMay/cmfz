package com.baizhi.controller;

import com.baizhi.dto.ArticlePageDto;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //分页查询
    @RequestMapping("queryByPage")
    public ArticlePageDto queryByPage(Integer page, Integer rows){
        ArticlePageDto articlePageDto = articleService.queryByPage(page, rows);
        return articlePageDto;
    }

    @RequestMapping("edit")
    public String edit(Article article, String oper, String[] id){

        if ("edit".equals(oper)){
            //修改
           String update = articleService.update(article);
            return update;
        }else if("del".equals(oper)){
            for (String s : id) {
                articleService.delete(s);
            }
        }
        return null;
    }
    @RequestMapping("addArticle")
    public void addArticle(String title,String content,Date publishTime){
        Article article = new Article();
        article.setGuruId(UUID.randomUUID().toString());
        article.setTitle(title);
        article.setContent(content);
        article.setPublishTime(publishTime);
        articleService.insert(article);
    }

    @RequestMapping("upload")
    public Map<String , Object> upload(MultipartFile file, HttpServletRequest request){
        String originalFilename = file.getOriginalFilename();
        String articlePic = request.getSession().getServletContext().getRealPath("articlePic");
        File file1 = new File(articlePic);
        if (!file1.exists()){
            file1.mkdir();
        }
        Map<String , Object> map = new HashMap<>();

        try {
            file.transferTo(new File(articlePic,originalFilename));
            map.put("error",0);
            map.put("url","http://localhost:7777/cmfz/articlePic/"+originalFilename);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("url","http://localhost:7777/cmfz/articlePic/"+originalFilename);
            return map;
        }

    }
    @RequestMapping("showAll")
    public Map<String , Object> showAll(HttpServletRequest request){
        String articlePic = request.getSession().getServletContext().getRealPath("articlePic");
        File file = new File(articlePic);
        String[] list = file.list();
        Map<String , Object> map = new HashMap<>();
        map.put("current_url","http://localhost:7777/cmfz/articlePic/");
        map.put("total_count",list.length);
        List<Object> lists = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            String s = list[i];
            Map<String , Object> map1 = new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            File file1 = new File(articlePic,s);
            long length = file1.length();
            map1.put("filesize",length);
            map1.put("is_photo",true);
            String substring = s.substring(s.lastIndexOf(".") + 1);
            map1.put("filetype",substring);
            map1.put("filename",s);
            map1.put("datetime",new Date());
            lists.add(map1);
        }
        map.put("file_list",lists);
        return map;
    }

    @RequestMapping("queryOneById")
    public Article queryOneById(String id){
        Article article = articleService.queryOneById(id);
        System.out.println(article);
        return article;
    }

}
