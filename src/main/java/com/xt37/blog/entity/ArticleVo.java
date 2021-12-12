package com.xt37.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author xt37
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    //一句话描述
    private String description;

    private String content;

    private String title;

    //分组
    private String groupBy;

    //分组选择
    private String selectGroup;

    private Integer status;

    private Integer follow;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    private Date gmtModify;
}
