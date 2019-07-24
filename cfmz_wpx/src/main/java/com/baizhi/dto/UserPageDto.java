package com.baizhi.dto;

import com.baizhi.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserPageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<User> rows;
}
