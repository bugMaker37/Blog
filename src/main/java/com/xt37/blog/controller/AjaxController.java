package com.xt37.blog.controller;

import com.alibaba.fastjson.JSONArray;
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
    private final static String Weather = "0f96dcd6dca349b799e3ee022a8ed427";
    private final static String getCity = "https://geoapi.qweather.com/v2/city/lookup";
    private final static String getWeather = "https://devapi.qweather.com/v7/weather/now";


    @Autowired
    private GroupMapper groupMapper;

    /**
     * 那年今日
     *
     * @return
     */
    @GetMapping("today")
    public JSONObject today() {
        HashMap<String, String> jsonType = new HashMap<>();
        jsonType.put("type", "json");
        String today = HttpClientUtils.doGet(TODAY, jsonType);
        JSONObject parseObject = JSONObject.parseObject(today);
        parseObject.remove(parseObject.size() - 2);
        return parseObject;
    }

    /**
     * 获取分组
     *
     * @return
     */
    @GetMapping("getGroup")
    public List getGroup() {
        QueryWrapper<articleGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        List<articleGroup> articleGroups = groupMapper.selectList(wrapper);
        return articleGroups;
    }

    /**
     * 获取天气详细信息
     *
     * @return
     */
    @GetMapping("weather")
    public JSONArray getWeather(String city) {
        HashMap<String, String> weather = new HashMap<>();
        if (city == null || "".equals(city)) {
            city = "101280601";
            weather.put("location", city);
        }
        weather.put("key", Weather);
        JSONObject jsonObject = JSONObject.parseObject(HttpClientUtils.doGet(getWeather, weather));
        JSONArray now = jsonObject.getJSONArray("now");
        return now;
    }

    /**
     * 获取城市详细信息  用于获取天气信息
     * todo  获取城市进行查询
     */
    @GetMapping("getCity")
    public static JSONArray getCity(String city) {
        HashMap<String, String> weather = new HashMap<>();
        weather.put("location", city);
        weather.put("key", Weather);
        JSONObject jsonObject = JSONObject.parseObject(HttpClientUtils.doGet(getCity, weather));
        if ("200".equals(jsonObject.getString("code"))) {
            JSONArray location = jsonObject.getJSONArray("location");
            return location;
        }
        return null;
    }

    public static void main(String[] args) {
        JSONArray city = AjaxController.getCity("中国");
        System.out.println(city);

    }

}
