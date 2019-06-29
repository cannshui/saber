package dn.cs.saber.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import dn.cs.saber.dao.UserDao;
import dn.cs.saber.dao.mapper.ArticleRowMapper;
import dn.cs.saber.dao.mapper.CommentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import dn.cs.saber.dao.ArticleDao;
import dn.cs.saber.domain.Article;
import dn.cs.saber.domain.Comment;
import dn.cs.saber.domain.Tag;
import dn.cs.saber.vo.PageData;

/**
 * @author dn
 * @since 1.0.0
 */
@Repository
public class ArticleDaoImpl implements ArticleDao {

    private static final String SELECT_TAGS_SQL = "select * from tag order by random()";
    private static final String SELECT_ARTICLE_SQL = "select * from article where id = ?";
    private static final String SELECT_ARTICLE_COUNT_SQL = "select count(*) from article";
    private static final String SELECT_LIMIT_ARTICLE_SQL = "select * from article order by uts desc limit ?, ?";
    private static final String SELECT_LIMIT_ARTICLE_BY_CAT_SQL = "select * from article ${cat} order by uts desc limit ?, ?";
    private static final String INSERT_ARTICLE_SQL = "insert into article (title, author, preview, type, tag) values (?, ?, ?, ?, ?)";
    private static final String INCREASE_ARTICLE_HIT_COUNT_SQL = "update article set hit_count = hit_count + 1 where id = ?";
    private static final String INCREASE_ARTICLE_READ_COUNT_SQL = "update article set read_count = read_count + 1 where id = ?";
    private static final String INCREASE_ARTICLE_RATING_SQL = "update article set rating = round((rating * rating_count + ?) / (rating_count + 1), 1), rating_count = rating_count + 1 where id = ?";
    private static final String DECREASE_ARTICLE_RATING_SQL = "update article set rating = round((rating * rating_count - ?) / (rating_count - 1), 1), rating_count = rating_count - 1 where id = ?";
    private static final String SELECT_ARTICLE_COMMENTS_SQL = "select * from comment where article = ?";
    private static final String INSERT_ARTICLE_COMMENT_SQL = "insert into comment (article, user, content, reply) values (?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    private List<Tag> tags;

    @Override
    public Article getArticle(int id) {
        Article article = DataAccessUtils.singleResult(this.jdbcTemplate.query(SELECT_ARTICLE_SQL, new Object[]{id}, new ArticleRowMapper()));
        completeArticle(article);
        return article;
    }

    @Override
    public PageData<Article> getArticles(PageData<Article> pageData) {
        Integer total = this.jdbcTemplate.queryForObject(SELECT_ARTICLE_COUNT_SQL, Integer.class);
        if (total == 0) {
            return pageData;
        }
        List<Article> articles = this.jdbcTemplate.query(SELECT_LIMIT_ARTICLE_SQL, new Object[]{pageData.getIndex(), pageData.getSize()}, new ArticleRowMapper());
        articles.forEach(this::completeArticle);
        setArticleTags(articles);
        pageData.setTotal(total);
        pageData.setDatas(articles);
        return pageData;
    }

    @Override
    public PageData<Article> getPagedArticlesByCat(PageData<Article> pageData, Integer type, Integer tag) {
        String catSQL = " where";
        List<Object> args = new ArrayList<>(4);
        if (type != null) {
            catSQL = catSQL + " type = ?";
            args.add(type);
        }
        if (tag != null) {
            if (type != null) {
                catSQL = catSQL + " and";
            }
            catSQL = catSQL + " tag like ?";
            args.add(String.format("%%:%s:%%", tag));
        }
        Integer total = this.jdbcTemplate.queryForObject(SELECT_ARTICLE_COUNT_SQL + catSQL, args.toArray(), Integer.class);
        if (total == 0) {
            return pageData;
        }
        args.add(pageData.getIndex());
        args.add(pageData.getSize());
        List<Article> articles = this.jdbcTemplate.query(SELECT_LIMIT_ARTICLE_BY_CAT_SQL.replace("${cat}", catSQL), args.toArray(), new ArticleRowMapper());
        articles.forEach(this::completeArticle);
        setArticleTags(articles);
        pageData.setTotal(total);
        pageData.setDatas(articles);
        return pageData;
    }

