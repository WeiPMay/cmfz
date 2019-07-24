package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements Serializable {
    private String id;
    private String albumId;
    private String title;
    private Double size;
    private String downPath;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private String uploadTime;
}
