package com.xt37.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xt37.blog.entity.User;
import com.xt37.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public void login(User user,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userName", user.getUserName());
        wrapper.eq("password", user.getPassword());
        wrapper.eq("is_delete", 1);
        User selectOne = userMapper.selectOne(wrapper);
        if (selectOne != null) {
            Cookie cookie = new Cookie("user", user.getUserName() + user.getJurisdiction());
            request.getSession().setAttribute("user", user);
            response.addCookie(cookie);
            response.sendRedirect("/");
        }
    }
}
