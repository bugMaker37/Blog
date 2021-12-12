package com.xt37.blog.mapper;

import com.xt37.blog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xt37
 * @since 2021-11-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
