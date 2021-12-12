package com.xt37.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class articleGroup {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    private String groupName;
    private int status;
    private int count;
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date gmtModified;
}
