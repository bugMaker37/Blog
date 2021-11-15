package com.xt37.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableField("userId")
    private String userId;

    @ApiModelProperty(value = "用户名")
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "用户密码 转MD5后存入")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "权限等级")
    private Integer jurisdiction;

    @ApiModelProperty(value = "是否删除 0或1")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date gmtGreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
