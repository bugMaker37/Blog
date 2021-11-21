package com.xt37.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.xt37.blog.entity.Article;
import com.xt37.blog.mapper.ArticleMapper;
import com.xt37.blog.utils.HttpClientUtils;
import lombok.extern.java.Log;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class IndexController {

    private final static String TODAY = "https://www.ipip5.com/today/api.php";
    private final static String TODAY2 = "http://route.showapi.com/119-42";

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("index")
    public String index(Model model) {
        List<Article> articles = articleMapper.selectList(null);
        HashMap<String, String> jsonType = new HashMap<>();
        jsonType.put("type", "json");
        String today = HttpClientUtils.doGet(TODAY, jsonType);
        JSONObject jsonObject = new JSONObject();
        System.out.println(today);
        JSONObject parseObject = JSONObject.parseObject(today);
        parseObject.remove(parseObject.size() - 1);

        model.addAttribute("articles", articles);
        model.addAttribute("today", parseObject);
        return "index";
    }

    @GetMapping("/")
    public String indexController(Model model) throws Exception {
        List<Article> articles = articleMapper.selectList(null);
        HashMap<String, String> jsonType = new HashMap<>();
        jsonType.put("type", "json");
        String today = HttpClientUtils.doGet(TODAY, jsonType);
        JSONObject jsonObject = new JSONObject();
        System.out.println(today);
        JSONObject parseObject = JSONObject.parseObject(today);
        parseObject.remove(parseObject.size() - 1);

        model.addAttribute("articles", articles);
        model.addAttribute("today", parseObject);
        return "index";
    }


}

