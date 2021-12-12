package com.xt37.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xt37.blog.entity.Article;
import com.xt37.blog.entity.ArticleVo;
import com.xt37.blog.entity.articleGroup;
import com.xt37.blog.mapper.ArticleMapper;
import com.xt37.blog.mapper.GroupMapper;
import com.xt37.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xt37
 * @since 2021-11-15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 文章存储
     * 按分组存储 如果没有当前分组新建一个分组然后进行存储
     *
     * @param article
     * @return
     */
    @Override
    public boolean saveArticle(ArticleVo article) {

        String groupBy = article.getGroupBy();
        String selectGroup = article.getSelectGroup();

        //判断是否是新增分组 如果是的话创建一个分组 如果不是 文章数+1
        if (groupBy != null || !"".equals(groupBy)) {
            articleGroup group = new articleGroup();
            group.setGroupName(groupBy);
            group.setCount(1);
            group.setStatus(1);
            group.setGmtCreate(new Date());
            groupMapper.insert(group);
        }
        //如果选择了分组进行分组的添加
        if (selectGroup != null || !"".equals(selectGroup)) {
            QueryWrapper<articleGroup> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.eq("group_name", selectGroup);
            List<articleGroup> articleGroups = groupMapper.selectList(groupQueryWrapper);
            articleGroups.forEach(s -> {
                System.out.println(s);
                s.setCount(s.getCount() + 1);
                groupMapper.updateById(s);
            });
        }
        Article newArticle = new Article();
        newArticle.setTitle(article.getTitle());
        newArticle.setGroupBy(selectGroup != null ? selectGroup : groupBy);
        newArticle.setContent(article.getContent());
        newArticle.setStatus(1);
        newArticle.setDescription(article.getDescription());
        newArticle.setGmtCreate(article.getGmtCreate());
        newArticle.setGmtModify(article.getGmtCreate());

        int insert = articleMapper.insert(newArticle);
        return insert == 0 ? false : true;
    }
}
