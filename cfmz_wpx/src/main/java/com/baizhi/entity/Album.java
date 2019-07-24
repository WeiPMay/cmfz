package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {
    private String id;//编号
    private String title;//专辑名称
    private String cover;//专辑封面
    private Integer count;//章节数量
    private Integer score;//专辑得分
    private String author;//专辑作者
    private String broadcast;//播音员
    private String brief;//专辑简介
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date publishTime;//出版时间

}
