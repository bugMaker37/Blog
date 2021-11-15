package com.xt37.blog.service.impl;

import com.xt37.blog.entity.User;
import com.xt37.blog.mapper.UserMapper;
import com.xt37.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xt37
 * @since 2021-11-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
