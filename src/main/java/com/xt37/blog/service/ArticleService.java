package com.xt37.blog.service;

import com.xt37.blog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xt37.blog.entity.ArticleVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xt37
 * @since 2021-11-15
 */
@Service
public interface ArticleService extends IService<Article> {

    boolean saveArticle(ArticleVo article);

}
