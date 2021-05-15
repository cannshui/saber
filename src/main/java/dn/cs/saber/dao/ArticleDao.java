package dn.cs.saber.dao;

import java.util.List;

import dn.cs.saber.domain.Article;
import dn.cs.saber.domain.Comment;
import dn.cs.saber.domain.Tag;
import dn.cs.saber.vo.PageData;

/**
 * Data Access Object for article.
 *
 * @author dn
 * @since 1.0.0
 */
public interface ArticleDao {

    /**
     * Query paged articles. Order by update time DESC.
     *
     * @param pageData pageData
     * @return PageData
     */
    PageData<Article> getArticles(PageData<Article> pageData);

    /**
     * Query articles, filter with type and tag.
     *
     * @param pageData pageData
     * @param type     type
     * @param tag      tag
     * @return PageData
     */
    PageData<Article> getPagedArticlesByCondition(PageData<Article> pageData, Integer type, Integer tag);

    /**
     * Query the specific article by primary key id.
     *
     * @param id id
     * @return Article
     */
    Article getArticle(int id);

    /**
     * Insert a article.
     *
     * @param article article
     */
    void addArticle(Article article);

    /**
     * Increase a article's hit count.
     *
     * @param id id
     */
    void increaseHitCount(int id);

    /**
     * Increase a article's read count.
     *
     * @param id id
     */
    void increaseReadCount(int id);

    /**
     * Re-compute a article's rating score. Increase.
     *
     * @param id id
     * @param rating rating
     */
    void increaseRating(int id, int rating);

    /**
     * Re-compute a article's rating score. Decrease.
     *
     * @param id id
     * @param rating rating
     */
    void decreaseRating(int id, int rating);

    /**
     * Query a article's all comments in the order of create time.
     * <p>Reply relations were resolved.
     *
     * @param articleId articleId
     * @return List Comment
     */
    List<Comment> getArticleComments(int articleId);

    /**
     * Insert a comment for a article.
     *
     * @param comment comment
     */
    void addComment(Comment comment);


    /**
     * Get all supported tags in database.
     *
     * @return Tag List
     */
    List<Tag> getAllTags();

    /**
     * Query all tags from table. And cache the result.
     *
     * @return Tag List
     */
    List<Tag> getTags();

    /**
     * Just return the cached tags.
     *
     * @return Tag List
     */
    List<Tag> getCachedTags();

}
