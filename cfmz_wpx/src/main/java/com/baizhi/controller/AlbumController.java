package com.baizhi.controller;

import com.baizhi.dto.AlbumPageDto;
import com.baizhi.dto.CarouselPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    //查询所有
    @RequestMapping("queryAll")
    public List<Album> queryAll() {
        List<Album> albums = albumService.queryAll();
        return albums;
    }
    //分页查询
    @RequestMapping("queryByPage")
    public AlbumPageDto queryByPage(Integer page, Integer rows){
        return albumService.queryByPage(page,rows);
    }

    @RequestMapping("edit")
    public String edit(Album album, String oper, String[] id){
        if ("add".equals(oper)){
            String s=albumService.insert(album);
            return s;
        } else if ("edit".equals(oper)){
            //修改
            String update = albumService.update(album);
            return  update;
        }else if("del".equals(oper)){
            for (String s : id) {
                albumService.remove(s);
            }
        }
        return null;
    }

    @RequestMapping("fileupload")
    public void fileupload(HttpServletRequest request, MultipartFile cover, HttpServletResponse response, String id) throws IOException {
        //获取原始文件名
        String originalFilename = cover.getOriginalFilename();
        if (originalFilename!=null&&originalFilename.length()!=0) {
            //获取文件目录的真实位置

            String path = request.getSession().getServletContext().getRealPath("albumPic");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            cover.transferTo(new File(path, originalFilename));
            Album album = new Album();
            album.setCover(originalFilename);
            album.setId(id);
            albumService.modifyImgPath(album);
        }
    }

}
