package com.baizhi.dto;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class AlbumPageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Album> rows;
}
