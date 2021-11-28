package com.xt37.blog.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xt37.blog.entity.Article;
import com.xt37.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
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
public class ArticleController {

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("article")
    public String writeArticle() {
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
        article.setGroupBy(group);
        article.setTitle(title);

        int insert = articleMapper.insert(article);
        if (insert != 0) {
            model.addAttribute("article", article);
            return "showArticle";
        } else {
            return "index";
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

