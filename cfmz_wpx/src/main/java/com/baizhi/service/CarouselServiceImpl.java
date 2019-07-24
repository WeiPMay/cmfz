package com.baizhi.service;

import com.baizhi.dao.CarouselDao;
import com.baizhi.dto.CarouselPageDto;
import com.baizhi.entity.Carousel;
import com.baizhi.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselDao carouselDao;
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<Carousel> queryAll() {
        List<Carousel> carousels = carouselDao.selectAll();
        return carousels;
    }

    @Override
    public String insertimg(Carousel carousel) {
        String s = UUID.randomUUID().toString();
        carousel.setId(s);
        carouselDao.insertimg(carousel);
        return s;
    }

    @Override
    public void remove(String id) {
        carouselDao.deleteimg(id);
    }

    @Override
    public void modifyImgPath(Carousel carousel) {
        carouselDao.updateImgPath(carousel);
    }

    @Override
    public String update(Carousel carousel) {
        carouselDao.update(carousel);
        return  carousel.getId();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public CarouselPageDto queryByPage(Integer page, Integer rows) {
        CarouselPageDto carouselPageDto = new CarouselPageDto();
        carouselPageDto.setPage(page);
        Integer totalCount =carouselDao.selectTotalCount();
        carouselPageDto.setTotal(totalCount%rows==0 ? totalCount/rows:totalCount/rows+1);
        carouselPageDto.setRecords(totalCount);
        carouselPageDto.setRows(carouselDao.selectByPage(page,rows));
        return carouselPageDto;
    }
}
