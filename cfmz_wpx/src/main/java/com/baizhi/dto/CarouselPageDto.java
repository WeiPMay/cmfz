package com.baizhi.dto;

import com.baizhi.entity.Carousel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class CarouselPageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Carousel> rows;

}
