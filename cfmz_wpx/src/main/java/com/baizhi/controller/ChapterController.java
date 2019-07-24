package com.baizhi.controller;

import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;
    //分页查询
    @RequestMapping("queryByPage")
    public ChapterPageDto queryByPage(Integer page, Integer rows,String albumId){
        ChapterPageDto chapterPageDto = chapterService.queryByPage(page, rows, albumId);
        return chapterPageDto;
    }

    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper, String[] id,String albumId){
        if ("add".equals(oper)){
            String s=chapterService.insert(chapter,albumId);
            Album album = albumService.queryById(albumId);
            album.setCount(album.getCount()+1);
            albumService.update(album);
            return s;
        } else if ("edit".equals(oper)){
            //修改
           String update = chapterService.update(chapter);
            return update;
        }else if("del".equals(oper)){
            for (String s : id) {
                chapterService.delete(s);
                Album album = albumService.queryById(albumId);
                album.setCount(album.getCount()-1);
                albumService.update(album);
            }
        }
        return null;
    }

    @RequestMapping("fileupload")
    public void fileupload(HttpServletRequest request, MultipartFile downPath, HttpServletResponse response, String id) throws IOException {
        //获取原始文件名
        String originalFilename = downPath.getOriginalFilename();
        //获取文件目录的真实位置
        String path = request.getSession().getServletContext().getRealPath("music");
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        /*long size = downPath.getSize();
        Double aDouble = Double.valueOf(size);*/
        BigDecimal size = new BigDecimal(downPath.getSize());
        BigDecimal mod = new BigDecimal(1024);
        size=size.divide(mod).divide(mod).setScale(2,BigDecimal.ROUND_HALF_UP);
        double c=size.doubleValue();
        downPath.transferTo(new File(path,originalFilename));
        Chapter chapter = new Chapter();
        chapter.setDownPath(originalFilename);
        chapter.setId(id);
        chapter.setSize(c);
        chapterService.modifyPath(chapter);
    }
    @RequestMapping("download")
    public void download(String downPath, HttpServletRequest request,HttpServletResponse response) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("music");
        File file = new File(path, downPath);
        String substring = downPath.substring(downPath.lastIndexOf("."));
        response.setContentType(request.getSession().getServletContext().getMimeType(substring));
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(downPath,"UTF-8"));
        FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

    }
}
