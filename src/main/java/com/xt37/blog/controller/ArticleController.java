package com.xt37.blog.controller;


import com.xt37.blog.entity.Article;
import com.xt37.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author xt37
 * @since 2021-11-15
 */
@Controller
@RequestMapping("/blog")
public class ArticleController {

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("article")
    public String getArticle() {
        return "MarkDown";
    }

    @GetMapping("PostArticle")
    public String PostToHtml() {
        return "showArticle";
    }

    @PostMapping("PostArticle")
    public String PostArticle(@RequestParam String content,
                              @RequestParam String title,
                              @RequestParam String group,
                              Model model) {

        System.out.println(content);
        Article article = new Article();
        article.setContent(content);
        article.setPart(group);
        article.setTitle(title);

        int insert = articleMapper.insert(article);
        if (insert != 0) {
            model.addAttribute("article", article);
            return "showArticle";
        }else {
            return "index";
        }
    }

}

