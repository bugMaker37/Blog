package com.xt37.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xt37.blog.entity.Article;
import com.xt37.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("index")
    public String index(Model model) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modify");
        wrapper.eq("status",1);
        List<Article> articles = articleMapper.selectList(wrapper);
        model.addAttribute("articles", articles);

        return "index";
    }

    @GetMapping("/")
    public String indexController(Model model) throws Exception {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modify");
        wrapper.eq("status",1);
        List<Article> articles = articleMapper.selectList(wrapper);
        model.addAttribute("articles", articles);
        return "index";
    }



}