    @Override
    public void addArticle(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_ARTICLE_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, article.getTitle());
            ps.setInt(2, article.getAuthor().getId());
            ps.setString(3, article.getPreview());
            ps.setInt(4, article.getType());
            ps.setString(5, ":" + article.getTag().replace(",", "::") + ":");
            return ps;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();
        article.setId(id);
        getTags();
    }

    @Override
    public void increaseHitCount(int id) {
        this.jdbcTemplate.update(INCREASE_ARTICLE_HIT_COUNT_SQL, id);
    }

    @Override
    public void increaseReadCount(int id) {
        this.jdbcTemplate.update(INCREASE_ARTICLE_READ_COUNT_SQL, id);
    }

    @Override
    public void increaseRating(int id, int rating) {
        this.jdbcTemplate.update(INCREASE_ARTICLE_RATING_SQL, rating, id);
    }

    @Override
    public void decreaseRating(int id, int rating) {
        this.jdbcTemplate.update(DECREASE_ARTICLE_RATING_SQL, rating, id);
    }

    @Override
    public void addComment(Comment comment) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_ARTICLE_COMMENT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comment.getArticle());
            ps.setInt(2, comment.getUser().getId());
            ps.setString(3, comment.getContent());
            ps.setInt(4, comment.getReply());
            return ps;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();
        comment.setId(id);
    }

    @Override
    public List<Comment> getArticleComments(int articleId) {
        List<Comment> comments = this.jdbcTemplate.query(SELECT_ARTICLE_COMMENTS_SQL, new Object[] { articleId }, new CommentRowMapper());
        comments.forEach(e -> e.setUser(this.userDao.getUser(e.getUserId())));

        Map<Integer, Comment> commentsMap = comments.stream().collect(Collectors.toMap(Comment::getId, Function.identity()));
        /* Parse reply relation. Traverse comments find a comment's replies. */
        List<Comment> relComments = new LinkedList<Comment>();
        for (Comment comment : comments) {
            /*
             * replyId ref to it's parent comment. If null, it's the first level
             * comment and a parent of some other comments. Or find its first
             * level comment and add self to that.
             */
            Integer replyId = comment.getReply();
            if (replyId == null) {
                relComments.add(comment);
                continue;
            }
            Comment parent;
            do {
                parent = commentsMap.get(replyId);
                replyId = parent.getReply();
                /*
                 * For a reply comment, there is only one parent. But for
                 * reusing, use Comment.replies field as parent comment. The
                 * size should be 1.
                 */
                List<Comment> parentList = new LinkedList<>();
                parentList.add(parent);
                comment.setReplies(parentList);
            } while (replyId != null);
            List<Comment> replies = parent.getReplies();
            if (replies == null) {
                replies = new ArrayList<>();
                parent.setReplies(replies);
            }
            replies.add(comment);
        }
        return relComments;
    }

    @PostConstruct
    @Override
    public List<Tag> getTags() {
        this.tags = this.jdbcTemplate.query(SELECT_TAGS_SQL, BeanPropertyRowMapper.newInstance(Tag.class));
        return this.tags;
    }

    @Override
    public List<Tag> getCachedTags() {
        return tags;
    }

    /**
     * Set tags for articles manually.
     */
    private void setArticleTags(List<Article> articles) {
        for (Article article : articles) {
            setArticleTag(article);
        }
    }

    private void completeArticle(Article article) {
        setArticleAuthor(article);
        setArticleTag(article);
    }

    /**
     * Set tags for a article manually.
     */
    private void setArticleAuthor(Article article) {
        article.setAuthor(this.userDao.getUser(article.getUserId()));
    }

    /**
     * Set tags for a article manually.
     */
    private void setArticleTag(Article article) {
        /*
         * Tricky, the tag must be :1::2::3::4::n:.
         * So after split("::?"), the first array el would always be null.
         * Do traverse from index 1.
         */
        String[] tagIds = article.getTag().split("::?");
        int l = tagIds.length;
        Tag[] aTags = new Tag[l - 1];
        for (int i = 1; i < l; i++) {
            int tagId = Integer.parseInt(tagIds[i]);
            for (Tag tag : tags) {
                if (tag.getId() == tagId) {
                    aTags[i - 1] = tag;
                }
            }
        }
        article.setTags(aTags);
    }

}
