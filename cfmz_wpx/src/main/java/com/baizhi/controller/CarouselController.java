package com.baizhi.controller;

import com.baizhi.dto.CarouselPageDto;
import com.baizhi.entity.Carousel;
import com.baizhi.service.CarouselService;
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
@RequestMapping("/carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;
    //查询所有
    @RequestMapping("queryAll")
    public List<Carousel> queryAll(){
        List<Carousel> carousels = carouselService.queryAll();
        return carousels;
    }
    @RequestMapping("edit")
    public String edit(Carousel carousel,String oper,String[] id){
        if ("add".equals(oper)){
            String s=carouselService.insertimg(carousel);
            return s;
        } else if ("edit".equals(oper)){
            //修改
            String update = carouselService.update(carousel);
            return update;
        }else if("del".equals(oper)){
            for (String s : id) {
                carouselService.remove(s);
            }
        }
        return null;
    }
    @RequestMapping("queryByPage")
    public CarouselPageDto queryByPage(Integer page, Integer rows){
        return carouselService.queryByPage(page,rows);
    }

    @RequestMapping("fileupload")
    public void fileupload(HttpServletRequest request,  MultipartFile imgpath, HttpServletResponse response,String id) throws IOException {
        //获取原始文件名
        String originalFilename = imgpath.getOriginalFilename();
        //获取文件目录的真实位置
        String path = request.getSession().getServletContext().getRealPath("carouselPic");
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        imgpath.transferTo(new File(path,originalFilename));
        Carousel carousel=new Carousel();
        carousel.setImgpath(originalFilename);
        carousel.setId(id);
        carouselService.modifyImgPath(carousel);

    }
}
