package com.baizhi.dto;

import com.baizhi.entity.Chapter;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ChapterPageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Chapter> rows;
}
