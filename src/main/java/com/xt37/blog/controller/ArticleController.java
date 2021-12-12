package com.xt37.blog.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xt37.blog.entity.Article;
import com.xt37.blog.entity.ArticleVo;
import com.xt37.blog.entity.articleGroup;
import com.xt37.blog.mapper.ArticleMapper;
import com.xt37.blog.mapper.GroupMapper;
import com.xt37.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author xt37
 * @since 2021-11-15
 */
@Controller
public class ArticleController {

    @Autowired
    private ArticleMapper articleMapper;


    @Autowired
    private ArticleService articleService;

    @GetMapping("article")
    public String writeArticle() {
        return "MarkDown";
    }


    @GetMapping("PostArticle")
    public String PostToHtml() {
        return "showArticle";
    }

    @PostMapping("PostArticle")
    public void PostArticle(ArticleVo article,
                              Model model,
                              HttpServletResponse response) throws IOException {
        boolean save = articleService.saveArticle(article);
        if (save) {
            response.sendRedirect("/");
        }else {
            response.sendRedirect("PostArticle");
        }
    }


    @GetMapping("detailArticle/{id}")
    public String getArticle(
            @PathVariable String id,
            Model model) {
        Article article = articleMapper.selectById(id);


        model.addAttribute("article", article);

        return "showArticle";
    }


}

