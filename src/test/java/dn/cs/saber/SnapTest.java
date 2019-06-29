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
    public void getPagedArticlesByCatTest() {
        PageData<Article> pageData = new PageData<>();
        pageData.setPage(1);
        pageData.setSize(10);
        this.articleDao.getPagedArticlesByCat(pageData, null, 1);
        System.out.println(pageData.getSize());
    }

    @Test
    public void getUserTest() {
        int id = 1;
        User user = this.userDao.getUser(id);
        System.out.println(user);
    }

}
