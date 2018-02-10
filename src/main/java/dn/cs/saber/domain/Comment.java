package dn.cs.saber.domain;

import java.util.Date;
import java.util.List;

/**
 * Comments attached to articles.
 * 
 * @author Nen Den
 */
public class Comment {

	private int id;

	private int article;

	private User user;

	private String content;

	private Integer reply;

	private List<Comment> replies;

	private Date cts;

	private Date uts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticle() {
		return article;
	}

	public void setArticle(int article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}

	public Date getUts() {
		return uts;
	}

	public void setUts(Date uts) {
		this.uts = uts;
	}

	@Override
	public String toString() {
		return "Comment: { id: " + id + ", article: " + article + ", user: "
				+ user + ", content: " + content + ", reply: " + reply
				+ ", replies: " + replies + ", cts: " + cts + ", uts: " + uts + " }";
	}

}