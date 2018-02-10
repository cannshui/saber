package dn.cs.saber.dao;

import java.util.List;

import dn.cs.saber.domain.Article;
import dn.cs.saber.domain.Comment;
import dn.cs.saber.domain.Tag;
import dn.cs.saber.vo.PageData;

/**
 * Data Access Object for article.
 * <br> 1. Query articles.
 * <br> 2. Query comments of a article.
 * <br> 3. Add articles.
 * <br> 4. Add comments of a article.
 * <br> 5. Increase hit count, read count and rating.
 * <br> 6. Query all tags at a random order.
 * <br> 7. Cache tags, since it's no need to query tags every time
 * 			while showing them. Update the cache when `article` table
 * 			changed.
 * 
 * @author Nen Den
 */
public interface ArticleDao {

	/**
	 * Query all the articles in insertion order.
	 * **For Test Only**.
	 * @return
	 */
	List<Article> getArticles();

	/**
	 * Query paged articles. Order by update time DESC.
	 * 
	 * @param pageData
	 * @return
	 */
	PageData<Article> getArticles(PageData<Article> pageData);

	/**
	 * Query articles, filter with type and tag.
	 * 
	 * @param pageData
	 * @param type
	 * @param tag
	 * @return
	 */
	PageData<Article> getPagedArticlesByCat(PageData<Article> pageData, String type, String tag);

	/**
	 * Query the specific article by primary key id.
	 * 
	 * @param id
	 * @return
	 */
	Article getArticle(int id);

	/**
	 * Insert a article.
	 * 
	 * @param article
	 */
	void addArticle(Article article);

	/**
	 * Increase a article's hit count.
	 * 
	 * @param id
	 */
	void increaseHitCount(int id);

	/**
	 * Increase a article's read count.
	 * 
	 * @param id
	 */
	void increaseReadCount(int id);

	/**
	 * Re-compute a article's rating score. Increase.
	 * 
	 * @param id
	 * @param rating
	 */
	void increaseRating(int id, int rating);

	/**
	 * Re-compute a article's rating score. Decrease.
	 * 
	 * @param id
	 * @param rating
	 */
	void decreaseRating(int id, int rating);

	/**
	 * Query a article's all comments in the order of create time.
	 * <p>Reply relations were resolved.
	 * 
	 * @param articleId
	 * @return
	 */
	List<Comment> getArticleComments(int articleId);

	/**
	 * Insert a comment for a article.
	 * 
	 * @param comment
	 */
	void addComment(Comment comment);

	/**
	 * Query all tags from table. And cache the result.
	 * 
	 * @return
	 */
	public List<Tag> getTags();

	/**
	 * Just return the cached tags.
	 * 
	 * @return
	 */
	public List<Tag> getCachedTags();

}