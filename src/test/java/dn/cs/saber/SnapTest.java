package dn.cs.saber;

import dn.cs.saber.dao.ArticleDao;
import dn.cs.saber.dao.UserDao;
import dn.cs.saber.domain.Article;
import dn.cs.saber.domain.User;
import dn.cs.saber.vo.PageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SnapTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void getArticleTest() {
        int id = 1;
        Article article = this.articleDao.getArticle(id);
        System.out.println(article);
    }

    @Test
    public void getArticlesTest() {
        PageData<Article> pageData = new PageData<>();
        pageData.setPage(1);
        pageData.setSize(10);
        this.articleDao.getArticles(pageData);
    }

    @Test
    public void getPagedArticlesByConditionTest() {
        PageData<Article> pageData = new PageData<>();
        pageData.setPage(1);
        pageData.setSize(10);
        this.articleDao.getPagedArticlesByCondition(pageData, 1, 1);
        pageData.getDatas().forEach(System.out::println);
        System.out.println(pageData.getTotal());
    }

    @Test
    public void getUserTest() {
        int id = 1;
        User user = this.userDao.getUser(id);
        System.out.println(user);
    }

    @Test
    public void updateTagCountsTest() {
        Map<Integer, Integer> tagCountMap = new HashMap<>();
        List<String> articleTags = this.jdbcTemplate.queryForList("select tag from article", String.class);
        articleTags.forEach(tag -> {
            String[] tagIds = tag.split("::?");
            IntStream
                    .range(1, tagIds.length)
                    .map(i -> Integer.parseInt(tagIds[i]))
                    .forEach(tagId -> tagCountMap.put(tagId, tagCountMap.getOrDefault(tagId, 0) + 1));
        });
        String updateSql = "update tag set count = ? where id = ?";
        tagCountMap.forEach((tagId, count) -> this.jdbcTemplate.update(updateSql, count, tagId));
    }

}