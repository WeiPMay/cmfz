package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {
    private String id;
    private String guruId;
    private String title;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date publishTime;
}
