package dn.cs.saber.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import dn.cs.saber.dao.ArticleDao;
import dn.cs.saber.dao.BaseDao;
import dn.cs.saber.domain.Article;
import dn.cs.saber.domain.Comment;
import dn.cs.saber.domain.Tag;
import dn.cs.saber.domain.User;
import dn.cs.saber.vo.PageData;

@Repository
public class ArticleDaoImpl extends BaseDao implements ArticleDao {

	private List<Tag> tags;

	@Override
	public Article getArticle(int id) {
		Article article = super.get("getArticle", id);
		setArticleTag(article);
		return article;
	}

	@Override
	public List<Article> getArticles() {
		List<Article> articles = super.getList("getArticles");
		setArticleTags(articles);
		return articles;
	}

	@Override
	public List<Comment> getArticleComments(int articleId) {
		List<Comment> comments = super.getList("getArticleComments", articleId);

		Map<Integer, Comment> commentsMap = new HashMap<Integer, Comment>();
		int s = comments.size();
		for (int i = 0; i < s; i++) {
			Comment comment = comments.get(i);
			commentsMap.put(comment.getId(), comment);
		}
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
				List<Comment> parentList = new LinkedList<Comment>();
				parentList.add(parent);
				comment.setReplies(parentList);
			} while (replyId != null);
			List<Comment> replies = parent.getReplies();
			if (replies == null) {
				replies = new ArrayList<Comment>();
				parent.setReplies(replies);
			}
			replies.add(comment);
		}
		return relComments;
	}

	@Override
	public PageData<Article> getPagedArticlesByCat(PageData<Article> pageData,
			String type, String tag) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("tag", tag);
		Integer total = super.get("getPagedArticlesByCatNumber", paramMap);

		paramMap.put("index", pageData.getIndex());
		paramMap.put("size", pageData.getSize());
		List<Article> articles = super.getList("getPagedArticlesByCat",
				paramMap);
		setArticleTags(articles);
		pageData.setTotal(total);
		pageData.setDatas(articles);
		return pageData;
	}

	@Override
	public void addArticle(Article article) {
		int id = super.add("insertArticle", article);
		article.setId(id);
		getTags();
	}

	@Override
	public void addComment(Comment comment) {
		User user = comment.getUser();
		if (user == null || user.getId() < 0) {
			throw new IllegalArgumentException(
					"Comment must come from a existed user.");
		}
		Integer id = super.add("insertComment", comment);
		comment.setId(id);
	}

	@Override
	public PageData<Article> getArticles(PageData<Article> pageData) {
		int total = (Integer) this.getSqlMapClientTemplate().queryForObject(
				"getArticlesNumber");
		List<Article> articles = super.getList("getPagedArticles", pageData);
		setArticleTags(articles);
		pageData.setTotal(total);
		pageData.setDatas(articles);
		return pageData;
	}

	@Override
	public void increaseHitCount(int id) {
		super.update("increaseHitCount", id);
	}

	@Override
	public void increaseReadCount(int id) {
		super.update("increaseReadCount", id);
	}

	@Override
	public void increaseRating(int id, int rating) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("id", id);
		paramMap.put("rating", rating);
		super.update("increaseRating", paramMap);
	}

	@Override
	public void decreaseRating(int id, int rating) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("id", id);
		paramMap.put("rating", rating);
		super.update("decreaseRating", paramMap);
	}

	@PostConstruct
	@Override
	public List<Tag> getTags() {
		tags = super.getList("getTags");
		return tags;
	}

	@Override
	public List<Tag> getCachedTags() {
		return tags;
	}

	/**
	 * Set tags for articles manually.
	 * 
	 * @param articles
	 */
	private void setArticleTags(List<Article> articles) {
		for (Article article: articles) {
			setArticleTag(article);
		}
	}

	/**
	 * Set tags for a article manually.
	 *
	 * @param article
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
			for (Tag tag: tags) {
				if (tag.getId() == tagId) {
					aTags[i - 1] = tag;
				}
			}
		}
		article.setTags(aTags);
	}

}