package com.xt37.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xt37.blog.entity.articleGroup;
import com.xt37.blog.mapper.GroupMapper;
import com.xt37.blog.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class AjaxController {
    private final static String TODAY = "https://www.ipip5.com/today/api.php";

    @Autowired
    private GroupMapper groupMapper;

    /**
     * 那年今日
     * @return
     */
    @GetMapping("today")
    public JSONObject today() {
        HashMap<String, String> jsonType = new HashMap<>();
        jsonType.put("type", "json");
        String today = HttpClientUtils.doGet(TODAY, jsonType);
        System.out.println(today);
        JSONObject parseObject = JSONObject.parseObject(today);
        parseObject.remove(parseObject.size() - 2);
        return parseObject;
    }

    /**
     * 获取分组
     * @return
     */
    @GetMapping("getGroup")
    public List getGroup() {
        QueryWrapper<articleGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        List<articleGroup> articleGroups = groupMapper.selectList(wrapper);
        return articleGroups;
    }
}
